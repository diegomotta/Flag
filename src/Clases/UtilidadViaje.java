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
public class UtilidadViaje implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idUtilidadViaje;
    private String nombreUtilidadViaje;

    public UtilidadViaje(){}
    
    public UtilidadViaje(String nombreUtilidadViaje) {
            this.nombreUtilidadViaje = nombreUtilidadViaje;
            Remiseria.persistencia.insert(this);
    }

    public int getIdUtilidadViaje() {
        return idUtilidadViaje;
    }

    public void setIdUtilidadViaje(int idUtilidadViaje) {
        this.idUtilidadViaje = idUtilidadViaje;
    }

    public String getNombreUtilidadViaje() {
        return nombreUtilidadViaje;
    }

    public void setNombreUtilidadViaje(String nombreUtilidadViaje) {
        this.nombreUtilidadViaje = nombreUtilidadViaje;
    }



@Override
    public String toString() {
        return "UtilidadViaje{" + "idUtilidadViaje=" + this.getIdUtilidadViaje() + "nombreTipoUtilidadViaje =" + this.getNombreUtilidadViaje() +  '}';
    }
    
 
    
}
