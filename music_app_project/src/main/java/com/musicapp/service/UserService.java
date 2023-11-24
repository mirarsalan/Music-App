package com.musicapp.service;

import com.musicapp.model.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);
}
