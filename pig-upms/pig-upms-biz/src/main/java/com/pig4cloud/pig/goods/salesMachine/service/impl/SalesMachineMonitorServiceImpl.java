package com.pig4cloud.pig.goods.salesMachine.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.common.core.util.DateConvertUtil;
import com.pig4cloud.pig.device.api.AisleSaleMachineOperation;
import com.pig4cloud.pig.device.api.SaleMachineHealth;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineMonitorService;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Title: SalesMachineMonitorServiceImpl</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月23日
 * @since 1.8
 */
@Service
@Slf4j
@AllArgsConstructor
public class SalesMachineMonitorServiceImpl implements SalesMachineMonitorService {
	private final SalesMachineService salesMachineService;
	private final AisleSaleMachineOperation api;
	private final SalesMachineAisleService salesMachineAisleService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void monitor() {
		// 排除已回收的设备
		List<SalesMachine> all = salesMachineService.list(Wrappers.<SalesMachine>query().lambda().ne(SalesMachine::getStatus, 6));
		if (all == null || all.isEmpty()) {
			log.warn("在线设备列表为空，未更新任何设备状态");
			return;
		}
		List<SalesMachineAisle> allAisle = salesMachineAisleService.list(Wrappers.<SalesMachineAisle>query().lambda().in(SalesMachineAisle::getMachineId, all.stream().map(SalesMachine::getId).collect(Collectors.toList())));
		all.stream().forEach(salesMachine -> {
			// 查询设备状态
			List<SalesMachineAisle> thisAisle = allAisle.stream().filter(aisle -> aisle.getMachineId().equals(salesMachine.getId())).collect(Collectors.toList());
			try {
				boolean isOnline = api.checkIfOnline(salesMachine.getCode(), true);
				SaleMachineHealth saleMachineHealth = null;
				if (isOnline) saleMachineHealth = api.checkHealthOfMachine(salesMachine.getCode(), true);
				if (!isOnline) {
					// 修改设备状态为离线
					if (salesMachine.getStatus() != 0)
						salesMachine.setStatus(5);
				} else if (isOnline && salesMachine.getStatus() == 0) {
					// 初次激活
					salesMachine.setActivateTime(DateConvertUtil.dateToLocalDateTime(saleMachineHealth.getUpdateTimes()));
					salesMachine.setStatus(1);
				} else {
					// 判断货道状态
					Map<String, Integer> aislesStatus = saleMachineHealth.getAislesStatus();
					boolean isOpen = false, isFail = false;
					for (String key : aislesStatus.keySet()) {
						Integer state = aislesStatus.get(key);
						if (state == null)
							continue;
						// 货道故障，则设备故障
						if (state == SaleMachineHealth.AISLE_STATE_EC_FAIL) {
							salesMachine.setStatus(2);
							isFail = true;
						}
						if (SaleMachineHealth.AISLE_STATE_OPEN == state && !key.equals("16"))
							isOpen = true;
						// 开门状态不需要变更，除了16货道
						if (isOpen) {
							continue;
						}
						thisAisle.stream().filter(aisle -> key.equals(aisle.getCode())).forEach(aisle -> {
							if (SaleMachineHealth.AISLE_STATE_EC_FAIL == state)
								aisle.setStatus(2);
							if (SaleMachineHealth.AISLE_STATE_NORAML == state || (SaleMachineHealth.AISLE_STATE_OPEN == state && key.equals("16")))
								aisle.setStatus(1);
						});
					}
					// 离线或故障转正常
					if (!isFail && (salesMachine.getStatus() == 5 || salesMachine.getStatus() == 2))
						salesMachine.setStatus(1);
					if (!isOpen && (salesMachine.getStatus() == 3 || salesMachine.getStatus() == 4))
						salesMachine.setStatus(1);
				}
			} catch (Exception e) {
				log.error("更新设备状态异常，{}", e.getMessage(), e);
				throw e;
			}
		});
		// 更新设备和货道
		salesMachineService.updateBatchById(all);
		salesMachineAisleService.updateBatchById(allAisle);
	}
}
