package com.gy.web.servlet.file;

import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.gy.base.CommonException;
import com.gy.util.Utils;

//@WebServlet(urlPatterns="/upload",asyncSupported=true)
public class DownloadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public static final String VERSION_CONFIG_FILE_NAME = "config.xml";
	public static final String VERSION_CONFIG_FILE_KEY = "__VERSION_CONFIG";
	
	public static final String UPDATE_TYPE = "updateType";
	public static final String UPDATE_TYPE_ANDROID_APP = "androidApp";
	public static final String UPDATE_TYPE_IOS_APP = "IOSApp";
	public static final String UPDATE_TYPE_DEVICE = "device";
	
	public static final String DEVICES_DIR = "update/devices/";
	public static final String ANDROID_DIR = "update/andApps/";
	public static final String IOS_DIR = "update/iosApps/";
	
	private final static Logger logger = Logger.getLogger(DownloadServlet.class);
   
	public DownloadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rp = (HttpServletResponse) response;
		
		String filePath = rq.getServletPath().replace("\\", "/");
		String dirPath = filePath.substring(0,filePath.lastIndexOf("/")+1);
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
        //String newFileName=Long.toString(Env.getContext().getDatabaseId())+"_"+fileName;
		//String newFileName = fileName; // by lzj, on 2010-6-10. 去掉帐套ID.
		
		/*String updateType = rq.getParameter(UPDATE_TYPE);
		if(updateType != null && VERSION_CONFIG_FILE_NAME.equals(UPDATE_TYPE_DEVICE)) {
			//下载版本配置文件
			dirPath = defaultDir;
			fileName = configFileName;
		}else if(updateType != null && VERSION_CONFIG_FILE_NAME.equals(UPDATE_TYPE_APP)) {
			//下载版本配置文件
			dirPath = this.defaultDir;
			fileName = this.configFileName;
		}*/
	
		String newFileRealPath = rq.getServletContext().getRealPath(dirPath+fileName);
		
		//清空response的buffer
		rp.reset();
		//设置输出类型
		rp.setContentType("application/octet-stream");
		rp.setHeader("Content-disposition","attachment;filename="+fileName);
		ServletOutputStream out=null;
		try {
			RandomAccessFile f = new RandomAccessFile(newFileRealPath,"r");
			byte[] content=new byte[(int)f.length()];
			f.read(content);
			out = rp.getOutputStream();
			out.write(content);
			out.flush();
		} catch (Throwable e) {
			String resp = Utils.getInstance().getResponse(null, false, "NoFileToSave");
			logger.error(resp,e);
		} finally {
			out.close();
		}		
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		/*defaultDir = config.getInitParameter("defaultDir");
		if(defaultDir == null || "".equals(defaultDir.trim())) {
			throw new CommonException("Must set param defaultDir value for Servlet: " + this.getClass().getName());
		}
		configFileName = config.getInitParameter("configFileName");
        if(configFileName == null || "".equals(configFileName.trim())) {
        	throw new CommonException("Must set param configFileName value Servlet: " + this.getClass().getName());
		}*/
	}

}
