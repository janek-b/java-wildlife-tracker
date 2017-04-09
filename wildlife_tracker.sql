--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: animals; Type: TABLE; Schema: public; Owner: janek
--

CREATE TABLE animals (
    id integer NOT NULL,
    speciesid integer,
    health character varying,
    age character varying,
    identifier character varying
);


ALTER TABLE animals OWNER TO janek;

--
-- Name: animals_id_seq; Type: SEQUENCE; Schema: public; Owner: janek
--

CREATE SEQUENCE animals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animals_id_seq OWNER TO janek;

--
-- Name: animals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: janek
--

ALTER SEQUENCE animals_id_seq OWNED BY animals.id;


--
-- Name: animals_sightings; Type: TABLE; Schema: public; Owner: janek
--

CREATE TABLE animals_sightings (
    id integer NOT NULL,
    animalid integer,
    sightingid integer
);


ALTER TABLE animals_sightings OWNER TO janek;

--
-- Name: animals_sightings_id_seq; Type: SEQUENCE; Schema: public; Owner: janek
--

CREATE SEQUENCE animals_sightings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animals_sightings_id_seq OWNER TO janek;

--
-- Name: animals_sightings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: janek
--

ALTER SEQUENCE animals_sightings_id_seq OWNED BY animals_sightings.id;


--
-- Name: sightings; Type: TABLE; Schema: public; Owner: janek
--

CREATE TABLE sightings (
    id integer NOT NULL,
    speciesid integer,
    location character varying,
    userid integer,
    "time" timestamp without time zone,
    image character varying
);


ALTER TABLE sightings OWNER TO janek;

--
-- Name: sightings_id_seq; Type: SEQUENCE; Schema: public; Owner: janek
--

CREATE SEQUENCE sightings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sightings_id_seq OWNER TO janek;

--
-- Name: sightings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: janek
--

ALTER SEQUENCE sightings_id_seq OWNED BY sightings.id;


--
-- Name: species; Type: TABLE; Schema: public; Owner: janek
--

CREATE TABLE species (
    id integer NOT NULL,
    name character varying,
    classification character varying,
    habitat character varying,
    endangered boolean,
    image character varying
);


ALTER TABLE species OWNER TO janek;

--
-- Name: species_id_seq; Type: SEQUENCE; Schema: public; Owner: janek
--

CREATE SEQUENCE species_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE species_id_seq OWNER TO janek;

--
-- Name: species_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: janek
--

ALTER SEQUENCE species_id_seq OWNED BY species.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: janek
--

CREATE TABLE users (
    id integer NOT NULL,
    email character varying,
    name character varying
);


ALTER TABLE users OWNER TO janek;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: janek
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO janek;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: janek
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: animals id; Type: DEFAULT; Schema: public; Owner: janek
--

ALTER TABLE ONLY animals ALTER COLUMN id SET DEFAULT nextval('animals_id_seq'::regclass);


--
-- Name: animals_sightings id; Type: DEFAULT; Schema: public; Owner: janek
--

ALTER TABLE ONLY animals_sightings ALTER COLUMN id SET DEFAULT nextval('animals_sightings_id_seq'::regclass);


--
-- Name: sightings id; Type: DEFAULT; Schema: public; Owner: janek
--

ALTER TABLE ONLY sightings ALTER COLUMN id SET DEFAULT nextval('sightings_id_seq'::regclass);


--
-- Name: species id; Type: DEFAULT; Schema: public; Owner: janek
--

