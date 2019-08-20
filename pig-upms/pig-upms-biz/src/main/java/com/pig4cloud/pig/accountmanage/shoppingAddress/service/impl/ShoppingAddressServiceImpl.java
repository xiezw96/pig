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
package com.pig4cloud.pig.accountmanage.shoppingAddress.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.accountmanage.shoppingAddress.entity.ShoppingAddress;
import com.pig4cloud.pig.accountmanage.shoppingAddress.mapper.ShoppingAddressMapper;
import com.pig4cloud.pig.accountmanage.shoppingAddress.service.ShoppingAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 收货地址管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:50:18
 */
@Service("shippingAddressService")
public class ShoppingAddressServiceImpl extends ServiceImpl<ShoppingAddressMapper, ShoppingAddress> implements ShoppingAddressService {

	/**
	 * 收货地址管理简单分页查询
	 *
	 * @param shoppingAddress 收货地址管理
	 * @return
	 */
	@Override
	public IPage<ShoppingAddress> getShoppingAddressPage(Page<ShoppingAddress> page, ShoppingAddress shoppingAddress) {
		return baseMapper.getShoppingAddressPage(page, shoppingAddress);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean setDefault(Integer id, Integer userId) {
		boolean result = update(Wrappers.<ShoppingAddress>update().lambda().eq(ShoppingAddress::getCreatorId, userId).set(ShoppingAddress::getIsDefault, false));

		if (result) {
			result = update(Wrappers.<ShoppingAddress>update().lambda().eq(ShoppingAddress::getId, id).set(ShoppingAddress::getIsDefault, true));
			if (!result) {
				throw new RuntimeException("设置默认异常");
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean cancelDefaultAddress(Integer userId) {
		return update(Wrappers.<ShoppingAddress>update().lambda().eq(ShoppingAddress::getCreatorId, userId).set(ShoppingAddress::getIsDefault, false));
	}
}
