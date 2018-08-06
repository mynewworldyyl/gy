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
@Table(name = "t_face")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="FaceSelectCollLostInfoFaces",
    		query="SELECT a FROM Face a WHERE a.ownerMod=:ownerMod "
    				/*+ "AND a.createdOn >= :lostDate"*/),
	 @NamedQuery(name="FaceSelectLostInfoFaces",
		query="SELECT a FROM Face a WHERE a.ownerMod=:ownerMod ")
})
public class Face  implements Serializable  {
		 
	private static final long serialVersionUID = -392558748613106876L;

	@Id
	@Column(nullable=false)
	private Long id=1l;

	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@ManyToOne  
    @JoinColumn(name="image_id",nullable=true)
    private ImageData imageData;
	
	@Column(name="owner_mod" , length=64)
    private String ownerMod;

	@Column(name="face_id", length=128)
    private String faceId;

	@Column(name="face_json",length=1024)
    private String faceJson;

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

	public ImageData getImageData() {
		return imageData;
	}

	public void setImageData(ImageData imageData) {
		this.imageData = imageData;
	}

	public String getOwnerMod() {
		return ownerMod;
	}

	public void setOwnerMod(String ownerMod) {
		this.ownerMod = ownerMod;
	}

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public String getFaceJson() {
		return faceJson;
	}

	public void setFaceJson(String faceJson) {
		this.faceJson = faceJson;
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