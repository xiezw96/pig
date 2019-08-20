package com.pig4cloud.pig.goods.salesMachineAisle.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.device.api.AisleMachineOperException;
import com.pig4cloud.pig.device.api.AisleSaleMachineOperation;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SaleMachineAisleIotService;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Title: SaleMachineAisleIotServiceImpl</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月29日
 * @since 1.8
 */
@Component
@AllArgsConstructor
public class SaleMachineAisleIotServiceImpl implements SaleMachineAisleIotService {
	private final AisleSaleMachineOperation aisleOperation;
	private final SalesMachineService salesMachineService;
	private final SalesMachineAisleService aisleService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean open(SalesMachine salesMachine, SalesMachineAisle aisle) {
		try {
			boolean result = aisleOperation.openTheAsileDoor(salesMachine.getCode(), aisle.getCode());
			if (result) {
				salesMachine.setStatus(3);
				aisle.setStatus(3);
				salesMachineService.updateById(salesMachine);
				aisleService.updateById(aisle);
			}
			return result;
		} catch (AisleMachineOperException e) {
			throw new RuntimeException("设备异常：" + e.getMessage() + "，错误码：" + e.getCode());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean openAll(SalesMachine salesMachine) {
		try {
			List<SalesMachineAisle> aisles = aisleService.list(Wrappers.<SalesMachineAisle>query().lambda().eq(SalesMachineAisle::getMachineId, salesMachine.getId()));
			String[] failCode = aisleOperation.openAllTheDoor(salesMachine.getCode());
			if (failCode.length == aisles.size()) {
				throw new RuntimeException("全部开启失败");
			}
			if (failCode == null || failCode.length == 0) {
				salesMachine.setStatus(3);
				salesMachineService.updateById(salesMachine);
				// 更新全部货道
				aisleService.updateBatchById(aisles.stream().map(aisle -> {
					aisle.setStatus(3);
					return aisle;
				}).collect(Collectors.toList()));
			} else {
				// 只更新成功的数据
				aisleService.updateBatchById(aisles.stream().filter(aisle -> {
					boolean flag = Arrays.stream(failCode).noneMatch(fail -> aisle.getCode().equals(fail));
					if (flag)
						aisle.setStatus(3);
					return flag;
				}).collect(Collectors.toList()));
			}
			return true;
		} catch (AisleMachineOperException e) {
			throw new RuntimeException("设备异常：" + e.getMessage() + "，错误码：" + e.getCode());
		}
	}
}
