package com.zxf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AdivceController {
	@RequestMapping("/beforeAdvice")
	public String beforeAdvice() {
		
		String result = "Before Advice !";
		return result;
	}

}
