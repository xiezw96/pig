drop index Index_sys_agent_userid on fx_agent;

drop index Index_sys_agent_phone on fx_agent;

drop index Index_sys_agent_name on fx_agent;

drop table if exists fx_agent;

drop table if exists fx_agent_combo;

drop table if exists fx_agent_day_report;

drop index index_agent_settlement_status on fx_agent_settlement;

drop index index_agent_settlement_req_time on fx_agent_settlement;

drop index Index_agent_settlement_code on fx_agent_settlement;

drop table if exists fx_agent_settlement;

drop index index_combo_detail_comboId on fx_combo_detail;

drop index index_combo_detail_seppriceId on fx_combo_detail;

drop index index_combo_detail_goodId on fx_combo_detail;

drop table if exists fx_combo_detail;

drop table if exists fx_combo_machine;

drop index index_combo_name on fx_combo_manage;

drop table if exists fx_combo_manage;

drop index index_combo_suitable_object_comboId on fx_combo_suitable_object;

drop table if exists fx_combo_suitable_object;

drop table if exists fx_commission;

drop table if exists fx_develop_audit;

drop index Index_develop_audit_detail_developId on fx_develop_audit_detail;

drop index Index_develop_audit_detail_agentId on fx_develop_audit_detail;

drop table if exists fx_develop_audit_detail;

drop table if exists fx_develop_award;

drop index index_goods_type on fx_goods;

drop index index_goods_status on fx_goods;

drop index index_goods_name on fx_goods;

drop index index_goods_code on fx_goods;

drop table if exists fx_goods;

drop index index_goods_category_name on fx_goods_category;

drop table if exists fx_goods_category;

drop index index_goods_group_name on fx_goods_group;

drop table if exists fx_goods_group;

drop index index_goods_group_ref_groupid on fx_goods_group_rel;

drop index index_goods_group_ref_goodid on fx_goods_group_rel;

drop table if exists fx_goods_group_rel;

drop index index_goods_pic_attid on fx_goods_pic;

drop index index_goods_pic_goodid on fx_goods_pic;

drop table if exists fx_goods_pic;

drop index index_goods_specification_name on fx_goods_spe_details;

drop table if exists fx_goods_spe_details;

drop index index_goods_spe_price_speid4 on fx_goods_spe_price;

drop index index_goods_spe_price_speid3 on fx_goods_spe_price;

drop index index_goods_spe_price_speid2 on fx_goods_spe_price;

drop index index_goods_spe_price_speid1 on fx_goods_spe_price;

drop index index_goods_spe_price_goodid on fx_goods_spe_price;

drop table if exists fx_goods_spe_price;

drop index index_goods_specification_name on fx_goods_specification;

drop table if exists fx_goods_specification;

drop index index_level_manage_name on fx_level_manage;

drop table if exists fx_level_manage;

drop table if exists fx_logistics_template;

drop index Index_machine_template_name on fx_machine_template;

drop table if exists fx_machine_template;

drop index Index_template_detail_specKey on fx_machine_template_detail;

drop index Index_template_detail_code on fx_machine_template_detail;

drop index Index_template_detail_tid on fx_machine_template_detail;

drop table if exists fx_machine_template_detail;

drop index index_message_name on fx_message;

drop table if exists fx_message;

drop index index_notice_name on fx_notice;

drop table if exists fx_notice;

drop index index_order_machine_id on fx_order;

drop index index_order_create_time on fx_order;

drop index index_order_uderid on fx_order;

drop index index_logistics_code on fx_order;

drop index Index_order_code on fx_order;

drop table if exists fx_order;

drop index index_good_id on fx_order_detail;

drop index Index_order_id on fx_order_detail;

drop table if exists fx_order_detail;

drop index index_order_voucher_id on fx_order_voucher;

drop index Index_order_voucher_orderid on fx_order_voucher;

drop table if exists fx_order_voucher;

drop index Index_performance_assessment_name on fx_performance_assessment;

drop table if exists fx_performance_assessment;

drop index index_return_order_logistics_code on fx_return_order;

drop index Index_return_order_code on fx_return_order;

drop index Index_return_order_orderid on fx_return_order;

drop table if exists fx_return_order;

drop index Index_sales_machine_activate_time on fx_sales_machine;

drop index Index_sales_machine_curr_userid on fx_sales_machine;

drop index Index_sales_machine_curr_address on fx_sales_machine;

drop index Index_sales_machine_activate_address on fx_sales_machine;

drop index Index_sales_machine_code on fx_sales_machine;

drop table if exists fx_sales_machine;

drop table if exists fx_sales_machine_aisle;

drop table if exists fx_sales_machine_aisle_goods;

drop table if exists fx_sales_machine_fault;

drop table if exists fx_sales_machine_spec;

drop index index_return_order_picId on fx_sales_return_pic;

drop index Index_return_order_orderid on fx_sales_return_pic;

drop table if exists fx_sales_return_pic;

drop index Index_settlement_detail_id on fx_settlement_detail;

drop index Index_settlement_detail_orderid on fx_settlement_detail;

drop table if exists fx_settlement_detail;

drop table if exists fx_share_profit;

drop index Index_shopping_address_name on fx_shopping_address;

drop table if exists fx_shopping_address;

drop index index_shopping_goodid on fx_shopping_cart;

drop index Index_shopping_userid on fx_shopping_cart;

drop table if exists fx_shopping_cart;

drop index index_user_wx_openid on fx_user;

drop index index_user_phone on fx_user;

drop index index_user_nickname on fx_user;

drop table if exists fx_user;

drop index USER_LOG_IDX_2 on fx_user_log;

drop index USER_LOG_IDX_1 on fx_user_log;

drop table if exists fx_user_log;

drop index fx_voucher_name on fx_voucher;

drop table if exists fx_voucher;

drop index fx_voucher_voucher_id on fx_voucher_grant_record;

drop index fx_voucher_user_id on fx_voucher_grant_record;

drop table if exists fx_voucher_grant_record;

drop index index_withdrawal_apply_req_time on fx_withdrawal_apply;

drop index Index_withdrawal_apply_userid on fx_withdrawal_apply;

drop table if exists fx_withdrawal_apply;

