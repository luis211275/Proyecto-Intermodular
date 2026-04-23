import { mostrarNotificacion, navegarA } from './router.js';

const API_BASE = '/api';

export const inicializarCompraVenta = async () => {
    const parametrosConsulta = new URLSearchParams(window.location.search);
    const id = parametrosConsulta.get('id');

    if (!id) {
        mostrarNotificacion('ID de coche no proporcionado', 'error');
        navegarA('/');
        return;
    }

    try {
        const respuesta = await fetch(`${API_BASE}/coches/${id}`);
        if (!respuesta.ok) throw new Error('Coche no encontrado');
        const coche = await respuesta.json();

        // Normalizar imagen si viene como urlImagen
        if (coche.urlImagen && !coche.imagen) {
            coche.imagen = coche.urlImagen;
        }

        mostrarContrato(coche);
        mostrarResumenPrecios(coche);
        configurarEscuchadoresEventos(coche);
    } catch (err) {
        console.error(err);
        mostrarNotificacion('Error al cargar datos de compra.', 'error');
        navegarA('/');
    }
};

function mostrarContrato(coche) {
    const contenedor = document.getElementById('contrato-content');
    const usuarioAutenticado = JSON.parse(localStorage.getItem('auth_user')) || { nombre: 'Invitado', apellido: '', dni: 'No disponible', telefono: 'No disponible' };

    contenedor.innerHTML = `
        <div class="legal-doc">
            <h2 class="legal-title">CONTRATO DE COMPRAVENTA DE VEHÍCULO USADO</h2>
            
            <div class="contract-grid">
                <div class="party-card">
                    <p class="party-role">EL VENDEDOR</p>
                    <p><strong>Nombre:</strong> ${escaparHtml(coche.vendedorNombre)}</p>
                    <p><strong>DNI:</strong> ${escaparHtml(coche.vendedorDni || 'No disponible')}</p>
                    <p><strong>Teléfono:</strong> ${escaparHtml(coche.vendedorTelefono || 'No disponible')}</p>
                </div>
                <div class="party-card">
                    <p class="party-role">EL COMPRADOR</p>
                    <p><strong>Nombre:</strong> ${escaparHtml(usuarioAutenticado.nombre + (usuarioAutenticado.apellido ? ' ' + usuarioAutenticado.apellido : ''))}</p>
                    <p><strong>DNI:</strong> ${escaparHtml(usuarioAutenticado.dni || 'No disponible')}</p>
                    <p><strong>Teléfono:</strong> ${escaparHtml(usuarioAutenticado.telefono || 'No disponible')}</p>
                </div>
            </div>

            <section class="legal-section" style="margin-top: 1rem;">
                <h3>OBJETO DEL CONTRATO</h3>
                <p>La compraventa del vehículo con las siguientes especificaciones:</p>
                <ul class="legal-specs">
                    <li><strong>Categoría:</strong> ${escaparHtml(coche.categoria || 'No disponible')}</li>
                    <li><strong>Marca/Modelo:</strong> ${escaparHtml(coche.marca)} ${escaparHtml(coche.modelo)}</li>
                    <li><strong>Versión:</strong> ${escaparHtml(coche.version || 'No disponible')}</li>
                    <li><strong>Combustible:</strong> ${escaparHtml(coche.combustible)}</li>
                    <li><strong>Transmisión:</strong> ${escaparHtml(coche.transmision)}</li>
                    <li><strong>Color:</strong> ${escaparHtml(coche.color || 'No disponible')}</li>
                    <li><strong>Etiqueta:</strong> ${escaparHtml(coche.etiquetaAmbiental || coche.etiqueta || 'No disponible')}</li>
                    <li><strong>Kilometraje:</strong> ${formatearNumero(coche.km)} km</li>
                    <li><strong>Año:</strong> ${coche.anio}</li>
                    <li><strong>Ciudad:</strong> ${escaparHtml(coche.ciudad)}</li>
                    <li><strong>Estado:</strong> ${escaparHtml(coche.estado)}</li>
                </ul>
            </section>

            <section class="legal-section">
                <h3>CLÁUSULAS</h3>
                <p>1. El precio acordado por el vehículo es de <strong>${formatearPrecio(coche.total)}€</strong> (impuestos y comisiones incluidos).</p>
                <p>2. El vendedor declara que el vehículo se encuentra en el estado de conservación descrito.</p>
                <p>3. El comprador acepta el vehículo en su estado actual, exonerando a la plataforma de cualquier responsabilidad civil o penal derivada de la transacción.</p>
            </section>
        </div>
    `;
}

