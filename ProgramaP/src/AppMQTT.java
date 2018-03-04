import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class AppMQTT{

	MqttClient client;

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
				public void connectionLost(Throwable cause) {}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("Message: " + message.toString());
				}

				public void deliveryComplete(IMqttDeliveryToken token) {}
			});

			client.subscribe("unidadResidencial/residencia/alarma/puertaAbierta", 0);
			client.subscribe("unidadResidencial/residencia/alarma/movimientoDetectado", 0);
			client.subscribe("unidadResidencial/residencia/alarma/intentosExcedidos", 0);
			
		} 
		catch (MqttException e) {
			e.printStackTrace();
		}
	}



}