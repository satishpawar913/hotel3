package com.springboot.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService getUserDetailsService() {

		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	            .anyRequest().authenticated()
	        .and()
	            .formLogin().loginPage("/login").permitAll()
	            .successHandler(new AuthenticationSuccessHandler() {
	                @Override
	                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                        Authentication authentication) throws IOException, ServletException {
	                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	                    String role = auth.getAuthorities().toString();
	                    String targetUrl = "";
	                    if (role.contains("MANAGER")) {
	                        targetUrl = "/manager";
	                    } else if (role.contains("OWNER")) {
	                        targetUrl = "/owner";
	                    }  else {
	                        targetUrl = "/hotel";
	                    }
	                    response.sendRedirect(targetUrl);
	                }
	            })
	        .and()
	            .logout()
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                .logoutSuccessUrl("/login")
	                .invalidateHttpSession(true)
	                .deleteCookies("JSESSIONID")
	        .and()
	            .csrf().disable();
	}

//	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/signup").antMatchers("/css/**").antMatchers("/register");

	}

	
	

}
