-- CREATING DATABASE --

CREATE DATABASE movieManager;


-- CREATING TABLES --

CREATE TABLE movie (
    movieId SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    director VARCHAR(255) NOT NULL,
    year INTEGER NOT NULL,
    duration INTEGER NOT NULL,
    genre VARCHAR(255) NOT NULL,
    classification VARCHAR(255) NOT NULL,
    rating INTEGER NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE userInfo (
    userId SERIAL PRIMARY KEY,
    fname VARCHAR(255) NOT NULL,
    lname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
);