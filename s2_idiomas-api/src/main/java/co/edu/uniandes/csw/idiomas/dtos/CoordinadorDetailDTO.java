/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link CoordinadorDTO} para manejar las relaciones entre
 * CoordinadorDTO y otros DTOs. Para el contenido de una coordinador ir a la
 * documentación de {@link CoordinadorDTO}
 * @author g.cubillosb
 */
public class CoordinadorDetailDTO extends CoordinadorDTO implements Serializable 
{
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Lista de tipo ActividadDTO contiene las actividades asociadas con
     * esta coordinador.
     */
    private List<ActividadDTO> actividades;
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    /**
     * Constructor vacío
     */
    public CoordinadorDetailDTO () 
    {
        super();
    }

    /**
     * Crea un objeto CoordinadorDetailDTO a partir de un objeto CoordinadorEntity
     * incluyendo los atributos de CoordinadorDTO.
     *
     * @param coordinadorEntity Entidad CoordinadorEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public CoordinadorDetailDTO(CoordinadorEntity coordinadorEntity) {
        super(coordinadorEntity);
    }
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------

    /**
     * Convierte un objeto CoordinadorDetailDTO a CoordinadorEntity incluyendo los
     * atributos de CoordinadorDTO.
     *
     * @return Nueva objeto CoordinadorEntity.
     *
     */
    @Override
    public CoordinadorEntity toEntity() 
    {
        CoordinadorEntity coordinadorEntity = super.toEntity();
        if (getActividades()!= null) {
            List<ActividadEntity> actividadesEntity = new ArrayList<>();
            for (ActividadDTO dtoActividades : getActividades()) {
                actividadesEntity.add(dtoActividades.toEntity());
            }
            coordinadorEntity.setActividades(actividadesEntity);
        }
        return coordinadorEntity;
    }

    /**
     * @return the actividades
     */
    public List<ActividadDTO> getActividades() {
        return actividades;
    }

    /**
     * @param actividades the actividades to set
     */
    public void setActividades(List<ActividadDTO> actividades) {
        this.actividades = actividades;
    }
}
