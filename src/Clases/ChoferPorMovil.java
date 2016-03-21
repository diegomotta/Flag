/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class ChoferPorMovil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int idChoferPorMovil;
    @ManyToOne
    private Movil unMovil;
    @ManyToOne
    private Chofer unChofer;
    @OneToMany
    private Map<Integer,Viaje> viajes;
    private String horaInicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicio;
    private String horaFin;
    private String fechaFin;
    private String estadoRendicion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaHora;
    private int cantidViajes;
    private int kms;
    public ChoferPorMovil (){}

    public ChoferPorMovil(Movil unMovil,Chofer unChofer,String horaInicio, Date fechaInicio, String horaFin, String fechaFin) {
        this.horaInicio = horaInicio;
        this.fechaInicio = fechaInicio;
        this.horaFin = horaFin;
        this.fechaFin = fechaFin;
        this.fechaHora = fechaHora;
        
        this.unMovil = unMovil;
        this.unChofer = unChofer;
        this.estadoRendicion = "sin rendir";
        this.cantidViajes = 0;
        this.viajes = new HashMap();
        this.kms = 0;
        Remiseria.persistencia.insert(this);
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getKms() {
        return kms;
    }

    public void setKms(int kms) {
        this.kms = kms;
    }

    public int getCantidViajes() {
        return cantidViajes;
    }

    public void setCantidViajes(int cantidViajes) {
        this.cantidViajes = cantidViajes;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getEstadoRendicion() {
        return estadoRendicion;
    }

    public void setEstadoRendicion(String estadoRendicion) {
        this.estadoRendicion = estadoRendicion;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
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

    public int getIdChoferPorMovil() {
        return idChoferPorMovil;
    }

    public void setIdChoferPorMovil(int idChoferPorMovil) {
        this.idChoferPorMovil = idChoferPorMovil;
    }

    public Chofer getUnChofer() {
        return unChofer;
    }

    public void setUnChofer(Chofer unChofer) {
        this.unChofer = unChofer;
    }

    public Movil getUnMovil() {
        return unMovil;
    }

    public void setUnMovil(Movil unMovil) {
        this.unMovil = unMovil;
    }

    
    public void guardarViajesChoferPorMovil(Viaje unViaje){
        this.viajes.put(unViaje.getNumeroViaje(),unViaje);
        Remiseria.persistencia.update(this);
    }

    public Map<Integer, Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(Map<Integer, Viaje> viajes) {
        this.viajes = viajes;
    }

}
