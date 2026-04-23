const contenedor = document.querySelector(".contenedor");
const modal = document.getElementById("modalCoche");

// 1. Función para obtener los coches del servidor Java
async function cargarCoches() {
    try {
        const respuesta = await fetch(`http://localhost:8080/api/coches/${id}`);
        const coches = await respuesta.json();
        listarCoches(coches);
    } catch (error) {
        console.error("Error al conectar con el servidor:", error);
        contenedor.innerHTML = "<p>Error al cargar los coches.</p>";
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


const requestOptions = {
  method: "GET",
  headers: "headers",
  redirect: "follow"
};

function filtrar(){
    fetch("http://localhost:8080/api/marcas", requestOptions)
    .then(response => response.json())
    .then((result) => {
        console.log(result.marcas);
        let results = result.marcas;
        for (let i = 0; i < results.length; i++) {
            let tarjeta = results[i];
            let opciones = tarjeta.opciones;
            console.log(opciones);
            console.log(correctAns);

            
            innerText = `Marcas: ${modalFMarca}`;
            apiOptions.innerText = opciones;

            opciones.forEach(opcion => {
                let Opcion = document.createElement("option");
                Opcion.value = opcion;
                Opcion.textContent = opcion;
                apiOptions.appendChild(Opcion);
            });
        }
    })
    .catch(error => console.error("Error en medio:", error));
}

function filtrar() {
    const textoBusqueda = inputCoche.value.toLowerCase();
    const marcaSeleccionada = selectMarca.value.toLowerCase();
}

function openModal(filtro) {
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

function abrirFiltro(lista) {
    contenedor.innerHTML = "";

        const tarjetaFiltro = document.createElement("div");
        tarjeta.add("tarjetaFiltro");
        tarjeta.innerHTML = `
            <h3>${filtro} ${coche.modelo}</h3>
            <p>${coche.precio} € - ${coche.ciudad}</p>
            <img src="${coche.img}" alt="${coche.marca}">
        `;
        
        tarjeta.addEventListener("click", () => abrirModal(coche));
        contenedor.appendChild(tarjeta);
};
