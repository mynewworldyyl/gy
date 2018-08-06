package com.gy.im.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.Client;
import com.gy.base.id.IDStrategy;
import com.gy.entity.Account;

@Entity
@Table(name="t_channel")
@IDStrategy
public class Channel{

	@Id
	private long id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@Column(name="channel_name", length=64)
	private String channelName ;
	
	@Column(name="desc0", length=512)
	private String description ;
	
	@Column(name="mod0", length=16)
	private String mod ;
	
	@Column(name="channel_num", length=8)
	private String channelNum ;
	
	@Column(name="ref_id")
	private long refId ;
	
	@Column(name="typecode")
	private String typecode="Public";
	
	@ManyToOne
	@JoinColumn(name="creator_id")
	private Account creator;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "r_users_channel",
	joinColumns = {@JoinColumn(name = "channel_id", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = "account_id", referencedColumnName ="id")})
    private Set<Account> users = new HashSet<Account>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "r_deputy_channel",
	joinColumns = { @JoinColumn(name = "channel_id", referencedColumnName = "id") },
	inverseJoinColumns = {@JoinColumn(name = "account_id", referencedColumnName ="id")} )
    private Set<Account> deputies = new HashSet<Account>();
	
    @Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
	
	@ManyToOne  
    @JoinColumn(name="created_by",nullable=true)
	private Account createdBy;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@ManyToOne  
    @JoinColumn(name="updated_by",nullable=true)
	private Account updatedBy;
    
	public String getChannelName() {
		return this.channelName;
	}

	public String getDescription() {
		return this.description;
	}

	public String getChannelType() {
		return this.typecode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setChannelType(String typecode) {
		this.typecode = typecode;
	}

	public Account getOWner() {
		return this.creator;
	}

	public Set<Account> getDeputy() {
		return this.deputies;
	}
}
