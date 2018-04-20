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
package co.edu.uniandes.isis2503.nosqljpa.model.dto.converter;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.ResidenciaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.ResidenciaEntity;
import java.util.ArrayList;
import java.util.List;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IResidenciaConverter;

/**
 *
 * @author ca.mendoza968
 */
public class ResidenciaConverter implements IResidenciaConverter {

    public static IResidenciaConverter CONVERTER = new ResidenciaConverter();

    public ResidenciaConverter() {
    }

    @Override
    public ResidenciaDTO entityToDto(ResidenciaEntity entity) {
        if(entity == null) return null;
        ResidenciaDTO dto = new ResidenciaDTO();
        dto.setId(entity.getId());
        dto.setNombreResidencia(entity.getNombreresidencia());
        dto.setPropietario(entity.getPropietario());
        dto.setHubs(entity.getHubs());
        dto.setActiva(entity.getActiva());
        return dto;
    }

    @Override
    public ResidenciaEntity dtoToEntity(ResidenciaDTO dto) {
        if(dto == null) return null;
        ResidenciaEntity entity = new ResidenciaEntity();
        entity.setId(dto.getId());
        entity.setNombreresidencia(dto.getNombreResidencia());
        entity.setPropietario(dto.getPropietario());
        entity.setHubs(dto.getHubs());
        entity.setActiva(dto.getActiva());
        return entity;
    }

    @Override
    public List<ResidenciaDTO> listEntitiesToListDTOs(List<ResidenciaEntity> entities) {
        ArrayList<ResidenciaDTO> dtos = new ArrayList<>();
        for (ResidenciaEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<ResidenciaEntity> listDTOsToListEntities(List<ResidenciaDTO> dtos) {
        ArrayList<ResidenciaEntity> entities = new ArrayList<>();
        for (ResidenciaDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }

}
