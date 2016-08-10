package com.lp.finance.platform.rest;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public interface EchoApi {

	String echo(@QueryParam("name") String name);
	
	String getUser(Long id);
	
	Echo printEcho(Echo echo);
	
	String postById(Long id);
}
