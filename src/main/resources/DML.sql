-- 1. CATALOGOS BASE

INSERT INTO tipos_combustible (nombre) VALUES
                                           ('Gasolina'), ('Diésel'), ('Híbrido'), ('Eléctrico'), ('GLP');

INSERT INTO tipos_transmision (nombre) VALUES
                                           ('Manual'), ('Automática'), ('CVT'), ('Doble Embrague'), ('Secuencial');

INSERT INTO ciudades (nombre) VALUES
                                  ('Madrid'), ('Barcelona'), ('Valencia'), ('Sevilla'), ('Zaragoza'),
                                  ('Málaga'), ('Murcia'), ('Palma'), ('Las Palmas'), ('Bilbao'),
                                  ('Alicante'), ('Córdoba'), ('Valladolid'), ('Vigo'), ('Gijón'),
                                  ('Vitoria'), ('A Coruña'), ('Granada'), ('Elche'), ('Oviedo');

INSERT INTO colores (nombre) VALUES
                                 ('Blanco'), ('Negro'), ('Gris Plata'), ('Azul Marino'), ('Rojo'),
                                 ('Verde'), ('Amarillo'), ('Naranja'), ('Beige'), ('Marrón'),
                                 ('Gris Antracita'), ('Azul Eléctrico'), ('Blanco Perlado'), ('Mate'), ('Dorado'),
                                 ('Bronce'), ('Turquesa'), ('Violeta'), ('Burdeos'), ('Champagne');

INSERT INTO etiquetas_ambientales (nombre) VALUES
                                               ('0 Emisiones'), ('ECO'), ('C'), ('B'), ('Sin Etiqueta');

INSERT INTO categorias (nombre) VALUES
                                    ('Sedán'), ('SUV'), ('Compacto'), ('Familiar'), ('Coupé'),
                                    ('Cabrio'), ('Monovolumen'), ('Pick-up'), ('Furgoneta'), ('Deportivo');

-- 2. MARCAS, MODELOS Y VERSIONES

INSERT INTO marcas (nombre) VALUES
                                ('Toyota'), ('Volkswagen'), ('Hyundai'), ('Kia'), ('Ford'),
                                ('BMW'), ('Mercedes-Benz'), ('Audi'), ('Renault'), ('Peugeot'),
                                ('Nissan'), ('Mazda'), ('Honda'), ('Volvo'), ('Tesla'),
                                ('Seat'), ('Skoda'), ('Fiat'), ('Lexus'), ('Porsche');

INSERT INTO modelos (nombre, marca_id) VALUES
                                           ('Corolla', 1), ('Yaris', 1), ('Golf', 2), ('Tiguan', 2),
                                           ('Tucson', 3), ('i30', 3), ('Sportage', 4), ('Ceed', 4),
                                           ('Focus', 5), ('Mustang', 5), ('Serie 3', 6), ('X5', 6),
                                           ('Clase C', 7), ('Clase A', 7), ('A4', 8), ('Q5', 8),
                                           ('Clio', 9), ('Megane', 9), ('208', 10), ('3008', 10);

INSERT INTO versiones (nombre, modelo_id) VALUES
                                              ('1.8 Hybrid Active', 1), ('1.5 Dynamic', 2), ('1.5 TSI Life', 3), ('2.0 TDI R-Line', 4),
                                              ('1.6 TGDI Tecno', 5), ('1.0 T-GDI N-Line', 6), ('1.6 CRDi Drive', 7), ('1.0 T-GDI Tech', 8),
                                              ('1.0 EcoBoost ST-Line', 9), ('5.0 V8 Fastback', 10), ('320d M Sport', 11), ('xDrive30d Black', 12),
                                              ('C 220 d AMG', 13), ('A 180 Progress', 14), ('35 TFSI S line', 15), ('40 TDI Quattro', 16),
                                              ('E-Tech Evolution', 17), ('1.3 TCe Zen', 18), ('PureTech Allure', 19), ('BlueHDi GT', 20);

