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
package com.pig4cloud.pig.order.returnOrder.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.order.returnOrder.entity.ReturnOrder;
import com.pig4cloud.pig.order.returnOrder.mapper.ReturnOrderMapper;
import com.pig4cloud.pig.order.returnOrder.service.ReturnOrderService;
import org.springframework.stereotype.Service;

/**
 * 退货订单
 *
 * @author yuxinyin
 * @date 2019-04-17 19:08:26
 */
@Service("returnOrderService")
public class ReturnOrderServiceImpl extends ServiceImpl<ReturnOrderMapper, ReturnOrder> implements ReturnOrderService {

  /**
   * 退货订单简单分页查询
   * @param returnOrder 退货订单
   * @return
   */
  @Override
  public IPage<ReturnOrder> getReturnOrderPage(Page<ReturnOrder> page, ReturnOrder returnOrder){
      return baseMapper.getReturnOrderPage(page,returnOrder);
  }

}
