package co.edu.uniandes.csw.idiomas.persistence;
import co.edu.uniandes.csw.idiomas.entities.CalificacionCoordinadorEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para CalificacionCoordinador. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author jdruedaa
 */
@Stateless
public class CalificacionCoordinadorPersistence {

    private static final Logger LOGGER = Logger.getLogger(CalificacionCoordinadorPersistence.class.getName());

    @PersistenceContext(unitName = "idiomasPU")
    protected EntityManager em;

    /**
     * Crea una calificación en la base de datos
     *
     * @param CalificacionCoordinadorEntity objeto calificación que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CalificacionCoordinadorEntity create(CalificacionCoordinadorEntity CalificacionCoordinadorEntity) {
        LOGGER.log(Level.INFO, "Creando una calificación nueva");
        em.persist(CalificacionCoordinadorEntity);
        LOGGER.log(Level.INFO, "CalificacionCoordinador creada");
        return CalificacionCoordinadorEntity;
    }

    /**
     * Devuelve todas las CalificacionCoordinadores de la base de datos.
     *
     * @return una lista con todas las CalificacionCoordinadores que encuentre en la base de
     * datos, "select u from CalificacionCoordinadorEntity u" es como un "select * from
     * CalificacionCoordinadorEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CalificacionCoordinadorEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las CalificacionCoordinadores");
        // Se crea un query para buscar todas las CalificacionCoordinadores en la base de datos.
        TypedQuery query = em.createQuery("select u from CalificacionCoordinadorEntity u", CalificacionCoordinadorEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de CalificacionCoordinadores.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna calificación con el id que se envía de argumento
     *
     * @param CalificacionCoordinadoresId: id correspondiente a la calificación buscada.
     * @return una calificación.
     */
    public CalificacionCoordinadorEntity find(Long CalificacionCoordinadoresId) {
        LOGGER.log(Level.INFO, "Consultando la CalificacionCoordinador con id={0}", CalificacionCoordinadoresId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from CalificacionCoordinadorEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(CalificacionCoordinadorEntity.class, CalificacionCoordinadoresId);
    }

    /**
     * Actualiza una CalificacionCoordinador.
     *
     * @param CalificacionCoordinadorEntity: la CalificacionCoordinador que viene con los nuevos cambios. Por
     * ejemplo la calificación pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una calificación con los cambios aplicados.
     */
    public CalificacionCoordinadorEntity update(CalificacionCoordinadorEntity CalificacionCoordinadorEntity) {
        LOGGER.log(Level.INFO, "Actualizando la CalificacionCoordinador con id={0}", CalificacionCoordinadorEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la CalificacionCoordinador con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(CalificacionCoordinadorEntity);
    }

    /**
     * Borra una calificación de la base de datos recibiendo como argumento el id de
     * la calificación
     *
     * @param CalificacionCoordinadoresId: id correspondiente a la calificación a borrar.
     */
    public void delete(Long CalificacionCoordinadoresId) {

        LOGGER.log(Level.INFO, "Borrando la CalificacionCoordinador con id={0}", CalificacionCoordinadoresId);
        // Se hace uso de mismo método que esta explicado en public CalificacionCoordinadorEntity find(Long id) para obtener la CalificacionCoordinador a borrar.
        CalificacionCoordinadorEntity CalificacionCoordinadorEntity = em.find(CalificacionCoordinadorEntity.class, CalificacionCoordinadoresId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from CalificacionCoordinadorEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(CalificacionCoordinadorEntity);
    }
}