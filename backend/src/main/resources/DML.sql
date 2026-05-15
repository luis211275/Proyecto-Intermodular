INSERT INTO
        TIPOS_COMBUSTIBLE (NOMBRE)
VALUES
        ('Gasolina'),
        ('Diesel'),
        ('Híbrido'),
        ('Eléctrico'),
        ('GLP');

INSERT INTO
        TIPOS_TRANSMISION (NOMBRE)
VALUES
        ('Manual'),
        ('Automático'),
        ('Semiautomático');

INSERT INTO
        ETIQUETAS_AMBIENTALES (NOMBRE)
VALUES
        ('0'),
        ('Eco'),
        ('C'),
        ('B'),
        ('Sin Etiqueta');

INSERT INTO
        CATEGORIAS (NOMBRE)
VALUES
        ('Sedán'),
        ('SUV'),
        ('Compacto'),
        ('Deportivo'),
        ('Monovolumen'),
        ('Pick-up');

INSERT INTO
        CIUDADES (NOMBRE)
VALUES
        ('Madrid'),
        ('Barcelona'),
        ('Valencia'),
        ('Sevilla'),
        ('Zaragoza'),
        ('Málaga'),
        ('Murcia'),
        ('Palma de Mallorca'),
        ('Las Palmas de Gran Canaria'),
        ('Bilbao'),
        ('Alicante'),
        ('Córdoba'),
        ('Valladolid'),
        ('Vigo'),
        ('Gijón'),
        ('Hospitalet de Llobregat'),
        ('Vitoria'),
        ('A Coruña'),
        ('Granada'),
        ('Elche');

INSERT INTO
        COLORES (NOMBRE)
VALUES
        ('Blanco'),
        ('Negro'),
        ('Gris'),
        ('Rojo'),
        ('Azul'),
        ('Amarillo'),
        ('Verde'),
        ('Naranja'),
        ('Marrón'),
        ('Beige'),
        ('Plateado'),
        ('Dorado'),
        ('Antracita'),
        ('Granate'),
        ('Crema');

INSERT INTO
        MARCAS (NOMBRE)
VALUES
        ('Toyota'),
        ('Volkswagen'),
        ('BMW'),
        ('Audi'),
        ('Mercedes-Benz'),
        ('Ford'),
        ('Hyundai'),
        ('Renault'),
        ('Peugeot'),
        ('Kia'),
        ('Seat'),
        ('Nissan'),
        ('Volvo'),
        ('Mazda'),
        ('Skoda');

INSERT INTO
        MODELOS (NOMBRE, MARCA_ID)
VALUES
        ('Corolla', 1),
        ('RAV4', 1),
        ('Golf', 2),
        ('Serie 3', 3),
        ('A3', 4),
        ('Clase C', 5),
        ('Clase GLA', 5),
        ('Focus', 6),
        ('Mustang', 6),
        ('Tucson', 7),
        ('i30', 7),
        ('Clio', 8),
        ('Megane', 8),
        ('208', 9),
        ('3008', 9),
        ('Sportage', 10),
        ('EV6', 10),
        ('Ibiza', 11),
        ('Leon', 11),
        ('Qashqai', 12),
        ('Juke', 12),
        ('XC40', 13),
        ('XC60', 13),
        ('CX-5', 14),
        ('Mazda3', 14),
        ('Octavia', 15),
        ('Kodiaq', 15),
        ('Yaris Cross', 1),
        ('T-Roc', 2),
        ('Serie 1', 3),
        ('Q5', 4),
        ('Clase E', 5),
        ('Puma', 6),
        ('Kona', 7),
        ('Austral', 8),
        ('2008', 9),
        ('Ceed', 10),
        ('Arona', 11),
        ('X-Trail', 12),
        ('V60', 13),
        ('CX-30', 14),
        ('Fabia', 15);

INSERT INTO
        VERSIONES (NOMBRE, MODELO_ID)
VALUES
        ('1.8 Hybrid Active', 1),
        ('2.0 Hybrid Style', 1),
        ('2.5 Hybrid AWD', 2),
        ('1.5 TSI Life', 3),
        ('GTI 2.0 TSI', 3),
        ('320d M Sport', 4),
        ('330e PHEV', 4),
        ('35 TFSI S line', 5),
        ('30 TDI Advanced', 5),
        ('C 220 d Avantgarde', 6),
        ('AMG C 43 4MATIC', 6),
        ('GLA 200 Progressive', 7),
        ('1.0 EcoBoost Trend', 8),
        ('ST-Line 1.5 EcoBlue', 8),
        ('5.0 V8 GT Fastback', 9),
        ('1.6 TGDI HEV Tecno', 10),
        ('1.6 CRDi 48V N Line', 11),
        ('1.0 TCe Evolution', 12),
        ('E-Tech Full Hybrid', 13),
        ('1.2 PureTech Allure', 14),
        ('Hybrid 225 e-EAT8 GT', 15),
        ('1.6 T-GDI HEV Drive', 16),
        ('Long Range AWD GT-Line', 17),
        ('1.0 TSI FR', 18),
        ('1.5 eTSI DSG Xcellence', 19),
        ('1.3 DIG-T mHEV N-Connecta', 20),
        ('e-Power 190 CV Tekna', 20),
        ('DIG-T 114 CV Tekna', 21),
        ('B3 Mild Hybrid Core', 22),
        ('Recharge Plug-in T6', 23),
        ('2.0 e-Skyactiv G Origin', 24),
        ('2.5 e-Skyactiv PHEV', 24),
        ('e-Skyactiv X Zenit', 25),
        ('2.0 TDI DSG Ambition', 26),
        ('1.5 TSI mHEV Selection', 26),
        ('2.0 TSI DSG RS', 27),
        ('GR Sport 2.0 Hybrid', 1),
        ('Adventure Plus 2.5 AWD', 2),
        ('116 CV Hybrid Style', 28),
        ('1.0 TSI R-Line', 29),
        ('120i M Sport', 30),
        ('40 TDI quattro S line', 31),
        ('E 300 de PHEV Luxury', 32),
        ('1.0 EcoBoost ST-Line X', 33),
        ('1.6 TGDI HEV Maxx', 34),
        ('E-Tech Full Hybrid esprit Alpine', 35),
        ('1.2 PureTech GT', 36),
        ('1.0 T-GDI Concept', 37),
        ('1.0 TSI FR Limited Edition', 38),
        ('e-Power 213 CV e-4ORCE', 39),
        ('B4 Mild Hybrid Plus', 40),
        ('2.0 e-Skyactiv G Homura', 41),
        ('1.0 TSI Monte Carlo', 42);

