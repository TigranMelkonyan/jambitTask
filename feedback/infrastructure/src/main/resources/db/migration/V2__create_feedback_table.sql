CREATE TABLE feedback
(
    id                 UUID         NOT NULL PRIMARY KEY,
    created_on         TIMESTAMP    NOT NULL,
    deleted_on         TIMESTAMP,
    status             VARCHAR(255) NOT NULL,
    updated_on         TIMESTAMP,
    active             BOOLEAN DEFAULT TRUE,
    comment            VARCHAR(1000),
    score              SMALLINT     NOT NULL,
    title              VARCHAR(50)  NOT NULL,
    user_id            UUID         NOT NULL,
    feedback_target_id UUID         NOT NULL,
    CONSTRAINT fk_feedback_target_id FOREIGN KEY (feedback_target_id) REFERENCES feedback_target,
    CONSTRAINT uk_feedback_target_user UNIQUE (feedback_target_id, user_id)
);