-- 3. USUARIOS

INSERT INTO usuarios (nombres, apellidos, dni, email, password, telefono, tipo_usuario) VALUES
                                                                                            ('Admin', 'Market', '00000000X', 'admin@market.com', 'hash_admin', '600000000', 'ADMIN'),
                                                                                            ('Juan', 'Lopez', '12345678A', 'juan@mail.com', 'hash_j', '600000001', 'USER'),
                                                                                            ('Maria', 'Garcia', '23456789B', 'maria@mail.com', 'hash_m', '600000002', 'USER'),
                                                                                            ('Carlos', 'Sanz', '34567890C', 'carlos@mail.com', 'hash_c', '600000003', 'USER'),
                                                                                            ('Ana', 'Ruiz', '45678901D', 'ana@mail.com', 'hash_a', '600000004', 'USER'),
                                                                                            ('Jose', 'Mora', '56789012E', 'jose@mail.com', 'hash_jo', '600000005', 'USER'),
                                                                                            ('Lucia', 'Vidal', '67890123F', 'lucia@mail.com', 'hash_l', '600000006', 'USER'),
                                                                                            ('Hugo', 'Perez', '78901234G', 'hugo@mail.com', 'hash_h', '600000007', 'USER'),
                                                                                            ('Sara', 'Cano', '89012345H', 'sara@mail.com', 'hash_s', '600000008', 'USER'),
                                                                                            ('Raul', 'Losa', '90123456I', 'raul@mail.com', 'hash_r', '600000009', 'USER'),
                                                                                            ('Elena', 'Torres', '01234567J', 'elena@mail.com', 'hash_e', '600000010', 'USER'),
                                                                                            ('Pedro', 'Gil', '11223344K', 'pedro@mail.com', 'hash_p', '600000011', 'USER'),
                                                                                            ('Carla', 'Marin', '22334455L', 'carla@mail.com', 'hash_ca', '600000012', 'USER'),
                                                                                            ('Mario', 'Daza', '33445566M', 'mario@mail.com', 'hash_ma', '600000013', 'USER'),
                                                                                            ('Rosa', 'Peña', '44556677N', 'rosa@mail.com', 'hash_ro', '600000014', 'USER'),
                                                                                            ('Dani', 'Soto', '55667788O', 'dani@mail.com', 'hash_d', '600000015', 'USER'),
                                                                                            ('Ines', 'Roca', '66778899P', 'ines@mail.com', 'hash_i', '600000016', 'USER'),
                                                                                            ('Alex', 'Pina', '77889900Q', 'alex@mail.com', 'hash_al', '600000017', 'USER'),
                                                                                            ('Eva', 'Luna', '88990011R', 'eva@mail.com', 'hash_ev', '600000018', 'USER'),
                                                                                            ('Ivan', 'Rius', '99001122S', 'ivan@mail.com', 'hash_iv', '600000019', 'USER');

-- 4. COCHES

