/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author j.barbosaj
 */
@Entity
public class UsuarioEntity  implements Serializable{
    

    
    private Long contrasenia;
    private String nombre;
    
    @PodamExclude
    @ManyToMany(mappedBy = "usuarios")
    private List<GrupoDeInteresEntity> gruposDeInteres = new ArrayList<>();

    public List<ActividadEntity> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadEntity> actividades) {
        this.actividades = actividades;
    }
    
       
    @PodamExclude
    @ManyToMany
    private List<ActividadEntity> actividades = new ArrayList<>();
 
    
    @PodamExclude
    @ManyToMany
    private List<EstadiaEntity> estadias = new ArrayList<>();    
    
    /**
     * Connstructor vacio de un Entity
     */
    public UsuarioEntity()
    {
        //contructor vacio
    }
    
    /**
     * Retorna la contrasenia de un Entity
     * @return contrasenia la contrseña
     */
    public Long getContrasenia() {
        return contrasenia;
    }
    
    /**
     * Asigna una contrasenia a un Entity
     * @param contrasenia 
     */
    public void setContrasenia(Long contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Retorna el nombre del Entity
     * @return nombre -el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nombre al Entity
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the estadias
     */
    public List<EstadiaEntity> getEstadias() {
        return estadias;
    }

    /**
     * @param estadias the estadias to set
     */
    public void setEstadias(List<EstadiaEntity> estadias) {
        this.estadias = estadias;
    }

    
    
   
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public int hashCode() {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
}
