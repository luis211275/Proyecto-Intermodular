const contenedor = document.querySelector(".contenedor");
const modal = document.getElementById("modalCoche");

function estaLogueado() {
    return !!localStorage.getItem("auth_user") || !!localStorage.getItem("auth_user_id");
}

function actualizarNavbarSesion() {
    const login = document.getElementById("login");
    const venta = document.getElementById("ventaCoche");
    if (!login) return;

    // Mostramos la sesión guardada.
    if (estaLogueado()) {
        login.innerHTML = "<a href=\"#\">Cerrar sesión</a>";
        login.onclick = (e) => {
            e.preventDefault();
            localStorage.removeItem("auth_user");
            localStorage.removeItem("auth_user_id");
            window.location.href = "home.html";
        };
    } else {
        login.innerHTML = "<a href=\"../html/login.html\">Iniciar Sesión</a>";
        login.onclick = null;
    }

    if (venta) {
        venta.innerHTML = "<a href=\"#\">Vende tu Coche</a>";
        venta.onclick = (e) => {
            e.preventDefault();
            window.location.href = estaLogueado() ? "publicar.html" : "login.html";
        };
    }
}

actualizarNavbarSesion();

// Guardamos el estado.
let cochesOriginales = [];
let favoritos = JSON.parse(localStorage.getItem("favoritos")) || []; // Guardamos favoritos.
let cocheSeleccionadoActual = null;
let viendoFavoritos = false; // Controlamos favoritos.
const btnFavoritosTop = document.getElementById("favoritos");

// Controlamos filtros.
const modalFiltro = document.getElementById("modalFiltro");
const btnAbrirFiltros = document.getElementById("filtros");
const btnCerrarFiltro = document.getElementById("btnCerrarFiltro");
const btnAplicarFiltros = document.getElementById("btnAplicarFiltros");

// Cargamos coches.
async function cargarCochesDisponibles() {
    contenedor.innerHTML = "<p>Cargando catálogo...</p>";

    try {
        // Pedimos todo el listado.
        const respuesta = await fetch("http://localhost:8080/api/coches");
        if (!respuesta.ok) {
            throw new Error("No se pudo cargar el catálogo");
        }

        cochesOriginales = await respuesta.json();
        listarCoches(cochesOriginales);
    } catch (error) {
        console.error("Error al conectar:", error);
        contenedor.innerHTML = "<p>Error al conectar con el servidor.</p>";
    }
}

