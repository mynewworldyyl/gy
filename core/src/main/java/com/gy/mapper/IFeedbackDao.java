package com.gy.mapper;

import com.gy.base.IBaseDao;
import com.gy.entity.Feedback;

public interface IFeedbackDao  extends IBaseDao<Feedback,Long>{
    int deleteByPrimaryKey(Long id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);
}