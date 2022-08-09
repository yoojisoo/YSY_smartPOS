package com.ysy.jwt.auth.handler;

import javax.servlet.http.HttpServletResponse;

public class ExptAuthHandler extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public ExptAuthHandler(String msg , HttpServletResponse response)  throws Exception{
		super(msg);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		response.addHeader("errormsg"  , msg);
		
		
	}
	
}
