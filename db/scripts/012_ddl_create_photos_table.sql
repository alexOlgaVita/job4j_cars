CREATE TABLE photos (
   id serial PRIMARY KEY,
   name TEXT UNIQUE NOT NULL
);

INSERT INTO photos (name) VALUES ('Анфас');
INSERT INTO photos (name) VALUES ('В профиль');
INSERT INTO photos (name) VALUES ('Прямой вид сзади');
INSERT INTO photos (name) VALUES ('Общий план передней части салона');
INSERT INTO photos (name) VALUES ('Приборная панель');
INSERT INTO photos (name) VALUES ('Пассажирские сиденья сзади');
INSERT INTO photos (name) VALUES ('Багажник');
INSERT INTO photos (name) VALUES ('Двигатель');