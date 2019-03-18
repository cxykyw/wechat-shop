package com.kyw.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kyw.base.controller.BaseController;
import com.kyw.constants.BaseApiConstants;
import com.kyw.entity.UserEntity;
import com.kyw.fegin.UserFeign;

@Controller
public class RegistController extends BaseController {

	private static final String LOCAREGIST = "locaRegist";
	private static final String LOGIN = "login";

	@Autowired
	private UserFeign userFeign;

	@RequestMapping("/locaRegist")
	public String locaRegist() {
		return LOCAREGIST;
	}
	
	@RequestMapping("/regist")
	public String regist(UserEntity userEntity, HttpServletRequest request) {
		try {
			Map<String, Object> registMap = userFeign.regist(userEntity);
			Integer code = (Integer) registMap.get(BaseApiConstants.HHTP_CODE_NAME);
			if (!BaseApiConstants.HHTP_200_CODE.equals(code)) {
				String msg = (String) registMap.get(BaseApiConstants.HHTP_MSG_NAME);
				return setError(request, msg, LOCAREGIST);
			}
			
			//注册成功
			return LOGIN;
		} catch (Exception e) {
			return setError(request, "注册失败!", LOCAREGIST);
		}
	}

}
