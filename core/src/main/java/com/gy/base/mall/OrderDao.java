package com.gy.base.mall;

import org.springframework.stereotype.Component;

import com.gy.base.BaseJpalDao;

@Component
public class OrderDao extends BaseJpalDao<Order,String> implements IOrderDao {

	@Override
	public Order getById(String id) {
		return this.getEntityManager().find(Order.class, id);
	}

	@Override
	public Order getOrder(String orderNum) {
		StringBuffer sb = new StringBuffer("SELECT a FROM Order WHERE a.orderNum=")
		.append(orderNum);
		Order or = (Order)this.getEntityManager()
				.createQuery(sb.toString())
				.getSingleResult();
		return or;
	}

	
}
