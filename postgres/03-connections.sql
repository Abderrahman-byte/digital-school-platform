CREATE TABLE IF NOT EXISTS request_for_connection (
    id VARCHAR (25) PRIMARY KEY,
    teacher_id VARCHAR NOT NULL REFERENCES teacher_profil (account_id) ON DELETE CASCADE,
    student_id VARCHAR NOT NULL REFERENCES student_profil (account_id) ON DELETE CASCADE,
    created_date TIMESTAMP WITH TIME ZONE default NOW(),
    UNIQUE (teacher_id, student_id)
);

CREATE TABLE IF NOT EXISTS connection (
    teacher_id VARCHAR NOT NULL REFERENCES teacher_profil (account_id) ON DELETE CASCADE,
    student_id VARCHAR NOT NULL REFERENCES student_profil (account_id) ON DELETE CASCADE,
    created_date TIMESTAMP WITH TIME ZONE default NOW(),
    
    PRIMARY KEY (teacher_id, student_id)
);