/*==============================================================*/
/* Table: fx_agent                                              */
/*==============================================================*/
create table fx_agent
(
   `agent_id` int(11) not null default 1 comment '主键ID',
   `name` varchar(64) comment '姓名',
   `level` varchar(250) comment '等级',
   `photo` varchar(200) comment '头像',
   `sex` int(1) comment '性别 0 男 1 女 2 保密',
   `birthday` date comment '生日',
   `phone` varchar(20) comment '手机号',
   `new_phone` varchar(20) comment '新手机号',
   `profit` decimal(5,2) default 0 comment '分润比例',
   `child_profit` decimal(5,2) default 0 comment '下级分润比例',
   `privince` varchar(100) comment '省',
   `city` varchar(100) comment '市',
   `area` varchar(100) default NULL comment '区',
   `company` varchar(500) comment '公司名称',
   `address` varchar(200) default NULL comment '地址',
   `wechat_id` varchar(100) default NULL comment '微信号',
   `new_wechat_id` varchar(100) default NULL comment '微信号',
   `id_card` varchar(20) comment '身份证号',
   `referrer_id` int(11) comment '推荐人',
   `referrer_ids` varchar(500) comment '推荐人关系',
   `identity_card_front` varchar(200) comment '身份证正面',
   `identity_card_verso` varchar(200) comment '身份证反面',
   `new_identity_card_front` varchar(200) comment '新身份证正面',
   `new_identity_card_verso` varchar(200) comment '新身份证反面',
   `bank_account_name` varchar(200) comment '银行开户名',
   `bank_card_front` varchar(200) comment '银行卡正面',
   `bank_card_verso` varchar(200) comment '银行卡反面',
   `new_bank_account_name` varchar(200) comment '新银行开户名',
   `new_bank_card_front` varchar(200) comment '新银行卡正面',
   `new_bank_card_verso` varchar(200) comment '新银行卡反面',
   `transaction_password` varchar(255) comment '交易密码',
   `withdrawal_price` decimal(9,2) comment '提现金额',
   `active_status` int(1) comment '激活状态 0 未激活 1 已激活',
   `active_time` datetime comment '激活时间',
   `referral_code` varchar(50) comment '推荐码',
   `audit_remark` varchar(500) comment '审核备注',
   `is_settlement` int(1) comment '0:未结算 1:已结算',
   `audit_status` int(1) comment '0:待审核 1:通过 1:拒绝',
   `audit_req_time` datetime comment '审核申请时间',
   `auditor_id` int(11) comment '审核人员',
   `audit_time` datetime comment '审核日期',
   `update_audit_status` int(1) comment '修改审核状态',
   `update_audit_req_time` datetime comment '修改审核申请日期',
   `update_auditor_id` int(11) comment '修改审核人员',
   `update_audit_time` datetime comment '修改审核日期',
   `update_audit_remark` varchar(500) comment '修改审核备注',
   primary key (agent_id)
)
ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表' comment '代理商用户表';

/*==============================================================*/
/* Index: Index_sys_agent_name                                  */
/*==============================================================*/
create index Index_sys_agent_name on fx_agent
(
   name
);

/*==============================================================*/
/* Index: Index_sys_agent_phone                                 */
/*==============================================================*/
create index Index_sys_agent_phone on fx_agent
(
   phone
);

/*==============================================================*/
/* Index: Index_sys_agent_userid                                */
/*==============================================================*/
/*create index Index_sys_agent_userid on fx_agent
/*(

/*);

/*==============================================================*/
/* Table: fx_agent_combo                                        */
/*==============================================================*/
create table fx_agent_combo
(
   `id` int(11) not null auto_increment comment 'id',
   `order_id` int(11) comment '订单id',
   `agent_id` int(11) not null comment '代理商id',
   `combo_id` int(11) comment '套餐id',
   `combo_type` int(2) comment '套餐类型',
   `total_money` decimal(9,2) comment '金额',
   `create_date` datetime comment '操作时间',
   `pay_status` int(1) comment '支付状态 0 未支付 1 已支付',
   `pay_time` datetime comment '支付时间',
   `trade_no` varchar(150) comment '交易码',
   `address_id` int(11) comment '收货地址',
   `message` varchar(1000) comment '买家留言',
   primary key (id)
)
 comment '代理商激活';

/*==============================================================*/
/* Table: fx_agent_day_report                                   */
/*==============================================================*/
create table fx_agent_day_report
(
   `id` int(11) not null auto_increment comment 'id',
   `agent_id` int(11) not null comment '代理商id',
   `new_user_count` int(11) comment '套餐id',
   `total` decimal(9,2) comment '流水',
   `online_total` decimal(9,2) comment '线上流水',
   `offline_total` decimal(9,2) comment '线下流水',
   `develop_count` int(5) comment '发展数',
   `withdrawal_apply_total` decimal(9,2) comment '已提现金额',
   `withdrawal_price` decimal(9,2) comment '支付时间',
   `create_date` datetime comment '创建时间',
   primary key (id)
)
 comment '代理商日报表';

/*==============================================================*/
/* Table: fx_agent_settlement                                   */
/*==============================================================*/
create table fx_agent_settlement
(
   `id` int(11) not null auto_increment comment 'id',
   `code` varchar(50) comment '结算单号',
   `total` decimal(9,2) comment '结算到账金额',
   `original_total` decimal(9,2) not null comment '结算金额',
   `commission` decimal(9,2) comment '通道费',
   `user_id` int(11) comment '申请代理商',
   `req_time` datetime comment '申请结算时间',
   `auditor_id` int(11) comment '审核人',
   `audit_time` datetime comment '审核时间',
   `status` int(1) comment '状态',
   `remark` varchar(500) comment '备注',
   primary key (id)
)
 comment '结算申请表';

/*==============================================================*/
/* Index: Index_agent_settlement_code                           */
/*==============================================================*/
create index Index_agent_settlement_code on fx_agent_settlement
(
   code
);

/*==============================================================*/
/* Index: index_agent_settlement_req_time                       */
/*==============================================================*/
create index index_agent_settlement_req_time on fx_agent_settlement
(
   req_time
);

/*==============================================================*/
/* Index: index_agent_settlement_status                         */
/*==============================================================*/
create index index_agent_settlement_status on fx_agent_settlement
(
   status
);

