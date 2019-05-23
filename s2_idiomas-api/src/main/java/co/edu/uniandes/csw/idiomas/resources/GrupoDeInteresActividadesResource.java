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

import co.edu.uniandes.csw.idiomas.dtos.ActividadDTO;
import co.edu.uniandes.csw.idiomas.dtos.ActividadDetailDTO;
import co.edu.uniandes.csw.idiomas.ejb.ActividadLogic;
import co.edu.uniandes.csw.idiomas.ejb.GrupoDeInteresActividadesLogic;
import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.idiomas.mappers.WebApplicationExceptionMapper;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "grupoDeInteres/{id}/actividades".
 *
 * @author ISIS2603
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GrupoDeInteresActividadesResource {

    private static final Logger LOGGER = Logger.getLogger(GrupoDeInteresActividadesResource.class.getName());

    @Inject
    private GrupoDeInteresActividadesLogic grupoDeInteresActividadesLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ActividadLogic actividadLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un libro dentro de una grupoDeInteres con la informacion que recibe el
     * la URL. Se devuelve el libro que se guarda en la grupoDeInteres.
     *
     * @param gruposDeInteresId Identificador de la grupoDeInteres que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param actividadesId Identificador del libro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ActividadDTO} - El libro guardado en la grupoDeInteres.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{actividadesId: \\d+}")
    public ActividadDTO addActividad(@PathParam("gruposDeInteresId") Long gruposDeInteresId, @PathParam("actividadesId") Long actividadesId) {
        LOGGER.log(Level.INFO, "GrupoDeInteresActividadesResource addActividad: input: gruposDeInteresID: {0} , actividadesId: {1}", new Object[]{gruposDeInteresId, actividadesId});
        if (actividadLogic.getActividad(actividadesId) == null) {
            throw new WebApplicationException("El recurso /actividades/" + actividadesId + " no existe.", 404);
        }
        ActividadDTO actividadDTO = new ActividadDTO(grupoDeInteresActividadesLogic.addActividad(actividadesId, gruposDeInteresId));
        LOGGER.log(Level.INFO, "GrupoDeInteresActividadesResource addActividad: output: {0}", actividadDTO);
        return actividadDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en la grupoDeInteres.
     *
     * @param gruposDeInteresId Identificador de la grupoDeInteres que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ActividadDetailDTO} - Los libros encontrados en la
     * grupoDeInteres. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ActividadDetailDTO> getActividades(@PathParam("gruposDeInteresId") Long gruposDeInteresId) {
        LOGGER.log(Level.INFO, "GrupoDeInteresActividadesResource getActividades: input: {0}", gruposDeInteresId);
        List<ActividadDetailDTO> listaDetailDTOs = actividadesListEntity2DTO(grupoDeInteresActividadesLogic.getActividades(gruposDeInteresId));
        LOGGER.log(Level.INFO, "GrupoDeInteresActividadesResource getActividades: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la grupoDeInteres con id asociado.
     *
     * @param gruposDeInteresId Identificador de la grupoDeInteres que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param actividadesId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ActividadDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * grupoDeInteres.
     */
    @GET
    @Path("{actividadesId: \\d+}")
    public ActividadDetailDTO getActividad(@PathParam("gruposDeInteresId") Long gruposDeInteresId, @PathParam("actividadesId") Long actividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "GrupoDeInteresActividadesResource getActividad: input: gruposDeInteresID: {0} , actividadesId: {1}", new Object[]{gruposDeInteresId, actividadesId});
        if (actividadLogic.getActividad(actividadesId) == null) {
            throw new WebApplicationException("El recurso /gruposDeInteres/" + gruposDeInteresId + "/actividades/" + actividadesId + " no existe.", 404);
        }
        ActividadDetailDTO actividadDetailDTO = new ActividadDetailDTO(grupoDeInteresActividadesLogic.getActividad(gruposDeInteresId, actividadesId));
        LOGGER.log(Level.INFO, "GrupoDeInteresActividadesResource getActividad: output: {0}", actividadDetailDTO);
        return actividadDetailDTO;
    }

    /**
     * Remplaza las instancias de Actividad asociadas a una instancia de GrupoDeInteres
     *
     * @param gruposDeInteresId Identificador de la grupoDeInteres que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param actividades JSONArray {@link ActividadDTO} El arreglo de libros nuevo para la
     * grupoDeInteres.
     * @return JSON {@link ActividadDTO} - El arreglo de libros guardado en la
     * grupoDeInteres.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<ActividadDetailDTO> replaceActividades(@PathParam("gruposDeInteresId") Long gruposDeInteresId, List<ActividadDetailDTO> actividades) {
        LOGGER.log(Level.INFO, "GrupoDeInteresActividadesResource replaceActividades: input: gruposDeInteresId: {0} , actividades: {1}", new Object[]{gruposDeInteresId, actividades});
        for (ActividadDetailDTO actividad : actividades) {
            if (actividadLogic.getActividad(actividad.getId()) == null) {
                throw new WebApplicationException("El recurso /actividades/" + actividad.getId() + " no existe.", 404);
            }
        }
        List<ActividadDetailDTO> listaDetailDTOs = actividadesListEntity2DTO(grupoDeInteresActividadesLogic.replaceActividades(gruposDeInteresId, actividadesListDTO2Entity(actividades)));
        LOGGER.log(Level.INFO, "GrupoDeInteresActividadesResource replaceActividades: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de ActividadEntity a una lista de ActividadDetailDTO.
     *
     * @param entityList Lista de ActividadEntity a convertir.
     * @return Lista de ActividadDTO convertida.
     */
    private List<ActividadDetailDTO> actividadesListEntity2DTO(List<ActividadEntity> entityList) {
        List<ActividadDetailDTO> list = new ArrayList();
        for (ActividadEntity entity : entityList) {
            list.add(new ActividadDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ActividadDetailDTO a una lista de ActividadEntity.
     *
     * @param dtos Lista de ActividadDetailDTO a convertir.
     * @return Lista de ActividadEntity convertida.
     */
    private List<ActividadEntity> actividadesListDTO2Entity(List<ActividadDetailDTO> dtos) {
        List<ActividadEntity> list = new ArrayList<>();
        for (ActividadDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
