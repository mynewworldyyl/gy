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
@Table(name = "t_img_match")
@IDStrategy
public class ImageMatch  implements Serializable  {
		 
	private static final long serialVersionUID = -392558748613106876L;

	@Id
	@Column(nullable=false)
	private Long id=1l;

	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@Column(name="owner_mod",length=64)
    private String ownerMod;

	@ManyToOne  
    @JoinColumn(name="lost_info_id",nullable=true)
    private LostInfo lostInfo;
	
	@ManyToOne  
    @JoinColumn(name="coll_li_id",nullable=true)
    private CollectLostInfo collectLostInfo;
	
	@Column(name="src_face_id")
    private Long srcFaceId;
	
	@Column(name="dest_face_id")
    private Long destFaceId;

	@Column(name="result", length=4096)
    private String result;

	@Column(name="month")
    private Double month;
	
	@Column(name="eyebrow")
    private Double eyebrow;
	
	@Column(name="eye")
    private Double eye;
	
	@Column(name="nose")
    private Double nose;
	
	@Column(name="similarity")
    private Double similarity;
	
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

	public String getOwnerMod() {
		return ownerMod;
	}

	public void setOwnerMod(String ownerMod) {
		this.ownerMod = ownerMod;
	}

	public LostInfo getLostInfo() {
		return lostInfo;
	}

	public void setLostInfo(LostInfo lostInfo) {
		this.lostInfo = lostInfo;
	}

	public CollectLostInfo getCollectLostInfo() {
		return collectLostInfo;
	}

	public void setCollectLostInfo(CollectLostInfo collectLostInfo) {
		this.collectLostInfo = collectLostInfo;
	}

	public Long getSrcFaceId() {
		return srcFaceId;
	}

	public void setSrcFaceId(Long srcFaceId) {
		this.srcFaceId = srcFaceId;
	}

	public Long getDestFaceId() {
		return destFaceId;
	}

	public void setDestFaceId(Long destFaceId) {
		this.destFaceId = destFaceId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Double getMonth() {
		return month;
	}

	public void setMonth(Double month) {
		this.month = month;
	}

	public Double getEyebrow() {
		return eyebrow;
	}

	public void setEyebrow(Double eyebrow) {
		this.eyebrow = eyebrow;
	}

	public Double getEye() {
		return eye;
	}

	public void setEye(Double eye) {
		this.eye = eye;
	}

	public Double getNose() {
		return nose;
	}

	public void setNose(Double nose) {
		this.nose = nose;
	}

	public Double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
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