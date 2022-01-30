CREATE TABLE IF NOT EXISTS country (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL UNIQUE,
    code VARCHAR (5) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "state" (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    country_id INTEGER NOT NULL REFERENCES country (id),
    UNIQUE (country_id, name)
);

CREATE TABLE IF NOT EXISTS city (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    state_id INTEGER NOT NULL REFERENCES "state" (id),
    UNIQUE (state_id, name)
);

CREATE TABLE IF NOT EXISTS school_profil (
    account_id VARCHAR(25) PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    location INTEGER NOT NULL REFERENCES city (id),
    subtitle VARCHAR (250),
    overview TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS teacher_profil (
    account_id VARCHAR(25) PRIMARY KEY,
    first_name VARCHAR (100) NOT NULL,
    last_name VARCHAR (100) NOT NULL,
    title VARCHAR (100) NOT NULL,
    bio TEXT,
    location INTEGER NOT NULL REFERENCES city (id),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS student_profil (
    account_id VARCHAR (25) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    day_of_birth DATE NOT NULL,
    location INTEGER NOT NULL REFERENCES city (id),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS teacher_school (
    teacher_id VARCHAR (25) NOT NULL REFERENCES teacher_profil (account_id) ON DELETE CASCADE,
    school_id VARCHAR (25) NOT NULL REFERENCES school_profil (account_id) ON DELETE CASCADE,

    title VARCHAR (100),
    verified BOOLEAN NOT NULL DEFAULT false,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    ended_date TIMESTAMP WITH TIME ZONE,

    PRIMARY KEY (teacher_id, school_id)
);

CREATE EXTENSION pg_trgm;

CREATE INDEX IF NOT EXISTS city_name_search_idx ON city USING gist (name gist_trgm_ops);