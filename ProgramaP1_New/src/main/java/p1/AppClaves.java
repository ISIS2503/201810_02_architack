/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author af.lopezf
 */
public class AppClaves {
    
    public void publishMessage(String pComando, int pIdClave, int pNuevaClave) throws MqttException{
        
        String contenidoMensaje = pComando + ";" +pIdClave + ";" + pNuevaClave;
        
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
        MqttMessage message = new MqttMessage();
        message.setPayload(contenidoMensaje.getBytes());
        client.publish("UnidadResidencial/Inmueble/Hub/Cerradura/Configuracion", message);
        client.disconnect();
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
