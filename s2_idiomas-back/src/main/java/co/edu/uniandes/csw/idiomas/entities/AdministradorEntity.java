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
 * Clase que representa un Administrador en la persistencia y permite su
 * serialización.
 * @author g.cubillosb
 */
@Entity
@DiscriminatorValue("A")
public class AdministradorEntity extends PersonaEntity implements Serializable{
    
    // -------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------
    
    /**
     * Atributo que representa los comentarios de la actividad.
     */
    @PodamExclude
    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    private List<GrupoDeInteresEntity> gruposDeInteres = new ArrayList<>();
    
    // ------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------
    
    // ------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------

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