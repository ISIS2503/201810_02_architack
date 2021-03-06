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
package co.edu.uniandes.isis2503.sistemaseguridad.logic;

import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IHorarioLogic;
import java.util.ArrayList;
import java.util.List;
import static co.edu.uniandes.isis2503.sistemaseguridad.model.dto.converter.HorarioConverter.CONVERTER;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.HorarioDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.HorarioPersistence;

/**
 *
 * @author ja.gomez1
 */
public class HorarioLogic implements IHorarioLogic {
    
    private final HorarioPersistence persistence;

    public HorarioLogic() {
        this.persistence = new HorarioPersistence();
    }
    
    @Override
    public HorarioDTO add(HorarioDTO dto) throws Exception{
         if (dto.getId()== null) {
            throw new Exception();   
        }
        HorarioDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public HorarioDTO update(HorarioDTO dto) {
        HorarioDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;    
    }

    @Override
    public HorarioDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public List<HorarioDTO> all() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
}
