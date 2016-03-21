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
public class PagoRadio implements Serializable {
 
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int idPagoRadio;
private double valor;

    public PagoRadio() {
    }
    public PagoRadio(double valor) {
        this.idPagoRadio = 0;
        this.valor = valor;
        Remiseria.persistencia.insert(this);
    }

    public int getIdPagoRadio() {
        return idPagoRadio;
    }

    public void setIdPagoRadio(int idPagoRadio) {
        this.idPagoRadio = idPagoRadio;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    
}


