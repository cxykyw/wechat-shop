package com.kyw.manage.impl;

import java.util.Map;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kyw.common.api.BaseApiService;
import com.kyw.common.redis.BaseRedisService;
import com.kyw.constants.DBTableName;
import com.kyw.constants.MQInterfaceType;
import com.kyw.constants.TokenConstant;
import com.kyw.dao.UserDao;
import com.kyw.entity.UserEntity;
import com.kyw.manage.UserServiceManage;
import com.kyw.mq.producer.RegisterMailboxProducer;
import com.kyw.utils.DateUtils;
import com.kyw.utils.MD5Util;
import com.kyw.utils.TokenUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceManageImpl extends BaseApiService implements UserServiceManage {
	@Autowired
	private UserDao userDao;

	@Autowired
	private RegisterMailboxProducer registerMailboxProducer;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private BaseRedisService baseRedisService;

	@Value("${messages.queue}")
	private String MESSAGE_QUEUE;

	@Override
	public void regist(UserEntity userEntity) {
		userEntity.setCreated(DateUtils.getTimestamp());
		userEntity.setUpdated(DateUtils.getTimestamp());
		String phone = userEntity.getPhone();
		String password = userEntity.getPassword();
		userEntity.setPassword(MD5WithSalt(phone, password));
		userDao.save(userEntity, DBTableName.TABLE_MB_USER);
		// 注册成功发送邮件
		Destination activeMQQueue = new ActiveMQQueue(MESSAGE_QUEUE);

		String json = mailMessage(userEntity.getEmail(), userEntity.getUserName());
		log.info("###regist() 注册发送邮件报文，返回json为:{}", json);
		registerMailboxProducer.send(activeMQQueue, json);
	}

	@Override
	public String MD5WithSalt(String phone, String password) {
		String newPwd = MD5Util.MD5(phone + password);
		return newPwd;
	}

	private String mailMessage(String mail, String userName) {
		// 组装报文格式
		JSONObject root = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", MQInterfaceType.SMS_MAIL);

		JSONObject content = new JSONObject();
		content.put("mail", mail);
		content.put("userName", userName);

		root.put("header", header);
		root.put("content", content);

		return root.toString();
	}

	@Override
	public Map<String, Object> login(UserEntity userEntity) {
		// 先登录。查找用户
		String phone = userEntity.getPhone();
		String password = userEntity.getPassword();
		String newPassword = MD5WithSalt(phone, password);
		UserEntity user = userDao.getUserByPhoneAndPwd(phone, newPassword);
		if (user == null) {
			return setResultError("用户名或密码错误");
		}
		
		String openId = userEntity.getOpenId();
		if(!StringUtils.isEmpty(openId)) {
			userDao.updateUserByOpenId(openId, DateUtils.getTimestamp(), user.getId());
		}
		// 生成token
		String token = setUserToken(user.getId());

		// 返回token
		return setResultSuccessData(token);
	}

	@Override
	public Map<String, Object> getUser(String token) {
		// 从redis中查找
		String userId = (String) baseRedisService.get(token);
		if (StringUtils.isEmpty(userId)) {
			return setResultError("用户已经过期");
		}
		Long newUserId = Long.parseLong(userId);
		UserEntity userInfo = userDao.getUserById(newUserId);
		userInfo.setPassword(null);
		return setResultSuccessData(userInfo);
	}

	@Override
	public Map<String, Object> userLoginWithOpenId(String openid) {
		UserEntity findUser = userDao.findUserByOpenId(openid);
		if (findUser == null) {
			return setResultError("没有关联用户");
		}
		
		//自动登录
		String token = setUserToken(findUser.getId());
		return setResultSuccessData(token);
	}

	private String setUserToken(Long id) {
		String token = tokenUtils.getToken();
		// 将用户的userId存入到redis，key为token
		baseRedisService.set(token, id + "", TokenConstant.TOKEN_TIMEOUT);
		// 返回token
		return token;
	}
}
