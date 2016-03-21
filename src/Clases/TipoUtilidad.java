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
public class TipoUtilidad implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idTipoUtilidad;
    private String nombreTipoUtilidad;

    public TipoUtilidad(){}
    
    public TipoUtilidad(String nombreTipoUtilidad) {
            this.nombreTipoUtilidad = nombreTipoUtilidad;
            Remiseria.persistencia.insert(this);
    }

    public int getIdTipoUtilidad() {
        return idTipoUtilidad;
    }

    public void setIdTipoUtilidad(int idTipoUtilidad) {
        this.idTipoUtilidad = idTipoUtilidad;
    }

 
    public String getNombreTipoUtilidad() {
        return nombreTipoUtilidad;
    }

    public void setNombreTipoUtilidad(String nombreTipoUtilidad) {
        this.nombreTipoUtilidad = nombreTipoUtilidad;
    }

@Override
    public String toString() {
        return  this.getNombreTipoUtilidad();
    }
    
 
    
}
