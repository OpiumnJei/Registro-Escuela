create table profesores(

        id bigint not null auto_increment,
        nombre varchar(100) not null unique,
        correo varchar(100) not null unique,
        telefono varchar(100) not null,

        primary key(id)
);