INSERT INTO coches (anio_fabricacion, kilometraje, precio_venta, es_premium, estado, version_id, combustible_id, transmision_id, ciudad_id, color_id, etiqueta_id, categoria_id, vendedor_id) VALUES
                                                                                                                                                                                                  (2022, 15000, 24500.00, TRUE, 'Disponible', 1, 3, 3, 1, 1, 2, 3, 2),
                                                                                                                                                                                                  (2021, 30000, 19800.00, FALSE, 'Disponible', 2, 1, 1, 2, 2, 3, 3, 3),
                                                                                                                                                                                                  (2020, 45000, 21000.00, FALSE, 'Disponible', 3, 1, 1, 3, 3, 3, 3, 4),
                                                                                                                                                                                                  (2022, 10000, 32000.00, TRUE, 'Disponible', 4, 2, 2, 4, 4, 3, 2, 5),
                                                                                                                                                                                                  (2023, 5000, 28500.00, TRUE, 'Disponible', 5, 1, 2, 5, 5, 2, 2, 6),
                                                                                                                                                                                                  (2021, 22000, 17500.00, FALSE, 'Disponible', 6, 1, 1, 6, 6, 3, 3, 7),
                                                                                                                                                                                                  (2019, 60000, 23000.00, FALSE, 'Vendido', 7, 2, 1, 7, 7, 4, 2, 8),
                                                                                                                                                                                                  (2020, 35000, 16000.00, FALSE, 'Disponible', 8, 1, 1, 8, 8, 3, 3, 9),
                                                                                                                                                                                                  (2022, 12000, 20500.00, TRUE, 'Disponible', 9, 1, 1, 9, 9, 3, 3, 10),
                                                                                                                                                                                                  (2021, 8000, 48000.00, TRUE, 'Disponible', 10, 1, 1, 10, 10, 3, 10, 11),
                                                                                                                                                                                                  (2021, 25000, 35000.00, TRUE, 'Disponible', 11, 2, 2, 11, 11, 3, 1, 12),
                                                                                                                                                                                                  (2022, 14000, 55000.00, TRUE, 'Disponible', 12, 2, 2, 12, 12, 3, 2, 13),
                                                                                                                                                                                                  (2019, 50000, 29000.00, TRUE, 'Disponible', 13, 2, 2, 13, 13, 3, 1, 14),
                                                                                                                                                                                                  (2020, 38000, 22000.00, FALSE, 'Disponible', 14, 1, 2, 14, 14, 3, 3, 15),
                                                                                                                                                                                                  (2021, 19000, 31000.00, TRUE, 'Disponible', 15, 1, 2, 15, 15, 3, 1, 16),
                                                                                                                                                                                                  (2022, 9000, 39500.00, TRUE, 'Vendido', 16, 2, 2, 16, 16, 3, 2, 17),
                                                                                                                                                                                                  (2023, 2000, 21000.00, FALSE, 'Disponible', 17, 3, 3, 17, 17, 2, 3, 18),
                                                                                                                                                                                                  (2021, 28000, 23500.00, FALSE, 'Disponible', 18, 1, 1, 18, 18, 3, 3, 19),
                                                                                                                                                                                                  (2022, 11000, 18900.00, FALSE, 'Disponible', 19, 1, 1, 19, 19, 3, 3, 20),
                                                                                                                                                                                                  (2022, 13000, 34000.00, TRUE, 'Disponible', 20, 2, 2, 20, 20, 3, 2, 3);

-- 5. IMAGENES, FACTURAS Y LINEAS

INSERT INTO imagenes_coches (url_imagen, es_principal, coche_id, datos_imagen)
SELECT '/assets/img/coche_' || id_coche || '.jpg', TRUE, id_coche, NULL FROM coches;

INSERT INTO facturas (comprador_id, total_base, iva_importe, comision_plataforma, total_pagado) VALUES
                                                                                                    (3, 23000.00, 4830.00, 690.00, 28520.00), (4, 39500.00, 8295.00, 1185.00, 48980.00);

INSERT INTO lineas_factura (factura_id, coche_id, precio_venta_momento) VALUES
                                                                            (1, 7, 23000.00), (2, 16, 39500.00);

-- 6. FAVORITOS
INSERT INTO favoritos (usuario_id, coche_id) VALUES
(2, 1), (2, 5), (3, 1), (4, 10), (5, 12),
(2, 8), (3, 5), (4, 1), (5, 2), (6, 12),
(7, 4), (8, 9), (9, 15), (10, 20), (11, 3);

-- Finalización de script
SELECT setval('ciudades_id_ciudad_seq', (SELECT MAX(id_ciudad) FROM ciudades));
SELECT setval('marcas_id_marca_seq', (SELECT MAX(id_marca) FROM marcas));
SELECT setval('usuarios_id_usuario_seq', (SELECT MAX(id_usuario) FROM usuarios));
SELECT setval('coches_id_coche_seq', (SELECT MAX(id_coche) FROM coches));