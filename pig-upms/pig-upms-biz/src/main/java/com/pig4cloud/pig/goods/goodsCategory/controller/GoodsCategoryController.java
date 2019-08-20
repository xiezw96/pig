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
package com.pig4cloud.pig.goods.goodsCategory.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.goods.goodsCategory.entity.GoodsCategory;
import com.pig4cloud.pig.goods.goodsCategory.service.GoodsCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;


/**
 * 商品类目
 *
 * @author zhuzubin
 * @date 2019-04-05 19:46:55
 */
@Api(value="goodscategory", tags="商品类目管理")
@RestController
@AllArgsConstructor
@RequestMapping("/goodscategory")
public class GoodsCategoryController {

  private final  GoodsCategoryService goodsCategoryService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param goodsCategory 商品类目
   * @return
   */
  @ApiOperation(value="分页查询", notes="分页查询")
  @GetMapping("/page")
  public R<IPage<GoodsCategory>> getGoodsCategoryPage(Page<GoodsCategory> page, GoodsCategory goodsCategory) {
    return  new R<>(goodsCategoryService.getGoodsCategoryPage(page,goodsCategory));
  }

  /**
   * 获取全部类型信息
   * @param page 分页对象
   * @param goodsCategory 商品类目
   * @return
   */
  @ApiOperation(value="获取全部类型信息", notes="获取全部类型信息")
  @GetMapping("/list")
  public List<GoodsCategory> getGoodsCategoryList(GoodsCategory goodsCategory) {
    return goodsCategoryService.getGoodsCategoryList(goodsCategory);
  }
  

  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @ApiOperation(value="通过id查询单条记录", notes="通过id查询单条记录")
  @GetMapping("/{id}")
  public R<GoodsCategory> getById(@PathVariable("id") Integer id){
    return new R<>(goodsCategoryService.getById(id));
  }

  /**
   * 新增记录
   * @param goodsCategory
   * @return R
   */
  @ApiOperation(value="新增商品类目", notes="新增商品类目")
  @SysLog("新增商品类目")
  @PostMapping
//  @PreAuthorize("@pms.hasPermission('goodsCategory_goodscategory_add')")
  public R save(@RequestBody GoodsCategory goodsCategory){
	  goodsCategory.setCreateDate(LocalDateTime.now());
	  goodsCategory.setCreatorId(SecurityUtils.getUser().getId());
    return new R<>(goodsCategoryService.save(goodsCategory));
  }

  /**
   * 修改记录
   * @param goodsCategory
   * @return R
   */
  @ApiOperation(value="修改商品类目", notes="修改商品类目")
  @SysLog("修改商品类目")
  @PutMapping
//  @PreAuthorize("@pms.hasPermission('goodsCategory_goodscategory_edit')")
  public R update(@RequestBody GoodsCategory goodsCategory){
    return new R<>(goodsCategoryService.updateById(goodsCategory));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @ApiOperation(value="删除商品类目", notes="删除商品类目")
  @SysLog("删除商品类目")
  @DeleteMapping("/{id}")
//  @PreAuthorize("@pms.hasPermission('goodsCategory_goodscategory_del')")
  public boolean removeById(@PathVariable Integer id){
    return goodsCategoryService.removeCategoryById(id);
  }

}
