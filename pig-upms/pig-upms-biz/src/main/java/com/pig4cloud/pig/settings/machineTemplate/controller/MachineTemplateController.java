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
package com.pig4cloud.pig.settings.machineTemplate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.machineTemplate.dto.MachineTemplateDTO;
import com.pig4cloud.pig.settings.machineTemplate.entity.MachineTemplate;
import com.pig4cloud.pig.settings.machineTemplate.service.MachineTemplateService;
import com.pig4cloud.pig.settings.machineTemplateDetail.service.MachineTemplateDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * 补货模板
 *
 * @author zhuzubin
 * @date 2019-07-08 15:38:50
 */
@RestController
@AllArgsConstructor
@Api(value = "machinetemplate", tags = "补货模板管理")
@RequestMapping("/machinetemplate")
public class MachineTemplateController {

	private final MachineTemplateService machineTemplateService;

	private final MachineTemplateDetailService machineTemplateDetailService;

	/**
	 * 简单分页查询
	 *
	 * @param page            分页对象
	 * @param machineTemplate 补货模板
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<MachineTemplate>> getMachineTemplatePage(Page<MachineTemplate> page, MachineTemplate machineTemplate) {
		return new R<>(machineTemplateService.getMachineTemplatePage(page, machineTemplate));
	}

	/**
	 * 简单分页查询
	 *
	 * @param page            分页对象
	 * @param machineTemplate 补货模板
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/agent/page")
	public R<IPage<MachineTemplate>> getMachineTemplatePageByAgent(Page<MachineTemplate> page, MachineTemplate machineTemplate) {
		machineTemplate.setCreatorId(SecurityUtils.getUser().getId());
		return new R<>(machineTemplateService.getMachineTemplatePage(page, machineTemplate));
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R<MachineTemplate> getByd(@PathVariable("id") Integer id) {
		MachineTemplate template = machineTemplateService.getById(id);
		template.setTemplateDetails(machineTemplateDetailService.getMachineTemplateDetailByTemplate(id));
		return new R<>(template);
	}

	/**
	 * 新增记录
	 *
	 * @param machineTemplateDto
	 * @return R
	 */
	@ApiOperation(value = "新增补货模板", notes = "新增补货模板")
	@SysLog("新增补货模板")
	@PostMapping
	public R save(@RequestBody MachineTemplateDTO machineTemplateDto) {
		MachineTemplate machineTemplate = new MachineTemplate();
		if (StringUtils.isEmpty(machineTemplateDto.getName())) {
			return new R(Boolean.FALSE, "模板名称不能为空");
		}
		BeanUtils.copyProperties(machineTemplateDto, machineTemplate);
		machineTemplate.setCreatorId(SecurityUtils.getUser().getId());
		machineTemplate.setCreateTime(LocalDateTime.now());
		return new R<>(machineTemplateService.save(machineTemplate));
	}

	/**
	 * 修改记录
	 *
	 * @param machineTemplateDto
	 * @return R
	 */
	@ApiOperation(value = "修改补货模板", notes = "修改补货模板")
	@PutMapping
	public R update(@RequestBody MachineTemplateDTO machineTemplateDto) {
		if (StringUtils.isEmpty(machineTemplateDto.getName())) {
			return new R(Boolean.FALSE, "模板名称不能为空");
		}
		MachineTemplate machineTemplate = new MachineTemplate();
		BeanUtils.copyProperties(machineTemplateDto, machineTemplate);
		return new R<>(machineTemplateService.updateById(machineTemplate));
	}

	/**
	 * 设备导入补货模板
	 *
	 * @param machineTemplateDto
	 * @return R
	 */
	@ApiOperation(value = "设备导入补货模板", notes = "设备导入补货模板")
	@SysLog("新增补货模板")
	@PostMapping("/machineSetTemplate")
	public R setTemplate(@RequestBody MachineTemplateDTO machineTemplateDto) {
		if (machineTemplateDto == null) {
			return new R(Boolean.FALSE, "请求参数为空");
		}
		MachineTemplate machineTemplate = new MachineTemplate();
		BeanUtils.copyProperties(machineTemplateDto, machineTemplate);
		return new R(machineTemplateService.setMachineTemplate(machineTemplate));
	}
	/**
	 * 通过id删除一条记录
	 *
	 * @param id
	 * @return R
	 */
	@SysLog("删除补货模板")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		boolean result = machineTemplateService.removeById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("template_id", id);
		machineTemplateDetailService.removeByMap(map);
		return new R<>(machineTemplateService.removeById(id));
	}

}
