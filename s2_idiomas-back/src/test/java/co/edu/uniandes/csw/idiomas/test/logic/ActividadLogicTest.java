/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.ActividadLogic;
import co.edu.uniandes.csw.idiomas.ejb.CoordinadorLogic;
import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
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
 * Pruebas de lógica de Actividad
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class ActividadLogicTest {

    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de dependencias con ActividadLogic.
     */
    @Inject
    private ActividadLogic actividadLogic;
    
    /**
     * Inyección de dependencias con CoordinadorLogic
     */
    @Inject
    private CoordinadorLogic corLogic;
    
    /**
     * Contexto de persistencia que se va a utilizar para acceder a la base 
     * de datos.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para marcar las transacciones del "em" (Entity Manager) cuando
     * se crean/borran datos.
     */
    @Inject
    private UserTransaction utx;

    private List<ActividadEntity> data = new ArrayList<>();


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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from ActividadEntity").executeUpdate();
        em.createQuery("delete from CoordinadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) 
        {
            ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setActividad(data.get(1));
        em.persist(comentario);
        data.get(1).getComentarios().add(comentario);
    }

    /**
     * Prueba para crear un Actividad.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createActividadTest() throws BusinessLogicException {
        ActividadEntity newEntity = factory.manufacturePojo(ActividadEntity.class);
        CoordinadorEntity newCorEntity = factory.manufacturePojo(CoordinadorEntity.class);
        
        // TODO: GC Conectar con coordinador Logic
//        newCorEntity = corLogic.createCoordinador(newCorEntity);
//        newEntity.getCoordinadores().add(newCorEntity);
        ActividadEntity result = actividadLogic.createActividad(newEntity);
        Assert.assertNotNull(result);
        ActividadEntity entity = em.find(ActividadEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        
    }
    
    /**
     * Prueba para crear un Actividad con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadTestConNombreInvalido1() throws BusinessLogicException {
        ActividadEntity newEntity = factory.manufacturePojo(ActividadEntity.class);
        newEntity.setNombre("");
        actividadLogic.createActividad(newEntity);
    }

    /**
     * Prueba para crear un Actividad con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadTestConNombreInvalido2() throws BusinessLogicException {
        ActividadEntity newEntity = factory.manufacturePojo(ActividadEntity.class);
        newEntity.setNombre(null);
        actividadLogic.createActividad(newEntity);
    }
    
    /**
     * Prueba para crear un Actividad sin un coordinador
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    // TODO: GC Conectar con coordinador
//    @Test(expected = BusinessLogicException.class)
//    public void createActividadTestSinCoordinador() throws BusinessLogicException {
//        ActividadEntity newEntity = factory.manufacturePojo(ActividadEntity.class);
//        newEntity.setCoordinadores(null);
//        actividadLogic.createActividad(newEntity);
//    }
    
    /**
     * Prueba para crear un Actividad ya existente.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createActividadTestYaExistente() throws BusinessLogicException {
        List<ActividadEntity> actividades = actividadLogic.getActividades();
        ActividadEntity newEntity = actividades.get(0);
        actividadLogic.createActividad(newEntity);
    }

    /**
     * Prueba para consultar un Actividad.
     */
    @Test
    public void getActividadTest() {
        ActividadEntity entity = data.get(0);
        ActividadEntity resultEntity = actividadLogic.getActividad(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getFecha(), entity.getFecha());
    }
    
    /**
     * Prueba para consultar un Actividad.
     */
    @Test
    public void getActividadNoExistenteTest() {
        ActividadEntity resultEntity = actividadLogic.getActividad(-1L);
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar un Actividad.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void updateActividadTest() throws BusinessLogicException {
        ActividadEntity entity = data.get(0);
        ActividadEntity pojoEntity = factory.manufacturePojo(ActividadEntity.class);

        pojoEntity.setId(entity.getId());

        actividadLogic.updateActividad(pojoEntity.getId(), pojoEntity);

        ActividadEntity resp = em.find(ActividadEntity.class, entity.getId());

        Assert.assertEquals(resp.getId(), pojoEntity.getId());
        Assert.assertEquals(resp.getNombre(), pojoEntity.getNombre());
        Assert.assertEquals(resp.getDescripcion(), pojoEntity.getDescripcion());
        Assert.assertEquals(resp.getFecha(), pojoEntity.getFecha());
    }
    
    /**
     * Prueba para crear un Actividad con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateActividadTestConNombreInvalido1() throws BusinessLogicException {
        ActividadEntity newEntity = factory.manufacturePojo(ActividadEntity.class);
        newEntity.setNombre("");
        actividadLogic.updateActividad(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para crear un Actividad con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateActividadTestConNombreInvalido2() throws BusinessLogicException {
        ActividadEntity newEntity = factory.manufacturePojo(ActividadEntity.class);
        newEntity.setNombre(null);
        actividadLogic.updateActividad(newEntity.getId(), newEntity);
    }
    
    /**
     * Prueba para crear un Actividad sin un coordinador
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    // TODO: GC Conectar con coordinador
//    @Test(expected = BusinessLogicException.class)
//    public void createActividadTestSinCoordinador() throws BusinessLogicException {
//        ActividadEntity newEntity = factory.manufacturePojo(ActividadEntity.class);
//        newEntity.setCoordinadores(null);
//        actividadLogic.createActividad(newEntity);
//    }
    
    /**
     * Prueba para crear un Actividad ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateActividadTestYaExistente() throws BusinessLogicException {
        List<ActividadEntity> actividades = actividadLogic.getActividades();
        ActividadEntity newEntity = actividades.get(0);
        actividadLogic.updateActividad(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para eliminar un Actividad
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteActividadTest() throws BusinessLogicException {
        ActividadEntity entity = data.get(0);
        actividadLogic.deleteActividad(entity.getId());
        ActividadEntity deleted = em.find(ActividadEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

//    /**
//     * Prueba para eliminar un Actividad asociado a un usuario
//     *
//     * 
//     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void deleteActividadConUsuarioTest() throws BusinessLogicException {
//        actividadLogic.deleteActividad(data.get(2).getId());
//    }

    /**
     * Prueba para eliminar un Actividad asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteActividadConComentarioTest() throws BusinessLogicException 
    {
        actividadLogic.deleteActividad(data.get(1).getId());
    }
    
}
