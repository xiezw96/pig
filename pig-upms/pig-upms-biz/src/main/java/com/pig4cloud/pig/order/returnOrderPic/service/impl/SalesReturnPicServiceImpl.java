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
package com.pig4cloud.pig.order.returnOrderPic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.order.returnOrderPic.entity.SalesReturnPic;
import com.pig4cloud.pig.order.returnOrderPic.mapper.SalesReturnPicMapper;
import com.pig4cloud.pig.order.returnOrderPic.service.SalesReturnPicService;
import org.springframework.stereotype.Service;

/**
 * 退货订单图片
 *
 * @author yuxinyin
 * @date 2019-04-17 19:07:36
 */
@Service("salesReturnPicService")
public class SalesReturnPicServiceImpl extends ServiceImpl<SalesReturnPicMapper, SalesReturnPic> implements SalesReturnPicService {

  /**
   * 退货订单图片简单分页查询
   * @param salesReturnPic 退货订单图片
   * @return
   */
  @Override
  public IPage<SalesReturnPic> getSalesReturnPicPage(Page<SalesReturnPic> page, SalesReturnPic salesReturnPic){
      return baseMapper.getSalesReturnPicPage(page,salesReturnPic);
  }

}