function mostrarResumenPrecios(coche) {
    const contenedor = document.getElementById('precio-summary');
    contenedor.innerHTML = `
        <div class="price-row"><span>Subtotal:</span><strong>${formatearPrecio(coche.subtotal)}€</strong></div>
        <div class="price-row"><span>IVA (21%):</span><strong>${formatearPrecio(coche.iva)}€</strong></div>
        <div class="price-row"><span>Comisión (2%):</span><strong>${formatearPrecio(coche.comision)}€</strong></div>
        <div class="price-row total"><span>TOTAL:</span><strong>${formatearPrecio(coche.total)}€</strong></div>
    `;
}

function configurarEscuchadoresEventos(coche) {
    const verificarTerminos = document.getElementById('acepto-terminos');
    const botonContinuar = document.getElementById('continuar-pago');
    const botonCancelar = document.getElementById('cancelar-compra');
    const botonPagar = document.getElementById('btn-pagar');

    verificarTerminos.onchange = (e) => {
        botonContinuar.disabled = !e.target.checked;
    };

    botonContinuar.onclick = () => {
        document.getElementById('seccion-contrato').classList.add('hidden');
        document.getElementById('seccion-pago').classList.remove('hidden');
        // Aseguramos que el formulario de pago sea visible al continuar
        document.getElementById('pago-form-container').classList.remove('hidden');
        window.scrollTo(0, 0);
    };

    botonCancelar.onclick = () => navegarA('/');

    document.getElementById('volver-contrato').onclick = () => {
        document.getElementById('seccion-pago').classList.add('hidden');
        document.getElementById('seccion-contrato').classList.remove('hidden');
        window.scrollTo(0, 0);
    };

    const formularioPago = document.getElementById('form-pago');
    if (formularioPago) {
        formularioPago.onsubmit = async (e) => {
            e.preventDefault();
            
            if (botonPagar) botonPagar.disabled = true;
            const cargador = document.getElementById('pago-spinner');
            const contenidoOriginal = document.getElementById('pago-form-container');

            // 1. OCULTAMOS TODO el formulario e imagen mockup INMEDIATAMENTE
            if (contenidoOriginal) contenidoOriginal.classList.add('hidden');
            
            // 2. PREPARAMOS EL SPINNER DINÁMICAMENTE para que no exista antes de este momento
            if (cargador) {
                cargador.innerHTML = `
                    <div class="cargador" style="margin: 0 auto 1.5rem; width: 50px; height: 50px; border: 4px solid rgba(0,0,0,0.1); border-top-color: var(--primary); border-radius: 50%; animation: spin 1s linear infinite;"></div>
                    <p id="pago-status-text" style="font-weight: 500; color: var(--text-main); font-size: 1.1rem;">Iniciando conexión segura...</p>
                `;
                cargador.classList.remove('hidden');
            }
            
            const textoEstado = document.getElementById('pago-status-text');
            
            const estados = [
                'Iniciando conexión segura...',
                'Validando datos de la tarjeta...',
                'Esperando autorización de la entidad bancaria...',
                'Procesando cobro...',
                'Pago aceptado. Finalizando compra...'
            ];

            for (let i = 0; i < estados.length; i++) {
                if (textoEstado) textoEstado.textContent = estados[i];
                await new Promise(r => setTimeout(r, 1000));
            }

            try {
                const respuesta = await fetch('/api/marcarVehiculoComoVendido', {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ id: coche.idCoche })
                });
                if (!respuesta.ok) throw new Error('Error al actualizar estado en BD');
                
                mostrarNotificacion('¡Compra completada con éxito!', 'success');
                setTimeout(() => {
                    navegarA('/');
                }, 1500);
            } catch (err) {
                console.error(err);
                mostrarNotificacion('Error al finalizar la compra.', 'error');
                if (botonPagar) botonPagar.disabled = false;
                if (cargador) {
                    cargador.classList.add('hidden');
                    cargador.innerHTML = ''; // Limpiamos
                }
                if (contenidoOriginal) contenidoOriginal.classList.remove('hidden');
            }
        };
    }
}

function formatearNumero(valor) {
    return new Intl.NumberFormat('es-ES').format(valor);
}

function formatearPrecio(valor) {
    return new Intl.NumberFormat('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(valor);
}

function escaparHtml(cadena) {
    return String(cadena || '').replace(/[&<>"']/g, m => ({
        '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
    }[m]));
}
