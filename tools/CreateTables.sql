-- CREATE  Students
CREATE TABLE students
(
    id SERIAL NOT NULL,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,
    CONSTRAINT student_id_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE students OWNER to andrey;    
-----------------------------------------------------------------
-- CREATE  Groups
CREATE TABLE groups
(
    id SERIAL NOT NULL,
    name varchar(50) NOT NULL,    	
    CONSTRAINT group_id_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE groups OWNER to andrey;
-----------------------------------------------------------------
-- CREATE  Group_Student
CREATE TABLE group_student
(
    group_id integer NOT NULL,
    student_id integer NOT NULL,    	
    CONSTRAINT group_student_pkey PRIMARY KEY (group_id,student_id),
    CONSTRAINT group_id_fkey FOREIGN KEY (group_id) REFERENCES groups (id),
    CONSTRAINT student_id_fkey FOREIGN KEY (student_id) REFERENCES students (id)    
)
TABLESPACE pg_default;
ALTER TABLE group_student OWNER to andrey;
-----------------------------------------------------------------
-- CREATE  Teachers
CREATE TABLE teachers
(
    id SERIAL NOT NULL,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,
    passport varchar(50) NOT NULL,    	
    CONSTRAINT teacher_id_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE teachers OWNER to andrey;    
-----------------------------------------------------------------
-- CREATE  Subjects
CREATE TABLE subjects
(
    id SERIAL NOT NULL,
    name varchar(100) NOT NULL,    	
    CONSTRAINT subject_id_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE subjects OWNER to andrey;
-----------------------------------------------------------------
-- CREATE  Classrooms
CREATE TABLE classrooms
(
    id SERIAL NOT NULL,
    number varchar(5) NOT NULL,
    building varchar(5),
    capacity integer NOT NULL,
    CONSTRAINT classroom_id_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE classrooms OWNER to andrey;
-----------------------------------------------------------------
-- CREATE  Schedules
CREATE TABLE schedules
(
    id SERIAL NOT NULL,
    teacher_id integer NOT NULL,
    group_id integer NOT NULL,
    classroom_id integer NOT NULL,
    subject_id integer NOT NULL,
    start_time timestamp NOT NULL,
    end_time timestamp NOT NULL
)
TABLESPACE pg_default;
ALTER TABLE schedules OWNER to andrey;