/*==============================================================*/
/* Table: fx_combo_detail                                       */
/*==============================================================*/
create table fx_combo_detail
(
   `id` int(11) not null auto_increment comment 'id',
   `combo_id` int(11) not null comment '套餐id',
   `goods_id` int(11) comment '商品id',
   `spe_price_id` int(11) comment '价目id',
   `spe_price_key` varchar(256) comment '价目key',
   `good_num` int(11) comment '数量',
   primary key (id)
)
 comment '套餐明细';

/*==============================================================*/
/* Index: index_combo_detail_goodId                             */
/*==============================================================*/
create index index_combo_detail_goodId on fx_combo_detail
(
   goods_id
);

/*==============================================================*/
/* Index: index_combo_detail_seppriceId                         */
/*==============================================================*/
create index index_combo_detail_seppriceId on fx_combo_detail
(
   spe_price_id
);

/*==============================================================*/
/* Index: index_combo_detail_comboId                            */
/*==============================================================*/
create index index_combo_detail_comboId on fx_combo_detail
(
   combo_id
);

/*==============================================================*/
/* Table: fx_combo_machine                                      */
/*==============================================================*/
create table fx_combo_machine
(
   `id` int(11) not null auto_increment comment 'id',
   `combo_id` int(11) not null comment '套餐id',
   `machine_spec_id` int(11) comment '柜子型号',
   `num` int(11) comment '数量',
   primary key (id)
)
 comment '套餐柜子明细';

/*==============================================================*/
/* Table: fx_combo_manage                                       */
/*==============================================================*/
create table fx_combo_manage
(
   `id` int(11) not null auto_increment comment 'id',
   `name` VARCHAR(200) not null comment '名称',
   `type` int(2) comment '类型',
   `eq_num` int(11) comment '柜子数量',
   `good_price` decimal(9,2) comment '商品金额',
   `voucher_id` int(11) comment '代金券id',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   primary key (id)
)
 comment '套餐管理';

/*==============================================================*/
/* Index: index_combo_name                                      */
/*==============================================================*/
create index index_combo_name on fx_combo_manage
(
   name
);

/*==============================================================*/
/* Table: fx_combo_suitable_object                              */
/*==============================================================*/
create table fx_combo_suitable_object
(
   `id` int(11) not null auto_increment comment 'id',
   `combo_id` int(11) not null comment '套餐id',
   `object_type` int(11) comment '适用对象类型',
   `object_name` int(11) comment '适用对象名称',
   primary key (id)
)
 comment '套餐适用对象';

/*==============================================================*/
/* Index: index_combo_suitable_object_comboId                   */
/*==============================================================*/
create index index_combo_suitable_object_comboId on fx_combo_suitable_object
(
   combo_id
);

/*==============================================================*/
/* Table: fx_commission                                         */
/*==============================================================*/
create table fx_commission
(
   `id` int(11) not null auto_increment comment 'id',
   `commission` decimal(8,5) comment '通道费',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   `modifier_id` int(11) comment '修改人',
   `update_time` datetime comment '修改时间',
   primary key (id)
)
 comment '通道费设置';

/*==============================================================*/
/* Table: fx_develop_audit                                      */
/*==============================================================*/
create table fx_develop_audit
(
   `id` int(11) not null auto_increment comment 'id',
   `agent_id` int(11) not null comment '代理商id',
   `agent_name` varchar(50) comment '代理商姓名',
   `develop_num` int(9) comment '发展数量',
   `total_money` decimal(9,2) comment '奖励到账金额',
   `original_total_money` decimal(9,2) not null comment '奖励金额',
   `commission` decimal(9,2) comment '通道费',
   `audit_status` int(1) comment '0:待审核 1:通过 1:拒绝',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   `remark` varchar(500) comment '备注',
   `req_time` datetime,
   primary key (id)
)
 comment '发展奖励审核';

/*==============================================================*/
/* Table: fx_develop_audit_detail                               */
/*==============================================================*/
create table fx_develop_audit_detail
(
   `id` int(11) not null auto_increment comment 'id',
   `agent_id` int(11) comment '代理商id',
   `develop_id` int(11) comment '发展审核id',
   `award_money` decimal(9,2) comment '奖励金额',
   primary key (id)
)
 comment '发展审核明细';

/*==============================================================*/
/* Index: Index_develop_audit_detail_agentId                    */
/*==============================================================*/
create index Index_develop_audit_detail_agentId on fx_develop_audit_detail
(
   agent_id
);

/*==============================================================*/
/* Index: Index_develop_audit_detail_developId                  */
/*==============================================================*/
create index Index_develop_audit_detail_developId on fx_develop_audit_detail
(
   develop_id
);

/*==============================================================*/
/* Table: fx_develop_award                                      */
/*==============================================================*/
create table fx_develop_award
(
   `id` int(11) not null auto_increment comment 'id',
   `award_money` decimal(9,2) comment '奖励金额/个',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   primary key (id)
)
 comment '发展奖励设置';

/*==============================================================*/
/* Table: fx_goods                                              */
/*==============================================================*/
create table fx_goods
(
   `id` int(11) not null auto_increment comment 'id',
   `category` int(11) comment '商品类目',
   `code` VARCHAR(100) comment '商品编码',
   `name` VARCHAR(200) not null comment '商品名称',
   `title` VARCHAR(200) comment '分享标题',
   `selling_points` VARCHAR(2000) comment '商品卖点',
   `status` int(1) not null comment '0:待上架 1:已上架  2:已下架',
   `att_id` varchar(200) comment '商品主图',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   `modifier_id` int(11) comment '修改人',
   `update_time` datetime comment '修改时间',
   `is_share_profit` int(1) comment '是否参与分润',
   `logistics_type` int(1) default 0 comment '物流类型 0 包邮 1 按物流模板',
   primary key (id)
)
 comment '商品表';

/*==============================================================*/
/* Index: index_goods_code                                      */
/*==============================================================*/
create index index_goods_code on fx_goods
(
   code
);

/*==============================================================*/
/* Index: index_goods_name                                      */
/*==============================================================*/
create index index_goods_name on fx_goods
(
   name
);

/*==============================================================*/
/* Index: index_goods_status                                    */
/*==============================================================*/
create index index_goods_status on fx_goods
(
   status
);

