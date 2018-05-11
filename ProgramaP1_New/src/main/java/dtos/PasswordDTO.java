/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author af.lopezf
 */
public class PasswordDTO {
    
    private String comando;
    
    private int idClave;
    
    private int nuevaClave;

    public PasswordDTO() {
    }

    public PasswordDTO(String comando, int idClave, int nuevaClave) {
        this.comando = comando;
        this.idClave = idClave;
        this.nuevaClave = nuevaClave;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public int getIdClave() {
        return idClave;
    }

    public void setIdClave(int idClave) {
        this.idClave = idClave;
    }

    public int getNuevaClave() {
        return nuevaClave;
    }

    public void setNuevaClave(int nuevaClave) {
        this.nuevaClave = nuevaClave;
    }
    
    
    
}
