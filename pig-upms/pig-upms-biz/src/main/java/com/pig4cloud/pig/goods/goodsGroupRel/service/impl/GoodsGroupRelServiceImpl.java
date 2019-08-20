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
package com.pig4cloud.pig.goods.goodsGroupRel.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.goodsGroupRel.entity.GoodsGroupRel;
import com.pig4cloud.pig.goods.goodsGroupRel.mapper.GoodsGroupRelMapper;
import com.pig4cloud.pig.goods.goodsGroupRel.service.GoodsGroupRelService;

/**
 * 商品分组关联
 *
 * @author zhuzubin
 * @date 2019-04-05 21:24:17
 */
@Service("goodsGroupRelService")
public class GoodsGroupRelServiceImpl extends ServiceImpl<GoodsGroupRelMapper, GoodsGroupRel> implements GoodsGroupRelService {

	/**
	 * 商品分组关联简单分页查询
	 * @param goodsGroupRel 商品分组关联
	 * @return
	 */
	@Override
	public IPage<GoodsGroupRel> getGoodsGroupRelPage(Page<GoodsGroupRel> page, GoodsGroupRel goodsGroupRel) {
		return baseMapper.getGoodsGroupRelPage(page, goodsGroupRel);
	}

	@Override
	public Boolean removeByGoodsId(Integer goodsId) {
		return baseMapper.removeByGoodsId(goodsId);
	}

}
