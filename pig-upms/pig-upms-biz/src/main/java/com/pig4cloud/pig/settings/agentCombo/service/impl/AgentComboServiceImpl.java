/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. Neither the name of the pig4cloud.com developer nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission. Author:
 * lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.settings.agentCombo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.common.core.util.DateConvertUtil;
import com.pig4cloud.pig.goods.goodsSpePrice.entity.GoodsSpePrice;
import com.pig4cloud.pig.goods.goodsSpePrice.service.GoodsSpePriceService;
import com.pig4cloud.pig.order.order.dto.AgentGoodsOrderDTO;
import com.pig4cloud.pig.order.order.dto.GoodsDetailDTO;
import com.pig4cloud.pig.order.order.entity.Order;
import com.pig4cloud.pig.order.order.service.OrderService;
import com.pig4cloud.pig.pay.weixin.api.entity.UnifiedOrderRequest;
import com.pig4cloud.pig.pay.weixin.api.service.WXPayService;
import com.pig4cloud.pig.pay.weixin.notify.entity.WXPayNotify;
import com.pig4cloud.pig.settings.agentCombo.dto.SelectComboDTO;
import com.pig4cloud.pig.settings.agentCombo.entity.AgentCombo;
import com.pig4cloud.pig.settings.agentCombo.mapper.AgentComboMapper;
import com.pig4cloud.pig.settings.agentCombo.service.AgentComboService;
import com.pig4cloud.pig.settings.comboDetail.entity.ComboDetail;
import com.pig4cloud.pig.settings.comboManage.entity.ComboManage;
import com.pig4cloud.pig.settings.voucher.entity.Voucher;
import com.pig4cloud.pig.settings.voucher.service.VoucherService;
import com.pig4cloud.pig.settings.voucherGrantRecord.entity.VoucherGrantRecord;
import com.pig4cloud.pig.settings.voucherGrantRecord.service.VoucherGrantRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 代理商激活套餐
 *
 * @author zhuzubin
 * @date 2019-04-05 11:27:38
 */
@Service("agentComboService")
public class AgentComboServiceImpl extends ServiceImpl<AgentComboMapper, AgentCombo> implements AgentComboService {

	@Autowired
	private VoucherService voucherService;
	@Autowired
	private VoucherGrantRecordService voucherGrantRecordService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private WXPayService payService;
	@Autowired
	private GoodsSpePriceService goodsSpePriceService;

	@Value("${pay.weixin.agentcombo.notify_url:}")
	private String notify_url;

