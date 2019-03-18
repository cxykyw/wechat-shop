package com.kyw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kyw.base.controller.BaseController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DemoController extends BaseController{

	private static final String INDEX = "index";
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,String token) {
		log.info("my first freemarke.ftl,userName:{}",getUserEntity(token).getUserName());
		return INDEX;
	}
}
