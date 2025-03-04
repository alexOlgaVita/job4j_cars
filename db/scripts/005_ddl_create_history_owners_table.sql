CREATE TABLE history_owners (
   id serial PRIMARY KEY,
   car_id int not null REFERENCES cars(id),
   owner_id int not null REFERENCES owners(id),
   UNIQUE (car_id, owner_id)
);