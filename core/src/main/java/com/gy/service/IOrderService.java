package com.gy.service;

public interface IOrderService {

	String createDrafOrder(String jsonOrder);
	
	//String createOrder(String order);

	String updateOrder(String order);

	String deleteOrder(String orderId);

	String confirmOrder(String orderId);

	String queryOrder(Long startIndex, Integer pageSize);

	String payOrder(String orderId);

	String goodRejected(String orderId);
	
}
