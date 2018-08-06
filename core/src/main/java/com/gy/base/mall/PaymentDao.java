package com.gy.base.mall;

import org.springframework.stereotype.Component;

import com.gy.base.BaseJpalDao;

@Component
public class PaymentDao extends BaseJpalDao<Payment,String> {

	public Payment getPaymentByOrderNum(String orderNum){
		Payment payment = (Payment) this.getEntityManager()
				.createNamedQuery("getPaymentByOrderNum")
				.setParameter("orderNum", orderNum)
				.getSingleResult();
		return payment;
	}
}
