/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
public class Liquidacion implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int idLiquidacion;
@Temporal(javax.persistence.TemporalType.TIMESTAMP)
private Date fechaCreacion;
@Temporal(javax.persistence.TemporalType.TIMESTAMP)
private Date fechaDePago;
private double totalLiquidado;
@OneToOne
private Chofer unChofer;
@OneToOne
private Operario unOperario;
@OneToOne
private MarcarTarjeta unMarcarTarjeta;
@OneToOne
private Maestro unMaestro;
@OneToOne
private Rendicion unaRendicion;
@OneToOne
private Cargo unCargo;
private String estadoLiquidacion;
private String detalle;
    public Liquidacion() {
    }

    public Liquidacion(Date fechaCreacion, double totalLiquidado, Maestro unMaestro,MarcarTarjeta unMarcarTarjeta) {
        this.unMaestro = unMaestro;
        this.fechaCreacion = fechaCreacion;
        this.totalLiquidado = totalLiquidado;
        this.fechaDePago = fechaDePago;
        this.unMarcarTarjeta = unMarcarTarjeta;
        this.estadoLiquidacion = "sin pagar";
        this.detalle = detalle;
        Remiseria.persistencia.insert(this);
    }

    public Liquidacion(Date fechaCreacion, double totalLiquidado, Operario unOperario, MarcarTarjeta unMarcarTarjeta) {
        this.unOperario = unOperario;
        this.fechaCreacion = fechaCreacion;
        this.fechaDePago = fechaDePago;
        this.totalLiquidado = totalLiquidado;
        this.unMarcarTarjeta = unMarcarTarjeta;
        this.estadoLiquidacion = "sin pagar";
        this.detalle = detalle;
        Remiseria.persistencia.insert(this);
    }

    public Liquidacion(Date fechaCreacion, double totalLiquidado, Chofer unChofer, Rendicion unaRendicion) 
    {
        this.unaRendicion = unaRendicion;
        this.unChofer = unChofer;
        this.fechaCreacion = fechaCreacion;
        this.fechaDePago = fechaDePago;
        this.totalLiquidado = totalLiquidado;
        this.estadoLiquidacion = "sin pagar";
        this.detalle = detalle;
        Remiseria.persistencia.insert(this);
    }
    
        public Liquidacion(Date fechaDePago,Cargo unCargo) 
    {
        this.unCargo = unCargo;
        this.fechaDePago = fechaDePago;
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
        this.totalLiquidado = Double.parseDouble(formateador.format(unCargo.getSueldo()));
        this.estadoLiquidacion = "pagado";
        this.detalle = "Rindió "+unCargo.getTipoPago();
        Remiseria.persistencia.insert(this);
    }

    public Cargo getUnCargo() {
        return unCargo;
    }

    public void setUnCargo(Cargo unCargo) {
        this.unCargo = unCargo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }
    
    public MarcarTarjeta getUnMarcarTarjeta() {
        return unMarcarTarjeta;
    }

    public void setUnMarcarTarjeta(MarcarTarjeta unMarcarTarjeta) {
        this.unMarcarTarjeta = unMarcarTarjeta;
    }

    public Maestro getUnMaestro() {
        return unMaestro;
    }

    public void setUnMaestro(Maestro unMaestro) {
        this.unMaestro = unMaestro;
    }

    public Chofer getUnChofer() {
        return unChofer;
    }

    public void setUnChofer(Chofer unChofer) {
        this.unChofer = unChofer;
    }

    public Operario getUnOperario() {
        return unOperario;
    }

    public void setUnOperario(Operario unOperario) {
        this.unOperario = unOperario;
    }

    public Rendicion getUnaRendicion() {
        return unaRendicion;
    }

    public void setUnaRendicion(Rendicion unaRendicion) {
        this.unaRendicion = unaRendicion;
    }

    public String getEstadoLiquidacion() {
        return estadoLiquidacion;
    }

    public void setEstadoLiquidacion(String estadoLiquidacion) {
        this.estadoLiquidacion = estadoLiquidacion;
    }
    
    public int getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(int idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getTotalLiquidado() {
        return totalLiquidado;
    }

    public void setTotalLiquidado(double totalLiquidado) {
        this.totalLiquidado = totalLiquidado;
    }



}
