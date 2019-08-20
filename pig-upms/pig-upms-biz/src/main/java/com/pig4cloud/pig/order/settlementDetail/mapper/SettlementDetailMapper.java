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
package com.pig4cloud.pig.order.settlementDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.order.settlementDetail.entity.OrderSettlementDetail;
import com.pig4cloud.pig.order.settlementDetail.entity.SettlementDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 结算单明细
 *
 * @author zhuzubin
 * @date 2019-04-05 23:21:42
 */
public interface SettlementDetailMapper extends BaseMapper<SettlementDetail> {
	/**
	 * 结算单明细简单分页查询
	 *
	 * @param settlementDetail 结算单明细
	 * @return
	 */
	IPage<SettlementDetail> getSettlementDetailPage(Page page, @Param("settlementDetail") SettlementDetail settlementDetail);


	/**
	 * 统计代理商可结算金额
	 * <p>Title: getAgentSettlementMoney</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年04月17日
	 * @author 余新引
	 */
	BigDecimal getAgentSettlementMoney(@Param("agentId") Integer agentId);

	/**
	 * 查询订单结算数据
	 * <p>Title: getOrderSettlementDetail</p>
	 * <p>Description: </p>
	 *
	 * @return java.util.List<com.pig4cloud.pig.order.settlementDetail.entity.OrderSettlementDetail>
	 * @date 2019年04月18日
	 * @author 余新引
	 */
	List<OrderSettlementDetail> getOrderSettlementDetail();

	/**
	 * 修改订单是否结算字段
	 * <p>Title: updateOrderSettlement</p>
	 * <p>Description: </p>
	 *
	 * @return java.lang.Integer
	 * @date 2019年07月02日
	 * @author 余新引
	 */
	Integer updateOrderSettlement(@Param("orderId") List<Integer> orderId);

	/**
	 * 结算申请修改代理商结算明细的订单ID（设置了订单ID表示已经申请结算）
	 * <p>Title: updateSettlementDetailOrderId</p>
	 * <p>Description: </p>
	 *
	 * @return java.lang.Integer
	 * @date 2019年04月22日
	 * @author 余新引
	 */
	Integer updateSettlementDetailOrderId(@Param("agentId") Integer agentId, @Param("settlementId") Integer settlementId);

}
