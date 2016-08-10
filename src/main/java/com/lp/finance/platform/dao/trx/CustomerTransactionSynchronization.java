package com.lp.finance.platform.dao.trx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import com.lp.transaction.client.api.TransactionClient;
import com.lp.transaction.client.model.TransactionRecordVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("customerTransactionSynchronization")
public class CustomerTransactionSynchronization extends TransactionSynchronizationAdapter {
	
	@Autowired
	TransactionClient trxClient;
	
	@Override
	public void afterCompletion(int status) {
		TransactionRecordVO vo = TransactionCoordinator.getTrxVO();
		if (vo == null) {
			log.info("no TransactionRecordVO");
			return;
		}
		if (STATUS_COMMITTED == status) {
			boolean commited = trxClient.commitMainTrx(vo);
			log.info("commit remote main trx:{}", commited);
			return;
		}
		if (STATUS_ROLLED_BACK == status) {
			boolean rollback = trxClient.rollbackMainTrx(vo);
			log.info("rollback remote main trx:{}", rollback);
			return;
		}
		log.info("unknown status does not process");
	}
	
}
