

CREATE TABLE users (
       USERID              INTEGER           NOT NULL GENERATED ALWAYS AS IDENTITY (start with 1, increment by 1),
       USERNAME            VARCHAR(32)       NOT NULL UNIQUE,
       PASSWORD            VARCHAR(64)       NOT NULL,
       FIRST_NAME          VARCHAR(64)       NOT NULL,
       LAST_NAME           VARCHAR(64)       NOT NULL,
       COMMENT             VARCHAR(64)
);

