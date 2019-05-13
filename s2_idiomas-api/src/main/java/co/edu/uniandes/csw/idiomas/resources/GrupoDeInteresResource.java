/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.GrupoDeInteresDTO;
import co.edu.uniandes.csw.idiomas.dtos.GrupoDeInteresDetailDTO;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
 * @author estudiante
 */
@Path("grupoDeInteres")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class GrupoDeInteresResource {
        // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    private static final Logger LOGGER = Logger.getLogger(EncuentroResource.class.getName());
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------
    
    /**
     * Crea un nuevo encuentro con la información que se recibe en el cuerpo
     * de la petición y se regresa un objeto idéntico, con un id autogenerado
     * por la base de datos.
     * 
     * @param grupoDeInteres {@link GrupoDeINteresDTO} - El grupo a guardar.
     * @return JSON {@link GrupoDeINteresDTO} - El grupo guardado con el atributo
     * autogenerado.
     */
    @POST
    public GrupoDeInteresDTO createGrupoDeInteres (GrupoDeInteresDTO grupoDeInteres)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "GrupoDeInteresResource createGrupoDeInteres: input: {0}", grupoDeInteres);
//        ActividadDTO actividadDTO = new ActividadDTO(authorLogic.createAuthor(actividad.toEntity()));
//        LOGGER.log(Level.INFO, "ActividadResource createActividad: output: {0}", actividadDTO);
//        return actividadDTO;
        return grupoDeInteres;
    }
    
    /**
     * Actualiza el encuentro asociado al id recibido en la URL con la información
     * que se recibe en el cuerpo de la petición.
     * 
     * @param grupoDeInteresId El identificador del grupo que se quiere. Debe ser
     * una cadena de dígitos.
     * @param grupoDeInteres {@link GrupoDeInteresDTO} - El grupo que se desea guardar.
     * @return JSON {@link GrupoDeInteresDTO} - El grupo guardado.
     */
    @PUT
    @Path("{grupoDeInteresId: \\d+}")
    public GrupoDeInteresDTO updateGrupoDeInteres (@PathParam("grupoDeInteresId") Long grupoDeInteresId, GrupoDeInteresDTO grupoDeInteres)throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "GrupoDeInteresResource updateGrupoDeInteres: input: encuentroId: {0}, encuentro: {1}", 
                new Object[]{grupoDeInteresId, grupoDeInteres});
//        actividad.setId(actividadId);
//        if(actividadLogic.getActividad(actividadId) == null)
//        {
//            throw new WebApplicationException("El recurso /actividad/" + actividadId + " no existe.", 404);
//        }
//        ActividadDetailDTO detailDTO = new ActividadDetailDTO(actividadLogic.updateActividad(actividadId, actividad.toEntity()));
//        LOGGER.log(Level.INFO, "ActividadResource updateActividad: output: {0}", detailDTO);
//        return detailDTO;
        return grupoDeInteres;
    }
    
    /**
     * Busca y devuelve el encuentro con el id asociado recibido en la URL
     * 
     * @param grupoDeInteresId El identificador del grupo que se busca. Debe ser
     * una cadena de dígitos.
     * @return JSON {@link GrupoDeInteresDTO} - El grupo buscado.
     */
    @GET
    @Path("{grupoDeInteresId: \\d+}")
    public GrupoDeInteresDetailDTO getGrupoDeInteres (@PathParam("grupoDeInteresId") Long grupoDeInteresId)
    {
        LOGGER.log(Level.INFO, "GrupoDeInteresResource getGrupoDeInteres: input: grupoDeInteresId: {0}", grupoDeInteresId);
//        ActividadEntity actividadEntity = actividadLogic.getActividad(actividadId);
//        if(ActividadEntity == null)
//        {
//            throw new WebApplicationException("El recurso /actividad/" + actividadId + " no existe.", 404;
//        }
//        ActividadDetailDTO detailDTO = new ActividadDetailDTO(actividadEntity);
//        LOGGER.log(Level.INFO, "ActividadResource getActividad: output: actividadId {0}"), detailDTO);
//        return detailDTO;
        return new GrupoDeInteresDetailDTO();
    }
    
    @DELETE
    @Path("{grupoDeInteresId: \\d+}")
    public void deleteGrupoDeInteres (@PathParam("grupoDeInteresId") Long grupoDeInteresId)
    {
        LOGGER.log(Level.INFO, "GrupoDeInteresResource deleteGrupoDeInteres: input: {0}", grupoDeInteresId);
//        if (actividadLogic.getActividad(actividadId) == null)
//        {
//            throw new WebApplicationException("El recurso /actividad/" + actividadId + "no existe.", 404);
//        }
//        actividadLogic.deleteActividad(actividadId)
    }
}
