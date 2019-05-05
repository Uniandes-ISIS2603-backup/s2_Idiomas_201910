/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.entities.CalificacionEntity;
import co.edu.uniandes.csw.idiomas.dtos.CalificacionDTO;
import co.edu.uniandes.csw.idiomas.dtos.CalificacionDetailDTO;
import co.edu.uniandes.csw.idiomas.ejb.CalificacionLogic;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author jdruedaa
 */
@Path("grades")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource {
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());
    
    @Inject
    private CalificacionLogic logica;
    
    /**
     * Busca y devuelve todas las Calificacions que existen en la aplicacion.
     *
     * @return JSONArray {@link CalificacionDetailDTO} - Las Calificacions
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CalificacionDetailDTO> getCalificaciones() {
        LOGGER.info("CalificacionResource getCalificacions: input: void");
        List<CalificacionDetailDTO> listaCalificaciones = listEntity2DetailDTO(logica.getCalificaciones());
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", listaCalificaciones.get(0).getId());
        return listaCalificaciones;
    } 
    
    @POST
    public CalificacionDTO createCalificacion (CalificacionDTO calificacion) throws BusinessLogicException
    {
        return calificacion;
    }
    @DELETE
    @Path("{CalificacionId: \\d+}")
    public Integer deleteCalificacion(@PathParam("idCalificacion") Integer calificacionId) {
        return calificacionId;
    }
    @GET
    @Path("{CalificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("CalificacionId") Long CalificacionId)
    {
        LOGGER.log(Level.INFO, "CalificacionGrupoResource getCalificacionGrupo: input: {0}", CalificacionId);
        CalificacionEntity CalificacionEntity = logica.getCalificacion(CalificacionId);
        if (CalificacionEntity == null) {
            throw new WebApplicationException("El recurso /CalificacionGrupoes/" + CalificacionId + " no existe.", 404);
        }
        CalificacionDTO detailDTO = new CalificacionDTO(CalificacionEntity);
        LOGGER.log(Level.INFO, "CalificacionGrupoResource getCalificacionGrupo: output: {0}", detailDTO);
        return detailDTO;
    }

    
    /**
     * Actualiza la calificacion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param calificacionId Identificador de la calificacion que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param calificacion {@link calificacionDetailDTO} La calificacion que se desea
     * guardar.
     * @return JSON {@link calificacionDetailDTO} - La calificacion guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion a
     * actualizar.
     */
    @PUT
    @Path("{CalificacionId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacion) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "calificacionResource updatecalificacion: input: id:{0} , calificacion: {1}", new Object[]{calificacionId, calificacion});
        calificacion.setId(calificacionId);
        if (logica.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO detailDTO = new CalificacionDTO(logica.updateCalificacion(calificacionId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "calificacionResource updatecalificacion: output: {0}", detailDTO);
        return detailDTO;

    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CalificacionesEntity a una lista de
     * objetos CalificacionDetailDTO (json)
     *
     * @param entityList corresponde a la lista de Calificaciones de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de Comentarios en forma DTO (json)
     */
    public List<CalificacionDetailDTO> listEntity2DetailDTO(List<CalificacionEntity> entityList) {
        List<CalificacionDetailDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDetailDTO(entity));
        }
        return list;
    }
}