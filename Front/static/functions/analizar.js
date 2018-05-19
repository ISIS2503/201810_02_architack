function analizar(id, token) {
    var xhr = new XMLHttpRequest();
    var texto = "Sin alarmas!";
    var cambiarColor = false;
    xhr.open('GET', "http://172.24.42.30:8080/api/residencia/" + id + "/alarms", true);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.send();
    xhr.addEventListener("readystatechange", processRequest, false);

    function processRequest(e) {
        if (xhr.readyState == 4) {
            console.log(xhr.status);
            var response = JSON.parse(xhr.responseText);
            evaluar(response);
        }
    }
    
    function evaluar(response) {
        cambiarColor = false;
        for(var i = 0; i < response.length && !cambiarColor; i = i+1) {
            var alarma = response[i];
            if(!alarma.silenciada) {
                texto = alarma.mensaje;
                cambiarColor = true;
            }
        }
        
        console.log(id + "//" + cambiarColor + "//" + texto);
        editarResidencia(id,texto);
    }
    
    function editarResidencia(id, texto) {
        var imagen = document.getElementById(id+"img");
        imagen.src = "https://image.ibb.co/hNyhYy/redhouse.png";
        var textField = document.getElementById(id+"texto");
        textField.innerHTML = texto;
    }
}