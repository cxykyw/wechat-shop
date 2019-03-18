package com.kyw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DemoController {

	private static final String INDEX = "index";
	
	@RequestMapping("/index")
	public String index() {
		log.info("my first freemarke.ftl");
		return INDEX;
	}
}
