
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.logic.HubLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.HubDTO;
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
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IHubLogic;

/**
 *
 * @author ca.mendoza968
 */
@Path("/hub")
@Produces(MediaType.APPLICATION_JSON)
public class HubService {
    private final IHubLogic HubLogic;

    public HubService() {
        this.HubLogic = new HubLogic();
    }

    @POST
    public HubDTO add(HubDTO dto) {
        return HubLogic.add(dto);
    }

    @PUT
    public HubDTO update(HubDTO dto) {
        return HubLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    public HubDTO find(@PathParam("id") String id) {
        return HubLogic.find(id);
    }

    @GET
    public List<HubDTO> all() {
        return HubLogic.all();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            HubDTO hub = HubLogic.find(id);
            hub.setActivo(false);
            HubLogic.update(hub);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Hub was disabled").build();
        } catch (Exception e) {
            Logger.getLogger(HubService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }    
}
