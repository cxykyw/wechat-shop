package com.kyw.api.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.kyw.api.server.DemoApiService;
import com.kyw.common.api.BaseApiService;
import com.kyw.common.redis.BaseRedisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DemoApiServiceImpl extends BaseApiService implements DemoApiService{

	@Autowired
	private BaseRedisService baseRedisService;
	
	@Override
	public Map<String,Object> demo() {
		log.info("this is demo...");
		return setResultSuccess();
	}

	@Override
	public Map<String, Object> setKey(String key, String value) {
		baseRedisService.setString(key, value);
		return setResultSuccess();
	}

	@Override
	public Map<String, Object> getKey(String key) {
		String value = (String) baseRedisService.get(key);
		return setResultSuccessData(value);
	}

}
