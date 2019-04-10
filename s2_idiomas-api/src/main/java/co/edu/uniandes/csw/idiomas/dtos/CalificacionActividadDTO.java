/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.CalificacionActividadEntity;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author jdruedaa
 */
public class CalificacionActividadDTO implements Serializable {

    private Long id;
    private Integer calificacion;
    private String mensaje;
    
    public CalificacionActividadDTO()
    {
        
    }
    
    public CalificacionActividadDTO(CalificacionActividadEntity cal)
    {
        if(cal != null)
        {
            this.id = cal.getId();
            this.calificacion = cal.getCalificacion();
            this.mensaje = cal.getMensaje();
        }
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
     * @return the calificacion
     */
    public Integer getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
    
    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    @Inject
    public CalificacionActividadEntity toEntity()
    {
        CalificacionActividadEntity cal = new CalificacionActividadEntity();
        cal.setId(id);
        cal.setCalificacion(calificacion);
        cal.setMensaje(mensaje);
        return cal;
    }
    
}
