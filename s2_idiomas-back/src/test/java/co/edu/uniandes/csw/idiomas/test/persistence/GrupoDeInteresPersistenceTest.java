/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.persistence;

import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
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
 * Pruebas de persistencia de GrupoDeInteres
 *
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class GrupoDeInteresPersistenceTest {
    
    // ------------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------------

    /**
     * Inyecta la dependencia de GrupoDeInteresPersistence.
     */
    @Inject
    private GrupoDeInteresPersistence grupoDeInteresPersistence;

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
    private List<GrupoDeInteresEntity> data = new ArrayList<>();
    
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
                .addPackage(GrupoDeInteresEntity.class.getPackage())
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
        em.createQuery("delete from GrupoDeInteresEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            GrupoDeInteresEntity entity = factory.manufacturePojo(GrupoDeInteresEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un GrupoDeInteres.
     */
    @Test
    public void createGrupoDeInteresTest() {
        PodamFactory factory = new PodamFactoryImpl();
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        GrupoDeInteresEntity result = grupoDeInteresPersistence.create(newEntity);

        Assert.assertNotNull(result);

        GrupoDeInteresEntity entity = em.find(GrupoDeInteresEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para consultar la lista de GruposDeInteres.
     */
    @Test
    public void getGruposDeInteresTest() {
        List<GrupoDeInteresEntity> list = grupoDeInteresPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (GrupoDeInteresEntity ent : list) {
            boolean found = false;
            for (GrupoDeInteresEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un GrupoDeInteres.
     */
    @Test
    public void getGrupoDeInteresTest() {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity newEntity = grupoDeInteresPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(entity.getIdioma(), newEntity.getIdioma());
    }

    /**
     * Prueba para actualizar un GrupoDeInteres.
     */
    @Test
    public void updateGrupoDeInteresTest() {
        GrupoDeInteresEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);

        newEntity.setId(entity.getId());

        grupoDeInteresPersistence.update(newEntity);

        GrupoDeInteresEntity resp = em.find(GrupoDeInteresEntity.class, entity.getId());
        
        Assert.assertEquals(resp.getNombre(), newEntity.getNombre());
        Assert.assertEquals(resp.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(resp.getIdioma(), newEntity.getIdioma());
    }

    /**
     * Prueba para eliminar un GrupoDeInteres.
     */
    @Test
    public void deleteGrupoDeInteresTest() {
        GrupoDeInteresEntity entity = data.get(0);
        grupoDeInteresPersistence.delete(entity.getId());
        GrupoDeInteresEntity deleted = em.find(GrupoDeInteresEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para consultar un GrupoDeInteres por nombre.
     */
    @Test
    public void findGrupoDeInteresByNameTest() {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity newEntity = grupoDeInteresPersistence.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = grupoDeInteresPersistence.findByName(null);
        Assert.assertNull(newEntity);
        newEntity = grupoDeInteresPersistence.findByName("");
        Assert.assertNull(newEntity);
    }
}

