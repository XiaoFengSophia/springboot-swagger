package com.zxf.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	private  final Logger log = LoggerFactory.getLogger(NettyClientHandler.class);
	
	/**
	 * 与服务端建立连接后netty会自动调用
	 */
 	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
 		System.out.println("客户端channel："+ctx.channel());
        log.info("连接服务端成功 .....");
    }
 	/**
     * 收到服务端传过来的数据后，netty会自动调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //log.info("客户端收到消息: {}", msg.toString());
    	System.out.println("收到服务端消息: "+msg.toString());
    	//ByteBuf byteBuf = (ByteBuf) msg;
    }
    /**
     * 发生异常时netty自动调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("网络异常。。。。。。");
    	cause.printStackTrace();
        ctx.close();
    }
    
}