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
package com.pig4cloud.pig.settings.levelManage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.settings.levelManage.entity.LevelManage;
import com.pig4cloud.pig.settings.levelManage.service.LevelManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 等级设置
 *
 * @author zhuzubin
 * @date 2019-04-05 11:23:51
 */
@Api(value="levelmanage", tags="等级设置")
@RestController
@AllArgsConstructor
@RequestMapping("/levelmanage")
public class LevelManageController {

  private final  LevelManageService levelManageService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param levelManage 等级设置
   * @return
   */
  @ApiOperation(value="分页查询", notes="分页查询")
  @GetMapping("/page")
  public R<IPage<LevelManage>> getLevelManagePage(Page<LevelManage> page, LevelManage levelManage) {
    return  new R<>(levelManageService.getLevelManagePage(page,levelManage));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @ApiOperation(value="通过id查询单条记录", notes="通过id查询单条记录")
  @GetMapping("/{id}")
  public R<LevelManage> getById(@PathVariable("id") Integer id){
    return new R<>(levelManageService.getById(id));
  }

  /**
   * 新增记录
   * @param levelManage
   * @return R
   */
  @ApiOperation(value="新增等级", notes="新增等级")
  @SysLog("新增等级设置")
  @PostMapping
//  @PreAuthorize("@pms.hasPermission('levelManage_levelmanage_add')")
  public R save(@RequestBody LevelManage levelManage){
    return new R<>(levelManageService.save(levelManage));
  }

  /**
   * 修改记录
   * @param levelManage
   * @return R
   */
  @ApiOperation(value="修改记录", notes="修改记录")
  @SysLog("修改等级设置")
  @PutMapping
//  @PreAuthorize("@pms.hasPermission('levelManage_levelmanage_edit')")
  public R update(@RequestBody LevelManage levelManage){
    return new R<>(levelManageService.updateById(levelManage));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @ApiOperation(value="通过id删除一条记录", notes="通过id删除一条记录")
  @SysLog("删除等级设置")
  @DeleteMapping("/{id}")
//  @PreAuthorize("@pms.hasPermission('levelManage_levelmanage_del')")
  public R removeById(@PathVariable Integer id){
    return new R<>(levelManageService.removeById(id));
  }

}
