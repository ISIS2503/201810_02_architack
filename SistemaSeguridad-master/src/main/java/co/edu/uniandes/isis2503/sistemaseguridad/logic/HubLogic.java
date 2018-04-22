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

import static co.edu.uniandes.isis2503.sistemaseguridad.model.dto.converter.HubConverter.CONVERTER;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.HubDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.HubPersistence;
import java.util.List;
import java.util.UUID;
import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IHubLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.UnidadResidencialDTO;
import java.util.ArrayList;

/**
 *
 * @author ca.mendoza968
 */
public class HubLogic implements IHubLogic {

    private final HubPersistence persistence;
    private final CerraduraLogic cerraduraLogic;

    public HubLogic() {
        this.persistence = new HubPersistence();
        this.cerraduraLogic = new CerraduraLogic();
    }

    @Override
    public HubDTO add(HubDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        HubDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public HubDTO update(HubDTO dto) {
        HubDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public HubDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }
    
    @Override
    public List<HubDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
    
    @Override
    public List<AlarmaDTO> findAlarms(String id) {
        HubDTO hub = this.find(id);
        List<String> cerraduras = hub.getCerraduras();
        
        List<AlarmaDTO> lista = new ArrayList<>();
        List<String> cerradurasBuscadas = new ArrayList<>();
        for(String idCerradura : cerraduras) {
            if(!cerradurasBuscadas.contains(idCerradura)) {
                cerradurasBuscadas.add(idCerradura);
                lista.addAll(cerraduraLogic.findAlarms(idCerradura));
            }
        }
        
        return lista;
    }
}
