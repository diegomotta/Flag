  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.*;
import static Interface.JDialogCaja.cargarTablaCaja;
import static Interface.JDialogCaja.esEntero;

//import Reportes.reporteCliente;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;


/**
 *
 * @author garba
 */
public class JInternalFrameViaje extends javax.swing.JInternalFrame implements  ActionListener {
    
    private Caja unaCaja = null;
    private Remiseria remiseria;
    private Utilidad utilidades;
    private java.awt.Frame parent;
    private boolean modal;
    private JButton btnViajes;
    private Operario unOperario;
    private Maestro unMaestro;
    private Collection movilesSegunDisponibilidad;
    LinkedList <ImprimirKmsRecorridosSegunHoraFecha> listaMovilUnicoConRecorridos = new LinkedList<>();    
    LinkedList <ImprimirViajes> listaViajesReportes = new LinkedList<>();
    LinkedList <ImprimirKilometrosRecorridos> listaServicio = new LinkedList<>();

    /**
     * Creates new form JInternalFrameViaje
     * @param remiseria
     * @param utilidades
     * @param btnViajes
     * @param unOperario
     */
    public JInternalFrameViaje(Remiseria remiseria,Utilidad utilidades,JButton btnViajes,Operario unOperario, Maestro unMaestro) {
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unOperario = unOperario;
        this.btnViajes = btnViajes;
        this.unMaestro = unMaestro;
        this.unaCaja = remiseria.getCajaPrincipal();

        initComponents();
        btnViajes.setEnabled(false);
        jTabbedPane1.setSelectedIndex(jTabbedPane1.indexOfComponent(jPanel7));
        this.cargarDisponibles();
        this.cargarTransito();
        this.cargarDescanso();
        this.cargarSinServicio();
        this.cargarReservas();
        this.cargarReservasTransito();
        this.cargarZonas();
        btnEstado.setEnabled(false);
        btnCancelarViaje.setEnabled(false);
        btnFinalizarViaje.setEnabled(false);
        btnCambiarEstado.setEnabled(false);
        btnIniciarTurno.setEnabled(false);
        hasta();
        this.cargarTablaViajes(tablaViajesRealizados, listarViajes());
        Pais pais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
        this.cargarCiudadesCombo(pais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()));
        cmbTipoDeCliente.addItem("Pasajero");
        cmbTipoDeCliente.addItem("Cliente");
         jdcFecha.setPreferredSize(new Dimension(50,24));
         panelDias.setPreferredSize(new Dimension(227, 140));
         //jPanel12.setPreferredSize(new Dimension(162, 525));
         txtNombreEsporadico.setPreferredSize(new Dimension(150,24));
         cmbHoraInicio.setPreferredSize(new Dimension(50, 24));
         cmbHoraFin.setPreferredSize(new Dimension(50, 24));
         cmbMinutoInicio.setPreferredSize(new Dimension(50, 24));    
         cmbMinutoFin.setPreferredSize(new Dimension(50, 24));   
         AutoCompleteDecorator.decorate(this.cmbPaisDestino);
         AutoCompleteDecorator.decorate(this.cmbProvinciaDestino);
         AutoCompleteDecorator.decorate(this.cmbCiudadDestino);
         AutoCompleteDecorator.decorate(this.cmbCiudad);
         AutoCompleteDecorator.decorate(this.cmbBarrio);
         AutoCompleteDecorator.decorate(this.cmbDireccion);
         AutoCompleteDecorator.decorate(this.cmbDireccionCliente);
         AutoCompleteDecorator.decorate(this.cmbCliente);
         AutoCompleteDecorator.decorate(this.cmbHoraInicio);
         AutoCompleteDecorator.decorate(this.cmbHoraFin);
         AutoCompleteDecorator.decorate(this.cmbMinutoInicio);
         AutoCompleteDecorator.decorate(this.cmbMinutoFin);
         AutoCompleteDecorator.decorate(this.cmbChofer);
         AutoCompleteDecorator.decorate(this.cbxMovil);
         jPanel11.setEnabled(false);
         jPanel11.setVisible(false);
         this.cargarChoferesCombo();
         this.cargarPaisDestinoCombo();
         this.cargarTipoViaje();
         this.cargarRodados();   
         checkRepetir.setSelected(false);
         jdcFecha.setEnabled(true);
         panelDias.setEnabled(false);
         panelDias.setVisible(false);
         lunes.setEnabled(false);
         martes.setEnabled(false);
         miercoles.setEnabled(false);
         jueves.setEnabled(false);
         viernes.setEnabled(false);
         sabado.setEnabled(false);
         domingo.setEnabled(false);   
         this.cargarKilometrosRecorridos();
         dp_desdeKmsRec.setEnabled(false);
         dp_hastaKmsRec.setEnabled(false);
         fechaDeterminado.setDate(utilidades.getFechaHoraActual());
         this.hastaKm();
         checkPeriodoViaje.setSelected(true);
         fechaDeterminadoViaje.setEnabled(false);
        this.cargarKilometrosRecorridosSegunPeriodoOFechaFija();   
        fechaDeterminado.setDate(utilidades.getFechaHoraActual());
        fechaDeterminadoViaje.setDate(utilidades.getFechaHoraActual());
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);
        jXTaskPane4.setCollapsed(true);
        this.hastaServicio();
        fechaDeterminadoServico.setDate(utilidades.getFechaHoraActual());
        this.cargarKilometrosRecorridosEnServicioFechaFijaYHoras();
        checkPeriodoServicio.setSelected(true);
        dp_desdeServicio.setEnabled(true);
        dp_hastaServicio.setEnabled(true);
        fechaDeterminadoServico.setEnabled(false);
        cmbChofer.setEnabled(false);
        hsDesde.setEnabled(false);
        minDesde.setEnabled(false); 
        hrHasta.setEnabled(false);  
        minHasta.setEnabled(false);    
        checkPeriodo1.setSelected(false);
        

         if (es_delDia(loteVigente())) 
         {                        
             jButton11.setEnabled(false);
             btnIniciarTurno.setEnabled(true);
         }              
         else 
        {
             jButton11.setEnabled(true);
             btnIniciarTurno.setEnabled(false);
        }    

        if ( this.unOperario != null) 
        {
               Object unUsuario = this.unOperario;
               if (unUsuario instanceof Operario) 
               {
                   Map<String, Permisos> permisos = ((Operario) unUsuario).getUnRol().getPermisos();
                   if (!permisos.containsKey("permitirGestionDeClientes")) {
                       btnClientesReserva.setEnabled(false);
                       btnClientesReserva.setVisible(false);
                       btnClientesReserva1.setEnabled(false);
                       btnClientesReserva1.setVisible(false);
                   } else {
                       btnClientesReserva.setEnabled(true);
                       btnClientesReserva.setVisible(true);
                       btnClientesReserva1.setEnabled(true);
                       btnClientesReserva1.setVisible(true);                       
                   } 
               }
        }        

   }
    
    private void hastaKm()
    {
        Date ahora = new Date();
        dp_desdeKmsRec.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hastaKmsRec.setDate(m.getTime());
    }
    
    private void hastaServicio()
    {
        Date ahora = new Date();
        dp_desdeServicio.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hastaServicio.setDate(m.getTime());
    }    
    
    public void cargarZonas()
    {
        cbxZona.removeAllItems();
        Collection zonas = remiseria.getZonas().values();
        if(zonas != null)
        {
            Zona aux = null;
            Iterator iter = zonas.iterator();
            while (iter.hasNext())
            {
                aux = (Zona) iter.next();
                cbxZona.addItem(aux);
            }
        }
    }
    
     public void cargarDisponibles()
     {
        Collection moviles = remiseria.buscarMovilesDisponibles(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaDisponibles.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Chofer");
        modelo.addColumn("Zona");
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            Object [] fila = new Object[5];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnaMarca().getNombreMarca();
            fila[2] = aux.getUnModelo().getNombreModelo();
            fila[3] = aux.getUnChofer().getNombre()+" "+aux.getUnChofer().getApellido();
            if(aux.getUnaZona()!= null)
            {
                fila[4] = aux.getUnaZona().getNombreZona();
            }
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaDisponibles.setModel(modelo);
    }
    
    public void cargarDescanso()
    {
        Collection moviles = remiseria.buscarMovilesDescanso();
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaDescanso.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Chofer");
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            Object [] fila = new Object[4];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnaMarca().getNombreMarca();
            fila[2] = aux.getUnModelo().getNombreModelo();
            fila[3] = aux.getUnChofer().getNombre()+ " "+ aux.getUnChofer().getApellido();
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaDescanso.setModel(modelo);
    }
    
    public void cargarSinServicio(){
        Collection moviles = remiseria.buscarMovilesSinServicio(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaSinServicio.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Tipo de rodado");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Chofer");
        modelo.addColumn("Alquilado");
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            Object [] fila = new Object[6];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnRodado().getNombreRodado();
            fila[2] = aux.getUnaMarca().getNombreMarca();
            fila[3] = aux.getUnModelo().getNombreModelo();
            if (aux.getUnChofer() == null)
            {
                fila[4] = "Sin chofer";
            }
            else
            {
                fila[4] = aux.getUnChofer().getNombre()+ " " +aux.getUnChofer().getApellido() ;
            }
            if(aux.isAlquilado()==false)
            {
                 fila[5] = "No";
            }
            else
            {
                 fila[5] = "Si";
            }
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaSinServicio.setModel(modelo);
    }

public void cargarSinServicioBuscado(List filtro){
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaSinServicio.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Chofer");
        modelo.addColumn("Alquilado");
        Movil aux = null;
        Iterator iter = filtro.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            Object [] fila = new Object[5];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnaMarca().getNombreMarca();
            fila[2] = aux.getUnModelo().getNombreModelo();
            if (aux.getUnChofer() == null)
            {
                fila[3] = "Sin chofer";
            }
            else
            {
                fila[3] = aux.getUnChofer().getNombre()+ " " +aux.getUnChofer().getApellido() ;
            }
            if(aux.isAlquilado()==false)
            {
            fila[4] = "No";
            }
            else
            {
                 fila[4] = "Si";
            }
            modelo.addRow(fila);
            }
        modelo.rowsRemoved(null);
        tablaSinServicio.setModel(modelo);
    }    
    public void cargarTransito(){
        Collection moviles = remiseria.buscarViajesActivos(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaTransito.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Viaje N°");
        modelo.addColumn("Chofer");
        modelo.addColumn("Hora");
        modelo.addColumn("Origen");
        modelo.addColumn("Destino");
        modelo.addColumn("Utilidad");
        modelo.addColumn("Asignado");
        Viaje aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Viaje) iter.next();
            Object [] fila = new Object[8];
            fila[0] = aux.obtenerNumeroMovil();
            fila[1] = aux.getNumeroViaje();
            fila[2] = aux.getUnChofer().getNombre()+" "+aux.getUnChofer().getApellido() ;
            fila[3] = utilidades.getHora(aux.getFechaHora());
            fila[4] = aux.getUnaZona().getNombreZona();
            fila[5] = aux.getUnDomiclio().getUnaCiudad().getNombreCiudad()+" - "+aux.getUnDomiclio().getUnBarrio().getNombreBarrio()+" - "+ aux.getUnDomiclio().getUnaDireccionViaje().getDireccion();
            if(aux.getUnCliente()!= null)
            {
                fila[6] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje() +" "+ aux.getUnCliente();
            }
            else
            {
                fila[6] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje();
            }
            if (aux.getAsignadoBase().equals("Base"))
            {
                fila[7] = "Base";
            }else{
                fila[7] = "Particular";
            }

            modelo.addRow(fila);
            }
        modelo.rowsRemoved(null);
        tablaTransito.setModel(modelo);
        tablaTransito.getColumn("Móvil N°").setMaxWidth(80);
        tablaTransito.getColumn("Viaje N°").setMaxWidth(80);        
        tablaTransito.getColumn("Chofer").setMaxWidth(200);   
        tablaTransito.getColumn("Hora").setMaxWidth(60);  
        tablaTransito.getColumn("Origen").setMaxWidth(80);  
        tablaTransito.getColumn("Destino").setMaxWidth(350);      
        tablaTransito.getColumn("Utilidad").setMaxWidth(230); 
        tablaTransito.getColumn("Asignado").setMaxWidth(80);     
    }
    
public void cargarReservas()
    {
        limpiar_tabla(tablaReservas);
        Collection reservas = remiseria.buscarReservas();
        if(reservas != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaReservas.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 10); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("N°");
            modelo.addColumn("Cliente");
            modelo.addColumn("Domicilio");
            modelo.addColumn("Destino");
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Tipo de viaje");
            modelo.addColumn("Fecha");
            modelo.addColumn("Inicio");
            modelo.addColumn("Fin");
            modelo.addColumn("Estado");
            Reserva aux = null;
            Iterator iter = reservas.iterator();
            while (iter.hasNext())
            {
                aux = (Reserva) iter.next();
                if(aux.getEstado().equals("pendiente") || aux.getEstado().equals("vencido") ||aux.getEstado().equals("cancelado") )
                {
                    Object [] fila = new Object[11];
                    fila[0] = aux.getNumeroReserva();
                    if(aux.getNombreClienteEsporadicto()!= null)
                    {
                        fila[1] = aux.getNombreClienteEsporadicto();
                    } 
                    else
                    {
                       if(aux.getUnCliente()!= null)
                       {
                            fila[1]= aux.getUnCliente().getNombre() +" "+aux.getUnCliente().getApellido()  ;
                       }
                    }
                    fila[2] = aux.getUnaDireccionViaje() +" - "+ aux.getUnPais() + " - " + aux.getUnaProvincia() + " - " + aux.getUnaCiudad() + " - "+ aux.getUnBarrio();
                    if(aux.getUnPaisDestino() != null && aux.getUnaProvinciaDestino() != null && aux.getUnaCiudadDestino() != null)
                    {
                        fila[3] = aux.getUnPaisDestino() +" - "+aux.getUnaProvinciaDestino()+" - "+aux.getUnaCiudadDestino()+ " - "+ aux.getDireccionDestino();
                    }
                    else
                    {
                        fila[3] = "Destino sin especificar";
                    }
                    if (aux.getUnMovil() == null)
                    {
                        fila[4] = "Móvil sin especificar";
                    }
                    else
                    {
                        fila[4] = aux.getUnMovil().getNumeroMovil();
                    }
                    if( aux.getUnChofer()!= null)
                    {
                        fila[5] = aux.getUnChofer();
                    }
                    else
                    {
                        fila[5] = "Chofer sin especificar";
                    }
                    fila[6] = aux.getUnTipoUtilidad().getNombreTipoUtilidad();
                    if(aux.getDias()== null)
                    {
                        if(aux.getFechaInicio()!= null)
                        {
                            fila[7] = utilidades.getFecha(aux.getFechaInicio());      
                        }
                    }
                    else
                    {
                        String dias =  aux.pasarString().toString();
                        String replace = dias.replace("[", "");
                        String replace1 = replace.replace("]", "");
                        fila[7] = (replace1);             
                    }
                    fila[8] = utilidades.getHora(aux.getHoraInicio());
                    fila[9] = utilidades.getHora(aux.getHoraFin());
                    fila[10] = aux.getEstado();
                    modelo.addRow(fila);
                }
                else
                {
                
                }
            }    
            modelo.rowsRemoved(null);
            tablaReservas.setModel(modelo);
            tablaReservas.getColumn("N°").setMaxWidth(30);
            tablaReservas.getColumn("Cliente").setMaxWidth(120);
            tablaReservas.getColumn("Domicilio").setMaxWidth(500);
            tablaReservas.getColumn("Destino").setMaxWidth(400);
            tablaReservas.getColumn("Móvil N°").setMaxWidth(60);
            tablaReservas.getColumn("Chofer").setMaxWidth(180); 
            tablaReservas.getColumn("Tipo de viaje").setMaxWidth(90);
            tablaReservas.getColumn("Fecha").setMaxWidth(200);
            tablaReservas.getColumn("Inicio").setMaxWidth(45);     
            tablaReservas.getColumn("Fin").setMaxWidth(45);
            tablaReservas.getColumn("Estado").setMaxWidth(80);   
        }
    }



