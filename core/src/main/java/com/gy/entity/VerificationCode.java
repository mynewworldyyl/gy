package com.gy.entity;

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

@Entity
@Table(name = "t_ferif_code")
@NamedQueries({
    @NamedQuery(name="VerificationCodeSelectLast",
    		query="SELECT a FROM VerificationCode a WHERE a.mobile=:mobile AND a.typeCode=:typeCode "
    				+ " AND a.updatedOn=(SELECT MAX(b.updatedOn) FROM VerificationCode b "
    				+ " WHERE b.mobile=:mobile AND b.typeCode=:typeCode)"),
})
@IDStrategy
public class VerificationCode  implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1024173739743307870L;

	public static enum TypeCode{

		Regist("1001"),		
		ResetPwd("1002"); 		
		
		 public final String value;

	    /**
	     * @param value
	     */
	    private TypeCode(String value) {
	        this.value = value;
	    }
	};
	@Id
	@Column(length=8,nullable=false)
	private Long id;

	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@Column(name="code", length=64)
    private String code;

	@Column(name="type_code", length=64)
    private String typeCode;

	@Column(name="status", length=64)
    private String status;

	@Column(name="mobile", length=64)
    private String mobile;

	@Column(name="remark", length=64)
    private String remark;

	@Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
	
	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}