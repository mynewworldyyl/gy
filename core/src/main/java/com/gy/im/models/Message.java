package com.gy.im.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.Client;
import com.gy.base.id.IDStrategy;
import com.gy.entity.Account;

@Entity
@Table(name="t_message")
@IDStrategy
public class Message{
	
	static enum MessageType{
		FriendMsg,
		ChannelMsg,
		MatchLostInfo,
		MatchCollectInfo
	}
	
	@Id
	private long mid;
	
	@Column(name="to1", length=32)
	private String to;
	
	@Column(name="one2one")
	private Boolean one2one;
	
	@Column(name="read1")
	private Boolean read;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="tos",nullable=true) 
	private String tos="";
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="content",nullable=true) 
	private String content;
	
	@Column(name="send_date",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendDate = new Date();
	
	@Column(name="typecode", length=16)
	private String typecode = MessageType.FriendMsg.name();
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
    @Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
	
	@ManyToOne  
    @JoinColumn(name="created_by",nullable=true)
	private Account createdBy;
	
	@ManyToOne  
    @JoinColumn(name="updated_by",nullable=true)
	private Account updatedBy;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	
}