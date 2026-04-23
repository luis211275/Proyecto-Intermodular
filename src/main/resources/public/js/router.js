import { inicializarInicio } from './home.js';
import { inicializarPublicar } from './publicar.js';
import { inicializarCompraVenta } from './compraVenta.js';

const rutas = {
    '/': { vista: '/views/home.html', inicializar: inicializarInicio, css: '/css/home.css' },
    '/publicar': { vista: '/views/publicar.html', inicializar: inicializarPublicar, css: '/css/publicar.css' },
    '/compraVenta': { vista: '/views/compraVenta.html', inicializar: inicializarCompraVenta, css: '/css/compraVenta.css' }
};

const contenedorApp = document.getElementById('app');

export const navegarA = async (url) => {
    history.pushState(null, null, url);
    await enrutar();
};

export const actualizarBarraNavegacionAutenticacion = () => {
    const contenedor = document.getElementById('nav-auth-container');
    if (!contenedor) return;

    const tieneSesion = !!localStorage.getItem('auth_user') || !!localStorage.getItem('auth_user_id');

    if (tieneSesion) {
        contenedor.innerHTML = `<a href="#" class="elemento-navegacion" id="btn-logout">Cerrar sesión</a>`;
        document.getElementById('btn-logout')?.addEventListener('click', (e) => {
            e.preventDefault();
            localStorage.clear();
            actualizarBarraNavegacionAutenticacion();
            mostrarNotificacion('Sesión cerrada correctamente', 'success');
            navegarA('/');
        });
    } else {
        contenedor.innerHTML = `<a href="#" class="elemento-navegacion" id="btn-login">Iniciar sesión / Registrarse</a>`;
        document.getElementById('btn-login')?.addEventListener('click', (e) => {
            e.preventDefault();
            // Simulación de login para 1º DAW
            const usuarioSimulado = { 
                id: 11, 
                nombre: 'Diego', 
                apellido: 'García', 
                email: 'diego@example.com', 
                dni: '12345678X', 
                telefono: '600123456' 
            };
            localStorage.setItem('auth_user', JSON.stringify(usuarioSimulado));
            localStorage.setItem('auth_user_id', '11');
            actualizarBarraNavegacionAutenticacion();
            mostrarNotificacion('Sesión iniciada correctamente', 'success');
            navegarA('/');
        });
    }
};

const enrutar = async () => {
    let ruta = window.location.pathname;
    if (!rutas[ruta]) ruta = '/';
    
    const infoRuta = rutas[ruta];
    
    try {
        const respuesta = await fetch(infoRuta.vista, { cache: 'no-store' });
        if (!respuesta.ok) throw new Error(`HTTP ${respuesta.status} cargando ${infoRuta.vista}`);
        const html = await respuesta.text();
        contenedorApp.innerHTML = html;
        actualizarEstiloPagina(infoRuta.css);
        establecerNavegacionActiva(ruta);
        actualizarBarraNavegacionAutenticacion();
        
        // REINICIALIZAR LISTENERS GLOBALES si es la home para evitar pérdida tras navegación
        if (ruta === '/') {
            // home.js ya tiene su propia lógica de wiredActions pero forzamos reset
            document.getElementById('catalogo').dataset.wiredActions = '';
        }

        if (infoRuta.inicializar) {
            console.log('Ejecutando inicialización para:', ruta);
            await infoRuta.inicializar();
        }
    } catch (err) {
        console.error('Error cargando vista:', err);
        contenedorApp.innerHTML = '<p class="inline-error">Error al cargar la página.</p>';
        mostrarNotificacion('No se pudo cargar la vista.', 'error');
    }
};

window.addEventListener('popstate', enrutar);

document.addEventListener('DOMContentLoaded', () => {
    document.body.addEventListener('click', e => {
        const enlace = e.target.closest('[data-route]');
        if (!enlace) return;
        if (enlace.origin && enlace.origin !== window.location.origin) return;
        e.preventDefault();
        navegarA(enlace.getAttribute('data-route'));
    });
    enrutar();
});

export function mostrarNotificacion(mensaje, tipo = 'success') {
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

function establecerNavegacionActiva(ruta) {
    document.querySelectorAll('.elemento-navegacion[data-route]').forEach((a) => {
        a.classList.toggle('active', a.getAttribute('data-route') === ruta);
    });
}

function actualizarEstiloPagina(rutaCss) {
    if (!rutaCss) return;
    let enlace = document.getElementById('page-style');
    if (!enlace) {
        enlace = document.createElement('link');
        enlace.id = 'page-style';
        enlace.rel = 'stylesheet';
        document.head.appendChild(enlace);
    }
    
    // Forzamos la recarga del CSS principal (home.css) siempre para estilos globales
    // y luego el específico de la página
    const marcaTiempo = new Date().getTime();
    
    // Si la ruta no es home.css, nos aseguramos de que home.css esté cargado como base
    if (rutaCss !== '/css/home.css') {
        // Podríamos tener múltiples links o simplemente confiar en que home.css tiene las variables
        // La mejor opción es que el router mantenga home.css y añada el específico
        let enlaceBase = document.getElementById('base-style');
        if (!enlaceBase) {
            enlaceBase = document.createElement('link');
            enlaceBase.id = 'base-style';
            enlaceBase.rel = 'stylesheet';
            enlaceBase.href = `/css/home.css?v=${marcaTiempo}`;
            document.head.insertBefore(enlaceBase, enlace);
        }
    }

    enlace.href = `${rutaCss}?v=${marcaTiempo}`;
}
