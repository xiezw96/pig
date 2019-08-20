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
package com.pig4cloud.pig.settings.comboDetail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.settings.comboDetail.entity.ComboDetail;

/**
 * 套餐明细
 *
 * @author zhuzubin
 * @date 2019-04-05 11:27:38
 */
public interface ComboDetailService extends IService<ComboDetail> {


	/**
	 * <p>Title: 根据套餐id删除套餐商品明细数据</p>
	 * <p>Description: </p>
	 * @param comboId
	 * @return
	 * @date 2019年4月13日
	 * @author zhuzubin
	 */
	boolean removeByComboId(Integer comboId);

}
