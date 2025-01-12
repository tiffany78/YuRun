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
INSERT INTO Race (title, end_date, distance, status, description) VALUES
('Sunrise Marathon', '2025-01-13', 40.00, FALSE, 'Run through scenic sunrise views.'),
('Twilight Run', '2025-01-07', 20.00, FALSE, 'An evening run with a beautiful twilight backdrop.'),
('Night Owl Sprint', '2024-12-06', 5.00, FALSE, 'A short and fast sprint for night runners.'),
('City Lights Race', '2024-12-12', 10.00, FALSE, 'Experience the city glow as you race under the lights.'),
('Beachside Dash', '2025-01-25', 15.50, FALSE, 'A refreshing dash along the serene beachside.');

-- Dummy data for Activity
INSERT INTO Activity (id_user, title, kind, distance, duration, date, time, description, path_pict) VALUES
-- Activities for Member 1 (id_user = 3)
(3, 'Morning Jog', 'Fun-Run', 5.50, '00:35:00', '2024-12-03', '06:30:00', 'A refreshing morning jog to start the day.', 'laripagi.jpg'),
(3, 'Evening Marathon Prep', 'Marathon', 25.00, '02:15:00', '2024-12-05', '17:00:00', 'A long run as preparation for an upcoming marathon.', 'larisore.jpg'),
(3, 'Park Run', 'Half-Marathon', 12.30, '01:10:00', '2024-12-07', '07:00:00', 'A fun half-marathon at the local park.', 'laritaman.jpg');

INSERT INTO Activity (id_user, title, kind, distance, duration, date, time, description) VALUES
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
INSERT INTO JoinRace (id_race, id_user, status) VALUES
-- Beachside Dash (Ongoing)
(5, 3, TRUE),
(5, 4, TRUE),
(5, 5, TRUE);

INSERT INTO JoinRace (id_race, id_user, duration, status, path_pict) VALUES
-- Sunrise Marathon
(1, 7, '3:10:00', TRUE, 'sunrise1.jpg'),
(1, 8, '4:05:10', TRUE, 'sunrise2.jpg'),
(1, 9, '4:56:50', TRUE, 'sunrise3.jpg'),
-- Twilight Run
(2, 4, '1:30:55', TRUE, 'twilight1.jpg'),
(2, 5, '1:40:50', TRUE, 'twilight2.jpg'),
(2, 6, '2:20:50', TRUE, 'twilight3.jpg'),
-- City Lights Race
(4, 10, '0:30:50', TRUE, 'city1.jpg'),
(4, 4, '0:48:50', TRUE, 'city2.jpg'),
(4, 5, '0:37:38', TRUE, 'city3.jpg'),
-- Night Owl Sprint
(3, 7, '1:10:00', TRUE, 'night1.jpg'),
(3, 8, '1:05:10', TRUE, 'night2.jpg'),
(3, 9, '0:59:50', TRUE, 'night3.jpg');

-- BEBERAPA BUTUH GAMBAR
INSERT INTO Activity (id_user, title, kind, distance, duration, date, time, description) VALUES
(3, 'Morning Jog', 'Fun-Run', 5.50, '00:35:00', '2024-12-03', '06:30:00', 'A refreshing morning jog to start the day.'),
(3, 'Evening Marathon Prep', 'Marathon', 25.00, '02:15:00', '2024-12-05', '17:00:00', 'A long run as preparation for an upcoming marathon.'),
(3, 'Park Run', 'Half-Marathon', 12.30, '01:10:00', '2024-12-07', '07:00:00', 'A fun half-marathon at the local park.'),
(3, 'Trail Run', 'Fun-Run', 8.20, '00:50:00', '2024-12-08', '06:45:00', 'A challenging trail run with scenic views.'),
(3, 'Interval Training', 'Half-Marathon', 6.00, '00:30:00', '2024-12-10', '18:30:00', 'High-intensity interval training to improve speed.'),
(3, 'Sunset Beach Run', 'Fun-Run', 7.50, '00:45:00', '2024-12-12', '17:15:00', 'Relaxing run along the beach during sunset.'),
(3, 'Long Weekend Run', 'Marathon', 30.00, '02:40:00', '2024-12-14', '06:00:00', 'Endurance run for the weekend.');

INSERT INTO Activity (id_user, title, kind, distance, duration, date, time, description) VALUES
(3, 'Morning Tempo Run', 'Fun-Run', 10.00, '00:55:00', '2024-12-15', '06:15:00', 'Steady tempo run to build stamina.'),
(3, 'Evening Fun-Run', 'Fun-Run', 5.00, '00:30:00', '2024-12-16', '17:30:00', 'Short and easy evening fun-run.'),
(3, 'Pre-Marathon Long Run', 'Marathon', 28.00, '02:20:00', '2024-12-18', '07:00:00', 'Preparation for the marathon event.'),
(3, 'Community Park Run', 'Half-Marathon', 13.10, '01:15:00', '2024-12-20', '08:00:00', 'A community-organized half-marathon.'),
(3, 'Hill Training', 'Fun-Run', 7.00, '00:40:00', '2024-12-21', '06:00:00', 'Hill running to build strength and endurance.'),
(3, 'City Lights Run', 'Fun-Run', 6.50, '00:38:00', '2024-12-22', '19:00:00', 'Evening run through the city lights.'),
(3, 'Holiday Marathon', 'Marathon', 26.20, '02:30:00', '2024-12-24', '07:30:00', 'Full marathon for the holiday season.'),
(3, 'Track and Field Practice', 'Half-Marathon', 9.50, '00:50:00', '2024-12-25', '06:30:00', 'Track run to improve pacing and speed.'),
(3, 'Sunrise Run', 'Fun-Run', 5.80, '00:35:00', '2024-12-26', '06:00:00', 'Morning run to enjoy the sunrise.'),
(3, 'Weekly Long Run', 'Marathon', 27.50, '02:25:00', '2024-12-27', '07:00:00', 'Weekly endurance training.'),
(3, 'Park Jogging', 'Fun-Run', 4.20, '00:25:00', '2024-12-28', '08:30:00', 'Relaxed jog at the park.'),
(3, 'Weekend Half-Marathon', 'Half-Marathon', 13.50, '01:18:00', '2024-12-29', '06:00:00', 'Half-marathon for weekend workout.'),
(3, 'Evening Breeze Run', 'Fun-Run', 6.70, '00:37:00', '2024-12-30', '17:45:00', 'Cool evening run after work.'),
(3, 'Marathon Endurance Run', 'Marathon', 29.00, '02:35:00', '2024-12-31', '06:15:00', 'Endurance-focused marathon practice.'),
(3, 'Morning Sprint Training', 'Fun-Run', 3.00, '00:20:00', '2025-01-01', '06:45:00', 'Sprint intervals in the morning.'),
(3, 'Evening Relax Run', 'Fun-Run', 5.50, '00:32:00', '2025-01-02', '18:00:00', 'Relaxing evening jog to wind down.'),
(3, 'City Marathon', 'Marathon', 26.20, '02:45:00', '2025-01-03', '06:30:00', 'Marathon through the city streets.'),
(3, 'Weekly Half-Marathon', 'Half-Marathon', 13.10, '01:12:00', '2025-01-04', '07:00:00', 'Half-marathon for weekly stamina check.');
