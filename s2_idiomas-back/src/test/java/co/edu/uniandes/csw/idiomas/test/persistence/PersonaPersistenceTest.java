/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.persistence;

import co.edu.uniandes.csw.idiomas.entities.PersonaEntity;
import co.edu.uniandes.csw.idiomas.entities.PersonaEntity;
import co.edu.uniandes.csw.idiomas.persistence.PersonaPersistence;
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
 *
 * @author j.barbosaj
 */
@RunWith(Arquillian.class)
public class PersonaPersistenceTest 
{
     
     /**
     * Inyección de la dependencia a la clase PersonaPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private PersonaPersistence personaPersistence;
    
     /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext 
    private EntityManager em;
    
     /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;
    
     /**
     * Lista que tiene los datos de prueba.
     */
    private List<PersonaEntity> data = new ArrayList<PersonaEntity>();
    
    
    
     /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Persona, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PersonaEntity.class.getPackage())
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
        em.createQuery("delete from PersonaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PersonaEntity entity = factory.manufacturePojo(PersonaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueva el crear
     */
    @Test
    public void createPersonaTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);
        PersonaEntity result = personaPersistence.create(newEntity);
        
         Assert.assertNotNull(result);
         
         PersonaEntity entity = em.find(PersonaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    /**
     * Prueba para consultar la lista de Personaes.
     */
    @Test
    public void getPersonaesTest() {
        List<PersonaEntity> list = personaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (PersonaEntity ent : list) {
            boolean found = false;
            for (PersonaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
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
        PersonaEntity newEntity = personaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getContrasenia(), newEntity.getContrasenia());
       }

    /**
     * Prueba para actualizar un Persona.
     */
    @Test
    public void updatePersonaTest() {
        PersonaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PersonaEntity newEntity = factory.manufacturePojo(PersonaEntity.class);

        newEntity.setId(entity.getId());

        personaPersistence.update(newEntity);

        PersonaEntity resp = em.find(PersonaEntity.class, entity.getId());
        
        Assert.assertEquals(resp.getId(), newEntity.getId());
        Assert.assertEquals(resp.getNombre(), newEntity.getNombre());
        Assert.assertEquals(resp.getNombre(), newEntity.getNombre());
        Assert.assertEquals(resp.getContrasenia(), newEntity.getContrasenia());
    }

    /**
     * Prueba para eliminar un Persona.
     */
    @Test
    public void deletePersonaTest() {
        PersonaEntity entity = data.get(0);
        personaPersistence.delete(entity.getId());
        PersonaEntity deleted = em.find(PersonaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para consultasr un Persona por nombre.
     */
    @Test
    public void findPersonaByNameTest() {
        PersonaEntity entity = data.get(0);
        PersonaEntity newEntity = personaPersistence.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = personaPersistence.findByName(null);
        Assert.assertNull(newEntity);
        newEntity = personaPersistence.findByName("");
        Assert.assertNull(newEntity);
    }
}
