CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(256) PRIMARY KEY,
  pwd VARCHAR(256),
  tkn VARCHAR(256)
);

create table IF NOT EXISTS trip (
    id BIGSERIAL PRIMARY KEY,
    busNumber int,
    tripDate DATE,
    fromTime TIME,
    toTime TIME,
    fromLocation VARCHAR(64),
    toLocation VARCHAR(64),
    numSeats INT DEFAULT(45)
);

create table reservation (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(256),
    trip BIGSERIAL,seat INT);
