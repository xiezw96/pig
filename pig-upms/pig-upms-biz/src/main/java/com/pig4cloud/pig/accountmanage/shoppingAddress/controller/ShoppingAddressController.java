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
package com.pig4cloud.pig.accountmanage.shoppingAddress.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.shoppingAddress.entity.ShoppingAddress;
import com.pig4cloud.pig.accountmanage.shoppingAddress.service.ShoppingAddressService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 收货地址管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:50:18
 */
@Api(value = "shippongaddress", tags = "收货地址管理")
@RestController
@AllArgsConstructor
@RequestMapping("/shoppingaddress")
public class ShoppingAddressController {

	private final ShoppingAddressService shoppingAddressService;

	/**
	 * 分页查询当前用户的收货地址
	 *
	 * @param page            分页对象
	 * @param shoppingAddress 收货地址管理
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<ShoppingAddress>> getShippingAddressPage(Page<ShoppingAddress> page, ShoppingAddress shoppingAddress) {
		shoppingAddress.setCreatorId(SecurityUtils.getUser().getId());
		return new R<>(shoppingAddressService.getShoppingAddressPage(page, shoppingAddress));
	}

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/admin/page")
	public R<IPage<ShoppingAddress>> getShippingAddressPageById(Page<ShoppingAddress> page, ShoppingAddress shoppingAddress) {
		return new R<>(shoppingAddressService.getShoppingAddressPage(page, shoppingAddress));
	}


	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<ShoppingAddress> getById(@PathVariable("id") Integer id) {
		return new R<>(shoppingAddressService.getById(id));
	}

	@ApiOperation(value = "新增记录", notes = "新增记录")
	@SysLog("新增收货地址管理")
	@PostMapping
	public R save(@RequestBody ShoppingAddress shoppingAddress) {
		shoppingAddress.setCreatorId(SecurityUtils.getUser().getId());
		return adminSave(shoppingAddress);
	}

	@ApiOperation("总部新增地址，creatorId为代理商Id，必填")
	@PostMapping("/admin")
	@PreAuthorize("@pms.hasPermission('agent_order_list')")
	public R adminSave(@RequestBody ShoppingAddress shoppingAddress) {
		if (shoppingAddress.getCreatorId() == null)
			return new R(Boolean.FALSE, "代理商id未设置");
		if (shoppingAddress.getIsDefault() > 0) {
			shoppingAddressService.cancelDefaultAddress(SecurityUtils.getUser().getId());
		}
		return new R<>(shoppingAddressService.save(shoppingAddress));
	}

	@ApiOperation(value = "修改默认地址", notes = "修改默认地址")
	@SysLog("修改默认地址")
	@PutMapping("/setDefault/{id}")
	public R setDefault(@PathVariable Integer id) {
		return new R(shoppingAddressService.setDefault(id, SecurityUtils.getUser().getId()));
	}

	/**
	 * 修改记录
	 *
	 * @param shoppingAddress
	 * @return R
	 */
	@ApiOperation(value = "修改记录", notes = "修改记录")
	@SysLog("修改收货地址管理")
	@PutMapping
//  @PreAuthorize("@pms.hasPermission('shippingAddress_shippingaddress_edit')")
	public R update(@RequestBody ShoppingAddress shoppingAddress) {
		if (shoppingAddress.getIsDefault() > 0) {
			shoppingAddressService.cancelDefaultAddress(SecurityUtils.getUser().getId());
		}
		return new R<>(shoppingAddressService.updateById(shoppingAddress));
	}

	/**
	 * 通过id删除一条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@SysLog("删除收货地址管理")
	@DeleteMapping("/{id}")
//  @PreAuthorize("@pms.hasPermission('shippingAddress_shippingaddress_del')")
	public R removeById(@PathVariable Integer id) {
		return new R<>(shoppingAddressService.removeById(id));
	}

}
