package com.xktime.community.service;

import com.xktime.community.model.dto.GithubUserDTO;
import com.xktime.community.model.entity.User;
import com.xktime.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public void insert(GithubUserDTO githubUser) {
		User user = new User();
		user.setAccount_id(githubUser.getId());
		user.setToken(UUID.randomUUID().toString());
		user.setBio(githubUser.getBio());
		user.setLogin(githubUser.getLogin());
		user.setName(githubUser.getName());
		userRepository.saveUser(user);
	}
}
