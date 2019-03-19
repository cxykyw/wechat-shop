package com.kyw.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kyw.base.controller.BaseController;
import com.kyw.constants.BaseApiConstants;
import com.kyw.constants.TokenConstant;
import com.kyw.entity.UserEntity;
import com.kyw.fegin.UserFeign;
import com.kyw.utils.CookieUtil;

@Controller
public class LoginController extends BaseController{

	private static final String LOGIN = "login";
	private static final String INDEX = "index";
	
	@Autowired
	private UserFeign userFegin;
	
	@RequestMapping("/locaLogin")
	public String locaLogin() {
		return LOGIN;
	}
	
	@RequestMapping("/login")
	public String login(UserEntity userEntity,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> login = userFegin.login(userEntity);
		Integer code = (Integer) login.get(BaseApiConstants.HHTP_CODE_NAME);
		if (!BaseApiConstants.HHTP_200_CODE.equals(code)) {
			String msg = (String) login.get(BaseApiConstants.HHTP_MSG_NAME);
			return setError(request, msg, LOGIN);
		}
		//登录成功获取token的值，放入到cookie中
		String token = (String) login.get(BaseApiConstants.HHTP_DATA_NAME);
		CookieUtil.addCookie(response, TokenConstant.USER_TOKEN, token, TokenConstant.COOKIE_TIMEOUT);
		
		return INDEX;
		
	}
}


