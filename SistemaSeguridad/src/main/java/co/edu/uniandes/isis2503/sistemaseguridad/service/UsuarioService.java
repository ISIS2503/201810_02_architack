/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
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
package co.edu.uniandes.isis2503.sistemaseguridad.service;

import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IHorarioLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IUsuarioLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.logic.HorarioLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.logic.UsuarioLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.HorarioDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.RespuestaDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.UsuarioDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
<<<<<<< HEAD
import java.util.List;
=======
import java.net.URLEncoder;
>>>>>>> 62ebffd975ae32886ddd7fa0d4e8518e0019511b
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ja.gomez1
 */
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioService {
    private static final String DENIED = "access_denied";
    private static final String DOMAIN = "isis2503-jagomez1.auth0.com";
    private static final String EXTENSION_URL = "https://isis2503-jagomez1.us.webtask.io/adf6e2f2b84784b57522e3b19dfc9201/api";
    private static final long TIME_OUT = 2000;
    private final IUsuarioLogic usuarioLogic;
    private final IHorarioLogic horarioLogic;
    
    public UsuarioService(){
        this.usuarioLogic = new UsuarioLogic();
        this.horarioLogic = new HorarioLogic();
    }
    
    @POST
    @Path("{id}/horarios")
    public HorarioDTO agregarHorario(@PathParam("id") String id,HorarioDTO dto) throws Exception {
        
        if (dto.getHorarioFinal().split(":").length != 2 || dto.getHorarioInicio().split(":").length != 2) throw new Exception("Formato de horario incorrecto");
        UsuarioDTO user = usuarioLogic.find(id);
        HorarioDTO horario = horarioLogic.add(dto);
        user.addHorario(horario.getId());
        usuarioLogic.update(user);
        return horario;
    }
    
    @PUT
    @Path("/modificarHorario")
    public HorarioDTO modificarHorario(HorarioDTO dto) throws Exception {
        if (dto.getHorarioFinal().split(":").length != 2 || dto.getHorarioInicio().split(":").length != 2) throw new Exception("Formato de horario incorrecto");
        HorarioDTO horario = horarioLogic.update(dto);
        return horario;
    }
    
    @DELETE
    @Path("{idUser}/eliminarHorario/{id}")
    public void delete(@PathParam("id") String id, @PathParam("idUser") String idUser){
        UsuarioDTO user = usuarioLogic.find(idUser);
        user.getHorarios().remove(id);
        usuarioLogic.update(user);
        horarioLogic.delete(id);
    }
    
    @GET
    @Path("{id}/darHorario")
    public HorarioDTO darHorarios (@PathParam("id") String id){
        return horarioLogic.find(id);
    }
    
    @GET
    @Path("{id}/darHorarioUser")
    public List<String> darHorariosUser (@PathParam("id") String id){
        return usuarioLogic.find(id).getHorarios();
    }
    
    @POST
    @Path("/token")
    public RespuestaDTO obtenerUserToken(UsuarioDTO usuario) {
        RespuestaDTO respuesta = new RespuestaDTO();
        HttpResponse<String> request = null;
        String token = "";
        String apiToken = obtenerAPIToken().getMsg();
        
        try {
            String jsonBody = "{\n" +
            "\"grant_type\":\"http://auth0.com/oauth/grant-type/password-realm\",\n" +
            "\"username\": \""+usuario.getUserName()+"\",\n" +
            "\"password\": \""+usuario.getPassword()+"\",\n" +
            "\"audience\": \"uniandes.edu.co/thermalcomfort\", \n" +
            "\"scope\": \"openid\",\n" +
            "\"client_id\": \"Y_BZT-Tyb4Z09mgbRkkfZrR7GWN1wJ4y\", \n" +
            "\"client_secret\": \"KgCeooHTX37S5LvbD-_FKhyFSr8GuzIcC0NANtB1sJtl8sMv3JcQP6ypCJtvezFH\", \n" +
            "\"realm\": \"Username-Password-Authentication\"\n" +
            "}";
        
            Unirest.setTimeouts(0, 0);
            request = Unirest.post("https://isis2503-jagomez1.auth0.com/oauth/token")
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + apiToken)
                    .body(jsonBody)
                    .asString();
            
            JSONObject body = new JSONObject(request.getBody());
            token = body.getString("id_token");
        } catch(Exception e) { return new RespuestaDTO(e.getMessage()); }
        
        respuesta.setMsg(token);
        return respuesta;
    }
    
    @GET
    @Path("/apiToken")
    public RespuestaDTO obtenerAPIToken() {
        String token = "";
        try {
            HttpResponse<String> response = Unirest.post("https://isis2503-jagomez1.auth0.com/oauth/token")
            .header("content-type", "application/json")
            .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"Y_BZT-Tyb4Z09mgbRkkfZrR7GWN1wJ4y\",\"client_secret\": \"KgCeooHTX37S5LvbD-_FKhyFSr8GuzIcC0NANtB1sJtl8sMv3JcQP6ypCJtvezFH\",\"audience\": \"https://isis2503-jagomez1.auth0.com/api/v2/\"}").asString();
            String body = response.getBody();
            if(body.contains(DENIED)) {
                token = DENIED;
            } else {
                token = body.split("\"")[3];
            }
            
        } catch(Exception e) {}
        RespuestaDTO respuesta = new RespuestaDTO();
        respuesta.setMsg(token);
        return respuesta;
    }
    
    @GET
    @Path("/authToken")
    public RespuestaDTO obtenerAuthToken() {
        String token = "";
        try {
            HttpResponse<String> response = Unirest.post("https://isis2503-jagomez1.auth0.com/oauth/token")
            .header("content-type", "application/json")
            .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"Y_BZT-Tyb4Z09mgbRkkfZrR7GWN1wJ4y\",\"client_secret\": \"KgCeooHTX37S5LvbD-_FKhyFSr8GuzIcC0NANtB1sJtl8sMv3JcQP6ypCJtvezFH\",\"audience\": \"urn:auth0-authz-api\"}").asString();
            String body = response.getBody();
            if(body.contains(DENIED)) {
                token = DENIED;
            } else {
                token = body.split("\"")[3];
            }
            
        } catch(Exception e) {}
        RespuestaDTO respuesta = new RespuestaDTO();
        respuesta.setMsg(token);
        return respuesta;
    }
    
    @POST
    @Path("/crear")
    public UsuarioDTO crearUsuario(UsuarioDTO usuario) throws Exception{
       return usuarioLogic.add(usuario);
    }
    
    @GET
    @Path("/dar")
    public List<UsuarioDTO> darusuarios(){
        return usuarioLogic.all();
    }
    
    @POST
    @Path("/registrar")
    public RespuestaDTO registrarUsuario(UsuarioDTO usuario) throws Exception{
        HttpResponse<String> request = null;
        RespuestaDTO respuesta = new RespuestaDTO();
        String apiToken = obtenerAPIToken().getMsg();
        String authToken = obtenerAuthToken().getMsg();
        String userID = "";
        String groupID = "";
        
        String userBody = "{" +
            "  \"connection\": \"Username-Password-Authentication\",\n" +
            "  \"email\": \"" + usuario.getEmail() + "\",\n" +
            "  \"username\": \"" + usuario.getUserName() + "\",\n" +
            "  \"password\": \"" + usuario.getPassword() + "\",\n" +
            "  \"user_metadata\": {},\n" +
            "  \"email_verified\": false,\n" +
            "  \"app_metadata\": {}\n" +
            "}";
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.post("https://"+ DOMAIN +"/api/v2/users")
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + apiToken)
                    .body(userBody)
                    .asString();
            
            JSONObject body = new JSONObject(request.getBody());
            userID = body.getString("user_id");
        } catch(Exception e) { return new RespuestaDTO(e.getMessage()); }
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.get(EXTENSION_URL + "/groups")
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + authToken)
                    .asString();
            JSONObject body = new JSONObject(request.getBody());
            
            JSONArray grupos = body.getJSONArray("groups");
            int cant = grupos.length();
            for(int i = 0; i < cant; i++) {
                JSONObject grupo = grupos.getJSONObject(i);
                if(grupo.getString("name").equals(usuario.getGrupo())) {
                    groupID = grupo.getString("_id"); break;
                }
            }
            
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.patch(EXTENSION_URL + "/groups/" + groupID + "/members")
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + authToken)
                    .body("[\"" + userID + "\"]")
                    .asString();
        } catch(Exception e) { return new RespuestaDTO("No se pudo asociar un grupo al usuario."); }
        usuarioLogic.add(usuario);
        respuesta.setMsg("El usuario se registro exitosamente");
        return respuesta;
    }
    
    @POST
    @Path("/grupo")
    public RespuestaDTO asignarGrupoUsuario(UsuarioDTO usuario) {
        HttpResponse<String> request = null;
        RespuestaDTO respuesta = new RespuestaDTO();
        String apiToken = obtenerAPIToken().getMsg();
        String authToken = obtenerAuthToken().getMsg();
        String userID = "";
        String groupID = "";
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.get("https://"+ DOMAIN +"/api/v2/users-by-email?email=" + usuario.getEmail().toLowerCase())
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + apiToken)
                    .asString();
            
            JSONObject body = new JSONArray(request.getBody()).getJSONObject(0);
            userID = body.getString("user_id");
        } catch(Exception e) { return new RespuestaDTO(e.getMessage()); }
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.get(EXTENSION_URL + "/groups")
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + authToken)
                    .asString();
            
            JSONObject body = new JSONObject(request.getBody());
            
            JSONArray grupos = body.getJSONArray("groups");
            int cant = grupos.length();
            for(int i = 0; i < cant; i++) {
                JSONObject grupo = grupos.getJSONObject(i);
                String temp_id = grupo.getString("_id");
                if(grupo.getString("name").equals(usuario.getGrupo())) {
                    groupID = temp_id;
                } else {
                    Unirest.setTimeouts(TIME_OUT, TIME_OUT);
                    request = Unirest.delete(EXTENSION_URL + "/groups/" + temp_id + "/members")
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + authToken)
                    .body("[\"" + userID + "\"]")
                    .asString();
                }
            }
            
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.patch(EXTENSION_URL + "/groups/" + groupID + "/members")
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + authToken)
                    .body("[\"" + userID + "\"]")
                    .asString();
            
        } catch(Exception e) { return new RespuestaDTO("No se pudo asociar un grupo al usuario."); }
        
        respuesta.setMsg("Se le asigno correctamente el nuevo grupo al usuario.");
        return respuesta;
    }
    
    @DELETE
    @Path("/eliminar/{email}")
    public RespuestaDTO eliminarUsuario(@PathParam("email") String email) {
        HttpResponse<String> request = null;
        RespuestaDTO respuesta = new RespuestaDTO();
        String apiToken = obtenerAPIToken().getMsg();
        String userID = "";
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.get("https://"+ DOMAIN +"/api/v2/users-by-email?email=" + email.toLowerCase())
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + apiToken)
                    .asString();
            
            JSONObject body = new JSONArray(request.getBody()).getJSONObject(0);
            userID = body.getString("user_id");
        } catch(Exception e) { return new RespuestaDTO(e.getMessage()); }
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.delete("https://"+ DOMAIN +"/api/v2/users/" + URLEncoder.encode(userID, "UTF-8"))
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + apiToken)
                    .asString();
        } catch(Exception e) { return new RespuestaDTO(e.getMessage()); }
        
        respuesta.setMsg("Se elimino el usuario con correo " + email + " correctamente.");
        return respuesta;
    }
    
    @POST
    @Path("/clave")
    public RespuestaDTO asignarClaveUsuario(UsuarioDTO usuario) {
        HttpResponse<String> request = null;
        RespuestaDTO respuesta = new RespuestaDTO();
        String apiToken = obtenerAPIToken().getMsg();
        String userID = "";
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.get("https://"+ DOMAIN +"/api/v2/users-by-email?email=" + usuario.getEmail().toLowerCase())
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + apiToken)
                    .asString();
            
            JSONObject body = new JSONArray(request.getBody()).getJSONObject(0);
            userID = body.getString("user_id");
        } catch(Exception e) { return new RespuestaDTO(e.getMessage()); }
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.patch("https://"+ DOMAIN +"/api/v2/users/" + URLEncoder.encode(userID, "UTF-8"))
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + apiToken)
                    .body("{\"password\":\"" + usuario.getPassword() + "\"}")
                    .asString();
            
        } catch(Exception e) { return new RespuestaDTO("No se pudo cambiar la clave al usuario."); }
        
        respuesta.setMsg("Se le cambio correctamente la clave al usuario.");
        return respuesta;
    }
    
    @POST
    @Path("/username")
    public RespuestaDTO asignarNombreUsuario(UsuarioDTO usuario) {
        HttpResponse<String> request = null;
        RespuestaDTO respuesta = new RespuestaDTO();
        String apiToken = obtenerAPIToken().getMsg();
        String userID = "";
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.get("https://"+ DOMAIN +"/api/v2/users-by-email?email=" + usuario.getEmail().toLowerCase())
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + apiToken)
                    .asString();
            
            JSONObject body = new JSONArray(request.getBody()).getJSONObject(0);
            userID = body.getString("user_id");
        } catch(Exception e) { return new RespuestaDTO(e.getMessage()); }
        
        try {
            Unirest.setTimeouts(TIME_OUT, TIME_OUT);
            request = Unirest.patch("https://"+ DOMAIN +"/api/v2/users/" + URLEncoder.encode(userID, "UTF-8"))
                    .header("content-type", "application/json")
                    .header("Authorization","Bearer " + apiToken)
                    .body("{\"username\":\"" + usuario.getUserName()+ "\"}")
                    .asString();
            
        } catch(Exception e) { return new RespuestaDTO("No se pudo cambiar el username del usuario."); }
        
        respuesta.setMsg("Se le cambio correctamente el username al usuario.");
        return respuesta;
    }
}
