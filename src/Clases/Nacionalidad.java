/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Nacionalidad implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNacionalidad;
    private String nombreNacionalidad;
 
    
    
    public Nacionalidad(){}
    
    public Nacionalidad(String nombreNacionalidad) {
        this.nombreNacionalidad = nombreNacionalidad;
        Remiseria.persistencia.insert(this);
    }


    public int getIdNacionalidad() {
        return idNacionalidad;
    }

    public void setIdNacionalidad(int idNacionalidad) {
        this.idNacionalidad = idNacionalidad;
    }

    public String getNombreNacionalidad() {
        return nombreNacionalidad;
    }

    public void setNombreNacionalidad(String nombreNacionalidad) {
        this.nombreNacionalidad = nombreNacionalidad;
    }
    
      @Override
    public String toString() {
        return "Nacionalidad{" + "idNacionalidad=" + this.idNacionalidad + "nombreNacionalidad=" + this.nombreNacionalidad + '}';
    }
}
