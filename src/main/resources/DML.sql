-- 1. CATÁLOGOS BASE
INSERT INTO TIPOS_COMBUSTIBLE (NOMBRE) VALUES ('Gasolina'), ('Diesel'), ('Híbrido'), ('Eléctrico'), ('GLP');
INSERT INTO TIPOS_TRANSMISION (NOMBRE) VALUES ('Manual'), ('Automático'), ('Semiautomático');
INSERT INTO ETIQUETAS_AMBIENTALES (NOMBRE) VALUES ('0'), ('Eco'), ('C'), ('B'), ('Sin Etiqueta');
INSERT INTO CATEGORIAS (NOMBRE) VALUES ('Sedán'), ('SUV'), ('Compacto'), ('Deportivo'), ('Monovolumen'), ('Pick-up'), ('Cabrio'), ('Furgoneta');
INSERT INTO CIUDADES (NOMBRE) VALUES ('Madrid'), ('Barcelona'), ('Valencia'), ('Sevilla'), ('Zaragoza'), ('Málaga'), ('Murcia'), ('Palma'), ('Bilbao'), ('Alicante');
INSERT INTO COLORES (NOMBRE) VALUES ('Blanco'), ('Negro'), ('Gris Plata'), ('Gris Antracita'), ('Azul Marino'), ('Rojo'), ('Verde Oliva'), ('Amarillo'), ('Naranja'), ('Beige'), ('Marrón'), ('Violeta'), ('Turquesa'), ('Granate'), ('Oro'), ('Bronce'), ('Azul Cielo'), ('Verde Esmeralda'), ('Blanco Perla'), ('Negro Mate');

-- 2. MARCAS (10)
INSERT INTO MARCAS (NOMBRE) VALUES ('Toyota'), ('Volkswagen'), ('BMW'), ('Audi'), ('Mercedes-Benz'), ('Ford'), ('Hyundai'), ('Renault'), ('Peugeot'), ('Kia');

-- 3. MODELOS (10 por Marca = 100 modelos)
-- Toyota (ID 1)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('Corolla',1),('Yaris',1),('RAV4',1),('Hilux',1),('Prius',1),('Camry',1),('C-HR',1),('Land Cruiser',1),('Supra',1),('Aygo',1);
-- Volkswagen (ID 2)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('Golf',2),('Polo',2),('Tiguan',2),('Passat',2),('T-Roc',2),('Arteon',2),('Touareg',2),('ID.3',2),('ID.4',2),('Touran',2);
-- BMW (ID 3)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('Serie 1',3),('Serie 2',3),('Serie 3',3),('Serie 4',3),('Serie 5',3),('X1',3),('X3',3),('X5',3),('M3',3),('Z4',3);
-- Audi (ID 4)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('A1',4),('A3',4),('A4',4),('A5',4),('A6',4),('Q2',4),('Q3',4),('Q5',4),('TT',4),('R8',4);
-- Mercedes (ID 5)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('Clase A',5),('Clase B',5),('Clase C',5),('Clase E',5),('GLA',5),('GLC',5),('GLE',5),('EQA',5),('EQS',5),('AMG GT',5);
-- Ford (ID 6)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('Fiesta',6),('Focus',6),('Mondeo',6),('Kuga',6),('Puma',6),('Mustang',6),('Explorer',6),('Ranger',6),('S-Max',6),('EcoSport',6);
-- Hyundai (ID 7)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('i10',7),('i20',7),('i30',7),('Ioniq',7),('Kona',7),('Tucson',7),('Santa Fe',7),('Bayon',7),('Nexo',7),('Staria',7);
-- Renault (ID 8)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('Clio',8),('Megane',8),('Captur',8),('Kadjar',8),('Austral',8),('Zoe',8),('Arkana',8),('Scenic',8),('Twingo',8),('Talisman',8);
-- Peugeot (ID 9)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('208',9),('308',9),('508',9),('2008',9),('3008',9),('5008',9),('Rifter',9),('Traveller',9),('RCZ',9),('108',9);
-- Kia (ID 10)
INSERT INTO MODELOS (NOMBRE, MARCA_ID) VALUES ('Picanto',10),('Rio',10),('Ceed',10),('Sportage',10),('Sorento',10),('Niro',10),('Stinger',10),('EV6',10),('Stonic',10),('XCeed',10);

