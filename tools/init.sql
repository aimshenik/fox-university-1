-- DROP  Students
DROP TABLE students; 

-- DROP  Groups
DROP TABLE groups;

-- DROP  Teachers
DROP TABLE teachers;

-- DROP  Subjects
DROP TABLE subjects;

-- DROP  Classrooms
DROP TABLE classrooms;

-- DROP  Schedules
DROP TABLE schedules;


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
-- CREATE  Students
CREATE TABLE students
(
    id SERIAL NOT NULL,
    group_id INT DEFAULT NULL,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,
    CONSTRAINT student_id_pkey PRIMARY KEY (id),
    CONSTRAINT group_id_fkey FOREIGN KEY (group_id) REFERENCES groups (id) 
)
TABLESPACE pg_default;
ALTER TABLE students OWNER to andrey;    

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
    number varchar(10) NOT NULL,
    building varchar(10),
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
-------------------------------------------------------------------
ALTER SEQUENCE groups_id_seq restart with 100;

-------------------------------------------------------------------
--------------------------------------------------------------------
--GROUPS
insert into groups (name) values ('1-D-31');
insert into groups (name) values ('1-L-32');
insert into groups (name) values ('2-M-43');
insert into groups (name) values ('3-F-64');
--------------------------------------------------------------------
--STUDENTS
insert into students (firstname,lastname,group_id) values ('Andrey','Imshenik',(select id from groups where name = '1-D-31'));
insert into students (firstname,lastname,group_id) values ('Ivan','Stepanov',(select id from groups where name = '1-D-31'));
insert into students (firstname,lastname,group_id) values ('Georgy','Teplov',(select id from groups where name = '1-D-31'));
insert into students (firstname,lastname,group_id) values ('Mary','Le',(select id from groups where name = '1-D-31'));
insert into students (firstname,lastname,group_id) values ('Elena','Yakovleva',(select id from groups where name = '1-D-31'));
insert into students (firstname,lastname,group_id) values ('Viktor','Polkin',(select id from groups where name = '1-D-31'));
insert into students (firstname,lastname,group_id) values ('Paul','Bakery',(select id from groups where name = '1-L-32'));
insert into students (firstname,lastname,group_id) values ('Nick','Step',(select id from groups where name = '1-L-32'));
insert into students (firstname,lastname,group_id) values ('Leo','Lukin',(select id from groups where name = '1-L-32'));
insert into students (firstname,lastname,group_id) values ('Mike','Zyuzyukin',(select id from groups where name = '1-L-32'));
insert into students (firstname,lastname,group_id) values ('Gleb','Rubashkin',(select id from groups where name = '1-L-32'));
insert into students (firstname,lastname,group_id) values ('Dmitry','Bredov',(select id from groups where name = '1-L-32'));
insert into students (firstname,lastname,group_id) values ('Rodeon','Chetkov',(select id from groups where name = '2-M-43'));
insert into students (firstname,lastname,group_id) values ('Kirill','Bazarov',(select id from groups where name = '2-M-43'));
insert into students (firstname,lastname,group_id) values ('Ivan','Denisov',(select id from groups where name = '2-M-43'));
insert into students (firstname,lastname,group_id) values ('Max','Hoyaks',(select id from groups where name = '2-M-43'));
insert into students (firstname,lastname,group_id) values ('Jack','Pugovkin',(select id from groups where name = '2-M-43'));
insert into students (firstname,lastname,group_id) values ('Papa','Rimsky',(select id from groups where name = '2-M-43'));
insert into students (firstname,lastname,group_id) values ('Hren','Morjovii',(select id from groups where name = '3-F-64'));
insert into students (firstname,lastname,group_id) values ('Helen','Harper',(select id from groups where name = '3-F-64'));
insert into students (firstname,lastname,group_id) values ('Ekaterina','Maslova',(select id from groups where name = '3-F-64'));
insert into students (firstname,lastname,group_id) values ('Sergey','Luchkov',(select id from groups where name = '3-F-64'));
insert into students (firstname,lastname,group_id) values ('Alexander','Gocha',(select id from groups where name = '3-F-64'));
insert into students (firstname,lastname,group_id) values ('Nikolay','Sharikov',(select id from groups where name = '3-F-64'));
--------------------------------------------------------------------
--------------------------------------------------------------------
--TEACHERS
insert into teachers (firstname,lastname,passport) values ('Andrey Fedorovich','Smikov','AA-11111');
insert into teachers (firstname,lastname,passport) values ('Viktor Sergeevich','Moiseev','AA-22222');
insert into teachers (firstname,lastname,passport) values ('Dmitry Vasilyevich','Berezhnoy','AA-33333');
insert into teachers (firstname,lastname,passport) values ('Boris Leonidovich','Bobryshev','AA-44444');
insert into teachers (firstname,lastname,passport) values ('Olga Valentinovna','Telitsyna','AA-55555');
--------------------------------------------------------------------
--SUBJECTS
insert into subjects (name) values ('Heat engineering and heat transfer basics');
insert into subjects (name) values ('Metrology, standardization and certification');
insert into subjects (name) values ('Safety of vital functions');
insert into subjects (name) values ('Basics of construction');
insert into subjects (name) values ('Foundry technology');
insert into subjects (name) values ('Materials Science');
--------------------------------------------------------------------
--CLASSROOMS
insert into classrooms (number, building, capacity) values ('106','23',30);
insert into classrooms (number, building, capacity) values ('211','23',50);
insert into classrooms (number, building, capacity) values ('321','23',30);
insert into classrooms (number, building, capacity) values ('401','24',100);
insert into classrooms (number, building, capacity) values ('305','24',30);
--------------------------------------------------------------------
--SCHEDULES
--Smikov
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='Smikov'),
		(select id from groups where name='1-D-31'),
		(select id from classrooms where building='23' and number='106'),
		(select id from subjects where name='Heat engineering and heat transfer basics'),
		'2018-01-09 09:00:00',
		'2018-01-09 10:20:00'
	);
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='Smikov'),
		(select id from groups where name='1-L-32'),
		(select id from classrooms where building='23' and number='106'),
		(select id from subjects where name='Heat engineering and heat transfer basics'),
		'2018-01-09 10:40:00',
		'2018-01-09 12:00:00'
	);
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='Smikov'),
		(select id from groups where name='2-M-43'),
		(select id from classrooms where building='23' and number='106'),
		(select id from subjects where name='Foundry technology'),
		'2018-01-09 13:00:00',
		'2018-01-09 14:20:00'
	);	
