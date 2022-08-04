ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK (age > 0);

alter TABLE student
    add constraint name_constraint unique (name);

alter table student
    alter column name SET NOT NULL;

alter table faculty
    add constraint name_color_constraint unique (name, color);

alter table student
    alter age set default 20;

