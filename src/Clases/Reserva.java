/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import org.hibernate.envers.Audited;

/**
 *
 * @author Owner
 */
@Audited
@Entity
public class Reserva implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int numeroReserva;
    private String nombreClienteEsporadicto;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date horaInicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date horaFin;
    private String estado;
    @ManyToOne
    private Movil unMovil;
    @ManyToOne
    private TipoUtilidad unTipoUtilidad;
    @ManyToOne
    private Pais unPais;
    @ManyToOne
    private Provincia unaProvincia;
    @ManyToOne
    private Ciudad unaCiudad;
    @ManyToOne
    private Barrio unBarrio;
    @ManyToOne
    private DireccionViaje unaDireccionViaje;
    @ManyToOne    
    private Chofer unChofer;
    @Transient
    private Utilidad utilidades = new Utilidad();
    @ManyToOne
    private Pais unPaisDestino;
    @ManyToOne
    private Provincia unaProvinciaDestino;
    @ManyToOne
    private Ciudad unaCiudadDestino;
    @ManyToOne
    private Cliente unCliente;
    @ManyToOne
    private Rodado unRodado;
    private String tipoCliente;
    private int numeroDelViajeGenerado;
    @OneToMany
    private Map<String, Dias> dias;  
    private String direccionDestino;
    public Reserva() {
    }

    public Reserva(Date fechaInicio,Date horaInicio, Date horaFin, String estado, Movil unMovil, TipoUtilidad unTipoUtilidad,Pais unPais, Provincia unaProvincia, Ciudad unaCiudad, Barrio unBarrio,DireccionViaje unaDireccionViaje,Chofer unChofer,Pais unPaisDestino,Provincia unaProvinciaDestino,Ciudad unaCiudadDestino, String direccionDestino,Cliente unCliente,String nombreClienteEsporadico,Rodado unRodado,String tipoCliente,Map<String, Dias> dias) {
        this.unaDireccionViaje = unaDireccionViaje;
        this.fechaInicio = fechaInicio;
        this.horaFin = horaFin;
        this.horaInicio = horaInicio;
        this.estado = estado;
        this.unMovil = unMovil;
        this.unTipoUtilidad = unTipoUtilidad;
        this.unPais = unPais;
        this.unaProvincia = unaProvincia;
        this.unaCiudad = unaCiudad;
        this.unBarrio = unBarrio;
        this.unChofer = unChofer;
        this.unPaisDestino = unPaisDestino;
        this.unaProvinciaDestino = unaProvinciaDestino;
        this.unaCiudadDestino = unaCiudadDestino;
        this.unCliente = unCliente;
        this.nombreClienteEsporadicto = nombreClienteEsporadico;
        this.unRodado = unRodado;
        this.tipoCliente = tipoCliente;
        this.numeroDelViajeGenerado = 0;
        this.dias= dias;
        this.direccionDestino = direccionDestino;
        Remiseria.persistencia.insert(this);
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }


    public Map<String, Dias> getDias() {
        return dias;
    }

    public void setDias(Map<String, Dias> dias) {
        this.dias = dias;
    }

    
    public int getNumeroDelViajeGenerado() {
        return numeroDelViajeGenerado;
    }

    public void setNumeroDelViajeGenerado(int numeroDelViajeGenerado) {
        this.numeroDelViajeGenerado = numeroDelViajeGenerado;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Rodado getUnRodado() {
        return unRodado;
    }

    public void setUnRodado(Rodado unRodado) {
        this.unRodado = unRodado;
    }

    public String getNombreClienteEsporadicto() {
        return nombreClienteEsporadicto;
    }

    public void setNombreClienteEsporadicto(String nombreClienteEsporadicto) {
        this.nombreClienteEsporadicto = nombreClienteEsporadicto;
    }

    public Cliente getUnCliente() {
        return unCliente;
    }

    public void setUnCliente(Cliente unCliente) {
        this.unCliente = unCliente;
    }

      
    
    public Pais getUnPaisDestino() {
        return unPaisDestino;
    }

    public void setUnPaisDestino(Pais unPaisDestino) {
        this.unPaisDestino = unPaisDestino;
    }

    public Ciudad getUnaCiudadDestino() {
        return unaCiudadDestino;
    }

    public void setUnaCiudadDestino(Ciudad unaCiudadDestino) {
        this.unaCiudadDestino = unaCiudadDestino;
    }

    public Provincia getUnaProvinciaDestino() {
        return unaProvinciaDestino;
    }

    public void setUnaProvinciaDestino(Provincia unaProvinciaDestino) {
        this.unaProvinciaDestino = unaProvinciaDestino;
    }

    public Pais getUnPais() {
        return unPais;
    }

    public void setUnPais(Pais unPais) {
        this.unPais = unPais;
    }

    public Chofer getUnChofer() {
        return unChofer;
    }

    public void setUnChofer(Chofer unChofer) {
        this.unChofer = unChofer;
    }

    public DireccionViaje getUnaDireccionViaje() {
        return unaDireccionViaje;
    }

    public void setUnaDireccionViaje(DireccionViaje unaDireccionViaje) {
        this.unaDireccionViaje = unaDireccionViaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(int numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public Barrio getUnBarrio() {
        return unBarrio;
    }

    public void setUnBarrio(Barrio unBarrio) {
        this.unBarrio = unBarrio;
    }

    public Movil getUnMovil() {
        return unMovil;
    }

    public void setUnMovil(Movil unMovil) {
        this.unMovil = unMovil;
    }

    public TipoUtilidad getUnTipoUtilidad() {
        return unTipoUtilidad;
    }

    public void setUnTipoUtilidad(TipoUtilidad unTipoUtilidad) {
        this.unTipoUtilidad = unTipoUtilidad;
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

    public Utilidad getUtilidades() {
        return utilidades;
    }

    public void setUtilidades(Utilidad utilidades) {
        this.utilidades = utilidades;
    }

    
    
    public boolean esHoy(){
        boolean retorno = false;
        Date ahora = utilidades.getFechaHoraActual();
        if(this.getFechaInicio()!=null)
        {
            Date fechaAca = this.getFechaInicio();

            if (fechaAca.getYear() == ahora.getYear() && fechaAca.getMonth() == ahora.getMonth() && fechaAca.getDate() == ahora.getDate()){
                retorno = true;
            }
        }
        return retorno;
    }
    
    public String obtenerDiaSemana(){
      String[] dias={"domingo","lunes","martes", "miercoles","jueves","viernes","sabado"};
      Date hoy=new Date();
      int numeroDia=0;
      Calendar cal= Calendar.getInstance();
      cal.setTime(hoy);
      numeroDia=cal.get(Calendar.DAY_OF_WEEK);
      //System.out.println("hoy es "+ dias[numeroDia - 1]);
      return dias[numeroDia - 1];
    }
    
    public boolean buscarDia(Reserva unaReserva)
    {
        Collection dias = unaReserva.getDias().values();
        Dias dia = null;
        Iterator iter = dias.iterator();
        while (iter.hasNext())
        {
            dia = (Dias) iter.next();
            //System.out.println(dia.getDiaSemana());
            //System.out.println(this.obtenerDiaSemana());
            if (dia.getDiaSemana().equals(this.obtenerDiaSemana()))
            {
                return true;
            }
        }
        
        return false;
    }
            
     public void EliminarDias (Reserva unaReserva)
    {
         unaReserva.getDias().clear();
         Remiseria.persistencia.update(this);   
    }

   public Collection pasarString()
   {
        Collection imprimirDias = new LinkedList();
        Collection dias = this.getDias().values();
        Dias dia = null;
        Iterator iter = dias.iterator();
        while (iter.hasNext())
        {
            dia = (Dias) iter.next();
            imprimirDias.add(dia.getDiaSemana().toString());
            
        }
        
        return imprimirDias;
    }     
     
     
    @Override
    public String toString() {
        return "Reserva{" + "numeroReserva=" + this.numeroReserva+ "destino=" + this.unaDireccionViaje + "nombreClienteEsporadicto=" + this.nombreClienteEsporadicto + "fechaInicio="+this.fechaInicio+ "horaInicio=" +this.horaInicio+ "horaFin="+this.horaFin+ "estado="+ this.estado+ "unMovil="+ this.unMovil.getNumeroMovil()+"unTipoUtilidad="+ this.unTipoUtilidad.getNombreTipoUtilidad()+ this.unPais.getNombrePais() +"unProvincia=" + this.unaProvincia.getNombreProvincia() +"unaCiudad="+this.unaCiudad.getNombreCiudad() +"unBarrio=" + this.unBarrio.getNombreBarrio()+"unPaisDestino="+ this.unPaisDestino.getNombrePais()+"unaProvinciaDestino="+ this.unaProvincia.getNombreProvincia()+"unaCiudadDestino"+this.unaCiudadDestino.getNombreCiudad() + '}';
    }
}
