CREATE TABLE feedback_target
(
    id          UUID         NOT NULL PRIMARY KEY,
    created_on  TIMESTAMP    NOT NULL,
    deleted_on  TIMESTAMP,
    status      VARCHAR(255) NOT NULL,
    updated_on  TIMESTAMP,
    active      BOOLEAN      DEFAULT TRUE,
    name        VARCHAR(100) NOT NULL,
    target_type VARCHAR(255) NOT NULL,
    CONSTRAINT uk_name UNIQUE (name)
);
