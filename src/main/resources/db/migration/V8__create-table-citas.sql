create table citas(

            id bigint not null auto_increment,
            id_alumno bigint not null,
            id_profesor bigint not null,
            tutorAlumno varchar(200) not null,
            fecha datetime not null,
            primary key(id),

            constraint fk_citas_alumno_id foreign key(id_alumno) references alumnos(id),
            constraint fk_citas_profesor_id foreign key(id_profesor) references profesores(id)
);