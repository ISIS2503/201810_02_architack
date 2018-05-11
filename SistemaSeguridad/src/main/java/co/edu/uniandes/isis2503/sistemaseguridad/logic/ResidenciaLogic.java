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
package co.edu.uniandes.isis2503.sistemaseguridad.logic;

import static co.edu.uniandes.isis2503.sistemaseguridad.model.dto.converter.ResidenciaConverter.CONVERTER;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.ResidenciaDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.ResidenciaPersistence;
import java.util.List;
import java.util.UUID;
import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IResidenciaLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.AlarmaDTO;
import java.util.ArrayList;

/**
 *
 * @author ca.mendoza968
 */
public class ResidenciaLogic implements IResidenciaLogic {
    private final ResidenciaPersistence persistence;
    private final HubLogic hubLogic;
    private final AlarmaLogic alarmaLogic;

    public ResidenciaLogic() {
        this.persistence = new ResidenciaPersistence();
        this.hubLogic = new HubLogic();
        this.alarmaLogic = new AlarmaLogic();
    }

    @Override
    public ResidenciaDTO add(ResidenciaDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        ResidenciaDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public ResidenciaDTO update(ResidenciaDTO dto) {
        ResidenciaDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public ResidenciaDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public List<ResidenciaDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
    
    @Override
    public List<AlarmaDTO> findAlarms(String id) {
        ResidenciaDTO residencia = this.find(id);
        List<String> hubs = residencia.getHubs();
        
        List<AlarmaDTO> lista = new ArrayList<>();
        List<String> hubsBuscados = new ArrayList<>();
        for(String idHub : hubs) {
            if(!hubsBuscados.contains(idHub)) {
                hubsBuscados.add(idHub);
                lista.addAll(hubLogic.findAlarms(idHub));
            }
        }
        
        return lista;
    }
    
    public List<AlarmaDTO> findAlarmsByMonth (String id)
    {
        return alarmaLogic.findAlarmsByMonth(findAlarms(id));
    }
}
