create table subject(
    id int auto_increment,
    title varchar(100) not null,
    subjectYear int not null,
    semester int not null,
    credits int not null,
    description varchar(1000),
    primary key(id)
);

create table course(
    subjectId int,
    numberWeeks int
);

create table approfundation(
    subjectId int,
    numberWeeks int,
    id int auto_increment,
    primary key (id)
);

create table resource(
    id int auto_increment,
    subjectId int,
    approfundationId int,
    title varchar(100) not null,
    location varchar(1000) not null,
    date_created date,
    primary key (id)
);

// insert data into all tables
insert into subject values(1, 'Database', 2, 1, 6, 'Database description');
insert into subject values(2, 'Web', 2, 1, 6, 'Web description');
insert into subject values(3, 'Network', 2, 1, 6, 'Network description');

insert into course values(1, 10);
insert into course values(2, 12);

insert into approfundation values(1, 5, 1);
insert into approfundation values(2, 6, 2);

insert into resource values(1, 1, 0, 'Database resource', 'http://www.database.com', '2015-01-01');
insert into resource values(2, 2, 1, 'Web resource', 'http://www.web.com', '2015-01-01');
