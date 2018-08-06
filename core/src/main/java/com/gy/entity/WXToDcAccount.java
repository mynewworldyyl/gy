package com.gy.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.Client;
import com.gy.base.id.IDStrategy;

@Entity
@Table(name = "t_wx_dc_act")
@IDStrategy
public class WXToDcAccount  implements Serializable {

	private static final long serialVersionUID = -6830730026619508642L;

	@Id
	private Long id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@Column(name="open_id" , length=64)
    private String openId;
	
	@OneToOne
    @JoinColumn(name="act_id",nullable=false)
	private Account act;

	@ManyToOne  
    @JoinColumn(name="created_by",nullable=true)
	private Account createdBy;
	
	@ManyToOne  
    @JoinColumn(name="updated_by",nullable=true)
	private Account updatedBy;
	
	@Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
	
}