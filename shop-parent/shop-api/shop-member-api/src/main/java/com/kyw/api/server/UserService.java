package com.kyw.api.server;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kyw.entity.UserEntity;

/**
 * 用户服务
 * @author Administrator
 *
 */
@RequestMapping("/member")
public interface UserService {

	/**
	 * 注册服务
	 * @param userEntity
	 * @return
	 */
	@PostMapping("/regist")
	Map<String,Object> regist(@RequestBody UserEntity userEntity);
	
	/**
	 * 用户登录,登录成功后，生成token，然后用token作为ley，userId作为value放入到redis中，返回token给客户端
	 * @return
	 */
	@PostMapping("/login")
	Map<String,Object> login(@RequestBody UserEntity userEntity);
	
	/**
	 * 根据token获取用户信息
	 * @param token
	 * @return
	 */
	@PostMapping("/getUserInfo")
	Map<String,Object> getUser(@RequestParam("token") String token);
}
