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
public class MovilPorZona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMovilPorZona;
    @ManyToOne
    private Movil unMovil;
    @ManyToOne
    private Zona unaZona;
    private String horaInicio;
    private String fechaInicio;
    private String horaFin;
    private String fechaFin;    


    public MovilPorZona(){}

    public MovilPorZona(Movil unMovil, Zona unaZona, String horaInicio, String fechaInicio, String horaFin, String fechaFin) {
        this.unMovil = unMovil;
        this.unaZona = unaZona;
        this.horaInicio = horaInicio;
        this.fechaInicio = fechaInicio;
        this.horaFin = horaFin;
        this.fechaFin = fechaFin;
        Remiseria.persistencia.insert(this);
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getIdMovilPorZona() {
        return idMovilPorZona;
    }

    public void setIdMovilPorZona(int idMovilPorZona) {
        this.idMovilPorZona = idMovilPorZona;
    }


    public Movil getUnMovil() {
        return unMovil;
    }

    public void setUnMovil(Movil unMovil) {
        this.unMovil = unMovil;
    }

    public Zona getUnaZona() {
        return unaZona;
    }

    public void setUnaZona(Zona unaZona) {
        this.unaZona = unaZona;
    }
    
    
}
