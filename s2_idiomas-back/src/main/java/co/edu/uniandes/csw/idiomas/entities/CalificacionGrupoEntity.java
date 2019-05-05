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
@DiscriminatorValue("G")
public class CalificacionGrupoEntity extends CalificacionEntity implements Serializable{
    
    /**
     * Atributo que representa los comentarios de la Grupo.
     */
    @PodamExclude
    @ManyToOne
    private GrupoDeInteresEntity grupo;
    
    /**
     * Constructor vacío de comentarioGrupoEntity.
     */
    public CalificacionGrupoEntity()
    {
        
    }

    /**
     * @return the grupo
     */
    public GrupoDeInteresEntity getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(GrupoDeInteresEntity grupo) {
        this.grupo = grupo;
    }
}