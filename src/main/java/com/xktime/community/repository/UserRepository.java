package com.xktime.community.repository;

import com.xktime.community.model.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRepository {
    void saveUser(User user);

    void updateUser(User user);

    User findByToken(@Param("token") String token);

    User findByAccountId(@Param("accountId") String accountId);

}
