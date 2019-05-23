/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.AdministradorEntity;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link AdministradorDTO} para manejar las relaciones entre
 * AdministradorDTO y otros DTOs. Para el contenido de una administrador ir a la
 * documentación de {@link AdministradorDTO}
 * @author g.cubillosb
 */
public class AdministradorDetailDTO extends AdministradorDTO implements Serializable 
{
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Lista de tipo GrupoDeInteresDTO contiene los grupos que están asociados con
     * esta administrador.
     */
    private List<GrupoDeInteresDTO> gruposDeInteres;
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    /**
     * Constructor vacío
     */
    public AdministradorDetailDTO () 
    {
        super();
    }

    /**
     * Crea un objeto AdministradorDetailDTO a partir de un objeto AdministradorEntity
     * incluyendo los atributos de AdministradorDTO.
     *
     * @param administradorEntity Entidad AdministradorEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public AdministradorDetailDTO(AdministradorEntity administradorEntity) {
        super(administradorEntity);
    }
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------

    /**
     * Convierte un objeto AdministradorDetailDTO a AdministradorEntity incluyendo los
     * atributos de AdministradorDTO.
     *
     * @return Nueva objeto AdministradorEntity.
     *
     */
    @Override
    public AdministradorEntity toEntity() 
    {
        AdministradorEntity administradorEntity = super.toEntity();
        if (getGruposDeInteres()!= null) {
            List<GrupoDeInteresEntity> gruposDeInteresEntity = new ArrayList<>();
            for (GrupoDeInteresDTO dtoGruposDeInteres : getGruposDeInteres()) {
                gruposDeInteresEntity.add(dtoGruposDeInteres.toEntity());
            }
            administradorEntity.setGruposDeInteres(gruposDeInteresEntity);
        }
        return administradorEntity;
    }

    /**
     * @return the gruposDeInteres
     */
    public List<GrupoDeInteresDTO> getGruposDeInteres() {
        return gruposDeInteres;
    }

    /**
     * @param gruposDeInteres the gruposDeInteres to set
     */
    public void setGruposDeInteres(List<GrupoDeInteresDTO> gruposDeInteres) {
        this.gruposDeInteres = gruposDeInteres;
    }    
}
