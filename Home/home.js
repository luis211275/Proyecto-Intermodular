const buscarCoches = document.getElementById("buscar");
const btnReset = document.getElementById("resetear");
const btnFav = document.getElementById("favoritos");

const modal = document.getElementById("modalCoche");
const modalTitulo = document.getElementById("modalTitulo");
const btnCerrarModal = document.getElementById("cerrarModal");
const modalImg = document.getElementById("modalImagen");
const modalAnio = document.getElementById("modalAnio");
const modalPrecio = document.getElementById("modalPrecio");
const modalColor = document.getElementById("modalColor");
const modalCarburante = document.getElementById("modalCarburante");
const modalTransmision = document.getElementById("modalTransmision");
const modalKM = document.getElementById("modalKM");
const modalCiudad = document.getElementById("modalCiudad");
const modalDescripcion = document.getElementById("modalDescripcion");

function abrirModal(coche) {
    modalTitulo.innerText = `${coche}`;
    modalAnio
    modalPrecio
    modalColor
    modalCarburante
    modalTransmision
    modalKM
    modalCiudad
    modalDescripcion

    modal.showModal();
}

btnCerrarModal.addEventListener("click", () => {
    modal.close();
});