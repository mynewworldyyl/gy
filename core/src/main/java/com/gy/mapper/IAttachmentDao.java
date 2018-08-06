package com.gy.mapper;

import com.gy.base.IBaseDao;
import com.gy.entity.Attachment;

public interface IAttachmentDao   extends IBaseDao<Attachment,Long>{
    int deleteByPrimaryKey(Long id);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    Attachment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);
}