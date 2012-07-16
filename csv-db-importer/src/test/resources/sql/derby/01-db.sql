
DROP SEQUENCE user_seqid;
DROP TABLE USERS;

CREATE SEQUENCE user_seqid START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
       USERID              INTEGER           NOT NULL,
       USERNAME            VARCHAR(32)       NOT NULL UNIQUE,
       PASSWORD            VARCHAR(64)       NOT NULL,
       FIRST_NAME          VARCHAR(64)       NOT NULL,
       LAST_NAME           VARCHAR(64)       NOT NULL,
       COMMENT             VARCHAR(64)
);

