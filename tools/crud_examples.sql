--student create
insert into students (firstname,lastname) values ('andrey','imshenik');
--student read
select * from students where firstname='andrey' and lastname ='imshenik';
--student update
update students set firstname='andrey',lastname='imshenik' where id=1;
--student delete
delete from students as s where s.firstname='andrey' and s.lastname='imshenik';
-------------------------------------------------------------------------------
--groups create
insert into groups (name) values ('1-d-31');
--groups read
select * from groups g where g.name='1-d-31';
--groups update
update groups set name='1-d-31' where id=1;
--groups delete
delete from groups as g where s.id=5;
-------------------------------------------------------------------------------
--group_student create
insert into group_student (group_id, student_id) values (100,200);
--group_student read
select * from group_student gs where gs.group_id=100;
--group_student update
update group_student set group_id=100 where group_id=1;
--group_student delete
delete from group_student as gs where gs.student_id = 12;
-------------------------------------------------------------------------------
--teachers create
insert into teachers (firstname,lastname,passport) values ('andrey fedorovich','smikov','aa-11111');
--teachers read
select * from teachers where lastname='berezhnoy';
--teachers update
update teachers set firstname='dmitry vasilyevich',lastname='berezhnoi',passport='aa-11111' where id=3;
--teachers delete
delete from teachers where id = 3;
-------------------------------------------------------------------------------
--subjects create
insert into subjects (name) values ('heat engineering and heat transfer basics');
--subjects read
select * from subjects where name like '%engineering%';
--subjects update
update subjects set name='heat engineering and heat transfer basics' where id=1;
--subjects delete
delete from subjects where id = 1;
-------------------------------------------------------------------------------
--classrooms create
insert into classrooms (number, building, capacity) values ('106','23',30);
--classrooms read
select * from classrooms where number='106' and building='23';
--classrooms update
update classrooms set number='106', building='23', capacity=30 where id=1;
--classrooms delete
delete from classrooms where id = 1;
-------------------------------------------------------------------------------
--schedules create
insert into schedules (teacher_id, group_id, classroom_id, subject_id, start_time, end_time) 
	values (
		(select id from teachers where lastname='smikov'),
		(select id from groups where name='1-d-31'),
		(select id from classrooms where building='23' and number='106'),
		(select id from subjects where name='heat engineering and heat transfer basics'),
		'2018-01-09 09:00:00',
		'2018-01-09 10:20:00'
	);
--schedules read
select * from schedules where teacher_id=1 and group_id=2;
--schedules update
update schedules set teacher_id=1, group_id=2, classroom_id=1,subject_id=1,start_time='2018-01-09 09:00:00',end_time=''2018-01-09 10:20:00'' where id=2;
--schedules delete
delete from schedules where id = 1;