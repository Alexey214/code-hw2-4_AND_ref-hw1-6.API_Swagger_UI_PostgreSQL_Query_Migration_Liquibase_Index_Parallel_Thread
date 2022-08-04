create table drivers
(
    id                    bigserial primary key,
    name                  varchar(255) unique,
    age                   integer check ( age > 16),
    driver_license_serial varchar(4) unique,
    driver_license_number varchar(6) unique,
    driver_license        boolean     default false,
    dt_create             timestamptz default now() not null
);

alter table drivers
    owner to "Alexey";

create table cars
(
    id         bigserial primary key,
    mark       varchar(30),
    cost       numeric(10, 2),
    dt_create  timestamptz default now() not null,
    drivers_id bigint references drivers (id)
);


alter table cars
    owner to "Alexey";

drop table cars;
drop table drivers;
