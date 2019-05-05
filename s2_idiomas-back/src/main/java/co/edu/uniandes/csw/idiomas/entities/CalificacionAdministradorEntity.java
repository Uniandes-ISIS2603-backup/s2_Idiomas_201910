/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author jd.ruedaa
 */
@Entity
@DiscriminatorValue("A")
public class CalificacionAdministradorEntity extends CalificacionEntity implements Serializable{
    
    
    /**
     * Atributo que representa los comentarios de la Administrador.
     */
    @PodamExclude
    @ManyToOne
    private AdministradorEntity administrador;
    
    /**
     * Constructor vacío de comentarioAdministradorEntity.
     */
    public CalificacionAdministradorEntity()
    {
        
    }

    /**
     * @return the Administrador
     */
    public AdministradorEntity getAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the Administrador to set
     */
    public void setAdministrador(AdministradorEntity administrador) {
        this.administrador = administrador;
    }
}