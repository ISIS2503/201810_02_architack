
package co.edu.uniandes.isis2503.sistemaseguridad.service;

import co.edu.uniandes.isis2503.sistemaseguridad.auth.AuthorizationFilter.Role;
import co.edu.uniandes.isis2503.sistemaseguridad.auth.Secured;
import co.edu.uniandes.isis2503.sistemaseguridad.logic.AlarmaLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.AlarmaDTO;
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
import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IAlarmaLogic;
import java.util.logging.Logger;

/**
 *
 * @author ca.mendoza968
 */
@Path("/alarm")
@Secured({Role.yale})
@Produces(MediaType.APPLICATION_JSON)
public class AlarmaService {
    private final IAlarmaLogic sensorLogic;

    public AlarmaService() {
        this.sensorLogic = new AlarmaLogic();
    }

    @POST
    public AlarmaDTO add(AlarmaDTO dto) {
        return sensorLogic.add(dto);
    }

    @PUT
    public AlarmaDTO update(AlarmaDTO dto) {
        return sensorLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    @Secured({Role.admin, Role.prop, Role.seguridad, Role.yale})
    public AlarmaDTO find(@PathParam("id") String id) {
        return sensorLogic.find(id);
    }

    @GET
    @Secured({Role.admin, Role.prop, Role.seguridad, Role.yale})
    public List<AlarmaDTO> all() {
        return sensorLogic.all();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            sensorLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Alarma was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(AlarmaService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }    
}
