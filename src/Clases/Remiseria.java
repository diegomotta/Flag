/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import Interface.JInternalFrameViaje;
import Interface.VentanaLogueo;
import Interface.VentanaPrincipal;
import Log_Auditoria.Auditoria_Operacional;
import java.util.*;
import Persistencia.Persistencia;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;
import javax.swing.JOptionPane;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.jdesktop.swingx.JXDatePicker;
/**
 *
 * @author Owner
 */
@Audited
@Entity
public class Remiseria implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idRemiseria;
    private String nombre;
    @OneToOne
    public Foto logo = null;    
    @OneToOne
    private Domicilio unDomicilio;
    @OneToOne
    private Telefono unTelefono;
    @OneToMany
    private Map<Integer,Movil> moviles;
    @OneToMany
    private Map<Integer,Chofer> choferes;
    @OneToMany    
    private Map<Integer,Viaje> viajes;
    @OneToMany
    private Map<Integer,Tarifa> tarifas;
    @OneToMany
    private Map<Integer,Zona> zonas;
    @OneToMany
    private Map<Integer,Reserva> reservas;
    @ManyToOne
    private Caja cajaPrincipal;
    @OneToMany
    private Map<Integer,Operario> operarios;
    @OneToMany
    private Map<Integer,Cargo> cargos;    
    @OneToMany
    private Map<Integer,Cliente> clientes;
    @OneToMany
    private Map<Integer,Marca> marcas;
    @OneToMany
    private Map<Integer,Motor> motores;
    @OneToMany
    private Map<Integer,Color> colores;
    @OneToMany
    private Map<Integer,TipoUtilidad> tiposUtilidades;
    @OneToMany
    private Map<Integer,Rodado> rodados;
    @OneToMany
    private Map<Integer,TipoDni> tiposDnis ;
    @OneToMany    
    private Map<Integer,Sexo> sexos;
    @OneToMany
    private Map<Integer,EstadoCivil> estadosCiviles;
    @OneToMany
    private Map<Integer,Nacionalidad> nacionalidades;
    @OneToMany
    private Map<Integer,TipoTelefono> tiposTelefonos;
    @OneToMany
    private Map<Integer,Pais> paises;
    @OneToMany
    private Map<Integer,Persona> personas;
    @OneToMany
    private Map<Integer,CodPais> codsPaises;
    @OneToMany
    private Map<Integer,ChoferPorMovil> choferesPorMoviles;
    @OneToMany
    private Map<Integer,MovilPorZona> movilesPorZonas;
    @OneToMany
    private Map<Integer,UtilidadViaje> utilidadViajes;
    @OneToMany
    private Map<Integer,Rol> roles;
    @OneToMany
    private Map<Integer,Rendicion> rendiciones;
    @OneToMany
    private Map<Integer,Liquidacion> liquidaciones;
    @OneToMany
    private Map<Integer,MarcarTarjeta> marcados;
    @OneToOne
    private Configuracion unaConfiguracion;    
    @OneToOne
    private PagoRadio unPagoRadio;        
    @OneToOne
    private Edad unaEdad;
    @OneToOne
    private CierreKilometraje unCierreKilometraje;
    @OneToOne
    private BajadaBandera unaBajadaBandera;
    @OneToOne
    private PorcentajeChofer unPorcentajeChofer;
    @OneToOne
    private PrecioAlquiler unPrecioAlquiler;
    @OneToOne
    private PagoOperario unPagoOperario;
    @OneToOne
    private TiempoEspera unTiempoEspera;    
    @OneToOne
    private TiempoEspera2 unTiempoEspera2;        
    @OneToMany
    @NotAudited
    private List<Auditoria_Operacional> audiciones;
    @OneToOne
    private Maestro unUsuarioMaestro;
    public  static Persistencia persistencia = new Persistencia();
    @Transient
    private Utilidad utilidades = new Utilidad();
    @Transient
    private JInternalFrameViaje unaVentana;
    @Transient
    private VentanaPrincipal ventPrincipal;
    @Transient
    private int prioridad = 1;
    @Transient
    private Movil movilprioridad = null;
    public Remiseria() {

    }

    public Remiseria(String nombre,Domicilio unDomicilio, Telefono unTelefono, Foto unaFoto) throws Exception {
        this();
        this.unaVentana = null;
        this.ventPrincipal = null;
        this.nombre = nombre;
        this.unDomicilio = unDomicilio;
        this.unTelefono = unTelefono;
        this.logo = unaFoto;
        this.moviles = new HashMap();
        this.choferes = new HashMap();
        this.viajes = new HashMap();
        this.tarifas = new TreeMap();
        this.zonas = new HashMap();
        this.reservas = new HashMap();
        this.operarios = new HashMap();
        this.clientes = new HashMap();
        this.marcas = new HashMap();
        this.motores = new HashMap();
        this.colores = new HashMap();
        this.tiposUtilidades = new HashMap();
        this.rodados = new HashMap();
        this.tiposDnis =  new HashMap();
        this.sexos =  new HashMap();
        this.estadosCiviles =  new HashMap();
        this.nacionalidades =  new HashMap();
        this.tiposTelefonos =  new HashMap();
        this.paises = new HashMap();
        this.personas = new HashMap();
        this.codsPaises = new HashMap();
        this.cajaPrincipal = new Caja("Caja Principal");
        this.choferesPorMoviles = new HashMap();
        this.movilesPorZonas = new HashMap();
        this.roles = new HashMap();
        this.utilidadViajes = new HashMap();
        this.unaConfiguracion = new Configuracion("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        this.unaEdad = new Edad(18,18);
        this.unCierreKilometraje = new CierreKilometraje(0);
        this.unPorcentajeChofer = new PorcentajeChofer (0);
        this.unPrecioAlquiler = new PrecioAlquiler(0);
        this.unaBajadaBandera = new BajadaBandera(0);
        this.unPagoRadio = new PagoRadio(0);
        this.unPagoOperario = new PagoOperario("por día",0);
        this.unTiempoEspera = new TiempoEspera(0);
        this.unTiempoEspera2 = new TiempoEspera2(0);
        this.unUsuarioMaestro = new Maestro("Administrador", "21232f297a57a5a743894a0e4a801fc3", "Diego", "Motta");
        this.rendiciones = new HashMap();
        this.liquidaciones = new HashMap();
        this.marcados = new HashMap();
        this.cargos = new HashMap();
        this.audiciones = new LinkedList<>();
        
        if(this.getNombre()!= null)
        {
            this.cargarDatos();
        }
        this.persistencia.insert(this);

    }

    //<editor-fold defaultstate="collapsed" desc=" GETTERS AND SETTERS ">   
        
    public TiempoEspera getUnTiempoEspera() {
        return unTiempoEspera;
    }

    public void setUnTiempoEspera(TiempoEspera unTiempoEspera) {
        this.unTiempoEspera = unTiempoEspera;
    }

    public TiempoEspera2 getUnTiempoEspera2() {
        return unTiempoEspera2;
    }

    public void setUnTiempoEspera2(TiempoEspera2 unTiempoEspera2) {
        this.unTiempoEspera2 = unTiempoEspera2;
    }
        
    public PagoRadio getUnPagoRadio() {
        return unPagoRadio;
    }

    public void setUnPagoRadio(PagoRadio unPagoRadio) {
        this.unPagoRadio = unPagoRadio;
    }
        
    public List<Auditoria_Operacional> getAudiciones() {
        return audiciones;
    }

    public void setAudiciones(List<Auditoria_Operacional> audiciones) {
        this.audiciones = audiciones;
    }
        
    public Map<Integer, Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(Map<Integer, Cargo> cargos) {
        this.cargos = cargos;
    }
        
    public VentanaPrincipal getVentPrincipal() {
        return ventPrincipal;
    }

    public void setVentPrincipal(VentanaPrincipal ventPrincipal) {
        this.ventPrincipal = ventPrincipal;
    }
        
    public BajadaBandera getUnaBajadaBandera() {
        return unaBajadaBandera;
    }

    public void setUnaBajadaBandera(BajadaBandera unaBajadaBandera) {
        this.unaBajadaBandera = unaBajadaBandera;
    }
        
    public JInternalFrameViaje getUnaVentana() {
        return unaVentana;
    }

    public void setUnaVentana(JInternalFrameViaje unaVentana) {
        this.unaVentana = unaVentana;
    }
        
    public Map<Integer, MarcarTarjeta> getMarcados() {
        return marcados;
    }

    public void setMarcados(Map<Integer, MarcarTarjeta> marcados) {
        this.marcados = marcados;
    }
        
    public Map<Integer, Liquidacion> getLiquidaciones() {
        return liquidaciones;
    }

    public void setLiquidaciones(Map<Integer, Liquidacion> liquidaciones) {
        this.liquidaciones = liquidaciones;
    }
        
    public PagoOperario getUnPagoOperario() {
        return unPagoOperario;
    }

    public void setUnPagoOperario(PagoOperario unPagoOperario) {
        this.unPagoOperario = unPagoOperario;
    }
        
    public CierreKilometraje getUnCierreKilometraje() {
        return unCierreKilometraje;
    }

    public void setUnCierreKilometraje(CierreKilometraje unCierreKilometraje) {
        this.unCierreKilometraje = unCierreKilometraje;
    }

    public PorcentajeChofer getUnPorcentajeChofer() {
        return unPorcentajeChofer;
    }

    public void setUnPorcentajeChofer(PorcentajeChofer unPorcentajeChofer) {
        this.unPorcentajeChofer = unPorcentajeChofer;
    }

    public PrecioAlquiler getUnPrecioAlquiler() {
        return unPrecioAlquiler;
    }

    public void setUnPrecioAlquiler(PrecioAlquiler unPrecioAlquiler) {
        this.unPrecioAlquiler = unPrecioAlquiler;
    }
        
    public Map<Integer, UtilidadViaje> getUtilidadViajes() {
        return utilidadViajes;
    }

    public void setUtilidadViajes(Map<Integer, UtilidadViaje> utilidadViajes) {
        this.utilidadViajes = utilidadViajes;
    }

    public Domicilio getUnDomicilio() {
        return unDomicilio;
    }

    public void setUnDomicilio(Domicilio unDomicilio) {
        this.unDomicilio = unDomicilio;
    }

    public Telefono getUnTelefono() {
        return unTelefono;
    }

    public void setUnTelefono(Telefono unTelefono) {
        this.unTelefono = unTelefono;
    }
          
    public Maestro getUnUsuarioMaestro() {
        return unUsuarioMaestro;
    }

    public void setUnUsuarioMaestro(Maestro unUsuarioMaestro) {
        this.unUsuarioMaestro = unUsuarioMaestro;
    }
    
    public Configuracion getUnaConfiguracion() {
        return unaConfiguracion;
    }

    public void setUnaConfiguracion(Configuracion unaConfiguracion) {
        this.unaConfiguracion = unaConfiguracion;
    }
    
    public Caja getCajaPrincipal() {
        return cajaPrincipal;
    }

    public void setCajaPrincipal(Caja cajaPrincipal) {
        this.cajaPrincipal = cajaPrincipal;
    }

    public Map<Integer, Chofer> getChoferes() {
        return choferes;
    }

    public void setChoferes(Map<Integer, Chofer> choferes) {
        this.choferes = choferes;
    }

    public Map<Integer, ChoferPorMovil> getChoferesPorMoviles() {
        return choferesPorMoviles;
    }

    public void setChoferesPorMoviles(Map<Integer, ChoferPorMovil> choferesPorMoviles) {
        this.choferesPorMoviles = choferesPorMoviles;
    }

    public Map<Integer, Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Map<Integer, Cliente> clientes) {
        this.clientes = clientes;
    }

    public Map<Integer, CodPais> getCodsPaises() {
        return codsPaises;
    }

    public void setCodsPaises(Map<Integer, CodPais> codsPaises) {
        this.codsPaises = codsPaises;
    }

    public Map<Integer, Color> getColores() {
        return colores;
    }

    public void setColores(Map<Integer, Color> colores) {
        this.colores = colores;
    }

    public Map<Integer, EstadoCivil> getEstadosCiviles() {
        return estadosCiviles;
    }

    public void setEstadosCiviles(Map<Integer, EstadoCivil> estadosCiviles) {
        this.estadosCiviles = estadosCiviles;
    }

    public int getIdRemiseria() {
        return idRemiseria;
    }

    public void setIdRemiseria(int idRemiseria) {
        this.idRemiseria = idRemiseria;
    }

    public Map<Integer, Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(Map<Integer, Marca> marcas) {
        this.marcas = marcas;
    }

    public Map<Integer, Motor> getMotores() {
        return motores;
    }

    public void setMotores(Map<Integer, Motor> motores) {
        this.motores = motores;
    }

    public Map<Integer, Movil> getMoviles() {
        return moviles;
    }

    public void setMoviles(Map<Integer, Movil> moviles) {
        this.moviles = moviles;
    }

    public Map<Integer, MovilPorZona> getMovilesPorZonas() {
        return movilesPorZonas;
    }

    public void setMovilesPorZonas(Map<Integer, MovilPorZona> movilesPorZonas) {
        this.movilesPorZonas = movilesPorZonas;
    }

    public Map<Integer, Nacionalidad> getNacionalidades() {
        return nacionalidades;
    }

    public void setNacionalidades(Map<Integer, Nacionalidad> nacionalidades) {
        this.nacionalidades = nacionalidades;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<Integer, Operario> getOperarios() {
        return operarios;
    }

    public void setOperarios(Map<Integer, Operario> operarios) {
        this.operarios = operarios;
    }

    public Map<Integer, Pais> getPaises() {
        return paises;
    }

    public void setPaises(Map<Integer, Pais> paises) {
        this.paises = paises;
    }

    public static Persistencia getPersistencia() {
        return persistencia;
    }

    public static void setPersistencia(Persistencia persistencia) {
        Remiseria.persistencia = persistencia;
    }

    public Map<Integer, Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(Map<Integer, Persona> personas) {
        this.personas = personas;
    }

    public Map<Integer, Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Map<Integer, Reserva> reservas) {
        this.reservas = reservas;
    }

    public Map<Integer, Rodado> getRodados() {
        return rodados;
    }

    public void setRodados(Map<Integer, Rodado> rodados) {
        this.rodados = rodados;
    }

    public Map<Integer, Rol> getRoles() {
        return roles;
    }

    public void setRoles(Map<Integer, Rol> roles) {
        this.roles = roles;
    }

    public Map<Integer, Sexo> getSexos() {
        return sexos;
    }

    public void setSexos(Map<Integer, Sexo> sexos) {
        this.sexos = sexos;
    }

    public Map<Integer, Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(Map<Integer, Tarifa> tarifas) {
        this.tarifas = tarifas;
    }

    public Map<Integer, TipoDni> getTiposDnis() {
        return tiposDnis;
    }

    public void setTiposDnis(Map<Integer, TipoDni> tiposDnis) {
        this.tiposDnis = tiposDnis;
    }

    public Map<Integer, TipoTelefono> getTiposTelefonos() {
        return tiposTelefonos;
    }

    public void setTiposTelefonos(Map<Integer, TipoTelefono> tiposTelefonos) {
        this.tiposTelefonos = tiposTelefonos;
    }

    public Map<Integer, TipoUtilidad> getTiposUtilidades() {
        return tiposUtilidades;
    }

    public void setTiposUtilidades(Map<Integer, TipoUtilidad> tiposUtilidades) {
        this.tiposUtilidades = tiposUtilidades;
    }

    public Utilidad getUtilidades() {
        return utilidades;
    }

    public void setUtilidades(Utilidad utilidades) {
        this.utilidades = utilidades;
    }

    public Map<Integer, Viaje> getViajes() {
        return viajes;
    }

    public void setViajes(Map<Integer, Viaje> viajes) {
        this.viajes = viajes;
    }

    public Map<Integer, Zona> getZonas() {
        return zonas;
    }

    public void setZonas(Map<Integer, Zona> zonas) {
        this.zonas = zonas;
    }    
  
    public Map<Integer, Rendicion> getRendiciones ()
    {
        return rendiciones;
    }

    public void setRendiciones(Map<Integer, Rendicion> rendiciones) {
        this.rendiciones = rendiciones;
    }
    
     public Foto getLogo ()
    {
        return logo;
    }

    public void setLogo(Foto logo) {
        this.logo = logo;
    }

    public Edad getUnaEdad ()
    {
        return unaEdad;
    }

    public void setUnaEdad(Edad unaEdad) {
        this.unaEdad = unaEdad;
    }
          
    //</editor-fold>
    
    public void crearAuditoria(String operacion, String detalle, String usuario, String hora) {
        Auditoria_Operacional nuevaAuditoria_Operacional = new Auditoria_Operacional(operacion, detalle, usuario);
        this.audiciones.add(nuevaAuditoria_Operacional);
        this.persistencia.update(this);
    }    
    
    //<editor-fold defaultstate="collapsed" desc="CARGA DE DATOS POR SISTEMA">      
    public void cargarSkin (String skin)
    {
        unaConfiguracion.setSkin(skin);
        this.persistencia.update(this);
    }
    
    public void cargarPorcentajeChofer (double porcentaje)
    {
       
        unPorcentajeChofer.setPorcentajeChofer(porcentaje);
        this.persistencia.update(this);
    }
    
    public void cargarTiempoEspera (int valor)
    {
        
        unTiempoEspera.setValorTiempo(valor*60000);
        this.persistencia.update(this);
    }    
    
    public void cargarTiempoEspera2 (int valor)
    {
        unTiempoEspera2.setValorTiempo(valor*60000);
        this.persistencia.update(this);
    }   
    
    
    public void cargarPagoRadio (double valor)
    {
        unPagoRadio.setValor(valor);
        this.persistencia.update(this);
    }
    
    public void cargarPrecioAlquiler (double precio)
    {
        unPrecioAlquiler.setPrecio(precio);
        this.persistencia.update(this);
    }
    
    public void cargarCierreKilometraje (double cierre)
    {
        unCierreKilometraje.setValor(cierre);
        this.persistencia.update(this);
    }
    
    public void cargarBajadaBandera (double valor)
    {
        unaBajadaBandera.setValorBajadaBandera(valor);
        this.persistencia.update(this);
    }    
    
    public void cargarEdadChofer (int edad)
    {
        unaEdad.setEdadMinimaChofer(edad);
        this.persistencia.update(this);        
    }

    public void cargarEdadOperario (int edad)
    {
        unaEdad.setEdadMinimaOperario(edad);
        this.persistencia.update(this);        
    }  
    
    public void cargarPagoOperario(double precio, String tipoPago)
    {
        unPagoOperario.setPrecio(precio);
        unPagoOperario.setTipoPago(tipoPago);
        this.persistencia.update(this);
    }
    
    public boolean cargarDatosRemiseria(String nombre, Pais pais, Provincia provincia, Ciudad ciudad,String calle,String nroCasa, CodPais unCodPais, CodArea unCodArea, String nroTel, TipoTelefono unTipoTelefono, Foto unaFoto)
    {                      
        Domicilio domicilio = new Domicilio (pais,provincia,ciudad,null,calle,nroCasa,null,null);
        Telefono telefono = new Telefono(nroTel,unCodArea,unCodPais,unTipoTelefono);
        this.setNombre(nombre);
        this.setLogo(unaFoto);
        this.setUnDomicilio(domicilio);
        this.setUnTelefono(telefono);
        this.persistencia.update(this);
        return true;
    }
    
    public static byte[] toArrayByte(String url) {
        File file = new File(url);
        byte[] bFile = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            //convert file into array of bytes
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bFile;
    }
          //</editor-fold>  
    //<editor-fold defaultstate="collapsed" desc="METODOS PARA CARGOS">    
    public Cargo agregarCargo(String tipoPago, String tipoCargo, Double sueldo, int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Date fechaNacimiento,Telefono unTelefono,Date fechaPago){
            Cargo unCargo = null;
            unCargo = new Cargo(tipoPago,tipoCargo,sueldo, dni, unTipoDni, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio,  nombre,  apellido,  fechaNacimiento, unTelefono,fechaPago);
            this.cargos.put(dni, unCargo);
            this.persistencia.update(this);     
        return unCargo;
    }
    
    public boolean modificarCargo(Cargo unCargo,String tipoPago, String tipoCargo, Double sueldo, int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Date fechaNacimiento,Telefono unTelefono,Date fechaPago){       
            unCargo.setDni(dni);
            unCargo.setUnTipoDni(unTipoDni);
            unCargo.setUnSexo(unSexo);
            unCargo.setUnEstadoCivil(unEstadoCivil);
            unCargo.setUnaNacionalidad(unaNacionalidad);
            unCargo.setUnDomicilio(unDomicilio);
            unCargo.setNombre(nombre);
            unCargo.setApellido(apellido);
            unCargo.setUnTelefono(unTelefono);
            unCargo.setFechaNacimiento(fechaNacimiento);
            unCargo.setTipoCargo(tipoCargo);
            unCargo.setTipoCargo(tipoCargo);
            unCargo.setSueldo(sueldo);
            unCargo.setFechaPago(fechaPago);
            this.persistencia.update(this);
            return true;
    } 
    
    public void eliminarCargo(Cargo unCargo)
    {
        this.cargos.remove(unCargo.getDni());
        this.persistencia.insert(unCargo);
    }
    
    public boolean nroCargoLibre(int key){
       boolean variable = false;
       Collection numeroCargo = this.getCargos().values();
       Cargo aux = null;
       Iterator iter = numeroCargo.iterator();
        while (iter.hasNext()){
           aux = (Cargo) iter.next();
           if (aux.getDni() == key){ 
                variable = true;
            }
        }
        return variable;
    }    
    
    public List buscarCargos(){
        List cargosActivos = new LinkedList();
        Collection cargoos = this.getCargos().values();
        Cargo aux = null;
        Iterator iter = cargoos.iterator();
        while (iter.hasNext()){
            aux = (Cargo) iter.next();
            cargosActivos.add(aux);

        }
        return cargosActivos;
    }
    
    public Cargo buscarCargoPersona(int dni){
       
       Collection numCargos = this.getCargos().values();
       Cargo aux = null;
       Iterator iter = numCargos.iterator();
        while (iter.hasNext()){
           aux = (Cargo) iter.next();
           if (aux.getDni() == dni)
           { 
                 return aux;
           }
        }
        return null;
    }    
        
       //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="METODOS PARA RENDICIONES">    
    
    public Rendicion agregarRendicion(double totalRendicion, Date fechaDesde, Date fechaHasta, int totalKms, ChoferPorMovil unChoferPorMovil)
    {
        unChoferPorMovil.setEstadoRendicion("rendido");
        Rendicion rendicion = null;
        this.marcarRendido(unChoferPorMovil.getViajes().values(),fechaDesde,fechaHasta);
        System.out.println("total de Viajes " + unChoferPorMovil.getViajes().size());
        rendicion = new Rendicion (totalRendicion,utilidades.getFechaHoraActual(),unChoferPorMovil.getUnMovil(),unChoferPorMovil.getUnChofer(), totalKms);
        
        this.agregarViajesARendicion(rendicion,fechaDesde,fechaHasta, unChoferPorMovil);
        //(unChoferPorMovil.getUnMovil().setKilometrosTrabajados(0);
        unChoferPorMovil.getUnMovil().setKilometraje(unChoferPorMovil.getUnMovil().getKilometrajeActual());
        //unChoferPorMovil.getUnMovil().setKilometrajeActual(0);
        this.rendiciones.put(rendicion.getIdRendicion(), rendicion);
        unChoferPorMovil.getUnMovil().setKilometrosTrabajados(0);
        this.persistencia.update(this);
        return rendicion;
    
    }
        
    public void marcarRendido(Collection <Viaje> viajesChoferPorMovil, Date fechaDesde, Date DateFechaHasta)
    {
        try
        {         
            Collection rend = viajesChoferPorMovil;
            Viaje aux = null;
            Viaje aux2 = null;
            Iterator iter = rend.iterator();

                while (iter.hasNext())
                {          
                    aux = (Viaje) iter.next();
                    aux2 = aux.getUnMovil().buscarViaje(aux);
                    if(aux.getEstadoRendicion().equals("sin rendir"))
                    {
//                        if((aux.getFechaHora().getTime() ==fechaDesde.getTime()) || (aux.getFechaHora().getTime() > fechaDesde.getTime()) ) 
//                       {
//                            if ((aux.getFechaHora().getTime() == DateFechaHasta.getTime()) || (aux.getFechaHora().getTime() < DateFechaHasta.getTime())) 
//                            {
                                    aux.setEstadoRendicion("rendido");
                                    aux2.setEstadoRendicion("rendido");
//                            }
//                        }
                    }
                }                                        
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e," ",JOptionPane.ERROR_MESSAGE);
        }
    }
   
    public void agregarViajesARendicion(Rendicion unaRendicion, Date fechaDesde, Date DateFechaHasta, ChoferPorMovil unChoferPorMovil)
    {
        try
        {         
            Collection rend = unChoferPorMovil.getViajes().values();
            Viaje aux = null;
            Iterator iter = rend.iterator();
                while (iter.hasNext())
                {          
                    aux = (Viaje) iter.next();
                    if(aux.getEstadoRendicion().equals("rendido"))
                    {
//                        if((aux.getFechaHora().getTime() ==fechaDesde.getTime()) || (aux.getFechaHora().getTime() > fechaDesde.getTime()) ) 
//                        {
//                                if ((aux.getFechaHora().getTime() == DateFechaHasta.getTime()) || (aux.getFechaHora().getTime() < DateFechaHasta.getTime())) 
//                                {
                                       unaRendicion.agregarViajes(aux);
//                                }
//                        }
                    }
                }                                        
            }       
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e," ",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean buscarRendicionesImpagas(Movil unMovil, Chofer unChofer)
    {
        if(unMovil.getUnChofer() == unChofer)
        {
            Collection via = unMovil.getViajes().values();
            Viaje aux = null;
            Iterator iter = via.iterator();
            while (iter.hasNext())
            {
                aux = (Viaje) iter.next();
                if(aux.getUnChofer() == unChofer)
                {
                    if(aux.getEstadoRendicion().equals("sin rendir") )
                    {   
                        return true;
                    }
                }
            }
        } 
        return false;
    }
    
       public String totalRendicionSegunFiltro (List lista)
       {
           Double total = 0.0;
           Rendicion rend = null;
           Iterator iter = lista.iterator();
           while (iter.hasNext())
           {
               rend = (Rendicion) iter.next();
               total = total + rend.getTotalRendicion();
           }
           return String.valueOf(total);
       }    
    
       //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA CHOFERxMovil">
    public void configurarEstado (Movil unMovil,Chofer chofer,Zona zona,String estado)
    {
        unMovil.setUnChofer(chofer);
        unMovil.setUnaZona(zona);
        unMovil.setEstado(estado);
        unMovil.movilDisponibleI(zona,chofer);
        this.persistencia.update(this);
    }

        
    
    public void eliminarChoferPorMovil(Chofer unChofer)
    {
        if(unChofer != null)
        {
            List ChoferPorMovilActivos =  this.buscarChoferPorMovil(unChofer);
             Iterator iter = ChoferPorMovilActivos.iterator();
             ChoferPorMovil aux = null;
             while (iter.hasNext()){
                 aux = (ChoferPorMovil) iter.next();
                 this.choferesPorMoviles.remove(aux.getIdChoferPorMovil());
                 this.persistencia.update(this);
                 this.persistencia.delete(aux);
             }
        }
    
    }
    
    public ChoferPorMovil agregarChoferPorMovil(Movil unMovil, Chofer unChofer, String horaInicio, Date fechaInicio){
            ChoferPorMovil unChoferPorMovil = null;
            unChoferPorMovil = new ChoferPorMovil(unMovil,unChofer,horaInicio,fechaInicio," "," ");
            this.choferesPorMoviles.put(unChoferPorMovil.getIdChoferPorMovil(), unChoferPorMovil);
            this.persistencia.update(this);
            return unChoferPorMovil;
    }
    //Todos los Choferes Por Moviles
    public List buscarTodoChoferPorMovil()
    {
        List ChoferPorMovilActivos = new LinkedList();
        Collection CxM = this.getChoferesPorMoviles().values();
        ChoferPorMovil aux = null;
        Iterator iter = CxM.iterator();
        while (iter.hasNext()){
            aux = (ChoferPorMovil) iter.next();
            ChoferPorMovilActivos.add(aux);
            
        }
        return ChoferPorMovilActivos;
    }
        
    //Por chofer
        public List buscarChoferPorMovil(Chofer unChofer)
    {
        List ChoferPorMovilActivos = new LinkedList();
        Collection CxM = this.getChoferesPorMoviles().values();
        ChoferPorMovil aux = null;
        Iterator iter = CxM.iterator();
        while (iter.hasNext()){
            aux = (ChoferPorMovil) iter.next();
            if( aux.getUnChofer().getDni() ==unChofer.getDni())
            {
                 ChoferPorMovilActivos.add(aux);
            }
           
            
        }
        return ChoferPorMovilActivos;
    }
        
    //Por movil
        
    public List buscarChoferPorMovil(Movil unMovil)
    {
        List ChoferPorMovilActivos = new LinkedList();
        Collection CxM = this.getChoferesPorMoviles().values();
        ChoferPorMovil aux = null;
        Iterator iter = CxM.iterator();
        while (iter.hasNext()){
            aux = (ChoferPorMovil) iter.next();
            if( aux.getUnMovil().getPatente().equals( unMovil.getPatente())               )
            {
                 ChoferPorMovilActivos.add(aux);
            }
           
            
        }
        return ChoferPorMovilActivos;
    }
    
    public ChoferPorMovil buscarChoferPorMovilUltimo(Movil unMovil)
    {
        List ChoferPorMovilActivos = new LinkedList();
        Collection CxM = this.getChoferesPorMoviles().values();
        ChoferPorMovil aux = null;
        ChoferPorMovil aux2 = null;
        Iterator iter = CxM.iterator();
        while (iter.hasNext()){
            aux = (ChoferPorMovil) iter.next();
            if( aux.getUnMovil().getPatente().equals( unMovil.getPatente())               )
            {
                 ChoferPorMovilActivos.add(aux);
            }
           
            
        }
        aux2 = (ChoferPorMovil) ChoferPorMovilActivos.get(ChoferPorMovilActivos.size()-1);
        return aux2;
    }
    
      public MarcarTarjeta buscarTarjetas()
    {
        List m = new LinkedList();
        Collection marcados1 = this.getMarcados().values();
        MarcarTarjeta marcaTar = null;
        MarcarTarjeta marcaTar2 = null;
        Iterator iter = marcados1.iterator();
        while (iter.hasNext())
        {
            marcaTar = (MarcarTarjeta) iter.next();
            m.add(marcaTar);    
        }
        Collections.sort(m, new Comparator<MarcarTarjeta>() {
        @Override
        public int compare(MarcarTarjeta p1, MarcarTarjeta p2) {                
                //return new Long(p1.getFechaInicio().getTime()).compareTo(new Long(p2.getFechaInicio().getTime()));
                return new Integer(p1.getIdMarcarTarjeta()).compareTo(new Integer(p2.getIdMarcarTarjeta()));
        }
        });        
        marcaTar2 = (MarcarTarjeta) m.get(marcados1.size()-1);
        return marcaTar2;
    }
    public void modificarChoferPorMovil( Movil unMovil){
            ChoferPorMovil unChoferPorMovil = this.buscarChoferPorMovilUltimo(unMovil);
            unChoferPorMovil.setFechaFin(utilidades.getFechaActual());
            unChoferPorMovil.setHoraFin(utilidades.getHoraActual());
            unChoferPorMovil.setFechaHora(utilidades.getFechaHoraActual());
            KilometrosEnServicio kmsEnServ = unMovil.buscarUltimoKilometrajeEnServicio(unMovil);
            unChoferPorMovil.setKms(kmsEnServ.getTotalKms());
            unChoferPorMovil.getUnMovil().setKilometrosTrabajados(0);
            this.persistencia.update(this);
    }
    
    public void modificarChoferPorMovil2 (ChoferPorMovil unChoferPorMovil, Date fechaInicio, String horaInicio)
    {
            unChoferPorMovil.setFechaInicio(fechaInicio);
            unChoferPorMovil.setHoraInicio(horaInicio);
            this.persistencia.update(this); 
    }

     //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA MOVILxZona">           
    
     public MovilPorZona agregarMovilPorZona(Movil unMovil, Zona unaZona, String horaInicio, String fechaInicio){
            MovilPorZona unMovilPorZona = null;
            unMovilPorZona = new MovilPorZona(unMovil,unaZona,horaInicio,fechaInicio," "," ");
            this.movilesPorZonas.put(unMovilPorZona.getIdMovilPorZona(), unMovilPorZona);
            this.persistencia.update(this);
            return unMovilPorZona;
    }
    
    public List buscarTodoMovilPorZona()
    {
        List movilPorZonaActivos = new LinkedList();
        Collection MxZ = this.getMovilesPorZonas().values();
        MovilPorZona aux = null;
        Iterator iter = MxZ.iterator();
        while (iter.hasNext()){
            aux = (MovilPorZona) iter.next();
            movilPorZonaActivos.add(aux);
            
        }
        return movilPorZonaActivos;
    }
        
    //Por Zona
        public List buscarMovilPorZona(Zona unaZona)
    {
        List movilPorZonaActivos = new LinkedList();
        Collection MxZ = this.getMovilesPorZonas().values();
        MovilPorZona aux = null;
        Iterator iter = MxZ.iterator();
        while (iter.hasNext()){
            aux = (MovilPorZona) iter.next();
            if( aux.getUnaZona().getNombreZona().equals(unaZona.getNombreZona()))
            {
                 movilPorZonaActivos.add(aux);
            }
           
            
        }
        return movilPorZonaActivos;
    }
        
    //Por movil
        
    public List buscarMovilPorZona(Movil unMovil)
    {
       List movilPorZonaActivos = new LinkedList();
        Collection MxZ = this.getMovilesPorZonas().values();
        MovilPorZona aux = null;
        Iterator iter = MxZ.iterator();
        while (iter.hasNext()){
            aux = (MovilPorZona) iter.next();
            if( aux.getUnMovil().getPatente().equals(unMovil.getPatente()))
            {
                 movilPorZonaActivos.add(aux);
            }
           
            
        }
        return movilPorZonaActivos;
    }
             
    public MovilPorZona buscarMovilPorZonaUltimo(Movil unMovil)
    {
       List movilPorZonaActivos = new LinkedList();
        Collection MxZ = this.getMovilesPorZonas().values();
        MovilPorZona aux = null;
        MovilPorZona aux2 = null;
        Iterator iter = MxZ.iterator();
        while (iter.hasNext()){
            aux = (MovilPorZona) iter.next();
            if( aux.getUnMovil().getPatente().equals(unMovil.getPatente()))
            {
                 movilPorZonaActivos.add(aux);
            }
           
            
        }
        aux2 = (MovilPorZona) movilPorZonaActivos.get(movilPorZonaActivos.size()-1);
        return aux2;
    }         
             
    
    public void modificarMovilPorZona (Movil unMovil)
    {
        MovilPorZona unMovilPorZona = this.buscarMovilPorZonaUltimo(unMovil);
        unMovilPorZona.setFechaFin(utilidades.getFechaActual());
         unMovilPorZona.setHoraFin(utilidades.getHoraActual());
         this.persistencia.update(this);
    }
    
      
             
   //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA OPERARIOS">
    
    public boolean nroOperarioLibre(int key){
       boolean variable = false;
       Collection numeroOperario = this.getOperarios().values();
       Operario aux = null;
       Iterator iter = numeroOperario.iterator();
        while (iter.hasNext()){
           aux = (Operario) iter.next();
           if (aux.getDni() == key){ 
                variable = true;
            }
        }
        return variable;
    }
    
    public Operario buscarOperario(int dni){
       
       Collection numeroOperario = this.getOperarios().values();
       Operario aux = null;
       Iterator iter = numeroOperario.iterator();
        while (iter.hasNext()){
           aux = (Operario) iter.next();
           // if (!aux.getEstado().equals("Eliminado")){ 
                if (aux.getDni() == dni){ 
                        return aux;
          //      }
             }
        }
        return null;
    }
 
    
    public Operario buscarIdOperario(String id){
        Collection users = this.getOperarios().values();
        Operario aux = null;
        Operario retorno = null;
        Iterator iter = users.iterator();
        while (iter.hasNext()){
            aux = (Operario) iter.next();
            if (aux.getId().equals(id)){
                retorno = aux;
            }
        }
        return retorno;
    }
     
    public String MD5 (String clave)
    {
        try {
            MessageDigest md =MessageDigest.getInstance("MD5");
            md.reset();
            md.update(clave.getBytes());
            byte bytes[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) 
            {
                String hex = Integer.toHexString(0xff & bytes[i]);
                if (hex.length() == 1)
                    sb.append('0');
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Remiseria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Operario agregarOperario(String id, String contrasena,int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Date fechaNacimiento, Telefono unTelefono,Rol unRol){
        Operario user = null;
        String encriptado = this.MD5(contrasena);
        user = new Operario(id, encriptado, dni, unTipoDni, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento, unTelefono,unRol);
        this.operarios.put(dni, user);
        this.persistencia.update(this);
        return user;
    }
      
    public Operario loggearse(String id, String contrasena){
        Collection users = this.getOperarios().values();
        Operario aux = null;
        Operario retorno = null;
        Iterator iter = users.iterator();
        while (iter.hasNext()){
            aux = (Operario) iter.next();
            if (aux.getId().equals(id) && aux.getContrasena().equals(contrasena)){
                retorno = aux;
            }
        }
        return retorno;
    }
     
    public Maestro loggearseMaestro(String id,String contraseña)
    {
       Maestro unMaestro = this.getUnUsuarioMaestro();
       if(unMaestro.getUssername().equals(id) && unMaestro.getPassword().equals(contraseña))
       {
           return unMaestro;
       }
       return null;
    }
     

    public void modificarOperario(Operario unOperario, String id, String contrasena,int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Date fechaNacimiento, Telefono unTelefono,Rol unRol){
            unOperario.setDni(dni);
            unOperario.setId(id);
            String encriptado = this.MD5(contrasena);
            unOperario.setContrasena(encriptado);
            unOperario.setNombre(nombre);
            unOperario.setApellido(apellido);
            unOperario.setFechaNacimiento(fechaNacimiento);
            unOperario.setUnDomicilio(unDomicilio);
            unOperario.setUnTelefono(unTelefono);
            unOperario.setUnEstadoCivil(unEstadoCivil);
            unOperario.setUnSexo(unSexo);
            unOperario.setUnTipoDni(unTipoDni);
            unOperario.setUnaNacionalidad(unaNacionalidad);
            unOperario.setUnRol(unRol);
            this.persistencia.update(this);    
    }
      
    public void modificarOperario2(Operario unOperario, String id, String contrasena,int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Date fechaNacimiento, Telefono unTelefono,Rol unRol)
    {
         this.eliminarOperario(unOperario);
         Operario operario = null;
         operario = new Operario(id, contrasena, dni, unTipoDni, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento, unTelefono,unRol);
         this.operarios.put(operario.getDni(), operario);
         this.persistencia.update(this);
    }
    
    public void eliminarOperario(Operario operario){
        this.operarios.remove(operario.getDni());
        //this.persistencia.delete(operario);
        this.persistencia.update(this);
        this.crearAuditoria("DEL", "class Clase.Operario", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
    }
     
    public List buscarOperariosActivos(){
        List operariosActivos = new LinkedList();
        Collection operarios = this.getOperarios().values();
        Operario aux = null;
        Iterator iter = operarios.iterator();
        while (iter.hasNext()){
            aux = (Operario) iter.next();
            operariosActivos.add(aux);
        }
        return operariosActivos;
    }
  
  //</editor-fold>  
    //<editor-fold defaultstate="collapsed" desc=" ROLES "> 
    public boolean existeRol(String nombre) {
            boolean existe = false;
            Collection roless = this.getRoles().values();
            Rol aux = null;
            Iterator iter = roless.iterator();
            while(iter.hasNext()){
                aux = (Rol) iter.next();
                if (aux.getNombreRol().toUpperCase().equals(nombre.toUpperCase())) {
                    existe = true;
                    break;
                }
            }

            return existe;
    }   
    
    public Rol agregarRol(String nombre, Map<String, Permisos> permisos) throws Exception {
        Rol nuevoRol = null;
        if (!existeRol(nombre)) {
            nuevoRol = new Rol(nombre, permisos);
            this.roles.put(nuevoRol.getIdRol(), nuevoRol);
            //this.roles.add(nuevoRol);
            this.persistencia.update(this);
          //  setChanged();
          //  notifyObservers(nuevoRol);
        } else {
            throw new Exception("Este rol ya existe");
        }
        return nuevoRol;
    }
    
    public Rol buscarUnRol(String rol) {
            Collection roless = this.getRoles().values();
            Rol aux = null;
            Iterator iter = roless.iterator();
            while(iter.hasNext()){
                aux = (Rol) iter.next();
                if (aux.getNombreRol().toUpperCase().equals(rol.toUpperCase())) {
                    return aux;
        
                }
            }

            return aux;
    }
    
        public void modificarRol(Rol unRol, String nombre, Map<String, Permisos> permisos) throws Exception {
        if (unRol.getNombreRol().toUpperCase().equals(nombre.toUpperCase())) 
        {
            unRol.setPermisos(permisos);
            this.persistencia.update(this);
            JOptionPane.showMessageDialog(null,"Se ha Modificado el Rol seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);
        }
        else 
        {
            if (!existeRol(nombre)) 
            {
                unRol.setNombreRol(nombre);
                unRol.setPermisos(permisos);
                this.persistencia.update(this);
                JOptionPane.showMessageDialog(null,"Se ha Modificado el Rol seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);

            } 
        }
    }
        
        
       public boolean utilizaRol(Rol unRol) {
        boolean existe = false;
        if (!this.getOperarios().isEmpty()) {
            Collection operarios1 = this.getOperarios().values();
            Operario unUsuario = null;
            Iterator iter = operarios1.iterator();
            while(iter.hasNext()){
                unUsuario = (Operario) iter.next();
                if (unRol.equals(unUsuario.getUnRol())) {
                    existe = true;
                    break;
                }
            }
        }
        return existe;
    }
  
    

    public void eliminarRol(Rol unRol) throws Exception {
        if (utilizaRol(unRol) != true) {
            
            this.roles.remove(unRol.getIdRol());
           
            //this.persistencia.delete(unRol);
            this.persistencia.update(this);
            this.crearAuditoria("DEL", "class Clase.Rol", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        } else {
            JOptionPane.showMessageDialog(null,"Este Rol no puede ser eliminado ya que esta en uso", null, JOptionPane.WARNING_MESSAGE);
        }

    }
   //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA TIPOS DE TELEFONOS ">  
    
    public TipoTelefono agregarTipoTelefono(String nombreTipoTelefono) 
    {
       TipoTelefono unTipoTelefono = null;
       unTipoTelefono = new TipoTelefono(nombreTipoTelefono);
       int numero = unTipoTelefono.getIdTipoTelefono();
       this.tiposTelefonos.put(numero, unTipoTelefono);
       this.persistencia.update(this);
            
        
       return unTipoTelefono;
           
    }
    
    public List buscarTiposTelefonos(){
        List tiposTelefonosActivos = new LinkedList();
        Collection telefonos = this.getTiposTelefonos().values();
        TipoTelefono aux = null;
        Iterator iter = telefonos.iterator();
        while (iter.hasNext()){
            aux = (TipoTelefono) iter.next();
            tiposTelefonosActivos.add(aux);
            
        }
        return tiposTelefonosActivos;
    }
    
    public void modificarTipoTelefono(TipoTelefono unTipoTelefono,String nombreTipoTelefono){
            unTipoTelefono.setNombreTipoTelefono(nombreTipoTelefono);
            this.persistencia.update(this);
    }
    
     public void eliminarTipoTelefono(String nombreTipoTelefono){
         if(!nombreTipoTelefono.equals(this.getUnTelefono().getUnTipoTelefono().getNombreTipoTelefono()))
         {
            TipoTelefono unTipoTelefono = this.buscarTipoTelefono(nombreTipoTelefono);
            this.tiposTelefonos.remove(unTipoTelefono.getIdTipoTelefono());
            //this.persistencia.delete(unTipoTelefono);
            this.crearAuditoria("DEL", "class Clase.TipoTelefono", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
            this.persistencia.update(this);
         }
         else
         {
                JOptionPane.showMessageDialog(null,"El dato esta siendo utilizado en otra instancia, no se puede eliminar", null, JOptionPane.WARNING_MESSAGE);
         }
       
    }
    
        public TipoTelefono buscarTipoTelefono(String nombreTipoTelefono){
        Collection telefonos = this.getTiposTelefonos().values();
        TipoTelefono aux = null;
        TipoTelefono retorno = null;
        Iterator iter = telefonos.iterator();
        while (iter.hasNext()){
            aux = (TipoTelefono) iter.next();
            if (aux.getNombreTipoTelefono().equals(nombreTipoTelefono)){
                retorno = aux;
            }
        }
        return retorno;
    }
 

    
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA PAISES ">  
    
    public Pais agregarPais(String nombrePais) 
    {
       Pais unPais = null;
       unPais = new Pais(nombrePais);
            int numero = unPais.getIdPais();
            this.paises.put(numero, unPais);
            this.persistencia.update(this);
            
        
       return unPais;
           
    }
    
    public List buscarPaises(){
        List paisesActivos = new LinkedList();
        Collection paises = this.getPaises().values();
        Pais aux = null;
        Iterator iter = paises.iterator();
        while (iter.hasNext()){
            aux = (Pais) iter.next();
            paisesActivos.add(aux);      
        }
        return paisesActivos;
    }
    
    public void modificarPais(Pais unPais,String nombrePais){
            unPais.setNombrePais(nombrePais);
            this.persistencia.update(this);
    }
    
     public void eliminarPais(String nombrePais){
         try{
        if(!nombrePais.equals(this.getUnDomicilio().getUnPais().getNombrePais()))
        {
            Pais unPais = this.buscarPais(nombrePais);
            this.paises.remove(unPais.getIdPais());
            //this.persistencia.delete(unPais);
            this.persistencia.update(this);
            this.crearAuditoria("DEL", "class Clase.Pais", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        }
        else
        {
            JOptionPane.showMessageDialog(null,"El dato esta siendo utilizado en otra instancia, no se puede eliminar",null,JOptionPane.WARNING_MESSAGE);
        }
         }
         catch (Exception ex)
         {
             JOptionPane.showMessageDialog(null,"El dato esta siendo utilizado en otra instancia, no se puede eliminar",null,JOptionPane.WARNING_MESSAGE);
         }
       
    }
    
        public Pais buscarPais(String nombrePais){
        Collection paises = this.getPaises().values();
        Pais aux = null;
        Pais retorno = null;
        Iterator iter = paises.iterator();
        while (iter.hasNext()){
            aux = (Pais) iter.next();
            if (aux.getNombrePais().equals(nombrePais)){
                retorno = aux;
            }
        }
        return retorno;
    }
    
    
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA CODIGOS DE PAISES TELEFONOS ">  
    
    public CodPais agregarCodPais(String codigoPais) 
    {
       CodPais unCodPais = null;
       unCodPais = new CodPais(codigoPais);
       int numero = unCodPais.getIdCodPais();
       this.codsPaises.put(numero, unCodPais);
       this.persistencia.update(this);
       return unCodPais;
           
    }
    
    public List buscarCodigosPaises(){
        List codigosPaisesActivos = new LinkedList();
        Collection codigosPaises = this.getCodsPaises().values();
        CodPais aux = null;
        Iterator iter = codigosPaises.iterator();
        while (iter.hasNext()){
            aux = (CodPais) iter.next();
            codigosPaisesActivos.add(aux);
            
        }
        return codigosPaisesActivos;
    }
    
    public void modificarCodigoPais(CodPais unCodPais,String codigoPais){
            unCodPais.setCodPais(codigoPais);
            this.persistencia.update(this);
    }
    
     public void eliminarCodigoPais(String codigoPais){
        if(!codigoPais.equals(this.getUnTelefono().getUnCodPais().getCodPais()))
        {
            CodPais unCodPais = this.buscarCodigoPais(codigoPais);
            this.paises.remove(unCodPais.getIdCodPais());
           // this.persistencia.delete(unCodPais);
            this.persistencia.update(this);
            this.crearAuditoria("DEL", "class Clase.CodPais", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        }
        else
        {
            JOptionPane.showMessageDialog(null,"El dato esta siendo utilizado en otra instancia, no se puede eliminar", null, JOptionPane.WARNING_MESSAGE);
        }
    }
    
        public CodPais buscarCodigoPais(String codigoPais){
        Collection codigosPaises = this.getCodsPaises().values();
        CodPais aux = null;
        CodPais retorno = null;
        Iterator iter = codigosPaises.iterator();
        while (iter.hasNext()){
            aux = (CodPais) iter.next();
            if (aux.getCodPais().equals(codigoPais)){
                retorno = aux;
            }
        }
        return retorno;
    }
    
    
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA COLORES "> 
     
    public Color agregarColor(String nombreColor) 
    {
        Color unColor = null;
        //Color unColor2 = (Color)this.buscarColor(nombreColor);
 
            unColor = new Color(nombreColor);
            int numero = unColor.getIdColor();
            this.colores.put(numero, unColor);
            this.persistencia.update(this);
      
        return unColor;
    
    }
    
    public List buscarColores(){
        List coloresActivos = new LinkedList();
        Collection colores = this.getColores().values();
        Color aux = null;
        Iterator iter = colores.iterator();
        while (iter.hasNext()){
            aux = (Color) iter.next();
            coloresActivos.add(aux);
            
        }
        return coloresActivos;
    }
    
    public void modificarColor(Color unColor,String nombreColor){
            unColor.setNombreColor(nombreColor);
            this.persistencia.update(this);
    }
    
     public void eliminarColor(String nombreColor){
        Color unColor = this.buscarColor(nombreColor);
        this.colores.remove(unColor.getIdColor());
        //this.persistencia.delete(unColor);
        this.persistencia.update(this);
       this.crearAuditoria("DEL", "class Clase.Color", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
    }
    
        public Color buscarColor(String nombreColor){
        Collection colores = this.getColores().values();
        Color aux = null;
        Color retorno = null;
        Iterator iter = colores.iterator();
        while (iter.hasNext()){
            aux = (Color) iter.next();
            if (aux.getNombreColor().equals(nombreColor)){
                retorno = aux;
            }
        }
        return retorno;
    }
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA NACIONALIDADES ">  
      public Nacionalidad agregarNacionalidad(String nombreNacionalidad)
    {
        Nacionalidad unaNacionalidad = null;
        unaNacionalidad = new Nacionalidad(nombreNacionalidad);
        int numero = unaNacionalidad.getIdNacionalidad();
        this.nacionalidades.put(numero, unaNacionalidad);
        this.persistencia.update(this);
        return unaNacionalidad;
    
    }
    
    public List buscarNacionalidades(){
        List nacionalidadActivos = new LinkedList();
        Collection nacionalidad = this.getNacionalidades().values();
        Nacionalidad aux = null;
        Iterator iter = nacionalidad.iterator();
        while (iter.hasNext()){
            aux = (Nacionalidad) iter.next();
            nacionalidadActivos.add(aux);
        }
        return nacionalidadActivos;
    }
    
    public void modificarNacionalidad(Nacionalidad unaNacionalidad,String nombreNacionalidad){
            unaNacionalidad.setNombreNacionalidad(nombreNacionalidad);
            this.persistencia.update(this);
    }
    
     public void eliminarNacionalidad(String nombreNacionalidad){
        Nacionalidad unaNacionalidad = this.buscarNacionalidad(nombreNacionalidad);
        this.nacionalidades.remove(unaNacionalidad.getIdNacionalidad());
        //this.persistencia.delete(unaNacionalidad);
        this.persistencia.update(this);
        this.crearAuditoria("DEL", "class Clase.Nacionalidad", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
    }
    
     public Nacionalidad buscarNacionalidad(String nombreNacionalidad){
        Collection nacionalidad = this.getNacionalidades().values();
        Nacionalidad aux = null;
        Nacionalidad retorno = null;
        Iterator iter = nacionalidad.iterator();
        while (iter.hasNext()){
            aux = (Nacionalidad) iter.next();
            if (aux.getNombreNacionalidad().equals(nombreNacionalidad)){
                retorno = aux;
            }
        }
        return retorno;
    }
        
       
        
    //</editor-fold>      
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA TIPO DE DOCUMENTO ">    
    public TipoDni agregartTipoDocumento(String descripcionTipoDNI, String siglas)
    {
        TipoDni unTipoDni = null;
        unTipoDni = new TipoDni(descripcionTipoDNI,siglas);
        int numero = unTipoDni.getIdTipoDni();
        this.tiposDnis.put(numero, unTipoDni);
        this.persistencia.update(this);
        return unTipoDni;
    
    }
    
    public List buscarTiposDocumentos(){
        List TiposDnisActivos = new LinkedList();
        Collection tiposDocumentos = this.getTiposDnis().values();
        TipoDni aux = null;
        Iterator iter = tiposDocumentos.iterator();
        while (iter.hasNext()){
            aux = (TipoDni) iter.next();
            TiposDnisActivos.add(aux);
            
        }
        return TiposDnisActivos;
    }
    
    public void modificarTipoDocumento( TipoDni unTipoDni,String descripcionTipoDNI, String siglas){
            unTipoDni.setDescripcionTipoDNI(descripcionTipoDNI);
            unTipoDni.setSiglas(siglas);
            this.persistencia.update(this);
    }
    
     public void eliminarTipoDocumento(String siglas){
        TipoDni unTipoDni = this.buscarTipoDocumento(siglas);
        this.tiposDnis.remove(unTipoDni.getIdTipoDni());
        //this.persistencia.delete(unTipoDni);
        this.crearAuditoria("DEL", "class Clase.TipoDni", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        this.persistencia.update(this);
       
    }
    
        public TipoDni buscarTipoDocumento(String siglas){
        Collection tiposDocumentos = this.getTiposDnis().values();
        TipoDni aux = null;
        TipoDni retorno = null;
        Iterator iter = tiposDocumentos.iterator();
        while (iter.hasNext()){
            aux = (TipoDni) iter.next();
            if (aux.getSiglas().equals(siglas)){
                retorno = aux;
            }
        }
        return retorno;
    }
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA MARCAS "> 
    
     public Marca agregarMarca(String nombreMarca)
    {
        Marca unaMarca = null;
        unaMarca = new Marca(nombreMarca);
        int numero = unaMarca.getIdMarca();
        this.marcas.put(numero, unaMarca);
        this.persistencia.update(this);
        return unaMarca;
    
    }
    
    public List buscarMarcas(){
        List marcasActivas = new LinkedList();
        Collection marcas = this.getMarcas().values();
        Marca aux = null;
        Iterator iter = marcas.iterator();
        while (iter.hasNext()){
            aux = (Marca) iter.next();
            marcasActivas.add(aux);
            
        }
        return marcasActivas;
    }
    
     public void modificarMarca(Marca unaMarca,String nombreMarca){
            unaMarca.setNombreMarca(nombreMarca);
            this.persistencia.update(this);
    }
    
     public void eliminarMarca(String nombreMarca){
       Marca unaMarca = this.buscarMarca(nombreMarca);
        this.marcas.remove(unaMarca.getIdMarca());
        //this.persistencia.delete(unaMarca);
        this.persistencia.update(this);
        this.crearAuditoria("DEL", "class Clase.Marca", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
    }
    
        public Marca buscarMarca(String nombreMarca){
        Collection marcas = this.getMarcas().values();
        Marca aux = null;
        Marca retorno = null;
        Iterator iter = marcas.iterator();
        while (iter.hasNext()){
            aux = (Marca) iter.next();
            if (aux.getNombreMarca().equals(nombreMarca)){
                retorno = aux;
            }
        }
        return retorno;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA MOTORES "> 
     public Motor agregarMotor(String nombreMotor)
    {
        Motor unMotor = null;
        unMotor = new Motor(nombreMotor);
        int numero = unMotor.getIdMotor();
        this.motores.put(numero, unMotor);
        this.persistencia.update(this);
        return unMotor;
    
    }
    
    public List buscarMotores(){
        List motoresActivas = new LinkedList();
        Collection motores = this.getMotores().values();
        Motor aux = null;
        Iterator iter = motores.iterator();
        while (iter.hasNext()){
            aux = (Motor) iter.next();
            motoresActivas.add(aux);
            
        }
        return motoresActivas;
    }
    
    public void modificarMotor(Motor unMotor,String nombreMotor){
            unMotor.setNombreMotor(nombreMotor);
            this.persistencia.update(this);
    }
    
     public void eliminarMotor(String nombreMotor){
        Motor unMotor = this.buscarMotor(nombreMotor);
        this.motores.remove(unMotor.getIdMotor());
        //this.persistencia.delete(unMotor);
        this.persistencia.update(this);
        this.crearAuditoria("DEL", "class Clase.Motor", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
    }
    
        public Motor buscarMotor(String nombreMotor){
        Collection motores = this.getMotores().values();
        Motor aux = null;
        Motor retorno = null;
        Iterator iter = motores.iterator();
        while (iter.hasNext()){
            aux = (Motor) iter.next();
            if (aux.getNombreMotor().equals(nombreMotor)){
                retorno = aux;
            }
        }
        return retorno;
    }
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA CLIENTES ">     
   
    
    public boolean dniClienteLibre(int key){
       boolean variable = false;
       Collection numeroClientes = this.getClientes().values();
       Cliente aux = null;
       Iterator iter = numeroClientes.iterator();
        while (iter.hasNext()){
           aux = (Cliente) iter.next();
           if (aux.getDni() == key){ 
                variable = true;
            }
        }
        return variable;
    }
        
    public void eliminarCliente(Cliente unCliente)
    {
        try
        {
                Collection telefonos = unCliente.getTelefonos().values();
                Collection domicilios = unCliente.getDomicilios().values();
                Telefono aux = null;
                Iterator iter = telefonos.iterator();
                while (iter.hasNext())
                {
                    aux = (Telefono) iter.next();
                    telefonos.remove(aux.getIdTelefono());
                    //this.persistencia.delete(aux);
                }
                Domicilio aux2 = null;
                Iterator iter2 = domicilios.iterator();
                while (iter2.hasNext()){
                    aux2 = (Domicilio) iter2.next();
                    domicilios.remove(aux2.getIdDomicilio());
                    //this.persistencia.delete(aux2);
                }
                this.clientes.remove(unCliente.getDni());
                //this.persistencia.delete(unCliente);
                this.persistencia.update(this);
                this.crearAuditoria("DEL", "class Clase.Cliente", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
           }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex, null, JOptionPane.ERROR_MESSAGE);
        }
    }
        
    public List buscarClientes(){
        List clientesActivos = new LinkedList();
        Collection clientes = this.getClientes().values();
        Cliente aux = null;
        Iterator iter = clientes.iterator();
        while (iter.hasNext()){
            aux = (Cliente) iter.next();
              if (!aux.getEstado().equals("Eliminado"))
              { 
                   clientesActivos.add(aux);
              }
        }
        return clientesActivos;
    }
    
     public Cliente buscarCliente(int dni){
       
       Collection numeroCliente = this.getClientes().values();
       Cliente aux = null;
       Iterator iter = numeroCliente.iterator();
        while (iter.hasNext()){
           aux = (Cliente) iter.next();
            if (!aux.getEstado().equals("Eliminado")){ 
                if (aux.getDni() == dni){ 
                        return aux;
                }
             }
        }
        return null;
    }
    
    public Cliente agregarCliente(int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, String nombre, String apellido, Date fechaNacimiento){
            Cliente unCliente= null;
            unCliente = new Cliente(dni, unTipoDni, unSexo,unEstadoCivil,unaNacionalidad, nombre, apellido,  fechaNacimiento);
            this.clientes.put(dni, unCliente);
            this.persistencia.update(this);
        
        return unCliente;
    }
    
    
  //Falta para MAP DE TELEFONO Y DOMICILIO
    public void modificarCliente(Cliente unCliente,int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, String nombre, String apellido,Date fechaNacimiento){
            unCliente.setDni(dni);
            unCliente.setUnTipoDni(unTipoDni);
            unCliente.setUnSexo(unSexo);
            unCliente.setUnEstadoCivil(unEstadoCivil);
            unCliente.setUnaNacionalidad(unaNacionalidad);
            unCliente.setNombre(nombre);
            unCliente.setApellido(apellido);
            unCliente.setFechaNacimiento(fechaNacimiento);
            this.persistencia.update(this);
    }
    
     public void modificarCliente2(Cliente unCliente,int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, String nombre, String apellido,Date fechaNacimiento)
     {
         this.eliminarCliente(unCliente);
         Cliente unClienteNuevo = null;
         unClienteNuevo = new Cliente(dni, unTipoDni, unSexo,unEstadoCivil,unaNacionalidad, nombre, apellido,  fechaNacimiento);
         this.clientes.put(dni, unClienteNuevo);
         this.persistencia.update(this);  
     }
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA TIPO_UTILIDADES ">
    public TipoUtilidad agregarTipoUtilidad(String nombreTipoUtilidad)
    {
        TipoUtilidad unTipoUtilidad = null;
        unTipoUtilidad = new TipoUtilidad(nombreTipoUtilidad);
        int numero = unTipoUtilidad.getIdTipoUtilidad();
        this.tiposUtilidades.put(numero, unTipoUtilidad);
        this.persistencia.update(this);
        return unTipoUtilidad;
    
    }
    
    public List buscarTiposUtilidades(){
        List TiposUtilidadesActivas = new LinkedList();
        Collection TiposUtilidades = this.getTiposUtilidades().values();
        TipoUtilidad aux = null;
        Iterator iter = TiposUtilidades.iterator();
        while (iter.hasNext()){
            aux = (TipoUtilidad) iter.next();
            TiposUtilidadesActivas.add(aux);
            
        }
        return TiposUtilidadesActivas;
    }
    
    public void modificarTipoUtilidad(TipoUtilidad unTipoUtilidad,String nombreTipoUtilidad){
            unTipoUtilidad.setNombreTipoUtilidad(nombreTipoUtilidad);
            this.persistencia.update(this);
    }
    
     public void eliminarTipoUtilidad(String nombreTipoUtilidad){
        TipoUtilidad unTipoUtilidad = this.buscarTipoUtilidad(nombreTipoUtilidad);
        this.tiposUtilidades.remove(unTipoUtilidad.getIdTipoUtilidad());
        //this.persistencia.delete(unTipoUtilidad);
        this.crearAuditoria("DEL", "class Clase.TipoUtilidad", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        this.persistencia.update(this);
       
    }
    
        public TipoUtilidad buscarTipoUtilidad(String nombreTipoUtilidad){
        Collection TiposUtilidades = this.getTiposUtilidades().values();
        TipoUtilidad aux = null;
        TipoUtilidad retorno = null;
        Iterator iter = TiposUtilidades.iterator();
        while (iter.hasNext()){
            aux = (TipoUtilidad) iter.next();
            if (aux.getNombreTipoUtilidad().equals(nombreTipoUtilidad)){
                retorno = aux;
            }
        }
        return retorno;
    }
     
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA UTILIDADES DE VIAJE ">
    public UtilidadViaje agregarTipoUtilidadViaje(String nombreTipoUtilidad)
    {
        UtilidadViaje unTipoUtilidad = null;
        unTipoUtilidad = new UtilidadViaje(nombreTipoUtilidad);
        int numero = unTipoUtilidad.getIdUtilidadViaje();
        this.utilidadViajes.put(numero, unTipoUtilidad);
        this.persistencia.update(this);
        return unTipoUtilidad;
    
    }
    
    public List buscarTiposUtilidadViaje(){
        List TiposUtilidadesActivas = new LinkedList();
        Collection TiposUtilidades = null;
        TiposUtilidades = this.getUtilidadViajes().values();
        if(TiposUtilidades != null)
        {
            UtilidadViaje aux = null;
            Iterator iter = TiposUtilidades.iterator();
            while (iter.hasNext()){
                aux = (UtilidadViaje) iter.next();
                TiposUtilidadesActivas.add(aux);
            }           
        }
        return TiposUtilidadesActivas;
}
    
    public void modificarTipoUtilidadViaje(UtilidadViaje unTipoUtilidad,String nombreTipoUtilidad){
            unTipoUtilidad.setNombreUtilidadViaje(nombreTipoUtilidad);
            this.persistencia.update(this);
    }
    
     public void eliminarTipoUtilidadViaje(String nombreTipoUtilidad){
         if(!nombreTipoUtilidad.equals("Cliente") ||!nombreTipoUtilidad.equals("CLIENTE"))
         {
            UtilidadViaje unTipoUtilidad = this.buscarTiposUtilidadViaje(nombreTipoUtilidad);
           this.utilidadViajes.remove(unTipoUtilidad.getIdUtilidadViaje());
           //this.persistencia.delete(unTipoUtilidad);
           this.crearAuditoria("DEL", "class Clase.UtilidadViaje", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
           this.persistencia.update(this);
         }
         else
         {
             JOptionPane.showMessageDialog(null,"El dato es utilizado en otra instacia, no se puede eliminar", nombre, idRemiseria);
         }
        
       
    }
    
        public UtilidadViaje buscarTiposUtilidadViaje(String nombreTipoUtilidad){
        Collection TiposUtilidades = this.getUtilidadViajes().values();
        UtilidadViaje aux = null;
        UtilidadViaje retorno = null;
        Iterator iter = TiposUtilidades.iterator();
        while (iter.hasNext()){
            aux = (UtilidadViaje) iter.next();
            if (aux.getNombreUtilidadViaje().equals(nombreTipoUtilidad)){
                retorno = aux;
            }
        }
        return retorno;
    }    
     //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA RODADOS "> 
     public Rodado agregarRodado(String nombreRodado)
    {
        Rodado unRodado = null;
        unRodado = new Rodado(nombreRodado);
        int numero = unRodado.getIdRodado();
        this.rodados.put(numero, unRodado);
        this.persistencia.update(this);
        return unRodado;
    
    }
    
    public List buscarRodados(){
        List rodadosActivas = new LinkedList();
        Collection rodados = this.getRodados().values();
        if(rodados != null)
        {
            Rodado aux = null;
            Iterator iter = rodados.iterator();
            while (iter.hasNext()){
                aux = (Rodado) iter.next();
                rodadosActivas.add(aux);

            }
        }
        return rodadosActivas;
    }
    
    public void modificarRodado(Rodado unRodado,String nombreRodado){
            unRodado.setNombreRodado(nombreRodado);
            this.persistencia.update(this);
    }
    
     public void eliminarRodado(String nombreRodado){
        Rodado unRodado = this.buscarRodado(nombreRodado);
        this.rodados.remove(unRodado.getIdRodado());
        //this.persistencia.delete(unRodado);
        this.persistencia.update(this);
        this.crearAuditoria("DEL", "class Clase.Rodado", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
    }
    
        public Rodado buscarRodado(String nombreRodado){
        Collection rodados = this.getRodados().values();
        Rodado aux = null;
        Rodado retorno = null;
        Iterator iter = rodados.iterator();
        while (iter.hasNext()){
            aux = (Rodado) iter.next();
            if (aux.getNombreRodado().equals(nombreRodado)){
                retorno = aux;
            }
        }
        return retorno;
    }
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA DATOS DE ESTADOS CIVILES "> 
    public EstadoCivil agregarEstadoCivil(String nombreEstadoCivil)
    {
        EstadoCivil unEstadoCivil = null;
        unEstadoCivil = new EstadoCivil(nombreEstadoCivil);
        int numero = unEstadoCivil.getIdEstadoCivil();
        this.estadosCiviles.put(numero, unEstadoCivil);
        this.persistencia.update(this);
        return unEstadoCivil;
    
    }
    
    public List buscarEstadosCiviles(){
        List estadosCivilesActivas = new LinkedList();
        Collection estados = this.getEstadosCiviles().values();
        EstadoCivil aux = null;
        Iterator iter = estados.iterator();
        while (iter.hasNext()){
            aux = (EstadoCivil) iter.next();
            estadosCivilesActivas.add(aux);
            
        }
        return estadosCivilesActivas;
    }
    
    public void modificarEstadoCivil(EstadoCivil unEstadoCivil,String nombreEstadoCivil){
            unEstadoCivil.setNombreEstadoCivil(nombreEstadoCivil);
            this.persistencia.update(this);
    }
    
     public void eliminarEstadoCivil(String nombreEstadoCivil){
        EstadoCivil unEstadoCivil = this.buscarEstadoCivil(nombreEstadoCivil);
        this.estadosCiviles.remove(unEstadoCivil.getIdEstadoCivil());
        //this.persistencia.delete(unEstadoCivil);
        this.persistencia.update(this);
        this.crearAuditoria("DEL", "class Clase.EstadoCivil", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
    }
    
        public EstadoCivil buscarEstadoCivil(String nombreEstadoCivil){
        Collection estados = this.getEstadosCiviles().values();
        EstadoCivil aux = null;
        EstadoCivil retorno = null;
        Iterator iter = estados.iterator();
        while (iter.hasNext()){
            aux = (EstadoCivil) iter.next();
            if (aux.getNombreEstadoCivil().equals(nombreEstadoCivil)){
                retorno = aux;
            }
        }
        return retorno;
    }
   //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA DATOS DE SEXOS "> 
         public Sexo agregarSexo(String nombreSexo)
    {
        Sexo unSexo = null;
        unSexo = new Sexo(nombreSexo);
        int numero = unSexo.getIdSexo();
        this.sexos.put(numero, unSexo);
        this.persistencia.update(this);
        return unSexo;
    
    }
    
    public List buscarSexos(){
        List sexosActivas = new LinkedList();
        Collection sexoss = this.getSexos().values();
        Sexo aux = null;
        Iterator iter = sexoss.iterator();
        while (iter.hasNext()){
            aux = (Sexo) iter.next();
            sexosActivas.add(aux);
            
        }
        return sexosActivas;
    }
    
    public void modificarSexo(Sexo unSexo,String nombreSexo){
            unSexo.setNombreSexo(nombreSexo);
            this.persistencia.update(this);
    }
    
     public void eliminarSexo(String nombreSexo){
        Sexo unSexo = this.buscarSexo(nombreSexo);
        this.sexos.remove(unSexo.getIdSexo());
        //this.persistencia.delete(unSexo);
         this.crearAuditoria("DEL", "class Clase.Sexo", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        this.persistencia.update(this);
       
    }
    
        public Sexo buscarSexo(String nombreSexo){
        Collection sexos = this.getSexos().values();
        Sexo aux = null;
        Sexo retorno = null;
        Iterator iter = sexos.iterator();
        while (iter.hasNext()){
            aux = (Sexo) iter.next();
            if (aux.getNombreSexo().equals(nombreSexo)){
                retorno = aux;
            }
        }
        return retorno;
    }
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA MÓVILES ">    
    public void ponerEnCeroPrioridad ()
    {
        Movil mov = null;
        Collection movs = this.getMoviles().values();
        Iterator iter = movs.iterator();
        while (iter.hasNext())
        {
            
            mov = (Movil) iter.next();
            if(!mov.getEstado().equals("disponible"))
            {
                mov.setPrioridad(0);
            }
            this.persistencia.update(this);
        }
        List movilesActivos = this.buscarMovilesDisponibles();
        if(movilesActivos.size() >0)
        {
            if(movilesActivos != null)
            {
                Movil aux = null;
                int i, resultado;
                i = 1;
                Iterator iter2 = movilesActivos.iterator();
                //Metodo de para obtener el máximo valor de un List
                resultado = ((Movil) movilesActivos.get(i-1)).getPrioridad();
                Movil resul = null;
                while (iter2.hasNext())
                {
                    aux = (Movil) iter2.next();
                    if (aux.getPrioridad() > resultado)
                    {
                        resultado = aux.getPrioridad();
                        resul = aux;
                    }        
                }
                //Asigna el valor a las variables auxiliares en caso de cerrar sesion con moviles disponibles aún
                prioridad = resultado;
                movilprioridad = resul;
            }
        }
    }
        
    public void asignarPrioridad(Movil unMovil){
        //List movilesActivos = this.buscarMovilesPrioridad();
        if(movilprioridad == null){
            unMovil.setPrioridad(prioridad);
            movilprioridad = unMovil;
        }else{
            prioridad = 1 + movilprioridad.getPrioridad(); //+ 1;
            unMovil.setPrioridad(prioridad);
            movilprioridad = unMovil;
        }
        System.out.println(prioridad);
        this.persistencia.update(this);
        //List movilesActivos = this.buscarMovilesDisponibles();
    }
    
    public void asignarPrioridadFinalizado(Movil unMovil){
        //int prioridad = 1;
        List movilesActivos = this.buscarMovilesDisponibles();
        if(movilesActivos.size()>0)
        {
            if(movilesActivos == null)
            {
                unMovil.setPrioridad(prioridad);
            }
            else
            {
                Movil aux = null;
                int i, resultado;
                i = 1;
                Iterator iter = movilesActivos.iterator();
                //Metodo de para obtener el máximo valor de un List
                resultado = ((Movil) movilesActivos.get(i-1)).getPrioridad();
                while (iter.hasNext())
                {
                    aux = (Movil) iter.next();
                    if (aux.getPrioridad() > resultado)
                    {
                        resultado = aux.getPrioridad();
                    }        
                }              
                int num = 1 + resultado;
                unMovil.setPrioridad(num);
            }
            this.persistencia.update(this);
        }
    }
    


    public boolean nroPatenteLibre(String key){
       boolean variable = false;
       Collection numeroMoviles = this.getMoviles().values();
       Movil aux = null;
       Iterator iter = numeroMoviles.iterator();
       while (iter.hasNext()){
           aux = (Movil) iter.next();
           if (aux.getPatente().equals(key))
           { 
                variable = true;
           }
       }
           return variable;
    }
    
    
    public boolean nroMovilLibre(int key){
       boolean variable = false;
       Collection numeroMoviles = this.getMoviles().values();
       Movil aux = null;
       Iterator iter = numeroMoviles.iterator();
        while (iter.hasNext()){
           aux = (Movil) iter.next();
           if (aux.getNumeroMovil()== key)
           { 
                variable = true;
           }
        }
           
        //}
        return variable;
    }
    
    public Movil buscarMovilPatente(String patente){
        Movil movil = null;
        Collection moviles = this.getMoviles().values();
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext()){
            aux = (Movil) iter.next();
           // if (!aux.getEstado().equals("eliminado")){ 
            if (aux.getPatente().equals(patente)){ 
                movil = aux;
            }//}
        }
        return movil;
    }
    
    public void eliminarMovil(int numeroMovil){
        Movil movil = (Movil) this.moviles.get(numeroMovil);
        Collection reservas1 = this.buscarReservas();
        Iterator iter = reservas1.iterator();
        Reserva aux = null;
        while(iter.hasNext())
        {
            aux =(Reserva) iter.next();
            if(aux.getUnMovil().equals(movil))
            {
                this.eliminarReserva(aux);
            }
        }
        if(this.getUnaVentana()!= null)
        {
            this.getUnaVentana().cargarReservas();
        }
        this.moviles.remove(movil.getNumeroMovil());
        //movil.setEstado("eliminado");
//        List resultado = Remiseria.persistencia.buscarRevisiones("entidad_revisada");

        this.crearAuditoria("DEL", "class Clase.Movil", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
       // this.persistencia.delete(this);
        this.persistencia.update(this);
    }
    
    public Movil agregarMovil(int numeroMovil, String patente, Marca unaMarca, Modelo unModelo, int anio, Color unColor,Chofer unChofer,TipoUtilidad unTipoUtilidad,Rodado unRodado,int kilometraje,String aireAcondicionado,Double capacidadCarga, Motor unMotor, Boolean esAlquilado){
        Movil movil = null;
        if(!this.moviles.containsKey(numeroMovil)){
            movil = new Movil(numeroMovil, patente, unaMarca,unModelo,  anio, unColor, unChofer, unTipoUtilidad, unRodado, kilometraje, aireAcondicionado, capacidadCarga, unMotor);
            //int numero = movil.getIdMovil();
            movil.setAlquilado(false);
            this.moviles.put(numeroMovil, movil);
            this.persistencia.update(this);
        }
        return movil;
    }
    
    public void cargarKms (Movil unMovil, int Kms)
    {
        unMovil.setKilometraje(Kms);
        this.persistencia.update(this);
    }
    
    public boolean modificarMovil(Movil unMovil,int numeroMovil, String patente, Marca unaMarca, Modelo unModelo, int anio, Color unColor,Chofer unChofer,TipoUtilidad unTipoUtilidad,Rodado unRodado,int kilometraje,String aireAcondicionado,Double capacidadCarga,Motor unMotor, Boolean esAlquilado)
    {
          if ((unMovil.getPatente().equals(patente))&& (unMovil.getNumeroMovil()== numeroMovil)) 
          {
                unMovil.setUnaMarca(unaMarca);
                unMovil.setUnModelo(unModelo);
                unMovil.setAnio(anio);
                unMovil.setUnColor(unColor);
                unMovil.setUnTipoUtilidad(unTipoUtilidad);
                unMovil.setUnMotor(unMotor);
                unMovil.setUnRodado(unRodado);
                unMovil.setKilometraje(kilometraje);
                unMovil.setAireAcondicionado(aireAcondicionado);
                unMovil.setCapacidadCarga(capacidadCarga);
                unMovil.setAlquilado(esAlquilado);
                this.persistencia.update(this);
                JOptionPane.showMessageDialog(null,"Se ha modificado el Móvil N° "+ numeroMovil + " con Patente: "+ patente," ",JOptionPane.INFORMATION_MESSAGE);
                return true;
         }
         else
         {
             if ((!unMovil.getPatente().equals(patente))&& (unMovil.getNumeroMovil()== numeroMovil))
             {
                  if(this.nroPatenteLibre(patente)== false)
                  {
                        unMovil.setPatente(patente);
                        unMovil.setUnaMarca(unaMarca);
                        unMovil.setUnModelo(unModelo);
                        unMovil.setAnio(anio);
                        unMovil.setUnColor(unColor);
                        unMovil.setUnTipoUtilidad(unTipoUtilidad);
                        unMovil.setUnMotor(unMotor);
                        unMovil.setUnRodado(unRodado);
                        unMovil.setKilometraje(kilometraje);
                        unMovil.setAireAcondicionado(aireAcondicionado);
                        unMovil.setCapacidadCarga(capacidadCarga);
                        this.persistencia.update(this);
                        JOptionPane.showMessageDialog(null,"Se ha modificado el Móvil N° "+ numeroMovil + " con Patente: "+ patente," ",JOptionPane.INFORMATION_MESSAGE);
                        return true;
                  }
                  else
                  {
                      JOptionPane.showMessageDialog(null,"El Movil con la Patente: " + patente + " ya existe"," ",JOptionPane.ERROR_MESSAGE);
                  }
                 
             }
             else
                  {
                      if((unMovil.getPatente().equals(patente))&& (unMovil.getNumeroMovil()!= numeroMovil))
                      {
                        if(this.nroMovilLibre(numeroMovil)== false)
                        {
                            unMovil.setNumeroMovil(numeroMovil);
                            unMovil.setPatente(patente);
                            unMovil.setUnaMarca(unaMarca);
                            unMovil.setUnModelo(unModelo);
                            unMovil.setAnio(anio);
                            unMovil.setUnColor(unColor);
                            unMovil.setUnTipoUtilidad(unTipoUtilidad);
                            unMovil.setUnMotor(unMotor);
                            unMovil.setUnRodado(unRodado);
                            unMovil.setKilometraje(kilometraje);
                            unMovil.setAireAcondicionado(aireAcondicionado);
                            unMovil.setCapacidadCarga(capacidadCarga);
                            this.persistencia.update(this);
                            JOptionPane.showMessageDialog(null,"Se ha modificado el Móvil N° "+ numeroMovil + " con Patente: "+ patente," ",JOptionPane.INFORMATION_MESSAGE);
                            return true;
                        } 
                         else
                         {
                             JOptionPane.showMessageDialog(null,"El Móvil con el N° "+ numeroMovil +" ya existe "," ",JOptionPane.ERROR_MESSAGE);
                         }   
                          
                      }
                      else
                      {

                        if(this.nroMovilLibre(numeroMovil)== false)
                        {
                                if(this.nroPatenteLibre(patente)== false){
                                    unMovil.setNumeroMovil(numeroMovil);
                                    unMovil.setPatente(patente);
                                    unMovil.setUnaMarca(unaMarca);
                                    unMovil.setUnModelo(unModelo);
                                    unMovil.setAnio(anio);
                                    unMovil.setUnColor(unColor);
                                    unMovil.setUnTipoUtilidad(unTipoUtilidad);
                                    unMovil.setUnMotor(unMotor);
                                    unMovil.setUnRodado(unRodado);
                                    unMovil.setKilometraje(kilometraje);
                                    unMovil.setAireAcondicionado(aireAcondicionado);
                                    unMovil.setCapacidadCarga(capacidadCarga);
                                    this.persistencia.update(this);
                                    JOptionPane.showMessageDialog(null,"Se ha modificado el Móvil N° "+ numeroMovil + " con Patente: "+ patente," ",JOptionPane.INFORMATION_MESSAGE);
                                    return true;
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null,"El Movil con la Patente: " + patente + " ya existe"," ",JOptionPane.ERROR_MESSAGE);
                                }
                        } 
                        else
                        {
                                JOptionPane.showMessageDialog(null,"El Móvil con el N° "+ numeroMovil +" ya existe "," ",JOptionPane.ERROR_MESSAGE);
                        }   
                        }
                   }
         }
          return false;
    }   
    
    public Movil buscarMovil(int numeroMovil){
        Movil movil = null;
        Collection moviles = this.getMoviles().values();
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext()){
            aux = (Movil) iter.next();
            if (aux.getNumeroMovil()== numeroMovil){ 
                movil = aux;
            }
        }
        return movil;
    }
    
    public Viaje buscarViaje(int numero){
        Viaje viaje = (Viaje) this.viajes.get(numero);
        return viaje;
    }
    
    public List buscarMovilesDisponibles(){
        List movilesActivos = new LinkedList();
        Collection moviles = this.getMoviles().values();
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
             aux = (Movil) iter.next();
            if (aux.getEstado().equals("disponible"))
            {
                movilesActivos.add(aux);
            }
          
        }       
        //Metodo para ordenar por un atributo dato de una List
        Collections.sort(movilesActivos, new Comparator<Movil>() {
                @Override
                public int compare(Movil p1, Movil p2) {
                        return new Integer(p1.getPrioridad()).compareTo(new Integer(p2.getPrioridad()));
                }
        });
        return movilesActivos;
    }
    
    public List buscarMovilesDescanso(){
        List movilesDescanso = new LinkedList();
        Collection moviles = this.getMoviles().values();
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext()){
            aux = (Movil) iter.next();
            if (aux.getEstado().equals("descanso")){ 
                movilesDescanso.add(aux);
            }
        }
        return movilesDescanso;
    }
    
    public List buscarMovilesSinServicio(){
        List movilesSinServicio = new LinkedList();
        Collection moviles = this.getMoviles().values();
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext()){
            aux = (Movil) iter.next();
            if (aux.getEstado().equals("sin servicio"))
            {
//                if(aux.isAlquilado()==false)
//                {
                    movilesSinServicio.add(aux);
//                }
            }
        }
        return movilesSinServicio;
    }
    
    public List buscarMovilesTransito(){
        List movilesTransito = new LinkedList();
        Collection moviles = this.getMoviles().values();
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext()){
            aux = (Movil) iter.next();
            if (aux.getEstado().equals("transito")){ 
                movilesTransito.add(aux);
            }
        }
        return movilesTransito;
    }
    
    public List buscarMoviles(){
        List movilesActivos = new LinkedList();
        Collection moviles = this.getMoviles().values();
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext()){
            aux = (Movil) iter.next();
                movilesActivos.add(aux);
        }
        return movilesActivos;
    }
    
//    public List buscarMovilesPrioridad(){
//        List movilesActivos = new LinkedList();
//        Map moviless = this;
//        Set ref = moviless.keySet();
//        Iterator iter = ref.iterator();
//        while (iter.hasNext())
//        {
//            movilesActivos.add(iter.next());
//        }
//        System.out.println(movilesActivos);
//        //Collections.sort(movilesActivos);
//        return movilesActivos;      
//    }
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA CHOFERES ">  
    public boolean choferOcupado(Chofer chofer)
    {
        Movil aux = null;
        Collection movilesEnTransito = this.getMoviles().values();
        Iterator iter = movilesEnTransito.iterator();
        while (iter.hasNext())
        {
             aux = (Movil) iter.next();
             if(aux.getUnChofer()!= null)
             {
                if(aux.getUnChofer().getDni()==chofer.getDni()) 
                {
                        if ((aux.getEstado().equals("disponible")||(aux.getEstado().equals("transito"))))
                        { 
                            return true;   
                        }
                }
             }            
        }
        return false;
     } 
    
        public boolean choferOcupado2(Chofer chofer)
    {
        Movil aux = null;
        Collection movilesEnTransito = this.getMoviles().values();
        Iterator iter = movilesEnTransito.iterator();
        while (iter.hasNext())
        {
             aux = (Movil) iter.next();
             if(aux.getUnChofer()!= null)
             {
                if(aux.getUnChofer().getDni()==chofer.getDni()) 
                {
                        if ((aux.getEstado().equals("transito")))
                        { 
                            return true;   
                        }
                }
             }            
        }
        return false;
     }    
    
    
    public Chofer buscarChoferPorMovil(int numeroMovil)
    {
        Collection choferes = this.getChoferes().values();
        Chofer aux = null;
        Iterator iter = choferes.iterator();
        while (iter.hasNext()){
           aux = (Chofer) iter.next();
           if (aux.getUnMovil().getNumeroMovil() == numeroMovil){ 
                return aux;
            }
        }
        return aux;
        
    }
    
    
    public boolean nroChoferLibre(int key){
       boolean variable = false;
       Collection numeroChoferes = this.getChoferes().values();
       Chofer aux = null;
       Iterator iter = numeroChoferes.iterator();
        while (iter.hasNext()){
           aux = (Chofer) iter.next();
           if (aux.getDni() == key){ 
                variable = true;
            }
        }
        return variable;
    }
    
        public boolean nroClienteLibre(int key){
       boolean variable = false;
       Collection numeroClientes = this.getClientes().values();
       Cliente aux = null;
       Iterator iter = numeroClientes.iterator();
        while (iter.hasNext()){
           aux = (Cliente) iter.next();
           if (aux.getDni() == key){ 
                variable = true;
            }
        }
        return variable;
    }
        
    public void eliminarChofer(Chofer unChofer){
        try
        {
            this.eliminarChoferPorMovil(unChofer);
            Collection moviles = this.getMoviles().values();
            Movil aux = null;
            Iterator iter = moviles.iterator();
            while (iter.hasNext()){
                aux = (Movil) iter.next();
                if (aux.obtenerDniChofer() == unChofer.getDni()){
                    aux.setUnChofer(null);
                    aux.setEstado("sin servicio");

                }
            }
            Collection reservas1 = this.buscarReservas();
            if(reservas1 != null)
            {
                Iterator iter2 = reservas1.iterator();
                Reserva aux2 = null;
                while(iter2.hasNext())
                {
                    aux2 =(Reserva) iter2.next();
                    if(aux2.getUnChofer().equals(unChofer))
                    {
                        this.eliminarReserva(aux2);
                    }
                }
                if(this.getUnaVentana()!= null)
                {
                    this.getUnaVentana().cargarReservas();
                }
            }
            this.choferes.remove(unChofer.getDni());
            this.persistencia.update(this);
            //this.persistencia.delete(unChofer);
            this.crearAuditoria("DEL", "class Clase.Chofer", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        }
        catch (Exception ex)
        {
                JOptionPane.showMessageDialog(null, ex + " El Chofer a eliminar esta relacionado con otras instancias", null,JOptionPane.INFORMATION_MESSAGE);
        }
    }
        
    public List buscarChoferes(){
        List choferesActivos = new LinkedList();
        Collection choferes = this.getChoferes().values();
        Chofer aux = null;
        Iterator iter = choferes.iterator();
        while (iter.hasNext()){
            aux = (Chofer) iter.next();
              if (!aux.getEstado().equals("Eliminado"))
              { 
                   choferesActivos.add(aux);
              }
        }
        return choferesActivos;
    }
    
    public Chofer buscarChofer(int dni){
       
       Collection numeroChoferes = this.getChoferes().values();
       Chofer aux = null;
       Iterator iter = numeroChoferes.iterator();
        while (iter.hasNext()){
           aux = (Chofer) iter.next();
           if (aux.getDni() == dni)
           { 
                 return aux;
           }
        }
        return null;
    }
    
    public Chofer agregarChofer(int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Telefono unTelefono, Date fechaNacimiento,Movil unMovil){
            Chofer chofer = null;
            chofer = new Chofer(dni,unTipoDni,unSexo,unEstadoCivil,unaNacionalidad,unDomicilio,nombre,apellido, unTelefono,fechaNacimiento,unMovil);
            if (chofer.getUnMovil() != null)
            {
                chofer.getUnMovil().setUnChofer(chofer);
            }
            this.choferes.put(dni, chofer);
            this.persistencia.update(this);
        
        return chofer;
    }
    
    public boolean modificarChofer(Chofer unChofer,int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Telefono unTelefono, Date fechaNacimiento,Movil unMovil){       
            unChofer.setDni(dni);
            unChofer.setUnTipoDni(unTipoDni);
            unChofer.setUnSexo(unSexo);
            unChofer.setUnEstadoCivil(unEstadoCivil);
            unChofer.setUnaNacionalidad(unaNacionalidad);
            unChofer.setUnDomicilio(unDomicilio);
            unChofer.setNombre(nombre);
            unChofer.setApellido(apellido);
            unChofer.setUnTelefono(unTelefono);
            unChofer.setFechaNacimiento(fechaNacimiento);
            unChofer.setUnMovil(unMovil);
            this.persistencia.update(this);
            return true;
    }
    
   public void  modificarChofer2(Chofer unChofer,int dni, TipoDni unTipoDni, Sexo unSexo, EstadoCivil unEstadoCivil, Nacionalidad unaNacionalidad, Domicilio unDomicilio, String nombre, String apellido, Telefono unTelefono, Date fechaNacimiento,Movil unMovil)
   {
                this.eliminarChofer(unChofer);
                Chofer chofer = null;
                chofer = new Chofer(dni,unTipoDni,unSexo,unEstadoCivil,unaNacionalidad,unDomicilio,nombre,apellido, unTelefono,fechaNacimiento,unMovil);
                this.choferes.put(chofer.getDni(), chofer);
                this.persistencia.update(this);
   }
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA ZONAS ">  
     public List buscarZonas()
    {
        List zonasActivas = new LinkedList();
        Collection zonas = this.getZonas().values();
        Zona aux = null;
        Iterator iter = zonas.iterator();
        while (iter.hasNext()){
            aux = (Zona) iter.next();
           zonasActivas.add(aux);
            }
        
        return zonasActivas;
    }
    
    
    public Zona agregarZona(String nombre, String descripcion)
    {
        Zona unaZona = null;
        unaZona = new Zona(nombre, descripcion);
        this.zonas.put(unaZona.getNumeroZona(), unaZona);
        this.persistencia.update(this);
        return unaZona;
    }
    
    
    public void modificarZona(Zona unaZona,  String nombreZona, String descripcion){
        if (unaZona.getNombreZona().equals(nombreZona))
        {
            unaZona.setDescripcion(descripcion);
        }
        else
        {
            Zona zona = null;
            zona = new Zona( nombreZona, descripcion);
            this.zonas.remove(unaZona.getNumeroZona());
            this.zonas.put(zona.getNumeroZona(), zona);
            this.persistencia.delete(unaZona);
        }
        this.persistencia.update(this);
    }
    
     public void eliminarZona(Zona unaZona){
         if(!unaZona.getNombreZona().equals("Base"))
         {
            this.zonas.remove(unaZona.getNumeroZona()); 
            
            //this.persistencia.delete(unaZona);
            this.crearAuditoria("DEL", "class Clase.Zona", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
            this.persistencia.update(this);
            JOptionPane.showMessageDialog(null,"La Zona a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);
         }
         else
         {
             JOptionPane.showMessageDialog(null, "Este dato esta siendo utilizado en otra instancia, no se puede eliminar", nombre, JOptionPane.WARNING_MESSAGE);
         }
        
    }
       
     public Zona buscarZona(int numeroZona){
        Zona unaZona = (Zona) this.zonas.get(numeroZona);
        return unaZona;
    }
     
    public Zona buscarZona(String nombreZona){
 
        Collection zonas = this.getZonas().values();
        Zona aux = null;
        Zona retorno = null;
        Iterator iter = zonas.iterator();
        while (iter.hasNext()){
            aux = (Zona) iter.next();
            if (aux.getNombreZona().equals(nombreZona)){
                retorno = aux;
            }
        }
        return retorno;
    }
    
    
     //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA TARIFAS">  
    
    public List buscarTarifas(){
        List tarifasActivas = new LinkedList();
        Map tarifass = this.getTarifas();
        Set ref = tarifass.keySet();
        Iterator iter = ref.iterator();
        while (iter.hasNext()){
            tarifasActivas.add(iter.next());
        }
        Collections.sort(tarifasActivas);
        return tarifasActivas;
    }
       
        public List buscarTarifasActivas(){
        List tarifasActivas = new LinkedList();
        Collection tarifass = this.getTarifas().values();
        if(tarifass != null)
        {
            Iterator iter = tarifass.iterator();
            while (iter.hasNext()){
                tarifasActivas.add(iter.next());
            }

            return tarifasActivas;
        }
        return null;
    }
    
     public Tarifa agregarTarifa(double codigoTarifa, String referencia, double precio){
        Tarifa tarifa = null;
        tarifa = new Tarifa(codigoTarifa, referencia, precio);
        this.tarifas.put(tarifa.getIdTarifa(), tarifa);
        this.persistencia.update(this);
       
        return tarifa;
    }
    
    public void eliminarTodasLasTarifas()
    {
        Collection ta = this.getTarifas().values();
        Iterator iter = ta.iterator();
        Tarifa t = null;
        while(iter.hasNext())
        {
            t =(Tarifa) iter.next();
            this.crearAuditoria("DEL", "class Clase.Tarifa", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        }
        this.tarifas.clear();
        this.persistencia.update(this);    
    } 
     
     
     
    //ARREGLAR
    public Tarifa buscarTarifa(double codigo){

        Collection tarifas = this.getTarifas().values();
        Tarifa aux = null;
        Tarifa retorno = null;
        Iterator iter = tarifas.iterator();
        while (iter.hasNext()){
            aux = (Tarifa) iter.next();
            if (aux.getCodigoTarifa()==(codigo)){
                retorno = aux;
            }
        }
        return retorno;
        

    }
    
    
        public Tarifa buscarImporte(double importe){

        Collection tarifas = this.getTarifas().values();
        Tarifa aux = null;
        Tarifa retorno = null;
        Iterator iter = tarifas.iterator();
        while (iter.hasNext()){
            aux = (Tarifa) iter.next();
            if (aux.getPrecio()==(importe)){
                retorno = aux;
            }
        }
        return retorno;
        

    }

     public void modificarTarifa(Tarifa unaTarifa, double codigo, String referencia, double importe){
        if (unaTarifa.getCodigoTarifa() == codigo){
            unaTarifa.setCodigoTarifa(codigo);
            unaTarifa.setReferencia(referencia);
            unaTarifa.setPrecio(importe);

        }else{
            Tarifa tarifa = null;
            tarifa = new Tarifa(codigo, referencia, importe);
            this.tarifas.remove(unaTarifa.getIdTarifa());
            this.tarifas.put(tarifa.getIdTarifa(), tarifa);
            this.persistencia.delete(unaTarifa);
        }
        this.persistencia.update(this);
    }
    
    public void eliminarTarifa(Tarifa unaTarifa){
        this.tarifas.remove(unaTarifa.getIdTarifa());
        //this.persistencia.delete(unaTarifa);
        this.crearAuditoria("DEL", "class Clase.Tarifa", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        this.persistencia.update(this);

    }
    
         //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA RESERVAS ">     
   public Collection movilesDisponiblesReservas(Date fechaInicio, String horaInicio, String horaFin) {
    Date horaIn = utilidades.getHour(horaInicio);
    Date horaOut = utilidades.getHour(horaFin);
    Date fechaIn = fechaInicio;
//    fechaIn.setHours(0);
//    fechaIn.setMinutes(0);
//    fechaIn.setSeconds(0);
    Calendar in = Calendar.getInstance();
    Calendar out = Calendar.getInstance();
    Calendar inReserva = Calendar.getInstance();
    Calendar outReserva = Calendar.getInstance();
    in.setTime(horaIn);
    out.setTime(horaOut);   
    Collection ReservasActivos = this.buscarReservas();
    Collection movilesCargados = this.buscarMoviles(); 
    Date inicioViaje;
    Date finViaje;
    Date fechaReserva;      
    Reserva auxReserva = null;
    Iterator iterViaje = ReservasActivos.iterator();
    while (iterViaje.hasNext())
    {
        auxReserva = (Reserva) iterViaje.next();
        inicioViaje = (auxReserva.getHoraInicio());
        finViaje = (auxReserva.getHoraFin());
        inReserva.setTime(inicioViaje);
        outReserva.setTime(finViaje);
        System.out.println(in);
        System.out.println(out);
        System.out.println(inReserva);
        System.out.println(outReserva);
        fechaReserva = auxReserva.getFechaInicio();
//        fechaReserva.setHours(0);
//        fechaReserva.setMinutes(0);
//        fechaReserva.setSeconds(0);
        if(fechaIn.equals(fechaReserva))
        {
            if(in.before(inReserva))
            {
                if(out.before(inReserva))
                {
                    
                }
                else
                {
                    movilesCargados.remove(auxReserva.getUnMovil());                    
                }
            }
            else
            {
                if (in.after(outReserva))
                {
        
                }
                else
                {
                    if (in.equals(outReserva))
                    {
                        
                    }
                    else
                    {                      
                        movilesCargados.remove(auxReserva.getUnMovil());
                    }
                }
            }
        }
        else
        {
           if(fechaIn.before(fechaIn))
           {


           }
           else
           {
               if(fechaIn.after(fechaIn)){}
           }

        }
           
    }

   return movilesCargados; 
   }
   
    public void cambiarEstadoReserva(Reserva unaReserva, Viaje unViaje)
    {
        unaReserva.setNumeroDelViajeGenerado(unViaje.getNumeroViaje());
        unaReserva.setEstado("activo");
        this.persistencia.update(this);
    }
    
    public Reserva agregarReserva(DireccionViaje unaDireccionViaje, Date fechaInicio,Date horaInicio, Date horaFin, String estado, Movil unMovil, TipoUtilidad unTipoUtilidad,Pais unPais, Provincia unaProvincia, Ciudad unaCiudad, Barrio unBarrio,Chofer unChofer,Pais unPaisDestino,Provincia unaProvinciaDestino,Ciudad unaCiudadDestino, String direccionDestino,Cliente unCliente,String nombreEsporadico, Rodado unRodado, String tipoCliente){
        Reserva reserva = null;
        reserva = new Reserva( fechaInicio,horaInicio, horaFin, estado, unMovil,  unTipoUtilidad, unPais, unaProvincia, unaCiudad, unBarrio, unaDireccionViaje,unChofer,unPaisDestino,unaProvinciaDestino,unaCiudadDestino,direccionDestino, unCliente, nombreEsporadico,unRodado, tipoCliente,null);
        if(unMovil!= null && unChofer != null)
        {
            unMovil.setUnChofer(unChofer);
        }
        this.reservas.put(reserva.getNumeroReserva(), reserva);
        this.persistencia.update(this);
        return reserva;
    }
    
    public Reserva agregarReserva1(String unaDireccionViaje, Date fechaInicio,Date horaInicio, Date horaFin, String estado, Movil unMovil, TipoUtilidad unTipoUtilidad,Pais unPais, Provincia unaProvincia, Ciudad unaCiudad, Barrio unBarrio,Chofer unChofer,Pais unPaisDestino,Provincia unaProvinciaDestino,Ciudad unaCiudadDestino, String direccionDestino,Cliente unCliente,String nombreEsporadico, Rodado unRodado, String tipoCliente){
        Reserva reserva = null;
        DireccionViaje dir = new DireccionViaje (unaDireccionViaje);
        reserva = new Reserva( fechaInicio,horaInicio, horaFin, estado, unMovil,  unTipoUtilidad, unPais, unaProvincia, unaCiudad, unBarrio, dir,unChofer,unPaisDestino,unaProvinciaDestino,unaCiudadDestino,direccionDestino, unCliente, nombreEsporadico,unRodado, tipoCliente,null);
        if(unMovil!= null && unChofer != null)
        {
            unMovil.setUnChofer(unChofer);
        }
        this.reservas.put(reserva.getNumeroReserva(), reserva);
        this.persistencia.update(this);
        return reserva;
    }    
    
    public Reserva agregarReservaSegunDia(String unaDireccionViaje,Date horaInicio, Date horaFin, String estado, Movil unMovil, TipoUtilidad unTipoUtilidad,Pais unPais, Provincia unaProvincia, Ciudad unaCiudad, Barrio unBarrio,Chofer unChofer,Pais unPaisDestino,Provincia unaProvinciaDestino,Ciudad unaCiudadDestino,String direccionDestino,Cliente unCliente, Rodado unRodado, String tipoCliente,Map<String, Dias> dias){
        Reserva reserva = null;
        DireccionViaje d = new DireccionViaje(unaDireccionViaje);
        reserva = new Reserva(null, horaInicio,  horaFin,  estado,  unMovil,  unTipoUtilidad, unPais,  unaProvincia,  unaCiudad,  unBarrio, d, unChofer, unPaisDestino, unaProvinciaDestino, unaCiudadDestino,direccionDestino ,unCliente, null, unRodado,tipoCliente,dias) ;
        if(unMovil!= null && unChofer != null)
        {
            unMovil.setUnChofer(unChofer);
        }
        unCliente.agregarReservaSegunDia(reserva);
        this.reservas.put(reserva.getNumeroReserva(), reserva);
        this.persistencia.update(this);
        return reserva;
    }
    
    
    public void modificarReserva(Reserva unaReserva,DireccionViaje unaDireccionViaje, Date fechaInicio,Date horaInicio, Date horaFin, String estado, Movil unMovil, TipoUtilidad unTipoUtilidad,Pais unPais, Provincia unaProvincia, Ciudad unaCiudad, Barrio unBarrio,Chofer unChofer,Pais unPaisDestino,Provincia unaProvinciaDestino,Ciudad unaCiudadDestino,String direccionDestino,Cliente unCliente,String nombreEsporadico, Rodado unRodado, String tipoCliente,Map<String, Dias> dias){
           unaReserva.setUnaDireccionViaje(unaDireccionViaje);
           unaReserva.setDireccionDestino(direccionDestino);
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
           if (unChofer != null)
           {
               unMovil.setUnChofer(unChofer);
           }
           unaReserva.setDias(dias);
           this.persistencia.update(this);
    }
 
    public void modificarReserva2(Reserva unaReserva,String unaDireccionViaje, Date fechaInicio,Date horaInicio, Date horaFin, String estado, Movil unMovil, TipoUtilidad unTipoUtilidad,Pais unPais, Provincia unaProvincia, Ciudad unaCiudad, Barrio unBarrio,Chofer unChofer,Pais unPaisDestino,Provincia unaProvinciaDestino,Ciudad unaCiudadDestino,String direccionDestino,Cliente unCliente,String nombreEsporadico, Rodado unRodado, String tipoCliente,Map<String, Dias> dias){
           DireccionViaje d = new DireccionViaje(unaDireccionViaje);
           unaReserva.setUnaDireccionViaje(d);
           unaReserva.setDireccionDestino(direccionDestino);
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
           if (unChofer != null)
           {
               unMovil.setUnChofer(unChofer);
           }
           unaReserva.setDias(dias);
           this.persistencia.update(this);
    }    
     public void eliminarReserva(Reserva unaReserva){
         try{
           Reserva reserva = (Reserva) this.reservas.get(unaReserva.getNumeroReserva());          

            Cliente unCliente = unaReserva.getUnCliente();
            if(unCliente != null)
            {
                unCliente.eliminarReserva(unaReserva);
            }
            reserva.setUnPais(null);
            reserva.setUnaProvincia(null);
            reserva.setUnaCiudad(null);
            reserva.setUnBarrio(null);
            reserva.setUnPaisDestino(null);
            reserva.setUnaProvinciaDestino(null);
            reserva.setUnaCiudadDestino(null);
            reserva.setUnMovil(null);
            reserva.setUnChofer(null);
            reserva.setUnCliente(null);
            reserva.setUnTipoUtilidad(null);
            reserva.setEstado("Eliminado");
            this.reservas.remove(unaReserva.getNumeroReserva());
            
            //this.persistencia.delete(unaReserva);
            this.crearAuditoria("DEL", "class Clase.Reserva", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
            this.persistencia.update(this);
         }
         catch (Exception ex)
         {
             JOptionPane.showMessageDialog(null, ex);
         }
            
          
   }
    
    public Reserva buscarReserva(int numeroReserva){
        Reserva reserva = (Reserva) this.reservas.get(numeroReserva);
        return reserva;
    }

    public List buscarReservas(){
        List reservasActivas = new LinkedList();
        Collection reservass = this.getReservas().values();
        Reserva aux = null;
        Iterator iter = reservass.iterator();
        while (iter.hasNext())
        {
            
            aux = (Reserva) iter.next();
            if(aux.getEstado().equals("pendiente")||aux.getEstado().equals("vencido")|| aux.getEstado().equals("cancelado"))
            {
                reservasActivas.add(aux);
            }
        }
        return reservasActivas;
    }
    
    
    
    public List buscarReservasActivasDelDia(){
        List reservasActivas = new LinkedList();
        if(this.getReservas()!= null)
        {
            Collection reservass = this.getReservas().values();
            Reserva aux = null;
            Iterator iter = reservass.iterator();
            while (iter.hasNext()){
                aux = (Reserva) iter.next();
                if(aux.getDias()==null)
                {
                    if(aux.getEstado()!= null)
                    {
                        if (aux.getEstado().equals("pendiente") && aux.esHoy()==true){
                            reservasActivas.add(aux);
                        }
                    }
                }
            }
        }
        return reservasActivas;
    }
    
    public List buscarReservasActivasDelDiaCliente(){
        List reservasActivas = new LinkedList();
        Collection reservass = this.getReservas().values();
        Reserva aux = null;
        Iterator iter = reservass.iterator();
        while (iter.hasNext()){
            aux = (Reserva) iter.next();
            if(aux.getDias()!= null)
            {
                if(aux.getEstado()!= null)
                {
                    if (aux.getEstado().equals("pendiente") && aux.buscarDia(aux)==true){
                        reservasActivas.add(aux);
                    }
                }
            }
        }
        return reservasActivas;
    }
        public List buscarReservasTransito(){
        List reservasActivas = new LinkedList();
        Collection reservass = this.getReservas().values();
        if(reservass != null)
        {
            Reserva aux = null;
            Iterator iter = reservass.iterator();
            while (iter.hasNext()){
                aux = (Reserva) iter.next();
                if (aux.getEstado().equals("activo")){
                    reservasActivas.add(aux);
                }
            }
        }
        return reservasActivas;
    }
    
    public void terminarReserva (Reserva unaReserva)
    {
        if(unaReserva.getDias()!= null && unaReserva.getDias().size()>0)
        {
            unaReserva.setEstado("pendiente");
            unaReserva.setUnMovil(null);
            unaReserva.setUnChofer(null);
        }
        else
        {            
            unaReserva.setEstado("terminado");
        }
        
        this.persistencia.update(this);
    }
    
    public void cancelarReserva(Reserva unaReserva)
    {
        if(unaReserva.getDias().size()>0)
        {
            unaReserva.setEstado("pendiente");
        }
        else
        {
            unaReserva.setEstado("cancelado");
            
        }    
        
        this.persistencia.update(this);
    }
        
    public void recuperarJInternalFrame(JInternalFrameViaje unaVentana)
    {
        this.setUnaVentana(unaVentana);
    }

    
    public void cargarReservaDatosReserva(Reserva unaReserva,Movil unMovil, Chofer unChofer, Rodado unRodado)
    {   
        if(unaReserva.getDias()== null)
        {
            unMovil.setUnChofer(unChofer);
            unaReserva.setEstado("activo");
            unaReserva.setUnMovil(unMovil);
            unaReserva.setUnChofer(unChofer);
            unaReserva.setUnRodado(unRodado);
            Viaje unViaje = null;
            
            if(unaReserva.getUnCliente()!= null)
            {
                 unViaje= this.agregarViajeReserva(unaReserva.getUnPaisDestino()+" - "+ unaReserva.getUnaProvinciaDestino()+ " - " + unaReserva.getUnaCiudadDestino(),"Reserva del Cliente: "+unaReserva.getUnCliente(),"Base", unMovil, unaReserva.getUnPais(), unaReserva.getUnaProvincia(), unaReserva.getUnaCiudad(), unaReserva.getUnBarrio(), unaReserva.getUnaDireccionViaje(),unaReserva.getUnCliente());
            }
            else if(unaReserva.getNombreClienteEsporadicto()!= null)
            {
                unViaje= this.agregarViajeReserva(unaReserva.getUnPaisDestino()+" - "+ unaReserva.getUnaProvinciaDestino()+ " - " + unaReserva.getUnaCiudadDestino(),"Reserva del Particular: "+unaReserva.getNombreClienteEsporadicto() ,"Base", unMovil, unaReserva.getUnPais(), unaReserva.getUnaProvincia(), unaReserva.getUnaCiudad(), unaReserva.getUnBarrio(), unaReserva.getUnaDireccionViaje(),null);
            }
        
            
            unaReserva.setNumeroDelViajeGenerado(unViaje.getNumeroViaje());
            this.persistencia.update(this);
        }
        else
        {
            this.cargarReservaDatosReservaSegunDia(unaReserva, unMovil, unChofer, unRodado);
        }
    }
    
    
        public void cargarReservaDatosReservaSegunDia(Reserva unaReserva,Movil unMovil, Chofer unChofer, Rodado unRodado)
    {   
        unaReserva.setEstado("activo");
        unaReserva.setUnMovil(unMovil);
        unaReserva.setUnChofer(unChofer);
        unaReserva.setUnRodado(unRodado);
        unMovil.setUnChofer(unChofer);
        Viaje unViaje = this.agregarViajeReservaSegunDia(unaReserva.getUnCliente(),unaReserva.getUnPaisDestino()+" - "+ unaReserva.getUnaProvinciaDestino()+ " - " + unaReserva.getUnaCiudadDestino(),"Reserva del Cliente: "+unaReserva.getUnCliente() ,"Base", unMovil, unaReserva.getUnPais(), unaReserva.getUnaProvincia(), unaReserva.getUnaCiudad(), unaReserva.getUnBarrio(), unaReserva.getUnaDireccionViaje());
        unaReserva.setNumeroDelViajeGenerado(unViaje.getNumeroViaje());
        this.persistencia.update(this);
    }
    
    public List buscarMovilesFletes(){
          List movilesFletes = new LinkedList();
        Collection movilesActivos = this.getMoviles().values();
        Movil aux = null;
        Iterator iter = movilesActivos.iterator();
        while (iter.hasNext()){
            aux = (Movil) iter.next();
            if (aux.getUnTipoUtilidad().getNombreTipoUtilidad().equals("Flete")){
                movilesFletes.add(aux);
            }
        }
        return movilesFletes;
    }
    
    public void aplicarVencimientoReserva(Reserva unaReserva)
    {
        unaReserva.setEstado("vencido");
        this.persistencia.update(this);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA VIAJES ">     
    public Viaje iniciarViaje(String asignadoBase, Movil unMovil,UtilidadViaje unaUtilidadViaje,Pais unPais,Provincia unaProvincia,Ciudad unaCiudad, Barrio unBarrio, DireccionViaje unaDireccionViaje, Zona unaZona,Cliente unCliente)
    {     
        Domicilio unDomicilio = new Domicilio (unPais,unaProvincia,unaCiudad,unBarrio,unaDireccionViaje);
        Viaje viaje = null;
        viaje = new Viaje(unaUtilidadViaje, asignadoBase,unMovil,unDomicilio,0,unaZona,unCliente);
        this.viajes.put(viaje.getNumeroViaje(), viaje);
        unMovil.iniciarViaje(viaje);
        unMovil.agregarKilometrajeRecorridoPorVuelta(unMovil.getKilometraje(), utilidades.getFechaHoraActual());
        this.persistencia.update(this);
        return viaje;        
    }
     
     public void agregarNuevoChoferPorMovil(Movil unMovil)
    {
        Chofer unChofer = unMovil.getUnChofer();
        String horaInicio = (utilidades.getHora(utilidades.getFechaHoraActual()));
        Date fechaInicio =utilidades.getFechaHoraActual();//utilidades.getFecha(utilidades.getFechaHoraActual());
        this.agregarChoferPorMovil(unMovil, unChofer, horaInicio, fechaInicio);
    }
    
    public void agragarNuevoMovilPorZona (Movil unMovil)
    {
        Zona unaZona = this.buscarZona("Base");
        String horaInicio = (utilidades.getHora(utilidades.getFechaHoraActual()));
        String fechaInicio =utilidades.getFecha(utilidades.getFechaHoraActual());
        this.agregarMovilPorZona(unMovil, unaZona, horaInicio, fechaInicio);
    }
       
    
    public Viaje iniciarViaje1(String asignadoBase, Movil unMovil,UtilidadViaje unaUtilidadViaje,Pais unPais,Provincia unaProvincia,Ciudad unaCiudad, Barrio unBarrio, String unaDireccion, Zona unaZona, Cliente unCliente)
    {            
        DireccionViaje unaDireccionViaje = new DireccionViaje(unaDireccion);
        Domicilio unDomicilio = new Domicilio (unPais,unaProvincia,unaCiudad,unBarrio,unaDireccionViaje);
        Viaje viaje = null;
        viaje = new Viaje(unaUtilidadViaje, asignadoBase,unMovil,unDomicilio,0,unaZona,unCliente);
        this.viajes.put(viaje.getNumeroViaje(), viaje);
        unMovil.iniciarViaje(viaje);
        unMovil.agregarKilometrajeRecorridoPorVuelta(unMovil.getKilometraje(), utilidades.getFechaHoraActual());
        this.persistencia.update(this);
        return viaje;        
    }    
    
    
    
    
    public Viaje agregarViajeReserva(String destino,String tipoCliente,String inicioReserva, Movil unMovil,Pais unPais,Provincia unaProvincia,Ciudad unaCiudad, Barrio unBarrio, DireccionViaje unaDireccionViaje, Cliente unCliente)
    {
        if(unMovil.getEstado().equals("sin servicio"))
        {
            unMovil.setEstado("disponible reserva");
            Zona zona = this.buscarZona("Base");
            unMovil.movilDisponibleI(zona,unMovil.getUnChofer());
            unMovil.agregarKilometrosEnServicio(unMovil.getKilometraje(), utilidades.getFechaHoraActual(), unMovil.getUnChofer());
            this.agregarNuevoChoferPorMovil(unMovil);
            this.agragarNuevoMovilPorZona(unMovil);
            
        }
        else
        {
            unMovil.setEstado("transito reserva");
        }       
        Domicilio unDomicilio = new Domicilio (unPais,unaProvincia,unaCiudad,unBarrio,unaDireccionViaje);
        Viaje viaje = null;
        viaje = new Viaje(null, "Base",unMovil,unDomicilio,0,null,null);
        viaje.setDestino(destino);
        viaje.setInicioReserva(inicioReserva);
        viaje.setTipoClienteReserva(tipoCliente);
        viaje.setViajeReservado("reserva");
        viaje.setUnCliente(unCliente);
        this.viajes.put(viaje.getNumeroViaje(), viaje);
        unMovil.iniciarViaje(viaje);
        unMovil.agregarKilometrajeRecorridoPorVuelta(unMovil.getKilometraje(), utilidades.getFechaHoraActual());
        this.persistencia.update(this);
        return viaje;        
    }
    
    public Viaje agregarViajeReservaSegunDia(Cliente unCliente,String destino,String tipoCliente,String inicioReserva, Movil unMovil,Pais unPais,Provincia unaProvincia,Ciudad unaCiudad, Barrio unBarrio, DireccionViaje unaDireccionViaje)
    {     
        if(unMovil.getEstado().equals("sin servicio"))
        {
            unMovil.setEstado("disponible reserva");
            Zona zona = this.buscarZona("Base");
            unMovil.movilDisponibleI(zona,unMovil.getUnChofer());
            unMovil.agregarKilometrosEnServicio(unMovil.getKilometraje(), utilidades.getFechaHoraActual(), unMovil.getUnChofer());
            this.agregarNuevoChoferPorMovil(unMovil);
            this.agragarNuevoMovilPorZona(unMovil);
        }
        else
        {
            unMovil.setEstado("transito reserva");
        }
        
        Domicilio unDomicilio = new Domicilio (unPais,unaProvincia,unaCiudad,unBarrio,unaDireccionViaje);
        Viaje viaje = null;
        viaje = new Viaje(null, "Base",unMovil,unDomicilio,0,null,unCliente);
        viaje.setDestino(destino);
        viaje.setInicioReserva(inicioReserva);
        viaje.setTipoClienteReserva(tipoCliente);
        viaje.setViajeReservado("reserva");
        viaje.setUnCliente(unCliente);
        this.viajes.put(viaje.getNumeroViaje(), viaje);
        unMovil.iniciarViaje(viaje);
        unMovil.agregarKilometrajeRecorridoPorVuelta(unMovil.getKilometraje(), utilidades.getFechaHoraActual());
        this.persistencia.update(this);
        return viaje;        
    }    
    
    
    public void cancelarViaje(int numero){
        Viaje viaje = (Viaje) this.viajes.get(numero);
        viaje.setEstado("cancelado");
        viaje.getUnMovil().cancelarViaje(numero);
        //this.persistencia.insert(this);
        this.persistencia.update(this);
    }
    
    public void finalizarViaje(int numero, int tarifaSiNo, double tarifa){
        double laTarifa = 0.0;       
        Viaje viaje = (Viaje) this.viajes.get(numero);
        viaje.setEstado("finalizado");
        viaje.setTarifaSiNo(tarifaSiNo);
        if (tarifaSiNo == 1){
            laTarifa = tarifa;
            viaje.setTarifa(laTarifa);
        }
        else
        {
            Tarifa unaTarifa = this.buscarTarifa(tarifa);
            laTarifa = unaTarifa.getPrecio();
            viaje.setTarifa(laTarifa);
        }
        viaje.getUnMovil().finalizarViaje(numero, tarifaSiNo, laTarifa);
        this.persistencia.update(this);
    }
    
        
    public List buscarViajesActivos(){
        List viajesActivos = new LinkedList();
        Collection moviles = this.buscarMovilesTransito();
        if(moviles != null)
        {
            Movil aux = null;
            Iterator iter = moviles.iterator();
            while (iter.hasNext()){
                aux = (Movil) iter.next();
                if(aux.getUnChofer()!=null)
                {
                    if(aux.getUnChofer().getUnViaje()!= null)
                    {
                        if(aux.getUnChofer().getUnViaje().getViajeReservado() == null)
                        {
                             viajesActivos.add(aux.getUnChofer().getUnViaje());
                        }
                    }
                }
            }       
        }
        return viajesActivos;
    }
      //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA LIQUIDACIONES ">     
    public void agregarLiquidacionChofer(Date fechaLiquidado, Rendicion unaRendicion, Chofer unChofer)
{       
        double totalLiquidado = unaRendicion.getTotalRendicion()* this.getUnPorcentajeChofer().getPorcentajeChofer();
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
        totalLiquidado =Double.parseDouble(formateador.format(totalLiquidado));
        Liquidacion unaLiquidacionChofer = new Liquidacion(fechaLiquidado, totalLiquidado,unChofer,unaRendicion);
        this.liquidaciones.put(unaLiquidacionChofer.getIdLiquidacion(), unaLiquidacionChofer);
        this.persistencia.update(this);
    
    }
    
    public Liquidacion agregarLiquidacionCargo(Date fechaLiquidado,Cargo unCargo)
{       
        Liquidacion liq = new Liquidacion(fechaLiquidado,unCargo);
        this.liquidaciones.put(liq.getIdLiquidacion(), liq);
        this.persistencia.update(this);
        return liq;
    }    
    
    //registra el inicio y el cierre de cuanto trabajo el Operario
    public void agregarLiquidacionPreviaOperario(Operario unOperario, Maestro unMaestro)
    {   
        double totalLiquidado = 0;
        if(unOperario != null)
        {
            try
            {
                MarcarTarjeta unMarcarTarjeta = this.buscarTarjetas();
                if(unPagoOperario.getTipoPago().equals("por hora"))
                {
                    DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                    simbolos.setDecimalSeparator('.');
                    DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
                    totalLiquidado = utilidades.calcularPagoOperario(unMarcarTarjeta.getTotal(),unMarcarTarjeta.getFechaInicio(), unMarcarTarjeta.getFechaFin(), this,unMarcarTarjeta);
                    totalLiquidado =Double.parseDouble(formateador.format(totalLiquidado));
                    Liquidacion unaLiquidacionOperario = new Liquidacion(utilidades.getFechaHoraActual(), totalLiquidado,unMarcarTarjeta.getUnOperario(),unMarcarTarjeta);
                    this.liquidaciones.put(unaLiquidacionOperario.getIdLiquidacion(), unaLiquidacionOperario);
                    this.persistencia.update(this);
                }
                else
                {
                    if(this.buscarUnaLiquidacionOperario(unOperario) == false)
                    {
                        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                        simbolos.setDecimalSeparator('.');
                        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
                        totalLiquidado =Double.parseDouble(formateador.format(unPagoOperario.getPrecio()));
                        Liquidacion unaLiquidacionOperario = new Liquidacion(utilidades.getFechaHoraActual(), totalLiquidado,unMarcarTarjeta.getUnOperario(),unMarcarTarjeta);
                        this.liquidaciones.put(unaLiquidacionOperario.getIdLiquidacion(), unaLiquidacionOperario);
                        this.persistencia.update(this);
                        }
                   }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e + " "+ "Error en agregar la Liquidacion del Operario", null, JOptionPane.ERROR_MESSAGE);
            }
        }
    else
        {
            if(unMaestro != null)
            {
                try
                {
                    MarcarTarjeta unMarcarTarjeta = this.buscarTarjetas();
                    if(unPagoOperario.getTipoPago().equals("por hora"))
                    {
                        totalLiquidado = utilidades.calcularPagoOperario(unMarcarTarjeta.getTotal(),unMarcarTarjeta.getFechaInicio(), unMarcarTarjeta.getFechaFin(), this,unMarcarTarjeta);
                        Liquidacion unaLiquidacionMaestro = new Liquidacion(utilidades.getFechaHoraActual(), totalLiquidado,unMarcarTarjeta.getUnMaestro(),unMarcarTarjeta);
                        this.liquidaciones.put(unaLiquidacionMaestro.getIdLiquidacion(), unaLiquidacionMaestro);
                        this.persistencia.update(this);
                    }
                    else
                    {
                        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                        simbolos.setDecimalSeparator('.');
                        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
                        totalLiquidado =Double.parseDouble(formateador.format(unPagoOperario.getPrecio()));
                        Liquidacion unaLiquidacionMaestro = new Liquidacion(utilidades.getFechaHoraActual(),totalLiquidado,unMarcarTarjeta.getUnMaestro(),unMarcarTarjeta);
                        this.liquidaciones.put(unaLiquidacionMaestro.getIdLiquidacion(), unaLiquidacionMaestro);
                        this.persistencia.update(this);
                    }
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null, e + " "+ "Error en agregar la Liquidacion del Usuario maestro", null, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void agregarMarcarTarjeta(Operario unOperario, Date fechaInicio)
    {
        MarcarTarjeta unMarcarTarjta  = new MarcarTarjeta(unOperario,fechaInicio,null);
        this.marcados.put(unMarcarTarjta.getIdMarcarTarjeta(), unMarcarTarjta);
        this.persistencia.update(this);
        
    }
    
    public void agregarMarcarTarjeta(Maestro unMaestro, Date fechaInicio)
    {
        MarcarTarjeta unMarcarTarjta  = new MarcarTarjeta(unMaestro,fechaInicio,null);
        this.marcados.put(unMarcarTarjta.getIdMarcarTarjeta(), unMarcarTarjta);
        this.persistencia.update(this);
        
    }
        
    public void cargarSetMarcadoTarjeta (MarcarTarjeta unMarcarTarjeta,Reloj reloj)
    {
        unMarcarTarjeta.setTotal(utilidades.getHour(reloj.horario()));
        unMarcarTarjeta.setFechaFin(utilidades.getFechaHoraActual());
        this.persistencia.update(this);
    }

    public List buscarLiquidacionChofer (Chofer unChofer)
    {
        List liqBuscadas = new LinkedList();
        Collection liq = this.getLiquidaciones().values();
        Liquidacion aux = null;
        Iterator iter = liq.iterator();
        while(iter.hasNext())
        {
            aux = (Liquidacion) iter.next();
            if(aux.getEstadoLiquidacion().equals("sin pagar"))
            {
                if(unChofer.equals(aux.getUnChofer()))
                {
                    liqBuscadas.add(aux);
                }
            }
        }
        return liqBuscadas;
    }
    
public List buscarLiquidacionCargo (Cargo unCargo)
    {
        List liqBuscadas = new LinkedList();
        Collection liq = this.getLiquidaciones().values();
        Liquidacion aux = null;
        Iterator iter = liq.iterator();
        while(iter.hasNext())
        {
            aux = (Liquidacion) iter.next();
            if(aux.getEstadoLiquidacion().equals("pagado"))
            {
                if(unCargo.equals(aux.getUnCargo()))
                {
                    liqBuscadas.add(aux);
                }
            }
        }
        return liqBuscadas;
    }    
    
    public List buscarLiquidacionOperario (Operario unOperario)
    {
        List liqBuscadas = new LinkedList();
        Collection liq = this.getLiquidaciones().values();
        Liquidacion aux = null;
        Iterator iter = liq.iterator();
        while(iter.hasNext())
        {
            aux = (Liquidacion) iter.next();
            if(aux.getEstadoLiquidacion().equals("sin pagar"))
            {
                if(aux.getUnOperario()!= null)
                {
                    if(aux.getUnOperario().getDni()==(unOperario.getDni()))
                    {
                        liqBuscadas.add(aux);
                    }
                }
            }
        }
        return liqBuscadas;
    }
        
        public Boolean buscarUnaLiquidacionOperario (Operario unOperario)
    {

        Collection liq = this.getLiquidaciones().values();
        Liquidacion aux = null;
        Iterator iter = liq.iterator();
        while(iter.hasNext())
        {
            aux = (Liquidacion) iter.next();
            if(aux.getUnOperario()!= null)
            {      
               if(aux.getUnOperario().getDni()==(unOperario.getDni()))
               {
                   if(aux.getEstadoLiquidacion().equals("sin pagar"))
                   {
                        if(utilidades.getFecha(aux.getFechaCreacion()).equals(utilidades.getFecha(utilidades.getFechaHoraActual())))
                        {
                               return true;
                        }
                   }
                }
            }
        }
        return false;
    }
        
        
    public Boolean buscarUnaLiquidacionCargo(Cargo unCargo)
    {
        List unaLiq = this.buscarLiquidacionCargo(unCargo);
        Liquidacion l = null;
        Iterator iter = unaLiq.iterator();
        while(iter.hasNext())
        {
            l = (Liquidacion) iter.next();
            if(utilidades.getFecha(l.getFechaDePago()).equals(utilidades.getFecha(utilidades.getFechaHoraActual())))
            {
                   return true;
            }
        }
        return false;                
    }
        
        public void modificarLiquidacion(Liquidacion unaLiquidacion, String detalle)
        {
            unaLiquidacion.setFechaDePago(utilidades.getFechaHoraActual());
            unaLiquidacion.setEstadoLiquidacion("pagado");
            unaLiquidacion.setDetalle(detalle);
            this.persistencia.update(this);
        }
        
        
        public List<Liquidacion> listaLiquidacionePagadasTodo()
        {     
            List<Liquidacion> liq = new LinkedList<>();
            Collection rend = this.getLiquidaciones().values();
            Liquidacion aux = null;
            Iterator iter = rend.iterator();
            while(iter.hasNext())
            {
                aux = (Liquidacion)iter.next();
                if(aux.getEstadoLiquidacion().equals("pagado"))
                {
                    liq.add(aux);
                }
            }            
            return liq;
        }      
        
        
        public List<Liquidacion> listaLiquidacionePagadasSegunFecha(JXDatePicker dp_desde,JXDatePicker dp_hasta)
        {     
            List<Liquidacion> via = new LinkedList<>();
            Collection liq = this.getLiquidaciones().values();
            Liquidacion aux = null;
            Iterator iter = liq.iterator();
            while(iter.hasNext())
            {
                aux = (Liquidacion)iter.next();
                if(aux.getFechaDePago() != null)
                {
                        if ((aux.getFechaDePago().getTime() == dp_desde.getDate().getTime()) || (aux.getFechaDePago().getTime() > dp_desde.getDate().getTime()))
                        {
                            if ((aux.getFechaDePago().getTime() == dp_hasta.getDate().getTime()) || (aux.getFechaDePago().getTime() < dp_hasta.getDate().getTime()))
                            {
                                      via.add(aux);                       
                            }
                        }
                }
            }            
            return via;
       }
        
        
        public List<Liquidacion> listaLiquidacionePagadasSegunFechaYPersona(JXDatePicker dp_desde,JXDatePicker dp_hasta, Object persona)
        {   Chofer unChofer = null;
            Operario unOpera = null;
            if(persona instanceof Chofer)
            {
                unChofer = (Chofer) persona;
            }
            else if(persona instanceof Operario)
            {
                unOpera = (Operario) persona;
            }
            if(persona!= null)
            {          
                List<Liquidacion> via = new LinkedList<>();
                Collection liq = this.getLiquidaciones().values();
                Liquidacion aux = null;
                Iterator iter = liq.iterator();
                while(iter.hasNext())
                {
                    aux = (Liquidacion)iter.next();
                    if(aux.getEstadoLiquidacion().equals("pagado"))
                    {
                            if ((aux.getFechaDePago().getTime() == dp_desde.getDate().getTime()) || (aux.getFechaDePago().getTime() > dp_desde.getDate().getTime()))
                            {
                                if ((aux.getFechaDePago().getTime() == dp_hasta.getDate().getTime()) || (aux.getFechaDePago().getTime() < dp_hasta.getDate().getTime()))
                                {
                                    if(aux.getUnOperario()!= null && aux.getUnOperario() == unOpera)
                                    {
                                        if(aux.getTotalLiquidado()>0.0)
                                        {
                                          via.add(aux);
                                        }
                                    }
                                    else if(aux.getUnChofer() != null && aux.getUnChofer() == unChofer)
                                    {
                                        via.add(aux);   
                                    }
                                }
                            }
                    }
                }            
                return via;
            }
            return null;
       }        

        public List<Liquidacion> listaLiquidacionePagadasTodoPersona(Object persona)
        {   Chofer unChofer = null;
            Operario unOpera = null;
            if(persona instanceof Chofer)
            {
                unChofer = (Chofer) persona;
            }
            else if(persona instanceof Operario)
            {
                unOpera = (Operario) persona;
            }
            if(persona!= null)
            {          
                List<Liquidacion> via = new LinkedList<>();
                Collection liq = this.getLiquidaciones().values();
                Liquidacion aux = null;
                Iterator iter = liq.iterator();
                while(iter.hasNext())
                {
                    aux = (Liquidacion)iter.next();
                    if(!aux.getEstadoLiquidacion().equals("sin pagar"))
                    {
                        if(aux.getUnOperario()!= null && aux.getUnOperario() == unOpera)
                        {
                              via.add(aux);       
                        }
                        else if(aux.getUnChofer() != null && aux.getUnChofer() == unChofer)
                        {
                            via.add(aux);   
                        }
                    }
                }            
                return via;
            }
            return null;
       }        
        
        
       public List agregarLiqEstatico(List liquidaciones)
       {
           Liquidacion liq = null;
           LinkedList <ImprimirLiquidacionSegunFiltro> listaLiq = new LinkedList<ImprimirLiquidacionSegunFiltro>(); 
           Iterator iter = liquidaciones.iterator();
           while (iter.hasNext())
           {
               liq = (Liquidacion) iter.next();
               String numeroLiq = String.valueOf(liq.getIdLiquidacion());
               String tipo = null;
               String numDocumento = null;
               String nombreYapellido = null;
               if(liq.getUnChofer()!= null)
               {
                   tipo = "Chofer" ;
                   nombreYapellido = liq.getUnChofer().toString();
                   numDocumento = String.valueOf(liq.getUnChofer().getDni());
               }
               else if(liq.getUnOperario()!= null)
               {
                   tipo = "Operario";
                   nombreYapellido = liq.getUnOperario().toString();
                   numDocumento = String.valueOf(liq.getUnOperario().getDni());
               }
               String fechaDePago = utilidades.getFecha(liq.getFechaDePago());
               String totalRendido = "";
               if(liq.getUnaRendicion()!= null)
               {
                 totalRendido ="$ "+ String.valueOf(liq.getUnaRendicion().getTotalRendicion());
               }
               else
               {
                   totalRendido = "No aplicado";
               }
               String totalLiquidado ="$ "+ String.valueOf(liq.getTotalLiquidado());
               listaLiq.add(new ImprimirLiquidacionSegunFiltro(numeroLiq,tipo,numDocumento,nombreYapellido,fechaDePago,totalRendido,totalLiquidado));
           }
           return listaLiq;
       }
        
       public String totalLiquidacionSegunFiltro (List lista)
       {
           Double total = 0.0;
           Liquidacion liq = null;
           Iterator iter = lista.iterator();
           while (iter.hasNext())
           {
               liq = (Liquidacion) iter.next();
               total = total + liq.getTotalLiquidado();
           }
           return String.valueOf(total);
       }
       
       public List totalLiquidacionTodosSegunFecha (JXDatePicker dp_desde,JXDatePicker dp_hasta)
       {
           List list = new LinkedList();
           Collection lista = this.getLiquidaciones().values();
           Liquidacion liq = null;
           Iterator iter = lista.iterator();
           while(iter.hasNext())
                {
                    liq = (Liquidacion)iter.next();
                    if(!liq.getEstadoLiquidacion().equals("sin pagar"))
                    {
                        if ((liq.getFechaDePago().getTime() == dp_desde.getDate().getTime()) || (liq.getFechaDePago().getTime() > dp_desde.getDate().getTime()))
                        {
                            if ((liq.getFechaDePago().getTime() == dp_hasta.getDate().getTime()) || (liq.getFechaDePago().getTime() < dp_hasta.getDate().getTime()))
                            {
                                    list.add(liq);
                            }
                        }
                    }
                }
              
           return list;
       }
          //</editor-fold>

       public List ordenarMayorKilometro (List<ImprimirMovilKmsRecorridosTotalesSegunPeriodo> lista)
       {
               Collections.sort(lista, new Comparator<ImprimirMovilKmsRecorridosTotalesSegunPeriodo>() {
                @Override
                public int compare(ImprimirMovilKmsRecorridosTotalesSegunPeriodo p1, ImprimirMovilKmsRecorridosTotalesSegunPeriodo p2) {
                        return new Integer(Integer.valueOf(p2.getKmsTotal())).compareTo(new Integer(Integer.valueOf(p1.getKmsTotal())));
                }

            });
            return lista;
       }
       
       public List ordenarMenorKilometro (List<ImprimirMovilKmsRecorridosTotalesSegunPeriodo> lista)
       {
               Collections.sort(lista, new Comparator<ImprimirMovilKmsRecorridosTotalesSegunPeriodo>() {
                @Override
                public int compare(ImprimirMovilKmsRecorridosTotalesSegunPeriodo p1, ImprimirMovilKmsRecorridosTotalesSegunPeriodo p2) {
                        return new Integer(Integer.valueOf(p1.getKmsTotal())).compareTo(new Integer(Integer.valueOf(p2.getKmsTotal())));
                }

            });         
            return lista;
       }    
       
       public List ordenarPorMovil (List<ImprimirKmsRecorridosSegunHoraFecha> lista)
       {
               Collections.sort(lista, new Comparator<ImprimirKmsRecorridosSegunHoraFecha>() {
                @Override
                public int compare(ImprimirKmsRecorridosSegunHoraFecha p1, ImprimirKmsRecorridosSegunHoraFecha p2) {
                        return new Integer(Integer.valueOf(p1.getMovil())).compareTo(new Integer(Integer.valueOf(p2.getMovil())));
                }

            });         
            return lista;
       }        
       
    //<editor-fold defaultstate="collapsed" desc="CARGAR DATOS DE ENTRADA">            
    public void cargarDatos() throws Exception{
        if(this.getNombre() != null)
        {
            //PARAMETROS GENERALES
            this.agregartTipoDocumento("Documento Nacional de Identidad","D.N.I");
            this.agregartTipoDocumento("Libreta Cívica","L.C");
            this.agregartTipoDocumento("Cédula de Identidad","C.I");
            this.agregarSexo("Masculino");
            this.agregarSexo("Femenino");
            this.agregarSexo("Sin especificar");        
            this.agregarEstadoCivil("Soltero/a");
            this.agregarEstadoCivil("Casado/a");
            this.agregarEstadoCivil("Separado/a");
            this.agregarNacionalidad("Argentino");
            this.agregarNacionalidad("Brasilero");
            this.agregarNacionalidad("Boliviano");
            this.agregarNacionalidad("Chileno");
            this.agregarNacionalidad("Paraguayo");
            this.agregarNacionalidad("Venezolano");

            Pais unPais = this.buscarPais(this.getUnDomicilio().getUnPais().getNombrePais());
            unPais.agregarProvincia("Corrientes");
            unPais.agregarProvincia("Chaco");
            unPais.agregarProvincia("Formaosa");
            unPais.agregarProvincia("Entre Rios");
            unPais.agregarProvincia("Tucumán");
            unPais.agregarProvincia("Salta");
            unPais.agregarProvincia("Jujuy");
            unPais.agregarProvincia("Catamarca");
            Provincia  unaProvincia = unPais.buscarProvincia(this.getUnDomicilio().getUnaProvincia().getNombreProvincia());
            //unaProvincia.agregarCiudad("Posadas");
            unaProvincia.agregarCiudad("Candelaria");
            unaProvincia.agregarCiudad("Garupa");
            unaProvincia.agregarCiudad("Apóstoles");
            Ciudad unaCiudad = unaProvincia.buscarCiudad(this.getUnDomicilio().getUnaCiudad().getNombreCiudad());
            unaCiudad.agregarBarrio("1° de Mayo");
            unaCiudad.agregarBarrio("Aero Club");
            unaCiudad.agregarBarrio("Aguas Corrientes");
            unaCiudad.agregarBarrio("Alta Gracia");
            unaCiudad.agregarBarrio("Bajada Vieja");
            unaCiudad.agregarBarrio("Belgrano");
            unaCiudad.agregarBarrio("Bella Vista");
            unaCiudad.agregarBarrio("Centenario");
            unaCiudad.agregarBarrio("Centro");
            unaCiudad.agregarBarrio("Cerro Pelón");
            unaCiudad.agregarBarrio("Cristo Rey");
            unaCiudad.agregarBarrio("Docente");
            unaCiudad.agregarBarrio("El Brete");
            unaCiudad.agregarBarrio("El Libertador");
            unaCiudad.agregarBarrio("El Palomar");
            unaCiudad.agregarBarrio("El Progreso");
            unaCiudad.agregarBarrio("Itaembé Miní");
            unaCiudad.agregarBarrio("Keneddy");          
            //TELEFONO
            this.agregarCodPais("54");
            CodPais unCodPais = this.buscarCodigoPais("54");
            unCodPais.agregarCodArea(	"2345"	)	;
            unCodPais.agregarCodArea(	"2902"	)	;
            unCodPais.agregarCodArea(	"2317"	)	;
            unCodPais.agregarCodArea(	"3469"	)	;
            unCodPais.agregarCodArea(	"3585"	)	;
            unCodPais.agregarCodArea(	"3865"	)	;
            unCodPais.agregarCodArea(	"3827"	)	;
            unCodPais.agregarCodArea(	"3438"	)	;
            unCodPais.agregarCodArea(	"3465"	)	;
            unCodPais.agregarCodArea(	"2225"	)	;
            unCodPais.agregarCodArea(	"2941"	)	;
            unCodPais.agregarCodArea(	"3571"	)	;
            unCodPais.agregarCodArea(	"3547"	)	;
            unCodPais.agregarCodArea(	"3892"	)	;
            unCodPais.agregarCodArea(	"11"	)	;
            unCodPais.agregarCodArea(	"2337"	)	;
            unCodPais.agregarCodArea(	"3844"	)	;
            unCodPais.agregarCodArea(	"3835"	)	;
            unCodPais.agregarCodArea(	"3827"	)	;
            unCodPais.agregarCodArea(	"3758"	)	;
            unCodPais.agregarCodArea(	"3543"	)	;
            unCodPais.agregarCodArea(	"2478"	)	;
            unCodPais.agregarCodArea(	"2353"	)	;
            unCodPais.agregarCodArea(	"3576"	)	;
            unCodPais.agregarCodArea(	"3402"	)	;
            unCodPais.agregarCodArea(	"3491"	)	;
            unCodPais.agregarCodArea(	"2353"	)	;
            unCodPais.agregarCodArea(	"2296"	)	;
            unCodPais.agregarCodArea(	"2281"	)	;
            unCodPais.agregarCodArea(	"291"	)	;
            unCodPais.agregarCodArea(	"2266"	)	;
            unCodPais.agregarCodArea(	"353"	)	;
            unCodPais.agregarCodArea(	"3563"	)	;
            unCodPais.agregarCodArea(	"3857"	)	;
            unCodPais.agregarCodArea(	"3329"	)	;
            unCodPais.agregarCodArea(	"3466"	)	;
            unCodPais.agregarCodArea(	"2648"	)	;
            unCodPais.agregarCodArea(	"3722"	)	;
            unCodPais.agregarCodArea(	"3534"	)	;
            unCodPais.agregarCodArea(	"2292"	)	;
            unCodPais.agregarCodArea(	"3741"	)	;
            unCodPais.agregarCodArea(	"2925"	)	;
            unCodPais.agregarCodArea(	"2314"	)	;
            unCodPais.agregarCodArea(	"3387"	)	;
            unCodPais.agregarCodArea(	"3438"	)	;
            unCodPais.agregarCodArea(	"2342"	)	;
            unCodPais.agregarCodArea(	"658"	)	;
            unCodPais.agregarCodArea(	"3894"	)	;
            unCodPais.agregarCodArea(	"3781"	)	;
            unCodPais.agregarCodArea(	"3868"	)	;
            unCodPais.agregarCodArea(	"297"	)	;
            unCodPais.agregarCodArea(	"2335"	)	;
            unCodPais.agregarCodArea(	"648"	)	;
            unCodPais.agregarCodArea(	"3489"	)	;
            unCodPais.agregarCodArea(	"3846"	)	;
            unCodPais.agregarCodArea(	"3471"	)	;
            unCodPais.agregarCodArea(	"3463"	)	;
            unCodPais.agregarCodArea(	"752"	)	;
            unCodPais.agregarCodArea(	"2651"	)	;
            unCodPais.agregarCodArea(	"2226"	)	;
            unCodPais.agregarCodArea(	"2323"	)	;
            unCodPais.agregarCodArea(	"2478"	)	;
            unCodPais.agregarCodArea(	"341"	)	;
            unCodPais.agregarCodArea(	"936"	)	;
            unCodPais.agregarCodArea(	"2254"	)	;
            unCodPais.agregarCodArea(	"2395"	)	;
            unCodPais.agregarCodArea(	"2274"	)	;
            unCodPais.agregarCodArea(	"2357"	)	;
            unCodPais.agregarCodArea(	"273"	)	;
            unCodPais.agregarCodArea(	"2929"	)	;
            unCodPais.agregarCodArea(	"3464"	)	;
            unCodPais.agregarCodArea(	"2245"	)	;
            unCodPais.agregarCodArea(	"3833"	)	;
            unCodPais.agregarCodArea(	"264"	)	;
            unCodPais.agregarCodArea(	"3491"	)	;
            unCodPais.agregarCodArea(	"2352"	)	;
            unCodPais.agregarCodArea(	"3582"	)	;
            unCodPais.agregarCodArea(	"3456"	)	;
            unCodPais.agregarCodArea(	"3826"	)	;
            unCodPais.agregarCodArea(	"3721"	)	;
            unCodPais.agregarCodArea(	"3731"	)	;
            unCodPais.agregarCodArea(	"2241"	)	;
            unCodPais.agregarCodArea(	"3821"	)	;
            unCodPais.agregarCodArea(	"3825"	)	;
            unCodPais.agregarCodArea(	"2346"	)	;
            unCodPais.agregarCodArea(	"2946"	)	;
            unCodPais.agregarCodArea(	"2948"	)	;
            unCodPais.agregarCodArea(	"299"	)	;
            unCodPais.agregarCodArea(	"2982"	)	;
            unCodPais.agregarCodArea(	"3718"	)	;
            unCodPais.agregarCodArea(	"2473"	)	;
            unCodPais.agregarCodArea(	"3447"	)	;
            unCodPais.agregarCodArea(	"2333"	)	;
            unCodPais.agregarCodArea(	"3722"	)	;
            unCodPais.agregarCodArea(	"3716"	)	;
            unCodPais.agregarCodArea(	"2962"	)	;
            unCodPais.agregarCodArea(	"2291"	)	;
            unCodPais.agregarCodArea(	"297"	)	;
            unCodPais.agregarCodArea(	"865"	)	;
            unCodPais.agregarCodArea(	"3442"	)	;
            unCodPais.agregarCodArea(	"345"	)	;
            unCodPais.agregarCodArea(	"351"	)	;
            unCodPais.agregarCodArea(	"342"	)	;
            unCodPais.agregarCodArea(	"2223"	)	;
            unCodPais.agregarCodArea(	"2921"	)	;
            unCodPais.agregarCodArea(	"3582"	)	;
            unCodPais.agregarCodArea(	"2922"	)	;
            unCodPais.agregarCodArea(	"2926"	)	;
            unCodPais.agregarCodArea(	"2265"	)	;
            unCodPais.agregarCodArea(	"3468"	)	;
            unCodPais.agregarCodArea(	"3783"	)	;
            unCodPais.agregarCodArea(	"3541"	)	;
            unCodPais.agregarCodArea(	"3467"	)	;
            unCodPais.agregarCodArea(	"3549"	)	;
            unCodPais.agregarCodArea(	"3774"	)	;
            unCodPais.agregarCodArea(	"299"	)	;
            unCodPais.agregarCodArea(	"2316"	)	;
            unCodPais.agregarCodArea(	"2924"	)	;
            unCodPais.agregarCodArea(	"3521"	)	;
            unCodPais.agregarCodArea(	"2626"	)	;
            unCodPais.agregarCodArea(	"2245"	)	;
            unCodPais.agregarCodArea(	"2317"	)	;
            unCodPais.agregarCodArea(	"2334"	)	;
            unCodPais.agregarCodArea(	"2902"	)	;
            unCodPais.agregarCodArea(	"3783"	)	;
            unCodPais.agregarCodArea(	"3401"	)	;
            unCodPais.agregarCodArea(	"3751"	)	;
            unCodPais.agregarCodArea(	"2335"	)	;
            unCodPais.agregarCodArea(	"3488"	)	;
            unCodPais.agregarCodArea(	"3496"	)	;
            unCodPais.agregarCodArea(	"2945"	)	;
            unCodPais.agregarCodArea(	"3454"	)	;
            unCodPais.agregarCodArea(	"3465"	)	;
            unCodPais.agregarCodArea(	"3717"	)	;
            unCodPais.agregarCodArea(	"2337"	)	;
            unCodPais.agregarCodArea(	"3854"	)	;
            unCodPais.agregarCodArea(	"3404"	)	;
            unCodPais.agregarCodArea(	"3752"	)	;
            unCodPais.agregarCodArea(	"2952"	)	;
            unCodPais.agregarCodArea(	"2344"	)	;
            unCodPais.agregarCodArea(	"2625"	)	;
            unCodPais.agregarCodArea(	"2353"	)	;
            unCodPais.agregarCodArea(	"2243"	)	;
            unCodPais.agregarCodArea(	"2245"	)	;
            unCodPais.agregarCodArea(	"2931"	)	;
            unCodPais.agregarCodArea(	"358"	)	;
            unCodPais.agregarCodArea(	"2268"	)	;
            unCodPais.agregarCodArea(	"3725"	)	;
            unCodPais.agregarCodArea(	"2286"	)	;
            unCodPais.agregarCodArea(	"2267"	)	;
            unCodPais.agregarCodArea(	"3711"	)	;
            unCodPais.agregarCodArea(	"2302"	)	;
            unCodPais.agregarCodArea(	"2356"	)	;
            unCodPais.agregarCodArea(	"2265"	)	;
            unCodPais.agregarCodArea(	"2941"	)	;
            unCodPais.agregarCodArea(	"237"	)	;
            unCodPais.agregarCodArea(	"3388"	)	;
            unCodPais.agregarCodArea(	"2224"	)	;
            unCodPais.agregarCodArea(	"3498"	)	;
            unCodPais.agregarCodArea(	"2202"	)	;
            unCodPais.agregarCodArea(	"3777"	)	;
            unCodPais.agregarCodArea(	"3444"	)	;
            unCodPais.agregarCodArea(	"3446"	)	;
            unCodPais.agregarCodArea(	"2929"	)	;
            unCodPais.agregarCodArea(	"2224"	)	;
            unCodPais.agregarCodArea(	"2933"	)	;
            unCodPais.agregarCodArea(	"2336"	)	;
            unCodPais.agregarCodArea(	"3887"	)	;
            unCodPais.agregarCodArea(	"3716"	)	;
            unCodPais.agregarCodArea(	"3711"	)	;
            unCodPais.agregarCodArea(	"2940"	)	;
            unCodPais.agregarCodArea(	"2335"	)	;
            unCodPais.agregarCodArea(	"3467"	)	;
            unCodPais.agregarCodArea(	"3786"	)	;
            unCodPais.agregarCodArea(	"2647"	)	;
            unCodPais.agregarCodArea(	"3525"	)	;
            unCodPais.agregarCodArea(	"3877"	)	;
            unCodPais.agregarCodArea(	"2320"	)	;
            unCodPais.agregarCodArea(	"2354"	)	;
            unCodPais.agregarCodArea(	"2229"	)	;
            unCodPais.agregarCodArea(	"2264"	)	;
            unCodPais.agregarCodArea(	"2362"	)	;
            unCodPais.agregarCodArea(	"2972"	)	;
            unCodPais.agregarCodArea(	"3584"	)	;
            unCodPais.agregarCodArea(	"2623"	)	;
            unCodPais.agregarCodArea(	"2264"	)	;
            unCodPais.agregarCodArea(	"3548"	)	;
            unCodPais.agregarCodArea(	"3437"	)	;
            unCodPais.agregarCodArea(	"2626"	)	;
            unCodPais.agregarCodArea(	"221"	)	;
            unCodPais.agregarCodArea(	"3575"	)	;
            unCodPais.agregarCodArea(	"3885"	)	;
            unCodPais.agregarCodArea(	"3822"	)	;
            unCodPais.agregarCodArea(	"2655"	)	;
            unCodPais.agregarCodArea(	"3385"	)	;
            unCodPais.agregarCodArea(	"3783"	)	;
            unCodPais.agregarCodArea(	"3891"	)	;
            unCodPais.agregarCodArea(	"2285"	)	;
            unCodPais.agregarCodArea(	"2268"	)	;
            unCodPais.agregarCodArea(	"2244"	)	;
            unCodPais.agregarCodArea(	"3877"	)	;
            unCodPais.agregarCodArea(	"3715"	)	;
            unCodPais.agregarCodArea(	"3482"	)	;
            unCodPais.agregarCodArea(	"3533"	)	;
            unCodPais.agregarCodArea(	"3754"	)	;
            unCodPais.agregarCodArea(	"3472"	)	;
            unCodPais.agregarCodArea(	"2242"	)	;
            unCodPais.agregarCodArea(	"220"	)	;
            unCodPais.agregarCodArea(	"3886"	)	;
            unCodPais.agregarCodArea(	"2355"	)	;
            unCodPais.agregarCodArea(	"3497"	)	;
            unCodPais.agregarCodArea(	"2261"	)	;
            unCodPais.agregarCodArea(	"2227"	)	;
            unCodPais.agregarCodArea(	"2948"	)	;
            unCodPais.agregarCodArea(	"3327"	)	;
            unCodPais.agregarCodArea(	"2322"	)	;
            unCodPais.agregarCodArea(	"3547"	)	;
            unCodPais.agregarCodArea(	"2358"	)	;
            unCodPais.agregarCodArea(	"2323"	)	;
            unCodPais.agregarCodArea(	"261"	)	;
            unCodPais.agregarCodArea(	"2953"	)	;
            unCodPais.agregarCodArea(	"3734"	)	;
            unCodPais.agregarCodArea(	"3476"	)	;
            unCodPais.agregarCodArea(	"2221"	)	;
            unCodPais.agregarCodArea(	"2268"	)	;
            unCodPais.agregarCodArea(	"261"	)	;
            unCodPais.agregarCodArea(	"2627"	)	;
            unCodPais.agregarCodArea(	"2257"	)	;
            unCodPais.agregarCodArea(	"223"	)	;
            unCodPais.agregarCodArea(	"3472"	)	;
            unCodPais.agregarCodArea(	"220"	)	;
            unCodPais.agregarCodArea(	"3722"	)	;
            unCodPais.agregarCodArea(	"2927"	)	;
            unCodPais.agregarCodArea(	"261"	)	;
            unCodPais.agregarCodArea(	"2324"	)	;
            unCodPais.agregarCodArea(	"3773"	)	;
            unCodPais.agregarCodArea(	"2657"	)	;
            unCodPais.agregarCodArea(	"220"	)	;
            unCodPais.agregarCodArea(	"2656"	)	;
            unCodPais.agregarCodArea(	"3876"	)	;
            unCodPais.agregarCodArea(	"2291"	)	;
            unCodPais.agregarCodArea(	"3409"	)	;
            unCodPais.agregarCodArea(	"3717"	)	;
            unCodPais.agregarCodArea(	"2271"	)	;
            unCodPais.agregarCodArea(	"3775"	)	;
            unCodPais.agregarCodArea(	"2921"	)	;
            unCodPais.agregarCodArea(	"3841"	)	;
            unCodPais.agregarCodArea(	"3891"	)	;
            unCodPais.agregarCodArea(	"3863"	)	;
            unCodPais.agregarCodArea(	"237"	)	;
            unCodPais.agregarCodArea(	"3562"	)	;
            unCodPais.agregarCodArea(	"2656"	)	;
            unCodPais.agregarCodArea(	"2272"	)	;
            unCodPais.agregarCodArea(	"2262"	)	;
            unCodPais.agregarCodArea(	"299"	)	;
            unCodPais.agregarCodArea(	"3435"	)	;
            unCodPais.agregarCodArea(	"3825"	)	;
            unCodPais.agregarCodArea(	"2343"	)	;
            unCodPais.agregarCodArea(	"3861"	)	;
            unCodPais.agregarCodArea(	"2342"	)	;
            unCodPais.agregarCodArea(	"3755"	)	;
            unCodPais.agregarCodArea(	"3856"	)	;
            unCodPais.agregarCodArea(	"2284"	)	;
            unCodPais.agregarCodArea(	"3532"	)	;
            unCodPais.agregarCodArea(	"3572"	)	;
            unCodPais.agregarCodArea(	"3878"	)	;
            unCodPais.agregarCodArea(	"2982"	)	;
            unCodPais.agregarCodArea(	"343"	)	;
            unCodPais.agregarCodArea(	"3772"	)	;
            unCodPais.agregarCodArea(	"2928"	)	;
            unCodPais.agregarCodArea(	"2396"	)	;
            unCodPais.agregarCodArea(	"2477"	)	;
            unCodPais.agregarCodArea(	"2963"	)	;
            unCodPais.agregarCodArea(	"2923"	)	;
            unCodPais.agregarCodArea(	"2242"	)	;
            unCodPais.agregarCodArea(	"2322"	)	;
            unCodPais.agregarCodArea(	"2254"	)	;
            unCodPais.agregarCodArea(	"223"	)	;
            unCodPais.agregarCodArea(	"376"	)	;
            unCodPais.agregarCodArea(	"3734"	)	;
            unCodPais.agregarCodArea(	"3732"	)	;
            unCodPais.agregarCodArea(	"3722"	)	;
            unCodPais.agregarCodArea(	"297"	)	;
            unCodPais.agregarCodArea(	"3757"	)	;
            unCodPais.agregarCodArea(	"2965"	)	;
            unCodPais.agregarCodArea(	"3743"	)	;
            unCodPais.agregarCodArea(	"2962"	)	;
            unCodPais.agregarCodArea(	"3722"	)	;
            unCodPais.agregarCodArea(	"2932"	)	;
            unCodPais.agregarCodArea(	"2333"	)	;
            unCodPais.agregarCodArea(	"3843"	)	;
            unCodPais.agregarCodArea(	"2651"	)	;
            unCodPais.agregarCodArea(	"2317"	)	;
            unCodPais.agregarCodArea(	"2475"	)	;
            unCodPais.agregarCodArea(	"3492"	)	;
            unCodPais.agregarCodArea(	"3407"	)	;
            unCodPais.agregarCodArea(	"3869"	)	;
            unCodPais.agregarCodArea(	"3861"	)	;
            unCodPais.agregarCodArea(	"2297"	)	;
            unCodPais.agregarCodArea(	"2965"	)	;
            unCodPais.agregarCodArea(	"2331"	)	;
            unCodPais.agregarCodArea(	"3482"	)	;
            unCodPais.agregarCodArea(	"3832"	)	;
            unCodPais.agregarCodArea(	"3722"	)	;
            unCodPais.agregarCodArea(	"3783"	)	;
            unCodPais.agregarCodArea(	"3543"	)	;
            unCodPais.agregarCodArea(	"2931"	)	;
            unCodPais.agregarCodArea(	"358")	;
            unCodPais.agregarCodArea(	"2966"	)	;
            unCodPais.agregarCodArea(	"2964"	)	;
            unCodPais.agregarCodArea(	"2903"	)	;
            unCodPais.agregarCodArea(	"3574"	)	;
            unCodPais.agregarCodArea(	"3572"	)	;
            unCodPais.agregarCodArea(	"3571"	)	;
            unCodPais.agregarCodArea(	"2902"	)	;
            unCodPais.agregarCodArea(	"2935"	)	;
            unCodPais.agregarCodArea(	"2475"	)	;
            unCodPais.agregarCodArea(	"341"	)	;
            unCodPais.agregarCodArea(	"3445"	)	;
            unCodPais.agregarCodArea(	"3382"	)	;
            unCodPais.agregarCodArea(	"3782"	)	;
            unCodPais.agregarCodArea(	"2344"	)	;
            unCodPais.agregarCodArea(	"2393"	)	;
            unCodPais.agregarCodArea(	"2394"	)	;
            unCodPais.agregarCodArea(	"3542"	)	;
            unCodPais.agregarCodArea(	"387"	)	;
            unCodPais.agregarCodArea(	"2474"	)	;
            unCodPais.agregarCodArea(	"3582"	)	;
            unCodPais.agregarCodArea(	"2646"	)	;
            unCodPais.agregarCodArea(	"2325"	)	;
            unCodPais.agregarCodArea(	"2326"	)	;
            unCodPais.agregarCodArea(	"2934"	)	;
            unCodPais.agregarCodArea(	"2257"	)	;
            unCodPais.agregarCodArea(	"3404"	)	;
            unCodPais.agregarCodArea(	"294"	)	;
            unCodPais.agregarCodArea(	"2252"	)	;
            unCodPais.agregarCodArea(	"3408"	)	;
            unCodPais.agregarCodArea(	"3564"	)	;
            unCodPais.agregarCodArea(	"3522"	)	;
            unCodPais.agregarCodArea(	"3754"	)	;
            unCodPais.agregarCodArea(	"3405"	)	;
            unCodPais.agregarCodArea(	"3406"	)	;
            unCodPais.agregarCodArea(	"3458"	)	;
            unCodPais.agregarCodArea(	"264"	)	;
            unCodPais.agregarCodArea(	"2962"	)	;
            unCodPais.agregarCodArea(	"3498"	)	;
            unCodPais.agregarCodArea(	"3476"	)	;
            unCodPais.agregarCodArea(	"2652"	)	;
            unCodPais.agregarCodArea(	"3783"	)	;
            unCodPais.agregarCodArea(	"2261"	)	;
            unCodPais.agregarCodArea(	"2623"	)	;
            unCodPais.agregarCodArea(	"264"	)	;
            unCodPais.agregarCodArea(	"2972"	)	;
            unCodPais.agregarCodArea(	"381"	)	;
            unCodPais.agregarCodArea(	"3461"	)	;
            unCodPais.agregarCodArea(	"3329"	)	;
            unCodPais.agregarCodArea(	"3884"	)	;
            unCodPais.agregarCodArea(	"2627"	)	;
            unCodPais.agregarCodArea(	"388"	)	;
            unCodPais.agregarCodArea(	"2225"	)	;
            unCodPais.agregarCodArea(	"3783"	)	;
            unCodPais.agregarCodArea(	"223"	)	;
            unCodPais.agregarCodArea(	"342"	)	;
            unCodPais.agregarCodArea(	"3838"	)	;
            unCodPais.agregarCodArea(	"2954"	)	;
            unCodPais.agregarCodArea(	"3546"	)	;
            unCodPais.agregarCodArea(	"3460"	)	;
            unCodPais.agregarCodArea(	"2246"	)	;
            unCodPais.agregarCodArea(	"385"	)	;
            unCodPais.agregarCodArea(	"3756"	)	;
            unCodPais.agregarCodArea(	"2934"	)	;
            unCodPais.agregarCodArea(	"2324"	)	;
            unCodPais.agregarCodArea(	"3856"	)	;
            unCodPais.agregarCodArea(	"3493"	)	;
            unCodPais.agregarCodArea(	"3855"	)	;
            unCodPais.agregarCodArea(	"3867"	)	;
            unCodPais.agregarCodArea(	"2293"	)	;
            unCodPais.agregarCodArea(	"2283"	)	;
            unCodPais.agregarCodArea(	"3875"	)	;
            unCodPais.agregarCodArea(	"3858"	)	;
            unCodPais.agregarCodArea(	"2656"	)	;
            unCodPais.agregarCodArea(	"3837"	)	;
            unCodPais.agregarCodArea(	"3846"	)	;
            unCodPais.agregarCodArea(	"3471"	)	;
            unCodPais.agregarCodArea(	"3862"	)	;
            unCodPais.agregarCodArea(	"3576"	)	;
            unCodPais.agregarCodArea(	"2965"	)	;
            unCodPais.agregarCodArea(	"2392"	)	;
            unCodPais.agregarCodArea(	"2983"	)	;
            unCodPais.agregarCodArea(	"2394"	)	;
            unCodPais.agregarCodArea(	"2622"	)	;
            unCodPais.agregarCodArea(	"3543"	)	;
            unCodPais.agregarCodArea(	"2901"	)	;
            unCodPais.agregarCodArea(	"2624"	)	;
            unCodPais.agregarCodArea(	"2354"	)	;
            unCodPais.agregarCodArea(	"3462"	)	;
            unCodPais.agregarCodArea(	"3483"	)	;
            unCodPais.agregarCodArea(	"2221"	)	;
            unCodPais.agregarCodArea(	"3436"	)	;
            unCodPais.agregarCodArea(	"2338"	)	;
            unCodPais.agregarCodArea(	"3583"	)	;
            unCodPais.agregarCodArea(	"2920"	)	;
            unCodPais.agregarCodArea(	"3735"	)	;
            unCodPais.agregarCodArea(	"2625"	)	;
            unCodPais.agregarCodArea(	"3541"	)	;
            unCodPais.agregarCodArea(	"3400"	)	;
            unCodPais.agregarCodArea(	"3522"	)	;
            unCodPais.agregarCodArea(	"3573"	)	;
            unCodPais.agregarCodArea(	"3524"	)	;
            unCodPais.agregarCodArea(	"3544"	)	;
            unCodPais.agregarCodArea(	"2255"	)	;
            unCodPais.agregarCodArea(	"2336"	)	;
            unCodPais.agregarCodArea(	"2925"	)	;
            unCodPais.agregarCodArea(	"2944"	)	;
            unCodPais.agregarCodArea(	"353"	)	;
            unCodPais.agregarCodArea(	"2941"	)	;
            unCodPais.agregarCodArea(	"3845"	)	;
            unCodPais.agregarCodArea(	"3455"	)	;
            unCodPais.agregarCodArea(	"2928"	)	;
            unCodPais.agregarCodArea(	"2265"	)	;
            unCodPais.agregarCodArea(	"2333"	)	;
            unCodPais.agregarCodArea(	"2942"	)	;
            unCodPais.agregarCodArea	("3487"	)	;
            this.agregarTipoTelefono("Celular");
//            this.agregarTipoTelefono("Fijo");      
            this.agregarTipoUtilidadViaje("Pasajero");
            this.agregarTipoUtilidadViaje("Cliente");
            this.agregarTipoUtilidadViaje("Pedido");  
            this.agregarColor("Negro y Amarillo");
            this.agregarColor("Negro y Blanco");
            this.agregarColor("Blanco");
            this.agregarColor("Negro");
            this.agregarColor("Gris");
            this.agregarColor("Rojo");
            this.agregarColor("Azul");
            this.agregarColor("Amarillo");       
            this.agregarMotor("Naftero");
            this.agregarMotor("Diesel");
            this.agregarMotor("Bio-Diesel");
            this.agregarMotor("Hidrogeno");
            this.agregarMotor("Eléctrico");
            this.agregarTipoUtilidad("Viajes cortos");
            this.agregarTipoUtilidad("Especial");
            this.agregarTipoUtilidad("Flete");
            this.agregarRodado("Automóvil");
            this.agregarRodado("Camioneta");
            this.agregarRodado("Utilitario");
            this.agregarRodado("Motovehículo");
            this.agregarRodado("Camión");
            this.agregarMarca("Fiat");
            this.agregarMarca("Ford");
            this.agregarMarca("Volkswagen");
            this.agregarMarca("Renault");
            Marca unaMarca = this.buscarMarca("Fiat");
            unaMarca.agregarModelo("Croma");
            unaMarca.agregarModelo("Ducato");        
            unaMarca.agregarModelo("Duna");        
            unaMarca.agregarModelo("Palio");
            unaMarca.agregarModelo("Palio Adventure");
            unaMarca.agregarModelo("Siena");
            unaMarca.agregarModelo("Stilo");
            unaMarca.agregarModelo("Uno");
            Marca unaMarca2 = this.buscarMarca("Ford");       
            unaMarca2.agregarModelo("Escape");
            unaMarca2.agregarModelo("Eco-Sport");        
            unaMarca2.agregarModelo("Fiesta");        
            unaMarca2.agregarModelo("Focus");
            unaMarca2.agregarModelo("Mondeo");
            Marca unaMarca3 = this.buscarMarca("Volkswagen"); 
            unaMarca3.agregarModelo("Polo GTI");
            unaMarca3.agregarModelo("Polo Classic");        
            unaMarca3.agregarModelo("Gol Country");        
            unaMarca3.agregarModelo("Golf");
            unaMarca3.agregarModelo("Voyage"); 
            unaMarca3.agregarModelo("Jetta");
            unaMarca3.agregarModelo("Bora");
            unaMarca3.agregarModelo("Caddy");  
            Marca unaMarca4 = this.buscarMarca("Renault");
            unaMarca4.agregarModelo("Clio");
            unaMarca4.agregarModelo("Kangoo");        
            unaMarca4.agregarModelo("Laguna");        
            unaMarca4.agregarModelo("Mégane");
            unaMarca4.agregarModelo("Safrane"); 
            unaMarca4.agregarModelo("Sandero");
            unaMarca4.agregarModelo("Bora");
            unaMarca4.agregarModelo("Caddy");
           this.agregarZona("Base", "Calle "+this.getUnDomicilio().getCalle() + " N° "+ this.getUnDomicilio().getNroCasa());
           this.agregarMovil(1, "AAA-111", unaMarca3, unaMarca3.buscarModelo("Polo GTI"), 2010,this.buscarColor("Rojo"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 33000, "si", 750.00,this.buscarMotor("Naftero"),false);
           this.agregarMovil(2, "BBB-222", unaMarca4, unaMarca4.buscarModelo("Clio"), 2010,this.buscarColor("Azul"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);  
           this.agregarMovil(3, "CCC-333", unaMarca2, unaMarca2.buscarModelo("Focus"), 2013,this.buscarColor("Negro"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Naftero"),false);
           this.agregarMovil(4, "DDD-444", unaMarca, unaMarca.buscarModelo("Croma"), 2008,this.buscarColor("Blanco"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);
           this.agregarMovil(5, "EEE-555", unaMarca4, unaMarca4.buscarModelo("Clio"), 20009,this.buscarColor("Rojo"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Naftero"),false);
           this.agregarMovil(6, "QWE-666", unaMarca4, unaMarca4.buscarModelo("Laguna"), 2010,this.buscarColor("Azul"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);
           this.agregarMovil(7, "GHJ-777", unaMarca4, unaMarca4.buscarModelo("Bora"), 2012,this.buscarColor("Amarillo"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Naftero"),false); 
           this.agregarMovil(8, "RTY-456", unaMarca3, unaMarca3.buscarModelo("Voyage"), 2009,this.buscarColor("Negro"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);       
           this.agregarMovil(9, "HJK-456", unaMarca4, unaMarca4.buscarModelo("Sandero"), 2004,this.buscarColor("Negro"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);
           this.agregarMovil(10,"BNM-456", unaMarca4, unaMarca4.buscarModelo("Clio"), 2014,this.buscarColor("Gris"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);              
           this.agregarMovil(11,"VBN-111", unaMarca, unaMarca.buscarModelo("Palio Adventure"), 2010,this.buscarColor("Rojo"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 33000, "si", 750.00,this.buscarMotor("Naftero"),false);
           this.agregarMovil(12,"JKI-222", unaMarca, unaMarca.buscarModelo("Uno"), 2010,this.buscarColor("Azul"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);  
           this.agregarMovil(13,"POI-333", unaMarca, unaMarca.buscarModelo("Uno"), 2013,this.buscarColor("Negro"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Naftero"),false);
           this.agregarMovil(14,"RET-444", unaMarca, unaMarca.buscarModelo("Stilo"), 2008,this.buscarColor("Blanco"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);
           this.agregarMovil(15,"QAZ-555", unaMarca2, unaMarca2.buscarModelo("Mondeo"), 2009,this.buscarColor("Rojo"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Naftero"),false);
           this.agregarMovil(16,"WSX-666", unaMarca2, unaMarca2.buscarModelo("Fiesta"), 2010,this.buscarColor("Azul"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);
           this.agregarMovil(17,"RFV-777", unaMarca2, unaMarca2.buscarModelo("Eco-Sport"), 2012,this.buscarColor("Amarillo"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Naftero"),false); 
           this.agregarMovil(18,"TGB-456", unaMarca3, unaMarca3.buscarModelo("Golf"), 2009,this.buscarColor("Negro"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);       
           this.agregarMovil(19,"YHN-456", unaMarca3, unaMarca3.buscarModelo("Gol Country"), 2004,this.buscarColor("Negro"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Naftero"),false);
           this.agregarMovil(20,"UJM-456", unaMarca3, unaMarca3.buscarModelo("Jetta"), 2014,this.buscarColor("Gris"),null , this.buscarTipoUtilidad("Viajes cortos"), this.buscarRodado("Automóvil"), 60000, "si", 500.00,this.buscarMotor("Diesel"),false);  

           Provincia unaProvincia1 = unPais.buscarProvincia(this.getUnDomicilio().getUnaProvincia().getNombreProvincia());
           Ciudad unaCiudad1 = unaProvincia1.buscarCiudad(this.getUnDomicilio().getUnaCiudad().getNombreCiudad());
           Barrio unBarrio = unaCiudad1.buscarBarrio("1° de Mayo");
           Barrio unBarrio1= unaCiudad1.buscarBarrio("Aero Club");
           Barrio unBarrio2= unaCiudad1.buscarBarrio("Aguas Corrientes");
           Barrio unBarrio3 = unaCiudad1.buscarBarrio("Alta Gracia");
           Barrio unBarrio4 = unaCiudad1.buscarBarrio("Bajada Vieja");
           Barrio unBarrio5 = unaCiudad1.buscarBarrio("Belgrano");
           Barrio unBarrio6 = unaCiudad1.buscarBarrio("Bella Vista");
           Barrio unBarrio7 = unaCiudad1.buscarBarrio("Centenario");
           Barrio unBarrio8 = unaCiudad1.buscarBarrio("Centro");
           Barrio unBarrio9 = unaCiudad1.buscarBarrio("Cerro Pelón");
           Barrio unBarrio10 = unaCiudad1.buscarBarrio("Docente");     
           Domicilio unDomicilio0 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio,"Palomas","456",null,null);
           Domicilio unDomicilio1 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio1,"Alvear","4566",null,null);       
           Domicilio unDomicilio2 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio2,"Av. Constitución","678",null,null);
           Domicilio unDomicilio3 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio3,"Av.Centenario","3456",null,null);
           Domicilio unDomicilio4 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio4,"Ayacucho Cont. Entre Rios","3456",null,null);
           Domicilio unDomicilio5 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio5,"Av. Las Américas","3456",null,null);       
           Domicilio unDomicilio6 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio6,"Av.Tulo Llamosas","3466",null,null);   
           Domicilio unDomicilio7 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio7,"456","3466",null,null);
           Domicilio unDomicilio8 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio8,"Av. Bustamante","236",null,null);
           Domicilio unDomicilio9 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio9,"Tripoli","345",null,null);  
           Domicilio unDomicilio10 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio10,"Avila","3345",null,null);

           Domicilio unDomicilio11 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio,"124","9456",null,null);
           Domicilio unDomicilio12 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio1,"Pueyredon","3216",null,null);       
           Domicilio unDomicili013 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio2,"Colon","1678",null,null);
           Domicilio unDomicilio14 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio3,"Salta","2356",null,null);
           Domicilio unDomicilio15 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio4,"Entre Rios","1456",null,null);
           Domicilio unDomicilio16 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio5,"Av. Las Américas","346",null,null);       
           Domicilio unDomicilio17 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio6,"Av.Tulo Llamosas","1200",null,null);   
           Domicilio unDomicilio18 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio7,"San Lorenzo","3466",null,null);
           Domicilio unDomicilio19 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio8,"Belgrano","236",null,null);
           Domicilio unDomicilio20 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio9,"Av Santa Catalina","478",null,null);  
           Domicilio unDomicilio21 = new Domicilio(unPais,unaProvincia,unaCiudad1, unBarrio10,"Avila","3911",null,null);       
           TipoTelefono unTipoTel = this.buscarTipoTelefono("Celular");
           CodPais unCodPa = this.buscarCodigoPais("54");
           CodArea unCodAr = unCodPa.buscarCodArea("376");
           Telefono unTelefono1 = new Telefono("56789",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono2 = new Telefono("456789",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono3 = new Telefono("234567",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono4 = new Telefono("123456",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono5 = new Telefono("789065",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono6 = new Telefono("357896",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono7 = new Telefono("651234",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono8 = new Telefono("876543",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono9 = new Telefono("890865",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono10 = new Telefono("145678",unCodAr,unCodPa,unTipoTel);

           Telefono unTelefono11 = new Telefono("453421",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono12 = new Telefono("675432",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono13 = new Telefono("132456",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono14 = new Telefono("788909",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono15 = new Telefono("234567",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono16 = new Telefono("246789",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono17 = new Telefono("132435",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono18 = new Telefono("567645",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono19 = new Telefono("453212",unCodAr,unCodPa,unTipoTel);
           Telefono unTelefono20 = new Telefono("876543",unCodAr,unCodPa,unTipoTel);      
           Sexo unSex = this.buscarSexo("Masculino");
           EstadoCivil unEstadoCiv = this.buscarEstadoCivil("Soltero/a");
           EstadoCivil unEstadoCiv1 = this.buscarEstadoCivil("Casado/a");    
           Nacionalidad unaNac = this.buscarNacionalidad("Argentino");
           TipoDni tipoDoc = this.buscarTipoDocumento("D.N.I");
           Calendar ahoraCal = Calendar.getInstance();
           ahoraCal.set(1993,01 ,04 ,00, 00, 00);
           Date fechaNacimiento = ahoraCal.getTime();  
           Calendar ahoraCa2 = Calendar.getInstance();
           ahoraCa2.set(1992,02 ,24 ,00, 00, 00);
           Date fechaNacimiento2 = ahoraCa2.getTime();
           Calendar ahoraCa3 = Calendar.getInstance();
           ahoraCa3.set(1991,01 ,13 ,00, 00, 00);
           Date fechaNacimiento3 = ahoraCa3.getTime();        
           Calendar ahoraCa4 = Calendar.getInstance();
           ahoraCa4.set(1991,01 ,13 ,00, 00, 00);
           Date fechaNacimiento4 = ahoraCa4.getTime();
           Calendar ahoraCa5 = Calendar.getInstance();
           ahoraCa5.set(1990,06 ,17 ,00, 00, 00);
           Date fechaNacimiento5 = ahoraCa5.getTime();         
           Calendar ahoraCa6 = Calendar.getInstance();
           ahoraCa6.set(1989,07 ,01 ,00, 00, 00);
           Date fechaNacimiento6 = ahoraCa6.getTime(); 
           Calendar ahoraCa7 = Calendar.getInstance();
           ahoraCa7.set(1987,12 ,11 ,00, 00, 00);
           Date fechaNacimiento7 = ahoraCa7.getTime(); 
           Calendar ahoraCa8 = Calendar.getInstance();
           ahoraCa8.set(1987,03 ,13 ,00, 00, 00);
           Date fechaNacimiento8 = ahoraCa8.getTime(); 
           Calendar ahoraCa9 = Calendar.getInstance();
           ahoraCa9.set(1987,04 ,12 ,00, 00, 00);
           Date fechaNacimiento9 = ahoraCa9.getTime(); 

           Calendar ahoraCa10 = Calendar.getInstance();
           ahoraCa10.set(1986,03 ,12 ,00, 00, 00);
           Date fechaNacimiento10 = ahoraCa10.getTime();        
           Calendar ahoraCa11 = Calendar.getInstance();
           ahoraCa11.set(1986,03 ,12 ,00, 00, 00);
           Date fechaNacimiento11 = ahoraCa11.getTime(); 
           Calendar ahoraCa12 = Calendar.getInstance();
           ahoraCa12.set(1988,03 ,12 ,00, 00, 00);
           Date fechaNacimiento12 = ahoraCa12.getTime(); 
           Calendar ahoraCa13 = Calendar.getInstance();
           ahoraCa13.set(1966,07 ,12 ,00, 00, 00);
           Date fechaNacimiento13 = ahoraCa13.getTime();        
           Calendar ahoraCa14 = Calendar.getInstance();
           ahoraCa14.set(1966,01 ,22 ,00, 00, 00);
           Date fechaNacimiento14 = ahoraCa14.getTime(); 
           Calendar ahoraCa15 = Calendar.getInstance();
           ahoraCa15.set(1978,04 ,25 ,00, 00, 00);
           Date fechaNacimiento15 = ahoraCa15.getTime(); 
           this.agregarChofer(34567890, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio0, "Jorge Alfredo","Gonzales", unTelefono1, fechaNacimiento, null);
           this.agregarChofer(35678908, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio1, "Leonardo Fabian","Gutierrez", unTelefono2, fechaNacimiento2, null);      
           this.agregarChofer(29890789, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio2, "Kevin Imanol","Sanchez", unTelefono3, fechaNacimiento3, null); 
           this.agregarChofer(25678654, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio3, "Marcelo Sebastian","Castillo", unTelefono4, fechaNacimiento4, null);  
           this.agregarChofer(32456789, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio4, "Cristhian Nahuel","Nolazco", unTelefono5, fechaNacimiento5, null);  
           this.agregarChofer(35678987, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio5, "José David","Panchuk", unTelefono6, fechaNacimiento6, null);         
           this.agregarChofer(25678545, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio6, "Manuel","Escurra", unTelefono7, fechaNacimiento8, null); 
           this.agregarChofer(35678987, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio7, "Juan Jose","Bolanos", unTelefono8, fechaNacimiento7, null); 
           this.agregarChofer(32456765, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio8, "Brian","Dumas", unTelefono9, fechaNacimiento9, null);
           this.agregarChofer(31456765, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio9, "Carlos Agustin","Benites", unTelefono10, fechaNacimiento9, null);

           this.agregarChofer(34213465, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio10, "Ivan Geremias","Lopez", unTelefono11, fechaNacimiento10, null);
           this.agregarChofer(23456789, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio11, "Hernan","De Nervaes", unTelefono12, fechaNacimiento11, null);      
           this.agregarChofer(25643789, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio12, "Ernesto","Guevara", unTelefono13, fechaNacimiento12, null); 
           this.agregarChofer(25111654, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio20, "Ricardo Sebastian","Somerville", unTelefono14, fechaNacimiento13, null);  
           this.agregarChofer(32222789, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio14, "Roberto Nahuel","Natalazco", unTelefono15, fechaNacimiento14, null);  
           this.agregarChofer(35338987, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio15, "Alejandro David","Boca", unTelefono16, fechaNacimiento15, null);         
           this.agregarChofer(25456788, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio16, "Hector","Rodriguez", unTelefono17, fechaNacimiento8, null); 
           this.agregarChofer(35678888, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio16, "Geremias","Xeneixe", unTelefono18, fechaNacimiento7, null); 
           this.agregarChofer(32456999, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio16, "Matias","Rowling", unTelefono19, fechaNacimiento9, null);
           this.agregarChofer(31499999, tipoDoc, unSex, unEstadoCiv1, unaNac, unDomicilio17, "Alexis Agustin","Piris", unTelefono20, fechaNacimiento9, null);       
        }
    }

    //</editor-fold>
        

}
