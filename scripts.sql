select *
from student;
select *
from faculty;
select *
from student
where age >= 14
  and age <= 15;
select name
from student;
select *
from student
where name LIKE '%о%'
  and name LIKE '%ф%';
select *
from student
where age <= student.id;
select *
from student
ORDER BY age;

select f.name
from student as s,
     faculty as f
where s.faculty_id = f.id
  and s.name like ?;

UPDATE student
SET faculty_id = 8
WHERE id = 1;
UPDATE student
SET faculty_id = 8
WHERE id = 2;
UPDATE student
SET faculty_id = 8
WHERE id = 3;
UPDATE student
SET faculty_id = 8
WHERE id = 7;
UPDATE student
SET faculty_id = 9
WHERE id = 4;
UPDATE student
SET faculty_id = 9
WHERE id = 6;
UPDATE student
SET faculty_id = 11
WHERE id = 12;