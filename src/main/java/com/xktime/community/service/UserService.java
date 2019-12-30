package com.xktime.community.service;

import com.xktime.community.model.entity.User;
import com.xktime.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Object LOCK_ME_OBJ = new Object();

    @Autowired
    private UserRepository userRepository;

    public synchronized void saveUser(User user) {
        if (user == null) {
            throw new NullPointerException("User不能为空");
        }
        if (user.getAccountId() == null) {
            throw new NullPointerException("User请求数据错误");
        }
        if (findByToken(user.getToken()) != null) {
            throw new IllegalArgumentException("token重复");
        }
        if (findByAccountId(user.getAccountId()) == null) {
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

    public User getCanceledUser() {
        User user = createCanceledUser();
        if (findByAccountId(user.getAccountId()) == null) {
            synchronized (LOCK_ME_OBJ) {
                if (findByAccountId(user.getAccountId()) == null) {
                    //如果没有，放进数据库就放进数据库
                    saveUser(user);
                }
            }
        }
        return user;
    }

    public void update(User user) {
        userRepository.updateUser(user);
    }

    /**
     * 创建一个注销账户
     */
    private User createCanceledUser() {
        User user = new User();
        user.setAvatarUrl("https://avatars2.githubusercontent.com/u/10693199?v=4");
        user.setName("已注销");
        user.setAccountId("0");
        return user;
    }
}
