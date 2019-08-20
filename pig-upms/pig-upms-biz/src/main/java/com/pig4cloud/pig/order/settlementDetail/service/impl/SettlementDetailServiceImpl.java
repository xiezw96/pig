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
package com.pig4cloud.pig.order.settlementDetail.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.entity.AgentChain;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.order.settlementDetail.commons.SettlementSource;
import com.pig4cloud.pig.order.settlementDetail.entity.OrderSettlementDetail;
import com.pig4cloud.pig.order.settlementDetail.entity.SettlementDetail;
import com.pig4cloud.pig.order.settlementDetail.entity.SettlementMoney;
import com.pig4cloud.pig.order.settlementDetail.mapper.SettlementDetailMapper;
import com.pig4cloud.pig.order.settlementDetail.service.SettlementDetailService;
import com.pig4cloud.pig.settings.commission.service.CommissionService;
import com.pig4cloud.pig.settings.shareProfit.entity.ShareProfit;
import com.pig4cloud.pig.settings.shareProfit.service.ShareProfitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 结算单明细
 *
 * @author zhuzubin
 * @date 2019-04-05 23:21:42
 */
@Slf4j
@Service("settlementDetailService")
public class SettlementDetailServiceImpl extends ServiceImpl<SettlementDetailMapper, SettlementDetail> implements SettlementDetailService {

	@Autowired
	private ShareProfitService shareProfitService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private CommissionService commissionService;

