package com.lp.finance.platform.bootstrap;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FinanceStarter {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-jdbc.xml");
		ac.start();
		System.err.println("finance server start to serving, press any key to exit...");
		System.in.read();
		ac.close();
	}

}
