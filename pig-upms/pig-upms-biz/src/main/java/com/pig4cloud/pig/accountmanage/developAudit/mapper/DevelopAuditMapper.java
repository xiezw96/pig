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
package com.pig4cloud.pig.accountmanage.developAudit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.developAudit.entity.DevelopAudit;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提现申请
 *
 * @author zhuzubin
 * @date 2019-04-05 23:38:35
 */
public interface DevelopAuditMapper extends BaseMapper<DevelopAudit> {
	/**
	 * 提现申请简单分页查询
	 *
	 * @param developAudit 提现申请
	 * @return
	 */
	IPage<DevelopAudit> getDevelopAuditPage(Page page, @Param("developAudit") DevelopAudit developAudit);


	/**
	 * 查询代理商累计发展收益
	 * <p>Title: getDevelopSumMonty</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年04月28日
	 * @author 余新引
	 */
	BigDecimal getDevelopSumMonty(@Param("agentId") Integer agentId);

	/**
	 * 查询待结算奖励
	 * <p>Title: getDevelopSumMonty</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年04月28日
	 * @author 余新引
	 */
	List<Integer> getDevelopSettlementAgentId(@Param("agentId") Integer agentId);
}
