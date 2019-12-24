package com.xktime.community.service;

import com.xktime.community.model.entity.User;
import com.xktime.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        if (user == null) {
            throw new NullPointerException("User不能为空");
        }
        if (findByAccountId(user.getAccount_id()) == null) {
            userRepository.saveUser(user);
        } else {
            update(user);
        }
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }

    public User findByAccountId(String accountId) {
        return userRepository.findByAccountId(accountId);
    }

    public void update(User user) {
        userRepository.updateUser(user);
    }
}
