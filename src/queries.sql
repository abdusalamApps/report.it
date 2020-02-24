-- SHA2 has to be used to encrypt passwords, 6 -> 12 characters
-- username 5 -> 10 characters
--
create table Administrators
(
    username varchar(30) primary key,
    name     varchar(30),
    password varchar(12),
    email    varchar(100)
);

create table Projects
(
    id            integer primary key auto_increment,
    name          varchar(30),
    administrator varchar(30),
    foreign key (administrator) references Administrators (username)
);

create table Users
(
    username varchar(30) primary key,
    name     varchar(30),
    password varchar(30),
    email    varchar(100)
);

-- Association table is used to enable users to be associated with projects
-- and to be able to exist in the system without being associated with any project
-- If we would have a foreign key referencing project name in users table
-- a user could've NOT exist at all without being associated with a project
create table ProjectMembers
(
    username     varchar(30),
    project_name varchar(30),
    role         integer,
    primary key (username, project_name),
    foreign key (username) references Users (username),
    foreign key (project_name) references Projects (name)
);

create table TimeReports
(
    id           varchar(50) primary key auto_increment,
    submitted    DATE,
    minutes_sum  integer,
    signed       bool,
    project_name varchar(30),
    username     varchar(30),
    foreign key (project_name) references Projects (name),
    foreign key (username) references Users (username)
);

create table Permissions
(
    type     integer,
    username varchar(30),
    foreign key (username) references Users (username)
);


insert into Administrators (username, name, password, email)
values ('admin', 'admin', 'admin', 'admin@reportit.com');

select *
from Administrators;

insert into Projects (name, administrator)
values ('stora projekt', 'admin');

select *
from Projects;

insert into Users(username, name, password, email, role)
VALUES ('abdo', 'abdo', '1234', 'abdo@pusb.se', 2);

insert into Users(username, name, password, email, role)
VALUES ('hadi', 'hadi', '1234', 'hadi@pusb.se', 2);

select *
from Users;

select *
from Users
         left join ProjectMembers PM on Users.username = PM.username;


INSERT INTO Projects (name, administrator)
VALUES ('databas', 'admin');

INSERT INTO Administrators (username, name, password, email)
VALUES ('admin2', 'admin2', 'admin2', 'admin2@pusb.se');

SELECT *
FROM Administrators;

INSERT INTO Projects (name, administrator)
VALUES ('java', 'admin2');

SELECT *
FROM Projects;

INSERT INTO Users (username, name, password, email, role, project)
VALUES ('daniel', 'daniel', '1234', 'daniel@pusb.se', 2, 'databas');

INSERT INTO Users (username, name, password, email, role, project)
VALUES ('nils', 'nils', '1234', 'nils@pusb.se', 2, 'java');

SELECT *
FROM Users;


