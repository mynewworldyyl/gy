package com.gy.base.mall;

import javax.persistence.Id;

import com.gy.base.id.IDStrategy;

@IDStrategy
public class OrderNumId{

	@Id
	private String id;
	
}
