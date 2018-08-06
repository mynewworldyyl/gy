package com.gy.im.dao;

import com.gy.base.IBaseDao;
import com.gy.im.models.Group;

public interface IGroupDao  extends IBaseDao<Group,Long>{

	void saveGroup(Group group);
	void removeGroup(Group group);
	Group findGroup(long groupId);
	void updateGroup(Group group);
}
