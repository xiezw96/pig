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
package com.pig4cloud.pig.goods.goodsGroupRel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.goods.goodsGroupRel.entity.GoodsGroupRel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 商品分组关联
 *
 * @author zhuzubin
 * @date 2019-04-05 21:24:17
 */
public interface GoodsGroupRelMapper extends BaseMapper<GoodsGroupRel> {
	/**
	* 商品分组关联简单分页查询
	* @param goodsGroupRel 商品分组关联
	* @return
	*/
	IPage<GoodsGroupRel> getGoodsGroupRelPage(Page page, @Param("goodsGroupRel") GoodsGroupRel goodsGroupRel);

	List<GoodsGroupRel> listGroupRelByGoodsId(@Param(value="goodsId")Integer goodsId);

	Boolean removeByGoodsId(@Param("goodsId") Integer goodsId);
}
