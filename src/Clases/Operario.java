/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Operario extends Persona {

    private String id;
    private String contrasena;
    @ManyToOne
    private Telefono unTelefono;
    @OneToOne
    private Rol unRol;
//    @OneToOne
//    private PagoOperario unPagoOperario;
    public Operario(){}

    public Operario(String id, String contrasena,int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Date fechaNacimiento,Telefono unTelefono,Rol unRol) {
        super(dni, unTipoDni, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento);
        this.id = id;
        this.contrasena = contrasena;
        this.unTelefono = unTelefono;
        this.unRol = unRol;
        Remiseria.persistencia.insert(this);
    }


    public Rol getUnRol() {
        return unRol;
    }

    public void setUnRol(Rol unRol) {
        this.unRol = unRol;
    }

    public Telefono getUnTelefono() {
        return unTelefono;
    }

    public void setUnTelefono(Telefono unTelefono) {
        this.unTelefono = unTelefono;
    }

    
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    @Override
    public String toString() {
        return this.getNombre() + " "+ this.getApellido();
    }
}
