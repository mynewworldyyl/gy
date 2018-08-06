package com.gy.bs.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.base.CommonException;
import com.gy.base.file.DBFileManager;
import com.gy.base.file.DbFile;
import com.gy.base.file.FileAttr;
import com.gy.base.i18n.I18NUtils;
import com.gy.util.Utils;
import com.gy.web.servlet.file.DownloadServlet;

//@Controller("userController")
//@RequestMapping("/user")

/**
* 目录结构
*  {主目录}:
*     config.xml //固件配置文件
*     devices  //设备更新文件夹
*             {dc_device_mm.ss.nn.zip} //设备更新文件，mm为主版本号，ss次版本号,nn为微版本号
*     andApps //Android app 更新目录        
*            {dc_android_mm.ss.nn.zip} //设备更新文件，mm为主版本号，ss次版本号,nn为微版本号
*     iosApps //iphone app 更新目录        
*            {dc_iphone_mm.ss.nn.zip} //设备更新文件，mm为主版本号，ss次版本号,nn为微版本号
*
*/

@Path("/update")
@Component
@Scope("singleton")
@Qualifier("update")
public class UpdateService {

	private final static Logger LOG = Logger.getLogger(UpdateService.class);
	
	//主目录
	@Value("#{configProperties['updateDir']}")
	public String updateDir = "C:/Users/ylye/Google 云端硬盘/fd/code/web/src/main/webapp/";
	
	//主目录下面的文件名
	@Value("#{configProperties['configFileName']}")
	public String configFileName = "update/config.xml";
	
	private String androidVersion = null;
	private String androidPath = null;
	
	@Autowired
	private DBFileManager fileManager;
	
	@GET
	@Path("/appInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String deviceInfo() {
		File dir = new File(updateDir + "updates/config.xml");
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new InputStreamReader(new FileInputStream(dir)));
			 StringBuffer sb = new StringBuffer();
			 String line = null;
			 while((line = reader.readLine()) != null) {
				 sb.append(line);
			 }
			 return sb.toString();
		} catch (Exception e) {
			throw new CommonException("NotKnowException");
		}finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new CommonException("NotKnowException");
				}
			}
		}
	}
	
	
	@GET
	@Path("/latestInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String latestInfo(@QueryParam("type") String appType) {
		
		DbFile file = this.fileManager.getLatestFile(appType);
		if(file == null) {
			return Utils.getInstance().getResponse(null, false, 
					I18NUtils.getInstance().getString("noInfo"));
		}
		
		Map<String,String> params = new HashMap<String,String>();
		for(FileAttr fa : file.getAttrs()) {
			params.put(fa.getName(),fa.getValue());
		}
		params.put("path", "/dc/rest/file/dbfile?fileId="+file.getId());
	
		String result = Utils.getInstance().getResponse(params, true, null);
		return result;
	}
	
	
	@GET
	@Path("/appInfo1")
	@Produces(MediaType.APPLICATION_JSON)
	public String appInfo1(/*@PathParam("appType") String appType*/) {
		
		String appType = DownloadServlet.UPDATE_TYPE_ANDROID_APP;
		if(appType == null || "".equals(appType.trim())) {
			throw new CommonException("appTypeError");
		}
		
		if(updateDir == null) {
			throw new CommonException("NotKnowException");
		}
		
		Map<String,String> params = new HashMap<String,String>();
		if(DownloadServlet.UPDATE_TYPE_ANDROID_APP.equals(appType)) {
			if(this.androidVersion == null) {
				getAndroidInfo();
			}
			
			if(this.androidVersion == null) {
				throw new CommonException("NoAppUpdateInfo");
			}
			
			params.put("version", this.androidVersion);
			params.put("version", this.androidVersion);
			params.put("path", this.androidPath);
			
		} else if(DownloadServlet.UPDATE_TYPE_IOS_APP.equals(appType)) {
			getIosInfo();
			
		}else {
			return Utils.getInstance().getResponse(null, false, 
					I18NUtils.getInstance().getString("appTypeError"));
		}
	
		String result = Utils.getInstance().getResponse(params, true, null);
		return result;
	}

	
	private void getIosInfo() {
		
		
	}

	private void getAndroidInfo() {
		
		String appName = null;
		String mdir = null;
		
		mdir = DownloadServlet.ANDROID_DIR;
		File dir = new File(updateDir + mdir);
		if(!dir.exists()) {
			throw new CommonException("NoAppUpdateInfo");
		}
		
		File[] files = dir.listFiles();
		if(files == null || files.length ==0 ) {
			throw new CommonException("NoAppUpdateInfo");
		}
		
		String newstVersion = "";
		
		for(File f : files) {
			String name = f.getName();
			if(!name.endsWith(".apk")) {
				continue;
			}
			String subStr = name.substring(0, name.lastIndexOf(".apk"));
			String nv = getMaxVer(newstVersion,subStr);
			if(nv != null && !nv.equals(newstVersion)) {
				appName = f.getName();
				newstVersion = nv;
			}
		}
		
		this.androidPath = mdir + appName;
		this.androidVersion= newstVersion;
	}


	private String getMaxVer(String newstVersion, String name) {
		String minVer = name.substring(name.lastIndexOf(".")+1, name.length());
		name = name.substring(0, name.lastIndexOf("."));
		String secVer = name.substring(name.lastIndexOf(".")+1, name.length());
		name = name.substring(0, name.lastIndexOf("."));
		String mainVer = name.substring(name.lastIndexOf("_")+1, name.length());
		
		String version = mainVer+"." + secVer + "." + minVer;
		if(version.compareTo(newstVersion) > 0) {
			newstVersion = version;
		}
		return newstVersion;
	}


	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String testService() {
		return "success";
	}
	
}
