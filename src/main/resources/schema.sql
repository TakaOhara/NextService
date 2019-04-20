CREATE TABLE tasks (
  id int(5) NOT NULL AUTO_INCREMENT,
  user_info_id int(5) NOT NULL,
  type_id int(2) NOT NULL,
  title varchar(50) NOT NULL,
  detail text,
  deadline datetime NOT NULL,
  PRIMARY KEY (`id`)
);

-- ,
--CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `task_type` (`id`),
--CONSTRAINT `tasks_usfk_1` FOREIGN KEY (`user_info_id`) REFERENCES `user_info` (`id`)
--

CREATE TABLE task_type (
  id int(2) NOT NULL,
  type varchar(20) NOT NULL,
  comment varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE user_info (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  email varchar(70) NOT NULL,
  password varchar(60) NOT NULL,
  enabled tinyint(1) NOT NULL,
  authority varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);
