
let cocheId = null;
let cocheActual = null;

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
        login.innerText = "Cerrar sesión";
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
            window.location.href = estaLogueado() ? "publicar.html" : "login.html";
        };
    }
}

actualizarNavbarSesion();

function mostrarNotificacion(mensaje, tipo = 'success') {
    let contenedor = document.getElementById('notification-container');
    if (!contenedor) {
        contenedor = document.createElement('div');
        contenedor.id = 'notification-container';
        document.body.appendChild(contenedor);
    }
    const notificacion = document.createElement('div');
    notificacion.className = `toast ${tipo}`;
    notificacion.textContent = mensaje;
    contenedor.appendChild(notificacion);
    setTimeout(() => {
        notificacion.classList.add('fade-out');
        setTimeout(() => notificacion.remove(), 300);
    }, 3000);
}

function limpiarTextoHtml(cadena) {
    return String(cadena || '').replace(/[&<>"']/g, m => ({
        '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
    }[m]));
}

function formatearImporte(valor) {
    return Number(valor || 0).toLocaleString('es-ES', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
}

async function finalizarCompra() {
    if (!cocheId) {
        throw new Error("Vehículo no especificado");
    }

    const compradorId = Number(localStorage.getItem("auth_user_id"));
    if (!compradorId) {
        throw new Error("Comprador no especificado");
    }

    const respuesta = await fetch('/api/marcarvehiculocomovendido', {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            id: Number(cocheId),
            compradorId
        })
    });

    if (!respuesta.ok) {
        throw new Error("No se pudo registrar la compra");
    }
}

async function asegurarDatosComprador() {
    const user = JSON.parse(localStorage.getItem("auth_user") || "{}");
    const tieneDatosBasicos = (user.nombres || user.apellidos) && user.dni;

    if (tieneDatosBasicos) {
        return user;
    }

    const idUsuario = localStorage.getItem("auth_user_id");
    try {
        // Probamos id o email.
        let url = null;
        if (idUsuario) {
            url = `http://localhost:8080/user/${idUsuario}`;
        } else if (user.email) {
            url = `http://localhost:8080/user/email?value=${encodeURIComponent(user.email)}`;
        } else {
            return user;
        }

        const respuesta = await fetch(url);
        if (!respuesta.ok) {
            return user;
        }

        const datosUsuario = await respuesta.json();
        const usuarioActualizado = {
            email: datosUsuario.email || user.email || "",
            nombres: datosUsuario.nombres || "",
            apellidos: datosUsuario.apellidos || "",
            dni: datosUsuario.dni || "",
            telefono: datosUsuario.telefono || ""
        };

        if (datosUsuario.id) {
            localStorage.setItem("auth_user_id", String(datosUsuario.id));
        }
        localStorage.setItem("auth_user", JSON.stringify(usuarioActualizado));
        return usuarioActualizado;
    } catch (error) {
        return user;
    }
}

const inicializarCompra = async () => {
    if (!estaLogueado()) { mandarALogin(); return; }

    const params = new URLSearchParams(window.location.search);
    cocheId = params.get('id');

    if (!cocheId) {
        mostrarNotificacion('Vehículo no especificado.', 'error');
        window.location.href = "home.html";
        return;
    }

    await asegurarDatosComprador();
    await cargarDatosCoche();
    configurarEventos();
};

async function cargarDatosCoche() {
    try {
        const res = await fetch(`/api/coches/${cocheId}`);
        if (!res.ok) throw new Error('Coche no encontrado');
        cocheActual = await res.json();
        renderizarContrato();
    } catch (err) {
        mostrarNotificacion('Error al cargar datos del vehículo.', 'error');
        window.location.href = "home.html";
    }
}

function renderizarContrato() {
    const contenedor = document.getElementById('contrato-content');
    const user = JSON.parse(localStorage.getItem("auth_user") || "{}");

    let nombreComprador = "No disponible";
    if (user.nombres && user.apellidos) {
        nombreComprador = `${user.nombres} ${user.apellidos}`;
    } else if (user.nombres || user.apellidos) {
        nombreComprador = user.nombres || user.apellidos;
    } else if (user.email) {
        nombreComprador = user.email;
    }

    const dniComprador = user.dni || "No disponible";
    const fechaActual = new Date().toLocaleDateString('es-ES');
    const fechaPub = cocheActual.fechaPublicacion ? new Date(cocheActual.fechaPublicacion).toLocaleDateString('es-ES') : "No disponible";

    contenedor.innerHTML = `
        <div class="contrato-encabezado">
            <h2>Contrato de compraventa de vehículo</h2>
            <p>${fechaActual} · ${limpiarTextoHtml(cocheActual.ciudad || "No disponible")}</p>
        </div>

        <div class="bloque-contrato">
            <h3>Partes</h3>
            <div class="contrato-grid contrato-grid-dos">
                <div class="dato-contrato">
                    <span class="dato-etiqueta">Vendedor</span>
                    <strong>${limpiarTextoHtml(cocheActual.vendedorNombre || "No disponible")}</strong>
                    <small>DNI/NIE: ${limpiarTextoHtml(cocheActual.vendedorDni || "No disponible")}</small>
                </div>
                <div class="dato-contrato">
                    <span class="dato-etiqueta">Comprador</span>
                    <strong>${limpiarTextoHtml(nombreComprador)}</strong>
                    <small>DNI/NIE: ${limpiarTextoHtml(dniComprador)}</small>
                </div>
            </div>
        </div>

        <div class="bloque-contrato">
            <h3>Datos del vehículo</h3>
            <div class="contrato-grid">
                <div class="dato-contrato"><span class="dato-etiqueta">Marca / Modelo</span><strong>${limpiarTextoHtml(cocheActual.marca || "No disponible")} / ${limpiarTextoHtml(cocheActual.modelo || "No disponible")}</strong></div>
                <div class="dato-contrato"><span class="dato-etiqueta">Categoría / Versión</span><strong>${limpiarTextoHtml(cocheActual.categoria || "No disponible")} / ${limpiarTextoHtml(cocheActual.version || "No disponible")}</strong></div>
                <div class="dato-contrato"><span class="dato-etiqueta">Año / Color</span><strong>${limpiarTextoHtml(cocheActual.anio || cocheActual.anioFabricacion || "No disponible")} / ${limpiarTextoHtml(cocheActual.color || "No disponible")}</strong></div>
                <div class="dato-contrato"><span class="dato-etiqueta">Etiqueta / Combustible</span><strong>${limpiarTextoHtml(cocheActual.etiquetaAmbiental || cocheActual.etiqueta || "No disponible")} / ${limpiarTextoHtml(cocheActual.combustible || "No disponible")}</strong></div>
                <div class="dato-contrato"><span class="dato-etiqueta">Transmisión</span><strong>${limpiarTextoHtml(cocheActual.transmision || "No disponible")}</strong></div>
                <div class="dato-contrato"><span class="dato-etiqueta">Fecha publicación</span><strong>${fechaPub}</strong></div>
            </div>
        </div>

        <div class="enlace-terminos">
            <a href="#" id="ver-terminos">Ver Términos y Condiciones</a>
        </div>
    `;
    renderizarResumenPrecios();

    document.getElementById('ver-terminos')?.addEventListener('click', (e) => {
        e.preventDefault();
        document.getElementById('modal-terminos').showModal();
    });
}

