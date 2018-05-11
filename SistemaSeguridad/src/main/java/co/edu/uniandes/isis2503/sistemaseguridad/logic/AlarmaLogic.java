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

import static co.edu.uniandes.isis2503.sistemaseguridad.model.dto.converter.AlarmaConverter.CONVERTER;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.persistence.AlarmaPersistence;
import java.util.List;
import java.util.UUID;
import co.edu.uniandes.isis2503.sistemaseguridad.interfaces.IAlarmaLogic;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ca.mendoza968
 */
public class AlarmaLogic implements IAlarmaLogic {

    private final AlarmaPersistence persistence;

    public AlarmaLogic() {
        this.persistence = new AlarmaPersistence();
    }

    @Override
    public AlarmaDTO add(AlarmaDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        AlarmaDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public AlarmaDTO update(AlarmaDTO dto) {
        AlarmaDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public AlarmaDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }
    
    @Override
    public AlarmaDTO findCode(String code) {
        return CONVERTER.entityToDto(persistence.findCode(code));
    }

    @Override
    public List<AlarmaDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
    
    public List<AlarmaDTO> findAlarmsByMonth (List<AlarmaDTO> lista){
        List <AlarmaDTO> alarmas = new ArrayList();
        List <AlarmaDTO> alarmasBuscadas = new ArrayList();
        alarmas = lista;
        String pattern = "d-MM-YYYY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String[] partes = date.split("-");
        String month = partes[2];
        int año = Integer.parseInt(partes[3]);
        
        for (AlarmaDTO alarma : alarmas) {
            String s = alarma.getTiempo();
            String[] token = s.split(" ");
            
            if (token[1].equals(month) && Integer.parseInt(token[2])== año)
            {
                alarmasBuscadas.add(alarma);
            } 
        }
      return alarmasBuscadas;  
    }
}
