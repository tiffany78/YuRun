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
    distance DECIMAL (10,2) NOT NULL
);

CREATE TABLE Activity (
    id_activity SERIAL PRIMARY KEY, 
    id_user INT,
    title VARCHAR(50) NOT NULL,
    kind VARCHAR(20) NOT NULL,
    distance DECIMAL (10,2) NOT NULL, 
    duration INTERVAL NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    description VARCHAR(300), 
    image_path VARCHAR(255),
    FOREIGN KEY (id_user) REFERENCES Users (id_user)
);

CREATE TABLE JoinRace (
    id_race INT, 
    id_user INT,
    time TIME,
    image_path VARCHAR(255),
    status BOOLEAN, 
    FOREIGN KEY (id_race) REFERENCES Race (id_race), 
    FOREIGN KEY (id_user) REFERENCES Users (id_user)
);