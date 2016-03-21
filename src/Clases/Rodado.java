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
public class Rodado implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idRodado;
    private String nombreRodado;

    public Rodado(){}
    
    public Rodado(String nombreRodado) {
        this.nombreRodado = nombreRodado;
        Remiseria.persistencia.insert(this);
    }

    public int getIdRodado() {
        return idRodado;
    }

    public void setIdRodado(int idRodado) {
        this.idRodado = idRodado;
    }

    public String getNombreRodado() {
        return nombreRodado;
    }

    public void setNombreRodado(String nombreRodado) {
        this.nombreRodado = nombreRodado;
    }
    
     @Override
    public String toString() {
        return  this.nombreRodado ;
    }
}
