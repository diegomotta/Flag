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
public class TipoTelefono implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idTipoTelefono;
    private String nombreTipoTelefono;
    
    public TipoTelefono(){}

    public TipoTelefono(String nombreTipoTelefono) {
        this.nombreTipoTelefono = nombreTipoTelefono;
        Remiseria.persistencia.insert(this);
    }

    public int getIdTipoTelefono() {
        return idTipoTelefono;
    }

    public void setIdTipoTelefono(int idTipoTelefono) {
        this.idTipoTelefono = idTipoTelefono;
    }

    public String getNombreTipoTelefono() {
        return nombreTipoTelefono;
    }

    public void setNombreTipoTelefono(String nombreTipoTelefono) {
        this.nombreTipoTelefono = nombreTipoTelefono;
    }


       @Override
    public String toString() {
        return "TipoTelefono{" + "idTipoTelefono=" + this.idTipoTelefono + "nombreTipoTelefono=" + this.nombreTipoTelefono + '}';
    }
}
