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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class MarcarTarjeta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private int idMarcarTarjeta;
    @OneToOne
    private Operario unOperario;
    @OneToOne
    private Maestro unMaestro;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaFin;
    private long horas;
    private long minutos;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date total;
    public MarcarTarjeta() {
    }

    public MarcarTarjeta(Operario unOperario, Date fechaInicio, Date fechaFin) {
        this.unOperario = unOperario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
        this.horas = horas;
        this.minutos = minutos;
        Remiseria.persistencia.insert(this);
    }
    
    public MarcarTarjeta(Maestro unMaestro, Date fechaInicio, Date fechaFin) {
        this.unMaestro = unMaestro;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
        this.horas = horas;
        this.minutos = minutos;        
        Remiseria.persistencia.insert(this);
    }

    public Date getTotal() {
        return total;
    }

    public void setTotal(Date total) {
        this.total = total;
    }

    public long getHoras() {
        return horas;
    }

    public void setHoras(long horas) {
        this.horas = horas;
    }

    public long getMinutos() {
        return minutos;
    }

    public void setMinutos(long minutos) {
        this.minutos = minutos;
    }


    public int getIdMarcarTarjeta() {
        return idMarcarTarjeta;
    }

    public void setIdMarcarTarjeta(int idMarcarTarjeta) {
        this.idMarcarTarjeta = idMarcarTarjeta;
    }

    public Operario getUnOperario() {
        return unOperario;
    }

    public void setUnOperario(Operario unOperario) {
        this.unOperario = unOperario;
    }

    public Maestro getUnMaestro() {
        return unMaestro;
    }

    public void setUnMaestro(Maestro unMaestro) {
        this.unMaestro = unMaestro;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }  

    @Override
    public String toString() {
        return "MarcarTarjeta{" + "idMarcarTarjeta=" + idMarcarTarjeta + ", unOperario=" + unOperario + ", unMaestro=" + unMaestro + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", horas=" + horas + ", minutos=" + minutos + ", total=" + total + '}';
    }
    
    
    
}
