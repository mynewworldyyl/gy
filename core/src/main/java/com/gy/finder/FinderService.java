package com.gy.finder;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.base.i18n.I18NUtils;
import com.gy.util.Utils;

@Path("/fs")
@Component
@Scope("singleton")
@Qualifier("finderService")
public class FinderService {

	@Autowired
	private FacePPService facePPService;
	
	@POST
	@Path("/notifyTaskAdd")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation=Propagation.REQUIRED)
	public String notifyTaskAdd(
			@FormParam("typecode") String typecode,@FormParam("ids") String ids){
		if(ids == null || ids.trim().equals("")) {
			return Utils.getInstance().getResponse(null, false
					,I18NUtils.getInstance().getString("TaskIdNotBeNull"));
		}
		String[] taskIds = ids.split(",");
		long[] taskIntIds = new long[taskIds.length];
		for(int index= 0; index < taskIds.length; index++) {
			taskIntIds[index] = Long.parseLong(taskIds[index]);
		}
		facePPService.startWork(taskIntIds);
		
		return Utils.getInstance().getResponse(null, true, "");
	}
	
	
	@POST
	@Path("/trigerLostInfoMatchNotify")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation=Propagation.REQUIRED)
	public String trigerLostInfoMatchNotify(
			@FormParam("taskId") Long taskId){
		if(taskId == null) {
			return Utils.getInstance().getResponse(null, false
					,I18NUtils.getInstance().getString("TaskIdNotBeNull"));
		}
		facePPService.notifyTaskEnd(taskId);
		return Utils.getInstance().getResponse(null, true, "");
	}
	
}
