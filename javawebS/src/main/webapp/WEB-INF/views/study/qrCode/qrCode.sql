show databases;

create table qrCode(
	idx int not null auto_increment primary key,
	mid varchar(20) not null,
	name varchar(20) not null,
	email varchar(50) not null,
	movieName varchar(50) not null,
	movieDate varchar(50) not null,
	movieTime varchar(50) not null,
	publishNow varchar(50) not null,
	movieAdult int default 0,
	movieChild int default 0,
	qrCodeName varchar(100) not null
);

desc qrCode;

drop table qrCode;