// Pintamos tarjetas.
function listarCoches(lista) {
    contenedor.innerHTML = "";
    if (lista.length === 0) {
        contenedor.innerHTML = "<p>No se han encontrado coches con esos filtros.</p>";
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

// Filtramos el buscador.
document.getElementById("buscar").addEventListener("input", (e) => {
    const termino = e.target.value.toLowerCase();
    const filtrados = cochesOriginales.filter(c => 
        (c.marca && c.marca.toLowerCase().includes(termino)) || 
        (c.modelo && c.modelo.toLowerCase().includes(termino))
    );
    listarCoches(filtrados);
});

// Alternamos favoritos.
btnFavoritosTop.addEventListener("click", () => {
    viendoFavoritos = !viendoFavoritos;

    if (viendoFavoritos) {
        btnFavoritosTop.innerText = "Salir de Favoritos";
        btnFavoritosTop.style.backgroundColor = "#ef4444";
        
        const soloFavoritos = cochesOriginales.filter(c => favoritos.includes(c.id || c.idCoche));
        listarCoches(soloFavoritos);
    } else {
        btnFavoritosTop.innerText = "Favoritos";
        btnFavoritosTop.style.backgroundColor = "#7c3aed";
        
        listarCoches(cochesOriginales);
    }
});

// Reiniciamos la vista.
document.getElementById("resetear").addEventListener("click", () => {
    document.getElementById("buscar").value = "";
    
    viendoFavoritos = false;
    btnFavoritosTop.innerText = "Favoritos";
    btnFavoritosTop.style.backgroundColor = "#7c3aed";
    
    // Limpiamos selectores.
    const selectores = modalFiltro.querySelectorAll("select");
    selectores.forEach(select => select.value = "");

    // Limpiamos rangos.
    const inputsRango = modalFiltro.querySelectorAll('input[type="number"]');
    inputsRango.forEach(input => input.value = "");

    listarCoches(cochesOriginales);
});

// Abrimos el modal.
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
    if(desc) desc.innerText = `Estado: ${coche.estado || 'Disponible'} | Etiqueta: ${coche.etiquetaAmbiental || 'N/A'}`;

    // Tomamos el id real.
    const cocheId = coche.id || coche.idCoche;

    // Ajustamos favoritos.
    const btnFav = document.getElementById("btnFavoritoModal");
    if (favoritos.includes(cocheId)) {
        btnFav.innerText = "Quitar de Favoritos";
        btnFav.classList.add("es-favorito");
    } else {
        btnFav.innerText = "Añadir a Favoritos";
        btnFav.classList.remove("es-favorito");
    }

    const btnComprar = document.getElementById("btnComprar");
    if (btnComprar) {
        // Enviamos el id correcto.
        btnComprar.onclick = () => {
            window.location.href = `compraVenta.html?id=${cocheId}`;
        };
    }

    modal.showModal();
}

// Cambiamos favoritos.
document.getElementById("btnFavoritoModal").addEventListener("click", () => {
    const id = cocheSeleccionadoActual.id || cocheSeleccionadoActual.idCoche;
    
    if (favoritos.includes(id)) {
        favoritos = favoritos.filter(favId => favId !== id);
    } else {
        favoritos.push(id);
    }
    
    localStorage.setItem("favoritos", JSON.stringify(favoritos));
    abrirModal(cocheSeleccionadoActual); // Refrescamos el color.
    
    if (viendoFavoritos) {
        const soloFavoritos = cochesOriginales.filter(c => favoritos.includes(c.id || c.idCoche));
        listarCoches(soloFavoritos);
    }
});
document.getElementById("cerrarModal").addEventListener("click", () => modal.close());

const mapeoAtributos = {
    "modalFMarca": "marca",
    "modalFModelo": "modelo",
    "modalFAnio": ["anioFabricacion", "anio"], 
    "modalFColor": "color",
    "modalFCombustible": "combustible",
    "modalFTransmision": "transmision",
    "modalFCiudad": "ciudad",
    "modalFEtiqAmb": "etiquetaAmbiental",
    "modalFVersion": "version",
    "modalFDisponible": ["estado", "disponible"]
};

// Leemos el valor disponible.
function obtenerValorCoche(coche, propiedades) {
    if (Array.isArray(propiedades)) {
        for (let prop of propiedades) {
            if (coche[prop] !== undefined) return coche[prop];
        }
        return undefined;
    }
    return coche[propiedades];
}

btnAbrirFiltros.addEventListener("click", () => {
    // Cargamos opciones del filtro.
    for (let selectId in mapeoAtributos) {
        const selectElement = document.getElementById(selectId);
        const propiedad = mapeoAtributos[selectId];
        
        const valorActual = selectElement.value;
        const valoresUnicos = [...new Set(cochesOriginales.map(c => obtenerValorCoche(c, propiedad)))].filter(v => v !== undefined && v !== "");
        
        selectElement.innerHTML = '<option value="">Todos</option>';
        valoresUnicos.sort().forEach(valor => {
            selectElement.innerHTML += `<option value="${valor}" ${valorActual == valor ? 'selected' : ''}>${valor}</option>`;
        });
    }
    
    modalFiltro.showModal();
});

btnAplicarFiltros.addEventListener("click", () => {
    // Leemos los rangos.
    const precioMin = parseFloat(document.getElementById("modalFPrecioMin").value) || 0;
    const precioMax = parseFloat(document.getElementById("modalFPrecioMax").value) || Infinity;
    
    const kmMin = parseFloat(document.getElementById("modalFKMMin").value) || 0;
    const kmMax = parseFloat(document.getElementById("modalFKMMax").value) || Infinity;

    const cochesFiltrados = cochesOriginales.filter(coche => {
        // Revisamos selectores.
        const cumpleSelectores = Object.keys(mapeoAtributos).every(selectId => {
            const selectValor = document.getElementById(selectId).value;
            if (selectValor === "") return true; // Ignoramos "Todos".
            
            const cocheValor = obtenerValorCoche(coche, mapeoAtributos[selectId]);
            return String(cocheValor) === String(selectValor);
        });

        if (!cumpleSelectores) return false;

        // Revisamos precio.
        const precioCoche = parseFloat(obtenerValorCoche(coche, ["precioVenta", "precio"])) || 0;
        if (precioCoche < precioMin || precioCoche > precioMax) return false;

        // Revisamos kilometraje.
        const kmCoche = parseFloat(obtenerValorCoche(coche, ["kilometraje", "km"])) || 0;
        if (kmCoche < kmMin || kmCoche > kmMax) return false;

        // Dejamos los válidos.
        return true;
    });

    viendoFavoritos = false;
    btnFavoritosTop.innerText = "Favoritos";
    btnFavoritosTop.style.backgroundColor = "#7c3aed";

    listarCoches(cochesFiltrados);
    modalFiltro.close();
});

btnCerrarFiltro.addEventListener("click", () => {
    modalFiltro.close();
});

// Iniciamos la vista.
cargarCochesDisponibles();
