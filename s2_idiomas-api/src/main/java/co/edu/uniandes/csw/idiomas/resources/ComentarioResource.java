/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.idiomas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.idiomas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.idiomas.mappers.WebApplicationExceptionMapper;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author se.gamboa
 */
@Path("comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped

public class ComentarioResource {

    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    /**
     * Atributo que representa el logger correspondiente de la clase. Para poder
     * enviar mensajes.
     */
    private static final Logger LOGGER = Logger.getLogger(ComentarioResource.class.getName());
    Calendar now = Calendar.getInstance();
    /**
     * Permite acceder a la lógica de la aplicación. Es una inyección de
     * dependenciasAS.
     */
    @Inject
    private ComentarioLogic ComentarioLogic;

    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------
    /**
     * Crea una nueva Comentario con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * @param Comentario {@link ComentarioDTO} - La Comentario que se desea
     * guardar.
     * @return JSON {@link ComentarioDTO} - La Comentario guardada con el
     * atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la Comentario.
     */
    @POST
    public ComentarioDetailDTO createComentario(ComentarioDetailDTO Comentario) throws BusinessLogicException, ParseException {
        LOGGER.log(Level.INFO, "ComentarioResource createComentario: input: {0}", Comentario);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        System.out.println(Comentario.getPersona());
        ComentarioEntity ComentarioEntity = Comentario.toEntity();
        System.out.println(ComentarioEntity.getPersona());
        ComentarioEntity.setFecha(now.getTime());
        // Invoca la lógica para crear la Comentario nueva
        ComentarioEntity nuevoComentarioEntity = ComentarioLogic.createComment(ComentarioEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        ComentarioDetailDTO nuevoComentarioDTO = new ComentarioDetailDTO(nuevoComentarioEntity);
        LOGGER.log(Level.INFO, "ComentarioResource createComentario: output: {0}", nuevoComentarioDTO);
        return nuevoComentarioDTO;
    }

    /**
     * Busca y devuelve todas las Comentarios que existen en la aplicacion.
     *
     * @return JSONArray {@link ComentarioDetailDTO} - Las Comentarios
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ComentarioDetailDTO> getComentarios() {
        LOGGER.info("ComentarioResource getComentarios: input: void");
        List<ComentarioDetailDTO> listaComentarios = listEntity2DetailDTO(ComentarioLogic.getComentarios());
        LOGGER.log(Level.INFO, "ComentarioResource getComentarios: output: {0}");
        return listaComentarios;
    }

    @GET
    @Path("/fechas")
    public List<ComentarioDetailDTO> getComentariosFecha(@QueryParam("fecha1") String fechas1, @QueryParam("fecha2") String fechas2) {

                LOGGER.info("ComentarioResource getComentarios: input: void");
        final Date fecha1;
        final Date fecha2;    
        fecha1 = new Date(fechas1);
        System.out.println(fecha1);
        fecha2 = new Date(fechas2);
        System.out.println(fecha2);
        List<ComentarioDetailDTO> listaComentarios = listEntity2DetailDTO(ComentarioLogic.getComentarioDate(fecha1, fecha2));
        LOGGER.log(Level.INFO, "ComentarioResource getComentarios: output: {0}");
        return listaComentarios;
    }

    /**
     * Busca la Comentario con el id asociado recibido en la URL y la devuelve.
     *
     * @param ComentariosId Identificador de la Comentario que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ComentarioDetailDTO} - La Comentario buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Comentario.
     */
    @GET
    @Path("{ComentariosId: \\d+}")
    public ComentarioDetailDTO getComentario(@PathParam("ComentariosId") Long ComentariosId) {
        LOGGER.log(Level.INFO, "ComentarioResource getComentario: input: {0}", ComentariosId);
        ComentarioEntity comentarioEntity = ComentarioLogic.getComentario(ComentariosId);
        LOGGER.log(Level.INFO, "ComentarioResource Tiene id: input: {0}", comentarioEntity.getId());
        if (comentarioEntity == null) {
            throw new WebApplicationException("El recurso /Comentarios/" + ComentariosId + " no existe.", 404);
        }
        ComentarioDetailDTO detailDTO = new ComentarioDetailDTO(comentarioEntity);
        LOGGER.log(Level.INFO, "ComentarioResource getComentario: output: {0}", detailDTO.getId());
        return detailDTO;
    }

    /**
     * Actualiza la Comentario con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param ComentariosId Identificador de la Comentario que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param Comentario {@link ComentarioDetailDTO} La Comentario que se desea
     * guardar.
     * @return JSON {@link ComentarioDetailDTO} - La Comentario guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Comentario a
     * actualizar.
     */
    @PUT
    @Path("{ComentariosId: \\d+}")
    public ComentarioDetailDTO updateComentario(@PathParam("ComentariosId") Long ComentariosId, ComentarioDetailDTO Comentario) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ComentarioResource updateComentario: input: id:{0} , Comentario: {1}", new Object[]{ComentariosId, Comentario});
        Comentario.setId(ComentariosId);
        //Comentario.setFecha(ComentarioLogic.getComentario(ComentariosId).getFecha());
        //Comentario.setTitulo(ComentarioLogic.getComentario(ComentariosId).getTitulo());

        if (ComentarioLogic.getComentario(ComentariosId) == null) {
            throw new WebApplicationException("El recurso /Comentarios/" + ComentariosId + " no existe.", 404);
        }
        ComentarioDetailDTO detailDTO = new ComentarioDetailDTO(ComentarioLogic.updateComentario(ComentariosId, Comentario.toEntity()));
        LOGGER.log(Level.INFO, "ComentarioResource updateComentario: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la Comentario con el id asociado recibido en la URL.
     *
     * @param ComentariosId Identificador de la Comentario que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la Comentario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Comentario.
     */
    @DELETE
    @Path("{ComentariosId: \\d+}")
    public void deleteComentario(@PathParam("ComentariosId") Long ComentariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ComentarioResource deleteComentario: input: {0}", ComentariosId);
        if (ComentarioLogic.getComentario(ComentariosId) == null) {
            throw new WebApplicationException("El recurso /Comentarios/" + ComentariosId + " no existe.", 404);
        }
        ComentarioLogic.deleteComment(ComentariosId);
        LOGGER.info("ComentarioResource deleteComentario: output: void");
    }

    /**
     * Conexión con el servicio de comentarios para una Comentario.
     * {@link ComentarioComentarioComentarioResourceResource}
     *
     * Este método conecta la ruta de /Comentarios con las rutas de /comentarios
     * que dependen de la Comentario, es una redirección al servicio que maneja
     * el segmento de la URL que se encarga de los comentarios de una
     * Comentario.
     *
     * @param ComentariosId El ID de la Comentario con respecto a la cual se
     * accede al servicio.
     * @return El servicio de comentarios para esta Comentario en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Comentario.
     */
    @Path("{ComentariosId: \\d+}/comentarios")
    public Class<PersonaResource> getComentarioComentarioResource(@PathParam("ComentariosId") Long ComentariosId) {
        if (ComentarioLogic.getComentario(ComentariosId) == null) {
            throw new WebApplicationException("El recurso /Comentarios/" + ComentariosId + " no existe.", 404);
        }
        return PersonaResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ComentarioEntity a una lista
     * de objetos ComentarioDetailDTO (json)
     *
     * @param entityList corresponde a la lista de Comentarios de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de Comentarios en forma DTO (json)
     */
    public List<ComentarioDetailDTO> listEntity2DetailDTO(List<ComentarioEntity> entityList) {
        List<ComentarioDetailDTO> list = new ArrayList<>();
        if (entityList.size() > 0) {
            for (ComentarioEntity entity : entityList) {
                list.add(new ComentarioDetailDTO(entity));
            }
        }

        return list;
    }
}
