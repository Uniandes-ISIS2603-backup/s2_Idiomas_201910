/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.persistence;

import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para GrupoDeInteres. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author g.cubillosb
 */
@Stateless
public class GrupoDeInteresPersistence {

    // ----------------------------------------------------------------------
    // Atributos 
    // ----------------------------------------------------------------------
    
    /**
     * Logger para las acciones de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger(GrupoDeInteresPersistence.class.getName());

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
     * @param pGrupoDeInteresEntity Objeto grupoDeInteres que se creará en la base de
     * datos.
     * @return Devuelve la grupoDeInteres creada con un id dado por la base de datos.
     */
    public GrupoDeInteresEntity create(GrupoDeInteresEntity pGrupoDeInteresEntity) {
        LOGGER.log(Level.INFO, "Creando una grupoDeInteres nueva");
        em.persist(pGrupoDeInteresEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una grupoDeInteres nueva");
        return pGrupoDeInteresEntity;
    }

    /**
     * Devuelve todas las gruposDeInteres de la base de datos.
     *
     * @return una lista con todas las gruposDeInteres que encuentre en la base de
     * datos, "select u from GrupoDeInteresEntity u" es como un "select * from
     * GrupoDeInteresEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<GrupoDeInteresEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las gruposDeInteres");
        // Se crea un query para buscar todas las gruposDeInteres en la base de datos.
        TypedQuery query = em.createQuery("select u from GrupoDeInteresEntity u", GrupoDeInteresEntity.class);
        // Se hace uso del método getResultList() que obtiene una lista de gruposDeInteres.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna grupoDeInteres con el id que se envía de argumento
     *
     * @param pGrupoDeInteresId: id correspondiente a la grupoDeInteres buscada.
     * @return una grupoDeInteres.
     */
    public GrupoDeInteresEntity find(Long pGrupoDeInteresId) {
        LOGGER.log(Level.INFO, "Consultando grupoDeInteres con id = {0}", pGrupoDeInteresId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from GrupoDeInteresEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(GrupoDeInteresEntity.class, pGrupoDeInteresId);
    }

    /**
     * Actualiza una grupoDeInteres.
     *
     * @param pGrupoDeInteresEntity: la grupoDeInteres que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una grupoDeInteres con los cambios aplicados.
     */
    public GrupoDeInteresEntity update(GrupoDeInteresEntity pGrupoDeInteresEntity) {
        LOGGER.log(Level.INFO, "Actualizando grupoDeInteres con id = {0}", pGrupoDeInteresEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la grupoDeInteres con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la grupoDeInteres con id = {0}", pGrupoDeInteresEntity.getId());
        return em.merge(pGrupoDeInteresEntity);
    }

    /**
     *
     * Borra una grupoDeInteres de la base de datos recibiendo como argumento el id
     * de la grupoDeInteres.
     *
     * @param pGrupoDeInteresId: id correspondiente a la grupoDeInteres a borrar.
     */
    public void delete(Long pGrupoDeInteresId) {
        LOGGER.log(Level.INFO, "Borrando grupoDeInteres con id = {0}", pGrupoDeInteresId);
        // Se hace uso de mismo método que esta explicado en public GrupoDeInteresEntity 
        // find(Long id) para obtener la grupoDeInteres a borrar.
        GrupoDeInteresEntity entity = em.find(GrupoDeInteresEntity.class, pGrupoDeInteresId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado 
         "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que 
         encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM 
         table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la grupoDeInteres con id = {0}", pGrupoDeInteresId);
    }

    /**
     * Busca si hay alguna grupoDeInteres con el nombre que se envía de argumento.
     *
     * @param pName: Nombre de la grupoDeInteres que se está buscando
     * @return null si no existe ninguna grupoDeInteres con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public GrupoDeInteresEntity findByName(String pName) {
        LOGGER.log(Level.INFO, "Consultando grupoDeInteres por nombre = {0}", pName);
        // Se crea un query para buscar gruposDeInteres con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From GrupoDeInteresEntity e where e.nombre = :nombre", GrupoDeInteresEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("nombre", pName);
        // Se invoca el query se obtiene la lista resultado
        List<GrupoDeInteresEntity> sameName = query.getResultList();
        GrupoDeInteresEntity result = null;
        if (!(sameName == null || sameName.isEmpty())) {
            result = sameName.get(0);
        }
            LOGGER.log(Level.INFO, "Saliendo de consultar grupoDeInteres por nombre = {0}", pName);
            return result;
        }
    }

