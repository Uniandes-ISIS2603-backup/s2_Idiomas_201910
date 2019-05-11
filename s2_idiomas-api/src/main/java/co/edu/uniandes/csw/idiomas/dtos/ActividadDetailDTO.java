/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link ActividadDTO} para manejar las relaciones entre
 * ActividadDTO y otros DTOs. Para el contenido de una actividad ir a la
 * documentación de {@link ActividadDTO}
 * @actividad g.cubillosb
 */
public class ActividadDetailDTO extends ActividadDTO implements Serializable {
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Lista de tipo UsuarioDTO contiene los usuarios que están asociados con
     * esta actividad.
     */
    private List<UsuarioDTO> asistentes;
    
    /**
     * Lista de tipo ComentarioDTO contiene los comentarios que están
     * asociados con esta actividad.
     */
    private List<ComentarioDTO> comentarioA;
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    /**
     * Constructor vacío
     */
    public ActividadDetailDTO () 
    {
        super();
    }

    /**
     * Crea un objeto ActividadDetailDTO a partir de un objeto ActividadEntity
     * incluyendo los atributos de ActividadDTO.
     *
     * @param actividadEntity Entidad ActividadEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public ActividadDetailDTO(ActividadEntity actividadEntity) {
        super(actividadEntity);
        if (actividadEntity.getAsistentes() != null) {
            asistentes = new ArrayList<>();
            for (UsuarioEntity entityAsistentes : actividadEntity.getAsistentes())
            {
                asistentes.add(new UsuarioDTO(entityAsistentes));
            }
        }
        if (actividadEntity.getComentarios() != null)
        {
            comentarioA = new ArrayList();
            for (ComentarioEntity entityComentarios : actividadEntity.getComentarios())
            {
                comentarioA.add(new ComentarioDTO(entityComentarios));
            }
        }
    }
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------

    /**
     * Convierte un objeto ActividadDetailDTO a ActividadEntity incluyendo los
     * atributos de ActividadDTO.
     *
     * @return Nueva objeto ActividadEntity.
     *
     */
    @Override
    public ActividadEntity toEntity() {
        ActividadEntity actividadEntity = super.toEntity();
        if (asistentes != null) {
            List<UsuarioEntity> usuarioEntity = new ArrayList<>();
            for (UsuarioDTO dtoUsuario : asistentes) {
                usuarioEntity.add(dtoUsuario.toEntity());
            }
            actividadEntity.setAsistentes(usuarioEntity);
        }
        if (comentarioA != null) {
            List<ComentarioEntity> comentariosEntity = new ArrayList<>();
            for (ComentarioDTO dtoComentario : comentarioA) {
                comentariosEntity.add(dtoComentario.toEntity());
            }
            actividadEntity.setComentarios(comentariosEntity);
        }
        return actividadEntity;
    }
    
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
    public List<ComentarioDTO> getComentarioA() {
        return comentarioA;
    }

    /**
     * @param comentarioA the comentarioA to set
     */
    public void setComentarioA(List<ComentarioDTO> comentarioA) {
        this.comentarioA = comentarioA;
    }
}
