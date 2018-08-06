package com.gy.web.servlet.file;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.gy.base.CommonException;
import com.gy.base.Response;
import com.gy.base.SpringContext;
import com.gy.base.UserContext;
import com.gy.base.file.DBFileManager;
import com.gy.usercenter.ACAccount;
import com.gy.usercenter.AccountCenterManager;
import com.gy.util.Utils;

@SuppressWarnings("serial")
public class FileUploadServlet extends HttpServlet {

	private final static Logger logger = Logger.getLogger(FileUploadServlet.class);
	
	  public static final String CONTENT_TYPE_HTML = "text/html";
	  public static final String CONTENT_TYPE_JAVASCRIPT = "text/javascript";
	  public static final String CONTENT_TYPE_JSON = "application/json"; // RFC 4627

	  public final static String CHARSET_UTF_8 = "UTF-8";
	  public static final String METHOD_GET = "GET";
	  public static final String METHOD_POST = "POST";
	  
	private ServletFileUpload upload = null;

	public FileUploadServlet() {
		// 为基于硬盘文件的项目集创建一个工厂
		FileItemFactory factory = new DiskFileItemFactory();
		// 创建一个新的文件上传处理器
		upload = new ServletFileUpload(factory);
		
		upload.setHeaderEncoding("UTF-8");
		// 5M 设置附件最大大小，超过这个大小上传会不成功
		upload.setSizeMax(30 * 1024 * 1024);
	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// 解析请求
		String resp = "";
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(request);
			if (items == null) {
				 resp = Utils.getInstance().getResponse(null, false, "NoFileToSave");
			}else {
				AccountCenterManager acManager = SpringContext.getContext()
						.getBean(AccountCenterManager.class);
				String username = request.getHeader("userName");
				if(username == null) {
					for(FileItem i : items) {
						if(i.isFormField()) {
							String name = i.getFieldName();
							if("userName".equals(name)) {
								username = i.getString();
								break;
							}
						}
					}
				}
				
				if(username != null) {
					Response r = acManager.isLogin(username);
					if(!r.isSuccess()) {
						throw new CommonException("NotLogin");
					}
					ACAccount aca = (ACAccount)r.getObj();
					aca.reflesh();
					UserContext.init(aca);
				} else {
					resp = Utils.getInstance().getResponse(null, false, "NotLogin");
				}
				if(UserContext.getCurrentACAccount() != null) {
					 resp = SpringContext.getContext()
								.getBean(DBFileManager.class).processFiles(items);
				}
			}
		} catch (Throwable e) {
			logger.error(e);
			resp = Utils.getInstance().getResponse(null, false, "NoFileToSave");
		}
		response.setContentType(CONTENT_TYPE_HTML);
		response.setCharacterEncoding(CHARSET_UTF_8);
		response.addHeader("Cache-Control",
				"max-age=0, no-cache, must-revalidate, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter w = response.getWriter();
		w.write(resp);
		w.flush();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

}
