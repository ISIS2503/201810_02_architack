import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
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
			MqttClient client = new MqttClient("ssl://172.24.41.163:8083", MqttClient.generateClientId(),null);
	        String contrasenia = "Isis2503.";
	        //Opciones para enviar a mosquitto con clave y usuario
	        MqttConnectOptions options = new MqttConnectOptions();
	        options.setCleanSession(true);
	        options.setKeepAliveInterval(60);
	        options.setUserName("microcontrolador");
	        options.setPassword(contrasenia.toCharArray());
//	      options.setSSLProperties();
	        try {
//	        	File nuevo = new File("./certificado/m2mqtt_ca.crt");
//	        	System.out.println(nuevo.exists());
	        	SSLSocketFactory cert = getSocketFactory("./certificado/m2mqtt_ca.crt");
	        	options.setSocketFactory(cert);			
			} catch (Exception e) {
				System.out.println("error" + e.getMessage());
			}
	        client.connect(options);
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
	
	public SSLSocketFactory getSocketFactory(String caCrtFile) throws Exception
    {
    	Security.addProvider(new BouncyCastleProvider());

		// load CA certificate
    	PEMParser reader = new PEMParser(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(caCrtFile)))));
		X509CertificateHolder caCert = (X509CertificateHolder)reader.readObject();
		reader.close();
		
		KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
		caKs.load(null, null);
		X509Certificate certi = new JcaX509CertificateConverter().setProvider( "BC" )
		  .getCertificate( caCert );
		caKs.setCertificateEntry("ca-certificate", certi);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(caKs);
		
		SSLContext context = SSLContext.getInstance("TLSv1.1");
		context.init(null,tmf.getTrustManagers(), null);

		return context.getSocketFactory();
    }
	

}



