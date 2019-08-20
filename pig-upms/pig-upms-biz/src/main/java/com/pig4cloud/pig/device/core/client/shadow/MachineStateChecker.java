package com.pig4cloud.pig.device.core.client.shadow;

import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.device.api.MachineInfo;
import com.pig4cloud.pig.device.api.SaleMachineHealth;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.*;

/**
 * @title: MachineTaskChecker
 * @projectName pig
 * @description: 设备相关调度任务
 */
@Component
@Log
public class MachineStateChecker implements InitializingBean {

	//状态同步周期（秒）
	@Value("${device.client.state.checkpriod:3600}")
	int stateCheckPeriodTimeSecs;
	//缓存数据全量同步周期（秒）
	@Value("${device.client.cache.checkpriod:3600}")
	int dbReloadPeriodTimesecs ;

	@Value("${device.client.healthchecker.workernum:5}")
	int workerNum ;

	@Value("${device.client.healthchecker.quesize:50}")
	int queSize ;

	@Value("${device.client.healthchecker.timewait:3000}")
	long timeWait ;

	@Value("${device.client.healthchecker.inputwaittime:50}")
	long inputWaiteTimeMillcs;

	BlockingQueue<String> que = null;

	@Autowired
	MachineStateRedisBuff  machineStateRedisBuff;

	@Autowired
	MachineHealthRealTimeService machineHealthRealTimeService;

	ExecutorService  se = null;

	public void afterPropertiesSet() throws Exception {
		que = new ArrayBlockingQueue<String>(queSize);
		se = Executors.newScheduledThreadPool(workerNum);
		se.submit(new PutWorker());
		for(int i=1; i<workerNum; i++)
			se.submit(new CheckWorker());
	}


	/**
	 * 机器健康状态检测线程
	 */
	class CheckWorker implements  Runnable {
		public void run() {
			String machineCode = null;
			while(true) {
				try {
					machineCode = que.poll(timeWait, TimeUnit.MILLISECONDS);
					if(StrUtil.isNotBlank(machineCode)){
						MachineInfo info = machineStateRedisBuff.getMachineInfo(machineCode);
						if(info == null) continue;
						//TODO 效率提效改造
						SaleMachineHealth smh = machineHealthRealTimeService.checkMachineHealthByRealTime(info);
						machineStateRedisBuff.updateMachineHealthState(smh);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					break ;
				}

			}
		}
	}

	/**
	 * 任务分派线程
	 */
	class PutWorker implements Runnable {
		public void run() {
			while(true){
				Set<String> machineCodes = machineStateRedisBuff.getCachedMachineCodes();
				log.info("+++后台状态检查code分派线程："+Thread.currentThread().getId()+",=="+machineCodes.size());
				if(machineCodes != null) {
					machineCodes.forEach(s -> {
						try{
							while(true) {
								if(que.offer(s,inputWaiteTimeMillcs,TimeUnit.MILLISECONDS))
									break;
							}
						}catch (InterruptedException e){
							log.warning("++检查线程被中断！");
							throw new RuntimeException(e);
						}
					});
					//
					try {
						Thread.sleep(stateCheckPeriodTimeSecs * 1000);
					} catch (InterruptedException e) {
						log.warning("++检查线程被中断！");
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

}
