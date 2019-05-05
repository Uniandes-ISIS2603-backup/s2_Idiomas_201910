package co.edu.uniandes.csw.idiomas.ejb;
import co.edu.uniandes.csw.idiomas.entities.CalificacionActividadEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.CalificacionActividadPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para las CalificacionActividades.
 *
 * @author jdruedaa
 */
@Stateless
public class CalificacionActividadLogic {

    private static final Logger LOGGER = Logger.getLogger(CalificacionActividadLogic.class.getName());

    @Inject
    private CalificacionActividadPersistence CalificacionActividadPersistence;
    
      /**
     * Se encarga de crear una Calificafion en la base de datos.
     *
     * @param CalificacionActividadEntity Objeto de CalificacionActividadEntity con los datos nuevos
     * @return Objeto de CalificacionActividadEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionActividadEntity createCalificacionActividad(CalificacionActividadEntity CalificacionActividadEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la CalificacionActividad");
        CalificacionActividadEntity newCalificacionActividadEntity = CalificacionActividadPersistence.create(CalificacionActividadEntity);
        if(newCalificacionActividadEntity != null)
        {
            if (newCalificacionActividadEntity.getCalificacion().equals((Integer)newCalificacionActividadEntity.getCalificacion()))
            {
            } else {
                throw new BusinessLogicException("La calificación no es entera");
            }
            if (newCalificacionActividadEntity.getCalificacion() < 0 || newCalificacionActividadEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionActividadEntity.getMensaje() != null)
            {
                if(newCalificacionActividadEntity.getMensaje().length() > 300)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                }
                
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de creación de la CalificacionActividad");
        return newCalificacionActividadEntity;
    }

    /**
     * Obtiene la lista de los registros de Calificafion.
     *
     * @return Colección de objetos de CalificafionEntity.
     */
    public List<CalificacionActividadEntity> getCalificacionActividades() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        List<CalificacionActividadEntity> lista = CalificacionActividadPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de CalificacionActividad a partir de su ID.
     *
     * @param CalificacionActividadId Identificador de la instancia a consultar
     * @return Instancia de CalificacionActividadEntity con los datos de la CalificacionActividad consultada.
     */
    public CalificacionActividadEntity getCalificacionActividad(Long CalificacionActividadId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la CalificacionActividad con id = {0}", CalificacionActividadId);
        CalificacionActividadEntity CalificacionActividadEntity = CalificacionActividadPersistence.find(CalificacionActividadId);
        if (CalificacionActividadEntity == null) {
            LOGGER.log(Level.SEVERE, "La CalificacionActividad con el id = {0} no existe", CalificacionActividadId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el CalificacionActividad con id = {0}", CalificacionActividadId);
        return CalificacionActividadEntity;
    }

    /**
     * Actualiza la información de una instancia de Calificafion.
     *
     * @param CalificacionActividadId Identificador de la instancia a actualizar
     * @param newCalificacionActividadEntity Instancia de CalificafionEntity con los nuevos datos.
     * @return Instancia de CalificafionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionActividadEntity updateCalificacionActividad(Long CalificacionActividadId, CalificacionActividadEntity newCalificacionActividadEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor con id = {0}", CalificacionActividadId);
         if(CalificacionActividadPersistence.find(CalificacionActividadId) != null)
        {
            if (newCalificacionActividadEntity.getCalificacion().equals((Integer)newCalificacionActividadEntity.getCalificacion()))
            {
            }
                else{
                        throw new BusinessLogicException("La calificación no es entera");
                    }
            if (newCalificacionActividadEntity.getCalificacion() < 0 || newCalificacionActividadEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionActividadEntity.getMensaje() != null)
            {
                if(newCalificacionActividadEntity.getMensaje().length() > 300)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                    
                }
                
            }
        }
        CalificacionActividadEntity newCalificacionActividadEntity2 = CalificacionActividadPersistence.update(newCalificacionActividadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", CalificacionActividadId);
        return newCalificacionActividadEntity2;
    }

    /**
     * Elimina una instancia de Calificafion de la base de datos.
     *
     * @param CalificacionActividadId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene libros asociados.
     */
    public void deleteCalificacionActividad(Long CalificacionActividadId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", CalificacionActividadId);
        CalificacionActividadPersistence.delete(CalificacionActividadId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", CalificacionActividadId);
    }
}