/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link UsuarioDTO} para manejar las relaciones entre
 * UsuarioDTO y otros DTOs. Para el contenido de una usuario ir a la
 * documentación de {@link UsuarioDTO}
 * @author g.cubillosb
 */
public class UsuarioDetailDTO extends UsuarioDTO implements Serializable 
{
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Lista de tipo ActividadDTO contiene las actividades que están
     * asociados con esta usuario.
     */
    private List<ActividadDTO> actividades;
    
    /**
     * Lista de tipo ActividadDTO contiene las actividades que están
     * asociados con esta usuario.
     */
    private List<GrupoDeInteresDTO> gruposDeInteres;
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    /**
     * Constructor vacío
     */
    public UsuarioDetailDTO () 
    {
        super();
    }

    /**
     * Crea un objeto UsuarioDetailDTO a partir de un objeto UsuarioEntity
     * incluyendo los atributos de UsuarioDTO.
     *
     * @param usuarioEntity Entidad UsuarioEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public UsuarioDetailDTO(UsuarioEntity usuarioEntity) {
        super(usuarioEntity);
        if (usuarioEntity.getActividades() != null)
        {
            actividades = new ArrayList();
            for (ActividadEntity entityActividad : usuarioEntity.getActividades())
            {
                actividades.add(new ActividadDTO(entityActividad));
            }
        }
        if (usuarioEntity.getGruposDeInteres() != null)
        {
            gruposDeInteres = new ArrayList();
            for (GrupoDeInteresEntity entityGrupoDeInteres : usuarioEntity.getGruposDeInteres())
            {
                gruposDeInteres.add(new GrupoDeInteresDTO(entityGrupoDeInteres));
            }
        }
    }
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------

    /**
     * Convierte un objeto UsuarioDetailDTO a UsuarioEntity incluyendo los
     * atributos de UsuarioDTO.
     *
     * @return Nueva objeto UsuarioEntity.
     *
     */
    @Override
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = super.toEntity();
        if (getActividades()!= null) {
            List<ActividadEntity> actividadesEntity = new ArrayList<>();
            for (ActividadDTO dtoActividad : getActividades()) {
                actividadesEntity.add(dtoActividad.toEntity());
            }
            usuarioEntity.setActividades(actividadesEntity);
        }
        if (getGruposDeInteres()!= null) {
            List<GrupoDeInteresEntity> gruposDeInteresEntity = new ArrayList<>();
            for (GrupoDeInteresDTO dtoGruposDeInteres : getGruposDeInteres()) {
                gruposDeInteresEntity.add(dtoGruposDeInteres.toEntity());
            }
            usuarioEntity.setGruposDeInteres(gruposDeInteresEntity);
        }
        return usuarioEntity;
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
