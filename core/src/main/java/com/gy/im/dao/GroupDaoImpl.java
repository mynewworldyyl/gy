package com.gy.im.dao;

import org.springframework.stereotype.Repository;

import com.gy.base.BaseJpalDao;
import com.gy.im.models.Group;

@Repository
public class GroupDaoImpl  extends BaseJpalDao<Group,Long> implements IGroupDao {

	@Override
	public void saveGroup(Group group) {
	    this.save(group);
	}

	@Override
	public void removeGroup(Group group) {
		this.remove(Group.class,group.getId());
	}

	@Override
	public Group findGroup(long groupId) {
		return this.find(Group.class, groupId);
	}

	@Override
	public void updateGroup(Group group) {
		this.update(group);
	}

	
}
