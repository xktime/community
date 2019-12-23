create table user
(
	id int auto_increment
		primary key,
	name varchar(10) null,
	account_id varchar(20) null,
	bio varchar(256) null,
	login varchar(10) null,
	token varchar(36) null
)
;

