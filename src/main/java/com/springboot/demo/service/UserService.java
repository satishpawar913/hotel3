package com.springboot.demo.service;

import java.util.List;

import com.springboot.demo.entities.User;

public interface UserService {

	User getUserByUserName(String userName);

	List<User> getUserByRole(String role);

	User saveUser(User user);

	void deleteUser(Integer userId);

	List<User> getAllUsers();

	User getUserById(Integer userId);

	boolean isEmailAlreadyExists(String email);

}
