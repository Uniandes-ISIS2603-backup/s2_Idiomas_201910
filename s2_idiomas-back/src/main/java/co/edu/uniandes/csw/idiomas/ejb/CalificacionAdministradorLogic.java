package co.edu.uniandes.csw.idiomas.ejb;
import co.edu.uniandes.csw.idiomas.entities.CalificacionAdministradorEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.CalificacionAdministradorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para las CalificacionAdministradores.
 *
 * @author jdruedaa
 */
@Stateless
public class CalificacionAdministradorLogic {

    private static final Logger LOGGER = Logger.getLogger(CalificacionAdministradorLogic.class.getName());

    @Inject
    private CalificacionAdministradorPersistence CalificacionAdministradorPersistence;
    
      /**
     * Se encarga de crear una Calificafion en la base de datos.
     *
     * @param CalificacionAdministradorEntity Objeto de CalificacionAdministradorEntity con los datos nuevos
     * @return Objeto de CalificacionAdministradorEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionAdministradorEntity createCalificacionAdministrador(CalificacionAdministradorEntity CalificacionAdministradorEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la CalificacionAdministrador");
        CalificacionAdministradorEntity newCalificacionAdministradorEntity = CalificacionAdministradorPersistence.create(CalificacionAdministradorEntity);
        if(newCalificacionAdministradorEntity != null)
        {
            if (newCalificacionAdministradorEntity.getCalificacion().equals((Integer)newCalificacionAdministradorEntity.getCalificacion()))
            {
            } else {
                throw new BusinessLogicException("La calificación no es entera");
            }
            if (newCalificacionAdministradorEntity.getCalificacion() < 0 || newCalificacionAdministradorEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionAdministradorEntity.getMensaje() != null)
            {
                if(newCalificacionAdministradorEntity.getMensaje().length() > 300)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                }
                
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de creación de la CalificacionAdministrador");
        return newCalificacionAdministradorEntity;
    }

    /**
     * Obtiene la lista de los registros de Calificafion.
     *
     * @return Colección de objetos de CalificafionEntity.
     */
    public List<CalificacionAdministradorEntity> getCalificacionAdministradores() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        List<CalificacionAdministradorEntity> lista = CalificacionAdministradorPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de CalificacionAdministrador a partir de su ID.
     *
     * @param CalificacionAdministradorId Identificador de la instancia a consultar
     * @return Instancia de CalificacionAdministradorEntity con los datos de la CalificacionAdministrador consultada.
     */
    public CalificacionAdministradorEntity getCalificacionAdministrador(Long CalificacionAdministradorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la CalificacionAdministrador con id = {0}", CalificacionAdministradorId);
        CalificacionAdministradorEntity CalificacionAdministradorEntity = CalificacionAdministradorPersistence.find(CalificacionAdministradorId);
        if (CalificacionAdministradorEntity == null) {
            LOGGER.log(Level.SEVERE, "La CalificacionAdministrador con el id = {0} no existe", CalificacionAdministradorId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el CalificacionAdministrador con id = {0}", CalificacionAdministradorId);
        return CalificacionAdministradorEntity;
    }

    /**
     * Actualiza la información de una instancia de Calificafion.
     *
     * @param CalificacionAdministradorId Identificador de la instancia a actualizar
     * @param newCalificacionAdministradorEntity Instancia de CalificafionEntity con los nuevos datos.
     * @return Instancia de CalificafionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionAdministradorEntity updateCalificacionAdministrador(Long CalificacionAdministradorId, CalificacionAdministradorEntity newCalificacionAdministradorEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor con id = {0}", CalificacionAdministradorId);
         if(CalificacionAdministradorPersistence.find(CalificacionAdministradorId) != null)
        {
            if (newCalificacionAdministradorEntity.getCalificacion().equals((Integer)newCalificacionAdministradorEntity.getCalificacion()))
            {
            }
                else{
                        throw new BusinessLogicException("La calificación no es entera");
                    }
            if (newCalificacionAdministradorEntity.getCalificacion() < 0 || newCalificacionAdministradorEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionAdministradorEntity.getMensaje() != null)
            {
                if(newCalificacionAdministradorEntity.getMensaje().length() > 300)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                    
                }
                
            }
        }
        CalificacionAdministradorEntity newCalificacionAdministradorEntity2 = CalificacionAdministradorPersistence.update(newCalificacionAdministradorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", CalificacionAdministradorId);
        return newCalificacionAdministradorEntity2;
    }

    /**
     * Elimina una instancia de Calificafion de la base de datos.
     *
     * @param CalificacionAdministradorId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene libros asociados.
     */
    public void deleteCalificacionAdministrador(Long CalificacionAdministradorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", CalificacionAdministradorId);
        CalificacionAdministradorPersistence.delete(CalificacionAdministradorId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", CalificacionAdministradorId);
    }
}