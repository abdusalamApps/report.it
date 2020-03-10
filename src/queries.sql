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
    name          varchar(30)
);

create table Users
(
    username varchar(30) primary key,
    name     varchar(30),
    password varchar(200),
    email    varchar(100)
);

-- Association table is used to enable users to be associated with projects
-- and to be able to exist in the system without being associated with any project
create table ProjectMembers
(
    username  varchar(30),
    projectId integer,
    role      integer,
    primary key (username, projectId),
    foreign key (username) references Users (username),
    foreign key (projectId) references Projects (id)
);

create table TimeReports
(
    id          integer auto_increment primary key,
    submitted   DATE,
    minutes_sum integer,
    signed      bool,
    projectId   integer,
    username    varchar(30),
    week        integer,
    foreign key (projectId) references Projects (id),
    foreign key (username) references Users (username)
);

insert into Administrators (username, name, password, email)
VALUES ('abdo', 'abdo', '123434', 'ab4700ya-s@student.lu.se');

insert into Users (username, name, password, email)
values ('user1', 'User One', '1234', 'user1@domain.com');

insert into Users (username, name, password, email)
values ('user2', 'User Two', '1234', 'user2@domain.com');;

insert into Users (username, name, password, email)
values ('user3', 'User Three',  '1234', 'user3@domain.com');

insert into Projects (name)
values ('Report It');

select *
from Users;

select * from Projects;
select *
from Administrators;

select * from ProjectMembers
join Projects P on ProjectMembers.projectId = P.id
where username = 'user1' and projectId = 1;

select * from Projects join ProjectMembers on ProjectMembers.projectId=projects.id
where name=? and id=?;
SELECT * FROM Users JOIN ProjectMembers ON
    Users.username = ProjectMembers.username JOIN Projects
    ON Users.name WHERE Users.name=? AND Projects.name=? AND role=?


