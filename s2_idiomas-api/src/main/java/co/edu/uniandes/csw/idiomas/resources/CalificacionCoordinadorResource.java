/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.CalificacionCoordinadorDTO;
import co.edu.uniandes.csw.idiomas.ejb.CalificacionCoordinadorLogic;
import co.edu.uniandes.csw.idiomas.entities.CalificacionCoordinadorEntity;
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
@Path("coordinadorGrades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CalificacionCoordinadorResource {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    private static final Logger LOGGER = Logger.getLogger(CalificacionCoordinadorResource.class.getName());
    
     @Inject
    private CalificacionCoordinadorLogic logica;
    
    /**
     * Crea una nueva CalificacionCoordinador con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param CalificacionCoordinador {@link CalificacionCoordinadorDTO} - La CalificacionCoordinador que se desea
     * guardar.
     * @return JSON {@link CalificacionCoordinadorDTO} - La CalificacionCoordinador guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando la CalificacionCoordinador no cumple con los requisitos.
     */
    @POST
    public CalificacionCoordinadorDTO createCalificacionCoordinador(CalificacionCoordinadorDTO CalificacionCoordinador) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionCoordinadorResource createCalificacionCoordinador: input: {0}", CalificacionCoordinador);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CalificacionCoordinadorEntity CalificacionCoordinadorEntity = CalificacionCoordinador.toEntity();
        // Invoca la lógica para crear una CalificacionCoordinador nueva
        CalificacionCoordinadorEntity CalificacionCoordinadorResEntity = logica.createCalificacionCoordinador(CalificacionCoordinadorEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CalificacionCoordinadorDTO CalificacionCoordinadorDTO = new CalificacionCoordinadorDTO(CalificacionCoordinadorResEntity);
        LOGGER.log(Level.INFO, "CalificacionCoordinadorResource createCalificacionCoordinador: output: {0}", CalificacionCoordinadorDTO);
        return CalificacionCoordinadorDTO;
    }
    
    /**
     * Busca el CalificacionCoordinador con el id asociado recibido en la URL y la devuelve.
     *
     * @param CalificacionCoordinadorId Identificador de la CalificacionCoordinador que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CalificacionCoordinadorDetailDTO} - La CalificacionCoordinador buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CalificacionCoordinador.
     */
    @GET
    @Path("{CalificacionCoordinadorId: \\d+}")
    public CalificacionCoordinadorDTO getCalificacionCoordinador(@PathParam("CalificacionCoordinadorId") Long CalificacionCoordinadorId)
    {
        LOGGER.log(Level.INFO, "CalificacionCoordinadorResource getCalificacionCoordinador: input: {0}", CalificacionCoordinadorId);
        CalificacionCoordinadorEntity CalificacionCoordinadorEntity = logica.getCalificacionCoordinador(CalificacionCoordinadorId);
        if (CalificacionCoordinadorEntity == null) {
            throw new WebApplicationException("El recurso /CalificacionCoordinadores/" + CalificacionCoordinadorId + " no existe.", 404);
        }
        CalificacionCoordinadorDTO detailDTO = new CalificacionCoordinadorDTO(CalificacionCoordinadorEntity);
        LOGGER.log(Level.INFO, "CalificacionCoordinadorResource getCalificacionCoordinador: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Borra la CalificacionCoordinador con el id asociado recibido en la URL.
     *
     * @param CalificacionCoordinadorId Identificador de la CalificacionCoordinador que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la CalificacionCoordinador.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CalificacionCoordinador.
     */
    @DELETE
    @Path("{CalificacionCoordinadorId: \\d+}")
    public void deleteCalificacionCoordinador(@PathParam("CalificacionCoordinadorId") Long CalificacionCoordinadorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionCoordinadorResource deleteCalificacionCoordinador: input: {0}", CalificacionCoordinadorId);
        if (logica.getCalificacionCoordinador(CalificacionCoordinadorId) == null) {
            throw new WebApplicationException("El recurso /CalificacionCoordinadors/" + CalificacionCoordinadorId + " no existe.", 404);
        }
        logica.deleteCalificacionCoordinador(CalificacionCoordinadorId);
        LOGGER.info("CalificacionCoordinadorResource deleteCalificacionCoordinador: output: void");
    }
    
     /**
     * Actualiza la CalificacionCoordinador con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param CalificacionCoordinadorId Identificador de la CalificacionCoordinador que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param CalificacionCoordinador {@link CalificacionCoordinadorDetailDTO} La CalificacionCoordinador que se desea
     * guardar.
     * @return JSON {@link CalificacionCoordinadorDetailDTO} - La CalificacionCoordinador guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CalificacionCoordinador a
     * actualizar.
     */
    @PUT
    @Path("{CalificacionCoordinadorId: \\d+}")
    public CalificacionCoordinadorDTO updateCalificacionCoordinador(@PathParam("CalificacionCoordinadorId") Long CalificacionCoordinadorId, CalificacionCoordinadorDTO CalificacionCoordinador) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionCoordinadorResource updateCalificacionCoordinador: input: id:{0} , CalificacionCoordinador: {1}", new Object[]{CalificacionCoordinadorId, CalificacionCoordinador});
        CalificacionCoordinador.setId(CalificacionCoordinadorId);
        if (logica.getCalificacionCoordinador(CalificacionCoordinadorId) == null) {
            throw new WebApplicationException("El recurso /CalificacionCoordinadores/" + CalificacionCoordinadorId + " no existe.", 404);
        }
        CalificacionCoordinadorDTO detailDTO = new CalificacionCoordinadorDTO(logica.updateCalificacionCoordinador(CalificacionCoordinadorId, CalificacionCoordinador.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionCoordinadorResource updateCalificacionCoordinador: output: {0}", detailDTO);
        return detailDTO;

    }
}
