/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un Usuario en la persistencia y permite su
 * serialización.
 * @author g.cubillosb
 */
@Entity
@DiscriminatorValue("U")
public class UsuarioEntity extends PersonaEntity implements Serializable{
    
    // -------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------
    
    /**
     * Atributo que representa los actividades asociados con el usuario.
     */
    @PodamExclude
    @ManyToMany(mappedBy = "usuarios", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ActividadEntity> actividades;
    
    /**
     * Atributo que representa los grupos de interés asociados con el usuario.
     */
    @PodamExclude
    @ManyToMany(mappedBy = "usuarios", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<GrupoDeInteresEntity> gruposDeInteres;
    
    // ------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------
    
    // ------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------

    /**
     * @return the actividades
     */
    public List<ActividadEntity> getActividades() {
        return actividades;
    }

    /**
     * @param actividades the actividades to set
     */
    public void setActividades(List<ActividadEntity> actividades) {
        this.actividades = actividades;
    }

    /**
     * @return the gruposDeInteres
     */
    public List<GrupoDeInteresEntity> getGruposDeInteres() {
        return gruposDeInteres;
    }

    /**
     * @param gruposDeInteres the gruposDeInteres to set
     */
    public void setGruposDeInteres(List<GrupoDeInteresEntity> gruposDeInteres) {
        this.gruposDeInteres = gruposDeInteres;
    }
    
    /**
     * Equals de la clase
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        PersonaEntity fobj = (PersonaEntity) obj;
        return this.getNombre().equals(fobj.getNombre())
                && this.getContrasenia().equals(fobj.getContrasenia());
    }
}