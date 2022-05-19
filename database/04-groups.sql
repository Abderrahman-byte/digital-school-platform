CREATE TABLE IF NOT EXISTS "group" (
    id VARCHAR(25) PRIMARY KEY,
    label VARCHAR(200) NOT NULL,
    created_by VARCHAR(25) NOT NULL REFERENCES teacher_profil(account_id) ON DELETE CASCADE,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    deleted_date TIMESTAMP WITH TIME ZONE,
    UNIQUE (label, created_by)
);

CREATE TABLE IF NOT EXISTS group_member (
    group_id VARCHAR(25) NOT NULL REFERENCES "group"(id) ON DELETE CASCADE,
    member_id VARCHAR(25) NOT NULL REFERENCES student_profil(account_id) ON DELETE CASCADE,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    PRIMARY KEY(group_id, member_id)
);