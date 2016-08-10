package com.lp.finance.platform.rest.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.lp.finance.platform.rest.Echo;
import com.lp.finance.platform.rest.EchoApi;

@Path("echo")
@Service
public class DefaultEchoApi implements EchoApi {

	@Override
	@GET
	@Path("test")
//	@Produces("application/json; charset=UTF-8")
	@Produces("text/plain") 
	public String echo(@QueryParam("name1") String name) {
		return "echo" + name;
	}
	
	@GET
    @Path("{id : \\d+}")
    public String getUser(@PathParam("id") Long id) {
        if (RpcContext.getContext().getRequest(HttpServletRequest.class) != null) {
            System.out.println("Client IP address from RpcContext: " + RpcContext.getContext().getRequest(HttpServletRequest.class).getRemoteAddr());
        }
        if (RpcContext.getContext().getResponse(HttpServletResponse.class) != null) {
            System.out.println("Response object from RpcContext: " + RpcContext.getContext().getResponse(HttpServletResponse.class));
        }
        return "User:" + id;
    }

	@Override
	@POST
    @Path("print")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ContentType.APPLICATION_JSON_UTF_8})
	public Echo printEcho(Echo echo) {
		System.err.println("received:" + echo);
		echo.setEchoId(echo.getEchoId() + 1);
		echo.setEchoRes("resp:" + System.currentTimeMillis());
		return echo;
	}

	@Override
	@POST
    @Path("printId")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({ContentType.APPLICATION_JSON_UTF_8})
	public String postById(Long id) {
		
		return "get id:"+id;
	}

}
