/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.CoordinadorLogic;
import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.CoordinadorPersistence;
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
 * Pruebas de lógica de Coordinador
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class CoordinadorLogicTest {

    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de dependencias con CoordinadorLogic.
     */
    @Inject
    private CoordinadorLogic coordinadorLogic;
    
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

    private List<CoordinadorEntity> data = new ArrayList<>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CoordinadorEntity.class.getPackage())
                .addPackage(CoordinadorLogic.class.getPackage())
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
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from CoordinadorEntity").executeUpdate();
        em.createQuery("delete from CoordinadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 4; i++) {
            CoordinadorEntity entity = factory.manufacturePojo(CoordinadorEntity.class);
            em.persist(entity);
            entity.setActividades(new ArrayList<>());
            data.add(entity);
        }

//        CoordinadorEntity coordinador = data.get(2);
//        ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
//        entity.getCoordinadores().add(coordinador);
//        em.persist(entity);
//        coordinador.getActividades().add(entity);
    }

    /**
     * Prueba para crear un Coordinador.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createCoordinadorTest() throws BusinessLogicException {
        CoordinadorEntity newEntity = factory.manufacturePojo(CoordinadorEntity.class);
        CoordinadorEntity result = coordinadorLogic.createCoordinador(newEntity);
        
        Assert.assertNotNull(result);
        CoordinadorEntity entity = em.find(CoordinadorEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
        
        
    }
    
    /**
     * Prueba para crear un Coordinador con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCoordinadorTestConNombreInvalido1() throws BusinessLogicException {
        CoordinadorEntity newEntity = factory.manufacturePojo(CoordinadorEntity.class);
        newEntity.setNombre("");
        coordinadorLogic.createCoordinador(newEntity);
    }

    /**
     * Prueba para crear un Coordinador con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCoordinadorTestConNombreInvalido2() throws BusinessLogicException {
        CoordinadorEntity newEntity = factory.manufacturePojo(CoordinadorEntity.class);
        newEntity.setNombre(null);
        coordinadorLogic.createCoordinador(newEntity);
    }
    
    /**
     * Prueba para crear un Coordinador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCoordinadorTestConContraseniaInvalido1() throws BusinessLogicException {
        CoordinadorEntity newEntity = factory.manufacturePojo(CoordinadorEntity.class);
        newEntity.setContrasenia("");
        coordinadorLogic.createCoordinador(newEntity);
    }

    /**
     * Prueba para crear un Coordinador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCoordinadorTestConContraseniaInvalido2() throws BusinessLogicException {
        CoordinadorEntity newEntity = factory.manufacturePojo(CoordinadorEntity.class);
        newEntity.setContrasenia(null);
        coordinadorLogic.createCoordinador(newEntity);
    }
    
    /**
     * Prueba para crear un Coordinador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCoordinadorTestConContraseniaInvalido3() throws BusinessLogicException {
        CoordinadorEntity newEntity = factory.manufacturePojo(CoordinadorEntity.class);
        newEntity.setContrasenia("a");
        coordinadorLogic.createCoordinador(newEntity);
    }
    
    
    
    /**
     * Prueba para crear un Coordinador ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCoordinadorTestYaExistente() throws BusinessLogicException {
        List<CoordinadorEntity> coordinadores = coordinadorLogic.getCoordinadores();
        CoordinadorEntity newEntity = coordinadores.get(0);
        coordinadorLogic.createCoordinador(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de Coordinadores.
     */
    @Test
    public void getCoordinadoresTest() {
        List<CoordinadorEntity> list = coordinadorLogic.getCoordinadores();
        Assert.assertEquals(data.size(), list.size());
        for (CoordinadorEntity entity : list) {
            boolean found = false;
            for (CoordinadorEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
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
        CoordinadorEntity newEntity = coordinadorLogic.getCoordinador(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
    }
    
    /**
     * Prueba para consultar un Coordinador.
     */
    @Test
    public void getCoordinadorNoExistenteTest() {
        CoordinadorEntity resultEntity = coordinadorLogic.getCoordinador(-1L);
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar un Coordinador.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void updateCoordinadorTest() throws BusinessLogicException {
        CoordinadorEntity entity = data.get(0);
        CoordinadorEntity pojoEntity = factory.manufacturePojo(CoordinadorEntity.class);

        pojoEntity.setId(entity.getId());

        coordinadorLogic.updateCoordinador(pojoEntity.getId(), pojoEntity);

        CoordinadorEntity newEntity = em.find(CoordinadorEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), newEntity.getId());
        Assert.assertEquals(pojoEntity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(pojoEntity.getContrasenia(), newEntity.getContrasenia());
    }
    
    /**
     * Prueba para crear un Coordinador con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateCoordinadorTestConNombreInvalido1() throws BusinessLogicException {
        CoordinadorEntity entity = data.get(0);
        CoordinadorEntity pojoEntity = factory.manufacturePojo(CoordinadorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("");

        coordinadorLogic.updateCoordinador(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un Coordinador con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateCoordinadorTestConNombreInvalido2() throws BusinessLogicException {
        CoordinadorEntity entity = data.get(0);
        CoordinadorEntity pojoEntity = factory.manufacturePojo(CoordinadorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(null);

        coordinadorLogic.updateCoordinador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Coordinador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateCoordinadorTestConContraseniaInvalido1() throws BusinessLogicException {
        CoordinadorEntity entity = data.get(0);
        CoordinadorEntity pojoEntity = factory.manufacturePojo(CoordinadorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia("");

        coordinadorLogic.updateCoordinador(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un Coordinador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateCoordinadorTestConContraseniaInvalido2() throws BusinessLogicException {
        CoordinadorEntity entity = data.get(0);
        CoordinadorEntity pojoEntity = factory.manufacturePojo(CoordinadorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia(null);

        coordinadorLogic.updateCoordinador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Coordinador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateCoordinadorTestConContraseniaInvalido3() throws BusinessLogicException {
        CoordinadorEntity entity = data.get(0);
        CoordinadorEntity pojoEntity = factory.manufacturePojo(CoordinadorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia("a");

        coordinadorLogic.updateCoordinador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Coordinador ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateCoordinadorTestYaExistente() throws BusinessLogicException {
        List<CoordinadorEntity> coordinadores = coordinadorLogic.getCoordinadores();
        CoordinadorEntity newEntity = coordinadores.get(0);
        coordinadorLogic.updateCoordinador(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para eliminar un Coordinador
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteCoordinadorTest() throws BusinessLogicException {
        CoordinadorEntity entity = data.get(0);
        coordinadorLogic.deleteCoordinador(entity.getId());
        CoordinadorEntity deleted = em.find(CoordinadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

//    /**
//     * Prueba para eliminar un Coordinador asociado a un comentario
//     *
//     * 
//     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void deleteCoordinadorConGrupoDeInteresTest() throws BusinessLogicException 
//    {
//        coordinadorLogic.deleteCoordinador(data.get(2).getId());
//    }
    
}
