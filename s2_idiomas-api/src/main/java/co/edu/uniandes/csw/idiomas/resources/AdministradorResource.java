/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.AdministradorDTO;
import co.edu.uniandes.csw.idiomas.dtos.AdministradorDetailDTO;
import co.edu.uniandes.csw.idiomas.ejb.AdministradorLogic;
import co.edu.uniandes.csw.idiomas.entities.AdministradorEntity;
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
 * Clase que define los servicios de la clase administrador
 * @author g.cubillosb
 */
@Path("administradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AdministradorResource {
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Atributo que representa el logger correspondiente de la clase. Para poder
     * enviar mensajes.
     */
    private static final Logger LOGGER = Logger.getLogger(AdministradorResource.class.getName());
    
    /**
     * Permite acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private AdministradorLogic administradorLogic; 
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------
    
    /**
     * Crea una nueva administrador con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param administrador {@link AdministradorDTO} - La administrador que se desea
     * guardar.
     * @return JSON {@link AdministradorDTO} - La administrador guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la administrador.
     */
    @POST
    public AdministradorDTO createAdministrador(AdministradorDTO administrador) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "AdministradorResource createAdministrador: input: {0}", administrador);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        AdministradorEntity administradorEntity = administrador.toEntity();
        // Invoca la lógica para crear la administrador nueva
        AdministradorEntity nuevoAdministradorEntity = administradorLogic.createAdministrador(administradorEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        AdministradorDTO nuevoAdministradorDTO = new AdministradorDTO(nuevoAdministradorEntity);
        LOGGER.log(Level.INFO, "AdministradorResource createAdministrador: output: {0}", nuevoAdministradorDTO);
        return nuevoAdministradorDTO;
    }

    /**
     * Busca y devuelve todas las administradores que existen en la aplicacion.
     *
     * @return JSONArray {@link AdministradorDetailDTO} - Las administradores
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<AdministradorDetailDTO> getAdministradores() {
        LOGGER.info("AdministradorResource getAdministradores: input: void");
        List<AdministradorDetailDTO> listaAdministradores = listEntity2DetailDTO(administradorLogic.getAdministradores());
        LOGGER.log(Level.INFO, "AdministradorResource getAdministradores: output: {0}", listaAdministradores);
        return listaAdministradores;
    }

    /**
     * Busca la administrador con el id asociado recibido en la URL y la devuelve.
     *
     * @param administradoresId Identificador de la administrador que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link AdministradorDetailDTO} - La administrador buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la administrador.
     */
    @GET
    @Path("{administradoresId: \\d+}")
    public AdministradorDetailDTO getAdministrador(@PathParam("administradoresId") Long administradoresId) 
    {
        LOGGER.log(Level.INFO, "AdministradorResource getAdministrador: input: {0}", administradoresId);
        AdministradorEntity administradorEntity = administradorLogic.getAdministrador(administradoresId);
        if (administradorEntity == null) {
            throw new WebApplicationException("El recurso /administradores/" + administradoresId + " no existe.", 404);
        }
        AdministradorDetailDTO detailDTO = new AdministradorDetailDTO(administradorEntity);
        LOGGER.log(Level.INFO, "AdministradorResource getAdministrador: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la administrador con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param administradoresId Identificador de la administrador que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param administrador {@link AdministradorDetailDTO} La administrador que se desea
     * guardar.
     * @return JSON {@link AdministradorDetailDTO} - La administrador guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la administrador a
     * actualizar.
     */
    @PUT
    @Path("{administradoresId: \\d+}")
    public AdministradorDetailDTO updateAdministrador(@PathParam("administradoresId") Long administradoresId, AdministradorDetailDTO administrador) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "AdministradorResource updateAdministrador: input: id:{0} , administrador: {1}", new Object[]{administradoresId, administrador});
        administrador.setId(administradoresId);
        if (administradorLogic.getAdministrador(administradoresId) == null) {
            throw new WebApplicationException("El recurso /administradores/" + administradoresId + " no existe.", 404);
        }
        AdministradorDetailDTO detailDTO = new AdministradorDetailDTO(administradorLogic.updateAdministrador(administradoresId, administrador.toEntity()));
        LOGGER.log(Level.INFO, "AdministradorResource updateAdministrador: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la administrador con el id asociado recibido en la URL.
     *
     * @param administradoresId Identificador de la administrador que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la administrador.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la administrador.
     */
    @DELETE
    @Path("{administradoresId: \\d+}")
    public void deleteAdministrador(@PathParam("administradoresId") Long administradoresId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "AdministradorResource deleteAdministrador: input: {0}", administradoresId);
        if (administradorLogic.getAdministrador(administradoresId) == null) {
            throw new WebApplicationException("El recurso /administradores/" + administradoresId + " no existe.", 404);
        }
        administradorLogic.deleteAdministrador(administradoresId);
        LOGGER.info("AdministradorResource deleteAdministrador: output: void");
    }

    /**
     * Conexión con el servicio de comentarios para una administrador.
     * {@link AdministradorComentarioResourceResource}
     *
     * Este método conecta la ruta de /administradores con las rutas de /comentarios que
     * dependen de la administrador, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los comentarios de una administrador.
     *
     * @param administradoresId El ID de la administrador con respecto a la cual se
     * accede al servicio.
     * @return El servicio de comentarios para esta administrador en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la administrador.
     */
    @Path("{administradoresId: \\d+}/gruposDeInteres")
    public Class<AdministradorGruposDeInteresResource> getAdministradorGruposDeInteresResource(@PathParam("administradoresId") Long administradoresId) {
        if (administradorLogic.getAdministrador(administradoresId) == null) {
            throw new WebApplicationException("El recurso /administradores/" + administradoresId + " no existe.", 404);
        }
        return AdministradorGruposDeInteresResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos AdministradorEntity a una lista de
     * objetos AdministradorDetailDTO (json)
     *
     * @param entityList corresponde a la lista de administradores de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de administradores en forma DTO (json)
     */
    private List<AdministradorDetailDTO> listEntity2DetailDTO(List<AdministradorEntity> entityList) {
        List<AdministradorDetailDTO> list = new ArrayList<>();
        for (AdministradorEntity entity : entityList) {
            list.add(new AdministradorDetailDTO(entity));
        }
        return list;
    }
    
    
    
}
