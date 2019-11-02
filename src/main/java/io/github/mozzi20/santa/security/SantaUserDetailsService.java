package io.github.mozzi20.santa.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import io.github.mozzi20.santa.models.User;
import io.github.mozzi20.santa.models.User.Role;
import io.github.mozzi20.santa.repositories.UserRepository;

@Component
public class SantaUserDetailsService extends OidcUserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public User loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser googleUser = super.loadUser(userRequest);
		Optional<User> santaUser = userRepo.findById(googleUser.getName());
		if (santaUser.isPresent()) {
			return santaUser.get();
		} else {
			User newSantaUser = new User();
			newSantaUser.setId(googleUser.getName());
			newSantaUser.setEmail(googleUser.getEmail());
			Role role = userRepo.count() == 0 ? Role.ADMIN : Role.PARTICIPANT;
			newSantaUser.setRole(role);
			return userRepo.save(newSantaUser);
		}
	}

}
