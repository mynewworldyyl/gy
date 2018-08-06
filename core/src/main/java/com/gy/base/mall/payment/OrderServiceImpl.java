package com.gy.base.mall.payment;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gy.base.CommonException;
import com.gy.base.id.ICacheIDGenerator;
import com.gy.base.mall.Good;
import com.gy.base.mall.IOrderDao;
import com.gy.base.mall.Order;
import com.gy.base.mall.OrderNumId;
import com.gy.base.mall.Payment;
import com.gy.service.IOrderService;
import com.gy.service.IPayService;
import com.gy.util.Utils;

@Component
public class OrderServiceImpl implements IOrderService {
	
	private final static Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private ICacheIDGenerator generator;
	
	@Autowired
	private GoodTypeManager gtMng;
	
	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private IPayService payService;

	@Override
	public String createDrafOrder(String jsonOrder) {
		if(null == jsonOrder) {
			throw new CommonException("OrderParamIsNull");
		}
		//Account act = UserContext.getAccount();		
		JSONObject jo = JSON.parseObject(jsonOrder);
        JSONArray goods = jo.getJSONArray("goods");
		if(goods == null) {
			throw new CommonException("GoodsIsNull");
		}
		
		Order order = new Order();
		order.setId(generator.getStringId(Order.class));
		order.setOrderNum(generator.getStringId(OrderNumId.class,Payment.ORDER_NUM_LEN));
		order.setAddress(jo.getString("addr"));
		order.setAmount(jo.getFloat("amount"));//总价
		order.setCurrency(jo.getString("currency"));
		order.setMobile(jo.getString("mobile"));		
		order.setRemark(jo.getString("remart"));
		order.setStatus(Order.Status.DRAF.name());
		//GoodType gt = this.gtMng.getGoodType();
		order.setTypecode(jo.getString("typecode"));
			
		for(Object jsonGood : goods) {
			JSONObject g = (JSONObject)jsonGood;
			String goodId = g.getString("goodId");
			String typeId = g.getString("typeId");
			if(goodId == null) {
				if(typeId == null || "".equals(typeId.trim())) {
					throw new CommonException("GoodDataError");
				}
				//GoodType gt = gtMng.getGoodType(typeId);
				Good good = gtMng.createVirtualGood(typeId);
				if(null == good) {
					throw new CommonException("GoodCreatedFail",typeId);
				}
				order.getGoods().add(good);
			}
		}
		orderDao.save(order);
		
		return Utils.getInstance().getResponse(null, true, null);
	}

	@Override
	public String updateOrder(String jsonOrder) {

		if(null == jsonOrder) {
			throw new CommonException("OrderParamIsNull");
		}

		//Account act = UserContext.getAccount();		
		JSONObject jo = JSON.parseObject(jsonOrder);
		String orderId = jo.getString("orderId");
		if(orderId == null || "".equals(orderId.trim())) {
			throw new CommonException("OrderParamIsNull");
		}
		
		Order order = this.orderDao.getById(orderId);
		if(order == null ) {
			throw new CommonException("OrderNotFound",orderId);
		}
		
        JSONArray goods = jo.getJSONArray("goods");
		
		order.setAddress(jo.getString("addr"));
		order.setAmount(jo.getFloat("amount"));//总价
		//order.setCurrency(jo.getString("currency"));
		order.setMobile(jo.getString("mobile"));		
		order.setRemark(jo.getString("remart"));
		//order.setStatus(Order.Status.DRAF.name());
		//GoodType gt = this.gtMng.getGoodType();
		//order.setTypecode(jo.getString("typecode"));
			
		if(goods == null || goods.isEmpty()) {
			order.getGoods().clear();
		}else {
			/*for(Object jsonGood : goods) {
				JSONObject g = (JSONObject)jsonGood;
				String goodId = g.getString("goodId");
				String typeId = g.getString("typeId");
				if(goodId == null) {
					if(typeId == null || "".equals(typeId.trim())) {
						throw new CommonException("GoodDataError");
					}
					//GoodType gt = gtMng.getGoodType(typeId);
					Good good = gtMng.createVirtualGood(typeId);
					order.getGoods().add(good);
				}
			}*/
		}	
		orderDao.update(order);
		return Utils.getInstance().getResponse(null, true, null);
	}

	@Override
	public String deleteOrder(String orderId) {
		
		return null;
	}

	@Override
	public String confirmOrder(String orderId) {
		Order order = this.orderDao.getById(orderId);
		if(order == null ) {
			throw new CommonException("OrderNotFound",orderId);
		}
		order.setStatus(Order.Status.SUBMIT.name());
		orderDao.update(order);
		return Utils.getInstance().getResponse(null, true, null);
	}
	
	@Override
	public String payOrder(String orderId) {
		Order order = this.orderDao.getById(orderId);
		if(order == null ) {
			throw new CommonException("OrderNotFound",orderId);
		}
		Payment payment = this.payService.payOrder(order);		
		if(payment.getPayId() == null) {
			throw new CommonException("PaymentFail",order.getOrderNum());
		}
		order.setStatus(Order.Status.PAYMENTING.name());
		orderDao.update(order);
		return Utils.getInstance().getResponse(null, true, null);
	}

	@Override
	public String queryOrder(Long startIndex, Integer pageSize) {
		
		return null;
	}

	@Override
	public String goodRejected(String orderId) {
		Order order = this.orderDao.getById(orderId);
		if(order == null) {
			throw new CommonException("OrderNotFound",orderId);
		}
		this.payService.goodRejected(order);
		order.setStatus(Order.Status.GOOD_REJECTING.name());
		this.orderDao.update(order);
		return null;
	}	
	
}
