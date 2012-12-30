CREATE TABLE IF NOT EXISTS Access_Token (
	access_token VARCHAR(128) NOT NULL,
	user_id BIGINT NOT NULL,
	created_at BIGINT NOT NULL,
	expired_in INT NOT NULL DEFAULT 7,
	PRIMARY KEY (code)
) ENGINE = InnoDB;
