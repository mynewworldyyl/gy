package com.gy.mapper.imp;

import org.springframework.stereotype.Component;

import com.gy.entity.Feedback;
import com.gy.mapper.IFeedbackDao;
@Component
public class FeedbackDaoImpl  extends MyBatisAdapterDaoImpl<Feedback,Long> 
implements IFeedbackDao{

	@Override
	public int deleteByPrimaryKey(Long id) {
		this.deleteByPrimaryKey(Feedback.class, id);
		return 0;
	}

	@Override
	public Feedback selectByPrimaryKey(Long id) {
		return this.selectByPrimaryKey(Feedback.class,id);
	}

}