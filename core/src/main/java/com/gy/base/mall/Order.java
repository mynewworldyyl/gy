package com.gy.base.mall;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.id.IDStrategy;
import com.gy.entity.Account;

@Entity
@Table(name = "t_order")
@IDStrategy
public class Order implements Serializable{

	public static enum Status{
		DRAF,
		SUBMIT,
		PAYMENTING,
		PAYMENTED,
		POSTING,
		RECEIVED,
		
		GOOD_REJECTING,
		GOOD_REJECTED,
	}
	
	public static enum Typecode{
		COMMON
	}
	
	@Id
	@Column(nullable=false)
	private String id;
	
	@Column(name="order_num", length=40)
	private String orderNum;
	
	@Column(name="typecode", length=16)
	private String typecode = Typecode.COMMON.name();
	
	@Column(name="amount")
	private Float amount;
	
	@Column(name="currency", length=32)
	private String currency;
	
	@Column(name="status", length=32)
	private String status;
	
	@Column(name="remark", length=255)
	private String remark;
	
	@Column(name="address", length=255)
	private String address;
	
	@Column(name="mobile", length=6432)
	private String mobile;
	
	@ManyToMany(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinTable(name="t_order_goods",joinColumns={@JoinColumn(name="order_id")}, 
    inverseJoinColumns={@JoinColumn(name="good_id")})  
	private Set<Good> goods = new HashSet<Good>(); 
	
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Set<Good> getGoods() {
		return goods;
	}

	public void setGoods(Set<Good> goods) {
		this.goods = goods;
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

	@Override
	public int hashCode() {
		if(this.orderNum == null) {
			return "".hashCode();
		} else {
			return this.orderNum.hashCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Order)) {
			return false;
		}
		Order o = (Order) obj;
		return this.orderNum.equals(o.orderNum);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id=").append(this.id).append(", ")
		.append("orderNum=").append(this.orderNum).append(", ")
		.append("remark=").append(this.remark).append(", ")
		.append("typecode=").append(this.typecode).append("");
		
		return sb.toString();
	}
	
	
}