ALTER TABLE ONLY species ALTER COLUMN id SET DEFAULT nextval('species_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: janek
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: animals; Type: TABLE DATA; Schema: public; Owner: janek
--

COPY animals (id, speciesid, health, age, identifier) FROM stdin;
1	2	healthy	young	Tag # 9
2	5	healthy	young	White spot on left side
3	2	healthy	young	Tag # 7
4	2	healthy	adult	Tag #10
\.


--
-- Name: animals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: janek
--

SELECT pg_catalog.setval('animals_id_seq', 4, true);


--
-- Data for Name: animals_sightings; Type: TABLE DATA; Schema: public; Owner: janek
--

COPY animals_sightings (id, animalid, sightingid) FROM stdin;
1	1	3
2	2	5
3	3	6
4	4	7
\.


--
-- Name: animals_sightings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: janek
--

SELECT pg_catalog.setval('animals_sightings_id_seq', 4, true);


--
-- Data for Name: sightings; Type: TABLE DATA; Schema: public; Owner: janek
--

COPY sightings (id, speciesid, location, userid, "time", image) FROM stdin;
9	4	In the forest	1	2017-04-09 14:16:06.386202	http://a57.foxnews.com/media2.foxnews.com/BrightCove/694940094001/2016/06/13/0/0/694940094001_4938743070001_061316-do-bear-1280.jpg?ve=1
8	4	By the river	1	2017-04-09 14:03:09.153892	/images/default-placeholder.png
5	5	In the meadow.	1	2017-04-08 18:09:10.880737	/images/default-placeholder.png
4	3	In the woods	1	2017-04-08 16:08:23.48201	/images/default-placeholder.png
2	4	The Woods	1	2017-04-08 13:01:18.008406	/images/default-placeholder.png
1	3	Near the river.	1	2017-04-07 14:24:09.61736	/images/default-placeholder.png
3	2	In a tree	1	2017-04-08 13:48:07.696281	/images/default-placeholder.png
6	2	Near the lake	1	2017-04-08 18:40:09.120007	/images/default-placeholder.png
7	2	In the Sky	1	2017-04-08 20:28:47.406348	/images/default-placeholder.png
\.


--
-- Name: sightings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: janek
--

SELECT pg_catalog.setval('sightings_id_seq', 9, true);


--
-- Data for Name: species; Type: TABLE DATA; Schema: public; Owner: janek
--

COPY species (id, name, classification, habitat, endangered, image) FROM stdin;
1	Wolverine	Mammal	Open forests and alpine areas	t	http://www.oregonwild.org/sites/default/files/styles/mid-thum-200x200/public/wildlife/Wolverine.jpg?itok=57Jza-i1
6	Columbian White-tailed Deer	mammals	Willow, cottonwood and alder thickets along stream sides	t	http://www.oregonwild.org/sites/default/files/styles/mid-thum-200x200/public/wildlife/ColumbianWhiteTailedDeer-FWS.jpg?itok=G3hhhF6Q
4	Black Bear	Mammal	Forests with streams and wetlands.	f	http://www.oregonwild.org/sites/default/files/styles/mid-thum-200x200/public/wildlife/BlackBear.jpg?itok=gFxwLQ2w
3	Gray Fox	Mammal	heavily wooded swamps and rough hilly terrain	f	http://www.oregonwild.org/sites/default/files/styles/mid-thum-200x200/public/wildlife/GrayFox.jpg?itok=kYvc4gDl
5	Gray Wolf	Mammal	A variety of habitats—from grasslands, deserts and tundra to mountainous woodlands	t	http://www.oregonwild.org/sites/default/files/styles/mid-thum-200x200/public/wildlife/fwswolf.gary_.kramer.jpg?itok=fDf46LQI
2	California condor	Bird	Large swathes of land with tall trees, snags, or rocky cliffs for nesting and grasslands or oak savanna for foraging.	t	http://www.oregonwild.org/sites/default/files/styles/mid-thum-200x200/public/wildlife/CaliforniaCondorUSFWS.jpg?itok=ACBosUfv
8	Roosevelt Elk	mammals	Old growth forests with breaks in the canopy allowing sunlight to reach the floor	f	http://www.oregonwild.org/sites/default/files/styles/mid-thum-200x200/public/wildlife/RooseveltElk.jpg?itok=jeBqE9DZ
9	Sasquatch	mammals	Remote forests	f	http://www.oregonwild.org/sites/default/files/styles/mid-thum-200x200/public/wildlife/SasquatchRogerPatterson.gif?itok=vFFc9sxC
7	Sea Otter	mammals	Temperate, coastal waters with rocky or soft sediment bottom and kelp forests	t	http://www.oregonwild.org/sites/default/files/styles/mid-thum-200x200/public/wildlife/SeaOtter.jpg?itok=6-EVVJGD
\.


--
-- Name: species_id_seq; Type: SEQUENCE SET; Schema: public; Owner: janek
--

SELECT pg_catalog.setval('species_id_seq', 9, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: janek
--

COPY users (id, email, name) FROM stdin;
1	brandt.janek@gmail.com	Janek Brandt
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: janek
--

SELECT pg_catalog.setval('users_id_seq', 1, true);


--
-- Name: animals animals_pkey; Type: CONSTRAINT; Schema: public; Owner: janek
--

ALTER TABLE ONLY animals
    ADD CONSTRAINT animals_pkey PRIMARY KEY (id);


--
-- Name: animals_sightings animals_sightings_pkey; Type: CONSTRAINT; Schema: public; Owner: janek
--

ALTER TABLE ONLY animals_sightings
    ADD CONSTRAINT animals_sightings_pkey PRIMARY KEY (id);


--
-- Name: sightings sightings_pkey; Type: CONSTRAINT; Schema: public; Owner: janek
--

ALTER TABLE ONLY sightings
    ADD CONSTRAINT sightings_pkey PRIMARY KEY (id);


--
-- Name: species species_pkey; Type: CONSTRAINT; Schema: public; Owner: janek
--

ALTER TABLE ONLY species
    ADD CONSTRAINT species_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: janek
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

