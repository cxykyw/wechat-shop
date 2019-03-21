package com.kyw.manage;

import java.util.Map;

import com.kyw.entity.UserEntity;

public interface UserServiceManage {

	/**
	 * 注册服务
	 * @param userEntity
	 */
	public void regist(UserEntity userEntity);
	
	/**
	 * 密码加盐
	 */
	public String MD5WithSalt(String phone,String password);
	
	public Map<String,Object> login(UserEntity userEntity);
	
	public Map<String,Object> getUser(String token);
	
	public Map<String, Object> userLoginWithOpenId(String openid);
}
