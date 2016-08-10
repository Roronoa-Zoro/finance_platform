package com.lp.finance.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@Data
@TableName("finance")
public class FinanceEntity {

	@TableId(value="finance_id", type=IdType.AUTO)
	private Long financeId;
	@TableField
	private BigDecimal amount;
	@TableField("user_id")
	private String userId;
	@TableField("finance_status")
	private Integer financeStatus;
	@TableField("product_id")
	private Long productId;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;
	@TableField("start_date")
	private Date startDate;
	@TableField("end_date")
	private Date endDate;
	@TableField("trx_id")
	private Long trxId;
	@TableField("acc_id")
	private Long accId;
}
