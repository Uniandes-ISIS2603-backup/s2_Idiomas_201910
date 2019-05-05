/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.CalificacionGrupoEntity;
import java.io.Serializable;


/**
 *
 * @author jdruedaa
 */
public class CalificacionGrupoDTO extends CalificacionDTO implements Serializable {

    public CalificacionGrupoDTO()
    {
        super();
    }
    
    public CalificacionGrupoDTO(CalificacionGrupoEntity cal)
    {
        super(cal);
    }

    @Override
    public CalificacionGrupoEntity toEntity()
    {
        CalificacionGrupoEntity cal = new CalificacionGrupoEntity();
        cal.setId(this.id);
        cal.setCalificacion(this.calificacion);
        cal.setMensaje(this.mensaje);
        return cal;
    }
    
    
}
