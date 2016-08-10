package com.lp.finance.platform.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.lp.account.client.api.AccountApi;
import com.lp.account.client.enums.BusinessType;
import com.lp.account.client.enums.OperationType;
import com.lp.account.client.model.Request;
import com.lp.account.client.model.RequestDetail;
import com.lp.finance.platform.dao.FinanceMapper;
import com.lp.finance.platform.dao.trx.CustomerTransactionSynchronization;
import com.lp.finance.platform.dao.trx.CustomTransactionSynchronizationManager;
import com.lp.finance.platform.entity.FinanceEntity;
import com.lp.finance.platform.entity.FinanceProductEntity;
import com.lp.finance.platform.enums.FinanceStatus;
import com.lp.finance.platform.service.FinanceProductService;
import com.lp.finance.platform.service.FinanceService;
import com.lp.finance.platform.vo.FinanceVO;
import com.lp.transaction.client.api.TransactionClient;
import com.lp.transaction.client.model.TransactionRecordVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class FinanceServiceImpl extends SuperServiceImpl<FinanceMapper, FinanceEntity> implements FinanceService {

	@Autowired
	FinanceProductService productService;
	@Autowired
	FinanceMapper financeMapper;
	@Autowired
	TransactionClient trxClient;
	@Autowired
	AccountApi accountApi;
	@Value("${finance.callback}")
	String callback;
	@Value("${finance.commit.callback}")
	String commitCallback;
	@Value("${finance.rollback.callback}")
	String rollbackCallback;
	@Autowired
	CustomerTransactionSynchronization trxSync;
	
	@Override
	public boolean buyFinance(FinanceVO vo) {
		FinanceProductEntity product = productService.selectById(vo.getProductId());
		if (product == null) {
			log.info("product not exists");
			return false;
		}
		if (vo.getAmount() == null) {
			log.info("amount can not be null");
			return false;
		}
		
		TransactionRecordVO trvo = new TransactionRecordVO();
		trvo.setTrxPartiNum(1);
		trvo.setTrxCallback(callback);
		trvo.setTrxRollbackCallback(rollbackCallback);
		trvo.setTrxSubmitCallback(commitCallback);
		TransactionRecordVO remoteVo = trxClient.addMainTrx(trvo);
		if (remoteVo.getTrxId() == null) {
			log.info("can not add main transaction");
			return false;
		}
		
		CustomTransactionSynchronizationManager.registerSynchronization(trxSync, remoteVo);
		CustomTransactionSynchronizationManager.bindResource(this, remoteVo);
//		TransactionSynchronizationManager.registerSynchronization(trxSync);
		//new finance record
		FinanceEntity finance = new FinanceEntity();
		finance.setAmount(vo.getAmount());
		finance.setCreateTime(LocalDateTime.now().toDate());
		finance.setUpdateTime(LocalDateTime.now().toDate());
		finance.setFinanceStatus(FinanceStatus.Financing.getStatus());
		finance.setProductId(vo.getProductId());
		finance.setAccId(vo.getAccId());
		finance.setUserId(vo.getUserId());
		LocalDate start = LocalDate.now();
		finance.setStartDate(start.toDate());
		LocalDate end = start.plusMonths(12);
		finance.setEndDate(end.toDate());
		finance.setTrxId(remoteVo.getTrxId());
		product.setSoldAmount(product.getSoldAmount().add(vo.getAmount()));
		financeMapper.insert(finance);
		
		FinanceProductEntity where = new FinanceProductEntity();
		where.setProductId(vo.getProductId());
		where.setVersion(product.getVersion());
		product.setVersion(product.getVersion() + 1);
		productService.update(product, where);
		
		log.info("will invoke account system");
		Request r = new Request();
		r.setBusinessType(BusinessType.Finance.getType());
		r.setBusReqId(finance.getFinanceId());
		r.setTrxId(remoteVo.getTrxId());
		List<RequestDetail> list = new ArrayList<>();
		r.setDetails(list);
		
		RequestDetail detail = new RequestDetail();
		detail.setAccId(vo.getAccId());
		detail.setAmount(vo.getAmount());
		if (vo.getAmount().compareTo(BigDecimal.valueOf(0L)) > 0) {
			detail.setOperateType(OperationType.Plus.getType());
		} else {
			detail.setOperateType(OperationType.Minus.getType());
		}
		list.add(detail);
		
		boolean res = accountApi.operateAccountRequest(r);
		if (res == false) {
			throw new RuntimeException("operate account fails, rollback local trx");
		}
		return true;
	}

	@Override
	public boolean rollbackBuyFinance(long trxId) {
		FinanceEntity fe = new FinanceEntity();
		fe.setTrxId(trxId);
		FinanceEntity dbFe = super.selectOne(fe);
		if (FinanceStatus.Invalid.getStatus() == dbFe.getFinanceStatus()) {
			log.info("finance:{} is already rollback, trxId:{}", dbFe.getFinanceId(), trxId);
			return true;
		}
		
		FinanceProductEntity fpe = productService.selectById(dbFe.getProductId());
		
		dbFe.setFinanceStatus(FinanceStatus.Invalid.getStatus());
		dbFe.setUpdateTime(new Date());
		super.updateById(dbFe);
		
		fpe.setSoldAmount(fpe.getSoldAmount().subtract(dbFe.getAmount()));
		fpe.setVersion(fpe.getVersion() + 1);
		
		FinanceProductEntity where = new FinanceProductEntity();
		where.setProductId(fpe.getProductId());
		where.setVersion(fpe.getVersion());
		//TODO 此处应有重试操作，避免并发时更新失败
		productService.update(fpe, where);
		
		return true;
	}

}
