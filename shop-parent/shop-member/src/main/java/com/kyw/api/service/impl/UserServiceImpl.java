package com.kyw.api.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kyw.api.server.UserService;
import com.kyw.common.api.BaseApiService;
import com.kyw.entity.UserEntity;
import com.kyw.manage.UserServiceManage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserServiceImpl extends BaseApiService implements UserService {

	@Autowired
	private UserServiceManage userServiceManage;
	
	/**
	 * 注册服务
	 */
	@Override
	public Map<String, Object> regist(@RequestBody UserEntity userEntity) {
		if(StringUtils.isEmpty(userEntity.getUserName())) {
			return setResultParamError("用户名不能为空");
		}
		if(StringUtils.isEmpty(userEntity.getPassword())) {
			return setResultParamError("密码不能为空");
		}
		try {
			userServiceManage.regist(userEntity);
			return setResultSuccess();
		} catch (Exception e) {
			log.error("###regist(),ERROR:",e);
			return setResultError("注册失败");
		}
	}

	@Override
	public Map<String, Object> login(@RequestBody UserEntity userEntity) {
		return userServiceManage.login(userEntity);
	}

	@Override
	public Map<String, Object> getUser(@RequestParam("token") String token) {
		if(StringUtils.isEmpty(token))
			return setResultError("token不能为空");
		return userServiceManage.getUser(token);
	}

	@Override
	public Map<String, Object> userLoginWithOpenId(String openid) {
		return null;
	}

}
