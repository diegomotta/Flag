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
public class Tarifa implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int idTarifa;
    private double codigoTarifa;
    private String referencia;
    private double precio;

    public Tarifa() {
    }

    public Tarifa(double codigoTarifa, String referencia, double precio) {
        this.codigoTarifa = codigoTarifa;
        this.referencia = referencia;
        this.precio = precio;
        Remiseria.persistencia.insert(this);
    }

    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public double getCodigoTarifa() {
        return codigoTarifa;
    }

    public void setCodigoTarifa(double codigoTarifa) {
        this.codigoTarifa = codigoTarifa;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return this.getCodigoTarifa() + " -> $" + this.getPrecio() ;
    }


}
