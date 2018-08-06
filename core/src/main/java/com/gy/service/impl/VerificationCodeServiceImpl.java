package com.gy.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.base.id.ICacheIDGenerator;
import com.gy.entity.VerificationCode;
import com.gy.mapper.IVerificationCodeDao;
import com.gy.service.VerificationCodeService;

@Component
public class VerificationCodeServiceImpl implements VerificationCodeService{
	private final static Logger LOG = Logger.getLogger(VerificationCodeServiceImpl.class);

	@Autowired
	private IVerificationCodeDao mapper;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	@Override
	public void insert(VerificationCode code) {
		if(code.getId() == null || code.getId().longValue() == 0){
			code.setId(generator.getNumId(VerificationCode.class).longValue());
		}
		mapper.save(code);
	}

	
	@Override
	public VerificationCode selectLast(String mobile, String typeCode) {
		return mapper.selectLast(mobile, typeCode);
	}

}
