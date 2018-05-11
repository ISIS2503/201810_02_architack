
package service;

import dtos.MessageDTO;
import dtos.MqttDTO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import p1.AppMQTT;

@Path("Yale/alarma")
public class P1Service {
    private AppMQTT mqtt;

    public P1Service() {
        this.mqtt = new AppMQTT();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public MessageDTO add(MqttDTO dto) throws Exception {
        System.out.println(" MENSAJE " + dto.getMessage());
       mqtt.messageArrived(dto.getTopic(), dto.getMessage());
       return new MessageDTO("La alarma se registro y notifico exitosamente en nuestro sistema de seguridad.");
    }
    
    @GET
    @Produces("application/json")
    public MqttDTO detail() {
        return new MqttDTO("this is a test", "already said this is a test");
    }
}

