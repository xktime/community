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

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }
}
