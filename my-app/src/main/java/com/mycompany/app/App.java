package com.mycompany.app;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App {
    private static final int CANTIDAD = 20;
    private static final String USER_ID = "jc.ruiz3@gmail.com";
    private static final String USER_NAME = "jc.ruiz3";
    private static final String PASSWORD = "jc.ruiz31234";
    private static final long TIME_OUT = 2000;
    private static final String path = "http://localhost:8080/";
    private static final String DENIED = "access_denied";
    
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
      
   public RespuestaDTO obtenerUserToken() {
        RespuestaDTO respuesta = new RespuestaDTO();
        HttpResponse<String> request = null;
        String token = "";
        String apiToken = obtenerAPIToken().getMsg();
        
        try {
            String jsonBody = "{\n" +
            "\"grant_type\":\"http://auth0.com/oauth/grant-type/password-realm\",\n" +
            "\"username\": \""+USER_NAME+"\",\n" +
            "\"password\": \""+PASSWORD+"\",\n" +
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
    
    public Horario converter(JSONObject pHorario) {
        Horario horario = new Horario();
        try {
            horario.setClave(pHorario.getString("clave"));
            horario.setHorarioInicio(pHorario.getString("horarioInicio"));
            horario.setHorarioFinal(pHorario.getString("horarioFinal"));
            horario.setSlot(pHorario.getInt("slot"));
            horario.setPrincipal(pHorario.getBoolean("principal"));
        } catch(Exception e) { horario = null; }
        return horario;
    }
    
    public boolean evaluarHorario(Horario horario, Date currentTime, int slot) {
        if (slot != horario.getSlot()) return false;
          
        int horaIn = Integer.parseInt(horario.getHorarioInicio().split(":")[0]);
        int minutesIn = Integer.parseInt(horario.getHorarioInicio().split(":")[1]);
        int horaFin = Integer.parseInt(horario.getHorarioFinal().split(":")[0]);
        int minutesFin = Integer.parseInt(horario.getHorarioFinal().split(":")[1]);
        
        if (horaIn < currentTime.getHours() && horaFin > currentTime.getHours())
            return true;
        else if ((horaIn == currentTime.getHours() || horaFin == currentTime.getHours()))
         if (minutesIn <= currentTime.getMinutes() && minutesFin >= currentTime.getMinutes())
                return true;
        return false;
    }
    
    public static void main( String[] args )
    {
        App app = new App();
        String[] claves = new String[CANTIDAD];
        
        try {
            HttpResponse response = Unirest.delete(path + "/claves/nosec/eliminarTodos")
                                .header("content-type", "application/json")
                                .asString();
            System.out.println(response.toString());;
        } catch(Exception e) { }
        
        while(true) {
            try {
                Thread.sleep(10000);
            } catch(Exception e) {}
            boolean[] justificaciones = new boolean[CANTIDAD];
            JSONArray json = null;
            ArrayList<Horario> horarios = new ArrayList();
            Horario[] hrs = new Horario[CANTIDAD];
            try {
                Date currentTime = new Date();
                
                Unirest.setTimeouts(TIME_OUT, TIME_OUT);
                HttpResponse<String> response = Unirest.get(path + "usuarios/" + USER_ID + "/darHorariosUser").asString();
                
              
                json = new JSONArray(response.getBody());
                int count = json.length();
               
                for(int i = 0; i < count; i++) {
                    Horario hr = app.converter(json.getJSONObject(i));
                    if(hr != null)
                        horarios.add(hr);
                }
                
                for(int i = 0; i < justificaciones.length; i++) {
                    boolean js = false;
                    for(int j = 0; j < horarios.size(); j++) {
                        Horario hr = horarios.get(j);
                        js = app.evaluarHorario(hr, currentTime, i);
                        if(js) {
                            hrs[i] = hr;
                            break;
                        }
                    }
                    justificaciones[i] = js;
                }
                
                for (int i = 0; i < justificaciones.length; i++) {
                    if(!justificaciones[i]) {
                        String body = "{\n" +
                            "	\"comando\": \"DELETE_PASSWORD\",\n" +
                            "	\"idClave\":"+i+"\n" +
                            "}";
                        response = Unirest.delete(path + "/claves/nosec/eliminar")
                                .header("content-type", "application/json")
                                .body(body)
                                .asString();
                        claves[i] = null;
                    } else {
                        if(claves[i]==null || (claves[i]!=null && !claves[i].equals(hrs[i].getClave()))) {
                            System.out.println("En la justificación " + i + " se cambia de horario");

                            String body = "{\n" +
                                "	\"comando\": \"ADD_PASSWORD\",\n" +
                                "	\"idClave\":"+hrs[i].getSlot()+",\n" +
                                "	\"nuevaClave\":"+hrs[i].getClave()+"\n" +
                                "}";
                            response = Unirest.post(path + "/claves/nosec/actualizar")
                                    .header("content-type", "application/json")
                                    .body(body)
                                    .asString();
                            claves[i] = hrs[i].getClave();
                        }
                    }
                }
            } catch(Exception e) {System.out.print("Error : " + e.getMessage());
            e.printStackTrace();}
        }
    }
}
