/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;

import co.edu.uniandes.csw.idiomas.ejb.ChatLogic;
import co.edu.uniandes.csw.idiomas.ejb.CoordinadorLogic;
import co.edu.uniandes.csw.idiomas.entities.ChatEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.ChatPersistence;
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
 * Pruebas de lógica de Chat
 * @author g.cubillosb
 */
@RunWith(Arquillian.class)
public class ChatLogicTest {

    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de dependencias con ChatLogic.
     */
    @Inject
    private ChatLogic chatLogic;
    
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

    private List<ChatEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ChatEntity.class.getPackage())
                .addPackage(ChatLogic.class.getPackage())
                .addPackage(ChatPersistence.class.getPackage())
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
        em.createQuery("delete from ChatEntity").executeUpdate();
        em.createQuery("delete from CoordinadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) 
        {
            ChatEntity entity = factory.manufacturePojo(ChatEntity.class);
            em.persist(entity);
            data.add(entity);
        }
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setActividad(data.get(1));
        em.persist(comentario);
        data.get(1).getComentarios().add(comentario);
    }

    /**
     * Prueba para crear un Chat.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void createChatTest() throws BusinessLogicException {
        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
        CoordinadorEntity newCorEntity = factory.manufacturePojo(CoordinadorEntity.class);
        
        // TODO: GC Conectar con coordinador Logic
//        newCorEntity = corLogic.createCoordinador(newCorEntity);
//        newEntity.getCoordinadores().add(newCorEntity);
        ChatEntity result = chatLogic.createChat(newEntity);
        Assert.assertNotNull(result);
        ChatEntity entity = em.find(ChatEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        
    }
    
    /**
     * Prueba para crear un Chat con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createChatTestConNombreInvalido1() throws BusinessLogicException {
        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
        newEntity.setNombre("");
        chatLogic.createChat(newEntity);
    }

    /**
     * Prueba para crear un Chat con nombre inválido
     *
     */
    @Test(expected = BusinessLogicException.class)
    public void createChatTestConNombreInvalido2() throws BusinessLogicException {
        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
        newEntity.setNombre(null);
        chatLogic.createChat(newEntity);
    }
    
    /**
     * Prueba para crear un Chat sin un coordinador
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    // TODO: GC Conectar con coordinador
//    @Test(expected = BusinessLogicException.class)
//    public void createChatTestSinCoordinador() throws BusinessLogicException {
//        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
//        newEntity.setCoordinadores(null);
//        chatLogic.createChat(newEntity);
//    }
    
    /**
     * Prueba para crear un Chat ya existente.
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createChatTestYaExistente() throws BusinessLogicException {
        List<ChatEntity> chats = chatLogic.getChats();
        ChatEntity newEntity = chats.get(0);
        chatLogic.createChat(newEntity);
    }
    
    /**
     * Prueba para crear un Chat sin un medio válido.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createChatTestConMedioInvalido1() throws BusinessLogicException {
        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
        newEntity.setMedio("");
        chatLogic.createChat(newEntity);
    }
    
    /**
     * Prueba para crear un Chat sin un medio válido.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createChatTestConMedioInvalido2() throws BusinessLogicException {
        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
        newEntity.setMedio(null);
        chatLogic.createChat(newEntity);
    }

    /**
     * Prueba para consultar un Chat.
     */
    @Test
    public void getChatTest() {
        ChatEntity entity = data.get(0);
        ChatEntity resultEntity = chatLogic.getChat(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(resultEntity.getFecha(), entity.getFecha());
    }
    
    /**
     * Prueba para consultar un Chat.
     */
    @Test
    public void getChatNoExistenteTest() {
        ChatEntity resultEntity = chatLogic.getChat(-1L);
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar un Chat.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void updateChatTest() throws BusinessLogicException {
        ChatEntity entity = data.get(0);
        ChatEntity pojoEntity = factory.manufacturePojo(ChatEntity.class);

        pojoEntity.setId(entity.getId());

        chatLogic.updateChat(pojoEntity.getId(), pojoEntity);

        ChatEntity resp = em.find(ChatEntity.class, entity.getId());

        Assert.assertEquals(resp.getId(), entity.getId());
//        Assert.assertEquals(resp.getNombre(), entity.getNombre());
//        Assert.assertEquals(resp.getDescripcion(), entity.getDescripcion());
//        Assert.assertEquals(resp.getFecha(), entity.getFecha());
    }
    
    /**
     * Prueba para crear un Chat con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateChatTestConNombreInvalido1() throws BusinessLogicException {
        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
        newEntity.setNombre("");
        chatLogic.updateChat(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para crear un Chat con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateChatTestConNombreInvalido2() throws BusinessLogicException {
        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
        newEntity.setNombre(null);
        chatLogic.updateChat(newEntity.getId(), newEntity);
    }
    
    /**
     * Prueba para crear un Chat sin un coordinador
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    // TODO: GC Conectar con coordinador
//    @Test(expected = BusinessLogicException.class)
//    public void createChatTestSinCoordinador() throws BusinessLogicException {
//        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
//        newEntity.setCoordinadores(null);
//        chatLogic.createChat(newEntity);
//    }
    
    /**
     * Prueba para crear un Chat ya existente.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateChatTestYaExistente() throws BusinessLogicException {
        List<ChatEntity> chats = chatLogic.getChats();
        ChatEntity newEntity = chats.get(0);
        chatLogic.updateChat(newEntity.getId(), newEntity);
    }
    
    /**
     * Prueba para crear un Chat con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateChatTestConMedioInvalido1() throws BusinessLogicException {
        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
        newEntity.setMedio("");
        chatLogic.updateChat(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para crear un Chat con nombre inválido
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateChatTestConMedioInvalido2() throws BusinessLogicException {
        ChatEntity newEntity = factory.manufacturePojo(ChatEntity.class);
        newEntity.setMedio(null);
        chatLogic.updateChat(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para eliminar un Chat
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteChatTest() throws BusinessLogicException {
        ChatEntity entity = data.get(0);
        chatLogic.deleteChat(entity.getId());
        ChatEntity deleted = em.find(ChatEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

//    /**
//     * Prueba para eliminar un Chat asociado a un usuario
//     *
//     * 
//     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void deleteChatConUsuarioTest() throws BusinessLogicException {
//        chatLogic.deleteChat(data.get(2).getId());
//    }

    /**
     * Prueba para eliminar un chat asociado a un comentario
     *
     * 
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteActividadConComentarioTest() throws BusinessLogicException 
    {
        chatLogic.deleteChat(data.get(1).getId());
    }
    
}
