package com.kyw.api.server;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/demo")
public interface DemoApiService {

	@GetMapping("/demo")
	Map<String,Object> demo();
	
	@GetMapping("/setkey")
	Map<String,Object> setKey(String key,String value);
	
	@GetMapping("/getkey")
	Map<String,Object> getKey(String key);
	
}