	/**
	 * 结算单明细简单分页查询
	 *
	 * @param settlementDetail 结算单明细
	 * @return
	 */
	@Override
	public IPage<SettlementDetail> getSettlementDetailPage(Page<SettlementDetail> page, SettlementDetail settlementDetail) {
		return baseMapper.getSettlementDetailPage(page, settlementDetail);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Integer executeOrderSettlement() {
		List<SettlementDetail> settlementDetails = new ArrayList<>();
		// 用于缓存代理商链，减少查询次数
		Map<Integer, AgentChain> agentChainCache = new HashMap<>();
		// 扫描订单明细数据
		List<OrderSettlementDetail> orderSettlementDetails = baseMapper.getOrderSettlementDetail();
		if (orderSettlementDetails != null && !orderSettlementDetails.isEmpty()) {
			// 查询商城分润规则
			List<ShareProfit> shareProfits = new ArrayList<>();
			// 分润计算规则
			orderSettlementDetails.stream().forEach(orderSettlementDetail -> {
				// 利润 = 零售价 - 统批价
				BigDecimal profit = orderSettlementDetail.getSalePrice().subtract(orderSettlementDetail.getTradePrice());
				// 没有利润，不计算
				if (profit.compareTo(new BigDecimal(0)) <= 0)
					return;
				// 线上商城分润计算规则
				if (orderSettlementDetail.getMachineId() == null || orderSettlementDetail.getMachineId() <= 0) {
					if (shareProfits.isEmpty()) {
						shareProfits.addAll(shareProfitService.list());
					}
					final ShareProfit shareProfit = shareProfits != null && !shareProfits.isEmpty() ? shareProfits.get(0) : null;
					if (shareProfit == null) {
						log.error("未设置在线商城分润规则，无法统计在线商城分润结算明细");
					}
					// TODO 商城分润
				} else {
					// 线下分润计算规则
					Integer agentId = orderSettlementDetail.getMachineAgentId();
					AgentChain agentChain = agentChainCache.computeIfAbsent(agentId, (k) -> agentService.getAgentChain(k));
					if (agentChain != null)
						// 初始剩余利润为 100%，即为 1
						createSettlementDetail(orderSettlementDetail, settlementDetails, agentChain, profit, agentChain.getAgent().getProfit(), new BigDecimal(1));
				}
			});
		}
		int result = settlementDetails.size();
		if (result > 0) {
			saveBatch(settlementDetails);
			baseMapper.updateOrderSettlement(settlementDetails.stream().map(SettlementDetail::getOrderId).collect(Collectors.toList()));
		}
		return result;
	}

	/**
	 * 线下分润计算
	 * <p>Title: createSettlementDetail</p>
	 * <p>Description: </p>
	 *
	 * @param profit        剩余利润
	 * @param shareProfit   代理商分润比例
	 * @param surplusProfit 剩余利润比例
	 * @return com.pig4cloud.pig.order.settlementDetail.entity.SettlementDetail
	 * @date 2019年04月18日
	 * @author 余新引
	 */
	private void createSettlementDetail(OrderSettlementDetail orderSettlementDetail, List<SettlementDetail> settlementDetails, AgentChain agentChain, BigDecimal profit, BigDecimal shareProfit, BigDecimal surplusProfit) {
		// 计算原理：
		// 递归所有上级进行分润计算，剩余利润第一次为1（100%）
		// 每次递归都将每一级的代理商的分润比例扣除
		// 直到最后的剩余利润比例即为一级代理商利润比例
		AgentChain parent = agentChain.getParent();
		if (parent == null) {
			// 没有上级，享受全部剩余利润
			shareProfit = surplusProfit;
		} else if (shareProfit == null) {
			shareProfit = new BigDecimal(0);
		}
		// 结算金额
		BigDecimal settlementPrice = shareProfit.multiply(profit);
		// 当前代理商为柜子归属代理商，则需要返还成本
		boolean isFirst = orderSettlementDetail.getMachineAgentId().compareTo(agentChain.getAgent().getAgentId()) == 0;
		if (isFirst) {
			// 柜子归属代理商结算金额 = (分润比例 × 利润) + 统批价
			// 统批价作为归属代理商的成本返还，不考虑优惠券的情况
			settlementPrice = settlementPrice.add(orderSettlementDetail.getTradePrice());
		}
		// 剩余利润 = 剩余利润比例 - 已分成的利润比例
		surplusProfit = surplusProfit.subtract(shareProfit);
		settlementDetails.add(buildSettmentDetail(orderSettlementDetail, agentChain.getAgent(), shareProfit, settlementPrice));
		// 上级分成计算
		if (parent != null)
			createSettlementDetail(orderSettlementDetail, settlementDetails, parent, profit, parent.getAgent().getProfit(), surplusProfit);
	}

	private SettlementDetail buildSettmentDetail(OrderSettlementDetail settlementDetail, Agent agent, BigDecimal shareProfit, BigDecimal settlementPrice) {
		int source = -1;
		// 存在柜子id并且柜子所属代理商跟当前代理商相同
		if (settlementDetail.getMachineId() != null && settlementDetail.getMachineAgentId().compareTo(agent.getAgentId()) == 0) {
			source = SettlementSource.SELF.ordinal();
		} else if (settlementDetail.getMachineId() == null) {
			source = SettlementSource.ONLINE.ordinal();
		} else {
			source = SettlementSource.SUBORDINATE.ordinal();
		}
		return SettlementDetail.builder()
			.agentId(agent.getAgentId())
			.sourceAgentId(settlementDetail.getMachineAgentId() != null ? settlementDetail.getMachineAgentId() : settlementDetail.getUserAgentId())
			.userId(settlementDetail.getUserId())
			.machineId(settlementDetail.getMachineId())
			.source(source)
			.settlementId(null)
			.orderId(settlementDetail.getOrderId())
			.orderCode(settlementDetail.getOrderCode())
			.orderDetailId(settlementDetail.getOrderDetailId())
			.shareProfit(shareProfit)
			.payTime(settlementDetail.getPayTime())
			.orderCreateTime(settlementDetail.getOrderCreateTime())
			.settlementPrice(settlementPrice)
			.build();
	}

	@Override
	public SettlementMoney getAgentSettlementMoney(Integer agentId) {
		SettlementMoney settlementMoney = new SettlementMoney();
		BigDecimal commission = commissionService.getCommission();
		BigDecimal total = Optional.ofNullable(baseMapper.getAgentSettlementMoney(agentId)).orElse(new BigDecimal(0));
		settlementMoney.setCommission(commissionService.getCommissionValue(commission, total).setScale(2, RoundingMode.HALF_UP));
		settlementMoney.setOriginalSettlementPrice(total.setScale(2, RoundingMode.HALF_UP));
		settlementMoney.setSettlementPrice(commissionService.subtractCommission(commission, total).setScale(2, RoundingMode.HALF_UP));
		return settlementMoney;
	}

	@Override
	public boolean updateSettlementDetailOrderId(Integer agentId, Integer settlementId) {
		return retBool(baseMapper.updateSettlementDetailOrderId(agentId, settlementId));
	}
}
