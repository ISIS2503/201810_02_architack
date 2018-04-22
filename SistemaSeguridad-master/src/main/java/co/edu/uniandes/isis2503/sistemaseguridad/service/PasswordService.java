
package co.edu.uniandes.isis2503.sistemaseguridad.service;

import co.edu.uniandes.isis2503.sistemaseguridad.entidadFisica.AppClaves;
import co.edu.uniandes.isis2503.sistemaseguridad.entidadFisica.MessageDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.entidadFisica.PasswordDTO;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
        
       return  new MessageDTO("Se cambi� la contrase�a satisfactoriamente.");
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
        
       return  new MessageDTO("Se agreg� la contrase�a satisfactoriamente.");
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
        
       return  new MessageDTO("Se elimin� la contrase�a satisfactoriamente.");
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
        
       return  new MessageDTO("Se eliminaron todas las contrase�as satisfactoriamente.");
    }
    
}
