CREATE DATABASE IF NOT EXISTS tecnoStore_SaraRamirez;
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
    contraseña VARCHAR(20) NOT NULL,
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

INSERT INTO persona (id, nombre, apellido, identificacion, email, telefono) VALUES
    (1, 'Admin', 'TecnoStore', '0000000000', 'admin@tecnostore.com', '3000000000'),
    (2, 'Danna', 'Tellez', '1012345678', 'sunisonfire@gmail.com', '3001234567'),
    (3, 'Sara', 'Ramírez', '1023456789', 'saris.rami@hotmail.com', '3109876543'),
    (4, 'Valentina', 'Navarro', '1034567890', 'valentina.navarro@gmail.com', '3205551122'),
    (5, 'Isabella', 'Villareal', '79456123', 'isavs.kz@outlook.com', '3153334455'),
    (6, 'Mario', 'Rojas', '1045678901', 'Mario.rojas@gmail.com', '3017778899'),
    (7, 'Juan Pablo', 'Navas', '80234567', 'juanpablo.navas@gmail.com', '3126667788'),
    (8, 'Daniel', 'Vargas', '1056789012', 'daniel.vargas@yahoo.com', '3042223344'),
    (9, 'Santiago', 'Mendoza', '1067890123', 'santiago.ma@gmail.com', '3188889900');

-- ADMINISTRADOR 
INSERT INTO administrador (id, usuario, `contraseña`) VALUES
    (1, 'admin', '1310');

-- CLIENTES
INSERT INTO cliente (id) VALUES
    (2), (3), (4), (5), (6), (7), (8), (9);

-- MARCAS
INSERT INTO marca (id, nombre) VALUES
    (1, 'Apple'),
    (2, 'Samsung'),
    (3, 'Xiaomi'),
    (4, 'Motorola'),
    (5, 'Honor');

-- CELULARES

INSERT INTO celular (id, id_marca, modelo, sistemaOperativo, gama, precio, stock) VALUES
    (1, 1, 'iPhone 15 Pro', 'IOS', 'ALTA', 5299000.00, 8),
    (2, 1, 'iPhone 15', 'IOS', 'ALTA', 4199000.00, 12),
    (3, 1, 'iPhone 13', 'IOS', 'MEDIA', 2799000.00, 3),
    (4, 2, 'Galaxy S24 Ultra', 'ANDROID', 'ALTA', 5499000.00, 6),
    (5, 2, 'Galaxy S23 FE', 'ANDROID', 'MEDIA', 2599000.00, 9),
    (6, 2, 'Galaxy A54 5G', 'ANDROID', 'MEDIA', 1599000.00, 15),
    (7, 2, 'Galaxy A15', 'ANDROID', 'BAJA', 749000.00, 25),
    (8, 3, 'Redmi Note 13 Pro', 'ANDROID', 'MEDIA', 1199000.00, 14),
    (9, 3, 'Poco X6 Pro', 'ANDROID', 'MEDIA', 1399000.00, 4),
    (10, 3, 'Redmi 13C', 'ANDROID', 'BAJA', 499000.00, 30),
    (11, 4, 'Edge 40 Neo', 'ANDROID', 'MEDIA', 1499000.00, 7),
    (12, 4, 'Moto G54', 'ANDROID', 'BAJA', 799000.00, 2),
    (13, 4, 'Moto G24', 'ANDROID', 'BAJA', 549000.00, 18),
    (14, 5, 'Magic6 Lite', 'ANDROID', 'MEDIA', 1299000.00, 10),
    (15, 5, 'X7b', 'ANDROID', 'BAJA', 699000.00, 1);

-- VENTAS
INSERT INTO venta (id, id_cliente, fecha, total, estado) VALUES
    (1, 2, '2026-06-05 10:15:00', 3090430.00, 'PAGADA'),
    (2, 3, '2026-06-18 16:40:00', 4996810.00, 'PAGADA'),
    (3, 4, '2026-07-03 11:05:00', 7197120.00, 'PAGADA'),
    (4, 5, '2026-07-22 09:30:00', 4100740.00, 'PAGADA'),
    (5, 6, '2026-07-28 18:20:00', 6305810.00, 'CANCELADA'),
    (6, 2, '2026-08-09 14:10:00', 3447430.00, 'PAGADA'),
    (7, 7, '2026-08-25 12:00:00', 5113430.00, 'PAGADA'),
    (8, 8, '2026-09-04 15:45:00', 8208620.00, 'PAGADA'),
    (9, 9, '2026-09-19 10:50:00', 3088050.00, 'PAGADA'),
    (10, 3, '2026-09-30 17:25:00', 6543810.00, 'PAGADA'),
    (11, 4, '2026-10-05 13:35:00', 2972620.00, 'PAGADA'),
    (12, 6, '2026-10-09 19:10:00', 2675120.00, 'PENDIENTE'),
    (13, 7, '2026-10-12 08:55:00', 3092810.00, 'PENDIENTE');

-- DETALLE DE LAS VENTAS
INSERT INTO detalle_venta (id, id_venta, id_celular, cantidad, subtotal) VALUES
    (1, 1, 6, 1, 1599000.00),
    (2, 1, 10, 2, 998000.00),
    (3, 2, 2, 1, 4199000.00),
    (4, 3, 4, 1, 5499000.00),
    (5, 3, 13, 1, 549000.00),
    (6, 4, 7, 3, 2247000.00),
    (7, 4, 8, 1, 1199000.00),
    (8, 5, 1, 1, 5299000.00),
    (9, 6, 8, 2, 2398000.00),
    (10, 6, 10, 1, 499000.00),
    (11, 7, 3, 1, 2799000.00),
    (12, 7, 7, 2, 1498000.00),
    (13, 8, 1, 1, 5299000.00),
    (14, 8, 6, 1, 1599000.00),
    (15, 9, 10, 3, 1497000.00),
    (16, 9, 13, 2, 1098000.00),
    (17, 10, 4, 1, 5499000.00),
    (18, 11, 8, 1, 1199000.00),
    (19, 11, 14, 1, 1299000.00),
    (20, 12, 11, 1, 1499000.00),
    (21, 12, 7, 1, 749000.00),
    (22, 13, 5, 1, 2599000.00);


-- Consultas para comprobar

-- Stock bajo (menos de 5):
-- SELECT id, modelo, stock FROM celular WHERE stock < 5 ORDER BY stock;

-- Top 3 más vendidos (solo ventas pagadas):
-- SELECT c.modelo, SUM(d.cantidad) AS unidades
-- FROM detalle_venta d
-- JOIN venta v ON v.id = d.id_venta
-- JOIN celular c ON c.id = d.id_celular
-- WHERE v.estado = 'PAGADA'
-- GROUP BY c.id, c.modelo
-- ORDER BY unidades DESC
-- LIMIT 3;

-- Ventas pagadas por mes:
-- SELECT DATE_FORMAT(fecha, '%Y-%m') AS mes, COUNT(*) AS ventas, SUM(total) AS total
-- FROM venta
-- WHERE estado = 'PAGADA'
-- GROUP BY mes
-- ORDER BY mes;


