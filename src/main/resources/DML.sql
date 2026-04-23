
INSERT INTO TIPOS_COMBUSTIBLE (NOMBRE) VALUES ('Gasolina'), ('Diesel'), ('Híbrido'), ('Eléctrico'), ('GLP');
INSERT INTO TIPOS_TRANSMISION (NOMBRE) VALUES ('Manual'), ('Automático'), ('Semiautomático');
INSERT INTO ETIQUETAS_AMBIENTALES (NOMBRE) VALUES ('0'), ('Eco'), ('C'), ('B'), ('Sin Etiqueta');
INSERT INTO CATEGORIAS (NOMBRE) VALUES ('Sedán'), ('SUV'), ('Compacto'), ('Deportivo'), ('Monovolumen'), ('Pick-up');
INSERT INTO CIUDADES (NOMBRE) VALUES ('Madrid'), ('Barcelona'), ('Valencia'), ('Sevilla'), ('Zaragoza');
INSERT INTO COLORES (NOMBRE) VALUES ('Blanco'), ('Negro'), ('Gris'), ('Rojo'), ('Azul');


INSERT INTO MARCAS (NOMBRE) VALUES ('Toyota'), ('Volkswagen'), ('BMW'), ('Audi');


INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES
                                           ('Corolla', 1), ('RAV4', 1),   -- Toyota (ID 1 y 2)
                                           ('Golf', 2), ('Tiguan', 2),    -- VW (ID 3 y 4)
                                           ('Serie 3', 3), ('X5', 3),     -- BMW (ID 5 y 6)
                                           ('A3', 4);                     -- Audi (ID 7)


INSERT INTO VERSIONES (NOMBRE, MODELO_ID) VALUES
                                              ('1.8 Hybrid Active', 1), ('2.0 Hybrid Style', 1), -- Del Corolla (ID 1 y 2)
                                              ('2.0 TDI R-Line', 3), ('GTI 2.0 TSI', 3),         -- Del Golf (ID 3 y 4)
                                              ('320d M Sport', 5), ('330e PHEV', 5),             -- Del Serie 3 (ID 5 y 6)
                                              ('35 TFSI S line', 7);                             -- Del A3 (ID 7)


INSERT INTO USUARIOS (NOMBRES, APELLIDOS, DNI, EMAIL, PASSWORD, TELEFONO) VALUES
                                                                              ('Juan', 'García', '11111111A', 'juan@mail.com', 'hash123', '600000001'),
                                                                              ('Maria', 'López', '22222222B', 'maria@mail.com', 'hash123', '600000002'),
                                                                              ('Carlos', 'Pérez', '33333333C', 'carlos@mail.com', 'hash123', '600000003');

INSERT INTO COCHES (ANIO_FABRICACION, KILOMETRAJE, PRECIO_VENTA, ESTADO, URL_IMAGEN, VERSION_ID, COMBUSTIBLE_ID, TRANSMISION_ID, CIUDAD_ID, COLOR_ID, ETIQUETA_ID, CATEGORIA_ID, VENDEDOR_ID) VALUES
-- 1. Toyota Corolla - Disponible
(2023, 0, 35500.00, 'Disponible', '/assets/img/cars/corolla_white.jpg', 1, 3, 2, 1, 1, 2, 1, 1),
-- 2. VW Golf - Disponible
(2022, 12500, 29000.00, 'Disponible', '/assets/img/cars/golf_red.jpg', 4, 1, 2, 2, 4, 3, 3, 2),
-- 3. BMW Serie 3 - Vendido
(2021, 45000, 31000.00, 'Vendido', '/assets/img/cars/bmw_black.jpg', 5, 2, 2, 1, 2, 3, 1, 1),
-- 4. Audi A3 - Disponible
(2023, 50, 38000.00, 'Disponible', '/assets/img/cars/audi_a3_grey.jpg', 7, 1, 2, 3, 3, 3, 3, 3),
-- 5. Toyota Corolla (Segunda Mano) - Disponible
(2019, 85000, 19500.00, 'Disponible', '/assets/img/cars/corolla_blue.jpg', 2, 3, 2, 2, 5, 2, 1, 2),
-- 6. VW Golf Diesel - Vendido
(2020, 70000, 22500.00, 'Vendido', '/assets/img/cars/golf_silver.jpg', 3, 2, 1, 4, 3, 3, 3, 1),
-- 7. BMW 330e PHEV - Disponible
(2022, 15000, 48000.00, 'Disponible', '/assets/img/cars/bmw_hybrid.jpg', 6, 3, 2, 1, 1, 2, 1, 3),
-- 8. Toyota RAV4 (Usando versión Corolla para test) - Desactivado
(2018, 110000, 21000.00, 'Desactivado', '/assets/img/cars/rav4_old.jpg', 2, 3, 2, 5, 2, 2, 2, 1),
-- 9. VW GTI - Disponible
(2023, 100, 45000.00, 'Disponible', '/assets/img/cars/golf_gti.jpg', 4, 1, 2, 1, 2, 3, 3, 2),
-- 10. Audi A3 Usado - Disponible
(2017, 140000, 15000.00, 'Disponible', '/assets/img/cars/audi_a3_old.jpg', 7, 2, 1, 2, 3, 4, 3, 3);


INSERT INTO FACTURAS (COMPRADOR_ID, VENDEDOR_ID, TOTAL_BASE, TOTAL_PAGADO, ID_TRANSACCION_BANCARIA) VALUES
                                                                                                        (3, 1, 31000.00, 38440.00, 'TX-99901'), -- Coche 3
                                                                                                        (2, 1, 22500.00, 27900.00, 'TX-99902'); -- Coche 6

INSERT INTO LINEAS_FACTURA (FACTURA_ID, COCHE_ID, PRECIO_VENTA_MOMENTO) VALUES
                                                                            (1, 3, 31000.00),
                                                                            (2, 6, 22500.00);