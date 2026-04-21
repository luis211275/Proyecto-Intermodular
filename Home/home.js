// const buscarCoches = document.getElementById("buscar");
// const btnReset = document.getElementById("resetear");
// const btnFav = document.getElementById("favoritos");

// const modal = document.getElementById("modalCoche");
// const modalTitulo = document.getElementById("modalTitulo");
// const btnCerrarModal = document.getElementById("cerrarModal");
// const modalImg = document.getElementById("modalImagen");
// const modalAnio = document.getElementById("modalAnio");
// const modalPrecio = document.getElementById("modalPrecio");
// const modalColor = document.getElementById("modalColor");
// const modalCarburante = document.getElementById("modalCarburante");
// const modalTransmision = document.getElementById("modalTransmision");
// const modalKM = document.getElementById("modalKM");
// const modalCiudad = document.getElementById("modalCiudad");
// const modalDescripcion = document.getElementById("modalDescripcion");

// function abrirModal(coche) {
//     modalTitulo.innerText = `${coche}`;
//     modalAnio
//     modalPrecio
//     modalColor
//     modalCarburante
//     modalTransmision
//     modalKM
//     modalCiudad
//     modalDescripcion

//     modal.showModal();
// }

// btnCerrarModal.addEventListener("click", () => {
//     modal.close();
// });

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
    contenedor.innerHTML = ""; // Limpiamos las tarjetas estáticas del HTML

    lista.forEach(coche => {
        const tarjeta = document.createElement("div");
        tarjeta.classList.add("tarjeta");
        tarjeta.innerHTML = `
            <h3>${coche.marca} ${coche.modelo}</h3>
            <p>${coche.precio} € - ${coche.ciudad}</p>
            <img src="${coche.img}" alt="${coche.marca}" style="width:100%; border-radius:5px;">
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