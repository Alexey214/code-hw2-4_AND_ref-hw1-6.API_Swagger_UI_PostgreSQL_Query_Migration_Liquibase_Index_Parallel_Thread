select s.name, s.age, f.name
from student s
         left join faculty f on faculty_id = f.id;

select s.id, s.name, s.age
from student s
         inner join avatar a on s.id = a.student_id;
