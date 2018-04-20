/*
 * The MIT License
 *
 * Copyright 2017 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IAlarmaLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.ICerraduraLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IHubLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.UnidadResidenciaLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.ResidenciaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UnidadResidencialDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.ResidenciaDTO;
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
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IUnidadResidencialLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IResidenciaLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.AlarmaLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.CerraduraLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.HubLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.CerraduraDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.HubDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.MensajeDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.RespuestaDTO;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author ca.mendoza968
 */
@Path("/unidadresidencial")
@Produces(MediaType.APPLICATION_JSON)
public class UnidadResidencialService {

    private final IUnidadResidencialLogic unidadResidencialLogic;
    private final IResidenciaLogic residenciaLogic;
    private final IHubLogic hubLogic;
    private final ICerraduraLogic cerraduraLogic;
    private final IAlarmaLogic alarmaLogic;

    public UnidadResidencialService() {
        this.unidadResidencialLogic = new UnidadResidenciaLogic();
        this.residenciaLogic = new ResidenciaLogic();
        this.hubLogic = new HubLogic();
        this.cerraduraLogic = new CerraduraLogic();
        this.alarmaLogic = new AlarmaLogic();
    }

    @POST
    public UnidadResidencialDTO add(UnidadResidencialDTO dto) {
        return unidadResidencialLogic.add(dto);
    }
    
    @POST
    @Path("/persistir")
    public List<Object> crearAlarma(MensajeDTO dto) throws Exception {
        List<Object> list = new LinkedList<>();
        RespuestaDTO r = new RespuestaDTO("El registro se realizo de manera exitosa");
        list.add(r);
        
        AlarmaDTO alarma = new AlarmaDTO(null, dto.getTipo(), dto.getTiempo(), dto.getMensaje());
        alarma = alarmaLogic.add(alarma);
        list.add(alarma);
        
        CerraduraDTO cerradura = cerraduraLogic.find(dto.getIdCerradura());
        if(cerradura == null) {
            cerradura = new CerraduraDTO();
            cerradura.setId(dto.getIdCerradura());
            cerradura = cerraduraLogic.add(cerradura);
            list.add(cerradura);
        }
        cerradura.addAlarma(alarma.getId());
        cerraduraLogic.update(cerradura);
        
        HubDTO hub = hubLogic.find(dto.getIdHub());
        if(hub == null) {
            hub = new HubDTO();
            hub.setId(dto.getIdHub());
            hub = hubLogic.add(hub);
            list.add(hub);
        }
        hub.addCerradura(cerradura.getId());
        hubLogic.update(hub);
        
        ResidenciaDTO residencia = residenciaLogic.find(dto.getIdResidencia());
        if(residencia == null) {
            residencia = new ResidenciaDTO(dto.getIdResidencia(), "Residencia autogenerada", "Propietario autogenerado", new ArrayList<String>(), true);
            residencia = residenciaLogic.add(residencia);
            list.add(residencia);
        }
        residencia.addHub(hub.getId());
        residenciaLogic.update(residencia);
            
        UnidadResidencialDTO unidadR = unidadResidencialLogic.find(dto.getIdUnidadR());
        if(unidadR == null) {
            unidadR = new UnidadResidencialDTO(dto.getIdUnidadR(), "Unidad Residencial autogenerada", new ArrayList<String>(), true);
            unidadR = unidadResidencialLogic.add(unidadR);
            list.add(unidadR);
        }
        unidadR.addResidencia(residencia.getId());
        unidadResidencialLogic.update(unidadR);
        
        return list;
    }

    @POST
    @Path("{nombre}/residencia")
    public ResidenciaDTO addResidencia(@PathParam("nombre") String id, ResidenciaDTO dto) throws Exception {
        UnidadResidencialDTO unidad = unidadResidencialLogic.find(id);
        ResidenciaDTO result = residenciaLogic.add(dto);
        unidad.addResidencia(result.getId());
        unidadResidencialLogic.update(unidad);
        return result;
    }

    @PUT
    public UnidadResidencialDTO update(UnidadResidencialDTO dto) {
        return unidadResidencialLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    public UnidadResidencialDTO find(@PathParam("id") String id) {
        return unidadResidencialLogic.find(id);
    }

    @GET
    public List<UnidadResidencialDTO> all() {
        return unidadResidencialLogic.all();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            UnidadResidencialDTO unidad = unidadResidencialLogic.find(id);
            unidad.setActiva(false);
            unidadResidencialLogic.update(unidad);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Unidad Residencial was disabled").build();
        } catch (Exception e) {
            Logger.getLogger(UnidadResidencialService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
