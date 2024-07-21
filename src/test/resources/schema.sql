CREATE TABLE usuarios (
    usuario_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    apellido VARCHAR(255),
    direccion VARCHAR(255),
    email VARCHAR(255),
    nombre VARCHAR(255),
    password VARCHAR(255),
    rut VARCHAR(255),
    telefono VARCHAR(255)
);

CREATE TABLE reservas (
    reserva_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    cantidad_juego INT,
    fecha_fin TIMESTAMP,
    fecha_inicio TIMESTAMP,
    total DECIMAL(10, 2),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id)
);
