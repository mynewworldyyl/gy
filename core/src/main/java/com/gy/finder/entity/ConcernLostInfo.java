package com.gy.finder.entity;

import java.io.Serializable;
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
@Table(name="t_concern_lost_info")
@IDStrategy
public class ConcernLostInfo  implements Serializable  {
		 
	private static final long serialVersionUID = -392558723613106876L;

	@Id
	@Column(nullable=false)
	private Long id=1l;

	@ManyToOne
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@ManyToOne
    @JoinColumn(name="lost_info_id",nullable=false)
	private LostInfo lostInfo;
	
	@Column(name="cancel")
    private boolean cancel=false;

	@ManyToOne
    @JoinColumn(name="concerner",nullable=false)
	private Account concerner;

	@Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

}