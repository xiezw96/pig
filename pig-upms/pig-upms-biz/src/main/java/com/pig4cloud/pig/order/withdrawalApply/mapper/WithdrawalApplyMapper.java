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
package com.pig4cloud.pig.order.withdrawalApply.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.order.withdrawalApply.entity.WithdrawalApply;
import org.apache.ibatis.annotations.Param;

/**
 * 提现申请
 *
 * @author zhuzubin
 * @date 2019-04-05 23:23:54
 */
public interface WithdrawalApplyMapper extends BaseMapper<WithdrawalApply> {
  /**
    * 提现申请简单分页查询
    * @param withdrawalApply 提现申请
    * @return
    */
  IPage<WithdrawalApply> getWithdrawalApplyPage(Page page, @Param("withdrawalApply") WithdrawalApply withdrawalApply);


}
