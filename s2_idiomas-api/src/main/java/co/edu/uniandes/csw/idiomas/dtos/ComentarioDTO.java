package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author se.gamboa
 */
public class ComentarioDTO implements Serializable {

//a
    private String texto;
    private Date fecha;
    private Long id;
    private String titulo;
    

    /**
     * Constructor de ComentarioActividadDTO
     *
     * @param entity recibe la informacion del comentario.
     */
    public ComentarioDTO(ComentarioEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.texto = entity.getTexto();
            this.fecha = entity.getFecha();
            this.titulo = entity.getTitulo();
            
        }
    }

    public ComentarioDTO() {

    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    

    /**
     * Convierte un objeto DTO a una Entidad.
     *
     * @return entidad convertida.
     */
    public ComentarioEntity toEntity() {
        ComentarioEntity entity = new ComentarioEntity();
        entity.setId(this.getId());
        entity.setFecha(this.getFecha());
        entity.setTexto(this.getTexto());
        entity.setTitulo(this.getTitulo());
        return entity;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
