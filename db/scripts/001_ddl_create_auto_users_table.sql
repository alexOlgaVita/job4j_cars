create table auto_users
(
    id   serial primary key,
    name varchar        not null,
    login varchar unique not null,
    password varchar not null,
    user_zone varchar not null
);