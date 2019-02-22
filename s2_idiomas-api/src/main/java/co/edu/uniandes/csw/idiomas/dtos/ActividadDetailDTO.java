/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import java.util.List;

/**
 *
 * @author g.cubillosb
 */
public class ActividadDetailDTO extends ActividadDTO{
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Lista de tipo UsuarioDTO contiene los usuarios que están asociados con
     * esta actividad.
     */
    private List<UsuarioDTO> asistentes;
    
    /**
     * Lista de tipo ComentarioActividadDTO contiene los comentarios que están
     * asociados con esta actividad.
     */
    private List<ComentarioActividadDTO> comentarioA;
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    /**
     * Constructor vacío
     */
    public ActividadDetailDTO () 
    {
        
    }
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------

    /**
     * @return the asistentes
     */
    public List<UsuarioDTO> getAsistentes() {
        return asistentes;
    }

    /**
     * @param asistentes the asistentes to set
     */
    public void setAsistentes(List<UsuarioDTO> asistentes) {
        this.asistentes = asistentes;
    }

    /**
     * @return the comentarioA
     */
    public List<ComentarioActividadDTO> getComentarioA() {
        return comentarioA;
    }

    /**
     * @param comentarioA the comentarioA to set
     */
    public void setComentarioA(List<ComentarioActividadDTO> comentarioA) {
        this.comentarioA = comentarioA;
    }
    
}