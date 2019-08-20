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
package com.pig4cloud.pig.goods.goodsGroup.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.goodsGroup.entity.GoodsGroup;
import com.pig4cloud.pig.goods.goodsGroup.mapper.GoodsGroupMapper;
import com.pig4cloud.pig.goods.goodsGroup.service.GoodsGroupService;
import com.pig4cloud.pig.goods.goodsGroupRel.entity.GoodsGroupRel;
import com.pig4cloud.pig.goods.goodsGroupRel.service.GoodsGroupRelService;

import lombok.AllArgsConstructor;

/**
 * 商品分组
 *
 * @author zhuzubin
 * @date 2019-04-05 14:10:14
 */
@AllArgsConstructor
@Service("goodsGroupService")
public class GoodsGroupServiceImpl extends ServiceImpl<GoodsGroupMapper, GoodsGroup> implements GoodsGroupService {

	private final GoodsGroupRelService goodsGroupRelService;

	/**
	 * 商品分组简单分页查询
	 * @param goodsGroup 商品分组
	 * @return
	 */
	@Override
	public IPage<GoodsGroup> getGoodsGroupPage(Page<GoodsGroup> page, GoodsGroup goodsGroup) {
		return baseMapper.getGoodsGroupPage(page, goodsGroup);
	}

	@Override
	public List<GoodsGroup> getGoodsGroupList(GoodsGroup goodsGroup) {
		return baseMapper.getGoodsGroupList(goodsGroup);
	}

	@Override
	public boolean removeGoodsGroupById(Integer id) {
		List<GoodsGroupRel> goodsList = goodsGroupRelService.list(Wrappers.<GoodsGroupRel> query().lambda().eq(GoodsGroupRel::getGoodsGroupId, id));
		if (goodsList != null && goodsList.size() > 0) {
			return Boolean.FALSE;
		}
		return super.removeById(id);
	}

}
