# 项目目录介绍
## com.zxf 项目启动类
## com.zxf.aop AOP案例
    WebLogAspect 基础的一些通知案例
## com.zxf.CGLIBdynamic  Cglib动态代理
## com.zxf.config  配置
    SessionConfig 全局session配置 待完善
    SwaggerConfig swagger配置
    TemplateConfig 邮件模板配置
## com.zxf.controller
    AdivceController  通知
    CityController 验证redis缓存
    MailController 验证发送各种邮件
	NettyController	验证Netty服务端给客户端发消息
    SessionController 验证session 待完善
    SwaggerController  验证swagger管理接口文档
    UploadController 上传文件
## com.zxf.entity
    City 城市实体类
## com.zxf.JDKdynamic JDK动态代理
## com.zxf.mapper  mapper接口
## com.zxf.netty.client	Netty客户端
	NettyClient	Netty 客户端初始化配置
	NettyClientHandler	客户端处理器配置
## com.zxf.netty.server Netty服务端
	NettyServer 服务端初始化配置
	NettyServerHandler 服务端处理器配置
## com.zxf.service 业务逻辑处理接口
    CityService  城市业务逻辑处理接口 redis缓存
## com.zxf.service.impl 业务逻辑处理接口实现
    CityServiceImpl  城市业务逻辑处理接口实现
