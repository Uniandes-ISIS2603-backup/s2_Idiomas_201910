/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.GrupoDeInteresDTO;
import co.edu.uniandes.csw.idiomas.dtos.GrupoDeInteresDetailDTO;
import co.edu.uniandes.csw.idiomas.ejb.GrupoDeInteresLogic;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
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
 * Clase que define los servicios de la clase grupoDeInteres
 * @author g.cubillosb
 */
@Path("grupoDeInteres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class GrupoDeInteresResource {
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Atributo que representa el logger correspondiente de la clase. Para poder
     * enviar mensajes.
     */
    private static final Logger LOGGER = Logger.getLogger(GrupoDeInteresResource.class.getName());
    
    /**
     * Permite acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private GrupoDeInteresLogic grupoDeInteresLogic; 
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------
    
    /**
     * Crea una nueva grupoDeInteres con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param grupoDeInteres {@link GrupoDeInteresDTO} - La grupoDeInteres que se desea
     * guardar.
     * @return JSON {@link GrupoDeInteresDTO} - La grupoDeInteres guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la grupoDeInteres.
     */
    @POST
    public GrupoDeInteresDTO createGrupoDeInteres(GrupoDeInteresDTO grupoDeInteres) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "GrupoDeInteresResource createGrupoDeInteres: input: {0}", grupoDeInteres);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        GrupoDeInteresEntity grupoDeInteresEntity = grupoDeInteres.toEntity();
        // Invoca la lógica para crear la grupoDeInteres nueva
        GrupoDeInteresEntity nuevoGrupoDeInteresEntity = grupoDeInteresLogic.createGrupoDeInteres(grupoDeInteresEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        GrupoDeInteresDTO nuevoGrupoDeInteresDTO = new GrupoDeInteresDTO(nuevoGrupoDeInteresEntity);
        LOGGER.log(Level.INFO, "GrupoDeInteresResource createGrupoDeInteres: output: {0}", nuevoGrupoDeInteresDTO);
        return nuevoGrupoDeInteresDTO;
    }

    /**
     * Busca y devuelve todas las gruposDeInteres que existen en la aplicacion.
     *
     * @return JSONArray {@link GrupoDeInteresDetailDTO} - Las gruposDeInteres
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<GrupoDeInteresDetailDTO> getGruposDeInteres() {
        LOGGER.info("GrupoDeInteresResource getGruposDeInteres: input: void");
        List<GrupoDeInteresDetailDTO> listaGruposDeInteres = listEntity2DetailDTO(grupoDeInteresLogic.getGruposDeInteres());
        LOGGER.log(Level.INFO, "GrupoDeInteresResource getGruposDeInteres: output: {0}", listaGruposDeInteres);
        return listaGruposDeInteres;
    }

    /**
     * Busca la grupoDeInteres con el id asociado recibido en la URL y la devuelve.
     *
     * @param gruposDeInteresId Identificador de la grupoDeInteres que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link GrupoDeInteresDetailDTO} - La grupoDeInteres buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la grupoDeInteres.
     */
    @GET
    @Path("{gruposDeInteresId: \\d+}")
    public GrupoDeInteresDetailDTO getGrupoDeInteres(@PathParam("gruposDeInteresId") Long gruposDeInteresId) 
    {
        LOGGER.log(Level.INFO, "GrupoDeInteresResource getGrupoDeInteres: input: {0}", gruposDeInteresId);
        GrupoDeInteresEntity grupoDeInteresEntity = grupoDeInteresLogic.getGrupoDeInteres(gruposDeInteresId);
        if (grupoDeInteresEntity == null) {
            throw new WebApplicationException("El recurso /gruposDeInteres/" + gruposDeInteresId + " no existe.", 404);
        }
        GrupoDeInteresDetailDTO detailDTO = new GrupoDeInteresDetailDTO(grupoDeInteresEntity);
        LOGGER.log(Level.INFO, "GrupoDeInteresResource getGrupoDeInteres: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la grupoDeInteres con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param gruposDeInteresId Identificador de la grupoDeInteres que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param grupoDeInteres {@link GrupoDeInteresDetailDTO} La grupoDeInteres que se desea
     * guardar.
     * @return JSON {@link GrupoDeInteresDetailDTO} - La grupoDeInteres guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la grupoDeInteres a
     * actualizar.
     */
    @PUT
    @Path("{gruposDeInteresId: \\d+}")
    public GrupoDeInteresDetailDTO updateGrupoDeInteres(@PathParam("gruposDeInteresId") Long gruposDeInteresId, GrupoDeInteresDetailDTO grupoDeInteres) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "GrupoDeInteresResource updateGrupoDeInteres: input: id:{0} , grupoDeInteres: {1}", new Object[]{gruposDeInteresId, grupoDeInteres});
        grupoDeInteres.setId(gruposDeInteresId);
        if (grupoDeInteresLogic.getGrupoDeInteres(gruposDeInteresId) == null) {
            throw new WebApplicationException("El recurso /gruposDeInteres/" + gruposDeInteresId + " no existe.", 404);
        }
        GrupoDeInteresDetailDTO detailDTO = new GrupoDeInteresDetailDTO(grupoDeInteresLogic.updateGrupoDeInteres(gruposDeInteresId, grupoDeInteres.toEntity()));
        LOGGER.log(Level.INFO, "GrupoDeInteresResource updateGrupoDeInteres: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la grupoDeInteres con el id asociado recibido en la URL.
     *
     * @param gruposDeInteresId Identificador de la grupoDeInteres que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la grupoDeInteres.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la grupoDeInteres.
     */
    @DELETE
    @Path("{gruposDeInteresId: \\d+}")
    public void deleteGrupoDeInteres(@PathParam("gruposDeInteresId") Long gruposDeInteresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "GrupoDeInteresResource deleteGrupoDeInteres: input: {0}", gruposDeInteresId);
        if (grupoDeInteresLogic.getGrupoDeInteres(gruposDeInteresId) == null) {
            throw new WebApplicationException("El recurso /gruposDeInteres/" + gruposDeInteresId + " no existe.", 404);
        }
        grupoDeInteresLogic.deleteGrupoDeInteres(gruposDeInteresId);
        LOGGER.info("GrupoDeInteresResource deleteGrupoDeInteres: output: void");
    }

    /**
     * Conexión con el servicio de comentarios para una grupoDeInteres.
     * {@link GrupoDeInteresComentarioResourceResource}
     *
     * Este método conecta la ruta de /gruposDeInteres con las rutas de /comentarios que
     * dependen de la grupoDeInteres, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los comentarios de una grupoDeInteres.
     *
     * @param gruposDeInteresId El ID de la grupoDeInteres con respecto a la cual se
     * accede al servicio.
     * @return El servicio de comentarios para esta grupoDeInteres en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la grupoDeInteres.
     */
    @Path("{gruposDeInteresId: \\d+}/comentarios")
    public Class<ComentarioResource> getGrupoDeInteresComentariosResource(@PathParam("gruposDeInteresId") Long gruposDeInteresId) {
        if (grupoDeInteresLogic.getGrupoDeInteres(gruposDeInteresId) == null) {
            throw new WebApplicationException("El recurso /gruposDeInteres/" + gruposDeInteresId + " no existe.", 404);
        }
        return GrupoDeInteresComentariosResource.class;
    }
    
    /**
     * Conexión con el servicio de usuarios para una grupoDeInteres.
     * {@link GrupoDeInteresComentarioResourceResource}
     *
     * Este método conecta la ruta de /gruposDeInteres con las rutas de /usuarios que
     * dependen de la grupoDeInteres, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los usuarios de una grupoDeInteres.
     *
     * @param gruposDeInteresId El ID de la grupoDeInteres con respecto a la cual se
     * accede al servicio.
     * @return El servicio de usuarios para esta grupoDeInteres en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la grupoDeInteres.
     */
    @Path("{gruposDeInteresId: \\d+}/usuarios")
    public Class<ComentarioResource> getGrupoDeInteresUsuariosResource(@PathParam("gruposDeInteresId") Long gruposDeInteresId) {
        if (grupoDeInteresLogic.getGrupoDeInteres(gruposDeInteresId) == null) {
            throw new WebApplicationException("El recurso /gruposDeInteres/" + gruposDeInteresId + " no existe.", 404);
        }
        return GrupoDeInteresUsuariosResource.class;
    }

    /**
     * Conexión con el servicio de actividades para una grupoDeInteres.
     * {@link GrupoDeInteresComentarioResourceResource}
     *
     * Este método conecta la ruta de /gruposDeInteres con las rutas de /actividades que
     * dependen de la grupoDeInteres, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los actividades de una grupoDeInteres.
     *
     * @param gruposDeInteresId El ID de la grupoDeInteres con respecto a la cual se
     * accede al servicio.
     * @return El servicio de actividades para esta grupoDeInteres en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la grupoDeInteres.
     */
    @Path("{gruposDeInteresId: \\d+}/actividades")
    public Class<ComentarioResource> getGrupoDeInteresActividadesResource(@PathParam("gruposDeInteresId") Long gruposDeInteresId) {
        if (grupoDeInteresLogic.getGrupoDeInteres(gruposDeInteresId) == null) {
            throw new WebApplicationException("El recurso /gruposDeInteres/" + gruposDeInteresId + " no existe.", 404);
        }
        return GrupoDeInteresActividadesResource.class;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos GrupoDeInteresEntity a una lista de
     * objetos GrupoDeInteresDetailDTO (json)
     *
     * @param entityList corresponde a la lista de gruposDeInteres de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de gruposDeInteres en forma DTO (json)
     */
    private List<GrupoDeInteresDetailDTO> listEntity2DetailDTO(List<GrupoDeInteresEntity> entityList) {
        List<GrupoDeInteresDetailDTO> list = new ArrayList<>();
        for (GrupoDeInteresEntity entity : entityList) {
            list.add(new GrupoDeInteresDetailDTO(entity));
        }
        return list;
    }
    
    
    
}
