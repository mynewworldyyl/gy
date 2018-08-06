package com.gy.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gy.base.Response;

public class JsonUtils {

	private static JsonUtils instance = null;
	private static GsonBuilder builder = null;
	
	private JsonUtils() {
		
	}
	public synchronized static JsonUtils getInstance() {
		if(instance == null) {
			instance = new JsonUtils();
		}
		return instance;
	}
	
	public GsonBuilder builder() {
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		//builder.registerTypeAdapter(MsgHeader.class, new MessageHeaderAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		return builder;
	}
	
	public<T> T fromJson(String json, java.lang.reflect.Type c) {
		return this.builder().create().fromJson(json, c);
	}
	
	public<T> T fromJson(String json, Class<T> c,boolean fixBug,boolean innerJson) {
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		if(innerJson) {
			json = this.processFromJson(json);
		}
		if(fixBug) {
			json = this.modifyGjonBug(json);
		}
		T obj = builder.create().fromJson(json, c);
		try {
			obj = (T)processResponse(obj);
		} catch (Throwable e) {
		}
		return obj;
	}
	
	public<T> T fromJson(String json, java.lang.reflect.Type type,boolean fixBug,boolean innerJson) {
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		if(innerJson) {
			json = this.processFromJson(json);
		}
		if(fixBug) {
			json = this.modifyGjonBug(json);
		}
		T obj = builder.create().fromJson(json, type);
		try {
			obj = (T)processResponse(obj);
		} catch (Throwable e) {
		}
		return obj;
	}
	
	public Map<String,String> getStringMap(String json,boolean innerJson) {
		Type type = new TypeToken<HashMap<String,String>>(){}.getType();
		Map<String,String> m = this.fromJson(json, type, false, innerJson);
		return m;
	}
	
	public List<String> getStringValueList(String json,boolean innerJson) {
		Type type = new TypeToken<List<String>>(){}.getType();
		List<String> m = this.fromJson(json, type, false, innerJson);
		return m;
	}
	
	private Object processResponse(Object resp) {
		if(!(resp instanceof Response)) {
			return resp;
		}
		Response r = (Response)resp;
		if(r.getClassType() == null || "".equals(r.getClassType().trim())) {
			return resp;
		}
		Class cls = null;
		try {
			cls = JsonUtils.class.getClassLoader().loadClass(r.getClassType());
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		}
		if(cls == null) {
			return r;
		}
		Object dataObject = fromJson(r.getData(), cls,false,true);
		r.setObj(dataObject);
		return r;
	}
	
	public String toJson(Object obj,boolean innerJson) {
		String json = null;
		if(obj instanceof JSON) {
			json = ((JSON)obj).toJSONString();
		}else {
			GsonBuilder builder = new GsonBuilder();
			//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
			//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
			json = builder.create().toJson(obj);
		}

		if(innerJson) {
			json = this.processToJson(json);
		}
		return json;
	}
	
	public String toJson(Object obj,java.lang.reflect.Type type,boolean innerJson) {
		GsonBuilder builder = new GsonBuilder();
		//builder.registerTypeAdapter(MessageType.class, new MessageTypeAdapter());
		//builder.registerTypeAdapter(MessageState.class, new MessageStateAdapter());
		String json = builder.create().toJson(obj,type);
		if(innerJson) {
			json = this.processToJson(json);
		}
		return builder.create().toJson(obj,type);
	}
	
	 private String processToJson(String json) {
	    	json = json.replaceAll("\\{", "(");
	    	json = json.replaceAll("\\}", ")");
	    	json = json.replaceAll("\"", "@@");
	    	return json;
	 }
	 
	 private String processFromJson(String json) {
	    	json = json.replaceAll("\\(","\\{");
	    	json = json.replaceAll("\\)","\\}");
	    	json = json.replaceAll( "@@","\"");
	    	return json;
	 }
	 
	 private String modifyGjonBug(String content) {
			content=content.substring(1, content.length()-1);
			content = content.replaceAll("\\\\", "");
			return content;
		}
}
