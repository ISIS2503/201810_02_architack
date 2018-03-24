
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.CerraduraDTO;
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
import co.edu.uniandes.isis2503.nosqljpa.interfaces.ICerraduraLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.CerraduraLogic;

/**
 *
 * @author ca.mendoza968
 */
@Path("/cerradura")
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
    public CerraduraDTO find(@PathParam("id") String id) {
        return cerraduraLogic.find(id);
    }

    @GET
    public List<CerraduraDTO> all() {
        return cerraduraLogic.all();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            cerraduraLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Cerradura was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(CerraduraService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }    
}
