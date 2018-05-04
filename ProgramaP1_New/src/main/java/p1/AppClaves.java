/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author af.lopezf
 */
public class AppClaves {
    
    public void publishMessage(String pComando, int pIdClave, int pNuevaClave) throws MqttException{
        
        String contenidoMensaje = pComando + ";" +pIdClave + ";" + pNuevaClave;
        
        MqttClient client = new MqttClient("tcp://172.24.42.106:8083", MqttClient.generateClientId());
        client.connect();
        MqttMessage message = new MqttMessage();
        message.setPayload(contenidoMensaje.getBytes());
        client.publish("UnidadResidencial/Inmueble/Hub/Cerradura/Configuracion", message);
        client.disconnect();
    }
    
}
