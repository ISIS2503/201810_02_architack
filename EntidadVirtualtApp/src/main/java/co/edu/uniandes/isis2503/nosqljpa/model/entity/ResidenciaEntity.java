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
package co.edu.uniandes.isis2503.nosqljpa.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ca.mendoza968
 */
@Entity
@Table(name = "RESIDENCIA_ENTITY")
public class ResidenciaEntity implements Serializable {

    @Id
    private String id;

    private String nombreresidencia;

    private String propietario;

    @ElementCollection
    private List<String> hubs;

    public ResidenciaEntity() {
        hubs = new ArrayList();
    }

    public ResidenciaEntity(String id, String nombre, String propietario, List<String> hubs) {
        this.id = id;
        this.nombreresidencia = nombre;
        this.propietario = propietario;
        this.hubs = hubs;
    }

    public String getNombreresidencia() {
        return nombreresidencia;
    }

    public void setNombreresidencia(String nombreresidencia) {
        this.nombreresidencia = nombreresidencia;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public List<String> getHubs() {
        return hubs;
    }

    public void setHubs(List<String> hubs) {
        this.hubs = hubs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
