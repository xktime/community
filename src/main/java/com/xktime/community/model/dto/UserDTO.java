package com.xktime.community.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String accountId;
    private String name;
    private String bio;
    private String login;
    private Date loginTime;
    private String avatarUrl;
}
