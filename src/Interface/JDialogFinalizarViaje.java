/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Clases.*;
import static Interface.JDialogCierreDeCaja.esEntero;
import java.awt.Font;
import java.awt.Toolkit;
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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
/**
 *
 * @author garba
 */
public class JDialogFinalizarViaje extends javax.swing.JDialog {
    private Remiseria remiseria;
    private Utilidad utilidades;
    private Viaje viaje;
    private JTable tabla;
    private JTable tabla2;
    private JTable tablaViajes;
    private JXDatePicker dp_desde;
    private JXDatePicker dp_hasta;
    private Reserva unaReserva;
    private Maestro unMaestro;
    private Operario unOperario;

    /**
     * Creates new form JDialogFinalizarViaje
     */
    public JDialogFinalizarViaje(java.awt.Frame parent, boolean modal,Remiseria remiseria,Utilidad utilidades, Viaje viaje, JTable tabla, JTable tabla2, JTable tablaViajes, JXDatePicker dp_desde,JXDatePicker dp_hasta , Reserva unaReserva, Maestro unMaestro,Operario unOperario) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.viaje = viaje;
        this.utilidades = utilidades;
        this.tabla = tabla;
        this.tabla2 = tabla2;
        this.tablaViajes = tablaViajes;
        this.dp_hasta = dp_hasta;
        this.dp_desde = dp_desde;
        this.unaReserva = unaReserva;
        this.unMaestro = unMaestro;
        this.unOperario = unOperario;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        cbxTipo.addItem("Tarifa");
        cbxTipo.addItem("Costo del viaje");
        cbxTipo.addItem("Kilometraje");
        txtMovil.setText(String.valueOf(this.viaje.getUnMovil().getNumeroMovil()));
        txtPatente.setText(this.viaje.getUnMovil().getPatente());
        txtNombre.setText(this.viaje.getUnMovil().getUnChofer().getNombre());
        txtApellido.setText(this.viaje.getUnMovil().getUnChofer().getApellido());
        txtDNI.setText(String.valueOf(viaje.getUnMovil().getUnChofer().getDni()));
        txtDNI.setVisible(false);
        this.cargarTarifas();
        AutoCompleteDecorator.decorate(this.cmbTarifas);
    }

    public void cargarTarifas ()
    {
        Collection tarifas = remiseria.getTarifas().values();
        List tarif = new LinkedList<>();
        if(tarifas != null)
        {
            Tarifa taf = null;
            Tarifa ta = null;
            Iterator iter = tarifas.iterator();
            while(iter.hasNext())
            {
                taf = (Tarifa) iter.next();
                tarif.add(taf);
               // cmbTarifas.addItem(taf);
            }
             Collections.sort(tarif, new Comparator<Tarifa>() {
                @Override
                public int compare(Tarifa p1, Tarifa p2) {                
                        return new Integer(p1.getIdTarifa()).compareTo(new Integer(p2.getIdTarifa()));
                }
        }); 
            Iterator iter2  = tarif.iterator();
            while (iter2.hasNext())
            {
                
                cmbTarifas.addItem((Tarifa)iter2.next());
            }
        }
    }
    
 public void finalizarViaje()
 {
     listarViaje hilo;
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.#",simbolos);
     try
     {
        if (!cbxTipo.getSelectedItem().toString().equals("Kilometraje"))
        { 
            LinkedList<ImprimirTicket> listaViaje = new LinkedList<ImprimirTicket>();
            Tarifa unaTa = null;
            double codigo = 0.0;
            if(cmbTarifas.isEnabled()==true)
            {
                unaTa = (Tarifa)cmbTarifas.getSelectedItem();
                codigo = unaTa.getCodigoTarifa();
            }
            if(txtCodigo.isEnabled()==true)
            {
                codigo = Double.parseDouble(txtCodigo.getText());
            }
     //       Tarifa precio = remiseria.buscarTarifa(codigo);
            int tarifaSiNo;
            if (cbxTipo.getSelectedItem().toString().equals("Tarifa")){
                tarifaSiNo = 0;
            }else{
                tarifaSiNo = 1;
            }
            if(!remiseria.getTarifas().isEmpty())
            {
                Movil unMovil = viaje.getUnMovil();
                remiseria.asignarPrioridadFinalizado(unMovil);
                if(unaReserva != null)
                {
                    remiseria.terminarReserva(unaReserva);
                    remiseria.getUnaVentana().cargarReservasTransito();
                    remiseria.getUnaVentana().cargarReservas();
                    viaje.getUnMovil().setEstado("disponible");
                    remiseria.asignarPrioridad(unMovil);
                    remiseria.getUnaVentana().cargarDisponibles();               
                }
                remiseria.finalizarViaje(viaje.getNumeroViaje(), tarifaSiNo, codigo);
                ChoferPorMovil unChoferPorMovil = remiseria.buscarChoferPorMovilUltimo(viaje.getUnMovil());
                unChoferPorMovil.guardarViajesChoferPorMovil(viaje);
                this.cargarDisponibles();
                hasta();
                cargarTablaViajes(tablaViajes, listarViajes());
                remiseria.getUnaVentana().cargarKilometrosRecorridos();
                this.cargarTransito();
                
                String numeroMovil = String.valueOf(viaje.getUnMovil().getNumeroMovil());
                String chofer = viaje.getUnChofer().toString();
                String origen =null;
                if(viaje.getUnaZona()!= null)
                {
                   origen  = viaje.getUnaZona().getNombreZona().toString();
                }
                else
                {
                    origen = "Base";
                }
                String domicilio = viaje.getUnDomiclio().getUnPais().getNombrePais()+"-"+viaje.getUnDomiclio().getUnaProvincia().getNombreProvincia()+"-"+viaje.getUnDomiclio().getUnBarrio()+"-"+viaje.getUnDomiclio().getUnaDireccionViaje().toString();
                String fechahora = utilidades.getFecha(viaje.getFechaHora())+" - "+utilidades.getHora(viaje.getFechaHora()) ;
                String asigando = viaje.getAsignadoBase();                                          
                String tarifa = "$ "+String.valueOf(viaje.getTarifa());

                listaViaje.add(new ImprimirTicket(numeroMovil,chofer,origen,domicilio,fechahora,asigando,tarifa));
                hilo = new listarViaje( unMaestro, unOperario, utilidades, remiseria, viaje, listaViaje);
                hilo.start();
//                
//                    try{
//                         HashMap<String, Object> parametros = new HashMap();
//                         parametros.clear();
//                         if(unMaestro != null)
//                         {
//                              parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
//                              parametros.put("fechaActual", utilidades.getFechaActual());
//                              parametros.put("fechaActual", utilidades.getFechaActual());
//                              parametros.put("nombreEmpresa",remiseria.getNombre());
//                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
//                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
//                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
//                              parametros.put("logo", remiseria.getLogo());  
//                              parametros.put("numeroTicket", viaje.getNumeroViaje());  
//                              if(viaje.getUnCliente()!= null)
//                              {
//                                    parametros.put("cliente", viaje.getUnCliente().toString());  
//                              }
//                              else
//                              {
//                                    parametros.put("cliente",null);
//                              }                                        
//                        }
//                        else if(unOperario != null)
//                        {
//                              parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
//                              parametros.put("fechaActual", utilidades.getFechaActual());
//                              parametros.put("fechaActual", utilidades.getFechaActual());
//                              parametros.put("nombreEmpresa",remiseria.getNombre());
//                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
//                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
//                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
//                              parametros.put("logo", remiseria.getLogo());  
//                              parametros.put("numeroTicket", viaje.getNumeroViaje());  
//
//                              if(viaje.getUnCliente()!= null)
//                              {
//                                    parametros.put("cliente", viaje.getUnCliente().toString());  
//                              }
//                              else
//                              {
//                                    parametros.put("cliente",null);
//                              }                             
//                        }  
//                        // C:/Users/garba/Desktop/Reportes/
//                        //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/ticketViaje.jrxml");
//                        JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/ticketViaje.jrxml");
//                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaViaje));
//
//                        JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
//                        JDialog frame;
//                        frame = new JDialog();
//                        frame.getContentPane().removeAll();
//                        frame.getContentPane().add(jviewer.getContentPane());
//                        frame.pack();
//                        frame.setSize(1100, 700);
//                        frame.setModal(true);
//                        frame.setLocationRelativeTo(null);                          
//                        frame.show();
//
//                     } 
//                     catch (JRException ex) 
//                     {
//                          JOptionPane.showMessageDialog(this, ex.getMessage());
//                     }                     
                this.dispose();
            }
            else
            {
                  JOptionPane.showMessageDialog(null,"No hay tarifas cargadas"," ",JOptionPane.WARNING_MESSAGE);
                  this.dispose();
            }
        }
        else if (cbxTipo.getSelectedItem().toString().equals("Kilometraje"))
        {
            LinkedList<ImprimirTicket> listaViaje = new LinkedList<ImprimirTicket>();
            double codigo = Double.parseDouble(formateador.format(Double.parseDouble(txtCostoViaje.getText())));
            if(!remiseria.getTarifas().isEmpty())
            {
                Movil unMovil = viaje.getUnMovil();
                remiseria.asignarPrioridadFinalizado(unMovil);
                if(unaReserva != null)
                {
                    remiseria.terminarReserva(unaReserva);
                    remiseria.getUnaVentana().cargarReservasTransito();
                    remiseria.getUnaVentana().cargarReservas();
                    viaje.getUnMovil().setEstado("disponible");
                    remiseria.asignarPrioridad(unMovil);
                    remiseria.getUnaVentana().cargarDisponibles();               
                }
                remiseria.finalizarViaje(viaje.getNumeroViaje(), 1, codigo);
                ChoferPorMovil unChoferPorMovil = remiseria.buscarChoferPorMovilUltimo(viaje.getUnMovil());
                unChoferPorMovil.guardarViajesChoferPorMovil(viaje);
                this.cargarDisponibles();
                hasta();
                cargarTablaViajes(tablaViajes, listarViajes());
                remiseria.getUnaVentana().cargarKilometrosRecorridos();                
                this.cargarTransito();
                String numeroMovil = String.valueOf(viaje.getUnMovil().getNumeroMovil());
                String chofer = viaje.getUnChofer().toString();
                String origen =null;
                if(viaje.getUnaZona()!= null)
                {
                   origen  = viaje.getUnaZona().getNombreZona().toString();
                }
                else
                {
                    origen = "Base";
                }
                String domicilio = viaje.getUnDomiclio().getUnPais().getNombrePais()+"-"+viaje.getUnDomiclio().getUnaProvincia().getNombreProvincia()+"-"+viaje.getUnDomiclio().getUnBarrio()+"-"+viaje.getUnDomiclio().getUnaDireccionViaje().toString();
                String fechahora = utilidades.getFecha(viaje.getFechaHora())+" - "+utilidades.getHora(viaje.getFechaHora()) ;
                String asigando = viaje.getAsignadoBase();                                          
                String tarifa = "$ "+String.valueOf(viaje.getTarifa());

                listaViaje.add(new ImprimirTicket(numeroMovil,chofer,origen,domicilio,fechahora,asigando,tarifa));
                    try{
                         HashMap<String, Object> parametros = new HashMap();
                         parametros.clear();
                         if(unMaestro != null)
                         {
                              parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                              parametros.put("logo", remiseria.getLogo());  
                              parametros.put("numeroTicket", viaje.getNumeroViaje());  
                              if(viaje.getUnCliente()!= null)
                              {
                                    parametros.put("cliente", viaje.getUnCliente().toString());  
                              }
                              else
                              {
                                    parametros.put("cliente",null);
                              }                                        
                        }
                        else if(unOperario != null)
                        {
                              parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                              parametros.put("logo", remiseria.getLogo());  
                              parametros.put("numeroTicket", viaje.getNumeroViaje());  

                              if(viaje.getUnCliente()!= null)
                              {
                                    parametros.put("cliente", viaje.getUnCliente().toString());  
                              }
                              else
                              {
                                    parametros.put("cliente",null);
                              }                             
                        }  
                        // C:/Users/garba/Desktop/Reportes/
                        //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/ticketViaje.jrxml");
                        JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/ticketViaje.jrxml");
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaViaje));

                        JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                        JDialog frame;
                        frame = new JDialog();
                        frame.getContentPane().removeAll();
                        frame.getContentPane().add(jviewer.getContentPane());
                        frame.pack();
                        frame.setSize(1100, 700);
                        frame.setModal(true);
                        frame.setLocationRelativeTo(null);                          
                        frame.show();

                     } 
                     catch (JRException ex) 
                     {
                          JOptionPane.showMessageDialog(this, ex.getMessage());
                     }                     
                this.dispose();
            }
            else
            {
                  JOptionPane.showMessageDialog(null,"No hay tarifas cargadas"," ",JOptionPane.WARNING_MESSAGE);
                  this.dispose();
            }            
        }
     }
     catch (Exception ex)
     {
         JOptionPane.showMessageDialog(null,ex);
     }
    }
    
     private void hasta() {
        Date ahora = new Date();
        dp_desde.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hasta.setDate(m.getTime());
    }

    public  void limpiar_tabla(JTable tabla) {
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
 
    public void cargarTransito(){
        Collection moviles = remiseria.buscarViajesActivos(); 
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
        tabla.setModel(modelo);
        tabla.getColumn("Móvil N°").setMaxWidth(80);
        tabla.getColumn("Viaje N°").setMaxWidth(80);        
        tabla.getColumn("Chofer").setMaxWidth(200);   
        tabla.getColumn("Hora").setMaxWidth(60);  
        tabla.getColumn("Origen").setMaxWidth(80);  
        tabla.getColumn("Destino").setMaxWidth(350);    
        tabla.getColumn("Utilidad").setMaxWidth(230); 
        tabla.getColumn("Asignado").setMaxWidth(80);   
     
    }
    
 public void cargarDisponibles(){
        Collection moviles = remiseria.buscarMovilesDisponibles(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tabla2.getTableHeader(); 
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
        while (iter.hasNext()){
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
        tabla2.setModel(modelo);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMovil = new javax.swing.JTextField();
        txtDNI = new javax.swing.JTextField();
        cbxTipo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPatente = new javax.swing.JTextField();
        cmbTarifas = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtKmsRealizados = new javax.swing.JTextField();
        txtIngreseKm = new javax.swing.JTextField();
        txtCostoViaje = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAceptar.setBackground(new java.awt.Color(255, 255, 255));
        btnAceptar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Movil N°:");

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Chofer:");

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel4.setText("Opción:");

        txtMovil.setEditable(false);
        txtMovil.setBackground(new java.awt.Color(204, 204, 204));
        txtMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMovil.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtDNI.setEditable(false);
        txtDNI.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDNI.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        cbxTipo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxTipo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbxTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel5.setText("Tarifa:");

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodigo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodigo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCodigoCaretUpdate(evt);
            }
        });
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(204, 204, 204));
        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtApellido.setEditable(false);
        txtApellido.setBackground(new java.awt.Color(204, 204, 204));
        txtApellido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtApellido.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("Patente:");

        txtPatente.setEditable(false);
        txtPatente.setBackground(new java.awt.Color(204, 204, 204));
        txtPatente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPatente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        cmbTarifas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel6.setText("Costo del viaje: $");

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel8.setText("Kilómetros:");

        txtKmsRealizados.setEditable(false);
        txtKmsRealizados.setBackground(new java.awt.Color(204, 204, 204));
        txtKmsRealizados.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtKmsRealizados.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtKmsRealizados.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtKmsRealizadosCaretUpdate(evt);
            }
        });

        txtIngreseKm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtIngreseKm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIngreseKm.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIngreseKm.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtIngreseKmCaretUpdate(evt);
            }
        });
        txtIngreseKm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIngreseKmKeyTyped(evt);
            }
        });

        txtCostoViaje.setEditable(false);
        txtCostoViaje.setBackground(new java.awt.Color(204, 204, 204));
        txtCostoViaje.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCostoViaje.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCostoViaje.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCostoViajeCaretUpdate(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel7.setText("Costo del viaje: $");

        jLabel10.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel10.setText("Kilómetros:");

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtApellido))
                            .addComponent(txtPatente)
                            .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbTarifas, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jLabel8))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKmsRealizados, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIngreseKm, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCostoViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPatente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(5, 5, 5)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIngreseKm, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(0, 0, 0)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCostoViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(1, 1, 1)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtKmsRealizados, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTarifas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(53, 53, 53))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel9.setText("Finalizar viaje");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.finalizarViaje();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void cbxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoActionPerformed
        // TODO add your handling code here:
        if(cbxTipo.getSelectedItem().equals("Tarifa"))
        {
            txtCodigo.setVisible(false);
            txtCodigo.setEnabled(false);
            jLabel6.setVisible(false);
            cmbTarifas.setEnabled(true);
            cmbTarifas.setVisible(true);
            jLabel5.setVisible(true); 
            txtKmsRealizados.setEnabled(false);
            txtKmsRealizados.setVisible(false);
            jLabel8.setVisible(false);
            txtIngreseKm.setEnabled(false);
            txtCostoViaje.setEnabled(false);
            txtCostoViaje.setVisible(false);
            txtIngreseKm.setVisible(false);            
            jLabel10.setVisible(false);
            jLabel7.setVisible(false);              
        }
        else if(cbxTipo.getSelectedItem().equals("Costo del viaje"))
        {
            cmbTarifas.setEnabled(false);
            cmbTarifas.setVisible(false);
            jLabel5.setVisible(false);
            txtCodigo.setVisible(true);
            txtCodigo.setEnabled(true);
            jLabel6.setVisible(true); 
            txtKmsRealizados.setEnabled(true);
            txtKmsRealizados.setVisible(true);
            jLabel8.setVisible(true);
            txtIngreseKm.setEnabled(false);
            txtCostoViaje.setVisible(false);
            txtIngreseKm.setVisible(false);
            txtCostoViaje.setEnabled(false);            
            jLabel10.setVisible(false);
            jLabel7.setVisible(false);         
          
        }
        else if(cbxTipo.getSelectedItem().equals("Kilometraje"))
        {
            txtCodigo.setEnabled(false);
            txtCodigo.setVisible(false);
            jLabel6.setVisible(false);
            txtKmsRealizados.setEnabled(false);
            txtKmsRealizados.setVisible(false);
            jLabel8.setVisible(false);
            jLabel5.setVisible(false);
            cmbTarifas.setEnabled(false);
            cmbTarifas.setVisible(false);
            txtIngreseKm.setEnabled(true);
            txtCostoViaje.setEnabled(true);
            jLabel10.setVisible(true);
            jLabel7.setVisible(true);     
            txtCostoViaje.setVisible(true);
            txtIngreseKm.setVisible(true);            
        }
    }//GEN-LAST:event_cbxTipoActionPerformed
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
    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        // TODO add your handling code here:
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }        
        int limite  = 12;
        {if (txtCodigo.getText().length()== limite)
            evt.consume();
        }       
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtKmsRealizadosCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtKmsRealizadosCaretUpdate
        // TODO add your handling code here:

    }//GEN-LAST:event_txtKmsRealizadosCaretUpdate

    private void txtCodigoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCodigoCaretUpdate
        // TODO add your handling code here:
        try
        {
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat formateador = new DecimalFormat("###########.#",simbolos);
            if (txtCodigo.getText().isEmpty()) {
                txtKmsRealizados.setText("");
            }
            else 
            {
                if (!txtCodigo.getText().isEmpty()) 
                {
                    Double diferencia = Double.parseDouble(formateador.format((Double.parseDouble(txtCodigo.getText())-remiseria.getUnaBajadaBandera().getValorBajadaBandera())/ remiseria.getUnCierreKilometraje().getValor()));
                    txtKmsRealizados.setText(String.valueOf(diferencia));
                } 

            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error al calcular los Kilómetros del viaje",null, JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_txtCodigoCaretUpdate

    private void txtIngreseKmCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtIngreseKmCaretUpdate
        // TODO add your handling code here:
        try 
        {
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat formateador = new DecimalFormat("###########.#",simbolos);
            if (txtIngreseKm.getText().isEmpty()) {
                txtCostoViaje.setText("");
            }
            else 
            {
                if (!txtIngreseKm.getText().isEmpty()) 
                {
                    if(utilidades.isDouble(txtIngreseKm.getText()))
                    {
                        Double kms = Double.parseDouble(txtIngreseKm.getText());     
                        Double diferencia = Double.parseDouble(formateador.format((kms*remiseria.getUnCierreKilometraje().getValor())+remiseria.getUnaBajadaBandera().getValorBajadaBandera()));
                        txtCostoViaje.setText(String.valueOf(diferencia));
                    }
                } 

            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error al calcular el Costo del viaje",null, JOptionPane.ERROR_MESSAGE);
        }                
    }//GEN-LAST:event_txtIngreseKmCaretUpdate

    private void txtIngreseKmKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngreseKmKeyTyped
        // TODO add your handling code here:
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }        
        int limite  = 12;
        {if (txtIngreseKm.getText().length()== limite)
            evt.consume();
        }           
    }//GEN-LAST:event_txtIngreseKmKeyTyped

    private void txtCostoViajeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCostoViajeCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoViajeCaretUpdate

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialogFinalizarViaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogFinalizarViaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogFinalizarViaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogFinalizarViaje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogFinalizarViaje dialog = new JDialogFinalizarViaje(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });*/
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox cbxTipo;
    private javax.swing.JComboBox cmbTarifas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCostoViaje;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtIngreseKm;
    private javax.swing.JTextField txtKmsRealizados;
    private javax.swing.JTextField txtMovil;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPatente;
    // End of variables declaration//GEN-END:variables
}
