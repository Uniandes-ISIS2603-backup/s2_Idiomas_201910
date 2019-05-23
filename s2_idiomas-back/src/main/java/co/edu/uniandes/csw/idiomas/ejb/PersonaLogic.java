/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.ejb;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.PersonaEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.PersonaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Persona.
 *
 * @author g.cubillosb
 */
@Stateless
public class PersonaLogic {

    // -------------------------------------------------------------------------
    // Atributos
    // -------------------------------------------------------------------------
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(PersonaLogic.class.getName());

    /**
     * Variable para acceder a la persistencia de la aplicación. Es una
     * inyección de dependencias.
     */
    @Inject
    private PersonaPersistence persistence;

    // -------------------------------------------------------------------------
    // Métodos
    // -------------------------------------------------------------------------
    /**
     * Crea una persona en la persistencia.
     *
     * @param personaEntity La entidad que representa la persona a
     * persistir.
     * @return La entidad de la persona luego de persistirla.
     * @throws BusinessLogicException Si la persona a persistir ya existe.
     */
    public PersonaEntity createPersona(PersonaEntity personaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la persona");

        // Verifica la regla de negocio que dice que el nombre de la persona no puede ser vacío.
        if (!validateName(personaEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la persona no puede ser vacío.
        if (!validateName(personaEntity.getContrasenia())) {
            throw new BusinessLogicException("La contrasenia es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la persona no puede ser menor a
        //ocho caracteres.
        if (personaEntity.getContrasenia().length() < 8 ) {
            throw new BusinessLogicException("La longitud no es válida.");
        }
        // Verifica la regla de negocio que dice que un persona no puede ser idéntico a otro persona.
        if (persistence.findByName(personaEntity.getNombre()) != null
                &&persistence.findByName(personaEntity.getNombre()).equals(personaEntity))
        {
            throw new BusinessLogicException("El persona ya existe.");         
        }
        // Invoca la persistencia para crear la persona
        persistence.create(personaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la persona");
        return personaEntity;
    }

    /**
     *
     * Obtener todas las personas existentes en la base de datos.
     *
     * @return una lista de personas.
     */
    public List<PersonaEntity> getPersonas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las personas");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<PersonaEntity> personas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las personas");
        return personas;
    }

    /**
     *
     * Obtener una persona por medio de su id.
     *
     * @param personasId: id de la persona para ser buscada.
     * @return la persona solicitada por medio de su id.
     */
    public PersonaEntity getPersona(Long personasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la persona con id = {0}", personasId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        PersonaEntity personaEntity = persistence.find(personasId);
        if (personaEntity == null) {
            LOGGER.log(Level.SEVERE, "La persona con el id = {0} no existe", personasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la persona con id = {0}", personasId);
        return personaEntity;
    }

    /**
     *
     * Actualizar una persona.
     *
     * @param pPersonasId: id de la persona para buscarla en la base de
     * datos.
     * @param personaEntity: persona con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la persona con los cambios actualizados en la base de datos.
     */
    public PersonaEntity updatePersona(Long pPersonasId, PersonaEntity personaEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la persona con id = {0}", pPersonasId);
        
        // Verifica la regla de negocio que dice que el nombre de la persona no puede ser vacío.
        if (!validateName(personaEntity.getNombre())) {
            throw new BusinessLogicException("El nombre es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la persona no puede ser vacío.
        if (!validateName(personaEntity.getContrasenia())) {
            throw new BusinessLogicException("La contrasenia es inválido.");
        }
        // Verifica la regla de negocio que dice que la contrasenia de la persona no puede ser menor a
        //ocho caracteres.
        if (personaEntity.getContrasenia().length() < 8 ) {
            throw new BusinessLogicException("La longitud no es válida.");
        }
        // Verifica la regla de negocio que dice que un persona no puede ser idéntico a otro persona.
        if (persistence.findByName(personaEntity.getNombre()) != null
                &&persistence.findByName(personaEntity.getNombre()).equals(personaEntity))
        {
            throw new BusinessLogicException("El persona ya existe.");         
        }
        
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        PersonaEntity newEntity = persistence.update(personaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la persona con id = {0}", personaEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un persona
     *
     * @param pPersonasId: id de la persona a borrar
     * @throws BusinessLogicException Si la persona a eliminar tiene libros.
     */
    public void deletePersona(Long pPersonasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la persona con id = {0}", pPersonasId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<ComentarioEntity> comentarios = getPersona(pPersonasId).getComentarios();
        if (comentarios != null && !comentarios.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la persona con id = " + pPersonasId + " porque tiene comentarios asociados");
        }
        
        persistence.delete(pPersonasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la persona con id = {0}", pPersonasId);
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
