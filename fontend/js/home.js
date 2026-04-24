import { mostrarNotificacion, navegarA } from './router.js';

const API_BASE = '/api';

let currentCoches = [];
let favoritos = [];
let currentCocheId = null;

export const inicializarInicio = async () => {
    console.log('Iniciando Home...');
    const container = document.getElementById('catalogo');
    if (!container) return;
    
    await cargarCatalogosFiltro();
    await cargarCoches();
    
    configurarAccionesVehiculo();
    configurarAccionesModales();
    configurarAccionesFiltro();
};

async function cargarCoches(filtros = {}) {
    const container = document.getElementById('catalogo');
    try {
        const queryParams = new URLSearchParams(filtros).toString();
        const response = await fetch(`${API_BASE}/coches?${queryParams}`, { cache: 'no-store' });
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        currentCoches = await response.json();
        
        const userId = localStorage.getItem('auth_user_id');
        if (userId) {
            const respuestaFavoritos = await fetch(`${API_BASE}/favoritos?usuarioId=${userId}`);
            if (respuestaFavoritos.ok) {
                const favs = await respuestaFavoritos.json();
                favoritos = favs.map(f => f.idCoche);
            }
        }

        mostrarCoches(currentCoches);
    } catch (err) {
        console.error('Error al obtener coches:', err);
        container.innerHTML = '<p class="inline-error">Error al cargar el catálogo.</p>';
    }
}

async function cargarCatalogosFiltro() {
    try {
        const [marcas, combustibles] = await Promise.all([
            fetch(`${API_BASE}/marcas`).then(r => r.json()),
            fetch(`${API_BASE}/combustibles`).then(r => r.json())
        ]);

        const selectMarca = document.getElementById('filter-marca');
        const selectComb = document.getElementById('filter-combustible');

        if (selectMarca) {
            selectMarca.innerHTML = '<option value="">Todas las Marcas</option>' + 
                marcas.map(m => `<option value="${m.idMarca}">${m.nombre}</option>`).join('');
        }
        if (selectComb) {
            selectComb.innerHTML = '<option value="">Combustible</option>' + 
                combustibles.map(c => `<option value="${c.idCombustible}">${c.nombre}</option>`).join('');
        }
    } catch (err) {
        console.error('Error cargando catálogos de filtro:', err);
    }
}

function configurarAccionesFiltro() {
    const btn = document.getElementById('btn-buscar');
    if (!btn) return;

    btn.onclick = () => {
        const filtros = {};
        const marcaId = document.getElementById('filter-marca').value;
        const combId = document.getElementById('filter-combustible').value;
        const precioMax = document.getElementById('filter-precio').value;

        if (marcaId) filtros.marcaId = marcaId;
        if (combId) filtros.combustibleId = combId;
        if (precioMax) filtros.precioMax = precioMax;

        cargarCoches(filtros);
    };
}

function mostrarCoches(coches) {
    const container = document.getElementById('catalogo');
    if (coches.length === 0) {
        container.innerHTML = '<p class="empty-state">No hay coches que coincidan con tu búsqueda.</p>';
        return;
    }
    
    const tieneSesion = !!localStorage.getItem('auth_user_id');

    container.innerHTML = coches.map(c => {
        const id = c.idCoche;
        const esFavorito = favoritos.includes(id);
        const estiloIconoFavorito = "width: 22px; height: 22px; pointer-events: none;";
        return `
        <div class="tarjeta-coche">
            <div class="contenedor-imagen-coche">
                <img src="${c.imagen || imagenMarcadorPosicion()}" class="imagen-coche" alt="${escaparHtml(`${c.marca} ${c.modelo}`)}" loading="lazy">
                <button class="boton-favorito ${esFavorito ? 'active' : ''}" data-action="toggle-fav" data-id="${id}" title="Añadir a favoritos">
                    <svg viewBox="0 0 24 24" style="${estiloIconoFavorito}"><path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/></svg>
                </button>
            </div>
            <div class="info-coche">
                <h3 class="titulo-coche">${escaparHtml(`${c.marca} ${c.modelo}`)}</h3>
                <p class="version-coche">${escaparHtml(c.version || '')}</p>
                <div class="metadatos-coche">
                    <span class="precio-coche">${formatearPrecio(c.precio)}€</span>
                    <span class="km-coche">${formatearNumero(c.km)} km</span>
                </div>
                <div class="pie-coche">
                    <span>${escaparHtml(c.ciudad || '')}</span>
                    <span>${escaparHtml(String(c.anio ?? ''))}</span>
                </div>
                <div class="acciones-coche">
                    <button class="boton-principal" type="button" data-action="detalle" data-id="${id}">Ver más detalle</button>
                    <button class="boton-secundario" type="button" data-action="compra" data-id="${id}">Comprar</button>
                </div>
            </div>
        </div>
    `}).join('');
}

