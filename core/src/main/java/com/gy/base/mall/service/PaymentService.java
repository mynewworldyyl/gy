package com.gy.base.mall.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.service.IPayService;

@Path("/ps")
@Component
@Scope("singleton")
public class PaymentService {

	private final static Logger logger = Logger.getLogger(PaymentService.class);
	
	@Autowired
	private IPayService payService;
	
	@POST
	@Path("/backen")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String backenNotify(@Context HttpServletRequest request) {		
		String result = doService(request);
		return "success";
	}
	
	@POST
	@Path("/frontend")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String frontendNotify(@Context HttpServletRequest request) {
		String result = doService(request);
		return "success";
	}
	
	private String doService(HttpServletRequest request) {
		logger.info("GET pay server notify: ");
		String result = payService.handlePayResp(request);
		return result;
	}

	/*
	@POST
	@Path("/backen")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String backenNotify(
			@QueryParam("version") String version,
			@QueryParam("charset") String charset,
			@QueryParam("signMethod") String signMethod,
			@QueryParam("signature") String signature,
			@QueryParam("transType") String transType,
			@QueryParam("merId") String merId,
			@QueryParam("transStatus") String transStatus,
			@QueryParam("respCode") String respCode,
			@QueryParam("respMsg") String respMsg,
			@QueryParam("qn") String qn,
			@QueryParam("orderNumber") String orderNumber,
			@QueryParam("orderTime") String orderTime,
			@QueryParam("settleAmount") String settleAmount,
			@QueryParam("settleCurrency") String settleCurrency,
			@QueryParam("settleDate") String settleDate,
			@QueryParam("exchangeDate") String exchangeDate,
			@QueryParam("exchangeRate") String exchangeRate,
			@QueryParam("merReserved") String merReserved,
			@QueryParam("reqReserved") String reqReserved,
			@QueryParam("sysReserved") String sysReserved) {		
		String result = doService(version,charset, signMethod,signature,
				transType, merId,transStatus,respCode,respMsg,qn,orderNumber, orderTime, settleAmount,
				 settleCurrency,settleDate, exchangeDate,exchangeRate,merReserved, reqReserved,sysReserved);		
		return "success";
	}
	
	@POST
	@Path("/frontend")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String frontendNotify(
			@QueryParam("version") String version,
			@QueryParam("charset") String charset,
			@QueryParam("signMethod") String signMethod,
			@QueryParam("signature") String signature,
			@QueryParam("transType") String transType,
			@QueryParam("merId") String merId,
			@QueryParam("transStatus") String transStatus,
			@QueryParam("respCode") String respCode,
			@QueryParam("respMsg") String respMsg,
			@QueryParam("qn") String qn,
			@QueryParam("orderNumber") String orderNumber,
			@QueryParam("orderTime") String orderTime,
			@QueryParam("settleAmount") String settleAmount,
			@QueryParam("settleCurrency") String settleCurrency,
			@QueryParam("settleDate") String settleDate,
			@QueryParam("exchangeDate") String exchangeDate,
			@QueryParam("exchangeRate") String exchangeRate,
			@QueryParam("merReserved") String merReserved,
			@QueryParam("reqReserved") String reqReserved,
			@QueryParam("sysReserved") String sysReserved) {
		String result = doService(version,charset, signMethod,signature,
				transType, merId,transStatus,respCode,respMsg,qn,orderNumber, orderTime, settleAmount,
				 settleCurrency,settleDate, exchangeDate,exchangeRate,merReserved, reqReserved,sysReserved);		
		return result;
	}
	
	private String doService(
			 String version,
			 String charset,
			String signMethod,
			String signature,
			 String transType,
			String merId,
			 String transStatus,
			 String respCode,
			 String respMsg,
			 String qn,
			String orderNumber,
			 String orderTime,
			String settleAmount,
			String settleCurrency,
			 String settleDate,
			String exchangeDate,
			 String exchangeRate,
			 String merReserved,
			 String reqReserved,
			 String sysReserved) {
		logger.info("GET pay server notify: ");
		//logger.info(resp);
		String result = payService.handlePayResp(version,charset, signMethod,signature,
				transType, merId,transStatus,respCode,respMsg,qn,orderNumber, orderTime, settleAmount,
				 settleCurrency,settleDate, exchangeDate,exchangeRate,merReserved, reqReserved,sysReserved);
		return result;
	}*/

}
