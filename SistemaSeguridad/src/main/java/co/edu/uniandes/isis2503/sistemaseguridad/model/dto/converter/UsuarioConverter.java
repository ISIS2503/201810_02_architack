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

import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IUsuarioConverter;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.UsuarioDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.entity.UsuarioEntity;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ja.gomez1
 */
public class UsuarioConverter implements IUsuarioConverter {
    
    public static IUsuarioConverter CONVERTER = new UsuarioConverter();

    public UsuarioConverter() {
    }

    @Override
    public UsuarioDTO entityToDto(UsuarioEntity entity) {
        if(entity == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(entity.getEmail());
        dto.setUserName(entity.getUserName());
        dto.setResidencia(entity.getResidencia());
        dto.setGrupo(entity.getPassword());
        dto.setHorarios(entity.getHorarios());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    @Override
    public UsuarioEntity dtoToEntity(UsuarioDTO dto) {
        if(dto == null) return null;
        UsuarioEntity entity = new UsuarioEntity();
        entity.setEmail(dto.getEmail());
        entity.setUserName(dto.getUserName());
        entity.setResidencia(dto.getResidencia());
        entity.setGrupo(dto.getPassword());
        entity.setHorarios(dto.getHorarios());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    @Override
    public List<UsuarioDTO> listEntitiesToListDTOs(List<UsuarioEntity> entities) {
        ArrayList<UsuarioDTO> dtos = new ArrayList<>();
        for (UsuarioEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<UsuarioEntity> listDTOsToListEntities(List<UsuarioDTO> dtos) {
        ArrayList<UsuarioEntity> entities = new ArrayList<>();
        for (UsuarioDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
    
}
