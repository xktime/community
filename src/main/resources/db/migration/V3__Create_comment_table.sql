CREATE TABLE comment
(
    id int PRIMARY KEY AUTO_INCREMENT,
    article_id int NOT NULL,
    author_account_id varchar(20) NOT NULL,
    content text,
    post_time datetime NOT NULL
);