package com.gy.base.mall.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.base.mall.payment.OrderServiceImpl;
import com.gy.service.IOrderService;

@Path("/os")
@Component
@Scope("singleton")
public class OrderService {

	private final static Logger logger = Logger.getLogger(OrderService.class);
	
	private IOrderService os;
	
	@POST
	@Path("/co")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String createOrder(@FormParam("order") String order) {
		return os.createDrafOrder(order);
	}
	
	@POST
	@Path("/up")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String updateOrder(@FormParam("order") String order) {
		return os.updateOrder(order);
	}
	
	@DELETE
	@Path("/del")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String deleteOrder(@FormParam("orderId") String orderId) {
		return os.deleteOrder(orderId);
	}
	
	@POST
	@Path("/cf")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String confirmOrder(@FormParam("orderId") String orderId) {
		return os.confirmOrder(orderId);
	}
	
	@GET
	@Path("/qr")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String queryOrders(@QueryParam("startIndex") Long startIndex,
			@QueryParam("pageSize") Integer pageSize) {
		return os.queryOrder(startIndex,pageSize);
	}
	
	@GET
	@Path("/rj")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String goodRejected(@FormParam("orderId") String orderId) {
		return os.goodRejected(orderId);
	}
	
	
	
	
	
}
