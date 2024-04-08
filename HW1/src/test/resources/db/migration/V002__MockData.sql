--user
insert into users (username, pwd,tkn) values ('Bruno', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0');

--trip
insert into trip (bus_number,trip_date,from_time,to_time,from_location,to_location) values (1,'2024-04-06','21:35','22:30','Porto','Aveiro');
insert into trip (bus_number,trip_date,from_time,to_time,from_location,to_location) values (1,'2024-04-06','19:35','20:30','Porto','Aveiro');
insert into trip (bus_number,trip_date,from_time,to_time,from_location,to_location) values (2,'2024-04-06','12:30','13:15','Aveiro','Figueira da Foz');

--booking
insert into reservation (username,trip,seat) values ('Bruno',1,11);

    