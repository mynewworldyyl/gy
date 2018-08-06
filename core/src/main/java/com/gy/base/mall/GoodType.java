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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.id.IDStrategy;
import com.gy.entity.Account;

@Entity
@Table(name = "t_good_type")
@IDStrategy
public class GoodType implements Serializable{

	@Id
	@Column(nullable=false)
	private String id;
	
	@Column(name="name", length=132)
	private String name;
	
	@Column(name="desc", length=255)
	private String desc;
	
	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy="parentType")
	private Set<GoodType> subTypes;
	
	@ManyToOne  
    @JoinColumn(name="parent_type_id",nullable=true)
	private GoodType parentType;

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
