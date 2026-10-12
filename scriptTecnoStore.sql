CREATE DATABASE tecnoStore_SaraRamirez;
USE tecnoStore_SaraRamirez;

CREATE TABLE persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE,
    telefono VARCHAR(20)
);

CREATE TABLE cliente (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES persona(id)
        ON DELETE CASCADE
);

CREATE TABLE administrador (
    id INT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES persona(id)
        ON DELETE CASCADE
);

CREATE TABLE marca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE celular (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_marca INT NOT NULL,
    modelo VARCHAR(80) NOT NULL,
    sistemaOperativo ENUM('IOS', 'ANDROID') NOT NULL,
    gama ENUM('BAJA', 'MEDIA', 'ALTA') NOT NULL,
    precio DECIMAL(12,2) NOT NULL CHECK (precio > 0),
    stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    FOREIGN KEY (id_marca) REFERENCES marca(id)
);

CREATE TABLE venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(12,2) NOT NULL DEFAULT 0,
    estado ENUM('PENDIENTE', 'PAGADA', 'CANCELADA') NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE detalle_venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_celular INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    subtotal DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES venta(id)
        ON DELETE CASCADE,
    FOREIGN KEY (id_celular) REFERENCES celular(id)
);

CREATE TABLE auditoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario VARCHAR(50) NOT NULL,
    accion VARCHAR(30) NOT NULL,
    entidad VARCHAR(30) NOT NULL,
    id_entidad INT NULL,
    detalle VARCHAR(255)
);


INSERT INTO administrador (id, usuario, `contraseña`)
SELECT p.id, 'admin', '1310'
FROM persona p
WHERE p.identificacion = '0000000000'
  AND NOT EXISTS (SELECT 1 FROM administrador WHERE usuario = 'admin');