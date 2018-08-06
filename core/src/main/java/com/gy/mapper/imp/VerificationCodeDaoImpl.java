package com.gy.mapper.imp;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gy.entity.VerificationCode;
import com.gy.mapper.IVerificationCodeDao;
@Component
public class VerificationCodeDaoImpl  extends MyBatisAdapterDaoImpl<VerificationCode,Long> implements IVerificationCodeDao{

	/*@Override
	public int deleteByPrimaryKey(Long id) {
		this.deleteByPrimaryKey(VerificationCode.class, id);
		return 0;
	}*/

	@Override
	public VerificationCode selectLast(String mobile, String typeCode) {
		VerificationCode vcode = (VerificationCode)this.getEntityManager().createNamedQuery("VerificationCodeSelectLast")
				.setParameter("mobile", mobile)
				.setParameter("typeCode", typeCode)
				.getSingleResult();
		return vcode;
	}

	@Override
	public List<VerificationCode> select(String mobile, String typeCode) {
		
		return null;
	}

	
}