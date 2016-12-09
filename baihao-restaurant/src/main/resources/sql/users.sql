CREATE TABLE users
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(20) NOT NULL,
    user_phone VARCHAR(11) NOT NULL,
    user_email VARCHAR(20) NOT NULL,
    user_rank INT(11) DEFAULT '1' NOT NULL,
    user_password VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX users_user_email_uindex ON users (user_email);
CREATE UNIQUE INDEX users_user_name_uindex ON users (user_name);
CREATE UNIQUE INDEX users_user_phone_uindex ON users (user_phone);