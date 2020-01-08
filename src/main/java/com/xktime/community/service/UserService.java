package com.xktime.community.service;

import com.xktime.community.model.dto.UserDTO;
import com.xktime.community.model.entity.User;
import com.xktime.community.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Object LOCK_ME_OBJ = new Object();

    @Autowired
    private UserRepository userRepository;

    /**
     * 将用户数据保存进数据库
     * 如果已有用户就更新数据
     */
    public synchronized void saveUser(User user) {
        if (user == null) {
            throw new NullPointerException("User不能为空");
        }
        if (StringUtils.isBlank(user.getAccountId())) {
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

    public UserDTO transferUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
    /**
     * 获得一个已注销的账户,并将其放入用户数据库中（似乎没什么必要放）
     */
    public User getCanceledUser() {
        User user = createCanceledUser();
        if (findByAccountId(user.getAccountId()) == null) {
            synchronized (LOCK_ME_OBJ) {
                if (findByAccountId(user.getAccountId()) == null) {
                    //如果没有，就放进数据库
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
