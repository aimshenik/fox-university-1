CREATE USER andrey WITH password '1234321';
--postgres=# \password 'andrey'; //смена пароля пользователю andrey

--postgres=# \l  //список баз данных

CREATE DATABASE university WITH OWNER andrey;

-- postgres=# \connect 'user=andrey dbname=university'     //подкл. к другой базе под другим пользователем

CREATE table Classrooms 