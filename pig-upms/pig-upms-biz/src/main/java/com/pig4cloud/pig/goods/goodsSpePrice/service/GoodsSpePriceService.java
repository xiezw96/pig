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
package com.pig4cloud.pig.goods.goodsSpePrice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.goods.goodsSpePrice.entity.GoodsSpePrice;

/**
 * 商品价目表
 *
 * @author zhuzubin
 * @date 2019-04-05 22:01:20
 */
public interface GoodsSpePriceService extends IService<GoodsSpePrice> {

  /**
   * 商品价目表简单分页查询
   * @param goodsSpePrice 商品价目表
   * @return
   */
  IPage<GoodsSpePrice> getGoodsSpePricePage(Page<GoodsSpePrice> page, GoodsSpePrice goodsSpePrice);

  /**
   * <p>Title: 删除商品规格价格</p>
   * <p>Description: </p>
   * @param id
   * @date 2019年4月11日
   * @author zhuzubin
   */
  Boolean removeByGoodsId(Integer goodsId);

}
