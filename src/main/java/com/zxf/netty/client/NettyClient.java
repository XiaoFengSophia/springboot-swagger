package com.zxf.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
@Component
public class NettyClient implements CommandLineRunner{
	private  final Logger log = LoggerFactory.getLogger(NettyClient.class);
	
	private static final String IP_ADDRESS = "127.0.0.1";
	private static final int PORT = 8092;
//	public void start(String ipAddress,int port) {
//		
//    }
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//创建客户端工作组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
	        //非服务端的channel而言
	        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						// TODO Auto-generated method stub
						socketChannel.pipeline().addLast("decoder", new StringDecoder());
				        socketChannel.pipeline().addLast("encoder", new StringEncoder());
				        socketChannel.pipeline().addLast(new NettyClientHandler());
					}
                });
	      //绑定地址
            ChannelFuture future = bootstrap.connect(IP_ADDRESS, PORT).sync();
            log.info("客户端成功....");
            //发送消息
            future.channel().writeAndFlush("你好啊！我是客户端");
            // 等待连接被关闭：主线程执行到这里就 wait 子线程结束，子线程才是真正监听和接受请求的，
            //closeFuture()是开启了一个channel的监听器，负责监听channel是否关闭的状态，
            //如果监听到channel关闭了，子线程才会释放，syncUninterruptibly()让主线程同步等待子线程结果
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
	}

}
