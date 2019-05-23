/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.persistence;

import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Coordinador. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author g.cubillosb
 */
@Stateless
public class CoordinadorPersistence {

    // ----------------------------------------------------------------------
    // Atributos 
    // ----------------------------------------------------------------------
    
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(CoordinadorPersistence.class.getName());

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
     * @param pCoordinadorEntity Objeto coordinador que se creará en la base de
     * datos.
     * @return Devuelve la coordinador creada con un id dado por la base de datos.
     */
    public CoordinadorEntity create(CoordinadorEntity pCoordinadorEntity) {
        LOGGER.log(Level.INFO, "Creando una coordinador nueva");
        em.persist(pCoordinadorEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una coordinador nueva");
        return pCoordinadorEntity;
    }

    /**
     * Devuelve todas las coordinadores de la base de datos.
     *
     * @return una lista con todas las coordinadores que encuentre en la base de
     * datos, "select u from CoordinadorEntity u" es como un "select * from
     * CoordinadorEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CoordinadorEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las coordinadores");
        // Se crea un query para buscar todas las coordinadores en la base de datos.
        TypedQuery query = em.createQuery("select u from CoordinadorEntity u", CoordinadorEntity.class);
        // Se hace uso del método getResultList() que obtiene una lista de coordinadores.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna coordinador con el id que se envía de argumento
     *
     * @param pCoordinadorId: id correspondiente a la coordinador buscada.
     * @return una coordinador.
     */
    public CoordinadorEntity find(Long pCoordinadorId) {
        LOGGER.log(Level.INFO, "Consultando coordinador con id = {0}", pCoordinadorId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from CoordinadorEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(CoordinadorEntity.class, pCoordinadorId);
    }

    /**
     * Actualiza una coordinador.
     *
     * @param pCoordinadorEntity: la coordinador que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una coordinador con los cambios aplicados.
     */
    public CoordinadorEntity update(CoordinadorEntity pCoordinadorEntity) {
        LOGGER.log(Level.INFO, "Actualizando coordinador con id = {0}", pCoordinadorEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la coordinador con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la coordinador con id = {0}", pCoordinadorEntity.getId());
        return em.merge(pCoordinadorEntity);
    }

    /**
     *
     * Borra una coordinador de la base de datos recibiendo como argumento el id
     * de la coordinador.
     *
     * @param pCoordinadorId: id correspondiente a la coordinador a borrar.
     */
    public void delete(Long pCoordinadorId) {
        LOGGER.log(Level.INFO, "Borrando coordinador con id = {0}", pCoordinadorId);
        // Se hace uso de mismo método que esta explicado en public CoordinadorEntity 
        // find(Long id) para obtener la coordinador a borrar.
        CoordinadorEntity entity = em.find(CoordinadorEntity.class, pCoordinadorId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado 
         "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que 
         encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM 
         table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la coordinador con id = {0}", pCoordinadorId);
    }

    /**
     * Busca si hay alguna coordinador con el nombre que se envía de argumento.
     *
     * @param pName: Nombre de la coordinador que se está buscando
     * @return null si no existe ninguna coordinador con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public CoordinadorEntity findByName(String pName) {
        LOGGER.log(Level.INFO, "Consultando coordinador por nombre = {0}", pName);
        // Se crea un query para buscar coordinadores con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From CoordinadorEntity e where e.nombre = :nombre", CoordinadorEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("nombre", pName);
        // Se invoca el query se obtiene la lista resultado
        List<CoordinadorEntity> sameName = query.getResultList();
        CoordinadorEntity result = null;
        if (!(sameName == null || sameName.isEmpty())) {
            result = sameName.get(0);
        }
            LOGGER.log(Level.INFO, "Saliendo de consultar coordinador por nombre = {0}", pName);
            return result;
        }
    }

