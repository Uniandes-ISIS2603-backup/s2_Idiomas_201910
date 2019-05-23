/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.ejb;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Usuario.
 *
 * @author g.cubillosb
 */
@Stateless
public class UsuarioLogic {

    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(UsuarioLogic.class.getName());

    /**
     * Variable para acceder a la persistencia de la aplicación. Es una
     * inyección de dependencias.
     */
    @Inject
    private UsuarioPersistence persistence;

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Crea una usuario en la persistencia.
     *
     * @param usuarioEntity La entidad que representa la usuario a
     * persistir.
     * @return La entidad de la usuario luego de persistirla.
     * @throws BusinessLogicException Si la usuario a persistir ya existe.
     */
    public UsuarioEntity createUsuario(UsuarioEntity usuarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la usuario");

        // Verifica la regla de negocio que dice que el nombre de la usuario no puede ser vacío.
        if (!validateName(usuarioEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la usuario no puede ser vacío.
        if (!validateName(usuarioEntity.getContrasenia())) {
            throw new BusinessLogicException("La contrasenia es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la usuario no puede ser menor a
        //ocho caracteres.
        if (usuarioEntity.getContrasenia().length() < 8 ) {
            throw new BusinessLogicException("La longitud no es válida.");
        }
        // Verifica la regla de negocio que dice que un usuario no puede ser idéntico a otro usuario.
        if (persistence.findByName(usuarioEntity.getNombre()) != null
                &&persistence.findByName(usuarioEntity.getNombre()).equals(usuarioEntity))
        {
            throw new BusinessLogicException("El usuario ya existe.");         
        }
        // Invoca la persistencia para crear la usuario
        persistence.create(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la usuario");
        return usuarioEntity;
    }

    /**
     *
     * Obtener todas las usuarios existentes en la base de datos.
     *
     * @return una lista de usuarios.
     */
    public List<UsuarioEntity> getUsuarios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las usuarios");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<UsuarioEntity> usuarios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las usuarios");
        return usuarios;
    }

    /**
     *
     * Obtener una usuario por medio de su id.
     *
     * @param usuariosId: id de la usuario para ser buscada.
     * @return la usuario solicitada por medio de su id.
     */
    public UsuarioEntity getUsuario(Long usuariosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la usuario con id = {0}", usuariosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        UsuarioEntity usuarioEntity = persistence.find(usuariosId);
        if (usuarioEntity == null) {
            LOGGER.log(Level.SEVERE, "La usuario con el id = {0} no existe", usuariosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la usuario con id = {0}", usuariosId);
        return usuarioEntity;
    }

    /**
     *
     * Actualizar una usuario.
     *
     * @param pUsuariosId: id de la usuario para buscarla en la base de
     * datos.
     * @param usuarioEntity: usuario con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la usuario con los cambios actualizados en la base de datos.
     */
    public UsuarioEntity updateUsuario(Long pUsuariosId, UsuarioEntity usuarioEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la usuario con id = {0}", pUsuariosId);
        
        // Verifica la regla de negocio que dice que el nombre de la usuario no puede ser vacío.
        if (!validateName(usuarioEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la usuario no puede ser vacío.
        if (!validateName(usuarioEntity.getContrasenia())) {
            throw new BusinessLogicException("La contrasenia es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la usuario no puede ser menor a
        //ocho caracteres.
        if (usuarioEntity.getContrasenia().length() < 8 ) {
            throw new BusinessLogicException("La longitud no es válida.");
        }
        // Verifica la regla de negocio que dice que un usuario no puede ser idéntico a otro usuario.
        if (persistence.findByName(usuarioEntity.getNombre()) != null
                &&persistence.findByName(usuarioEntity.getNombre()).equals(usuarioEntity))
        {
            throw new BusinessLogicException("El usuario ya existe.");         
        }
        
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        UsuarioEntity newEntity = persistence.update(usuarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la usuario con id = {0}", usuarioEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un usuario
     *
     * @param pUsuariosId: id de la usuario a borrar
     * @throws BusinessLogicException Si la usuario a eliminar tiene libros.
     */
    public void deleteUsuario(Long pUsuariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la usuario con id = {0}", pUsuariosId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<ActividadEntity> actividades = getUsuario(pUsuariosId).getActividades();
        if (actividades != null && !actividades.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la usuario con id = " + pUsuariosId + " porque tiene actividades asociados");
        }
        
        persistence.delete(pUsuariosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la usuario con id = {0}", pUsuariosId);
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
