/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.ejb;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.CoordinadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Coordinador.
 *
 * @author g.cubillosb
 */
@Stateless
public class CoordinadorLogic {

    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(CoordinadorLogic.class.getName());

    /**
     * Variable para acceder a la persistencia de la aplicación. Es una
     * inyección de dependencias.
     */
    @Inject
    private CoordinadorPersistence persistence;

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Crea una coordinador en la persistencia.
     *
     * @param coordinadorEntity La entidad que representa la coordinador a
     * persistir.
     * @return La entidad de la coordinador luego de persistirla.
     * @throws BusinessLogicException Si la coordinador a persistir ya existe.
     */
    public CoordinadorEntity createCoordinador(CoordinadorEntity coordinadorEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la coordinador");

        // Verifica la regla de negocio que dice que el nombre de la coordinador no puede ser vacío.
        if (!validateName(coordinadorEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la coordinador no puede ser vacío.
        if (!validateName(coordinadorEntity.getContrasenia())) {
            throw new BusinessLogicException("La contrasenia es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la coordinador no puede ser menor a
        //ocho caracteres.
        if (coordinadorEntity.getContrasenia().length() < 8 ) {
            throw new BusinessLogicException("La longitud no es válida.");
        }
        // Verifica la regla de negocio que dice que un coordinador no puede ser idéntico a otro coordinador.
        if (persistence.findByName(coordinadorEntity.getNombre()) != null
                &&persistence.findByName(coordinadorEntity.getNombre()).equals(coordinadorEntity))
        {
            throw new BusinessLogicException("El coordinador ya existe.");         
        }
        // Invoca la persistencia para crear la coordinador
        persistence.create(coordinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la coordinador");
        return coordinadorEntity;
    }

    /**
     *
     * Obtener todas las coordinadores existentes en la base de datos.
     *
     * @return una lista de coordinadores.
     */
    public List<CoordinadorEntity> getCoordinadores() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las coordinadores");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<CoordinadorEntity> coordinadores = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las coordinadores");
        return coordinadores;
    }

    /**
     *
     * Obtener una coordinador por medio de su id.
     *
     * @param coordinadoresId: id de la coordinador para ser buscada.
     * @return la coordinador solicitada por medio de su id.
     */
    public CoordinadorEntity getCoordinador(Long coordinadoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la coordinador con id = {0}", coordinadoresId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        CoordinadorEntity coordinadorEntity = persistence.find(coordinadoresId);
        if (coordinadorEntity == null) {
            LOGGER.log(Level.SEVERE, "La coordinador con el id = {0} no existe", coordinadoresId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la coordinador con id = {0}", coordinadoresId);
        return coordinadorEntity;
    }

    /**
     *
     * Actualizar una coordinador.
     *
     * @param pCoordinadoresId: id de la coordinador para buscarla en la base de
     * datos.
     * @param coordinadorEntity: coordinador con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la coordinador con los cambios actualizados en la base de datos.
     */
    public CoordinadorEntity updateCoordinador(Long pCoordinadoresId, CoordinadorEntity coordinadorEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la coordinador con id = {0}", pCoordinadoresId);
        
        // Verifica la regla de negocio que dice que el nombre de la coordinador no puede ser vacío.
        if (!validateName(coordinadorEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la coordinador no puede ser vacío.
        if (!validateName(coordinadorEntity.getContrasenia())) {
            throw new BusinessLogicException("La contrasenia es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la coordinador no puede ser menor a
        //ocho caracteres.
        if (coordinadorEntity.getContrasenia().length() < 8 ) {
            throw new BusinessLogicException("La longitud no es válida.");
        }
        // Verifica la regla de negocio que dice que un coordinador no puede ser idéntico a otro coordinador.
        if (persistence.findByName(coordinadorEntity.getNombre()) != null
                &&persistence.findByName(coordinadorEntity.getNombre()).equals(coordinadorEntity))
        {
            throw new BusinessLogicException("El coordinador ya existe.");         
        }
        
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        CoordinadorEntity newEntity = persistence.update(coordinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la coordinador con id = {0}", coordinadorEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un coordinador
     *
     * @param pCoordinadoresId: id de la coordinador a borrar
     * @throws BusinessLogicException Si la coordinador a eliminar tiene libros.
     */
    public void deleteCoordinador(Long pCoordinadoresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la coordinador con id = {0}", pCoordinadoresId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<ActividadEntity> actividades = getCoordinador(pCoordinadoresId).getActividades();
        if (actividades != null && !actividades.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la coordinador con id = " + pCoordinadoresId + " porque tiene actividades asociados");
        }
        
        persistence.delete(pCoordinadoresId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la coordinador con id = {0}", pCoordinadoresId);
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
