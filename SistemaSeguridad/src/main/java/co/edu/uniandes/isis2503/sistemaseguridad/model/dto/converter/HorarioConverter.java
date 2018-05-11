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
package co.edu.uniandes.isis2503.sistemaseguridad.model.dto.converter;

import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IHorarioConverter;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.HorarioDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.entity.HorarioEntity;
import java.util.List;

/**
 *
 * @author ja.gomez1
 */
public class HorarioConverter implements IHorarioConverter{
    
    public static IHorarioConverter CONVERTER = new HorarioConverter();

    public HorarioConverter() {
    }
    
    @Override
    public HorarioDTO entityToDto(HorarioEntity entity) {
        if (entity == null) return null;
        HorarioDTO dto = new HorarioDTO();
        dto.setId(entity.getId());
        dto.setClave(entity.getClave());
        dto.setHorarioFinal(entity.getHorarioFinal());
        dto.setHorarioInicio(entity.getHorarioInicio());
        dto.setPrincipal(entity.isPrincipal());
        dto.setSlot(entity.getSlot());
        return dto;
    }

    @Override
    public HorarioEntity dtoToEntity(HorarioDTO dto) {
         if(dto == null) return null;
        HorarioEntity entity = new HorarioEntity();
        entity.setClave(dto.getClave());
        entity.setHorarioFinal(dto.getHorarioFinal());
        entity.setHorarioInicio(dto.getHorarioInicio());
        entity.setId(dto.getId());
        entity.setPrincipal(dto.isPrincipal());
        entity.setSlot(dto.getSlot());
     
        return entity;
    }

    @Override
    public List<HorarioDTO> listEntitiesToListDTOs(List<HorarioEntity> entities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HorarioEntity> listDTOsToListEntities(List<HorarioDTO> dtos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
