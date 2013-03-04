CREATE TABLE IF NOT EXISTS friend (
	user_id BIGINT NOT NULL,
	friend_id BIGINT NOT NULL,
	created_at BIGINT,
	PRIMARY KEY (user_id, friend_id)
) ENGINE = InnoDB;