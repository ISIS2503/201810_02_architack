package p1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.sun.jmx.snmp.Timestamp;

public class AppMQTT {

    
    private static final String URLCORREO = "http://localhost:8088/alarm";

    public AppMQTT() {
    }

    public void messageArrived(String topic, String message) throws Exception {
        topic = "123/12/12/12";
               String[] topico = topic.split("/");
        String[] messages = new String[4];
        messages[0] = "yale@gmail.com";
        messages[1] = "cliente@gmail.edu.co";
        messages[2] = "ALARMA!";
        String jsonCorreo = "";
        String jsonPersistir = "";
        int tipo;
        String mens = "";

        System.out.print("TOPICO : " + topic);
        System.out.print("MENSAJE : " + message);
        String pUrlPersistir = "http://172.24.42.30:8080/unidadresidencial/persistir";

        if (message.trim().equalsIgnoreCase("11111")) {
            tipo = 1;
            mens = "Puerta abierta por mas 30 segundos";
        } else if (message.trim().equalsIgnoreCase("22222")) {
            tipo = 2;
            mens = "Se detecto movimiento en la cerradura";
        } else if (message.trim().equalsIgnoreCase("44444")) {
            tipo = 4;
            mens = "Cerradura con nivel de bateria critica";
        } else {
            tipo = 3;
            mens = "Numero de intentos excedidos";
        }

        messages[3] = "Hubo una alarma en su inmueble: " + mens;
        System.out.println(messages[3]);

        jsonCorreo = "{\"correoEmisor\": \"" + messages[0] + "\", \"correoReceptor\" : \"" + messages[1]
                + " \", \"asunto\" : \"" + messages[2] + "\", \"cuerpo\" : \"" + messages[3] + "\"}";

        String[] tiempo = (new Timestamp(System.currentTimeMillis())).toString().split("\\{");
        String withFormat = tiempo[2].substring(12, tiempo[2].length() - 1);
        System.out.print(withFormat);
        
        System.out.print("-------------------");
        
          jsonPersistir = "{\"idUnidadR\": \"" + topico[0] + "\", \"idResidencia\" : \"" + topico[1] + "\" , \"idHub\" : \"" + topico[2] + "\" , \"idCerradura\" : \""
                  + topico[3] + "\" , \"tipo\" :" + tipo + ", \"mensaje\" : \"" + messages[3] + "\" ," + " \"tiempo\" : \"" + withFormat + "\"}";
       
          System.out.print("Correo : " + jsonCorreo);
          System.out.print("Por persistir :" + jsonPersistir);
//        enviardatosCorreo(jsonCorreo);
//        enviarDatosPersistir(jsonPersistir, pUrlPersistir);
    }

    public static String enviardatosCorreo(String jsonCorreo) {
        String resultado = "";

        try {
            String urlParameters = jsonCorreo;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            String request = URLCORREO;
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            try {
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(postData);
                wr.flush();
                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                for (int c; (c = in.read()) >= 0;) {
                    resultado += (char) c;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println("RESULTADO:" + resultado);
        return resultado;
    }

    private static String enviarDatosPersistir(String jsonPersistir, String pUrlPersistir) {
        String resultado = "";

        try {
            String urlParameters = jsonPersistir;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            String request = pUrlPersistir;
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            try {
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(postData);
                wr.flush();
                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                for (int c; (c = in.read()) >= 0;) {
                    resultado += (char) c;
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        String resultadoFinal = resultado;
        System.out.println("RESULTADO: " + resultado);
        return resultado;

    }
}
