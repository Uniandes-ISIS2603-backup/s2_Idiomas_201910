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
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author j.barbosaj
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeUser", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("P")
public class PersonaEntity extends BaseEntity  implements Serializable
{
    
    // -------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------
    
    /**
     * Atributo que representa el nombre de la actividad.
     */
    private String nombre;

    /**
     * Atributo que representa el tipo de la persona
     */
    @Column(name = "typeUser", insertable = false, updatable = false)
    private Character subTypeId;
    
    /**
     * Atributo que representa la contrasenia de la persona
     */
    private String contrasenia;
    
    /**
     * Atributo que representa los comentarios de la persona.
     */
    @PodamExclude
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<ComentarioEntity> comentarios = new ArrayList<>();
    
    // ------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------
    // ------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the subTypeId
     */
    public Character getSubTypeId() {
        return subTypeId;
    }

    /**
     * @param subTypeId the subTypeId to set
     */
    public void setSubTypeId(Character subTypeId) {
        this.subTypeId = subTypeId;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
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
