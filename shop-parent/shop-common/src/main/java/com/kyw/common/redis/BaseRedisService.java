package com.kyw.common.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class BaseRedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public void setString(String key, String value, Long timeout) {
		set(key, value, timeout);
	}
	public void setString(String key, String value) {
		set(key, value, null);
	}
	
	/**
	 * 向redis里面存储值
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void set(String key, Object value, Long timeout) {
		if (value != null) {
			if (value instanceof String) {
				String setValue = (String) value;
				redisTemplate.opsForValue().set(key, setValue);
			}
			if(timeout != null) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
			}
		}
	}
	
	/**
	 * 获取redis信息
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 删除redis信息
	 * @param key
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}
}
