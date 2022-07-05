CREATE TYPE account_type AS ENUM ('STUDENT', 'TEACHER', 'SCHOOL');

CREATE TABLE IF NOT EXISTS account (
    id VARCHAR(25) PRIMARY KEY,
    username VARCHAR (100) NOT NULL UNIQUE,
    email VARCHAR (100) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    is_admin Boolean NOT NULL DEFAULT false,
    is_active Boolean NOT NULL DEFAULT false,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    acc_type account_type NOT NULL DEFAULT 'STUDENT'
);

CREATE TABLE IF NOT EXISTS session (
    sid VARCHAR(25) PRIMARY KEY,
    payload json,
    expires TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS session_expires_idx ON session (expires);