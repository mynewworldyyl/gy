package com.gy.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



//@WebFilter(urlPatterns = { "/*" }, asyncSupported = true, description = "Encode UTF-8")
public class EncodingFilter implements Filter {

    private String charset = "UTF-8";

    private final static Logger LOG = Logger.getLogger(EncodingFilter.class);

    public void init(FilterConfig config) throws ServletException {
        String paramCharset = config.getInitParameter("charset");
        if(paramCharset != null && paramCharset.length() > 0) {
            this.charset = paramCharset;
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
    	HttpServletResponse resp = (HttpServletResponse)response;   	
        request.setCharacterEncoding(charset);
        response.setCharacterEncoding(charset);
        chain.doFilter(request, response);
        resp.setHeader("Access-Control-Allow-Origin","*");
        //response.setCharacterEncoding(charset);
    }

    public void destroy() {
    }
}
