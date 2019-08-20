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
package com.pig4cloud.pig.settings.comboManage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.settings.comboDetail.service.ComboDetailService;
import com.pig4cloud.pig.settings.comboMachine.service.ComboMachineService;
import com.pig4cloud.pig.settings.comboManage.entity.ComboManage;
import com.pig4cloud.pig.settings.comboManage.mapper.ComboManageMapper;
import com.pig4cloud.pig.settings.comboManage.service.ComboManageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 套餐管理
 *
 * @author zhuzubin
 * @date 2019-04-05 11:26:21
 */
@AllArgsConstructor
@Service("comboManageService")
public class ComboManageServiceImpl extends ServiceImpl<ComboManageMapper, ComboManage> implements ComboManageService {

	private final ComboDetailService comboDetailService;
	private final ComboMachineService comboMachineService;

	/**
	 * 套餐管理简单分页查询
	 *
	 * @param comboManage 套餐管理
	 * @return
	 */
	@Override
	public IPage<ComboManage> getComboManagePage(Page<ComboManage> page, ComboManage comboManage) {
		return baseMapper.getComboManagePage(page, comboManage);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveCombo(ComboManage combo) {
		super.save(combo);
		this.saveComboMachine(combo);
		return this.saveComboDetail(combo);
	}

	private boolean saveComboDetail(ComboManage combo) {
		/**
		 * 添加套餐商品详情
		 */
		if (combo.getDetailList() == null || combo.getDetailList().size() == 0)
			return true;
		combo.getDetailList().stream().forEach(detail -> {
			detail.setComboId(combo.getId());
		});
		return comboDetailService.saveBatch(combo.getDetailList());
	}

	private boolean saveComboMachine(ComboManage combo) {
		/**
		 * 添加套餐设备
		 */
		if (combo.getMachineList() == null || combo.getMachineList().size() == 0)
			return true;
		combo.getMachineList().stream().forEach(machine -> {
			machine.setComboId(combo.getId());
		});
		return comboMachineService.saveBatch(combo.getMachineList());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateCombo(ComboManage combo) {
		/**
		 * 删除详细信息
		 */
		this.deleteComboDetail(combo.getId());
		this.deleteComboMachine(combo.getId());
		/**
		 * 添加新的详细信息
		 */
		this.saveComboDetail(combo);
		this.saveComboMachine(combo);
		return super.updateById(combo);
	}

	private boolean deleteComboDetail(Integer comboId) {
		/**
		 * 删除套餐商品详情
		 */
		return comboDetailService.removeByComboId(comboId);
	}

	private boolean deleteComboMachine(Integer comboId) {
		/**
		 * 删除套餐商品详情
		 */
		return comboMachineService.removeByComboId(comboId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean removeComboById(Integer id) {
		this.deleteComboDetail(id);
		this.deleteComboMachine(id);
		return super.removeById(id);
	}

	@Override
	public ComboManage getComboById(Integer id) {
		return baseMapper.getComboById(id);
	}

}
