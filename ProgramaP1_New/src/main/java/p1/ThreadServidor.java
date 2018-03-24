package p1;

//import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ThreadServidor extends Thread{
	private String topic;
	private String message;
	public ThreadServidor(String topic, String message) {
        	this.topic = topic;
		this.message = message;
	}
	
	@Override
	public void run() {
		//AppMQTT.procesar(topic, message);
	}
}
