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
package com.pig4cloud.pig.order.returnOrder.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 退货订单
 *
 * @author yuxinyin
 * @date 2019-04-17 19:08:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_return_order")
public class ReturnOrder extends Model<ReturnOrder> {
private static final long serialVersionUID = 1L;

    /**
   * id
   */
    @TableId
    private Integer id;
    /**
   * 原订单id
   */
    private Integer orderid;
    /**
   * 退货单编号
   */
    private String code;
    /**
   * 退货原因
   */
    private String returnReason;
    /**
   * 物流公司
   */
    private String logisticsCompany;
    /**
   * 物流单号
   */
    private String logisticsCode;
    /**
   * 审核状态
   */
    private Integer status;
    /**
   * 是否退款
   */
    private Integer isRefund;
    /**
   * 买家留言
   */
    private String remark;
  
}
