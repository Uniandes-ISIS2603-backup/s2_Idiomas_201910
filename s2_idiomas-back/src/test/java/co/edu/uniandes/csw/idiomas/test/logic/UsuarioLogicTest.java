/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.UsuarioPersistence;
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
 * Pruebas de lógica de Usuario
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class UsuarioLogicTest {

    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de dependencias con UsuarioLogic.
     */
    @Inject
    private UsuarioLogic usuarioLogic;
    
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

    private List<UsuarioEntity> data = new ArrayList<>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from AdministradorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 4; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
        }

        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setPersona(data.get(2));
        em.persist(comentario);
        data.get(2).getComentarios().add(comentario);
    }

    /**
     * Prueba para crear un Usuario.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = usuarioLogic.createUsuario(newEntity);
        
        Assert.assertNotNull(result);
        UsuarioEntity entity = em.find(UsuarioEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
        
        
    }
    
    /**
     * Prueba para crear un Usuario con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioTestConNombreInvalido1() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setNombre("");
        usuarioLogic.createUsuario(newEntity);
    }

    /**
     * Prueba para crear un Usuario con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioTestConNombreInvalido2() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setNombre(null);
        usuarioLogic.createUsuario(newEntity);
    }
    
    /**
     * Prueba para crear un Usuario con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioTestConContraseniaInvalido1() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setContrasenia("");
        usuarioLogic.createUsuario(newEntity);
    }

    /**
     * Prueba para crear un Usuario con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioTestConContraseniaInvalido2() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setContrasenia(null);
        usuarioLogic.createUsuario(newEntity);
    }
    
    /**
     * Prueba para crear un Usuario con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioTestConContraseniaInvalido3() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setContrasenia("a");
        usuarioLogic.createUsuario(newEntity);
    }
    
    
    
    /**
     * Prueba para crear un Usuario ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioTestYaExistente() throws BusinessLogicException {
        List<UsuarioEntity> usuarios = usuarioLogic.getUsuarios();
        UsuarioEntity newEntity = usuarios.get(0);
        usuarioLogic.createUsuario(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de Usuarios.
     */
    @Test
    public void getUsuariosTest() {
        List<UsuarioEntity> list = usuarioLogic.getUsuarios();
        Assert.assertEquals(data.size(), list.size());
        for (UsuarioEntity entity : list) {
            boolean found = false;
            for (UsuarioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Usuario.
     */
    @Test
    public void getUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity newEntity = usuarioLogic.getUsuario(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
    }
    
    /**
     * Prueba para consultar un Usuario.
     */
    @Test
    public void getUsuarioNoExistenteTest() {
        UsuarioEntity resultEntity = usuarioLogic.getUsuario(-1L);
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar un Usuario.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void updateUsuarioTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);

        pojoEntity.setId(entity.getId());

        usuarioLogic.updateUsuario(pojoEntity.getId(), pojoEntity);

        UsuarioEntity newEntity = em.find(UsuarioEntity.class, entity.getId());

        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
    }
    
    /**
     * Prueba para crear un Usuario con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateUsuarioTestConNombreInvalido1() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("");

        usuarioLogic.updateUsuario(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un Usuario con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateUsuarioTestConNombreInvalido2() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(null);

        usuarioLogic.updateUsuario(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Usuario con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateUsuarioTestConContraseniaInvalido1() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia("");

        usuarioLogic.updateUsuario(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un Usuario con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateUsuarioTestConContraseniaInvalido2() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia(null);

        usuarioLogic.updateUsuario(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Usuario con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateUsuarioTestConContraseniaInvalido3() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia("a");

        usuarioLogic.updateUsuario(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Usuario ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateUsuarioTestYaExistente() throws BusinessLogicException {
        List<UsuarioEntity> usuarios = usuarioLogic.getUsuarios();
        UsuarioEntity newEntity = usuarios.get(0);
        usuarioLogic.updateUsuario(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para eliminar un Usuario
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteUsuarioTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        usuarioLogic.deleteUsuario(entity.getId());
        UsuarioEntity deleted = em.find(UsuarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Usuario asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteUsuarioConComentarioTest() throws BusinessLogicException 
    {
        usuarioLogic.deleteUsuario(data.get(2).getId());
    }
    
}
