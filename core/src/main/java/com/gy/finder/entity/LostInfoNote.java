package com.gy.finder.entity;

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
@Table(name = "t_lost_info_note")
@IDStrategy
/*@NamedQueries({
    @NamedQuery(name="AccountSelectByName",
    		query="SELECT a FROM Account a WHERE a.userName=:acctName ")
})*/
public class LostInfoNote {

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
	
	@ManyToOne  
    @JoinColumn(name="lost_info_id",nullable=false)
	private LostInfo lostInfo;
	
	@ManyToOne  
    @JoinColumn(name="note_id",nullable=true)
	private LostInfoNote forNote=null;
	
	@Column(name="seq")
    private int seq=0;
	
	@Column(name="images", length=1024)
	private String images;
	
	//举报
	//private int accusation;
	
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
