package com.zxf.JDKdynamic;
/**
 * RealWork：真实对象
 * 真实对象RealWork实现PrepareWork，实现接口里所有的方法 
 * @author lancoo
 *
 */
public class RealWork implements PrepareWork {

	@Override
	public void openCompare() {
		// TODO Auto-generated method stub
		System.out.println("电脑已成功开启！");

	}

	@Override
	public void LuCode(String language) {
		// TODO Auto-generated method stub
		System.out.println("今天用"+language+"撸代码");

	}

}
