create table PUBLIC.USER
(
	ID INTEGER default  auto_increment,
	NAME VARCHAR default '50',
	ACCOUNT_ID VARCHAR default '100',
	TOKEN CHAR default '50',
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint USER_PK
		primary key (ID)
);