/*==============================================================*/
/* Index: index_goods_type                                      */
/*==============================================================*/
create index index_goods_type on fx_goods
(
   category
);

/*==============================================================*/
/* Table: fx_goods_category                                     */
/*==============================================================*/
create table fx_goods_category
(
   `id` int(11) not null auto_increment comment 'id',
   `name` VARCHAR(200) not null comment '名称',
   `sort` int(6) comment '排序值',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   primary key (id)
)
 comment '商品类目表';

/*==============================================================*/
/* Index: index_goods_category_name                             */
/*==============================================================*/
create index index_goods_category_name on fx_goods_category
(
   name
);

/*==============================================================*/
/* Table: fx_goods_group                                        */
/*==============================================================*/
create table fx_goods_group
(
   `id` int(11) not null auto_increment comment 'id',
   `name` VARCHAR(200) not null comment '分组名称',
   `sort` int(6) comment '排序值',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   primary key (id)
)
 comment '商品分组表';

/*==============================================================*/
/* Index: index_goods_group_name                                */
/*==============================================================*/
create index index_goods_group_name on fx_goods_group
(
   name
);

/*==============================================================*/
/* Table: fx_goods_group_rel                                    */
/*==============================================================*/
create table fx_goods_group_rel
(
   `id` int(11) not null auto_increment comment 'id',
   `goods_id` int(11) not null comment '商品ID',
   `goods_group_id` int(11) not null comment '分组ID',
   primary key (id)
)
 comment '商品分组关联表';

/*==============================================================*/
/* Index: index_goods_group_ref_goodid                          */
/*==============================================================*/
create index index_goods_group_ref_goodid on fx_goods_group_rel
(
   goods_id
);

/*==============================================================*/
/* Index: index_goods_group_ref_groupid                         */
/*==============================================================*/
create index index_goods_group_ref_groupid on fx_goods_group_rel
(
   goods_group_id
);

/*==============================================================*/
/* Table: fx_goods_pic                                          */
/*==============================================================*/
create table fx_goods_pic
(
   `id` int(11) not null auto_increment comment 'id',
   `goods_id` int(11) not null comment '商品id',
   `att_id` varchar(200) not null comment '图片id',
   `type` int(1) comment '0:主图 1:缩略图 2:商品详情图片',
   primary key (id)
)
 comment '商品图片表';

/*==============================================================*/
/* Index: index_goods_pic_goodid                                */
/*==============================================================*/
create index index_goods_pic_goodid on fx_goods_pic
(
   goods_id
);

/*==============================================================*/
/* Index: index_goods_pic_attid                                 */
/*==============================================================*/
create index index_goods_pic_attid on fx_goods_pic
(
   att_id
);

/*==============================================================*/
/* Table: fx_goods_spe_details                                  */
/*==============================================================*/
create table fx_goods_spe_details
(
   `id` int(11) not null comment 'id',
   `spe_id` int(11) not null comment '规格id',
   `value` varchar(50) comment '规格值',
   primary key (id)
)
 comment '商品规格细项表';

/*==============================================================*/
/* Index: index_goods_specification_name                        */
/*==============================================================*/
create index index_goods_specification_name on fx_goods_spe_details
(
   spe_id
);

/*==============================================================*/
/* Table: fx_goods_spe_price                                    */
/*==============================================================*/
create table fx_goods_spe_price
(
   `id` int(11) not null auto_increment comment 'id',
   `goods_id` int(11) comment '商品id',
   `spe_price_key` varchar(256) comment '价目key',
   `spe_name1` varchar(200) comment '规格名称1',
   `spe_name2` varchar(200) comment '规格名称2',
   `spe_name3` varchar(200) comment '规格名称3',
   `spe_name4` varchar(200) comment '规格名称4',
   `spe_id1` varchar(200) not null comment '规格1',
   `spe_id2` varchar(200) comment '规格2',
   `spe_id3` varchar(200) comment '规格3',
   `spe_id4` varchar(200) comment '规格4',
   `sale_price` decimal(9,2) comment '零售价',
   `trade_price` decimal(9,2) comment '统批价',
   `inventory_num` int(11) comment '库存',
   `sales_volume` int(11) comment '销量',
   `weight` decimal(9,2) comment '重量(kg)',
   primary key (id)
)
 comment '商品规格价格表';

/*==============================================================*/
/* Index: index_goods_spe_price_goodid                          */
/*==============================================================*/
create index index_goods_spe_price_goodid on fx_goods_spe_price
(
   goods_id
);

/*==============================================================*/
/* Index: index_goods_spe_price_speid1                          */
/*==============================================================*/
create index index_goods_spe_price_speid1 on fx_goods_spe_price
(
   spe_id1
);

/*==============================================================*/
/* Index: index_goods_spe_price_speid2                          */
/*==============================================================*/
create index index_goods_spe_price_speid2 on fx_goods_spe_price
(
   spe_id2
);

/*==============================================================*/
/* Index: index_goods_spe_price_speid3                          */
/*==============================================================*/
create index index_goods_spe_price_speid3 on fx_goods_spe_price
(
   spe_id3
);

/*==============================================================*/
/* Index: index_goods_spe_price_speid4                          */
/*==============================================================*/
create index index_goods_spe_price_speid4 on fx_goods_spe_price
(
   spe_id4
);

/*==============================================================*/
/* Table: fx_goods_specification                                */
/*==============================================================*/
create table fx_goods_specification
(
   `id` int(11) not null comment 'id',
   `good_id` int(11) comment '商品id',
   `name` VARCHAR(200) not null comment '名称',
   `sort` int(6) comment '排序值',
   primary key (id)
)
 comment '商品规格表';

/*==============================================================*/
/* Index: index_goods_specification_name                        */
/*==============================================================*/
create index index_goods_specification_name on fx_goods_specification
(
   name
);

/*==============================================================*/
/* Table: fx_level_manage                                       */
/*==============================================================*/
create table fx_level_manage
(
   `id` int(11) not null auto_increment comment 'id',
   `name` VARCHAR(200) not null comment '名称',
   `weight` int(6) comment '权重值',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   primary key (id)
)
 comment '等级管理';

/*==============================================================*/
/* Index: index_level_manage_name                               */
/*==============================================================*/
create index index_level_manage_name on fx_level_manage
(
   name
);

