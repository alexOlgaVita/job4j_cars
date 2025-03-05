CREATE TABLE brands (
   id serial PRIMARY KEY,
   name TEXT UNIQUE NOT NULL
);

INSERT INTO brands (name) VALUES ('Toyota');
INSERT INTO brands (name) VALUES ('Audi');
INSERT INTO brands (name) VALUES ('BMW');
INSERT INTO brands (name) VALUES ('Ford');
INSERT INTO brands (name) VALUES ('Subaru');
INSERT INTO brands (name) VALUES ('Volkswagen');
INSERT INTO brands (name) VALUES ('Opel');
INSERT INTO brands (name) VALUES ('Renault');
INSERT INTO brands (name) VALUES ('Kia');
INSERT INTO brands (name) VALUES ('Hyundai');
INSERT INTO brands (name) VALUES ('Honda');
INSERT INTO brands (name) VALUES ('Skoda');
INSERT INTO brands (name) VALUES ('Peugeot');
INSERT INTO brands (name) VALUES ('Haval');
INSERT INTO brands (name) VALUES ('Geely');
INSERT INTO brands (name) VALUES ('Chevrolet');
INSERT INTO brands (name) VALUES ('Lexus');
INSERT INTO brands (name) VALUES ('Nissan');
INSERT INTO brands (name) VALUES ('Mitsubishi');
INSERT INTO brands (name) VALUES ('Mazda');
INSERT INTO brands (name) VALUES ('Daewoo');
INSERT INTO brands (name) VALUES ('Lada (ВАЗ)');
INSERT INTO brands (name) VALUES ('Mercedes-Benz');
INSERT INTO brands (name) VALUES ('Москвич');
INSERT INTO brands (name) VALUES ('УАЗ');
INSERT INTO brands (name) VALUES ('Volvo');
INSERT INTO brands (name) VALUES ('Porsche');
INSERT INTO brands (name) VALUES ('Bentley');
INSERT INTO brands (name) VALUES ('Suzuki');
INSERT INTO brands (name) VALUES ('Solaris');
INSERT INTO brands (name) VALUES ('Tesla');
INSERT INTO brands (name) VALUES ('Citroen');
INSERT INTO brands (name) VALUES ('Dodge');
INSERT INTO brands (name) VALUES ('Jaguar');
INSERT INTO brands (name) VALUES ('Mercury');
INSERT INTO brands (name) VALUES ('Rolls-Royce');
INSERT INTO brands (name) VALUES ('Ferrari');
INSERT INTO brands (name) VALUES ('Changan');
INSERT INTO brands (name) VALUES ('Jeep');
INSERT INTO brands (name) VALUES ('Lamborghini');
INSERT INTO brands (name) VALUES ('Fiat');
INSERT INTO brands (name) VALUES ('Rover');
INSERT INTO brands (name) VALUES ('Cadillac');
INSERT INTO brands (name) VALUES ('Hummer');
INSERT INTO brands (name) VALUES ('Lincoln');
INSERT INTO brands (name) VALUES ('Foton');
INSERT INTO brands (name) VALUES ('Cupra');
INSERT INTO brands (name) VALUES ('Infiniti');
INSERT INTO brands (name) VALUES ('Exeed');
INSERT INTO brands (name) VALUES ('Datsun');
INSERT INTO brands (name) VALUES ('Chrysler');
INSERT INTO brands (name) VALUES ('Maserati');
INSERT INTO brands (name) VALUES ('Volga');
INSERT INTO brands (name) VALUES ('Venturi');

ALTER TABLE auto_posts ADD COLUMN brand_id int REFERENCES brands(id);

UPDATE auto_posts SET brand_id = (SELECT id FROM brands WHERE name = 'Toyota');