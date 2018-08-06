package com.gy.mapper.imp;

import org.springframework.stereotype.Component;

import com.gy.entity.Attachment;
import com.gy.mapper.IAttachmentDao;
@Component
public class AttachmentDaoImpl extends MyBatisAdapterDaoImpl<Attachment,Long> implements IAttachmentDao{

	@Override
	public int deleteByPrimaryKey(Long id) {
		this.deleteByPrimaryKey(Attachment.class, id);
		return 0;
	}

	@Override
	public Attachment selectByPrimaryKey(Long id) {
		return this.selectByPrimaryKey(Attachment.class,id);
	}

	

}