DROP TABLE CLASSROOMS;

CREATE TABLE IF NOT EXISTS CLASSROOMS
(
    ID INTEGER AUTO_INCREMENT NOT NULL,
    NUMBER VARCHAR(10),
    BUILDING VARCHAR(10),
    CAPACITY INTEGER NOT NULL,
    CONSTRAINT CLASSROOM_ID_PKEY PRIMARY KEY (ID)
);
