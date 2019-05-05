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
@DiscriminatorValue("AD")
public class CalificacionCoordinadorEntity extends CalificacionEntity implements Serializable{
    
    
    /**
     * Atributo que representa los comentarios de la Coordinador.
     */
    @PodamExclude
    @ManyToOne
    private CoordinadorEntity coordinador;
    
    /**
     * Constructor vacío de comentarioCoordinadorEntity.
     */
    public CalificacionCoordinadorEntity()
    {
        
    }

    /**
     * @return the Coordinador
     */
    public CoordinadorEntity getCoordinador() {
        return coordinador;
    }

    /**
     * @param coordinador the Coordinador to set
     */
    public void setCoordinador(CoordinadorEntity coordinador) {
        this.coordinador = coordinador;
    }
}