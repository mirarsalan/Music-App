package com.musicapp.service.impl;

import com.musicapp.model.User;
import com.musicapp.repository.RoleRepository;
import com.musicapp.repository.UserRepository;
import com.musicapp.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder	bCryptPasswordEncoder;

	private final Logger			logger	= LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public void save(User user) {

		logger.info("Saving the user");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {

		logger.info("Finding by username");
		return userRepository.findByUsername(username);
	}
}
