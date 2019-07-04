package com.zxf.CGLIBdynamic;

import java.lang.reflect.Method;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodProxy;
/**
 * 在实际开发中，可能需要对没有实现接口的类增强，用JDK动态代理的方式就没法实现。
 * 采用Cglib动态代理可以对没有实现接口的类产生代理，实际上是生成了目标类的子类来增强
 * @author 赵晓峰
 *
 */
public class TestCglib {
	public static void main(String[] args) {
		RealWork realWork = new RealWork();
		//创建cglib核心对象
		Enhancer enhancer = new Enhancer();
		//设置父类
		enhancer.setSuperclass(realWork.getClass());
		//设置回调
		enhancer.setCallback(new org.springframework.cglib.proxy.MethodInterceptor() {
		    /**
             * 当你调用目标方法时，实质上是调用该方法
             * intercept四个参数：
             * proxy:代理对象
             * method:目标方法
             * args：目标方法的形参
             * methodProxy:代理方法
            */
			@Override
			public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
				// TODO Auto-generated method stub
				System.out.println("场景一： 记录日志 ");
				System.out.println("场景二： 监控方法运行时间 (监控性能)");
				System.out.println("场景三： 权限控制 ");
				System.out.println("场景四： 缓存优化 (第一次调用查询数据库，将查询结果放入内存对象， 第二次调用， 直接从内存对象返回，不需要查询数据库 )");
				System.out.println("场景五： 事务管理 (调用方法前开启事务， 调用方法后提交关闭事务 ) ");
				Object obj = method.invoke(realWork, args);
				return obj;
			}
		});
		//创建代理对象
		RealWork proxyObj = (RealWork) enhancer.create();
		proxyObj.insertInf();
		proxyObj.deleteInf();
		proxyObj.updateInf();
		proxyObj.deleteInf();
	}

}
