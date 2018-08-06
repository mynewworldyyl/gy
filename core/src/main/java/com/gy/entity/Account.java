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
@Table(name = "t_account")
@IDStrategy
@NamedQueries({
    @NamedQuery(name="AccountSelectByName",
    		query="SELECT a FROM Account a WHERE a.userName=:acctName  AND a.registed=:registed "),
    @NamedQuery(name="AccountSelectByMobile",
     		query="SELECT a FROM Account a WHERE a.mobile=:mobile"),
    @NamedQuery(name="AccountSelectAllByName",
     		query="SELECT a FROM Account a WHERE a.mobile=:mobile "),
})
public class Account   implements Serializable{
	
	public static final String STATU_ACTIVE = "Active";
	public static final String STATU_DISABLE = "Disabled";
	
	public static final String TYPE_COMMON = "Common";
	public static final String TYPE_ADMIN = "Admin";
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 108395152609827766L;
	@Id
	@Column(nullable=false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="client_id",nullable=false)
	private Client client;
	
	@Column(name="type_code" ,nullable=false,length=12)
    private String typeCode=TYPE_COMMON;

	@Column(name="acct_name" ,length=64)
    private String userName;

	@Column(name="pwd" ,length=64)
    private String password;
	
	@Column(name="person_id" ,length=64)
    private String personId;

	@Column(name="status" ,length=64)
    private String status = STATU_ACTIVE;

	@Column(name="icon_url" ,length=128)
    private String iconUrl;

	@Column(name="nick_name" ,length=64)
    private String nickName;
	
	@Column(name="email" ,length=64)
    private String email;

	@Column(name="office_phone" ,length=64)
    private String officePhone;

	@Column(name="home_phone" ,length=64)
    private String homePhone;

	@Column(name="mobile" ,length=64)
    private String mobile;
	
	@Column(name="integral")
    private Integer integral;
	
	@Column(name="total_integral")
    private Integer totalIntegral;
	
	@Column(name="level")
    private Integer level;
	
	@Column(name="level_name")
    private String levelName;

	@Column(name="addr_l1" ,length=64)
    private String addrLine1;

	@Column(name="addr_l2" ,length=64)
    private String addrLine2;

	@Column(name="desc0" , length=128)
    private String description;

	@Column(name="remark", length=128)
    private String remark;
	
	@Column(name="qq")
	private String qq;
	
	@Column(name="openid")
	private String openid;
	
	@Column(name="unionid")
	private String unionid;
	
	@Column(name="registed")
	private Boolean registed=false;

	@Column(name="created_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

	@Column(name="updated_on",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
	
/*	@OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy="parent")
	private Set<Attr> subTypes = new HashSet<Attr>();*/

	
    public Long getId() {
        return id;
    }

    public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Override
	public boolean equals(Object obj) {
    	if(!(obj instanceof Account)) {
    		return false;
    	}
    	
    	Account act = (Account)obj;
    	if(this.id == null && act.getId() == null) {
    		return true;
    	}
    	
    	if((this.id == null && act.getId() != null)) {
    		return false;
    	}
    	if((this.id != null && act.getId() == null)) {
    		return false;
    	}
		return this.id.equals(act.getId());
	}

	@Override
	public int hashCode() {
		if(this.id == null) {
			return super.hashCode();
		}else {
			return id.hashCode();
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id=").append(this.id)
		.append(", mobile=").append(this.mobile);
		return sb.toString();
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

    public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
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

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone == null ? null : officePhone.trim();
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone == null ? null : homePhone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAddrLine1() {
        return addrLine1;
    }

    public void setAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1 == null ? null : addrLine1.trim();
    }

    public String getAddrLine2() {
        return addrLine2;
    }

    public void setAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2 == null ? null : addrLine2.trim();
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


	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Integer getTotalIntegral() {
		return totalIntegral;
	}

	public void setTotalIntegral(Integer totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public Boolean getRegisted() {
		return registed;
	}

	public void setRegisted(Boolean registed) {
		this.registed = registed;
	}
    
    
}