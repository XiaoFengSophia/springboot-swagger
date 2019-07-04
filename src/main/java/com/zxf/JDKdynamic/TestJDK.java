package com.zxf.JDKdynamic;

import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/**
 * 测试类
 * @author lancoo
 *
 */
public class TestJDK {
	public static void main(String[] args) {
		// 1.要代理的真实对象
		RealWork prepareWork = new RealWork();//
		
		
		/**
		 * 3.通过Proxy 的
		 * 第一个参数：handler.getClass().getClassLoader() 代理类的类加载器
		 * 第二个参数：realWork.getClass().getInterfaces() 代理类要实现的接口列表，
		 * 		          表示我要代理该真实对象，这样就乐意调用接口中的方法了
		 * 第三个参数：回调，是一个InvocationHandler接口的实现对象，当调用代理对象的方法时，执行的是回调中的invoke方法
		 */
		PrepareWork proxy = (PrepareWork) Proxy.newProxyInstance(prepareWork.getClass().getClassLoader(), 
				prepareWork.getClass().getInterfaces(), new InvocationHandler() {
			
			// proxy:表示我们代理的真实对象
			// method:表示调用真实对象中的某个方法的metode对象
			// args:调用真实对象方法里所传的参数
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// TODO Auto-generated method stub
				//在代理真实对象前我们可以添加一些自己的操作
				System.out.println("今天是五一假期前的最后一天了！");
				System.out.println("方法名："+method.getName());
				//目标方法调用
				Object obj = method.invoke(prepareWork, args);
				System.out.println(method + "执行完毕！");
				return obj;
			}
		});
		System.out.println(proxy.getClass().getName());
		proxy.openCompare();
		proxy.LuCode("JAVA");
	}
	
}
