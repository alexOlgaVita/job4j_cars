create table auto_posts
(
    id serial primary key,
    description varchar not null,
    created timestamp not null,
    auto_user_id int  references auto_users(id),
    car_id int references cars(id),
    history_id int REFERENCES histories(id),
    done BOOLEAN
);