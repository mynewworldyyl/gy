package com.gy.finder.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "t_lost_info")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="LostInfoSelectById",
    		query="SELECT a FROM LostInfo a WHERE a.id=:id "),
})
public class LostInfo  implements Serializable  {
		 
	private static final long serialVersionUID = -392558748613106876L;

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

	@Column(name="lost_date")
    private Date lostDate;

	@Column(name="lost_addr", length=255)
    private String lostAddr;

	@Column(name="contact_name", length=64)
    private String contactName;

	@Column(name="mobile1")
    private String contactMobile1;
	
	@Column(name="mobile2")
    private String contactMobile2;

	@Column(name="desc0", length=2048)
    private String description;
	
	@Column(name="sex", length=12)
    private String sex="male";

	@Column(name="remark", length=255)
    private String remark;
	
	@Column(name="email", length=64)
    private String email;
	
	@Column(name="images", length=128)
	private String images;
	
	@Column(name="person_id", length=64)
	private String personId;
	
	@Column(name="concern_num")
	private int concernNum=0;
	
	@Column(name="channel_num")
	private String channelNum;
	
    @Column(name="face_created_on",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
    private Date faceCreatedOn;
	
	@Column(name="is_close")
	private Boolean isClose = false;
	
	@Column(name="is_lost")
	private Boolean isLost = false;
	
	@Column(name="relatived_name",length=32)
	private String relativedName="";
	
	@Column(name="figure_print",length=128)
	private String figurePrint;
	
	@Column(name="fb_sum")
	private Float fbSum;
	
	@Column(name="fd_desc",length=255)
	private String fdDesc;
	
	@Column(name="fp_range")
	private Float fpNotifyRange;
	
	@Column(name="hi_range")
	private Float hiRange;
	
	@Column(name="id_num",length=32)
	private String idNum;
	
	@Column(name="id_picture0",length=128)
	private String idPicture0;
	
	@Column(name="id_picture1",length=128)
	private String idPicture1;
	
	@Column(name="latitude",length=32)
    private String latitude;
	
	@Column(name="longitude",length=32)
    private String longitude;
	
	@Column(name="pos",length=256)
    private String pos;
	
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
	
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public int getConcernNum() {
		return concernNum;
	}

	public void setConcernNum(int concernNum) {
		this.concernNum = concernNum;
	}

	public Date getFaceCreatedOn() {
		return faceCreatedOn;
	}

	public void setFaceCreatedOn(Date faceCreatedOn) {
		this.faceCreatedOn = faceCreatedOn;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

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

	public Date getLostDate() {
		return lostDate;
	}

	public void setLostDate(Date lostDate) {
		this.lostDate = lostDate;
	}

	public String getLostAddr() {
		return lostAddr;
	}

	public void setLostAddr(String lostAddr) {
		this.lostAddr = lostAddr;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile1() {
		return contactMobile1;
	}

	public void setContactMobile1(String contactMobile1) {
		this.contactMobile1 = contactMobile1;
	}

	public String getContactMobile2() {
		return contactMobile2;
	}

	public void setContactMobile2(String contactMobile2) {
		this.contactMobile2 = contactMobile2;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsClose() {
		return isClose;
	}

	public void setIsClose(Boolean isClose) {
		this.isClose = isClose;
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