/*==============================================================*/
/* Table: fx_logistics_template                                 */
/*==============================================================*/
create table fx_logistics_template
(
   `id` int(11) not null auto_increment comment 'id',
   `starting_weight` decimal(9,2) comment '首重',
   `starting_price` decimal(9,2) comment '起步价(1kg之内)',
   `increment` decimal(9,2) comment '续重 每增加1kg，增加的金额',
   `faraway_starting_price` decimal(9,2) comment '偏远地区起步价',
   `faraway_increment` decimal(9,2) comment '偏远地区续重',
   `faraway_provinces` varchar(500) comment '偏远省份，逗号隔开',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   `modifier_id` int(11) comment '修改人',
   `update_time` datetime comment '修改时间',
   primary key (id)
)
 comment '物流模板';

/*==============================================================*/
/* Table: fx_machine_template                                   */
/*==============================================================*/
create table fx_machine_template
(
   `id` int(11) not null auto_increment comment 'id',
   `name` varchar(50) not null comment '模板名称',
   `type` int(1) default 0 comment '0 私有 1 公共',
   `machine_type` int(11) default NULL comment '设备类型',
   `status` int(1) default 0 comment '0启用 1停用',
   `creator_id` int(11) comment '创建人',
   `create_time` datetime comment '创建时间',
   primary key (id)
)
 comment '机器模板表';

/*==============================================================*/
/* Index: Index_machine_template_name                           */
/*==============================================================*/
create index Index_machine_template_name on fx_machine_template
(
   name
);

/*==============================================================*/
/* Table: fx_machine_template_detail                            */
/*==============================================================*/
create table fx_machine_template_detail
(
   `id` int(11) not null auto_increment comment 'id',
   `template_id` int(11) not null comment '模板id',
   `aisle_code` varchar(200) default NULL comment '货道编号',
   `goods_id` int(11) default NULL comment '商品ID',
   `spe_price_id` int(11) comment '商品规格ID',
   `spec_price_key` varchar(256) comment '商品规格key',
   `num` int(3) default 1 comment '商品数量',
   primary key (id)
)
 comment '销售机器模板明细表';

/*==============================================================*/
/* Index: Index_template_detail_tid                             */
/*==============================================================*/
create unique index Index_template_detail_tid on fx_machine_template_detail
(
   template_id
);

/*==============================================================*/
/* Index: Index_template_detail_code                            */
/*==============================================================*/
create index Index_template_detail_code on fx_machine_template_detail
(
   goods_id
);

/*==============================================================*/
/* Index: Index_template_detail_specKey                         */
/*==============================================================*/
create index Index_template_detail_specKey on fx_machine_template_detail
(
   spec_price_key
);

/*==============================================================*/
/* Table: fx_message                                            */
/*==============================================================*/
create table fx_message
(
   `id` int(11) not null auto_increment comment 'id',
   `user_id` int(11) comment '用户id',
   `title` VARCHAR(200) not null comment '标题',
   `read_status` int(1) comment '阅读状态 0 未读 1 已读',
   `content` text comment '公告内容',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   primary key (id)
)
 comment '消息';

/*==============================================================*/
/* Index: index_message_name                                    */
/*==============================================================*/
create index index_message_name on fx_message
(
   title
);

/*==============================================================*/
/* Table: fx_notice                                             */
/*==============================================================*/
create table fx_notice
(
   `id` int(11) not null auto_increment comment 'id',
   `title` VARCHAR(200) not null comment '标题',
   `status` int(1) comment '状态',
   `period` int(1) comment '弹窗周期',
   `start_time` datetime comment '弹窗开始时间',
   `end_time` datetime comment '弹窗结束时间',
   `content` text comment '公告内容',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   primary key (id)
)
 comment '公告表';

/*==============================================================*/
/* Index: index_notice_name                                     */
/*==============================================================*/
create index index_notice_name on fx_notice
(
   title
);

/*==============================================================*/
/* Table: fx_order                                              */
/*==============================================================*/
create table fx_order
(
   `id` int(11) not null auto_increment comment 'id',
   `code` varchar(64) not null comment '订单编号',
   `type` int(1) comment '订单类型',
   `total_money` decimal(9,2) comment '订单金额',
   `machine_id` int(11) comment '柜子id',
   `aisle_id` int(11) comment '货道id',
   `creator_id` int(11) comment '下单用户',
   `shopping_address` int(11) comment '收货地址',
   `create_time` datetime comment '下单时间',
   `send_time` datetime comment '发货时间',
   `logistics_company` varchar(255) comment '物流公司',
   `logistics_code` varchar(50) comment '物流单号',
   `logistics_operator_id` int(11) comment '物流操作人',
   `status` int(2) comment '0 未付款  1 已付款未发货 2 已付款已发货 3 已退货未退款 4 已退货已退款',
   `pay_status` int(1) comment '支付状态',
   `pay_time` datetime comment '支付时间',
   `remark` varchar(1000) comment '买家留言',
   `is_settlement` int(1) default 0 comment '是否结算',
   `name` varchar(64) comment '姓名',
   `phone` varchar(20) comment '手机号',
   `privince` varchar(100) comment '省',
   `city` varchar(100) comment '市',
   `area` varchar(100) default NULL comment '区',
   `address` varchar(200) comment '地址',
   `operator_id` int(11) comment '创建人',
   `operation_time` datetime comment '创建时间',
   primary key (id)
)
 comment '订单表';

/*==============================================================*/
/* Index: Index_order_code                                      */
/*==============================================================*/
create index Index_order_code on fx_order
(
   code
);

/*==============================================================*/
/* Index: index_logistics_code                                  */
/*==============================================================*/
create index index_logistics_code on fx_order
(
   logistics_code
);

/*==============================================================*/
/* Index: index_order_uderid                                    */
/*==============================================================*/
create index index_order_uderid on fx_order
(
   creator_id
);

/*==============================================================*/
/* Index: index_order_create_time                               */
/*==============================================================*/
create index index_order_create_time on fx_order
(
   create_time
);

/*==============================================================*/
/* Index: index_order_machine_id                                */
/*==============================================================*/
create index index_order_machine_id on fx_order
(
   machine_id
);

