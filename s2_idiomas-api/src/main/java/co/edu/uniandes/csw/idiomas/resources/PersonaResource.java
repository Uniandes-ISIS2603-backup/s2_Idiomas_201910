/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.PersonaDTO;
import co.edu.uniandes.csw.idiomas.dtos.PersonaDetailDTO;
import co.edu.uniandes.csw.idiomas.ejb.PersonaLogic;
import co.edu.uniandes.csw.idiomas.entities.PersonaEntity;
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
 * Clase que define los servicios de la clase persona
 * @author g.cubillosb
 */
@Path("personas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PersonaResource {
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------
    
    /**
     * Atributo que representa el logger correspondiente de la clase. Para poder
     * enviar mensajes.
     */
    private static final Logger LOGGER = Logger.getLogger(PersonaResource.class.getName());
    
    /**
     * Permite acceder a la lógica de la aplicación. Es una inyección de dependencias.
     */
    @Inject
    private PersonaLogic personaLogic; 
    
    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------
    
    /**
     * Crea una nueva persona con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param persona {@link PersonaDTO} - La persona que se desea
     * guardar.
     * @return JSON {@link PersonaDTO} - La persona guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la persona.
     */
    @POST
    public PersonaDTO createPersona(PersonaDTO persona) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "PersonaResource createPersona: input: {0}", persona);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        PersonaEntity personaEntity = persona.toEntity();
        // Invoca la lógica para crear la persona nueva
        PersonaEntity nuevoPersonaEntity = personaLogic.createPersona(personaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        PersonaDTO nuevoPersonaDTO = new PersonaDTO(nuevoPersonaEntity);
        LOGGER.log(Level.INFO, "PersonaResource createPersona: output: {0}", nuevoPersonaDTO);
        return nuevoPersonaDTO;
    }

    /**
     * Busca y devuelve todas las personas que existen en la aplicacion.
     *
     * @return JSONArray {@link PersonaDetailDTO} - Las personas
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<PersonaDetailDTO> getPersonas() {
        LOGGER.info("PersonaResource getPersonas: input: void");
        List<PersonaDetailDTO> listaPersonas = listEntity2DetailDTO(personaLogic.getPersonas());
        LOGGER.log(Level.INFO, "PersonaResource getPersonas: output: {0}", listaPersonas);
        return listaPersonas;
    }

    /**
     * Busca la persona con el id asociado recibido en la URL y la devuelve.
     *
     * @param personasId Identificador de la persona que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link PersonaDetailDTO} - La persona buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la persona.
     */
    @GET
    @Path("{personasId: \\d+}")
    public PersonaDetailDTO getPersona(@PathParam("personasId") Long personasId) 
    {
        LOGGER.log(Level.INFO, "PersonaResource getPersona: input: {0}", personasId);
        PersonaEntity personaEntity = personaLogic.getPersona(personasId);
        if (personaEntity == null) {
            throw new WebApplicationException("El recurso /personas/" + personasId + " no existe.", 404);
        }
        PersonaDetailDTO detailDTO = new PersonaDetailDTO(personaEntity);
        LOGGER.log(Level.INFO, "PersonaResource getPersona: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la persona con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param personasId Identificador de la persona que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param persona {@link PersonaDetailDTO} La persona que se desea
     * guardar.
     * @return JSON {@link PersonaDetailDTO} - La persona guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la persona a
     * actualizar.
     */
    @PUT
    @Path("{personasId: \\d+}")
    public PersonaDetailDTO updatePersona(@PathParam("personasId") Long personasId, PersonaDetailDTO persona) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "PersonaResource updatePersona: input: id:{0} , persona: {1}", new Object[]{personasId, persona});
        persona.setId(personasId);
        if (personaLogic.getPersona(personasId) == null) {
            throw new WebApplicationException("El recurso /personas/" + personasId + " no existe.", 404);
        }
        PersonaDetailDTO detailDTO = new PersonaDetailDTO(personaLogic.updatePersona(personasId, persona.toEntity()));
        LOGGER.log(Level.INFO, "PersonaResource updatePersona: output: {0}", detailDTO);
        return detailDTO;

    }

    /**
     * Borra la persona con el id asociado recibido en la URL.
     *
     * @param personasId Identificador de la persona que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la persona.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la persona.
     */
    @DELETE
    @Path("{personasId: \\d+}")
    public void deletePersona(@PathParam("personasId") Long personasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PersonaResource deletePersona: input: {0}", personasId);
        if (personaLogic.getPersona(personasId) == null) {
            throw new WebApplicationException("El recurso /personas/" + personasId + " no existe.", 404);
        }
        personaLogic.deletePersona(personasId);
        LOGGER.info("PersonaResource deletePersona: output: void");
    }

    /**
     * Conexión con el servicio de comentarios para una persona.
     * {@link PersonaComentarioResourceResource}
     *
     * Este método conecta la ruta de /personas con las rutas de /comentarios que
     * dependen de la persona, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los comentarios de una persona.
     *
     * @param personasId El ID de la persona con respecto a la cual se
     * accede al servicio.
     * @return El servicio de comentarios para esta persona en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la persona.
     */
    @Path("{personasId: \\d+}/comentarios")
    public Class<ComentarioResource> getPersonaComentarioResource(@PathParam("personasId") Long personasId) {
        if (personaLogic.getPersona(personasId) == null) {
            throw new WebApplicationException("El recurso /personas/" + personasId + " no existe.", 404);
        }
        return ComentarioResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PersonaEntity a una lista de
     * objetos PersonaDetailDTO (json)
     *
     * @param entityList corresponde a la lista de personas de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de personas en forma DTO (json)
     */
    private List<PersonaDetailDTO> listEntity2DetailDTO(List<PersonaEntity> entityList) {
        List<PersonaDetailDTO> list = new ArrayList<>();
        for (PersonaEntity entity : entityList) {
            list.add(new PersonaDetailDTO(entity));
        }
        return list;
    }
    
    
    
}
