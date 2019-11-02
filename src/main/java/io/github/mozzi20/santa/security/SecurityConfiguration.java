package io.github.mozzi20.santa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.github.mozzi20.santa.models.User;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SantaUserDetailsService userService;
	
	@Autowired
	private LoginHandler loginHandler;
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin().disable()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
			.and()
			.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/wishlist/**").hasRole("PARTICIPANT")
			.and()
			.oauth2Login()
				.successHandler(loginHandler)
				.userInfoEndpoint()
					.customUserType(User.class, "google")
					.oidcUserService(userService)
				.and()
			.and()
		;
	}
}
