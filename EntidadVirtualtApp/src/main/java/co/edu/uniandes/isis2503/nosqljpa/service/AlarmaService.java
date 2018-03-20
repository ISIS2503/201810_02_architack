
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.logic.AlarmaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlarmaDTO;
import com.sun.istack.logging.Logger;
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
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IAlarmaLogic;

/**
 *
 * @author ca.mendoza968
 */
@Path("/alarms")
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
    public AlarmaDTO find(@PathParam("id") String id) {
        return sensorLogic.find(id);
    }

    @GET
    public List<AlarmaDTO> all() {
        return sensorLogic.all();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            sensorLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Sensor was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(AlarmaService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }    
}
