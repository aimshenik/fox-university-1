select groups.name, t.firstname as AAA, s.start_time, s.end_time
from schedules s
INNER JOIN groups on groups.id = s.group_id
INNER JOIN teachers t on t.id = s.teacher_id
WHERE (group_id = (SELECT group_id 
 				FROM group_student  
 				WHERE student_id = (SELECT s.id FROM students s WHERE firstname='Andrey' and lastname='Imshenik')));