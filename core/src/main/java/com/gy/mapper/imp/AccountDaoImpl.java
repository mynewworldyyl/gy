package com.gy.mapper.imp;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Component;

import com.gy.entity.Account;
import com.gy.mapper.IAccountDao;

@Component
public class AccountDaoImpl extends MyBatisAdapterDaoImpl<Account,Long> implements IAccountDao{

	@Override
	public int deleteByPrimaryKey(Long id) {
		this.deleteByPrimaryKey(Account.class, id);
		return 0;
	}

	@Override
	public Account selectByPrimaryKey(Long id) {
		return this.selectByPrimaryKey(Account.class,id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Account selectByName(String name,Boolean registed) {
		Account db = null;
		try {
			db = this.getEntityManager()
					.createNamedQuery("AccountSelectByName",Account.class)
					.setParameter("acctName", name)
					.setParameter("registed", registed)
					.getSingleResult();
		} catch (NoResultException e) {
			//throw new CommonException("AccountNameNotExist",name);
		}
		return db;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Account> selectAllByName(String name) {
		List<Account> db = null;
		try {
			db = (List<Account>)this.getEntityManager()
					.createNamedQuery("AccountSelectAllByName")
					.setParameter("mobile", name)
					.getResultList();
		} catch (NoResultException e) {
			//throw new CommonException("AccountNameNotExist",name);
		}
		return db;
	}

}