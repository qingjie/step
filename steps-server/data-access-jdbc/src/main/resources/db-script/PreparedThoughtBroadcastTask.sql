CREATE TABLE IF NOT EXISTS Prepared_Thought_Broadcast_Task(
	id serial,
	thought_id bigint not null,
	recipient_list text not null,
	PRIMARY KEY(id)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;