package com.gy.im.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.base.id.ICacheIDGenerator;
import com.gy.entity.Account;
import com.gy.im.dao.IChannelDao;
import com.gy.im.models.Group;

@Component
public class ImManager {

	@Autowired
	private IChannelDao dao;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	public Group newGroup(String groupName,Account act){
		Group g = new Group();
		g.setId(this.generator.getNumId(Group.class).longValue());
		if(groupName != null) {
			g.setDesc(groupName);
			g.setName(groupName);
		}
		g.setClient(act.getClient());
		g.setOwner(act);
		g.setCreatedBy(act);
		g.setUpdatedBy(act);
		dao.getEntityManager().persist(g);
		return g;
	}
}
