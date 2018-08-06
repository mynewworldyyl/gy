package com.gy.mapper.imp;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.gy.base.BaseJpalDao;
import com.gy.base.ClientType;

@Component
public class ClientTypeDaoImpl extends BaseJpalDao<ClientType,String>{

	public ClientType getClientTypeByTypeCode(String typecode) {
		ClientType ct = null;
		String sql = "select a from ClientType a where a.typeCode='" + typecode + "'";
		Query q = this.getEntityManager().createQuery(sql);
		ct = (ClientType)q.getSingleResult();
		return ct;
	}
}
