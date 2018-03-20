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
	String[] messages = new String[4];
	String jsonCorreo = "";
	String jsonPersistir = "";
	private static String pUrlCorreo = "http://172.24.42.50:8080/postTest";
	private static String pUrlPersistir = "http://172.24.42.30:8080/alarms";
	String resultadoFinal = "";
	int tipo = -1;

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

					jsonCorreo = "{\"correoEmisor\": \"" + messages[0] + "\", \"correoReceptor\" : \"" + messages[1]
							+ " \", \"asunto\" : \"" + messages[2] + "\", \"cuerpo\" : \"" + messages[3] + "\"}";

					if (messages[2].equalsIgnoreCase("puertaAbierta"))
						tipo = 1;
					else if (messages[2].equalsIgnoreCase("movimientoDetectado"))
						tipo = 2;
					else
						tipo = 3;
					
					String[] tiempo = (new Timestamp(System.currentTimeMillis())).toString().split("\\{");
					String withFormat = tiempo[2].substring(12, tiempo[2].length() - 1);
					
					jsonPersistir = "{\"tipo\": \"" + tipo + "\", \"mensaje\" : \"" + messages[3] + "\" , \"tiempo\" : \""
							+ withFormat + "\" }";

					enviardatosCorreo();
					enviarDatosPersistir();
				}

				public void deliveryComplete(IMqttDeliveryToken token) {
				}
			});

			client.subscribe("+/+/alarma/#", 0);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public String enviardatosCorreo() {
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
		resultadoFinal = resultado;
		System.out.println(resultado);
		return resultado;
	}

	private String enviarDatosPersistir() {
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
		resultadoFinal = resultado;
		System.out.println(resultado);
		return resultado;

	}

}