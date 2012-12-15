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



CREATE TABLE IF NOT EXISTS Ticket (
	code VARCHAR(128) NOT NULL,
	user_id BIGINT NOT NULL,
	PRIMARY KEY (code)
) ENGINE = InnoDB;

