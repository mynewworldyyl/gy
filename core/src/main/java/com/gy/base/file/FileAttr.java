package com.gy.base.file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gy.base.Client;
import com.gy.base.id.IDStrategy;

@Entity
@Table(name = "t_file_attr")
@IDStrategy
public class FileAttr implements Cloneable{

	@Id
	@Column(length = 64)
	private String id;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "file_id", nullable = false)
	private DbFile file;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "value")
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DbFile getFile() {
		return file;
	}

	public void setFile(DbFile file) {
		this.file = file;
	}

	@Override
	public FileAttr clone() throws CloneNotSupportedException {
		FileAttr fa = (FileAttr) super.clone();
		fa.setClient(this.client);
		fa.setFile(null);
		fa.setName(this.name);
		fa.setValue(this.value);
		return fa;
	}
	
	
}
