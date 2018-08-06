package com.gy.mapper;

import java.util.List;

import com.gy.base.IBaseDao;
import com.gy.entity.Account;

public interface IAccountDao extends IBaseDao<Account,Long>{
    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    Account selectByName(String name,Boolean registed);
    
    List<Account> selectAllByName(String name);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
    
}