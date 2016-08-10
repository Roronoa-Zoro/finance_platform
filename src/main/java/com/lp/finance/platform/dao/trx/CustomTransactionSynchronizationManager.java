package com.lp.finance.platform.dao.trx;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.lp.transaction.client.model.TransactionRecordVO;

public class CustomTransactionSynchronizationManager extends TransactionSynchronizationManager {

	public static void registerSynchronization(TransactionSynchronization synchronization, TransactionRecordVO vo)
			throws IllegalStateException {

		registerSynchronization(synchronization);
		TransactionCoordinator.setTrxVO(vo);
	}
}
