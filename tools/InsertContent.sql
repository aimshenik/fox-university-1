--------------------------------------------------------------------
--STUDENTS
insert into students (firstname,lastname) values ('Andrey','Imshenik');
insert into students (firstname,lastname) values ('Ivan','Stepanov');
insert into students (firstname,lastname) values ('Georgy','Teplov');
insert into students (firstname,lastname) values ('Mary','Le');
insert into students (firstname,lastname) values ('Elena','Yakovleva');
insert into students (firstname,lastname) values ('Viktor','Polkin');
insert into students (firstname,lastname) values ('Paul','Bakery');
insert into students (firstname,lastname) values ('Nick','Step');
insert into students (firstname,lastname) values ('Leo','Lukin');
insert into students (firstname,lastname) values ('Mike','Zyuzyukin');
insert into students (firstname,lastname) values ('Gleb','Rubashkin');
insert into students (firstname,lastname) values ('Dmitry','Bredov');
insert into students (firstname,lastname) values ('Rodeon','Chetkov');
insert into students (firstname,lastname) values ('Kirill','Bazarov');
insert into students (firstname,lastname) values ('Ivan','Denisov');
insert into students (firstname,lastname) values ('Max','Hoyaks');
insert into students (firstname,lastname) values ('Jack','Pugovkin');
insert into students (firstname,lastname) values ('Papa','Rimsky');
insert into students (firstname,lastname) values ('Hren','Morjovii');
insert into students (firstname,lastname) values ('Helen','Harper');
insert into students (firstname,lastname) values ('Ekaterina','Maslova');
insert into students (firstname,lastname) values ('Sergey','Luchkov');
insert into students (firstname,lastname) values ('Alexander','Gocha');
insert into students (firstname,lastname) values ('Nikolay','Sharikov');
--------------------------------------------------------------------
--GROUPS
insert into groups (name) values ('1-D-31');
insert into groups (name) values ('1-L-32');
insert into groups (name) values ('2-M-43');
insert into groups (name) values ('3-F-64');
--------------------------------------------------------------------
--GROUPSTUDENT
--1-L-32
insert into group_student (group_id, student_id) values ((select id from groups where name='1-L-32'),(select id from students where lastname='Imshenik'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-L-32'),(select id from students where lastname='Stepanov'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-L-32'),(select id from students where lastname='Teplov'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-L-32'),(select id from students where lastname='Le'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-L-32'),(select id from students where lastname='Yakovleva'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-L-32'),(select id from students where lastname='Polkin'));
--1-D-31
insert into group_student (group_id, student_id) values ((select id from groups where name='1-D-31'),(select id from students where lastname='Bakery'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-D-31'),(select id from students where lastname='Step'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-D-31'),(select id from students where lastname='Lukin'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-D-31'),(select id from students where lastname='Zyuzyukin'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-D-31'),(select id from students where lastname='Rubashkin'));
insert into group_student (group_id, student_id) values ((select id from groups where name='1-D-31'),(select id from students where lastname='Bredov'));
--2-M-43
insert into group_student (group_id, student_id) values ((select id from groups where name='2-M-43'),(select id from students where lastname='Chetkov'));
insert into group_student (group_id, student_id) values ((select id from groups where name='2-M-43'),(select id from students where lastname='Bazarov'));
insert into group_student (group_id, student_id) values ((select id from groups where name='2-M-43'),(select id from students where lastname='Denisov'));
insert into group_student (group_id, student_id) values ((select id from groups where name='2-M-43'),(select id from students where lastname='Hoyaks'));
insert into group_student (group_id, student_id) values ((select id from groups where name='2-M-43'),(select id from students where lastname='Pugovkin'));
insert into group_student (group_id, student_id) values ((select id from groups where name='2-M-43'),(select id from students where lastname='Rimsky'));
--3-F-64
insert into group_student (group_id, student_id) values ((select id from groups where name='3-F-64'),(select id from students where lastname='Morjovii'));
insert into group_student (group_id, student_id) values ((select id from groups where name='3-F-64'),(select id from students where lastname='Harper'));
insert into group_student (group_id, student_id) values ((select id from groups where name='3-F-64'),(select id from students where lastname='Maslova'));
insert into group_student (group_id, student_id) values ((select id from groups where name='3-F-64'),(select id from students where lastname='Luchkov'));
insert into group_student (group_id, student_id) values ((select id from groups where name='3-F-64'),(select id from students where lastname='Gocha'));
insert into group_student (group_id, student_id) values ((select id from groups where name='3-F-64'),(select id from students where lastname='Sharikov'));
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