import {navegarA, mostrarNotificacion} from './router.js';

export const inicializarPublicar = async () => {
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
            const respuesta = await fetch(api(ruta), {cache: 'no-store'});
            if (!respuesta.ok) throw new Error(`HTTP ${respuesta.status}`);
            const datos = await respuesta.json();
            const opciones = datos.map(i => `<option value="${i.id}">${i.nombre}</option>`).join('');
            establecerSelector(idElemento, (marcadorPosicion ? `<option value="">${marcadorPosicion}</option>` : '') + opciones, false);
        } catch (e) {
            console.error(`Error cargando ${ruta}`, e);
            establecerSelector(idElemento, `<option value="">No disponible</option>`, true);
            mostrarNotificacion(`No se pudo cargar: ${marcadorPosicion || idElemento}`, 'error');
        }
    };

    await cargarSelector('marcas', 'marcaId', 'Seleccione marca');
    await cargarSelector('ciudades', 'id_ciudad', 'Seleccione ciudad');
    await cargarSelector('combustibles', 'id_combustible', 'Combustible');
    await cargarSelector('transmisiones', 'id_transmision', 'Transmisión');
    await cargarSelector('categorias', 'id_categoria', 'Categoría');
    await cargarSelector('colores', 'id_color', 'Color');
    await cargarSelector('etiquetas', 'id_etiqueta', 'Etiqueta');

    establecerSelector('modeloId', '<option value="">Seleccione marca primero</option>', true);
    establecerSelector('id_version', '<option value="">Seleccione modelo primero</option>', true);

    const elementoMarca = document.getElementById('marcaId');
    if (elementoMarca) {
        elementoMarca.addEventListener('change', async (e) => {
            const idMarca = e.target.value;
            establecerSelector('modeloId', '<option value="">Seleccione marca primero</option>', !idMarca);
            establecerSelector('id_version', '<option value="">Seleccione modelo primero</option>', true);
            if (!idMarca) return;

            establecerSelector('modeloId', '<option value="">Cargando…</option>', true);
            try {
                const respuesta = await fetch(api(`modelos?marcaId=${encodeURIComponent(idMarca)}`), {cache: 'no-store'});
                if (!respuesta.ok) throw new Error(`HTTP ${respuesta.status}`);
                const modelos = await respuesta.json();
                establecerSelector(
                    'modeloId',
                    '<option value="">Seleccione modelo</option>' + modelos.map(m => `<option value="${m.id}">${m.nombre}</option>`).join(''),
                    false
                );
            } catch (err) {
                console.error(err);
                establecerSelector('modeloId', '<option value="">No disponible</option>', true);
                mostrarNotificacion('No se pudieron cargar los modelos.', 'error');
            }
        });
    }

    const elementoModelo = document.getElementById('modeloId');
    if (elementoModelo) {
        elementoModelo.addEventListener('change', async (e) => {
            const idModelo = e.target.value;
            establecerSelector('id_version', '<option value="">Seleccione modelo primero</option>', !idModelo);
            if (!idModelo) return;

            establecerSelector('id_version', '<option value="">Cargando…</option>', true);
            try {
                const respuesta = await fetch(api(`versiones?modeloId=${encodeURIComponent(idModelo)}`), {cache: 'no-store'});
                if (!respuesta.ok) throw new Error(`HTTP ${respuesta.status}`);
                const versiones = await respuesta.json();
                establecerSelector(
                    'id_version',
                    '<option value="">Seleccione versión</option>' + versiones.map(v => `<option value="${v.id}">${v.nombre}</option>`).join(''),
                    false
                );
            } catch (err) {
                console.error(err);
                establecerSelector('id_version', '<option value="">No disponible</option>', true);
                mostrarNotificacion('No se pudieron cargar las versiones.', 'error');
            }
        });
    }

    const elementoFormulario = document.getElementById('formVenta');
    if (elementoFormulario) {
        elementoFormulario.addEventListener('submit', async (e) => {
            e.preventDefault();
            const formulario = e.currentTarget;
            if (!formulario.reportValidity()) return;

            const datosFormulario = new FormData(formulario);
            const archivo = datosFormulario.get('imagen');
            if (!(archivo instanceof File) || archivo.size === 0) {
                mostrarNotificacion('Selecciona una imagen antes de publicar.', 'error');
                return;
            }

            const idVendedor = obtenerIdUsuarioIdentificado() || 1;
            datosFormulario.set('id_vendedor', String(idVendedor));

            try {
                const respuesta = await fetch('/api/publicarVehiculo', {
                    method: 'POST',
                    body: datosFormulario
                });
                if (respuesta.ok) {
                    const cargaUtil = await obtenerJsonSeguro(respuesta);
                    mostrarNotificacion(cargaUtil?.message || "Anuncio publicado con éxito.", "success");
                    formulario.reset();
                    establecerSelector('modeloId', '<option value="">Seleccione marca primero</option>', true);
                    establecerSelector('id_version', '<option value="">Seleccione modelo primero</option>', true);
                    navegarA('/');
                } else {
                    const cargaUtil = await obtenerJsonSeguro(respuesta);
                    mostrarNotificacion(cargaUtil?.message || "Error al publicar el anuncio. Verifique los datos.", "error");
                }
            } catch (err) {
                mostrarNotificacion("Error de conexión con el servidor.", "error");
            }
        });
    }
};

async function obtenerJsonSeguro(respuesta) {
    try {
        return await respuesta.json();
    } catch {
        return null;
    }
}

function obtenerIdUsuarioIdentificado() {
    const clavesDirectas = ['auth_user_id', 'usuarioId', 'userId'];
    for (const clave of clavesDirectas) {
        const v = localStorage.getItem(clave);
        if (!v || String(v).trim() === '') continue;
        const n = Number(v);
        if (Number.isFinite(n) && n > 0) return n;
    }

    const usuarioAutenticado = localStorage.getItem('auth_user');
    if (usuarioAutenticado) {
        try {
            const obj = JSON.parse(usuarioAutenticado);
            if (obj && (obj.id || obj.idUsuario || obj.usuarioId)) {
                const n = Number(obj.id || obj.idUsuario || obj.usuarioId);
                if (Number.isFinite(n) && n > 0) return n;
            }
        } catch {
            // ignorar
        }
    }

    return null;
}
