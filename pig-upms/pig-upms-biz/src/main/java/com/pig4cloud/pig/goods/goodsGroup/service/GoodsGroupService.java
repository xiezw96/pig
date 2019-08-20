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
package com.pig4cloud.pig.goods.goodsGroup.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.goods.goodsGroup.entity.GoodsGroup;

/**
 * 商品分组
 *
 * @author zhuzubin
 * @date 2019-04-05 14:10:14
 */
public interface GoodsGroupService extends IService<GoodsGroup> {

	/**
	 * 商品分组简单分页查询
	 * @param goodsGroup 商品分组
	 * @return
	 */
	IPage<GoodsGroup> getGoodsGroupPage(Page<GoodsGroup> page, GoodsGroup goodsGroup);

	List<GoodsGroup> getGoodsGroupList(GoodsGroup goodsGroup);
	/**
	 * <p>Title: 删除分组</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * @date 2019年4月12日
	 * @author zhuzubin
	 */
	boolean removeGoodsGroupById(Integer id);

}
