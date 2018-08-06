package com.gy.base.mall;

import com.gy.base.IBaseDao;

public interface IOrderDao extends IBaseDao<Order,String>{

	Order getById(String valueOf);

	Order getOrder(String orderId);

}
