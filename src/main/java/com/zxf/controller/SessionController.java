package com.zxf.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zxf.entity.City;

@RestController
public class SessionController {
	
	@Value("${server.port}")
	private int port;
	/** 
     * @Description http://localhost:8091/getSessionId 
     * @method 获取session id
     * @return 
     */
	@RequestMapping("/getSessionId")
	public String getSessionId(HttpSession session) {
		UUID uid = (UUID) session.getAttribute("uid");
		if(uid == null) {
			uid = UUID.randomUUID();
		}
		System.out.println(uid);
		session.setAttribute("uid", uid);
		return session.getId();
	}
	/** 
     * @Description http://localhost:8091/getCity 
     * @method 
     * @return 
     */
	@RequestMapping("/getCity")
	@Cacheable(value="city-key")
	public City getCity() {
		City city = new City(3,3,"焦作","焦作市");
		System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
		return city;
	}
	/** 
     * @Description http://localhost:8091/login 
     * @method 
     * @return 
     */
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		request.getSession().setAttribute("zhangsan", request.getSession().getId());
		return request.getSession().getId();
	}
	/** 
     * @Description http://localhost:8091/getSession 
     * @method 
     * @return 
     */
	@RequestMapping("/getSession")
	public String getSession(HttpServletRequest request) {
		System.out.println("这是"+port+"端口");
		return request.getSession().getAttribute("zhangsan")+"@@"+port;
	}

}
