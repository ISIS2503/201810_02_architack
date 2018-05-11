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

import static co.edu.uniandes.isis2503.sistemaseguridad.model.dto.converter.UnidadResidencialConverter.CONVERTER;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.UnidadResidencialDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.UnidadResidencialPersistence;
import java.util.List;
import java.util.UUID;
import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IUnidadResidencialLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.AlarmaPersistence;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.CerraduraPersistence;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.HubPersistence;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.ResidenciaPersistence;
import java.util.ArrayList;

/**
 *
 * @author ca.mendoza968
 */
public class UnidadResidenciaLogic implements IUnidadResidencialLogic{
    
    private final UnidadResidencialPersistence persistence;
    private final ResidenciaLogic residenciaLogic;

    public UnidadResidenciaLogic() {
        this.persistence = new UnidadResidencialPersistence();
        this.residenciaLogic = new ResidenciaLogic();
    }

    @Override
    public UnidadResidencialDTO add(UnidadResidencialDTO dto) {
         if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        UnidadResidencialDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public UnidadResidencialDTO update(UnidadResidencialDTO dto) {
        UnidadResidencialDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public UnidadResidencialDTO find(String nombre) {
        return CONVERTER.entityToDto(persistence.find(nombre));
    }

    @Override
    public List<UnidadResidencialDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String nombre) {
        return persistence.delete(nombre);
    }
    
    @Override
    public List<AlarmaDTO> findAlarms(String id) {
        UnidadResidencialDTO unidadR = this.find(id);
        List<String> residencias = unidadR.getResidencias();
        
        List<AlarmaDTO> lista = new ArrayList<>();
        List<String> residenciasBuscadas = new ArrayList<>();
        for(String idResidencia : residencias) {
            if(!residenciasBuscadas.contains(idResidencia)) {
                residenciasBuscadas.add(idResidencia);
                lista.addAll(residenciaLogic.findAlarms(idResidencia));
            }
        }
        
        return lista;
    }
    
    public List<AlarmaDTO> findAlarmasBarrio(String barrio) {
        List<AlarmaDTO> lista = new ArrayList<>();
        
        List<UnidadResidencialDTO> urList = CONVERTER.listEntitiesToListDTOs(persistence.all());
        for(UnidadResidencialDTO ur : urList) {
            if(barrio.equals(ur.getBarrio())) {
                String id = ur.getId();
                lista.addAll(findAlarms(id));
            }
            
        }
        
        return lista; 
    }

    @Override
    public List<AlarmaDTO> findAlarmsBarrio(String barrio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
