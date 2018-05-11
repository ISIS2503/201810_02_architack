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
package co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model;

import java.util.Date;

/**
 *
 * @author mdr.leon10
 */
public class HorarioDTO {
    
    private String id;
    private String horarioInicio;
    private String horarioFinal;
    private boolean principal;
    private String clave;
    private int slot;

    public HorarioDTO() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public void setHorarioFinal(String horarioFinal) {
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

    public String getId() {
        return id;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public String getHorarioFinal() {
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

   
}
