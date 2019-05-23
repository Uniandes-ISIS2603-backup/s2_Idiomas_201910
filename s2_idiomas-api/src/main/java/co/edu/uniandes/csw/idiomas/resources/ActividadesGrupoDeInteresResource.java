/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.idiomas.resources;

import co.edu.uniandes.csw.idiomas.dtos.GrupoDeInteresDTO;
import co.edu.uniandes.csw.idiomas.dtos.GrupoDeInteresDetailDTO;
import co.edu.uniandes.csw.idiomas.ejb.ActividadesGrupoDeInteresLogic;
import co.edu.uniandes.csw.idiomas.ejb.GrupoDeInteresLogic;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.mappers.WebApplicationExceptionMapper;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "actividad/{id}/grupoDeInteres".
 *
 * @author g.cubillosb
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActividadesGrupoDeInteresResource {

    private static final Logger LOGGER = Logger.getLogger(ActividadesGrupoDeInteresResource.class.getName());

    @Inject
    private ActividadesGrupoDeInteresLogic actividadGrupoDeInteresLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private GrupoDeInteresLogic grupoDeInteresLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un grupoDeInteres dentro de un premio con la informacion que recibe el la
     * URL.
     *
     * @param actividadesId Identificador de el premio que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param gruposDeInteresId Identificador del autor que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link GrupoDeInteresDTO} - El autor guardado en el premio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el autor.
     */
    @POST
    @Path("{gruposDeInteresId: \\d+}")
    public GrupoDeInteresDTO addGrupoDeInteres(@PathParam("actividadesId") Long actividadesId, @PathParam("gruposDeInteresId") Long gruposDeInteresId) {
        LOGGER.log(Level.INFO, "ActividadesGrupoDeInteresResource addGrupoDeInteres: input: actividadesID: {0} , gruposDeInteresId: {1}", new Object[]{actividadesId, gruposDeInteresId});
        if (grupoDeInteresLogic.getGrupoDeInteres(gruposDeInteresId) == null) {
            throw new WebApplicationException("El recurso /gruposDeInteres/" + gruposDeInteresId + " no existe.", 404);
        }
        GrupoDeInteresDTO grupoDeInteresDTO = new GrupoDeInteresDTO(actividadGrupoDeInteresLogic.addGrupoDeInteres(gruposDeInteresId, actividadesId));
        LOGGER.log(Level.INFO, "ActividadesGrupoDeInteresResource addGrupoDeInteres: output: {0}", grupoDeInteresDTO);
        return grupoDeInteresDTO;
    }

    /**
     * Busca el autor dentro de el premio con id asociado.
     *
     * @param actividadesId Identificador de el premio que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link GrupoDeInteresDetailDTO} - El autor buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando el premio no tiene autor.
     */
    @GET
    public GrupoDeInteresDetailDTO getGrupoDeInteres(@PathParam("actividadesId") Long actividadesId) {
        LOGGER.log(Level.INFO, "ActividadesGrupoDeInteresResource getGrupoDeInteres: input: {0}", actividadesId);
        GrupoDeInteresEntity grupoDeInteresEntity = actividadGrupoDeInteresLogic.getGrupoDeInteres(actividadesId);
        if (grupoDeInteresEntity == null) {
            throw new WebApplicationException("El recurso /actividades/" + actividadesId + "/grupoDeInteres no existe.", 404);
        }
        GrupoDeInteresDetailDTO grupoDeInteresDetailDTO = new GrupoDeInteresDetailDTO(grupoDeInteresEntity);
        LOGGER.log(Level.INFO, "ActividadesGrupoDeInteresResource getGrupoDeInteres: output: {0}", grupoDeInteresDetailDTO);
        return grupoDeInteresDetailDTO;
    }

    /**
     * Remplaza la instancia de GrupoDeInteres asociada a una instancia de Actividad
     *
     * @param actividadesId Identificador de el premio que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param gruposDeInteresId Identificador de el grupoDeInteres que se esta remplazando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link GrupoDeInteresDetailDTO} - El autor actualizado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el autor.
     */
    @PUT
    @Path("{gruposDeInteresId: \\d+}")
    public GrupoDeInteresDetailDTO replaceGrupoDeInteres(@PathParam("actividadesId") Long actividadesId, @PathParam("gruposDeInteresId") Long gruposDeInteresId) {
        LOGGER.log(Level.INFO, "ActividadesGrupoDeInteresResource replaceGrupoDeInteres: input: actividadesId: {0} , gruposDeInteresId: {1}", new Object[]{actividadesId, gruposDeInteresId});
        if (grupoDeInteresLogic.getGrupoDeInteres(gruposDeInteresId) == null) {
            throw new WebApplicationException("El recurso /gruposDeInteres/" + gruposDeInteresId + " no existe.", 404);
        }
        GrupoDeInteresDetailDTO grupoDeInteresDetailDTO = new GrupoDeInteresDetailDTO(actividadGrupoDeInteresLogic.replaceGrupoDeInteres(actividadesId, gruposDeInteresId));
        LOGGER.log(Level.INFO, "ActividadesGrupoDeInteresResource replaceGrupoDeInteres: output: {0}", grupoDeInteresDetailDTO);
        return grupoDeInteresDetailDTO;
    }

    /**
     * Elimina la conexión entre el autor y el premio recibido en la URL.
     *
     * @param actividadesId El ID del premio al cual se le va a desasociar el autor
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     * Error de lógica que se genera cuando el premio no tiene autor.
     */
    @DELETE
    public void removeGrupoDeInteres(@PathParam("actividadesId") Long actividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ActividadesGrupoDeInteresResource removeGrupoDeInteres: input: {0}", actividadesId);
        actividadGrupoDeInteresLogic.removeGrupoDeInteres(actividadesId);
        LOGGER.info("ActividadesGrupoDeInteresResource removeGrupoDeInteres: output: void");
    }
}
