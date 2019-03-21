package com.kyw.constants;

public interface TokenConstant {

	//用户会话保存30天
	Long TOKEN_TIMEOUT = 60*60*24*7l;
	String USER_TOKEN = "token";
	
	int COOKIE_TIMEOUT = 1000*60*60*24*7;
	
	String USER_OPEN_ID = "openid";
	
	String USER_SOURCE_QQ = "qq";
}
