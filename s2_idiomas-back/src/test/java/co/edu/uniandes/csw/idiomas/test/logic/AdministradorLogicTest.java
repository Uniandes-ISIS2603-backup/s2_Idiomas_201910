/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.AdministradorLogic;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.AdministradorEntity;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.AdministradorPersistence;
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
 * Pruebas de lógica de Administrador
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class AdministradorLogicTest {

    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de dependencias con AdministradorLogic.
     */
    @Inject
    private AdministradorLogic administradorLogic;
    
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

    private List<AdministradorEntity> data = new ArrayList<>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AdministradorEntity.class.getPackage())
                .addPackage(AdministradorLogic.class.getPackage())
                .addPackage(AdministradorPersistence.class.getPackage())
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
        em.createQuery("delete from AdministradorEntity").executeUpdate();
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from AdministradorEntity").executeUpdate();
        em.createQuery("delete from AdministradorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 4; i++) {
            AdministradorEntity entity = factory.manufacturePojo(AdministradorEntity.class);
            em.persist(entity);
            entity.setGruposDeInteres(new ArrayList<>());
            data.add(entity);
        }

        GrupoDeInteresEntity grupoDeInteres = factory.manufacturePojo(GrupoDeInteresEntity.class);
        grupoDeInteres.setAdministrador(data.get(2));
        em.persist(grupoDeInteres);
        data.get(2).getGruposDeInteres().add(grupoDeInteres);
    }

    /**
     * Prueba para crear un Administrador.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createAdministradorTest() throws BusinessLogicException {
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);
        AdministradorEntity result = administradorLogic.createAdministrador(newEntity);
        
        Assert.assertNotNull(result);
        AdministradorEntity entity = em.find(AdministradorEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
        
        
    }
    
    /**
     * Prueba para crear un Administrador con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createAdministradorTestConNombreInvalido1() throws BusinessLogicException {
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);
        newEntity.setNombre("");
        administradorLogic.createAdministrador(newEntity);
    }

    /**
     * Prueba para crear un Administrador con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createAdministradorTestConNombreInvalido2() throws BusinessLogicException {
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);
        newEntity.setNombre(null);
        administradorLogic.createAdministrador(newEntity);
    }
    
    /**
     * Prueba para crear un Administrador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createAdministradorTestConContraseniaInvalido1() throws BusinessLogicException {
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);
        newEntity.setContrasenia("");
        administradorLogic.createAdministrador(newEntity);
    }

    /**
     * Prueba para crear un Administrador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createAdministradorTestConContraseniaInvalido2() throws BusinessLogicException {
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);
        newEntity.setContrasenia(null);
        administradorLogic.createAdministrador(newEntity);
    }
    
    /**
     * Prueba para crear un Administrador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createAdministradorTestConContraseniaInvalido3() throws BusinessLogicException {
        AdministradorEntity newEntity = factory.manufacturePojo(AdministradorEntity.class);
        newEntity.setContrasenia("a");
        administradorLogic.createAdministrador(newEntity);
    }
    
    
    
    /**
     * Prueba para crear un Administrador ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createAdministradorTestYaExistente() throws BusinessLogicException {
        List<AdministradorEntity> administradores = administradorLogic.getAdministradores();
        AdministradorEntity newEntity = administradores.get(0);
        administradorLogic.createAdministrador(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de Administradores.
     */
    @Test
    public void getAdministradoresTest() {
        List<AdministradorEntity> list = administradorLogic.getAdministradores();
        Assert.assertEquals(data.size(), list.size());
        for (AdministradorEntity entity : list) {
            boolean found = false;
            for (AdministradorEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Administrador.
     */
    @Test
    public void getAdministradorTest() {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity newEntity = administradorLogic.getAdministrador(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
    }
    
    /**
     * Prueba para consultar un Administrador.
     */
    @Test
    public void getAdministradorNoExistenteTest() {
        AdministradorEntity resultEntity = administradorLogic.getAdministrador(-1L);
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar un Administrador.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void updateAdministradorTest() throws BusinessLogicException {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity pojoEntity = factory.manufacturePojo(AdministradorEntity.class);

        pojoEntity.setId(entity.getId());

        administradorLogic.updateAdministrador(pojoEntity.getId(), pojoEntity);

        AdministradorEntity newEntity = em.find(AdministradorEntity.class, entity.getId());

        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
    }
    
    /**
     * Prueba para crear un Administrador con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateAdministradorTestConNombreInvalido1() throws BusinessLogicException {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity pojoEntity = factory.manufacturePojo(AdministradorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("");

        administradorLogic.updateAdministrador(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un Administrador con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateAdministradorTestConNombreInvalido2() throws BusinessLogicException {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity pojoEntity = factory.manufacturePojo(AdministradorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(null);

        administradorLogic.updateAdministrador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Administrador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateAdministradorTestConContraseniaInvalido1() throws BusinessLogicException {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity pojoEntity = factory.manufacturePojo(AdministradorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia("");

        administradorLogic.updateAdministrador(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un Administrador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateAdministradorTestConContraseniaInvalido2() throws BusinessLogicException {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity pojoEntity = factory.manufacturePojo(AdministradorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia(null);

        administradorLogic.updateAdministrador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Administrador con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateAdministradorTestConContraseniaInvalido3() throws BusinessLogicException {
        AdministradorEntity entity = data.get(0);
        AdministradorEntity pojoEntity = factory.manufacturePojo(AdministradorEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia("a");

        administradorLogic.updateAdministrador(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Administrador ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateAdministradorTestYaExistente() throws BusinessLogicException {
        List<AdministradorEntity> administradores = administradorLogic.getAdministradores();
        AdministradorEntity newEntity = administradores.get(0);
        administradorLogic.updateAdministrador(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para eliminar un Administrador
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteAdministradorTest() throws BusinessLogicException {
        AdministradorEntity entity = data.get(0);
        administradorLogic.deleteAdministrador(entity.getId());
        AdministradorEntity deleted = em.find(AdministradorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Administrador asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteAdministradorConGrupoDeInteresTest() throws BusinessLogicException 
    {
        administradorLogic.deleteAdministrador(data.get(2).getId());
    }
    
}
