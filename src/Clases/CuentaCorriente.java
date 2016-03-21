/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class CuentaCorriente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private int numeroCuentaCorriente;
    private double saldo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaUltimoMovimiento;
    @Transient
    private Utilidad utilidad = new Utilidad();
    
    public CuentaCorriente(){}

    public CuentaCorriente(double saldo) {
        this.saldo = saldo;
        this.fechaUltimoMovimiento = utilidad.getFechaHoraActual();
        Remiseria.persistencia.insert(this);
        
    }

    public Date getFechaUltimoMovimiento() {
        return fechaUltimoMovimiento;
    }

    public void setFechaUltimoMovimiento(Date fechaUltimoMovimiento) {
        this.fechaUltimoMovimiento = fechaUltimoMovimiento;
    }


    public int getNumeroCuentaCorriente() {
        return numeroCuentaCorriente;
    }

    public void setNumeroCuentaCorriente(int numeroCuentaCorriente) {
        this.numeroCuentaCorriente = numeroCuentaCorriente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
}
