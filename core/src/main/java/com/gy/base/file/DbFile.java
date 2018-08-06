package com.gy.base.file;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gy.base.Client;
import com.gy.base.id.IDStrategy;
import com.gy.entity.Account;

@Entity
@Table(name = "t_file")
@IDStrategy
@NamedQueries({
   @NamedQuery(name="removeFileById",query="DELETE FROM DbFile a Where a.id = :id"),
   @NamedQuery(name="selectLatest",query="SELECT a FROM DbFile a WHERE a.contentType=:contentType "
   		+ " AND a.createdOn=(SELECT MAX(f.createdOn) FROM DbFile f WHERE f.contentType=:contentType) "),
})
public class DbFile {

	public static final String CONTENT_TYPE = "contentType";
	
	public static enum ContentType{
		Normal,
		File,
		Image,
		APK,
		DiviceRom
	};
	
	@Id
	@Column(length = 64)
	private String id;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "mineType", nullable = false)
	private String mineType;
	
	@Column(name = "contentType", nullable = false)
	private String contentType;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="file")
	private Set<FileAttr> attrs = new HashSet<FileAttr>();
	
	@Lob 
	@Basic(fetch = FetchType.LAZY)
	@Column(name="data", /*columnDefinition="CLOB",*/ nullable=true) 
	private byte[] data;

    /*	
    @Column(name = "owner")
	private Account owner;
	*/

	@Column(name = "created_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	public Set<FileAttr> getAttrs() {
		return attrs;
	}

	public void setAttrs(Set<FileAttr> attrs) {
		this.attrs = attrs;
	}

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = true)
	private Account createdBy;

	@Column(name = "updated_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@ManyToOne
	@JoinColumn(name = "updated_by", nullable = true)
	private Account updatedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getMineType() {
		return mineType;
	}

	public void setMineType(String mineType) {
		this.mineType = mineType;
	}
	
	
}
