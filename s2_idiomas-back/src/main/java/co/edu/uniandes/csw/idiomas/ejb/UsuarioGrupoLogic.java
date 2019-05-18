/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.idiomas.ejb;

import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.GrupoDeInteresPersistence;
import co.edu.uniandes.csw.idiomas.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author j.barbosaj 201717575
 */
public class UsuarioGrupoLogic {
    
    // -----------------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------------
    
    /**
     * Logger para las asociaciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(UsuarioGrupoLogic.class.getName());
    
    /**
     * Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private GrupoDeInteresPersistence grupoPersistence;
    
    /**
     * Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private UsuarioPersistence usuarioPersistence;
    
    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    
    /**
     * Asocia un Grupo existente a un Usuario
     *
     * @param usuariosId Identificador de la instancia de Usuario
     * @param gruposId Identificador de la instancia de Grupo
     * @return Instancia de GrupoDeInteresEntity que fue asociada a Usuario
     */
    public GrupoDeInteresEntity addGrupo(Long usuariosId, Long gruposId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un grupo a la usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        GrupoDeInteresEntity grupoEntity = grupoPersistence.find(gruposId);
        usuarioEntity.getGrupos().add(grupoEntity);
        //hay que hablar con luis
//        grupoEntity.getUsuarios.add(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un libro al usuario con id = {0}", usuariosId);
        return grupoPersistence.find(gruposId);
    }
    
    /**
     * Obtiene una colección de instancias de GrupoDeInteresEntity asociadas a una
     * instancia de Usuario
     *
     * @param usuariosId Identificador de la instancia de Usuario
     * @return Colección de instancias de GrupoDeInteresEntity asociadas a la instancia de
     * Usuario
     */
    public List<GrupoDeInteresEntity> getGrupos(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los grupos del usuario con id = {0}", usuariosId);
        return usuarioPersistence.find(usuariosId).getGrupos();
    }
    
    /**
     * Obtiene una instancia de GrupoDeInteresEntity asociada a una instancia de Usuario
     *
     * @param usuariosId Identificador de la instancia de Usuario
     * @param gruposId Identificador de la instancia de Grupo
     * @return La entidadd de Libro del usuario
     * @throws BusinessLogicException Si el libro no está asociado al usuario
     */
    public GrupoDeInteresEntity getGrupo(Long usuariosId, Long gruposId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el libro con id = {0} del usuario con id = " + usuariosId, gruposId);
        List<GrupoDeInteresEntity> grupos = usuarioPersistence.find(usuariosId).getGrupos();
        GrupoDeInteresEntity grupoEntity = grupoPersistence.find(gruposId);
        int index = grupos.indexOf(grupoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0} del usuario con id = " + usuariosId, gruposId);
        if (index >= 0) {
            return grupos.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado al usuario");
    }
    
    /**
     * Remplaza las instancias de Grupo asociadas a una instancia de Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param grupos Colección de instancias de GrupoDeInteresEntity a asociar a instancia
     * de Usuario
     * @return Nueva colección de GrupoDeInteresEntity asociada a la instancia de Usuario
     */
    public List<GrupoDeInteresEntity> replaceGrupos(Long usuarioId, List<GrupoDeInteresEntity> grupos) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los grupos asocidos al usuario con id = {0}", usuarioId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuarioId);
        List<GrupoDeInteresEntity> grupoList = grupoPersistence.findAll();
        
        for (GrupoDeInteresEntity grupo : grupoList) {
            if (grupos.contains(grupo))
            {
//                if (!grupo.getUsuarios().contains(usuarioEntity))
//                {
//                    grupo.getUsuarios().add(usuarioEntity);
//                }
            }
            else
            {
//                if (grupo.getUsuarios().contains(usuarioEntity))
//                {
//                    grupo.getUsuarios().remove(usuarioEntity);
//                }
                
            }
        }        
     
        usuarioEntity.setGrupos(grupos);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los grupos asocidos al usuario con id = {0}", usuarioId);
        return usuarioEntity.getGrupos();
    }
    
    /**
     * Desasocia un Grupo existente de un Usuario existente
     *
     * @param usuariosId Identificador de la instancia de Usuario
     * @param gruposId Identificador de la instancia de Grupo
     */
    public void removeGrupo(Long usuariosId, Long gruposId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un libro del usuario con id = {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        GrupoDeInteresEntity grupoEntity = grupoPersistence.find(gruposId);
        usuarioEntity.getGrupos().remove(grupoEntity);
//        grupoEntity.getUsuarios().remove(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un libro del usuario con id = {0}", usuariosId);
    }
}
