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