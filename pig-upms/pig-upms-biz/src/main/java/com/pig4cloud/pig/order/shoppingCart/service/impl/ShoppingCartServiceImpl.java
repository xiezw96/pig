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
package com.pig4cloud.pig.order.shoppingCart.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.goodsGroupRel.service.GoodsGroupRelService;
import com.pig4cloud.pig.goods.goodsPic.service.GoodsPicService;
import com.pig4cloud.pig.goods.goodsSpePrice.service.GoodsSpePriceService;
import com.pig4cloud.pig.order.shoppingCart.entity.ShoppingCart;
import com.pig4cloud.pig.order.shoppingCart.mapper.ShoppingCartMapper;
import com.pig4cloud.pig.order.shoppingCart.service.ShoppingCartService;

import lombok.AllArgsConstructor;

/**
 * 购物车
 *
 * @author zhuzubin
 * @date 2019-04-05 23:18:23
 */
@Service("shoppingCartService")
@AllArgsConstructor
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

  /**
   * 购物车简单分页查询
   * @param shoppingCart 购物车
   * @return
   */
  @Override
  public IPage<ShoppingCart> getShoppingCartPage(Page<ShoppingCart> page, ShoppingCart shoppingCart){
      return baseMapper.getShoppingCartPage(page,shoppingCart);
  }

}