--------------------------------------------------------------------
--GROUPS
insert into groups (name) values ('1-D-31');
insert into groups (name) values ('1-L-32');
insert into groups (name) values ('2-M-43');
insert into groups (name) values ('3-F-64');
--------------------------------------------------------------------
--STUDENTS
insert into students (firstname,lastname,group_id) values ('Andrey','Imshenik',1);
insert into students (firstname,lastname,group_id) values ('Ivan','Stepanov',1);
insert into students (firstname,lastname,group_id) values ('Georgy','Teplov',1);
insert into students (firstname,lastname,group_id) values ('Mary','Le',1);
insert into students (firstname,lastname,group_id) values ('Elena','Yakovleva',1);
insert into students (firstname,lastname,group_id) values ('Viktor','Polkin',1);
insert into students (firstname,lastname,group_id) values ('Paul','Bakery',2);
insert into students (firstname,lastname,group_id) values ('Nick','Step',2);
insert into students (firstname,lastname,group_id) values ('Leo','Lukin',2);
insert into students (firstname,lastname,group_id) values ('Mike','Zyuzyukin',2);
insert into students (firstname,lastname,group_id) values ('Gleb','Rubashkin',2);
insert into students (firstname,lastname,group_id) values ('Dmitry','Bredov',2);
insert into students (firstname,lastname,group_id) values ('Rodeon','Chetkov',3);
insert into students (firstname,lastname,group_id) values ('Kirill','Bazarov',3);
insert into students (firstname,lastname,group_id) values ('Ivan','Denisov',3);
insert into students (firstname,lastname,group_id) values ('Max','Hoyaks',3);
insert into students (firstname,lastname,group_id) values ('Jack','Pugovkin',3);
insert into students (firstname,lastname,group_id) values ('Papa','Rimsky',3);
insert into students (firstname,lastname,group_id) values ('Hren','Morjovii',4);
insert into students (firstname,lastname,group_id) values ('Helen','Harper',4);
insert into students (firstname,lastname,group_id) values ('Ekaterina','Maslova',4);
insert into students (firstname,lastname,group_id) values ('Sergey','Luchkov',4);
insert into students (firstname,lastname,group_id) values ('Alexander','Gocha',4);
insert into students (firstname,lastname,group_id) values ('Nikolay','Sharikov',4);
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