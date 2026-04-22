-- SCRIPT DDL: MARKETPLACE INTERMODULAR (ESTRUCTURA FINAL OPTIMIZADA)

-- 1. CATALOGOS BASE

CREATE TABLE tipos_combustible (
                                   id_combustible SERIAL PRIMARY KEY,
                                   nombre VARCHAR(20) NOT NULL
);

CREATE TABLE tipos_transmision (
                                   id_transmision SERIAL PRIMARY KEY,
                                   nombre VARCHAR(20) NOT NULL
);

CREATE TABLE ciudades (
                          id_ciudad SERIAL PRIMARY KEY,
                          nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE colores (
                         id_color SERIAL PRIMARY KEY,
                         nombre VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE etiquetas_ambientales (
                                       id_etiqueta SERIAL PRIMARY KEY,
                                       nombre VARCHAR(15) UNIQUE NOT NULL
);

CREATE TABLE categorias (
                            id_categoria SERIAL PRIMARY KEY,
                            nombre VARCHAR(30) UNIQUE NOT NULL
);

-- 2. JERARQUIA DE MARCAS Y PRODUCTOS

CREATE TABLE marcas (
                        id_marca SERIAL PRIMARY KEY,
                        nombre VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE modelos (
                         id_modelo SERIAL PRIMARY KEY,
                         nombre VARCHAR(50) NOT NULL,
                         marca_id INT NOT NULL REFERENCES marcas(id_marca) ON DELETE CASCADE
);

CREATE TABLE versiones (
                           id_version SERIAL PRIMARY KEY,
                           nombre VARCHAR(80) NOT NULL,
                           modelo_id INT NOT NULL REFERENCES modelos(id_modelo) ON DELETE CASCADE
);

-- 3. GESTION DE USUARIOS

CREATE TABLE usuarios (
                          id_usuario SERIAL PRIMARY KEY,
                          nombres VARCHAR(50) NOT NULL,
                          apellidos VARCHAR(50) NOT NULL,
                          dni VARCHAR(10) UNIQUE NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          password VARCHAR(64) NOT NULL, -- Almacenará el hash de la contraseña
                          telefono VARCHAR(15),
                          tipo_usuario VARCHAR(10) DEFAULT 'USER' -- 'ADMIN', 'USER'
);

-- 4. ENTIDAD PRINCIPAL: COCHES

CREATE TABLE coches (
                        id_coche SERIAL PRIMARY KEY,
                        anio_fabricacion SMALLINT NOT NULL,
                        kilometraje INT NOT NULL,
                        precio_venta DECIMAL(10, 2) NOT NULL,
                        es_premium BOOLEAN DEFAULT FALSE,
                        estado VARCHAR(15) DEFAULT 'Disponible',
                        fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Relaciones (Normalización Total)
                        version_id INT NOT NULL REFERENCES versiones(id_version),
                        combustible_id INT NOT NULL REFERENCES tipos_combustible(id_combustible),
                        transmision_id INT NOT NULL REFERENCES tipos_transmision(id_transmision),
                        ciudad_id INT NOT NULL REFERENCES ciudades(id_ciudad),
                        color_id INT NOT NULL REFERENCES colores(id_color),
                        etiqueta_id INT NOT NULL REFERENCES etiquetas_ambientales(id_etiqueta),
                        categoria_id INT NOT NULL REFERENCES categorias(id_categoria),
                        vendedor_id INT NOT NULL REFERENCES usuarios(id_usuario)
);

-- 5. MULTIMEDIA Y TRANSACCIONES

CREATE TABLE imagenes_coches (
                                 id_imagen SERIAL PRIMARY KEY,
                                 datos_imagen BYTEA, -- Opcional, mantenemos por compatibilidad
                                 url_imagen VARCHAR(255), -- Ruta de la imagen
                                 extension VARCHAR(10),
                                 es_principal BOOLEAN DEFAULT FALSE,
                                 coche_id INT NOT NULL REFERENCES coches(id_coche) ON DELETE CASCADE
);

CREATE TABLE facturas (
    id_factura SERIAL PRIMARY KEY,
    comprador_id INT NOT NULL REFERENCES usuarios(id_usuario),
    total_base DECIMAL(10, 2) NOT NULL,
    iva_importe DECIMAL(10, 2) NOT NULL,
    comision_plataforma DECIMAL(10, 2) NOT NULL,
    total_pagado DECIMAL(10, 2) NOT NULL,
    fecha_factura TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE lineas_factura (
                                id_linea SERIAL PRIMARY KEY,
                                factura_id INT NOT NULL REFERENCES facturas(id_factura) ON DELETE CASCADE,
                                coche_id INT NOT NULL REFERENCES coches(id_coche),
                                precio_venta_momento DECIMAL(10, 2) NOT NULL -- Precio histórico al momento de la venta
);

-- 6. AUDITORIA

CREATE TABLE auditoria_logs (
                                id_log SERIAL PRIMARY KEY,
                                usuario_id INT REFERENCES usuarios(id_usuario),
                                accion VARCHAR(50) NOT NULL,
                                detalles TEXT,
                                fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. FAVORITOS

CREATE TABLE favoritos (
    usuario_id INT NOT NULL REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    coche_id INT NOT NULL REFERENCES coches(id_coche) ON DELETE CASCADE,
    fecha_agregado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (usuario_id, coche_id)
);