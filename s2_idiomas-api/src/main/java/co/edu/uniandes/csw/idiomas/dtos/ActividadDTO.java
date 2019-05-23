/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import java.io.Serializable;
import java.util.Date;

/**
 * ActividadDTO Objeto de transferencia de datos de la actividad. Los DTO contienen
 * las representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 * @author g.cubillosb
 */
public class ActividadDTO implements Serializable{
    
    // -----------------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------------
    
    /**
     * Atributo que representa el Id de la actividad
     */
    protected Long id;
    
    /**
     * Atributo que representa el nombre de la actividad.
     */
    protected String nombre;
    
    /**
     * Atributo que contiene la fecha.
     */
    protected Date fecha;
    
    /**
     * Atributo que contiene la descripcion de la actividad.
     */
    protected String descripcion;
    
    /**
     * Atributo que contiene la motivacion de la actvidad
     */
    protected String motivacion;
    
    /**
     * Atributo que contiene el tipo
     */
    protected Character pTipo;
    
    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------
    
    /**
     * Constructor vacio de una actividad
     */
    public ActividadDTO ()
    {
        
    }
    
    /**
     * Convertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param pActividadEntity: Es la entidad que se va a convertir a DTO
     */
    public ActividadDTO(ActividadEntity pActividadEntity) 
    {
        if (pActividadEntity != null) 
        {
            this.id = pActividadEntity.getId();
            this.descripcion = pActividadEntity.getDescripcion();
            this.fecha = pActividadEntity.getFecha();
            this.motivacion = pActividadEntity.getMotivacion();
            this.nombre = pActividadEntity.getNombre();
            this.pTipo = pActividadEntity.getSubTypeId();
        }
    }
            
    
    // ----------------------------------------------------------------------
    // Metodos
    // ----------------------------------------------------------------------

    /**
     * Convierte un objeto ActividadDTO a ActividadEntity.
     *
     * @return Nueva objeto ActividadEntity.
     *
     */
    public ActividadEntity toEntity() {
        ActividadEntity actividadEntity = new ActividadEntity();
        actividadEntity.setId(this.getId());
        actividadEntity.setDescripcion(this.getDescripcion());
        actividadEntity.setFecha(this.getFecha());
        actividadEntity.setMotivacion(this.getMotivacion());
        actividadEntity.setNombre(this.getNombre());
        return actividadEntity;
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
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @return the pTipo
     */
    public Character getpTipo() {
        return pTipo;
    }

    /**
     * @param pTipo the pTipo to set
     */
    public void setpTipo(Character pTipo) {
        this.pTipo = pTipo;
    }

    
}
