import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Bridge{

	public final static String URL_BALANCEADOR = "http://172.24.42.106:80/Yale/alarma";

	MqttClient client;



	public Bridge() {
	}

	public static void main(String[] args) {
		new Bridge().recibirMensajes();
	}

	public void recibirMensajes() {
		try {
			client = new MqttClient("tcp://172.24.42.106:8083", "Subscribing");
			client.connect();
			client.setCallback(new MqttCallback() {
				public void connectionLost(Throwable cause) {}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("Message: " + message.toString());
					System.out.println(topic);
					String jsonMessage = createJson(message.toString(), topic);
					System.out.println(jsonMessage);
					sendRequest(jsonMessage);
					//sendPost(jsonMessage);
				}

				public void deliveryComplete(IMqttDeliveryToken token) {}
			});

			//client.subscribe("alarmaprueba");
			//client.subscribe("unidadResidencial/residencia/alarma/puertaAbierta", 0);
			//client.subscribe("unidadResidencial/residencia/alarma/movimientoDetectado", 0);
			//client.subscribe("unidadResidencial/residencia/alarma/intentosExcedidos", 0);
			//client.subscribe("#", 0);
			client.subscribe("UnidadResidencial/Inmueble/#");
			client.subscribe("UnidadResidencial/Inmueble/Hub/Alarma");
			client.subscribe("Unidad1/Inmueble1/alarma/bateria baja");

		} 
		catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public String createJson(String message, String topic) {

		String [] fixTopic = topic.split("/");
		String topicPost = fixTopic[0] + "/" + fixTopic[1] + "/alarma/tipo alarma";
		String json = "";
		json = "{\"topic\": \"" + topicPost + "\", \"message\" : \"" + message + "\"}";

		return json;



	}

	public void sendRequest(String messageJson) {
		String resultado = "";

		try {
			String urlParameters = messageJson;
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			String request = URL_BALANCEADOR;
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


	}
	
	

}



