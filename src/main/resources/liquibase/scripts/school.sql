-- liquibase formatted sql

-- changeset agolenev:1
create index faculty_name_color_index on faculty (name, color);

-- changeset agolenev:2
create index student_name_index on student (name);

