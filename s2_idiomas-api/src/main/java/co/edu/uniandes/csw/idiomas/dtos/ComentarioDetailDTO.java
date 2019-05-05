package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import java.io.Serializable;

/**
 *
 * @author se.gamboa
 */
public class ComentarioDetailDTO extends ComentarioDTO implements Serializable {

    private PersonaDTO autor;
    private ActividadDTO actividad;
    private CalificacionDTO calificaciones;

    public ComentarioDetailDTO(ComentarioEntity entity) {
        super(entity);
        if (entity.getAutor() != null) {
            this.autor = new PersonaDTO(entity.getAutor());
        } else {
            this.autor = null;
        }
        if (entity.getActividad() != null) {
            this.actividad = new ActividadDTO(entity.getActividad());
        } else {
            this.actividad = null;
        }
        if (entity.getCalificaciones() != null) {
            this.calificaciones = new CalificacionDTO(entity.getCalificaciones());
        } else {
            this.calificaciones = null;
        }

    }

    public ComentarioDetailDTO() {
        super();
    }

    @Override
    public ComentarioEntity toEntity() {
        ComentarioEntity entity = super.toEntity();
        if (getAutor() != null) {
            entity.setAutor(this.getAutor().toEntity());
        }
        if (getActividad() != null) {
            entity.setActividad(this.getActividad().toEntity());
        }
        if (getCalificaciones() != null) {
            entity.setCalificaciones(this.getCalificaciones().toEntity());
        }
        return entity;
    }

    /**
     * @return the autor
     */
    public PersonaDTO getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(PersonaDTO autor) {
        this.autor = autor;
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
     * @return the calificaciones
     */
    public CalificacionDTO getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(CalificacionDTO calificaciones) {
        this.calificaciones = calificaciones;
    }

}
