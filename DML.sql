CREATE TABLE coches (
    id_coche SERIAL PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    color VARCHAR(30) NOT NULL,
    anio INT NOT NULL,
    kilometraje INT NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    combustible VARCHAR(50) NOT NULL,
    transmision VARCHAR(50) NOT NULL,
    img VARCHAR(500) NOT NULL
);

INSERT INTO coches (marca, modelo, precio, color, anio, kilometraje, ciudad, combustible, transmision, img) VALUES
('BMW', 'Serie 3', '20000', 'Negro', 2019, '50000', 'Madrid', 'Diesel', 'Manual', 'https://immagini.alvolante.it/sites/default/files/styles/image_gallery_big/public/news_galleria/2022/05/bmw.serie-3-restyling-2022-cina_08.jpg'),
('Audi', 'A4', '25000', 'Blanco', 2020, '30000', 'Barcelona', 'Gasolina', 'Automático', 'https://prod.pictures.autoscout24.net/listing-images/44c43247-8427-42ee-a696-b35ab2215aae_5c4b80ed-a815-40f3-997d-cc16fe49bc66.jpg/1920x1080.webp'),
('Mercedes', 'Clase C', '27000', 'Gris', 2018, '60000', 'Valencia', 'Diesel', 'Automático', 'https://cdn.media.kaavan.es/blobs/noticias/38690b1f-2e54-4904-be9c-30958a361f60/medias/35679.jpg'),
('Volkswagen', 'Golf', '18000', 'Rojo', 2021, '20000', 'Sevilla', 'Gasolina', 'Manual', 'https://cdn.motor1.com/images/mgl/W8Y60O/240:0:1439:1080/volkswagen-golf-tsi-115-cv-2024.webp'),
('Toyota', 'Corolla', '22000', 'Azul', 2022, '15000', 'Bilbao', 'Híbrido', 'Automático', 'https://images.prismic.io/carwow/ee269639-71b1-4e69-b739-c138f869187c_Toyota+Corolla+Sedan-10.jpg'),
('Ford', 'Focus', '17000', 'Blanco', 2019, '40000', 'Zaragoza', 'Diesel', 'Manual', 'https://cdncla.lavoz.com.ar/files/avisos/aviso_auto/aviso-auto-ford-focus-14080160.webp'),
('Seat', 'León', '16000', 'Gris', 2020, '35000', 'Málaga', 'Gasolina', 'Manual', 'https://www.diariomotor.com/imagenes/2020/01/seat-leon-sportstourer-2020-fr-gris-magnetic-tech-03.jpg?class=XL'),
('Hyundai', 'i30', '15000', 'Negro', 2018, '55000', 'Granada', 'Diesel', 'Manual', 'https://ajracingcars.com/wp-content/uploads/2024/10/IMG_7185-scaled.jpeg'),
('Kia', 'Ceed', '15500', 'Azul', 2021, '25000', 'Alicante', 'Gasolina', 'Manual', 'https://hackercar.com/wp-content/uploads/2019/07/kia-ceed-di%C3%A9sel-prueba-hackercar.jpg'),
('Peugeot', '308', '16500', 'Blanco', 2019, '42000', 'Murcia', 'Diesel', 'Manual', 'https://www.autofacil.es/wp-content/uploads/2021/05/peugeot-15g-1.jpg');


select * from coches