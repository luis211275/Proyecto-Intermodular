const contenedor = document.querySelector(".contenedor");
const modal = document.getElementById("modalCoche");

// Variables de estado
let cochesOriginales = [];
let favoritos = JSON.parse(localStorage.getItem("favoritos")) || []; // IDs de favoritos
let cocheSeleccionadoActual = null;
let viendoFavoritos = false; // Variable para saber si estamos en la vista de favoritos
const btnFavoritosTop = document.getElementById("favoritos");

// Tarea 2: Funciones simples de auth
function estaLogueado() {
    return !!localStorage.getItem("auth_user") || !!localStorage.getItem("auth_user_id");
}

function mandarALogin() {
    localStorage.setItem("redirect_after_login", window.location.href);
    window.location.href = "login.html";
}

function actualizarNavbarSesion() {
    const login = document.getElementById("login");
    const venta = document.getElementById("ventaCoche");
    if (!login) return;

    if (estaLogueado()) {
        login.innerText = "⎋ Cerrar sesión";
        login.onclick = () => {
            localStorage.removeItem("auth_user");
            localStorage.removeItem("auth_user_id");
            window.location.href = "home.html";
        };
    } else {
        login.innerText = "Iniciar Sesión";
        login.onclick = () => {
            window.location.href = "login.html";
        };
    }

    if (venta) {
        venta.onclick = () => {
            if (!estaLogueado()) {
                mandarALogin();
            } else {
                window.location.href = "publicar.html";
            }
        };
    }
}

actualizarNavbarSesion();

// 1. Cargar y guardar los coches
async function cargarCoches() {
    contenedor.innerHTML = "<p>Cargando catálogo...</p>";

    try {
        const res = await fetch("/api/coches");
        if (!res.ok) throw new Error("Error en el servidor");

        cochesOriginales = await res.json();
        listarCoches(cochesOriginales);
    } catch (error) {
        console.error("Error al conectar:", error);
        contenedor.innerHTML = "<p>Error al conectar con el servidor.</p>";
    }
}

// 2. Renderizar tarjetas
function listarCoches(lista) {
    contenedor.innerHTML = "";
    if (lista.length === 0) {
        contenedor.innerHTML = "<p>No se han encontrado coches.</p>";
        return;
    }

    lista.forEach(coche => {
        const tarjeta = document.createElement("div");
        tarjeta.classList.add("tarjeta");

        const precio = coche.precioVenta || coche.precio || "Consultar";
        const imagen = coche.urlImagen || coche.imagen;

        tarjeta.innerHTML = `
            <h3>${coche.marca} ${coche.modelo}</h3>
            <p><strong>${precio} €</strong> - ${coche.ciudad}</p>
            <img src="${imagen}" alt="${coche.marca}">
        `;

        tarjeta.addEventListener("click", () => abrirModal(coche));
        contenedor.appendChild(tarjeta);
    });
}

// 3. Lógica del Buscador
document.getElementById("buscar").addEventListener("input", (e) => {
    const termino = e.target.value.toLowerCase();
    const filtrados = cochesOriginales.filter(c =>
        c.marca.toLowerCase().includes(termino) ||
        c.modelo.toLowerCase().includes(termino)
    );
    listarCoches(filtrados);
});

// 4. Lógica de Favoritos (Botón Superior como Interruptor) - Tarea 5: Proteger
btnFavoritosTop.addEventListener("click", () => {
    if (!estaLogueado()) {
        mandarALogin();
        return;
    }

    viendoFavoritos = !viendoFavoritos; // Alterna entre true y false

    if (viendoFavoritos) {
        // Entramos en modo favoritos
        btnFavoritosTop.innerText = "Salir de Favoritos";
        btnFavoritosTop.style.backgroundColor = "#ef4444";

        const soloFavoritos = cochesOriginales.filter(c => favoritos.includes(c.id || c.idCoche));
        listarCoches(soloFavoritos);
    } else {
        // Salimos de favoritos, volvemos a la lista general
        btnFavoritosTop.innerText = "Favoritos";
        btnFavoritosTop.style.backgroundColor = "#7c3aed";

        listarCoches(cochesOriginales);
    }
});

// 5. Resetear vista (Buscador y Favoritos)
document.getElementById("resetear").addEventListener("click", () => {
    document.getElementById("buscar").value = "";

    viendoFavoritos = false;
    btnFavoritosTop.innerText = "Favoritos";
    btnFavoritosTop.style.backgroundColor = "#7c3aed";

    listarCoches(cochesOriginales);
});

// 6. Modal y Relleno de datos
function abrirModal(coche) {
    cocheSeleccionadoActual = coche;

    const precio = coche.precioVenta || coche.precio || 0;
    const kms = coche.kilometraje || coche.km || 0;
    const anio = coche.anioFabricacion || coche.anio || "N/A";

    document.getElementById("modalTitulo").innerText = `${coche.marca} ${coche.modelo} ${coche.version ? `(${coche.version})` : ''}`;
    document.getElementById("modalImagen").src = coche.urlImagen || coche.imagen;
    document.getElementById("modalAnio").innerText = anio;
    document.getElementById("modalPrecio").innerText = precio.toLocaleString() + " €";
    document.getElementById("modalColor").innerText = coche.color || "No especificado";
    document.getElementById("modalCarburante").innerText = coche.combustible || "No especificado";
    document.getElementById("modalTransmision").innerText = coche.transmision || "No especificado";
    document.getElementById("modalKM").innerText = Number(kms).toLocaleString() + " km";
    document.getElementById("modalCiudad").innerText = coche.ciudad || "No especificada";

    const desc = document.getElementById("modalDescripcion");
    if (desc) desc.innerText = `Estado: ${coche.estado || 'Disponible'} | Etiqueta: ${coche.etiquetaAmbiental || 'N/A'}`;

    // Obtener ID real del coche
    const cocheId = coche.id || coche.idCoche;

    // Actualizar estado del botón de favoritos en el modal
    const btnFav = document.getElementById("btnFavoritoModal");
    if (favoritos.includes(cocheId)) {
        btnFav.innerText = "Quitar de Favoritos";
        btnFav.classList.add("es-favorito");
    } else {
        btnFav.innerText = "Añadir a Favoritos";
        btnFav.classList.remove("es-favorito");
    }

    modal.showModal();
}

// Tarea 7: Conectar botón comprar
document.getElementById("btnComprar").addEventListener("click", () => {
    if (!estaLogueado()) {
        mandarALogin();
        return;
    }

    const id = cocheSeleccionadoActual.id || cocheSeleccionadoActual.idCoche;
    window.location.href = `compraVenta.html?id=${id}`;
});


// 7. Evento para añadir/quitar favorito dentro del modal - Tarea 6: Proteger
document.getElementById("btnFavoritoModal").addEventListener("click", () => {
    if (!estaLogueado()) {
        mandarALogin();
        return;
    }

    const id = cocheSeleccionadoActual.id || cocheSeleccionadoActual.idCoche;

    if (favoritos.includes(id)) {
        favoritos = favoritos.filter(favId => favId !== id);
    } else {
        favoritos.push(id);
    }

    localStorage.setItem("favoritos", JSON.stringify(favoritos));
    abrirModal(cocheSeleccionadoActual);

    // Si estamos en la vista de favoritos y quitamos un coche, actualizar la lista de fondo
    if (viendoFavoritos) {
        const soloFavoritos = cochesOriginales.filter(c => favoritos.includes(c.id || c.idCoche));
        listarCoches(soloFavoritos);
    }
});

cargarCoches();

document.getElementById("cerrarModal").addEventListener("click", () => modal.close());