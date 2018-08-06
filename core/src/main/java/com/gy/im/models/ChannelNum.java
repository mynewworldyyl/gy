package com.gy.im.models;

import javax.persistence.Id;

import com.gy.base.id.IDStrategy;

@IDStrategy(prefixValueLen=2)
public class ChannelNum {

	@Id
	private String id;
	
}
