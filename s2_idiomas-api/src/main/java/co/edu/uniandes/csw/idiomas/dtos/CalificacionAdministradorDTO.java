/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.CalificacionAdministradorEntity;
import java.io.Serializable;


/**
 *
 * @author jdruedaa
 */
public class CalificacionAdministradorDTO extends CalificacionDTO implements Serializable {

    public CalificacionAdministradorDTO()
    {
        super();
    }
    
    public CalificacionAdministradorDTO(CalificacionAdministradorEntity cal)
    {
        super(cal);
    }

    @Override
    public CalificacionAdministradorEntity toEntity()
    {
        CalificacionAdministradorEntity cal = new CalificacionAdministradorEntity();
        cal.setId(this.id);
        cal.setCalificacion(this.calificacion);
        cal.setMensaje(this.mensaje);
        return cal;
    }
    
}
