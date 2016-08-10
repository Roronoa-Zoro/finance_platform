package com.lp.finance.platform.rest;

import com.lp.finance.platform.vo.FinanceVO;
import com.lp.transaction.client.enums.CallbackState;

/**
 * Created by 123 on 2016/8/4.
 */
public interface FinanceApi {
	
    public boolean buyFinance(FinanceVO financeVO);

    public CallbackState transactionCheck(Long trxId);

    public CallbackState transactionCommit(Long trxId);

    public CallbackState transactionRollback(Long trxId);
    
    public String check();
}
