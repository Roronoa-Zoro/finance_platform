package com.lp.finance.platform.dao.trx;

import com.lp.transaction.client.model.TransactionRecordVO;

public class TransactionCoordinator {

	public static final ThreadLocal<TransactionRecordVO> trxVO = new ThreadLocal<>();
	
	public static void setTrxVO(TransactionRecordVO vo) {
		trxVO.set(vo);
	}
	
	public static TransactionRecordVO getTrxVO() {
		return trxVO.get();
	}
}
