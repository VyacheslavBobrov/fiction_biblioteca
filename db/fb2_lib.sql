--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: fb2_lib; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE fb2_lib WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'ru_RU.UTF-8' LC_CTYPE = 'ru_RU.UTF-8';


ALTER DATABASE fb2_lib OWNER TO postgres;

\connect fb2_lib

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

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
-- Name: authors; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE authors (
    "ID" integer NOT NULL,
    first_name character varying(80),
    patr_name character varying(80),
    last_name character varying
);


ALTER TABLE public.authors OWNER TO postgres;

--
-- Name: authors_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "authors_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."authors_ID_seq" OWNER TO postgres;

--
-- Name: authors_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "authors_ID_seq" OWNED BY authors."ID";


--
-- Name: books; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE books (
    "ID" integer NOT NULL,
    title character varying(150),
    annotation text,
    cover bytea,
    sequence integer,
    num_in_sequence integer
);


ALTER TABLE public.books OWNER TO postgres;

--
-- Name: books_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "books_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."books_ID_seq" OWNER TO postgres;

--
-- Name: books_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "books_ID_seq" OWNED BY books."ID";


--
-- Name: books_authors; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE books_authors (
    book_id integer NOT NULL,
    author_id integer NOT NULL
);


ALTER TABLE public.books_authors OWNER TO postgres;

--
-- Name: books_genres; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE books_genres (
    book_id integer NOT NULL,
    genre_id integer NOT NULL
);


ALTER TABLE public.books_genres OWNER TO postgres;

--
-- Name: genres; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE genres (
    "ID" integer NOT NULL,
    name character varying(100)
);


ALTER TABLE public.genres OWNER TO postgres;

--
-- Name: genres_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "genres_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."genres_ID_seq" OWNER TO postgres;

--
-- Name: genres_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "genres_ID_seq" OWNED BY genres."ID";


--
-- Name: last_dirs_modified; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE last_dirs_modified (
    dir_name character varying(150) NOT NULL,
    last_mod timestamp without time zone
);


ALTER TABLE public.last_dirs_modified OWNER TO postgres;

--
-- Name: sequences; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sequences (
    "ID" integer NOT NULL,
    name character varying(100)
);


ALTER TABLE public.sequences OWNER TO postgres;

--
-- Name: sequences_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "sequences_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."sequences_ID_seq" OWNER TO postgres;

--
-- Name: sequences_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "sequences_ID_seq" OWNED BY sequences."ID";


--
-- Name: ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY authors ALTER COLUMN "ID" SET DEFAULT nextval('"authors_ID_seq"'::regclass);


--
-- Name: ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books ALTER COLUMN "ID" SET DEFAULT nextval('"books_ID_seq"'::regclass);


--
-- Name: ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY genres ALTER COLUMN "ID" SET DEFAULT nextval('"genres_ID_seq"'::regclass);


--
-- Name: ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sequences ALTER COLUMN "ID" SET DEFAULT nextval('"sequences_ID_seq"'::regclass);


--
-- Name: pk_authors_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY authors
    ADD CONSTRAINT pk_authors_id PRIMARY KEY ("ID");


--
-- Name: pk_books_authors; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY books_authors
    ADD CONSTRAINT pk_books_authors PRIMARY KEY (book_id, author_id);


--
-- Name: pk_books_genres_ids; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY books_genres
    ADD CONSTRAINT pk_books_genres_ids PRIMARY KEY (book_id, genre_id);


--
-- Name: pk_books_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY books
    ADD CONSTRAINT pk_books_id PRIMARY KEY ("ID");


--
-- Name: pk_dirs_modified_dir_name; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY last_dirs_modified
    ADD CONSTRAINT pk_dirs_modified_dir_name PRIMARY KEY (dir_name);


--
-- Name: pk_genre_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY genres
    ADD CONSTRAINT pk_genre_id PRIMARY KEY ("ID");


--
-- Name: pk_sequences_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sequences
    ADD CONSTRAINT pk_sequences_id PRIMARY KEY ("ID");


--
-- Name: uniq_genre_name; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY genres
    ADD CONSTRAINT uniq_genre_name UNIQUE (name);


--
-- Name: uniq_id_sequence_num; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY books
    ADD CONSTRAINT uniq_id_sequence_num UNIQUE ("ID", sequence, num_in_sequence);


--
-- Name: fki_books_sequence; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_books_sequence ON books USING btree (sequence);


--
-- Name: indx_authors_first_name; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX indx_authors_first_name ON authors USING btree (first_name);


--
-- Name: indx_authors_last_name; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX indx_authors_last_name ON authors USING btree (last_name);


--
-- Name: indx_authors_patr_name; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX indx_authors_patr_name ON authors USING btree (patr_name);


--
-- Name: fk_book_authors; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books_authors
    ADD CONSTRAINT fk_book_authors FOREIGN KEY (book_id) REFERENCES books("ID");


--
-- Name: fk_book_genres; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books_genres
    ADD CONSTRAINT fk_book_genres FOREIGN KEY (book_id) REFERENCES books("ID");


--
-- Name: fk_books_author; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books_authors
    ADD CONSTRAINT fk_books_author FOREIGN KEY (author_id) REFERENCES authors("ID");


--
-- Name: fk_books_genre; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books_genres
    ADD CONSTRAINT fk_books_genre FOREIGN KEY (genre_id) REFERENCES genres("ID");


--
-- Name: fk_books_sequence; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books
    ADD CONSTRAINT fk_books_sequence FOREIGN KEY (sequence) REFERENCES sequences("ID");


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

