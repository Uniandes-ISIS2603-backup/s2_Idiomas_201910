/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.ActividadComentariosLogic;
import co.edu.uniandes.csw.idiomas.ejb.ActividadLogic;
import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.ActividadPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de lógica de la relación Actividad - Comentario
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class ActividadComentariosLogicTest 
{
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ActividadLogic actividadLogic;
    @Inject
    private ActividadComentariosLogic actividadComentariosLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ActividadEntity> data = new ArrayList<ActividadEntity>();

    private List<ComentarioEntity> comentariosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ActividadEntity.class.getPackage())
                .addPackage(ActividadLogic.class.getPackage())
                .addPackage(ActividadPersistence.class.getPackage())
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
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from ActividadEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentarios = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(comentarios);
            comentariosData.add(comentarios);
        }
        for (int i = 0; i < 3; i++) {
            ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                comentariosData.get(i).setActividad(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Comentarios existente a un Actividad.
     */
    @Test
    public void addComentariosTest() {
        ActividadEntity entity = data.get(0);
        ComentarioEntity comentarioEntity = comentariosData.get(1);
        ComentarioEntity response = actividadComentariosLogic.addComentario(comentarioEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(comentarioEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Comentarios asociadas a una
     * instancia Actividad.
     */
    @Test
    public void getComentariosTest() {
        List<ComentarioEntity> list = actividadComentariosLogic.getComentarios(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Comentarios asociada a una instancia
     * Actividad.
     *
     * @throws co.edu.uniandes.csw.comentariostore.exceptions.BusinessLogicException
     */
    @Test
    public void getComentarioTest() throws BusinessLogicException {
        ActividadEntity entity = data.get(0);
        ComentarioEntity comentarioEntity = comentariosData.get(0);
        ComentarioEntity response = actividadComentariosLogic.getComentario(entity.getId(), comentarioEntity.getId());

        Assert.assertEquals(comentarioEntity.getId(), response.getId());
        Assert.assertEquals(comentarioEntity.getActividad(), response.getActividad());
        Assert.assertEquals(comentarioEntity.getPersona(), response.getPersona());
        Assert.assertEquals(comentarioEntity.getFecha(), response.getFecha());
    }

    /**
     * Prueba para obtener una instancia de Comentarios asociada a una instancia
     * Actividad que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.comentariostore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getComentarioNoAsociadoTest() throws BusinessLogicException {
        ActividadEntity entity = data.get(0);
        ComentarioEntity comentarioEntity = comentariosData.get(1);
        actividadComentariosLogic.getComentario(entity.getId(), comentarioEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Comentarios asociadas a una instancia
     * de Actividad.
     */
    @Test
    public void replaceComentariosTest() {
        ActividadEntity entity = data.get(0);
        List<ComentarioEntity> list = comentariosData.subList(1, 3);
        actividadComentariosLogic.replaceComentarios(entity.getId(), list);

        entity = actividadLogic.getActividad(entity.getId());
        Assert.assertFalse(entity.getComentarios().contains(comentariosData.get(0)));
        Assert.assertTrue(entity.getComentarios().contains(comentariosData.get(1)));
        Assert.assertTrue(entity.getComentarios().contains(comentariosData.get(2)));
    }
}
