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
package com.pig4cloud.pig.goods.goodsPic.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.goodsPic.entity.GoodsPic;
import com.pig4cloud.pig.goods.goodsPic.mapper.GoodsPicMapper;
import com.pig4cloud.pig.goods.goodsPic.service.GoodsPicService;

import lombok.AllArgsConstructor;

/**
 * 商品图片
 *
 * @author zhuzubin
 * @date 2019-04-05 21:25:59
 */
@Service("goodsPicService")
@AllArgsConstructor
public class GoodsPicServiceImpl extends ServiceImpl<GoodsPicMapper, GoodsPic> implements GoodsPicService {

	/**
	 * 商品图片简单分页查询
	 * @param goodsPic 商品图片
	 * @return
	 */
	@Override
	public IPage<GoodsPic> getGoodsPicPage(Page<GoodsPic> page, GoodsPic goodsPic) {
		return baseMapper.getGoodsPicPage(page, goodsPic);
	}

	@Override
	public Boolean removeByGoodsId(Integer goodsId) {
		return baseMapper.removeByGoodsId(goodsId);
	}

}
