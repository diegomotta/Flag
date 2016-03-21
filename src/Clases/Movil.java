/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import javax.persistence.*;
import javax.swing.JOptionPane;
import org.hibernate.envers.Audited;

/**
 *
 * @author Owner
 */
@Audited
@Entity
public class Movil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMovil;
    private int numeroMovil;
    private String patente;
    private int anio;
    private String estado;
    private int prioridad;
    @ManyToOne
    private Chofer unChofer;
    @ManyToOne
    private Marca unaMarca;
    @ManyToOne
    private Modelo unModelo;
    @ManyToOne
    private Motor unMotor;
    @ManyToOne
    private Color unColor;
    @ManyToOne
    private TipoUtilidad unTipoUtilidad;
    @ManyToOne
    private Rodado unRodado;
    private int kilometraje;
    private int kilometrajeActual;
    private int kilometrosTrabajados;
    private int kilometraje2;
    private int kilometrajeActual2;
    private String aireAcondicionado;
    private Double capacidadCarga;
    @ManyToOne
    private Zona unaZona;
    @OneToMany
    private Map<Integer,Viaje> viajes ;
    @OneToMany
    private Map<Integer,KilometrosRecorridos> kilometrosRecorridos;    
    @OneToMany
    private Map<Integer,KilometrosEnServicio> kilometrosEnServicios;   
    private boolean alquilado;
    public Movil() {}

    public Movil(int numeroMovil, String patente, Marca unaMarca, Modelo unModelo, int anio, Color unColor,Chofer unChofer,TipoUtilidad unTipoUtilidad,Rodado unRodado,int kilometraje,String aireAcondicionado,Double capacidadCarga,Motor unMotor) {
        this.numeroMovil = numeroMovil;
        this.patente = patente;
        this.unaMarca = unaMarca;
        this.unModelo = unModelo;
        this.anio = anio;
        this.unColor = unColor;
        this.estado = "sin servicio";
        this.prioridad = 0;
        this.unChofer = unChofer;
        this.unTipoUtilidad = unTipoUtilidad;
        this.unRodado = unRodado;
        this.kilometraje = kilometraje;
        this.aireAcondicionado = aireAcondicionado;
        this.capacidadCarga = capacidadCarga;
        this.unMotor = unMotor;
        this.unaZona = unaZona;
        this.kilometrosTrabajados = 0;
        this.kilometrajeActual = 0;
        this.kilometraje2 = 0;
        this.kilometrajeActual2 = 0;
        this.viajes = new HashMap();
        this.kilometrosRecorridos = new HashMap();
        this.kilometrosEnServicios = new HashMap();
        this.alquilado = false;
        Remiseria.persistencia.insert(this);
    }

    public boolean isAlquilado() {
        return alquilado;
    }

    public void setAlquilado(boolean alquilado) {
        this.alquilado = alquilado;
    }

    public Map<Integer, KilometrosEnServicio> getKilometrosEnServicios() {
        return kilometrosEnServicios;
    }

    public void setKilometrosEnServicios(Map<Integer, KilometrosEnServicio> kilometrosEnServicios) {
        this.kilometrosEnServicios = kilometrosEnServicios;
    }

    public Map<Integer, KilometrosRecorridos> getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(Map<Integer, KilometrosRecorridos> kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    public int getKilometrosTrabajados() {
        return kilometrosTrabajados;
    }

    public int getKilometraje2() {
        return kilometraje2;
    }

    public void setKilometraje2(int kilometraje2) {
        this.kilometraje2 = kilometraje2;
    }

    public int getKilometrajeActual2() {
        return kilometrajeActual2;
    }

    public void setKilometrajeActual2(int kilometrajeActual2) {
        this.kilometrajeActual2 = kilometrajeActual2;
    }

    public void setKilometrosTrabajados(int kilometrosTrabajados) {
        this.kilometrosTrabajados = kilometrosTrabajados;
    }

    public int getKilometrajeActual() {
        return kilometrajeActual;
    }

    public void setKilometrajeActual(int kilometrajeActual) {
        this.kilometrajeActual = kilometrajeActual;
    }

    public Map<Integer, Viaje> getViajes() {
        return viajes;
    }

    public Zona getUnaZona() {
        return unaZona;
    }

    public void setUnaZona(Zona unaZona) {
        this.unaZona = unaZona;
    }

   public int getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(int idMovil) {
        this.idMovil = idMovil;
    }

    public String getAireAcondicionado() {
        return aireAcondicionado;
    }

    public void setAireAcondicionado(String aireAcondicionado) {
        this.aireAcondicionado = aireAcondicionado;
    }

    public Double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(Double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumeroMovil() {
        return numeroMovil;
    }

    public void setNumeroMovil(int numeroMovil) {
        this.numeroMovil = numeroMovil;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Chofer getUnChofer() {
        return unChofer;
    }

    public void setUnChofer(Chofer unChofer) {
        this.unChofer = unChofer;
    }

    public Color getUnColor() {
        return unColor;
    }

    public void setUnColor(Color unColor) {
        this.unColor = unColor;
    }

    public Modelo getUnModelo() {
        return unModelo;
    }

    public void setUnModelo(Modelo unModelo) {
        this.unModelo = unModelo;
    }

    public Motor getUnMotor() {
        return unMotor;
    }

    public void setUnMotor(Motor unMotor) {
        this.unMotor = unMotor;
    }

    public Rodado getUnRodado() {
        return unRodado;
    }

    public void setUnRodado(Rodado unRodado) {
        this.unRodado = unRodado;
    }

    public TipoUtilidad getUnTipoUtilidad() {
        return unTipoUtilidad;
    }

    public void setUnTipoUtilidad(TipoUtilidad unTipoUtilidad) {
        this.unTipoUtilidad = unTipoUtilidad;
    }

    public Marca getUnaMarca() {
        return unaMarca;
    }

    public void setUnaMarca(Marca unaMarca) {
        this.unaMarca = unaMarca;
    }



    public void setViajes(Map viajes) {
        this.viajes = viajes;
    }

    

    
    public void agregarViaje(Viaje unViaje)
    {
        this.viajes.put(unViaje.getNumeroViaje(),unViaje);
        Remiseria.persistencia.update(this);
    }
    
    public void movilDescanso(String estado){
       // this.prioridad = 0;
        this.estado = estado;
        this.unaZona = null;
        this.prioridad = 0;
        Remiseria.persistencia.update(this);
   }
   
    
   public void movilSinServicio(String estado){
        this.prioridad = 0;
        this.estado = estado;
        this.unaZona = null;
        Remiseria.persistencia.update(this);
   }
   
    public void movilDisponible (Zona unaZona,String estado)
    {
        this.estado = estado;
        this.unaZona = unaZona;
        Remiseria.persistencia.update(this);
    }
    
    public void movilDisponibleI (Zona unaZona, Chofer unChofer)
    {
        this.unaZona = unaZona;
        this.unChofer = unChofer;
        this.estado ="disponible";
        Remiseria.persistencia.update(this);
    }
    
    public Viaje buscarViaje(Viaje unViaje)
    {
       Collection via = this.getViajes().values();
       Viaje aux = null;
       Iterator iter = via.iterator();
       while(iter.hasNext())
       {
           aux = (Viaje) iter.next();
           if(aux.equals(unViaje))
           {
               return aux;
           }
       }
       return null;
    }
    
    public void iniciarViaje(Viaje unViaje){
       // this.prioridad = 0;
        this.viajes.put(unViaje.getNumeroViaje(),unViaje);
        this.estado = "transito";
        Remiseria.persistencia.update(this);
        this.unChofer.setUnViaje(unViaje);
    }
    
    public void cancelarViaje(int numero){
        Viaje viaje = (Viaje) this.viajes.get(numero);
        viaje.setEstado("cancelado");
        this.setEstado("disponible");
        this.unChofer.setUnViaje(null);
        Remiseria.persistencia.update(this);
    }
    
    public void finalizarViaje(int numero, int tarifaSiNo, double tarifa){
        Viaje viaje = (Viaje) this.viajes.get(numero);
        viaje.setEstado("finalizado");
        viaje.setTarifaSiNo(tarifaSiNo);
        viaje.setTarifa(tarifa);
        this.setEstado("disponible");
        this.unChofer.setUnViaje(null);
        Remiseria.persistencia.update(this);
    }
    
    public String obtenerNombreChofer(){
        String nombre = this.unChofer.getNombre() + " " + this.unChofer.getApellido();
        return nombre;
    }
    
    public int obtenerDniChofer(){
        int dni = 0;
        if (this.unChofer != null){
            dni = this.unChofer.getDni();
        }
        return dni;
    }
    
    public void pasarViajes(Movil movilNuevo){
        Collection losViajes = this.viajes.values();
        Viaje aux = null;
        Iterator iter = losViajes.iterator();
        while (iter.hasNext()){
            aux = (Viaje) iter.next();
            aux.setUnMovil(movilNuevo);
            movilNuevo.agregarViaje(aux);
            this.viajes.remove(aux.getNumeroViaje());
        }
        Remiseria.persistencia.update(this);
       
    }

  public void cargarDatosMovilSinServicio (Movil unMovil, String estado, String kilometrajeActual)
  {
        unMovil.setEstado(estado);
        unMovil.setUnaZona(null);
        unMovil.setPrioridad(0);    
        unMovil.movilSinServicio(estado);
        unMovil.setKilometrajeActual(Integer.valueOf(kilometrajeActual));
        unMovil.setKilometrosTrabajados(unMovil.getKilometrosTrabajados() +(unMovil.getKilometrajeActual()-unMovil.getKilometraje()));
        unMovil.setKilometraje2(unMovil.getKilometraje());
        unMovil.setKilometrajeActual2(unMovil.getKilometrajeActual());
        unMovil.setKilometraje(unMovil.getKilometrajeActual());  
        Remiseria.persistencia.update(this);
  }
    
  public void cargarDatosMovilDisponible(Movil unMovil,String estate,String kilometrajeActual,Zona zona)
  {
        unMovil.setEstado(estate);
        unMovil.setUnaZona(zona);
        unMovil.movilDisponible(zona, estate);
        unMovil.setKilometrajeActual(Integer.valueOf(kilometrajeActual));
        unMovil.setKilometraje2(unMovil.getKilometraje());
        unMovil.setKilometrajeActual2(unMovil.getKilometrajeActual());
        unMovil.setKilometraje(unMovil.getKilometrajeActual());
        unMovil.setKilometrajeActual(0);  
        Remiseria.persistencia.update(this);        
  }
  
  public void cargarDatosMovilDescanso(Movil unMovil,String estate,String kilometrajeActual)
  {
        unMovil.setEstado(estate);
        unMovil.setUnaZona(null);
        unMovil.setPrioridad(0);    
        unMovil.movilDescanso(estate);
        unMovil.setKilometrajeActual(Integer.valueOf(kilometrajeActual));
        unMovil.setKilometrosTrabajados(unMovil.getKilometrosTrabajados() +(unMovil.getKilometrajeActual()-unMovil.getKilometraje()));
        unMovil.setKilometraje2(unMovil.getKilometraje());
        unMovil.setKilometrajeActual2(unMovil.getKilometrajeActual());
        unMovil.setKilometraje(unMovil.getKilometrajeActual());  
        Remiseria.persistencia.update(this);      
  }
  
  
  
    @Override
    public String toString() {
        return String.valueOf(this.numeroMovil);
    }


    public void agregarKilometrajeRecorridoPorVuelta(int kilometrosInicial,Date fecha)
    {
        KilometrosRecorridos kmsRec = null;
        kmsRec = new KilometrosRecorridos(fecha,kilometrosInicial,0,0);
        this.kilometrosRecorridos.put(kmsRec.getIdKilometrosRecorridos(), kmsRec);
        Remiseria.persistencia.update(this);
    }
    
    public Viaje buscarUltimoViaje (Movil unMovil)
    {
        LinkedList<Viaje> m = new LinkedList();
        Collection marcados1 = unMovil.getViajes().values();
        Viaje marcaTar = null;
        Viaje marcaTar2 = null;
        Iterator iter = marcados1.iterator();
        while (iter.hasNext())
        {
            marcaTar = (Viaje) iter.next();
            m.add(marcaTar);    
        }
        
        Collections.sort(m, new Comparator<Viaje>() {
        @Override
        public int compare(Viaje p1, Viaje p2) {                
                return new Integer(p1.getNumeroViaje()).compareTo(new Integer(p2.getNumeroViaje()));
        }
        });   
       //marcaTar2 = (Viaje) m.get(marcados1.size()-1);
        marcaTar2 = (Viaje) m.getLast();
        return marcaTar2;    
    }
    
    public KilometrosRecorridos modificarKilometrajeRecorridoPorVuelta(KilometrosRecorridos kmsRec, int kilometroFinal, Movil unMovil, Remiseria remiseria)
    {
        try{
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat formateador = new DecimalFormat("###########.#",simbolos);
            kmsRec.setKilometroFinal(kilometroFinal);
            kmsRec.setTotalKilometrajeVuelta(kmsRec.getKilometroFinal()-kmsRec.getKilometrosInicial());
            Viaje unViaje = this.buscarUltimoViaje(unMovil);
            System.out.println(unViaje.getTarifa());
            double valor = unViaje.getTarifa();
            Tarifa unaTarifa = remiseria.buscarImporte(valor);
            if(unaTarifa != null)
            {
                kmsRec.setKmsEnUltimaViajeRealizado(unaTarifa.getCodigoTarifa());
                double totalKilometrajeVuelta = (Double.parseDouble(formateador.format(Double.valueOf(String.valueOf(kmsRec.getKilometroFinal()-kmsRec.getKilometrosInicial())))))-Double.parseDouble(formateador.format(Double.valueOf(String.valueOf(unaTarifa.getCodigoTarifa()))));
                double to = Double.parseDouble(formateador.format(totalKilometrajeVuelta));
                kmsRec.setDiferencia(to);
                Remiseria.persistencia.update(this);
            }
            else
            {
                double va = Double.parseDouble(formateador.format((unViaje.getTarifa()-remiseria.getUnaBajadaBandera().getValorBajadaBandera())/remiseria.getUnCierreKilometraje().getValor()));
                kmsRec.setKmsEnUltimaViajeRealizado(va);
                double totalKilometrajeVuelta = (Double.parseDouble(formateador.format(Double.valueOf(String.valueOf(kmsRec.getKilometroFinal()-kmsRec.getKilometrosInicial())))))-Double.parseDouble(formateador.format(Double.valueOf(String.valueOf(va))));
                kmsRec.setDiferencia(totalKilometrajeVuelta);
                //JOptionPane.showMessageDialog(null,"El viaje buscado no tiene un código de tárifa asignado,\n debido que no se encuentra en la lista de las tarifas registradas \n, igual se registrara el Kilómetro final actual ", null, JOptionPane.WARNING_MESSAGE);
                Remiseria.persistencia.update(this);
            }
           
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
        }
         return kmsRec;
    }    
    
    
     public KilometrosRecorridos buscarUltimoKilometrajeRecorrido(Movil unMovil)
    {
        try{
            LinkedList<KilometrosRecorridos> m = new LinkedList<>();
            Collection marcados1 = unMovil.getKilometrosRecorridos().values();
            KilometrosRecorridos marcaTar = null;
            KilometrosRecorridos marcaTar2 = null;
            if(marcados1 != null && marcados1.size()>0)
            {
                Iterator iter = marcados1.iterator();
                while (iter.hasNext())
                {
                    marcaTar = (KilometrosRecorridos) iter.next();
                    m.add(marcaTar);    
                }
                Collections.sort(m, new Comparator<KilometrosRecorridos>() {
                @Override
                public int compare(KilometrosRecorridos p1, KilometrosRecorridos p2) {                
                       // return new Long(p1.getFecha().getTime()).compareTo(new Long(p2.getFecha().getTime()));
                         return new Integer(p1.getIdKilometrosRecorridos()).compareTo(new Integer(p2.getIdKilometrosRecorridos()));
                }
        });                      
                marcaTar2 = (KilometrosRecorridos) m.getLast();
                System.out.println(marcaTar2);
                return marcaTar2;            
            }
            return marcaTar2;
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;       
    }
     
     public KilometrosRecorridos buscarUltimoKilometrajeRecorrido2(Movil unMovil, Utilidad utilidades)
    {
        List m = new LinkedList();
        Collection marcados1 = unMovil.getKilometrosRecorridos().values();
        KilometrosRecorridos marcaTar = null;
        KilometrosRecorridos marcaTar2 = null;
        Iterator iter = marcados1.iterator();
        while (iter.hasNext())
        {
            marcaTar = (KilometrosRecorridos) iter.next();
            if(utilidades.getFecha(marcaTar.getFecha()).equals(utilidades.getFecha(utilidades.getFechaHoraActual())))
            {
                m.add(marcaTar);
            }
        }
        
        marcaTar2 = (KilometrosRecorridos) m.get(marcados1.size()-1);
        return marcaTar2;
    }     
     public void modificarkmsIn (int valor ,Movil unMovil)
     {
         unMovil.setKilometraje(valor);
         Remiseria.persistencia.update(this);
     }
     
     
     public void agregarKilometrosEnServicio (int kilometroInicialServicio,Date fechaHoraInicio, Chofer unChofer)
     {
         KilometrosEnServicio kmsEnServ = null;
         kmsEnServ = new KilometrosEnServicio(kilometroInicialServicio,0,fechaHoraInicio,null,unChofer);
         this.kilometrosEnServicios.put(kmsEnServ.getIdKilometrosEnServicio(), kmsEnServ);
         Remiseria.persistencia.update(this);
     }
     
     public void modificarKilometrosEnServicio ( KilometrosEnServicio kmsEnServ,int KilometroFinalServicio, Date fechaHoraFinal)
     {
          kmsEnServ.setKilometroFinalServicio(KilometroFinalServicio);
          kmsEnServ.setFechaHoraFinal(fechaHoraFinal);
          kmsEnServ.setTotalKms(KilometroFinalServicio-kmsEnServ.getKilometroInicialServicio());
          Remiseria.persistencia.update(this);
     }
     
     public void ponerEnCeroKilometraje (Movil unMovil)
    {
        unMovil.setKilometraje(0);
        Remiseria.persistencia.update(this);
    }
     
     public KilometrosEnServicio buscarUltimoKilometrajeEnServicio(Movil unMovil)
    {
        try
        {
            LinkedList<KilometrosEnServicio> kmsRecorridoss = new LinkedList<>();
            Collection kms = unMovil.getKilometrosEnServicios().values();
            KilometrosEnServicio aux = null;
            KilometrosEnServicio aux2 = null;
            if(kms != null && kms.size()>0)
            {
                Iterator iter = kms.iterator();
                while (iter.hasNext())
                {
                     aux = (KilometrosEnServicio) iter.next();
                     kmsRecorridoss.add(aux);
                }
                Collections.sort(kmsRecorridoss, new Comparator<KilometrosEnServicio>() {
                        @Override
                        public int compare(KilometrosEnServicio p1, KilometrosEnServicio p2) {                
                                //return new Long(p1.getFechaHoraInicio().getTime()).compareTo(new Long(p2.getFechaHoraInicio().getTime()));
                                return new Integer(p1.getIdKilometrosEnServicio()).compareTo(new Integer(p2.getIdKilometrosEnServicio()));
                        }
                });      
                if(kmsRecorridoss.size()>0)
                {
                    aux2 = (KilometrosEnServicio) kmsRecorridoss.getLast();
                }
                return aux2;
            }
    //        if(kmsRecorridoss.size()>0)
    //        {
    //            aux2 = (KilometrosEnServicio) kmsRecorridoss.get(kmsRecorridoss.size()-1);
    //        }
            return aux2;
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex, null, JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }     
}
