CREATE TABLE IF NOT EXISTS request_for_connection (
    id VARCHAR (25) PRIMARY KEY,
    teacher_id VARCHAR NOT NULL REFERENCES teacher_profil (account_id),
    student_id VARCHAR NOT NULL REFERENCES student_profil (account_id),
    UNIQUE (teacher_id, student_id)
);

CREATE TABLE IF NOT EXISTS connection (
    teacher_id VARCHAR NOT NULL REFERENCES teacher_profil (account_id),
    student_id VARCHAR NOT NULL REFERENCES student_profil (account_id),
    
    PRIMARY KEY (teacher_id, student_id)
);