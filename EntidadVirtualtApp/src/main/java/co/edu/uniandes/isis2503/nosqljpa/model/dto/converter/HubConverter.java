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

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.HubDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.HubEntity;
import java.util.ArrayList;
import java.util.List;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IHubConverter;

/**
 *
 * @author ca.mendoza968
 */
public class HubConverter implements IHubConverter {

    public static IHubConverter CONVERTER = new HubConverter();

    public HubConverter() {
    }

    @Override
    public HubDTO entityToDto(HubEntity entity) {
        if(entity == null) return null;
        HubDTO dto = new HubDTO();
        dto.setId(entity.getId());
        dto.setCerraduras(entity.getCerraduras());
        return dto;
    }

    @Override
    public HubEntity dtoToEntity(HubDTO dto) {
        if(dto == null) return null;
        HubEntity entity = new HubEntity();
        entity.setId(dto.getId());
        entity.setCerraduras(dto.getCerraduras());
  
        return entity;
    }

    @Override
    public List<HubDTO> listEntitiesToListDTOs(List<HubEntity> entities) {
        ArrayList<HubDTO> dtos = new ArrayList<>();
        for (HubEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<HubEntity> listDTOsToListEntities(List<HubDTO> dtos) {
        ArrayList<HubEntity> entities = new ArrayList<>();
        for (HubDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
