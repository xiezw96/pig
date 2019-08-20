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
package com.pig4cloud.pig.settings.comboSuitableObject.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.settings.comboSuitableObject.entity.ComboSuitableObject;
import com.pig4cloud.pig.settings.comboSuitableObject.mapper.ComboSuitableObjectMapper;
import com.pig4cloud.pig.settings.comboSuitableObject.service.ComboSuitableObjectService;

/**
 * 套餐适用对象
 *
 * @author zhuzubin
 * @date 2019-04-13 17:42:56
 */
@Service("comboSuitableObjectService")
public class ComboSuitableObjectServiceImpl extends ServiceImpl<ComboSuitableObjectMapper, ComboSuitableObject> implements ComboSuitableObjectService {

  /**
   * 套餐适用对象简单分页查询
   * @param comboSuitableObject 套餐适用对象
   * @return
   */
  @Override
  public IPage<ComboSuitableObject> getComboSuitableObjectPage(Page<ComboSuitableObject> page, ComboSuitableObject comboSuitableObject){
      return baseMapper.getComboSuitableObjectPage(page,comboSuitableObject);
  }

@Override
public boolean removeByComboId(Integer comboId) {
	if (StringUtils.isEmpty(comboId))
		return Boolean.FALSE;
	return baseMapper.removeByComboId(comboId);
}

}
