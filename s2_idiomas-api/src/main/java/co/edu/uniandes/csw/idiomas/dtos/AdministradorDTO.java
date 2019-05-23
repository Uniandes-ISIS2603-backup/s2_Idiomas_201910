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

import co.edu.uniandes.csw.idiomas.entities.AdministradorEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * AdministradorDTO Objeto de transferencia de datos de Administradores. Los
 * DTO contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * @author g.cubillosb
 */
public class AdministradorDTO extends PersonaDTO implements Serializable {
    
    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    
    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    /**
     * Constructor vacio
     */
    public AdministradorDTO() {
        super();
    }

    /**
     * Crea un objeto AdministradorDTO a partir de un objeto
     * AdministradorEntity.
     *
     * @param administradorEntity Entidad AdministradorEntity desde la cual se
     * va a crear el nuevo objeto.
     *
     */
    public AdministradorDTO(AdministradorEntity administradorEntity) {
        if (administradorEntity != null) 
        {
            this.id = administradorEntity.getId();
            this.contrasenia = administradorEntity.getContrasenia();
            this.nombre = administradorEntity.getNombre();
        }
    }

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Convierte un objeto AdministradorDTO a AdministradorEntity.
     *
     * @return Nueva objeto AdministradorEntity.
     */
    @Override
    public AdministradorEntity toEntity() 
    {
        AdministradorEntity administradorEntity = new AdministradorEntity();
        administradorEntity.setId(this.getId());
        administradorEntity.setContrasenia(this.getContrasenia());
        administradorEntity.setNombre(this.getNombre());
        return administradorEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
