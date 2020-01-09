CREATE TABLE article
(
  id int PRIMARY KEY AUTO_INCREMENT,
  title varchar(36),
  content text,
  author_account_id varchar(20),
  view_count int DEFAULT 0,
  post_time datetime
);