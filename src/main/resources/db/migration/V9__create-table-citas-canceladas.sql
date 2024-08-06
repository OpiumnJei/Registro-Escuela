create table citas_canceladas(

        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        id_cita BIGINT NOT NULL,
        motivo_cancelacion VARCHAR(255) NOT NULL,
        fecha_cancelacion TIMESTAMP NOT NULL,
        FOREIGN KEY (id_cita) REFERENCES citas(id)
);