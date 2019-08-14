package com.rammus;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class MainClass {
	@SuppressWarnings("deprecation")
	@Test
	public void shiro() {

		String username = "admin"; // 模拟从客户端接收用户名
		String password = "123"; // 模拟从客户端接收密码

		// 1.加载realm配置文件初始化SecurityManagerFactory
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:realm.ini");
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
		} catch (IncorrectCredentialsException e) {
			System.out.println("认证失败");
		}
		// 用户是否认证成功
		boolean b = subject.isAuthenticated();
		System.out.println("用户是否认证成功--" + b);

		// 判断是否有角色
		boolean hasRole = subject.hasRole("role1");
		System.out.println("has role------" + hasRole);

		// 判断是否有多个角色
		boolean rs = subject.hasAllRoles(Arrays.asList("role1", "role2"));
		System.out.println("has roles------" + rs);

		// 判断是否有权限
		boolean permitted = subject.isPermitted("user:select");
		System.out.println("permitted---------" + permitted);

		// 判断是否有多个权限
		boolean permittedAll = subject.isPermittedAll("user:select", "user:update");
		System.out.println("permittedAll--------" + permittedAll);

		// 退出
		subject.logout();
		boolean b2 = subject.isAuthenticated();
		System.out.println("退出后用户是否认证成功--" + b2);

	}

}
