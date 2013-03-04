CREATE TABLE IF NOT EXISTS User_To_Thought (
	user_id BIGINT NOT NULL,
	thought_id BIGINT NOT NULL,
	created_at BIGINT  NOT NULL,
	PRIMARY KEY (user_id, thought_id DESC)
) ENGINE = InnoDB;