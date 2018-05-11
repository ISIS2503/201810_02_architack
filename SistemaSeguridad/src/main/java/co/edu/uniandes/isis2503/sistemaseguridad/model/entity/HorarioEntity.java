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
package co.edu.uniandes.isis2503.sistemaseguridad.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ja.gomez1
 */

@Entity
@Table(name = "HORARIO_ENTITY")
public class HorarioEntity implements Serializable{
    
    @Id
    private String id;
    private Date horarioInicio;
    private Date horarioFinal;
    private boolean principal;
    private String clave;
    private int slot;
  
    public HorarioEntity(){
        
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHorarioInicio(Date horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public void setHorarioFinal(Date horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }


    public Date getHorarioInicio() {
        return horarioInicio;
    }

    public Date getHorarioFinal() {
        return horarioFinal;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public String getClave() {
        return clave;
    }

    public int getSlot() {
        return slot;
    }


    public HorarioEntity(String id, Date horarioInicio, Date horarioFinal, boolean principal, String clave, int slot) {
        this.id = id;
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.principal = principal;
        this.clave = clave;
        this.slot = slot;
    }
    
    
}