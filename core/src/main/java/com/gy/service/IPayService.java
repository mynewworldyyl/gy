package com.gy.service;

import javax.servlet.http.HttpServletRequest;

import com.gy.base.mall.Order;
import com.gy.base.mall.Payment;

public interface IPayService {

	String getID();

	Payment payOrder(Order order);

	String handlePayResp(HttpServletRequest req);

	void goodRejected(Order order);
			
	/*String handlePayResp(
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
			 String sysReserved);*/
}
