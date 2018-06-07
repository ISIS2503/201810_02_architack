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
package co.edu.uniandes.isis2503.sistemaseguridad.model.dto.converter;

import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.entity.AlarmaEntity;
import java.util.ArrayList;
import java.util.List;
import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IAlarmaConverter;

/**
 *
 * @author ca.mendoza968
 */
public class AlarmaConverter implements IAlarmaConverter {

    public static IAlarmaConverter CONVERTER = new AlarmaConverter();

    public AlarmaConverter() {
    }

    @Override
    public AlarmaDTO entityToDto(AlarmaEntity entity) {
        if(entity == null) return null;
        AlarmaDTO dto = new AlarmaDTO();
        dto.setId(entity.getId());
        dto.setMensaje(entity.getMensaje());
        dto.setTiempo(entity.getTiempo());
        dto.setTipo(entity.getTipo());
        dto.setSilenciada(entity.getSilenciada());
        
        return dto;
    }

    @Override
    public AlarmaEntity dtoToEntity(AlarmaDTO dto) {
        if(dto == null) return null;
        AlarmaEntity entity = new AlarmaEntity();
        entity.setId(dto.getId());
        entity.setMensaje(dto.getMensaje());
        entity.setTiempo(dto.getTiempo());
        entity.setTipo(dto.getTipo());
        entity.setSilenciada(dto.isSilenciada());
        return entity;
    }

    @Override
    public List<AlarmaDTO> listEntitiesToListDTOs(List<AlarmaEntity> entities) {
        ArrayList<AlarmaDTO> dtos = new ArrayList<>();
        for (AlarmaEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<AlarmaEntity> listDTOsToListEntities(List<AlarmaDTO> dtos) {
        ArrayList<AlarmaEntity> entities = new ArrayList<>();
        for (AlarmaDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
