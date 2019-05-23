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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un Coordinador en la persistencia y permite su
 * serialización.
 * @author g.cubillosb
 */
@Entity
@DiscriminatorValue("A")
public class CoordinadorEntity extends PersonaEntity implements Serializable{
    
    // -------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------
    
    /**
     * Atributo que representa las actividades de un coordinador.
     */
    @PodamExclude
    @ManyToMany(mappedBy = "coordinadores", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ActividadEntity> actividades = new ArrayList<>();
    
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