/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.PersonaLogic;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.PersonaEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.PersonaPersistence;
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
 * Pruebas de lógica de Persona
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class PersonaLogicTest {

    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de dependencias con PersonaLogic.
     */
    @Inject
    private PersonaLogic personaLogic;
    
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

    private List<PersonaEntity> data = new ArrayList<>();


    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PersonaEntity.class.getPackage())
                .addPackage(PersonaLogic.class.getPackage())
                .addPackage(PersonaPersistence.class.getPackage())
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
        em.createQuery("delete from PersonaEntity").executeUpdate();
        em.createQuery("delete from AdministradorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 4; i++) {
            PersonaEntity entity = factory.manufacturePojo(PersonaEntity.class);
            em.persist(entity);
            data.add(entity);
        }

        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setPersona(data.get(2));
        em.persist(comentario);
        data.get(2).getComentarios().add(comentario);
    }

    /**
     * Prueba para crear un Persona.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createPersonaTest() throws BusinessLogicException {
        PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
        PersonaEntity result = personaLogic.createPersona(newEntity);
        
        Assert.assertNotNull(result);
        PersonaEntity entity = em.find(PersonaEntity.class, result.getId());
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
        
        
    }
    
    /**
     * Prueba para crear un Persona con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPersonaTestConNombreInvalido1() throws BusinessLogicException {
        PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
        newEntity.setNombre("");
        personaLogic.createPersona(newEntity);
    }

    /**
     * Prueba para crear un Persona con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPersonaTestConNombreInvalido2() throws BusinessLogicException {
        PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
        newEntity.setNombre(null);
        personaLogic.createPersona(newEntity);
    }
    
    /**
     * Prueba para crear un Persona con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPersonaTestConContraseniaInvalido1() throws BusinessLogicException {
        PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
        newEntity.setContrasenia("");
        personaLogic.createPersona(newEntity);
    }

    /**
     * Prueba para crear un Persona con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPersonaTestConContraseniaInvalido2() throws BusinessLogicException {
        PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
        newEntity.setContrasenia(null);
        personaLogic.createPersona(newEntity);
    }
    
    /**
     * Prueba para crear un Persona con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPersonaTestConContraseniaInvalido3() throws BusinessLogicException {
        PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
        newEntity.setContrasenia("a");
        personaLogic.createPersona(newEntity);
    }
    
    
    
    /**
     * Prueba para crear un Persona ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPersonaTestYaExistente() throws BusinessLogicException {
        List<PersonaEntity> personas = personaLogic.getPersonas();
        PersonaEntity newEntity = personas.get(0);
        personaLogic.createPersona(newEntity);
    }
    
    /**
     * Prueba para consultar la lista de Personas.
     */
    @Test
    public void getPersonasTest() {
        List<PersonaEntity> list = personaLogic.getPersonas();
        Assert.assertEquals(data.size(), list.size());
        for (PersonaEntity entity : list) {
            boolean found = false;
            for (PersonaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Persona.
     */
    @Test
    public void getPersonaTest() {
        PersonaEntity entity = data.get(0);
        PersonaEntity newEntity = personaLogic.getPersona(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
    }
    
    /**
     * Prueba para consultar un Persona.
     */
    @Test
    public void getPersonaNoExistenteTest() {
        PersonaEntity resultEntity = personaLogic.getPersona(-1L);
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar un Persona.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void updatePersonaTest() throws BusinessLogicException {
        PersonaEntity entity = data.get(0);
        PersonaEntity pojoEntity = factory.manufacturePojo(PersonaEntity.class);

        pojoEntity.setId(entity.getId());

        personaLogic.updatePersona(pojoEntity.getId(), pojoEntity);

        PersonaEntity newEntity = em.find(PersonaEntity.class, entity.getId());

        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
    }
    
    /**
     * Prueba para crear un Persona con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePersonaTestConNombreInvalido1() throws BusinessLogicException {
        PersonaEntity entity = data.get(0);
        PersonaEntity pojoEntity = factory.manufacturePojo(PersonaEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre("");

        personaLogic.updatePersona(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un Persona con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePersonaTestConNombreInvalido2() throws BusinessLogicException {
        PersonaEntity entity = data.get(0);
        PersonaEntity pojoEntity = factory.manufacturePojo(PersonaEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setNombre(null);

        personaLogic.updatePersona(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Persona con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePersonaTestConContraseniaInvalido1() throws BusinessLogicException {
        PersonaEntity entity = data.get(0);
        PersonaEntity pojoEntity = factory.manufacturePojo(PersonaEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia("");

        personaLogic.updatePersona(pojoEntity.getId(), pojoEntity);
    }

    /**
     * Prueba para crear un Persona con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePersonaTestConContraseniaInvalido2() throws BusinessLogicException {
        PersonaEntity entity = data.get(0);
        PersonaEntity pojoEntity = factory.manufacturePojo(PersonaEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia(null);

        personaLogic.updatePersona(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Persona con contrasenia inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePersonaTestConContraseniaInvalido3() throws BusinessLogicException {
        PersonaEntity entity = data.get(0);
        PersonaEntity pojoEntity = factory.manufacturePojo(PersonaEntity.class);

        pojoEntity.setId(entity.getId());
        pojoEntity.setContrasenia("a");

        personaLogic.updatePersona(pojoEntity.getId(), pojoEntity);
    }
    
    /**
     * Prueba para crear un Persona ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePersonaTestYaExistente() throws BusinessLogicException {
        List<PersonaEntity> personas = personaLogic.getPersonas();
        PersonaEntity newEntity = personas.get(0);
        personaLogic.updatePersona(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para eliminar un Persona
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deletePersonaTest() throws BusinessLogicException {
        PersonaEntity entity = data.get(0);
        personaLogic.deletePersona(entity.getId());
        PersonaEntity deleted = em.find(PersonaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Persona asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deletePersonaConComentarioTest() throws BusinessLogicException 
    {
        personaLogic.deletePersona(data.get(2).getId());
    }
    
}
