package com.pig4cloud.pig.device.core.client.shadow;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.device.api.MachineInfo;
import com.pig4cloud.pig.device.api.SaleMachineHealth;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisleDetail;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import com.pig4cloud.pig.goods.salesMachineSpec.entity.SalesMachineSpec;
import com.pig4cloud.pig.goods.salesMachineSpec.service.SalesMachineSpecService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @title: MachineStateRedisBuff
 * @projectName pig
 * @description: TODO
 */
@Component
@Log
public class MachineStateRedisBuff implements InitializingBean {
	//REDIS KEY --- 设备类型相关
	public static final String REDIS_KEY_MACHINESPEC 	= "RKEY_MACHINESEPC";
	//REDIS KEY --- 设备相关信息
	public static final String REDIS_KEY_MACHINEINOF 	= "RKEY_MACHINEINFO";
	//REDIS KEY --- 设备状态相关
	public static final String REDIS_KEY_MACHINE_HEALTH = "RKEY_MACHINE_HEALTH";

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	SalesMachineSpecService salesMachineSpecService;
	@Autowired
	SalesMachineService salesMachineService;
	@Autowired
	SalesMachineAisleService salesMachineAisleService;

	public void afterPropertiesSet() throws Exception {
		//初始化加载
		loadSaleMachineSpecInfo();
		loadMachineInfo();
	}

	/**
	 * 1-加载机器型号信息
	 */
	public void loadSaleMachineSpecInfo(){
		salesMachineSpecService.list().forEach(salesMachineSpec -> {
			redisTemplate.opsForHash().put(REDIS_KEY_MACHINESPEC,salesMachineSpec.getId().toString(),salesMachineSpec);
		});
	}


	/**
	 * 2-加载机器设备相关的档案信息
	 */
	public void loadMachineInfo(){
		//缓存设备相关数据
		salesMachineService.list().forEach(salesMachine -> {
			MachineInfo info = (MachineInfo)redisTemplate.opsForHash().get(REDIS_KEY_MACHINEINOF,salesMachine.getCode());
			updateMachineInfo(salesMachine,info);
		});
	}

	/**
	 * 更新时间戳machineInfo信息，不存在则创建
	 * @param salesMachine
	 * @param info
	 * @return
	 */
	MachineInfo updateMachineInfo(SalesMachine salesMachine, MachineInfo info){
		if(info == null) {
			info  = new MachineInfo();
			info.setCode(salesMachine.getCode());
			info.setId(salesMachine.getId());
		}
		info.setName(salesMachine.getName());
		info.setSpecId(salesMachine.getSpecId());
		info.setStatus(salesMachine.getStatus());

		SalesMachineSpec spec = getMachineSpec(salesMachine.getSpecId());
		if(spec == null) {
			log.warning("++该设备无法查询到对应的类型信息,详细："+salesMachine);
			throw new RuntimeException("++该设备无法查询到对应的类型信息,详细："+salesMachine);
		}else{
			info.setSpecProvider(spec.getProvider());
			info.setIotProto(spec.getProtocol());
			updateMachineAisleInfo(info);
			info.setUpDate(new Date());
			redisTemplate.opsForHash().put(REDIS_KEY_MACHINEINOF,salesMachine.getCode(),info);
		}
		return info;
	}

	/**
	 * 获取机器类型
	 * @param machineSpecId
	 * @return
	 */
	SalesMachineSpec getMachineSpec(Integer machineSpecId){
		if(machineSpecId == null) return  null;
		SalesMachineSpec machineSpec = (SalesMachineSpec)redisTemplate.opsForHash().get(REDIS_KEY_MACHINESPEC,machineSpecId.toString());

		if(machineSpec == null) {
			machineSpec = salesMachineSpecService.getById(machineSpecId);
			if(machineSpec != null)
				redisTemplate.opsForHash().put(REDIS_KEY_MACHINESPEC,machineSpecId.toString(),machineSpec);
		}

		return machineSpec;
	}

	/**
	 * 更新设备货道信息
	 * @param info
	 */
	void updateMachineAisleInfo(MachineInfo info){
		List<SalesMachineAisleDetail>  aisleList = salesMachineAisleService.getSalesMachineAisleDetailByMachineId(info.getId());
		if(CollUtil.isEmpty(aisleList)) return ;
		for(SalesMachineAisleDetail aisleDetail : aisleList){
			if(!info.getAllAisleCodes().contains(aisleDetail.getCode()))
				info.getAllAisleCodes().add(aisleDetail.getCode());
		}
	}

	/**
	 * 从redis缓存中读取设备相关信息，不存在则从数据库中获取
	 * @param machineCode
	 * @return
	 */
	public MachineInfo getMachineInfo(String machineCode){
		if(StrUtil.isBlank(machineCode)) return  null;
		//1.取缓存相关数据
		MachineInfo info = (MachineInfo)redisTemplate.opsForHash().get(REDIS_KEY_MACHINEINOF,machineCode);
		if(info != null) return info;
		//2.取db生成数据
		SalesMachine sm = salesMachineService.getByCode(machineCode);
		if(sm != null) {
			info = updateMachineInfo(sm,info);
		}
		return info;
	}

	/**
	 * 从redis缓存中读取机器健康状态信息
	 * @param machineCode
	 * @return
	 */
	public SaleMachineHealth getMachineHealthState(String machineCode){
		if(StrUtil.isBlank(machineCode)) return  null;
		//1.取缓存相关数据
		SaleMachineHealth smh = (SaleMachineHealth)redisTemplate.opsForHash().get(REDIS_KEY_MACHINE_HEALTH,machineCode);
		return smh;
	}

	/**
	 * 更新redis缓存中的设备健康状态信息
	 * @param smh
	 */
	public void updateMachineHealthState(SaleMachineHealth smh){
		if(smh == null) return ;

		smh.updateTimeStamp();
		redisTemplate.opsForHash().put(REDIS_KEY_MACHINE_HEALTH,smh.getMachineCode(),smh);
	}

	public Set<String> getCachedMachineCodes(){
		return redisTemplate.opsForHash().keys(REDIS_KEY_MACHINEINOF);
	}


}
