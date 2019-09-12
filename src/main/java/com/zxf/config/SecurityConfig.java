package com.zxf.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
		.antMatchers("/admin/**")
		.hasRole("ADMIN")
		.antMatchers("/user/**")
		.access("hasAnyRole('ADMIN','USER')")
		.antMatchers("/db/**")
		.access("hasRole('ADMIN') and hasRole('DBA')")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		//.loginPage("/login_page")
		.loginProcessingUrl("/login")
		.usernameParameter("name")
		.passwordParameter("passwd")
		.successHandler(new AuthenticationSuccessHandler() {//登陆成功
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
					throws IOException, ServletException {
				// TODO Auto-generated method stub
				response.setContentType("application/json;charset=utf-8");
				Object principal = auth.getPrincipal();
				PrintWriter out = response.getWriter();
				response.setStatus(200);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("status", 200);
				map.put("msg", principal);
				ObjectMapper om = new ObjectMapper();
				out.write(om.writeValueAsString(map));
				out.flush();
				out.close();
				
			}
		})
		.failureHandler(new AuthenticationFailureHandler() {//登陆失败
			
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
					throws IOException, ServletException {
				// TODO Auto-generated method stub
				response.setContentType("application/json;charset=utf-8");
				PrintWriter out = response.getWriter();
				response.setStatus(401);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("status", 401);
				if(authException instanceof LockedException) {
					map.put("msg", "账户被锁定，登录失败");
				}else if(authException instanceof BadCredentialsException) {
					map.put("msg", "账户名或密码输入错误，登录失败");
				}else if(authException instanceof DisabledException) {
					map.put("msg", "账户被禁用，登录失败");
				}else if(authException instanceof AccountExpiredException) {
					map.put("msg", "账户已过期，登录失败");
				}else if(authException instanceof CredentialsExpiredException) {
					map.put("msg", "密码已过期，登录失败");
				}else {
					map.put("msg", "登录失败");
				}
				ObjectMapper om = new ObjectMapper();
				out.write(om.writeValueAsString(map));
				out.flush();
				out.close();
			}
		})
		.permitAll()
		.and()
		.csrf()
		.disable();
	}

	//	  @Bean
//	  PasswordEncoder passwordEncoder() {
//	      return new BCryptPasswordEncoder();
//	  }
	  /**
	   * 不对密码进行加密
	   */
	  @Bean
	  PasswordEncoder passwordEncoder() {
	      return NoOpPasswordEncoder.getInstance();
	  }
  
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //下面这两行配置表示在内存中配置用户admin具备两个角色"ADMIN","USER",用户sophi具备一个角色"USER"
        auth.inMemoryAuthentication()
        		.withUser("root").password("123").roles("ADMIN","DBA")
        		.and()
                .withUser("admin").password("123").roles("ADMIN","USER")
                .and()
                .withUser("sophia").password("123").roles("USER");
    }


}
