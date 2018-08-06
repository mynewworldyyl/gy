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

import com.gy.base.SpringContext;
import com.gy.base.file.DBFileManager;
import com.gy.base.file.DbFile;
import com.gy.util.Utils;

//@WebServlet(urlPatterns="/upload",asyncSupported=true)
public class DownloadDBFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 11L;
	
	private final static Logger logger = Logger.getLogger(DownloadDBFileServlet.class);
   
	public DownloadDBFileServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		//HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rp = (HttpServletResponse) response;
		
		String fileId = request.getParameter("fileId");
		DbFile file = SpringContext.getContext().getBean(DBFileManager.class).getDbFile(fileId);
		if(file == null) {
			String resp = Utils.getInstance().getResponse(null, false, "FileNotFound");
			logger.error(resp);
			return ;
		}
		//清空response的buffer
		rp.reset();
		//设置输出类型
		rp.setContentType("application/octet-stream");
		//rp.setContentType(file.getMineType());
		rp.setHeader("Content-disposition","attachment;filename="+file.getName());
		ServletOutputStream out=null;
		try {
			out = rp.getOutputStream();
			out.write(file.getData());
			out.flush();
		} catch (Throwable e) {
			String resp = Utils.getInstance().getResponse(null, false, "NoFileToSave");
			logger.error(resp,e);
		} finally {
			out.flush();;
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
