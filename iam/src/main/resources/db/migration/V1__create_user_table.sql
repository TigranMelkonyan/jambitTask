CREATE TABLE iam_user
(
    id         UUID         NOT NULL PRIMARY KEY,
    created_on TIMESTAMP    NOT NULL,
    deleted_on TIMESTAMP,
    status     VARCHAR(255) NOT NULL,
    updated_on TIMESTAMP,
    active     BOOLEAN DEFAULT TRUE,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR      NOT NULL,
    role       VARCHAR(255),
    username   VARCHAR(255) NOT NULL,
    CONSTRAINT uk_email UNIQUE (email),
    CONSTRAINT uk_username UNIQUE (username)
);

CREATE INDEX username_idx ON iam_user (username);
