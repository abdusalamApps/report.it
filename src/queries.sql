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
  --  administrator varchar(30),
   -- foreign key (administrator) references Administrators (username)
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
create table ProjectMembers
(
    username varchar(30),
    projectId  integer,
    role     integer,
    primary key (username, projectId),
    foreign key (username) references Users (username),
    foreign key (projectId) references Projects (id)
);

create table TimeReports
(
    id          varchar(50) primary key,
    submitted   DATE,
    minutes_sum integer,
    signed      bool,
    projectId integer,
    username    varchar(30),
    week        integer,
    foreign key (projectId) references Projects (id),
    foreign key (username) references Users (username)
);

insert into Administrators (username, name, password, email)
VALUES
('abdo', 'abdo', '123434', 'ab4700ya-s@student.lu.se');

select * from Users;

select * from Administrators;