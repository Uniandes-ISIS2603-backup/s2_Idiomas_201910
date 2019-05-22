/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.test.logic;


import co.edu.uniandes.csw.idiomas.ejb.ActividadLogic;
import co.edu.uniandes.csw.idiomas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.idiomas.entities.ActividadEntity;
import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.entities.CoordinadorEntity;

import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.ActividadPersistence;
import java.util.ArrayList;
import java.util.List;
import co.edu.uniandes.csw.idiomas.persistence.ComentarioPersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author se.gamboa
 */
@RunWith(Arquillian.class)
public class ComentarioTest {

    
    /**
     * Atributo que representa los datos aleatorios a ser creados.
     */
    
    @Inject
    private ActividadLogic actividadLogic;
    @Inject
    private ComentarioLogic commentLogic;
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ActividadEntity> data = new ArrayList<>();
    private List<CoordinadorEntity> coordinadorData = new ArrayList<>();
    private List<ComentarioEntity> comentarioData = new ArrayList<>();
    

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioLogic.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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

            private void clearData() {
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from ActividadEntity").executeUpdate();
        em.createQuery("delete from CoordinadorEntity").executeUpdate();
    }
        
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() throws BusinessLogicException, ParseException {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++)
        {
            CoordinadorEntity coordinador = factory.manufacturePojo(CoordinadorEntity.class);
            em.persist(coordinador);
            coordinadorData.add(coordinador);
        }
        for (int i = 0; i < 3; i++) 
        {
            ActividadEntity entity = factory.manufacturePojo(ActividadEntity.class);
            em.persist(entity);
            entity.getCoordinadores().add(coordinadorData.get(0));
            // TODO : GC Poner calificación
            data.add(entity);
        }
        for(int i =0; i<2;i++){
        comentarioData.add(factory.manufacturePojo(ComentarioEntity.class));
        comentarioData.get(0).setActividad(data.get(1));
        em.persist(comentarioData.get(i));
        data.get(1).getComentarios().add(comentarioData.get(0));
        }
    }
    
    

    @Test
    public void createBlogComment() throws BusinessLogicException, ParseException {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        Date date1 = sdf.parse("2019-01-01");
        if (newEntity.getFecha().before(date1)) {
            newEntity.setFecha(date1);
        }
        ComentarioEntity result = commentLogic.createComment(newEntity);
        Assert.assertNotNull(result);

        ComentarioEntity entity = em.find(ComentarioEntity.class, result.getId());

        Assert.assertEquals(newEntity.getTexto(), entity.getTexto());
        Assert.assertEquals(newEntity.getFecha(), entity.getFecha());
    }

    @Test(expected = BusinessLogicException.class)
    public void crearComentarioSinTexto() throws BusinessLogicException, ParseException {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setTexto("");
        commentLogic.createComment(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void crearComentarioSinFechaPermitida() throws BusinessLogicException, ParseException {
        Date date1 = sdf.parse("2018-12-01");
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setFecha(date1);
        commentLogic.createComment(newEntity);
    }
    
     /**
     * Prueba para consultar un Comentario.
     */
    @Test
    public void getComentarioTest() {
        ComentarioEntity entity = comentarioData.get(0);
        System.out.println(comentarioData.get(0).getTexto());
        ComentarioEntity resultEntity = em.find(ComentarioEntity.class, entity.getId());
        //ComentarioEntity resultEntity = commentLogic.getComentario(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getTitulo(), entity.getTitulo());
        Assert.assertEquals(resultEntity.getTexto(), entity.getTexto());
        Assert.assertEquals(resultEntity.getFecha(), entity.getFecha());
    }
    
    /**
     * Prueba para consultar un Comentario.
     */
    @Test
    public void getComentarioNoExistenteTest() {
        ComentarioEntity resultEntity = em.find(ComentarioEntity.class, (long)1313131111);
        Assert.assertNull(resultEntity);
    }

    /**
     * Prueba para actualizar un Comentario.
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException

    @Test
    public void updateComentarioTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity entity = comentarioData.get(0);
        ComentarioEntity pojoEntity = factory.manufacturePojo(ComentarioEntity.class);

        pojoEntity.setId(entity.getId());

        commentLogic.updateComentario(pojoEntity.getId(), pojoEntity);

        ComentarioEntity resp = em.find(ComentarioEntity.class, entity.getId());

        Assert.assertEquals(resp.getId(), entity.getId());
        Assert.assertEquals(resp.getTexto(), entity.getTexto());
        Assert.assertEquals(resp.getTitulo(), entity.getTitulo());
        Assert.assertEquals(resp.getFecha(), entity.getFecha());
    }
      */
    
    /**
     * Prueba para crear un Comentario con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateComentarioTestConNombreInvalido1() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();        
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setTexto("");
        commentLogic.updateComentario(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para crear un Comentario con nombre inválido
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updateComentarioTestConNombreInvalido2() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setTexto(null);
        commentLogic.updateComentario(newEntity.getId(), newEntity);
    }

    /**
     * Prueba para eliminar un Comentario
     *
     * @throws co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteComentarioTest() throws BusinessLogicException {     
        ComentarioEntity entity = comentarioData.get(0);
        commentLogic.deleteComment(entity.getId());
        ComentarioEntity deleted = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}