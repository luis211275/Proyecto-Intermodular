
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

const inicializarPublicar = async () => {
    if (!estaLogueado()) { mandarALogin(); return; }

    const api = (ruta) => `/api/${ruta}`.replace(/\/{2,}/g, '/');

    const establecerSelector = (idElemento, html, deshabilitado = false) => {
        const el = document.getElementById(idElemento);
        if (!el) return;
        el.innerHTML = html;
        el.disabled = deshabilitado;
    };

    const cargarSelector = async (ruta, idElemento, marcadorPosicion) => {
        establecerSelector(idElemento, `<option value="">Cargando…</option>`, true);
        try {
            const respuesta = await fetch(api(ruta), { cache: 'no-store' });
            const datos = await respuesta.json();
            const opciones = datos.map(i => `<option value="${i.id}">${i.nombre}</option>`).join('');
            establecerSelector(idElemento, (marcadorPosicion ? `<option value="">${marcadorPosicion}</option>` : '') + opciones, false);
        } catch (e) {
            establecerSelector(idElemento, `<option value="">No disponible</option>`, true);
        }
    };

    // Carga inicial de selectores estáticos
    await cargarSelector('marcas', 'marcaId', 'Seleccione marca');
    await cargarSelector('ciudades', 'id_ciudad', 'Seleccione ciudad');
    await cargarSelector('combustibles', 'id_combustible', 'Combustible');
    await cargarSelector('transmisiones', 'id_transmision', 'Transmisión');
    await cargarSelector('categorias', 'id_categoria', 'Categoría');
    await cargarSelector('colores', 'id_color', 'Color');
    await cargarSelector('etiquetas', 'id_etiqueta', 'Etiqueta');

    // Al cambiar marca, limpiar modelos y versiones
    document.getElementById('marcaId')?.addEventListener('change', async (e) => {
        const idMarca = e.target.value;
        const selectModelo = document.getElementById('modeloId');
        const selectVersion = document.getElementById('id_version');

        // Limpiar ambos
        establecerSelector('modeloId', '<option value="">Seleccione modelo</option>', true);
        establecerSelector('id_version', '<option value="">Seleccione modelo primero</option>', true);

        if (!idMarca) return;

        try {
            const respuesta = await fetch(api(`modelos?marcaId=${idMarca}`));
            const modelos = await respuesta.json();
            establecerSelector('modeloId', '<option value="">Seleccione modelo</option>' + modelos.map(m => `<option value="${m.id}">${m.nombre}</option>`).join(''), false);
        } catch (err) { }
    });

    // Al cambiar modelo, cargar versiones
    document.getElementById('modeloId')?.addEventListener('change', async (e) => {
        const idModelo = e.target.value;
        const selectVersion = document.getElementById('id_version');

        // Limpiar versiones
        establecerSelector('id_version', '<option value="">Seleccione versión</option>', true);

        if (!idModelo) return;

        try {
            const respuesta = await fetch(api(`versiones?modeloId=${idModelo}`));
            const versiones = await respuesta.json();
            establecerSelector('id_version', '<option value="">Seleccione versión</option>' + versiones.map(v => `<option value="${v.id}">${v.nombre}</option>`).join(''), false);
        } catch (err) { }
    });

    document.getElementById('formVenta')?.addEventListener('submit', async (e) => {
        e.preventDefault();
        const datosFormulario = new FormData(e.currentTarget);
        const idVendedor = localStorage.getItem('auth_user_id') || 1;
        datosFormulario.set('id_vendedor', String(idVendedor));

        try {
            const respuesta = await fetch('/api/publicarVehiculo', {
                method: 'POST',
                body: datosFormulario
            });
            if (respuesta.ok) {
                mostrarNotificacion("Anuncio publicado con éxito.", "success");
                window.location.href = "home.html";
            } else {
                mostrarNotificacion("Error al publicar el anuncio.", "error");
            }
        } catch (err) {
            mostrarNotificacion("Error de conexión.", "error");
        }
    });
};

document.addEventListener('DOMContentLoaded', inicializarPublicar);
