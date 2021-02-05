drop database if exists guess_the_number_db;
create database guess_the_number_db;
use guess_the_number_db;

set sql_safe_updates = 0;

create table if not exists score_log (
    id integer not null auto_increment,
	name varchar(50) not null,
	score int not null,
    created timestamp default current_timestamp,
    lastUpdate timestamp default current_timestamp on update current_timestamp,
	primary key(id)
    );