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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Actividad y GrupoDeInteres
 *
 * @author g.cubillosb
 */
@Stateless
public class ActividadesGrupoDeInteresLogic {

    private static final Logger LOGGER = Logger.getLogger(ActividadesGrupoDeInteresLogic.class.getName());

    @Inject
    private GrupoDeInteresPersistence grupoDeInteresPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ActividadPersistence actividadPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un autor a un premio
     *
     * @param actividadesId El id premio a guardar
     * @param gruposDeInteresId El id del autor al cual se le va a guardar el premio.
     * @return El premio que fue agregado al autor.
     */
    public GrupoDeInteresEntity addGrupoDeInteres(Long gruposDeInteresId, Long actividadesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el autor con id = {0} al premio con id = " + actividadesId, gruposDeInteresId);
        GrupoDeInteresEntity autorEntity = grupoDeInteresPersistence.find(gruposDeInteresId);
        ActividadEntity actividadEntity = actividadPersistence.find(actividadesId);
        actividadEntity.setGrupoDeInteres(autorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el autor con id = {0} al premio con id = " + actividadesId, gruposDeInteresId);
        return grupoDeInteresPersistence.find(gruposDeInteresId);
    }

    /**
     *
     * Obtener un premio por medio de su id y el de su autor.
     *
     * @param actividadesId id del premio a ser buscado.
     * @return el autor solicitada por medio de su id.
     */
    public GrupoDeInteresEntity getGrupoDeInteres(Long actividadesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el autor del premio con id = {0}", actividadesId);
        GrupoDeInteresEntity grupoDeInteresEntity = actividadPersistence.find(actividadesId).getGrupoDeInteres();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el autor del premio con id = {0}", actividadesId);
        return grupoDeInteresEntity;
    }

    /**
     * Remplazar autor de un premio
     *
     * @param actividadesId el id del premio que se quiere actualizar.
     * @param gruposDeInteresId El id del nuebo autor asociado al premio.
     * @return el nuevo autor asociado.
     */
    public GrupoDeInteresEntity replaceGrupoDeInteres(Long actividadesId, Long gruposDeInteresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor del premio premio con id = {0}", actividadesId);
        GrupoDeInteresEntity autorEntity = grupoDeInteresPersistence.find(gruposDeInteresId);
        ActividadEntity actividadEntity = actividadPersistence.find(actividadesId);
        actividadEntity.setGrupoDeInteres(autorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el autor con id = {0} al premio con id = " + actividadesId, gruposDeInteresId);
        return grupoDeInteresPersistence.find(gruposDeInteresId);
    }

    /**
     * Borrar el autor de un premio
     *
     * @param actividadesId El premio que se desea borrar del autor.
     * @throws BusinessLogicException si el premio no tiene autor
     */
    public void removeGrupoDeInteres(Long actividadesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor del premio con id = {0}", actividadesId);
        ActividadEntity actividadEntity = actividadPersistence.find(actividadesId);
        if (actividadEntity.getGrupoDeInteres() == null) {
            throw new BusinessLogicException("El premio no tiene autor");
        }
        GrupoDeInteresEntity grupoDeInteresEntity = grupoDeInteresPersistence.find(actividadEntity.getGrupoDeInteres().getId());
        actividadEntity.setGrupoDeInteres(null);
        grupoDeInteresEntity.getActividades().remove(actividadEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0} del premio con id = " + actividadesId, grupoDeInteresEntity.getId());
    }
}
