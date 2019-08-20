/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.order.settlementDetail.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.order.settlementDetail.entity.SettlementDetail;
import com.pig4cloud.pig.order.settlementDetail.entity.SettlementMoney;

/**
 * 结算单明细
 *
 * @author zhuzubin
 * @date 2019-04-05 23:21:42
 */
public interface SettlementDetailService extends IService<SettlementDetail> {

	/**
	 * 结算单明细简单分页查询
	 *
	 * @param settlementDetail 结算单明细
	 * @return
	 */
	IPage<SettlementDetail> getSettlementDetailPage(Page<SettlementDetail> page, SettlementDetail settlementDetail);


	/**
	 * 执行订单结算统计
	 * <p>Title: executeOrderSettlement</p>
	 * <p>Description: </p>
	 *
	 * @return java.lang.Integer
	 * @date 2019年04月17日
	 * @author 余新引
	 */
	Integer executeOrderSettlement();

	/**
	 * 统计可结算金额
	 * <p>Title: getAgentSettlementMoney</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年04月17日
	 * @author 余新引
	 */
	SettlementMoney getAgentSettlementMoney(Integer agentId);

	/**
	 * 结算申请修改代理商结算明细的订单ID（设置了订单ID表示已经申请结算）
	 * <p>Title: updateSettlementDetailOrderId</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年04月22日
	 * @author 余新引
	 */
	boolean updateSettlementDetailOrderId(Integer agentId, Integer settlementId);
}
