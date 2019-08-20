/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. Neither the name of the pig4cloud.com developer nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission. Author:
 * lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.goods.goodsSpePrice.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.goodsPic.service.GoodsPicService;
import com.pig4cloud.pig.goods.goodsSpePrice.entity.GoodsSpePrice;
import com.pig4cloud.pig.goods.goodsSpePrice.mapper.GoodsSpePriceMapper;
import com.pig4cloud.pig.goods.goodsSpePrice.service.GoodsSpePriceService;

import lombok.AllArgsConstructor;

/**
 * 商品价目表
 *
 * @author zhuzubin
 * @date 2019-04-05 22:01:20
 */
@Service("goodsSpePriceService")
@AllArgsConstructor
public class GoodsSpePriceServiceImpl extends ServiceImpl<GoodsSpePriceMapper, GoodsSpePrice> implements GoodsSpePriceService {

	/**
	 * 商品价目表简单分页查询
	 * @param goodsSpePrice 商品价目表
	 * @return
	 */
	@Override
	public IPage<GoodsSpePrice> getGoodsSpePricePage(Page<GoodsSpePrice> page, GoodsSpePrice goodsSpePrice) {
		return baseMapper.getGoodsSpePricePage(page, goodsSpePrice);
	}

	@Override
	public Boolean removeByGoodsId(Integer goodsId) {
		return baseMapper.removeByGoodsId(goodsId);
	}

}
