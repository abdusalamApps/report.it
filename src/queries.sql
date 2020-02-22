create table Administrators
(
    username varchar(30) primary key,
    name     varchar(30),
    password varchar(30),
    email    varchar(100)
);

create table Projects
(
    name          varchar(30) primary key,
    administrator varchar(30),
    foreign key (administrator) references Administrators (username)
);

create table Users
(
    username varchar(30) primary key,
    name     varchar(30),
    password varchar(30),
    email    varchar(100),
    role     integer,
    project  varchar(30),
    foreign key (project) references Projects (name)
);

create table TimeReports
(
    submitted    DATE,
    minutes_sum  integer,
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

insert into Users(username, name, password, email, role, project)
VALUES ('abdo', 'abdo', '1234', 'abdo@pusb.se', 2, 'stora projekt');

select *
from Users;
