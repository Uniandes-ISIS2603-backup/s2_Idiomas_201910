/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.ejb;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.GrupoDeInteresPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * GrupoDeInteres.
 *
 * @author g.cubillosb
 */
@Stateless
public class GrupoDeInteresLogic {

    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(GrupoDeInteresLogic.class.getName());

    /**
     * Variable para acceder a la persistencia de la aplicación. Es una
     * inyección de dependencias.
     */
    @Inject
    private GrupoDeInteresPersistence persistence;

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Crea una grupoDeInteres en la persistencia.
     *
     * @param grupoDeInteresEntity La entidad que representa la grupoDeInteres a
     * persistir.
     * @return La entidad de la grupoDeInteres luego de persistirla.
     * @throws BusinessLogicException Si la grupoDeInteres a persistir ya existe.
     */
    public GrupoDeInteresEntity createGrupoDeInteres(GrupoDeInteresEntity grupoDeInteresEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la grupoDeInteres");

        // Verifica la regla de negocio que dice que el nombre de la grupoDeInteres no puede ser vacío.
        if (!validateName(grupoDeInteresEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que el idioma de la grupoDeInteres no puede ser vacío.
        if (!validateName(grupoDeInteresEntity.getIdioma())) {
            throw new BusinessLogicException("El idioma es inválido.");
        }
        // Verifica la regla de negocio que dice que la descripción de la grupoDeInteres no puede ser vacío.
        if (!validateName(grupoDeInteresEntity.getDescripcion())) {
            throw new BusinessLogicException("La descripción es inválida.");
        }
        // Verifica la regla de negocio que dice que un grupoDeInteres no puede ser idéntico a otro grupoDeInteres.
        if (persistence.findByName(grupoDeInteresEntity.getNombre()) != null
                &&persistence.findByName(grupoDeInteresEntity.getNombre()).equals(grupoDeInteresEntity))
        {
            throw new BusinessLogicException("El grupoDeInteres ya existe.");         
        }
        // Invoca la persistencia para crear la grupoDeInteres
        persistence.create(grupoDeInteresEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la grupoDeInteres");
        return grupoDeInteresEntity;
    }

    /**
     *
     * Obtener todas las gruposDeInteres existentes en la base de datos.
     *
     * @return una lista de gruposDeInteres.
     */
    public List<GrupoDeInteresEntity> getGruposDeInteres() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las gruposDeInteres");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<GrupoDeInteresEntity> gruposDeInteres = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las gruposDeInteres");
        return gruposDeInteres;
    }

    /**
     *
     * Obtener una grupoDeInteres por medio de su id.
     *
     * @param gruposDeInteresId: id de la grupoDeInteres para ser buscada.
     * @return la grupoDeInteres solicitada por medio de su id.
     */
    public GrupoDeInteresEntity getGrupoDeInteres(Long gruposDeInteresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la grupoDeInteres con id = {0}", gruposDeInteresId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        GrupoDeInteresEntity grupoDeInteresEntity = persistence.find(gruposDeInteresId);
        if (grupoDeInteresEntity == null) {
            LOGGER.log(Level.SEVERE, "La grupoDeInteres con el id = {0} no existe", gruposDeInteresId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la grupoDeInteres con id = {0}", gruposDeInteresId);
        return grupoDeInteresEntity;
    }

    /**
     *
     * Actualizar una grupoDeInteres.
     *
     * @param pGruposDeInteresId: id de la grupoDeInteres para buscarla en la base de
     * datos.
     * @param grupoDeInteresEntity: grupoDeInteres con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la grupoDeInteres con los cambios actualizados en la base de datos.
     */
    public GrupoDeInteresEntity updateGrupoDeInteres(Long pGruposDeInteresId, GrupoDeInteresEntity grupoDeInteresEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la grupoDeInteres con id = {0}", pGruposDeInteresId);
        // Verifica la regla de negocio que dice que el nombre de la grupoDeInteres no puede ser vacío.
        if (!validateName(grupoDeInteresEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que el idioma de la grupoDeInteres no puede ser vacío.
        if (!validateName(grupoDeInteresEntity.getIdioma())) {
            throw new BusinessLogicException("El idioma es inválido.");
        }
        // Verifica la regla de negocio que dice que la descripción de la grupoDeInteres no puede ser vacío.
        if (!validateName(grupoDeInteresEntity.getDescripcion())) {
            throw new BusinessLogicException("La descripción es inválida.");
        }
        // Verifica la regla de negocio que dice que un grupoDeInteres debe tener un administrador.
        if (grupoDeInteresEntity.getAdministrador() == null)
        {
            throw new BusinessLogicException("El grupo debe tener un administrador.");
        }
        // Verifica la regla de negocio que dice que un grupoDeInteres no puede ser idéntico a otro grupoDeInteres.
        if (persistence.findByName(grupoDeInteresEntity.getNombre()) != null
                &&persistence.findByName(grupoDeInteresEntity.getNombre()).equals(grupoDeInteresEntity))
        {
            throw new BusinessLogicException("El grupoDeInteres ya existe.");         
        }
        
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        GrupoDeInteresEntity newEntity = persistence.update(grupoDeInteresEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la grupoDeInteres con id = {0}", grupoDeInteresEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un grupoDeInteres
     *
     * @param pGruposDeInteresId: id de la grupoDeInteres a borrar
     * @throws BusinessLogicException Si la grupoDeInteres a eliminar tiene libros.
     */
    public void deleteGrupoDeInteres(Long pGruposDeInteresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la grupoDeInteres con id = {0}", pGruposDeInteresId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<ComentarioEntity> comentarios = getGrupoDeInteres(pGruposDeInteresId).getComentarios();
        if (comentarios != null && !comentarios.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la grupoDeInteres con id = " + pGruposDeInteresId + " porque tiene comentarios asociados");
        }
        List<ActividadEntity> actividades = getGrupoDeInteres(pGruposDeInteresId).getActividades();
        if (actividades != null && !actividades.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la grupoDeInteres con id = " + pGruposDeInteresId + " porque tiene actividades asociados");
        }
        
        persistence.delete(pGruposDeInteresId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la grupoDeInteres con id = {0}", pGruposDeInteresId);
    }

    /**
     * Verifica que el nombre no sea invalido.
     *
     * @param pNombre a verificar
     * @return true si el nombre es valido.
     */
    private boolean validateName(String pNombre) {
        return !(pNombre == null || pNombre.isEmpty());
    }

}