function configurarAccionesVehiculo() {
    console.log('Configurando acciones de vehículos...');
    const container = document.getElementById('catalogo');
    if (!container) return;
    if (container.dataset.wiredActions === '1') return;
    container.dataset.wiredActions = '1';

    container.addEventListener('click', async (e) => {
        const btn = e.target.closest('button[data-action]');
        if (!btn) return;

        const id = Number(btn.dataset.id);
        const accion = btn.dataset.action;
        console.log('Acción detectada:', accion, 'ID:', id);
        
        currentCocheId = id;

        if (accion === 'detalle') {
            abrirModalDetalle(id);
        } else if (accion === 'compra') {
            navegarA(`/compraVenta?id=${id}`);
        } else if (accion === 'toggle-fav') {
            manejarCambioFavorito(id, btn);
        } else if (accion === 'eliminar') {
            if (confirm('¿Estás seguro de que quieres eliminar este anuncio?')) {
                manejarEliminacion(id, btn);
            }
        }
    });
}

function configurarAccionesModales() {
    console.log('Configurando acciones modales...');
    
    // Evitar duplicar listeners
    if (document.body.dataset.wiredModals === '1') return;
    document.body.dataset.wiredModals = '1';

    document.addEventListener('click', (e) => {
        const botonDetalle = e.target.closest('button[data-action]');
        
        if (botonDetalle && botonDetalle.dataset.action === 'cerrar-detalle') {
            cerrarModal('modal-detalle');
        }
        
        if (botonDetalle && botonDetalle.dataset.action === 'compra' && e.target.closest('#modal-detalle')) {
            cerrarModal('modal-detalle');
            navegarA(`/compraVenta?id=${botonDetalle.dataset.id}`);
        }

        if (e.target.classList.contains('modal-capa-superpuesta')) {
            cerrarModal(e.target.id);
        }
    });

    // Checkbox términos (delegado porque el contrato es dinámico)
    // No longer needed here
}

async function manejarCambioFavorito(cocheId, boton) {
    const userId = localStorage.getItem('auth_user_id');
    if (!userId) {
        mostrarNotificacion('Debes iniciar sesión para guardar favoritos', 'error');
        return;
    }

    const esFavorito = favoritos.includes(cocheId);
    try {
        if (esFavorito) {
            console.log('Eliminando de favoritos:', { usuarioId: userId, cocheId: cocheId });
            const respuesta = await fetch(`${API_BASE}/favoritos?usuarioId=${userId}&cocheId=${cocheId}`, { method: 'DELETE' });
            if (!respuesta.ok) throw new Error('Error al eliminar de favoritos');
            boton.classList.remove('active');
            favoritos = favoritos.filter(id => id !== cocheId);
            showNotification('Eliminado de favoritos');
        } else {
            console.log('Añadiendo a favoritos:', { usuarioId: userId, cocheId: cocheId });
            const respuesta = await fetch(`${API_BASE}/favoritos`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ usuarioId: parseInt(userId), cocheId: cocheId })
            });
            if (!respuesta.ok) throw new Error('Error al añadir a favoritos');
            boton.classList.add('active');
            favoritos.push(cocheId);
            showNotification('Añadido a favoritos');
        }
    } catch (err) {
        console.error('Error al cambiar favorito:', err);
    }
}

