CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(256) PRIMARY KEY,
  pwd VARCHAR(256),
  tkn VARCHAR(256)
);

create table IF NOT EXISTS trip (
    id BIGSERIAL PRIMARY KEY,
    bus_number int,
    trip_date DATE,
    from_time TIME,
    to_time TIME,
    from_location VARCHAR(64),
    to_location VARCHAR(64),
    num_seats INT DEFAULT(45)
);

create table reservation (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(256),
    trip BIGSERIAL,seat INT);
