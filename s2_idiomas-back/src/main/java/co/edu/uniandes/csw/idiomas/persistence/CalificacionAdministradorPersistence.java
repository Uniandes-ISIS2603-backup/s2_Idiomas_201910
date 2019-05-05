package co.edu.uniandes.csw.idiomas.persistence;
import co.edu.uniandes.csw.idiomas.entities.CalificacionAdministradorEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para CalificacionAdministrador. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author jdruedaa
 */
@Stateless
public class CalificacionAdministradorPersistence {

    private static final Logger LOGGER = Logger.getLogger(CalificacionAdministradorPersistence.class.getName());

    @PersistenceContext(unitName = "idiomasPU")
    protected EntityManager em;

    /**
     * Crea una calificación en la base de datos
     *
     * @param CalificacionAdministradorEntity objeto calificación que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CalificacionAdministradorEntity create(CalificacionAdministradorEntity CalificacionAdministradorEntity) {
        LOGGER.log(Level.INFO, "Creando una calificación nueva");
        em.persist(CalificacionAdministradorEntity);
        LOGGER.log(Level.INFO, "CalificacionAdministrador creada");
        return CalificacionAdministradorEntity;
    }

    /**
     * Devuelve todas las CalificacionAdministradores de la base de datos.
     *
     * @return una lista con todas las CalificacionAdministradores que encuentre en la base de
     * datos, "select u from CalificacionAdministradorEntity u" es como un "select * from
     * CalificacionAdministradorEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CalificacionAdministradorEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las CalificacionAdministradores");
        // Se crea un query para buscar todas las CalificacionAdministradores en la base de datos.
        TypedQuery query = em.createQuery("select u from CalificacionAdministradorEntity u", CalificacionAdministradorEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de CalificacionAdministradores.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna calificación con el id que se envía de argumento
     *
     * @param CalificacionAdministradoresId: id correspondiente a la calificación buscada.
     * @return una calificación.
     */
    public CalificacionAdministradorEntity find(Long CalificacionAdministradoresId) {
        LOGGER.log(Level.INFO, "Consultando la CalificacionAdministrador con id={0}", CalificacionAdministradoresId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from CalificacionAdministradorEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(CalificacionAdministradorEntity.class, CalificacionAdministradoresId);
    }

    /**
     * Actualiza una CalificacionAdministrador.
     *
     * @param CalificacionAdministradorEntity: la CalificacionAdministrador que viene con los nuevos cambios. Por
     * ejemplo la calificación pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una calificación con los cambios aplicados.
     */
    public CalificacionAdministradorEntity update(CalificacionAdministradorEntity CalificacionAdministradorEntity) {
        LOGGER.log(Level.INFO, "Actualizando la CalificacionAdministrador con id={0}", CalificacionAdministradorEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la CalificacionAdministrador con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(CalificacionAdministradorEntity);
    }

    /**
     * Borra una calificación de la base de datos recibiendo como argumento el id de
     * la calificación
     *
     * @param CalificacionAdministradoresId: id correspondiente a la calificación a borrar.
     */
    public void delete(Long CalificacionAdministradoresId) {

        LOGGER.log(Level.INFO, "Borrando la CalificacionAdministrador con id={0}", CalificacionAdministradoresId);
        // Se hace uso de mismo método que esta explicado en public CalificacionAdministradorEntity find(Long id) para obtener la CalificacionAdministrador a borrar.
        CalificacionAdministradorEntity CalificacionAdministradorEntity = em.find(CalificacionAdministradorEntity.class, CalificacionAdministradoresId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from CalificacionAdministradorEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(CalificacionAdministradorEntity);
    }
}