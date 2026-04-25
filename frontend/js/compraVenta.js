
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

const inicializarCompra = async () => {
    if (!estaLogueado()) { mandarALogin(); return; }

    const params = new URLSearchParams(window.location.search);
    cocheId = params.get('id');

    if (!cocheId) {
        mostrarNotificacion('Vehículo no especificado.', 'error');
        window.location.href = "home.html";
        return;
    }

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
        <h2 style="text-align: center; text-transform: uppercase; margin-bottom: 1.5rem;">CONTRATO DE COMPRAVENTA DE VEHÍCULO</h2>
        <p><strong>FECHA:</strong> ${fechaActual} | <strong>Ciudad:</strong> ${limpiarTextoHtml(cocheActual.ciudad || "No disponible")}</p>
        
        <div style="margin-top: 1.5rem;">
            <h3>PARTES:</h3>
            <p><strong>VENDEDOR:</strong> ${limpiarTextoHtml(cocheActual.vendedorNombre || "No disponible")}, <strong>DNI/NIE:</strong> ${limpiarTextoHtml(cocheActual.vendedorDni || "No disponible")}</p>
            <p><strong>COMPRADOR:</strong> ${limpiarTextoHtml(nombreComprador)}, <strong>DNI/NIE:</strong> ${limpiarTextoHtml(dniComprador)}</p>
        </div>

        <div style="margin-top: 1.5rem;">
            <h3>DATOS DEL VEHÍCULO:</h3>
            <p><strong>Marca / Modelo:</strong> ${limpiarTextoHtml(cocheActual.marca || "No disponible")} / ${limpiarTextoHtml(cocheActual.modelo || "No disponible")}</p>
            <p><strong>Categoría / Versión:</strong> ${limpiarTextoHtml(cocheActual.categoria || "No disponible")} / ${limpiarTextoHtml(cocheActual.version || "No disponible")}</p>
            <p><strong>Año Fab. / Color:</strong> ${limpiarTextoHtml(cocheActual.anio || cocheActual.anioFabricacion || "No disponible")} / ${limpiarTextoHtml(cocheActual.color || "No disponible")}</p>
            <p><strong>Etiqueta / Combustible:</strong> ${limpiarTextoHtml(cocheActual.etiquetaAmbiental || cocheActual.etiqueta || "No disponible")} / ${limpiarTextoHtml(cocheActual.combustible || "No disponible")}</p>
            <p><strong>Transmisión:</strong> ${limpiarTextoHtml(cocheActual.transmision || "No disponible")} | <strong>Fecha Publicación:</strong> ${fechaPub}</p>
        </div>

        <div style="margin-top: 2rem; text-align: center;">
            <a href="#" id="ver-terminos" style="color: #db9c37; font-weight: bold; text-decoration: underline;">Ver Términos y Condiciones</a>
        </div>
    `;
    renderizarResumenPrecios();

    document.getElementById('ver-terminos')?.addEventListener('click', (e) => {
        e.preventDefault();
        document.getElementById('modal-terminos').showModal();
    });
}

function renderizarResumenPrecios() {
    const precioBase = Number(cocheActual.precioVenta || cocheActual.precio);
    const comision = precioBase * 0.03;
    const total = precioBase + comision;
    const summary = document.getElementById('precio-summary');
    summary.innerHTML = `
        <div style="display: flex; justify-content: space-between; margin-bottom: 5px;"><span>Precio vehículo:</span> <span>${precioBase.toLocaleString()} €</span></div>
        <div style="display: flex; justify-content: space-between; margin-bottom: 5px;"><span>Comisión (3%):</span> <span>${comision.toLocaleString()} €</span></div>
        <div style="display: flex; justify-content: space-between; font-weight:bold; border-top: 1px solid #cbd5e1; padding-top: 5px;"><span>Total:</span> <span>${total.toLocaleString()} €</span></div>
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
                <p>Procesando pago seguro...</p>
            </div>
            <style>@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }</style>
        `;

        setTimeout(() => {
            mostrarNotificacion('¡Compra realizada con éxito!', 'success');
            setTimeout(() => window.location.href = "home.html", 1500);
        }, 2000);
    });

    document.getElementById('cancelar-compra')?.addEventListener('click', () => {
        window.location.href = "home.html";
    });
}

document.addEventListener('DOMContentLoaded', inicializarCompra);
