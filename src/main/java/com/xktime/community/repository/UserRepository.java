package com.xktime.community.repository;

import com.xktime.community.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepository {
    @Insert("INSERT INTO user(name,account_id,bio,login,token) VALUES (#{name},#{account_id},#{bio},#{login},#{token})")
    void saveUser(User user);

    @Select("SELECT * FROM user WHERE token = #{token}")
    User findByToken(@Param("token") String token);
}
