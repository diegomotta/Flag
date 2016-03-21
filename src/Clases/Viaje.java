/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.envers.Audited;
/**
 *
 * @author Owner
 */
@Audited
@Entity
public class Viaje implements Serializable 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int numeroViaje;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaHora;
    private String estado;
    private String asignadoBase;
    private int tarifaSiNo;
    private double tarifa;
    @ManyToOne
    private Movil unMovil;
    @ManyToOne
    private Chofer unChofer;
    @ManyToOne
    private Zona  unaZona;
    @Transient
    private Utilidad utilidad = new Utilidad();
    @ManyToOne 
    private Domicilio unDomiclio;
    @ManyToOne
    private UtilidadViaje unaUtilidadViaje;
    @OneToOne
    private Cliente unCliente;
    private String estadoRendicion;
    //Datos para las reservas que luego se convierten en viaje
    private String destino;
    private String inicioReserva ;
    private String tipoClienteReserva;
    private String viajeReservado;
    public Viaje() {}

    public Viaje(UtilidadViaje unaUtilidadViaje,String asignadoBase, Movil unMovil, Domicilio unDomicilio, double tarifa, Zona unaZona,Cliente unCliente) {
        this.fechaHora = utilidad.getFechaHoraActual();
        this.estado = "activo";
        this.estadoRendicion = "sin rendir";
        this.asignadoBase = asignadoBase;
        this.unMovil = unMovil;
        this.unDomiclio = unDomicilio;
        this.unChofer = unMovil.getUnChofer();
        this.tarifa = tarifa;
        this.unaZona = unaZona ;
        this.unaUtilidadViaje = unaUtilidadViaje ;
        this.destino = null;
        this.inicioReserva = null;
        this.tipoClienteReserva = null;
        this.viajeReservado = null;
        this.unCliente = unCliente;
        Remiseria.persistencia.insert(this);
    }

    public Cliente getUnCliente() {
        return unCliente;
    }

    public void setUnCliente(Cliente unCliente) {
        this.unCliente = unCliente;
    }

    public String getViajeReservado() {
        return viajeReservado;
    }

    public void setViajeReservado(String viajeReservado) {
        this.viajeReservado = viajeReservado;
    }

    public String getTipoClienteReserva() {
        return tipoClienteReserva;
    }

    public void setTipoClienteReserva(String tipoClienteReserva) {
        this.tipoClienteReserva = tipoClienteReserva;
    }

    public String getInicioReserva() {
        return inicioReserva;
    }

    public void setInicioReserva(String inicioReserva) {
        this.inicioReserva = inicioReserva;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEstadoRendicion() {
        return estadoRendicion;
    }

    public void setEstadoRendicion(String estadoRendicion) {
        this.estadoRendicion = estadoRendicion;
    }

    public UtilidadViaje getUnaUtilidadViaje() {
        return unaUtilidadViaje;
    }

    public void setUnaUtilidadViaje(UtilidadViaje unaUtilidadViaje) {
        this.unaUtilidadViaje = unaUtilidadViaje;
    }

    public Domicilio getUnDomiclio() {
        return unDomiclio;
    }

    public void setUnDomiclio(Domicilio unDomiclio) {
        this.unDomiclio = unDomiclio;
    }

    public Zona getUnaZona() {
        return unaZona;
    }

    public void setUnaZona(Zona unaZona) {
        this.unaZona = unaZona;
    }

    public String getAsignadoBase() {
        return asignadoBase;
    }

    public void setAsignadoBase(String asignadoBase) {
        this.asignadoBase = asignadoBase;
    }
        
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getNumeroViaje() {
        return numeroViaje;
    }

    public void setNumeroViaje(int numeroViaje) {
        this.numeroViaje = numeroViaje;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public int getTarifaSiNo() {
        return tarifaSiNo;
    }

    public void setTarifaSiNo(int tarifaSiNo) {
        this.tarifaSiNo = tarifaSiNo;
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

    public Utilidad getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(Utilidad utilidad) {
        this.utilidad = utilidad;
    }
    
    public int obtenerNumeroMovil(){
        int retorno = this.unMovil.getNumeroMovil();
        return retorno;
    }
}
