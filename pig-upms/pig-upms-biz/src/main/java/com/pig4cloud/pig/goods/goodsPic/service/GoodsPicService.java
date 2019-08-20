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
package com.pig4cloud.pig.goods.goodsPic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.goods.goodsPic.entity.GoodsPic;

/**
 * 商品图片
 *
 * @author zhuzubin
 * @date 2019-04-05 21:25:59
 */
public interface GoodsPicService extends IService<GoodsPic> {

  /**
   * 商品图片简单分页查询
   * @param goodsPic 商品图片
   * @return
   */
  IPage<GoodsPic> getGoodsPicPage(Page<GoodsPic> page, GoodsPic goodsPic);

  /**
   * <p>Title: 删除商品图片信息</p>
   * <p>Description: </p>
   * @param id
   * @date 2019年4月11日
   * @author zhuzubin
   */
  Boolean removeByGoodsId(Integer goodsId);


}
