package com.springboot.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.entities.User;
import com.springboot.demo.repository.UserRepository;
import com.springboot.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserByUserName(String userName) {
		return userRepository.getUserByUserName(userName);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Integer userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		return userOptional.orElse(null);
	}

	@Override
	public List<User> getUserByRole(String role) {
		return userRepository.getUserByRole(role);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Integer userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public boolean isEmailAlreadyExists(String email) {
		return userRepository.existsByEmail(email);
	}

}
