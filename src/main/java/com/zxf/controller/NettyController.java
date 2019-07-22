package com.zxf.controller;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zxf.netty.server.NettyServerHandler;
@RestController
public class NettyController {
	
	@Autowired
	private NettyServerHandler nettyServerHandler;
	
	/**
	 * @URL 127.0.0.1:8081/specifiedClient?channelId=
	 * @param ctx
	 * @return
	 * @description 给指定客户端发行消息
	 */
	@RequestMapping("/specifiedClient")
	public String specifiedClient(String channelId,String msg) {
		try {
			nettyServerHandler.channelRead(NettyServerHandler.map.get(channelId), msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	/**
	 * @URL 127.0.0.1:8081/allClient
	 * @param ctx
	 * @return
	 * @description 给指定客户端发行消息
	 */
	@RequestMapping("/allClient")
	public String allClient(String channelId) {
		try {
			while(true) {
				System.out.print("请输入：");
				String msg = new Scanner(System.in).nextLine();
				nettyServerHandler.channelRead0(null,msg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

}
