package com.gy.bbs;

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
@Table(name = "t_topic")
@IDStrategy
/*@NamedQueries({
    @NamedQuery(name="AccountSelectByName",
    		query="SELECT a FROM Account a WHERE a.userName=:acctName ")
})*/
public class Topic {

	@Id
	@Column(nullable=false)
	private Long id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@Lob
    @Basic(fetch=FetchType.LAZY)
	@Column(name="content")
    private String content;
	
	@Column(name="title" , length=128)
    private String title;
	
	@Column(name="read_num")
    private int readNum=0;
	
	@Column(name="note_num")
    private int noteNum=0;
	
	@Column(name="resolved")
    private boolean resolved=false;
	
	@Column(name="locked")
    private boolean locked = false;
	
	@Column(name="recall")
    private boolean recall = false;	
	
	@Column(name="top_seq")
    private int topSeq=0;
	
	@Column(name="first_topic" )
    private boolean firstTopic=false;
	
	@Column(name="essence" )
    private boolean essence=false;
	
	@ManyToOne  
    @JoinColumn(name="topic_type",nullable=false)
	private TopicType topicType;
	
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
