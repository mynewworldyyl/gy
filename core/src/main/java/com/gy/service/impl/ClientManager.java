package com.gy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.base.Client;
import com.gy.mapper.IClientDao;

@Component
public class ClientManager {

	public static final String DEFAULT_CLIENT_ID="2";
	
	@Autowired
	private IClientDao clientDao;
	
	private Client defaultClient=null;
	
	public Client getDefaultClient() {
		if(null != defaultClient) {
			return this.defaultClient;
		}
		defaultClient = clientDao.find(Client.class, DEFAULT_CLIENT_ID);
		return this.defaultClient;
	}
	
}
