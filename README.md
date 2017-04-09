## Wildlife Tracker

An app for the forest service to track animals for an environmental impact study.

### Description

The Forest Service is considering a proposal from a timber company to clearcut a nearby forest of Douglas Fir. Before this proposal may be approved, they must complete an environmental impact study. This application was developed to allow Rangers to track wildlife sightings in the area.

### Setup

To create the necessary databases, launch postgres, then psql, and run the following commands:

* `CREATE DATABASE wildlife_tracker;`
* From the terminal run this command to import the database `psql < wildlife_tracker.sql`

If for some reason that command fails, run the command below from psql to create the required tables in the database.

* `\c wildlife_tracker;`
* `CREATE TABLE species (id serial PRIMARY KEY, name varchar, classification varchar, hahitat varchar, endangered boolean, image varchar);`
* `CREATE TABLE animals (id serial PRIMARY KEY, speciesId int, health varchar, age varchar, identifier varchar);`
* `CREATE TABLE sightings (id serial PRIMARY KEY, speciesId int, location varchar, userId varchar, time timestamp, image varchar);`
* `CREATE TABLE animals_sightings (id serial PRIMARY KEY, animalId int, speciesId int);`
* `CREATE TABLE users (id serial PRIMARY KEY, email varchar, name varchar);`
* `CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;`

### License

Copyright (c) 2017 **_MIT License_**
