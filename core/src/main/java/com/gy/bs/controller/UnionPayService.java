package com.gy.bs.controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 接收通知并处理
 *	//CallBackUnionPay.java
	//这个是我自己写的一个银联回调的servlet,opt为自定义参数,opt=0代表支付订单,opt=1代表充值操作
 * @author gxw
 */
@Path("/ups")
@Component
@Scope("singleton")
public class UnionPayService {
	
	private final static Logger logger = Logger.getLogger(UnionPayService.class);
	
	@POST
	@Path("/notify")
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String receiveNotify(
			@FormParam("opt") String opt,
			@FormParam("user_id") String userId,
			@FormParam("order_id") String orderId,
			@FormParam("transStatus") String transStatus,
			@FormParam("settleAmount") String settleAmount) {
		System.out.println("接收到通知!");
		// 服务器签名验证成功
		// 请在这里加上商户的业务逻辑程序代码
		if ("0".equals(opt)) {
			logger.info("user_id" + userId + "&order_id=" + orderId);
			// 获取通知返回参数，可参考接口文档中通知参数列表(以下仅供参考)
			if (null != transStatus && transStatus.equals("00")) {
				// 交易处理成功
			}
			return "success";
		}
		if ("1".equals(opt)) {
			//String payMoney2 = params.get("settleAmount");
			//String user_id2 = personParams.get("user_id");
			// 获取通知返回参数，可参考接口文档中通知参数列表(以下仅供参考)
			//String transStatus2 = request.getParameter("transStatus");// 交易状态
			if (null != transStatus && transStatus.equals("00")) {
				// 交易处理成功
			}
			return "success";
		} else {// 服务器签名验证失败
			return "fail";
		}

	}
}
