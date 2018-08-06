package com.gy.base;

import java.io.Serializable;

import javax.persistence.EntityManager;



public interface IBaseDao<T,ID extends Serializable> {

	void save(T entity);
	
	void remove(Class<T> cls, ID id);
	
	T update(T entity);
	
	T find(Class<T> cls, ID id);
	
	EntityManager getEntityManager();
	
}
