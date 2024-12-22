-- Dummy data for Users
INSERT INTO Users (name, email, password, isAdmin, status) VALUES
-- Admins
('Admin One', 'admin1@example.com', 'admin123', B'1', TRUE),
('Admin Two', 'admin2@example.com', 'admin456', B'1', TRUE),
-- Members
('Adji Ganteng', 'sigantengadji@example.com', 'adji123', B'0', TRUE),
('Cler Ganteng', 'sigantengcler@example.com', 'cler123', B'0', TRUE),
('Edo Cute', 'sicuteedo@example.com', 'edo123', B'0', TRUE),
('Tifanny Imut', 'sitipanoiimut@example.com', 'tifanny123', B'0', TRUE),
('Charlie Davis', 'charlied@example.com', 'charlie123', B'0', TRUE),
('Diana Evans', 'dianae@example.com', 'diana123', B'0', TRUE),
('Ethan Foster', 'ethanf@example.com', 'ethan123', B'0', TRUE),
('Fiona Green', 'fionag@example.com', 'fiona123', B'0', TRUE),
('George Harris', 'georgeh@example.com', 'george123', B'0', TRUE),
('Hannah White', 'hannahw@example.com', 'hannah123', B'0', TRUE);


-- Dummy data for Race (with end_date removed)
INSERT INTO Race (title, start_date, time, distance) VALUES
('Sunrise Marathon', '2024-12-10', '06:00:00', 40.00),
('Twilight Run', '2024-12-08', '18:00:00', 20.00),
('Night Owl Sprint', '2024-12-06', '22:00:00', 5.00),
('City Lights Race', '2024-12-12', '20:00:00', 10.00),
('Beachside Dash', '2024-12-07', '07:30:00', 15.50); 

-- Dummy data for Activity
INSERT INTO Activity (id_user, title, kind, distance, duration, date, time, description) VALUES
-- Activities for Member 1 (id_user = 3)
(3, 'Morning Jog', 'Fun-Run', 5.50, '00:35:00', '2024-12-03', '06:30:00', 'A refreshing morning jog to start the day.'),
(3, 'Evening Marathon Prep', 'Marathon', 25.00, '02:15:00', '2024-12-05', '17:00:00', 'A long run as preparation for an upcoming marathon.'),
(3, 'Park Run', 'Half-Marathon', 12.30, '01:10:00', '2024-12-07', '07:00:00', 'A fun half-marathon at the local park.'),

-- Activities for Member 2 (id_user = 4)
(4, 'City Sprint', 'Fun-Run', 4.80, '00:28:00', '2024-12-04', '08:00:00', 'A quick sprint through the city streets.'),
(4, 'Beach Run', 'Half-Marathon', 14.00, '01:20:00', '2024-12-06', '06:45:00', 'A relaxing run along the beach.'),
(4, 'Training Session', 'Marathon', 30.00, '02:40:00', '2024-12-08', '05:30:00', 'A focused training session for an upcoming race.'),

-- Activities for Member 3 (id_user = 5)
(5, 'Forest Run', 'Fun-Run', 6.50, '00:45:00', '2024-12-03', '07:00:00', 'A run through the forest trail.'),
(5, 'Speed Drill', 'Half-Marathon', 15.00, '01:30:00', '2024-12-05', '06:45:00', 'A speed-focused long run on flat terrain.'),
(5, 'Weekend Marathon Prep', 'Marathon', 30.00, '02:50:00', '2024-12-07', '05:30:00', 'A long-distance run as preparation for a marathon.'),

-- Activities for Member 4 (id_user = 6)
(6, 'Morning Sprint', 'Fun-Run', 5.20, '00:33:00', '2024-12-04', '06:30:00', 'A quick morning sprint to start the day.'),
(6, 'Trail Challenge', 'Half-Marathon', 16.50, '01:40:00', '2024-12-06', '07:00:00', 'A challenging run on a rugged trail.'),
(6, 'City Marathon', 'Marathon', 42.20, '04:20:00', '2024-12-08', '05:45:00', 'A full marathon run through city streets.'),

