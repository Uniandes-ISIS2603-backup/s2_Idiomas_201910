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
package co.edu.uniandes.csw.idiomas.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un grupoDeInteres en la persistencia y permite su serialización
 *
 * @author g.cubillosb
 */
@Entity
public class GrupoDeInteresEntity extends BaseEntity implements Serializable 
{
    
    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    
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
    @PodamExclude
    @OneToOne
    private AdministradorEntity administrador;

    /**
     * Atributo que representa los usuarios asociados con el grupo de interés.
     */
    @PodamExclude
    @ManyToMany(mappedBy = "gruposDeInteres", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UsuarioEntity> usuarios;
    
    /**
     * Atributo que representa las actividades asociados con el grupo de interés.
     */
    @PodamExclude
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL)
    private List<ActividadEntity> actividades;
    
    /**
     * Atributo que representa los comentarios asociados con el grupo de interés.
     */
    @PodamExclude
    @OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL)
    private List<ComentarioEntity> comentarios;
    
    // ------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------

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

    /**
     * @return the administrador
     */
    public AdministradorEntity getAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the administrador to set
     */
    public void setAdministrador(AdministradorEntity administrador) {
        this.administrador = administrador;
    }

    /**
     * @return the usuarios
     */
    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the actividades
     */
    public List<ActividadEntity> getActividades() {
        return actividades;
    }

    /**
     * @param actividades the actividades to set
     */
    public void setActividades(List<ActividadEntity> actividades) {
        this.actividades = actividades;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }
    
}
