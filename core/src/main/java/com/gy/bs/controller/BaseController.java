package com.wiair.electric.web.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class BaseController {
	
	public JSONObject defultResponseJson(){
		JSONObject json = new JSONObject();
		json.put("success", true);
		return json;
	}
	
	public void putData(JSONObject respJson,JSON data){
		respJson.put("data", data);
	}
}
