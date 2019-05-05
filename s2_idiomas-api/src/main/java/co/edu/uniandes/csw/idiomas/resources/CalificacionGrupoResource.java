/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.CalificacionGrupoDTO;
import co.edu.uniandes.csw.idiomas.ejb.CalificacionGrupoLogic;
import co.edu.uniandes.csw.idiomas.entities.CalificacionGrupoEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jd.ruedaa
 */
@Path("grupoGrades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CalificacionGrupoResource {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    private static final Logger LOGGER = Logger.getLogger(CalificacionGrupoResource.class.getName());
    
     @Inject
    private CalificacionGrupoLogic logica;
    
    /**
     * Crea una nueva CalificacionGrupo con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param CalificacionGrupo {@link CalificacionGrupoDTO} - La CalificacionGrupo que se desea
     * guardar.
     * @return JSON {@link CalificacionGrupoDTO} - La CalificacionGrupo guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando la CalificacionGrupo no cumple con los requisitos.
     */
    @POST
    public CalificacionGrupoDTO createCalificacionGrupo(CalificacionGrupoDTO CalificacionGrupo) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionGrupoResource createCalificacionGrupo: input: {0}", CalificacionGrupo);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CalificacionGrupoEntity CalificacionGrupoEntity = CalificacionGrupo.toEntity();
        // Invoca la lógica para crear una CalificacionGrupo nueva
        CalificacionGrupoEntity CalificacionGrupoResEntity = logica.createCalificacionGrupo(CalificacionGrupoEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CalificacionGrupoDTO CalificacionGrupoDTO = new CalificacionGrupoDTO(CalificacionGrupoResEntity);
        LOGGER.log(Level.INFO, "CalificacionGrupoResource createCalificacionGrupo: output: {0}", CalificacionGrupoDTO);
        return CalificacionGrupoDTO;
    }
    
    /**
     * Busca el CalificacionGrupo con el id asociado recibido en la URL y la devuelve.
     *
     * @param CalificacionGrupoId Identificador de la CalificacionGrupo que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CalificacionGrupoDetailDTO} - La CalificacionGrupo buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CalificacionGrupo.
     */
    @GET
    @Path("{CalificacionGrupoId: \\d+}")
    public CalificacionGrupoDTO getCalificacionGrupo(@PathParam("CalificacionGrupoId") Long CalificacionGrupoId)
    {
        LOGGER.log(Level.INFO, "CalificacionGrupoResource getCalificacionGrupo: input: {0}", CalificacionGrupoId);
        CalificacionGrupoEntity CalificacionGrupoEntity = logica.getCalificacionGrupo(CalificacionGrupoId);
        if (CalificacionGrupoEntity == null) {
            throw new WebApplicationException("El recurso /CalificacionGrupoes/" + CalificacionGrupoId + " no existe.", 404);
        }
        CalificacionGrupoDTO detailDTO = new CalificacionGrupoDTO(CalificacionGrupoEntity);
        LOGGER.log(Level.INFO, "CalificacionGrupoResource getCalificacionGrupo: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Borra la CalificacionGrupo con el id asociado recibido en la URL.
     *
     * @param CalificacionGrupoId Identificador de la CalificacionGrupo que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la CalificacionGrupo.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CalificacionGrupo.
     */
    @DELETE
    @Path("{CalificacionGrupoId: \\d+}")
    public void deleteCalificacionGrupo(@PathParam("CalificacionGrupoId") Long CalificacionGrupoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionGrupoResource deleteCalificacionGrupo: input: {0}", CalificacionGrupoId);
        if (logica.getCalificacionGrupo(CalificacionGrupoId) == null) {
            throw new WebApplicationException("El recurso /CalificacionGrupos/" + CalificacionGrupoId + " no existe.", 404);
        }
        logica.deleteCalificacionGrupo(CalificacionGrupoId);
        LOGGER.info("CalificacionGrupoResource deleteCalificacionGrupo: output: void");
    }
    
     /**
     * Actualiza la CalificacionGrupo con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param CalificacionGrupoId Identificador de la CalificacionGrupo que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param CalificacionGrupo {@link CalificacionGrupoDetailDTO} La CalificacionGrupo que se desea
     * guardar.
     * @return JSON {@link CalificacionGrupoDetailDTO} - La CalificacionGrupo guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CalificacionGrupo a
     * actualizar.
     */
    @PUT
    @Path("{CalificacionGrupoId: \\d+}")
    public CalificacionGrupoDTO updateCalificacionGrupo(@PathParam("CalificacionGrupoId") Long CalificacionGrupoId, CalificacionGrupoDTO CalificacionGrupo) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionGrupoResource updateCalificacionGrupo: input: id:{0} , CalificacionGrupo: {1}", new Object[]{CalificacionGrupoId, CalificacionGrupo});
        CalificacionGrupo.setId(CalificacionGrupoId);
        if (logica.getCalificacionGrupo(CalificacionGrupoId) == null) {
            throw new WebApplicationException("El recurso /CalificacionGrupoes/" + CalificacionGrupoId + " no existe.", 404);
        }
        CalificacionGrupoDTO detailDTO = new CalificacionGrupoDTO(logica.updateCalificacionGrupo(CalificacionGrupoId, CalificacionGrupo.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionGrupoResource updateCalificacionGrupo: output: {0}", detailDTO);
        return detailDTO;

    }
}
