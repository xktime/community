CREATE TABLE user
(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(10),
    account_id varchar(20),
    bio varchar(256),
    login varchar(10),
    token varchar(36),
    avatar_url varchar(100),
    login_time datetime
);
CREATE UNIQUE INDEX user_id_uindex ON user (id);
CREATE UNIQUE INDEX user_account_id_uindex ON user (account_id);
CREATE UNIQUE INDEX user_token_uindex ON user (token);

