/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class PagoOperario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPagoOperario;
    private String tipoPago;
    private double precio;
    public PagoOperario() {
    }

    public PagoOperario(String tipoPago, double precio) {
        this.idPagoOperario = 0;
        this.tipoPago = tipoPago;
        this.precio = precio;
        Remiseria.persistencia.insert(this);
    }


    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdPagoOperario() {
        return idPagoOperario;
    }

    public void setIdPagoOperario(int idPagoOperario) {
        this.idPagoOperario = idPagoOperario;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
    
    
}
