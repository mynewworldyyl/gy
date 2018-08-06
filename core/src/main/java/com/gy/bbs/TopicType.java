package com.gy.bbs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.Client;
import com.gy.base.id.IDStrategy;
import com.gy.entity.Account;

@Entity
@Table(name = "t_topic_type")
@IDStrategy
/*@NamedQueries({
    @NamedQuery(name="AccountSelectByName",
    		query="SELECT a FROM Account a WHERE a.userName=:acctName ")
})*/
public class TopicType {

	@Id
	@Column(nullable=false)
	private Long id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@Column(name="description" , length=128)
    private String description;
	
	@Column(name="title" , length=128, nullable=false, unique=true)
    private String title;
	
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
