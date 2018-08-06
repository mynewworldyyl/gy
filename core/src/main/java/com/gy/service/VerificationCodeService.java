package com.gy.service;

import com.gy.entity.VerificationCode;

public interface VerificationCodeService {
   
	void insert(VerificationCode code);
    public VerificationCode selectLast(String mobile,String typeCode);
    
   
}
