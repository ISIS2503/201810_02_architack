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
package co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Id;

/**
 *
 * @author ca.mendoza968
 */
@XmlRootElement
public class UnidadResidencialDTO {
    @Id
    private String id;
    
    private String nombreUnidadResidencial;
    
    private List<String> residencias;
    
    private Boolean activa;
    
    private String barrio;

    public UnidadResidencialDTO() {
        this.residencias = new ArrayList();
        this.activa = true;
    }

    public UnidadResidencialDTO(String id, String name, List<String> residencias, Boolean activa, String barrio) {
        this.id = id;
        this.nombreUnidadResidencial = name;
        this.residencias = residencias;
        this.activa = activa;
        this.barrio = barrio;
    }

    public String getNombreUnidadResidencial() {
        return nombreUnidadResidencial;
    }

    public void setNombreUnidadResidencial(String nombreUnidadResidencial) {
        this.nombreUnidadResidencial = nombreUnidadResidencial;
    }

    public List<String> getResidencias() {
        return residencias;
    }

    public void setResidencias(List<String> residencias) {
        this.residencias = residencias;
    }
    
    public void addResidencia(String id) {
        this.residencias.add(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the activa
     */
    public Boolean getActiva() {
        return activa;
    }

    /**
     * @param activa the activa to set
     */
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    /**
     * @return the barrio
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * @param barrio the barrio to set
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
}
