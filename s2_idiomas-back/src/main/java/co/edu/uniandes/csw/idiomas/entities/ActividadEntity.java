/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.entities;

import co.edu.uniandes.csw.idiomas.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 * Clase que representa una actividad en la persistencia y permite su
 * serialización.
 *
 * @author g.cubillosb
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeActivity", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("A")
public class ActividadEntity extends BaseEntity implements Serializable {

    // -------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------
    /**
     * Atributo que representa el nombre de la actividad.
     */
    private String nombre;

    @Column(name = "typeActivity", insertable = false, updatable = false)
    private char subTypeId;
    /**
     * Atributo que representa la fecha de la actividad.
     */
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;

    /**
     * Atributo que modela la descripción de la actividad.
     */
    private String descripcion;

    /**
     * Atributo que representa la motivación de la actividad.
     */
    private String motivacion;

    /**
     * Atributo que representa los comentarios de la actividad.
     */
    @PodamExclude
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL)
    private List<ComentarioEntity> comentarios = new ArrayList<>();
    
    /**
     * Atributo que representa los asistentes de la actividad.
     */
    @PodamExclude
    @ManyToMany(mappedBy = "actividades", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UsuarioEntity> asistentes = new ArrayList<>();

    /**
     * Atributo que representa los coordinadores de una actividad.
     */
    @PodamExclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CoordinadorEntity> coordinadores = new ArrayList<>();

    // ------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------
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
     * @return the motivacion
     */
    public String getMotivacion() {
        return motivacion;
    }

    /**
     * @param motivacion the motivacion to set
     */
    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
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
     * @return the asistentes
     */
    public List<UsuarioEntity> getAsistentes() {
        return asistentes;
    }

    /**
     * @param asistentes the asistentes to set
     */
    public void setAsistentes(List<UsuarioEntity> asistentes) {
        this.asistentes = asistentes;
    }

    /**
     * @return the coordinadores
     */
    public List<CoordinadorEntity> getCoordinadores() {
        return coordinadores;
    }

    /**
     * @param coordinadores the coordinadores to set
     */
    public void setCoordinadores(List<CoordinadorEntity> coordinadores) {
        this.coordinadores = coordinadores;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Equals de la clase
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        ActividadEntity fobj = (ActividadEntity) obj;
        return this.getDescripcion().equals(fobj.getDescripcion()) && this.getFecha().equals(fobj.getFecha())
                && this.getMotivacion().equals(fobj.getMotivacion())
                && this.getNombre().equals(fobj.getNombre());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.getNombre());
        hash = 97 * hash + Objects.hashCode(this.getFecha());
        hash = 97 * hash + Objects.hashCode(this.getDescripcion());
        hash = 97 * hash + Objects.hashCode(this.getMotivacion());
        return hash;
    }

    /**
     * @return the subTypeId
     */
    public char getSubTypeId() {
        return subTypeId;
    }

    /**
     * @param subTypeId the subTypeId to set
     */
    public void setSubTypeId(char subTypeId) {
        this.subTypeId = subTypeId;
    }

}
