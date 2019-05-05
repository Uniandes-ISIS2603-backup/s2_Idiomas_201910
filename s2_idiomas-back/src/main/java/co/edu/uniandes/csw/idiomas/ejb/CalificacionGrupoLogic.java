package co.edu.uniandes.csw.idiomas.ejb;
import co.edu.uniandes.csw.idiomas.entities.CalificacionGrupoEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.CalificacionGrupoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para las CalificacionGrupoes.
 *
 * @author jdruedaa
 */
@Stateless
public class CalificacionGrupoLogic {

    private static final Logger LOGGER = Logger.getLogger(CalificacionGrupoLogic.class.getName());

    @Inject
    private CalificacionGrupoPersistence CalificacionGrupoPersistence;
    
      /**
     * Se encarga de crear una Calificafion en la base de datos.
     *
     * @param CalificacionGrupoEntity Objeto de CalificacionGrupoEntity con los datos nuevos
     * @return Objeto de CalificacionGrupoEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionGrupoEntity createCalificacionGrupo(CalificacionGrupoEntity CalificacionGrupoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la CalificacionGrupo");
        CalificacionGrupoEntity newCalificacionGrupoEntity = CalificacionGrupoPersistence.create(CalificacionGrupoEntity);
        if(newCalificacionGrupoEntity != null)
        {
            if (newCalificacionGrupoEntity.getCalificacion().equals((Integer)newCalificacionGrupoEntity.getCalificacion()))
            {
            } else {
                throw new BusinessLogicException("La calificación no es entera");
            }
            if (newCalificacionGrupoEntity.getCalificacion() < 0 || newCalificacionGrupoEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionGrupoEntity.getMensaje() != null)
            {
                if(newCalificacionGrupoEntity.getMensaje().length() > 300)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                }
                
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de creación de la CalificacionGrupo");
        return newCalificacionGrupoEntity;
    }

    /**
     * Obtiene la lista de los registros de Calificafion.
     *
     * @return Colección de objetos de CalificafionEntity.
     */
    public List<CalificacionGrupoEntity> getCalificacionGrupoes() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        List<CalificacionGrupoEntity> lista = CalificacionGrupoPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los autores");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de CalificacionGrupo a partir de su ID.
     *
     * @param CalificacionGrupoId Identificador de la instancia a consultar
     * @return Instancia de CalificacionGrupoEntity con los datos de la CalificacionGrupo consultada.
     */
    public CalificacionGrupoEntity getCalificacionGrupo(Long CalificacionGrupoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la CalificacionGrupo con id = {0}", CalificacionGrupoId);
        CalificacionGrupoEntity CalificacionGrupoEntity = CalificacionGrupoPersistence.find(CalificacionGrupoId);
        if (CalificacionGrupoEntity == null) {
            LOGGER.log(Level.SEVERE, "La CalificacionGrupo con el id = {0} no existe", CalificacionGrupoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el CalificacionGrupo con id = {0}", CalificacionGrupoId);
        return CalificacionGrupoEntity;
    }

    /**
     * Actualiza la información de una instancia de Calificafion.
     *
     * @param CalificacionGrupoId Identificador de la instancia a actualizar
     * @param newCalificacionGrupoEntity Instancia de CalificafionEntity con los nuevos datos.
     * @return Instancia de CalificafionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    public CalificacionGrupoEntity updateCalificacionGrupo(Long CalificacionGrupoId, CalificacionGrupoEntity newCalificacionGrupoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor con id = {0}", CalificacionGrupoId);
         if(CalificacionGrupoPersistence.find(CalificacionGrupoId) != null)
        {
            if (newCalificacionGrupoEntity.getCalificacion().equals((Integer)newCalificacionGrupoEntity.getCalificacion()))
            {
            }
                else{
                        throw new BusinessLogicException("La calificación no es entera");
                    }
            if (newCalificacionGrupoEntity.getCalificacion() < 0 || newCalificacionGrupoEntity.getCalificacion() > 5)
            {
                throw new BusinessLogicException("La calificación no se encuentra entre 0 y 5");
                
            }
            if (newCalificacionGrupoEntity.getMensaje() != null)
            {
                if(newCalificacionGrupoEntity.getMensaje().length() > 300)
                {
                    throw new BusinessLogicException("El mensaje supera el número de caracteres permitidos");
                    
                }
                
            }
        }
        CalificacionGrupoEntity newCalificacionGrupoEntity2 = CalificacionGrupoPersistence.update(newCalificacionGrupoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", CalificacionGrupoId);
        return newCalificacionGrupoEntity2;
    }

    /**
     * Elimina una instancia de Calificafion de la base de datos.
     *
     * @param CalificacionGrupoId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el autor tiene libros asociados.
     */
    public void deleteCalificacionGrupo(Long CalificacionGrupoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", CalificacionGrupoId);
        CalificacionGrupoPersistence.delete(CalificacionGrupoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", CalificacionGrupoId);
    }
}