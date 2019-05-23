/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.entities;

import co.edu.uniandes.csw.idiomas.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;  
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author se.gamboa
 */
@Entity
public class ComentarioEntity extends BaseEntity implements Serializable {

    private String texto;
    private String titulo;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;

    @PodamExclude
    @ManyToOne
    private PersonaEntity persona;

    
    @PodamExclude
    @ManyToOne
    private ActividadEntity actividad;
    
    /**
     * Atributo que representa los comentarios de la actividad.
     */
    @PodamExclude
    @ManyToOne
    private GrupoDeInteresEntity grupoDeInteres;
    
    /**
     * Constructor vacío de ComentarioEntity.
     */
    public ComentarioEntity() {

    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    /**
     * @return the actividad
     */
    public ActividadEntity getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(ActividadEntity actividad) {
        this.actividad = actividad;
    }

    /**
     * @return the grupoDeInteres
     */
    public GrupoDeInteresEntity getGrupoDeInteres() {
        return grupoDeInteres;
    }

    /**
     * @param grupoDeInteres the grupoDeInteres to set
     */
    public void setGrupoDeInteres(GrupoDeInteresEntity grupoDeInteres) {
        this.grupoDeInteres = grupoDeInteres;
    }

    /**
     * @return the persona
     */
    public PersonaEntity getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
    }
}