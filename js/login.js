
const btnIniciarL = document.getElementById("btnLogin");
const form = document.getElementById("form-login");
const mensajeHtml = document.getElementById("mensajeError");
const btnVolver = document.getElementById("mensajeError");

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
        window.location.href = "home.html"; 
    })
    .catch(error => {
        console.error("Error detectado:", error.message);
        mensajeHtml.innerText = error.message;
        mensajeHtml.style.color = "red";
    });
});





btnVolver.addEventListener("click", (e) =>{
    window.location.href="home.html";
});