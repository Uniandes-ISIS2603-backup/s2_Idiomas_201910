/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.PersonaEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link PersonaDTO} para manejar las relaciones entre
 * PersonaDTO y otros DTOs. Para el contenido de una persona ir a la
 * documentación de {@link PersonaDTO}
 * @author g.cubillosb
 */
public class PersonaDetailDTO extends PersonaDTO implements Serializable 
{
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Lista de tipo ComentarioDTO contiene los comentarios que están
     * asociados con esta persona.
     */
    private List<ComentarioDTO> comentarios;
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    /**
     * Constructor vacío
     */
    public PersonaDetailDTO () 
    {
        super();
    }

    /**
     * Crea un objeto PersonaDetailDTO a partir de un objeto PersonaEntity
     * incluyendo los atributos de PersonaDTO.
     *
     * @param personaEntity Entidad PersonaEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public PersonaDetailDTO(PersonaEntity personaEntity) {
        super(personaEntity);
        if (personaEntity.getComentarios() != null)
        {
            comentarios = new ArrayList();
            for (ComentarioEntity entityComentarios : personaEntity.getComentarios())
            {
                comentarios.add(new ComentarioDTO(entityComentarios));
            }
        }
    }
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------

    /**
     * Convierte un objeto PersonaDetailDTO a PersonaEntity incluyendo los
     * atributos de PersonaDTO.
     *
     * @return Nueva objeto PersonaEntity.
     *
     */
    @Override
    public PersonaEntity toEntity() {
        PersonaEntity personaEntity = super.toEntity();
        if (getComentarios() != null) {
            List<ComentarioEntity> comentariosEntity = new ArrayList<>();
            for (ComentarioDTO dtoComentario : getComentarios()) {
                comentariosEntity.add(dtoComentario.toEntity());
            }
            personaEntity.setComentarios(comentariosEntity);
        }
        return personaEntity;
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
