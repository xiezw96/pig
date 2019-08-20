package com.pig4cloud.pig.goods.salesMachineAisle.service;

import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;

/**
 * <p>Title: SaleMachineAisleIotService</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月29日
 * @since 1.8
 */
public interface SaleMachineAisleIotService {
	/**
	 * 开门
	 * <p>Title: open</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月29日
	 * @author 余新引
	 */
	boolean open(SalesMachine salesMachine, SalesMachineAisle aisle);

	/**
	 * 开门
	 * <p>Title: open</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月29日
	 * @author 余新引
	 */
	boolean openAll(SalesMachine salesMachine);
}
