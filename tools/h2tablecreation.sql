CREATE TABLE IF NOT EXISTS classrooms
(
    id INTEGER auto increment NOT NULL,
    number varchar(10),
    building varchar(10),
    capacity integer NOT NULL,
    CONSTRAINT classroom_id_pkey PRIMARY KEY (id)
)
ALTER TABLE classrooms OWNER to andrey;