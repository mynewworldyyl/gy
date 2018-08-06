package com.gy.mapper.imp;

import java.io.Serializable;

import com.gy.base.BaseJpalDao;


 abstract class MyBatisAdapterDaoImpl<T,ID extends Serializable>   extends BaseJpalDao<T, ID>{

	public int deleteByPrimaryKey(Class<T> cls,ID id) {
		this.remove(cls, id);
		return 0;
	}

	public int insert(T record) {
		this.save(record);
		return 0;
	}

	public int insertSelective(T record) {
		insert(record);
		return 0;
	}

	public T selectByPrimaryKey(Class<T> cls,ID id) {
		return this.find(cls, id);
	}

	public int updateByPrimaryKeySelective(T record) {
		this.update(record);
		return 0;
	}

	
	public int updateByPrimaryKey(T record) {
		this.update(record);
		return 0;
	}

}