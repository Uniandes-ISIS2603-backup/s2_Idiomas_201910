/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.CalificacionCoordinadorEntity;
import java.io.Serializable;

/**
 *
 * @author jdruedaa
 */
public class CalificacionCoordinadorDTO extends CalificacionDTO implements Serializable {

    public CalificacionCoordinadorDTO()
    {
        super();
    }
    
    public CalificacionCoordinadorDTO(CalificacionCoordinadorEntity cal)
    {
        super(cal);
    }

    @Override
    public CalificacionCoordinadorEntity toEntity()
    {
        CalificacionCoordinadorEntity cal = new CalificacionCoordinadorEntity();
        cal.setId(this.id);
        cal.setCalificacion(this.calificacion);
        cal.setMensaje(this.mensaje);
        return cal;
    }
    
    
}
