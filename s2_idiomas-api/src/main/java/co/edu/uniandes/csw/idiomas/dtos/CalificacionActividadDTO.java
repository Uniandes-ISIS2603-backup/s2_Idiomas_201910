/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.CalificacionActividadEntity;
import java.io.Serializable;

/**
 *
 * @author jdruedaa
 */
public class CalificacionActividadDTO extends CalificacionDTO implements Serializable {

    public CalificacionActividadDTO()
    {
        super();
    }
    
    public CalificacionActividadDTO(CalificacionActividadEntity cal)
    {
        super(cal);
    }

    @Override
    public CalificacionActividadEntity toEntity()
    {
        CalificacionActividadEntity cal = new CalificacionActividadEntity();
        cal.setId(this.id);
        cal.setCalificacion(this.calificacion);
        cal.setMensaje(this.mensaje);
        return cal;
    }
    
}
