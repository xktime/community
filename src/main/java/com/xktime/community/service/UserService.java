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

    public User transferGithubUserToUser(GithubUserDTO githubUser) {
        User user = new User();
        user.setToken(UUID.randomUUID().toString());
        user.setAccount_id(githubUser.getId());
        user.setBio(githubUser.getBio());
        user.setLogin(githubUser.getLogin());
        user.setName(githubUser.getName());
        return user;
    }

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }
}