/*==============================================================*/
/* Table: fx_order_detail                                       */
/*==============================================================*/
create table fx_order_detail
(
   `id` int(11) not null auto_increment comment 'id',
   `order_id` int(11) not null comment '订单id',
   `goods_id` int(11) comment '商品id',
   `spe_price_id` int(11) comment '价目id',
   `spe_price_key` varchar(256) comment '价目key',
   `goods_name` varchar(200) comment '商品名称',
   `goods_att_id` varchar(200) comment '商品图片',
   `goods_spe1` varchar(200) comment '商品规格1',
   `goods_spe2` varchar(200) comment '商品规格2',
   `sale_price` decimal(9,2) comment '零售价',
   `trade_price` decimal(9,2) comment '统批价',
   `logistics_price` decimal(9,2) comment '物流价格',
   `voucher_id` int(11) comment '优惠券',
   `pay_num` int(11) comment '购买数量',
   `pay_price` decimal(9,2) comment '实付金额',
   primary key (id)
)
 comment '订单明细表';

/*==============================================================*/
/* Index: Index_order_id                                        */
/*==============================================================*/
create index Index_order_id on fx_order_detail
(
   order_id
);

/*==============================================================*/
/* Index: index_good_id                                         */
/*==============================================================*/
create index index_good_id on fx_order_detail
(
   voucher_id
);

/*==============================================================*/
/* Table: fx_order_voucher                                      */
/*==============================================================*/
create table fx_order_voucher
(
   `id` int(11) not null auto_increment comment 'id',
   `order_id` varchar(64) not null comment '订单id',
   `voucher_id` int(1) comment '代金券id',
   primary key (id)
)
 comment '订单代金券表';

/*==============================================================*/
/* Index: Index_order_voucher_orderid                           */
/*==============================================================*/
create index Index_order_voucher_orderid on fx_order_voucher
(
   order_id
);

/*==============================================================*/
/* Index: index_order_voucher_id                                */
/*==============================================================*/
create index index_order_voucher_id on fx_order_voucher
(
   voucher_id
);

/*==============================================================*/
/* Table: fx_performance_assessment                             */
/*==============================================================*/
create table fx_performance_assessment
(
   `id` int(11) not null auto_increment comment 'id',
   `name` VARCHAR(200) not null comment '名称',
   `degrad_moon_journal_account` decimal(9,2) comment '降级月流水',
   `upgrade_moon_journal_account` decimal(9,2) comment '升级月流水',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   primary key (id)
)
 comment '绩效考核';

/*==============================================================*/
/* Index: Index_performance_assessment_name                     */
/*==============================================================*/
create index Index_performance_assessment_name on fx_performance_assessment
(
   name
);

/*==============================================================*/
/* Table: fx_return_order                                       */
/*==============================================================*/
create table fx_return_order
(
   `id` int(11) not null auto_increment comment 'id',
   `orderid` int(11) not null comment '原订单id',
   `code` varchar(64) comment '退货单编号',
   `return_reason` varchar(1000) comment '退货原因',
   `logistics_company` varchar(64) comment '物流公司',
   `logistics_code` varchar(64) comment '物流单号',
   `status` int(1) comment '审核状态',
   `is_refund` int(1) comment '是否退款',
   `remark` varchar(1000) comment '买家留言',
   primary key (id)
)
 comment '退货订单';

/*==============================================================*/
/* Index: Index_return_order_orderid                            */
/*==============================================================*/
create index Index_return_order_orderid on fx_return_order
(
   orderid
);

/*==============================================================*/
/* Index: Index_return_order_code                               */
/*==============================================================*/
create index Index_return_order_code on fx_return_order
(
   code
);

/*==============================================================*/
/* Index: index_return_order_logistics_code                     */
/*==============================================================*/
create index index_return_order_logistics_code on fx_return_order
(
   logistics_code
);

/*==============================================================*/
/* Table: fx_sales_machine                                      */
/*==============================================================*/
create table fx_sales_machine
(
   `id` int(11) not null auto_increment comment 'id',
   `name` varchar(200) not null comment '机器名称',
   `spec_id` int(11) comment '设备类型id',
   `code` varchar(200) comment '编码',
   `status` int(1) default 0 comment '状态 0 待激活 1 正常  2 故障 3补货  4 离线 5回收',
   `curr_addreaa` varchar(200) comment '当前地址',
   `activate_address` varchar(200) comment '激活地址',
   `activate_time` datetime comment '激活日期',
   `belongs_user` int(11) comment '所属人',
   `belongs_type` int(1) comment '使用权/所有权',
   `creator_id` int(11) comment '操作人',
   `create_time` datetime comment '创建时间',
   `referral_code` varchar(50) comment '推广码',
   primary key (id)
)
 comment '销售机器';

/*==============================================================*/
/* Index: Index_sales_machine_code                              */
/*==============================================================*/
create index Index_sales_machine_code on fx_sales_machine
(
   code
);

/*==============================================================*/
/* Index: Index_sales_machine_activate_address                  */
/*==============================================================*/
create index Index_sales_machine_activate_address on fx_sales_machine
(
   activate_address
);

/*==============================================================*/
/* Index: Index_sales_machine_curr_address                      */
/*==============================================================*/
create index Index_sales_machine_curr_address on fx_sales_machine
(
   curr_addreaa
);

/*==============================================================*/
/* Index: Index_sales_machine_curr_userid                       */
/*==============================================================*/
create index Index_sales_machine_curr_userid on fx_sales_machine
(
   belongs_user
);

/*==============================================================*/
/* Index: Index_sales_machine_activate_time                     */
/*==============================================================*/
create index Index_sales_machine_activate_time on fx_sales_machine
(
   activate_time
);

/*==============================================================*/
/* Table: fx_sales_machine_aisle                                */
/*==============================================================*/
create table fx_sales_machine_aisle
(
   `id` int(11) not null auto_increment comment 'id',
   `machine_id` int(11) not null comment '销售机器id',
   `code` varchar(200) comment '编码',
   `status` int(1) default 1 comment '状态 1 正常 2 故障 3 补货',
   primary key (id)
)
 comment '销售机器货道';

