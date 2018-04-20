
package service;

import dtos.MessageDTO;
import dtos.MqttDTO;
import dtos.PasswordDTO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import p1.AppMQTT;
import p1.AppClaves;

@Path("claves")
public class PasswordService {
    
    private AppClaves appClaves;
            
    //private AppMQTT mqtt;
            
    public PasswordService() {
        appClaves = new AppClaves();
    }

    @PUT
    @Path("actualizar")
    @Consumes("application/json")
    @Produces("application/json")
    public MessageDTO updatePassword(PasswordDTO dto) throws Exception {
        dto.setComando("CHANGE_PASSWORD");
        System.out.println(dto.getComando());
        System.out.println(dto.getIdClave());
        System.out.println(dto.getNuevaClave());

        appClaves.publishMessage(dto.getComando(), dto.getIdClave(), dto.getNuevaClave());
        
       return  new MessageDTO("Se cambió la contraseña satisfactoriamente.");
    }
    
    
    @POST
    @Path("agregar")
    @Consumes("application/json")
    @Produces("application/json")
    public MessageDTO addPassword(PasswordDTO dto) throws Exception {
        dto.setComando("ADD_PASSWORD");
        System.out.println(dto.getComando());
        System.out.println(dto.getIdClave());
        System.out.println(dto.getNuevaClave());
        
        appClaves.publishMessage(dto.getComando(), dto.getIdClave(), dto.getNuevaClave());
        
       return  new MessageDTO("Se agregó la contraseña satisfactoriamente.");
    }
    
    @DELETE
    @Path("eliminar")
    @Consumes("application/json")
    @Produces("application/json")
    public MessageDTO deletePassword(PasswordDTO dto) throws Exception {
        dto.setComando("DELETE_PASSWORD");
        System.out.println(dto.getComando());
        System.out.println(dto.getIdClave());
        System.out.println(dto.getNuevaClave());
        
        appClaves.publishMessage(dto.getComando(), dto.getIdClave(), dto.getNuevaClave());
        
       return  new MessageDTO("Se eliminó la contraseña satisfactoriamente.");
    }
    
    @DELETE
    @Path("eliminarTodas")
    @Produces("application/json")
    public MessageDTO deleteAllPasswords() throws Exception {
        PasswordDTO dto = new PasswordDTO();
        dto.setComando("DELETE_ALL");
        dto.setIdClave(00);
        dto.setNuevaClave(0000);
        System.out.println(dto.getComando());
        System.out.println(dto.getIdClave());
        System.out.println(dto.getNuevaClave());
        
        appClaves.publishMessage(dto.getComando(), dto.getIdClave(), dto.getNuevaClave());
        
       return  new MessageDTO("Se eliminaron todas las contraseñas satisfactoriamente.");
    }
    
}