	@Override
	public IPage<AgentCombo> getAgentComboPage(Page<AgentCombo> page, AgentCombo agentCombo) {
		return baseMapper.getAgentComboPage(page, agentCombo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveAgentCombo(Agent agent, ComboManage combo, SelectComboDTO dto) {
		AgentCombo oldAgentCombo = getOne(Wrappers.<AgentCombo>query().lambda().eq(AgentCombo::getAgentId, agent.getAgentId()));
		if (oldAgentCombo != null) {
			if (oldAgentCombo.getPayStatus() != null && oldAgentCombo.getPayStatus() == 1)
				throw new RuntimeException("已支付激活套餐");
			// 移除旧数据
			removeById(oldAgentCombo.getId());
		}
		// 保存代理商激活记录
		AgentCombo agentCombo = AgentCombo.builder()
			.agentId(agent.getAgentId())
			.comboId(combo.getId())
			.comboType(combo.getType())
			.payStatus(0)
			.tradeNo(agent.getAgentId() + combo.getType() + System.currentTimeMillis() + combo.getId() + "")
			.message(dto.getMessage())
			.addressId(dto.getAddressId())
			.totalMoney(getTotalMoney(combo, dto).setScale(2, RoundingMode.HALF_UP))
			.createDate(LocalDateTime.now())
			.build();
		this.save(agentCombo);
		return agentCombo.getTradeNo();
	}

	@Override
	public BigDecimal getComboTotalMoney(ComboManage combo, SelectComboDTO dto) {
		return getTotalMoney(combo, dto);
	}

	private BigDecimal getTotalMoney(ComboManage combo, SelectComboDTO dto) {
		BigDecimal totalMoney = null;
		switch (combo.getType()) {
			case 0:
				// 自购柜子,支付柜子的总额
				totalMoney = combo.getMachineList().stream()
					.map(m -> Optional.ofNullable(m.getMachineTradePrice()).orElse(new BigDecimal(0)).multiply(new BigDecimal(m.getNum())))
					.reduce((a, b) -> a.add(b)).orElse(new BigDecimal(0));
				break;
			case 1:
				// 购买指定商品组合
				totalMoney = orderService.getAgentTotalMoneyByGoods(createAgentGoodsOrder(combo.getDetailList(), dto.getAddressId(), ""));
				break;
			case 2:
				// 购买指定金额
				totalMoney = combo.getGoodPrice();
		}
		return totalMoney;
	}

	@Override
	public Map<String, String> unifiedOrder(AgentCombo agentCombo, String wxOpenid, String ip) {
		UnifiedOrderRequest unifiedOrderRequest = UnifiedOrderRequest.builder()
			.body("疯象乐园 - 代理商激活")
			.out_trade_no(agentCombo.getTradeNo())
			.fee_type("CNY")
			.total_fee(agentCombo.getTotalMoney().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).toString())
			.spbill_create_ip(ip)
			.notify_url(notify_url)
			.trade_type("JSAPI")
			.openid(wxOpenid)
			.build();
		return payService.unifiedOrder(unifiedOrderRequest);
	}

	@Override
	public void updateByWXPayNotify(WXPayNotify notify, ComboManage combo, Agent agent, AgentCombo agentCombo) {
		if (combo == null) {
			throw new RuntimeException("套餐不存在");
		}
		if (agent == null) {
			throw new RuntimeException("代理商不存在");
		}
		LocalDateTime payTime = LocalDateTime.parse(notify.getTime_end(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		switch (agentCombo.getComboType()) {
			case 0:
				// 自购柜子,由管理员通过后台手动绑定柜子
				break;
			case 1:
				// 购买指定商品组合
				Order order = Order.builder()
					.status(1)
					.payStatus(1)
					.payTime(payTime)
					.build();
				orderService.createAgentOrder(createAgentGoodsOrder(combo.getDetailList(), agentCombo.getAddressId(), agentCombo.getMessage()), agentCombo.getAgentId(), order);
				agentCombo.setOrderId(order.getId());
				break;
			case 2:
				// 购买指定金额
				// 支付成功后需要生成代金券
				saveVoucher(agentCombo.getAgentId(), combo);
		}
		agentCombo.setPayStatus(1);
		agentCombo.setPayTime(payTime);
		updateById(agentCombo);

		// 激活代理商
		agent.setActiveStatus(1);
		agent.setActiveTime(payTime);
		agentService.updateById(agent);

	}


	// 根据套餐金额生成代金券
	private void saveVoucher(Integer agentId, ComboManage combo) {
		Voucher voucher = new Voucher();
		voucher.setCreateDate(LocalDateTime.now());
		voucher.setStartTime(voucher.getCreateDate());
		Date endTime = DateUtil.offsetMonth(DateConvertUtil.localDateTimeToDate(voucher.getCreateDate()), 12);
		voucher.setEndTime(DateConvertUtil.dateToLocalDateTime(endTime));
		voucher.setPrice(combo.getGoodPrice());
		voucher.setMinPrice(combo.getGoodPrice());
		voucher.setName("套餐激活代金券");
		boolean result = voucherService.save(voucher);
		if (!result) {
			throw new RuntimeException("生成代金券失败");
		}

		// 生成代金券发放记录
		VoucherGrantRecord voucherGrantRecord = new VoucherGrantRecord();
		voucherGrantRecord.setUserId(agentId);
		voucherGrantRecord.setGrantType(0);
		voucherGrantRecord.setIsConsumer(0);
		voucherGrantRecord.setCreateDate(LocalDateTime.now());
		voucherGrantRecord.setVoucherId(voucher.getId());
		voucherGrantRecordService.save(voucherGrantRecord);
	}

	// 生成采购单
	private AgentGoodsOrderDTO createAgentGoodsOrder(List<ComboDetail> detailList, Integer addressId, String message) {
		AgentGoodsOrderDTO goodsOrder = new AgentGoodsOrderDTO();
		goodsOrder.setAddressId(addressId);
		goodsOrder.setMessage(message);
		List<GoodsSpePrice> goodsSpePriceList = goodsSpePriceService.list(Wrappers.<GoodsSpePrice>query().lambda().in(GoodsSpePrice::getSpePriceKey, detailList.stream().map(ComboDetail::getSpePriceKey).collect(Collectors.toList())));
		goodsOrder.setGoods(detailList.stream().map((detail) -> {
			GoodsDetailDTO goodsDetail = new GoodsDetailDTO();
			goodsDetail.setGoodsId(detail.getGoodsId());
			goodsDetail.setNum(detail.getGoodNum());
			GoodsSpePrice thisGoodsSpePrice = goodsSpePriceList.stream().filter(s -> s.getSpePriceKey().equals(detail.getSpePriceKey())).findFirst().orElse(null);
			if (thisGoodsSpePrice == null)
				throw new RuntimeException("套餐商品已删除，联系管理员");
			goodsDetail.setGoodsSpeId(thisGoodsSpePrice.getId());
			return goodsDetail;
		}).collect(Collectors.toList()));
		return goodsOrder;
	}


}

