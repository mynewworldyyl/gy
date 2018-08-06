package com.gy.finder.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.Client;
import com.gy.base.id.IDStrategy;
import com.gy.entity.Account;

@Entity
@Table(name = "t_collect_lost_info")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="CollectLostInfoSelectById",
    		query="SELECT a FROM CollectLostInfo a WHERE a.id=:id "),
})
public class CollectLostInfo  implements Serializable  {
		 
	private static final long serialVersionUID = -3928865878613106876L;

	@Id
	@Column(nullable=false)
	private Long id=1l;

	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@Column(name="name", length=64)
    private String name;

	@Column(name="age")
    private Integer age;

	@Column(name="height")
    private Integer height;

	@Column(name="found_date")
    private Date foundDate;

	@Column(name="found_addr", length=255)
    private String foundAddr;

	@Column(name="contact_name", length=64)
    private String contactName;

	@Column(name="desc0", length=2048)
    private String description;

	@Column(name="remark", length=255)
    private String remark;
	
	@Column(name="is_close")
	private Boolean isClose = false;
	
	@Column(name="images", length=128)
	private String images;
	
	@Column(name="figure_print",length=128)
	private String figurePrint;
	
	@Column(name="mobile1")
    private String contactMobile1;
	
	@Column(name="latitude",length=32)
    private String latitude;
	
	@Column(name="longitude",length=32)
    private String longitude;
	
	@Column(name="pos",length=256)
    private String pos;
	
	@Column(name="loc",length=128)
    private String loc;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public String getFoundAddr() {
		return foundAddr;
	}

	public void setFoundAddr(String foundAddr) {
		this.foundAddr = foundAddr;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsClose() {
		return isClose;
	}

	public void setIsClose(Boolean isClose) {
		this.isClose = isClose;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getContactMobile1() {
		return contactMobile1;
	}

	public void setContactMobile1(String contactMobile1) {
		this.contactMobile1 = contactMobile1;
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
}