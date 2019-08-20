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
package com.pig4cloud.pig.accountmanage.shoppingAddress.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.accountmanage.shoppingAddress.entity.ShoppingAddress;

/**
 * 收货地址管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:50:18
 */
public interface ShoppingAddressService extends IService<ShoppingAddress> {

	/**
	 * 收货地址管理简单分页查询
	 *
	 * @param shoppingAddress 收货地址管理
	 * @return
	 */
	IPage<ShoppingAddress> getShoppingAddressPage(Page<ShoppingAddress> page, ShoppingAddress shoppingAddress);

	/**
	 * <p>Title: setDefault</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年05月13日
	 * @author 余新引
	 */
	boolean setDefault(Integer id, Integer userId);

	boolean cancelDefaultAddress(Integer userId);


}
