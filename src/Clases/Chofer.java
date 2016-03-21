/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.Date;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.hibernate.envers.Audited;

/**
 *
 * @author Owner
 */
@Audited
@Entity
public class Chofer extends Persona{
    @ManyToOne
    private Movil unMovil;
    @ManyToOne
    private Viaje unViaje;
    @ManyToOne
    private Telefono unTelefono;
    
    public Chofer() {
    }

    public Chofer( int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Telefono unTelefono, Date fechaNacimiento,Movil unMovil) {
        super(dni, unTipoDni, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento);
        this.unMovil = unMovil;
        this.unTelefono = unTelefono;
        Remiseria.persistencia.insert(this);
        
    }

    public Telefono getUnTelefono() {
        return unTelefono;
    }

    public void setUnTelefono(Telefono unTelefono) {
        this.unTelefono = unTelefono;
    }

   
    
    public Movil getUnMovil() {
        return unMovil;
    }

    public void setUnMovil(Movil unMovil) {
        this.unMovil = unMovil;
    }
    
    public int obtenerNumeroMovil(){
        return this.unMovil.getNumeroMovil();
    }

    public Viaje getUnViaje() {
        return unViaje;
    }

    public void setUnViaje(Viaje unViaje) {
        this.unViaje = unViaje;
    }
    
    

    @Override
    public String toString() {
        return this.getNombre() +" "+this.getApellido();
    }    
}
