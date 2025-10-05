//alert('Pagina esta cargando');

function cambiarTexto() {
    const parrafo = document.getElementById("descripcion");
    parrafo.innerText = "Una nueva descripcion de producto.";
}

function validarFormulario() {
    const email = document.getElementById("exampleInputEmail1");
    const password = document.getElementById("exampleInputPassword1");

    if (email.value === '' || password.value === '') {
        alert("Datos incompletos");
        return false;
    }
}