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

import java.util.ArrayList;
import java.util.List;
import static co.edu.uniandes.isis2503.sistemaseguridad.model.dto.converter.UsuarioConverter.CONVERTER;
import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IUsuarioLogic;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.HorarioDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.UsuarioDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.UsuarioPersistence;

/**
 *
 * @author ja.gomez1
 */
public class UsuarioLogic implements IUsuarioLogic {
    
    private final UsuarioPersistence persistence;
    private final ResidenciaLogic residenciaLogic;
    private final HorarioLogic horarioLogic;

    public UsuarioLogic() {
        this.persistence = new UsuarioPersistence();
        this.residenciaLogic = new ResidenciaLogic();
        this.horarioLogic = new HorarioLogic();
    }

    @Override
    public UsuarioDTO add(UsuarioDTO dto) throws Exception
    {
        if (dto.getEmail()== null) {
            throw new Exception();   
        }
        UsuarioDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public UsuarioDTO update(UsuarioDTO dto) {
        UsuarioDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public UsuarioDTO find(String id) {
         return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public List<UsuarioDTO> all() {
         return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
    
    @Override
    public List<AlarmaDTO> findAlarms(String idDueño, String idResidencia) throws Exception
    {
        if (residenciaLogic.find(idResidencia).getPropietario().equals(idDueño))
        {
            throw new Exception();
        }
        
        return residenciaLogic.findAlarms(idResidencia);
    }
    
    @Override
    public List<HorarioDTO> darHorarios(String id){
        List hors = persistence.find(id).getHorarios();
        List <HorarioDTO> horarios = new ArrayList();
        ArrayList yaEstan = new ArrayList();
        for (int i = 0; i < hors.size(); i++){
            if (!yaEstan.contains(Integer.parseInt((String)hors.get(i)))){
              horarios.add(horarioLogic.find((String) hors.get(i)));
              yaEstan.add(Integer.parseInt((String)hors.get(i)));
            }
        }
        return horarios;
    }
    

}
