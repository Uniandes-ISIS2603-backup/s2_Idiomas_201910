/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.CalificacionAdministradorDTO;
import co.edu.uniandes.csw.idiomas.ejb.CalificacionAdministradorLogic;
import co.edu.uniandes.csw.idiomas.entities.CalificacionAdministradorEntity;
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
@Path("administradorGrades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CalificacionAdministradorResource {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    private static final Logger LOGGER = Logger.getLogger(CalificacionAdministradorResource.class.getName());
    
     @Inject
    private CalificacionAdministradorLogic logica;
    
    /**
     * Crea una nueva CalificacionAdministrador con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param CalificacionAdministrador {@link CalificacionAdministradorDTO} - La CalificacionAdministrador que se desea
     * guardar.
     * @return JSON {@link CalificacionAdministradorDTO} - La CalificacionAdministrador guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando la CalificacionAdministrador no cumple con los requisitos.
     */
    @POST
    public CalificacionAdministradorDTO createCalificacionAdministrador(CalificacionAdministradorDTO CalificacionAdministrador) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionAdministradorResource createCalificacionAdministrador: input: {0}", CalificacionAdministrador);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CalificacionAdministradorEntity CalificacionAdministradorEntity = CalificacionAdministrador.toEntity();
        // Invoca la lógica para crear una CalificacionAdministrador nueva
        CalificacionAdministradorEntity CalificacionAdministradorResEntity = logica.createCalificacionAdministrador(CalificacionAdministradorEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CalificacionAdministradorDTO CalificacionAdministradorDTO = new CalificacionAdministradorDTO(CalificacionAdministradorResEntity);
        LOGGER.log(Level.INFO, "CalificacionAdministradorResource createCalificacionAdministrador: output: {0}", CalificacionAdministradorDTO);
        return CalificacionAdministradorDTO;
    }
    
    /**
     * Busca el CalificacionAdministrador con el id asociado recibido en la URL y la devuelve.
     *
     * @param CalificacionAdministradorId Identificador de la CalificacionAdministrador que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CalificacionAdministradorDetailDTO} - La CalificacionAdministrador buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CalificacionAdministrador.
     */
    @GET
    @Path("{CalificacionAdministradorId: \\d+}")
    public CalificacionAdministradorDTO getCalificacionAdministrador(@PathParam("CalificacionAdministradorId") Long CalificacionAdministradorId)
    {
        LOGGER.log(Level.INFO, "CalificacionAdministradorResource getCalificacionAdministrador: input: {0}", CalificacionAdministradorId);
        CalificacionAdministradorEntity CalificacionAdministradorEntity = logica.getCalificacionAdministrador(CalificacionAdministradorId);
        if (CalificacionAdministradorEntity == null) {
            throw new WebApplicationException("El recurso /CalificacionAdministradores/" + CalificacionAdministradorId + " no existe.", 404);
        }
        CalificacionAdministradorDTO detailDTO = new CalificacionAdministradorDTO(CalificacionAdministradorEntity);
        LOGGER.log(Level.INFO, "CalificacionAdministradorResource getCalificacionAdministrador: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Borra la CalificacionAdministrador con el id asociado recibido en la URL.
     *
     * @param CalificacionAdministradorId Identificador de la CalificacionAdministrador que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la CalificacionAdministrador.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CalificacionAdministrador.
     */
    @DELETE
    @Path("{CalificacionAdministradorId: \\d+}")
    public void deleteCalificacionAdministrador(@PathParam("CalificacionAdministradorId") Long CalificacionAdministradorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionAdministradorResource deleteCalificacionAdministrador: input: {0}", CalificacionAdministradorId);
        if (logica.getCalificacionAdministrador(CalificacionAdministradorId) == null) {
            throw new WebApplicationException("El recurso /CalificacionAdministradors/" + CalificacionAdministradorId + " no existe.", 404);
        }
        logica.deleteCalificacionAdministrador(CalificacionAdministradorId);
        LOGGER.info("CalificacionAdministradorResource deleteCalificacionAdministrador: output: void");
    }
    
     /**
     * Actualiza la CalificacionAdministrador con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param CalificacionAdministradorId Identificador de la CalificacionAdministrador que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param CalificacionAdministrador {@link CalificacionAdministradorDetailDTO} La CalificacionAdministrador que se desea
     * guardar.
     * @return JSON {@link CalificacionAdministradorDetailDTO} - La CalificacionAdministrador guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la CalificacionAdministrador a
     * actualizar.
     */
    @PUT
    @Path("{CalificacionAdministradorId: \\d+}")
    public CalificacionAdministradorDTO updateCalificacionAdministrador(@PathParam("CalificacionAdministradorId") Long CalificacionAdministradorId, CalificacionAdministradorDTO CalificacionAdministrador) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CalificacionAdministradorResource updateCalificacionAdministrador: input: id:{0} , CalificacionAdministrador: {1}", new Object[]{CalificacionAdministradorId, CalificacionAdministrador});
        CalificacionAdministrador.setId(CalificacionAdministradorId);
        if (logica.getCalificacionAdministrador(CalificacionAdministradorId) == null) {
            throw new WebApplicationException("El recurso /CalificacionAdministradores/" + CalificacionAdministradorId + " no existe.", 404);
        }
        CalificacionAdministradorDTO detailDTO = new CalificacionAdministradorDTO(logica.updateCalificacionAdministrador(CalificacionAdministradorId, CalificacionAdministrador.toEntity()));
        LOGGER.log(Level.INFO, "CalificacionAdministradorResource updateCalificacionAdministrador: output: {0}", detailDTO);
        return detailDTO;

    }
}
