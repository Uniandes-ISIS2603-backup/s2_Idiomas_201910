package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import java.io.Serializable;

/**
 *
 * @author se.gamboa
 */
public class ComentarioDetailDTO extends ComentarioDTO implements Serializable {

    private PersonaDTO persona;
    
    private ActividadDTO actividad;

    public ComentarioDetailDTO(ComentarioEntity entity) {
        super(entity);
        if (entity.getPersona()!= null) {
            this.persona = new PersonaDTO(entity.getPersona());
        } else {
            this.persona = null;
        }
        if (entity.getActividad() != null) {
            this.actividad = new ActividadDTO(entity.getActividad());
        } else {
            this.actividad = null;
        }

    }

    public ComentarioDetailDTO() {
        super();
    }

    @Override
    public ComentarioEntity toEntity() {
        ComentarioEntity entity = super.toEntity();
        if (getPersona()!= null) {
            entity.setPersona(this.getPersona().toEntity());
        }
        if (getActividad() != null) {
            entity.setActividad(this.getActividad().toEntity());
        }
        return entity;
    }

    

    /**
     * @return the actividad
     */
    public ActividadDTO getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(ActividadDTO actividad) {
        this.actividad = actividad;
    }

    /**
     * @return the persona
     */
    public PersonaDTO getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

}
