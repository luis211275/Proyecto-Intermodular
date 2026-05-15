const btnRegister = document.getElementById("btnRegister");
const formRegister = document.getElementById("form-register"); 

btnRegister.addEventListener("click", (e) => {
    e.preventDefault();

    const mensajeHtml = document.getElementById("mensajeRegistro");
    
    const registerData = {
        nombres: document.getElementById("nameRegister").value.trim(),
        apellidos: document.getElementById("apellidoRegister").value.trim(),
        email: document.getElementById("emailRegister").value.trim(),
        dni: document.getElementById("dniRegister").value.trim(),
        telefono: document.getElementById("telefonoRegister").value.trim(),
        password: document.getElementById("passwordRegister").value.trim()
    };
    
    
    // Validamos los datos.

    mensajeHtml.innerText = "";

    if (!registerData.email.includes("@")) {
        mensajeHtml.innerText = "El email debe ser válido (contener @)";
        return; 
    }


    const dniRegExp = /^\d{8}[a-zA-Z]$/;
    if (!dniRegExp.test(registerData.dni)) {
        mensajeHtml.innerText = "El DNI debe tener 8 números y 1 letra";
        return;
    }

    const telfRegExp = /^\d{9}$/;
    if (!telfRegExp.test(registerData.telefono)) {
        mensajeHtml.innerText = "El teléfono debe tener exactamente 9 números";
        return;
    }

    const passRegExp = /^(?=.*[A-Z]).{7,}$/;
    if (!passRegExp.test(registerData.password)) {
        mensajeHtml.innerText = "La contraseña requiere mínimo 7 caracteres y una mayúscula";
        return;
    }

    

    fetch("http://localhost:8080/user/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(registerData)
    })
    .then(response => {
        return response.json().then(data => {
            if (!response.ok) {
                throw new Error(data.message);
            }
            return data;
        });
    })
    .then(data => {
        console.log("Éxito:", data.message);
        window.location.href = "login.html"; 
    })
    .catch(error => {
        console.error("Error detectado:", error.message);
        mensajeHtml.innerText = error.message; 
    });
});
