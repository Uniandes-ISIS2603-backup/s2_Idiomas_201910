/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.idiomas.ejb;

import co.edu.uniandes.csw.idiomas.entities.ComentarioEntity;
import co.edu.uniandes.csw.idiomas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.idiomas.persistence.ComentarioPersistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author se.gamboa
 */
@Stateless
public class ComentarioLogic {
   @Inject
    private ComentarioPersistence persistence;
   
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

   private static final Logger LOGGER = Logger.getLogger(ComentarioLogic.class.getName());
    /**
     * Creac un comentario de tipo Calificacion
     *
     * @param entidad
     * @return entidad creada.
     * @throws BusinessLogicException
     */
    public ComentarioEntity createComment(ComentarioEntity entidad) throws BusinessLogicException, ParseException {
        Date date1 = sdf.parse("2018-12-31");
        if (entidad.getTexto() == null) {
            throw new BusinessLogicException("El texto del comentario no puede ser null");
        }
        if (entidad.getTexto().length() == 0) {
            throw new BusinessLogicException("El texto debe contener al menos un caracter");
        }
        if (entidad.getFecha().before(date1)) {
            throw new BusinessLogicException("la fecha no es aceptada");
        }
        entidad = persistence.create(entidad);
        return entidad;
    } 
    
        /**
     *
     * Obtener todas las Comentarios existentes en la base de datos.
     *
     * @return una lista de Comentarios.
     */
    public List<ComentarioEntity> getComentarios() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las Comentarios");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ComentarioEntity> Comentarios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las Comentarios");
        return Comentarios;
    }
  
    
    public void deleteComment(Long commentId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la comentario con id = {0}", commentId); 
        if(persistence.find(commentId)==null){
            throw new BusinessLogicException("El comentario no existe");
        }
        persistence.delete(commentId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la comentario con id = {0}", commentId);
    }
    
    
    /**
     *
     * Obtener una Comentario por medio de su id.
     *
     * @param ComentariosId: id de la Comentario para ser buscada.
     * @return la Comentario solicitada por medio de su id.
     */
    public ComentarioEntity getComentario(Long ComentariosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la Comentario con id = {0}", ComentariosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ComentarioEntity ComentarioEntity = persistence.find(ComentariosId);
        if (ComentarioEntity == null) {
            LOGGER.log(Level.SEVERE, "La Comentario con el id = {0} no existe", ComentariosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la Comentario con id = {0}", ComentariosId);
        return ComentarioEntity;
    }
    
        public List<ComentarioEntity> getComentarioDate(Date fecha1, Date fecha2) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la Comentario con id = {0}");
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        List<ComentarioEntity> ComentarioEntity = persistence.findDate(fecha1, fecha2);
        if (ComentarioEntity == null) {
            LOGGER.log(Level.SEVERE, "La Comentario con el id = {0} no existe");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la Comentario con id = {0}");
        return ComentarioEntity;
    }
    
        /**
     *
     * Actualizar una Comentario.
     *
     * @param pComentariosId: id de la Comentario para buscarla en la base de
     * datos.
     * @param ComentarioEntity: Comentario con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la Comentario con los cambios actualizados en la base de datos.
     */
    public ComentarioEntity updateComentario(Long  pComentariosId, ComentarioEntity ComentarioEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la Comentario con id = {0}",  pComentariosId);
        if (!validateText(ComentarioEntity.getTexto()))
        {
            throw new BusinessLogicException("El texto es inválido.");
        }

        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ComentarioEntity newEntity = persistence.update(ComentarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la Comentario con id = {0}", ComentarioEntity.getId());
        return newEntity;
    }
    
        /**
     * Verifica que el nombre no sea invalido.
     *
     * @param pNombre a verificar
     * @return true si el nombre es valido.
     */
    private boolean validateText(String pTexto) 
    {
        return !(pTexto == null || pTexto.isEmpty() || pTexto.length() >=300);
    }
}