-- 4. VERSIONES (Ejemplos representativos para completar lógica)
INSERT INTO VERSIONES (NOMBRE, MODELO_ID) VALUES 
-- Toyota Corolla (ID 1)
('1.8 Hybrid Active',1),('2.0 Hybrid Style',1),('GR-Sport',1),
-- VW Golf (ID 11)
('1.0 TSI Life',11),('2.0 TDI R-Line',11),('GTI',11),
-- BMW Serie 1 (ID 21)
('118i',21),('120d M Sport',21),
-- BMW Serie 2 (ID 22)
('218i Gran Coupe',22),('M235i xDrive',22),
-- BMW Serie 3 (ID 23)
('320d',23),('330e Hybrid',23),
-- Audi A3 (ID 32)
('30 TFSI',32),('35 TDI S line',32),
-- Mercedes Clase A (ID 41)
('A 200',41),('A 250 e',41);

-- 5. USUARIOS (20)
-- IDs 1 al 10: Vendedores | IDs 11 al 20: Compradores
INSERT INTO USUARIOS (NOMBRES, APELLIDOS, DNI, EMAIL, PASSWORD, TELEFONO) VALUES 
('Juan','García','11111111A','juan@mail.com','hash','600000001'),('Maria','López','22222222B','maria@mail.com','hash','600000002'),
('Carlos','Pérez','33333333C','carlos@mail.com','hash','600000003'),('Ana','Marta','44444444D','ana@mail.com','hash','600000004'),
('Luis','Sanz','55555555E','luis@mail.com','hash','600000005'),('Elena','Gil','66666666F','elena@mail.com','hash','600000006'),
('Pablo','Ruiz','77777777G','pablo@mail.com','hash','600000007'),('Lucia','Díaz','88888888H','lucia@mail.com','hash','600000008'),
('Jorge','Mora','99999999I','jorge@mail.com','hash','600000009'),('Sofia','Vidal','00000000J','sofia@mail.com','hash','600000010'),
('Diego','Roca','12345678K','diego@mail.com','hash','611000001'),('Laura','Blanco','23456789L','laura@mail.com','hash','611000002'),
('Marta','Cano','34567890M','marta@mail.com','hash','611000003'),('Raul','Ortiz','45678901N','raul@mail.com','hash','611000004'),
('Silvia','Marín','56789012O','silvia@mail.com','hash','611000005'),('Victor','Gómez','67890123P','victor@mail.com','hash','611000006'),
('Nerea','Sánchez','78901234Q','nerea@mail.com','hash','611000007'),('Oscar','Peña','89012345R','oscar@mail.com','hash','611000008'),
('Rocío','Luna','90123456S','rocio@mail.com','hash','611000009'),('Ivan','Toro','01234567T','ivan@mail.com','hash','611000010');

