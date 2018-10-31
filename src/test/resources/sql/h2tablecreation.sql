CREATE TABLE IF NOT EXISTS classrooms
(
    id INTEGER auto_increment NOT NULL,
    number varchar(10),
    building varchar(10),
    capacity integer NOT NULL,
    CONSTRAINT classroom_id_pkey PRIMARY KEY (id)
);
--ALTER TABLE classrooms SET OWNER to andrey;