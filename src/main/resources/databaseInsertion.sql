INSERT INTO expositions_calendar_spring.users(email, password, name, surname, role) VALUES ("admin@gmail.com",  "$2a$10$sng05kSz2o2nNs6RYXzROOs1lcoTIrPlhUs8/8wIn0ncJm.Tlz1CW", "Admin",  "Admin", "ADMIN"),
("user@gmail.com",  "$2a$10$PnCxT8sZgPhpZ6yzIDMxIe5tj2e2PWx9wuul59HSCyxFzwYyxnwpq", "Jack",  "Blevis", "USER"),
("user1@gmail.com",  "$2a$10$m8AYa7avgsRJdHU1O3xTCu3kfVPF9qbocTF4GPV5HcUUs/F4Syb.K", "Arthur",  "Hofman", "USER");

INSERT INTO expositions_calendar_spring.halls (name ,city, street, house_number) VALUES ("International Exhibition Centre", "Kyiv", "Brovarskyi Ave", 15),
("Expo Center of Ukraine", "Kyiv", "Academica Glushkova Avenue", 1);

INSERT INTO expositions_calendar_spring.expositions(title, theme, start_date, end_date, ticket_price, description, hall_id) VALUES ("IDDM", "Informatics and Medicine", "2019-11-5", "2019-12-23"
, 80.25, "The International Seminar on Computer Science and Medicine, Data-Driven IDDM, focuses on all the technical and practical aspects of the latest research
 and the results of international academics, scientists and practitioners related to the topics of intellectual medical data processing.", 1),
 ("EuroAgro", "Agricultural Industry", "2019-12-4", "2020-01-14", 40.50,
 "EuroAGRO is a professional platform where domestic producers have the opportunity to demonstrate their achievements in the field
 of agriculture and get acquainted with the latest technologies and solutions of the European agricultural market", 2);

 INSERT INTO expositions_calendar_spring.payments(payment_time, status, tickets_amount, price, user_id, exposition_id) VALUES("2019-11-8 13:31:18", "PASSED", 3, 240.75, 2, 1),
("2019-11-9 15:24:45", "PASSED", 4, 162, 3, 2),
("2019-11-10 21:13:28", "FAILED", 1, 80.25, 2, 2),
("2019-11-7 9:31:25", "FAILED", 2, 81, 3, 1);

INSERT INTO expositions_calendar_spring.tickets (valid_date, user_id, payment_id, exposition_id) VALUES ("2019-11-23", 2, 1, 1),
("2019-11-23", 2, 1, 1),
("2019-11-23", 2, 1, 1),
("2019-12-28", 3, 2, 2),
("2019-12-28", 3, 2, 2),
("2019-12-28", 3, 2, 2),
("2019-12-28", 3, 2, 2);