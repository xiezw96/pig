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
package com.pig4cloud.pig.order.order.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.accountmanage.shoppingAddress.entity.ShoppingAddress;
import com.pig4cloud.pig.accountmanage.shoppingAddress.service.ShoppingAddressService;
import com.pig4cloud.pig.accountmanage.user.entity.User;
import com.pig4cloud.pig.accountmanage.user.service.UserService;
import com.pig4cloud.pig.device.api.AisleSaleMachineOperation;
import com.pig4cloud.pig.goods.goods.entity.Goods;
import com.pig4cloud.pig.goods.goods.service.GoodsService;
import com.pig4cloud.pig.goods.goodsSpePrice.entity.GoodsSpePrice;
import com.pig4cloud.pig.goods.goodsSpePrice.service.GoodsSpePriceService;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoods;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.service.SalesMachineAisleGoodsService;
import com.pig4cloud.pig.order.order.commons.OrderConstants;
import com.pig4cloud.pig.order.order.dto.AgentCartOrderDTO;
import com.pig4cloud.pig.order.order.dto.CartOrderDTO;
import com.pig4cloud.pig.order.order.dto.GoodsDetailDTO;
import com.pig4cloud.pig.order.order.dto.GoodsOrderDTO;
import com.pig4cloud.pig.order.order.dto.UserCartOrderDTO;
import com.pig4cloud.pig.order.order.dto.UserGoodsOrderDTO;
import com.pig4cloud.pig.order.order.dto.UserMachineOrderDTO;
import com.pig4cloud.pig.order.order.entity.AgentOrder;
import com.pig4cloud.pig.order.order.entity.GoodsOrder;
import com.pig4cloud.pig.order.order.entity.LogisticsOrder;
import com.pig4cloud.pig.order.order.entity.Order;
import com.pig4cloud.pig.order.order.mapper.OrderMapper;
import com.pig4cloud.pig.order.order.service.OrderService;
import com.pig4cloud.pig.order.orderDetail.entity.OrderDetail;
import com.pig4cloud.pig.order.orderDetail.service.OrderDetailService;
import com.pig4cloud.pig.order.shoppingCart.entity.ShoppingCart;
import com.pig4cloud.pig.order.shoppingCart.service.ShoppingCartService;
import com.pig4cloud.pig.pay.weixin.api.entity.UnifiedOrderRequest;
import com.pig4cloud.pig.pay.weixin.api.service.WXPayService;
import com.pig4cloud.pig.pay.weixin.notify.entity.WXPayNotify;
import com.pig4cloud.pig.settings.logisticsTemplate.service.LogisticsTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 订单管理
 *
 * @author zhuzubin
 * @date 2019-04-05 23:06:00
 */
