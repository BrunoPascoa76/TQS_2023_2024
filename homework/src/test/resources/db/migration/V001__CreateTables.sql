create table user (
	username VARCHAR(256) PRIMARY KEY,
	[password] VARCHAR(256)
);

create table trip (
	id BIGSERIAL PRIMARY KEY IDENTITY(1,1),
	busNumber int,
    [date] DATE,
    fromTime TIME,
    toTime TIME,
    fromLocation VARCHAR(64),
    toLocation VARCHAR(64),
    numSeats INT DEFAULT(45)
);

create table reservation (
    id BIGSERIAL PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(256) FOREIGN KEY REFERENCES [user](username),
    trip BIGSERIAL FOREIGN KEY REFERENCES trip(id),
    seat INT
);
