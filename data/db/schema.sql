DROP DATABASE IF EXISTS example;
CREATE DATABASE IF NOT EXISTS example CHARACTER SET = 'utf8' COLLATE 'utf8_bin';
USE example;

CREATE TABLE Thing (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255)
);

CREATE TABLE User (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  username   VARCHAR(255)       UNIQUE NOT NULL,
  role       VARCHAR(255)              NOT NULL
);

CREATE TABLE Authentication (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  token      VARCHAR(255)       UNIQUE NOT NULL,
  user       BIGINT                    NOT NULL,

  FOREIGN KEY (user)
  REFERENCES User(id)
    ON DELETE CASCADE
);
