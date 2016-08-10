package com.lp.finance;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.lp.finance.platform.rest.Echo;
import com.lp.finance.platform.vo.FinanceVO;
import com.lp.transaction.client.enums.CallbackState;

public class RestTest {

	RestTemplate rt = new RestTemplate();
	
	@Test
	public void accountCommitTest() {
		String url = "http://127.0.0.1:8068/account/callback/commit";
		Long id = 6L;
		CallbackState res = rt.postForObject(url, id, CallbackState.class);
		System.err.println(res.name());
	}
	
	@Test
	public void checkTest() {
		String url = "http://127.0.0.1:8088/finance/callback/check";
		Long id = 7L;
		Boolean res = rt.postForObject(url, id, Boolean.class);
		System.err.println(res);
	}
	@Test
	public void rollbackFinance() {
		String url = "http://127.0.0.1:8088/services/finance/callback/rollback";
		Long id = 7L;
		String res = rt.postForObject(url, id, String.class);
		System.err.println(res);
	}
	
	@Test
	public void buyTest() {
		String url = "http://127.0.0.1:8088/finance/buyFinance";
		FinanceVO vo = new FinanceVO();
		vo.setAccId(1);
		vo.setAmount(BigDecimal.valueOf(1000));
		vo.setProductId(1);
		vo.setUserId("123456");
		boolean res = rt.postForObject(url, vo, Boolean.class);
		System.err.println("buy finance success:" + res);
	}
	
	@Test
	public void testPostEntity() {
		String url = "http://127.0.0.1:8088/echo/print";
		Echo req = new Echo();
		req.setEchoId(1);
		req.setEchoRes("req");
		Echo res = rt.postForObject(url, req, Echo.class);
		System.err.println(res);
	}
	
	@Test
	public void testGetWithParam() {
		String url = "http://127.0.0.1:8088/services/echo/test";
		Map<String, Object> var = new HashMap<>();
		var.put("name", "hello");
		String res = rt.getForObject(url, String.class, var);
		System.err.println(res);
	}
	
	@Test
	public void testPostWityId() {
		String url = "http://127.0.0.1:8088/services/echo/printId";
		Long id = 1L;
		String res = rt.postForObject(url, id, String.class);
		System.err.println(res);
	}

}
