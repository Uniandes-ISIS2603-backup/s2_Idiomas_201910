package co.edu.uniandes.csw.idiomas.dtos;

import co.edu.uniandes.csw.idiomas.entities.PersonaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * PersonaDTO Objeto de transferencia de datos de Personaes. Los DTO
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
 * </pre> Por ejemplo una persona se representa asi:<br>
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
public class PersonaDTO implements Serializable 
{ // -----------------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------------
    
    /**
     * Atributo que representa el Id de la persona
     */
    private Long id;
    
    /**
     * Atributo que representa el nombre de la persona.
     */
    private String nombre;
    
    /**
     * Atributo que representa la contrasenia de la persona.
     */
    private String contrasenia;
    
    
    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------
    
    /**
     * Constructor vacio de una persona
     */
    public PersonaDTO ()
    {
        
    }
    
    /**
     * Convertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param pPersonaEntity: Es la entidad que se va a convertir a DTO
     */
    public PersonaDTO(PersonaEntity pPersonaEntity) 
    {
        if (pPersonaEntity != null) 
        {
            this.id = pPersonaEntity.getId();
            this.nombre = pPersonaEntity.getNombre();
            this.contrasenia = pPersonaEntity.getContrasenia();
        }
    }
    
    // ----------------------------------------------------------------------
    // Metodos
    // ----------------------------------------------------------------------

    /**
     * Convierte un objeto PersonaDTO a PersonaEntity.
     *
     * @return Nueva objeto PersonaEntity.
     *
     */
    public PersonaEntity toEntity() {
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setId(this.getId());
        personaEntity.setNombre(this.getNombre());
        personaEntity.setContrasenia(this.getContrasenia());
        return personaEntity;
    }

    /**
     * Devuelve el ID de la persona.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la persona.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la persona.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la persona.
     *
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }
}