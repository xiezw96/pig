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
package com.pig4cloud.pig.goods.goodsSpePrice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.goods.goodsSpePrice.entity.GoodsSpePrice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 商品价目表
 *
 * @author zhuzubin
 * @date 2019-04-05 22:01:20
 */
public interface GoodsSpePriceMapper extends BaseMapper<GoodsSpePrice> {
  /**
    * 商品价目表简单分页查询
    * @param goodsSpePrice 商品价目表
    * @return
    */
  IPage<GoodsSpePrice> getGoodsSpePricePage(Page page, @Param("goodsSpePrice") GoodsSpePrice goodsSpePrice);

  List<GoodsSpePrice> listSpePriceByGoodsId(@Param(value="goodsId") Integer goodsId);

  Boolean removeByGoodsId(@Param("goodsId") Integer goodsId);
}
