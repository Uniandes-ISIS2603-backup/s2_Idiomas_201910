package co.edu.uniandes.csw.idiomas.persistence;
import co.edu.uniandes.csw.idiomas.entities.CalificacionGrupoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para CalificacionGrupo. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author jdruedaa
 */
@Stateless
public class CalificacionGrupoPersistence {

    private static final Logger LOGGER = Logger.getLogger(CalificacionGrupoPersistence.class.getName());

    @PersistenceContext(unitName = "idiomasPU")
    protected EntityManager em;

    /**
     * Crea una calificación en la base de datos
     *
     * @param CalificacionGrupoEntity objeto calificación que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CalificacionGrupoEntity create(CalificacionGrupoEntity CalificacionGrupoEntity) {
        LOGGER.log(Level.INFO, "Creando una calificación nueva");
        em.persist(CalificacionGrupoEntity);
        LOGGER.log(Level.INFO, "CalificacionGrupo creada");
        return CalificacionGrupoEntity;
    }

    /**
     * Devuelve todas las CalificacionGrupoes de la base de datos.
     *
     * @return una lista con todas las CalificacionGrupoes que encuentre en la base de
     * datos, "select u from CalificacionGrupoEntity u" es como un "select * from
     * CalificacionGrupoEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CalificacionGrupoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las CalificacionGrupoes");
        // Se crea un query para buscar todas las CalificacionGrupoes en la base de datos.
        TypedQuery query = em.createQuery("select u from CalificacionGrupoEntity u", CalificacionGrupoEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de CalificacionGrupoes.
        return query.getResultList();
    }

    /**
     * Busca si hay alguna calificación con el id que se envía de argumento
     *
     * @param CalificacionGrupoesId: id correspondiente a la calificación buscada.
     * @return una calificación.
     */
    public CalificacionGrupoEntity find(Long CalificacionGrupoesId) {
        LOGGER.log(Level.INFO, "Consultando la CalificacionGrupo con id={0}", CalificacionGrupoesId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from CalificacionGrupoEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(CalificacionGrupoEntity.class, CalificacionGrupoesId);
    }

    /**
     * Actualiza una CalificacionGrupo.
     *
     * @param CalificacionGrupoEntity: la CalificacionGrupo que viene con los nuevos cambios. Por
     * ejemplo la calificación pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una calificación con los cambios aplicados.
     */
    public CalificacionGrupoEntity update(CalificacionGrupoEntity CalificacionGrupoEntity) {
        LOGGER.log(Level.INFO, "Actualizando la CalificacionGrupo con id={0}", CalificacionGrupoEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la CalificacionGrupo con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(CalificacionGrupoEntity);
    }

    /**
     * Borra una calificación de la base de datos recibiendo como argumento el id de
     * la calificación
     *
     * @param CalificacionGrupoesId: id correspondiente a la calificación a borrar.
     */
    public void delete(Long CalificacionGrupoesId) {

        LOGGER.log(Level.INFO, "Borrando la CalificacionGrupo con id={0}", CalificacionGrupoesId);
        // Se hace uso de mismo método que esta explicado en public CalificacionGrupoEntity find(Long id) para obtener la CalificacionGrupo a borrar.
        CalificacionGrupoEntity CalificacionGrupoEntity = em.find(CalificacionGrupoEntity.class, CalificacionGrupoesId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from CalificacionGrupoEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(CalificacionGrupoEntity);
    }
}