
-- One admin user, named admin1 with passwor 4dm1n and authority admin

INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);

-- Creamos la autoridad player con un user
INSERT INTO authorities(id,authority) VALUES (5,'PLAYER');
INSERT INTO appusers(id,username,password,authority) VALUES (25,'player1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',5);
INSERT INTO appusers(id,username,password,authority) VALUES (26,'player2','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',5);
INSERT INTO appusers(id,username,password,authority) VALUES (27,'player3','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',5);
INSERT INTO appusers(id,username,password,authority) VALUES (28,'player4','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',5);
INSERT INTO appusers(id,username,password,authority) VALUES (29,'player5','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',5);
INSERT INTO appusers(id,username,password,authority) VALUES (30,'player6','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',5);

INSERT INTO players(id, first_name, last_name, email, telephone, user_id) VALUES (1, 'Julen', 'Lopetegui', 'lopeteguiJulen@gmail.com', '607555103',25);
INSERT INTO players(id, first_name, last_name, email, telephone, user_id) VALUES (2, 'Unai', 'Emery', 'unaiemery@gmail.com', '625874321', 26);
INSERT INTO players(id, first_name, last_name, email, telephone, user_id) VALUES (3, 'Manuel', 'Ruperto', 'manuelp@gmail.com', '639285174', 27);
INSERT INTO players(id, first_name, last_name, email, telephone, user_id) VALUES (4, 'Cesar', 'Cadaval', 'cesarcadaval@gmail.com', '661794382', 28);
INSERT INTO players(id, first_name, last_name, email, telephone, user_id) VALUES (5, 'Jorge', 'Cadaval', 'jorgecadaval@gmail.com', '654312789', 29);
INSERT INTO players(id, first_name, last_name, email, telephone, user_id) VALUES (6, 'Alessandro', 'Ruperto', 'brevi@gmail.com', '655221133', 30);

-- Three clinic owners, with password "clinic_owner"
INSERT INTO authorities(id,authority) VALUES (2,'CLINIC_OWNER');
INSERT INTO appusers(id,username,password,authority) VALUES (2,'clinicOwner1','$2a$10$t.I/C4cjUdUWzqlFlSddLeh9SbZ6d8wR7mdbeIRghT355/KRKZPAi',2);
INSERT INTO appusers(id,username,password,authority) VALUES (3,'clinicOwner2','$2a$10$t.I/C4cjUdUWzqlFlSddLeh9SbZ6d8wR7mdbeIRghT355/KRKZPAi',2);

INSERT INTO clinic_owners(id,first_name,last_name,user_id) VALUES (1, 'John', 'Doe', 2);
INSERT INTO clinic_owners(id,first_name,last_name,user_id) VALUES (2, 'Jane', 'Doe', 3);

INSERT INTO clinics(id, name, address, telephone, plan, clinic_owner) VALUES (1, 'Clinic 1', 'Av. Palmera, 26', '955684230', 'PLATINUM', 1);
INSERT INTO clinics(id, name, address, telephone, plan, clinic_owner) VALUES (2, 'Clinic 2', 'Av. Torneo, 52', '955634232', 'GOLD', 2);
INSERT INTO clinics(id, name, address, telephone, plan, clinic_owner) VALUES (3, 'Clinic 3', 'Av. Reina Mercedes, 70', '955382238', 'BASIC', 2);

