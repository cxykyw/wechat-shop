package com.kyw.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kyw.base.controller.BaseController;
import com.kyw.constants.BaseApiConstants;
import com.kyw.constants.TokenConstant;
import com.kyw.entity.UserEntity;
import com.kyw.fegin.UserFeign;
import com.kyw.utils.CookieUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;

@Controller
public class LoginController extends BaseController {

	private static final String LOGIN = "login";
	private static final String INDEX = "index";
	private static final String ERROR = "error";
	private static final String ASSOCIATEDACCOUNT = "associatedAccount";

	@Autowired
	private UserFeign userFegin;

	@RequestMapping("/locaLogin")
	public String locaLogin(String source,HttpServletRequest request) {
		request.setAttribute("source", source);
		return LOGIN;
	}

	@RequestMapping("/login")
	public String login(UserEntity userEntity, String source, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		if (StringUtils.isEmpty(source) && TokenConstant.USER_SOURCE_QQ.equals(source)) {
			String openid = (String) session.getAttribute(TokenConstant.USER_OPEN_ID);
			userEntity.setOpenId(openid);
		}

		Map<String, Object> login = userFegin.login(userEntity);
		Integer code = (Integer) login.get(BaseApiConstants.HHTP_CODE_NAME);
		if (!BaseApiConstants.HHTP_200_CODE.equals(code)) {
			String msg = (String) login.get(BaseApiConstants.HHTP_MSG_NAME);
			return setError(request, msg, LOGIN);
		}
		// 登录成功获取token的值，放入到cookie中
		String token = (String) login.get(BaseApiConstants.HHTP_DATA_NAME);
		CookieUtil.addCookie(response, TokenConstant.USER_TOKEN, token, TokenConstant.COOKIE_TIMEOUT);

		return INDEX;

	}

	@RequestMapping("/authorizeUrl")
	public String authorizeUrl(HttpServletRequest request) throws QQConnectException {
		String url = new Oauth().getAuthorizeURL(request);

		return "redirect:" + url;
	}

	@RequestMapping("/qqLoginCallback")
	public String qqLoginCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws QQConnectException {
		// 1.获取授权码
		// 2.获取accessToken
		AccessToken accessTokenObject = new Oauth().getAccessTokenByRequest(request);
		String accessToken = accessTokenObject.getAccessToken();
		if (StringUtils.isEmpty(accessToken)) {
			return setError(request, "QQ授权失败！！！", ERROR);
		}
		// 3.获取openid
		OpenID openIDObj = new OpenID(accessToken);
		String userOpenID = openIDObj.getUserOpenID();

		// 4.查找openid是否关联，如果没有的话先跳转到关联界面，否则直接登录
		Map<String, Object> userLogin = userFegin.userLoginWithOpenId(userOpenID);
		Integer code = (Integer) userLogin.get(BaseApiConstants.HHTP_CODE_NAME);
		if (BaseApiConstants.HHTP_200_CODE.equals(code)) {
			String token = (String) userLogin.get(BaseApiConstants.HHTP_DATA_NAME);
			CookieUtil.addCookie(response, TokenConstant.USER_TOKEN, token, TokenConstant.COOKIE_TIMEOUT);
			return "redirect:/" + INDEX;
		}

		// 没有关联就跳页
		session.setAttribute(TokenConstant.USER_OPEN_ID, userOpenID);
		return ASSOCIATEDACCOUNT;
	}
}
