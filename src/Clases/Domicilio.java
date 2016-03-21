/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Domicilio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDomicilio;
    private String calle;
    private String nroCasa;
    private String piso;
    private String dpto;
    @ManyToOne
    private Barrio unBarrio;
    @ManyToOne
    private Ciudad unaCiudad;
    @ManyToOne
    private Provincia unaProvincia;
    @ManyToOne
    private Pais unPais;
    @ManyToOne
    private DireccionViaje unaDireccionViaje;
    
    public Domicilio (){}

    public Domicilio(Pais unPais,Provincia unaProvincia,Ciudad unaCiudad,Barrio unBarrio,String calle,String nroCasa,String piso,String dpto) {
        this.calle = calle;
        this.nroCasa = nroCasa;
        this.piso = piso;
        this.dpto = dpto;
        this.unBarrio = unBarrio;
        this.unaCiudad = unaCiudad;
        this.unaProvincia = unaProvincia;
        this.unPais = unPais;
        Remiseria.persistencia.insert(this);
    }

    public Domicilio(Pais unPais,Provincia unaProvincia,Ciudad unaCiudad,Barrio unBarrio,DireccionViaje unaDireccionViaje)
    {
        this.unBarrio = unBarrio;
        this.unaCiudad = unaCiudad;
        this.unaProvincia = unaProvincia;
        this.unPais = unPais;
        this.unaDireccionViaje = unaDireccionViaje;
        Remiseria.persistencia.insert(this);
    
    }
    


    public DireccionViaje getUnaDireccionViaje() {
        return unaDireccionViaje;
    }

    public void setUnaDireccionViaje(DireccionViaje unaDireccionViaje) {
        this.unaDireccionViaje = unaDireccionViaje;
    }
    
    
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    
    public int getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(int idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public String getNroCasa() {
        return nroCasa;
    }

    public void setNroCasa(String nroCasa) {
        this.nroCasa = nroCasa;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public Barrio getUnBarrio() {
        return unBarrio;
    }

    public void setUnBarrio(Barrio unBarrio) {
        this.unBarrio = unBarrio;
    }

    public Pais getUnPais() {
        return unPais;
    }

    public void setUnPais(Pais unPais) {
        this.unPais = unPais;
    }

    public Ciudad getUnaCiudad() {
        return unaCiudad;
    }

    public void setUnaCiudad(Ciudad unaCiudad) {
        this.unaCiudad = unaCiudad;
    }

    public Provincia getUnaProvincia() {
        return unaProvincia;
    }

    public void setUnaProvincia(Provincia unaProvincia) {
        this.unaProvincia = unaProvincia;
    }
    
    
          @Override
    public String toString() 
    {
        return "B. "+this.unBarrio.getNombreBarrio()+" - "+" Calle "+ this.getCalle()+" N° "+this.getNroCasa() ;
    }
    
    
}
