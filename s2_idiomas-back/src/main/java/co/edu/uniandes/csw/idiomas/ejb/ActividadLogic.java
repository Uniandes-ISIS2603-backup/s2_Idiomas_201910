/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.ejb;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.ActividadPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Actividad.
 *
 * @author g.cubillosb
 */
@Stateless
public class ActividadLogic {

    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(ActividadLogic.class.getName());

    /**
     * Variable para acceder a la persistencia de la aplicación. Es una
     * inyección de dependencias.
     */
    @Inject
    private ActividadPersistence persistence;

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Crea una actividad en la persistencia.
     *
     * @param actividadEntity La entidad que representa la actividad a
     * persistir.
     * @return La entidad de la actividad luego de persistirla.
     * @throws BusinessLogicException Si la actividad a persistir ya existe.
     */
    public ActividadEntity createActividad(ActividadEntity actividadEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la actividad");

        // Verifica la regla de negocio que dice que el nombre de la actividad no puede ser vacío.
        if (!validateName(actividadEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }

        // Verifica la regla de negocio que dice que una actividad debe tener un coordinador.
//        if (actividadEntity.getCoordinadores().isEmpty())
//        {
//            throw new BusinessLogicException("La actividad debe tener un coordinador.");
//        }
        // Verifica la regla de negocio que dice que una actividad no puede ser idéntica a otra actividad.
        if (persistence.findByName(actividadEntity.getNombre()) != null
                &&persistence.findByName(actividadEntity.getNombre()).equals(actividadEntity))
        {
            throw new BusinessLogicException("La actividad ya existe.");         
        }

        // Invoca la persistencia para crear la actividad
        persistence.create(actividadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la actividad");
        return actividadEntity;
    }

    /**
     *
     * Obtener todas las actividades existentes en la base de datos.
     *
     * @return una lista de actividades.
     */
    public List<ActividadEntity> getActividades() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las actividades");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ActividadEntity> actividades = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las actividades");
        return actividades;
    }

    /**
     *
     * Obtener una actividad por medio de su id.
     *
     * @param actividadesId: id de la actividad para ser buscada.
     * @return la actividad solicitada por medio de su id.
     */
    public ActividadEntity getActividad(Long actividadesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la actividad con id = {0}", actividadesId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ActividadEntity actividadEntity = persistence.find(actividadesId);
        if (actividadEntity == null) {
            LOGGER.log(Level.SEVERE, "La actividad con el id = {0} no existe", actividadesId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la actividad con id = {0}", actividadesId);
        return actividadEntity;
    }

    /**
     *
     * Actualizar una actividad.
     *
     * @param pActividadesId: id de la actividad para buscarla en la base de
     * datos.
     * @param actividadEntity: actividad con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la actividad con los cambios actualizados en la base de datos.
     */
    public ActividadEntity updateActividad(Long pActividadesId, ActividadEntity actividadEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la actividad con id = {0}", pActividadesId);
        if (!validateName(actividadEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que una actividad debe tener un coordinador.
//        if (actividadEntity.getCoordinadores().isEmpty())
//        {
//            throw new BusinessLogicException("La actividad debe tener un coordinador.");
//        }
        // Verifica la regla de negocio que dice que una actividad no puede ser idéntica a otra actividad.
        if (persistence.findByName(actividadEntity.getNombre()) != null
                && persistence.findByName(actividadEntity.getNombre()).equals(actividadEntity)) {
            throw new BusinessLogicException("La actividad ya existe.");
        }
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ActividadEntity newEntity = persistence.update(actividadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la actividad con id = {0}", actividadEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un actividad
     *
     * @param pActividadesId: id de la actividad a borrar
     * @throws BusinessLogicException Si la actividad a eliminar tiene libros.
     */
    public void deleteActividad(Long pActividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la actividad con id = {0}", pActividadesId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<ComentarioEntity> comentarios = getActividad(pActividadesId).getComentarios();
        if (comentarios != null && !comentarios.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la actividad con id = " + pActividadesId + " porque tiene comentarios asociados");
        }
        persistence.delete(pActividadesId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la actividad con id = {0}", pActividadesId);
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