-- Ten owner user, named owner1 with passwor 0wn3r
INSERT INTO authorities(id,authority) VALUES (3,'OWNER');
INSERT INTO appusers(id,username,password,authority) VALUES (4,'owner1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (5,'owner2','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (6,'owner3','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (7,'owner4','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (8,'owner5','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (9,'owner6','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (10,'owner7','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (11,'owner8','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (12,'owner9','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (13,'owner10','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (20,'marcartal1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3); /* CAMBIO */
INSERT INTO appusers(id,username,password,authority) VALUES (21,'lidjimsor','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3); /* CAMBIO */
INSERT INTO appusers(id,username,password,authority) VALUES (22,'natolmvil','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (23,'ignblabla','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);
INSERT INTO appusers(id,username,password,authority) VALUES (24,'adrcabmar','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3);


-- One vet user, named vet1 with passwor v3t
/*INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (12,'vet1','veterinarian');*/
INSERT INTO authorities(id,authority) VALUES (4,'VET');
INSERT INTO appusers(id,username,password,authority) VALUES (14,'vet1','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (15,'vet2','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (16,'vet3','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (17,'vet4','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (18,'vet5','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);
INSERT INTO appusers(id,username,password,authority) VALUES (19,'vet6','$2a$10$aeypcHWSf4YEkDAF0d.vjOLu94aS40MBUb4rOtDncFxZdo2wpkt8.',4);

INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (1, 'James', 'Carter','Sevilla', 1, 14);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (2, 'Helen', 'Leary','Sevilla', 1, 15);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (3, 'Linda', 'Douglas','Sevilla', 2, 16);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (4, 'Rafael', 'Ortega','Badajoz', 2, 17);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (5, 'Henry', 'Stevens','Badajoz', 3, 18);
INSERT INTO vets(id, first_name,last_name,city, clinic, user_id) VALUES (6, 'Sharon', 'Jenkins','Cádiz', 3, 19);

INSERT INTO specialties(id,name) VALUES (1, 'radiology');
INSERT INTO specialties(id,name) VALUES (2, 'surgery');
INSERT INTO specialties(id,name) VALUES (3, 'dentistry');

INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (2, 1);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 2);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (3, 3);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (4, 2);
INSERT INTO vet_specialties(vet_id,specialty_id) VALUES (5, 1);

INSERT INTO types(id,name)  VALUES (1, 'cat');
INSERT INTO types(id,name)  VALUES (2, 'dog');
INSERT INTO types(id,name)  VALUES (3, 'lizard');
INSERT INTO types(id,name)  VALUES (4, 'snake');
INSERT INTO types(id,name)  VALUES (5, 'bird');
INSERT INTO types(id,name)  VALUES (6, 'hamster');
INSERT INTO types(id,name)  VALUES (7, 'turtle');

INSERT INTO	owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Sevilla', '608555103', 4, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sevilla', '608555174', 5, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'Sevilla', '608558763', 6, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Sevilla', '608555319', 7, 2);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Sevilla', '608555765', 8, 2);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Badajoz', '608555264', 9, 2);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Badajoz', '608555538', 10, 3);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Badajoz', '608557683', 11, 3);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail','Cádiz', '685559435', 12, 3);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Cádiz', '685555487', 13, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (11, 'María', 'Carrera', '34 Av. Reina Mercedes', 'Sevilla', '685555487', 20, 3); /* CAMBIO */
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (12, 'Lidia', 'Jimenez', '37 Av. Reina Mercedes', 'Sevilla', '685555487', 21, 3); /* CAMBIO */
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (13, 'Natalia', 'Olmo', '18 Av. de las Ciencias', 'Sevilla', '685555488', 22, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (14, 'Ignacio', 'Blanquero', '75 Av. Reina Mercedes', 'Sevilla', '685555567', 23, 1);
INSERT INTO owners(id, first_name, last_name, address, city, telephone, user_id, clinic) VALUES (15, 'Adrian', 'Cabello', '75 Av. Reina Mercedes', 'Sevilla', '644888533', 24, 1);

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Risketos', '2020-08-17', 1, 11); /* CAMBIO */
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Ali', '2023-07-07', 1, 11); /* CAMBIO */
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Oli', '2023-07-07', 1, 11); /* CAMBIO */
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'Bella', '2013-02-17', 2, 12); /* CAMBIO */
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'Dino', '2023-07-07', 2, 12); /* CAMBIO */
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (19, 'Piolin', '2023-07-07', 2, 12); /* CAMBIO */
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (20, 'Toby', '2021-04-17', 2, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (21, 'Curro', '2018-12-25', 5, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (22, 'Wasi', '2022-03-21', 7, 14);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (23, 'Ginebra', '2021-10-07', 7, 14);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (24, 'Granuja', '2017-06-17', 5, 15);


INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (1, 7, '2013-01-01 13:00', 'rabies shot', 4);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (2, 8, '2013-01-02 15:30', 'rabies shot', 5);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (3, 8, '2013-01-03 9:45', 'neutered', 5);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (4, 7, '2013-01-04 17:30', 'spayed', 4);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (5, 1, '2013-01-01 13:00', 'rabies shot', 1);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (6, 1, '2020-01-02 15:30', 'rabies shot', 1);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (7, 1, '2020-01-02 15:30', 'rabies shot', 1);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (8, 2, '2013-01-03 9:45', 'neutered', 2);
INSERT INTO visits(id,pet_id,visit_date_time,description,vet_id) VALUES (9, 3, '2013-01-04 17:30', 'spayed', 3);

INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (1, 'Consultation about vaccines', 0, 'ANSWERED', 1, 1, '2023-01-04 17:30');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (2, 'My dog gets really nervous', 0, 'PENDING', 1, 1, '2022-01-02 19:30');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (3, 'My cat does not eat', 0, 'PENDING', 2, 2, '2023-04-11 11:20');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (4, 'My lovebird does not sing', 0, 'CLOSED', 2, 2, '2023-02-24 10:30');
INSERT INTO consultations(id,title, is_clinic_comment,status,owner_id,pet_id,creation_date) VALUES (5, 'My snake has layed eggs', 0, 'PENDING', 10, 12, '2023-04-11 11:20');

INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (1, 'What vaccine should my dog receive?', '2023-01-04 17:32', 4, 1);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (2, 'Rabies'' one.', '2023-01-04 17:36', 14, 1);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (3, 'My dog gets really nervous during football matches. What should I do?', '2022-01-02 19:31', 4, 2);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (4, 'It also happens with tennis matches.', '2022-01-02 19:33', 4, 2);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (5, 'My cat han''t been eating his fodder.', '2023-04-11 11:30', 5, 3);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (6, 'Try to give him some tuna to check if he eats that.', '2023-04-11 15:20', 15, 3);
INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (7, 'My lovebird doesn''t sing as my neighbour''s one.', '2023-02-24 12:30', 5, 4);

INSERT INTO consultation_tickets(id,description,creation_date, user_id, consultation_id) VALUES (8, 'Lovebirds do not sing.', '2023-02-24 18:30', 16, 4);


--Creamos los juegos

 INSERT INTO game(id,is_competitive,state,time_limit,start_date, finish_date, game_code, limited_by_round, round_limit ) VALUES (1, TRUE,  'FINISHED', null, '2013-01-01 13:50:30', '2013-01-01 14:00:30',  'st5k1', TRUE, 3);
 INSERT INTO game(id,is_competitive,state,time_limit,start_date, finish_date, game_code, limited_by_round, round_limit ) VALUES (2, TRUE,  'ONGOING', 5, '2023-01-01 14:00:25', '2023-01-01 14:20:03', 'nk9m3', FALSE, null);
 INSERT INTO game(id,is_competitive,state,time_limit,start_date, finish_date, game_code, limited_by_round, round_limit ) VALUES (3, TRUE,  'UNSTARTED', 5, null, null, 'hola1', FALSE, null);

 INSERT INTO game_properties(id, game_points, is_game_winner, is_creator, game_id, player_id) VALUES (2, 100, TRUE,FALSE, 1, 2), (3, 100, TRUE,FALSE, 1, 3),(4, 100, TRUE,FALSE, 2, 4), (5, 100, TRUE,FALSE, 2, 5), (6, 100, TRUE,FALSE, 2, 1);

INSERT INTO round(id,minigame,is_on_going) VALUES (1, 'TORREINFERNAL', TRUE);
INSERT INTO round(id,minigame,is_on_going) VALUES (2, 'PATATACALIENTE', TRUE);
INSERT INTO round(id,minigame,is_on_going) VALUES (3, 'TORREINFERNAL', TRUE);
INSERT INTO round(id,minigame,is_on_going) VALUES (4, 'PATATACALIENTE', TRUE);
INSERT INTO round(id,minigame,is_on_going) VALUES (5, 'TORREINFERNAL', TRUE);

-- INSERT INTO round_properties(id, rounds_points, is_round_winner, round_id, player_id) VALUES 
-- (1, 20, TRUE, 1, 1), (2, 50, TRUE, 2, 2), (3, 10, TRUE, 1, 3), (4, 15, TRUE, 1, 4), (5, 5, TRUE, 1, 5);

-- Symbols
INSERT INTO symbol(id, name) VALUES (1, 'ABEJA');
INSERT INTO symbol(id, name) VALUES (2, 'AGUILA');
INSERT INTO symbol(id, name) VALUES (3, 'ALPACA');
INSERT INTO symbol(id, name) VALUES (4, 'ARDILLA');
INSERT INTO symbol(id, name) VALUES (5, 'AVISPA');
INSERT INTO symbol(id, name) VALUES (6, 'BALLENA');
INSERT INTO symbol(id, name) VALUES (7, 'BUHO');
INSERT INTO symbol(id, name) VALUES (8, 'CABALLO');
INSERT INTO symbol(id, name) VALUES (9, 'CANGREJO');
INSERT INTO symbol(id, name) VALUES (10, 'CANGURO');
INSERT INTO symbol(id, name) VALUES (11, 'CARACOL');
INSERT INTO symbol(id, name) VALUES (12, 'CEBRA');
INSERT INTO symbol(id, name) VALUES (13, 'CIGUENA');
INSERT INTO symbol(id, name) VALUES (14, 'CISNE');
INSERT INTO symbol(id, name) VALUES (15, 'COCODRILO');
INSERT INTO symbol(id, name) VALUES (16, 'CONEJO');
INSERT INTO symbol(id, name) VALUES (17, 'DELFIN');
INSERT INTO symbol(id, name) VALUES (18, 'ELEFANTE');
INSERT INTO symbol(id, name) VALUES (19, 'FLAMENCO');
INSERT INTO symbol(id, name) VALUES (20, 'FOCA');
INSERT INTO symbol(id, name) VALUES (21, 'GALLINA');
INSERT INTO symbol(id, name) VALUES (22, 'GAMBA');
INSERT INTO symbol(id, name) VALUES (23, 'GATO');
INSERT INTO symbol(id, name) VALUES (24, 'GORRINO');
INSERT INTO symbol(id, name) VALUES (25, 'GRILLO');
INSERT INTO symbol(id, name) VALUES (26, 'HAMSTER');
INSERT INTO symbol(id, name) VALUES (27, 'HURON');
INSERT INTO symbol(id, name) VALUES (28, 'KIWI');
INSERT INTO symbol(id, name) VALUES (29, 'LEON');
INSERT INTO symbol(id, name) VALUES (30, 'LOBO');
INSERT INTO symbol(id, name) VALUES (31, 'LORO');
INSERT INTO symbol(id, name) VALUES (32, 'MARIPOSA');
INSERT INTO symbol(id, name) VALUES (33, 'MARIQUITA');
INSERT INTO symbol(id, name) VALUES (34, 'ORCA');
INSERT INTO symbol(id, name) VALUES (35, 'OSO');
INSERT INTO symbol(id, name) VALUES (36, 'OVEJA');
INSERT INTO symbol(id, name) VALUES (37, 'PAJARO');
INSERT INTO symbol(id, name) VALUES (38, 'PALOMA');
INSERT INTO symbol(id, name) VALUES (39, 'PANDA');
INSERT INTO symbol(id, name) VALUES (40, 'PAVOREAL');
INSERT INTO symbol(id, name) VALUES (41, 'PEREZOSO');
INSERT INTO symbol(id, name) VALUES (42, 'PERRO');
INSERT INTO symbol(id, name) VALUES (43, 'PEZ');
INSERT INTO symbol(id, name) VALUES (44, 'PINGUINO');
INSERT INTO symbol(id, name) VALUES (45, 'PULPO');
INSERT INTO symbol(id, name) VALUES (46, 'RANA');
INSERT INTO symbol(id, name) VALUES (47, 'RATA');
INSERT INTO symbol(id, name) VALUES (48, 'RENO');
INSERT INTO symbol(id, name) VALUES (49, 'RINOCERONTE');
INSERT INTO symbol(id, name) VALUES (50, 'SERPIENTE');
INSERT INTO symbol(id, name) VALUES (51, 'SINSAJO');
INSERT INTO symbol(id, name) VALUES (52, 'STAR');
INSERT INTO symbol(id, name) VALUES (53, 'TIGRE');
INSERT INTO symbol(id, name) VALUES (54, 'TORTUGA');
INSERT INTO symbol(id, name) VALUES (55, 'UNICORNIO');
INSERT INTO symbol(id, name) VALUES (56, 'VACA');
INSERT INTO symbol(id, name) VALUES (57, 'ZORRO');

-- Cards
INSERT INTO card(id) VALUES (1);
INSERT INTO card(id) VALUES (2);
INSERT INTO card(id) VALUES (3);
INSERT INTO card(id) VALUES (4);
INSERT INTO card(id) VALUES (5);
INSERT INTO card(id) VALUES (6);
INSERT INTO card(id) VALUES (7);
INSERT INTO card(id) VALUES (8);
INSERT INTO card(id) VALUES (9);
INSERT INTO card(id) VALUES (10);
-- INSERT INTO card(id) VALUES (11);
-- INSERT INTO card(id) VALUES (12);
-- INSERT INTO card(id) VALUES (13);
-- INSERT INTO card(id) VALUES (14);
-- INSERT INTO card(id) VALUES (15);
-- INSERT INTO card(id) VALUES (16);
-- INSERT INTO card(id) VALUES (17);
-- INSERT INTO card(id) VALUES (18);
-- INSERT INTO card(id) VALUES (19);
-- INSERT INTO card(id) VALUES (20);
-- INSERT INTO card(id) VALUES (21);
-- INSERT INTO card(id) VALUES (22);
-- INSERT INTO card(id) VALUES (23);
-- INSERT INTO card(id) VALUES (24);
-- INSERT INTO card(id) VALUES (25);
-- INSERT INTO card(id) VALUES (26);
-- INSERT INTO card(id) VALUES (27);
-- INSERT INTO card(id) VALUES (28);
-- INSERT INTO card(id) VALUES (29);
-- INSERT INTO card(id) VALUES (30);
-- INSERT INTO card(id) VALUES (31);
-- INSERT INTO card(id) VALUES (32);
-- INSERT INTO card(id) VALUES (33);
-- INSERT INTO card(id) VALUES (34);
-- INSERT INTO card(id) VALUES (35);
-- INSERT INTO card(id) VALUES (36);
-- INSERT INTO card(id) VALUES (37);
-- INSERT INTO card(id) VALUES (38);
-- INSERT INTO card(id) VALUES (39);
-- INSERT INTO card(id) VALUES (40);
-- INSERT INTO card(id) VALUES (41);
-- INSERT INTO card(id) VALUES (42);
-- INSERT INTO card(id) VALUES (43);
-- INSERT INTO card(id) VALUES (44);
-- INSERT INTO card(id) VALUES (45);
-- INSERT INTO card(id) VALUES (46);
-- INSERT INTO card(id) VALUES (47);
-- INSERT INTO card(id) VALUES (48);
-- INSERT INTO card(id) VALUES (49);
-- INSERT INTO card(id) VALUES (50);
-- INSERT INTO card(id) VALUES (51);
-- INSERT INTO card(id) VALUES (52);
-- INSERT INTO card(id) VALUES (53);
-- INSERT INTO card(id) VALUES (54);
-- INSERT INTO card(id) VALUES (55);

-- -- Card symbols
INSERT INTO card_symbols(card_id, symbols_id) VALUES (1,29), (1,22), (1,15), (1,8), (1,57), (1,43), (1,36), (1,1);
INSERT INTO card_symbols(card_id, symbols_id) VALUES (2,9), (2,57), (2,44), (2,37), (2,30), (2,23), (2,16), (2,2);
INSERT INTO card_symbols(card_id, symbols_id) VALUES (3,31), (3,24), (3,17), (3,10), (3,57), (3,45), (3,38), (3,3);
INSERT INTO card_symbols(card_id, symbols_id) VALUES (4,11), (4,57), (4,46), (4,39), (4,32), (4,25), (4,18), (4,4);
INSERT INTO card_symbols(card_id, symbols_id) VALUES (5,40), (5,33), (5,26), (5,19), (5,12), (5,57), (5,47), (5,5);
INSERT INTO card_symbols(card_id, symbols_id) VALUES (6,13), (6,20), (6,57), (6,48), (6,6), (6,41), (6,34), (6,27);
INSERT INTO card_symbols(card_id, symbols_id) VALUES (7,14), (7,57), (7,21), (7,7), (7,49), (7,42), (7,28), (7,35);
INSERT INTO card_symbols(card_id, symbols_id) VALUES (8,6), (8,5), (8,7), (8,4), (8,1), (8,50), (8,3), (8,2);
INSERT INTO card_symbols(card_id, symbols_id) VALUES (9,50), (9,9), (9,10), (9,14), (9,13), (9,8), (9,11), (9,12);
INSERT INTO card_symbols(card_id, symbols_id) VALUES (10,16), (10,50), (10,21), (10,20), (10,19), (10,18), (10,17), (10,15);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (11,26), (11,25), (11,24), (11,23), (11,50), (11,28), (11,27), (11,22);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (12,30), (12,50), (12,35), (12,34), (12,33), (12,32), (12,31), (12,29);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (13,40), (13,39), (13,38), (13,37), (13,50), (13,42), (13,41), (13,36);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (14,44), (14,50), (14,49), (14,48), (14,47), (14,46), (14,45), (14,43);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (15,33), (15,25), (15,17), (15,9), (15,51), (15,49), (15,41), (15,1);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (16,16), (16,51), (16,7), (16,48), (16,40), (16,32), (16,24), (16,8);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (17,47), (17,39), (17,31), (17,23), (17,51), (17,14), (17,6), (17,15);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (18,30), (18,51), (18,21), (18,13), (18,5), (18,46), (18,38), (18,22);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (19,12), (19,4), (19,45), (19,37), (19,51), (19,28), (19,20), (19,29);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (20,44), (20,51), (20,35), (20,27), (20,19), (20,11), (20,3), (20,36);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES  (21,26), (21,18), (21,10), (21,2), (21,51), (21,42), (21,34), (21,43);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES  (22,16), (22,52), (22,42), (22,27), (22,12), (22,46), (22,31), (22,1);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES  (23,19), (23,4), (23,38), (23,23), (23,52), (23,49), (23,34), (23,8);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES  (24,30), (24,7), (24,41), (24,26), (24,52), (24,11), (24,45), (24,15);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES  (25,33), (25,18), (25,3), (25,37), (25,52), (25,14), (25,48), (25,22);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES  (26,44), (26,52), (26,21), (26,6), (26,40), (26,25), (26,10), (26,29);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES  (27,47), (27,32), (27,17), (27,2), (27,52), (27,28), (27,13), (27,36);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES  (28,9), (28,52), (28,35), (28,20), (28,5), (28,39), (28,24), (28,43);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES  (29,40), (29,18), (29,45), (29,23), (29,53), (29,35), (29,1), (29,13);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (30,5), (30,32), (30,10), (30,37), (30,53), (30,49), (30,27), (30,15);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (31,44), (31,53), (31,7), (31,34), (31,12), (31,39), (31,17), (31,22);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (32,9), (32,53), (32,21), (32,48), (32,26), (32,4), (32,31), (32,36);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (33,33), (33,11), (33,38), (33,16), (33,53), (33,28), (33,6), (33,43);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (34,30), (34,54), (34,28), (34,48), (34,19), (34,39), (34,10), (34,1);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (35,26), (35,46), (35,17), (35,37), (35,54), (35,35), (35,6), (35,8);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (36,44), (36,54), (36,42), (36,13), (36,33), (36,4), (36,24), (36,15);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (37,40), (37,11), (37,31), (37,2), (37,54), (37,49), (37,20), (37,22);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (38,9), (38,54), (38,7), (38,27), (38,47), (38,18), (38,38), (38,29);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (39,5), (39,25), (39,45), (39,16), (39,54), (39,14), (39,34), (39,36);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (40,23), (40,54), (40,21), (40,41), (40,12), (40,32), (40,3), (40,43);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (41,47), (41,11), (41,24), (41,37), (41,55), (41,21), (41,34), (41,1);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (42,44), (42,55), (42,28), (42,41), (42,5), (42,18), (42,31), (42,8);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (43,12), (43,25), (43,38), (43,2), (43,55), (43,35), (43,48), (43,15);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (44,9), (44,55), (44,42), (44,6), (44,19), (44,32), (44,45), (44,22);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (45,26), (45,39), (45,3), (45,16), (45,55), (45,49), (45,13), (45,29);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (46,23), (46,55), (46,7), (46,20), (46,33), (46,46), (46,10), (46,36);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (47,40), (47,4), (47,17), (47,30), (47,55), (47,14), (47,27), (47,43);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (48,44), (48,56), (48,14), (48,20), (48,26), (48,32), (48,38), (48,1);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (49,33), (49,39), (49,45), (49,3), (49,56), (49,21), (49,27), (49,8);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (50,9), (50,56), (50,28), (50,34), (50,40), (50,46), (50,3), (50,15);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (51,41), (51,47), (51,4), (51,10), (51,16), (51,56), (51,35), (51,22);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (52,23), (52,56), (52,42), (52,48), (52,5), (52,11), (52,17), (52,29);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (53,6), (53,12), (53,18), (53,24), (53,30), (53,56), (53,49), (53,36);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (54,37), (54,56), (54,7), (54,13), (54,19), (54,25), (54,31), (54,43);
-- INSERT INTO card_symbols(card_id, symbols_id) VALUES (55,51), (55,57), (55,56), (55,55), (55,54), (55,53), (55,52), (55,50);

