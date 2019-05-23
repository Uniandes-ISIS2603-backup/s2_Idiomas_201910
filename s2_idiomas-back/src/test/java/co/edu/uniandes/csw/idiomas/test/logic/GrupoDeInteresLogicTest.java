/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.GrupoDeInteresLogic;
import co.edu.uniandes.csw.idiomas.ejb.CoordinadorLogic;
import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.GrupoDeInteresPersistence;
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
 * Pruebas de lógica de GrupoDeInteres
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class GrupoDeInteresLogicTest {

    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de dependencias con GrupoDeInteresLogic.
     */
    @Inject
    private GrupoDeInteresLogic grupoDeInteresLogic;
    
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

    private List<GrupoDeInteresEntity> data = new ArrayList<>();
    
    private List<CoordinadorEntity> coordinadorData = new ArrayList<>();


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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from GrupoDeInteresEntity").executeUpdate();
        em.createQuery("delete from AdministradorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            GrupoDeInteresEntity entity = factory.manufacturePojo(GrupoDeInteresEntity.class);
            em.persist(entity);
            entity.setUsuarios(new ArrayList<>());
            data.add(entity);
        }
        GrupoDeInteresEntity grupoDeInteres = data.get(2);
        ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
        entity.ge().add(grupoDeInteres);
        em.persist(entity);
        grupoDeInteres.getBooks().add(entity);

        PrizeEntity prize = factory.manufacturePojo(PrizeEntity.class);
        prize.setGrupoDeInteres(data.get(1));
        em.persist(prize);
        data.get(1).getPrizes().add(prize);
    }

    /**
     * Prueba para crear un GrupoDeInteres.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createGrupoDeInteresTest() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        CoordinadorEntity newCorEntity = factory.manufacturePojo(CoordinadorEntity.class);
        
        // TODO: GC Conectar con coordinador Logic
//        newCorEntity = corLogic.createCoordinador(newCorEntity);
//        newEntity.getCoordinadores().add(newCorEntity);
        GrupoDeInteresEntity result = grupoDeInteresLogic.createGrupoDeInteres(newEntity);
        Assert.assertNotNull(result);
        GrupoDeInteresEntity entity = em.find(GrupoDeInteresEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        
    }
    
    /**
     * Prueba para crear un GrupoDeInteres con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestConNombreInvalido1() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setNombre("");
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }

    /**
     * Prueba para crear un GrupoDeInteres con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestConNombreInvalido2() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setNombre(null);
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }
    
    /**
     * Prueba para crear un GrupoDeInteres sin un coordinador
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    // TODO: GC Conectar con coordinador
//    @Test(expected = BusinessLogicException.class)
//    public void createGrupoDeInteresTestSinCoordinador() throws BusinessLogicException {
//        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
//        newEntity.setCoordinadores(null);
//        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
//    }
    
    /**
     * Prueba para crear un GrupoDeInteres ya existente.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestYaExistente() throws BusinessLogicException {
        List<GrupoDeInteresEntity> gruposDeInteres = grupoDeInteresLogic.getGruposDeInteres();
        GrupoDeInteresEntity newEntity = gruposDeInteres.get(0);
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }

    /**
     * Prueba para consultar un GrupoDeInteres.
     */
    @Test
    public void getGrupoDeInteresTest() {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity resultEntity = grupoDeInteresLogic.getGrupoDeInteres(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getFecha(), entity.getFecha());
    }
    
    /**
     * Prueba para consultar un GrupoDeInteres.
     */
    @Test
    public void getGrupoDeInteresNoExistenteTest() {
        GrupoDeInteresEntity resultEntity = grupoDeInteresLogic.getGrupoDeInteres(-1L);
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar un GrupoDeInteres.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void updateGrupoDeInteresTest() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity pojoEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);

        pojoEntity.setId(entity.getId());

        grupoDeInteresLogic.updateGrupoDeInteres(pojoEntity.getId(), pojoEntity);

        GrupoDeInteresEntity resp = em.find(GrupoDeInteresEntity.class, entity.getId());

        Assert.assertEquals(resp.getId(), entity.getId());
        Assert.assertEquals(resp.getNombre(), entity.getNombre());
        Assert.assertEquals(resp.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resp.getFecha(), entity.getFecha());
    }
    
    /**
     * Prueba para crear un GrupoDeInteres con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestConNombreInvalido1() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setNombre("");
        grupoDeInteresLogic.updateGrupoDeInteres(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para crear un GrupoDeInteres con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestConNombreInvalido2() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setNombre(null);
        grupoDeInteresLogic.updateGrupoDeInteres(newEntity.getId(), newEntity);
    }
    
    /**
     * Prueba para crear un GrupoDeInteres sin un coordinador
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    // TODO: GC Conectar con coordinador
//    @Test(expected = BusinessLogicException.class)
//    public void createGrupoDeInteresTestSinCoordinador() throws BusinessLogicException {
//        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
//        newEntity.setCoordinadores(null);
//        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
//    }
    
    /**
     * Prueba para crear un GrupoDeInteres ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestYaExistente() throws BusinessLogicException {
        List<GrupoDeInteresEntity> gruposDeInteres = grupoDeInteresLogic.getGruposDeInteres();
        GrupoDeInteresEntity newEntity = gruposDeInteres.get(0);
        grupoDeInteresLogic.updateGrupoDeInteres(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para eliminar un GrupoDeInteres
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteGrupoDeInteresTest() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        grupoDeInteresLogic.deleteGrupoDeInteres(entity.getId());
        GrupoDeInteresEntity deleted = em.find(GrupoDeInteresEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

//    /**
//     * Prueba para eliminar un GrupoDeInteres asociado a un usuario
//     *
//     * 
//     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void deleteGrupoDeInteresConUsuarioTest() throws BusinessLogicException {
//        grupoDeInteresLogic.deleteGrupoDeInteres(data.get(2).getId());
//    }

    /**
     * Prueba para eliminar un GrupoDeInteres asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteGrupoDeInteresConComentarioTest() throws BusinessLogicException 
    {
        grupoDeInteresLogic.deleteGrupoDeInteres(data.get(1).getId());
    }
    
}
