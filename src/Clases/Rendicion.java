/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.hibernate.envers.Audited;


/**
 *
 * @author garba
 */
@Audited
@Entity
public class Rendicion implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idRendicion;
    private double totalRendicion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaRendicion;
    @ManyToOne
    private Movil unMovil;
    @ManyToOne
    private Chofer unChofer;
    @OneToMany
    private Map<Integer,Viaje> viajesPagados;
    private int totalKilometros;

    public Rendicion (){}
    
    public Rendicion (double totalRendicion, Date fechaRendicion, Movil unMovil, Chofer unChofer, int totalKilometros){
        this.totalRendicion = totalRendicion;
        this.fechaRendicion = fechaRendicion;
        this.viajesPagados = new HashMap();
        this.totalKilometros = totalKilometros;
        this.unMovil = unMovil;
        this.unChofer = unChofer;
        Remiseria.persistencia.insert(this);
    }
    
    public int getTotalKilometros() {
        return totalKilometros;
    }

    public void setTotalKilometros(int totalKilometros) {
        this.totalKilometros = totalKilometros;
    }

    public Map<Integer, Viaje> getViajesPagados() {
        return viajesPagados;
    }

    public void setViajesPagados(Map<Integer, Viaje> viajesPagados) {
        this.viajesPagados = viajesPagados;
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

    public int getIdRendicion() {
        return idRendicion;
    }

    public void setIdRendicion(int idRendicion) {
        this.idRendicion = idRendicion;
    }


    public double getTotalRendicion() {
        return totalRendicion;
    }

    public void setTotalRendicion(double totalRendicion) {
        this.totalRendicion = totalRendicion;
    }

    public Date getFechaRendicion() {
        return fechaRendicion;
    }

    public void setFechaRendicion(Date fechaRendicion) {
        this.fechaRendicion = fechaRendicion;
    }
    
        public void agregarViajes(Viaje unViaje)
    {
        this.viajesPagados.put(unViaje.getNumeroViaje(),unViaje);
        Remiseria.persistencia.update(this);
    }
}
