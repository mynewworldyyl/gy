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
@Table(name = "t_img_data")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="ImageDataSelectByRefId",
    		query="SELECT a FROM ImageData a WHERE a.refId=:refId AND a.ownerMod=:ownerMod"),
})
public class ImageData  implements Serializable  {
		 
	private static final long serialVersionUID = -392558748613106876L;

	@Id
	@Column(nullable=false)
	private Long id=1l;

	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@Column(name="ref_id")
    private Long refId;

	@Column(name="owner_mod")
    private String ownerMod;

	@Column(name="image_id", length=128)
    private String imageId;

	@Column(name="image_url" , length=255)
    private String imageUrl;

	@Column(name="face_count")
    private Integer faceCount;

	@Column(name="got_result_on")
    private Date gotResultOn;

	@Column(name="timeout")
    private Integer timeout=72;
	
	@Column(name="session_id",length=128)
    private String sessionId;

	@Column(name="third_img_id", length=128)
    private String thirdImgId;

	@Column(name="img_width")
    private Integer imgWidth;
	
	@Column(name="img_height")
    private Integer imgHeight;
	
	@Column(name="result", length=4096)
	private String result;

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

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getOwnerMod() {
		return ownerMod;
	}

	public void setOwnerMod(String ownerMod) {
		this.ownerMod = ownerMod;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getFaceCount() {
		return faceCount;
	}

	public void setFaceCount(Integer faceCount) {
		this.faceCount = faceCount;
	}

	public Date getGotResultOn() {
		return gotResultOn;
	}

	public void setGotResultOn(Date gotResultOn) {
		this.gotResultOn = gotResultOn;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getThirdImgId() {
		return thirdImgId;
	}

	public void setThirdImgId(String thirdImgId) {
		this.thirdImgId = thirdImgId;
	}

	public Integer getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}

	public Integer getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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