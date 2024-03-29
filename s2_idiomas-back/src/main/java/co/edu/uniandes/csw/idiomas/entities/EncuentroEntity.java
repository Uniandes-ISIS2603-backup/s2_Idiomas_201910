/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Clase que representa un encuentro en la persistencia y permite su
 * serialización.
 * @author g.cubillosb
 */
@Entity
@DiscriminatorValue("E")
public class EncuentroEntity extends ActividadEntity implements Serializable
{
    
    // -------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------
    
    /**
     * Atributo que representa el lugar del encuentro.
     */
    private String lugar;
    
    /**
     * Atributo que representa el número máximo de asistentes del encuentro.
     */
    private Integer numeroMaxAsistentes;
    
    /**
     * Atributo que modela el estado de aprobación del encuentro.
     */
    private Boolean aprobado;
    
    // ------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------
    
    // ------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------

    /**
     * @return the lugar
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @return the numeroMaxAsistentes
     */
    public Integer getNumeroMaxAsistentes() {
        return numeroMaxAsistentes;
    }

    /**
     * @param numeroMaxAsistentes the numeroMaxAsistentes to set
     */
    public void setNumeroMaxAsistentes(Integer numeroMaxAsistentes) {
        this.numeroMaxAsistentes = numeroMaxAsistentes;
    }

    /**
     * @return the aprobado
     */
    public Boolean getAprobado() {
        return aprobado;
    }

    /**
     * @param aprobado the aprobado to set
     */
    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }
    
    /**
     * Equals de la clase
     */
    @Override
    public boolean equals(Object obj)
    {
        if(!super.equals(obj))
        {
            return false;
        }
        EncuentroEntity fobj = (EncuentroEntity) obj;
        return getAprobado().equals(fobj.getAprobado()) && getLugar().equals(fobj.getLugar())
                && getNumeroMaxAsistentes().equals(fobj.getNumeroMaxAsistentes());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.lugar);
        hash = 31 * hash + Objects.hashCode(this.numeroMaxAsistentes);
        hash = 31 * hash + Objects.hashCode(this.aprobado);
        return hash;
    }
}
