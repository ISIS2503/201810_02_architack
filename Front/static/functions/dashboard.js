function createWorker(id, path, token) {
    w = new Worker(path);
    w.postMessage({"id":id, "token":token});
    w.onmessage = function(event) {
        switch(event.data.type) {
            case 0:
                altertarResidencia(event.data.id,event.data.texto);
                break;
            case 1:
                calmarResidencia(event.data.id,event.data.texto);
                break;
        }
    }
}

function vistaAlertas(tipo, idUnidad) {
    if(tipo != "Alertas")
        window.location.replace("http://localhost:9080/dashboardAlertas?idUnidad=" + idUnidad);
}

function vistaFallos(tipo, idUnidad) {
    if(tipo != "Fallos")
        window.location.replace("http://localhost:9080/dashboardFallos?idUnidad=" + idUnidad);
}

function vistaCompleta(tipo, idUnidad) {
    if(tipo != "Completa")
        window.location.replace("http://localhost:9080/dashboard?idUnidad=" + idUnidad);
}

function altertarResidencia(id, texto) {
        var imagen = document.getElementById(id+"img");
        if(imagen.src != "https://image.ibb.co/hNyhYy/redhouse.png")
            imagen.src = "https://image.ibb.co/hNyhYy/redhouse.png";
        var textField = document.getElementById(id+"texto");
        if(textField.innerHTML != texto) 
            textField.innerHTML = texto;
}

function calmarResidencia(id, texto) {
        var imagen = document.getElementById(id+"img");
        if(imagen.src != "https://image.ibb.co/cJTS0d/greenhouse.png")
            imagen.src = "https://image.ibb.co/cJTS0d/greenhouse.png";
        var textField = document.getElementById(id+"texto");
        if(textField.innerHTML != texto) 
            textField.innerHTML = texto;
}