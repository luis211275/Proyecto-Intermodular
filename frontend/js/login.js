
const btnIniciarL = document.getElementById("btnLogin");
const form = document.getElementById("form-login");
const mensajeHtml = document.getElementById("mensajeError");
const btnVolver = document.getElementById("btnVolver");

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
        // Guardamos la sesión.
        const usuarioAutenticado = {
            email: data.email || login.email,
            nombres: data.nombres || "",
            apellidos: data.apellidos || "",
            dni: data.dni || "",
            telefono: data.telefono || ""
        };

        // Guardamos datos básicos.
        localStorage.setItem("auth_user", JSON.stringify(usuarioAutenticado));

        // Guardamos el id real.
        if (data.userId) {
            localStorage.setItem("auth_user_id", String(data.userId));
        }

        // Respetamos la vuelta pendiente.
        const redirectPendiente = localStorage.getItem("redirect_after_login");
        if (redirectPendiente) {
            localStorage.removeItem("redirect_after_login");
            window.location.href = redirectPendiente;
            return;
        }

        // Volvemos al inicio.
        window.location.href = "home.html";
    })
    .catch(error => {
        console.error("Error detectado:", error.message);
        mensajeHtml.innerText = error.message;
        mensajeHtml.style.color = "red";
    });
});

btnVolver?.addEventListener("click", () => {
    window.location.href = "home.html";
});
