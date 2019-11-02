package io.github.mozzi20.santa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mozzi20.santa.models.User;
import io.github.mozzi20.santa.repositories.UserRepository;

@Service
@Transactional(readOnly=true)
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	public Optional<User> getPersistedUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			return userRepo.findById(((User) principal).getId());			
		} else {
			return Optional.empty();
		}
	}
	
	public Optional<String> getUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof User) {
			return Optional.of(((User) principal).getId());
		} else {
			return Optional.empty();
		}
	}

	public Iterable<User> getAllUsers() {
		return userRepo.findAll();
	}
	
}
