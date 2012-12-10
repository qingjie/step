CREATE TABLE IF NOT EXISTS Thought(
	`id` bigint NOT NULL,
	`author_id` bigint NOT NULL,
	`text` text NOT NULL,
	`created_at` bigint DEFAULT NULL,
	PRIMARY KEY (`id`),
	KEY `idx_thought_author` (`author_id`,`id` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;