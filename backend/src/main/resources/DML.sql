
INSERT INTO TIPOS_COMBUSTIBLE (NOMBRE)
VALUES ('Gasolina'),
       ('Diesel'),
       ('Híbrido'),
       ('Eléctrico'),
       ('GLP');

INSERT INTO TIPOS_TRANSMISION (NOMBRE)
VALUES ('Manual'),
       ('Automático'),
       ('Semiautomático');

INSERT INTO ETIQUETAS_AMBIENTALES (NOMBRE)
VALUES ('0'),
       ('Eco'),
       ('C'),
       ('B'),
       ('Sin Etiqueta');

INSERT INTO CATEGORIAS (NOMBRE)
VALUES ('Sedán'),
       ('SUV'),
       ('Compacto'),
       ('Deportivo'),
       ('Monovolumen'),
       ('Pick-up');

INSERT INTO CIUDADES (NOMBRE)
VALUES ('Madrid'),
       ('Barcelona'),
       ('Valencia'),
       ('Sevilla'),
       ('Zaragoza');

INSERT INTO COLORES (NOMBRE)
VALUES ('Blanco'),
       ('Negro'),
       ('Gris'),
       ('Rojo'),
       ('Azul');


INSERT INTO MARCAS (NOMBRE)
VALUES ('Toyota'),
       ('Volkswagen'),
       ('BMW'),
       ('Audi');


INSERT INTO MODELOS (NOMBRE, MARCA_ID)
VALUES ('Corolla', 1),
       ('RAV4', 1),
       ('Golf', 2),
       ('Serie 3', 3),
       ('A3', 4);


INSERT INTO VERSIONES (NOMBRE, MODELO_ID)
VALUES ('1.8 Hybrid Active', 1),
       ('2.0 Hybrid Style', 1),
       ('2.5 Hybrid AWD', 2),
       ('1.5 TSI Life', 3),
       ('GTI 2.0 TSI', 3),
       ('320d M Sport', 4),
       ('330e PHEV', 4),
       ('35 TFSI S line', 5),
       ('30 TDI Advanced', 5);


INSERT INTO USUARIOS (NOMBRES, APELLIDOS, DNI, EMAIL, PASSWORD, TELEFONO)
VALUES ('Juan', 'García', '11111111A', 'juan@mail.com', 'hash123', '600000001'),
       ('Maria', 'López', '22222222B', 'maria@mail.com', 'hash123', '600000002'),
       ('Carlos', 'Pérez', '33333333C', 'carlos@mail.com', 'hash123', '600000003');


INSERT INTO COCHES (ANIO_FABRICACION,
                    KILOMETRAJE,
                    PRECIO_VENTA,
                    ESTADO,
                    URL_IMAGEN,
                    VERSION_ID,
                    COMBUSTIBLE_ID,
                    TRANSMISION_ID,
                    CIUDAD_ID,
                    COLOR_ID,
                    ETIQUETA_ID,
                    CATEGORIA_ID,
                    VENDEDOR_ID)
VALUES

-- 1. Audi A3 gris
(2023, 50, 38000.00, 'Disponible', '/assets/img/cars/audi_a3_grey.jpg', 8, 1, 2, 1, 3, 3, 3, 1),

-- 2. Audi A3 antiguo
(2017, 140000, 15000.00, 'Disponible', '/assets/img/cars/audi_a3_old.jpg', 9, 2, 1, 2, 3, 4, 3, 2),

-- 3. BMW Serie 3 negro
(2021, 45000, 31000.00, 'Disponible', '/assets/img/cars/bmw_black.jpg', 6, 2, 2, 1, 2, 3, 1, 1),

-- 4. BMW Serie 3 híbrido
(2022, 15000, 48000.00, 'Disponible', '/assets/img/cars/bmw_hybrid.jpg', 7, 3, 2, 3, 1, 2, 1, 3),

-- 5. Toyota Corolla azul
(2019, 85000, 19500.00, 'Disponible', '/assets/img/cars/corolla_blue.jpg', 2, 3, 2, 2, 5, 2, 1, 2),

-- 6. Toyota Corolla blanco
(2023, 0, 35500.00, 'Disponible', '/assets/img/cars/corolla_white.jpg', 1, 3, 2, 1, 1, 2, 1, 1),

-- 7. Volkswagen Golf GTI
(2023, 100, 45000.00, 'Disponible', '/assets/img/cars/golf_gti.jpg', 5, 1, 2, 4, 4, 3, 3, 2),

-- 8. Volkswagen Golf rojo
(2022, 12500, 29000.00, 'Disponible', '/assets/img/cars/golf_red.jpg', 4, 1, 2, 2, 4, 3, 3, 2),

-- 9. Volkswagen Golf plata
(2020, 70000, 22500.00, 'Disponible', '/assets/img/cars/golf_silver.jpg', 4, 2, 1, 5, 3, 3, 3, 1),

-- 10. Toyota RAV4 antiguo
(2018, 110000, 21000.00, 'Disponible', '/assets/img/cars/rav4_old.jpg', 3, 3, 2, 5, 2, 2, 2, 1);