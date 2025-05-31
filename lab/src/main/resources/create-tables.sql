CREATE TABLE IF NOT EXISTS countries
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE SEQUENCE IF NOT EXISTS coordinates_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS coordinates
(
    id BIGINT DEFAULT nextval('coordinates_id_seq') PRIMARY KEY,
    x  INT   NOT NULL,
    y  FLOAT NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS users_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT DEFAULT nextval('users_id_seq') PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS location_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS locations
(
    id   BIGINT DEFAULT nextval('location_id_seq') PRIMARY KEY,
    x    DOUBLE PRECISION NOT NULL,
    y    DOUBLE PRECISION NOT NULL,
    z    INTEGER          NOT NULL,
    name VARCHAR(255)     NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS person_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS persons
(
    id             BIGINT DEFAULT nextval('person_id_seq') PRIMARY KEY,
    key            BIGINT       NOT NULL,
    name           VARCHAR(255) NOT NULL,
    coordinates_id BIGINT       NOT NULL,
    creation_date  DATE         NOT NULL,
    height         BIGINT       NOT NULL,
    birthday       TIMESTAMP    NOT NULL,
    passport_id    VARCHAR(33)  NOT NULL,
    location_id    BIGINT,
    country_id     BIGINT       NOT NULL,
    owner_id       BIGINT       NOT NULL,
    FOREIGN KEY (coordinates_id) REFERENCES coordinates (id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE,
    FOREIGN KEY (country_id) REFERENCES countries (id),
    FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);


INSERT INTO countries (id, name)
VALUES (1, 'GERMANY'),
       (2, 'FRANCE'),
       (3, 'JAPAN');