/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model;

/**
 *
 * @author mdr.leon10
 */
public class MensajeDTO {
    private String idUnidadR;
    private String idResidencia;
    private String idHub;
    private String idCerradura;
    private int tipo;
    private String mensaje;
    private String tiempo;

    public MensajeDTO(String idUnidadR, String idResidencia, String idHub, String idCerradura, int tipo, String mensaje, String tiempo) {
        this.idUnidadR = idUnidadR;
        this.idResidencia = idResidencia;
        this.idHub = idHub;
        this.idCerradura = idCerradura;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.tiempo = tiempo;
    }

    public MensajeDTO() {
    }

    /**
     * @return the idUnidadR
     */
    public String getIdUnidadR() {
        return idUnidadR;
    }

    /**
     * @param idUnidadR the idUnidadR to set
     */
    public void setIdUnidadR(String idUnidadR) {
        this.idUnidadR = idUnidadR;
    }

    /**
     * @return the idResidencia
     */
    public String getIdResidencia() {
        return idResidencia;
    }

    /**
     * @param idResidencia the idResidencia to set
     */
    public void setIdResidencia(String idResidencia) {
        this.idResidencia = idResidencia;
    }

    /**
     * @return the idHub
     */
    public String getIdHub() {
        return idHub;
    }

    /**
     * @param idHub the idHub to set
     */
    public void setIdHub(String idHub) {
        this.idHub = idHub;
    }

    /**
     * @return the idCerradura
     */
    public String getIdCerradura() {
        return idCerradura;
    }

    /**
     * @param idCerradura the idCerradura to set
     */
    public void setIdCerradura(String idCerradura) {
        this.idCerradura = idCerradura;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the tiempo
     */
    public String getTiempo() {
        return tiempo;
    }

    /**
     * @param tiempo the tiempo to set
     */
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