/*==============================================================*/
/* Table: fx_sales_machine_aisle_goods                          */
/*==============================================================*/
create table fx_sales_machine_aisle_goods
(
   `id` int(11) not null auto_increment comment 'id',
   `goods_id` int(11) not null comment '商品id',
   `spe_price_id` int(11) comment '商品价格id',
   `spe_price_key` varchar(256) comment '价目key',
   `machine_id` int(11) not null comment '销售机器id',
   `aisle_id` int(11) not null comment '货道id',
   `num` int(2) comment '数量',
   primary key (id)
)
 comment '销售机器货道商品表';

/*==============================================================*/
/* Table: fx_sales_machine_fault                                */
/*==============================================================*/
create table fx_sales_machine_fault
(
   `id` int(11) not null auto_increment comment 'id',
   `machine_id` int(11) not null comment '销售机器id',
   `fault_type` int(1) default 0 comment '故障类型 0 整机故障 1 货道故障 2 其他故障',
   `aisle_id` int(11) not null comment '货道id',
   `remark` varchar(1000) comment '故障描述',
   `agent_id` int(11) comment '代理商id',
   `creator_id` int(11) comment '创建人',
   `create_time` datetime comment '申请结算时间',
   `operator_id` int(11) comment '处理人',
   `operate_time` datetime comment '处理时间',
   `status` int(1) default 0 comment '状态 0 待处理 1 成功 2 失败',
   `deal_remark` varchar(1000) comment '处理描述',
   `pics` varchar(1000) comment '图片信息，多个逗号隔开',
   primary key (id)
)
 comment '销售机器故障表';

/*==============================================================*/
/* Table: fx_sales_machine_spec                                 */
/*==============================================================*/
create table fx_sales_machine_spec
(
   `id` int(11) not null auto_increment comment 'id',
   `provider` varchar(200) comment '厂家',
   `spec` varchar(200) not null comment '销售机器id',
   `trade_price` decimal(9,2) comment '统批价',
   `aisle_count` int(3) comment '货道数量',
   `protocol` varchar(256) comment '协议',
   `creator_id` int(11) comment '创建人',
   `create_date` datetime comment '创建时间',
   `modifier_id` int(11) comment '修改人',
   `update_time` datetime comment '修改时间',
   primary key (id)
)
 comment '销售机器规格';

/*==============================================================*/
/* Table: fx_sales_return_pic                                   */
/*==============================================================*/
create table fx_sales_return_pic
(
   `id` int(11) not null auto_increment comment 'id',
   `return_id` int(11) not null comment '退货单id',
   `pic_id` varchar(200) comment '图片id',
   primary key (id)
)
 comment '退货订单图片';

/*==============================================================*/
/* Index: Index_return_order_orderid                            */
/*==============================================================*/
create index Index_return_order_orderid on fx_sales_return_pic
(
   return_id
);

/*==============================================================*/
/* Index: index_return_order_picId                              */
/*==============================================================*/
create index index_return_order_picId on fx_sales_return_pic
(
   pic_id
);

/*==============================================================*/
/* Table: fx_settlement_detail                                  */
/*==============================================================*/
create table fx_settlement_detail
(
   `id` int(11) not null auto_increment comment 'id',
   `agent_id` int(11) comment '代理商id',
   `source_agent_id` int(11) comment '归属代理商',
   `user_id` int(11) not null comment '消费者id',
   `machine_id` int(11) comment '柜子id',
   `source` int not null comment '归属  0 自有（自己的设备上消费）、1 商城（线上商城消费）、2 下级（发展的下级代理设备省消费）',
   `settlement_id` int(11) comment '结算id',
   `order_id` int(11) not null comment '订单id',
   `order_code` varchar(64) comment '订单号',
   `order_detail_id` int(11) not null comment '订单明细id',
   `share_profit` decimal(5,2) not null comment '提成比例',
   `pay_time` datetime not null comment '支付时间',
   `order_create_time` datetime not null comment '订单下单时间',
   `settlement_price` decimal(9,2) not null comment '结算金额',
   primary key (id)
)
 comment '结算订单明细';

/*==============================================================*/
/* Index: Index_settlement_detail_orderid                       */
/*==============================================================*/
create index Index_settlement_detail_orderid on fx_settlement_detail
(
   order_id
);

/*==============================================================*/
/* Index: Index_settlement_detail_id                            */
/*==============================================================*/
create index Index_settlement_detail_id on fx_settlement_detail
(
   settlement_id
);

/*==============================================================*/
/* Table: fx_share_profit                                       */
/*==============================================================*/
create table fx_share_profit
(
   `id` int(11) not null auto_increment comment 'id',
   `province_share_profit` decimal(5,2) comment '省代分润',
   `city_share_profit` decimal(5,2) comment '市代分润比例',
   `area_share_profit` decimal(5,2) comment '区/县代分润比例',
   `goods_price` decimal(9,2) not null comment '商品售价',
   `up_agent_share_profit` decimal(5,2) comment '代理上级分润比例',
   `agent_share_profit` decimal(5,2) comment '代理分润比例',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   `modifier_id` int(11) comment '修改人',
   `update_time` datetime comment '修改时间',
   primary key (id)
)
 comment '商城分润';

