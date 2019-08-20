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
package com.pig4cloud.pig.settings.notice.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.settings.notice.entity.Notice;
import com.pig4cloud.pig.settings.notice.mapper.NoticeMapper;
import com.pig4cloud.pig.settings.notice.service.NoticeService;

/**
 * 公告信息
 *
 * @author zhuzubin
 * @date 2019-04-05 11:25:21
 */
@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

  /**
   * 公告信息简单分页查询
   * @param notice 公告信息
   * @return
   */
  @Override
  public IPage<Notice> getNoticePage(Page<Notice> page, Notice notice){
      return baseMapper.getNoticePage(page,notice);
  }

}
