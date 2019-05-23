/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.persistence;

import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import co.edu.uniandes.csw.idiomas.persistence.CoordinadorPersistence;
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
 * Pruebas de persistencia de Coordinador
 *
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class CoordinadorPersistenceTest {
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------

    /**
     * Inyecta la dependencia de CoordinadorPersistence.
     */
    @Inject
    private CoordinadorPersistence coordinadorPersistence;

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
    UserTransaction utx;

    /**
     * Lista de los datos de prueba.
     */
    private List<CoordinadorEntity> data = new ArrayList<>();
    
    // ------------------------------------------------------------------------
    // Métodos
    // ------------------------------------------------------------------------

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CoordinadorEntity.class.getPackage())
                .addPackage(CoordinadorPersistence.class.getPackage())
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
            em.joinTransaction();
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
        em.createQuery("delete from CoordinadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CoordinadorEntity entity = factory.manufacturePojo(CoordinadorEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Coordinador.
     */
    @Test
    public void createCoordinadorTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CoordinadorEntity newEntity = factory.manufacturePojo(CoordinadorEntity.class);
        CoordinadorEntity result = coordinadorPersistence.create(newEntity);

        Assert.assertNotNull(result);

        CoordinadorEntity entity = em.find(CoordinadorEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para consultar la lista de Coordinadores.
     */
    @Test
    public void getCoordinadoresTest() {
        List<CoordinadorEntity> list = coordinadorPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CoordinadorEntity ent : list) {
            boolean found = false;
            for (CoordinadorEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Coordinador.
     */
    @Test
    public void getCoordinadorTest() {
        CoordinadorEntity entity = data.get(0);
        CoordinadorEntity newEntity = coordinadorPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
    }

    /**
     * Prueba para actualizar un Coordinador.
     */
    @Test
    public void updateCoordinadorTest() {
        CoordinadorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CoordinadorEntity newEntity = factory.manufacturePojo(CoordinadorEntity.class);

        newEntity.setId(entity.getId());

        coordinadorPersistence.update(newEntity);

        CoordinadorEntity resp = em.find(CoordinadorEntity.class, entity.getId());
        
        Assert.assertEquals(resp.getNombre(), newEntity.getNombre());
        Assert.assertEquals(resp.getContrasenia(), newEntity.getContrasenia());
    }

    /**
     * Prueba para eliminar un Coordinador.
     */
    @Test
    public void deleteCoordinadorTest() {
        CoordinadorEntity entity = data.get(0);
        coordinadorPersistence.delete(entity.getId());
        CoordinadorEntity deleted = em.find(CoordinadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para consultar un Coordinador por nombre.
     */
    @Test
    public void findCoordinadorByNameTest() {
        CoordinadorEntity entity = data.get(0);
        CoordinadorEntity newEntity = coordinadorPersistence.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = coordinadorPersistence.findByName(null);
        Assert.assertNull(newEntity);
        newEntity = coordinadorPersistence.findByName("");
        Assert.assertNull(newEntity);
    }
}