function renderizarResumenPrecios() {
    const precioBase = Number(cocheActual.subtotal ?? cocheActual.precioVenta ?? cocheActual.precio ?? 0);
    const iva = Math.round(precioBase * 0.21 * 100) / 100;
    const comision = Math.round(precioBase * 0.03 * 100) / 100;
    const total = Math.round((precioBase + iva + comision) * 100) / 100;
    const summary = document.getElementById('precio-summary');
    summary.innerHTML = `
        <div class="fila-precio"><span>Precio vehículo</span><strong>${formatearImporte(precioBase)} €</strong></div>
        <div class="fila-precio"><span>IVA (21%)</span><strong>${formatearImporte(iva)} €</strong></div>
        <div class="fila-precio"><span>Comisión (3%)</span><strong>${formatearImporte(comision)} €</strong></div>
        <div class="fila-precio fila-precio-total"><span>Total</span><strong>${formatearImporte(total)} €</strong></div>
    `;
}

function configurarEventos() {

    const seccionContrato = document.getElementById('seccion-contrato');
    const seccionPago = document.getElementById('seccion-pago');
    const pagoFormContainer = document.getElementById('pago-form-container');
    const pagoSpinner = document.getElementById('pago-spinner');
    const btnContinuar = document.getElementById('continuar-pago');
    const checkbox = document.getElementById('acepto-terminos');

    seccionContrato.classList.remove('hidden');
    seccionPago.classList.add('hidden');
    pagoFormContainer.classList.add('hidden');
    pagoSpinner.classList.add('hidden');
    btnContinuar.disabled = true;

    checkbox?.addEventListener('change', (e) => {
        btnContinuar.disabled = !e.target.checked;
    });

    btnContinuar?.addEventListener('click', () => {
        seccionContrato.classList.add('hidden');
        seccionPago.classList.remove('hidden');
        pagoFormContainer.classList.remove('hidden');
    });

    document.getElementById('cerrar-modal-terminos')?.addEventListener('click', () => {
        document.getElementById('modal-terminos').close();
    });

    document.getElementById('volver-contrato')?.addEventListener('click', () => {
        seccionContrato.classList.remove('hidden');
        seccionPago.classList.add('hidden');
    });

    document.getElementById('form-pago')?.addEventListener('submit', (e) => {
        e.preventDefault();

        pagoFormContainer.classList.add('hidden');
        pagoSpinner.classList.remove('hidden');
        pagoSpinner.innerHTML = `
            <div style="display: flex; flex-direction: column; align-items: center; gap: 1rem;">
                <div class="spinner" style="width: 40px; height: 40px; border: 4px solid #f3f3f3; border-top: 4px solid #3498db; border-radius: 50%; animation: spin 1s linear infinite;"></div>
                <p id="estado-pago-texto">Esperando conexión con el banco...</p>
            </div>
            <style>@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }</style>
        `;

        const pasosPago = [
            "Esperando conexión con el banco...",
            "Validando información...",
            "Procesando pago...",
            "Pago aceptado."
        ];

        const textoEstadoPago = document.getElementById('estado-pago-texto');
        let pasoActual = 0;

        const intervaloPago = setInterval(async () => {
            pasoActual += 1;

            if (pasoActual < pasosPago.length) {
                textoEstadoPago.textContent = pasosPago[pasoActual];
                return;
            }

            clearInterval(intervaloPago);
            try {
                await finalizarCompra();
                mostrarNotificacion('¡Compra realizada con éxito!', 'success');
                setTimeout(() => window.location.href = "home.html", 1500);
            } catch (error) {
                mostrarNotificacion('El pago se simuló, pero no se pudo registrar la compra.', 'error');
            }
        }, 3000);
    });

    document.getElementById('cancelar-compra')?.addEventListener('click', () => {
        window.location.href = "home.html";
    });
}

document.addEventListener('DOMContentLoaded', inicializarCompra);