async function abrirModalDetalle(id) {
    console.log('Abriendo modal detalle para ID:', id);
    try {
        const respuesta = await fetch(`${API_BASE}/coches/${id}`);
        if (!respuesta.ok) throw new Error('Coche no encontrado');
        const coche = await respuesta.json();

        const contenido = document.getElementById('detalle-content');
        const titulo = document.getElementById('detalle-titulo');
        if (titulo) titulo.textContent = `${coche.marca} ${coche.modelo}`;

        contenido.innerHTML = `
        <div class="cuadricula-detalle">
            <div class="galeria-detalle">
                <img src="${coche.imagen || imagenMarcadorPosicion()}" class="imagen-detalle" alt="${coche.marca} ${coche.modelo}">
            </div>
            <div class="info-detalle">
                <p class="version-coche" style="font-size: 1.2rem; color: var(--text-muted); margin-bottom: 0.5rem;">${escaparHtml(coche.version)}</p>
                <div class="precio-detalle">${formatearPrecio(coche.precio)}€</div>
                
                <div class="lista-especificaciones">
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Kilometraje</span>
                        <span class="valor-especificacion">${formatearNumero(coche.km)} km</span>
                    </div>
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Año</span>
                        <span class="valor-especificacion">${coche.anio}</span>
                    </div>
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Categoría</span>
                        <span class="valor-especificacion">${escaparHtml(coche.categoria || 'No disponible')}</span>
                    </div>
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Combustible</span>
                        <span class="valor-especificacion">${escaparHtml(coche.combustible)}</span>
                    </div>
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Transmisión</span>
                        <span class="valor-especificacion">${escaparHtml(coche.transmision)}</span>
                    </div>
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Color</span>
                        <span class="valor-especificacion">${escaparHtml(coche.color || 'No disponible')}</span>
                    </div>
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Etiqueta</span>
                        <span class="valor-especificacion">${escaparHtml(coche.etiquetaAmbiental || 'No disponible')}</span>
                    </div>
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Ciudad</span>
                        <span class="valor-especificacion">${escaparHtml(coche.ciudad)}</span>
                    </div>
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Estado</span>
                        <span class="valor-especificacion">${escaparHtml(coche.estado)}</span>
                    </div>
                    <div class="item-especificacion">
                        <span class="etiqueta-especificacion">Fecha pub.</span>
                        <span class="valor-especificacion">${coche.fechaPublicacion ? new Date(coche.fechaPublicacion).toLocaleDateString('es-ES') : 'No disponible'}</span>
                    </div>
                </div>

                <div class="info-vendedor">
                    <h3>Datos del vendedor</h3>
                    <p><strong>Vendedor:</strong> ${escaparHtml(coche.vendedorNombre)}</p>
                    <p><strong>DNI:</strong> ${escaparHtml(coche.vendedorDni || 'No disponible')}</p>
                    <p><strong>Email:</strong> ${escaparHtml(coche.vendedorEmail)}</p>
                    <p><strong>Teléfono:</strong> ${escaparHtml(coche.vendedorTelefono || 'No disponible')}</p>
                </div>

                <div style="margin-top: 2rem; display: flex; gap: 1rem; justify-content: flex-end; border-top: 1px solid var(--border); padding-top: 1.5rem;">
                    <button class="boton-secundario" type="button" data-action="cerrar-detalle">Cerrar</button>
                    <button class="boton-principal" type="button" data-action="compra" data-id="${coche.idCoche || id}">Comprar ahora</button>
                </div>
            </div>
        </div>
    `;

    // Solo abrimos el modal, la delegación global en wireModalActions se encarga de los botones internos
    abrirModal('modal-detalle');
    } catch (err) {
        console.error(err);
        mostrarNotificacion('Error al cargar detalles.', 'error');
    }
}

async function manejarEliminacion(id, boton) {
    boton.disabled = true;
    try {
        const respuesta = await fetch(`/api/eliminarAnuncioVehiculo?id=${encodeURIComponent(id)}`, { method: 'DELETE' });
        const datos = await obtenerJsonSeguro(respuesta);
        if (!respuesta.ok) throw new Error(datos?.message || 'Error al eliminar');
        mostrarNotificacion('Anuncio eliminado.', 'success');
        boton.closest('.tarjeta-coche').remove();
    } catch (err) {
        mostrarNotificacion(err.message, 'error');
        boton.disabled = false;
    }
}

function abrirModal(id) {
    const modal = document.getElementById(id);
    if (modal) modal.classList.add('active');
}

function cerrarModal(id) {
    console.log('Cerrando modal:', id);
    const modal = document.getElementById(id);
    if (modal) modal.classList.remove('active');
}

async function obtenerJsonSeguro(respuesta) {
    try {
        return await respuesta.json();
    } catch {
        return null;
    }
}

function formatearNumero(valor) {
    const n = Number(valor);
    if (!Number.isFinite(n)) return '';
    return new Intl.NumberFormat('es-ES').format(n);
}

function formatearPrecio(valor) {
    const n = Number(valor);
    if (!Number.isFinite(n)) return '';
    return new Intl.NumberFormat('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(n);
}

function escaparHtml(cadena) {
    return String(cadena)
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#039;');
}

function imagenMarcadorPosicion() {
    const svg = `
      <svg xmlns="http://www.w3.org/2000/svg" width="800" height="450" viewBox="0 0 800 450">
        <defs>
          <linearGradient id="g" x1="0" y1="0" x2="1" y2="1">
            <stop offset="0" stop-color="#E2E8F0"/>
            <stop offset="1" stop-color="#F8FAFC"/>
          </linearGradient>
        </defs>
        <rect width="800" height="450" fill="url(#g)"/>
        <path d="M190 285c18-42 40-74 68-95 34-26 80-40 142-40h60c62 0 108 14 142 40 28 21 50 53 68 95" fill="none" stroke="#94A3B8" stroke-width="14" stroke-linecap="round"/>
        <path d="M168 285h464c22 0 40 18 40 40v18c0 18-14 32-32 32H160c-18 0-32-14-32-32v-18c0-22 18-40 40-40z" fill="none" stroke="#94A3B8" stroke-width="14"/>
        <circle cx="250" cy="345" r="34" fill="#F8FAFC" stroke="#94A3B8" stroke-width="14"/>
        <circle cx="548" cy="345" r="34" fill="#F8FAFC" stroke="#94A3B8" stroke-width="14"/>
        <text x="400" y="225" text-anchor="middle" font-family="Inter, system-ui, sans-serif" font-size="28" fill="#64748B">Sin imagen</text>
      </svg>
    `.trim();
    return `data:image/svg+xml;charset=utf-8,${encodeURIComponent(svg)}`;
}
