const form = document.querySelector("#form-contacto");
const mensaje = document.querySelector("#mensaje");
const contador = document.querySelector("#contador");
const alerta = document.querySelector("#alerta");

mensaje.addEventListener("input", () => {
  contador.textContent = `${mensaje.value.length} / ${mensaje.maxLength} caracteres`;
});

form.addEventListener("submit", (e) => {
  e.preventDefault();
  alerta.classList.add("d-none");

  if (!form.checkValidity()) {
    form.classList.add("was-validated");
    return;
  }

  alerta.classList.remove("d-none");
  form.reset();
  form.classList.remove("was-validated");
  contador.textContent = `0 / ${mensaje.maxLength} caracteres`;
});
