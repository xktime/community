package com.xktime.community.repository;

import com.xktime.community.model.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserRepository {
    @Insert("INSERT INTO user(name,account_id,bio,login,token,login_time,avatar_url) " +
            "VALUES (#{name},#{accountId},#{bio},#{login},#{token},#{loginTime},#{avatarUrl})")
    void saveUser(User user);

    @Update("UPDATE user SET token = #{token}, login_time = #{loginTime}, bio = #{bio}, name = #{name}, avatar_url = #{avatarUrl}")
    void updateUser(User user);

    @Select("SELECT * FROM user WHERE token = #{token}")
//    @Results ({
//            @Result(property = "id", column = "id"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "accountId", column = "account_id"),
//            @Result(property = "bio", column = "bio"),
//            @Result(property = "login", column = "login"),
//            @Result(property = "token", column = "token"),
//            @Result(property = "loginTime", column = "login_time"),
//            @Result(property = "avatarUrl", column = "avatar_url"),
//    })
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM user WHERE account_id = #{accountId}")
//    @Results ({
//            @Result(property = "id", column = "id"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "accountId", column = "account_id"),
//            @Result(property = "bio", column = "bio"),
//            @Result(property = "login", column = "login"),
//            @Result(property = "token", column = "token"),
//            @Result(property = "loginTime", column = "login_time"),
//            @Result(property = "avatarUrl", column = "avatar_url"),
//    })
    User findByAccountId(@Param("accountId")String accountId);

}
