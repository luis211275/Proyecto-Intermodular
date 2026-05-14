
const btnIniciarL = document.getElementById("btnLogin");
const form = document.getElementById("form-login");
const mensajeHtml = document.getElementById("mensajeError");

btnIniciarL.addEventListener("click", (e) => {
    e.preventDefault();

    const login ={
        email: document.getElementById("email-login").value.trim(),
        password: document.getElementById("password-login").value.trim()
    }

    console.log(login);

 

    fetch("http://localhost:8080/user/logger", {
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
        // Antes solo redirigíamos a home.html y no persistíamos ninguna sesión.
        // Como publicar.js y compraVenta.js validan el login leyendo localStorage,
        // el usuario parecía deslogueado al entrar en rutas protegidas.
        const usuarioAutenticado = {
            email: data.email || login.email,
            nombres: data.nombres || "",
            apellidos: data.apellidos || "",
            dni: data.dni || "",
            telefono: data.telefono || ""
        };

        // Ahora guardamos una sesión mínima para que el resto del frontend
        // detecte correctamente que el usuario ya está autenticado.
        // También guardamos nombre y DNI para mostrarlos en la compraventa.
        localStorage.setItem("auth_user", JSON.stringify(usuarioAutenticado));

        // Antes no guardábamos el id del usuario y publicar.js acababa usando
        // un id fijo por defecto. Ahora guardamos el id real que devuelve el backend.
        if (data.userId) {
            localStorage.setItem("auth_user_id", String(data.userId));
        }

        // Si el usuario fue enviado aquí desde una página protegida, lo
        // devolvemos a esa URL en lugar de mandarlo siempre al home.
        const redirectPendiente = localStorage.getItem("redirect_after_login");
        if (redirectPendiente) {
            localStorage.removeItem("redirect_after_login");
            window.location.href = redirectPendiente;
            return;
        }

        // Si no hay redirección pendiente, mantenemos el comportamiento normal.
        window.location.href = "home.html";
    })
    .catch(error => {
        console.error("Error detectado:", error.message);
        mensajeHtml.innerText = error.message;
        mensajeHtml.style.color = "red";
    });
});
