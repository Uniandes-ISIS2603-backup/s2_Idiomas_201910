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
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.GrupoDeInteresActividadesLogic;
import co.edu.uniandes.csw.idiomas.ejb.GrupoDeInteresLogic;
import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.GrupoDeInteresPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion GrupoDeInteres - Actividades
 *
 * @author ISIS2603
 */
@RunWith(Arquillian.class)
public class GrupoDeInteresActividadesLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private GrupoDeInteresLogic grupoDeInteresLogic;
    @Inject
    private GrupoDeInteresActividadesLogic grupoDeInteresActividadesLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<GrupoDeInteresEntity> data = new ArrayList<GrupoDeInteresEntity>();

    private List<ActividadEntity> actividadesData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(GrupoDeInteresEntity.class.getPackage())
                .addPackage(GrupoDeInteresLogic.class.getPackage())
                .addPackage(GrupoDeInteresPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ActividadEntity").executeUpdate();
        em.createQuery("delete from GrupoDeInteresEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ActividadEntity actividades = factory.manufacturePojo(ActividadEntity.class);
            em.persist(actividades);
            actividadesData.add(actividades);
        }
        for (int i = 0; i < 3; i++) {
            GrupoDeInteresEntity entity = factory.manufacturePojo(GrupoDeInteresEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                actividadesData.get(i).setGrupoDeInteres(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Actividades existente a un GrupoDeInteres.
     */
    @Test
    public void addActividadesTest() {
        GrupoDeInteresEntity entity = data.get(0);
        ActividadEntity actividadEntity = actividadesData.get(1);
        ActividadEntity response = grupoDeInteresActividadesLogic.addActividad(actividadEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(actividadEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Actividades asociadas a una
     * instancia GrupoDeInteres.
     */
    @Test
    public void getActividadesTest() {
        List<ActividadEntity> list = grupoDeInteresActividadesLogic.getActividades(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Actividades asociada a una instancia
     * GrupoDeInteres.
     *
     * @throws co.edu.uniandes.csw.actividadestore.exceptions.BusinessLogicException
     */
    @Test
    public void getActividadTest() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        ActividadEntity actividadEntity = actividadesData.get(0);
        ActividadEntity response = grupoDeInteresActividadesLogic.getActividad(entity.getId(), actividadEntity.getId());

        Assert.assertEquals(actividadEntity.getId(), response.getId());
        Assert.assertEquals(actividadEntity.getNombre(), response.getNombre());
        Assert.assertEquals(actividadEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(actividadEntity.getFecha(), response.getFecha());
    }

    /**
     * Prueba para obtener una instancia de Actividades asociada a una instancia
     * GrupoDeInteres que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.actividadestore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getActividadNoAsociadoTest() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        ActividadEntity actividadEntity = actividadesData.get(1);
        grupoDeInteresActividadesLogic.getActividad(entity.getId(), actividadEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Actividades asociadas a una instancia
     * de GrupoDeInteres.
     */
    @Test
    public void replaceActividadesTest() {
        GrupoDeInteresEntity entity = data.get(0);
        List<ActividadEntity> list = actividadesData.subList(1, 3);
        grupoDeInteresActividadesLogic.replaceActividades(entity.getId(), list);

        entity = grupoDeInteresLogic.getGrupoDeInteres(entity.getId());
        Assert.assertFalse(entity.getActividades().contains(actividadesData.get(0)));
        Assert.assertTrue(entity.getActividades().contains(actividadesData.get(1)));
        Assert.assertTrue(entity.getActividades().contains(actividadesData.get(2)));
    }
}
