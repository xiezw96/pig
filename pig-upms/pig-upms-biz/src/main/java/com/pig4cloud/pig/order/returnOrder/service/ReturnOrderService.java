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
package com.pig4cloud.pig.order.returnOrder.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.order.returnOrder.entity.ReturnOrder;

/**
 * 退货订单
 *
 * @author yuxinyin
 * @date 2019-04-17 19:08:26
 */
public interface ReturnOrderService extends IService<ReturnOrder> {

  /**
   * 退货订单简单分页查询
   * @param returnOrder 退货订单
   * @return
   */
  IPage<ReturnOrder> getReturnOrderPage(Page<ReturnOrder> page, ReturnOrder returnOrder);


}
