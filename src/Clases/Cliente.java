/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Interface.VentanaLogueo;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Cliente  extends Persona   {
    @OneToMany   
    private Map<Integer,Telefono> telefonos;
    @OneToMany 
    private Map<Integer,Domicilio> domicilios; 
    @OneToMany
    private Map<Integer,Reserva> reservas; 
    @Transient
    private Utilidad utilidades1 = new Utilidad();
    public Cliente() 
    {}

    public Cliente(int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, String nombre, String apellido, Date fechaNacimiento) {
        super(dni, unTipoDni, unSexo, unEstadoCivil, unaNacionalidad, null, nombre, apellido,  fechaNacimiento);
        this.telefonos = new HashMap();
        this.domicilios = new HashMap();
        this.reservas = new HashMap();
        Remiseria.persistencia.insert(this);
    }

    public Map<Integer, Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Map<Integer, Reserva> reservas) {
        this.reservas = reservas;
    }


    public Map<Integer, Domicilio> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(Map<Integer, Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    public Map<Integer, Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(Map<Integer, Telefono> telefonos) {
        this.telefonos = telefonos;
    }





    public Telefono agregarTelefono(String numeroTelefono,CodArea unCodArea,CodPais unCodPais,TipoTelefono unTipoTelefono)
    {
        Telefono unTelefono = null;
        unTelefono = new Telefono(numeroTelefono,unCodArea,unCodPais,unTipoTelefono);
        int numero = unTelefono.getIdTelefono();
        this.telefonos.put(numero, unTelefono);
        Remiseria.persistencia.update(this);
        return unTelefono;
    
    }
    
    public List buscarTelefonos(){
        List telefonosActivos = new LinkedList();
        Collection tels = this.getTelefonos().values();
        Telefono aux = null;
        Iterator iter = tels.iterator();
        while (iter.hasNext()){
            aux = (Telefono) iter.next();
            telefonosActivos.add(aux);
            
        }
        return telefonosActivos;
    }
    
    public void modificarTelefono(Telefono unTelefono,String numeroTelefono,CodArea unCodArea,CodPais unCodPais,TipoTelefono unTipoTelefono){
        unTelefono.setNroTelefonico(numeroTelefono);
        unTelefono.setUnCodArea(unCodArea);
        unTelefono.setUnCodPais(unCodPais);
        unTelefono.setUnTipoTelefono(unTipoTelefono);
        Remiseria.persistencia.update(this);
    }
    
     public void eliminarTelefono(Telefono unTelefono,Remiseria unaRemiseria){
     
         this.telefonos.remove(unTelefono.getIdTelefono());
        //Remiseria.persistencia.delete(unTelefono);
        unaRemiseria.crearAuditoria("DEL", "class Clase.Telefono", VentanaLogueo.getUsuario(),utilidades1.getFechaActual());
        Remiseria.persistencia.update(this);
       
    }

     public Domicilio agregarDomicilio(Pais unPais,Provincia unaProvincia,Ciudad unaCiudad,Barrio unBarrio,String calle,String nroCasa,String piso,String dpto)
    {
        Domicilio unDomicilio = null;
        unBarrio.agregarDireccion("Calle "+ calle+ " N° "+ nroCasa);
        unDomicilio = new Domicilio(unPais,unaProvincia,unaCiudad,unBarrio,calle,nroCasa,piso,dpto);
        int numero = unDomicilio.getIdDomicilio();
        this.domicilios.put(numero, unDomicilio);
        Remiseria.persistencia.update(this);
        return unDomicilio;


    }
     
     
    public Reserva agregarReservaSegunDia(Reserva unaReserva){
        Reserva reserva = unaReserva;
        this.reservas.put(reserva.getNumeroReserva(), reserva);
        Remiseria.persistencia.update(this);
        return reserva;
    } 
     
    public void modificarReserva(Reserva unaReserva,DireccionViaje unaDireccionViaje, Date fechaInicio,Date horaInicio, Date horaFin, String estado, Movil unMovil, TipoUtilidad unTipoUtilidad,Pais unPais, Provincia unaProvincia, Ciudad unaCiudad, Barrio unBarrio,Chofer unChofer,Pais unPaisDestino,Provincia unaProvinciaDestino,Ciudad unaCiudadDestino,Cliente unCliente,String nombreEsporadico, Rodado unRodado, String tipoCliente){
           unaReserva.setUnaDireccionViaje(unaDireccionViaje);
           unaReserva.setEstado(estado);
           unaReserva.setFechaInicio(fechaInicio);
           unaReserva.setHoraInicio(horaInicio);
           unaReserva.setHoraFin(horaFin);
           unaReserva.setNombreClienteEsporadicto(nombreEsporadico);
           unaReserva.setTipoCliente(tipoCliente);
           unaReserva.setUnBarrio(unBarrio);
           unaReserva.setUnChofer(unChofer);
           unaReserva.setUnCliente(unCliente);
           unaReserva.setUnMovil(unMovil);
           unaReserva.setUnPais(unPais);
           unaReserva.setUnPaisDestino(unPaisDestino);
           unaReserva.setUnRodado(unRodado);
           unaReserva.setUnTipoUtilidad(unTipoUtilidad);
           unaReserva.setUnaCiudad(unaCiudad);
           unaReserva.setUnaCiudadDestino(unaCiudadDestino);
           unaReserva.setUnaProvincia(unaProvincia);
           unaReserva.setUnaProvinciaDestino(unaProvinciaDestino);
           unMovil.setUnChofer(unChofer);
           Remiseria.persistencia.update(this);
    }
    
     public void eliminarReserva(Reserva unaReserva)
     {
            //Reserva reserva = (Reserva) this.reservas.get(unaReserva.getNumeroReserva());
            this.reservas.remove(unaReserva.getNumeroReserva());
            Remiseria.persistencia.update(this);   
    
   }    
     
    public List buscarDirecciones(Cliente unCliente){
        List direccionesActivos = new LinkedList();
        Collection direccionesDeCliente = unCliente.getDomicilios().values();
        Domicilio aux = null;
        Iterator iter = direccionesDeCliente.iterator();
        while (iter.hasNext()){
            aux = (Domicilio) iter.next();
            direccionesActivos.add(aux);
            
        }
        return direccionesActivos;
    }
    
    public boolean existeTelefono(CodPais codPais, CodArea codArea, String numero)
    {
          
          Collection todosLosTelefonos = telefonos.values();
          Telefono aux = null;
          Iterator iter = todosLosTelefonos.iterator();
          while (iter.hasNext())
          {
            aux = (Telefono) iter.next();
            if((aux.getUnCodPais().getCodPais().equals(codPais.getCodPais()) && (aux.getUnCodArea().getCodArea().equals(codArea.getCodArea()))) && (aux.getNroTelefonico().equals(numero)))
            {
                return true; 
            }
            
        }
          return false;
    }
    
    public List buscarTelefonos(Cliente unCliente){
        List telefonosActivos = new LinkedList();
        Collection telefonosDeCliente = unCliente.getTelefonos().values();
        Telefono aux = null;
        Iterator iter = telefonosDeCliente.iterator();
        while (iter.hasNext()){
            aux = (Telefono) iter.next();
            telefonosActivos.add(aux);
            
        }
        return telefonosActivos;
    }
    
    public void modificarDireccion(Domicilio unDomicilio,Pais unPais,Provincia unaProvincia,Ciudad unaCiudad,Barrio unBarrio,String calle,String nroCasa,String piso,String dpto){
        unDomicilio.setUnPais(unPais);
        unDomicilio.setUnaProvincia(unaProvincia);
        unDomicilio.setUnaCiudad(unaCiudad);
        unDomicilio.setUnBarrio(unBarrio);
        unDomicilio.setCalle(calle);
        unDomicilio.setNroCasa(nroCasa);
        unDomicilio.setPiso(piso);
        unDomicilio.setDpto(dpto);
        Remiseria.persistencia.update(this);
    }
    
     public void eliminarDomicilio(Domicilio unDomicilio){
        this.domicilios.remove(unDomicilio.getIdDomicilio());
        //Remiseria.persistencia.delete(unDomicilio);
        Remiseria.persistencia.update(this);
       
    }
     
     public Domicilio buscarDomicilio(String numeroDomicilio, Cliente cliente)
     {
        Collection domiciliosCliente = cliente.getDomicilios().values();
        Domicilio aux = null;
        Domicilio retorno = null;
        Iterator iter = domiciliosCliente.iterator();
        while (iter.hasNext()){
            aux = (Domicilio) iter.next();
            if (aux.getNroCasa().equals(numeroDomicilio)){
                retorno = aux;
            }
        }
         return retorno;
     }
    
     public Telefono buscarTelefono(String numeroTelefono){
        Collection tels = this.getTelefonos().values();
        Telefono aux = null;
        Telefono retorno = null;
        Iterator iter = tels.iterator();
        while (iter.hasNext()){
            aux = (Telefono) iter.next();
            if (aux.getNroTelefonico().equals(numeroTelefono)){
                retorno = aux;
            }
        }
        return retorno;
    }
    
          @Override
    public String toString() 
    {
        return this.getNombre()+ " "+ this.getApellido();
    }
}
