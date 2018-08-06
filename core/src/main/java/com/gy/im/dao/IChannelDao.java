package com.gy.im.dao;

import com.gy.base.IBaseDao;
import com.gy.im.models.Channel;

public interface IChannelDao  extends IBaseDao<Channel,Long>{

	void saveChannel(Channel channel);
	void removeChannel(Channel channel);
	Channel findChannel(long channel);
	void updateChannel(Channel channel);
}
