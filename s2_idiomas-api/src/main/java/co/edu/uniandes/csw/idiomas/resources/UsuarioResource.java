/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.UsuarioDTO;
import co.edu.uniandes.csw.idiomas.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.idiomas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.idiomas.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Clase que define los servicios de la clase usuario
 * @author g.cubillosb
 */
@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UsuarioResource {
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Atributo que representa el logger correspondiente de la clase. Para poder
     * enviar mensajes.
     */
    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    
    /**
     * Permite acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private UsuarioLogic usuarioLogic; 
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------
    
    /**
     * Crea una nueva usuario con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param usuario {@link UsuarioDTO} - La usuario que se desea
     * guardar.
     * @return JSON {@link UsuarioDTO} - La usuario guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la usuario.
     */
    @POST
    public UsuarioDTO createUsuario(UsuarioDTO usuario) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "UsuarioResource createUsuario: input: {0}", usuario);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        UsuarioEntity usuarioEntity = usuario.toEntity();
        // Invoca la lógica para crear la usuario nueva
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.createUsuario(usuarioEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource createUsuario: output: {0}", nuevoUsuarioDTO);
        return nuevoUsuarioDTO;
    }

    /**
     * Busca y devuelve todas las usuarios que existen en la aplicacion.
     *
     * @return JSONArray {@link UsuarioDetailDTO} - Las usuarios
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<UsuarioDetailDTO> getUsuarios() {
        LOGGER.info("UsuarioResource getUsuarios: input: void");
        List<UsuarioDetailDTO> listaUsuarios = listEntity2DetailDTO(usuarioLogic.getUsuarios());
        LOGGER.log(Level.INFO, "UsuarioResource getUsuarios: output: {0}", listaUsuarios);
        return listaUsuarios;
    }

    /**
     * Busca la usuario con el id asociado recibido en la URL y la devuelve.
     *
     * @param usuariosId Identificador de la usuario que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link UsuarioDetailDTO} - La usuario buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario.
     */
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO getUsuario(@PathParam("usuariosId") Long usuariosId) 
    {
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: input: {0}", usuariosId);
        UsuarioEntity usuarioEntity = usuarioLogic.getUsuario(usuariosId);
        if (usuarioEntity == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioEntity);
        LOGGER.log(Level.INFO, "UsuarioResource getUsuario: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la usuario con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param usuariosId Identificador de la usuario que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param usuario {@link UsuarioDetailDTO} La usuario que se desea
     * guardar.
     * @return JSON {@link UsuarioDetailDTO} - La usuario guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario a
     * actualizar.
     */
    @PUT
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO updateUsuario(@PathParam("usuariosId") Long usuariosId, UsuarioDetailDTO usuario) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: input: id:{0} , usuario: {1}", new Object[]{usuariosId, usuario});
        usuario.setId(usuariosId);
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioLogic.updateUsuario(usuariosId, usuario.toEntity()));
        LOGGER.log(Level.INFO, "UsuarioResource updateUsuario: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la usuario con el id asociado recibido en la URL.
     *
     * @param usuariosId Identificador de la usuario que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario.
     */
    @DELETE
    @Path("{usuariosId: \\d+}")
    public void deleteUsuario(@PathParam("usuariosId") Long usuariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "UsuarioResource deleteUsuario: input: {0}", usuariosId);
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        usuarioLogic.deleteUsuario(usuariosId);
        LOGGER.info("UsuarioResource deleteUsuario: output: void");
    }

    /**
     * Conexión con el servicio de comentarios para una usuario.
     * {@link UsuarioComentarioResourceResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /comentarios que
     * dependen de la usuario, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los comentarios de una usuario.
     *
     * @param usuariosId El ID de la usuario con respecto a la cual se
     * accede al servicio.
     * @return El servicio de comentarios para esta usuario en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario.
     */
    @Path("{usuariosId: \\d+}/actividades")
    public Class<UsuariosActividadesResource> getUsuariosActividadesResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        return UsuariosActividadesResource.class;
    }
    
    /**
     * Conexión con el servicio de comentarios para una usuario.
     * {@link UsuarioComentarioResourceResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /comentarios que
     * dependen de la usuario, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los comentarios de una usuario.
     *
     * @param usuariosId El ID de la usuario con respecto a la cual se
     * accede al servicio.
     * @return El servicio de comentarios para esta usuario en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario.
     */
    @Path("{usuariosId: \\d+}/gruposDeInteres")
    public Class<UsuariosGruposDeInteresResource> getUsuariosGruposDeInteresResource(@PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        return UsuariosGruposDeInteresResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos UsuarioEntity a una lista de
     * objetos UsuarioDetailDTO (json)
     *
     * @param entityList corresponde a la lista de usuarios de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de usuarios en forma DTO (json)
     */
    private List<UsuarioDetailDTO> listEntity2DetailDTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }
    
    
    
}
