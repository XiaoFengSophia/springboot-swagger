package com.zxf.netty.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
@Service
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	private  final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);
	/**
	 * @description 静态map，用来存放ChannelHandlerContext 实例，以便在给指定客户端发送消息
	 * @param String 用来存放与服务端建立连接的channelId
	 * @param ChannelHandlerContext 用来存放与服务端建立连接的ChannelHandlerContext实例
	 */
	public static Map<String,ChannelHandlerContext> map = new ConcurrentHashMap<String,ChannelHandlerContext>();
	 //保留所有与服务器建立连接的channel对象
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
   /**
    * channelActive()方法将会在连接被建立并且准备进行通信时被调用
    * @paramctx
    */
   @Override
   public void channelActive(ChannelHandlerContext ctx) throws Exception {
       log.info("Channel active......");
       System.out.println("客户端："+ctx.channel().remoteAddress()+"上线了");
       System.out.println("id:"+ctx.channel().id().toString());
       if(! map.containsKey(ctx.channel().id().toString())) {
    	   map.put(ctx.channel().id().toString(), ctx);
       }
   }
   /**
    * 客户端下线通知
    */
   @Override
   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
       Channel channel = ctx.channel();
       System.out.println(channel.remoteAddress() +" 下线了");
   }
 

   /**
    * @说明 客户端发消息会触发，在收到客户端传过来的消息的时候，netty会自动调用该方法
    * @param ctx 提供各种不同的操作用于触发不同的I/O时间和操作
    * 调用write方法来逐字返回接收到的信息
    * 只调用write是不会释放的，它会缓存，直到调用flush
    * 可以直接使用writeAndFlush(msg)
    * @description 此方法为给指定客户端推送消息
    */
   @Override
   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	   System.out.println("接收到客户端消息："+msg);
	   channelGroup.forEach(System.out::println);
	   channelGroup.forEach(ch -> {
		   System.out.println(ch.id());
	   });
	   //给指定客户端推送消息
	   if(ctx != null && map.get(ctx.channel().id().toString()).equals(ctx)) {
		   ChannelFuture future = ctx.writeAndFlush(msg, ctx.channel().newPromise());
		   future.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
				if(future.isSuccess()) {
					System.out.println("给"+future.channel().remoteAddress()+"发送消息成功！");
				}else {
					System.out.println("消息发送失败！");
				}
			}
		});
	   }
   }
   /**
    * 此方法为给所有连接到服务端的客户端推行消息
    * @说明 给所有客户端推送消息
    */
   public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
	   channelGroup.forEach(System.out::println);
	   //给所有客户端推送消息
	   channelGroup.forEach(ch -> {
	   System.out.println(ch.id());
       ch.writeAndFlush(msg+" \n");
       });
   }

   /**
    * 发生异常时触发此消息
    */
   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
   }
 //表示服务端与客户端连接建立
   @Override
   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
       Channel channel = ctx.channel();  //其实相当于一个connection
       /**
        * 调用channelGroup的writeAndFlush其实就相当于channelGroup中的每个channel都writeAndFlush
        * 先去广播，再将自己加入到channelGroup中
        */
       channelGroup.writeAndFlush(" 【服务器】 -" +channel.remoteAddress() +" 加入\n");
       channelGroup.add(channel);
   }
   
}
