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
 * @author g.cubillosb
 */
public class ActividadDetailDTO extends ActividadDTO implements Serializable 
{
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Lista de tipo UsuarioDTO contiene los usuarios que están asociados con
     * esta actividad.
     */
    private List<UsuarioDTO> usuarios;
    
    /**
     * Lista de tipo ComentarioDTO contiene los comentarios que están
     * asociados con esta actividad.
     */
    private List<ComentarioDTO> comentarios;
    
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
        if (actividadEntity.getUsuarios()!= null) {
            usuarios = new ArrayList<>();
            for (UsuarioEntity dtoUsuarios : actividadEntity.getUsuarios())
            {
                usuarios.add(new UsuarioDTO(dtoUsuarios));
            }
        }
        if (actividadEntity.getComentarios() != null)
        {
            comentarios = new ArrayList();
            for (ComentarioEntity entityComentarios : actividadEntity.getComentarios())
            {
                comentarios.add(new ComentarioDTO(entityComentarios));
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
        if (getUsuarios() != null) {
            List<UsuarioEntity> usuarioEntity = new ArrayList<>();
            for (UsuarioDTO dtoUsuario : getUsuarios()) {
                usuarioEntity.add(dtoUsuario.toEntity());
            }
            actividadEntity.setUsuarios(usuarioEntity);
        }
        if (getComentarios() != null) {
            List<ComentarioEntity> comentariosEntity = new ArrayList<>();
            for (ComentarioDTO dtoComentario : getComentarios()) {
                comentariosEntity.add(dtoComentario.toEntity());
            }
            actividadEntity.setComentarios(comentariosEntity);
        }
        return actividadEntity;
    }

    /**
     * @return the usuarios
     */
    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }
    
    
}