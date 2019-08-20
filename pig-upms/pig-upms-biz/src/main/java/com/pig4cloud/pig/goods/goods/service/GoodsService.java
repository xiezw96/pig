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
package com.pig4cloud.pig.goods.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.goods.goods.entity.Goods;

/**
 * 商品信息
 *
 * @author zhuzubin
 * @date 2019-04-05 22:36:49
 */
public interface GoodsService extends IService<Goods> {

	/**
	 * 商品信息简单分页查询
	 * @param goods 商品信息
	 * @return
	 */
	IPage<Goods> getGoodsPage(Page<Goods> page, Goods goods);

	boolean saveGoods(Goods goods);

	boolean updateGoodsById(Goods goods);

	boolean removeGoodsById(Integer id);
	
	/**
	 * <p>Title: 根据id获取商品信息</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * @date 2019年4月13日
	 * @author zhuzubin
	 */
	Goods getGoodsById(Integer id);

}
