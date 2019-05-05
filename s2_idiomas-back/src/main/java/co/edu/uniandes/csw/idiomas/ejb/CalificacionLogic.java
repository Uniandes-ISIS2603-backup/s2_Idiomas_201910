package co.edu.uniandes.csw.idiomas.ejb;
import co.edu.uniandes.csw.idiomas.entities.CalificacionEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.CalificacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para las calificaciones.
 *
 * @author jdruedaa
 */
@Stateless
public class CalificacionLogic {

    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());

    @Inject
    private CalificacionPersistence calificacionPersistence;
    
      /**
     * Se encarga de crear una Calificafion en la base de datos.
     *
     * @param calificacionEntity Objeto de CalificacionEntity con los datos nuevos
     * @return Objeto de CalificacionEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionEntity createCalificacion(CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la calificacion");
        CalificacionEntity newCalificacionEntity = calificacionPersistence.create(calificacionEntity);
        if(newCalificacionEntity != null)
        {
            if (newCalificacionEntity.getCalificacion().equals((Integer)newCalificacionEntity.getCalificacion()))
            {
            } else {
                throw new BusinessLogicException("La calificación no es entera");
            }
            if (newCalificacionEntity.getCalificacion() < 0 || newCalificacionEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionEntity.getMensaje() != null)
            {
                if(newCalificacionEntity.getMensaje().length() > 250)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                }
                
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de creación de la calificacion");
        return newCalificacionEntity;
    }

    /**
     * Obtiene la lista de los registros de Calificafion.
     *
     * @return Colección de objetos de CalificafionEntity.
     */
    public List<CalificacionEntity> getCalificaciones() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        List<CalificacionEntity> lista = calificacionPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Calificacion a partir de su ID.
     *
     * @param calificacionId Identificador de la instancia a consultar
     * @return Instancia de CalificacionEntity con los datos de la Calificacion consultada.
     */
    public CalificacionEntity getCalificacion(Long calificacionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0}", calificacionId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionId);
        if (calificacionEntity == null) {
            LOGGER.log(Level.SEVERE, "La calificacion con el id = {0} no existe", calificacionId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el calificacion con id = {0}", calificacionId);
        return calificacionEntity;
    }

    /**
     * Actualiza la información de una instancia de Calificafion.
     *
     * @param calificacionId Identificador de la instancia a actualizar
     * @param newCalificacionEntity Instancia de CalificafionEntity con los nuevos datos.
     * @return Instancia de CalificafionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionEntity updateCalificacion(Long calificacionId, CalificacionEntity newCalificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor con id = {0}", calificacionId);
         if(calificacionPersistence.find(calificacionId) != null)
        {
            if (newCalificacionEntity.getCalificacion().equals((Integer)newCalificacionEntity.getCalificacion()))
            {
            }
                else{
                        throw new BusinessLogicException("La calificación no es entera");
                    }
            if (newCalificacionEntity.getCalificacion() < 0 || newCalificacionEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionEntity.getMensaje() != null)
            {
                if(newCalificacionEntity.getMensaje().length() > 300)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                    
                }
                
            }
        }
        CalificacionEntity newCalificacionEntity2 = calificacionPersistence.update(newCalificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", calificacionId);
        return newCalificacionEntity2;
    }

    /**
     * Elimina una instancia de Calificafion de la base de datos.
     *
     * @param calificacionId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene libros asociados.
     */
    public void deleteCalificacion(Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", calificacionId);
        calificacionPersistence.delete(calificacionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", calificacionId);
    }
}