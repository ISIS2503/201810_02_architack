/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

/**
 *
 * @author mdr.leon10
 */
public class RespuestaDTO {
    private String msg;

    public RespuestaDTO(String msg) {
        this.msg = msg;
    }

    public RespuestaDTO() {
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
}
