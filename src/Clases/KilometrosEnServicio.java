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
public class KilometrosEnServicio implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idKilometrosEnServicio;
  private int kilometroInicialServicio;
  private int kilometroFinalServicio;
  private int totalKms;
  @Temporal(javax.persistence.TemporalType.TIMESTAMP)
  private Date fechaHoraInicio;
  @Temporal(javax.persistence.TemporalType.TIMESTAMP)
  private Date fechaHoraFinal;
  @OneToOne
  private Chofer unChofer;

  public KilometrosEnServicio(){}
  
    public KilometrosEnServicio(int kilometroInicialServicio, int kilometroFinalServicio, Date fechaHoraInicio, Date fechaHoraFinal, Chofer unChofer) {
        this.kilometroInicialServicio = kilometroInicialServicio;
        this.kilometroFinalServicio = kilometroFinalServicio;
        this.totalKms = totalKms;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFinal = fechaHoraFinal;
        this.unChofer = unChofer;
        Remiseria.persistencia.insert(this);
    }

    public int getTotalKms() {
        return totalKms;
    }

    public void setTotalKms(int totalKms) {
        this.totalKms = totalKms;
    }

    public int getIdKilometrosEnServicio() {
        return idKilometrosEnServicio;
    }

    public void setIdKilometrosEnServicio(int idKilometrosEnServicio) {
        this.idKilometrosEnServicio = idKilometrosEnServicio;
    }

    public int getKilometroInicialServicio() {
        return kilometroInicialServicio;
    }

    public void setKilometroInicialServicio(int kilometroInicialServicio) {
        this.kilometroInicialServicio = kilometroInicialServicio;
    }

    public int getKilometroFinalServicio() {
        return kilometroFinalServicio;
    }

    public void setKilometroFinalServicio(int kilometroFinalServicio) {
        this.kilometroFinalServicio = kilometroFinalServicio;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(Date fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Date getFechaHoraFinal() {
        return fechaHoraFinal;
    }

    public void setFechaHoraFinal(Date fechaHoraFinal) {
        this.fechaHoraFinal = fechaHoraFinal;
    }

    public Chofer getUnChofer() {
        return unChofer;
    }

    public void setUnChofer(Chofer unChofer) {
        this.unChofer = unChofer;
    }
  
  
}
