INSERT INTO car_bodies (name) VALUES ('Седан');
INSERT INTO car_bodies (name) VALUES ('Универсал');
INSERT INTO car_bodies (name) VALUES ('Пикап');
INSERT INTO car_bodies (name) VALUES ('Минивэн');
INSERT INTO car_bodies (name) VALUES ('Фургон');
INSERT INTO car_bodies (name) VALUES ('Кабриолет');
INSERT INTO car_bodies (name) VALUES ('Купе');
INSERT INTO car_bodies (name) VALUES ('Лифтбэк');
INSERT INTO car_bodies (name) VALUES ('Внедорожник 3 дв.');
INSERT INTO car_bodies (name) VALUES ('Внедорожник 5 дв.');
INSERT INTO car_bodies (name) VALUES ('Хэтчбек 3 дв.');
INSERT INTO car_bodies (name) VALUES ('Хэтчбек 5 дв.');

ALTER TABLE cars ADD COLUMN car_body_id int REFERENCES car_bodies(id);

UPDATE cars SET car_body_id = (SELECT id FROM car_bodies WHERE name = 'Седан');