public void cargarReservasTransito()
    {
        try
        {
            limpiar_tabla(tablaReservasActivas);
            Collection reservas = remiseria.buscarReservasTransito();
            if(reservas != null)
            {
                DefaultTableModel modelo = new DefaultTableModel(){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                JTableHeader th; 
                th = tablaReservasActivas.getTableHeader(); 
                Font fuente = new Font("Tahoma", Font.BOLD, 10); 
                th.setForeground(java.awt.Color.BLACK);
                th.setBackground(java.awt.Color.white);
                th.setFont(fuente); 
                modelo.addColumn("N°");
                modelo.addColumn("Cliente");
                modelo.addColumn("Domicilio");
                modelo.addColumn("Destino");
                modelo.addColumn("Móvil N°");
                modelo.addColumn("Chofer");
                modelo.addColumn("Tipo de viaje");
                modelo.addColumn("Fecha");
                modelo.addColumn("Inicio");
                modelo.addColumn("Fin");
                modelo.addColumn("Estado");
                Reserva aux = null;
                Iterator iter = reservas.iterator();
                while (iter.hasNext())
                {
                    aux = (Reserva) iter.next();
                    Object [] fila = new Object[11];
                    fila[0] = aux.getNumeroReserva();
                    if(aux.getNombreClienteEsporadicto()!= null)
                    {
                        fila[1] = aux.getNombreClienteEsporadicto();
                    } 
                    else
                    {
                       if(aux.getUnCliente()!= null)
                       {
                            fila[1]= aux.getUnCliente().getNombre() +" "+aux.getUnCliente().getApellido()  ;
                       }
                    }
                    fila[2] = aux.getUnaDireccionViaje() +" - "+ aux.getUnPais() + " - " + aux.getUnaProvincia() + " - " + aux.getUnaCiudad() + " - "+ aux.getUnBarrio();
                    if(aux.getUnPaisDestino() != null && aux.getUnaProvinciaDestino() != null && aux.getUnaCiudadDestino() != null)
                    {
                        fila[3] = aux.getUnPaisDestino() +" - "+aux.getUnaProvinciaDestino()+" - "+aux.getUnaCiudadDestino()+ " - "+ aux.getDireccionDestino();
                    }
                    else
                    {
                        fila[3] = "Destino sin especificar";
                    }
                    if (aux.getUnMovil() == null)
                    {
                        fila[4] = "Móvil sin especificar";
                    }
                    else
                    {
                        fila[4] = aux.getUnMovil().getNumeroMovil();
                    }
                    if( aux.getUnChofer()!= null)
                    {
                        fila[5] = aux.getUnChofer();
                    }
                    else
                    {
                        fila[5] = "Chofer sin especificar";
                    }
                    fila[6] = aux.getUnTipoUtilidad().getNombreTipoUtilidad();
                    if(aux.getFechaInicio()!= null)
                    {
                        fila[7] = utilidades.getFecha(aux.getFechaInicio());      
                    }
                    else
                    {
                        String dias =  aux.pasarString().toString();
                        String replace = dias.replace("[", "");
                        String replace1 = replace.replace("]", "");
                        fila[7] = (replace1);             
                    }
                    fila[8] = utilidades.getHora(aux.getHoraInicio());
                    fila[9] = utilidades.getHora(aux.getHoraFin());
                    fila[10] = aux.getEstado();
                    modelo.addRow(fila);
               }     

                    modelo.rowsRemoved(null);
                    tablaReservasActivas.setModel(modelo);
                    tablaReservasActivas.getColumn("N°").setMaxWidth(30);
                    tablaReservasActivas.getColumn("Cliente").setMaxWidth(120);
                    tablaReservasActivas.getColumn("Domicilio").setMaxWidth(400);
                    tablaReservasActivas.getColumn("Destino").setMaxWidth(400);
                    tablaReservasActivas.getColumn("Móvil N°").setMaxWidth(60);
                    tablaReservasActivas.getColumn("Chofer").setMaxWidth(180); 
                    tablaReservasActivas.getColumn("Tipo de viaje").setMaxWidth(90);
                    tablaReservasActivas.getColumn("Fecha").setMaxWidth(200);
                    tablaReservasActivas.getColumn("Inicio").setMaxWidth(45);     
                    tablaReservasActivas.getColumn("Fin").setMaxWidth(45);
                    tablaReservasActivas.getColumn("Estado").setMaxWidth(80);  
                }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar la tabla de Reservas activas");
        }
    }

     public void cargarViajesRealizados()
     {
        Collection viajes = remiseria.buscarViajesActivos(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaViajesRealizados.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Chofer");
        modelo.addColumn("Origen");
        modelo.addColumn("Destino");
        modelo.addColumn("Utilidad");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora");
        modelo.addColumn("Inicio");
        modelo.addColumn("Precio del Viaje");        
        Viaje aux = null;
        Iterator iter = viajes.iterator();
        while (iter.hasNext())
        {
            aux = (Viaje) iter.next();
            Object [] fila = new Object[9];
            fila[0] = aux.getUnMovil().getNumeroMovil();
            fila[1] = aux.getUnChofer().getNombre() +" "+aux.getUnChofer().getApellido();
            fila[2] = aux.getUnaZona().getNombreZona();
            fila[3] = aux.getUnDomiclio().getUnaCiudad().getNombreCiudad()+ " "+ aux.getUnDomiclio().getUnBarrio().getNombreBarrio()+ " "+ aux.getUnDomiclio().getUnaDireccionViaje().getDireccion();
            fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje();
            fila[5] = utilidades.getFecha(aux.getFechaHora());
            fila[6] = utilidades.getHora(aux.getFechaHora());
            fila[7] = aux.getAsignadoBase();
            fila[8] = aux.getTarifa();
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaViajesRealizados.setModel(modelo);
    }
    
   public void cancelarViaje(){
        int fila = tablaTransito.getSelectedRow();
        int numeroViaje = (int) tablaTransito.getValueAt(fila,1);
        remiseria.cancelarViaje(numeroViaje);
        this.cargarDisponibles();
        this.cargarTransito();
        cargarTablaViajes(tablaViajesRealizados, listarViajes());
        btnEstado.setEnabled(false);
        btnCancelarViaje.setEnabled(false);
        btnFinalizarViaje.setEnabled(false);
    }
    
    public Movil obtenerMovil(){
        int fila = (int) tablaSinServicio.getSelectedRow();
        int numeroMovil = (int) tablaSinServicio.getValueAt(fila, 0);
        Movil unMovil = remiseria.buscarMovil(numeroMovil);
        return unMovil;
    }
    
    public Movil obtenerMovilDisponible(){
        int fila = (int)tablaDisponibles.getSelectedRow();
        Movil unMovil = null;
        int numeroMovil = (int) tablaDisponibles.getValueAt(fila, 0);
        unMovil = remiseria.buscarMovil(numeroMovil);
        return unMovil;
    }
    
    public Movil obtenerMovilDescanso(){
        int fila = (int) tablaDescanso.getSelectedRow();
        int numeroMovil = (int) tablaDescanso.getValueAt(fila, 0);
        Movil unMovil = remiseria.buscarMovil(numeroMovil);
        return unMovil;
    }
    
    public Viaje obtenerViaje(){
        int fila =(int) tablaTransito.getSelectedRow();
        int numeroViaje = (int) tablaTransito.getValueAt(fila, 1);
        Viaje unViaje = remiseria.buscarViaje(numeroViaje);
        return unViaje;
    }
    
    public Reserva obtenerReservaActiva()
    {
        try
        {
            int fila =(int) tablaReservasActivas.getSelectedRow();
            int numeroViaje = (int) tablaReservasActivas.getValueAt(fila, 0);
            Reserva unaReserva = remiseria.getReservas().get(numeroViaje);
            return unaReserva;
        }
        catch (Exception es)
        {
            JOptionPane.showMessageDialog(null, "Error al intentar acceder a una posicion inexistente de la tabla Reservas activas.");
        }
        return null;
    }
    
    
    public Reserva obtenerReserva(){
        int fila = (int) tablaReservas.getSelectedRow();
        int numeroReserva = (int) tablaReservas.getValueAt(fila, 0);
        Reserva unaReserva = remiseria.buscarReserva(numeroReserva);
        return unaReserva;
    }
    
    public void eleminarReserva(){
         int eleccion = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar la Reserva seleccionada?");
         if ( eleccion == 0)
         {    
           int numeroReserva = (int) tablaReservas.getValueAt(tablaReservas.getSelectedRow(), 0);
           Reserva unaReserva = remiseria.buscarReserva(numeroReserva);
           
           remiseria.eliminarReserva(unaReserva);
           cargarReservas();
         }
    }
     
    private void hasta() {
        Date ahora = new Date();
        dp_desde.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hasta.setDate(m.getTime());
    }
    
    public void limpiar_tabla(JTable tabla) {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }

 public void cargarTablaViajes(JTable tabla, List<Viaje> lista) {
        limpiar_tabla(tabla);
        Collection viajes = lista; 
        if(lista != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tabla.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Origen");
            modelo.addColumn("Destino");
            modelo.addColumn("Utilidad");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            modelo.addColumn("Inicio");
            modelo.addColumn("Costo");        
            Viaje aux = null;
            Iterator iter = viajes.iterator();

            while (iter.hasNext())
            {
                aux = (Viaje) iter.next();
                Object [] fila = new Object[9];
                fila[0] = aux.getUnMovil().getNumeroMovil();
                fila[1] = aux.getUnChofer();//.getNombre() +" "+aux.getUnChofer().getApellido();
                if(aux.getUnaZona()!=null)
                {
                     fila[2] = aux.getUnaZona().getNombreZona();
                }
                else
                {
                     fila[2] = aux.getInicioReserva();  
                }
                fila[3] = aux.getUnDomiclio().getUnaCiudad().getNombreCiudad()+ " "+ aux.getUnDomiclio().getUnBarrio().getNombreBarrio()+ " "+ aux.getUnDomiclio().getUnaDireccionViaje().getDireccion();
                if(aux.getUnaUtilidadViaje() != null)
                {
                    if(aux.getUnaUtilidadViaje().getNombreUtilidadViaje().equals("Cliente"))
                    {
                        fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje()+ " : " + aux.getUnCliente();
                    }
                    else
                    {
                        fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje();
                    }
                }
                else
                {
                    fila[4] = aux.getTipoClienteReserva();
                }
                fila[5] = utilidades.getFecha(aux.getFechaHora());
                fila[6] = utilidades.getHora(aux.getFechaHora());    
                if(aux.getAsignadoBase()!= null)
                {
                    fila[7] = aux.getAsignadoBase();
                }
                else
                {
                    fila[7] = "Base";
                }
                fila[8] ="$ " +aux.getTarifa();
                modelo.addRow(fila);
            }
            modelo.rowsRemoved(null);
            tabla.setModel(modelo);
            tabla.setModel(modelo);
            tabla.getColumn("Móvil N°").setMaxWidth(60);
            tabla.getColumn("Chofer").setMaxWidth(150);
            tabla.getColumn("Origen").setMaxWidth(150);
            tabla.getColumn("Destino").setMaxWidth(400);
            tabla.getColumn("Utilidad").setMaxWidth(300);
            tabla.getColumn("Fecha").setMaxWidth(100); 
            tabla.getColumn("Hora").setMaxWidth(60);
            tabla.getColumn("Inicio").setMaxWidth(100);
            tabla.getColumn("Costo").setMaxWidth(150);     
        } 
    }   
    
    
 public void cargarTablaViajes() {
      
        List<Viaje> lista = listarViajes();
        limpiar_tabla(tablaViajesRealizados);
        Collection viajes = lista; 
        if(lista != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaViajesRealizados.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Origen");
            modelo.addColumn("Destino");
            modelo.addColumn("Utilidad");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            modelo.addColumn("Inicio");
            modelo.addColumn("Costo");        
            Viaje aux = null;
            Iterator iter = viajes.iterator();

            while (iter.hasNext())
            {
                aux = (Viaje) iter.next();
                if(aux.getEstado().equals("cancelado"))
                {
                    Object [] fila = new Object[9];
                    fila[0] = aux.getUnMovil().getNumeroMovil();
                    fila[1] = aux.getUnChofer();//.getNombre() +" "+aux.getUnChofer().getApellido();
                    if(aux.getUnaZona()!=null)
                    {
                         fila[2] = aux.getUnaZona().getNombreZona();
                    }
                    else
                    {
                         fila[2] = aux.getInicioReserva();  
                    }
                    fila[3] = aux.getUnDomiclio().getUnaCiudad().getNombreCiudad()+ " "+ aux.getUnDomiclio().getUnBarrio().getNombreBarrio()+ " "+ aux.getUnDomiclio().getUnaDireccionViaje().getDireccion();
                    if(aux.getUnaUtilidadViaje() != null)
                    {
                        if(aux.getUnaUtilidadViaje().getNombreUtilidadViaje().equals("Cliente"))
                        {
                            fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje()+ " : " + aux.getUnCliente();
                        }
                        else
                        {
                            fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje();
                        }
                    }
                    else
                    {
                        fila[4] = aux.getTipoClienteReserva();
                    }
                    fila[5] = utilidades.getFecha(aux.getFechaHora());
                    fila[6] = utilidades.getHora(aux.getFechaHora());    
                    if(aux.getAsignadoBase()!= null)
                    {
                        fila[7] = aux.getAsignadoBase();
                    }
                    else
                    {
                        fila[7] = "Base";
                    }
                    fila[8] ="$ " +aux.getTarifa();
                    modelo.addRow(fila);

                }
            }
            modelo.rowsRemoved(null);
            tablaViajesRealizados.setModel(modelo);
            tablaViajesRealizados.setModel(modelo);
            tablaViajesRealizados.getColumn("Móvil N°").setMaxWidth(60);
            tablaViajesRealizados.getColumn("Chofer").setMaxWidth(250);
            tablaViajesRealizados.getColumn("Origen").setMaxWidth(150);
            tablaViajesRealizados.getColumn("Destino").setMaxWidth(400);
            tablaViajesRealizados.getColumn("Utilidad").setMaxWidth(300);
            tablaViajesRealizados.getColumn("Fecha").setMaxWidth(100); 
            tablaViajesRealizados.getColumn("Hora").setMaxWidth(60);
            tablaViajesRealizados.getColumn("Inicio").setMaxWidth(100);
            tablaViajesRealizados.getColumn("Costo").setMaxWidth(150);     
        }
         
    }   
 
        public void cargarViajesReporte(List<Viaje> lista) {
        Collection viajes = lista; 
        if(lista != null)
        {      
            Viaje aux = null;
            Iterator iter = viajes.iterator();
            while (iter.hasNext())
            {
                aux = (Viaje) iter.next();
                Date fec = utilidades.getDate(utilidades.getFecha(aux.getFechaHora()));
                System.out.println(fec);
                System.out.println(dp_desde.getDate());
                if(dp_desde.isEnabled()==true && dp_hasta.isEnabled()==true)
                {
                    if ((fec.getTime() == dp_desde.getDate().getTime()) || (fec.getTime() > dp_desde.getDate().getTime())) 
                    {
                        if ((fec.getTime() == dp_hasta.getDate().getTime()) || (fec.getTime() < dp_hasta.getDate().getTime())) 
                        {
                            Object [] fila = new Object[9];
                            fila[0] = aux.getUnMovil().getNumeroMovil();
                            fila[1] = aux.getUnChofer();//.getNombre() +" "+aux.getUnChofer().getApellido();
                            if(aux.getUnaZona()!=null)
                            {
                                 fila[2] = aux.getUnaZona().getNombreZona();
                            }
                            else
                            {
                                 fila[2] = aux.getInicioReserva();  
                            }
                            fila[3] = aux.getUnDomiclio().getUnaCiudad().getNombreCiudad()+ " "+ aux.getUnDomiclio().getUnBarrio().getNombreBarrio()+ " "+ aux.getUnDomiclio().getUnaDireccionViaje().getDireccion();
                            if(aux.getUnaUtilidadViaje() != null)
                            {
                                if(aux.getUnaUtilidadViaje().getNombreUtilidadViaje().equals("Cliente"))
                                {
                                    fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje()+ " : " + aux.getUnCliente();
                                }
                                else
                                {
                                    fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje();
                                }
                            }
                            else
                            {
                                fila[4] = aux.getTipoClienteReserva();
                            }
                            fila[5] = utilidades.getFecha(aux.getFechaHora());
                            fila[6] = utilidades.getHora(aux.getFechaHora());    
                            if(aux.getAsignadoBase()!= null)
                            {
                                fila[7] = aux.getAsignadoBase();
                            }
                            else
                            {
                                fila[7] = "Base";
                            }
                            fila[8] ="$ " +aux.getTarifa();
                            listaViajesReportes.add(new ImprimirViajes (String.valueOf(aux.getNumeroViaje()),fila[0].toString(),fila[1].toString(),fila[2].toString(),fila[3].toString(),fila[4].toString(),utilidades.getFecha(aux.getFechaHora())+"-"+utilidades.getHora(aux.getFechaHora()),fila[7].toString(),fila[8].toString()));
                        }
                    }   
                }
                else if(fechaDeterminadoViaje.isEnabled()==true)
                {
                        if ((fec.getTime() == fechaDeterminadoViaje.getDate().getTime())) 
                        {
                            Object [] fila = new Object[9];
                            fila[0] = aux.getUnMovil().getNumeroMovil();
                            fila[1] = aux.getUnChofer();//.getNombre() +" "+aux.getUnChofer().getApellido();
                            if(aux.getUnaZona()!=null)
                            {
                                 fila[2] = aux.getUnaZona().getNombreZona();
                            }
                            else
                            {
                                 fila[2] = aux.getInicioReserva();  
                            }
                            fila[3] = aux.getUnDomiclio().getUnaCiudad().getNombreCiudad()+ " "+ aux.getUnDomiclio().getUnBarrio().getNombreBarrio()+ " "+ aux.getUnDomiclio().getUnaDireccionViaje().getDireccion();
                            if(aux.getUnaUtilidadViaje() != null)
                            {
                                if(aux.getUnaUtilidadViaje().getNombreUtilidadViaje().equals("Cliente"))
                                {
                                    fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje()+ " : " + aux.getUnCliente();
                                }
                                else
                                {
                                    fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje();
                                }
                            }
                            else
                            {
                                fila[4] = aux.getTipoClienteReserva();
                            }
                            fila[5] = utilidades.getFecha(aux.getFechaHora());
                            fila[6] = utilidades.getHora(aux.getFechaHora());    
                            if(aux.getAsignadoBase()!= null)
                            {
                                fila[7] = aux.getAsignadoBase();
                            }
                            else
                            {
                                fila[7] = "Base";
                            }
                            fila[8] ="$ " +aux.getTarifa();
                            listaViajesReportes.add(new ImprimirViajes (String.valueOf(aux.getNumeroViaje()),fila[0].toString(),fila[1].toString(),fila[2].toString(),fila[3].toString(),fila[4].toString(),utilidades.getFecha(aux.getFechaHora())+"-"+utilidades.getHora(aux.getFechaHora()),fila[7].toString(),fila[8].toString()));
                        }                
                }                    
            }
        } 
    }  
    
    private List<Viaje> listarViajes() {
        List<Viaje> via = new LinkedList<>();
        for (Viaje l : remiseria.getViajes().values()) {
            if ((l.getFechaHora().getTime() == dp_desde.getDate().getTime()) || (l.getFechaHora().getTime() > dp_desde.getDate().getTime())) {
                if ((l.getFechaHora().getTime() == dp_hasta.getDate().getTime()) || (l.getFechaHora().getTime() < dp_hasta.getDate().getTime())) {
                        via.add(l);
                }
            }
        }
        return via;
    }        
    
    private List<Viaje> listarViajesUnicaFecha() {
        List<Viaje> via = new LinkedList<>();
        for (Viaje l : remiseria.getViajes().values()) {
            if (utilidades.getFecha(l.getFechaHora()).equals(utilidades.getFecha(fechaDeterminadoViaje.getDate()))) {
                        via.add(l);
                }
            
        }
        return via;
    }       
    
public List<ImprimirViajes> cargarViajesTodosReporte() {
        Collection viajes = remiseria.getViajes().values(); 
        if(viajes != null)
        {      
            Viaje aux = null;
            Iterator iter = viajes.iterator();
            while (iter.hasNext())
            {
                aux = (Viaje) iter.next();
                Date fec = utilidades.getDate(utilidades.getFecha(aux.getFechaHora()));

                
                if(dp_desde.isEnabled()==true && dp_hasta.isEnabled()==true)
                {
                    if ((fec.getTime() == dp_desde.getDate().getTime()) || (fec.getTime() > dp_desde.getDate().getTime())) 
                    {
                        if ((fec.getTime() == dp_hasta.getDate().getTime()) || (fec.getTime() < dp_hasta.getDate().getTime())) 
                        {
                            Object [] fila = new Object[9];
                            fila[0] = aux.getUnMovil().getNumeroMovil();
                            fila[1] = aux.getUnChofer();//.getNombre() +" "+aux.getUnChofer().getApellido();
                            if(aux.getUnaZona()!=null)
                            {
                                 fila[2] = aux.getUnaZona().getNombreZona();
                            }
                            else
                            {
                                 fila[2] = aux.getInicioReserva();  
                            }
                            fila[3] = aux.getUnDomiclio().getUnaCiudad().getNombreCiudad()+ " "+ aux.getUnDomiclio().getUnBarrio().getNombreBarrio()+ " "+ aux.getUnDomiclio().getUnaDireccionViaje().getDireccion();
                            if(aux.getUnaUtilidadViaje() != null)
                            {
                                if(aux.getUnaUtilidadViaje().getNombreUtilidadViaje().equals("Cliente"))
                                {
                                    fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje()+ " : " + aux.getUnCliente();
                                }
                                else
                                {
                                    fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje();
                                }
                            }
                            else
                            {
                                fila[4] = aux.getTipoClienteReserva();
                            }
                            fila[5] = utilidades.getFecha(aux.getFechaHora());
                            fila[6] = utilidades.getHora(aux.getFechaHora());    
                            if(aux.getAsignadoBase()!= null)
                            {
                                fila[7] = aux.getAsignadoBase();
                            }
                            else
                            {
                                fila[7] = "Base";
                            }
                            fila[8] ="$ " +aux.getTarifa();
                            listaViajesReportes.add(new ImprimirViajes (String.valueOf(aux.getNumeroViaje()),fila[0].toString(),fila[1].toString(),fila[2].toString(),fila[3].toString(),fila[4].toString(),utilidades.getFecha(aux.getFechaHora())+"-"+utilidades.getHora(aux.getFechaHora()),fila[7].toString(),fila[8].toString()));
                        }
                    }   
                }
                else if(fechaDeterminadoViaje.isEnabled()==true)
                {
                        if ((fec.getTime() == fechaDeterminadoViaje.getDate().getTime())) 
                        {
                            Object [] fila = new Object[9];
                            fila[0] = aux.getUnMovil().getNumeroMovil();
                            fila[1] = aux.getUnChofer();//.getNombre() +" "+aux.getUnChofer().getApellido();
                            if(aux.getUnaZona()!=null)
                            {
                                 fila[2] = aux.getUnaZona().getNombreZona();
                            }
                            else
                            {
                                 fila[2] = aux.getInicioReserva();  
                            }
                            fila[3] = aux.getUnDomiclio().getUnaCiudad().getNombreCiudad()+ " "+ aux.getUnDomiclio().getUnBarrio().getNombreBarrio()+ " "+ aux.getUnDomiclio().getUnaDireccionViaje().getDireccion();
                            if(aux.getUnaUtilidadViaje() != null)
                            {
                                if(aux.getUnaUtilidadViaje().getNombreUtilidadViaje().equals("Cliente"))
                                {
                                    fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje()+ " : " + aux.getUnCliente();
                                }
                                else
                                {
                                    fila[4] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje();
                                }
                            }
                            else
                            {
                                fila[4] = aux.getTipoClienteReserva();
                            }
                            fila[5] = utilidades.getFecha(aux.getFechaHora());
                            fila[6] = utilidades.getHora(aux.getFechaHora());    
                            if(aux.getAsignadoBase()!= null)
                            {
                                fila[7] = aux.getAsignadoBase();
                            }
                            else
                            {
                                fila[7] = "Base";
                            }
                            fila[8] ="$ " +aux.getTarifa();
                            listaViajesReportes.add(new ImprimirViajes (String.valueOf(aux.getNumeroViaje()),fila[0].toString(),fila[1].toString(),fila[2].toString(),fila[3].toString(),fila[4].toString(),utilidades.getFecha(aux.getFechaHora())+"-"+utilidades.getHora(aux.getFechaHora()),fila[7].toString(),fila[8].toString()));
                        }                
                }                    
            }
        }
        return listaViajesReportes;
    }      
    
    //AQUI EMPIEZA LA PARTE DE RESERVAS
    
    public void cargarChoferesCombo()
    {
        try
        {
            cmbChofer.removeAllItems();
            cmbChofer.addItem("Sin chofer");
            List choferes = remiseria.buscarChoferes(); 
            if(choferes != null)
            {
                Chofer unChofer = null;
                Iterator iter = choferes.iterator();
                while(iter.hasNext())
                {
                    unChofer = (Chofer) iter.next();
                    if(unChofer.getUnMovil()!= null)
                    {
                        if (remiseria.choferOcupado2(unChofer) == false)
                        {
                            cmbChofer.addItem(unChofer);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error al cargar el combo despegable de Choferes");
        }
    }
    
    public void cargarTipoViaje()
    {
        cmbTipoViaje.removeAllItems();
        Collection<TipoUtilidad> moviles = remiseria.getTiposUtilidades().values(); 
        if(moviles != null)
        {
            TipoUtilidad unMovil = null;
            Iterator iter = moviles.iterator();
            while(iter.hasNext())
            {
                unMovil = (TipoUtilidad) iter.next();
                cmbTipoViaje.addItem(unMovil);
            }
        }
    }
    
    
    public void cargarRodados ()
    {
        cmbTipoMovil.removeAllItems();
        Collection<Rodado> rod = remiseria.getRodados().values(); 
        Rodado unRodado = null;
        if(rod != null)
        {
            Iterator iter = rod.iterator();
            while(iter.hasNext())
            {
                unRodado = (Rodado) iter.next();
                cmbTipoMovil.addItem(unRodado);
            }
        }
    }
    
    public void buscarMovilesDispReservas()
    {
         Calendar cal = jdcFecha.getCalendar();
         if(cal == null)
         {
             cal = Calendar.getInstance();
         }
//       Calendar to Date Conversion
         int year = cal.get(Calendar.YEAR);
         int month = cal.get(Calendar.MONTH);
         int day = cal.get(Calendar.DATE);
         Date date = new Date(year -1900, month, day);
         Date fechaInicio = date;//utilidades.getDate(utilidades.getFecha((jdcFecha.getDate())));
         String horaInicio = (cmbHoraInicio.getSelectedItem().toString()+":"+cmbMinutoInicio.getSelectedItem().toString());
         String horaFin = (cmbHoraFin.getSelectedItem().toString()+":"+cmbMinutoFin.getSelectedItem().toString());
         movilesSegunDisponibilidad = remiseria.movilesDisponiblesReservas(fechaInicio,horaInicio,horaFin);
         this.cargarMoviles(movilesSegunDisponibilidad);
    }
    
    public void cargarMoviles(Collection moviles)
    {
        cbxMovil.removeAllItems();
        cbxMovil.addItem("Sin móvil");
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            if(aux.getEstado().equals("disponible"))
            {
                cbxMovil.addItem(aux.getNumeroMovil());
            }
        }
    }
   
   public void agregarReserva()
   {
       try{
            boolean ok = true;
            String tipoCliente = cmbTipoDeCliente.getSelectedItem().toString();
            String nombreEsporadico = null;
            Cliente unCliente = null;
            if(cmbCliente.isEnabled()==true)
            {
                unCliente = (Cliente) cmbCliente.getSelectedItem();
            }
            if(txtNombreEsporadico.isEnabled()== true)
            {
                nombreEsporadico=txtNombreEsporadico.getText();
            }
            String estado = "pendiente";
            Calendar cal = jdcFecha.getCalendar();
            if (cal == null)
            {
                cal = Calendar.getInstance();
            }
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DATE);
            Date date = new Date(year -1900, month, day);        
            Date fechaInicio = date;//utilidades.getDate(utilidades.getFecha(jdcFecha.getDate()));
            Date horaInicio = utilidades.getHour(cmbHoraInicio.getSelectedItem().toString()+":"+cmbMinutoInicio.getSelectedItem().toString());
            Date horaFin = utilidades.getHour(cmbHoraFin.getSelectedItem().toString()+":"+cmbMinutoFin.getSelectedItem().toString());
            TipoUtilidad unTipoUtilidad = null;
            if(cmbTipoViaje.isEnabled()==true)
            {
                unTipoUtilidad =(TipoUtilidad) cmbTipoViaje.getSelectedItem();
            }
            Movil unMovil = null;
            Rodado unRodado = (Rodado)cmbTipoMovil.getSelectedItem();
            Chofer unChofer = null;

            if(txtNombreEsporadico.isEnabled()==true)
            {
                if(txtNombreEsporadico.getText().isEmpty() || nombreEsporadico == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha ingresado un Nombre", null,JOptionPane.ERROR_MESSAGE);
                    txtNombreEsporadico.setBackground(java.awt.Color.red);
                    ok= false;
                }
            }

            if(jdcFecha.isEnabled()==true)
            {
                if(fechaInicio == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha ingresado una Fecha", null,JOptionPane.ERROR_MESSAGE);
                    ok= false;
                }
                else
                {
                    if(utilidades.compararFechasConDate(fechaInicio).equals("Menor"))
                    {
                         JOptionPane.showMessageDialog(null, "La Fecha ingresada es menor al Fecha actual", null,JOptionPane.ERROR_MESSAGE);
                        ok= false;
                    }
                }
            }

            if(unTipoUtilidad != null)
            {
                System.out.println(unTipoUtilidad.getNombreTipoUtilidad());
                if(!unTipoUtilidad.getNombreTipoUtilidad().equals("Especial"))
                {
                    if(utilidades.compararHoras(horaInicio, horaFin).equals("Mayor")||utilidades.compararHoras(horaInicio, horaFin).equals("Igual"))
                    {
                        if(utilidades.compararHoras(horaInicio, horaFin).equals("Mayor"))
                        {
                            JOptionPane.showMessageDialog(null, "La Hora Inicio es mayor a la Hora Fin", null,JOptionPane.ERROR_MESSAGE);
                            ok= false;
                        }
                        if(utilidades.compararHoras(horaInicio, horaFin).equals("Igual"))
                        {
                            JOptionPane.showMessageDialog(null, "La Hora Inicio es igual a la Hora Fin", null,JOptionPane.ERROR_MESSAGE);
                            ok= false;
                        }            
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al seleccionar Tipo de Viaje", null,JOptionPane.ERROR_MESSAGE);
                ok= false;
            }
            if(cbxMovil.isEnabled()==true)
            {
                if(cbxMovil.getSelectedItem() != null)
                {
                    if(utilidades.isNumber(cbxMovil.getSelectedItem().toString())==true)
                    {
                        unMovil =(Movil) remiseria.buscarMovil(Integer.parseInt(cbxMovil.getSelectedItem().toString()));
                    }
                }
                else if(cbxMovil.getSelectedItem().equals("Sin móvil"))
                {
                    unMovil = null;
                }            
                else
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un Móvil", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }
            }
            if(cmbChofer.isEnabled()==true)
            {
                if(!cmbChofer.getSelectedItem().equals("Sin chofer"))
                {
                    unChofer = (Chofer) cmbChofer.getSelectedItem();
                }
                else if(cmbChofer.getSelectedItem().equals("Sin chofer"))
                {
                    unChofer = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un Chofer", null,JOptionPane.ERROR_MESSAGE);
                    ok= false;
                }
            }
            Pais unPais = (Pais) remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
            Provincia unaProvincia =(Provincia) (unPais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()));
            Ciudad unaCiudad =null;        
            Barrio unBarrio =null;
            
            if(cmbCliente.isEnabled()!=true)
            {
               unaCiudad =(Ciudad) cmbCiudad.getSelectedItem();        
               unBarrio =(Barrio)cmbBarrio.getSelectedItem();
            }
            DireccionViaje direccionViaje = null;
            Domicilio unDomicilio = null;
            if(cmbDireccion.isEnabled()==true)
            {
                direccionViaje =(DireccionViaje) cmbDireccion.getSelectedItem();
            }
            else
            {
                unDomicilio =(Domicilio) cmbDireccionCliente.getSelectedItem();
            }
            Pais unPaisDestino = null;
            Provincia unaProvinciaDestino = null;
            Ciudad unaCiudadDestino = null;  
            String direccionDestino = null;
            Map<String, Dias> dias = null;

            if(checkRepetir.isSelected()==true && panelDias.isEnabled()==true && panelDias.isVisible()==true)
            {   
                dias = new HashMap<>();
                dias.clear();
                for (Component unComponent : panelDias.getComponents())
                {
                    if (unComponent instanceof JCheckBox) 
                    {
                        if (((JCheckBox) unComponent).isSelected())
                        {
                            Dias nuevoDia = new Dias(unComponent.getName());
                            dias.put(nuevoDia.getDiaSemana(), nuevoDia);
                        }
                    }
                }     
            }


            if(jPanel11.isEnabled()==true && jPanel11.isVisible()==true )
            {
                unPaisDestino = (Pais) (cmbPaisDestino.getSelectedItem());
                unaProvinciaDestino = (Provincia)(cmbProvinciaDestino.getSelectedItem());
                unaCiudadDestino = (Ciudad)cmbCiudadDestino.getSelectedItem();  
                direccionDestino = txtDireccionDestino.getText();
            }

            if(cmbTipoViaje.isEnabled()==true)
            {
                if(unTipoUtilidad == null)
                    {
                        JOptionPane.showMessageDialog(null, "No ha seleccionado una Utilidad", null,JOptionPane.ERROR_MESSAGE);
                        ok= false;
                    }
            }

            if(unRodado == null)
            {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un Rodado", null,JOptionPane.ERROR_MESSAGE);
                ok= false; 
            }

            if(cmbCliente.isEnabled()!= true)
            {
                if(unaCiudad == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Ciudad", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }

                if(unBarrio == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un Barrio", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }
            }
            if(cmbDireccionCliente.isEnabled()==false)
            {
                if(direccionViaje == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Direccion", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }        
            }
            if(cmbDireccionCliente.isEnabled()==true)
            {
                if(unDomicilio == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Direccion del Cliente", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                } 
            }

            if(jPanel11.isEnabled()==true && jPanel11.isVisible()==true)
            {

                if(unPaisDestino == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un Pais de Destino", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }

                if(unaCiudadDestino == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Ciudad de Destino", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }

                if(unaProvinciaDestino == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Provincia de Destino", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }

                if(txtDireccionDestino.getText().isEmpty() || direccionDestino == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha ingresado una Direcion de Destino", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                    txtDireccionDestino.setBackground(java.awt.Color.red);
                }

            }

            if(cmbCliente.isEnabled()==true)
            {
                if(unCliente == null)
                {
                     JOptionPane.showMessageDialog(null, "No ha seleccionado un Cliente", null,JOptionPane.ERROR_MESSAGE);
                     ok= false; 
                }
            }

            if(panelDias.isEnabled()==true)
            {
                if(dias.isEmpty())
                {
                    JOptionPane.showMessageDialog(this, "Debe al menos seleccionar un día para crear la reserva");
                    ok=false;
                }
            }        

            if(ok == true )
            {
                if(unCliente != null)
                {
                    if(dias== null)
                    {
                        remiseria.agregarReserva1("Calle "+unDomicilio.getCalle()+ " N° "+unDomicilio.getNroCasa(), fechaInicio, horaInicio, horaFin, estado, unMovil, unTipoUtilidad, unDomicilio.getUnPais(), unDomicilio.getUnaProvincia(), unDomicilio.getUnaCiudad(), unDomicilio.getUnBarrio(), unChofer, unPaisDestino, unaProvinciaDestino, unaCiudadDestino, direccionDestino,unCliente, nombreEsporadico, unRodado, tipoCliente);
                        this.cargarReservas();
                        remiseria.getUnaVentana().cargarSinServicio();
                        JOptionPane.showMessageDialog(null,"Se ha agregado una nueva Reserva", null, JOptionPane.INFORMATION_MESSAGE);
                        btnAgregarReserva.setEnabled(true);
                         jXTaskPane4.setCollapsed(true);
                        btnModificarReserva.setEnabled(true);
                        btnEliminarReserva.setEnabled(true);
                        btnFinalizarReserva.setEnabled(true);
                        tablaReservasActivas.setEnabled(true);
                        tablaReservas.setEnabled(true);
                        btnGuardarModificaciones.setEnabled(false);
                        btnGuardarChofer.setEnabled(false);   
                        tablaReservas.setRowSelectionInterval(0, 0);
                        btnCancelarViaje1.setEnabled(true);
                        this.limpiar();
                    }
                    else
                    {
                        remiseria.agregarReservaSegunDia("Calle "+unDomicilio.getCalle()+ " N° "+unDomicilio.getNroCasa(), horaInicio, horaFin, estado, null, unTipoUtilidad, unDomicilio.getUnPais(), unDomicilio.getUnaProvincia(), unDomicilio.getUnaCiudad(), unDomicilio.getUnBarrio(), null, unPaisDestino, unaProvinciaDestino, unaCiudadDestino,direccionDestino, unCliente, unRodado, tipoCliente,dias);
                        this.cargarReservas();
                        remiseria.getUnaVentana().cargarSinServicio();
                        JOptionPane.showMessageDialog(null,"Se ha agregado una nueva Reserva", null, JOptionPane.INFORMATION_MESSAGE);
                        btnAgregarReserva.setEnabled(true);
                          jXTaskPane4.setCollapsed(true);
                        btnModificarReserva.setEnabled(true);
                        btnEliminarReserva.setEnabled(true);
                        btnFinalizarReserva.setEnabled(true);
                        tablaReservasActivas.setEnabled(true);
                        tablaReservas.setEnabled(true);
                        btnGuardarModificaciones.setEnabled(false);
                        btnGuardarChofer.setEnabled(false); 
                        btnCancelarViaje1.setEnabled(true);
                        this.limpiar();
                    }    
                }
                else
                {
                    if(unChofer != null && unMovil != null)
                    {
                        remiseria.agregarReserva(direccionViaje, fechaInicio, horaInicio, horaFin, estado, unMovil, unTipoUtilidad, unPais, unaProvincia, unaCiudad, unBarrio, unChofer, unPaisDestino, unaProvinciaDestino, unaCiudadDestino,direccionDestino, unCliente, nombreEsporadico, unRodado, tipoCliente);
                        this.cargarReservas();
                        remiseria.getUnaVentana().cargarSinServicio();
                        JOptionPane.showMessageDialog(null,"Se ha agregado una nueva Reserva para Cliente Esporádico", null, JOptionPane.INFORMATION_MESSAGE);
                        btnAgregarReserva.setEnabled(true);
                          jXTaskPane4.setCollapsed(true);
                        btnModificarReserva.setEnabled(true);
                        btnEliminarReserva.setEnabled(true);
                        btnFinalizarReserva.setEnabled(true);
                        tablaReservasActivas.setEnabled(true);
                        tablaReservas.setEnabled(true);
                        btnGuardarModificaciones.setEnabled(false);
                        btnGuardarChofer.setEnabled(false);    
                        tablaReservas.setRowSelectionInterval(0, 0);
                        btnCancelarViaje1.setEnabled(true);
                        this.limpiar();                
                    }
                    else if(unChofer == null && unMovil == null && cmbChofer.getSelectedItem().equals("Sin chofer") &&cbxMovil.getSelectedItem().equals("Sin móvil") )
                    {
                       remiseria.agregarReserva(direccionViaje, fechaInicio, horaInicio, horaFin, estado, null, unTipoUtilidad, unPais, unaProvincia, unaCiudad, unBarrio, null, unPaisDestino, unaProvinciaDestino, unaCiudadDestino,direccionDestino, unCliente, nombreEsporadico, unRodado, tipoCliente);         
                        this.cargarReservas();
                        remiseria.getUnaVentana().cargarSinServicio();
                        JOptionPane.showMessageDialog(null,"Se ha agregado una nueva Reserva para Cliente Esporádico", null, JOptionPane.INFORMATION_MESSAGE);
                        btnAgregarReserva.setEnabled(true);
                          jXTaskPane4.setCollapsed(true);
                        btnModificarReserva.setEnabled(true);
                        btnEliminarReserva.setEnabled(true);
                        btnFinalizarReserva.setEnabled(true);
                        tablaReservasActivas.setEnabled(true);
                        tablaReservas.setEnabled(true);
                        btnGuardarModificaciones.setEnabled(false);
                        btnGuardarChofer.setEnabled(false);    
                        tablaReservas.setRowSelectionInterval(0, 0);
                        btnCancelarViaje1.setEnabled(true);
                        this.limpiar();               
                    }
                    else 
                    {
                        if(utilidades.isNumber(cbxMovil.getSelectedItem().toString())==true)
                        {
                            JOptionPane.showMessageDialog(null, "No ha asignado un Chofer al Móvil", null, JOptionPane.ERROR_MESSAGE);
                        }

                    }

                }
            }
       }
       catch(Exception ex)
       {
               JOptionPane.showMessageDialog(null, ex);
       }
   }
    
    public void modificarReserva(){
//        try{
        boolean ok = true;
        int fila = (int) tablaReservas.getSelectedRow();
        int numeroReserva = (int) tablaReservas.getValueAt(fila, 0);
        Reserva unaReserva = remiseria.getReservas().get(numeroReserva); 
        String tipoCliente = cmbTipoDeCliente.getSelectedItem().toString();
        String nombreEsporadico = null;
        Cliente unCliente = null;
            if(cmbCliente.isEnabled()==true)
            {
                unCliente = (Cliente) cmbCliente.getSelectedItem();
            }
            if(txtNombreEsporadico.isEnabled()== true)
            {
                nombreEsporadico=txtNombreEsporadico.getText();
            }
            String estado = "pendiente";
            Calendar cal = jdcFecha.getCalendar();
            if (cal == null)
            {
                cal = Calendar.getInstance();
            }
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DATE);
            Date date = new Date(year -1900, month, day);        
            Date fechaInicio = date;//utilidades.getDate(utilidades.getFecha(jdcFecha.getDate()));
            Date horaInicio = utilidades.getHour(cmbHoraInicio.getSelectedItem().toString()+":"+cmbMinutoInicio.getSelectedItem().toString());
            Date horaFin = utilidades.getHour(cmbHoraFin.getSelectedItem().toString()+":"+cmbMinutoFin.getSelectedItem().toString());
            TipoUtilidad unTipoUtilidad = null;
            if(cmbTipoViaje.isEnabled()==true)
            {
                unTipoUtilidad =(TipoUtilidad) cmbTipoViaje.getSelectedItem();
            }
            Movil unMovil = null;
            Rodado unRodado = (Rodado)cmbTipoMovil.getSelectedItem();
            Chofer unChofer = null;

            if(txtNombreEsporadico.isEnabled()==true)
            {
                if(txtNombreEsporadico.getText().isEmpty() || nombreEsporadico == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha ingresado un Nombre", null,JOptionPane.ERROR_MESSAGE);
                    txtNombreEsporadico.setBackground(java.awt.Color.red);
                    ok= false;
                }
            }

            if(jdcFecha.isEnabled()==true)
            {
                if(fechaInicio == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha ingresado una Fecha", null,JOptionPane.ERROR_MESSAGE);
                    ok= false;
                }
                else
                {
                    if(utilidades.compararFechasConDate(fechaInicio).equals("Menor"))
                    {
                         JOptionPane.showMessageDialog(null, "La Fecha ingresada es menor al Fecha actual", null,JOptionPane.ERROR_MESSAGE);
                        ok= false;
                    }
                }
            }

            if(unTipoUtilidad != null)
            {
                System.out.println(unTipoUtilidad.getNombreTipoUtilidad());
                if(!unTipoUtilidad.getNombreTipoUtilidad().equals("Especial"))
                {
                    if(utilidades.compararHoras(horaInicio, horaFin).equals("Mayor")||utilidades.compararHoras(horaInicio, horaFin).equals("Igual"))
                    {
                        if(utilidades.compararHoras(horaInicio, horaFin).equals("Mayor"))
                        {
                            JOptionPane.showMessageDialog(null, "La Hora Inicio es mayor a la Hora Fin", null,JOptionPane.ERROR_MESSAGE);
                            ok= false;
                        }
                        if(utilidades.compararHoras(horaInicio, horaFin).equals("Igual"))
                        {
                            JOptionPane.showMessageDialog(null, "La Hora Inicio es igual a la Hora Fin", null,JOptionPane.ERROR_MESSAGE);
                            ok= false;
                        }            
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al seleccionar Tipo de Viaje", null,JOptionPane.ERROR_MESSAGE);
                ok= false;
            }
            if(cbxMovil.isEnabled()==true)
            {
                if(cbxMovil.getSelectedItem() != null)
                {
                    if(utilidades.isNumber(cbxMovil.getSelectedItem().toString())==true)
                    {
                        unMovil =(Movil) remiseria.buscarMovil(Integer.parseInt(cbxMovil.getSelectedItem().toString()));
                    }
                }
                else if(cbxMovil.getSelectedItem().equals("Sin móvil"))
                {
                    unMovil = null;
                }            
                else
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un Móvil", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }
            }
            if(cmbChofer.isEnabled()==true)
            {
                if(!cmbChofer.getSelectedItem().equals("Sin chofer"))
                {
                    unChofer = (Chofer) cmbChofer.getSelectedItem();
                }
                else if(cmbChofer.getSelectedItem().equals("Sin chofer"))
                {
                    unChofer = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un Chofer", null,JOptionPane.ERROR_MESSAGE);
                    ok= false;
                }
            }
            Pais unPais = (Pais) remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
            Provincia unaProvincia =(Provincia) (unPais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()));
            Ciudad unaCiudad =null;        
            Barrio unBarrio =null;
            
            if(cmbCliente.isEnabled()!=true)
            {
               unaCiudad =(Ciudad) cmbCiudad.getSelectedItem();        
               unBarrio =(Barrio)cmbBarrio.getSelectedItem();
            }
            DireccionViaje direccionViaje = null;
            Domicilio unDomicilio = null;
            if(cmbDireccion.isEnabled()==true)
            {
                direccionViaje =(DireccionViaje) cmbDireccion.getSelectedItem();
            }
            else
            {
                unDomicilio =(Domicilio) cmbDireccionCliente.getSelectedItem();
            }
            Pais unPaisDestino = null;
            Provincia unaProvinciaDestino = null;
            Ciudad unaCiudadDestino = null;  
            String direccionDestino = null;
            Map<String, Dias> dias = null;

            if(checkRepetir.isSelected()==true && panelDias.isEnabled()==true && panelDias.isVisible()==true)
            {   
                dias = new HashMap<>();
                dias.clear();
                for (Component unComponent : panelDias.getComponents())
                {
                    if (unComponent instanceof JCheckBox) 
                    {
                        if (((JCheckBox) unComponent).isSelected())
                        {
                            Dias nuevoDia = new Dias(unComponent.getName());
                            dias.put(nuevoDia.getDiaSemana(), nuevoDia);
                        }
                    }
                }     
            }


            if(jPanel11.isEnabled()==true && jPanel11.isVisible()==true )
            {
                unPaisDestino = (Pais) (cmbPaisDestino.getSelectedItem());
                unaProvinciaDestino = (Provincia)(cmbProvinciaDestino.getSelectedItem());
                unaCiudadDestino = (Ciudad)cmbCiudadDestino.getSelectedItem();  
                direccionDestino = txtDireccionDestino.getText();
            }

            if(cmbTipoViaje.isEnabled()==true)
            {
                if(unTipoUtilidad == null)
                    {
                        JOptionPane.showMessageDialog(null, "No ha seleccionado una Utilidad", null,JOptionPane.ERROR_MESSAGE);
                        ok= false;
                    }
            }

            if(unRodado == null)
            {
                JOptionPane.showMessageDialog(null, "No ha seleccionado un Rodado", null,JOptionPane.ERROR_MESSAGE);
                ok= false; 
            }

            if(cmbCliente.isEnabled()!= true)
            {
                if(unaCiudad == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Ciudad", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }

                if(unBarrio == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un Barrio", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }
            }
            if(cmbDireccionCliente.isEnabled()==false)
            {
                if(direccionViaje == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Direccion", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }        
            }
            if(cmbDireccionCliente.isEnabled()==true)
            {
                if(unDomicilio == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Direccion del Cliente", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                } 
            }

            if(jPanel11.isEnabled()==true && jPanel11.isVisible()==true)
            {

                if(unPaisDestino == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado un Pais de Destino", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }

                if(unaCiudadDestino == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Ciudad de Destino", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }

                if(unaProvinciaDestino == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Provincia de Destino", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }

                if(txtDireccionDestino.getText().isEmpty() || direccionDestino == null)
                {
                    JOptionPane.showMessageDialog(null, "No ha ingresado una Direcion de Destino", null,JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                    txtDireccionDestino.setBackground(java.awt.Color.red);
                }

            }

            if(cmbCliente.isEnabled()==true)
            {
                if(unCliente == null)
                {
                     JOptionPane.showMessageDialog(null, "No ha seleccionado un Cliente", null,JOptionPane.ERROR_MESSAGE);
                     ok= false; 
                }
            }

            if(panelDias.isEnabled()==true)
            {
                if(dias.isEmpty())
                {
                    JOptionPane.showMessageDialog(this, "Debe al menos seleccionar un día para crear la reserva");
                    ok=false;
                }
            } 
            
        if(ok == true )
        {
            if(unCliente != null)
            {
                if(dias== null )
                {
                    remiseria.modificarReserva2(unaReserva, "Calle "+unDomicilio.getCalle()+ " N° "+unDomicilio.getNroCasa(), fechaInicio, horaInicio, horaFin, estado, unMovil, unTipoUtilidad, unDomicilio.getUnPais(), unDomicilio.getUnaProvincia(), unDomicilio.getUnaCiudad(), unDomicilio.getUnBarrio(), unChofer, unPaisDestino, unaProvinciaDestino, unaCiudadDestino, direccionDestino, unCliente, nombreEsporadico, unRodado, tipoCliente, dias);                    this.cargarReservas();
                    remiseria.getUnaVentana().cargarSinServicio();
                    JOptionPane.showMessageDialog(null,"Se ha modificado la Reserva seleccionada", null, JOptionPane.INFORMATION_MESSAGE);
                    btnAgregarReserva.setEnabled(true);
                    jXTaskPane4.setCollapsed(true);
                    btnModificarReserva.setEnabled(true);
                    btnEliminarReserva.setEnabled(true);
                    btnFinalizarReserva.setEnabled(true);
                    tablaReservasActivas.setEnabled(true);
                    tablaReservas.setEnabled(true);
                    btnGuardarModificaciones.setEnabled(false);
                    btnGuardarChofer.setEnabled(false);    
                    tablaReservas.setRowSelectionInterval(0, 0);
                    btnCancelarViaje1.setEnabled(true);
                    this.limpiar();
                }
                else
                {
                    
                    remiseria.modificarReserva2(unaReserva, "Calle "+unDomicilio.getCalle()+ " N° "+unDomicilio.getNroCasa(), fechaInicio, horaInicio, horaFin, estado, unMovil, unTipoUtilidad, unDomicilio.getUnPais(), unDomicilio.getUnaProvincia(), unDomicilio.getUnaCiudad(), unDomicilio.getUnBarrio(), unChofer, unPaisDestino, unaProvinciaDestino, unaCiudadDestino, direccionDestino, unCliente, nombreEsporadico, unRodado, tipoCliente, dias);
                    this.cargarReservas();
                    remiseria.getUnaVentana().cargarSinServicio();
                    JOptionPane.showMessageDialog(null,"Se ha modificado la Reserva seleccionada", null, JOptionPane.INFORMATION_MESSAGE);
                    btnAgregarReserva.setEnabled(true);
                    jXTaskPane4.setCollapsed(true);
                    btnModificarReserva.setEnabled(true);
                    btnEliminarReserva.setEnabled(true);
                    btnFinalizarReserva.setEnabled(true);
                    tablaReservasActivas.setEnabled(true);
                    tablaReservas.setEnabled(true);
                    btnGuardarModificaciones.setEnabled(false);
                    btnGuardarChofer.setEnabled(false);  
                   // tablaReservas.setRowSelectionInterval(0, 0);
                    btnCancelarViaje1.setEnabled(true);
                    this.limpiar();
                }    
            }
            else
            {
                if(unChofer != null && unMovil != null)
                {
                    remiseria.modificarReserva(unaReserva, direccionViaje, fechaInicio, horaInicio, horaFin, estado, unMovil, unTipoUtilidad, unPais, unaProvincia, unaCiudad, unBarrio, unChofer, unPaisDestino, unaProvinciaDestino, unaCiudadDestino, direccionDestino, unCliente, nombreEsporadico, unRodado, tipoCliente, dias);
                    this.cargarReservas();
                    remiseria.getUnaVentana().cargarSinServicio();
                    JOptionPane.showMessageDialog(null,"Se ha modificado la Reserva seleccionada", null, JOptionPane.INFORMATION_MESSAGE);
                    btnAgregarReserva.setEnabled(true);
                      jXTaskPane4.setCollapsed(true);
                    btnModificarReserva.setEnabled(true);
                    btnEliminarReserva.setEnabled(true);
                    btnFinalizarReserva.setEnabled(true);
                    tablaReservasActivas.setEnabled(true);
                    tablaReservas.setEnabled(true);
                    btnGuardarModificaciones.setEnabled(false);
                    btnGuardarChofer.setEnabled(false);   
                    tablaReservas.setRowSelectionInterval(0, 0);
                    btnCancelarViaje1.setEnabled(true);
                    this.limpiar();
                }
                else if(unChofer == null && unMovil == null && cmbChofer.getSelectedItem().equals("Sin chofer") &&cbxMovil.getSelectedItem().equals("Sin móvil") )
                {
                    remiseria.modificarReserva(unaReserva, direccionViaje, fechaInicio, horaInicio, horaFin, estado, null, unTipoUtilidad, unPais, unaProvincia, unaCiudad, unBarrio, null, unPaisDestino, unaProvinciaDestino, unaCiudadDestino, direccionDestino, unCliente, nombreEsporadico, unRodado, tipoCliente, dias);
                    this.cargarReservas();
                    remiseria.getUnaVentana().cargarSinServicio();
                    JOptionPane.showMessageDialog(null,"Se ha modificado la Reserva seleccionada", null, JOptionPane.INFORMATION_MESSAGE);
                    btnAgregarReserva.setEnabled(true);
                    jXTaskPane4.setCollapsed(true);
                    btnModificarReserva.setEnabled(true);
                    btnEliminarReserva.setEnabled(true);
                    btnFinalizarReserva.setEnabled(true);
                    tablaReservasActivas.setEnabled(true);
                    tablaReservas.setEnabled(true);
                    btnGuardarModificaciones.setEnabled(false);
                    btnGuardarChofer.setEnabled(false);   
                    //tablaReservas.setRowSelectionInterval(0, 0);
                    btnCancelarViaje1.setEnabled(true);
                    this.limpiar();                    
                }
                else 
                {
                    if(utilidades.isNumber(cbxMovil.getSelectedItem().toString())==true)
                    {
                        JOptionPane.showMessageDialog(null, "No ha asignado un Chofer al Móvil", null, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
//        }
//        catch (Exception ex)
//        {
//            JOptionPane.showMessageDialog(null, ex);
//        }
    }
       
    public void cargarPaisDestinoCombo()
    {
        cmbPaisDestino.removeAllItems();
        List paises = remiseria.buscarPaises();
        if(paises != null)
        {
            Pais aux = null;
            Iterator iter = paises.iterator();
            while (iter.hasNext())
            {
                aux = (Pais) iter.next();
                cmbPaisDestino.addItem(aux);
            }
        }
    }
        
    public void cargarProvinciasDestino(Pais unPais){
        cmbProvinciaDestino.removeAllItems();
        List provincias = unPais.buscarProvincias();
        if(provincias != null)
        {
            Provincia aux = null;
            Iterator iter = provincias.iterator();
            while (iter.hasNext())
            {
                aux = (Provincia) iter.next();
                cmbProvinciaDestino.addItem(aux);
            }
        }
    }
    
    public void cargarCiudadesCombo(Provincia unaProvincia)
    {
       cmbCiudad.removeAllItems();
       List ciudades = unaProvincia.buscarCiudades();
       if(ciudades != null){
           Ciudad aux = null;
           Iterator iter = ciudades.iterator();
           while (iter.hasNext()){
           aux = (Ciudad) iter.next();
           cmbCiudad.addItem(aux);
        }
        //cmbCiudad.setSelectedIndex(0);
       }
    }
    
    
    public void cargarCiudadesComboDestino(Provincia unaProvincia)
    {
        cmbCiudadDestino.removeAllItems();
        List ciudades = unaProvincia.buscarCiudades();
        if(ciudades != null)
        {
            Ciudad aux = null;
            Iterator iter = ciudades.iterator();
            while (iter.hasNext())
            {
                aux = (Ciudad) iter.next();
                cmbCiudadDestino.addItem(aux);
            }
        }
    }
    public void cargarBarriosCombo(Ciudad unaCiudad){
        cmbBarrio.removeAllItems();
        List barrios = unaCiudad.buscarBarrios();
        if(barrios != null)
        {
            Barrio aux = null;
            Iterator iter = barrios.iterator();
            while (iter.hasNext()){
                aux = (Barrio) iter.next();
                cmbBarrio.addItem(aux);
            }
        }
    }  

public void cargarDireccionesAutoCompleter(Barrio unBarrio){
        cmbDireccion.removeAllItems();
        List direcciones = unBarrio.buscarDirecciones();
        if(direcciones != null)
        {
            DireccionViaje aux = null;
            Iterator iter = direcciones.iterator();
            while (iter.hasNext()){
                aux = (DireccionViaje) iter.next();
                cmbDireccion.addItem(aux);
            }
        }
    }

   public void agregarNuevaDireccion(){
      String nombrePais = remiseria.getUnDomicilio().getUnPais().getNombrePais();
      Pais unPais = remiseria.buscarPais(nombrePais);
      if (unPais != null)
      {
          String nombreProvincia = remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia();
          Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
          if(unaProvincia != null)
          {
              String nombreCiudad = cmbCiudad.getSelectedItem().toString();
              if(!nombreCiudad.isEmpty())
              {
                 Ciudad unaCiudad = unaProvincia.buscarCiudad(nombreCiudad);
                 if(unaCiudad != null)
                 {
                    String nombreBarrio = cmbBarrio.getSelectedItem().toString();
                    if(!nombreBarrio.isEmpty())
                    {
                        Barrio unBarrio = unaCiudad.buscarBarrio(nombreBarrio);
                        String direccion = cmbDireccion.getSelectedItem().toString();
                        if(unBarrio.buscarDireccionViaje(direccion)==null){
                            unBarrio.agregarDireccion(direccion);
                            this.cargarDireccionesAutoCompleter(unBarrio);
                            JOptionPane.showMessageDialog(null,"Se registro una nueva Dirección"," ",JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                        {
                                JOptionPane.showMessageDialog(null,"Ya existe la Dirección","",JOptionPane.ERROR_MESSAGE);

                        }
                    }
                 }
              }
           }
       }    
   }        
   
   
   
   
   public void limpiar()
   {
       cmbTipoDeCliente.setSelectedIndex(0);
       txtNombreEsporadico.setText(null);
       jdcFecha.setDate(null);
       cmbHoraInicio.setSelectedIndex(0);
       cmbHoraFin.setSelectedIndex(0);
       cmbMinutoInicio.setSelectedIndex(0);
       cmbMinutoFin.setSelectedIndex(0);
       cmbTipoMovil.setSelectedIndex(0);
       cmbTipoViaje.setSelectedIndex(0);
       cbxMovil.removeAllItems();
       cmbChofer.removeAllItems();
       Pais pais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
       this.cargarCiudadesCombo(pais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()));
       this.cargarPaisDestinoCombo();
       jCheckBox1.setSelected(false);
       jPanel11.setVisible(false);
       jPanel11.setEnabled(false);
       cmbPaisDestino.setEnabled(false);
       cmbProvinciaDestino.setEnabled(false);
       cmbCiudadDestino.setEnabled(false);
       lunes.setSelected(false);
       martes.setSelected(false);
       miercoles.setSelected(false);
       jueves.setSelected(false);
       viernes.setEnabled(false);
       sabado.setEnabled(false);
       domingo.setEnabled(false);
       checkRepetir.setSelected(false);
       panelDias.setEnabled(false);
       panelDias.setVisible(false);
       jdcFecha.setEnabled(true);
   }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        btnSalir4 = new javax.swing.JButton();
        btnIniciarTurno = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jXTaskPaneContainer5 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaSinServicio = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jXSearchMovilesSinServicio = new org.jdesktop.swingx.JXSearchField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnIniciarViaje = new javax.swing.JButton();
        btnCancelarViaje = new javax.swing.JButton();
        btnFinalizarViaje = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnEstado = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel14 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDisponibles = new javax.swing.JTable();
        jPanel34 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbxZona = new javax.swing.JComboBox();
        jLabel34 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtKmsFinalAnterior = new javax.swing.JTextField();
        txtKmsInicial = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        txtFechaInicial = new javax.swing.JTextField();
        jXTaskPaneContainer2 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel17 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTransito = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir3 = new javax.swing.JButton();
        btnCambiarEstado = new javax.swing.JButton();
        jXTaskPaneContainer4 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDescanso = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        btnAgregarReserva = new javax.swing.JButton();
        btnSalir2 = new javax.swing.JButton();
        btnEliminarReserva = new javax.swing.JButton();
        btnModificarReserva = new javax.swing.JButton();
        btnFinalizarReserva = new javax.swing.JButton();
        btnCancelarViaje1 = new javax.swing.JButton();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaReservas = new javax.swing.JTable();
        jXTaskPane4 = new org.jdesktop.swingx.JXTaskPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        cmbCiudadDestino = new javax.swing.JComboBox();
        cmbProvinciaDestino = new javax.swing.JComboBox();
        cmbPaisDestino = new javax.swing.JComboBox();
        btnPais1 = new javax.swing.JButton();
        btnProvincia1 = new javax.swing.JButton();
        bnCiudad1 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        txtDireccionDestino = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        cmbCliente = new javax.swing.JComboBox();
        txtNombreEsporadico = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbTipoDeCliente = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cmbCiudad = new javax.swing.JComboBox();
        btnCiudad = new javax.swing.JButton();
        btnBarrio = new javax.swing.JButton();
        cmbBarrio = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cmbDireccionCliente = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel27 = new javax.swing.JPanel();
        btnAgregarDireccion = new javax.swing.JButton();
        btnDirreccion = new javax.swing.JButton();
        cmbDireccion = new javax.swing.JComboBox();
        btnClientesReserva = new javax.swing.JButton();
        btnClientesReserva1 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbHoraFin = new javax.swing.JComboBox();
        cmbHoraInicio = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cmbMinutoFin = new javax.swing.JComboBox();
        cmbMinutoInicio = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cmbTipoMovil = new javax.swing.JComboBox();
        cbxMovil = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbTipoViaje = new javax.swing.JComboBox();
        cmbChofer = new javax.swing.JComboBox();
        checkRepetir = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel26 = new javax.swing.JPanel();
        panelDias = new javax.swing.JPanel();
        lunes = new javax.swing.JCheckBox();
        martes = new javax.swing.JCheckBox();
        miercoles = new javax.swing.JCheckBox();
        jueves = new javax.swing.JCheckBox();
        sabado = new javax.swing.JCheckBox();
        viernes = new javax.swing.JCheckBox();
        domingo = new javax.swing.JCheckBox();
        btnGuardarChofer = new javax.swing.JButton();
        btnGuardarModificaciones = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaReservasActivas = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jXTaskPaneContainer6 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel20 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaViajesRealizados = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        checkPeriodoViaje = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        dp_desde = new org.jdesktop.swingx.JXDatePicker();
        jLabel18 = new javax.swing.JLabel();
        dp_hasta = new org.jdesktop.swingx.JXDatePicker();
        jLabel40 = new javax.swing.JLabel();
        fechaDeterminadoViaje = new org.jdesktop.swingx.JXDatePicker();
        cmbOpcionBusqueda = new javax.swing.JComboBox();
        buscarViaje = new org.jdesktop.swingx.JXSearchField();
        jButton7 = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaKilometrosRecorridos = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        dp_hastaKmsRec = new org.jdesktop.swingx.JXDatePicker();
        dp_desdeKmsRec = new org.jdesktop.swingx.JXDatePicker();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        SearchMovilKmsRecorridos = new org.jdesktop.swingx.JXSearchField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtKilometrosTotalesRecoridos = new javax.swing.JTextField();
        fechaDeterminado = new org.jdesktop.swingx.JXDatePicker();
        jLabel39 = new javax.swing.JLabel();
        checkPeriodo = new javax.swing.JCheckBox();
        hsDesde = new javax.swing.JComboBox();
        minDesde = new javax.swing.JComboBox();
        hrHasta = new javax.swing.JComboBox();
        minHasta = new javax.swing.JComboBox();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        checkPeriodo1 = new javax.swing.JCheckBox();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel29 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaKilómetrosEnServicio = new javax.swing.JTable();
        jPanel33 = new javax.swing.JPanel();
        checkPeriodoServicio = new javax.swing.JCheckBox();
        dp_desdeServicio = new org.jdesktop.swingx.JXDatePicker();
        dp_hastaServicio = new org.jdesktop.swingx.JXDatePicker();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        fechaDeterminadoServico = new org.jdesktop.swingx.JXDatePicker();
        SearchServicio = new org.jdesktop.swingx.JXSearchField();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        setTitle("Gestor de Viajes");
        setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        setOpaque(true);
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);

        jPanel1.setMaximumSize(new java.awt.Dimension(1159, 642));
        jPanel1.setMinimumSize(new java.awt.Dimension(1159, 642));

        jTabbedPane1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(790, 488));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(790, 488));

        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel16.setMaximumSize(new java.awt.Dimension(162, 453));
        jPanel16.setMinimumSize(new java.awt.Dimension(162, 453));

        btnSalir4.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir4.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnSalir4.setText("Salir");
        btnSalir4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir4ActionPerformed(evt);
            }
        });

        btnIniciarTurno.setBackground(new java.awt.Color(255, 255, 255));
        btnIniciarTurno.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnIniciarTurno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/dialog-apply.png"))); // NOI18N
        btnIniciarTurno.setText("Iniciar turno");
        btnIniciarTurno.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnIniciarTurno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnIniciarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarTurnoActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Moneda.png"))); // NOI18N
        jButton11.setText("Abrir caja");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalir4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btnIniciarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIniciarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tablaSinServicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaSinServicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaSinServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Móvil N°", "Tipo de rodado", "Marca", "Modelo", "Nombre ", "Apellido "
            }
        ));
        tablaSinServicio.setGridColor(new java.awt.Color(0, 0, 0));
        tablaSinServicio.setRowHeight(20);
        tablaSinServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSinServicioMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaSinServicio);

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel7.setText("Móviles fuera de servicio");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7))
        );

        jXSearchMovilesSinServicio.setPrompt("Buscar un Móvil");
        jXSearchMovilesSinServicio.setToolTipText("");
        jXSearchMovilesSinServicio.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jXSearchMovilesSinServicioCaretUpdate(evt);
            }
        });
        jXSearchMovilesSinServicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jXSearchMovilesSinServicioKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer5Layout = new javax.swing.GroupLayout(jXTaskPaneContainer5);
        jXTaskPaneContainer5.setLayout(jXTaskPaneContainer5Layout);
        jXTaskPaneContainer5Layout.setHorizontalGroup(
            jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 966, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jXSearchMovilesSinServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jXTaskPaneContainer5Layout.setVerticalGroup(
            jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer5Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXSearchMovilesSinServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jXTaskPaneContainer5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Móviles fuera de servicio", jPanel7);

        jPanel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(776, 443));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setMaximumSize(new java.awt.Dimension(173, 453));
        jPanel4.setMinimumSize(new java.awt.Dimension(173, 453));

        btnIniciarViaje.setBackground(new java.awt.Color(255, 255, 255));
        btnIniciarViaje.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnIniciarViaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/view-sort-ascending.png"))); // NOI18N
        btnIniciarViaje.setText("Nuevo viaje");
        btnIniciarViaje.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnIniciarViaje.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnIniciarViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarViajeActionPerformed(evt);
            }
        });

        btnCancelarViaje.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelarViaje.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCancelarViaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gtk-no.png"))); // NOI18N
        btnCancelarViaje.setText("Cancelar viaje");
        btnCancelarViaje.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelarViaje.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelarViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarViajeActionPerformed(evt);
            }
        });

        btnFinalizarViaje.setBackground(new java.awt.Color(255, 255, 255));
        btnFinalizarViaje.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnFinalizarViaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/view-sort-descending.png"))); // NOI18N
        btnFinalizarViaje.setText("Finalizar viaje");
        btnFinalizarViaje.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnFinalizarViaje.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFinalizarViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarViajeActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnEstado.setBackground(new java.awt.Color(255, 255, 255));
        btnEstado.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document-import.png"))); // NOI18N
        btnEstado.setText("Cambiar estado");
        btnEstado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEstado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFinalizarViaje, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarViaje, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIniciarViaje, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIniciarViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFinalizarViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel2.setText("Móviles disponibles");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );

        tablaDisponibles.setBackground(new java.awt.Color(51, 255, 51));
        tablaDisponibles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaDisponibles.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Móvil N°", "Marca", "Modelo", "Chofer", "Zona"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDisponibles.setGridColor(new java.awt.Color(0, 0, 0));
        tablaDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDisponiblesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDisponibles);

        jPanel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel4.setText("Kilómetros realizados");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jLabel6.setText("Cambiar Zona:");

        cbxZona.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxZona.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbxZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxZonaActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jLabel34.setText("Kilomentro final actual:");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jLabel36.setText("Kilómetro final:");

        txtKmsFinalAnterior.setEditable(false);
        txtKmsFinalAnterior.setBackground(new java.awt.Color(204, 204, 204));
        txtKmsFinalAnterior.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtKmsFinalAnterior.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtKmsInicial.setEditable(false);
        txtKmsInicial.setBackground(new java.awt.Color(204, 204, 204));
        txtKmsInicial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtKmsInicial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel35.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jLabel35.setText("Kilómetro inicial:");

        jLabel60.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jLabel60.setText("Fecha- Hora:");

        txtFechaInicial.setEditable(false);
        txtFechaInicial.setBackground(new java.awt.Color(204, 204, 204));
        txtFechaInicial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFechaInicial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbxZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel35Layout.createSequentialGroup()
                                .addComponent(jTextField1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel35Layout.createSequentialGroup()
                                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(txtKmsInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKmsFinalAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel36)))
                            .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFechaInicial, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 20, Short.MAX_VALUE))))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addComponent(jLabel60)
                .addGap(6, 6, 6)
                .addComponent(txtFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(6, 6, 6)
                        .addComponent(txtKmsFinalAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(6, 6, 6)
                        .addComponent(txtKmsInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addGap(8, 8, 8)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jLabel6)
                .addGap(3, 3, 3)
                .addComponent(cbxZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel3.setText("Móviles en tránsito");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3))
        );

        tablaTransito.setBackground(new java.awt.Color(255, 102, 102));
        tablaTransito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaTransito.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaTransito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Móvil N°", "Viaje N°", "Chofer", "Hora", "Origen", "Destino", "Asignado", "Utilidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaTransito.setGridColor(new java.awt.Color(0, 0, 0));
        tablaTransito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTransitoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaTransito);
        if (tablaTransito.getColumnModel().getColumnCount() > 0) {
            tablaTransito.getColumnModel().getColumn(0).setPreferredWidth(10);
            tablaTransito.getColumnModel().getColumn(1).setPreferredWidth(10);
            tablaTransito.getColumnModel().getColumn(3).setPreferredWidth(10);
        }

        javax.swing.GroupLayout jXTaskPaneContainer2Layout = new javax.swing.GroupLayout(jXTaskPaneContainer2);
        jXTaskPaneContainer2.setLayout(jXTaskPaneContainer2Layout);
        jXTaskPaneContainer2Layout.setHorizontalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );
        jXTaskPaneContainer2Layout.setVerticalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jTabbedPane1.addTab("Móviles en servicio", jPanel3);

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel13.setMaximumSize(new java.awt.Dimension(173, 453));
        jPanel13.setMinimumSize(new java.awt.Dimension(173, 453));

        btnSalir3.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir3.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnSalir3.setText("Salir");
        btnSalir3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir3ActionPerformed(evt);
            }
        });

        btnCambiarEstado.setBackground(new java.awt.Color(255, 255, 255));
        btnCambiarEstado.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCambiarEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/dialog-apply.png"))); // NOI18N
        btnCambiarEstado.setText("Cambiar estado");
        btnCambiarEstado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCambiarEstado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCambiarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCambiarEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCambiarEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tablaDescanso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaDescanso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaDescanso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Móvil N°", "Marca", "Modelo", "Nombre", "Apellido"
            }
        ));
        tablaDescanso.setGridColor(new java.awt.Color(0, 0, 0));
        tablaDescanso.setRowHeight(20);
        tablaDescanso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDescansoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDescanso);

        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel5.setText("Móviles en descanso");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5))
        );

        javax.swing.GroupLayout jXTaskPaneContainer4Layout = new javax.swing.GroupLayout(jXTaskPaneContainer4);
        jXTaskPaneContainer4.setLayout(jXTaskPaneContainer4Layout);
        jXTaskPaneContainer4Layout.setHorizontalGroup(
            jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 966, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jXTaskPaneContainer4Layout.setVerticalGroup(
            jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer4Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jXTaskPaneContainer4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jXTaskPaneContainer4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Móviles en descanso", jPanel6);

        jPanel5.setPreferredSize(new java.awt.Dimension(776, 443));

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel12.setMaximumSize(new java.awt.Dimension(162, 453));
        jPanel12.setMinimumSize(new java.awt.Dimension(162, 453));
        jPanel12.setPreferredSize(new java.awt.Dimension(162, 458));

        btnAgregarReserva.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregarReserva.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregarReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregarReserva.setText("Nueva reserva");
        btnAgregarReserva.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAgregarReserva.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarReservaActionPerformed(evt);
            }
        });

        btnSalir2.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnSalir2.setText("Salir");
        btnSalir2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir2ActionPerformed(evt);
            }
        });

        btnEliminarReserva.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarReserva.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarReserva.setText("Eliminar reserva");
        btnEliminarReserva.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminarReserva.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarReservaActionPerformed(evt);
            }
        });

        btnModificarReserva.setBackground(new java.awt.Color(255, 255, 255));
        btnModificarReserva.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/leafpad.png"))); // NOI18N
        btnModificarReserva.setText("Modificar reserva");
        btnModificarReserva.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnModificarReserva.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarReservaActionPerformed(evt);
            }
        });

        btnFinalizarReserva.setBackground(new java.awt.Color(255, 255, 255));
        btnFinalizarReserva.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnFinalizarReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/view-sort-descending.png"))); // NOI18N
        btnFinalizarReserva.setText("Finalizar viaje");
        btnFinalizarReserva.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnFinalizarReserva.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFinalizarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarReservaActionPerformed(evt);
            }
        });

        btnCancelarViaje1.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelarViaje1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCancelarViaje1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gtk-no.png"))); // NOI18N
        btnCancelarViaje1.setText("Cancelar viaje");
        btnCancelarViaje1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelarViaje1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelarViaje1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarViaje1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnAgregarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnModificarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnEliminarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnFinalizarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnSalir2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCancelarViaje1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btnAgregarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnModificarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnEliminarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136)
                .addComponent(btnFinalizarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarViaje1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(btnSalir2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tablaReservas.setBackground(new java.awt.Color(255, 255, 0));
        tablaReservas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaReservas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Cliente", "Domicilio", "Destino", "Móvil N°", "Chofer", "Tipo de viaje", "Fecha", "Inicio", "Fin", "Estado"
            }
        ));
        tablaReservas.setGridColor(new java.awt.Color(0, 0, 0));
        tablaReservas.setRowHeight(20);
        tablaReservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaReservasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaReservas);

        jXTaskPane4.setCollapsed(true);
        jXTaskPane4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document-new.png"))); // NOI18N
        jXTaskPane4.setTitle("Datos de la Reserva");

        jPanel8.setBackground(new java.awt.Color(117, 150, 227));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jPanel11.setBackground(new java.awt.Color(117, 150, 227));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Destino", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Goudy Old Style", 0, 18))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel30.setText("País:");

        jLabel29.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel29.setText("Provincia:");

        jLabel28.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel28.setText("Ciudad:");

        cmbCiudadDestino.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        cmbProvinciaDestino.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbProvinciaDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvinciaDestinoActionPerformed(evt);
            }
        });

        cmbPaisDestino.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbPaisDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaisDestinoActionPerformed(evt);
            }
        });

        btnPais1.setFont(new java.awt.Font("Nyala", 0, 12)); // NOI18N
        btnPais1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnPais1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPais1ActionPerformed(evt);
            }
        });

        btnProvincia1.setFont(new java.awt.Font("Nyala", 0, 12)); // NOI18N
        btnProvincia1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnProvincia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProvincia1ActionPerformed(evt);
            }
        });

        bnCiudad1.setFont(new java.awt.Font("Nyala", 0, 12)); // NOI18N
        bnCiudad1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        bnCiudad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnCiudad1ActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel33.setText("Dirección:");

        txtDireccionDestino.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDireccionDestino.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDireccionDestino.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDireccionDestino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDireccionDestinoMouseClicked(evt);
            }
        });
        txtDireccionDestino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionDestinoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel33)
                    .addComponent(jLabel30)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbPaisDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbProvinciaDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnProvincia1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPais1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDireccionDestino, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbCiudadDestino, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bnCiudad1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbCiudadDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bnCiudad1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(cmbPaisDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPais1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(cmbProvinciaDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProvincia1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel21.setBackground(new java.awt.Color(117, 150, 227));

        cmbCliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbClienteActionPerformed(evt);
            }
        });

        txtNombreEsporadico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombreEsporadico.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreEsporadico.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombreEsporadico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreEsporadicoMouseClicked(evt);
            }
        });
        txtNombreEsporadico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreEsporadicoActionPerformed(evt);
            }
        });
        txtNombreEsporadico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreEsporadicoKeyTyped(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel19.setText("Nombre:");

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel11.setText("Cliente:");

        cmbTipoDeCliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbTipoDeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoDeClienteActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel13.setText("Tipo de cliente:");

        jLabel20.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel20.setText("Ciudad:");

        cmbCiudad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCiudadActionPerformed(evt);
            }
        });

        btnCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCiudadActionPerformed(evt);
            }
        });

        btnBarrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarrioActionPerformed(evt);
            }
        });

        cmbBarrio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBarrioActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel21.setText("Barrio:");

        jLabel22.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel22.setText("Dirección:");

        jLabel23.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel23.setText("Dirección:");

        cmbDireccionCliente.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbDireccionCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDireccionClienteActionPerformed(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(117, 150, 227));
        jCheckBox1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jCheckBox1.setText("Activar destino");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jPanel27.setBackground(new java.awt.Color(117, 150, 227));

        btnAgregarDireccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnAgregarDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDireccionActionPerformed(evt);
            }
        });

        btnDirreccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnDirreccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirreccionActionPerformed(evt);
            }
        });

        cmbDireccion.setEditable(true);
        cmbDireccion.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(cmbDireccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(btnAgregarDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnDirreccion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDirreccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnClientesReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnClientesReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesReservaActionPerformed(evt);
            }
        });

        btnClientesReserva1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnClientesReserva1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesReserva1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(btnClientesReserva1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel11)
                        .addGap(10, 10, 10)
                        .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnClientesReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel20)
                        .addGap(10, 10, 10)
                        .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel21)
                        .addGap(10, 10, 10)
                        .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(10, 10, 10)
                                .addComponent(cmbTipoDeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(10, 10, 10)
                                .addComponent(txtNombreEsporadico, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(cmbTipoDeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtNombreEsporadico, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(btnClientesReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnClientesReserva1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel22.setBackground(new java.awt.Color(117, 150, 227));

        jdcFecha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jdcFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jdcFechaMouseClicked(evt);
            }
        });
        jdcFecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdcFechaPropertyChange(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Fecha:");

        jLabel24.setBackground(new java.awt.Color(0, 0, 0));
        jLabel24.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel24.setText("Hora inicio:");

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel14.setText("Hora fin:");

        cmbHoraFin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
        cmbHoraFin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbHoraFinItemStateChanged(evt);
            }
        });
        cmbHoraFin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbHoraFinPropertyChange(evt);
            }
        });

        cmbHoraInicio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
        cmbHoraInicio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbHoraInicioItemStateChanged(evt);
            }
        });
        cmbHoraInicio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbHoraInicioPropertyChange(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel9.setText(":");

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel25.setText(":");

        cmbMinutoFin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
        cmbMinutoFin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMinutoFinItemStateChanged(evt);
            }
        });
        cmbMinutoFin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbMinutoFinPropertyChange(evt);
            }
        });

        cmbMinutoInicio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
        cmbMinutoInicio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMinutoInicioItemStateChanged(evt);
            }
        });
        cmbMinutoInicio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbMinutoInicioPropertyChange(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(0, 0, 0));
        jLabel26.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel26.setText("hs");

        jLabel27.setBackground(new java.awt.Color(0, 0, 0));
        jLabel27.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel27.setText("hs");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbMinutoFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMinutoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(cmbHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMinutoFin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27)))
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(cmbMinutoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel23.setBackground(new java.awt.Color(117, 150, 227));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel15.setText("Tipo de Móvil:");

        cmbTipoMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbTipoMovil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMovilActionPerformed(evt);
            }
        });

        cbxMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxMovil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMovilActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel10.setText("Móvil:");

        jLabel16.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel16.setText("Chofer:");

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel12.setText("Tipo de viaje:");

        cmbTipoViaje.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbTipoViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoViajeActionPerformed(evt);
            }
        });

        cmbChofer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbChofer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTipoViaje, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTipoMovil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxMovil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cmbTipoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cmbChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cmbTipoViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        checkRepetir.setBackground(new java.awt.Color(117, 150, 227));
        checkRepetir.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        checkRepetir.setText("Repetir días");
        checkRepetir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRepetirActionPerformed(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(117, 150, 227));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jSeparator3.setBackground(new java.awt.Color(117, 150, 227));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel26.setBackground(new java.awt.Color(117, 150, 227));

        panelDias.setBackground(new java.awt.Color(117, 150, 227));
        panelDias.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Goudy Old Style", 0, 18))); // NOI18N

        lunes.setBackground(new java.awt.Color(117, 150, 227));
        lunes.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        lunes.setText("Lunes");
        lunes.setName("lunes"); // NOI18N

        martes.setBackground(new java.awt.Color(117, 150, 227));
        martes.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        martes.setText("Martes");
        martes.setName("martes"); // NOI18N

        miercoles.setBackground(new java.awt.Color(117, 150, 227));
        miercoles.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        miercoles.setText("Miércoles");
        miercoles.setName("miercoles"); // NOI18N

        jueves.setBackground(new java.awt.Color(117, 150, 227));
        jueves.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jueves.setText("Jueves");
        jueves.setName("jueves"); // NOI18N

        sabado.setBackground(new java.awt.Color(117, 150, 227));
        sabado.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        sabado.setText("Sábado");
        sabado.setName("sabado"); // NOI18N

        viernes.setBackground(new java.awt.Color(117, 150, 227));
        viernes.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        viernes.setText("Viernes");
        viernes.setName("viernes"); // NOI18N

        domingo.setBackground(new java.awt.Color(117, 150, 227));
        domingo.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        domingo.setText("Domingo");
        domingo.setName("domingo"); // NOI18N

        javax.swing.GroupLayout panelDiasLayout = new javax.swing.GroupLayout(panelDias);
        panelDias.setLayout(panelDiasLayout);
        panelDiasLayout.setHorizontalGroup(
            panelDiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDiasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(martes)
                    .addComponent(miercoles)
                    .addComponent(jueves)
                    .addComponent(lunes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(panelDiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDiasLayout.createSequentialGroup()
                        .addGroup(panelDiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sabado)
                            .addComponent(viernes))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDiasLayout.createSequentialGroup()
                        .addComponent(domingo)
                        .addContainerGap())))
        );
        panelDiasLayout.setVerticalGroup(
            panelDiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDiasLayout.createSequentialGroup()
                .addGroup(panelDiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDiasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lunes, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(martes, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(miercoles, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jueves, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDiasLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(viernes, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sabado, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(domingo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(checkRepetir)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(checkRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardarChofer.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardarChofer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/media-floppy.png"))); // NOI18N
        btnGuardarChofer.setText("Guardar");
        btnGuardarChofer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardarChofer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarChoferActionPerformed(evt);
            }
        });

        btnGuardarModificaciones.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardarModificaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnGuardarModificaciones.setText("Guardar Modificaciones");
        btnGuardarModificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificacionesActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPane4Layout = new javax.swing.GroupLayout(jXTaskPane4.getContentPane());
        jXTaskPane4.getContentPane().setLayout(jXTaskPane4Layout);
        jXTaskPane4Layout.setHorizontalGroup(
            jXTaskPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jXTaskPane4Layout.createSequentialGroup()
                .addComponent(btnGuardarChofer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarModificaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar))
        );
        jXTaskPane4Layout.setVerticalGroup(
            jXTaskPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jXTaskPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardarModificaciones)
                    .addComponent(btnGuardarChofer)))
        );

        tablaReservasActivas.setBackground(new java.awt.Color(255, 102, 102));
        tablaReservasActivas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaReservasActivas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaReservasActivas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Cliente", "Domicilio", "Destino", "Móvil N°", "Chofer", "Tipo de viaje", "Fecha", "Inicio", "Fin", "Estado"
            }
        ));
        tablaReservasActivas.setGridColor(new java.awt.Color(0, 0, 0));
        tablaReservasActivas.setRowHeight(20);
        tablaReservasActivas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaReservasActivasMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tablaReservasActivas);

        jPanel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel31.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel31.setText("Reservas activas");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel31))
        );

        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel32.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel32.setText("Reservas pendientes");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel32))
        );

        javax.swing.GroupLayout jXTaskPaneContainer3Layout = new javax.swing.GroupLayout(jXTaskPaneContainer3);
        jXTaskPaneContainer3.setLayout(jXTaskPaneContainer3Layout);
        jXTaskPaneContainer3Layout.setHorizontalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane7)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jXTaskPane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jXTaskPaneContainer3Layout.setVerticalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer3Layout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Reservas", jPanel5);

        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel8.setText("Viajes realizados");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8))
        );

        tablaViajesRealizados.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaViajesRealizados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Móvil N°", "Chofer", "Origen", "Destino", "Utilidad", "Fecha", "Hora", "Inicio", "Costo"
            }
        ));
        tablaViajesRealizados.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane6.setViewportView(tablaViajesRealizados);
        if (tablaViajesRealizados.getColumnModel().getColumnCount() > 0) {
            tablaViajesRealizados.getColumnModel().getColumn(5).setHeaderValue("Fecha");
            tablaViajesRealizados.getColumnModel().getColumn(6).setHeaderValue("Hora");
            tablaViajesRealizados.getColumnModel().getColumn(7).setHeaderValue("Inicio");
            tablaViajesRealizados.getColumnModel().getColumn(8).setHeaderValue("Costo");
        }

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        checkPeriodoViaje.setFont(new java.awt.Font("Goudy Old Style", 0, 14)); // NOI18N
        checkPeriodoViaje.setText("Periodo de tiempo");
        checkPeriodoViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPeriodoViajeActionPerformed(evt);
            }
        });
        jPanel10.add(checkPeriodoViaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 8, -1, 22));

        jLabel17.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel17.setText("Desde:");
        jPanel10.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(214, 7, -1, -1));

        dp_desde.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_desde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_desdeActionPerformed(evt);
            }
        });
        jPanel10.add(dp_desde, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 8, -1, 24));

        jLabel18.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel18.setText("Hasta:");
        jPanel10.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 7, -1, -1));

        dp_hasta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_hasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_hastaActionPerformed(evt);
            }
        });
        jPanel10.add(dp_hasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(404, 8, 88, 24));

        jLabel40.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel40.setText("Fecha:");
        jPanel10.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(496, 7, -1, -1));

        fechaDeterminadoViaje.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fechaDeterminadoViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaDeterminadoViajeActionPerformed(evt);
            }
        });
        jPanel10.add(fechaDeterminadoViaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 8, -1, 24));

        cmbOpcionBusqueda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbOpcionBusqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N° Móvil", "Chofer", "Cliente", "Origen", "Ciudad", "Barrio", "Dirección", "Reserva", "Base", "Particular" }));
        jPanel10.add(cmbOpcionBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(782, 7, -1, 25));

        buscarViaje.setPrompt("Buscar un viaje");
        buscarViaje.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                buscarViajeCaretUpdate(evt);
            }
        });
        jPanel10.add(buscarViaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(863, 8, 126, 24));

        jButton7.setFont(new java.awt.Font("Goudy Old Style", 1, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Listar.png"))); // NOI18N
        jButton7.setText("Listado específico");
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(995, 8, -1, -1));

        btnListar.setBackground(new java.awt.Color(255, 255, 255));
        btnListar.setFont(new java.awt.Font("Goudy Old Style", 1, 12)); // NOI18N
        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER.png"))); // NOI18N
        btnListar.setText("Listar todo");
        btnListar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });
        jPanel10.add(btnListar, new org.netbeans.lib.awtextra.AbsoluteConstraints(637, 8, 131, -1));

        jLabel57.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel57.setText("Filtrar por:");
        jPanel10.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 7, -1, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel10.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(774, 2, -1, 36));

        javax.swing.GroupLayout jXTaskPaneContainer6Layout = new javax.swing.GroupLayout(jXTaskPaneContainer6);
        jXTaskPaneContainer6.setLayout(jXTaskPaneContainer6Layout);
        jXTaskPaneContainer6Layout.setHorizontalGroup(
            jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1134, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jXTaskPaneContainer6Layout.setVerticalGroup(
            jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer6Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Historial de viajes", jPanel9);

        jPanel2.setBackground(new java.awt.Color(117, 150, 227));

        jPanel28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel37.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel37.setText("Kilómetros realizados");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel37))
        );

        tablaKilometrosRecorridos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaKilometrosRecorridos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Móvil N°", "Chofer", "Fecha", "Hora", "Kms inicial", "Kms final", "Kms realizado", "Kms del viaje", "Diferencia"
            }
        ));
        tablaKilometrosRecorridos.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane8.setViewportView(tablaKilometrosRecorridos);

        dp_hastaKmsRec.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_hastaKmsRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_hastaKmsRecActionPerformed(evt);
            }
        });

        dp_desdeKmsRec.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_desdeKmsRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_desdeKmsRecActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel42.setText("Desde:");

        jLabel43.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel43.setText("Hasta:");

        SearchMovilKmsRecorridos.setPrompt("Ingrese un N° Móvil");
        SearchMovilKmsRecorridos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SearchMovilKmsRecorridos.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                SearchMovilKmsRecorridosCaretUpdate(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel44.setText("Desde:");

        jLabel45.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel45.setText("Hasta:");

        jLabel46.setFont(new java.awt.Font("Goudy Old Style", 0, 11)); // NOI18N
        jLabel46.setText("Hora");

        jLabel47.setFont(new java.awt.Font("Goudy Old Style", 0, 11)); // NOI18N
        jLabel47.setText("Min");

        jLabel48.setFont(new java.awt.Font("Goudy Old Style", 0, 11)); // NOI18N
        jLabel48.setText("Min");

        jLabel49.setFont(new java.awt.Font("Goudy Old Style", 0, 11)); // NOI18N
        jLabel49.setText("Hora");

        jLabel38.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel38.setText("Total Kms:");

        txtKilometrosTotalesRecoridos.setEditable(false);
        txtKilometrosTotalesRecoridos.setBackground(new java.awt.Color(204, 204, 204));
        txtKilometrosTotalesRecoridos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtKilometrosTotalesRecoridos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        fechaDeterminado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fechaDeterminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaDeterminadoActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel39.setText("Fecha particular:");

        checkPeriodo.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        checkPeriodo.setText("Periodo de tiempo");
        checkPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPeriodoActionPerformed(evt);
            }
        });

        hsDesde.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        hsDesde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
        hsDesde.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hsDesdeItemStateChanged(evt);
            }
        });
        hsDesde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                hsDesdePropertyChange(evt);
            }
        });

        minDesde.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        minDesde.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
        minDesde.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                minDesdeItemStateChanged(evt);
            }
        });
        minDesde.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                minDesdePropertyChange(evt);
            }
        });

        hrHasta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        hrHasta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
        hrHasta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hrHastaItemStateChanged(evt);
            }
        });
        hrHasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hrHastaActionPerformed(evt);
            }
        });
        hrHasta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                hrHastaPropertyChange(evt);
            }
        });

        minHasta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        minHasta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
        minHasta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                minHastaItemStateChanged(evt);
            }
        });
        minHasta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                minHastaPropertyChange(evt);
            }
        });

        jLabel51.setBackground(new java.awt.Color(0, 0, 0));
        jLabel51.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel51.setText(":");

        jLabel52.setBackground(new java.awt.Color(0, 0, 0));
        jLabel52.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel52.setText(":");

        checkPeriodo1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        checkPeriodo1.setText("Horas");
        checkPeriodo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPeriodo1ActionPerformed(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel58.setText("Filtrar por:");

        jLabel59.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel59.setText("Búsqueda específica:");

        jButton6.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Listar.png"))); // NOI18N
        jButton6.setText("Listado específico");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkPeriodo1)
                            .addComponent(checkPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addComponent(jLabel46)
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel47))
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addComponent(hsDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabel51)
                                        .addGap(0, 0, 0)
                                        .addComponent(minDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dp_desdeKmsRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(jLabel45)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel49)
                                    .addComponent(hrHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48)
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(minHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dp_hastaKmsRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fechaDeterminado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKilometrosTotalesRecoridos, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addComponent(SearchMovilKmsRecorridos, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel31Layout.createSequentialGroup()
                    .addGap(0, 566, Short.MAX_VALUE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 566, Short.MAX_VALUE)))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel58)
                .addGap(6, 6, 6)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dp_desdeKmsRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(checkPeriodo1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel47))
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(hsDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(minDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel51))))))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dp_hastaKmsRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43)
                            .addComponent(jLabel39)
                            .addComponent(fechaDeterminado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel44)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel49))
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(hrHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(minHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel52)
                                    .addComponent(jLabel45))))
                        .addGap(3, 3, 3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator5)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel59)
                .addGap(6, 6, 6)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchMovilKmsRecorridos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKilometrosTotalesRecoridos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel31Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jButton1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER 1.png"))); // NOI18N
        jButton1.setText("Listar por km total");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER 1.png"))); // NOI18N
        jButton3.setText("Listar por mayor km");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER 1.png"))); // NOI18N
        jButton4.setText("Listar por menor km");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER 1.png"))); // NOI18N
        jButton5.setText("Listar todo");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Goudy Old Style", 1, 12)); // NOI18N
        jLabel41.setText("Listar con filtro aplicando fecha y hora:");

        jLabel50.setFont(new java.awt.Font("Goudy Old Style", 1, 12)); // NOI18N
        jLabel50.setText("Listar con filtros aplicando fecha:");

        jButton8.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/computer.png"))); // NOI18N
        jButton8.setText("Visualizar");
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel41))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Historial de kilómetros", jPanel2);

        jPanel30.setBackground(new java.awt.Color(117, 150, 227));

        jPanel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel53.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel53.setText("Turnos realizado");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jLabel53)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel53)
                .addGap(473, 473, 473))
        );

        tablaKilómetrosEnServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Móvil N°", "Chofer", "Kilómetro incial", "Kilómetro final", "Kilómetros totales", "Fecha - Hora Inicio", "Fecha - Hora Finalizado"
            }
        ));
        jScrollPane9.setViewportView(tablaKilómetrosEnServicio);

        checkPeriodoServicio.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        checkPeriodoServicio.setText("Periodo de tiempo");
        checkPeriodoServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPeriodoServicioActionPerformed(evt);
            }
        });

        dp_desdeServicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_desdeServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_desdeServicioActionPerformed(evt);
            }
        });

        dp_hastaServicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_hastaServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_hastaServicioActionPerformed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel54.setText("Fecha particular");

        jLabel55.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel55.setText("Hasta:");

        jLabel56.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel56.setText("Desde:");

        fechaDeterminadoServico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fechaDeterminadoServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaDeterminadoServicoActionPerformed(evt);
            }
        });

        SearchServicio.setPrompt("Ingrese un N° Móvil");
        SearchServicio.setToolTipText("Buscar ");
        SearchServicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SearchServicio.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                SearchServicioCaretUpdate(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Goudy Old Style", 1, 12)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Listar.png"))); // NOI18N
        jButton9.setText("Listar todo");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Goudy Old Style", 1, 12)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER.png"))); // NOI18N
        jButton10.setText("Listado especifico");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkPeriodoServicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dp_desdeServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dp_hastaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechaDeterminadoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(SearchServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton10))
                    .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel54)
                                .addComponent(fechaDeterminadoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton9))
                            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(dp_desdeServicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel56)
                                .addComponent(dp_hastaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel55)))
                        .addComponent(checkPeriodoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 1134, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Historial de turnos", jPanel18);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void tablaDisponiblesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDisponiblesMouseClicked
        // TODO add your handling code here:
//        try{
            txtKmsInicial.setText(null);
            txtKmsFinalAnterior.setText(null);
            btnEstado.setEnabled(true);
            int fila = (int) tablaDisponibles.getSelectedRow();
            int numeroMovil = (int) tablaDisponibles.getValueAt(fila, 0);
            Movil unMovil = remiseria.buscarMovil(numeroMovil); 
            //txtKmsInicial.setText(String.valueOf(unMovil.getKilometraje()));
            KilometrosRecorridos kmsRec = unMovil.buscarUltimoKilometrajeRecorrido(unMovil);
             
            if(kmsRec != null)
            {
                txtKmsInicial.setText(String.valueOf(kmsRec.getKilometrosInicial()));
                txtKmsFinalAnterior.setText(String.valueOf(kmsRec.getKilometroFinal()));
                txtFechaInicial.setText(utilidades.getFecha(kmsRec.getFecha())+"-"+utilidades.getHora(kmsRec.getFecha()) );
               
            }
            else if (kmsRec== null)
            {
                 txtKmsInicial.setText(String.valueOf(unMovil.getKilometraje()));
                 KilometrosEnServicio kmSer = unMovil.buscarUltimoKilometrajeEnServicio(unMovil);
                 if(kmSer.getFechaHoraInicio()!= null)
                 {
                 txtFechaInicial.setText(utilidades.getFecha(kmSer.getFechaHoraInicio())+"-"+utilidades.getHora(kmSer.getFechaHoraInicio()));
                 }
            }
            
            if(evt.getClickCount() == 2)
            {
                JDialogConfiguracionEsporadica ventana = new JDialogConfiguracionEsporadica(parent, modal,remiseria, tablaDisponibles, tablaTransito, utilidades,unMaestro, unOperario);
                ventana.show();
            }
//        }
//        catch (Exception ex)
//        {
//            JOptionPane.showMessageDialog(null,ex, null, JOptionPane.ERROR_MESSAGE);
//        }
        
    }//GEN-LAST:event_tablaDisponiblesMouseClicked

    private void tablaTransitoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTransitoMouseClicked
        // TODO add your handling code here:
        btnCancelarViaje.setEnabled(true);
        btnFinalizarViaje.setEnabled(true);
        if(evt.getClickCount() == 2)
        {
            
            JDialogFinalizarViaje ventana = new JDialogFinalizarViaje(parent, modal, remiseria, utilidades, this.obtenerViaje(), tablaTransito, tablaDisponibles,tablaViajesRealizados,dp_desde, dp_hasta,null,unMaestro,unOperario);
            ventana.show();
        }
    }//GEN-LAST:event_tablaTransitoMouseClicked

    private void btnIniciarViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarViajeActionPerformed
        // TODO add your handling code here:
        try
        {
            if(tablaDisponibles.getSelectedRow()!= -1)
            {
                txtKmsInicial.setText(null);
                txtFechaInicial.setText(null);
                txtKmsFinalAnterior.setText(null);
                JDialogConfiguracionEsporadica ventana = new JDialogConfiguracionEsporadica(parent, modal,remiseria, tablaDisponibles, tablaTransito, utilidades,unMaestro, unOperario);
                ventana.show();
                txtKmsInicial.setText(null);
                txtKmsFinalAnterior.setText(null);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil Disponible para iniciar el viaje"," ",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnIniciarViajeActionPerformed

    private void btnCancelarViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarViajeActionPerformed
        // TODO add your handling code here:
        try
        {
            if(tablaTransito.getSelectedRow() != -1)
            {
                 int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de cancelar el Viaje seleccionado?");
                 if ( eleccion == 0)
                 {   
                    this.cancelarViaje();
                 }
            }
            else
            {
               JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil en Tránsito para cancelar el viaje"," ",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnCancelarViajeActionPerformed

    private void btnFinalizarViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarViajeActionPerformed
        // TODO add your handling code here:
        try
        {
            if(tablaTransito.getSelectedRow()!= -1)
            {
                JDialogFinalizarViaje ventana = new JDialogFinalizarViaje(parent, modal, remiseria, utilidades, this.obtenerViaje(), tablaTransito, tablaDisponibles,tablaViajesRealizados,dp_desde, dp_hasta,null,unMaestro,unOperario);
                ventana.show();
            }
            else
            {
               JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil en Tránsito para finalizar el viaje"," ",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
        }
                
    }//GEN-LAST:event_btnFinalizarViajeActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        btnViajes.setEnabled(true);
        this.dispose();

    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoActionPerformed
        // peneeTODO add your handling code here:
//        try{
        if(tablaDisponibles.getSelectedRow() != -1)
        {   
            txtKmsInicial.setText(null);
            txtKmsFinalAnterior.setText(null);
//        int fila = (int) tablaDisponibles.getSelectedRow();
//        int numeroMovil = (int) tablaDisponibles.getValueAt(fila, 0);
//        Movil unMovil = remiseria.buscarMovil(numeroMovil); 
//        //txtKmsInicial.setText(String.valueOf(unMovil.getKilometraje()));
//        KilometrosRecorridos kmsRec = unMovil.buscarUltimoKilometrajeRecorrido(unMovil);
//        if(kmsRec!= null)
//        {
//                if(kmsRec.getKilometroFinal()>0)
//                {
                      JDialogCambiarEstado ventana = new JDialogCambiarEstado(parent, modal, remiseria, this.obtenerMovilDisponible(), tablaDisponibles, tablaSinServicio, tablaDescanso, 0,utilidades);
                      ventana.show();
//                }
//                else
//                {
//                    JOptionPane.showMessageDialog(null,"Debe ingresar el Kilometro final en parada y debe ser \n mayor al Kilometro inicial en parada para cambiar de estado"," ",JOptionPane.ERROR_MESSAGE);
//                }
//            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Debe seleccionar un Movil disponibles para realizar el cambio de estado"," ",JOptionPane.ERROR_MESSAGE);
        }  
//        }
//        catch (Exception ex)
//        {
//            JOptionPane.showMessageDialog(null, ex);
//        }
    }//GEN-LAST:event_btnEstadoActionPerformed

    public void obtenerReservaSeleccionada()
    {
        try
        {
            int fila = (int) tablaReservas.getSelectedRow();
            int numeroReserva = (int) tablaReservas.getValueAt(fila, 0);
            Reserva unaReserva = remiseria.getReservas().get(numeroReserva);
            if(unaReserva.getDias()!= null && unaReserva.getDias().size()>0)
            {
                panelDias.setEnabled(true);
                panelDias.setVisible(true);
                lunes.setEnabled(true);
                martes.setEnabled(true);
                miercoles.setEnabled(true);
                jueves.setEnabled(true);
                viernes.setEnabled(true);
                sabado.setEnabled(true);
                domingo.setEnabled(true);
                checkRepetir.setSelected(true);
                cbxMovil.removeAllItems();
                cmbChofer.removeAllItems();
                cmbChofer.setEnabled(false);
                cbxMovil.setEnabled(false);
                cmbTipoDeCliente.setSelectedItem(unaReserva.getTipoCliente()); 
                Cliente unCliente = unaReserva.getUnCliente();
                if(unCliente != null)
                {
                    jLabel19.setVisible(false);
                    txtNombreEsporadico.setEnabled(false);
                    jLabel11.setVisible(true);
                    cmbCliente.setEnabled(true);
                    cmbCliente.setSelectedItem(unaReserva.getUnCliente());
                    jLabel22.setVisible(false);
                    cmbDireccion.setVisible(false);
                    cmbDireccion.setEnabled(false);
                    btnAgregarDireccion.setVisible(false);
                    btnDirreccion.setVisible(false);
                    jLabel23.setVisible(true);
                    cmbDireccionCliente.setVisible(true);
                    cmbDireccionCliente.setEditable(true);
                    Cliente unCliente1 =(Cliente) (unaReserva.getUnCliente());
                    Cliente cliente = remiseria.buscarCliente(unCliente1.getDni());
                    if(cliente != null)
                    {
                        Collection direcciones = cliente.getDomicilios().values();
                        Domicilio aux = null;
                        Iterator iter= direcciones.iterator();
                        while(iter.hasNext())
                        {
                            aux = (Domicilio) iter.next();
                            cmbDireccionCliente.addItem(aux);
                        }
                    }                    
                   // cmbDireccionCliente.setSelectedItem(unaReserva.);
                }               
                Dias aux = null;
                Collection dias = unaReserva.getDias().values();
                Iterator it = dias.iterator();
                while (it.hasNext())
                {
                    aux = (Dias)it.next();
                    for (Component unComponent : panelDias.getComponents())
                    {
                        if(aux.getDiaSemana().equals(unComponent.getName()))
                        {
                             ((JCheckBox) unComponent).setSelected(true);
                             break;
                        }
                    }
                }
                cmbHoraInicio.setSelectedItem(unaReserva.getHoraInicio().getHours());
                cmbMinutoInicio.setSelectedItem(unaReserva.getHoraInicio().getMinutes());
                cmbHoraFin.setSelectedItem(unaReserva.getHoraFin().getHours());
                cmbMinutoFin.setSelectedItem(unaReserva.getHoraFin().getMinutes());
                cmbCiudad.setSelectedItem( unaReserva.getUnaCiudad());        
                cmbBarrio.setSelectedItem(unaReserva.getUnBarrio());

                if(unaReserva.getUnPaisDestino() != null && unaReserva.getUnaProvinciaDestino()!= null && unaReserva.getUnaCiudadDestino()!= null)
                {
                    jCheckBox1.setSelected(true);
                    jPanel11.setEnabled(true);
                    jPanel11.setVisible(true);
                    cmbPaisDestino.setEnabled(true);
                    cmbProvinciaDestino.setEnabled(true);
                    cmbCiudadDestino.setEnabled(true);
                    txtDireccionDestino.setEnabled(true);
                    if(jPanel11.isEnabled()==true)
                    {
                        cmbPaisDestino.setSelectedItem(unaReserva.getUnPaisDestino()) ;//(Pais) (cmbPaisDestino.getSelectedItem());
                        cmbProvinciaDestino.setSelectedItem(unaReserva.getUnaProvinciaDestino());//(Provincia)(cmbProvinciaDestino.getSelectedItem());
                        cmbCiudadDestino.setSelectedItem(unaReserva.getUnaCiudadDestino());//(Ciudad)cmbCiudadDestino.getSelectedItem();        
                        txtDireccionDestino.setText(unaReserva.getDireccionDestino());
                    }
                }
                else
                {
                    jCheckBox1.setSelected(false);
                    jPanel11.setEnabled(false);
                    jPanel11.setVisible(false);
                    cmbPaisDestino.setEnabled(false);
                    cmbProvinciaDestino.setEnabled(false);
                    cmbCiudadDestino.setEnabled(false);
                    txtDireccionDestino.setEnabled(false);            
                }  
                cmbTipoViaje.setSelectedItem(unaReserva.getUnTipoUtilidad());
            }
            else
            {

                checkRepetir.setSelected(false);
                panelDias.setEnabled(false);
                panelDias.setVisible(false);
                lunes.setEnabled(false);
                martes.setEnabled(false);
                miercoles.setEnabled(false);
                jueves.setEnabled(false);
                viernes.setEnabled(false);
                sabado.setEnabled(false);
                domingo.setEnabled(false);            
                cmbTipoDeCliente.setSelectedItem(unaReserva.getTipoCliente()); 
                String nombreEsporadico = unaReserva.getNombreClienteEsporadicto();
                Cliente unCliente = unaReserva.getUnCliente();
                if(unCliente != null)
                {
                    jLabel19.setVisible(false);
                    txtNombreEsporadico.setEnabled(false);
                    jLabel11.setVisible(true);
                    cmbCliente.setEnabled(true);
                    cmbCliente.setSelectedItem(unaReserva.getUnCliente());
                }
                else if (nombreEsporadico != null)
                {
                    jLabel19.setVisible(true);
                    txtNombreEsporadico.setEnabled(true);
                    jLabel11.setVisible(false);
                    cmbCliente.setEnabled(false);
                    txtNombreEsporadico.setText(unaReserva.getNombreClienteEsporadicto());
                }     
                String estado = unaReserva.getEstado();
                jdcFecha.setDate(unaReserva.getFechaInicio());// jdcFecha.getDate();
                cmbHoraInicio.setSelectedItem(unaReserva.getHoraInicio().getHours());
                cmbMinutoInicio.setSelectedItem(unaReserva.getHoraInicio().getMinutes());
                cmbHoraFin.setSelectedItem(unaReserva.getHoraFin().getHours());
                cmbMinutoFin.setSelectedItem(unaReserva.getHoraFin().getMinutes());
                cmbTipoMovil.setSelectedItem(unaReserva.getUnTipoUtilidad());//(TipoUtilidad) cmbTipoViaje.getSelectedItem();
                cmbTipoMovil.setSelectedItem(unaReserva.getUnRodado());//(Rodado)cmbTipoMovil.getSelectedItem();
                if(unaReserva.getUnChofer()!= null)
                {
                    cmbChofer.setSelectedItem(unaReserva.getUnChofer()); 
                }
                if(unaReserva.getUnMovil()!= null)
                {
                    cbxMovil.setSelectedItem(unaReserva.getUnMovil());//(Movil) remiseria.buscarMovil(Integer.parseInt(cbxMovil.getSelectedItem().toString()));
                }
                cmbCiudad.setSelectedItem( unaReserva.getUnaCiudad());        
                cmbBarrio.setSelectedItem(unaReserva.getUnBarrio());
                if(unaReserva.getUnCliente() != null)
                {
                    jLabel22.setVisible(false);
                    cmbDireccion.setVisible(false);
                    cmbDireccion.setEnabled(false);
                    btnAgregarDireccion.setVisible(false);
                    btnDirreccion.setVisible(false);
                    jLabel23.setVisible(true);
                    cmbDireccionCliente.setVisible(true);
                    cmbDireccionCliente.setEditable(true);
                    Cliente unCliente1 =(Cliente) (unaReserva.getUnCliente());
                    Cliente cliente = remiseria.buscarCliente(unCliente1.getDni());
                    if(cliente != null)
                    {
                        Collection direcciones = cliente.getDomicilios().values();
                        Domicilio aux = null;
                        Iterator iter= direcciones.iterator();
                        while(iter.hasNext())
                        {
                            aux = (Domicilio) iter.next();
                            cmbDireccionCliente.addItem(aux);
                        }
                    }            
                    checkRepetir.setSelected(false);
                    panelDias.setEnabled(false);
                    panelDias.setVisible(false);
                    lunes.setEnabled(false);
                    martes.setEnabled(false);
                    miercoles.setEnabled(false);
                    jueves.setEnabled(false);
                    viernes.setEnabled(false);
                    sabado.setEnabled(false);
                    domingo.setEnabled(false);                            
                }
                else if(unaReserva.getNombreClienteEsporadicto()!= null)
                {
                    jLabel22.setVisible(true);
                    cmbDireccion.setVisible(true);
                    cmbDireccion.setEnabled(true);
                    btnAgregarDireccion.setVisible(true);
                    btnDirreccion.setVisible(true);
                    cmbDireccion.setSelectedItem(unaReserva.getUnaDireccionViaje());
                    jLabel23.setVisible(false);
                    cmbDireccionCliente.setVisible(false);
                    cmbDireccionCliente.setEditable(false);
                    checkRepetir.setSelected(false);
                    panelDias.setEnabled(false);
                    panelDias.setVisible(false);
                    lunes.setEnabled(false);
                    martes.setEnabled(false);
                    miercoles.setEnabled(false);
                    jueves.setEnabled(false);
                    viernes.setEnabled(false);
                    sabado.setEnabled(false);
                    domingo.setEnabled(false);                   
                }

                if(unaReserva.getUnPaisDestino() != null && unaReserva.getUnaProvinciaDestino()!= null && unaReserva.getUnaCiudadDestino()!= null)
                {
                    jCheckBox1.setSelected(true);
                    jPanel11.setEnabled(true);
                    jPanel11.setVisible(true);
                    cmbPaisDestino.setEnabled(true);
                    cmbProvinciaDestino.setEnabled(true);
                    cmbCiudadDestino.setEnabled(true);
                    txtDireccionDestino.setEnabled(true);
                    if(jPanel11.isEnabled()==true)
                    {
                        cmbPaisDestino.setSelectedItem(unaReserva.getUnPaisDestino()) ;//(Pais) (cmbPaisDestino.getSelectedItem());
                        cmbProvinciaDestino.setSelectedItem(unaReserva.getUnaProvinciaDestino());//(Provincia)(cmbProvinciaDestino.getSelectedItem());
                        cmbCiudadDestino.setSelectedItem(unaReserva.getUnaCiudadDestino());//(Ciudad)cmbCiudadDestino.getSelectedItem();        
                        txtDireccionDestino.setText(unaReserva.getDireccionDestino());
                    }
                    
                }
                else
                {
                    jCheckBox1.setSelected(false);
                    jPanel11.setEnabled(false);
                    jPanel11.setVisible(false);
                    cmbPaisDestino.setEnabled(false);
                    cmbProvinciaDestino.setEnabled(false);
                    cmbCiudadDestino.setEnabled(false);
                    txtDireccionDestino.setEnabled(false); 

                }     
                cmbTipoViaje.setSelectedItem(unaReserva.getUnTipoUtilidad());
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void tablaReservasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaReservasMouseClicked

    }//GEN-LAST:event_tablaReservasMouseClicked

    private void btnAgregarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarReservaActionPerformed
        // TODO add your handling code here:
        jXTaskPane4.setCollapsed(false);
        btnAgregarReserva.setEnabled(false);
        btnModificarReserva.setEnabled(false);
        btnEliminarReserva.setEnabled(false);
        btnFinalizarReserva.setEnabled(false);
        tablaReservasActivas.setEnabled(false);
        tablaReservas.setEnabled(false);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(true);
        btnCancelarViaje1.setEnabled(false);
        this.limpiar();
    }//GEN-LAST:event_btnAgregarReservaActionPerformed

    private void btnSalir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir2ActionPerformed
        // TODO add your handling code here:
        btnViajes.setEnabled(true);
        this.dispose();

    }//GEN-LAST:event_btnSalir2ActionPerformed

    private void btnEliminarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarReservaActionPerformed
        // TODO add your handling code here:
        if(tablaReservas.getSelectedRow()!=-1)
        {
            this.eleminarReserva();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una Reserva"," ",JOptionPane.ERROR_MESSAGE);
        }      
      
    }//GEN-LAST:event_btnEliminarReservaActionPerformed

    private void btnModificarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarReservaActionPerformed
        if(tablaReservas.getSelectedRow()!=-1)
        {
        this.limpiar();
        this.obtenerReservaSeleccionada();
        jXTaskPane4.setCollapsed(false);
        btnAgregarReserva.setEnabled(false);
        btnModificarReserva.setEnabled(false);
        btnEliminarReserva.setEnabled(false);
        btnFinalizarReserva.setEnabled(false);
        tablaReservasActivas.setEnabled(false);
        tablaReservas.setEnabled(false);
        btnGuardarModificaciones.setEnabled(true);
        btnGuardarChofer.setEnabled(false);
        btnCancelarViaje1.setEnabled(false);
        }
         else
               {
                   JOptionPane.showMessageDialog(null, "No ha seleccionado una Reserva para realizar la modificación");
               }
    }//GEN-LAST:event_btnModificarReservaActionPerformed

    private void tablaDescansoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDescansoMouseClicked
        // TODO add your handling code here:
        btnCambiarEstado.setEnabled(true);
        if(evt.getClickCount()==2)
        {
              txtKmsInicial.setText(null);
              txtKmsFinalAnterior.setText(null);
              JDialogCambiarEstado ventana = new JDialogCambiarEstado(parent, modal, remiseria, this.obtenerMovilDisponible(), tablaDisponibles, tablaSinServicio, tablaDescanso, 0,utilidades);
              ventana.show();
        }
    }//GEN-LAST:event_tablaDescansoMouseClicked

    private void btnSalir3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir3ActionPerformed
        // TODO add your handling code here:
        btnViajes.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnSalir3ActionPerformed

    private void btnCambiarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarEstadoActionPerformed
        // TODO add your handling code here:
        try{
        if(tablaDescanso.getSelectedRow() != -1)
        {
            JDialogCambiarEstado ventana = new JDialogCambiarEstado(parent, modal, remiseria, this.obtenerMovilDescanso(), tablaDisponibles, tablaSinServicio, tablaDescanso, 1,utilidades);
            ventana.show();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil en Descanso para cambiar el estado"," ",JOptionPane.ERROR_MESSAGE);
        }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex,null,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCambiarEstadoActionPerformed

    private void tablaSinServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSinServicioMouseClicked
        // TODO add your handling code here:
       
        if(evt.getClickCount()== 2)
        {
            
            Lote ultimoLote = unaCaja.obtenerLote_Vigente();
            if(ultimoLote == null )
            {
                JOptionPane.showMessageDialog(null, "Debe realizar la Apertura de la Caja, para Iniciar los viajes", null, JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                boolean ok = true; 
                 Collection choferes = remiseria.buscarChoferes();
                 Collection zonas = remiseria.buscarZonas();
                 Collection tarifas = remiseria.buscarTarifas();
                 if (choferes.isEmpty())
                 {
                    JOptionPane.showMessageDialog(null,"Registre Choferes para iniciar el turno","No hay Choferes registrados",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                 }
                 if(zonas.isEmpty())
                 {
                    JOptionPane.showMessageDialog(null,"Registre Zonas para iniciar el turno","No hay Zonas registradas",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                 }

                 if(tarifas.isEmpty())
                 {
                    JOptionPane.showMessageDialog(null,"Registre Tarifas para iniciar el turno","No hay Tarifas registradas",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                 }
                 if(ok == true)
                 {
                     if(tablaSinServicio.getSelectedRow()!= -1)
                     {
                         if (es_delDia(loteVigente())) 
                         {      
                                btnIniciarTurno.setEnabled(true);
                                JDialogCambiarDisponible ventana = new JDialogCambiarDisponible(parent, modal, remiseria,utilidades, this.obtenerMovil(), tablaDisponibles, tablaSinServicio, unaCaja);
                                ventana.show();       
                         }              
                         else 
                         {
                            JOptionPane.showMessageDialog(this, "Debe realizar una nueva Apertura de Caja para continuar con esta operacion");
                         }  
                     }
                     else
                     {
                        JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil Fuera de Servicio para iniciar el turno"," ",JOptionPane.ERROR_MESSAGE);
                     }
                 }
            }    

        }
    }//GEN-LAST:event_tablaSinServicioMouseClicked

    private void btnSalir4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir4ActionPerformed
        // TODO add your handling code here:
        btnViajes.setEnabled(true);
        this.dispose();

    }//GEN-LAST:event_btnSalir4ActionPerformed
    public Lote loteVigente() {

        Lote vigente = null;
        Collection lotesAnteriores = unaCaja.getLotes().values();
        //antes solamente List
        LinkedList<Lote> lotesAnteriores2 = new LinkedList();
        Iterator iter = lotesAnteriores.iterator();
        Lote lote = null;
        while(iter.hasNext()){
            lote = (Lote) iter.next();
            lotesAnteriores2.add(lote);
        }
        Collections.sort(lotesAnteriores2, new Comparator<Lote>() {
                @Override
                public int compare(Lote p1, Lote p2) {                
                        return new Long(p1.getFechaLote().getTime()).compareTo(new Long(p2.getFechaLote().getTime()));
                }
        });        
//        if (!lotesAnteriores2.isEmpty()) {
//            vigente = (Lote) lotesAnteriores2.get(lotesAnteriores2.size() - 1);
//        }
        if (!lotesAnteriores2.isEmpty()) {
            vigente = (Lote) lotesAnteriores2.getLast();
        }
        return vigente;
    }
    private void btnIniciarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarTurnoActionPerformed
        // TODO add your handling code here:
        try
        {
       
                boolean ok = true; 
                 Collection choferes = remiseria.buscarChoferes();
                 Collection zonas = remiseria.buscarZonas();
                 Collection tarifas = remiseria.buscarTarifas();
                 if (choferes.isEmpty())
                 {
                    JOptionPane.showMessageDialog(null,"Registre Choferes para iniciar el turno","No hay Choferes registrados",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                 }
                 if(zonas.isEmpty())
                 {
                    JOptionPane.showMessageDialog(null,"Registre Zonas para iniciar el turno","No hay Zonas registradas",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                 }

                 if(tarifas.isEmpty())
                 {
                    JOptionPane.showMessageDialog(null,"Registre Tarifas para iniciar el turno","No hay Tarifas registradas",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                 }
                 if(ok == true)
                 {
                     if(tablaSinServicio.getSelectedRow()!= -1)
                     {
                         if (es_delDia(loteVigente())) 
                         {                        
                                JDialogCambiarDisponible ventana = new JDialogCambiarDisponible(parent, modal, remiseria,utilidades, this.obtenerMovil(), tablaDisponibles, tablaSinServicio, unaCaja);
                                ventana.show();       
                         }              
                         else 
                        {
                            JOptionPane.showMessageDialog(null, "Debe realizar la Apertura de la Caja, para Iniciar los viajes", null, JOptionPane.ERROR_MESSAGE);
                        }      
                     }
                     else
                     {
                        JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil Fuera de Servicio para iniciar el turno"," ",JOptionPane.ERROR_MESSAGE);
                     }
                 }
        
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnIniciarTurnoActionPerformed

    private void cbxZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZonaActionPerformed
        // TODO add your handling code here:
        if (tablaDisponibles.getSelectedRow() != -1){
            int fila = tablaDisponibles.getSelectedRow();
            int numeroMovil = (int) tablaDisponibles.getValueAt(fila, 0);
            Movil unMovil = remiseria.buscarMovil(numeroMovil);
            Zona unaZona = null;
            if(cbxZona.getSelectedItem()!= null)
            {
                unaZona = remiseria.buscarZona(cbxZona.getSelectedItem().toString());
            }
            else if(unMovil.getUnaZona()!= null)
            {          
                unaZona = unMovil.getUnaZona();
            }
            unMovil.setUnaZona(unaZona);
            remiseria.modificarMovilPorZona(unMovil);
            remiseria.agregarMovilPorZona(unMovil, unaZona, utilidades.getHoraActual(), utilidades.getFechaActual());
            this.cargarDisponibles();}
            
    }//GEN-LAST:event_cbxZonaActionPerformed

    private void dp_desdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_desdeActionPerformed
        if(dp_desde.getDate().getTime()< dp_hasta.getDate().getTime())
        {
            cargarTablaViajes(tablaViajesRealizados, listarViajes());
        }
        else
        {
            JOptionPane.showMessageDialog(null,"La Fecha hasta debe ser mayor a la Fecha desde");
        }        
    }//GEN-LAST:event_dp_desdeActionPerformed

    private void dp_hastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_hastaActionPerformed
        if(dp_desde.getDate().getTime()< dp_hasta.getDate().getTime())
        {
            cargarTablaViajes(tablaViajesRealizados, listarViajes());
        }
        else
        {
            JOptionPane.showMessageDialog(null,"La Fecha hasta debe ser mayor a la Fecha desde");
        }
    }//GEN-LAST:event_dp_hastaActionPerformed

    private void cbxMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMovilActionPerformed
        // TODO add your handling code here:
        if(cbxMovil.isEnabled()==true)
        {
            if (cbxMovil.getItemCount() != 0)
            {
                //this.buscarMovilesDispReservas();
                if(utilidades.isNumber(cbxMovil.getSelectedItem().toString()) ==true)
                {
                    Movil unMovil = remiseria.buscarMovil(Integer.parseInt(cbxMovil.getSelectedItem().toString()));
                    if(unMovil.getUnChofer()!= null)
                    {
                        cmbChofer.setEnabled(true);
                        cmbChofer.setSelectedItem(unMovil.getUnChofer());
                    }
                    else
                    {
                        cmbChofer.addItem("Sin chofer");
                        cmbChofer.setEnabled(true);
                        cmbChofer.setSelectedItem("Sin chofer");
                    }
                    cmbTipoViaje.setEnabled(true);
                }
                else
                {
                    cmbChofer.setSelectedItem("Sin chofer");
                    cmbChofer.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_cbxMovilActionPerformed

    private void jdcFechaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jdcFechaMouseClicked
        // TODO add your handling code here:
        jdcFecha.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jdcFechaMouseClicked

    private void jdcFechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcFechaPropertyChange
        // TODO add your handling code here:
        if(jdcFecha.getDate()!= null)
        {
            this.buscarMovilesDispReservas();
            cbxMovil.setEnabled(true);
            //cmbChofer.setEnabled(true);
            this.cargarChoferesCombo();
        }
    }//GEN-LAST:event_jdcFechaPropertyChange

    private void cmbTipoDeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoDeClienteActionPerformed
        // TODO add your handling code here:
        if (cmbTipoDeCliente.getItemCount()!= 0)
        {
            if(cmbTipoDeCliente.getSelectedItem().equals("Pasajero"))
            {
                cmbCiudad.setEnabled(true);
                cmbBarrio.setEnabled(true);
                btnCiudad.setEnabled(true);
                btnBarrio.setEnabled(true);
                txtNombreEsporadico.setEnabled(true);
                txtNombreEsporadico.setVisible(true);
                jLabel19.setVisible(true);
                cmbCliente.setVisible(false);
                cmbCliente.setEnabled(false);
                jLabel11.setVisible(false);
                jLabel22.setVisible(true);
                cmbDireccion.setEnabled(true);
                cmbDireccion.setVisible(true);
                btnAgregarDireccion.setVisible(true);
                btnDirreccion.setVisible(true);
                jLabel23.setVisible(false);
                cmbDireccionCliente.setVisible(false);
                cmbDireccionCliente.setEnabled(false);
                checkRepetir.setEnabled(false);
                checkRepetir.setVisible(false);
                panelDias.setEnabled(false);
                panelDias.setVisible(false);
                lunes.setEnabled(false);
                martes.setEnabled(false);
                miercoles.setEnabled(false);
                jueves.setEnabled(false);
                viernes.setEnabled(false);
                sabado.setEnabled(false);
                domingo.setEnabled(false);
                btnClientesReserva.setEnabled(false);
                btnClientesReserva.setVisible(false);
                btnClientesReserva1.setEnabled(false);
                btnClientesReserva1.setVisible(false);
            }
            else
            {
                if(cmbTipoDeCliente.getSelectedItem().equals("Cliente"))
                {
                    cmbCiudad.setEnabled(false);
                    cmbBarrio.setEnabled(false);
                    btnCiudad.setEnabled(false);
                    btnBarrio.setEnabled(false);
                    checkRepetir.setEnabled(true);
                    checkRepetir.setVisible(true);
                    txtNombreEsporadico.setEnabled(false);
                    txtNombreEsporadico.setVisible(false);
                    jLabel19.setVisible(false);
                    cmbCliente.setVisible(true);
                    cmbCliente.setEnabled(true);
                    jLabel11.setVisible(true);
                    jLabel22.setVisible(false);
                    cmbDireccion.setEnabled(false);
                    cmbDireccion.setVisible(false);
                    btnAgregarDireccion.setVisible(false);
                    btnDirreccion.setVisible(false);
                    jLabel23.setVisible(true);
                    cmbDireccionCliente.setVisible(true);
                    cmbDireccionCliente.setEnabled(true);
                    
                    btnClientesReserva.setEnabled(true);
                    btnClientesReserva1.setEnabled(true);
                    btnClientesReserva.setVisible(true);
                    btnClientesReserva1.setVisible(true); 
                    if(this.unOperario != null)
                    {
                        Object unUsuario = this.unOperario;
                        if (unUsuario instanceof Operario) 
                        {
                           Map<String, Permisos> permisos = ((Operario) unUsuario).getUnRol().getPermisos();
                           if (permisos.containsKey("permitirGestionDeClientes")) 
                           {
                                btnClientesReserva.setEnabled(true);
                                btnClientesReserva1.setEnabled(true);
                                btnClientesReserva.setVisible(true);
                                btnClientesReserva1.setVisible(true);     
                           }
                           else
                           {
                                btnClientesReserva.setEnabled(false);
                                btnClientesReserva1.setEnabled(false);
                                btnClientesReserva.setVisible(false);
                                btnClientesReserva1.setVisible(false);                            
                           }
                         }   
                    }
                    else
                    {
                        btnClientesReserva.setEnabled(true);
                        btnClientesReserva1.setEnabled(true);
                        btnClientesReserva.setVisible(true);
                        btnClientesReserva1.setVisible(true);                      
                    }
                    cmbCliente.removeAllItems();
                    Collection clientes = remiseria.buscarClientes();
                    if(clientes != null)
                    {
                        Cliente aux = null;
                        Iterator iter = clientes.iterator();
                        while (iter.hasNext())
                        {
                            aux = (Cliente) iter.next();
                            cmbCliente.addItem(aux);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"No hay Cliente registrados, por favor registre al menos un Cliente para iniciar el viaje"," ",JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
                txtNombreEsporadico.setEnabled(false);
                txtNombreEsporadico.setText(" ");
            }
        }
    }//GEN-LAST:event_cmbTipoDeClienteActionPerformed

    private void cmbTipoViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoViajeActionPerformed

    }//GEN-LAST:event_cmbTipoViajeActionPerformed

    private void txtNombreEsporadicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreEsporadicoActionPerformed
        // TODO add your handling code here:
        txtNombreEsporadico.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtNombreEsporadicoActionPerformed

    private void txtNombreEsporadicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreEsporadicoKeyTyped
        // TODO add your handling code here:
        jdcFecha.setEnabled(true);
        int limite  = 25;
        {if (txtNombreEsporadico.getText().length()== limite)
            evt.consume();
        }          
    }//GEN-LAST:event_txtNombreEsporadicoKeyTyped

    private void cmbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbClienteActionPerformed
        // TODO add your handling code here:
        try
        {
            cmbCiudad.setEnabled(false);
            cmbBarrio.setEnabled(false);
            if(cmbCliente.getItemCount() != 0)
            {
                cmbDireccionCliente.removeAllItems();
                String nombre = cmbTipoDeCliente.getSelectedItem().toString();
                if(nombre.equals("Cliente"))
                {
                    Cliente unCliente =(Cliente) (cmbCliente.getSelectedItem());
                    Cliente cliente = remiseria.buscarCliente(unCliente.getDni());
                    if(cliente != null)
                    {
                        Collection direcciones = cliente.getDomicilios().values();
                        Domicilio aux = null;
                        Iterator iter= direcciones.iterator();
                        while(iter.hasNext())
                        {
                            aux = (Domicilio) iter.next();
                            cmbDireccionCliente.addItem(aux);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar la direccion Cliente seleccionado");
        }
    }//GEN-LAST:event_cmbClienteActionPerformed

    private void cmbHoraInicioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbHoraInicioItemStateChanged
        // TODO add your handling code here:
        this.buscarMovilesDispReservas();
    }//GEN-LAST:event_cmbHoraInicioItemStateChanged

    private void cmbMinutoInicioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMinutoInicioItemStateChanged
        // TODO add your handling code here:
        this.buscarMovilesDispReservas();
    }//GEN-LAST:event_cmbMinutoInicioItemStateChanged

    private void cmbHoraFinItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbHoraFinItemStateChanged
        // TODO add your handling code here:
        this.buscarMovilesDispReservas();
    }//GEN-LAST:event_cmbHoraFinItemStateChanged

    private void cmbMinutoFinItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMinutoFinItemStateChanged
        // TODO add your handling code here:
        this.buscarMovilesDispReservas();
    }//GEN-LAST:event_cmbMinutoFinItemStateChanged

    private void cmbCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCiudadActionPerformed
        // TODO add your handling code here:
        if (cmbCiudad.getItemCount() != 0)
        {
            cmbBarrio.removeAllItems();
            cmbDireccion.removeAllItems();
            String nombrePais = remiseria.getUnDomicilio().getUnPais().getNombrePais();
            Pais unPais = remiseria.buscarPais(nombrePais);
            if (unPais != null)
            {
                String nombreProvincia = remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia();
                Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
                if(unaProvincia != null)
                {
                    String nombreCiudad = cmbCiudad.getSelectedItem().toString();
                    if(!nombreCiudad.isEmpty())
                    {
                        Ciudad unaCiudad = unaProvincia.buscarCiudad(nombreCiudad);
                        if(unaCiudad != null)
                        {
                            this.cargarBarriosCombo(unaCiudad);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_cmbCiudadActionPerformed

    private void btnAgregarDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDireccionActionPerformed
        // TODO add your handling code here:
        if(!cmbDireccion.getSelectedItem().toString().isEmpty())
        {
            this.agregarNuevaDireccion();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado la Dirección"," ",JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_btnAgregarDireccionActionPerformed

    private void btnDirreccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirreccionActionPerformed
        // TODO add your handling code here:
        try{
            //if(cmbCiudad.getSelectedItem().toString()!= ""){
                Pais unPais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
                Provincia unaProvincia = unPais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia());
                Ciudad unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
                Barrio unBarrio = unaCiudad.buscarBarrio(cmbBarrio.getSelectedItem().toString());
                JDialogDireccion ventana = new JDialogDireccion (parent,modal,cmbDireccion,unBarrio,remiseria );
                ventana.show();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado un Barrio"," ",JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btnDirreccionActionPerformed

    private void btnBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarrioActionPerformed
        // TODO add your handling code here:
        try{
            //if(cmbCiudad.getSelectedItem().toString()!= ""){
                Pais unPais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
                Provincia unaProvincia = unPais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia());
                Ciudad unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
                JDialogBarrio ventana = new JDialogBarrio (parent,modal,cmbBarrio,unaCiudad,remiseria );
                ventana.show();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado una Ciudad"," ",JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btnBarrioActionPerformed

    private void btnCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCiudadActionPerformed
        // TODO add your handling code here:
        Pais unPais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
        Provincia unaProvincia = unPais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia());
        JDialogCiudad ventana = new JDialogCiudad(parent, modal,cmbCiudad, unaProvincia,remiseria);
        ventana.show();
    }//GEN-LAST:event_btnCiudadActionPerformed

    private void cmbProvinciaDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinciaDestinoActionPerformed
        // TODO add your handling code here:
        if (cmbProvinciaDestino.getItemCount()!= 0)
        {
            String nombrePais = cmbPaisDestino.getSelectedItem().toString();
            if(!nombrePais.isEmpty())
            {
                Pais unPais = remiseria.buscarPais(nombrePais);
                if (unPais != null)
                {
                    String nombreProvincia = cmbProvinciaDestino.getSelectedItem().toString();
                    if(!nombreProvincia.isEmpty())
                    {
                        Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
                        if(unaProvincia != null)
                        {
                            this.cargarCiudadesComboDestino(unaProvincia);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_cmbProvinciaDestinoActionPerformed

    private void cmbPaisDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaisDestinoActionPerformed
        // TODO add your handling code here:
        if (cmbPaisDestino.getItemCount()!= 0)
        {
            String pais = (cmbPaisDestino.getSelectedItem().toString());
            if(!pais.isEmpty())
            {
                Pais unPais = remiseria.buscarPais(pais);
                if(unPais != null)
                {
                    this.cargarProvinciasDestino(unPais);
                }
            }
        }
    }//GEN-LAST:event_cmbPaisDestinoActionPerformed

    private void btnPais1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPais1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPais1ActionPerformed

    private void btnProvincia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProvincia1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProvincia1ActionPerformed

    private void bnCiudad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnCiudad1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bnCiudad1ActionPerformed
    public void cargarComboMovilSegunTipoMovil(Collection movilesSegTipoMovil)
    {
        if(movilesSegTipoMovil!= null)
        {
            cbxMovil.removeAllItems();
            cbxMovil.addItem("Sin móvil");
            Movil aux = null;
            Iterator iter = movilesSegTipoMovil.iterator();
            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                cbxMovil.addItem(aux.getNumeroMovil());      
            }  
        }
    }
    private void cmbTipoMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMovilActionPerformed
        // TODO add your handling code here:
        if(cmbTipoMovil.getItemCount()!= 0)
        {
            Collection movilesDisponiblesRes = movilesSegunDisponibilidad;
            if(movilesDisponiblesRes != null)
            {
                List filtro = new LinkedList();
                Movil aux = null;
                Iterator iter = movilesDisponiblesRes.iterator();
                while (iter.hasNext())
                {
                        aux = (Movil) iter.next();
                        if (aux.getUnRodado().equals(cmbTipoMovil.getSelectedItem())) 
                        {
                            if(aux.getEstado().equals("disponible"))
                            {
                                filtro.add(aux);
                            }
                        }
                } 
                cargarComboMovilSegunTipoMovil(filtro);
            }
        }                
    }//GEN-LAST:event_cmbTipoMovilActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        if(jCheckBox1.isSelected()== false)
        {
            jPanel11.setEnabled(false);
            jPanel11.setVisible(false);
            cmbPaisDestino.setEnabled(false);
            cmbProvinciaDestino.setEnabled(false);
            cmbCiudadDestino.setEnabled(false);
            txtDireccionDestino.setEnabled(false);
        }
        else
        {
            jPanel11.setEnabled(true);
            jPanel11.setVisible(true);
            cmbPaisDestino.setEnabled(true);
            cmbProvinciaDestino.setEnabled(true);
            cmbCiudadDestino.setEnabled(true);
            txtDireccionDestino.setEnabled(true);

        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void tablaReservasActivasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaReservasActivasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaReservasActivasMouseClicked

    private void btnFinalizarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarReservaActionPerformed
        // TODO add your handling code here:
        try
        {
            if(tablaReservasActivas.getSelectedRow()!= -1)
            {
                try
                {
                    Reserva unaReserva = this.obtenerReservaActiva();
                    Viaje unViaje = remiseria.getViajes().get(unaReserva.getNumeroDelViajeGenerado());
                    JDialogFinalizarViaje ventana = new JDialogFinalizarViaje(parent, modal, remiseria, utilidades, unViaje, tablaTransito, tablaDisponibles,tablaViajesRealizados,dp_desde, dp_hasta,unaReserva,unMaestro,unOperario);
                    ventana.show();
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null,ex,null, JOptionPane.ERROR_MESSAGE);
                }

            }
            else
            {
               JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil en Tránsito para finalizar el viaje"," ",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnFinalizarReservaActionPerformed

    private void txtNombreEsporadicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreEsporadicoMouseClicked
        // TODO add your handling code here:
        txtNombreEsporadico.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtNombreEsporadicoMouseClicked

    private void checkRepetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRepetirActionPerformed
        // TODO add your handling code here:
        if(checkRepetir.isSelected()==true)
        {
            jdcFecha.setEnabled(false);
            panelDias.setEnabled(true);
            panelDias.setVisible(true);
            lunes.setEnabled(true);
            martes.setEnabled(true);
            miercoles.setEnabled(true);
            jueves.setEnabled(true);
            viernes.setEnabled(true);
            sabado.setEnabled(true);
            domingo.setEnabled(true);
            cbxMovil.setEnabled(false);
            cbxMovil.removeAllItems();
            cmbChofer.setEnabled(false);
            cmbChofer.removeAllItems();
        }
        else
        {
            jdcFecha.setEnabled(true);
            panelDias.setEnabled(false);
            panelDias.setVisible(false);
            lunes.setEnabled(false);
            martes.setEnabled(false);
            miercoles.setEnabled(false);
            jueves.setEnabled(false);
            viernes.setEnabled(false);
            sabado.setEnabled(false);
            domingo.setEnabled(false);  
            cbxMovil.setEnabled(true);
            cmbChofer.setEnabled(true);
            cmbTipoMovil.setEnabled(true);
            this.cargarChoferesCombo();
        }
    }//GEN-LAST:event_checkRepetirActionPerformed

    private void cmbHoraInicioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbHoraInicioPropertyChange
        // TODO add your handling code here:
            this.buscarMovilesDispReservas();
      
    }//GEN-LAST:event_cmbHoraInicioPropertyChange

    private void cmbMinutoInicioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbMinutoInicioPropertyChange
        // TODO add your handling code here:
            this.buscarMovilesDispReservas();
 
    }//GEN-LAST:event_cmbMinutoInicioPropertyChange

    private void cmbHoraFinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbHoraFinPropertyChange
        // TODO add your handling code here:
            this.buscarMovilesDispReservas();
 
    }//GEN-LAST:event_cmbHoraFinPropertyChange

    private void cmbMinutoFinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbMinutoFinPropertyChange
        // TODO add your handling code here:
            this.buscarMovilesDispReservas();

    }//GEN-LAST:event_cmbMinutoFinPropertyChange

    private void txtDireccionDestinoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDireccionDestinoMouseClicked
        // TODO add your handling code here:
        txtDireccionDestino.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtDireccionDestinoMouseClicked

    private void cmbDireccionClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDireccionClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDireccionClienteActionPerformed

    private void cmbBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBarrioActionPerformed
        // TODO add your handling code here:
        if (cmbBarrio.getItemCount()!= 0)
        {
            String nombrePais = remiseria.getUnDomicilio().getUnPais().getNombrePais();
            Pais unPais = remiseria.buscarPais(nombrePais);
            if (unPais != null)
            {
                String nombreProvincia = remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia();
                Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
                if(unaProvincia != null)
                {
                    String nombreCiudad = cmbCiudad.getSelectedItem().toString();
                    if(!nombreCiudad.isEmpty())
                    {
                        Ciudad unaCiudad = unaProvincia.buscarCiudad(nombreCiudad);
                        if(unaCiudad != null)
                        {
                            String nombreBarrio = cmbBarrio.getSelectedItem().toString();
                            if(!nombreBarrio.isEmpty())
                            {
                                Barrio unBarrio = unaCiudad.buscarBarrio(nombreBarrio);
                                if(unBarrio != null)
                                {
                                    this.cargarDireccionesAutoCompleter(unBarrio);
                                }
                            }
                        }
                    }
                }
            }
        }        
    }//GEN-LAST:event_cmbBarrioActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        rootPane.setDefaultButton(jButton2);   
        
    }//GEN-LAST:event_jTextField1KeyPressed
        public static boolean esEntero(char caracter) {
        boolean res = true;
        //12 = borrar; 127 = suprimir; 46 = punto; 44 = coma
        if (!Character.isDigit(caracter)) {
            if (caracter != (char) '\b') {
                if (caracter != (char) 127) {
                    if (caracter != (char) 46) {
                        res = false;
                        java.awt.Toolkit.getDefaultToolkit().beep();
                    }

                }
            }
        }
        return res;
    }
        
    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }        
                int limite  = 7;
        {if ( jTextField1.getText().length()== limite)
            evt.consume();
        }     
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }          
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try
        {
            if(!jTextField1.getText().toString().isEmpty())
            {
                if(tablaDisponibles.getSelectedRow()!= -1)
                {
                    if(utilidades.isNumber(jTextField1.getText()))
                    {
                            int valor = Integer.valueOf(jTextField1.getText());
                            int fila = (int) tablaDisponibles.getSelectedRow();
                            int numeroMovil = (int) tablaDisponibles.getValueAt(fila, 0);
                            Movil unMovil = remiseria.buscarMovil(numeroMovil); 
                            KilometrosRecorridos kmR = unMovil.buscarUltimoKilometrajeRecorrido(unMovil);
                            if( kmR!= null)
                            {

                                if(valor > unMovil.getKilometraje())
                                {
                                    int valorInit = Integer.parseInt(txtKmsInicial.getText());
                                    int eleccion = JOptionPane.showConfirmDialog(null, "Kilómetro inicial: "+valorInit +"\n Kilómetro final: "+ valor +"\n ¿El Kilometraje Final del móvil ingresado es correcto?");
                                    if ( eleccion == 0)
                                    {  
                                        //KilometrosRecorridos kmRec = unMovil.buscarUltimoKilometrajeRecorrido(unMovil);
                                        KilometrosRecorridos kmRec2 = unMovil.modificarKilometrajeRecorridoPorVuelta(kmR, valor, unMovil,remiseria);
                                        unMovil.modificarkmsIn(valor,unMovil);
                                        txtKmsInicial.setText(String.valueOf(kmRec2.getKilometrosInicial()));
                                        txtKmsFinalAnterior.setText(String.valueOf(kmRec2.getKilometroFinal()));
                                        jTextField1.setText(null);
                                        this.cargarKilometrosRecorridos();
                                    }
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null,"Ha ingresado un Kilometraje Final menor o igual al Kilometraje Inicial", null, JOptionPane.ERROR_MESSAGE);
                                    jTextField1.setBackground(java.awt.Color.red);
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"Debe iniciar un viaje para poder cargar un Kilometrake Final nuevo", null, JOptionPane.ERROR_MESSAGE);
                                jTextField1.setText(null);
                            }
                      }                  

                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil disponible", null, JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado el Kilometro final en parada",null, JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex)
        {
             JOptionPane.showMessageDialog(null,"El móvil no tiene asignado aún los kms recorridos,\n debe iniciar un nuevo viaje para poder cargar el kilometro final en parada",null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        // TODO add your handling code here:
        jTextField1.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jTextField1MouseClicked

    private void buscarViajeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_buscarViajeCaretUpdate
        // TODO add your handling code here:
        try{
        Viaje aux = null;
        List viajes =  this.listarViajes();
        List filtro = new LinkedList();
        if (!buscarViaje.getText().isEmpty()) 
        {
            listaViajesReportes.clear();
            Iterator iter = viajes.iterator();
            while (iter.hasNext())
            {
                aux = (Viaje) iter.next();
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("N° Móvil"))
                {
                        if (String.valueOf(aux.getUnMovil().getNumeroMovil()).equals(buscarViaje.getText())) 
                        {
                            filtro.add(aux);
                        }

                        this.cargarTablaViajes(tablaViajesRealizados, filtro);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("Chofer"))
                {
                        if (aux.getUnChofer().toString().toUpperCase().startsWith(buscarViaje.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }

                        this.cargarTablaViajes(tablaViajesRealizados, filtro);
                }
                
                 if(cmbOpcionBusqueda.getSelectedItem().equals("Origen"))
                {
                        if (aux.getUnaZona().getNombreZona().toUpperCase().startsWith(buscarViaje.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }

                        this.cargarTablaViajes(tablaViajesRealizados, filtro);
                }
                if(cmbOpcionBusqueda.getSelectedItem().equals("Cliente"))
                {
                        if(aux.getUnCliente()!= null)
                        {
                            if (aux.getUnCliente().toString().toUpperCase().startsWith(buscarViaje.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                            this.cargarTablaViajes(tablaViajesRealizados, filtro);
                        }
                }  
                if(cmbOpcionBusqueda.getSelectedItem().equals("Barrio"))
                {
                            if (aux.getUnDomiclio().getUnBarrio().toString().toUpperCase().startsWith(buscarViaje.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                            this.cargarTablaViajes(tablaViajesRealizados, filtro);
                }                  
                if(cmbOpcionBusqueda.getSelectedItem().equals("Ciudad"))
                {
                            if (aux.getUnDomiclio().getUnaCiudad().toString().toUpperCase().startsWith(buscarViaje.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                            this.cargarTablaViajes(tablaViajesRealizados, filtro);
                }  
                if(cmbOpcionBusqueda.getSelectedItem().equals("Dirección"))
                {
                            if (aux.getUnDomiclio().getUnaDireccionViaje().toString().toUpperCase().startsWith(buscarViaje.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                            this.cargarTablaViajes(tablaViajesRealizados, filtro);
                }  
                if(cmbOpcionBusqueda.getSelectedItem().equals("Reserva"))
                {
                    if(aux.getViajeReservado()!= null)
                    {
                            if (String.valueOf(aux.getUnMovil().getNumeroMovil()).equals(buscarViaje.getText())) 
                            {
                                filtro.add(aux);
                            }
                            this.cargarTablaViajes(tablaViajesRealizados, filtro);
                    }    
                } 
                if(cmbOpcionBusqueda.getSelectedItem().equals("Base"))
                {
                    if (String.valueOf(aux.getUnMovil().getNumeroMovil()).equals(buscarViaje.getText())) 
                    {
                        if(aux.getAsignadoBase().equals("Base"))
                        {
                            filtro.add(aux);
                        }
                    }
                    this.cargarTablaViajes(tablaViajesRealizados, filtro); 
                }  
                if(cmbOpcionBusqueda.getSelectedItem().equals("Particular"))
                {
                    if (String.valueOf(aux.getUnMovil().getNumeroMovil()).equals(buscarViaje.getText())) 
                    {
                        if(aux.getAsignadoBase().equals("Particular"))
                        {
                            filtro.add(aux);
                        }
                    }
                    this.cargarTablaViajes(tablaViajesRealizados, filtro);
                }                  
            }
            this.cargarViajesReporte(filtro);
        } 
        else 
        {
            this.cargarTablaViajes(tablaViajesRealizados, listarViajes());
        }   
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_buscarViajeCaretUpdate

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        // TODO add your handling code here:
        try{
                List<ImprimirViajes> l  =    this.cargarViajesTodosReporte(); 
                try{
                     HashMap<String, Object> parametros = new HashMap();
                     parametros.clear();
                     if(unMaestro != null)
                     {
                          parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          parametros.put("total", String.valueOf(l.size()));
                          if(dp_desde.isEnabled()==true&& dp_hasta.isEnabled())
                          {
                            if(dp_desde.getDate()!= null || dp_hasta.getDate()!= null)
                            {
                                   parametros.put("fechaDesde",utilidades.getFecha(dp_desde.getDate()) );
                                   parametros.put("fechaHasta",utilidades.getFecha(dp_hasta.getDate()) );

                            }
                          }
                          else if (fechaDeterminadoViaje.isEnabled()==true)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminadoViaje.getDate()) );
                             parametros.put("fechaHasta", null ); 
                          }                          
                    }
                    else if(unOperario != null)
                    {
                          parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          parametros.put("total", String.valueOf(l.size()));                         
                         if(dp_desde.isEnabled()==true&& dp_hasta.isEnabled())
                          {
                            if(dp_desde.getDate()!= null || dp_hasta.getDate()!= null)
                            {
                                   parametros.put("fechaDesde",utilidades.getFecha(dp_desde.getDate()) );
                                   parametros.put("fechaHasta",utilidades.getFecha(dp_hasta.getDate()) );

                            }
                          }
                          else if (fechaDeterminadoViaje.isEnabled()==true)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminadoViaje.getDate()) );
                             parametros.put("fechaHasta",null ); 
                          }                             
                    }  
                     //C:/Users/garba/Desktop/Reportes/
                    //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/ReporteViajes.jrxml");
                     JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/ReporteViajes.jrxml");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(l));
                    listaViajesReportes.clear();
                    JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                    JDialog frame;
                    frame = new JDialog();
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(jviewer.getContentPane());
                    frame.pack();
                    frame.setSize(1100, 600);
                    frame.setModal(true);
                    frame.setLocationRelativeTo(null);                          
                    frame.show();

                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 }  
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No hay Información de Viajes para realizar el reporte", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnListarActionPerformed

    private void dp_hastaKmsRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_hastaKmsRecActionPerformed
        // TODO add your handling code here:
        if(dp_hastaKmsRec.getDate().getTime()> dp_desdeKmsRec.getDate().getTime())
        {
        this.cargarKilometrosRecorridosSegunPeriodoOFechaFija();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"La Fecha hasta debe ser mayor a la Fecha desde");
        }
    }//GEN-LAST:event_dp_hastaKmsRecActionPerformed

    private void dp_desdeKmsRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_desdeKmsRecActionPerformed
        // TODO add your handling code here:
        if(dp_hastaKmsRec.getDate().getTime()> dp_desdeKmsRec.getDate().getTime())
        {
        this.cargarKilometrosRecorridosSegunPeriodoOFechaFija();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"La Fecha hasta debe ser mayor a la Fecha desde");
        }
    }//GEN-LAST:event_dp_desdeKmsRecActionPerformed

    private void SearchMovilKmsRecorridosCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_SearchMovilKmsRecorridosCaretUpdate
        // TODO add your handling code here:
        // TODO add your handling code here:        
        if(!SearchMovilKmsRecorridos.getText().isEmpty())
        {
                listaMovilUnicoConRecorridos.clear();
                if(utilidades.isNumber(SearchMovilKmsRecorridos.getText()))
                {
                    int valor = Integer.valueOf(SearchMovilKmsRecorridos.getText());
                    Movil unMovil = remiseria.buscarMovil(valor);
                    if(unMovil!= null)
                    {
                        this.listaMovKmsRecSegunFlitro(unMovil);
                    }
                }
        }
        else 
        {
            this.cargarKilometrosRecorridos();
        }        
            
    }//GEN-LAST:event_SearchMovilKmsRecorridosCaretUpdate

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try
        {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
        LinkedList <ImprimirMovilKmsRecorridosTotalesSegunPeriodo> lista = new LinkedList<ImprimirMovilKmsRecorridosTotalesSegunPeriodo>();
        Collection moviles = remiseria.buscarMoviles();
        Collection kmsMovil = new LinkedList();
        Movil movil = null;
        int total = 0;
        double kmsViajes = 0.0;
        double diferencia = 0.0;
        KilometrosRecorridos kmsRec = null;
        Iterator iter = moviles.iterator();     
        if(dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true)
        {
            Date desde = dp_desdeKmsRec.getDate();
            Date hasta = dp_hastaKmsRec.getDate();
            if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
            {        
                while(iter.hasNext())
                {
                    movil = (Movil) iter.next();
                    if(movil.getKilometrosRecorridos()!= null && movil.getKilometrosRecorridos().size()>0 )
                    {
                        kmsMovil = movil.getKilometrosRecorridos().values();
                        Iterator iter2 = kmsMovil.iterator();
                        while(iter2.hasNext())
                        {
                            kmsRec =(KilometrosRecorridos) iter2.next();
                            Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha())); 

                                if ((fec.getTime() == desde.getTime()) || (fec.getTime() > desde.getTime())) 
                                {
                                    if ((fec.getTime() == hasta.getTime()) || (fec.getTime() < hasta.getTime())) 
                                    {  
                                        if(kmsRec.getTotalKilometrajeVuelta()>0)
                                        {
                                            total = total + kmsRec.getTotalKilometrajeVuelta();
                                            kmsViajes = kmsViajes + kmsRec.getKmsEnUltimaViajeRealizado();
                                            diferencia = diferencia + kmsRec.getDiferencia();
                                        }
                                    }
//                                
//                                else
//                                {
//                                    JOptionPane.showMessageDialog(null,"No ha ingresado un periodo entre fecha");
//                                }
                             }   
                        }
                        if(total >0)
                        {
                            kmsViajes = Double.parseDouble(formateador.format(kmsViajes));
                            diferencia = Double.parseDouble(formateador.format(diferencia));
                            lista.add(new ImprimirMovilKmsRecorridosTotalesSegunPeriodo(String.valueOf(movil.getNumeroMovil()),String.valueOf(total),String.valueOf(kmsViajes),String.valueOf(diferencia)));             
                            total = 0;
                            kmsViajes = 0.0;
                            diferencia = 0.0;
                        }
                    }
                }
               try{
                    HashMap<String, Object> parametros = new HashMap();
                    parametros.clear();
                    if(unMaestro != null)
                    {
                         if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
                         {
                             parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                             parametros.put("fechaActual", utilidades.getFechaActual());
                             parametros.put("nombreEmpresa",remiseria.getNombre());
                             parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                             parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                             parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                             parametros.put("logo", remiseria.getLogo());                              
                             parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                             parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                         }
                   }
                   else if(unOperario != null)
                   {
                         if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
                         {
                             parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                             parametros.put("fechaActual", utilidades.getFechaActual());
                             parametros.put("nombreEmpresa",remiseria.getNombre());
                             parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                             parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                             parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                             parametros.put("logo", remiseria.getLogo());                              
                             parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                             parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                         }
                   }  
                    //C:/Users/garba/Desktop/Reportes/
                   //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/KilometrosRecorridos.jrxml");
                   JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/KilometrosRecorridos.jrxml");
                   JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(lista));
                   JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                   JDialog frame;
                   frame = new JDialog();
                   frame.getContentPane().removeAll();
                   frame.getContentPane().add(jviewer.getContentPane());
                   frame.pack();
                   frame.setSize(1100, 600);
                   frame.setModal(true);
                   frame.setLocationRelativeTo(null);                          
                   frame.show();

                } 
                catch (JRException ex) 
                {
                     JOptionPane.showMessageDialog(this, ex.getMessage());
                }    
           }
           else
           {
                JOptionPane.showMessageDialog(null,"No ha ingresado un periodo entre fecha");
           } 
        }
        else if (fechaDeterminado.isEnabled()==true)
        {
                while(iter.hasNext())
                {
                    movil = (Movil) iter.next();
                    if(movil.getKilometrosRecorridos()!= null && movil.getKilometrosRecorridos().size()>0)
                    {
                        kmsMovil = movil.getKilometrosRecorridos().values();
                        Iterator iter2 = kmsMovil.iterator();
                        while(iter2.hasNext())
                        {
                            kmsRec =(KilometrosRecorridos) iter2.next();
    
                            Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));   
                            if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                            {
                                if(kmsRec.getTotalKilometrajeVuelta()>0)
                                {
                                    total = total + kmsRec.getTotalKilometrajeVuelta();
                                    kmsViajes = kmsViajes + kmsRec.getKmsEnUltimaViajeRealizado();
                                    diferencia = diferencia + kmsRec.getDiferencia();                        
                                }
                            }
                        }
                        if(total>0)
                        {    
                            kmsViajes = Double.parseDouble(formateador.format(kmsViajes));
                            diferencia = Double.parseDouble(formateador.format(diferencia));
                            lista.add(new ImprimirMovilKmsRecorridosTotalesSegunPeriodo(String.valueOf(movil.getNumeroMovil()),String.valueOf(total),String.valueOf(kmsViajes),String.valueOf(diferencia)));             
                            total = 0;
                            kmsViajes = 0.0;
                            diferencia = 0.0;                            
                        }
                    }
                }
               try{
                    HashMap<String, Object> parametros = new HashMap();
                    parametros.clear();
                    if(unMaestro != null)
                    {
                             parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                             parametros.put("fechaActual", utilidades.getFechaActual());
                             parametros.put("nombreEmpresa",remiseria.getNombre());
                             parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                             parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                             parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                             parametros.put("logo", remiseria.getLogo());                              
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                             parametros.put("fechaHasta",null);                         
                   }
                   else if(unOperario != null)
                   {
                             parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                             parametros.put("fechaActual", utilidades.getFechaActual());
                             parametros.put("nombreEmpresa",remiseria.getNombre());
                             parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                             parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                             parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                             parametros.put("logo", remiseria.getLogo());                              
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                             parametros.put("fechaHasta",null);   
                   }  
                    //C:/Users/garba/Desktop/Reportes/
                   //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/KilometrosRecorridos.jrxml"); 
                   JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/KilometrosRecorridos.jrxml");
                   JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(lista));
                   JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                   JDialog frame;
                   frame = new JDialog();
                   frame.getContentPane().removeAll();
                   frame.getContentPane().add(jviewer.getContentPane());
                   frame.pack();
                   frame.setSize(1100, 600);
                   frame.setModal(true);
                   frame.setLocationRelativeTo(null);                          
                   frame.show();

                } 
                catch (JRException ex) 
                {
                     JOptionPane.showMessageDialog(this, ex.getMessage());
                }            
        }
        }catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try{
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);    
        LinkedList <ImprimirMovilKmsRecorridosTotalesSegunPeriodo> lista = new LinkedList<ImprimirMovilKmsRecorridosTotalesSegunPeriodo>();
        Collection moviles = remiseria.buscarMoviles();
        Collection kmsMovil = new LinkedList();
        Movil movil = null;
        int total = 0;
        double kmsViajes = 0.0;
        double diferencia = 0.0;        
        KilometrosRecorridos kmsRec = null;
        Iterator iter = moviles.iterator();
        if(dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true)
        {
            Date desde = dp_desdeKmsRec.getDate();
            Date hasta = dp_hastaKmsRec.getDate();
            if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
            {        
                while(iter.hasNext())
                {
                    movil = (Movil) iter.next();
                    if(movil.getKilometrosRecorridos()!= null && movil.getKilometrosRecorridos().size()>0)
                    {
                        kmsMovil = movil.getKilometrosRecorridos().values();
                        Iterator iter2 = kmsMovil.iterator();
                        while(iter2.hasNext())
                        {
                                kmsRec =(KilometrosRecorridos) iter2.next();
                                Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));   
                                if ((fec.getTime() == desde.getTime()) || (fec.getTime() > desde.getTime())) 
                                {
                                    if ((fec.getTime() == hasta.getTime()) || (fec.getTime() < hasta.getTime())) 
                                    {  
                                        if(kmsRec.getTotalKilometrajeVuelta()>0)
                                        {
                                            total = total + kmsRec.getTotalKilometrajeVuelta();
                                            kmsViajes = kmsViajes + kmsRec.getKmsEnUltimaViajeRealizado();
                                            diferencia = diferencia + kmsRec.getDiferencia();  
                                        }
                                    }
                                }
                        }
                        if(total>0)
                        {
                            kmsViajes = Double.parseDouble(formateador.format(kmsViajes));
                            diferencia = Double.parseDouble(formateador.format(diferencia));
                            lista.add(new ImprimirMovilKmsRecorridosTotalesSegunPeriodo(String.valueOf(movil.getNumeroMovil()),String.valueOf(total),String.valueOf(kmsViajes),String.valueOf(diferencia)));             
                            total = 0;
                            diferencia = 0.0;
                            kmsViajes = 0.0;
                        }
                    }

                }
                List<ImprimirMovilKmsRecorridosTotalesSegunPeriodo>listaOrdena = remiseria.ordenarMayorKilometro(lista);
                try{
                     HashMap<String, Object> parametros = new HashMap();
                     parametros.clear();
                     if(unMaestro != null)
                     {
                          parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                          parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                    }
                    else if(unOperario != null)
                    {
                          parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());               
                          parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                          parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                    }  
                     //C:/Users/garba/Desktop/Reportes/
                    // JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/KilometrosRecorridos.jrxml");
                    JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/KilometrosRecorridos.jrxml");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaOrdena));
                    JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                    JDialog frame;
                    frame = new JDialog();
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(jviewer.getContentPane());
                    frame.pack();
                    frame.setSize(1100, 600);
                    frame.setModal(true);
                    frame.setLocationRelativeTo(null);                          
                    frame.show();

                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 } 
           }
           else
           {
                JOptionPane.showMessageDialog(null,"No ha ingresado un periodo entre fecha");
           }
        }
        else if(fechaDeterminado.isEnabled()==true)
        {
                while(iter.hasNext())
                {
                    movil = (Movil) iter.next();
                    if(movil.getKilometrosRecorridos()!= null && movil.getKilometrosRecorridos().size()>0)
                    {
                        kmsMovil = movil.getKilometrosRecorridos().values();
                        Iterator iter2 = kmsMovil.iterator();
                        while(iter2.hasNext())
                        {
                            kmsRec =(KilometrosRecorridos) iter2.next();
                            Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));   
                            if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                            { 
                                if(kmsRec.getTotalKilometrajeVuelta()>0)
                                {
                                    total = total + kmsRec.getTotalKilometrajeVuelta();
                                    kmsViajes = kmsViajes + kmsRec.getKmsEnUltimaViajeRealizado();
                                    diferencia = diferencia + kmsRec.getDiferencia();                                     
                                }
                            }
                        }
                        if(total >0)
                        {
                            kmsViajes = Double.parseDouble(formateador.format(kmsViajes));
                            diferencia = Double.parseDouble(formateador.format(diferencia));
                            lista.add(new ImprimirMovilKmsRecorridosTotalesSegunPeriodo(String.valueOf(movil.getNumeroMovil()),String.valueOf(total),String.valueOf(kmsViajes),String.valueOf(diferencia)));             
                            total = 0;
                            diferencia = 0.0;
                            kmsViajes = 0.0;
                        }
                    }
                }
                List<ImprimirMovilKmsRecorridosTotalesSegunPeriodo>listaOrdena = remiseria.ordenarMayorKilometro(lista);
                try{
                         HashMap<String, Object> parametros = new HashMap();
                         parametros.clear();
                         if(unMaestro != null)
                         {
                              parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                              parametros.put("logo", remiseria.getLogo());                                 
                              parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                              parametros.put("fechaHasta",null );
                        }
                        else if(unOperario != null)
                        {
                              parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                              parametros.put("logo", remiseria.getLogo());                               
                              parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                              parametros.put("fechaHasta",null );
                        }  
                         //C:/Users/garba/Desktop/Reportes/
                        //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/KilometrosRecorridos.jrxml");
                         JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/KilometrosRecorridos.jrxml");
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaOrdena));
                        JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                        JDialog frame;
                        frame = new JDialog();
                        frame.getContentPane().removeAll();
                        frame.getContentPane().add(jviewer.getContentPane());
                        frame.pack();
                        frame.setSize(1100, 600);
                        frame.setModal(true);
                        frame.setLocationRelativeTo(null);                          
                        frame.show();

                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 }         
        }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try
        {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);          
        LinkedList <ImprimirMovilKmsRecorridosTotalesSegunPeriodo> lista = new LinkedList<ImprimirMovilKmsRecorridosTotalesSegunPeriodo>();
        Collection moviles = remiseria.buscarMoviles();
        Collection kmsMovil = new LinkedList();
        Movil movil = null;
        int total = 0;
        double kmsViajes = 0.0;
        double diferencia = 0.0;  
        KilometrosRecorridos kmsRec = null;
        Iterator iter = moviles.iterator();
        if(dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true)
        {               
            Date desde = dp_desdeKmsRec.getDate();
            Date hasta = dp_hastaKmsRec.getDate();     
            if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
            {        
                while(iter.hasNext())
                {
                    movil = (Movil) iter.next();
                    if(movil.getKilometrosRecorridos()!= null && movil.getKilometrosRecorridos().size()>0)
                    {
                        kmsMovil = movil.getKilometrosRecorridos().values();
                        Iterator iter2 = kmsMovil.iterator();
                        while(iter2.hasNext())
                        {
                                kmsRec =(KilometrosRecorridos) iter2.next();
                                Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));   

                                if ((fec.getTime() == desde.getTime()) || (fec.getTime() > desde.getTime())) 
                                {
                                    if ((fec.getTime() == hasta.getTime()) || (fec.getTime() < hasta.getTime())) 
                                    {  
                                        if(kmsRec.getTotalKilometrajeVuelta()>0)
                                        {
                                            total = total + kmsRec.getTotalKilometrajeVuelta();
                                            kmsViajes = kmsViajes + kmsRec.getKmsEnUltimaViajeRealizado();
                                            diferencia = diferencia + kmsRec.getDiferencia();  
                                        }
                                    }
                                }
                         }
                        if (total >0)
                        {
                            kmsViajes = Double.parseDouble(formateador.format(kmsViajes));
                            diferencia = Double.parseDouble(formateador.format(diferencia));
                            lista.add(new ImprimirMovilKmsRecorridosTotalesSegunPeriodo(String.valueOf(movil.getNumeroMovil()),String.valueOf(total),String.valueOf(kmsViajes),String.valueOf(diferencia)));             
                            total = 0;
                            diferencia = 0.0;
                            kmsViajes = 0.0;
                        }
                    }
                }
                List<ImprimirMovilKmsRecorridosTotalesSegunPeriodo>listaOrdena = remiseria.ordenarMenorKilometro(lista);
                try{
                     HashMap<String, Object> parametros = new HashMap();
                     parametros.clear();
                     if(unMaestro != null)
                     {
                          parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                             parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                          }
                    }
                    else if(unOperario != null)
                    {
                          parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                             parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                          }
                    }  
                    // C:/Users/garba/Desktop/Reportes/
                    //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/KilometrosRecorridos.jrxml");
                    JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/KilometrosRecorridos.jrxml");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaOrdena));
                    JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                    JDialog frame;
                    frame = new JDialog();
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(jviewer.getContentPane());
                    frame.pack();
                    frame.setSize(1100, 600);
                    frame.setModal(true);
                    frame.setLocationRelativeTo(null);                          
                    frame.show();

                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 } 
           }
           else
           {
                JOptionPane.showMessageDialog(null,"No ha ingresado un periodo entre fecha");
           }
        }
        else if(fechaDeterminado.isEnabled()==true)
        {
                while(iter.hasNext())
                {
                    movil = (Movil) iter.next();
                    if(movil.getKilometrosRecorridos()!= null && movil.getKilometrosRecorridos().size()>0)
                    {
                        kmsMovil = movil.getKilometrosRecorridos().values();
                        Iterator iter2 = kmsMovil.iterator();
                        while(iter2.hasNext())
                        {
                                kmsRec =(KilometrosRecorridos) iter2.next();
                                Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));   
                                if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                                { 
                                        if(kmsRec.getTotalKilometrajeVuelta()>0)
                                        {
                                            total = total + kmsRec.getTotalKilometrajeVuelta();
                                            kmsViajes = kmsViajes + kmsRec.getKmsEnUltimaViajeRealizado();
                                            diferencia = diferencia + kmsRec.getDiferencia();  
                                        }
                                }
                         }
                        if(total>0)
                        {
                            kmsViajes = Double.parseDouble(formateador.format(kmsViajes));
                            diferencia = Double.parseDouble(formateador.format(diferencia));    
                            lista.add(new ImprimirMovilKmsRecorridosTotalesSegunPeriodo(String.valueOf(movil.getNumeroMovil()),String.valueOf(total),String.valueOf(kmsViajes),String.valueOf(diferencia)));             
                            total = 0;
                            diferencia = 0.0;
                            kmsViajes = 0.0;
                        }
                    }
                }
                List<ImprimirMovilKmsRecorridosTotalesSegunPeriodo>listaOrdena = remiseria.ordenarMenorKilometro(lista);
                try{
                     HashMap<String, Object> parametros = new HashMap();
                     parametros.clear();
                     if(unMaestro != null)
                     {
                          parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                          parametros.put("fechaHasta",null );
                    }
                    else if(unOperario != null)
                    {
                          parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                          parametros.put("fechaHasta",null );
                    }  
                     //C:/Users/garba/Desktop/Reportes/
                    //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/KilometrosRecorridos.jrxml");
                    JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/KilometrosRecorridos.jrxml");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaOrdena));
                    JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                    JDialog frame;
                    frame = new JDialog();
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(jviewer.getContentPane());
                    frame.pack();
                    frame.setSize(1100, 600);
                    frame.setModal(true);
                    frame.setLocationRelativeTo(null);                          
                    frame.show();

                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 }           
        }
        }catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void fechaDeterminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaDeterminadoActionPerformed
        // TODO add your handling code here:
        this.cargarKilometrosRecorridosSegunPeriodoOFechaFija();
    }//GEN-LAST:event_fechaDeterminadoActionPerformed

    private void checkPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPeriodoActionPerformed
        // TODO add your handling code here:
      if(checkPeriodo.isSelected()==true)
      {
        dp_desdeKmsRec.setEnabled(true);
        dp_hastaKmsRec.setEnabled(true);
        fechaDeterminado.setEnabled(false);
      }
      else if(checkPeriodo.isSelected()==false)
      {
            dp_desdeKmsRec.setEnabled(false);
            dp_hastaKmsRec.setEnabled(false);
            fechaDeterminado.setEnabled(true);
      }
    }//GEN-LAST:event_checkPeriodoActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try{
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
        LinkedList <ImprimirKmsRecorridosSegunHoraFecha> lista = new LinkedList<ImprimirKmsRecorridosSegunHoraFecha>();
        Collection moviles = remiseria.buscarMoviles();
        Collection kmsMovil = new LinkedList();
        Movil movil = null;
        int total = 0;
        double totalKmsViaje = 0.0;
        double diferencia = 0.0;
        double totalKmsViaje1 = 0.0;
        double diferencia1 = 0.0;        
        KilometrosRecorridos kmsRec = null;
        Iterator iter = moviles.iterator();
        if(dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true && checkPeriodo.isSelected()==true)
        {        
            if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
            {        
                while(iter.hasNext())
                {
                    movil = (Movil) iter.next();
                    if(movil.getKilometrosRecorridos()!= null && movil.getKilometrosRecorridos().size()>0)
                    {
                        kmsMovil = movil.getKilometrosRecorridos().values();
                        Iterator iter2 = kmsMovil.iterator();
                        while(iter2.hasNext())
                        {
                               kmsRec =(KilometrosRecorridos) iter2.next();
                               Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));   
                               Date hor = utilidades.getHour(utilidades.getHora(kmsRec.getFecha()));
                               Date horaDesde = utilidades.getHour(hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                               Date horaHasta = utilidades.getHour(hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                               if ((fec.getTime() == dp_desdeKmsRec.getDate().getTime()) || (fec.getTime() > dp_desdeKmsRec.getDate().getTime())) 
                               {
                                   if ((fec.getTime() == dp_hastaKmsRec.getDate().getTime()) || (fec.getTime() < dp_hastaKmsRec.getDate().getTime())) 
                                   {
                                        if ((hor.getTime() == horaDesde.getTime()) || (hor.getTime() >= horaDesde.getTime())) 
                                        {
                                            if ((hor.getTime() == horaHasta.getTime()) || (hor.getTime() <= horaHasta.getTime())) 
                                            {
                                                if(kmsRec.getTotalKilometrajeVuelta()>0)
                                                {
                                                    total = total + kmsRec.getTotalKilometrajeVuelta();
                                                    totalKmsViaje = (totalKmsViaje + kmsRec.getKmsEnUltimaViajeRealizado());
                                                    totalKmsViaje1 = Double.parseDouble(formateador.format(totalKmsViaje));
                                                    diferencia = diferencia + kmsRec.getDiferencia();
                                                    diferencia1 = Double.parseDouble(formateador.format(diferencia));
                                                    lista.add(new ImprimirKmsRecorridosSegunHoraFecha(String.valueOf(movil.getNumeroMovil()),movil.getUnChofer().toString(),String.valueOf(kmsRec.getKilometrosInicial()),String.valueOf(kmsRec.getKilometroFinal()),String.valueOf(kmsRec.getTotalKilometrajeVuelta()),String.valueOf(kmsRec.getKmsEnUltimaViajeRealizado()),String.valueOf(kmsRec.getDiferencia())));  
                                                }                                                
                                            }
                                        }
                                   }
                               }

                          }
                        if(total>0)
                        {
                            lista.add(new ImprimirKmsRecorridosSegunHoraFecha(String.valueOf(movil.getNumeroMovil()),"TOTAL","-------------------------------------","-------------------------------------",String.valueOf(total),String.valueOf(totalKmsViaje1),String.valueOf(diferencia1))); 
                            total = 0;
                            diferencia = 0.0;
                            totalKmsViaje = 0.0;
                            diferencia1 = 0.0;
                            totalKmsViaje1 = 0.0;
                        }
                    }
                }
                List<ImprimirKmsRecorridosSegunHoraFecha>listaOrdena = remiseria.ordenarPorMovil(lista);
                try{
                         HashMap<String, Object> parametros = new HashMap();
                         parametros.clear();
                         if(unMaestro != null)
                         {
                              parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                              parametros.put("logo", remiseria.getLogo());                               
                              if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
                              {
                                 parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                                 parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                                 parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                                 parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                              }
                              else
                              {
                                 parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                                 parametros.put("fechaHasta",null ); 
                                 parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                                 parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                              }
                         }
                         else if(unOperario != null)
                         {
                              parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                              parametros.put("logo", remiseria.getLogo());                               
                              if(dp_desdeKmsRec.getDate()!= null || dp_hastaKmsRec.getDate()!= null)
                              {
                                 parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                                 parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                                 parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                                 parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                              }
                              else
                              {
                                 parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                                 parametros.put("fechaHasta",null ); 
                                 parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                                 parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                              }                              
                         }  
                       //  C:/Users/garba/Desktop/Reportes/
                     //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/MovilesMuchosRecorridos.jrxml");
                     JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/MovilesMuchosRecorridos.jrxml");
                     JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaOrdena));
                     JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                     JDialog frame;
                     frame = new JDialog();
                     frame.getContentPane().removeAll();
                     frame.getContentPane().add(jviewer.getContentPane());
                     frame.pack();
                     frame.setSize(1100, 600);
                     frame.setModal(true);
                     frame.setLocationRelativeTo(null);                          
                     frame.show();
                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 }
            }
            else
            {
                    JOptionPane.showMessageDialog(null,"No ha ingresado un periodo entre fecha");
            }
        }
        else if(fechaDeterminado.isEnabled()==true && checkPeriodo.isSelected()==false)
        {
                while(iter.hasNext())
                {
                    movil = (Movil) iter.next();
                    if(movil.getKilometrosRecorridos()!= null && movil.getKilometrosRecorridos().size()>0)
                    {
                        kmsMovil = movil.getKilometrosRecorridos().values();
                        Iterator iter2 = kmsMovil.iterator();
                        while(iter2.hasNext())
                        {
                                kmsRec =(KilometrosRecorridos) iter2.next();
                                Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));  
                                Date hor = utilidades.getHour(utilidades.getHora(kmsRec.getFecha()));
                                Date horaDesde = utilidades.getHour(hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                                Date horaHasta = utilidades.getHour(hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());                                
                                if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                                { 
                                   if ((hor.getTime() == horaDesde.getTime()) || (hor.getTime() >= horaDesde.getTime())) 
                                    {
                                        if ((hor.getTime() == horaHasta.getTime()) || (hor.getTime() <= horaHasta.getTime())) 
                                        {
                                                if(kmsRec.getTotalKilometrajeVuelta()>0)
                                                {
                                                    total = total + kmsRec.getTotalKilometrajeVuelta();
                                                    totalKmsViaje = (totalKmsViaje + kmsRec.getKmsEnUltimaViajeRealizado());
                                                    totalKmsViaje1 = Double.parseDouble(formateador.format((totalKmsViaje)));
                                                    diferencia = diferencia + kmsRec.getDiferencia();
                                                    diferencia1 = Double.parseDouble(formateador.format((diferencia)));
                                                    lista.add(new ImprimirKmsRecorridosSegunHoraFecha(String.valueOf(movil.getNumeroMovil()),movil.getUnChofer().toString(),String.valueOf(kmsRec.getKilometrosInicial()),String.valueOf(kmsRec.getKilometroFinal()),String.valueOf(kmsRec.getTotalKilometrajeVuelta()),String.valueOf(kmsRec.getKmsEnUltimaViajeRealizado()),String.valueOf(kmsRec.getDiferencia())));  
                                                } 
                                        }
                                    }
                                }
                        }
                    }
                    if(total >0)
                    {
                            lista.add(new ImprimirKmsRecorridosSegunHoraFecha(String.valueOf(movil.getNumeroMovil()),"TOTAL","-------------------------------------","-------------------------------------",String.valueOf(total),String.valueOf(totalKmsViaje1),String.valueOf(diferencia1))); 
                            total = 0;
                            diferencia = 0.0;
                            totalKmsViaje = 0.0;
                            diferencia1 = 0.0;
                            totalKmsViaje = 0.0;
                    }
                }             
               List<ImprimirKmsRecorridosSegunHoraFecha>listaOrdena = remiseria.ordenarPorMovil(lista);
              
               try{
                     HashMap<String, Object> parametros = new HashMap();
                     parametros.clear();
                     if(unMaestro != null)
                     {
                          parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                          parametros.put("fechaHasta",null ); 
                          parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                          parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                                                 
                    }
                    else if(unOperario != null)
                    {
                          parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                          parametros.put("fechaHasta",null ); 
                          parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                          parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                          
                    }  
                    //C:/Users/garba/Desktop/Reportes/
                    //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/MovilesMuchosRecorridos.jrxml"); 
                    JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/MovilesMuchosRecorridos.jrxml");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaOrdena));
                    JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                    JDialog frame;
                    frame = new JDialog();
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(jviewer.getContentPane());
                    frame.pack();
                    frame.setSize(1100, 600);
                    frame.setModal(true);
                    frame.setLocationRelativeTo(null);                          
                    frame.show();

                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 }           
        }  
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try
        {
                try{
                     HashMap<String, Object> parametros = new HashMap();
                     parametros.clear();
                     if(unMaestro != null)
                     {
                          parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                          
                          if(dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true && checkPeriodo1.isSelected()==true)
                          {
                                 parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                                 parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                                 parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                                 parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                          }
                          else if(fechaDeterminado.isEnabled()==true && checkPeriodo1.isSelected()==true)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                             parametros.put("fechaHasta",null ); 
                             parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                             parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                          }  
                          else if (dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true && checkPeriodo1.isSelected()==false)
                          {
                                parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                                parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                                parametros.put("horaDesde", null);
                                parametros.put("horaHasta",null);
                          } 
                          else if(fechaDeterminado.isEnabled()==true && checkPeriodo1.isSelected()==false)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                             parametros.put("fechaHasta",null ); 
                             parametros.put("horaDesde", null);
                             parametros.put("horaHasta",null);          
                          }        
                    }
                    else if(unOperario != null)
                    {
                          parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                            parametros.put("nombreEmpresa",remiseria.getNombre());
                            parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                            parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                            parametros.put("logo", remiseria.getLogo());                               
                          if(dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true && checkPeriodo1.isSelected()==true)
                          {
                                 parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                                 parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                                 parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                                 parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                          }
                          else if(fechaDeterminado.isEnabled()==true && checkPeriodo1.isSelected()==true)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                             parametros.put("fechaHasta",null ); 
                             parametros.put("horaDesde", hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                             parametros.put("horaHasta",hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                          }  
                          else if (dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true && checkPeriodo1.isSelected()==false)
                          {
                                parametros.put("fechaDesde",utilidades.getFecha(dp_desdeKmsRec.getDate()) );
                                parametros.put("fechaHasta",utilidades.getFecha(dp_hastaKmsRec.getDate()) );
                                parametros.put("horaDesde", null);
                                parametros.put("horaHasta",null);
                          } 
                          else if(fechaDeterminado.isEnabled()==true && checkPeriodo1.isSelected()==false)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                             parametros.put("fechaHasta",null ); 
                             parametros.put("horaDesde", null);
                             parametros.put("horaHasta",null);          
                          }                           
                    }
                 try
                 {
                    ImprimirKmsRecorridosSegunHoraFecha p = listaMovilUnicoConRecorridos.get(listaMovilUnicoConRecorridos.size()-1); 
                    parametros.put("total1",p.getTotal() );
                    parametros.put("total2",p.getKmsViaje() ); 
                    parametros.put("total3",p.getDiferencia());
                    listaMovilUnicoConRecorridos.removeLast(); 
                     }
                    catch (Exception ex)
                    {
                      JOptionPane.showMessageDialog(null, ex);
                            
                    }
                 //C:/Users/garba/Desktop/Reportes/
                    //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/MovilesMuchosRecorridos.jrxml");
                    JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/MovilesMuchosRecorridos.jrxml");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaMovilUnicoConRecorridos));
                    listaMovilUnicoConRecorridos.clear();
                    JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                    JDialog frame;
                    frame = new JDialog();
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(jviewer.getContentPane());
                    frame.pack();
                    frame.setSize(1100, 600);
                    frame.setModal(true);
                    frame.setLocationRelativeTo(null);                          
                    frame.show();
                    SearchMovilKmsRecorridos.setText(null);
                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 } 
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No ha selecciona un N° Móvil en específico en el campo de búsqueda");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void fechaDeterminadoViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaDeterminadoViajeActionPerformed
        // TODO add your handling code here:
        cargarTablaViajes(tablaViajesRealizados,this.listarViajesUnicaFecha());
    }//GEN-LAST:event_fechaDeterminadoViajeActionPerformed

    private void checkPeriodoViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPeriodoViajeActionPerformed
        // TODO add your handling code here:
        if(checkPeriodoViaje.isSelected()== true)
        {
               fechaDeterminadoViaje.setEnabled(false);
               dp_desde.setEnabled(true);
               dp_hasta.setEnabled(true);
        }
        else
        {
               fechaDeterminadoViaje.setEnabled(true);
               dp_desde.setEnabled(false);
               dp_hasta.setEnabled(false);       
        }
        
    }//GEN-LAST:event_checkPeriodoViajeActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        try
        {
                try{
                     HashMap<String, Object> parametros = new HashMap();
                     parametros.clear();
                     if(unMaestro != null)
                     {
                          parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          parametros.put("total", String.valueOf(listaViajesReportes.size()));
                          if(dp_desde.isEnabled()==true&& dp_hasta.isEnabled())
                          {
                            if(dp_desde.getDate()!= null || dp_hasta.getDate()!= null)
                            {
                                   parametros.put("fechaDesde",utilidades.getFecha(dp_desde.getDate()) );
                                   parametros.put("fechaHasta",utilidades.getFecha(dp_hasta.getDate()) );

                            }
                          }
                          else if (fechaDeterminadoViaje.isEnabled()==true)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminadoViaje.getDate()) );
                             parametros.put("fechaHasta",null ); 
                          }                          
                    }
                    else if(unOperario != null)
                    {
                          parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          parametros.put("total", String.valueOf(listaViajesReportes.size()));
                         if(dp_desde.isEnabled()==true&& dp_hasta.isEnabled())
                          {
                            if(dp_desde.getDate()!= null || dp_hasta.getDate()!= null)
                            {
                                   parametros.put("fechaDesde",utilidades.getFecha(dp_desde.getDate()) );
                                   parametros.put("fechaHasta",utilidades.getFecha(dp_hasta.getDate()) );

                            }
                          }
                          else if (fechaDeterminadoViaje.isEnabled()==true)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminadoViaje.getDate()) );
                             parametros.put("fechaHasta",null ); 
                          }                             
                    }  
                    // C:/Users/garba/Desktop/Reportes/
                    //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/ReporteViajes.jrxml");
                     JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/ReporteViajes.jrxml");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaViajesReportes));
                    listaViajesReportes.clear();
                    JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                    JDialog frame;
                    frame = new JDialog();
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(jviewer.getContentPane());
                    frame.pack();
                    frame.setSize(1100, 600);
                    frame.setModal(true);
                    frame.setLocationRelativeTo(null);                          
                    frame.show();
                    buscarViaje.setText(null);
                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 }    
                
        
    }
           
        
         catch (Exception ex) 
         {
              JOptionPane.showMessageDialog(this, "No ha ingresado una Información de Viaje en específico en el campo de búsqueda");
         }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if(checkPeriodo1.isSelected()==true)
        {
            Date horaDesde = utilidades.getHour(hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
            Date horaHasta = utilidades.getHour(hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
            if (horaHasta.getTime() >(horaDesde.getTime() )) 
            {                                
                this.cargarKilometrosRecorridosSegunPeriodoOFechaFijaYHoras();
            }
            else
            {
                        JOptionPane.showMessageDialog(null, "La  Hora hasta debe ser mayor a la Hora desde ", null, JOptionPane.ERROR_MESSAGE);
            }   
        }
        else if(checkPeriodo1.isSelected()==false)
        {
            this.cargarKilometrosRecorridosSegunPeriodoOFechaFijaYSinHoras();
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jXSearchMovilesSinServicioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jXSearchMovilesSinServicioCaretUpdate
        // TODO add your handling code here:
        Movil aux = null;
        Collection mo = remiseria.buscarMovilesSinServicio();
        List filtro = new LinkedList();
        if (!jXSearchMovilesSinServicio.getText().isEmpty()) 
        {
            Iterator iter = mo.iterator();
            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                if (String.valueOf(aux.getNumeroMovil()).equals(jXSearchMovilesSinServicio.getText())) 
                {
                       filtro.add(aux);
                }          
                this.cargarSinServicioBuscado(filtro);
            }
        } 
        else
        {
            this.cargarSinServicio();
        }
    }//GEN-LAST:event_jXSearchMovilesSinServicioCaretUpdate

    private void jXSearchMovilesSinServicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jXSearchMovilesSinServicioKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }        
    }//GEN-LAST:event_jXSearchMovilesSinServicioKeyTyped

    private void btnGuardarChoferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarChoferActionPerformed
        // TODO add your handling code here:
        this.agregarReserva();        
    }//GEN-LAST:event_btnGuardarChoferActionPerformed

    private void btnGuardarModificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionesActionPerformed
        // TODO add your handling code here:
        if(tablaReservas.getSelectedRow()!=-1)
        {
            this.modificarReserva();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una Reserva"," ",JOptionPane.ERROR_MESSAGE);
        }           
    }//GEN-LAST:event_btnGuardarModificacionesActionPerformed

    private void txtDireccionDestinoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionDestinoKeyTyped
        int limite  = 50;
        {if (txtDireccionDestino.getText().length()== limite)
            evt.consume();
        }   
    }//GEN-LAST:event_txtDireccionDestinoKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnAgregarReserva.setEnabled(true);
        jXTaskPane4.setCollapsed(true);
        btnModificarReserva.setEnabled(true);
        btnEliminarReserva.setEnabled(true);
        btnFinalizarReserva.setEnabled(true);
        tablaReservasActivas.setEnabled(true);
        tablaReservas.setEnabled(true);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);  
        btnCancelarViaje1.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCancelarViaje1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarViaje1ActionPerformed
        // TODO add your handling code here:
        try
        {
                 int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de cancelar el Viaje seleccionado?");
                 if ( eleccion == 0)
                 {   
                        Reserva unaReserva = this.obtenerReservaActiva();
                        Viaje unViaje = remiseria.getViajes().get(unaReserva.getNumeroDelViajeGenerado());
                        remiseria.cancelarViaje(unViaje.getNumeroViaje());
                        remiseria.cancelarReserva(unaReserva);
                        remiseria.getUnaVentana().cargarReservasTransito();
                        remiseria.getUnaVentana().cargarReservas();
                        unViaje.getUnMovil().setEstado("disponible");
                        remiseria.asignarPrioridad(unViaje.getUnMovil());
                        remiseria.getUnaVentana().cargarDisponibles();
                        remiseria.getUnaVentana().cargarTablaViajes();
                 }
        }
        catch (Exception ex)
        {
            if(tablaReservasActivas.getSelectedRow()!=-1)
            {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una Reserva activa para cancelar el viaje");
            }
            else
            {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_btnCancelarViaje1ActionPerformed

    private void hsDesdeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hsDesdeItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_hsDesdeItemStateChanged

    private void hsDesdePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_hsDesdePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_hsDesdePropertyChange

    private void minDesdeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_minDesdeItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_minDesdeItemStateChanged

    private void minDesdePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_minDesdePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_minDesdePropertyChange

    private void hrHastaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hrHastaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_hrHastaItemStateChanged

    private void hrHastaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_hrHastaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_hrHastaPropertyChange

    private void minHastaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_minHastaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_minHastaItemStateChanged

    private void minHastaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_minHastaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_minHastaPropertyChange

    private void hrHastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hrHastaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hrHastaActionPerformed

    private void checkPeriodoServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPeriodoServicioActionPerformed
        // TODO add your handling code here:
       if(checkPeriodoServicio.isSelected()==true)
      {
        dp_desdeServicio.setEnabled(true);
        dp_hastaServicio.setEnabled(true);
        fechaDeterminadoServico.setEnabled(false);
      }
      else if(checkPeriodoServicio.isSelected()==false)
      {
            dp_desdeServicio.setEnabled(false);
            dp_hastaServicio.setEnabled(false);
            fechaDeterminadoServico.setEnabled(true);
      }       
    }//GEN-LAST:event_checkPeriodoServicioActionPerformed

    private void dp_desdeServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_desdeServicioActionPerformed
        // TODO add your handling code here:
        if( dp_desdeServicio.getDate().getTime()< dp_hastaServicio.getDate().getTime())
        {
            this.cargarKilometrosRecorridosEnServicioFechaFijaYHoras();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"La Fecha Hasta debe ser mayor a la Fecha desde");
        }
    }//GEN-LAST:event_dp_desdeServicioActionPerformed

    private void dp_hastaServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_hastaServicioActionPerformed
        // TODO add your handling code here:
        if( dp_desdeServicio.getDate().getTime()< dp_hastaServicio.getDate().getTime())
        {
            this.cargarKilometrosRecorridosEnServicioFechaFijaYHoras();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"La Fecha Hasta debe ser mayor a la Fecha desde");
        }        
    }//GEN-LAST:event_dp_hastaServicioActionPerformed

    private void fechaDeterminadoServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaDeterminadoServicoActionPerformed
        // TODO add your handling code here:
        this.cargarKilometrosRecorridosEnServicioFechaFijaYHoras();                
    }//GEN-LAST:event_fechaDeterminadoServicoActionPerformed

    private void SearchServicioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_SearchServicioCaretUpdate
        // TODO add your handling code here:
        try{
        if(!SearchServicio.getText().isEmpty())
        {
                listaServicio.clear();
                if(utilidades.isNumber(SearchServicio.getText()))
                {
                    int valor = Integer.valueOf(SearchServicio.getText());
                    Movil unMovil = remiseria.buscarMovil(valor);
                    if(unMovil!= null)
                    {
                        this.cargarKilometrosRecorridosEnServicioFechaFijaYHorasMovil(unMovil);
                    }
                }
        }
        else 
        {
            this.cargarKilometrosRecorridosEnServicioFechaFijaYHoras();
        }  
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error al buscar el Móvil",null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_SearchServicioCaretUpdate

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        try
        {
                LinkedList<ImprimirKilometrosRecorridos> l  =  this.cargarKilometrosRecorridosEnServicioFechaFijaYHorasImprimir(); 
                try{
                     HashMap<String, Object> parametros = new HashMap();
                     parametros.clear();
                     if(unMaestro != null)
                     {
                          parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                           
                          if(dp_desdeServicio.isEnabled()==true&& dp_hastaServicio.isEnabled())
                          {
                            if(dp_desdeServicio.getDate()!= null || dp_hastaServicio.getDate()!= null)
                            {
                                   parametros.put("fechaDesde",utilidades.getFecha(dp_desdeServicio.getDate()) );
                                   parametros.put("fechaHasta",utilidades.getFecha(dp_hastaServicio.getDate()) );

                            }
                          }
                          else if (fechaDeterminadoServico.isEnabled()==true)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminadoServico.getDate()) );
                             parametros.put("fechaHasta", null ); 
                          }                          
                    }
                    else if(unOperario != null)
                    {
                          parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("fechaActual", utilidades.getFechaActual());
                          parametros.put("nombreEmpresa",remiseria.getNombre());
                          parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                          parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                          parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                          parametros.put("logo", remiseria.getLogo());                                                
                         if(dp_desdeServicio.isEnabled()==true&& dp_hastaServicio.isEnabled())
                          {
                            if(dp_desdeServicio.getDate()!= null || dp_hastaServicio.getDate()!= null)
                            {
                                   parametros.put("fechaDesde",utilidades.getFecha(dp_desdeServicio.getDate()) );
                                   parametros.put("fechaHasta",utilidades.getFecha(dp_hastaServicio.getDate()) );

                            }
                          }
                          else if (fechaDeterminadoServico.isEnabled()==true)
                          {
                             parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminadoServico.getDate()) );
                             parametros.put("fechaHasta",null ); 
                          }                             
                    }  
                     //C:/Users/garba/Desktop/Reportes/
                     //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/ReporteKilometrosServicio.jrxml");
                    JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/ReporteKilometrosServicio.jrxml");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(l));
                    JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                    JDialog frame;
                    frame = new JDialog();
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(jviewer.getContentPane());
                    frame.pack();
                    frame.setSize(1100, 600);
                    frame.setModal(true);
                    frame.setLocationRelativeTo(null);                          
                    frame.show();

                 } 
                 catch (JRException ex) 
                 {
                      JOptionPane.showMessageDialog(this, ex.getMessage());
                 }      
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No hay Información de Turnos realizados para realizar el roporte");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
                   try{
                    try{
                         HashMap<String, Object> parametros = new HashMap();
                         parametros.clear();
                         if(unMaestro != null)
                         {
                              parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                              parametros.put("logo", remiseria.getLogo());                           
                              if(dp_desdeServicio.isEnabled()==true&& dp_hastaServicio.isEnabled())
                              {
                                if(dp_desdeServicio.getDate()!= null || dp_hastaServicio.getDate()!= null)
                                {
                                       parametros.put("fechaDesde",utilidades.getFecha(dp_desdeServicio.getDate()) );
                                       parametros.put("fechaHasta",utilidades.getFecha(dp_hastaServicio.getDate()) );

                                }
                              }
                              else if (fechaDeterminadoServico.isEnabled()==true)
                              {
                                 parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminadoServico.getDate()) );
                                 parametros.put("fechaHasta", null ); 
                              }                          
                        }
                        else if(unOperario != null)
                        {
                              parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                              parametros.put("logo", remiseria.getLogo());                                                
                             if(dp_desdeServicio.isEnabled()==true&& dp_hastaServicio.isEnabled())
                              {
                                if(dp_desdeServicio.getDate()!= null || dp_hastaServicio.getDate()!= null)
                                {
                                       parametros.put("fechaDesde",utilidades.getFecha(dp_desdeServicio.getDate()) );
                                       parametros.put("fechaHasta",utilidades.getFecha(dp_hastaServicio.getDate()) );

                                }
                              }
                              else if (fechaDeterminadoServico.isEnabled()==true)
                              {
                                 parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminadoServico.getDate()) );
                                 parametros.put("fechaHasta",null ); 
                              }                             
                        }  
                         //C:/Users/garba/Desktop/Reportes/
                        // JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/ReporteKilometrosServicio.jrxml");
                        JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/ReporteKilometrosServicio.jrxml");
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaServicio));
                        JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                        JDialog frame;
                        frame = new JDialog();
                        frame.getContentPane().removeAll();
                        frame.getContentPane().add(jviewer.getContentPane());
                        frame.pack();
                        frame.setSize(1100, 600);
                        frame.setModal(true);
                        frame.setLocationRelativeTo(null);                          
                        frame.show();
                        listaServicio.clear();

                     } 
                     catch (JRException ex) 
                     {
                          JOptionPane.showMessageDialog(this, ex.getMessage());
                     } 
                   }
                   catch (Exception ex)
                   {
                        JOptionPane.showMessageDialog(null,"No ha ingresado un N° Móvil en específico para realizar el reporte");
                   }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void checkPeriodo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPeriodo1ActionPerformed
        // TODO add your handling code here:
        if(checkPeriodo1.isSelected()==true)
        {
            hsDesde.setEnabled(true);
            minDesde.setEnabled(true); 
            hrHasta.setEnabled(true);  
            minHasta.setEnabled(true);        
        }
        else
        {
            hsDesde.setEnabled(false);
            minDesde.setEnabled(false); 
            hrHasta.setEnabled(false);  
            minHasta.setEnabled(false); 
        }
    }//GEN-LAST:event_checkPeriodo1ActionPerformed

     private boolean es_delDia(Lote unLote)
     {
        boolean ok = false;
        
        if (unLote != null) {
            Calendar hoy = Calendar.getInstance();
            Calendar fechaLote = Calendar.getInstance();
            fechaLote.setTime(unLote.getFechaLote());
            if (hoy.get(Calendar.DAY_OF_YEAR) == fechaLote.get(Calendar.DAY_OF_YEAR)) {
                ok = true;
            }
        }
        return ok;
    }    

     private int verificarCierrePendiente() 
     {
        int res= 0;
        Lote vigente = unaCaja.obtenerLote_Vigente();
        if (vigente != null) 
        {
            if (!es_delDia(vigente)) {
                if (!vigente.isCerrado() && "".equals(vigente.getHoraCierre())) {
                    if (vigente.getMovimientos() != null) 
                    {
                   //     res = JOptionPane.showConfirmDialog(this, "Aún no se ha realizado el cierre de caja de las ultimas actividades, ¿desea cerrarlo?","Advertencia", JOptionPane.YES_NO_OPTION);
                        JOptionPane.showConfirmDialog(this, "Aún no se ha realizado el cierre de caja de las ultimas actividades, deber ir a Caja y realizar el Cierre de caja","Advertencia", JOptionPane.WARNING_MESSAGE);
                        res = 1;
                    }
//                    if (res == JOptionPane.YES_OPTION) {
//                        btnCerrarCajaActionPerformed(null);
//                    }
                }
            }
        }
        return res;
    }
    
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        try
        { 
            int res = verificarCierrePendiente();
            Calendar hoy = Calendar.getInstance();
            Calendar fechaLote = Calendar.getInstance();
            if (unaCaja.obtenerLote_Vigente() != null)
            {
                fechaLote.setTime(unaCaja.obtenerLote_Vigente().getFechaLote());

                if (fechaLote.get(Calendar.DAY_OF_YEAR) == hoy.get(Calendar.DAY_OF_YEAR)) 
                {
                    JOptionPane.showMessageDialog(null, "La apertura de caja ya ha sido realizada el dia de hoy","Advertencia",JOptionPane.WARNING_MESSAGE);
                } 
                else if(res ==0)
                {             
                   JDialogAperturaDeCaja apertura = new JDialogAperturaDeCaja(parent,modal,remiseria,utilidades,unaCaja,null,null,null,null,btnIniciarTurno,jButton11,null,null,null);
                   apertura.show();
                   
                }    
            } 
            else
            {
                   JDialogAperturaDeCaja apertura = new JDialogAperturaDeCaja(parent,modal,remiseria,utilidades,unaCaja,null,null,null,null,btnIniciarTurno,jButton11,null,null,null);
                   apertura.show();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al Abrir Caja",null,JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btnClientesReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesReservaActionPerformed
        JDialogCliente ventana = new JDialogCliente(parent,modal,remiseria,utilidades,unMaestro,unOperario,cmbCliente);
        ventana.show();   
    }//GEN-LAST:event_btnClientesReservaActionPerformed

    private void btnClientesReserva1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesReserva1ActionPerformed
        try
        {
            if(cmbCliente.getSelectedItem()!= null)
            {
                Cliente unCliente = (Cliente) cmbCliente.getSelectedItem();
                JDialogEditarDomicilios ventana = new JDialogEditarDomicilios(parent, modal, remiseria, unCliente,cmbDireccionCliente,cmbCiudad,cmbBarrio);
                ventana.show();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar ingresar a la pantalla de Direcciones del Cliente", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnClientesReserva1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXSearchField SearchMovilKmsRecorridos;
    private org.jdesktop.swingx.JXSearchField SearchServicio;
    private javax.swing.JButton bnCiudad1;
    private javax.swing.JButton btnAgregarDireccion;
    private javax.swing.JButton btnAgregarReserva;
    private javax.swing.JButton btnBarrio;
    private javax.swing.JButton btnCambiarEstado;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarViaje;
    private javax.swing.JButton btnCancelarViaje1;
    private javax.swing.JButton btnCiudad;
    private javax.swing.JButton btnClientesReserva;
    private javax.swing.JButton btnClientesReserva1;
    private javax.swing.JButton btnDirreccion;
    private javax.swing.JButton btnEliminarReserva;
    private javax.swing.JButton btnEstado;
    private javax.swing.JButton btnFinalizarReserva;
    private javax.swing.JButton btnFinalizarViaje;
    private javax.swing.JButton btnGuardarChofer;
    private javax.swing.JButton btnGuardarModificaciones;
    private javax.swing.JButton btnIniciarTurno;
    private javax.swing.JButton btnIniciarViaje;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnModificarReserva;
    private javax.swing.JButton btnPais1;
    private javax.swing.JButton btnProvincia1;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir2;
    private javax.swing.JButton btnSalir3;
    private javax.swing.JButton btnSalir4;
    private org.jdesktop.swingx.JXSearchField buscarViaje;
    private javax.swing.JComboBox cbxMovil;
    private javax.swing.JComboBox cbxZona;
    private javax.swing.JCheckBox checkPeriodo;
    private javax.swing.JCheckBox checkPeriodo1;
    private javax.swing.JCheckBox checkPeriodoServicio;
    private javax.swing.JCheckBox checkPeriodoViaje;
    private javax.swing.JCheckBox checkRepetir;
    private javax.swing.JComboBox cmbBarrio;
    private javax.swing.JComboBox cmbChofer;
    private javax.swing.JComboBox cmbCiudad;
    private javax.swing.JComboBox cmbCiudadDestino;
    private javax.swing.JComboBox cmbCliente;
    private javax.swing.JComboBox cmbDireccion;
    private javax.swing.JComboBox cmbDireccionCliente;
    private javax.swing.JComboBox cmbHoraFin;
    private javax.swing.JComboBox cmbHoraInicio;
    private javax.swing.JComboBox cmbMinutoFin;
    private javax.swing.JComboBox cmbMinutoInicio;
    private javax.swing.JComboBox cmbOpcionBusqueda;
    private javax.swing.JComboBox cmbPaisDestino;
    private javax.swing.JComboBox cmbProvinciaDestino;
    private javax.swing.JComboBox cmbTipoDeCliente;
    private javax.swing.JComboBox cmbTipoMovil;
    private javax.swing.JComboBox cmbTipoViaje;
    private javax.swing.JCheckBox domingo;
    private org.jdesktop.swingx.JXDatePicker dp_desde;
    private org.jdesktop.swingx.JXDatePicker dp_desdeKmsRec;
    private org.jdesktop.swingx.JXDatePicker dp_desdeServicio;
    private org.jdesktop.swingx.JXDatePicker dp_hasta;
    private org.jdesktop.swingx.JXDatePicker dp_hastaKmsRec;
    private org.jdesktop.swingx.JXDatePicker dp_hastaServicio;
    private org.jdesktop.swingx.JXDatePicker fechaDeterminado;
    private org.jdesktop.swingx.JXDatePicker fechaDeterminadoServico;
    private org.jdesktop.swingx.JXDatePicker fechaDeterminadoViaje;
    private javax.swing.JComboBox hrHasta;
    private javax.swing.JComboBox hsDesde;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.swingx.JXSearchField jXSearchMovilesSinServicio;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane4;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer4;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer5;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer6;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JCheckBox jueves;
    private javax.swing.JCheckBox lunes;
    private javax.swing.JCheckBox martes;
    private javax.swing.JCheckBox miercoles;
    private javax.swing.JComboBox minDesde;
    private javax.swing.JComboBox minHasta;
    private javax.swing.JPanel panelDias;
    private javax.swing.JCheckBox sabado;
    private javax.swing.JTable tablaDescanso;
    private javax.swing.JTable tablaDisponibles;
    private javax.swing.JTable tablaKilometrosRecorridos;
    private javax.swing.JTable tablaKilómetrosEnServicio;
    private javax.swing.JTable tablaReservas;
    private javax.swing.JTable tablaReservasActivas;
    private javax.swing.JTable tablaSinServicio;
    private javax.swing.JTable tablaTransito;
    private javax.swing.JTable tablaViajesRealizados;
    private javax.swing.JTextField txtDireccionDestino;
    private javax.swing.JTextField txtFechaInicial;
    private javax.swing.JTextField txtKilometrosTotalesRecoridos;
    private javax.swing.JTextField txtKmsFinalAnterior;
    private javax.swing.JTextField txtKmsInicial;
    private javax.swing.JTextField txtNombreEsporadico;
    private javax.swing.JCheckBox viernes;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

     public void cargarKilometrosRecorridos()
     {
         try
         {
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
            limpiar_tabla(tablaKilometrosRecorridos);
            Collection moviles = remiseria.buscarMoviles(); 
            if(moviles != null)
            {
                DefaultTableModel modelo = new DefaultTableModel(){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                JTableHeader th; 
                th = tablaKilometrosRecorridos.getTableHeader(); 
                Font fuente = new Font("Tahoma", Font.BOLD, 12); 
                th.setForeground(java.awt.Color.BLACK);
                th.setBackground(java.awt.Color.white);
                th.setFont(fuente); 
                modelo.addColumn("Móvil N°");
                modelo.addColumn("Chofer");
                modelo.addColumn("Fecha");
                modelo.addColumn("Hora");
                modelo.addColumn("Kms inicial");
                modelo.addColumn("Kms final");
                modelo.addColumn("Kms realizado"); 
                modelo.addColumn("Kms del viaje"); 
                modelo.addColumn("Diferencia");  
                Movil aux = null;
                KilometrosRecorridos aux2 = null;
                Iterator iter = moviles.iterator();

                while (iter.hasNext())
                {
                    aux = (Movil) iter.next();
                    if(aux.getUnChofer()!= null)
                    {
                        if(aux.getKilometrosRecorridos().size()>0)
                        {
                            Collection kms = aux.getKilometrosRecorridos().values();
                            List <KilometrosRecorridos> listaAuxiliar = new LinkedList<>();
                            Iterator iter4 = kms.iterator();
                            while(iter4.hasNext())
                            {
                                listaAuxiliar.add((KilometrosRecorridos) iter4.next());
                            }
                            Collections.sort(listaAuxiliar, new Comparator<KilometrosRecorridos>() {
                                           @Override
                                           public int compare(KilometrosRecorridos p1, KilometrosRecorridos p2) {                
                                                   return new Long(p1.getFecha().getTime()).compareTo(new Long(p2.getFecha().getTime()));
                                           }
                                   });                                
                            Iterator iter2 = listaAuxiliar.iterator();
                            while(iter2.hasNext())
                            {
                                aux2 = (KilometrosRecorridos) iter2.next();
                                if(utilidades.getFecha(aux2.getFecha()).equals(utilidades.getFecha(utilidades.getFechaHoraActual())))
                                {
                                    if(aux2.getKilometroFinal()>0)
                                    {
                                        Object [] fila = new Object[9];
                                        fila[0] = aux.getNumeroMovil();
                                        fila[1] = aux.getUnChofer();
                                        fila[2] = utilidades.getFecha(aux2.getFecha());
                                        fila[3] = utilidades.getHora(aux2.getFecha());
                                        fila[4] = aux2.getKilometrosInicial();
                                        fila[5] = aux2.getKilometroFinal();
                                        fila[6] = aux2.getTotalKilometrajeVuelta();
                                        fila[7] = aux2.getKmsEnUltimaViajeRealizado();
                                        fila[8] = Double.parseDouble(formateador.format(aux2.getDiferencia()));
                                        modelo.addRow(fila);
                                    }
                                }
                            }
                        }
                    }

                }

                modelo.rowsRemoved(null);
                tablaKilometrosRecorridos.setModel(modelo);
            }
         }
         catch (Exception ex)
         {
             JOptionPane.showMessageDialog(null, "Error al cargar la tabla Kilómetros realizados");
         }
    }  
     
     public void cargarKilometrosRecorridosSegunPeriodoOFechaFija()
     {
        limpiar_tabla(tablaKilometrosRecorridos);
        Collection moviles = remiseria.buscarMoviles(); 
        if(moviles != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaKilometrosRecorridos.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            modelo.addColumn("Kms inicial");
            modelo.addColumn("Kms final");
            modelo.addColumn("Kms realizado"); 
            modelo.addColumn("Kms del viaje"); 
            modelo.addColumn("Diferencia");            
            Movil aux = null;
            KilometrosRecorridos aux2 = null;
            Iterator iter = moviles.iterator();

            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                if(aux.getUnChofer()!= null)
                {
                    if(aux.getKilometrosRecorridos().size()>0)
                    {
                        Collection kms = aux.getKilometrosRecorridos().values();
                        Iterator iter2 = kms.iterator();
                        while(iter2.hasNext())
                        {
                            aux2 = (KilometrosRecorridos) iter2.next();
                            Date fec = utilidades.getDate(utilidades.getFecha(aux2.getFecha()));
                            if(dp_hastaKmsRec.isEnabled()==true && dp_desdeKmsRec.isEnabled()==true)
                            {
                                if ((fec.getTime() == dp_desdeKmsRec.getDate().getTime()) || (fec.getTime() > dp_desdeKmsRec.getDate().getTime())) 
                                {
                                    if ((fec.getTime() == dp_hastaKmsRec.getDate().getTime()) || (fec.getTime() < dp_hastaKmsRec.getDate().getTime())) 
                                    {
                                        if(aux2.getKilometroFinal()>0)
                                        {
                                             Object [] fila = new Object[9];
                                             fila[0] = aux.getNumeroMovil();
                                             fila[1] = aux.getUnChofer();
                                             fila[2] = utilidades.getFecha(aux2.getFecha());
                                             fila[3] = utilidades.getHora(aux2.getFecha());
                                             fila[4] = aux2.getKilometrosInicial();
                                             fila[5] = aux2.getKilometroFinal();
                                             fila[6] = aux2.getTotalKilometrajeVuelta();
                                             fila[7] = aux2.getKmsEnUltimaViajeRealizado();
                                             fila[8] = aux2.getDiferencia();
                                             modelo.addRow(fila);
                                        }
                                    }
                                }
                            }
                            else if(fechaDeterminado.isEnabled()==true)
                            {
                                if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                                {
                                     Object [] fila = new Object[9];
                                     fila[0] = aux.getNumeroMovil();
                                     fila[1] = aux.getUnChofer();
                                     fila[2] = utilidades.getFecha(aux2.getFecha());
                                     fila[3] = utilidades.getHora(aux2.getFecha());
                                     fila[4] = aux2.getKilometrosInicial();
                                     fila[5] = aux2.getKilometroFinal();
                                     fila[6] = aux2.getTotalKilometrajeVuelta();
                                     fila [7] = aux2.getKmsEnUltimaViajeRealizado();
                                     fila [8] = aux2.getDiferencia();                             
                                     modelo.addRow(fila);
                                }                             
                            }
                        }
                    }
                }
               
            }
           
            modelo.rowsRemoved(null);
            tablaKilometrosRecorridos.setModel(modelo);
        }
    }    
     
     public void cargarKilometrosRecorridosSegunPeriodoOFechaFijaYHoras()
     {
         try{
        limpiar_tabla(tablaKilometrosRecorridos);
        Collection moviles = remiseria.buscarMoviles(); 
        if(moviles != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaKilometrosRecorridos.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            modelo.addColumn("Kms inicial");
            modelo.addColumn("Kms final");
            modelo.addColumn("Kms realizado"); 
            modelo.addColumn("Kms del viaje");
            modelo.addColumn("Diferencia");               
            Movil aux = null;
            KilometrosRecorridos aux2 = null;
            Iterator iter = moviles.iterator();

            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                if(aux.getUnChofer()!= null)
                {
                    if(aux.getKilometrosRecorridos().size()>0)
                    {
                        Collection kms = aux.getKilometrosRecorridos().values();
                        Iterator iter2 = kms.iterator();
                        while(iter2.hasNext())
                        {
                            aux2 = (KilometrosRecorridos) iter2.next();
                            Date fec = utilidades.getDate(utilidades.getFecha(aux2.getFecha()));
                            Date hor = utilidades.getHour(utilidades.getHora(aux2.getFecha()));
                            Date horaDesde = utilidades.getHour(hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                            Date horaHasta = utilidades.getHour(hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                            if(dp_hastaKmsRec.isEnabled()==true && dp_desdeKmsRec.isEnabled()==true)
                            {
                                if ((fec.getTime() == dp_desdeKmsRec.getDate().getTime()) || (fec.getTime() > dp_desdeKmsRec.getDate().getTime())) 
                                {
                                    if ((fec.getTime() == dp_hastaKmsRec.getDate().getTime()) || (fec.getTime() < dp_hastaKmsRec.getDate().getTime())) 
                                    {
                                         if ((hor.getTime() == horaDesde.getTime()) || (hor.getTime() >= horaDesde.getTime())) 
                                         {
                                             if ((hor.getTime() == horaHasta.getTime()) || (hor.getTime() <= horaHasta.getTime())) 
                                             {    
                                                 if(aux2.getKilometroFinal()>0)
                                                 {
                                                    Object [] fila = new Object[9];
                                                    fila[0] = aux.getNumeroMovil();
                                                    fila[1] = aux.getUnChofer();
                                                    fila[2] = utilidades.getFecha(aux2.getFecha());
                                                    fila[3] = utilidades.getHora(aux2.getFecha());
                                                    fila[4] = aux2.getKilometrosInicial();
                                                    fila[5] = aux2.getKilometroFinal();
                                                    fila[6] = aux2.getTotalKilometrajeVuelta();
                                                    fila [7] = aux2.getKmsEnUltimaViajeRealizado();
                                                    fila [8] = aux2.getDiferencia();                
                                                    modelo.addRow(fila);
                                                 }
                                             }
                                         }
                                    }
                                }
                            }
                            else if(fechaDeterminado.isEnabled()==true)
                            {
                                if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                                {
                                     if ((hor.getTime() == horaDesde.getTime()) || (hor.getTime() >= horaDesde.getTime())) 
                                     {
                                         if ((hor.getTime() == horaHasta.getTime()) || (hor.getTime() <= horaHasta.getTime())) 
                                         {              
                                             if(aux2.getKilometroFinal()>0)
                                             {
                                                Object [] fila = new Object[9];
                                                fila[0] = aux.getNumeroMovil();
                                                fila[1] = aux.getUnChofer();
                                                fila[2] = utilidades.getFecha(aux2.getFecha());
                                                fila[3] = utilidades.getHora(aux2.getFecha());
                                                fila[4] = aux2.getKilometrosInicial();
                                                fila[5] = aux2.getKilometroFinal();
                                                fila[6] = aux2.getTotalKilometrajeVuelta();
                                                fila [7] = aux2.getKmsEnUltimaViajeRealizado();
                                                fila [8] = aux2.getDiferencia();
                                                modelo.addRow(fila);
                                             }
                                         }
                                     }
                                }                             
                            }
                        }
                    }
                }
               
            }
           
            modelo.rowsRemoved(null);
            tablaKilometrosRecorridos.setModel(modelo);
        }
         }catch (Exception ex)
         {
             JOptionPane.showMessageDialog(null, ex);
         }
    }       

public void cargarKilometrosRecorridosSegunPeriodoOFechaFijaYSinHoras()
     {
         try{
        limpiar_tabla(tablaKilometrosRecorridos);
        Collection moviles = remiseria.buscarMoviles(); 
        if(moviles != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaKilometrosRecorridos.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            modelo.addColumn("Kms inicial");
            modelo.addColumn("Kms final");
            modelo.addColumn("Kms realizado"); 
            modelo.addColumn("Kms del viaje");
            modelo.addColumn("Diferencia");               
            Movil aux = null;
            KilometrosRecorridos aux2 = null;
            Iterator iter = moviles.iterator();
            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                if(aux.getUnChofer()!= null)
                {
                    if(aux.getKilometrosRecorridos().size()>0)
                    {
                        Collection kms = aux.getKilometrosRecorridos().values();
                        Iterator iter2 = kms.iterator();
                        while(iter2.hasNext())
                        {
                            aux2 = (KilometrosRecorridos) iter2.next();
                            Date fec = utilidades.getDate(utilidades.getFecha(aux2.getFecha()));
                            
                            if(dp_hastaKmsRec.isEnabled()==true && dp_desdeKmsRec.isEnabled()==true)
                            {
                                if ((fec.getTime() == dp_desdeKmsRec.getDate().getTime()) || (fec.getTime() > dp_desdeKmsRec.getDate().getTime())) 
                                {
                                    if ((fec.getTime() == dp_hastaKmsRec.getDate().getTime()) || (fec.getTime() < dp_hastaKmsRec.getDate().getTime())) 
                                    {
                           
                                        if(aux2.getKilometroFinal()>0)
                                        {
                                             Object [] fila = new Object[9];
                                             fila[0] = aux.getNumeroMovil();
                                             fila[1] = aux.getUnChofer();
                                             fila[2] = utilidades.getFecha(aux2.getFecha());
                                             fila[3] = utilidades.getHora(aux2.getFecha());
                                             fila[4] = aux2.getKilometrosInicial();
                                             fila[5] = aux2.getKilometroFinal();
                                             fila[6] = aux2.getTotalKilometrajeVuelta();
                                             fila [7] = aux2.getKmsEnUltimaViajeRealizado();
                                             fila [8] = aux2.getDiferencia();                
                                             modelo.addRow(fila);
                                        }
                                    }
                                }
                            }
                            else if(fechaDeterminado.isEnabled()==true)
                            {
                                if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                                {
                                    if(aux2.getKilometroFinal()>0)
                                    {
                                         Object [] fila = new Object[9];
                                         fila[0] = aux.getNumeroMovil();
                                         fila[1] = aux.getUnChofer();
                                         fila[2] = utilidades.getFecha(aux2.getFecha());
                                         fila[3] = utilidades.getHora(aux2.getFecha());
                                         fila[4] = aux2.getKilometrosInicial();
                                         fila[5] = aux2.getKilometroFinal();
                                         fila[6] = aux2.getTotalKilometrajeVuelta();
                                         fila [7] = aux2.getKmsEnUltimaViajeRealizado();
                                         fila [8] = aux2.getDiferencia();
                                         modelo.addRow(fila);
                                    }
                                }                             
                            }
                        }
                    }
                }
               
            }
           
            modelo.rowsRemoved(null);
            tablaKilometrosRecorridos.setModel(modelo);
        }
         }catch (Exception ex)
         {
             JOptionPane.showMessageDialog(null, ex);
         }
    }      
     
     public void  listaMovKmsRecSegunFlitro(Movil filtro)
     {      
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);         
            int valor = 0;
            double diferencia = 0.0;
            double kmsViaje = 0.0;
            limpiar_tabla(tablaKilometrosRecorridos);
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaKilometrosRecorridos.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            modelo.addColumn("Kms inicial");
            modelo.addColumn("Kms final");
            modelo.addColumn("Kms realizado");  
            modelo.addColumn("Kms del viaje"); 
            modelo.addColumn("Diferencia");            
            Collection kms = new LinkedList();
            KilometrosRecorridos kmsRec = null;
            kms = filtro.getKilometrosRecorridos().values();
            Iterator iter2 = kms.iterator();
            if(dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true && checkPeriodo1.isSelected()==true)
            {
                while(iter2.hasNext())
                {
                   kmsRec = (KilometrosRecorridos)iter2.next();
                   Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));
                   Date hor = utilidades.getHour(utilidades.getHora(kmsRec.getFecha()));
                   Date horaDesde = utilidades.getHour(hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                   Date horaHasta = utilidades.getHour(hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                   if ((fec.getTime() == dp_desdeKmsRec.getDate().getTime()) || (fec.getTime() > dp_desdeKmsRec.getDate().getTime())) 
                   {
                       if ((fec.getTime() == dp_hastaKmsRec.getDate().getTime()) || (fec.getTime() < dp_hastaKmsRec.getDate().getTime())) 
                       {
                            if ((hor.getTime() == horaDesde.getTime()) || (hor.getTime() >= horaDesde.getTime())) 
                            {
                                if ((hor.getTime() == horaHasta.getTime()) || (hor.getTime() <= horaHasta.getTime())) 
                                { 
                                   if(kmsRec.getKilometroFinal()>0)
                                   {
                                        Object [] fila = new Object[9];
                                        fila[0] = filtro.getNumeroMovil();
                                        fila[1] = filtro.getUnChofer();
                                        fila[2] = utilidades.getFecha(kmsRec.getFecha());
                                        fila[3] = utilidades.getHora(kmsRec.getFecha());
                                        fila[4] = kmsRec.getKilometrosInicial();
                                        fila[5] = kmsRec.getKilometroFinal();
                                        fila[6] = kmsRec.getTotalKilometrajeVuelta();
                                        fila [7] = kmsRec.getKmsEnUltimaViajeRealizado();
                                        fila [8] = kmsRec.getDiferencia();
                                        listaMovilUnicoConRecorridos.add(new ImprimirKmsRecorridosSegunHoraFecha(String.valueOf(filtro.getNumeroMovil()),filtro.getUnChofer().toString(),String.valueOf(kmsRec.getKilometrosInicial()),String.valueOf(kmsRec.getKilometroFinal()),String.valueOf(kmsRec.getTotalKilometrajeVuelta()),String.valueOf(kmsRec.getKmsEnUltimaViajeRealizado()),String.valueOf(kmsRec.getDiferencia())));
                                        valor = valor + kmsRec.getTotalKilometrajeVuelta();
                                        diferencia = diferencia + kmsRec.getDiferencia();
                                        kmsViaje = kmsViaje + kmsRec.getKmsEnUltimaViajeRealizado();                                   
                                        modelo.addRow(fila);
                                   }
                                }                    
                            }
                       }
                   }
                }
                
            }
            else if(fechaDeterminado.isEnabled()==true && checkPeriodo1.isSelected()==true)
            {
                while(iter2.hasNext())
                {
                   kmsRec = (KilometrosRecorridos)iter2.next();
                   Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));
                   Date hor = utilidades.getHour(utilidades.getHora(kmsRec.getFecha()));
                   Date horaDesde = utilidades.getHour(hsDesde.getSelectedItem().toString()+":"+minDesde.getSelectedItem().toString());
                   Date horaHasta = utilidades.getHour(hrHasta.getSelectedItem().toString()+":"+minHasta.getSelectedItem().toString());
                   if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                   {
                            if ((hor.getTime() == horaDesde.getTime()) || (hor.getTime() >= horaDesde.getTime())) 
                            {
                                if ((hor.getTime() == horaHasta.getTime()) || (hor.getTime() <= horaHasta.getTime())) 
                                {
                                   if(kmsRec.getKilometroFinal()>0)
                                   {
                                        Object [] fila = new Object[9];  
                                        fila[0] = filtro.getNumeroMovil();
                                        fila[1] = filtro.getUnChofer();
                                        fila[2] = utilidades.getFecha(kmsRec.getFecha());
                                        fila[3] = utilidades.getHora(kmsRec.getFecha());
                                        fila[4] = kmsRec.getKilometrosInicial();
                                        fila[5] = kmsRec.getKilometroFinal();
                                        fila[6] = kmsRec.getTotalKilometrajeVuelta();
                                        fila [7] = kmsRec.getKmsEnUltimaViajeRealizado();
                                        fila [8] = kmsRec.getDiferencia();
                                        listaMovilUnicoConRecorridos.add(new ImprimirKmsRecorridosSegunHoraFecha(String.valueOf(filtro.getNumeroMovil()),filtro.getUnChofer().toString(),String.valueOf(kmsRec.getKilometrosInicial()),String.valueOf(kmsRec.getKilometroFinal()),String.valueOf(kmsRec.getTotalKilometrajeVuelta()),String.valueOf(kmsRec.getKmsEnUltimaViajeRealizado()),String.valueOf(kmsRec.getDiferencia())));
                                        valor = valor + kmsRec.getTotalKilometrajeVuelta();
                                        diferencia = diferencia + kmsRec.getDiferencia();
                                        kmsViaje = kmsViaje + kmsRec.getKmsEnUltimaViajeRealizado();
                                        modelo.addRow(fila);
                                   }
                                }                    
                            }
                   
                   }    
                }  
               
            }
            else if (dp_desdeKmsRec.isEnabled()==true&&dp_hastaKmsRec.isEnabled()==true && checkPeriodo1.isSelected()==false)
            {
                while(iter2.hasNext())
                {
                   kmsRec = (KilometrosRecorridos)iter2.next();
                   Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));                
                   if ((fec.getTime() == dp_desdeKmsRec.getDate().getTime()) || (fec.getTime() > dp_desdeKmsRec.getDate().getTime())) 
                   {
                       if ((fec.getTime() == dp_hastaKmsRec.getDate().getTime()) || (fec.getTime() < dp_hastaKmsRec.getDate().getTime())) 
                       {
                          if(kmsRec.getKilometroFinal()>0)
                          {
                                Object [] fila = new Object[9];
                                fila[0] = filtro.getNumeroMovil();
                                fila[1] = filtro.getUnChofer();
                                fila[2] = utilidades.getFecha(kmsRec.getFecha());
                                fila[3] = utilidades.getHora(kmsRec.getFecha());
                                fila[4] = kmsRec.getKilometrosInicial();
                                fila[5] = kmsRec.getKilometroFinal();
                                fila[6] = kmsRec.getTotalKilometrajeVuelta();
                                fila [7] = kmsRec.getKmsEnUltimaViajeRealizado();
                                fila [8] = kmsRec.getDiferencia();
                                listaMovilUnicoConRecorridos.add(new ImprimirKmsRecorridosSegunHoraFecha(String.valueOf(filtro.getNumeroMovil()),filtro.getUnChofer().toString(),String.valueOf(kmsRec.getKilometrosInicial()),String.valueOf(kmsRec.getKilometroFinal()),String.valueOf(kmsRec.getTotalKilometrajeVuelta()),String.valueOf(kmsRec.getKmsEnUltimaViajeRealizado()),String.valueOf(kmsRec.getDiferencia())));
                                valor = valor + kmsRec.getTotalKilometrajeVuelta();
                                diferencia = diferencia + kmsRec.getDiferencia();
                                kmsViaje = kmsViaje + kmsRec.getKmsEnUltimaViajeRealizado();                                   
                                modelo.addRow(fila);
                          }
                       }
                   }
                }            
            }
            else if(fechaDeterminado.isEnabled()==true && checkPeriodo1.isSelected()==false)
            {
                while(iter2.hasNext())
                {
                   kmsRec = (KilometrosRecorridos)iter2.next();
                   Date fec = utilidades.getDate(utilidades.getFecha(kmsRec.getFecha()));                   
                   if ((fec.getTime() == dp_desdeKmsRec.getDate().getTime()) || (fec.getTime() > dp_desdeKmsRec.getDate().getTime())) 
                   {
                       if ((fec.getTime() == dp_hastaKmsRec.getDate().getTime()) || (fec.getTime() < dp_hastaKmsRec.getDate().getTime())) 
                       {
                           if(kmsRec.getKilometroFinal()>0)
                           {
                                Object [] fila = new Object[9];
                                fila[0] = filtro.getNumeroMovil();
                                fila[1] = filtro.getUnChofer();
                                fila[2] = utilidades.getFecha(kmsRec.getFecha());
                                fila[3] = utilidades.getHora(kmsRec.getFecha());
                                fila[4] = kmsRec.getKilometrosInicial();
                                fila[5] = kmsRec.getKilometroFinal();
                                fila[6] = kmsRec.getTotalKilometrajeVuelta();
                                fila [7] = kmsRec.getKmsEnUltimaViajeRealizado();
                                fila [8] = kmsRec.getDiferencia();
                                listaMovilUnicoConRecorridos.add(new ImprimirKmsRecorridosSegunHoraFecha(String.valueOf(filtro.getNumeroMovil()),filtro.getUnChofer().toString(),String.valueOf(kmsRec.getKilometrosInicial()),String.valueOf(kmsRec.getKilometroFinal()),String.valueOf(kmsRec.getTotalKilometrajeVuelta()),String.valueOf(kmsRec.getKmsEnUltimaViajeRealizado()),String.valueOf(kmsRec.getDiferencia())));
                                valor = valor + kmsRec.getTotalKilometrajeVuelta();
                                diferencia = diferencia + kmsRec.getDiferencia();
                                kmsViaje = kmsViaje + kmsRec.getKmsEnUltimaViajeRealizado();                                   
                                modelo.addRow(fila);
                           }
                       }
                   }
                }            
            }
            diferencia = Double.parseDouble(formateador.format((diferencia)));
            kmsViaje = Double.parseDouble(formateador.format((kmsViaje)));
            listaMovilUnicoConRecorridos.add(new ImprimirKmsRecorridosSegunHoraFecha(String.valueOf(filtro.getNumeroMovil()),"Total de Kms",".............","............",String.valueOf(valor),String.valueOf(kmsViaje),String.valueOf(diferencia)));
            modelo.rowsRemoved(null);
            tablaKilometrosRecorridos.setModel(modelo);
            txtKilometrosTotalesRecoridos.setText(String.valueOf(valor));
     }
     
     
     
     public void cargarKilometrosRecorridosEnServicioFechaFijaYHoras()
     {
        limpiar_tabla(tablaKilómetrosEnServicio);
        Collection moviles = remiseria.buscarMoviles(); 
        if(moviles != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaKilómetrosEnServicio.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Kilómetro inicial");
            modelo.addColumn("Kilómetro final");
            modelo.addColumn("Kilómetro totales");
            modelo.addColumn("Fecha - Hora inicio");
            modelo.addColumn("Fecha - Hora finalizado");      
            
            Movil aux = null;
            KilometrosEnServicio aux2 = null;
            Iterator iter = moviles.iterator();
            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                if(aux.getKilometrosEnServicios().size()>0)
                {
                        Collection kms = aux.getKilometrosEnServicios().values();
                        Iterator iter2 = kms.iterator();
                        while(iter2.hasNext())
                        {
                            aux2 = (KilometrosEnServicio) iter2.next();
                            Date fec1 = utilidades.getDate(utilidades.getFecha(aux2.getFechaHoraInicio()));
                            if(dp_desdeServicio.isEnabled()==true && dp_hastaServicio.isEnabled()==true)
                            {
                                if ((fec1.getTime() == dp_desdeServicio.getDate().getTime()) || (fec1.getTime() > dp_desdeServicio.getDate().getTime())) 
                                {
                                    if ((fec1.getTime() == dp_hastaServicio.getDate().getTime()) || (fec1.getTime() < dp_hastaServicio.getDate().getTime())) 
                                    {
                                         Object [] fila = new Object[7];
                                         fila[0] = aux.getNumeroMovil();
                                         fila[1] = aux2.getUnChofer();
                                         fila[2] = aux2.getKilometroInicialServicio();
                                         fila[3] = aux2.getKilometroFinalServicio();
                                         fila[4] = aux2.getTotalKms();
                                         if(aux2.getFechaHoraInicio()!= null)
                                         {
                                            fila[5] = utilidades.getFecha(aux2.getFechaHoraInicio())+" - "+utilidades.getHora(aux2.getFechaHoraInicio()) ;
                                         }
                                         if(aux2.getFechaHoraFinal()!= null)
                                         {
                                            fila[6] = utilidades.getFecha(aux2.getFechaHoraFinal())+" - "+utilidades.getHora(aux2.getFechaHoraFinal()) ;
                                         }
                                         modelo.addRow(fila);
                                    }
                                }
                            }
                            else if(fechaDeterminadoServico.isEnabled()==true)
                            {
                                if ((fec1.getTime() == fechaDeterminadoServico.getDate().getTime())) 
                                {                           
                                     Object [] fila = new Object[7];
                                     fila[0] = aux.getNumeroMovil();
                                     fila[1] = aux2.getUnChofer();
                                     fila[2] = aux2.getKilometroInicialServicio();
                                     fila[3] = aux2.getKilometroFinalServicio();
                                     fila[4] = aux2.getTotalKms();
                                     if(aux2.getFechaHoraInicio()!= null)
                                     {
                                        fila[5] = utilidades.getFecha(aux2.getFechaHoraInicio())+" - "+utilidades.getHora(aux2.getFechaHoraInicio()) ;
                                     }
                                     if(aux2.getFechaHoraFinal()!= null)
                                     {
                                        fila[6] = utilidades.getFecha(aux2.getFechaHoraFinal())+" - "+utilidades.getHora(aux2.getFechaHoraFinal()) ;
                                     }                                  
                                     modelo.addRow(fila);
                                }                             
                            }
                        }
                    }
                }              
            modelo.rowsRemoved(null);
            tablaKilómetrosEnServicio.setModel(modelo);
        }
     }
     
     public void cargarKilometrosRecorridosEnServicioFechaFijaYHorasMovil(Movil unMovil)
     {
        limpiar_tabla(tablaKilómetrosEnServicio);
        Collection moviles = unMovil.getKilometrosEnServicios().values();
        if(moviles != null && moviles.size()>0)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaKilómetrosEnServicio.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Kilómetro inicial");
            modelo.addColumn("Kilómetro final");
            modelo.addColumn("Kilómetro totales");
            modelo.addColumn("Fecha - Hora inicio");
            modelo.addColumn("Fecha - Hora finalizado");
            Movil aux = null;
            KilometrosEnServicio aux2 = null;
            Iterator iter = moviles.iterator();
            while(iter.hasNext())
            {
                aux2 = (KilometrosEnServicio) iter.next();
                Date fec1 = utilidades.getDate(utilidades.getFecha(aux2.getFechaHoraInicio()));
                if(dp_desdeServicio.isEnabled()==true && dp_hastaServicio.isEnabled()==true)
                {
                    if ((fec1.getTime() == dp_desdeServicio.getDate().getTime()) || (fec1.getTime() > dp_desdeServicio.getDate().getTime())) 
                    {
                        if ((fec1.getTime() == dp_hastaServicio.getDate().getTime()) || (fec1.getTime() < dp_hastaServicio.getDate().getTime())) 
                        {
                            if(aux2.getFechaHoraInicio()!= null && aux2.getFechaHoraFinal()!= null)
                            {
                                Object [] fila = new Object[7];
                                fila[0] = unMovil.getNumeroMovil();
                                fila[1] = aux2.getUnChofer();
                                fila[2] = aux2.getKilometroInicialServicio();
                                fila[3] = aux2.getKilometroFinalServicio();
                                fila[4] = aux2.getTotalKms();
                                if(aux2.getFechaHoraInicio()!= null)
                                {
                                   fila[5] = utilidades.getFecha(aux2.getFechaHoraInicio())+" - "+utilidades.getHora(aux2.getFechaHoraInicio()) ;
                                }
                                else
                                {fila[5] ="";}
                                if(aux2.getFechaHoraFinal()!= null)
                                {
                                   fila[6] = utilidades.getFecha(aux2.getFechaHoraFinal())+" - "+utilidades.getHora(aux2.getFechaHoraFinal()) ;
                                } 
                                else
                                {
                                    fila[6] ="";
                                }
                                listaServicio.add(new ImprimirKilometrosRecorridos(fila[0].toString(),fila[2].toString(),fila[3].toString(),fila[4].toString(),fila[5].toString(),fila[6].toString(),fila[1].toString()));
                                modelo.addRow(fila);
                            }
                        }
                    }
                }
                else if(fechaDeterminadoServico.isEnabled()==true)
                {
                    if ((fec1.getTime() == fechaDeterminadoServico.getDate().getTime())) 
                    {                           
                         Object [] fila = new Object[7];
                         fila[0] = unMovil.getNumeroMovil();
                         fila[1] = aux2.getUnChofer();
                         fila[2] = aux2.getKilometroInicialServicio();
                         fila[3] = aux2.getKilometroFinalServicio();
                         fila[4] = aux2.getTotalKms();
                        if(aux2.getFechaHoraInicio()!= null)
                        {
                           fila[5] = utilidades.getFecha(aux2.getFechaHoraInicio())+" - "+utilidades.getHora(aux2.getFechaHoraInicio()) ;
                        }
                        else
                        {fila[5] ="";}
                        if(aux2.getFechaHoraFinal()!= null)
                        {
                           fila[6] = utilidades.getFecha(aux2.getFechaHoraFinal())+" - "+utilidades.getHora(aux2.getFechaHoraFinal()) ;
                        } 
                        else
                        {
                            fila[6] ="";
                        }
                         listaServicio.add(new ImprimirKilometrosRecorridos(fila[0].toString(),fila[2].toString(),fila[3].toString(),fila[4].toString(),fila[5].toString(),fila[6].toString(),fila[1].toString()));
                         modelo.addRow(fila);
                    }                             
                }
            }
            modelo.rowsRemoved(null);
            tablaKilómetrosEnServicio.setModel(modelo);
            
        }
    }        
        
     
     public LinkedList<ImprimirKilometrosRecorridos> cargarKilometrosRecorridosEnServicioFechaFijaYHorasImprimir()
     {
        LinkedList<ImprimirKilometrosRecorridos> lista = new LinkedList<>();
        lista.clear();
        Collection moviles = remiseria.buscarMoviles(); 
        if(moviles != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaKilómetrosEnServicio.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Kilómetro inicial");
            modelo.addColumn("Kilómetro final");
            modelo.addColumn("Kilómetro totales");
            modelo.addColumn("Fecha - Hora inicio");
            modelo.addColumn("Fecha - Hora finalizado");      
            
            Movil aux = null;
            KilometrosEnServicio aux2 = null;
            Iterator iter = moviles.iterator();
            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                if(aux.getKilometrosEnServicios().size()>0)
                {
                        Collection kms = aux.getKilometrosEnServicios().values();
                        Iterator iter2 = kms.iterator();
                        while(iter2.hasNext())
                        {
                            aux2 = (KilometrosEnServicio) iter2.next();
                            Date fec1 = utilidades.getDate(utilidades.getFecha(aux2.getFechaHoraInicio()));
                            if(dp_desdeServicio.isEnabled()==true && dp_hastaServicio.isEnabled()==true)
                            {
                                if ((fec1.getTime() == dp_desdeServicio.getDate().getTime()) || (fec1.getTime() > dp_desdeServicio.getDate().getTime())) 
                                {
                                    if ((fec1.getTime() == dp_hastaServicio.getDate().getTime()) || (fec1.getTime() < dp_hastaServicio.getDate().getTime())) 
                                    {
                                        if(aux2.getFechaHoraInicio()!= null && aux2.getFechaHoraFinal()!= null)
                                        {
                                            Object [] fila = new Object[7];
                                            fila[0] = aux.getNumeroMovil();
                                            fila[1] = aux2.getUnChofer();
                                            fila[2] = aux2.getKilometroInicialServicio();
                                            fila[3] = aux2.getKilometroFinalServicio();
                                            fila[4] = aux2.getTotalKms();
                                            if(aux2.getFechaHoraInicio()!= null)
                                            {
                                               fila[5] = utilidades.getFecha(aux2.getFechaHoraInicio())+" - "+utilidades.getHora(aux2.getFechaHoraInicio()) ;
                                            }
                                            else
                                            {fila[5] ="";}
                                            if(aux2.getFechaHoraFinal()!= null)
                                            {
                                               fila[6] = utilidades.getFecha(aux2.getFechaHoraFinal())+" - "+utilidades.getHora(aux2.getFechaHoraFinal()) ;
                                            } 
                                            else
                                            {
                                                fila[6] ="";
                                            }
                                            lista.add(new ImprimirKilometrosRecorridos(fila[0].toString(),fila[2].toString(),fila[3].toString(),fila[4].toString(),fila[5].toString(),fila[6].toString(),fila[1].toString()));
                                            modelo.addRow(fila);
                                        }
                                    }
                                }
                            }
                            else if(fechaDeterminadoServico.isEnabled()==true)
                            {
                                if ((fec1.getTime() == fechaDeterminadoServico.getDate().getTime())) 
                                {                           
                                     Object [] fila = new Object[7];
                                     fila[0] = aux.getNumeroMovil();
                                     fila[1] = aux2.getUnChofer();
                                     fila[2] = aux2.getKilometroInicialServicio();
                                     fila[3] = aux2.getKilometroFinalServicio();
                                     fila[4] = aux2.getTotalKms();
                                    if(aux2.getFechaHoraInicio()!= null)
                                    {
                                       fila[5] = utilidades.getFecha(aux2.getFechaHoraInicio())+" - "+utilidades.getHora(aux2.getFechaHoraInicio()) ;
                                    }
                                    else
                                    {fila[5] ="";}
                                    if(aux2.getFechaHoraFinal()!= null)
                                    {
                                       fila[6] = utilidades.getFecha(aux2.getFechaHoraFinal())+" - "+utilidades.getHora(aux2.getFechaHoraFinal()) ;
                                    } 
                                    else
                                    {
                                        fila[6] ="";
                                    }                             
                                     lista.add(new ImprimirKilometrosRecorridos(fila[0].toString(),fila[2].toString(),fila[3].toString(),fila[4].toString(),fila[5].toString(),fila[6].toString(),fila[1].toString()));
                                     modelo.addRow(fila);
                                }                             
                            }
                        }
                    }
                }              
            modelo.rowsRemoved(null);
            tablaKilómetrosEnServicio.setModel(modelo);
        }
        return lista;
     }     

    
      
}
