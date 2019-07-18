package com.zxf.controller;

import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
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

}
