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
public class TipoDni implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idTipoDni;
    private String descripcionTipoDNI;
    private String siglas;

    public TipoDni(){}
    
    public TipoDni(String descripcionTipoDNI, String siglas) {
        this.descripcionTipoDNI = descripcionTipoDNI;
        this.siglas = siglas;
        Remiseria.persistencia.insert(this);
    }

    public int getIdTipoDni() {
        return idTipoDni;
    }

    public void setIdTipoDni(int idTipoDni) {
        this.idTipoDni = idTipoDni;
    }

    public String getDescripcionTipoDNI() {
        return descripcionTipoDNI;
    }

    public void setDescripcionTipoDNI(String descripcionTipoDNI) {
        this.descripcionTipoDNI = descripcionTipoDNI;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }
    
    
 @Override
    public String toString() {
        return "TipoDni{" + "idTipoDni=" + this.idTipoDni + "descripcionTipoDNI=" + this.descripcionTipoDNI + "siglas="+ this.siglas +'}';
    }

}
