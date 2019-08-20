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
package com.pig4cloud.pig.goods.goodsCategory.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.goods.entity.Goods;
import com.pig4cloud.pig.goods.goods.service.GoodsService;
import com.pig4cloud.pig.goods.goodsCategory.entity.GoodsCategory;
import com.pig4cloud.pig.goods.goodsCategory.mapper.GoodsCategoryMapper;
import com.pig4cloud.pig.goods.goodsCategory.service.GoodsCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类目
 *
 * @author zhuzubin
 * @date 2019-04-05 19:46:55
 */
@AllArgsConstructor
@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {

	private final GoodsService goodsService;
	/**
	 * 商品类目简单分页查询
	 * @param goodsCategory 商品类目
	 * @return
	 */
	@Override
	public IPage<GoodsCategory> getGoodsCategoryPage(Page<GoodsCategory> page, GoodsCategory goodsCategory) {
		return baseMapper.getGoodsCategoryPage(page, goodsCategory);
	}

	@Override
	public List<GoodsCategory> getGoodsCategoryList(GoodsCategory goodsCategory) {
		return baseMapper.selectList(goodsCategory);
	}

	@Override
	public boolean removeCategoryById(Integer id) {
		List<Goods> goodsList = goodsService.list(Wrappers.<Goods>query().lambda().eq(Goods::getCategory, id));
		if (goodsList != null && goodsList.size() > 0) {
			return Boolean.FALSE;
		}
		return super.removeById(id);
	}

}
