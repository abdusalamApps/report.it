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

# Password for user1, user2 and user3 is 1234

insert into Users (username, name, password, email)
 VALUES ('admin', 'Admin', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'admin@domain.com');

insert into Users (username, name, password, email)
values ('user1', 'User One', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'user1@domain.com');

insert into Users (username, name, password, email)
values ('user2', 'User Two', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'user2@domain.com
');

insert into Users (username, name, password, email)
values ('user3', 'User Three',  '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'user3@domain.com');

insert into Projects (name)
values ('Report It');

insert into Projects (name)
values ('Krusty Cookies');

select * from Users;

insert into ProjectMembers (username, projectId, role)
values ('user1', 1, 1);

insert into ProjectMembers (username, projectId, role)
values ('user2', 1, 2);

insert into ProjectMembers (username, projectId, role)
values ('user2', 1, 2);

insert into ProjectMembers (username, projectId, role)
values ('user3', 2, 1);

select ProjectMembers.username, ProjectMembers.projectId, name
from ProjectMembers
join Projects P on ProjectMembers.projectId = P.id;

select U.name, U.username, ProjectMembers.role
from ProjectMembers join Users U on ProjectMembers.username = U.username
join Projects P on ProjectMembers.projectId = P.id
where P.name = 'Report It';

update ProjectMembers
set role = 2
where username = 'user1' and projectId = '1';

insert into TimeReports (submitted, minutes_sum, signed, projectId, username, week)
values ('2020-03-03', 390, false, 1, 'user1', 9);

select * from TimeReports;

select * from Projects;