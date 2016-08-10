package com.lp.finance.platform.service;

import com.baomidou.framework.service.ISuperService;
import com.lp.finance.platform.entity.FinanceEntity;
import com.lp.finance.platform.vo.FinanceVO;

public interface FinanceService extends ISuperService<FinanceEntity> {

	boolean buyFinance(FinanceVO vo);
	
	boolean rollbackBuyFinance(long trxId);
}
