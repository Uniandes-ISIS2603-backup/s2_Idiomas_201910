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
    private ActividadDTO actividad;
    private PersonaDTO autor;
    

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
           // if(entity.getActividad() !=null)
           // {
           //     this.actividad = new ActividadDTO(entity.getActividad());
           // }
           // else
           // {
           //     this.actividad = null;
           // }
           // if(entity.getAutor()!=null){
           //    this.autor=new PersonaDTO(entity.getAutor());
           // }
            
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
        //if(this.actividad!=null){
        //entity.setActividad(this.getActividad().toEntity());
        //}
        //if(this.autor!=null){
        //    entity.setAutor(this.getAutor().toEntity());
        //}
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
/**
    public void setActividad(ActividadDTO actividad){
        this.actividad=actividad;
    }
    
    public ActividadDTO getActividad(){
        return actividad;
    }


    public PersonaDTO getAutor() {
        return autor;
    }


    public void setAutor(PersonaDTO autor) {
        this.autor = autor;
    }
         */
}
