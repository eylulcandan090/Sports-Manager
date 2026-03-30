CREATE TABLE Sport (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE Team (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    sport_id INTEGER,
    FOREIGN KEY (sport_id) REFERENCES Sport(id)
);

CREATE TABLE Player (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    team_id INTEGER,
    rating INTEGER,
    FOREIGN KEY (team_id) REFERENCES Team(id)
);
