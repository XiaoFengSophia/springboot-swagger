package com.zxf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
	
	/**
	 * @url http://localhost:8097/admin/hello
	 * @return
	 */
	@GetMapping("/admin/hello")
	public String admin() {
		
		return "hello admin !";
	}
	/**
	 * @url http://localhost:8097/user/hello
	 * @return
	 */
	@GetMapping("/user/hello")
	public String user() {
		
		return "hello user !";
	}
	/**
	 * @url http://localhost:8097/db/hello
	 * @return
	 */
	@GetMapping("/db/hello")
	public String dba() {
		
		return "hello dba !";
	}
	/**
	 * @url http://localhost:8097/hello
	 * @return
	 */
	@GetMapping("/hello")
	public String hello() {
		
		return "This is hello !";
	}
	
	public String login() {
		
		return "login success !";
	}
	

}
