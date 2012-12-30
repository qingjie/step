CREATE TABLE IF NOT EXISTS User_Credential (
	user_id BIGINT NOT NULL,
	password_hash varchar(128) NOT NULL,
	user_name varchar(128) NOT NULL,
	PRIMARY KEY (user_name),
	INDEX idx_user_credential_user_name (user_name)
) ENGINE = InnoDB;