package com.xktime.community.mapper;

import com.xktime.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user(name,account_id,bio,login,token) VALUES (#{name},#{account_id},#{bio},#{login},#{token})")
    void inser(User user);
}
