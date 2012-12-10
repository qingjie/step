CREATE TABLE IF NOT EXISTS User(
	`id` bigint(64) NOT NULL,
	`name` varchar(128) NOT NULL,
	PRIMARY KEY(`id`),
	UNIQUE KEY `idx_user_name` (`name`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;