create table owners
(
    id   serial primary key,
    name varchar unique not null,
    user_id int references auto_users(id)
);