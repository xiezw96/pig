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
package com.pig4cloud.pig.order.withdrawalApply.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.order.withdrawalApply.dto.WithdrawalApplyReqDTO;
import com.pig4cloud.pig.order.withdrawalApply.entity.WithdrawalApply;

/**
 * 提现申请
 *
 * @author zhuzubin
 * @date 2019-04-05 23:23:54
 */
public interface WithdrawalApplyService extends IService<WithdrawalApply> {

	/**
	 * 提现申请简单分页查询
	 *
	 * @param withdrawalApply 提现申请
	 * @return
	 */
	IPage<WithdrawalApply> getWithdrawalApplyPage(Page<WithdrawalApply> page, WithdrawalApply withdrawalApply);

	/**
	 * 代理商申请提现
	 * <p>Title: requestWithdrawalApply</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年04月28日
	 * @author 余新引
	 */
	boolean requestWithdrawalApply(Agent agent, WithdrawalApplyReqDTO reqDTO);

	/**
	 * 审核提现申请单
	 * <p>Title: audit</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年05月12日
	 * @author 余新引
	 */
	boolean audit(WithdrawalApply ap);

	/**
	 * 提现打款状态
	 * <p>Title: remit</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年07月01日
	 * @author 余新引
	 */
	boolean remit(WithdrawalApply ap);
}
