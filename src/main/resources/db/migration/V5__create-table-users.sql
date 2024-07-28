create table users(

        id bigint not null auto_increment,
        login varchar(100) not null ,
        contrasenia varchar(300) not null,

        primary key(id)
);