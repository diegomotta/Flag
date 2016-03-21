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
public class Motor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMotor;
    private String nombreMotor;

    public Motor(){}
    
    public Motor(String nombreMotor) {
        this.nombreMotor = nombreMotor;
        Remiseria.persistencia.insert(this);
    }

    public int getIdMotor() {
        return idMotor;
    }

    public void setIdMotor(int idMotor) {
        this.idMotor = idMotor;
    }

    public String getNombreMotor() {
        return nombreMotor;
    }

    public void setNombreMotor(String nombreMotor) {
        this.nombreMotor = nombreMotor;
    }
    
     @Override
    public String toString() {
        return "Motor{" + "idMotor=" + this.idMotor + "nombreMotor=" + this.nombreMotor + '}';
    }
}
