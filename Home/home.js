const contenedor = document.querySelector(".contenedor");
const modal = document.getElementById("modalCoche");

// 1. Función para obtener los coches del servidor Java
async function cargarCoches() {
    try {
        const respuesta = await fetch('http://localhost:8080/coches');
        const coches = await respuesta.json();
        listarCoches(coches);
    } catch (error) {
        console.error("Error al conectar con el servidor:", error);
        contenedor.innerHTML = "<p>Error al cargar los coches. ¿Está el servidor encendido?</p>";
    }
}

// 2. Función para crear las tarjetas dinámicamente
function listarCoches(lista) {
    contenedor.innerHTML = "";

    lista.forEach(coche => {
        const tarjeta = document.createElement("div");
        tarjeta.classList.add("tarjeta");
        tarjeta.innerHTML = `
            <h3>${coche.marca} ${coche.modelo}</h3>
            <p>${coche.precio} € - ${coche.ciudad}</p>
            <img src="${coche.img}" alt="${coche.marca}">
        `;
        
        tarjeta.addEventListener("click", () => abrirModal(coche));
        contenedor.appendChild(tarjeta);
    });
}

// 3. Función para mostrar el detalle en el Modal
function abrirModal(coche) {
    document.getElementById("modalTitulo").innerText = `${coche.marca} ${coche.modelo}`;
    document.getElementById("modalImagen").src = coche.img;
    document.getElementById("modalAnio").innerText = coche.anio;
    document.getElementById("modalPrecio").innerText = coche.precio + " €";
    document.getElementById("modalColor").innerText = coche.color;
    document.getElementById("modalCarburante").innerText = coche.combustible;
    document.getElementById("modalTransmision").innerText = coche.transmision;
    document.getElementById("modalKM").innerText = coche.kilometraje;
    document.getElementById("modalCiudad").innerText = coche.ciudad;

    modal.showModal();
}

cargarCoches();

document.getElementById("cerrarModal").addEventListener("click", () => modal.close());


    fetch('http://localhost:8080/apis/coches/listarcoches')
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('contenedor-coches');
            data.forEach(coche => {
                container.innerHTML += `
                    <div class="card">
                        <img src="${coche.img || 'placeholder.jpg'}" width="200">
                        <h3>${coche.marca} ${coche.modelo}</h3>
                        <p>Precio: ${coche.precioVenta}€</p>
                        <p>Año: ${coche.anioFabricacion}</p>
                    </div>
                `;
            });
        })
        .catch(error => console.error('Error:', error));