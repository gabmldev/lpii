CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE role (
    id          uuid    PRIMARY KEY DEFAULT gen_random_uuid(),
    name        text    NOT NULL,
    description text           -- nullable (no NOT NULL)
);

CREATE TABLE users (
    id       uuid    PRIMARY KEY DEFAULT gen_random_uuid(),
    username text    NOT NULL,
    email    text    NOT NULL,
    pwd      text    NOT NULL,
    role_id  uuid    NOT NULL,

    CONSTRAINT fk_users_role
        FOREIGN KEY (role_id)
        REFERENCES role (id)
        ON DELETE RESTRICT               -- keep role rows while users exist
        ON UPDATE CASCADE
);

CREATE UNIQUE INDEX uq_users_username
CREATE UNIQUE INDEX uq_users_email

CREATE TABLE session (
    id         uuid            PRIMARY KEY DEFAULT gen_random_uuid(),
    tjti       text            NOT NULL,
    token      text            NOT NULL,
    user_id    uuid            NOT NULL,
    created_at timestamptz     NOT NULL DEFAULT now(),
    expires_at timestamptz     NOT NULL DEFAULT now(),

    CONSTRAINT fk_session_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE                -- delete sessions when a user is removed
        ON UPDATE CASCADE;
);

CREATE INDEX idx_session_user_jti ON session(user_id, tjti);
