package p1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.sun.jmx.snmp.Timestamp;

public class AppMQTT {

	MqttClient client;

	// UnidadResdencial/Residencia/Alarma
	// CorreoEmitente, CorreoReceptor, Asunto, Cuerpo
	private static String pUrlCorreo = "http://172.24.42.50:8080/alarm";
	private static String pUrlPersistir = "http://172.24.42.30:8080/alarms";

	public AppMQTT() {
	}

	public static void main(String[] args) {
		new AppMQTT().recibirMensajes();
	}

	public void recibirMensajes() {
		try {
			client = new MqttClient("tcp://172.24.42.106:8083", "Subscribing");
			client.connect();
			client.setCallback(new MqttCallback() {
				public void connectionLost(Throwable cause) {
				}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					ThreadServidor ts = new ThreadServidor(topic, message);
					ts.start();
				}

				public void deliveryComplete(IMqttDeliveryToken token) {
				}
			});

			client.subscribe("+/+/alarma/#", 0);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public static String enviardatosCorreo(String jsonCorreo) {
		String resultado = "";

		try {
			String urlParameters = jsonCorreo;
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			String request = pUrlCorreo;
			URL url = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.setUseCaches(false);
			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.write(postData);
				wr.flush();
				Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				for (int c; (c = in.read()) >= 0;)
					resultado += (char) c;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String resultadoFinal = resultado;
		System.out.println(resultado);
		return resultado;
	}

	private static String enviarDatosPersistir(String jsonPersistir) {
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
			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.write(postData);
				wr.flush();
				Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				for (int c; (c = in.read()) >= 0;)
					resultado += (char) c;

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String resultadoFinal = resultado;
		System.out.println(resultado);
		return resultado;

	}
	
	public static void procesar(String topic, MqttMessage message) {
		String[] messages = new String[4];
		messages[0] = "yale@gmail.com";
		messages[1] = "cliente@gmail.edu.co";
		messages[2] = "ALARMA!";
		String jsonCorreo = "";
		String jsonPersistir = "";
		int tipo = -1;
		
		String[] msg = message.toString().split("\"");
	
		messages[3] = "Hubo una alarma en su inmueble: " +  msg[3];
		System.out.println(messages[3]);
		
		jsonCorreo = "{\"correoEmisor\": \"" + messages[0] + "\", \"correoReceptor\" : \"" + messages[1]
				+ " \", \"asunto\" : \"" + messages[2] + "\", \"cuerpo\" : \"" + messages[3] + "\"}";

		if (msg[3].equalsIgnoreCase("Puerta abierta por mas 30 segundos"))
			tipo = 1;
		else if (msg[3].equalsIgnoreCase("Se detecto movimiento en la cerradura"))
			tipo = 2;
		else if (msg[3].equalsIgnoreCase("Cerradura con nivel de bateria critica"))
			tipo = 4;
		else
			tipo = 3;
		
		String[] tiempo = (new Timestamp(System.currentTimeMillis())).toString().split("\\{");
		String withFormat = tiempo[2].substring(12, tiempo[2].length() - 1);
		
		jsonPersistir = "{\"tipo\": \"" + tipo + "\", \"mensaje\" : \"" + messages[3] + "\" , \"tiempo\" : \""
				+ withFormat + "\" }";

		enviardatosCorreo(jsonCorreo);
		//enviarDatosPersistir(jsonPersistir);
	}
}