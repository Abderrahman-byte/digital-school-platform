CREATE TYPE account_type AS ENUM ('student', 'teacher', 'school');

CREATE TABLE IF NOT EXISTS account (
    id VARCHAR(25) PRIMARY KEY,
    username VARCHAR (100) NOT NULL UNIQUE,
    email VARCHAR (100) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    is_admin Boolean NOT NULL DEFAULT false,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    acc_type account_type NOT NULL DEFAULT 'student'
);

CREATE TABLE IF NOT EXISTS session (
    sid VARCHAR(25) PRIMARY KEY,
    payload json,
    expires TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX session_expires_idx ON session (expires);