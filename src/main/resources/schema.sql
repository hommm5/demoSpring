DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS "address";
DROP TABLE IF EXISTS "geo";
DROP TABLE IF EXISTS "company";

CREATE TABLE IF NOT EXISTS Geo(
    id SERIAL PRIMARY KEY,
    lat varchar(255) NOT NULL,
    lng varchar(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Address(
    id SERIAL PRIMARY KEY,
    street varchar(255) NOT NULL,
    suite varchar(255) NOT NULL,
    city varchar(255) NOT NULL,
    zipcode varchar(255) NOT NULL,
    geo_id INT REFERENCES Geo(id)
);

CREATE TABLE IF NOT EXISTS Company(
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    catchPhrase varchar(255) NOT NULL,
    bs varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "user"(
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    address_id INT REFERENCES Address(id),
    company_id INT REFERENCES Company(id)
);

CREATE TABLE IF NOT EXISTS Run(
    id INT NOT NULL,
    title varchar(255) NOT NULL,
    started_on timestamp NOT NULL,
    completed_on timestamp NOT NULL,
    miles INT NOT NULL,
    location varchar(10) NOT NULL,
    version INT,
    PRIMARY KEY (id)
);