--Smikov
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='Moiseev'),
		(select id from groups where name='1-L-32'),
		(select id from classrooms where building='23' and number='211'),
		(select id from subjects where name='Metrology, standardization and certification'),
		'2018-01-09 09:00:00',
		'2018-01-09 10:20:00'
	);
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='Moiseev'),
		(select id from groups where name='1-D-31'),
		(select id from classrooms where building='23' and number='211'),
		(select id from subjects where name='Metrology, standardization and certification'),
		'2018-01-09 10:40:00',
		'2018-01-09 12:00:00'
	);
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='Moiseev'),
		(select id from groups where name='3-F-64'),
		(select id from classrooms where building='23' and number='211'),
		(select id from subjects where name='Metrology, standardization and certification'),
		'2018-01-09 13:00:00',
		'2018-01-09 14:20:00'
	);	
--Berezhnoy
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='Berezhnoy'),
		(select id from groups where name='2-M-43'),
		(select id from classrooms where building='23' and number='211'),
		(select id from subjects where name='Safety of vital functions'),
		'2018-01-09 09:00:00',
		'2018-01-09 10:20:00'
	);
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='Berezhnoy'),
		(select id from groups where name='3-F-64'),
		(select id from classrooms where building='23' and number='211'),
		(select id from subjects where name='Safety of vital functions'),
		'2018-01-09 10:40:00',
		'2018-01-09 12:00:00'
	);
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='Berezhnoy'),
		(select id from groups where name='1-D-31'),
		(select id from classrooms where building='23' and number='211'),
		(select id from subjects where name='Safety of vital functions'),
		'2018-01-09 13:00:00',
		'2018-01-09 14:20:00'
	);	