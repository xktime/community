package com.xktime.community.model.entity;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class User {
    private int id;
    private String name;
    private String accountId;
    private String bio;
    private String login;
    private String token;
    private Date loginTime;
    private String avatarUrl;
}
