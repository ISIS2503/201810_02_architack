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


public class AppMQTT {

	MqttClient client;
	
	// UnidadResdencial/Residencia/Alarma
	// CorreoEmitente, CorreoReceptor, Asunto, Cuerpo
	String[] messages = new String[4];
	String json = "";
	private static String pUrl = "http://192.168.0.11:8080/postTest";
	String resultadoFinal = "";
	
	public AppMQTT() {
	}

	public static void main(String[] args) {
		new AppMQTT().recibirMensajes();
	}

	public void recibirMensajes() {
		messages[0] = "yale@gmail.com";
		messages[1] = "jag@gmail.com";
		messages[2] = "ALARMA!";
		try {
			client = new MqttClient("tcp://172.24.42.106:8083", "Subscribing");
			client.connect();
			client.setCallback(new MqttCallback() {
				public void connectionLost(Throwable cause) {
				}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("Message: " + message.toString());
					messages[3] = "Hubo una alarma en su inmueble: " + message.toString();

					json = "{\"correoEmisor\": \"" + messages[0] + "\", \"correoReceptor\" : \"" + messages[1] + " \", \"asunto\" : \""
							+ messages[2] + "\", \"cuerpo\" : \"" + messages[3] + "\"}";
					enviardatos();
				}

				public void deliveryComplete(IMqttDeliveryToken token) {
				}
			});

			client.subscribe("+/+/alarma/#", 0);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public String enviardatos() {
		String resultado = "";

		try {
			String urlParameters = json;
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			String request = pUrl;
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
		resultadoFinal = resultado;
		System.out.println(resultado);
		return resultado;

	}

}