/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.CoordinadorDTO;
import co.edu.uniandes.csw.idiomas.dtos.CoordinadorDetailDTO;
import co.edu.uniandes.csw.idiomas.ejb.CoordinadorLogic;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
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
 * Clase que define los servicios de la clase coordinador
 * @author g.cubillosb
 */
@Path("coordinadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CoordinadorResource {
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Atributo que representa el logger correspondiente de la clase. Para poder
     * enviar mensajes.
     */
    private static final Logger LOGGER = Logger.getLogger(CoordinadorResource.class.getName());
    
    /**
     * Permite acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private CoordinadorLogic coordinadorLogic; 
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------
    
    /**
     * Crea una nueva coordinador con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param coordinador {@link CoordinadorDTO} - La coordinador que se desea
     * guardar.
     * @return JSON {@link CoordinadorDTO} - La coordinador guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la coordinador.
     */
    @POST
    public CoordinadorDTO createCoordinador(CoordinadorDTO coordinador) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "CoordinadorResource createCoordinador: input: {0}", coordinador);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CoordinadorEntity coordinadorEntity = coordinador.toEntity();
        // Invoca la lógica para crear la coordinador nueva
        CoordinadorEntity nuevoCoordinadorEntity = coordinadorLogic.createCoordinador(coordinadorEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CoordinadorDTO nuevoCoordinadorDTO = new CoordinadorDTO(nuevoCoordinadorEntity);
        LOGGER.log(Level.INFO, "CoordinadorResource createCoordinador: output: {0}", nuevoCoordinadorDTO);
        return nuevoCoordinadorDTO;
    }

    /**
     * Busca y devuelve todas las coordinadores que existen en la aplicacion.
     *
     * @return JSONArray {@link CoordinadorDetailDTO} - Las coordinadores
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CoordinadorDetailDTO> getCoordinadores() {
        LOGGER.info("CoordinadorResource getCoordinadores: input: void");
        List<CoordinadorDetailDTO> listaCoordinadores = listEntity2DetailDTO(coordinadorLogic.getCoordinadores());
        LOGGER.log(Level.INFO, "CoordinadorResource getCoordinadores: output: {0}", listaCoordinadores);
        return listaCoordinadores;
    }

    /**
     * Busca la coordinador con el id asociado recibido en la URL y la devuelve.
     *
     * @param coordinadoresId Identificador de la coordinador que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CoordinadorDetailDTO} - La coordinador buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la coordinador.
     */
    @GET
    @Path("{coordinadoresId: \\d+}")
    public CoordinadorDetailDTO getCoordinador(@PathParam("coordinadoresId") Long coordinadoresId) 
    {
        LOGGER.log(Level.INFO, "CoordinadorResource getCoordinador: input: {0}", coordinadoresId);
        CoordinadorEntity coordinadorEntity = coordinadorLogic.getCoordinador(coordinadoresId);
        if (coordinadorEntity == null) {
            throw new WebApplicationException("El recurso /coordinadores/" + coordinadoresId + " no existe.", 404);
        }
        CoordinadorDetailDTO detailDTO = new CoordinadorDetailDTO(coordinadorEntity);
        LOGGER.log(Level.INFO, "CoordinadorResource getCoordinador: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la coordinador con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param coordinadoresId Identificador de la coordinador que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param coordinador {@link CoordinadorDetailDTO} La coordinador que se desea
     * guardar.
     * @return JSON {@link CoordinadorDetailDTO} - La coordinador guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la coordinador a
     * actualizar.
     */
    @PUT
    @Path("{coordinadoresId: \\d+}")
    public CoordinadorDetailDTO updateCoordinador(@PathParam("coordinadoresId") Long coordinadoresId, CoordinadorDetailDTO coordinador) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CoordinadorResource updateCoordinador: input: id:{0} , coordinador: {1}", new Object[]{coordinadoresId, coordinador});
        coordinador.setId(coordinadoresId);
        if (coordinadorLogic.getCoordinador(coordinadoresId) == null) {
            throw new WebApplicationException("El recurso /coordinadores/" + coordinadoresId + " no existe.", 404);
        }
        CoordinadorDetailDTO detailDTO = new CoordinadorDetailDTO(coordinadorLogic.updateCoordinador(coordinadoresId, coordinador.toEntity()));
        LOGGER.log(Level.INFO, "CoordinadorResource updateCoordinador: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la coordinador con el id asociado recibido en la URL.
     *
     * @param coordinadoresId Identificador de la coordinador que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la coordinador.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la coordinador.
     */
    @DELETE
    @Path("{coordinadoresId: \\d+}")
    public void deleteCoordinador(@PathParam("coordinadoresId") Long coordinadoresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CoordinadorResource deleteCoordinador: input: {0}", coordinadoresId);
        if (coordinadorLogic.getCoordinador(coordinadoresId) == null) {
            throw new WebApplicationException("El recurso /coordinadores/" + coordinadoresId + " no existe.", 404);
        }
        coordinadorLogic.deleteCoordinador(coordinadoresId);
        LOGGER.info("CoordinadorResource deleteCoordinador: output: void");
    }

    /**
     * Conexión con el servicio de comentarios para una coordinador.
     * {@link CoordinadorComentarioResourceResource}
     *
     * Este método conecta la ruta de /coordinadores con las rutas de /comentarios que
     * dependen de la coordinador, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los comentarios de una coordinador.
     *
     * @param coordinadoresId El ID de la coordinador con respecto a la cual se
     * accede al servicio.
     * @return El servicio de comentarios para esta coordinador en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la coordinador.
     */
    @Path("{coordinadoresId: \\d+}/actividades")
    public Class<CoordinadoresActividadesResource> getCoordinadoresActividadesResource(@PathParam("coordinadoresId") Long coordinadoresId) {
        if (coordinadorLogic.getCoordinador(coordinadoresId) == null) {
            throw new WebApplicationException("El recurso /coordinadores/" + coordinadoresId + " no existe.", 404);
        }
        return CoordinadoresActividadesResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CoordinadorEntity a una lista de
     * objetos CoordinadorDetailDTO (json)
     *
     * @param entityList corresponde a la lista de coordinadores de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de coordinadores en forma DTO (json)
     */
    private List<CoordinadorDetailDTO> listEntity2DetailDTO(List<CoordinadorEntity> entityList) {
        List<CoordinadorDetailDTO> list = new ArrayList<>();
        for (CoordinadorEntity entity : entityList) {
            list.add(new CoordinadorDetailDTO(entity));
        }
        return list;
    }
    
    
    
}
