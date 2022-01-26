CREATE TABLE country (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL UNIQUE,
    code VARCHAR (5) NOT NULL UNIQUE
);

CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    country_id INTEGER NOT NULL REFERENCES country (id),
    UNIQUE (country_id, name)
); 

CREATE TABLE IF NOT EXISTS school_profil (
    account_id VARCHAR(25) PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    location INTEGER NOT NULL REFERENCES city (id),
    subtitle VARCHAR (250),
    overview TEXT,

    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),

    FOREIGN KEY (account_id) REFERENCES account (id)
);