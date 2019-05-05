package co.edu.uniandes.csw.idiomas.ejb;
import co.edu.uniandes.csw.idiomas.entities.CalificacionCoordinadorEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.CalificacionCoordinadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para las CalificacionCoordinadores.
 *
 * @author jdruedaa
 */
@Stateless
public class CalificacionCoordinadorLogic {

    private static final Logger LOGGER = Logger.getLogger(CalificacionCoordinadorLogic.class.getName());

    @Inject
    private CalificacionCoordinadorPersistence CalificacionCoordinadorPersistence;
    
      /**
     * Se encarga de crear una Calificafion en la base de datos.
     *
     * @param CalificacionCoordinadorEntity Objeto de CalificacionCoordinadorEntity con los datos nuevos
     * @return Objeto de CalificacionCoordinadorEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionCoordinadorEntity createCalificacionCoordinador(CalificacionCoordinadorEntity CalificacionCoordinadorEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la CalificacionCoordinador");
        CalificacionCoordinadorEntity newCalificacionCoordinadorEntity = CalificacionCoordinadorPersistence.create(CalificacionCoordinadorEntity);
        if(newCalificacionCoordinadorEntity != null)
        {
            if (newCalificacionCoordinadorEntity.getCalificacion().equals((Integer)newCalificacionCoordinadorEntity.getCalificacion()))
            {
            } else {
                throw new BusinessLogicException("La calificación no es entera");
            }
            if (newCalificacionCoordinadorEntity.getCalificacion() < 0 || newCalificacionCoordinadorEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionCoordinadorEntity.getMensaje() != null)
            {
                if(newCalificacionCoordinadorEntity.getMensaje().length() > 300)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                }
                
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de creación de la CalificacionCoordinador");
        return newCalificacionCoordinadorEntity;
    }

    /**
     * Obtiene la lista de los registros de Calificafion.
     *
     * @return Colección de objetos de CalificafionEntity.
     */
    public List<CalificacionCoordinadorEntity> getCalificacionCoordinadores() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        List<CalificacionCoordinadorEntity> lista = CalificacionCoordinadorPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de CalificacionCoordinador a partir de su ID.
     *
     * @param CalificacionCoordinadorId Identificador de la instancia a consultar
     * @return Instancia de CalificacionCoordinadorEntity con los datos de la CalificacionCoordinador consultada.
     */
    public CalificacionCoordinadorEntity getCalificacionCoordinador(Long CalificacionCoordinadorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la CalificacionCoordinador con id = {0}", CalificacionCoordinadorId);
        CalificacionCoordinadorEntity CalificacionCoordinadorEntity = CalificacionCoordinadorPersistence.find(CalificacionCoordinadorId);
        if (CalificacionCoordinadorEntity == null) {
            LOGGER.log(Level.SEVERE, "La CalificacionCoordinador con el id = {0} no existe", CalificacionCoordinadorId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el CalificacionCoordinador con id = {0}", CalificacionCoordinadorId);
        return CalificacionCoordinadorEntity;
    }

    /**
     * Actualiza la información de una instancia de Calificafion.
     *
     * @param CalificacionCoordinadorId Identificador de la instancia a actualizar
     * @param newCalificacionCoordinadorEntity Instancia de CalificafionEntity con los nuevos datos.
     * @return Instancia de CalificafionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionCoordinadorEntity updateCalificacionCoordinador(Long CalificacionCoordinadorId, CalificacionCoordinadorEntity newCalificacionCoordinadorEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor con id = {0}", CalificacionCoordinadorId);
         if(CalificacionCoordinadorPersistence.find(CalificacionCoordinadorId) != null)
        {
            if (newCalificacionCoordinadorEntity.getCalificacion().equals((Integer)newCalificacionCoordinadorEntity.getCalificacion()))
            {
            }
                else{
                        throw new BusinessLogicException("La calificación no es entera");
                    }
            if (newCalificacionCoordinadorEntity.getCalificacion() < 0 || newCalificacionCoordinadorEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionCoordinadorEntity.getMensaje() != null)
            {
                if(newCalificacionCoordinadorEntity.getMensaje().length() > 300)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                    
                }
                
            }
        }
        CalificacionCoordinadorEntity newCalificacionCoordinadorEntity2 = CalificacionCoordinadorPersistence.update(newCalificacionCoordinadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", CalificacionCoordinadorId);
        return newCalificacionCoordinadorEntity2;
    }

    /**
     * Elimina una instancia de Calificafion de la base de datos.
     *
     * @param CalificacionCoordinadorId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene libros asociados.
     */
    public void deleteCalificacionCoordinador(Long CalificacionCoordinadorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", CalificacionCoordinadorId);
        CalificacionCoordinadorPersistence.delete(CalificacionCoordinadorId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", CalificacionCoordinadorId);
    }
}