package com.gy.entity;

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

@Entity
@Table(name = "t_attachment")
@IDStrategy
/*@NamedQueries({
    @NamedQuery(name="getClientByTypecode",query="SELECT a FROM Client a WHERE a.typecode=:typecode"),
})*/
public class Attachment   implements Serializable{
   
	private static final long serialVersionUID = 3111559380612419254L;

	@Id
	@Column(length=8,nullable=false)
	private Long id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@Column(name="type_code" ,nullable=false,length=12)
    private String typeCode;

	@Column(name="status" ,nullable=false,length=12)
    private String status;

	@Column(name="file_name" ,nullable=false,length=12)
    private String fileName;

	@Column(name="url" ,nullable=false,length=12)
    private String url;

	@Column(name="acct_name" ,nullable=false,length=12)
    private String userName;

	@Column(name="desc0" ,nullable=false,length=12)
    private String description;

	@Column(name="remark" ,nullable=false,length=12)
    private String remark;

	@Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
	
	@ManyToOne
	@JoinColumn(name = "feedback_id", nullable = true)
	private Feedback feedback;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
}