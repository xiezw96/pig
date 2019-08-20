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
package com.pig4cloud.pig.goods.goodsCategory.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.goods.goodsCategory.entity.GoodsCategory;

/**
 * 商品类目
 *
 * @author zhuzubin
 * @date 2019-04-05 19:46:55
 */
public interface GoodsCategoryService extends IService<GoodsCategory> {

  /**
   * 商品类目简单分页查询
   * @param goodsCategory 商品类目
   * @return
   */
  IPage<GoodsCategory> getGoodsCategoryPage(Page<GoodsCategory> page, GoodsCategory goodsCategory);

  List<GoodsCategory> getGoodsCategoryList(GoodsCategory goodsCategory);

  /**
   * <p>Title: 删除商品分类</p>
   * <p>Description: </p>
   * @param id
   * @return
   * @date 2019年4月12日
   * @author zhuzubin
   */
  boolean removeCategoryById(Integer id);


}
