const btnIniciarL = document.getElementById("btnLogin");
const form = document.getElementById("form-login");
const mensajeHtml = document.getElementById("mensajeError");

function estaLogueado() {
    return !!localStorage.getItem("auth_user") || !!localStorage.getItem("auth_user_id");
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

btnIniciarL.addEventListener("click", (e) => {
    e.preventDefault();

    const login = {
        email: document.getElementById("email-login").value.trim(),
        password: document.getElementById("password-login").value.trim()
    }

    if (!login.email || !login.password) {
        mensajeHtml.innerText = "Por favor, rellena todos los campos";
        mensajeHtml.style.color = "red";
        return;
    }

    fetch("/user/logger", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(login)
    })
        .then(response => {
            return response.json().then(data => {
                if (!response.ok) {
                    throw new Error(data.message || "Error en el servidor");
                }
                return data;
            });
        })
        .then(data => {
            console.log("Éxito:", data.message);

            // Tarea 8: Guardar sesión real
            // El backend devuelve un mensaje y el objeto usuario (si está bien configurado)
            // Si el backend no devuelve el usuario, guardamos al menos el email para simular
            const userObj = data.usuario || { email: login.email };
            localStorage.setItem("auth_user", JSON.stringify(userObj));
            localStorage.setItem("auth_user_id", userObj.id || "1");

            // Tarea 9: Redirección inteligente
            const redirectUrl = localStorage.getItem("redirect_after_login");
            if (redirectUrl) {
                localStorage.removeItem("redirect_after_login");
                window.location.href = redirectUrl;
            } else {
                window.location.href = "home.html";
            }
        })
        .catch(error => {
            console.error("Error detectado:", error.message);
            mensajeHtml.innerText = error.message;
            mensajeHtml.style.color = "red";
        });
});