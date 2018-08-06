package com.gy.mapper;

import com.gy.base.IBaseDao;
import com.gy.entity.SmsRecord;

public interface ISmsRecordDao  extends IBaseDao<SmsRecord,Long>{
    int deleteByPrimaryKey(Long id);

    int insert(SmsRecord record);

    int insertSelective(SmsRecord record);

    SmsRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsRecord record);

    int updateByPrimaryKey(SmsRecord record);
}