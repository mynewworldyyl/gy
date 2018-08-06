package com.gy.im.dao;

import org.springframework.stereotype.Repository;

import com.gy.base.BaseJpalDao;
import com.gy.im.models.Channel;

@Repository
public class ChannelDaoImpl  extends BaseJpalDao<Channel,Long>  implements IChannelDao {

	@Override
	public void saveChannel(Channel channel) {
		// TODO Auto-generated method stub
		 this.save(channel);
	}

	@Override
	public void removeChannel(Channel channel) {
		// TODO Auto-generated method stub
		this.remove(Channel.class, channel.getId());
	}

	@Override
	public Channel findChannel(long channelId) {
		// TODO Auto-generated method stub
		return this.find(Channel.class, channelId);
	}

	@Override
	public void updateChannel(Channel channel) {
		// TODO Auto-generated method stub
		this.update(channel);
	}
	
	
}
