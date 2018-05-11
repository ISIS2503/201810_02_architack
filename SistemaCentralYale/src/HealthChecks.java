import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Timer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HealthChecks{

	public final static String USUARIO = "yale";
	
	public final static String PASSWORD = "26b3b688cd3067bdceef21cda277c4c9";
	
	public final static String URL_CORREO = "http://172.24.42.26:8088/healthError";
	
	public final static int TIME_BETWEEN_HEALTHCHECKS = 10000;
	
	public final static int LOST_HEALTHCHECKS_FAIL = 4;
	

	private MqttClient client;
	
	//Para asegurar
	private MqttClient client2;
	
	private MqttConnectOptions connOpt;
	
	public long timeLastHealthCheck; 
	
	public long timeCurrentHealthCheck;



	public HealthChecks() {
		//timeLastHealthCheck = System.currentTimeMillis();
		timeLastHealthCheck = System.currentTimeMillis();

		
	}

	public static void main(String[] args) {
		//new HealthChecks().recibirMensajes();
		new HealthChecks().recibirMensajesAsegurado();
	}

	/**
	 * Recepción 
	 */
	public void recibirMensajes() {
		//String json = createJson("22222", "1212/2121/21/42");
		//sendRequest(json);
		try {
			client = new MqttClient("tcp://172.24.42.106:8083", "Subscribing");
			client.connect();
			client.setCallback(new MqttCallback() {
				public void connectionLost(Throwable cause) {}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					
					
					timeLastHealthCheck = timeCurrentHealthCheck;
					
					//Tomar tiempo llegada Health Check
					timeCurrentHealthCheck = System.currentTimeMillis();
				
					//Imprimir cotenido Health Check
					System.out.println("Health Check Hub: " + message.toString());
					

					//String jsonPrueba = createJsonCorreo("HUB FUERA DE LINEA", "Se han perdido la conexión con el hub ubicado en su inmueble.");

					//System.out.println(jsonPrueba);

				}

				public void deliveryComplete(IMqttDeliveryToken token) {}
			});
			
			
			

			//client.subscribe("alarmaprueba");
			//client.subscribe("unidadResidencial/residencia/alarma/puertaAbierta", 0);
			//client.subscribe("unidadResidencial/residencia/alarma/movimientoDetectado", 0);
			//client.subscribe("unidadResidencial/residencia/alarma/intentosExcedidos", 0);
			//client.subscribe("#", 0);
			client.subscribe("UnidadResidencial/Inmueble/Hub/HealthCheck");
			//client.subscribe("UnidadResidencial/Inmueble/Hub/Alarma");
			//client.subscribe("Unidad1/Inmueble1/alarma/bateria baja");
			
			
			Timer timer = new Timer();
			timer.schedule(new CheckTime(this), 10000, TIME_BETWEEN_HEALTHCHECKS);
			
			
			

		} 
		catch (MqttException e) {
			e.printStackTrace();
		}
	}

	
	
	
	public String createJsonCorreo(String asuntoError, String cuerpoError) {

		String json = "";
		
		String correoUsuario = "jagv1998@gmail.com";

		
		json = "{\"correo\": \"" + correoUsuario + "\", \"asunto\": \"" + asuntoError + "\", \"cuerpo\": \"" + cuerpoError + "\"}";

		return json;



	}
	

	public void sendRequest(String messageJson) {
		String resultado = "";

		try {
			String urlParameters = messageJson;
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			String request = URL_CORREO;
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

	public long getTimeLastHealthCheck() {
		return timeLastHealthCheck;
	}

	public void setTimeLastHealthCheck(long timeLastHealthCheck) {
		this.timeLastHealthCheck = timeLastHealthCheck;
	}

	public long getTimeCurrentHealthCheck() {
		return timeCurrentHealthCheck;
	}

	public void setTimeCurrentHealthCheck(long timeCurrentHealthCheck) {
		this.timeCurrentHealthCheck = timeCurrentHealthCheck;
	}
	
	
	
	public void recibirMensajesAsegurado() {
		//String json = createJson("22222", "1212/2121/21/42");
		//sendRequest(json);
		try {
			String clientID = "Subscribing";
			
			connOpt = new MqttConnectOptions();
			connOpt.setCleanSession(true);
			connOpt.setUserName("");
			connOpt.setPassword(PASSWORD.toCharArray());
			
			
			
			
			client2 = new MqttClient("tcp://172.24.42.106:8083", clientID);
			
			client2.setCallback(new MqttCallback() {
				public void connectionLost(Throwable cause) {}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					
					
					timeLastHealthCheck = timeCurrentHealthCheck;
					
					//Tomar tiempo llegada Health Check
					timeCurrentHealthCheck = System.currentTimeMillis();
				
					//Imprimir cotenido Health Check
					System.out.println("Health Check Hub: " + message.toString());
					

					//String jsonPrueba = createJsonCorreo("HUB FUERA DE LINEA", "Se han perdido la conexión con el hub ubicado en su inmueble.");

					//System.out.println(jsonPrueba);

				}

				public void deliveryComplete(IMqttDeliveryToken token) {}
			});
			
			client2.connect(connOpt);
			
			
			

			//client.subscribe("alarmaprueba");
			//client.subscribe("unidadResidencial/residencia/alarma/puertaAbierta", 0);
			//client.subscribe("unidadResidencial/residencia/alarma/movimientoDetectado", 0);
			//client.subscribe("unidadResidencial/residencia/alarma/intentosExcedidos", 0);
			//client.subscribe("#", 0);
			client.subscribe("UnidadResidencial/Inmueble/Hub/HealthCheck");
			//client.subscribe("UnidadResidencial/Inmueble/Hub/Alarma");
			//client.subscribe("Unidad1/Inmueble1/alarma/bateria baja");
			
			
			Timer timer = new Timer();
			timer.schedule(new CheckTime(this), 10000, TIME_BETWEEN_HEALTHCHECKS);
			
			
			

		} 
		catch (MqttException e) {
			e.printStackTrace();
		}
	}

	
	
	
	

}