/*==============================================================*/
/* Table: fx_shopping_address                                   */
/*==============================================================*/
create table fx_shopping_address
(
   `id` int(11) not null auto_increment comment '主键ID',
   `name` varchar(64) comment '姓名',
   `phone` varchar(20) comment '手机号',
   `privince` varchar(100) comment '省',
   `city` varchar(100) comment '市',
   `area` varchar(100) default NULL comment '区',
   `address` varchar(200) comment '地址',
   `is_default` int(1) comment '0:否 1:是',
   `creator_id` int(11) comment '创建人',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表' comment '收货地址';

/*==============================================================*/
/* Index: Index_shopping_address_name                           */
/*==============================================================*/
create index Index_shopping_address_name on fx_shopping_address
(
   name
);

/*==============================================================*/
/* Table: fx_shopping_cart                                      */
/*==============================================================*/
create table fx_shopping_cart
(
   `id` int(11) not null auto_increment comment 'id',
   `user_id` int(11) comment 'user_id',
   `goods_id` int(11) comment 'good_id',
   `goods_name` varchar(200) comment '商品名称',
   `goods_att_id` varchar(200) not null comment '图片id',
   `num` int(11) comment '购买数量',
   `goods_spe_id` int(11) comment '商品规格id',
   `goods_spe1` varchar(200) comment '商品规格1',
   `goods_spe2` varchar(200) comment '商品规格2',
   `spe_price_key` varchar(256) comment '价目key',
   `sale_price` decimal(9,2) comment '零售价',
   `trade_price` decimal(9,2) comment '统批价',
   `logistics_price` decimal(9,2) comment '单价',
   `creator_time` datetime comment '添加时间',
   `is_gen_order` int(1) comment '生成订单并付款后改变状态，或直接删除',
   `user_type` int(1) default 0 comment '购物车类型 0 代理商 1 消费者',
   primary key (id)
)
 comment '购物车';

/*==============================================================*/
/* Index: Index_shopping_userid                                 */
/*==============================================================*/
create index Index_shopping_userid on fx_shopping_cart
(
   user_id
);

/*==============================================================*/
/* Index: index_shopping_goodid                                 */
/*==============================================================*/
create index index_shopping_goodid on fx_shopping_cart
(
   num,
   goods_id
);

/*==============================================================*/
/* Table: fx_user                                               */
/*==============================================================*/
create table fx_user
(
   `id` int(11) not null auto_increment comment 'id',
   `nickname` varchar(50) comment '昵称',
   `sex` int(1) comment '性别',
   `birthday` date comment '生日',
   `image` VARCHAR(500) comment '头像',
   `region` VARCHAR(100) comment '地区',
   `phone` varchar(20) comment '手机号',
   `register_id` VARCHAR(50) comment '注册ip',
   `register_eq` VARCHAR(50) comment '注册设备',
   `register_date` datetime comment '注册时间',
   `login_time` datetime comment '最近登录时间',
   `login_ip` VARCHAR(50) comment '最近登录ip',
   `of_proxy` int(11) comment '归属代理',
   `totle_consumption` decimal(9,2) comment '累计消费',
   `recent_consumption` decimal(9,2) comment '近期消费',
   `wx_openid` VARCHAR(64) comment 'wx_openid',
   `state` int(1) comment '状态 0 正常 1 冻结',
   primary key (id)
)
 comment '消费者表';

/*==============================================================*/
/* Index: index_user_nickname                                   */
/*==============================================================*/
create index index_user_nickname on fx_user
(
   nickname
);

/*==============================================================*/
/* Index: index_user_phone                                      */
/*==============================================================*/
create index index_user_phone on fx_user
(
   phone
);

/*==============================================================*/
/* Index: index_user_wx_openid                                  */
/*==============================================================*/
create index index_user_wx_openid on fx_user
(
   wx_openid
);

/*==============================================================*/
/* Table: fx_user_log                                           */
/*==============================================================*/
create table fx_user_log
(
   `id` int(11) not null auto_increment comment 'id',
   `user_id` int(11) comment '消费者id',
   `wx_openid` varchar(100) comment '微信openid',
   `log_type` varchar(100) comment '类型，根据活动自定义的key',
   `create_time` datetime comment '创建时间',
   primary key (id)
)
 comment '消费者活动记录';

/*==============================================================*/
/* Index: USER_LOG_IDX_1                                        */
/*==============================================================*/
create index USER_LOG_IDX_1 on fx_user_log
(
   wx_openid
);

/*==============================================================*/
/* Index: USER_LOG_IDX_2                                        */
/*==============================================================*/
create index USER_LOG_IDX_2 on fx_user_log
(
   log_type
);

/*==============================================================*/
/* Table: fx_voucher                                            */
/*==============================================================*/
create table fx_voucher
(
   `id` int(11) not null auto_increment comment 'id',
   `name` varchar(200) not null comment '名称',
   `price` decimal(9,2) comment '面额',
   `min_price` decimal(9,2) comment '最低消费',
   `start_time` datetime comment '有效期开始时间',
   `end_time` datetime comment '有效期结束时间',
   `use_status` int(1) comment '使用状态 0  未使用 1 已使用',
   `creator_id` int(11) comment '操作人',
   `create_date` datetime comment '操作时间',
   primary key (id)
)
 comment '代金券';

/*==============================================================*/
/* Index: fx_voucher_name                                       */
/*==============================================================*/
create index fx_voucher_name on fx_voucher
(
   name
);

/*==============================================================*/
/* Table: fx_voucher_grant_record                               */
/*==============================================================*/
create table fx_voucher_grant_record
(
   `id` int(11) not null auto_increment comment 'id',
   `user_id` int(11) not null comment '代理商id',
   `voucher_id` int(11) comment '代金券id',
   `grant_type` int(1) comment '发放类型',
   `create_date` datetime comment '发放时间',
   `is_consumer` int(1) comment '是否使用',
   primary key (id)
)
 comment '代金券发放记录';

/*==============================================================*/
/* Index: fx_voucher_user_id                                    */
/*==============================================================*/
create index fx_voucher_user_id on fx_voucher_grant_record
(
   user_id
);

/*==============================================================*/
/* Index: fx_voucher_voucher_id                                 */
/*==============================================================*/
create index fx_voucher_voucher_id on fx_voucher_grant_record
(
   voucher_id
);

/*==============================================================*/
/* Table: fx_withdrawal_apply                                   */
/*==============================================================*/
create table fx_withdrawal_apply
(
   `id` int(11) not null auto_increment comment 'id',
   `code` varchar(50) comment '提现单号',
   `total` decimal(9,2) comment '提现金额',
   `user_id` int(11) comment '申请代理商',
   `req_time` datetime comment '申请结算时间',
   `auditor_id` int(11) comment '审核人',
   `audit_time` datetime comment '审核时间',
   `audit_status` int(1) comment '审核状态 0 待审核 1 通过 2 拒绝',
   `remit_status` int(1) comment '打款状态 0 未打款 1 已打款',
   `remit_user_id` int(11) comment '打款确认人员',
   `remit_time` datetime comment '打款大约时间',
   `remark` varchar(500) comment '备注',
   primary key (id)
)
 comment '提现申请表';

/*==============================================================*/
/* Index: Index_withdrawal_apply_userid                         */
/*==============================================================*/
create index Index_withdrawal_apply_userid on fx_withdrawal_apply
(
   user_id
);

/*==============================================================*/
/* Index: index_withdrawal_apply_req_time                       */
/*==============================================================*/
create index index_withdrawal_apply_req_time on fx_withdrawal_apply
(
   req_time
);
