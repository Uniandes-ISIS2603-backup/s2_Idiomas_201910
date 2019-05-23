/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.GrupoDeInteresLogic;
import co.edu.uniandes.csw.idiomas.ejb.CoordinadorLogic;
import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.AdministradorEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.GrupoDeInteresEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
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
        for (int i = 0; i < 4; i++) {
            GrupoDeInteresEntity entity = factory.manufacturePojo(GrupoDeInteresEntity.class);
            em.persist(entity);
            entity.setUsuarios(new ArrayList<>());
            data.add(entity);
        }
        ActividadEntity actividad = factory.manufacturePojo(ActividadEntity.class);
        actividad.setGrupoDeInteres(data.get(1));
        em.persist(actividad);
        data.get(1).getActividades().add(actividad);

        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setGrupoDeInteres(data.get(2));
        em.persist(comentario);
        data.get(2).getComentarios().add(comentario);
        
        GrupoDeInteresEntity grupoDeInteres = data.get(3);
        UsuarioEntity usuarios = factory.manufacturePojo(UsuarioEntity.class);
        usuarios.getGruposDeInteres().add(grupoDeInteres);
        em.persist(usuarios);
        grupoDeInteres.getUsuarios().add(usuarios);
    }

    /**
     * Prueba para crear un GrupoDeInteres.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createGrupoDeInteresTest() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        // Asignar el administrador
        AdministradorEntity administrador = factory.manufacturePojo(AdministradorEntity.class);
        newEntity.setAdministrador(administrador);
        GrupoDeInteresEntity result = grupoDeInteresLogic.createGrupoDeInteres(newEntity);
        
        Assert.assertNotNull(result);
        GrupoDeInteresEntity entity = em.find(GrupoDeInteresEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(entity.getIdioma(), newEntity.getIdioma());
        
        
    }
    
    /**
     * Prueba para crear un GrupoDeInteres con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
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
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestConNombreInvalido2() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setNombre(null);
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }
    
    /**
     * Prueba para crear un GrupoDeInteres con idioma inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestConIdiomaInvalido1() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setIdioma("");
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }

    /**
     * Prueba para crear un GrupoDeInteres con idioma inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestConIdiomaInvalido2() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setIdioma(null);
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }
    
    /**
     * Prueba para crear un GrupoDeInteres con descripcion inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestConDescripcionInvalido1() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setDescripcion("");
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }

    /**
     * Prueba para crear un GrupoDeInteres con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestConDescripcionInvalido2() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setDescripcion(null);
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }
    
    /**
     * Prueba para crear un GrupoDeInteres sin un administrador
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestSinAdministrador() throws BusinessLogicException {
        GrupoDeInteresEntity newEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);
        newEntity.setAdministrador(null);
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }
    
    /**
     * Prueba para crear un GrupoDeInteres ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createGrupoDeInteresTestYaExistente() throws BusinessLogicException {
        List<GrupoDeInteresEntity> gruposDeInteres = grupoDeInteresLogic.getGruposDeInteres();
        GrupoDeInteresEntity newEntity = gruposDeInteres.get(0);
        grupoDeInteresLogic.createGrupoDeInteres(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de GruposDeInteres.
     */
    @Test
    public void getGruposDeInteresTest() {
        List<GrupoDeInteresEntity> list = grupoDeInteresLogic.getGruposDeInteres();
        Assert.assertEquals(data.size(), list.size());
        for (GrupoDeInteresEntity entity : list) {
            boolean found = false;
            for (GrupoDeInteresEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
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
        GrupoDeInteresEntity newEntity = grupoDeInteresLogic.getGrupoDeInteres(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(entity.getIdioma(), newEntity.getIdioma());
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

        GrupoDeInteresEntity newEntity = em.find(GrupoDeInteresEntity.class, entity.getId());

        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(entity.getIdioma(), newEntity.getIdioma());
    }
    
    /**
     * Prueba para crear un GrupoDeInteres con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestConNombreInvalido1() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity pojoEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("");

        grupoDeInteresLogic.updateGrupoDeInteres(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un GrupoDeInteres con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestConNombreInvalido2() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity pojoEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(null);

        grupoDeInteresLogic.updateGrupoDeInteres(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un GrupoDeInteres con idioma inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestConIdiomaInvalido1() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity pojoEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setIdioma("");

        grupoDeInteresLogic.updateGrupoDeInteres(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un GrupoDeInteres con idioma inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestConIdiomaInvalido2() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity pojoEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setIdioma(null);

        grupoDeInteresLogic.updateGrupoDeInteres(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un GrupoDeInteres con descripcion inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestConDescripcionInvalido1() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity pojoEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setDescripcion("");

        grupoDeInteresLogic.updateGrupoDeInteres(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un GrupoDeInteres con descripcion inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestConDescripcionInvalido2() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity pojoEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setDescripcion(null);

        grupoDeInteresLogic.updateGrupoDeInteres(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un GrupoDeInteres sin Administrador
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateGrupoDeInteresTestSinAdministrador() throws BusinessLogicException {
        GrupoDeInteresEntity entity = data.get(0);
        GrupoDeInteresEntity pojoEntity = factory.manufacturePojo(GrupoDeInteresEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setAdministrador(null);

        grupoDeInteresLogic.updateGrupoDeInteres(pojoEntity.getId(), pojoEntity);
    }
    
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

    /**
     * Prueba para eliminar un GrupoDeInteres asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteGrupoDeInteresConActividadTest() throws BusinessLogicException 
    {
        grupoDeInteresLogic.deleteGrupoDeInteres(data.get(1).getId());
    }
    
    /**
     * Prueba para eliminar un GrupoDeInteres asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteGrupoDeInteresConComentarioTest() throws BusinessLogicException 
    {
        grupoDeInteresLogic.deleteGrupoDeInteres(data.get(2).getId());
    }
    
}
