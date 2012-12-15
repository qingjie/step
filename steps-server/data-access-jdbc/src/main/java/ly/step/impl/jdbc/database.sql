CREATE DATABASE IF NOT EXISTS step CHARACTER SET = utf8;

USE step;

CREATE TABLE IF NOT EXISTS User (
	id BIGINT NOT NULL,
	name VARCHAR(128) NOt NULL,
	PRIMARY KEY (id),
	UNIQUE INDEX idx_user_name USING HASH (name)    
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Thought (
	id BIGINT NOT NULL,
	author_id BIGINT NOT NULL,
	`text` TEXT NOT NULL,
	created_at BIGINT NULL,
	PRIMARY KEY (id),
	INDEX idx_thought_author (author_id, id DESC)		 
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS User_To_Thought (
	user_id BIGINT NOT NULL,
	thought_id BIGINT NOT NULL,
	created_at BIGINT  NOT NULL,
	PRIMARY KEY (user_id, thought_id DESC)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Comment (
	id BIGINT NOT NULL,
	thought_id BIGINT NOT NULL,
	author_id BIGINT NOT NULL,
	`text` TEXT NOT NULL,
	created_at BIGINT NOT NULL,
	PRIMARY KEY (id),
	INDEX idx_comment_thought_id (thought_id, id desc)	
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Ticket (
	code VARCHAR(128) NOT NULL,
	user_id BIGINT NOT NULL,
	PRIMARY KEY (code)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS USER_RELATION (
	friend_a BIGINT NOT NULL,
	friend_b BIGINT NOT NULL,
	created_at BIGINT,
	UNIQUE INDEX idx_user_relation_a_b (friend_a, friend_b),
	UNIQUE INDEX idx_user_relation_a_b (friend_b, friend_a)
) ENGINE = InnoDB;