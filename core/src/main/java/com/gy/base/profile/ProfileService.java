package com.gy.base.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gy.base.CommonException;
import com.gy.base.UserContext;
import com.gy.base.vos.ProfileVo;
import com.gy.util.JsonUtils;
import com.gy.util.Utils;

@Component
@Path("/profile")
@Scope("singleton")
public class ProfileService {

	@Autowired
	private ProfileManager pm;
	
	@POST
	@Path("/setValue")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public String setValue(@FormParam("mid") String mid,
			@FormParam("key") String key,@FormParam("value") String value ) {
		ProfileVo vo = null;
		try {
			vo = pm.saveOUpdateKeyValue(mid,key,value);
		} catch (CommonException e) {
			return Utils.getInstance().getResponse(null, false, e.getMessage());
		}
		return Utils.getInstance().getResponse(vo, true, null);
	}
	
	@POST
	@Path("/setValues")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public String setValues(@FormParam("mid") String mid,
			@FormParam("values") String json) {
		List<ProfileVo> vos = new ArrayList<ProfileVo>();
		try {
			Map<String,String> kvalues = JsonUtils.getInstance().getStringMap(json, true);
			if(kvalues == null) {
				return Utils.getInstance().getResponse(null, false,"NoValues");
			}
			for(Map.Entry<String, String> e : kvalues.entrySet()) {
				ProfileVo vo = pm.saveOUpdateKeyValue(mid,e.getKey(),e.getValue());
				if(vo != null) {
					vos.add(vo);
				}
			}
		} catch (CommonException e) {
			return Utils.getInstance().getResponse(null, false, e.getMessage());
		}
		return Utils.getInstance().getResponse(vos, true, null);
	}
	
	
	@POST
	@Path("/setContent")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public String setContent(@FormParam("mid") String mid,
			@FormParam("key") String key,@FormParam("content") String content ) {
		ProfileVo vo = null;
		try {
			vo = pm.saveOUpdateContent(mid, key, content);
		} catch (CommonException e) {
			return Utils.getInstance().getResponse(null, false, e.getMessage());
		}
		return Utils.getInstance().getResponse(vo, true, null);
	}
	
	
	@POST
	@Path("/setData")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public String setData(@FormParam("mid") String mid,
			@FormParam("key") String key, @FormParam("data") String bsdata ) {
		ProfileVo vo = null;
		byte[] bdata = null;
		try {
			vo = pm.saveOUpdateBinary(mid, key, bdata);
		} catch (CommonException e) {
			return Utils.getInstance().getResponse(null, false, e.getMessage());
		}
		return Utils.getInstance().getResponse(vo, true, null);
	}
	
	
	@GET
	@Path("/getProfile")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public String getProfile() {
		List<Profile> pl = pm.getProfile(UserContext.getAccount().getUserName());
		if(pl == null || pl.isEmpty()) {
			return Utils.getInstance().getResponse(null, false, "NotAnyProfieFound");
		}
		Map<String,Map<String,ProfileVo>> ps = new HashMap<String,Map<String,ProfileVo>>();
		
		for(Profile p : pl) {
			Map<String,ProfileVo> modelProfile = ps.get(p.getModelId());
			if(modelProfile == null) {
				modelProfile = new HashMap<String,ProfileVo>();
				ps.put(p.getModelId(), modelProfile);
			}
			modelProfile.put(p.getKey(), pm.entityToVo(p));
		}
		return Utils.getInstance().getResponse(ps, true, null);
	}
	
}
