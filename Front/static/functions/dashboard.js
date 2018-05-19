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