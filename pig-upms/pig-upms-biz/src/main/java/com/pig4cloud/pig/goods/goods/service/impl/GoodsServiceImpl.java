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
package com.pig4cloud.pig.goods.goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.goods.entity.Goods;
import com.pig4cloud.pig.goods.goods.mapper.GoodsMapper;
import com.pig4cloud.pig.goods.goods.service.GoodsService;
import com.pig4cloud.pig.goods.goodsGroupRel.service.GoodsGroupRelService;
import com.pig4cloud.pig.goods.goodsPic.service.GoodsPicService;
import com.pig4cloud.pig.goods.goodsSpePrice.service.GoodsSpePriceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 商品信息
 *
 * @author zhuzubin
 * @date 2019-04-05 22:36:49
 */
@Service("goodsService")
@AllArgsConstructor
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

	private final GoodsPicService goodsPicService;
	private final GoodsSpePriceService goodsSpePriceService;
	private final GoodsGroupRelService goodsGroupRelService;

	/**
	 * 商品信息简单分页查询
	 *
	 * @param goods 商品信息
	 * @return
	 */
	@Override
	public IPage<Goods> getGoodsPage(Page<Goods> page, Goods goods) {
		return baseMapper.getGoodsPage(page, goods);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveGoods(Goods goods) {
		if (goods.getPicList() != null && !goods.getPicList().isEmpty()) {
			goods.setAttId(goods.getPicList().get(0).getAttId());
		}
		boolean result = super.save(goods);
		if (result) {
			result = this.saveGoodsRef(goods);
			if (!result)
				throw new RuntimeException("保存商品信息失败");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateGoodsById(Goods goods) {
		this.deleteGoodsRef(goods.getId());
		if (goods.getPicList() != null && !goods.getPicList().isEmpty()) {
			goods.setAttId(goods.getPicList().get(0).getAttId());
		}
		boolean result = this.saveGoodsRef(goods);
		if (!result)
			throw new RuntimeException("保存商品信息失败");
		if (result) {
			result = super.updateById(goods);
			if (!result)
				throw new RuntimeException("更新商品失败");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeGoodsById(Integer id) {
		this.deleteGoodsRef(id);
		boolean result = goodsGroupRelService.removeById(id);
		if (!result)
			throw new RuntimeException("更新商品失败");
		return result;
	}

	/**
	 * <p>Title: 添加商品关联信息</p>
	 * <p>Description: </p>
	 *
	 * @param goods
	 * @date 2019年4月11日
	 * @author zhuzubin
	 */
	private boolean saveGoodsRef(final Goods goods) {
		boolean result = false;
		if (goods.getPicList() != null && !goods.getPicList().isEmpty()) {
			// 商品图片列表
			goods.getPicList().stream().forEach(goodsPic -> {
				goodsPic.setGoodsId(goods.getId());
				goodsPic.setId(null);
			});
			result = goodsPicService.saveBatch(goods.getPicList());
		}
		if (result && goods.getSepPriceList() != null && !goods.getSepPriceList().isEmpty()) {
			// 商品价格列表
			goods.getSepPriceList().forEach(goodsPic -> {
				goodsPic.setGoodsId(goods.getId());
				goodsPic.setSpePriceKey(goods.getId() + "_" + (goodsPic.getSpeId1() == null ? "" : goodsPic.getSpeId1()) + "_" + (goodsPic.getSpeId2() == null ? "" : goodsPic.getSpeId2()));
				goodsPic.setId(null);
			});
			result = goodsSpePriceService.saveBatch(goods.getSepPriceList());
		}
		if (result && goods.getGroupRelList() != null && !goods.getGroupRelList().isEmpty()) {
			// 商品分组列表
			goods.getGroupRelList().forEach(goodsGroupRel -> {
				goodsGroupRel.setGoodsId(goods.getId());
			});
			result = goodsGroupRelService.saveBatch(goods.getGroupRelList());
		}
		return result;
	}

	/**
	 * <p>Title: 删除商品关联信息</p>
	 * <p>Description: </p>
	 *
	 * @param goodsId
	 * @date 2019年4月11日
	 * @author zhuzubin
	 */
	private void deleteGoodsRef(Integer goodsId) {
		// 删除商品图片关联
		goodsPicService.removeByGoodsId(goodsId);
		// 删除商品分组关联
		goodsGroupRelService.removeByGoodsId(goodsId);
		// 删除商品规格价格
		goodsSpePriceService.removeByGoodsId(goodsId);
	}

	@Override
	public Goods getGoodsById(Integer id) {
		if (StringUtils.isEmpty(id)) return null;
		return baseMapper.getGoodsById(id);
	}

}
