package com.gy.web.filter;

import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest rq=(HttpServletRequest) request;
		HttpServletResponse rp=(HttpServletResponse) response;
		String filePath=rq.getServletPath().replace("\\", "/");
		String dirPath=filePath.substring(0,filePath.lastIndexOf("/")+1);
		String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
//		String newFileName=Long.toString(Env.getContext().getDatabaseId())+"_"+fileName;
		String newFileName=fileName; // by lzj, on 2010-6-10. 去掉帐套ID.
		String newFileRealPath=rq.getServletContext().getRealPath(dirPath+newFileName);
		//清空response的buffer
		rp.reset();
		//设置输出类型
		rp.setContentType("application/octet-stream");
		rp.setHeader("Content-disposition","attachment;filename="+fileName);
		ServletOutputStream out=null;
		try {
			
			RandomAccessFile f=new RandomAccessFile(newFileRealPath,"r");
			byte[] content=new byte[(int)f.length()];
			f.read(content);
			
			out = rp.getOutputStream();
			out.write(content);
			
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("输出文件时发生错误!",e);
		}finally {
			out.close();
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
