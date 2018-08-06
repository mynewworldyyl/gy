package com.gy.mapper;

import java.util.List;

import com.gy.base.IBaseDao;
import com.gy.entity.VerificationCode;

public interface IVerificationCodeDao  extends IBaseDao<VerificationCode,Long>{
    //int insert(VerificationCode record);

    //int insertSelective(VerificationCode record);

    //VerificationCode selectByPrimaryKey(Long id);
    
    public VerificationCode selectLast(String mobile, String typeCode) ;

	public List<VerificationCode> select(String mobile,String typeCode) ;
}