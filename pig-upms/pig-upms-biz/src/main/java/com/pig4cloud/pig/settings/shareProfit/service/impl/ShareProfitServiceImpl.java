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
package com.pig4cloud.pig.settings.shareProfit.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.settings.shareProfit.entity.ShareProfit;
import com.pig4cloud.pig.settings.shareProfit.mapper.ShareProfitMapper;
import com.pig4cloud.pig.settings.shareProfit.service.ShareProfitService;

/**
 * 商城分润
 *
 * @author zhuzubin
 * @date 2019-04-05 11:29:49
 */
@Service("shareProfitService")
public class ShareProfitServiceImpl extends ServiceImpl<ShareProfitMapper, ShareProfit> implements ShareProfitService {

  /**
   * 商城分润简单分页查询
   * @param shareProfit 商城分润
   * @return
   */
  @Override
  public IPage<ShareProfit> getShareProfitPage(Page<ShareProfit> page, ShareProfit shareProfit){
      return baseMapper.getShareProfitPage(page,shareProfit);
  }

}
