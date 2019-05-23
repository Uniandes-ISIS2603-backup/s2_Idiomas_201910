/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.persistence;

import co.edu.uniandes.csw.idiomas.entities.AdministradorEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Administrador. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author g.cubillosb
 */
@Stateless
public class AdministradorPersistence {

    // ----------------------------------------------------------------------
    // Atributos 
    // ----------------------------------------------------------------------
    
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(AdministradorPersistence.class.getName());

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
     * @param pAdministradorEntity Objeto administrador que se creará en la base de
     * datos.
     * @return Devuelve la administrador creada con un id dado por la base de datos.
     */
    public AdministradorEntity create(AdministradorEntity pAdministradorEntity) {
        LOGGER.log(Level.INFO, "Creando una administrador nueva");
        em.persist(pAdministradorEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una administrador nueva");
        return pAdministradorEntity;
    }

    /**
     * Devuelve todas las administradores de la base de datos.
     *
     * @return una lista con todas las administradores que encuentre en la base de
     * datos, "select u from AdministradorEntity u" es como un "select * from
     * AdministradorEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<AdministradorEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las administradores");
        // Se crea un query para buscar todas las administradores en la base de datos.
        TypedQuery query = em.createQuery("select u from AdministradorEntity u", AdministradorEntity.class);
        // Se hace uso del método getResultList() que obtiene una lista de administradores.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna administrador con el id que se envía de argumento
     *
     * @param pAdministradorId: id correspondiente a la administrador buscada.
     * @return una administrador.
     */
    public AdministradorEntity find(Long pAdministradorId) {
        LOGGER.log(Level.INFO, "Consultando administrador con id = {0}", pAdministradorId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from AdministradorEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(AdministradorEntity.class, pAdministradorId);
    }

    /**
     * Actualiza una administrador.
     *
     * @param pAdministradorEntity: la administrador que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una administrador con los cambios aplicados.
     */
    public AdministradorEntity update(AdministradorEntity pAdministradorEntity) {
        LOGGER.log(Level.INFO, "Actualizando administrador con id = {0}", pAdministradorEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la administrador con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la administrador con id = {0}", pAdministradorEntity.getId());
        return em.merge(pAdministradorEntity);
    }

    /**
     *
     * Borra una administrador de la base de datos recibiendo como argumento el id
     * de la administrador.
     *
     * @param pAdministradorId: id correspondiente a la administrador a borrar.
     */
    public void delete(Long pAdministradorId) {
        LOGGER.log(Level.INFO, "Borrando administrador con id = {0}", pAdministradorId);
        // Se hace uso de mismo método que esta explicado en public AdministradorEntity 
        // find(Long id) para obtener la administrador a borrar.
        AdministradorEntity entity = em.find(AdministradorEntity.class, pAdministradorId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado 
         "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que 
         encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM 
         table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la administrador con id = {0}", pAdministradorId);
    }

    /**
     * Busca si hay alguna administrador con el nombre que se envía de argumento.
     *
     * @param pName: Nombre de la administrador que se está buscando
     * @return null si no existe ninguna administrador con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public AdministradorEntity findByName(String pName) {
        LOGGER.log(Level.INFO, "Consultando administrador por nombre = {0}", pName);
        // Se crea un query para buscar administradores con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From AdministradorEntity e where e.nombre = :nombre", AdministradorEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("nombre", pName);
        // Se invoca el query se obtiene la lista resultado
        List<AdministradorEntity> sameName = query.getResultList();
        AdministradorEntity result = null;
        if (!(sameName == null || sameName.isEmpty())) {
            result = sameName.get(0);
        }
            LOGGER.log(Level.INFO, "Saliendo de consultar administrador por nombre = {0}", pName);
            return result;
        }
    }

