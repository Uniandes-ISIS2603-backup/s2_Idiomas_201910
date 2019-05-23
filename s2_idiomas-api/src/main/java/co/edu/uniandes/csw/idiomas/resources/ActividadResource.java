/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.ActividadDTO;
import co.edu.uniandes.csw.idiomas.dtos.ActividadDetailDTO;
import co.edu.uniandes.csw.idiomas.ejb.ActividadLogic;
import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Clase que define los servicios de la clase actividad
 * @author g.cubillosb
 */
@Path("actividades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ActividadResource {
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Atributo que representa el logger correspondiente de la clase. Para poder
     * enviar mensajes.
     */
    private static final Logger LOGGER = Logger.getLogger(ActividadResource.class.getName());
    
    /**
     * Permite acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private ActividadLogic actividadLogic; 
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------
    
    /**
     * Crea una nueva actividad con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param actividad {@link ActividadDTO} - La actividad que se desea
     * guardar.
     * @return JSON {@link ActividadDTO} - La actividad guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la actividad.
     */
    @POST
    public ActividadDTO createActividad(ActividadDTO actividad) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "ActividadResource createActividad: input: {0}", actividad);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        ActividadEntity actividadEntity = actividad.toEntity();
        // Invoca la lógica para crear la actividad nueva
        ActividadEntity nuevoActividadEntity = actividadLogic.createActividad(actividadEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ActividadDTO nuevoActividadDTO = new ActividadDTO(nuevoActividadEntity);
        LOGGER.log(Level.INFO, "ActividadResource createActividad: output: {0}", nuevoActividadDTO);
        return nuevoActividadDTO;
    }

    /**
     * Busca y devuelve todas las actividades que existen en la aplicacion.
     *
     * @return JSONArray {@link ActividadDetailDTO} - Las actividades
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ActividadDetailDTO> getActividades() {
        LOGGER.info("ActividadResource getActividades: input: void");
        List<ActividadDetailDTO> listaActividades = listEntity2DetailDTO(actividadLogic.getActividades());
        LOGGER.log(Level.INFO, "ActividadResource getActividades: output: {0}", listaActividades);
        return listaActividades;
    }

    /**
     * Busca la actividad con el id asociado recibido en la URL y la devuelve.
     *
     * @param actividadesId Identificador de la actividad que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ActividadDetailDTO} - La actividad buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la actividad.
     */
    @GET
    @Path("{actividadesId: \\d+}")
    public ActividadDetailDTO getActividad(@PathParam("actividadesId") Long actividadesId) 
    {
        LOGGER.log(Level.INFO, "ActividadResource getActividad: input: {0}", actividadesId);
        ActividadEntity actividadEntity = actividadLogic.getActividad(actividadesId);
        if (actividadEntity == null) {
            throw new WebApplicationException("El recurso /actividades/" + actividadesId + " no existe.", 404);
        }
        ActividadDetailDTO detailDTO = new ActividadDetailDTO(actividadEntity);
        LOGGER.log(Level.INFO, "ActividadResource getActividad: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la actividad con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param actividadesId Identificador de la actividad que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param actividad {@link ActividadDetailDTO} La actividad que se desea
     * guardar.
     * @return JSON {@link ActividadDetailDTO} - La actividad guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la actividad a
     * actualizar.
     */
    @PUT
    @Path("{actividadesId: \\d+}")
    public ActividadDetailDTO updateActividad(@PathParam("actividadesId") Long actividadesId, ActividadDetailDTO actividad) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ActividadResource updateActividad: input: id:{0} , actividad: {1}", new Object[]{actividadesId, actividad});
        actividad.setId(actividadesId);
        if (actividadLogic.getActividad(actividadesId) == null) {
            throw new WebApplicationException("El recurso /actividades/" + actividadesId + " no existe.", 404);
        }
        ActividadDetailDTO detailDTO = new ActividadDetailDTO(actividadLogic.updateActividad(actividadesId, actividad.toEntity()));
        LOGGER.log(Level.INFO, "ActividadResource updateActividad: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la actividad con el id asociado recibido en la URL.
     *
     * @param actividadesId Identificador de la actividad que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la actividad.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la actividad.
     */
    @DELETE
    @Path("{actividadesId: \\d+}")
    public void deleteActividad(@PathParam("actividadesId") Long actividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ActividadResource deleteActividad: input: {0}", actividadesId);
        if (actividadLogic.getActividad(actividadesId) == null) {
            throw new WebApplicationException("El recurso /actividades/" + actividadesId + " no existe.", 404);
        }
        actividadLogic.deleteActividad(actividadesId);
        LOGGER.info("ActividadResource deleteActividad: output: void");
    }

    /**
     * Conexión con el servicio de comentarios para una actividad.
     * {@link ActividadComentarioResourceResource}
     *
     * Este método conecta la ruta de /actividades con las rutas de /comentarios que
     * dependen de la actividad, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los comentarios de una actividad.
     *
     * @param actividadesId El ID de la actividad con respecto a la cual se
     * accede al servicio.
     * @return El servicio de comentarios para esta actividad en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la actividad.
     */
    @Path("{actividadesId: \\d+}/comentarios")
    public Class<ComentarioResource> getActividadComentarioResource(@PathParam("actividadesId") Long actividadesId) {
        if (actividadLogic.getActividad(actividadesId) == null) {
            throw new WebApplicationException("El recurso /actividades/" + actividadesId + " no existe.", 404);
        }
        return ComentarioResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ActividadEntity a una lista de
     * objetos ActividadDetailDTO (json)
     *
     * @param entityList corresponde a la lista de actividades de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de actividades en forma DTO (json)
     */
    private List<ActividadDetailDTO> listEntity2DetailDTO(List<ActividadEntity> entityList) {
        List<ActividadDetailDTO> list = new ArrayList<>();
        for (ActividadEntity entity : entityList) {
            list.add(new ActividadDetailDTO(entity));
        }
        return list;
    }
    
    
    
}
