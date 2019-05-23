/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.persistence;

import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Usuario. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author g.cubillosb
 */
@Stateless
public class UsuarioPersistence {

    // ----------------------------------------------------------------------
    // Atributos 
    // ----------------------------------------------------------------------
    
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());

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
     * @param pUsuarioEntity Objeto usuario que se creará en la base de
     * datos.
     * @return Devuelve la usuario creada con un id dado por la base de datos.
     */
    public UsuarioEntity create(UsuarioEntity pUsuarioEntity) {
        LOGGER.log(Level.INFO, "Creando una usuario nueva");
        em.persist(pUsuarioEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una usuario nueva");
        return pUsuarioEntity;
    }

    /**
     * Devuelve todas las usuarios de la base de datos.
     *
     * @return una lista con todas las usuarios que encuentre en la base de
     * datos, "select u from UsuarioEntity u" es como un "select * from
     * UsuarioEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<UsuarioEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las usuarios");
        // Se crea un query para buscar todas las usuarios en la base de datos.
        TypedQuery query = em.createQuery("select u from UsuarioEntity u", UsuarioEntity.class);
        // Se hace uso del método getResultList() que obtiene una lista de usuarios.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna usuario con el id que se envía de argumento
     *
     * @param pUsuarioId: id correspondiente a la usuario buscada.
     * @return una usuario.
     */
    public UsuarioEntity find(Long pUsuarioId) {
        LOGGER.log(Level.INFO, "Consultando usuario con id = {0}", pUsuarioId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from UsuarioEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(UsuarioEntity.class, pUsuarioId);
    }

    /**
     * Actualiza una usuario.
     *
     * @param pUsuarioEntity: la usuario que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una usuario con los cambios aplicados.
     */
    public UsuarioEntity update(UsuarioEntity pUsuarioEntity) {
        LOGGER.log(Level.INFO, "Actualizando usuario con id = {0}", pUsuarioEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la usuario con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la usuario con id = {0}", pUsuarioEntity.getId());
        return em.merge(pUsuarioEntity);
    }

    /**
     *
     * Borra una usuario de la base de datos recibiendo como argumento el id
     * de la usuario.
     *
     * @param pUsuarioId: id correspondiente a la usuario a borrar.
     */
    public void delete(Long pUsuarioId) {
        LOGGER.log(Level.INFO, "Borrando usuario con id = {0}", pUsuarioId);
        // Se hace uso de mismo método que esta explicado en public UsuarioEntity 
        // find(Long id) para obtener la usuario a borrar.
        UsuarioEntity entity = em.find(UsuarioEntity.class, pUsuarioId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado 
         "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que 
         encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM 
         table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la usuario con id = {0}", pUsuarioId);
    }

    /**
     * Busca si hay alguna usuario con el nombre que se envía de argumento.
     *
     * @param pName: Nombre de la usuario que se está buscando
     * @return null si no existe ninguna usuario con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public UsuarioEntity findByName(String pName) {
        LOGGER.log(Level.INFO, "Consultando usuario por nombre = {0}", pName);
        // Se crea un query para buscar usuarios con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From UsuarioEntity e where e.nombre = :nombre", UsuarioEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("nombre", pName);
        // Se invoca el query se obtiene la lista resultado
        List<UsuarioEntity> sameName = query.getResultList();
        UsuarioEntity result = null;
        if (!(sameName == null || sameName.isEmpty())) {
            result = sameName.get(0);
        }
            LOGGER.log(Level.INFO, "Saliendo de consultar usuario por nombre = {0}", pName);
            return result;
        }
    }

