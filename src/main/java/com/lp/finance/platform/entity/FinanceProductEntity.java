package com.lp.finance.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@Data
@TableName("finance_product")
public class FinanceProductEntity {

	@TableId(value="product_id", type=IdType.AUTO)
	private Long productId;
	@TableField("product_name")
	private String productName;
	@TableField("total_amount")
	private BigDecimal totalAmount;
	@TableField("sold_amount")
	private BigDecimal soldAmount;
	@TableField
	private String creator;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;
	@TableField
	private int version;
	
}
