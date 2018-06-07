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
package co.edu.uniandes.isis2503.sistemaseguridad.interfaces;

import java.util.List;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.UsuarioDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.sistemaseguridad.model.dto.model.HorarioDTO;

/**
 *
 * @author ja.gomez1
 */
public interface IUsuarioLogic {
    
    public UsuarioDTO add(UsuarioDTO dto)throws Exception;
    public UsuarioDTO update(UsuarioDTO dto);
    public UsuarioDTO find(String id);
    public List<UsuarioDTO> all();
    public Boolean delete(String id);
    public List<AlarmaDTO> findAlarms(String idDueño, String idResidencia) throws Exception;
    public List<HorarioDTO> darHorarios(String id);
    public UsuarioDTO asignarResidencia(String idUser, String idResidencia);
}