create table cars
(
    id   serial primary key,
    name varchar unique not null,
    engine_id int  references engines(id)
);