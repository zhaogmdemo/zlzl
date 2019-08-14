package com.rammus;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class MainClass2 {
	@SuppressWarnings("deprecation")
	@Test
	public void shiro() {

		String username = "admin"; // 模拟从客户端接收用户名
		String password = "123"; // 模拟从客户端接收密码

		// 1.加载realm配置文件初始化SecurityManagerFactory
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:realm2.ini");
		// 2.通过工厂获取对象
		SecurityManager sm = factory.getInstance();

		// 3.通过SecurityUtils把SecurityManager对象放到运行环境中
		SecurityUtils.setSecurityManager(sm);
		// 4.通过SecurityUtils获取Subject对象
		Subject subject = SecurityUtils.getSubject();
		// 5.根据用户登录信息创建token
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		// 6.认证，认证失败抛异常
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			System.out.println("认证失败");
		}
		// 用户是否认证成功
		boolean b = subject.isAuthenticated();
		System.out.println("用户是否认证成功--" + b);

		// 判断是否有权限
		boolean permitted = subject.isPermitted("select");
		System.out.println("permitted---------" + permitted);
		// 判断用户是否有多个权限
		boolean permitted2 = subject.isPermittedAll("select", "update");
		System.out.println("判断用户是否有多个权限" + permitted2);

		// 退出
		subject.logout();
		boolean b2 = subject.isAuthenticated();
		System.out.println("退出后用户是否认证成功--" + b2);

	}

}
