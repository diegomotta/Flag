/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import org.hibernate.envers.Audited;
/**
 *
 * @author Owner
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idPersona;
    private int dni;
    @ManyToOne
    private TipoDni unTipoDni;
    private String estado;
    @ManyToOne    
    private Sexo unSexo;
    @ManyToOne
    private EstadoCivil unEstadoCivil;
    @ManyToOne
    private Nacionalidad unaNacionalidad;
    @ManyToOne
    private Domicilio unDomicilio;
    private String nombre;
    private String apellido;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;


    public Persona() {
    }

    public Persona(int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Date fechaNacimiento) {
        this.dni = dni;
        this.unTipoDni = unTipoDni;
        this.estado = "Activo";
        this.unSexo = unSexo;
        this.unEstadoCivil = unEstadoCivil;
        this.unaNacionalidad = unaNacionalidad;
        this.unDomicilio = unDomicilio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
       // Remiseria.persistencia.insert(this);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    
    
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Domicilio getUnDomicilio() {
        return unDomicilio;
    }

    public void setUnDomicilio(Domicilio unDomicilio) {
        this.unDomicilio = unDomicilio;
    }

    public EstadoCivil getUnEstadoCivil() {
        return unEstadoCivil;
    }

    public void setUnEstadoCivil(EstadoCivil unEstadoCivil) {
        this.unEstadoCivil = unEstadoCivil;
    }

    public Sexo getUnSexo() {
        return unSexo;
    }

    public void setUnSexo(Sexo unSexo) {
        this.unSexo = unSexo;
    }

    public TipoDni getUnTipoDni() {
        return unTipoDni;
    }

    public void setUnTipoDni(TipoDni unTipoDni) {
        this.unTipoDni = unTipoDni;
    }

    public Nacionalidad getUnaNacionalidad() {
        return unaNacionalidad;
    }

    public void setUnaNacionalidad(Nacionalidad unaNacionalidad) {
        this.unaNacionalidad = unaNacionalidad;
    }


    
    @Override
    public String toString() {
                   
        return "Persona{" + "dni=" + this.dni + "unTipoDeDni=" + this.unTipoDni.getSiglas() + "unSexo="+ this.unSexo.getNombreSexo()+"unEstadoCivil=" +this.unEstadoCivil.getNombreEstadoCivil()+"unaNacionalidad="+ this.unaNacionalidad.getNombreNacionalidad()+"nombre="+this.nombre+"apellido="+this.apellido+"fechaNacimiento="+this.fechaNacimiento+'}';
    }
    
   
}
