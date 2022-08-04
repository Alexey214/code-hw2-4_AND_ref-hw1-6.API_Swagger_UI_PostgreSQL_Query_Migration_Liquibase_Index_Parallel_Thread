create table drivers
(
    id                    bigserial,
    name                  varchar(255),
    age                   integer,
    driver_license_serial varchar(4),
    driver_license_number varchar(6),
    driver_license        boolean     default false,
    dt_create             timestamptz default now() not null,
    constraint drivers_PK primary key (id),
    constraint check_age check ( age > 0),
    constraint driver_license_name_serial_number_unique unique (name,
                                                                driver_license_serial,
                                                                driver_license_number)
);

alter table drivers
    owner to "Alexey";

create table cars
(
    id         bigserial,
    mark       varchar(30),
    cost       numeric(10, 2),
    dt_create  timestamptz default now() not null,
    drivers_id bigint references drivers (id),
    constraint cars_PK primary key (id)
);


alter table cars
    owner to "Alexey";

drop table cars;
drop table drivers;
