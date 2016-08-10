package com.lp.finance;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lp.transaction.client.api.TransactionClient;
import com.lp.transaction.client.model.TransactionRecordVO;

public class DubboTest {

	@Test
	public void testDubbo() {

		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-jdbc.xml");
		TransactionClient trxClient = ac.getBean(TransactionClient.class);
		TransactionRecordVO trvo = new TransactionRecordVO();
		trvo.setTrxPartiNum(1);
		trvo.setTrxCallback("");
		trvo.setTrxRollbackCallback("");
		trvo.setTrxSubmitCallback("");
		TransactionRecordVO remoteVo = trxClient.addMainTrx(trvo);
		System.err.println(remoteVo);
	}

}
