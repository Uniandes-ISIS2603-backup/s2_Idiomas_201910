package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.UsuarioEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * UsuarioDTO Objeto de transferencia de datos de Usuarios. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string
 *      "contraseña" : string
 *   }
 * </pre> Por ejemplo una usuario se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1,
 *      "nombre": juan
 *      "contraseña" : 1234
 *   }
 *
 * </pre>
 *
 * @author j.barbosa
 */
public class UsuarioDTO extends PersonaDTO implements Serializable
{
    //
    private Long id;
    private String nombre;
    Long contrasenia;


    
    
    /**
     * Constructor por defecto
     */
    public UsuarioDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param usuarioEntity: Es la entidad que se va a convertir a DTO
     */
    public UsuarioDTO(UsuarioEntity usuarioEntity) {
        if (usuarioEntity != null) {
            this.id = usuarioEntity.getId();
            this.nombre = usuarioEntity.getNombre();
            this.contrasenia = usuarioEntity.getContrasenia();
        }
    }

    /**
     * Devuelve el ID de la usuario.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la usuario.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la usuario.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la usuario.
     *
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
     /**
     * Devuelve el contrasenia de la usuario.
     *
     * @return the contrasenia
     */
    public Long getContrasenia() {
        return contrasenia;
    }
    
    /**
     * Modifica la conrasenia del usuario.
     *
     * @param contraseina the nombre to set
     */
    public void setContrasenia(Long contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(this.id);
        usuarioEntity.setNombre(this.nombre);
        usuarioEntity.setContrasenia(this.contrasenia);
        return usuarioEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}