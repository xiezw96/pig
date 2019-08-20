package com.pig4cloud.pig.wx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.accountmanage.user.entity.User;
import com.pig4cloud.pig.accountmanage.user.service.UserService;
import com.pig4cloud.pig.accountmanage.userlog.entity.UserLog;
import com.pig4cloud.pig.accountmanage.userlog.service.UserLogService;
import com.pig4cloud.pig.device.api.AisleSaleMachineOperation;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoods;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.service.SalesMachineAisleGoodsService;
import com.pig4cloud.pig.wx.entity.SubscribeEvent;
import com.pig4cloud.pig.wx.service.EventService;
import com.pig4cloud.pig.wx.service.ZsbytAcceptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Title: ZsbytAcceptServiceImpl</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月25日
 * @since 1.8
 */
@Service
@AllArgsConstructor
public class ZsbytAcceptServiceImpl implements ZsbytAcceptService {
	private final UserService fxUserService;
	private final SalesMachineService salesMachineService;
	private final SalesMachineAisleService salesMachineAisleService;
	private final SalesMachineAisleGoodsService salesMachineAisleGoodsService;
	private final UserLogService userLogService;
	private final AisleSaleMachineOperation saleMachineOperation;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String zsbyt(SubscribeEvent subscribeEvent, String eventKey) throws Exception {
		String logType = "GZ-ZSBYT";
		String businessKey = eventKey.replace(EventService.ZSBYT_PREFIX, "");
		String[] businessKeys = businessKey.split("-");
		String machineCode = businessKeys[0];
		String aisleCode = businessKeys[1];
		SalesMachine salesMachine = salesMachineService.getOne(Wrappers.<SalesMachine>query().lambda().eq(SalesMachine::getCode, machineCode));
		if (salesMachine == null)
			return "扫描的活动二维码设备不存在";
		SalesMachineAisle aisle = salesMachineAisleService.getOne(Wrappers.<SalesMachineAisle>query()
			.lambda()
			.eq(SalesMachineAisle::getMachineId, salesMachine.getId())
			.eq(SalesMachineAisle::getCode, aisleCode));
		// 判断货道状态是否正常
		if (aisle == null)
			return "扫描的活动二维码货道不存在";
		if (aisle.getStatus() != null && aisle.getStatus() == 4)
			return "门没关，换台柜子试试吧";
		if (aisle.getStatus() != null && aisle.getStatus() == 3)
			return "正在补货，换台柜子试试吧";
		if (aisle.getStatus() != null && aisle.getStatus() == 3)
			return "柜子开小差了，换台柜子试试吧";
		if (aisle.getStatus() == null || aisle.getStatus() != 1)
			return "货道状态异常，换一台柜子试试吧";
		// 查询货道内是否还有礼品
		List<SalesMachineAisleGoods> aisleGoodsList = salesMachineAisleGoodsService.list(Wrappers.<SalesMachineAisleGoods>query()
			.lambda()
			.eq(SalesMachineAisleGoods::getAisleId, aisle.getId())
			.gt(SalesMachineAisleGoods::getNum, 0));
		if (aisleGoodsList == null || aisleGoodsList.isEmpty())
			return "赠品已经领完了，换台柜子领取吧";
		String fromOpenid = subscribeEvent.getFromUserName();
		// 今日是否已经领取过
		int count = userLogService.getTodayUserLogByWxOpenidAndType(fromOpenid, logType);
		if (count > 0)
			return "您今天已经领取过赠品，明天再来吧";
		User user = fxUserService.getOne(Wrappers.<User>query().lambda().eq(User::getWxOpenid, fromOpenid), false);
		if (user == null) {
			// 新增关注
			user = new User();
			user.setWxOpenid(fromOpenid);
			LocalDateTime now = LocalDateTime.now();
			user.setRegisterDate(now);
			user.setLoginTime(now);
			user.setOfProxy(salesMachine.getBelongsUser());
			fxUserService.save(user);
		}
		SalesMachineAisleGoods aisleGoods = aisleGoodsList.get(0);
		aisleGoods.setNum(aisleGoods.getNum() - 1);
		// 减少存货
		salesMachineAisleGoodsService.updateById(aisleGoods);
		// 取货
		String orderId = logType + System.currentTimeMillis() + salesMachine.getId();
		if (orderId.length() > 32) {
			orderId = orderId.substring(0, 32);
		}
		// 开门取货一件
		saleMachineOperation.layTheGoods(orderId, salesMachine.getCode(), aisleCode, 1);
		UserLog userLog = new UserLog();
		userLog.setUserId(user.getId() + "");
		userLog.setWxOpenid(fromOpenid);
		userLog.setLogType(logType);
		userLog.setCreateTime(LocalDateTime.now());
		userLogService.save(userLog);
		// 已经领取
		return "领取成功，请取走您的赠品";
	}
}

