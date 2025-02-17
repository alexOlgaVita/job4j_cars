create table auto_users
(
    id   serial primary key,
    login varchar unique not null,
    password varchar not null
);