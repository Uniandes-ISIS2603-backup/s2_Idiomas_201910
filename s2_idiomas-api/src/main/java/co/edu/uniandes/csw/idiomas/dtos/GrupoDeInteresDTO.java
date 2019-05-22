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

import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * GrupoDeInteresDTO Objeto de transferencia de datos de GruposDeInteres. Los
 * DTO contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * @author g.cubillosb
 */
public class GrupoDeInteresDTO implements Serializable {
    
    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------

    /**
     * Atributo que representa el id del grupo.
     */
    private Long id;

    /**
     * Atributo que representa el idioma del grupo.
     */
    private String idioma;

    /**
     * Atributo que representa el nombre del grupo.
     */
    private String nombre;
    
    /**
     * Atributo que representa la descripción del grupo.
     */
    private String descripcion;

    /**
     * Atributo que representa el administrador del grupo.
     */
    private AdministradorDTO administrador;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    /**
     * Constructor vacio
     */
    public GrupoDeInteresDTO() {
    }

    /**
     * Crea un objeto GrupoDeInteresDTO a partir de un objeto
     * GrupoDeInteresEntity.
     *
     * @param grupoDeInteresEntity Entidad GrupoDeInteresEntity desde la cual se
     * va a crear el nuevo objeto.
     *
     */
    public GrupoDeInteresDTO(GrupoDeInteresEntity grupoDeInteresEntity) {
        if (grupoDeInteresEntity != null) {
            this.id = grupoDeInteresEntity.getId();
            this.idioma = grupoDeInteresEntity.getIdioma();
            if (grupoDeInteresEntity.getAdministrador() != null) {
                this.administrador = new AdministradorDTO(grupoDeInteresEntity.getAdministrador());
            } else {
                this.administrador = null;
            }
            this.descripcion = grupoDeInteresEntity.getDescripcion();
            this.nombre = grupoDeInteresEntity.getIdioma();
        }
    }

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Convierte un objeto GrupoDeInteresDTO a GrupoDeInteresEntity.
     *
     * @return Nueva objeto GrupoDeInteresEntity.
     */
    public GrupoDeInteresEntity toEntity() {
        GrupoDeInteresEntity grupoDeInteresEntity = new GrupoDeInteresEntity();
        if (this.getAdministrador() != null)
        {
            grupoDeInteresEntity.setAdministrador(this.getAdministrador().toEntity());
        }
        grupoDeInteresEntity.setDescripcion(this.getDescripcion());
        grupoDeInteresEntity.setId(this.getId());
        grupoDeInteresEntity.setIdioma(this.getIdioma());
        grupoDeInteresEntity.setNombre(this.getNombre());
        return grupoDeInteresEntity;
    }

    /**
     * Obtiene el atributo id.
     *
     * @return atributo id.
     *
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     *
     * @param id nuevo valor del atributo
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the administrador
     */
    public AdministradorDTO getAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the administrador to set
     */
    public void setAdministrador(AdministradorDTO administrador) {
        this.administrador = administrador;
    }
    
    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
