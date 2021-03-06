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
package co.edu.uniandes.isis2503.sistemaseguridad.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ca.mendoza968
 */
@Entity
@Table(name = "ALARMA_ENTITY")
public class AlarmaEntity implements Serializable {
    private static final int PUERTA_ABIERTA = 1;
    private static final int MOVIMIENTO_DETECTADO = 2;
    private static final int INTENTOS_DETECTADOS = 3;
    private static final int BATERIA_CRITICA = 4;

    @Id
    private String id;

    private int tipo;
    
    private String mensaje;
    
    private String tiempo;
    
    private Boolean silenciada;

    public AlarmaEntity() {

    }

    public AlarmaEntity(String id, int tipo, String mensaje, String tiempo, Boolean silenciada) {
        this.id = id;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.tiempo = tiempo;
        this.silenciada = silenciada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * @return the silenciada
     */
    public Boolean getSilenciada() {
        return silenciada;
    }

    /**
     * @param silenciada the silenciada to set
     */
    public void setSilenciada(Boolean silenciada) {
        this.silenciada = silenciada;
    }

    
}
