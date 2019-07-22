package com.zxf.netty.server;

import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

@Component
public class NettyServer implements CommandLineRunner {
	private  final Logger log = LoggerFactory.getLogger(NettyServer.class);
	
	private static final int PORT = 8092;
//	public void start(int port) {
//		
//    }
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//NioEventLoopGroup是一个处理I/O操作的多线程事件循环
				//Netty内部都是通过线程在处理各种数据，EventLoopGroup就是用来管理调度他们的，注册Channel，管理他们的生命周期。
				
		        //new 一个主线程组    负责接收客户端的连接 bossGroup只负责接收客户端的连接，不做复杂操作，为了减少资源占用，取值越小越好
		        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		        //new 一个工作线程组   workGroup作为worker，处理boss接收的连接的流量和将接收的连接注册进入这个worker
		        EventLoopGroup workGroup = new NioEventLoopGroup(200);
		        try {
			        //ServerBootstrap负责建立服务端
			        ServerBootstrap bootstrap = new ServerBootstrap();
			        bootstrap.group(bossGroup, workGroup)
		        		  //指定使用NioServerSocketChannel产生一个Channel用来接收连接
		        		 .channel(NioServerSocketChannel.class)
		        		 //ChannelInitializer用于配置一个新的Channel
		        		 //用于向你的Channel当中添加ChannelInboundHandler的实现
		                 .childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							// TODO Auto-generated method stub
							 //添加编解码
		                    socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		                    socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		                    //服务器的逻辑
		                    socketChannel.pipeline().addLast(new NettyServerHandler());
						}
		            })
		                 .localAddress(new InetSocketAddress(PORT))
		                 //设置队列大小
		                 //Option是为了NioServerSocketChannel设置的，用来接收传入连接的
		                 .option(ChannelOption.SO_BACKLOG, 1024)
		                 //是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活。
		     			 //childOption是用来给父级ServerChannel之下的Channels设置参数的
		                 .childOption(ChannelOption.SO_KEEPALIVE, true);
		        
		        	//绑定端口,开始接收进来的连接
		            ChannelFuture future = bootstrap.bind().sync();
		            //log.info("服务器启动开始监听端口: {}", socketAddress.getPort());
		            future.channel().closeFuture().sync();
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        } finally {
		            //关闭主线程组
		            bossGroup.shutdownGracefully();
		            //关闭工作线程组
		            workGroup.shutdownGracefully();
		        }
	}

}
