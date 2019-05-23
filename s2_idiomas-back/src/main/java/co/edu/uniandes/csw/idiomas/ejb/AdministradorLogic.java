/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.ejb;

import co.edu.uniandes.csw.idiomas.entities.AdministradorEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.AdministradorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Administrador.
 *
 * @author g.cubillosb
 */
@Stateless
public class AdministradorLogic {

    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(AdministradorLogic.class.getName());

    /**
     * Variable para acceder a la persistencia de la aplicación. Es una
     * inyección de dependencias.
     */
    @Inject
    private AdministradorPersistence persistence;

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Crea una administrador en la persistencia.
     *
     * @param administradorEntity La entidad que representa la administrador a
     * persistir.
     * @return La entidad de la administrador luego de persistirla.
     * @throws BusinessLogicException Si la administrador a persistir ya existe.
     */
    public AdministradorEntity createAdministrador(AdministradorEntity administradorEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la administrador");

        // Verifica la regla de negocio que dice que el nombre de la administrador no puede ser vacío.
        if (!validateName(administradorEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la administrador no puede ser vacío.
        if (!validateName(administradorEntity.getContrasenia())) {
            throw new BusinessLogicException("La contrasenia es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la administrador no puede ser menor a
        //ocho caracteres.
        if (administradorEntity.getContrasenia().length() < 8 ) {
            throw new BusinessLogicException("La longitud no es válida.");
        }
        // Verifica la regla de negocio que dice que un administrador no puede ser idéntico a otro administrador.
        if (persistence.findByName(administradorEntity.getNombre()) != null
                &&persistence.findByName(administradorEntity.getNombre()).equals(administradorEntity))
        {
            throw new BusinessLogicException("El administrador ya existe.");         
        }
        // Invoca la persistencia para crear la administrador
        persistence.create(administradorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la administrador");
        return administradorEntity;
    }

    /**
     *
     * Obtener todas las administradores existentes en la base de datos.
     *
     * @return una lista de administradores.
     */
    public List<AdministradorEntity> getAdministradores() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las administradores");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<AdministradorEntity> administradores = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las administradores");
        return administradores;
    }

    /**
     *
     * Obtener una administrador por medio de su id.
     *
     * @param administradoresId: id de la administrador para ser buscada.
     * @return la administrador solicitada por medio de su id.
     */
    public AdministradorEntity getAdministrador(Long administradoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la administrador con id = {0}", administradoresId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        AdministradorEntity administradorEntity = persistence.find(administradoresId);
        if (administradorEntity == null) {
            LOGGER.log(Level.SEVERE, "La administrador con el id = {0} no existe", administradoresId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la administrador con id = {0}", administradoresId);
        return administradorEntity;
    }

    /**
     *
     * Actualizar una administrador.
     *
     * @param pAdministradoresId: id de la administrador para buscarla en la base de
     * datos.
     * @param administradorEntity: administrador con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la administrador con los cambios actualizados en la base de datos.
     */
    public AdministradorEntity updateAdministrador(Long pAdministradoresId, AdministradorEntity administradorEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la administrador con id = {0}", pAdministradoresId);
        
        // Verifica la regla de negocio que dice que el nombre de la administrador no puede ser vacío.
        if (!validateName(administradorEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la administrador no puede ser vacío.
        if (!validateName(administradorEntity.getContrasenia())) {
            throw new BusinessLogicException("La contrasenia es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la administrador no puede ser menor a
        //ocho caracteres.
        if (administradorEntity.getContrasenia().length() < 8 ) {
            throw new BusinessLogicException("La longitud no es válida.");
        }
        // Verifica la regla de negocio que dice que un administrador no puede ser idéntico a otro administrador.
        if (persistence.findByName(administradorEntity.getNombre()) != null
                &&persistence.findByName(administradorEntity.getNombre()).equals(administradorEntity))
        {
            throw new BusinessLogicException("El administrador ya existe.");         
        }
        
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        AdministradorEntity newEntity = persistence.update(administradorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la administrador con id = {0}", administradorEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un administrador
     *
     * @param pAdministradoresId: id de la administrador a borrar
     * @throws BusinessLogicException Si la administrador a eliminar tiene libros.
     */
    public void deleteAdministrador(Long pAdministradoresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la administrador con id = {0}", pAdministradoresId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<ComentarioEntity> comentarios = getAdministrador(pAdministradoresId).getComentarios();
        if (comentarios != null && !comentarios.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la administrador con id = " + pAdministradoresId + " porque tiene comentarios asociados");
        }
        
        persistence.delete(pAdministradoresId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la administrador con id = {0}", pAdministradoresId);
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
