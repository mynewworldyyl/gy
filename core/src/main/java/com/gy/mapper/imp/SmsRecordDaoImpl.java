package com.gy.mapper.imp;

import org.springframework.stereotype.Component;

import com.gy.entity.SmsRecord;
import com.gy.mapper.ISmsRecordDao;
@Component
public class SmsRecordDaoImpl  extends MyBatisAdapterDaoImpl<SmsRecord,Long> 
implements ISmsRecordDao{

	@Override
	public int deleteByPrimaryKey(Long id) {
		this.deleteByPrimaryKey(SmsRecord.class, id);
		return 0;
	}

	@Override
	public SmsRecord selectByPrimaryKey(Long id) {
		return this.selectByPrimaryKey(SmsRecord.class,id);
	}

	
}