/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.persistence;

import co.edu.uniandes.csw.idiomas.entities.PersonaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Persona. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author g.cubillosb
 */
@Stateless
public class PersonaPersistence {

    // ----------------------------------------------------------------------
    // Atributos 
    // ----------------------------------------------------------------------
    
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(PersonaPersistence.class.getName());

    /**
     * Entity manager para la clase.
     */
    @PersistenceContext(unitName = "idiomasPU")
    protected EntityManager em;

    // ----------------------------------------------------------------------
    // Métodos
    // ----------------------------------------------------------------------
    
    /**
     * Método para persistir la entidad en la base de datos.
     *
     * @param pPersonaEntity Objeto persona que se creará en la base de
     * datos.
     * @return Devuelve la persona creada con un id dado por la base de datos.
     */
    public PersonaEntity create(PersonaEntity pPersonaEntity) {
        LOGGER.log(Level.INFO, "Creando una persona nueva");
        em.persist(pPersonaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una persona nueva");
        return pPersonaEntity;
    }

    /**
     * Devuelve todas las personas de la base de datos.
     *
     * @return una lista con todas las personas que encuentre en la base de
     * datos, "select u from PersonaEntity u" es como un "select * from
     * PersonaEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<PersonaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las personas");
        // Se crea un query para buscar todas las personas en la base de datos.
        TypedQuery query = em.createQuery("select u from PersonaEntity u", PersonaEntity.class);
        // Se hace uso del método getResultList() que obtiene una lista de personas.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna persona con el id que se envía de argumento
     *
     * @param pPersonaId: id correspondiente a la persona buscada.
     * @return una persona.
     */
    public PersonaEntity find(Long pPersonaId) {
        LOGGER.log(Level.INFO, "Consultando persona con id = {0}", pPersonaId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from PersonaEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(PersonaEntity.class, pPersonaId);
    }

    /**
     * Actualiza una persona.
     *
     * @param pPersonaEntity: la persona que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una persona con los cambios aplicados.
     */
    public PersonaEntity update(PersonaEntity pPersonaEntity) {
        LOGGER.log(Level.INFO, "Actualizando persona con id = {0}", pPersonaEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la persona con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la persona con id = {0}", pPersonaEntity.getId());
        return em.merge(pPersonaEntity);
    }

    /**
     *
     * Borra una persona de la base de datos recibiendo como argumento el id
     * de la persona.
     *
     * @param pPersonaId: id correspondiente a la persona a borrar.
     */
    public void delete(Long pPersonaId) {
        LOGGER.log(Level.INFO, "Borrando persona con id = {0}", pPersonaId);
        // Se hace uso de mismo método que esta explicado en public PersonaEntity 
        // find(Long id) para obtener la persona a borrar.
        PersonaEntity entity = em.find(PersonaEntity.class, pPersonaId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado 
         "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que 
         encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM 
         table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la persona con id = {0}", pPersonaId);
    }

    /**
     * Busca si hay alguna persona con el nombre que se envía de argumento.
     *
     * @param pName: Nombre de la persona que se está buscando
     * @return null si no existe ninguna persona con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public PersonaEntity findByName(String pName) {
        LOGGER.log(Level.INFO, "Consultando persona por nombre = {0}", pName);
        // Se crea un query para buscar personas con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From PersonaEntity e where e.nombre = :nombre", PersonaEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("nombre", pName);
        // Se invoca el query se obtiene la lista resultado
        List<PersonaEntity> sameName = query.getResultList();
        PersonaEntity result = null;
        if (!(sameName == null || sameName.isEmpty())) {
            result = sameName.get(0);
        }
            LOGGER.log(Level.INFO, "Saliendo de consultar persona por nombre = {0}", pName);
            return result;
        }
    }

