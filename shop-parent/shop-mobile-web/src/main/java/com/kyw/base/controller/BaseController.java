package com.kyw.base.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.kyw.constants.BaseApiConstants;
import com.kyw.entity.UserEntity;
import com.kyw.fegin.UserFeign;

@Controller
public class BaseController {

	@Autowired
	private UserFeign userFeign;
	
	protected UserEntity getUserEntity(String token) {
		Map<String, Object> userMap = userFeign.getUser(token);
		Integer code = (Integer) userMap.get(BaseApiConstants.HHTP_CODE_NAME);
		if(!code.equals(BaseApiConstants.HHTP_200_CODE)) {
			return null;
		}
		LinkedHashMap linkedHashMap = (LinkedHashMap) userMap.get(BaseApiConstants.HHTP_DATA_NAME);
		String userJson = JSON.toJSONString(linkedHashMap);
		UserEntity user = JSON.parseObject(userJson,UserEntity.class);
		return user;
	}
	
	protected String setError(HttpServletRequest request, String msg, String address) {
		request.setAttribute("error", "注册失败!");
		return address;
	}
}
