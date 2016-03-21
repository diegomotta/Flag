/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
public class PrecioAlquiler implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int idPrecioAlquiler;
    private double precio;

    public PrecioAlquiler() {
    }

    public PrecioAlquiler(double precio) {
        this.idPrecioAlquiler = 0;
        this.precio = precio;
        Remiseria.persistencia.insert(this);
    }

    public int getIdPrecioAlquiler() {
        return idPrecioAlquiler;
    }

    public void setIdPrecioAlquiler(int idPrecioAlquiler) {
        this.idPrecioAlquiler = idPrecioAlquiler;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
}
