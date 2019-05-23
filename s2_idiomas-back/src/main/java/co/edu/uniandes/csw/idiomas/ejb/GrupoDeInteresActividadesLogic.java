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
package co.edu.uniandes.csw.idiomas.ejb;

import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.ActividadPersistence;
import co.edu.uniandes.csw.idiomas.persistence.GrupoDeInteresPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de GrupoDeInteres y Actividad.
 *
 * @author ISIS2603
 */
@Stateless
public class GrupoDeInteresActividadesLogic {

    private static final Logger LOGGER = Logger.getLogger(GrupoDeInteresActividadesLogic.class.getName());

    @Inject
    private ActividadPersistence actividadPersistence;

    @Inject
    private GrupoDeInteresPersistence grupoDeInteresPersistence;

    /**
     * Agregar un actividad a la grupoDeInteres
     *
     * @param actividadesId El id libro a guardar
     * @param gruposDeInteresId El id de la grupoDeInteres en la cual se va a guardar el
     * libro.
     * @return El libro creado.
     */
    public ActividadEntity addActividad(Long actividadesId, Long gruposDeInteresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un libro a la grupoDeInteres con id = {0}", gruposDeInteresId);
        GrupoDeInteresEntity grupoDeInteresEntity = grupoDeInteresPersistence.find(gruposDeInteresId);
        ActividadEntity actividadEntity = actividadPersistence.find(actividadesId);
        actividadEntity.setGrupoDeInteres(grupoDeInteresEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un libro a la grupoDeInteres con id = {0}", gruposDeInteresId);
        return actividadEntity;
    }

    /**
     * Retorna todos los actividades asociados a una grupoDeInteres
     *
     * @param gruposDeInteresId El ID de la grupoDeInteres buscada
     * @return La lista de libros de la grupoDeInteres
     */
    public List<ActividadEntity> getActividades(Long gruposDeInteresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los libros asociados a la grupoDeInteres con id = {0}", gruposDeInteresId);
        return grupoDeInteresPersistence.find(gruposDeInteresId).getActividades();
    }

    /**
     * Retorna un actividad asociado a una grupoDeInteres
     *
     * @param gruposDeInteresId El id de la grupoDeInteres a buscar.
     * @param actividadesId El id del libro a buscar
     * @return El libro encontrado dentro de la grupoDeInteres.
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * grupoDeInteres
     */
    public ActividadEntity getActividad(Long gruposDeInteresId, Long actividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el libro con id = {0} de la grupoDeInteres con id = " + gruposDeInteresId, actividadesId);
        List<ActividadEntity> actividades = grupoDeInteresPersistence.find(gruposDeInteresId).getActividades();
        ActividadEntity actividadEntity = actividadPersistence.find(actividadesId);
        int index = actividades.indexOf(actividadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0} de la grupoDeInteres con id = " + gruposDeInteresId, actividadesId);
        if (index >= 0) {
            return actividades.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado a la grupoDeInteres");
    }

    /**
     * Remplazar actividades de una grupoDeInteres
     *
     * @param actividades Lista de libros que serán los de la grupoDeInteres.
     * @param gruposDeInteresId El id de la grupoDeInteres que se quiere actualizar.
     * @return La lista de libros actualizada.
     */
    public List<ActividadEntity> replaceActividades(Long gruposDeInteresId, List<ActividadEntity> actividades) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la grupoDeInteres con id = {0}", gruposDeInteresId);
        GrupoDeInteresEntity grupoDeInteresEntity = grupoDeInteresPersistence.find(gruposDeInteresId);
        List<ActividadEntity> actividadList = actividadPersistence.findAll();
        for (ActividadEntity actividad : actividadList) {
            if (actividades.contains(actividad)) {
                actividad.setGrupoDeInteres(grupoDeInteresEntity);
            } else if (actividad.getGrupoDeInteres() != null && actividad.getGrupoDeInteres().equals(grupoDeInteresEntity)) {
                actividad.setGrupoDeInteres(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la grupoDeInteres con id = {0}", gruposDeInteresId);
        return actividades;
    }
}
