/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Cargo extends Persona {
private String tipoPago;
private String tipoCargo;
private Double sueldo;
@ManyToOne
private Telefono unTelefono;
@Temporal(javax.persistence.TemporalType.TIMESTAMP)
private Date fechaPago;
    public Cargo(){}

    public Cargo(String tipoPago, String tipoCargo, Double sueldo, int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Date fechaNacimiento,Telefono unTelefono,Date fechaPago) {
        super(dni, unTipoDni, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento);
        this.tipoPago = tipoPago;
        this.tipoCargo = tipoCargo;
        this.sueldo = sueldo;
        this.unTelefono = unTelefono;
        this.fechaPago = fechaPago;
        Remiseria.persistencia.insert(this);
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Telefono getUnTelefono() {
        return unTelefono;
    }

    public void setUnTelefono(Telefono unTelefono) {
        this.unTelefono = unTelefono;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(String tipoCargo) {
        this.tipoCargo = tipoCargo;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return this.getNombre() + " " + this.getApellido();
    }
    
    
    
}
