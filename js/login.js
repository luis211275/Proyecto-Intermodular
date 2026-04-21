const myHeaders = new Headers();
const requestOptions = {
    method: "GET",
    headers: myHeaders,
    redirect: "follow"
};

const btnIniciarL = document.getElementById("btnLogin");
const form = document.getElementById("form-login")

btnIniciarL.addEventListener("click", (e) => {
    e.preventDefault();

    const login ={
        email: document.getElementById("email-login").value.trim(),
        password: document.getElementById("password-login").value.trim()
    }

    console.log(login);

    const jsonR = JSON.stringify(login);
    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "aplication/json")

     fetch("http://localhost:8080/user/logger",{
        method : "POST",
        headers:myHeaders,
        body: jsonR
    })

    .then(response => response.json())
    .then(data => {
        console.log("Resultado en el backend", data);
        form.reset();
    })
    .catch(error => console.error(error));
});