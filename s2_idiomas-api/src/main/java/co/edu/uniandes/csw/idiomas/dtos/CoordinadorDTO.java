/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CoordinadorDTO Objeto de transferencia de datos de Coordinadores. Los
 * DTO contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * @author g.cubillosb
 */
public class CoordinadorDTO extends PersonaDTO implements Serializable {
    
    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    
    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    /**
     * Constructor vacio
     */
    public CoordinadorDTO() {
        super();
    }

    /**
     * Crea un objeto CoordinadorDTO a partir de un objeto
     * CoordinadorEntity.
     *
     * @param coordinadorEntity Entidad CoordinadorEntity desde la cual se
     * va a crear el nuevo objeto.
     *
     */
    public CoordinadorDTO(CoordinadorEntity coordinadorEntity) {
        if (coordinadorEntity != null) 
        {
            this.id = coordinadorEntity.getId();
            this.contrasenia = coordinadorEntity.getContrasenia();
            this.nombre = coordinadorEntity.getNombre();
        }
    }

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Convierte un objeto CoordinadorDTO a CoordinadorEntity.
     *
     * @return Nueva objeto CoordinadorEntity.
     */
    @Override
    public CoordinadorEntity toEntity() 
    {
        CoordinadorEntity coordinadorEntity = new CoordinadorEntity();
        coordinadorEntity.setId(this.getId());
        coordinadorEntity.setContrasenia(this.getContrasenia());
        coordinadorEntity.setNombre(this.getNombre());
        return coordinadorEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
