--create table tasks (id bigserial, name varchar(255), creator varchar(255), assignee varchar(255), description varchar(255), status int, primary key(id));
--
--create table users (id bigserial, name varchar(255), email varchar(255), status int, primary key(id));
CREATE TABLE users
(
    id bigserial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    status INTEGER NOT NULL
);

CREATE TABLE tasks
(
    id bigserial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    status INTEGER NOT NULL,
    creator INTEGER NOT NULL,
    assignee INTEGER,
    FOREIGN KEY (creator) REFERENCES users (id),
    FOREIGN KEY (assignee) REFERENCES users (id)
);