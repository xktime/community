package com.xktime.community.repository;

import com.xktime.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    @Insert("INSERT INTO user(name,account_id,bio,login,token) VALUES (#{name},#{account_id},#{bio},#{login},#{token})")
    void inser(User user);
}
