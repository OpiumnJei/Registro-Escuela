create table alumnos(

        id bigint not null auto_increment,
        nombre varchar(100) not null unique,
        matricula varchar(100) not null unique,
        correo varchar(40) not null unique,
        grado varchar(100) not null,

        primary key(id)
);