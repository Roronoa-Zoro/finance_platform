package com.lp.finance.platform.rest.impl;

import com.lp.finance.platform.entity.FinanceEntity;
import com.lp.finance.platform.rest.FinanceApi;
import com.lp.finance.platform.service.FinanceService;
import com.lp.finance.platform.vo.FinanceVO;
import com.lp.transaction.client.enums.CallbackState;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by 123 on 2016/8/4.
 */
@Path("finance")
@Service
@Slf4j
public class DefaultFinanceApi implements FinanceApi {

    @Autowired
    FinanceService fs;

    @POST
    @Path("buyFinance")
    @Consumes({MediaType.APPLICATION_JSON})
    public boolean buyFinance(FinanceVO financeVO) {
        return fs.buyFinance(financeVO);
    }

    @POST
    @Path("callback/check")
    @Consumes({MediaType.APPLICATION_JSON})
    public CallbackState transactionCheck(Long trxId) {
        FinanceEntity fe = new FinanceEntity();
        fe.setTrxId(trxId);
        FinanceEntity dbFe = fs.selectOne(fe);
        if (dbFe == null) {
            return CallbackState.CallbackCommitFailure;
        }

        return CallbackState.CallbackCommitSuccess;
    }

    //这个不需要，主事务方法不会处在预提交状态
    @POST
    @Path("callback/commit")
    @Consumes({MediaType.APPLICATION_JSON})
    public CallbackState transactionCommit(Long trxId) {
        return CallbackState.CallbackCommitSuccess;
    }

    @POST
    @Path("callback/rollback")
    @Consumes({MediaType.APPLICATION_JSON})
    public CallbackState transactionRollback(Long trxId) {
        boolean res = fs.rollbackBuyFinance(trxId);
        log.info("rollback trxID:{} result:{}", trxId, res);
        return CallbackState.CallbackRollbackSuccess;
    }

	@Override
	@Path("check")
	public String check() {
		return "hello";
	}
}
