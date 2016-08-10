package com.lp.finance.platform.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 123 on 2016/8/4.
 */
@Data
public class FinanceVO {
    private String userId;
    private BigDecimal amount;
    private long productId;
    private long accId;
    private long trxId;
}
