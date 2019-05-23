/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.EncuentroLogic;
import co.edu.uniandes.csw.idiomas.ejb.CoordinadorLogic;
import co.edu.uniandes.csw.idiomas.entities.EncuentroEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.EncuentroPersistence;
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
 * Pruebas de lógica de Encuentro
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class EncuentroLogicTest {

    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de dependencias con EncuentroLogic.
     */
    @Inject
    private EncuentroLogic encuentroLogic;
    
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

    private List<EncuentroEntity> data = new ArrayList<>();
    
    private List<CoordinadorEntity> coordinadorData = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EncuentroEntity.class.getPackage())
                .addPackage(EncuentroLogic.class.getPackage())
                .addPackage(EncuentroPersistence.class.getPackage())
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
        em.createQuery("delete from EncuentroEntity").executeUpdate();
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
            EncuentroEntity entity = factory.manufacturePojo(EncuentroEntity.class);
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
     * Prueba para crear un Encuentro.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createEncuentroTest() throws BusinessLogicException {
        EncuentroEntity newEntity = factory.manufacturePojo(EncuentroEntity.class);
        CoordinadorEntity newCorEntity = factory.manufacturePojo(CoordinadorEntity.class);
        
        // TODO: GC Conectar con coordinador Logic
//        newCorEntity = corLogic.createCoordinador(newCorEntity);
//        newEntity.getCoordinadores().add(newCorEntity);
        if(newEntity.getNumeroMaxAsistentes() <= 0)
        {
            newEntity.setNumeroMaxAsistentes((newEntity.getNumeroMaxAsistentes()*(-1)+1));
        }
        EncuentroEntity result = encuentroLogic.createEncuentro(newEntity);
        Assert.assertNotNull(result);
        EncuentroEntity entity = em.find(EncuentroEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        
    }
    
    /**
     * Prueba para crear un Encuentro con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createEncuentroTestConNombreInvalido1() throws BusinessLogicException {
        EncuentroEntity newEntity = factory.manufacturePojo(EncuentroEntity.class);
        newEntity.setNombre("");
        encuentroLogic.createEncuentro(newEntity);
    }

    /**
     * Prueba para crear un Encuentro con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createEncuentroTestConNombreInvalido2() throws BusinessLogicException {
        EncuentroEntity newEntity = factory.manufacturePojo(EncuentroEntity.class);
        newEntity.setNombre(null);
        encuentroLogic.createEncuentro(newEntity);
    }
    
    /**
     * Prueba para crear un Encuentro sin un coordinador
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    // TODO: GC Conectar con coordinador
//    @Test(expected = BusinessLogicException.class)
//    public void createEncuentroTestSinCoordinador() throws BusinessLogicException {
//        EncuentroEntity newEntity = factory.manufacturePojo(EncuentroEntity.class);
//        newEntity.setCoordinadores(null);
//        encuentroLogic.createEncuentro(newEntity);
//    }
    
    /**
     * Prueba para crear un Encuentro ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createEncuentroTestYaExistente() throws BusinessLogicException {
        List<EncuentroEntity> encuentros = encuentroLogic.getEncuentros();
        EncuentroEntity newEntity = encuentros.get(0);
        encuentroLogic.createEncuentro(newEntity);
    }
    
    /**
     * Prueba para crear un Encuentro sin un lugar válido.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createEncuentroTestConLugarInvalido1() throws BusinessLogicException {
        EncuentroEntity newEntity = factory.manufacturePojo(EncuentroEntity.class);
        newEntity.setLugar("");
        encuentroLogic.createEncuentro(newEntity);
    }
    
    /**
     * Prueba para crear un Encuentro sin un lugar válido.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createEncuentroTestConLugarInvalido2() throws BusinessLogicException {
        EncuentroEntity newEntity = factory.manufacturePojo(EncuentroEntity.class);
        newEntity.setLugar(null);
        encuentroLogic.createEncuentro(newEntity);
    }
    
    /**
     * Prueba para crear un Encuentro sin un asistentes válido.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createEncuentroTestConAsistentesInvalido1() throws BusinessLogicException {
        EncuentroEntity newEntity = factory.manufacturePojo(EncuentroEntity.class);
        newEntity.setNumeroMaxAsistentes(null);
        encuentroLogic.createEncuentro(newEntity);
    }
    
    /**
     * Prueba para crear un Encuentro sin un asistentes válido.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createEncuentroTestConAsistentesInvalido2() throws BusinessLogicException {
        EncuentroEntity newEntity = factory.manufacturePojo(EncuentroEntity.class);
        newEntity.setNumeroMaxAsistentes(-1);
        encuentroLogic.createEncuentro(newEntity);
    }

    /**
     * Prueba para consultar un Encuentro.
     */
    @Test
    public void getEncuentroTest() {
        EncuentroEntity entity = data.get(0);
        EncuentroEntity resultEntity = encuentroLogic.getEncuentro(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getFecha(), entity.getFecha());
    }

    /**
     * Prueba para actualizar un Encuentro.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void updateEncuentroTest() throws BusinessLogicException {
        EncuentroEntity entity = data.get(0);
        EncuentroEntity pojoEntity = factory.manufacturePojo(EncuentroEntity.class);

        if(pojoEntity.getNumeroMaxAsistentes() <= 0)
        {
            pojoEntity.setNumeroMaxAsistentes((pojoEntity.getNumeroMaxAsistentes()*(-1)+1));
        }
        pojoEntity.setId(entity.getId());

        encuentroLogic.updateEncuentro(pojoEntity.getId(), pojoEntity);

        EncuentroEntity resp = em.find(EncuentroEntity.class, entity.getId());

        Assert.assertEquals(resp.getId(), entity.getId());
//        Assert.assertEquals(resp.getNombre(), entity.getNombre());
//        Assert.assertEquals(resp.getDescripcion(), entity.getDescripcion());
//        Assert.assertEquals(resp.getFecha(), entity.getFecha());
    }

    /**
     * Prueba para eliminar un Encuentro
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteEncuentroTest() throws BusinessLogicException {
        EncuentroEntity entity = data.get(0);
        encuentroLogic.deleteEncuentro(entity.getId());
        EncuentroEntity deleted = em.find(EncuentroEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

//    /**
//     * Prueba para eliminar un Encuentro asociado a un usuario
//     *
//     * 
//     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void deleteEncuentroConUsuarioTest() throws BusinessLogicException {
//        encuentroLogic.deleteEncuentro(data.get(2).getId());
//    }

    /**
     * Prueba para eliminar un Encuentro asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
//    @Test(expected = BusinessLogicException.class)
//    public void deleteEncuentroConComentarioTest() throws BusinessLogicException {
//        encuentroLogic.deleteEncuentro(data.get(1).getId());
//    }
    
}
