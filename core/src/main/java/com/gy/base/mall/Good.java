package com.gy.base.mall;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.id.IDStrategy;
import com.gy.entity.Account;

@Entity
@Table(name = "t_good")
@IDStrategy
public class Good implements Serializable{

	@Id
	@Column(nullable=false)
	private String id;
	
	@Column(name="type_id")
	private GoodType type;
	
	@Column(name="status")
	private String status;
	
	@Column(name="serial_id")
	private GoodSerial serial;
	
	@Column(name="sell_price")
	private Float sellPrice;
	
	@OneToOne  
    @JoinColumn(name="buyer_id",nullable=true)
	private Account buyer;
	
	@Column(name="sell_batch_id")
	private SellBatch sellBatch;	
	
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
}
