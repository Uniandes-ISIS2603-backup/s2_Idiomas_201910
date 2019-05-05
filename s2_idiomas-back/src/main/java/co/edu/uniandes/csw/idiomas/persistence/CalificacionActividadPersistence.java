package co.edu.uniandes.csw.idiomas.persistence;
import co.edu.uniandes.csw.idiomas.entities.CalificacionActividadEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para CalificacionActividad. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author jdruedaa
 */
@Stateless
public class CalificacionActividadPersistence {

    private static final Logger LOGGER = Logger.getLogger(CalificacionActividadPersistence.class.getName());

    @PersistenceContext(unitName = "idiomasPU")
    protected EntityManager em;

    /**
     * Crea una calificación en la base de datos
     *
     * @param CalificacionActividadEntity objeto calificación que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CalificacionActividadEntity create(CalificacionActividadEntity CalificacionActividadEntity) {
        LOGGER.log(Level.INFO, "Creando una calificación nueva");
        em.persist(CalificacionActividadEntity);
        LOGGER.log(Level.INFO, "CalificacionActividad creada");
        return CalificacionActividadEntity;
    }

    /**
     * Devuelve todas las CalificacionActividades de la base de datos.
     *
     * @return una lista con todas las CalificacionActividades que encuentre en la base de
     * datos, "select u from CalificacionActividadEntity u" es como un "select * from
     * CalificacionActividadEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CalificacionActividadEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las CalificacionActividades");
        // Se crea un query para buscar todas las CalificacionActividades en la base de datos.
        TypedQuery query = em.createQuery("select u from CalificacionActividadEntity u", CalificacionActividadEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de CalificacionActividades.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna calificación con el id que se envía de argumento
     *
     * @param CalificacionActividadesId: id correspondiente a la calificación buscada.
     * @return una calificación.
     */
    public CalificacionActividadEntity find(Long CalificacionActividadesId) {
        LOGGER.log(Level.INFO, "Consultando la CalificacionActividad con id={0}", CalificacionActividadesId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from CalificacionActividadEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(CalificacionActividadEntity.class, CalificacionActividadesId);
    }

    /**
     * Actualiza una CalificacionActividad.
     *
     * @param CalificacionActividadEntity: la CalificacionActividad que viene con los nuevos cambios. Por
     * ejemplo la calificación pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una calificación con los cambios aplicados.
     */
    public CalificacionActividadEntity update(CalificacionActividadEntity CalificacionActividadEntity) {
        LOGGER.log(Level.INFO, "Actualizando la CalificacionActividad con id={0}", CalificacionActividadEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la CalificacionActividad con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(CalificacionActividadEntity);
    }

    /**
     * Borra una calificación de la base de datos recibiendo como argumento el id de
     * la calificación
     *
     * @param CalificacionActividadesId: id correspondiente a la calificación a borrar.
     */
    public void delete(Long CalificacionActividadesId) {

        LOGGER.log(Level.INFO, "Borrando la CalificacionActividad con id={0}", CalificacionActividadesId);
        // Se hace uso de mismo método que esta explicado en public CalificacionActividadEntity find(Long id) para obtener la CalificacionActividad a borrar.
        CalificacionActividadEntity CalificacionActividadEntity = em.find(CalificacionActividadEntity.class, CalificacionActividadesId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from CalificacionActividadEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(CalificacionActividadEntity);
    }
}