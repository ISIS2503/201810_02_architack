function analizarSilenciar(id, silenciada) {
    console.log(silenciada);
    if(silenciada == "False") {
        var boton = document.getElementById(id+"Boton");
        boton.class = "btn btn-danger";
        boton.innerHTML = "Silenciar";
        boton.disabled = false;
    }
}