INSERT INTO
        USUARIOS (
                NOMBRES,
                APELLIDOS,
                DNI,
                EMAIL,
                PASSWORD,
                TELEFONO
        )
VALUES
        (
                'Juan',
                'García',
                '11111111A',
                'juan.garcia@mail.com',
                'hash123',
                '600000001'
        ),
        (
                'Maria',
                'López',
                '22222222B',
                'maria.lopez@mail.com',
                'hash123',
                '600000002'
        ),
        (
                'Carlos',
                'Pérez',
                '33333333C',
                'carlos.perez@mail.com',
                'hash123',
                '600000003'
        ),
        (
                'Ana',
                'Martínez',
                '44444444D',
                'ana.martinez@mail.com',
                'hash123',
                '600000004'
        ),
        (
                'Luis',
                'Rodríguez',
                '55555555E',
                'l.rodriguez@mail.com',
                'hash123',
                '600000005'
        ),
        (
                'Elena',
                'Sánchez',
                '66666666F',
                'elena.sanchez@mail.com',
                'hash123',
                '600000006'
        ),
        (
                'Javier',
                'Fernández',
                '77777777G',
                'javi.fer@mail.com',
                'hash123',
                '600000007'
        ),
        (
                'Lucía',
                'Gómez',
                '88888888H',
                'lucia.gomez@mail.com',
                'hash123',
                '600000008'
        ),
        (
                'Diego',
                'Ruiz',
                '99999999I',
                'diego.ruiz@mail.com',
                'hash123',
                '600000009'
        ),
        (
                'Carmen',
                'Jiménez',
                '00000000J',
                'carmen.jim@mail.com',
                'hash123',
                '600000010'
        );

INSERT INTO
        COCHES (
                ANIO_FABRICACION,
                KILOMETRAJE,
                PRECIO_VENTA,
                ESTADO,
                IMAGEN,
                VERSION_ID,
                COMBUSTIBLE_ID,
                TRANSMISION_ID,
                CIUDAD_ID,
                COLOR_ID,
                ETIQUETA_ID,
                CATEGORIA_ID,
                VENDEDOR_ID
        )
VALUES
        -- 1. Audi A3 gris
        (
                2023,
                12546,
                38000.00,
                'Disponible',
                '/assets/img/cars/audi_a3_grey.jpg',
                8,
                1,
                2,
                1,
                3,
                3,
                3,
                1
        ),
        -- 2. Audi A3 antiguo
        (
                2017,
                140000,
                15000.00,
                'Disponible',
                '/assets/img/cars/audi_a3_old.jpg',
                9,
                2,
                1,
                2,
                3,
                4,
                3,
                2
        ),
        -- 3. BMW Serie 3 negro
        (
                2021,
                45000,
                31000.00,
                'Disponible',
                '/assets/img/cars/bmw_black.jpg',
                6,
                2,
                2,
                1,
                2,
                3,
                1,
                3
        ),
        -- 4. BMW Serie 3 híbrido
        (
                2022,
                15000,
                48000.00,
                'Disponible',
                '/assets/img/cars/bmw_hybrid.jpg',
                7,
                3,
                2,
                3,
                1,
                2,
                1,
                4
        ),
        -- 5. Toyota Corolla azul
        (
                2019,
                85000,
                19500.00,
                'Disponible',
                '/assets/img/cars/corolla_blue.jpg',
                2,
                3,
                2,
                2,
                5,
                2,
                1,
                5
        ),
        -- 6. Toyota Corolla blanco
        (
                2023,
                1525,
                35500.00,
                'Disponible',
                '/assets/img/cars/corolla_white.jpg',
                1,
                3,
                2,
                1,
                1,
                2,
                1,
                6
        ),
        -- 7. Volkswagen Golf GTI
        (
                2023,
                69536,
                45000.00,
                'Disponible',
                '/assets/img/cars/golf_gti.jpg',
                5,
                1,
                2,
                4,
                4,
                3,
                3,
                7
        ),
        -- 8. Volkswagen Golf rojo
        (
                2022,
                12500,
                29000.00,
                'Disponible',
                '/assets/img/cars/golf_red.jpg',
                4,
                1,
                2,
                2,
                4,
                3,
                3,
                8
        ),
        -- 9. Volkswagen Golf plata
        (
                2020,
                70000,
                22500.00,
                'Disponible',
                '/assets/img/cars/golf_silver.jpg',
                4,
                2,
                1,
                5,
                3,
                3,
                3,
                9
        ),
        -- 10. Toyota RAV4 antiguo
        (
                2018,
                110000,
                21000.00,
                'Disponible',
                '/assets/img/cars/rav4_old.jpg',
                3,
                3,
                2,
                5,
                2,
                2,
                2,
                10
        );