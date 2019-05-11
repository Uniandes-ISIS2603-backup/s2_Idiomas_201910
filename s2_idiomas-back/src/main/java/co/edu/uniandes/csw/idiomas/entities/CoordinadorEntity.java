/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
public class CoordinadorEntity  implements Serializable
{
    Long contrasenia;
    String nombre;    
    
    @PodamExclude
    @ManyToMany(mappedBy = "coordinadores", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ActividadEntity> actividadesCoordinadas = new ArrayList<>();
    
    /**
     * Connstructor vacio de un Entity
     */
    public CoordinadorEntity()
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

    public List<ActividadEntity> getActividadesCoordinadas() {
        return actividadesCoordinadas;
    }

    public void setActividadesCoordinadas(List<ActividadEntity> actividadesCoordinadas) {
        this.actividadesCoordinadas = actividadesCoordinadas;
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
