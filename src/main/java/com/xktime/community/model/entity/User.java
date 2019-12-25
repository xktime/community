package com.xktime.community.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;
    private String name;
    private String account_id;
    private String bio;
    private String login;
    private String token;
    private Date loginTime;
}