@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

	@Autowired
	private ShoppingCartService cartService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsSpePriceService goodsSpePriceService;
	@Autowired
	private WXPayService payService;
	@Autowired
	private UserService userService;
	@Autowired
	private SalesMachineService salesMachineService;
	@Autowired
	private SalesMachineAisleService aisleService;
	@Autowired
	private SalesMachineAisleGoodsService aisleGoodsService;
	@Autowired
	private AisleSaleMachineOperation api;
	@Autowired
	private LogisticsTemplateService logisticsTemplateService;
	@Autowired
	private ShoppingAddressService addressService;

	@Value("${pay.weixin.order.notify_url}")
	private String notify_url;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String createAgentOrderByUserCart(AgentCartOrderDTO agentOrder, Integer userId) {
		Integer userType = 0;
		String result = createAgentOrderByGoods(getGoodsOrderDTO(userId, userType, agentOrder), userId);
		if (result != null) {
			// 清空购物车
			if (agentOrder.getCartIds() == null || agentOrder.getCartIds().isEmpty()) {
				cartService.remove(Wrappers.<ShoppingCart>query().lambda().eq(ShoppingCart::getUserId, userId).eq(ShoppingCart::getUserType, userType));
			} else {
				cartService.removeByIds(agentOrder.getCartIds().stream().collect(Collectors.toList()));
			}

		}
		return result;
	}

	private GoodsOrderDTO getGoodsOrderDTO(Integer userId, Integer userType, CartOrderDTO cartOrderDTO) {
		List<ShoppingCart> carts = cartService.list(Wrappers.<ShoppingCart>query().lambda().eq(ShoppingCart::getUserType, userType).eq(ShoppingCart::getUserId, userId));
		if (carts == null || carts.isEmpty()) {
			throw new RuntimeException("购物车是空的");
		}
		List<ShoppingCart> selectCarts = null;
		if (cartOrderDTO.getCartIds() == null || cartOrderDTO.getCartIds().isEmpty())
			selectCarts = carts;
		else
			selectCarts = carts.stream().filter((cart) -> cartOrderDTO.getCartIds().stream().anyMatch((id) -> id.equals(cart.getId()))).collect(Collectors.toList());
		List<GoodsSpePrice> goodsSpePriceList = goodsSpePriceService.list(Wrappers.<GoodsSpePrice>query().lambda().in(GoodsSpePrice::getSpePriceKey, selectCarts.stream().map(ShoppingCart::getSpePriceKey).collect(Collectors.toList())));
		GoodsOrderDTO dto = new GoodsOrderDTO();
		BeanUtils.copyProperties(cartOrderDTO, dto);
		dto.setGoods(selectCarts.stream().map((cart) -> {
			GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
			goodsDetailDTO.setGoodsId(cart.getGoodsId());
			GoodsSpePrice thisGoodsSpePrice = goodsSpePriceList.stream().filter(s -> s.getSpePriceKey().equals(cart.getSpePriceKey())).findFirst().orElse(null);
			if (thisGoodsSpePrice == null)
				throw new RuntimeException("购物车商品已删除，请刷新重试");
			goodsDetailDTO.setGoodsSpeId(thisGoodsSpePrice.getId());
			goodsDetailDTO.setSpePriceKey(cart.getSpePriceKey());
			goodsDetailDTO.setNum(cart.getNum());
			return goodsDetailDTO;
		}).collect(Collectors.toList()));
		return dto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String createAgentOrderByGoods(GoodsOrderDTO agentOrder, Integer userId) {
		Order order = Order.builder()
			.status(0)
			.payStatus(0)
			.build();
		// 总部添加
		if (agentOrder.getAdminId() != null) {
			order.setOperatorId(agentOrder.getAdminId());
			order.setOperationTime(LocalDateTime.now());
			order.setPayStatus(1);
			order.setStatus(1);
			order.setPayTime(LocalDateTime.now());
		}
		createAgentOrder(agentOrder, userId, order);
		return order.getCode();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> createUserOrderByMachine(UserMachineOrderDTO userOrder, String openid, String ip) {
		// 规则： 设备编码-货道编码
		// 扫码购买
		String[] arr = userOrder.getQrStr().split("-");
		SalesMachine salesMachine = salesMachineService.getOne(Wrappers.<SalesMachine>query().lambda().eq(SalesMachine::getCode, arr[0]));
		Optional.ofNullable(salesMachine).orElseThrow(() -> new RuntimeException("设备编码不正确，请到其他货柜购买"));

		SalesMachineAisle aisle = aisleService.getOne(Wrappers.<SalesMachineAisle>query().lambda().eq(SalesMachineAisle::getMachineId, salesMachine.getId()).eq(SalesMachineAisle::getCode, arr[1]));
		Optional.ofNullable(aisle).orElseThrow(() -> new RuntimeException("货道编码不正确，请到其他货柜购买"));
		List<SalesMachineAisleGoods> aisleGoodsList = aisleGoodsService.list(Wrappers.<SalesMachineAisleGoods>query().lambda().eq(SalesMachineAisleGoods::getAisleId, aisle.getId()).gt(SalesMachineAisleGoods::getNum, 0));
		if (aisleGoodsList == null || aisleGoodsList.isEmpty())
			throw new RuntimeException("商品已经卖完，换个商品试试吧");
		if (aisle.getStatus() == null)
			throw new RuntimeException("货道状态异常，换个商品试试吧");
		if (aisle.getStatus() == 2)
			throw new RuntimeException("货道开小差了，换个商品试试吧");
		if (aisle.getStatus() == 3)
			throw new RuntimeException("正在补货，换个商品试试吧");
		if (aisle.getStatus() == 4)
			throw new RuntimeException("商品正在出货，请稍后再试");
		User user = getAndSaveUser(userOrder.getOpenid(), salesMachine.getCode(), salesMachine.getBelongsUser());
		GoodsOrderDTO dto = new GoodsOrderDTO();
		BeanUtils.copyProperties(userOrder, dto);
		GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
		goodsDetailDTO.setGoodsId(aisleGoodsList.get(0).getGoodsId());
		GoodsSpePrice goodsSpePrice = goodsSpePriceService.getOne(Wrappers.<GoodsSpePrice>query().lambda().eq(GoodsSpePrice::getSpePriceKey, aisleGoodsList.get(0).getSpePriceKey()), false);
		if (goodsSpePrice == null) {
			throw new RuntimeException("商品下架啦，换个商品试试吧");
		}
		goodsDetailDTO.setGoodsSpeId(goodsSpePrice.getId());
		goodsDetailDTO.setNum(1);
		dto.setGoods(Arrays.asList(goodsDetailDTO));
		Order order = Order.builder()
			.status(0)
			.payStatus(0)
			.machineId(salesMachine.getId())
			.aisleId(aisle.getId())
			.build();
		createOrder(dto, user.getId(), order, false, true);

		// 统一下单
		Map<String, String> map = unifiedOrder(order, openid, ip);
		map.put("outTradeNo", order.getCode());
		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String createUserOrderByUserCart(UserCartOrderDTO userOrder, String openid) {
		Integer userType = 1;
		User user = getAndSaveUser(openid);
		UserGoodsOrderDTO userGoodsOrderDTO = new UserGoodsOrderDTO();
		GoodsOrderDTO goodsOrderDTO = getGoodsOrderDTO(user.getId(), userType, userOrder);
		BeanUtils.copyProperties(goodsOrderDTO, userGoodsOrderDTO);
		BeanUtils.copyProperties(userOrder, userGoodsOrderDTO);
		String result = createUserOrderByGoods(userGoodsOrderDTO, openid);
		if (result != null) {
			// 清空购物车
			if (userOrder.getCartIds() == null || userOrder.getCartIds().isEmpty()) {
				cartService.remove(Wrappers.<ShoppingCart>query().lambda().eq(ShoppingCart::getUserId, user.getId()).eq(ShoppingCart::getUserType, userType));
			} else {
				cartService.removeByIds(userOrder.getCartIds().stream().collect(Collectors.toList()));
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String createUserOrderByGoods(GoodsOrderDTO userOrder, String openid) {
		User user = getAndSaveUser(openid);
		Order order = Order.builder()
			.status(0)
			.payStatus(0)
			.build();
		createOrder(userOrder, user.getId(), order, false);
		return order.getCode();
	}

	private User getAndSaveUser(String openid) {
		return getAndSaveUser(openid, null, null);
	}

	private User getAndSaveUser(String openid, String eqCode, Integer agentId) {
		if (StringUtils.isEmpty(openid))
			throw new RuntimeException("openid为空");
		User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getWxOpenid, openid), false);
		if (user == null) {
			user = new User();
			user.setWxOpenid(openid);
			user.setOfProxy(agentId);
			user.setRegisterEq(eqCode);
			user.setRegisterDate(LocalDateTime.now());
			userService.save(user);
		}
		return user;
	}

	@Override
	public Map<String, String> unifiedOrder(Order order, String wxOpenid, String ip) {
		UnifiedOrderRequest unifiedOrderRequest = UnifiedOrderRequest.builder()
			.body("疯象乐园 - 订单支付")
			.out_trade_no(order.getCode())
			.fee_type("CNY")
			.total_fee(order.getTotalMoney().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).toString())
			.spbill_create_ip(ip)
			.notify_url(notify_url)
			.trade_type("JSAPI")
			.openid(wxOpenid)
			.build();
		return payService.unifiedOrder(unifiedOrderRequest);
	}

	@Override
	public void createAgentOrder(GoodsOrderDTO agentOrder, Integer userId, Order order) {
		createOrder(agentOrder, userId, order, true);
	}

	private void createOrder(GoodsOrderDTO orderDTO, Integer userId, Order order, boolean isAgent) {
		createOrder(orderDTO, userId, order, isAgent, false);
	}

	private void createOrder(GoodsOrderDTO orderDTO, Integer userId, Order order, boolean isAgent, boolean byMachine) {
		order = createOrderEntity(orderDTO, userId, order, isAgent, byMachine);
		if (!save(order)) {
			throw new RuntimeException("添加订单出错了");
		}
		Integer orderId = order.getId();
		// 添加订单明细
		if (!orderDetailService.saveBatch(order.getOrderDetails().stream().map(d -> {
			d.setOrderId(orderId);
			return d;
		}).collect(Collectors.toList()))) {
			throw new RuntimeException("添加订单明细出错了");
		}
	}

	// 创建消费单实体
	private Order createUserOrderEntity(GoodsOrderDTO orderDTO, Integer userId, Order order) {
		return createOrderEntity(orderDTO, userId, order, false, false);
	}

	// 创建采购单实体
	private Order createAgentOrderEntity(GoodsOrderDTO orderDTO, Integer userId, Order order) {
		return createOrderEntity(orderDTO, userId, order, true, false);
	}

	// 创建线上订单实体
	private Order createOrderEntity(GoodsOrderDTO orderDTO, Integer userId, Order order, boolean isAgent) {
		return createOrderEntity(orderDTO, userId, order, isAgent, false);
	}

	// 创建订单实体
	private Order createOrderEntity(GoodsOrderDTO orderDTO, Integer userId, Order order, boolean isAgent, boolean byMachine) {
		if (!byMachine && (orderDTO.getAddressId() == null || orderDTO.getAddressId() <= 0)) {
			throw new RuntimeException("请先选择收货地址");
		}
		if (orderDTO.getGoods() == null || orderDTO.getGoods().isEmpty()) {
			throw new RuntimeException("订单商品为空");
		}
		ShoppingAddress address = !byMachine ? Optional.ofNullable(addressService.getById(orderDTO.getAddressId())).orElseThrow(() -> new RuntimeException("收货地址不存在")) : null;
		Collection<Goods> goodsList = goodsService.listByIds(orderDTO.getGoods().stream().map(GoodsDetailDTO::getGoodsId).collect(Collectors.toList()));
		Collection<GoodsSpePrice> goodsSpePrices = goodsSpePriceService.listByIds(orderDTO.getGoods().stream().map(GoodsDetailDTO::getGoodsSpeId).collect(Collectors.toList()));
		// 订单总价
		List<BigDecimal> sumMoneys = new ArrayList<>();
		List<OrderDetail> orderDetails = orderDTO.getGoods().stream().map((goodsDetail) -> {
			Goods thisGoods = goodsList.stream().filter((goods -> goods.getId().equals(goodsDetail.getGoodsId()))).findFirst().orElseThrow(() -> new RuntimeException("商品不存在"));
			GoodsSpePrice thisSpePrice = goodsSpePrices.stream().filter((spePrice -> spePrice.getId().equals(goodsDetail.getGoodsSpeId()))).findFirst().orElseThrow(() -> new RuntimeException("规格不存在"));
			BigDecimal price = null;
			if (isAgent) {
				price = Optional.ofNullable(thisSpePrice.getTradePrice()).orElseThrow(() -> new RuntimeException("统批价为空"));
			} else {
				price = Optional.ofNullable(thisSpePrice.getSalePrice()).orElseThrow(() -> new RuntimeException("零售价为空"));
			}
			// 单价 × 数量
			BigDecimal goodsMoney = price.multiply(new BigDecimal(goodsDetail.getNum()));
			sumMoneys.add(goodsMoney);

			return OrderDetail.builder()
				.goodsId(thisGoods.getId())
				.goodsName(thisGoods.getName())
				.goodsAttId(thisGoods.getAttId())
				.spePriceId(thisSpePrice.getId())
				.spePriceKey(thisSpePrice.getSpePriceKey())
				.goodsSpe1(thisSpePrice.getSpeId1())
				.goodsSpe2(thisSpePrice.getSpeId2())
				.salePrice(thisSpePrice.getSalePrice())
				.tradePrice(thisSpePrice.getTradePrice())
				.payPrice(goodsMoney)
				.payNum(goodsDetail.getNum())
				.build();
		}).collect(Collectors.toList());
		BigDecimal sumMoney = sumMoneys.stream().reduce(BigDecimal::add).orElse(new BigDecimal(0));
		order.setCode(DateUtil.format(new Date(), "yyyyMMdd") + userId + orderDTO.getGoods().size() + System.currentTimeMillis() + "");
		order.setType(isAgent ? OrderConstants.AGENT : OrderConstants.USER);
		order.setCreatorId(userId);
		order.setCreateTime(LocalDateTime.now());
		order.setRemark(orderDTO.getMessage());
		if (address != null) {
			order.setName(address.getName());
			order.setPhone(address.getPhone());
			order.setPrivince(address.getPrivince());
			order.setCity(address.getCity());
			order.setArea(address.getArea());
			order.setAddress(address.getAddress());
			order.setShoppingAddress(address.getId());

			// 物流费
			BigDecimal totalLogisticsPrice = getLogisticsPrice(orderDTO.getGoods(), address, goodsList, goodsSpePrices);
			order.setTotalMoney(sumMoney.add(totalLogisticsPrice).setScale(2, RoundingMode.HALF_UP));
		} else {
			order.setTotalMoney(sumMoney.setScale(2, RoundingMode.HALF_UP));
		}
		order.setOrderDetails(orderDetails);
		return order;
	}

	@Override
	public BigDecimal getOrderDayMoneySum(String date, Integer userId) {
		return Optional.ofNullable(baseMapper.getOrderDayMoneySum(date, userId)).orElse(new BigDecimal(0));
	}

	@Override
	public int getOrderDaySum(String date, Integer payStatus, Integer userId) {
		return baseMapper.getOrderDaySum(date, payStatus, userId);
	}

	/**
	 * 订单管理简单分页查询
	 *
	 * @param order 订单管理
	 * @return
	 */
	@Override
	public IPage<Order> getOrderPage(Page<Order> page, Order order) {
		return baseMapper.getOrderPage(page, order);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateByWXPayNotify(WXPayNotify notify, Order order) {
		order.setPayStatus(1);
		order.setStatus(1);
		order.setPayTime(LocalDateTime.parse(notify.getTime_end(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
		updateById(order);
		// 设备上的订单需要执行开门动作
		if (order.getMachineId() != null && order.getAisleId() != null) {
			SalesMachine salesMachine = salesMachineService.getById(order.getMachineId());
			SalesMachineAisle aisle = aisleService.getById(order.getAisleId());
			if (salesMachine == null || aisle == null)
				throw new RuntimeException("设备或货道不存在");
			// 开门
			api.layTheGoods(order.getCode(), salesMachine.getCode(), aisle.getCode(), 1);
			// 减少货品数量
			List<SalesMachineAisleGoods> aisleGoodsList = aisleGoodsService.list(Wrappers.<SalesMachineAisleGoods>query().lambda().eq(SalesMachineAisleGoods::getAisleId, aisle.getId()).gt(SalesMachineAisleGoods::getNum, 0));
			if (aisleGoodsList != null && !aisleGoodsList.isEmpty()) {
				SalesMachineAisleGoods aisleGoods = aisleGoodsList.get(0);
				aisleGoods.setNum(aisleGoods.getNum() - 1);
				aisleGoodsService.updateById(aisleGoods);
			}
			// 更新设备状态
			salesMachine.setStatus(4);
			aisle.setStatus(4);
			salesMachineService.updateById(salesMachine);
			aisleService.updateById(aisle);
		} else {
			List<OrderDetail> orderDetails = orderDetailService.list(Wrappers.<OrderDetail>query().lambda().eq(OrderDetail::getOrderId, order.getId()));
			Collection<GoodsSpePrice> goodsSpePrices = goodsSpePriceService.list(Wrappers.<GoodsSpePrice>query().lambda().in(GoodsSpePrice::getSpePriceKey, orderDetails.stream().map(OrderDetail::getSpePriceKey).collect(Collectors.toList())));
			orderDetails.stream().forEach((d) -> {
				Optional.ofNullable(goodsSpePrices.stream().filter(p -> p.getSpePriceKey().equals(d.getSpePriceKey())).findFirst().orElse(null)).ifPresent(p -> {
					// 减库存
					p.setInventoryNum(Optional.ofNullable(p.getInventoryNum()).orElse(0) - d.getPayNum());
					// 加销量
					p.setSalesVolume(Optional.ofNullable(p.getSalesVolume()).orElse(0) + d.getPayNum());
				});
			});
			goodsSpePriceService.updateBatchById(goodsSpePrices);
		}
	}

	@Override
	public BigDecimal getTotalLogisticsPriceByCartId(AgentCartOrderDTO agentOrder, Integer userId) {
		return getTotalLogisticsPriceByGoods(getGoodsOrderDTO(userId, 0, agentOrder));
	}

	@Override
	public BigDecimal getTotalLogisticsPriceByGoods(GoodsOrderDTO agentOrder) {
		if (agentOrder.getAddressId() == null || agentOrder.getAddressId() <= 0) {
			throw new RuntimeException("请先选择收货地址");
		}
		if (agentOrder.getGoods() == null || agentOrder.getGoods().isEmpty()) {
			throw new RuntimeException("订单商品为空");
		}
		ShoppingAddress address = Optional.ofNullable(addressService.getById(agentOrder.getAddressId())).orElseThrow(() -> new RuntimeException("收货地址不存在"));
		Collection<Goods> goodsList = goodsService.listByIds(agentOrder.getGoods().stream().map(GoodsDetailDTO::getGoodsId).collect(Collectors.toList()));
		Collection<GoodsSpePrice> goodsSpePrices = goodsSpePriceService.listByIds(agentOrder.getGoods().stream().map(GoodsDetailDTO::getGoodsSpeId).collect(Collectors.toList()));
		return getLogisticsPrice(agentOrder.getGoods(), address, goodsList, goodsSpePrices);
	}

	// 计算物流价格
	private BigDecimal getLogisticsPrice(List<GoodsDetailDTO> goodsDetails, ShoppingAddress address, Collection<Goods> goodsList, Collection<GoodsSpePrice> goodsSpePrices) {
		BigDecimal weight = goodsDetails.stream()
			// 排除包邮
			.filter(g -> goodsList.stream().filter((goods -> goods.getId().equals(g.getGoodsId()))).findFirst().orElseThrow(() -> new RuntimeException("商品不存在")).getLogisticsType() == 1)
			// 重量 * 数量
			.map(g -> Optional.ofNullable(goodsSpePrices.stream().filter((spePrice -> spePrice.getId().equals(g.getGoodsSpeId()))).findFirst().orElseThrow(() -> new RuntimeException("规格不存在")).getWeight()).orElse(new BigDecimal(0).multiply(new BigDecimal(g.getNum()))))
			.reduce((a, b) -> a.add(b)).orElse(new BigDecimal(0));
		return logisticsTemplateService.getLogisticsPrice(address.getPrivince(), weight);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean remove(Integer orderId) {
		orderDetailService.remove(Wrappers.<OrderDetail>query().lambda().eq(OrderDetail::getOrderId, orderId));
		return removeById(orderId);
	}

	@Override
	public IPage<GoodsOrder> getGoodsOrderPage(Page<GoodsOrder> page, GoodsOrder goodsOrder) {
		return baseMapper.getGoodsOrderPage(page, goodsOrder);
	}

	@Override
	public IPage<AgentOrder> getAllAgentOrderPage(Page<AgentOrder> page, AgentOrder agentOrder) {
		return baseMapper.getAllAgentOrderPage(page, agentOrder);
	}

	@Override
	public IPage<LogisticsOrder> getLogisticsOrderPage(Page<LogisticsOrder> page, LogisticsOrder order) {
		return baseMapper.getLogisticsOrderPage(page, order);
	}

	@Override
	public BigDecimal getAgentTotalMoneyByCartId(AgentCartOrderDTO dto, Integer userId) {
		return createAgentOrderEntity(getGoodsOrderDTO(userId, 0, dto), userId, new Order()).getTotalMoney();
	}

	@Override
	public BigDecimal getUserTotalMoneyByCartId(UserCartOrderDTO dto, Integer userId) {
		return createUserOrderEntity(getGoodsOrderDTO(userId, 1, dto), userId, new Order()).getTotalMoney();
	}

	@Override
	public BigDecimal getAgentTotalMoneyByGoods(GoodsOrderDTO dto) {
		return createAgentOrderEntity(dto, null, new Order()).getTotalMoney();
	}

	@Override
	public BigDecimal getUserTotalMoneyByGoods(GoodsOrderDTO dto) {
		return createUserOrderEntity(dto, null, new Order()).getTotalMoney();
	}
}
