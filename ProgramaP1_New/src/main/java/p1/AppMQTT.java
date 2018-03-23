package p1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.sun.jmx.snmp.Timestamp;

public class AppMQTT {

//    MqttClient client;
    // UnidadResdencial/Residencia/Alarma
    // CorreoEmitente, CorreoReceptor, Asunto, Cuerpo
    private static final String URLCORREO = "http://172.24.42.50:8080/alarm";

    public AppMQTT() {
    }

    public void messageArrived(String topic, String message) throws Exception {
        ThreadServidor ts = new ThreadServidor(topic, message);
        ts.start();
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

    //client.subscribe("+/+/alarma/#", 0);
    public static void procesar(String topic, String message) {
        String[] topico = topic.split("/");
        String[] messages = new String[4];
        messages[0] = "yale@gmail.com";
        messages[1] = "cliente@gmail.edu.co";
        messages[2] = "ALARMA!";
        String jsonCorreo = "";
        String jsonPersistir = "";
        int tipo;
        String mens = "";

        String pUrlPersistir = "http://172.24.42.30:8080/" + topico[0] + "/" + topico[1]
                + "/" + topico[2];

        if (message.equalsIgnoreCase("11111")) {
            tipo = 1;
            mens = "Puerta abierta por mas 30 segundos";
        } else if (message.equalsIgnoreCase("22222")) {
            tipo = 2;
            mens = "Se detecto movimiento en la cerradura";
        } else if (message.equalsIgnoreCase("44444")) {
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

        jsonPersistir = "{\"tipo\": \"" + tipo + "\", \"mensaje\" : \"" + messages[3] + "\" , \"tiempo\" : \""
                + withFormat + "\"}";

        enviardatosCorreo(jsonCorreo);
        //enviarDatosPersistir(jsonPersistir, pUrlPersistir);
    }
}
