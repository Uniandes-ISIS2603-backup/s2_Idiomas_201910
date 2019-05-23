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

import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * UsuarioDTO Objeto de transferencia de datos de Usuarios. Los
 * DTO contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * @author g.cubillosb
 */
public class UsuarioDTO extends PersonaDTO implements Serializable {
    
    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    /**
     * Constructor vacio
     */
    public UsuarioDTO() {
        super();
    }

    /**
     * Crea un objeto UsuarioDTO a partir de un objeto
     * UsuarioEntity.
     *
     * @param usuarioEntity Entidad UsuarioEntity desde la cual se
     * va a crear el nuevo objeto.
     *
     */
    public UsuarioDTO(UsuarioEntity usuarioEntity) {
        if (usuarioEntity != null) 
        {
            this.id = usuarioEntity.getId();
            this.contrasenia = usuarioEntity.getContrasenia();
            this.nombre = usuarioEntity.getNombre();
        }
    }

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Convierte un objeto UsuarioDTO a UsuarioEntity.
     *
     * @return Nueva objeto UsuarioEntity.
     */
    @Override
    public UsuarioEntity toEntity() 
    {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(this.getId());
        usuarioEntity.setContrasenia(this.getContrasenia());
        usuarioEntity.setNombre(this.getNombre());
        return usuarioEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
