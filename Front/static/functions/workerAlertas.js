function analizar(id, token) {
    var xhr = new XMLHttpRequest();
    var texto = "Sin alarmas";
    var cambiarColor = false;
    var type = 1;
    xhr.open('GET', "http://localhost:8080/api/residencia/" + id + "/alarms", false);
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    xhr.send();

    if (xhr.readyState == 4) {
        console.log(xhr.status);
        var response = JSON.parse(xhr.responseText);
        evaluar(response);
    }

    function evaluar(response) {
        cambiarColor = false;
        for(var i = 0; i < response.length && !cambiarColor; i = i+1) {
            var alarma = response[i];
            if(!alarma.silenciada && (alarma.tipo != 5 && alarma.tipo != 6)) {
                texto = alarma.mensaje;
                cambiarColor = true;
            }
        }

        if(cambiarColor) type = 0;

        console.log(id + "//" + cambiarColor + "//" + texto + "//" + type);

        data = {"id":id, "texto":texto, "type": type};
        postMessage(data);
    }
}

self.onmessage = function(msg) {
    id = msg.data.id;
    token = msg.data.token;
    console.log(id, token);
    while(true) {
        analizar(id, token);
    }
}
