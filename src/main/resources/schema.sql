DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks (
  id int(5) NOT NULL AUTO_INCREMENT,
  user_id int(5) NOT NULL,
  type_id int(2) NOT NULL,
  title varchar(50) NOT NULL,
  detail text,
  deadline datetime NOT NULL,
  PRIMARY KEY (id)
);
DROP TABLE IF EXISTS task_type;
CREATE TABLE task_type (
  id int(2) NOT NULL,
  type varchar(20) NOT NULL,
  comment varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  email varchar(70) NOT NULL,
  password varchar(60) NOT NULL,
  enabled boolean NOT NULL,
  authority varchar(50) NOT NULL,
  tempkey varchar(255),
  PRIMARY KEY (id)
);
DROP TABLE IF EXISTS profile;
CREATE TABLE profile (
  id int(10) NOT NULL AUTO_INCREMENT,
  user_id int(11),
  nickname VARCHAR(50),
  image VARCHAR(100),
  detail text,
  updated datetime,
  PRIMARY KEY (id)
);
DROP TABLE IF EXISTS community;
CREATE TABLE community (
  id int(10) NOT NULL AUTO_INCREMENT,
  user_id int(10),
  name VARCHAR(50),
  detail text,
  created datetime,
  PRIMARY KEY (id)
);  
DROP TABLE IF EXISTS user_info;

