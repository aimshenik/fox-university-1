DROP TABLE IF EXISTS SCHEDULES;

CREATE TABLE IF NOT EXISTS SCHEDULES
(
    ID INTEGER AUTO_INCREMENT NOT NULL,
    TEACHER_ID INTEGER NOT NULL,
    GROUP_ID INTEGER NOT NULL,
    CLASSROOM_ID INTEGER NOT NULL,
    SUBJECT_ID INTEGER NOT NULL,
    START_TIME TIMESTAMP NOT NULL,
    END_TIME TIMESTAMP NOT NULL,
    CONSTRAINT SCHEDULES_ID_PKEY PRIMARY KEY (ID)
);
--------
DROP TABLE IF EXISTS CLASSROOMS;

CREATE TABLE IF NOT EXISTS CLASSROOMS
(
    ID INTEGER AUTO_INCREMENT NOT NULL,
    NUMBER VARCHAR(10),
    BUILDING VARCHAR(10),
    CAPACITY INTEGER NOT NULL,
    CONSTRAINT CLASSROOMS_ID_PKEY PRIMARY KEY (ID)
);
DROP TABLE IF EXISTS GROUPS;

CREATE TABLE IF NOT EXISTS GROUPS
(
    ID INTEGER AUTO_INCREMENT NOT NULL,
    NAME VARCHAR(50),
    CONSTRAINT GROUPS_ID_PKEY PRIMARY KEY (ID)
);
DROP TABLE IF EXISTS SUBJECTS;

CREATE TABLE IF NOT EXISTS SUBJECTS
(
    ID INTEGER AUTO_INCREMENT NOT NULL,
    NAME VARCHAR(150),
    CONSTRAINT SUBJECTS_ID_PKEY PRIMARY KEY (ID)
);
DROP TABLE IF EXISTS TEACHERS;

CREATE TABLE IF NOT EXISTS TEACHERS
(
    ID INTEGER AUTO_INCREMENT NOT NULL,
    FIRSTNAME VARCHAR(50) NOT NULL,
    LASTNAME VARCHAR(50) NOT NULL,
    PASSPORT VARCHAR(50),
    CONSTRAINT TEACHERS_ID_PKEY PRIMARY KEY (ID),
);







