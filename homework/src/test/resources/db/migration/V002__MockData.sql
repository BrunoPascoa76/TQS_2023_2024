--user
insert into users (username, pwd) values ('Bruno', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');

--trip
insert into trip (busNumber,tripDate,fromTime,toTime,fromLocation,toLocation) values (1,'2024-04-06','21:35','22:30','Porto','Aveiro');
insert into trip (busNumber,tripDate,fromTime,toTime,fromLocation,toLocation) values (1,'2024-04-06','19:35','20:30','Porto','Aveiro');
insert into trip (busNumber,tripDate,fromTime,toTime,fromLocation,toLocation) values (2,'2024-04-06','12:30','13:15','Aveiro','Figueira da Foz');

--booking
insert into reservation (username,trip,seat) values ('Bruno',1,11);

    