-- Activities for Member 1 (id_user = 1)
(1, 'Morning Run', 'Fun-Run', 5.00, '00:30:00', '2024-12-02', '06:00:00', 'A quick morning run through the neighborhood.'),
(1, 'Weekend Half-Marathon', 'Half-Marathon', 21.10, '02:00:00', '2024-12-04', '08:00:00', 'A weekend half-marathon for endurance training.'),
(1, 'Evening Marathon', 'Marathon', 42.20, '03:30:00', '2024-12-06', '18:00:00', 'An evening marathon around the city.'),

-- Activities for Member 2 (id_user = 2)
(2, 'Morning Walk', 'Fun-Run', 4.00, '00:25:00', '2024-12-02', '07:00:00', 'A light walk to start the day.'),
(2, 'City Marathon', 'Marathon', 42.20, '03:15:00', '2024-12-05', '16:00:00', 'A marathon through the bustling city streets.'),
(2, 'Track Day', 'Half-Marathon', 21.10, '01:55:00', '2024-12-07', '09:00:00', 'Training day with a half-marathon around the track.'),

-- Activities for Member 7 (id_user = 7)
(7, 'Sunset Jog', 'Fun-Run', 6.00, '00:38:00', '2024-12-03', '18:30:00', 'A relaxing jog at sunset to unwind after work.'),
(7, 'City Park Marathon', 'Marathon', 42.20, '03:45:00', '2024-12-05', '06:00:00', 'A marathon through the beautiful city park.'),
(7, 'Morning Sprint', 'Half-Marathon', 21.10, '02:00:00', '2024-12-07', '07:30:00', 'A sprint session in the morning for endurance.'),

-- Activities for Member 8 (id_user = 8)
(8, 'Forest Run', 'Fun-Run', 4.50, '00:28:00', '2024-12-04', '06:15:00', 'A fun run through the forest trails.'),
(8, 'Beachfront Half-Marathon', 'Half-Marathon', 21.10, '02:05:00', '2024-12-06', '08:00:00', 'Running along the beach for a half-marathon.'),
(8, 'Mountain Marathon', 'Marathon', 42.20, '04:00:00', '2024-12-08', '05:45:00', 'A challenging marathon through mountain paths.'),

-- Activities for Member 9 (id_user = 9)
(9, 'Park Loop', 'Fun-Run', 3.80, '00:22:00', '2024-12-03', '07:00:00', 'A quick loop around the park for a short run.'),
(9, 'Lakeview Half-Marathon', 'Half-Marathon', 21.10, '02:10:00', '2024-12-04', '08:30:00', 'A half-marathon by the lake with stunning views.'),
(9, 'Desert Marathon', 'Marathon', 42.20, '03:50:00', '2024-12-06', '06:30:00', 'A marathon run through the desert with tough terrain.'),

-- Activities for Member 10 (id_user = 10)
(10, 'Evening Jog', 'Fun-Run', 5.00, '00:32:00', '2024-12-02', '19:00:00', 'A relaxed evening jog through the neighborhood.'),
(10, 'Town Marathon', 'Marathon', 42.20, '03:20:00', '2024-12-04', '07:00:00', 'A marathon through the town center with lots of spectators.'),
(10, 'Morning Half-Marathon', 'Half-Marathon', 21.10, '02:00:00', '2024-12-06', '06:30:00', 'A morning half-marathon with scenic views of the city.');

-- Dummy data for JoinRace
INSERT INTO JoinRace (id_race, id_user) VALUES
-- Sunrise Marathon (Upcoming)
(1, 1),
(1, 2),
(1, 3),
-- Twilight Run (Ongoing)
(2, 4),
(2, 5),
(2, 6),
-- City Lights Race (Upcoming)
(4, 10),
(4, 1),
(4, 2),
-- Beachside Dash (Ongoing)
(5, 3),
(5, 4),
(5, 5);

INSERT INTO JoinRace (id_race, id_user, time, status) VALUES
-- Night Owl Sprint (Finished)
(3, 7, '22:00:00', FALSE),
(3, 8, '22:00:00', FALSE),
(3, 9, '22:00:00', FALSE);