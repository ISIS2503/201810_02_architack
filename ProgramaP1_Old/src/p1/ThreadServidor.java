package p1;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ThreadServidor extends Thread{
	private String topic;
	private MqttMessage message;
	public ThreadServidor(String topic, MqttMessage message) {
		this.topic = topic;
		this.message = message;
	}
	
	@Override
	public void run() {
		AppMQTT.procesar(topic, message);
	}
}
