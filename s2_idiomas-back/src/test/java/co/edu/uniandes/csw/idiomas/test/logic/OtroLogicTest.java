/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.OtroLogic;
import co.edu.uniandes.csw.idiomas.ejb.CoordinadorLogic;
import co.edu.uniandes.csw.idiomas.entities.OtroEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.OtroPersistence;
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
 * Pruebas de lógica de Otro
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class OtroLogicTest {

    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de dependencias con OtroLogic.
     */
    @Inject
    private OtroLogic otroLogic;
    
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

    private List<OtroEntity> data = new ArrayList<>();
    
    private List<CoordinadorEntity> coordinadorData = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OtroEntity.class.getPackage())
                .addPackage(OtroLogic.class.getPackage())
                .addPackage(OtroPersistence.class.getPackage())
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
        em.createQuery("delete from OtroEntity").executeUpdate();
        em.createQuery("delete from CoordinadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++)
        {
            CoordinadorEntity coordinador = factory.manufacturePojo(CoordinadorEntity.class);
            em.persist(coordinador);
            coordinadorData.add(coordinador);
        }
        for (int i = 0; i < 3; i++) 
        {
            OtroEntity entity = factory.manufacturePojo(OtroEntity.class);
            em.persist(entity);
            entity.setUsuarios(new ArrayList<>());
            entity.setComentarios(new ArrayList<>());
            entity.setCoordinadores(new ArrayList<>());
            entity.getCoordinadores().add(coordinadorData.get(0));
            // TODO : GC Poner calificación
            data.add(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Otro.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createOtroTest() throws BusinessLogicException {
        OtroEntity newEntity = factory.manufacturePojo(OtroEntity.class);
        CoordinadorEntity newCorEntity = factory.manufacturePojo(CoordinadorEntity.class);
        
        // TODO: GC Conectar con coordinador Logic
//        newCorEntity = corLogic.createCoordinador(newCorEntity);
//        newEntity.getCoordinadores().add(newCorEntity);
        OtroEntity result = otroLogic.createOtro(newEntity);
        Assert.assertNotNull(result);
        OtroEntity entity = em.find(OtroEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        
    }
    
    /**
     * Prueba para crear un Otro con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createOtroTestConNombreInvalido1() throws BusinessLogicException {
        OtroEntity newEntity = factory.manufacturePojo(OtroEntity.class);
        newEntity.setNombre("");
        otroLogic.createOtro(newEntity);
    }

    /**
     * Prueba para crear un Otro con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createOtroTestConNombreInvalido2() throws BusinessLogicException {
        OtroEntity newEntity = factory.manufacturePojo(OtroEntity.class);
        newEntity.setNombre(null);
        otroLogic.createOtro(newEntity);
    }
    
    /**
     * Prueba para crear un Otro sin un coordinador
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    // TODO: GC Conectar con coordinador
//    @Test(expected = BusinessLogicException.class)
//    public void createOtroTestSinCoordinador() throws BusinessLogicException {
//        OtroEntity newEntity = factory.manufacturePojo(OtroEntity.class);
//        newEntity.setCoordinadores(null);
//        otroLogic.createOtro(newEntity);
//    }
    
    /**
     * Prueba para crear un Otro ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createOtroTestYaExistente() throws BusinessLogicException {
        List<OtroEntity> otros = otroLogic.getOtros();
        OtroEntity newEntity = otros.get(0);
        otroLogic.createOtro(newEntity);
    }

    /**
     * Prueba para consultar un Otro.
     */
    @Test
    public void getOtroTest() {
        OtroEntity entity = data.get(0);
        OtroEntity resultEntity = otroLogic.getOtro(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getFecha(), entity.getFecha());
    }

    /**
     * Prueba para actualizar un Otro.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void updateOtroTest() throws BusinessLogicException {
        OtroEntity entity = data.get(0);
        OtroEntity pojoEntity = factory.manufacturePojo(OtroEntity.class);

        pojoEntity.setId(entity.getId());

        otroLogic.updateOtro(pojoEntity.getId(), pojoEntity);

        OtroEntity resp = em.find(OtroEntity.class, entity.getId());

        Assert.assertEquals(resp.getId(), entity.getId());
//        Assert.assertEquals(resp.getNombre(), entity.getNombre());
//        Assert.assertEquals(resp.getDescripcion(), entity.getDescripcion());
//        Assert.assertEquals(resp.getFecha(), entity.getFecha());
    }

    /**
     * Prueba para eliminar un Otro
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteOtroTest() throws BusinessLogicException {
        OtroEntity entity = data.get(0);
        otroLogic.deleteOtro(entity.getId());
        OtroEntity deleted = em.find(OtroEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

//    /**
//     * Prueba para eliminar un Otro asociado a un usuario
//     *
//     * 
//     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void deleteOtroConUsuarioTest() throws BusinessLogicException {
//        otroLogic.deleteOtro(data.get(2).getId());
//    }

    /**
     * Prueba para eliminar un Otro asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
//    @Test(expected = BusinessLogicException.class)
//    public void deleteOtroConComentarioTest() throws BusinessLogicException {
//        otroLogic.deleteOtro(data.get(1).getId());
//    }
    
}
