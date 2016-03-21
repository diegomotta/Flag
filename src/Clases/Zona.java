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
 * @author Owner
 */
@Audited
@Entity
public class Zona implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int numeroZona;
    private String nombreZona;
    private String descripcion;


    public Zona(){}
    
    public Zona(String nombreZona, String descripcion) {
        //this.numeroZona = numeroZona;
        this.nombreZona = nombreZona;
        this.descripcion = descripcion;        
        Remiseria.persistencia.insert(this);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    public int getNumeroZona() {
        return numeroZona;
    }

    public void setNumeroZona(int numeroZona) {
        this.numeroZona = numeroZona;
    }

    @Override
    public String toString() {
        return  nombreZona.toString();
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }
    
    
}