-- 6. COCHES (40 Anuncios)
-- 10 Vendidos (IDs 1-10), 5 Desactivados (IDs 11-15), 25 Disponibles (IDs 16-40)
INSERT INTO COCHES (ANIO_FABRICACION, KILOMETRAJE, PRECIO_VENTA, ESTADO, URL_IMAGEN, VERSION_ID, COMBUSTIBLE_ID, TRANSMISION_ID, CIUDAD_ID, COLOR_ID, ETIQUETA_ID, CATEGORIA_ID, VENDEDOR_ID) VALUES 
-- VENDIDOS
(2020, 50000, 20000.00, 'Vendido', 'public/assets/img/cars/toyota_corolla_1.jpg', 1, 3, 2, 1, 1, 2, 1, 1),
(2019, 75000, 18500.00, 'Vendido', 'public/assets/img/cars/vw_golf_1.jpg', 11, 1, 1, 2, 2, 3, 3, 2),
(2021, 15000, 32000.00, 'Vendido', 'public/assets/img/cars/toyota_corolla_2.jpg', 3, 3, 2, 3, 5, 2, 1, 3),
(2018, 90000, 14000.00, 'Vendido', 'public/assets/img/cars/vw_golf_2.jpg', 12, 2, 1, 4, 3, 4, 3, 4),
(2022, 5000, 45000.00, 'Vendido', 'public/assets/img/cars/luxury_hybrid.jpg', 7, 4, 2, 5, 20, 1, 4, 5),
(2017, 120000, 11000.00, 'Vendido', 'public/assets/img/cars/old_reliable.jpg', 10, 1, 1, 6, 4, 3, 1, 6),
(2020, 35000, 27000.00, 'Vendido', 'public/assets/img/cars/suv_white.jpg', 15, 1, 2, 1, 1, 3, 2, 7),
(2019, 60000, 19000.00, 'Vendido', 'public/assets/img/cars/compact_red.jpg', 5, 2, 1, 2, 6, 4, 3, 8),
(2021, 22000, 29500.00, 'Vendido', 'public/assets/img/cars/electric_blue.jpg', 14, 4, 2, 3, 17, 1, 3, 9),
(2018, 95000, 13500.00, 'Vendido', 'public/assets/img/cars/diesel_grey.jpg', 8, 2, 1, 1, 3, 4, 1, 10),
-- DESACTIVADOS
(2015, 160000, 7000.00, 'Desactivado', 'public/assets/img/cars/off_1.jpg', 1, 1, 1, 1, 2, 5, 1, 1),
(2016, 140000, 8500.00, 'Desactivado', 'public/assets/img/cars/off_2.jpg', 2, 2, 1, 2, 4, 4, 1, 2),
(2014, 200000, 5000.00, 'Desactivado', 'public/assets/img/cars/off_3.jpg', 11, 2, 1, 1, 3, 5, 3, 3),
(2015, 130000, 9000.00, 'Desactivado', 'public/assets/img/cars/off_4.jpg', 12, 1, 1, 4, 1, 3, 3, 4),
(2013, 220000, 4000.00, 'Desactivado', 'public/assets/img/cars/off_5.jpg', 5, 2, 1, 5, 2, 5, 1, 5),
-- DISPONIBLES (Muestra de los 25 requeridos)
(2023, 10, 35000.00, 'Disponible', 'public/assets/img/cars/new_corolla.jpg', 1, 3, 2, 1, 1, 2, 1, 6),
(2022, 12000, 28000.00, 'Disponible', 'public/assets/img/cars/golf_rline.jpg', 12, 1, 2, 2, 2, 3, 3, 7),
(2023, 50, 55000.00, 'Disponible', 'public/assets/img/cars/bmw_s3.jpg', 20, 1, 2, 1, 2, 3, 1, 8),
(2022, 18000, 31000.00, 'Disponible', 'public/assets/img/cars/audi_a3.jpg', 15, 1, 2, 3, 4, 3, 3, 9),
(2023, 100, 42000.00, 'Disponible', 'public/assets/img/cars/mercedes_a.jpg', 18, 3, 2, 1, 19, 2, 1, 10);
-- [Repetir hasta 40 registros con rutas similares]

-- 7. FACTURAS (Para los 10 coches vendidos)
-- Cálculo rápido: Base + 21% IVA + 3% Comisión = Base * 1.24
INSERT INTO FACTURAS (COMPRADOR_ID, VENDEDOR_ID, TOTAL_BASE, TOTAL_PAGADO, ID_TRANSACCION_BANCARIA) VALUES 
(11, 1, 20000.00, 24800.00, 'SIM-TX-001'),(12, 2, 18500.00, 22940.00, 'SIM-TX-002'),
(13, 3, 32000.00, 39680.00, 'SIM-TX-003'),(14, 4, 14000.00, 17360.00, 'SIM-TX-004'),
(15, 5, 45000.00, 55800.00, 'SIM-TX-005'),(16, 6, 11000.00, 13640.00, 'SIM-TX-006'),
(17, 7, 27000.00, 33480.00, 'SIM-TX-007'),(18, 8, 19000.00, 23560.00, 'SIM-TX-008'),
(19, 9, 29500.00, 36580.00, 'SIM-TX-009'),(20, 10, 13500.00, 16740.00, 'SIM-TX-010');

-- 8. LINEAS DE FACTURA
INSERT INTO LINEAS_FACTURA (FACTURA_ID, COCHE_ID, PRECIO_VENTA_MOMENTO) VALUES 
(1,1,20000.00),(2,2,18500.00),(3,3,32000.00),(4,4,14000.00),(5,5,45000.00),
(6,6,11000.00),(7,7,27000.00),(8,8,19000.00),(9,9,29500.00),(10,10,13500.00);

-- 9. FAVORITOS (Check de favoritos)
INSERT INTO FAVORITOS (USUARIO_ID, COCHE_ID) VALUES 
(11, 16), (11, 17), -- Diego tiene favoritos disponibles
(12, 1),           -- Laura tiene uno que ya se vendió
(13, 18), (14, 19), (15, 20), (16, 16), (17, 1);