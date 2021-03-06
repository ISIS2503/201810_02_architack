
package co.edu.uniandes.isis2503.sistemaseguridad.service;

import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.CerraduraDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.auth.AuthorizationFilter.Role;
import co.edu.uniandes.isis2503.sistemaseguridad.auth.Secured;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.ICerraduraLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.logic.CerraduraLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.AlarmaDTO;
import java.util.logging.Logger;

/**
 *
 * @author ca.mendoza968
 */
@Path("/cerradura")
@Secured({Role.yale})
@Produces(MediaType.APPLICATION_JSON)
public class CerraduraService {
    
    private final ICerraduraLogic cerraduraLogic;

    public CerraduraService() {
        this.cerraduraLogic = new CerraduraLogic();
    }

    @POST
    public CerraduraDTO add(CerraduraDTO dto) {
        return cerraduraLogic.add(dto);
    }

    @PUT
    public CerraduraDTO update(CerraduraDTO dto) {
        return cerraduraLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    @Secured({Role.admin, Role.prop, Role.seguridad, Role.yale})
    public CerraduraDTO find(@PathParam("id") String id) {
        return cerraduraLogic.find(id);
    }

    @GET
    @Secured({Role.admin, Role.prop, Role.seguridad, Role.yale})
    public List<CerraduraDTO> all() {
        return cerraduraLogic.all();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            CerraduraDTO c = cerraduraLogic.find(id);
            c.setActiva(false);
            cerraduraLogic.update(c);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Cerradura was disabled").build();
        } catch (Exception e) {
            Logger.getLogger(CerraduraService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    } 
    
    @GET
    @Path("/{id}/alarms")
    @Secured({Role.yale, Role.admin, Role.seguridad, Role.prop})
    public List<AlarmaDTO> findAlarms(@PathParam("id") String id) {
        return cerraduraLogic.findAlarms(id);
    }
}
