const myHeaders = new Headers();
const requestOptions = {
    method: "GET",
    headers: myHeaders,
    redirect: "follow"
};


const btnIniciarR = document.getElementById("btnRegister");

btnIniciarR.addEventListener("click", (e) => {
    e.preventDefault();

    const register = {
        nombres: document.getElementById("nameRegister").value.trim(),
        apellidos: document.getElementById("apellidoRegister").value.trim(),
        email: document.getElementById("emailRegister").value.trim(),
        dni: document.getElementById("dniRegister").value.trim(),
        telefono: document.getElementById("telefonoRegister").value.trim(),
        password: document.getElementById("passwordRegister").value.trim()
    }


    console.log(register);

    const jsonR = JSON.stringify(register);
    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "aplication/json")

     fetch("http://localhost:8080/user/register",{
        method : "POST",
        headers:myHeaders,
        body: jsonR
    })

    .then(response => response.json())
    .then(data => {
        console.log("Resultado en el backend", data)
    })
    .catch(error => console.error(error));
});