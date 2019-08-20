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
package com.pig4cloud.pig.settings.comboManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.settings.comboManage.entity.ComboManage;
import org.apache.ibatis.annotations.Param;

/**
 * 套餐管理
 *
 * @author zhuzubin
 * @date 2019-04-05 11:26:21
 */
public interface ComboManageMapper extends BaseMapper<ComboManage> {
	/**
	* 套餐管理简单分页查询
	* @param comboManage 套餐管理
	* @return
	*/
	IPage<ComboManage> getComboManagePage(Page page, @Param("comboManage") ComboManage comboManage);

	/**
	 * <p>Title: 根据id查看套餐信息</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * @date 2019年4月13日
	 * @author zhuzubin
	 */
	ComboManage getComboById(@Param("id") Integer id);

}
