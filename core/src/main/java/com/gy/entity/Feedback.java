package com.gy.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gy.base.Client;
import com.gy.base.id.IDStrategy;

@Entity
@Table(name = "t_feedback")
@IDStrategy
public class Feedback  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6830730026619508646L;

	@Id
	@Column(length=8,nullable=false)
	private Long id;
	
	@ManyToOne  
    @JoinColumn(name="client_id",nullable=false)
	private Client client;

	@Column(name="type_code" , length=8)
    private String typeCode;

	@Column(name="status" , length=8)
    private String status;

	@ManyToOne
	@JoinColumn(name = "acct_id", nullable = false)
    private Account account;

	@Column(name="nick_name" , length=8)
    private String nickName;

	@Column(name="email" , length=8)
    private String email;

	@Column(name="content" , length=8)
    private String content;

	@Column(name="desc0" , length=8)
    private String description;

	@Column(name="remark" , length=8)
    private String remark;

	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy="feedback")
    private Set<Attachment> attachments;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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