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
@Table(name="t_group")
@IDStrategy
public class Group {

	@Id
	private long id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@Column(name="desc0")
	private String desc = "Default Group";
	
	@Column(nullable=false)
	private String name = "defGroup";
	
	@ManyToOne  
    @JoinColumn(name="owner_id")
	private Account owner;
	
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="r_group_friends",joinColumns={@JoinColumn(name="group_id")},
     inverseJoinColumns={@JoinColumn(name="account_id")})  
	private Set<Account> friends = new HashSet<Account>(); 
	
    @Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();
	
	@ManyToOne  
    @JoinColumn(name="created_by",nullable=true)
	private Account createdBy;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn = new Date();;

	@ManyToOne  
    @JoinColumn(name="updated_by",nullable=true)
	private Account updatedBy;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addFriend(Account friend) {
		if(friend == this.getOwner()) {
			return;
		}
		this.friends.add(friend);
	}

	public String getDesc() {
		return this.desc;
	}

	public String getName() {
		return this.name;
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public Set<Account> getFriends() {
		return friends;
	}

	public void setFriends(Set<Account> friends) {
		this.friends = friends;
	}

	@Override
	public Group clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Group cln  = new Group();
		cln.setDesc(this.getDesc());
		cln.setId(this.getId());
		cln.setName(this.getName());
		/*cln.setOwner(this.getOwner().clone());
		for(UserImpl u : this.getFriends()) {
			cln.getFriends().add(u.clone());
		}*/
		return cln;
	}

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("desc: ").append(this.desc)
        .append(",id:").append(this.getId())
        .append(",Owner:").append(this.getOwner().getUserName());
		return sb.toString();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (int)this.getId();
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Account getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Account createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Account getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Account updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	
}
