DROP VIEW IF EXISTS show_race_admin;
DROP VIEW IF EXISTS count_race_admin;
DROP TABLE JoinRace;
DROP TABLE Activity;
DROP TABLE Race;
DROP TABLE Users;

CREATE TABLE Users (
    id_user SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    isAdmin BIT NOT NULL,
    status BOOLEAN NOT NULL
);

CREATE TABLE Race (
    id_race SERIAL PRIMARY KEY, 
    title VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    time TIME NOT NULL,
    distance DECIMAL (10,2) NOT NULL,
    description VARCHAR(300),
    status BOOLEAN
);

CREATE TABLE Activity (
    id_activity SERIAL PRIMARY KEY, 
    id_user INT,
    title VARCHAR(50) NOT NULL,
    kind VARCHAR(20) NOT NULL,
    distance DECIMAL (10,2) NOT NULL, 
    duration VARCHAR(8),
    date DATE NOT NULL,
    time TIME NOT NULL,
    description VARCHAR(300),
    path_pict VARCHAR(50),
    FOREIGN KEY (id_user) REFERENCES Users (id_user)
);

CREATE TABLE JoinRace (
    id_race INT, 
    id_user INT,
    time TIME,
    path_pict VARCHAR(50),
    status BOOLEAN, 
    FOREIGN KEY (id_race) REFERENCES Race (id_race), 
    FOREIGN KEY (id_user) REFERENCES Users (id_user)
);

CREATE VIEW show_race_admin AS 
select race.id_race, race.title, race.time as race_time, race.distance, users.id_user, users.name, joinrace.time as member_time, joinrace.path_pict, joinrace.status
from joinrace
join race on race.id_race = joinrace.id_race
join users on users.id_user = joinrace.id_user;

CREATE VIEW count_race_admin AS 
select race.id_race, race.title, race.start_date, race.time as race_time, race.distance, race.description, count(joinrace.id_user), race.status
from joinrace
join race on race.id_race = joinrace.id_race
group by race.id_race;