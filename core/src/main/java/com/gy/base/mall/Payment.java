package com.gy.base.mall;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.id.IDStrategy;
import com.gy.entity.Account;

@Entity
@Table(name = "t_payment")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="getPaymentByOrderNum",query="SELECT a FROM Payment a WHERE a.order.orderNum=:orderNum "),
})
public class Payment implements Serializable{

	public static final int ORDER_NUM_LEN = 20;
	
	public static enum Status{
		FAIL,
		WAIN_FOR_RESULT,
		GET_RESULT,
		FINISHED,
		
		GOOD_REJECTING,
		GOOD_REJECTED,
	}
	
	public static enum Typecode{		
		COMMON
	}
	
	public static enum Currency{		
		RMB("156"),
		USD("840"),
		EUR("978"),
		HKD("344");
		
		private String value;
		Currency(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	public static enum UnionTransType{
		//消费
		Comsume("01"),
		//消费撤销
		CancelConsume("31"),
		//预授权
		PreAuth("02"),
		//预授权撤销
		CancelPreAuth("32"),
		//预授权完成
		PreAuthFinish("03"),
		//预授权完成撤销
		CancelPreAuthFinish("33"),
		//退货
		GoodRejected("04"),
		//余额查询
		QueryBalance("71"),
		//账户验证
		CheckAccount("72"),
		//账单缴费
		PayBill("81"),
		//信用卡还款
		PayRreditCard ("82");
		
		private String value;
		
		UnionTransType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		
	}
	
	@Id
	@Column(nullable=false)
	private String id;
	
	@Column(name="typecode", length=16)
	private String typecode = Typecode.COMMON.name();
	
	@Column(name="pay_id")
	private String payId = null;
	
	@Column(name="trans_type")
	private String transType = UnionTransType.Comsume.getValue();
	
	@ManyToOne  
    @JoinColumn(name="order_id",nullable=true)
	private Order order;
	
	@Column(name="order_time",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;
	
	@Column(name="timeout",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date orderTimeout;
	
	@Column(name="amount")
	private Float amount;
	
	@Column(name="currency", length=32)
	private String currency;
	
	@Column(name="status", length=32)
	private String status=Status.WAIN_FOR_RESULT.name();
	
	@Column(name="remark", length=255)
	private String remark;
	
	@Column(name="mobile", length=6432)
	private String mobile;
	
	@Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
	
	@ManyToOne  
    @JoinColumn(name="created_by",nullable=true)
	private Account createdBy;

	@ManyToOne  
    @JoinColumn(name="updated_by",nullable=true)
	private Account updatedBy;
	

	@Override
	public int hashCode() {
		if(this.id == null) {
			return "".hashCode();
		} else {
			return this.id.hashCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Payment)) {
			return false;
		}
		Payment o = (Payment) obj;
		return this.id.equals(o.id);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id=").append(this.id).append(", ")
		.append("remark=").append(this.remark).append(", ")
		.append("typecode=").append(this.typecode).append("");		
		return sb.toString();
	}

	public String getPayId() {
		return payId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getOrderTimeout() {
		return orderTimeout;
	}

	public void setOrderTimeout(Date orderTimeout) {
		this.orderTimeout = orderTimeout;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Account getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Account createdBy) {
		this.createdBy = createdBy;
	}

	public Account getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Account updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
