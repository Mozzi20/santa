package dev.movitz.santa.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				response.sendRedirect("/admin");
				return;
			}
			if (authority.getAuthority().equals("ROLE_PARTICIPANT")) {
				response.sendRedirect("/wishlist");
				return;
			}
			if(authority.getAuthority().equals("ROLE_MODERATOR")) {
				response.sendRedirect("/moderator");
				return;
			}
		}
		response.sendRedirect("/");
	}

}
