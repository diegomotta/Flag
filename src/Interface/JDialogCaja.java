/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Clases.*;
import static Interface.JDialogFinalizarViaje.esEntero;
import static Interface.JDialogMovil.limpiar_tabla;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author garba
 */
public class JDialogCaja extends javax.swing.JDialog {
private Remiseria remiseria;
private Utilidad utilidades;
private Caja unaCaja;
private java.awt.Frame parent;
private boolean modal;
private Operario unOperario;
private Maestro unMaestro;


  //<editor-fold defaultstate="collapsed" desc=" CONSTRUCTORES ">    
    public JDialogCaja(java.awt.Frame parent, boolean modal,Remiseria remiseria,Utilidad utilidades,Operario unOperario)
    {
        super(parent, modal);
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unaCaja = remiseria.getCajaPrincipal();
        this.unOperario = unOperario;
        initComponents();
        rbnEntrada.setEnabled(false);
        rbnSalida.setEnabled(false);
        cbxEntrada.setEnabled(false);
        cbxSalida.setEnabled(false);
        txtImporte.setEnabled(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);       
        JTableHeader th;
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th = tbl_caja.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);          
        this.tbl_caja.getColumn("Ingreso").setMaxWidth(85);
        this.tbl_caja.getColumn("Egreso").setMaxWidth(85);
        this.tbl_caja.getColumn("Detalle").setMaxWidth(391);
        this.tbl_caja.getColumn("Fecha").setMaxWidth(80);
        this.tbl_caja.getColumn("Hora").setMaxWidth(46);
        verificarCierrePendiente();
        cargarInformacion(unaCaja.obtenerLote_Vigente());
        Date ahora = utilidades.getFechaHoraActual();
        dp_desde3.setDate(ahora);
        hasta();
        mostrarLoteActual(); 
        tbl_caja.setDefaultRenderer(Object.class, new Mi_CellRenderer());
        tbl_caja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.hasta0();
        this.hasta1();
        this.hastaLiquidacion();
        this.hastaLiquidacionPagado();
        //this.cargarTabla1(tabla1, remiseria.buscarMoviles());
        cargarTabla1Bis(tabla1, remiseria.getChoferesPorMoviles().values());
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        th = tabla2.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);     
        tabla1.getColumn("Deuda N°").setMaxWidth(80);        
        tabla1.getColumn("Móvil N°").setMaxWidth(80);
        tabla1.getColumn("Chofer").setMaxWidth(400);
        tabla1.getColumn("Viajes").setMaxWidth(100);
        tabla2.getColumn("Viaje").setMaxWidth(340);
        tabla2.getColumn("Fecha").setMaxWidth(100);         
        tabla2.getColumn("Hora").setMaxWidth(60);  
        tabla2.getColumn("Costo").setMaxWidth(60);
        th = tablaDetalles.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        tablaDetalles.getColumn("Detalle").setMaxWidth(450);
        tablaDetalles.getColumn("Costo").setMaxWidth(60);
        tablaDetalles.getColumn("Fecha").setMaxWidth(100);
        tablaDetalles.getColumn("Hora").setMaxWidth(60);   
        blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),false);
        blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),false);        
        btnCerrarCaja.setEnabled(false);
        btnGuardar.setEnabled(false);  
        cargarTablaPersonas();
        th = tablaLiquidacionImpaga.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);         
        tablaLiquidacionImpaga.getColumn("N° Liquidación").setMaxWidth(130);
        tablaLiquidacionImpaga.getColumn("Tipo").setMaxWidth(155);
        tablaLiquidacionImpaga.getColumn("Fecha").setMaxWidth(150);
        tablaLiquidacionImpaga.getColumn("Detalle").setMaxWidth(160);
        tablaLiquidacionImpaga.getColumn("Total a Liquidar").setMaxWidth(170);
        th = TablaLiquidacionesPagadas.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);   
        TablaLiquidacionesPagadas.getColumn("N° Liquidación").setMaxWidth(100);
        TablaLiquidacionesPagadas.getColumn("Tipo").setMaxWidth(100);
        TablaLiquidacionesPagadas.getColumn("N° Documento").setMaxWidth(100);
        TablaLiquidacionesPagadas.getColumn("Nombre y Apellido").setMaxWidth(350);
        TablaLiquidacionesPagadas.getColumn("Fecha de pago").setMaxWidth(150);
        TablaLiquidacionesPagadas.getColumn("Detalle").setMaxWidth(170);
        TablaLiquidacionesPagadas.getColumn("Total Liquidado").setMaxWidth(150);        
        cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1));
        lbl_totalIN.setBackground(java.awt.Color.green);
        cmbFiltroReporte.setEnabled(false);
        checkFiltrar.setSelected(false);
        cmbFiltroReporte.setEnabled(false);
        cmbChoferMovil.setEnabled(false);
        hasta();
        hasta1();
        this.cargarTablaRendicionesPagadas2(tablaRendPagos, this.listarViajesRendidos3());    
        checFiltrarLiq.setSelected(false);
        cmbFiltrarPorChoferOperario.setEnabled(false);
        cmbOperarioChofer.setEnabled(false);
        this.cargarSalida();
        this.cargarEntrada();
        jLabel30.setVisible(false);
        txtKmsTotal.setVisible(false);
        btnGuardar1.setEnabled(false);
        btnCancelarMov.setEnabled(false);
        rbnEntrada.setEnabled(false);
        rbnSalida.setEnabled(false);
        cbxEntrada.setEnabled(false);
        cbxSalida.setEnabled(false);
        txtDetalle.setEnabled(false);
        txtImporte.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelarMov.setEnabled(false);  
        btnGuardar1.setEnabled(false);
        rbnEntrada.setSelected(false);
        cbxEntrada.setEnabled(false);  
        lbl_Efectivo.setVisible(false);
        jLabel19.setVisible(false);
        if (es_delDia(loteVigente()))
        {
            try
            {   
                Lote ultimoLote = unaCaja.obtenerLote_Vigente();
                if(ultimoLote != null )
                {
                    unaCaja.reabrirLote(ultimoLote);
                    btnCerrarCaja.setEnabled(true);
                    btnGuardar1.setEnabled(true);
                    blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),true);
                    blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),true);   
                    //JOptionPane.showMessageDialog(this, "Se ha vuelto a abrir el presente Lote");
                    cargarTablaCaja(tbl_caja, listarMovimientos());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aun no ha hecho la apertura de caja del día", null, JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null,"Error al Reabrir Caja", null, JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
             blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),false);
             blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),false);   
             btnGuardar.setEnabled(false);
             btnCancelarMov.setEnabled(false);  
            JOptionPane.showMessageDialog(null,"Debe realizar la Apertura de Caja del Día", null, JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
    
    public JDialogCaja(java.awt.Frame parent, boolean modal,Remiseria remiseria,Utilidad utilidades,Maestro unMaestro) 
    {
        super(parent, modal);
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unaCaja = remiseria.getCajaPrincipal();
        this.unMaestro = unMaestro;
        initComponents();       
        rbnEntrada.setEnabled(false);
        rbnSalida.setEnabled(false);
        cbxEntrada.setEnabled(false);
        cbxSalida.setEnabled(false);
        txtImporte.setEnabled(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        JTableHeader th;
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th = tbl_caja.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);          
        this.tbl_caja.getColumn("Ingreso").setMaxWidth(85);
        this.tbl_caja.getColumn("Egreso").setMaxWidth(85);
        this.tbl_caja.getColumn("Detalle").setMaxWidth(391);
        this.tbl_caja.getColumn("Fecha").setMaxWidth(80);
        this.tbl_caja.getColumn("Hora").setMaxWidth(46);
        verificarCierrePendiente();
        cargarInformacion(unaCaja.obtenerLote_Vigente());
        Date ahora = utilidades.getFechaHoraActual();
        dp_desde3.setDate(ahora);
        hasta();
        mostrarLoteActual(); 
        tbl_caja.setDefaultRenderer(Object.class, new Mi_CellRenderer());
        tbl_caja.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.hasta0();
        this.hasta1();
        this.hastaLiquidacion();
        this.hastaLiquidacionPagado();
        //this.cargarTabla1(tabla1, remiseria.buscarMoviles());
        cargarTabla1Bis(tabla1, remiseria.getChoferesPorMoviles().values());
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        th = tabla2.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);     
        tabla1.getColumn("Deuda N°").setMaxWidth(80);        
        tabla1.getColumn("Móvil N°").setMaxWidth(80);
        tabla1.getColumn("Chofer").setMaxWidth(400);
        tabla1.getColumn("Viajes").setMaxWidth(100);
        tabla2.getColumn("Viaje").setMaxWidth(340);
        tabla2.getColumn("Fecha").setMaxWidth(100);         
        tabla2.getColumn("Hora").setMaxWidth(60);  
        tabla2.getColumn("Costo").setMaxWidth(60);
        th = tablaDetalles.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        tablaDetalles.getColumn("Detalle").setMaxWidth(450);
        tablaDetalles.getColumn("Costo").setMaxWidth(60);
        tablaDetalles.getColumn("Fecha").setMaxWidth(100);
        tablaDetalles.getColumn("Hora").setMaxWidth(60);   
        blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),false);
        blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),false);        
        btnCerrarCaja.setEnabled(false);
        btnGuardar.setEnabled(false);  
        cargarTablaPersonas();
        th = tablaLiquidacionImpaga.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);         
        tablaLiquidacionImpaga.getColumn("N° Liquidación").setMaxWidth(130);
        tablaLiquidacionImpaga.getColumn("Tipo").setMaxWidth(155);
        tablaLiquidacionImpaga.getColumn("Fecha").setMaxWidth(150);
        tablaLiquidacionImpaga.getColumn("Detalle").setMaxWidth(160);
        tablaLiquidacionImpaga.getColumn("Total a Liquidar").setMaxWidth(170);
        th = TablaLiquidacionesPagadas.getTableHeader(); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);   
        TablaLiquidacionesPagadas.getColumn("N° Liquidación").setMaxWidth(100);
        TablaLiquidacionesPagadas.getColumn("Tipo").setMaxWidth(100);
        TablaLiquidacionesPagadas.getColumn("N° Documento").setMaxWidth(100);
        TablaLiquidacionesPagadas.getColumn("Nombre y Apellido").setMaxWidth(350);
        TablaLiquidacionesPagadas.getColumn("Fecha de pago").setMaxWidth(150);
        TablaLiquidacionesPagadas.getColumn("Detalle").setMaxWidth(170);
        TablaLiquidacionesPagadas.getColumn("Total Liquidado").setMaxWidth(150);   
        cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1));
        lbl_totalIN.setBackground(java.awt.Color.green);
        cmbFiltroReporte.setEnabled(false);
        checkFiltrar.setSelected(false);
        cmbFiltroReporte.setEnabled(false);
        cmbChoferMovil.setEnabled(false);
        hasta();
        hasta1();
        this.cargarTablaRendicionesPagadas2(tablaRendPagos, this.listarViajesRendidos3());    
        checFiltrarLiq.setSelected(false);
        cmbFiltrarPorChoferOperario.setEnabled(false);
        cmbOperarioChofer.setEnabled(false);   
        this.cargarSalida();
        this.cargarEntrada();
        jLabel30.setVisible(false);
        txtKmsTotal.setVisible(false);
        btnGuardar1.setEnabled(false);
        btnCancelarMov.setEnabled(false);
        jLabel30.setVisible(false);
        txtKmsTotal.setVisible(false);
        btnGuardar1.setEnabled(false);
        btnCancelarMov.setEnabled(false);
        rbnEntrada.setEnabled(false);
        rbnSalida.setEnabled(false);
        cbxEntrada.setEnabled(false);
        cbxSalida.setEnabled(false);
        txtDetalle.setEnabled(false);
        txtImporte.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelarMov.setEnabled(false);  
        btnGuardar1.setEnabled(false);
        rbnEntrada.setSelected(false);
        cbxEntrada.setEnabled(false);    
        lbl_Efectivo.setVisible(false);
        jLabel19.setVisible(false); 
        if (es_delDia(loteVigente()))
        {
            try
            {   
                Lote ultimoLote = unaCaja.obtenerLote_Vigente();
                if(ultimoLote != null )
                {
                    unaCaja.reabrirLote(ultimoLote);
                    btnCerrarCaja.setEnabled(true);
                    btnGuardar1.setEnabled(true);
                    blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),true);
                    blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),true);   
                    cargarTablaCaja(tbl_caja, listarMovimientos());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aun no ha hecho la Apertura de Caja del día", null, JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null,"Error al Reabrir Caja", null, JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
             blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),false);
             blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),false);   
             btnGuardar.setEnabled(false);
             btnCancelarMov.setEnabled(false);  
            JOptionPane.showMessageDialog(null,"Debe realizar la Apertura de Caja del Día", null, JOptionPane.ERROR_MESSAGE);
            
        }
    }     
   
      //</editor-fold>       
    
  //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA RENDICIONES ">    
    private void hasta0()
    {
        Date ahora = new Date();
        dp_desde.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hasta.setDate(m.getTime());
    }
    
    private void hastaLiquidacion()
    {
        Date ahora = new Date();
        dp_desdeLiq.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hastaLiq.setDate(m.getTime());
    }

    private void hastaLiquidacionPagado()
    {
        Date ahora = new Date();
        dp_desdeLiq1.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hastaLiq1.setDate(m.getTime());
    }            
            
    private void hasta1() 
    {
        Date ahora = new Date();
        dp_desde1.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hasta1.setDate(m.getTime());
    }  
    
    public static void limpiar_tabla(JTable tabla)
    {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }

    public void cargarTabla1Bis(JTable tabla, Collection lista) 
    {
        limpiar_tabla(tabla);
        Collection rendicionesPorPagar = lista; 
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
            modelo.addColumn("Deuda N°");
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Viajes");
            ChoferPorMovil aux = null;
            Iterator iter = rendicionesPorPagar.iterator();
            while (iter.hasNext())
            {                
                aux = (ChoferPorMovil) iter.next();
                aux.setCantidViajes(this.contarViajesSinRendir(aux));
                if(aux.getEstadoRendicion().equals("sin rendir") && aux.getCantidViajes() > 0)
                {
                    Object [] fila = new Object[4];
                    fila[0] = aux.getIdChoferPorMovil();
                    fila[1] = aux.getUnMovil().getNumeroMovil();
                    fila[2] = aux.getUnChofer().getNombre() +" "+aux.getUnChofer().getApellido();
                    fila[3] = aux.getCantidViajes();
                    modelo.addRow(fila);
                }
            }
            modelo.rowsRemoved(null);
            tabla.setModel(modelo);
            tabla.getColumn("Deuda N°").setMaxWidth(80);
            tabla.getColumn("Móvil N°").setMaxWidth(80);
            tabla.getColumn("Chofer").setMaxWidth(400);
            tabla.getColumn("Viajes").setMaxWidth(100);
        } 
    }    
    public  void cargarTabla2(JTable tabla, List<Viaje> lista,ChoferPorMovil unChoferPorMovil) {
        limpiar_tabla(tabla);
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);        
        Collection viajes = lista; 
        if(unChoferPorMovil.getUnMovil().isAlquilado()==false)
        {
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
                modelo.addColumn("Viaje");
                modelo.addColumn("Fecha");
                modelo.addColumn("Hora");
                modelo.addColumn("Costo");
                double total = 0;
                Viaje aux = null;
                Iterator iter = viajes.iterator();
                while (iter.hasNext())
                {
                    aux = (Viaje) iter.next();
                    Object [] fila = new Object[4];
                    fila[0] = aux.getUnDomiclio().getUnPais().getNombrePais()+"-"+aux.getUnDomiclio().getUnaProvincia().getNombreProvincia()+"-"+aux.getUnDomiclio().getUnBarrio()+"-"+aux.getUnDomiclio().getUnaDireccionViaje().toString();
                    fila[1] = utilidades.getFecha(aux.getFechaHora());
                    fila[2] = utilidades.getHora(aux.getFechaHora());
                    fila[3] = "$ "+aux.getTarifa();
                    total = total + Double.parseDouble(formateador.format(aux.getTarifa()));
                    modelo.addRow(fila);
                }
                modelo.rowsRemoved(null);
                tabla.setModel(modelo);
                tabla.getColumn("Viaje").setMaxWidth(340);
                tabla.getColumn("Fecha").setMaxWidth(100);
                tabla.getColumn("Hora").setMaxWidth(60);
                tabla.getColumn("Costo").setMaxWidth(100);
                txtTotalRendir.setText(""+Double.parseDouble(formateador.format(total)));
            }
        }
        else if(unChoferPorMovil.getUnMovil().isAlquilado()==true)
        {
               DefaultTableModel modelo = new DefaultTableModel(){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                txtTotalRendir.setText(String.valueOf(remiseria.getUnPrecioAlquiler().getPrecio()));
                JTableHeader th; 
                th = tabla.getTableHeader(); 
                Font fuente = new Font("Tahoma", Font.BOLD, 12); 
                th.setForeground(java.awt.Color.BLACK);
                th.setBackground(java.awt.Color.white);
                th.setFont(fuente); 
                modelo.addColumn("Detalle");
                modelo.addColumn("Fecha");
                modelo.addColumn("Hora");
                modelo.addColumn("Costo");
                Object [] fila = new Object[4];
                fila[0] = "Pago de Alquiler";
                fila[1] = utilidades.getFecha(utilidades.getFechaHoraActual());
                fila[2] = utilidades.getHora(utilidades.getFechaHoraActual());
                fila[3] = "$ "+remiseria.getUnPrecioAlquiler().getPrecio();
                modelo.addRow(fila);
                modelo.rowsRemoved(null);
                tabla.setModel(modelo);
                tabla.getColumn("Detalle").setMaxWidth(340);
                tabla.getColumn("Fecha").setMaxWidth(100);
                tabla.getColumn("Hora").setMaxWidth(60);
                tabla.getColumn("Costo").setMaxWidth(100);            
        }
            
    }    
   
     private List<Viaje> listarViajes(ChoferPorMovil unChoferPorMovil) {
        List<Viaje> via = new LinkedList<>();
        Collection viajesMovil = unChoferPorMovil.getViajes().values();
        Viaje aux = null;
        Iterator iter = viajesMovil.iterator();
        while(iter.hasNext()){
            aux = (Viaje)iter.next();
            if(aux.getEstadoRendicion().equals("sin rendir"))
            {   
                    via.add(aux);
            }
        }
        return via;
    }    
     
    private List<ChoferPorMovil> listarChoferPorMovilSegunFecha() {
        List<ChoferPorMovil> via = new LinkedList<>();
        Collection viajesMovil = remiseria.getChoferesPorMoviles().values();
        ChoferPorMovil aux = null;
        Iterator iter = viajesMovil.iterator();
        while(iter.hasNext()){
            aux = (ChoferPorMovil)iter.next();
            Date fechaDesde = utilidades.getDate(utilidades.getFecha(dp_desde.getDate()));
            Date fechaHasta = utilidades.getDate(utilidades.getFecha(dp_hasta.getDate()));
            if(aux.getFechaInicio()!= null)
            {
                if ((aux.getFechaInicio().getTime() == fechaDesde.getTime()) || (aux.getFechaInicio().getTime() > fechaDesde.getTime())) {
                    if ((aux.getFechaInicio().getTime() == fechaHasta.getTime()) || (aux.getFechaInicio().getTime() < fechaHasta.getTime())) {
                        if(aux.getEstadoRendicion().equals("sin rendir"))
                        {   
                                via.add(aux);
                        }
                    }
                }
            }
        }
        return via;
    }     
   
    private List<ChoferPorMovil> listarChoferPorMovilSinFecha() {
        List<ChoferPorMovil> via = new LinkedList<>();
        Collection viajesMovil = remiseria.getChoferesPorMoviles().values();
        ChoferPorMovil aux = null;
        Iterator iter = viajesMovil.iterator();
        while(iter.hasNext())
        {
            aux = (ChoferPorMovil)iter.next();
                if(aux.getEstadoRendicion().equals("sin rendir"))
                {   
                        via.add(aux);
                }
        }
        return via;
    }    
    
    private int contarViajesSinRendir(ChoferPorMovil unChoferPorMovil) {
        List<Viaje> via = new LinkedList<>();
        Collection viajesMovil = unChoferPorMovil.getViajes().values();
        Viaje aux = null;
        Iterator iter = viajesMovil.iterator();
        while(iter.hasNext())
        {
            aux = (Viaje)iter.next();
            if(aux.getEstadoRendicion().equals("sin rendir"))
            {
                via.add(aux);
            }
        }
        return via.size();
    }    
      
    public Movil obtener()
    {
        int fila = tabla1.getSelectedRow();
        int numeroMovil = (int) tabla1.getValueAt(fila, 1);
        if(numeroMovil != -1)
        {
            Movil unMovil = remiseria.buscarMovil(numeroMovil);  
            return unMovil;
        }
        return null;
    }
    
    public ChoferPorMovil obtenerRendicionPorPagar()
    {
        int fila = tabla1.getSelectedRow();
        int numero = (int) tabla1.getValueAt(fila,0);
        if(numero != -1)
        {
            ChoferPorMovil unChoferPorMovil = remiseria.getChoferesPorMoviles().get(numero);  
            return unChoferPorMovil;
        }
        return null;
    }
     
    public Rendicion obtenerRendicion()
    {
        int fila = tablaRendPagos.getSelectedRow();
        int numeroMovil = (int) tablaRendPagos.getValueAt(fila, 0);
        if(numeroMovil != -1)
        {
            Rendicion unaRendicion= remiseria.getRendiciones().get(numeroMovil);
            return unaRendicion;
        }
        return null;
    }
    
    public void agregarRendicion() throws JRException
    {
        listarRendicion hilo;
        if (es_delDia(loteVigente()))
        {
            try{
                Lote unLote = loteVigente();
                ChoferPorMovil unChoferPorMovil = this.obtenerRendicionPorPagar();
                int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de realizar la rendición del Chofer "+ unChoferPorMovil.getUnChofer().toString() + " ?");
                if ( eleccion == 0)
                {
                    if(unChoferPorMovil.getUnMovil() != null && unChoferPorMovil.getUnChofer() != null && !txtTotalRendir.getText().isEmpty() && tabla2.getRowCount()>0)
                    {
                        if(unChoferPorMovil.getUnMovil().getEstado().equals("sin servicio"))
                        {   Rendicion rendicion = null;
                            if(unChoferPorMovil.getUnMovil().isAlquilado()==false)
                            {
                                rendicion = remiseria.agregarRendicion(Double.valueOf(txtTotalRendir.getText()),dp_desde.getDate(),dp_hasta.getDate(),Integer.valueOf(txtKmsTotal.getText()),unChoferPorMovil);
                                unLote.altaMovimiento(utilidades.getDate(utilidades.getFechaActual()), utilidades.getHora(utilidades.getFechaHoraActual()), Double.valueOf(txtTotalRendir.getText()), "Ingreso","Rendición de viajes -"+ " Móvil N°"+String.valueOf(unChoferPorMovil.getUnMovil().getNumeroMovil())+" - Chofer: "+unChoferPorMovil.getUnChofer(), "Efectivo");            
                                remiseria.agregarLiquidacionChofer(utilidades.getFechaHoraActual(),rendicion,rendicion.getUnChofer());
                                this.cargarTablaRendicionesPagadas2(tablaRendPagos, this.listarViajesRendidos3());  
                                cargarTabla2(tabla2, listarViajes(unChoferPorMovil),unChoferPorMovil);
                                limpiar_tabla(tabla2);
//                                txtKmsInicial1.setText(null);
//                                txtKmsActual1.setText(null);
//                                txtKmsRealizados2.setText(null);
                                txtKmsTotal.setText(null);
                                txtTotalRendir.setText(null);
                                cargarTablaCaja(tbl_caja, listarMovimientos());
                                cargarInformacion(unLote);
                                cargarTabla1Bis(tabla1, remiseria.getChoferesPorMoviles().values());
                            }
                            else
                            {
                                rendicion = remiseria.agregarRendicion(remiseria.getUnPrecioAlquiler().getPrecio(),dp_desde.getDate(),dp_hasta.getDate(),Integer.valueOf(txtKmsTotal.getText()),unChoferPorMovil);
                                unLote.altaMovimiento(utilidades.getDate(utilidades.getFechaActual()), utilidades.getHora(utilidades.getFechaHoraActual()), remiseria.getUnPrecioAlquiler().getPrecio(), "Ingreso","Pago de Alquiler -"+ " Móvil N°"+String.valueOf(unChoferPorMovil.getUnMovil().getNumeroMovil())+" - Chofer: "+unChoferPorMovil.getUnChofer(), "Efectivo");            
                                //remiseria.agregarLiquidacionChofer(utilidades.getFechaHoraActual(),rendicion,rendicion.getUnChofer());
                                this.cargarTablaRendicionesPagadas2(tablaRendPagos, this.listarViajesRendidos3());  
                                cargarTabla2(tabla2, listarViajes(unChoferPorMovil),unChoferPorMovil);
                                limpiar_tabla(tabla2);
//                                txtKmsInicial1.setText(null);
//                                txtKmsActual1.setText(null);
//                                txtKmsRealizados2.setText(null);
                                txtKmsTotal.setText(null);
                                txtTotalRendir.setText(null);
                                cargarTablaCaja(tbl_caja, listarMovimientos());
                                cargarInformacion(unLote);
                                cargarTabla1Bis(tabla1, remiseria.getChoferesPorMoviles().values());
                            }
                           
//                      try{  
//                              List historial = this.listarViajesRendidos(rendicion);
//                              LinkedList <ImprimirViajesRendiciones> lista = new LinkedList<ImprimirViajesRendiciones>(); 
//                              if(unChoferPorMovil.getUnMovil().isAlquilado()==false)
//                              {
//                                    Viaje aux = null;
//                                    Iterator iter = historial.iterator();
//                                    while (iter.hasNext())
//                                    {
//                                          aux = (Viaje) iter.next();                                  
//                                          lista.add(new ImprimirViajesRendiciones(aux.getUnDomiclio().getUnPais().getNombrePais()+"-"+aux.getUnDomiclio().getUnaProvincia().getNombreProvincia()+"-"+aux.getUnDomiclio().getUnBarrio()+"-"+aux.getUnDomiclio().getUnaDireccionViaje().toString(),"$ "+String.valueOf(aux.getTarifa()),utilidades.getFecha(aux.getFechaHora()),utilidades.getHora(aux.getFechaHora())));
//                                    }
//                              }
//                              else
//                              {
//                                  lista.add(new ImprimirViajesRendiciones("Pago de Alquiler del Móvil","$ "+rendicion.getTotalRendicion(),utilidades.getFecha(rendicion.getFechaRendicion()),utilidades.getHora(rendicion.getFechaRendicion())));
//                              }
                             hilo = new listarRendicion( unMaestro, unOperario, utilidades,remiseria,unChoferPorMovil, rendicion );  
                             hilo.start();
//                              HashMap<String, Object> parametros = new HashMap();
//                              parametros.clear();
//                              if(unMaestro != null)
//                              {
//                                    parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
//                                    parametros.put("nombreEmpresa",remiseria.getNombre());
//                                    parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
//                                    parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
//                                    parametros.put("fechaActual", utilidades.getFechaActual());
//                                    parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
//                                    parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
//                                    parametros.put("logo", remiseria.getLogo());
//                                    parametros.put("chofer", unChoferPorMovil.getUnChofer());
//                                    parametros.put("movil", unChoferPorMovil.getUnMovil().getNumeroMovil());
//                                    parametros.put("total","$ "+String.valueOf(rendicion.getTotalRendicion()));
//                                    parametros.put("numeroRendicion",String.valueOf(rendicion.getIdRendicion()));
//                              }
//                              else if(unOperario != null)
//                              {
//                                    parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
//                                    parametros.put("nombreEmpresa",remiseria.getNombre());
//                                    parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
//                                    parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
//                                    parametros.put("fechaActual", utilidades.getFechaActual());
//                                    parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
//                                    parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
//                                    parametros.put("logo", remiseria.getLogo());
//                                    parametros.put("chofer", unChoferPorMovil.getUnChofer());
//                                    parametros.put("movil", unChoferPorMovil.getUnMovil().getNumeroMovil());
//                                    parametros.put("total","$ "+String.valueOf(rendicion.getTotalRendicion()));
//                                    parametros.put("numeroRendicion",String.valueOf(rendicion.getIdRendicion()));
//                              }  
//                              //C:/Users/garba/Desktop/Reportes/
//                              //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Rendicion.jrxml");
//                              JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Rendicion.jrxml");
//                              JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JRBeanCollectionDataSource(lista));
//                              JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
//                              JDialog frame;
//                              frame = new JDialog();
//                              frame.getContentPane().removeAll();
//                              frame.getContentPane().add(jviewer.getContentPane());
//                              frame.pack();
//                              frame.setSize(1100, 600);
//                              frame.setModal(true);
//                              frame.setLocationRelativeTo(null);                          
//                              frame.show();
//                          } catch (JRException ex) {
//                              JOptionPane.showMessageDialog(this, ex.getMessage());
//                          }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"El móvil aún se encuentra en servicio"," ",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        if(unChoferPorMovil == null)
                        {
                        JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil para realizar la Rendición"," ",JOptionPane.ERROR_MESSAGE);
                        }
                        if(tabla2.getRowCount()==0)
                        {
                           JOptionPane.showMessageDialog(null,"No existen viajes en la fecha seleccionada"," ",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                 JOptionPane.showMessageDialog(null,ex,"Error al obtener la instancia de la rendición ",JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Debe realizar una Apertura de Lote para continuar con esta operacion");
        }    
    }
    
    public  void cargarTablaRendicionesPagadas2(JTable tabla, List<Rendicion> rendiciones) {
        limpiar_tabla(tabla);
        limpiar_tabla(tablaDetalles);
        Collection rend = rendiciones; 
        if(rend != null)
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
            modelo.addColumn("Rendición N°");
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Total");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora"); 
            modelo.addColumn("Viajes");
            Rendicion aux = null;
            Iterator iter = rend.iterator();
            while (iter.hasNext())
            {
                    aux = (Rendicion) iter.next();
                    Object [] fila = new Object[7];
                    fila[0] = aux.getIdRendicion();
                    fila[1] = aux.getUnMovil().getNumeroMovil();
                    fila[2] = aux.getUnChofer();
                    fila[3] = "$ "+aux.getTotalRendicion();
                    fila[4] = utilidades.getFecha(aux.getFechaRendicion());
                    fila[5] = utilidades.getHora(aux.getFechaRendicion());
                    fila[6] = aux.getViajesPagados().size();
                    modelo.addRow(fila);
            }
            modelo.rowsRemoved(null);
            tabla.setModel(modelo);
            tabla.getColumn("Rendición N°").setMaxWidth(100);
            tabla.getColumn("Móvil N°").setMaxWidth(60);
            tabla.getColumn("Chofer").setMaxWidth(160);
            tabla.getColumn("Total").setMaxWidth(75);
            tabla.getColumn("Fecha").setMaxWidth(80);
            tabla.getColumn("Hora").setMaxWidth(45);
            tabla.getColumn("Viajes").setMaxWidth(90);         
        } 
    }
    
     public  void cargarTablaDetalle(JTable tabla, List<Viaje> lista) {
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
            modelo.addColumn("Detalle");
            modelo.addColumn("Costo");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora");
            Viaje aux = null;
            Iterator iter = viajes.iterator();
            while (iter.hasNext())
            {
                aux = (Viaje) iter.next();
                Object [] fila = new Object[4];
                fila[0] = aux.getUnDomiclio().getUnPais().getNombrePais()+"-"+aux.getUnDomiclio().getUnaProvincia().getNombreProvincia()+"-"+aux.getUnDomiclio().getUnBarrio()+"-"+aux.getUnDomiclio().getUnaDireccionViaje().toString();
                fila[1] = "$ "+aux.getTarifa();
                fila[2] = utilidades.getFecha(aux.getFechaHora());
                fila[3] = utilidades.getHora(aux.getFechaHora());                
                modelo.addRow(fila);           
            }
            modelo.rowsRemoved(null);
            tabla.setModel(modelo);
            tabla.getColumn("Detalle").setMaxWidth(450);
            tabla.getColumn("Costo").setMaxWidth(60);
            tabla.getColumn("Fecha").setMaxWidth(100);
            tabla.getColumn("Hora").setMaxWidth(60);
        } 
    }  

     
     
 private List<Viaje> listarViajesRendidos(Rendicion unaRendicion) {
        
        List<Viaje> via = new LinkedList<>();
        
        Collection viajesMovil = unaRendicion.getViajesPagados().values();
        Viaje aux = null;
        Iterator iter = viajesMovil.iterator();
        while(iter.hasNext()){
            aux = (Viaje)iter.next();
            
                    if(aux.getEstadoRendicion().equals("rendido"))
                    {   
                        via.add(aux);
                    }
        }
        return via;
    }           
 
  private List<Rendicion> listarViajesRendidos2()
  {     
        List<Rendicion> via = new LinkedList<>();
        Collection rend = remiseria.getRendiciones().values();
        Rendicion aux = null;
        Iterator iter = rend.iterator();
        while(iter.hasNext())
        {
            aux = (Rendicion)iter.next();
            
                    if ((aux.getFechaRendicion().getTime() == dp_desde1.getDate().getTime()) || (aux.getFechaRendicion().getTime() > dp_desde1.getDate().getTime()))
                    {
                        if ((aux.getFechaRendicion().getTime() == dp_hasta1.getDate().getTime()) || (aux.getFechaRendicion().getTime() < dp_hasta1.getDate().getTime()))
                        {
                                  via.add(aux);                                            
                        }
                    }
        }            
        return via;
    }    
  
    private List<Rendicion> listarViajesRendidos3()
  {     
        List<Rendicion> via = new LinkedList<>();
        Collection rend = remiseria.getRendiciones().values();
        Rendicion aux = null;
        Iterator iter = rend.iterator();
        while(iter.hasNext())
        {
            aux = (Rendicion)iter.next();
                    if ((aux.getFechaRendicion().getTime() == dp_desde.getDate().getTime()) || (aux.getFechaRendicion().getTime() > dp_desde.getDate().getTime()))
                    {
                        if ((aux.getFechaRendicion().getTime() == dp_hasta.getDate().getTime()) || (aux.getFechaRendicion().getTime() < dp_hasta.getDate().getTime()))
                        {
                                  via.add(aux);                       
                        }
                    }
        }            
        return via;
    }
    
    private List<Rendicion> listarViajesRendidosTodo()
  {     
        List<Rendicion> via = new LinkedList<>();
        Collection rend = remiseria.getRendiciones().values();
        Rendicion aux = null;
        Iterator iter = rend.iterator();
        while(iter.hasNext())
        {
            aux = (Rendicion)iter.next();
            via.add(aux);                       
        }            
        return via;
    }    
    
      //</editor-fold>    
    
  //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA lIQUIDACIONES ">    
    public Liquidacion obtenerLiquidacion()
    {
        int fila = tablaLiquidacionImpaga.getSelectedRow();
        int numero = (int) tablaLiquidacionImpaga.getValueAt(fila,0);
        if(numero != -1)
        {
            Liquidacion unaLiquidacion = remiseria.getLiquidaciones().get(numero);  
            return unaLiquidacion;
        }
        return null;
    }
    
       public Liquidacion obtenerLiquidacion2()
    {
        int fila = TablaLiquidacionesPagadas.getSelectedRow();
        int numero = (int) TablaLiquidacionesPagadas.getValueAt(fila,0);
        if(numero != -1)
        {
            Liquidacion unaLiquidacion = remiseria.getLiquidaciones().get(numero);  
            return unaLiquidacion;
        }
        return null;
    }
    
    
    public void agregarLiquidacionPagada()
    {
        listarLiquidacion hilo;
        try
        {
            Chofer unChofer = null;
            Operario unOpera = null;
            //Cargo unCargo = null;
            Object persona = null;
            if(TablaTipoPersona.getSelectedRow() != -1)
            {
                persona = this.obtenerPersona();
                if(tablaLiquidacionImpaga.getSelectedRow() != -1)
                {  
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
                        if (es_delDia(loteVigente()))
                        {
                            try
                            {
                                int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de realizar la liquidacio de "+ persona.toString() +" ?");
                                if ( eleccion == 0)
                                {
                                    Lote unLote = loteVigente();
                                    Liquidacion unaLiquidacion = this.obtenerLiquidacion();
                                    if(unaLiquidacion.getEstadoLiquidacion().equals("sin pagar"))
                                    {
                                            int fila = tablaLiquidacionImpaga.getSelectedRow();
                                            String detalle = (String) tablaLiquidacionImpaga.getValueAt(fila,3);
                                            remiseria.modificarLiquidacion(unaLiquidacion,detalle);
                                            if(unChofer!= null)
                                            {
                                                unLote.altaMovimiento(utilidades.getDate(utilidades.getFechaActual()), utilidades.getHora(utilidades.getFechaHoraActual()), unaLiquidacion.getTotalLiquidado(), "Egreso","Liquidación a chofer "+ unaLiquidacion.getUnChofer(), "Efectivo");            
                                                List liq = remiseria.buscarLiquidacionChofer(unChofer);
                                                cargarTablaLiquidacionSegunPersona(liq);
                                            }
                                            else if(unOpera != null)
                                            {
                                                unLote.altaMovimiento(utilidades.getDate(utilidades.getFechaActual()), utilidades.getHora(utilidades.getFechaHoraActual()), unaLiquidacion.getTotalLiquidado(), "Egreso","Liquidación a operario "+ unaLiquidacion.getUnOperario(), "Efectivo");            
                                                List liq = remiseria.buscarLiquidacionOperario(unOpera);
                                                cargarTablaLiquidacionSegunPersona(liq);
                                            }
                                            cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1));
                                            cargarTablaCaja(tbl_caja, listarMovimientos());
                                            cargarInformacion(unLote);

//                                         try
//                                         {  

//                                            LinkedList <ImprimirLiquidacion> lista = new LinkedList<ImprimirLiquidacion>(); 
//
//                                            String numeroLiq = String.valueOf(unaLiquidacion.getIdLiquidacion());
//                                            String total= "$ "+String.valueOf(unaLiquidacion.getTotalLiquidado());
//                                            String totalRendido = null;
//                                            String porcentaje = null;
//                                            if(unaLiquidacion.getUnOperario()!= null)
//                                            {
//                                                totalRendido = String.valueOf(unaLiquidacion.getDetalle());
//                                                porcentaje = "No aplicado";
//                                            }
//                                            else if(unaLiquidacion.getUnChofer()!= null)
//                                            {
//                                                totalRendido = "$ "+String.valueOf(unaLiquidacion.getUnaRendicion().getTotalRendicion());
//                                                porcentaje= String.valueOf(((remiseria.getUnPorcentajeChofer().getPorcentajeChofer())*100) + " %");
//                                            }
//                                            String fechaLiquidado = utilidades.getFecha(unaLiquidacion.getFechaCreacion());
//                                            lista.add(new ImprimirLiquidacion(numeroLiq,total,totalRendido,porcentaje,fechaLiquidado));
                                            hilo = new listarLiquidacion(unMaestro, unOperario,remiseria, utilidades, unChofer,unaLiquidacion, unOpera);
                                            hilo.start();
//                                            HashMap<String, Object> parametros = new HashMap();
//                                            parametros.clear();
//                                            if(unMaestro != null)
//                                            {
//                                                  parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
//                                                  parametros.put("nombreEmpresa",remiseria.getNombre());
//                                                  parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
//                                                  parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
//                                                  parametros.put("fechaActual", utilidades.getFechaActual());
//                                                  parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
//
//                                                  parametros.put("logo", remiseria.getLogo());
//                                                  if(unChofer != null)
//                                                  {
//                                                        parametros.put("apellido", unaLiquidacion.getUnChofer().getApellido());
//                                                        parametros.put("nombre", unaLiquidacion.getUnChofer().getNombre());
//                                                        parametros.put("documento",String.valueOf(unaLiquidacion.getUnChofer().getDni()));
//                                                        parametros.put("funcion","Chofer");
//                                                  }
//                                                  else if(unOpera != null)
//                                                  {
//                                                        parametros.put("apellido", unaLiquidacion.getUnOperario().getApellido());
//                                                        parametros.put("nombre", unaLiquidacion.getUnOperario().getNombre());
//                                                        parametros.put("documento",String.valueOf(unaLiquidacion.getUnOperario().getDni()));
//                                                        parametros.put("funcion","Operario");
//
//                                                  }
//                                            }
//                                            else if(unOperario != null)
//                                            {
//                                                  parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
//                                                  parametros.put("nombreEmpresa",remiseria.getNombre());
//                                                  parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
//                                                  parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
//                                                  parametros.put("fechaActual", utilidades.getFechaActual());
//                                                  parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
//                                                  parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
//                                                  parametros.put("logo", remiseria.getLogo());
//                                                  if(unChofer != null)
//                                                  {
//                                                        parametros.put("apellido", unaLiquidacion.getUnChofer().getApellido());
//                                                        parametros.put("nombre", unaLiquidacion.getUnChofer().getNombre());
//                                                        parametros.put("documento",String.valueOf(unaLiquidacion.getUnChofer().getDni()));
//                                                        parametros.put("funcion","Chofer");
//                                                  }
//                                                  else if(unOpera != null)
//                                                  {
//                                                        parametros.put("apellido", unaLiquidacion.getUnOperario().getApellido());
//                                                        parametros.put("nombre", unaLiquidacion.getUnOperario().getNombre());
//                                                        parametros.put("documento",String.valueOf(unaLiquidacion.getUnOperario().getDni()));
//                                                        parametros.put("funcion","Operario");
//
//                                                  }
//
//                                            }  
//                                            //C:/Users/garba/Desktop/Reportes/
//                                            //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Liquidacion.jrxml");
//                                            JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Liquidacion.jrxml");
//                                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(lista));
//                                            JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
//                                            JDialog frame;
//                                            frame = new JDialog();
//                                            frame.getContentPane().removeAll();
//                                            frame.getContentPane().add(jviewer.getContentPane());
//                                            frame.pack();
//                                            frame.setSize(1100, 600);
//                                            frame.setModal(true);
//                                            frame.setLocationRelativeTo(null);                          
//                                            frame.show();
//
//                                         } 
//                                         catch (JRException ex) 
//                                         {
//                                              JOptionPane.showMessageDialog(this, ex.getMessage());
//                                         }                                
                                     }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null,"No existen liquidaciones a Pagar", null, JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            } 
                            catch(Exception e)
                            {
                                 JOptionPane.showMessageDialog(null,e," ",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                        {
                             JOptionPane.showMessageDialog(this, "Debe realizar una Apertura de Lote para continuar con esta operacion");
                        }    
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "No ha selecciona el Tipo de Persona", null, JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    if(tablaLiquidacionImpaga.getRowCount()==0)
                    {
                        JOptionPane.showMessageDialog(null,"No existe liquidaciones a pagar de " + persona.toString(), null, JOptionPane.ERROR_MESSAGE);
                    }
                    else if(tablaLiquidacionImpaga.getSelectedRow()==-1)
                    {
                        JOptionPane.showMessageDialog(null,"No ha seleccionado la Liquidación a pagar de " + persona.toString(), null, JOptionPane.ERROR_MESSAGE);
                    } 

                }

    //            //De aca para abaja
    //            else if(persona instanceof Cargo)
    //            {
    //                unCargo = (Cargo) persona;
    //                if (unCargo.getTipoPago().equals("por el día")) 
    //                {                   
    //                        if(remiseria.buscarUnaLiquidacionCargo(unCargo) == false)
    //                        {
    //                            Liquidacion unaLiquidacion = remiseria.agregarLiquidacionCargo(utilidades.getFechaHoraActual(), unCargo);
    //                            if (es_delDia(loteVigente()))
    //                            {
    //                                try
    //                                {
    //                                    Lote unLote = loteVigente();
    //                                    unLote.altaMovimiento(utilidades.getDate(utilidades.getFechaActual()), utilidades.getHora(utilidades.getFechaHoraActual()), unaLiquidacion.getTotalLiquidado(), "Egreso","Liquidación por Cargo "+ unaLiquidacion.getUnCargo(), "Efectivo");            
    //                                    cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1));
    //                                    cargarTablaCaja(tbl_caja, listarMovimientos());
    //                                    cargarInformacion(unLote);
    //                                    try
    //                                    {
    //                                        
    //                                        LinkedList <ImprimirLiquidacion> lista = new LinkedList<ImprimirLiquidacion>(); 
    //                                        HashMap<String, Object> parametros = new HashMap();
    //                                        parametros.clear();
    //                                        String numeroLiq = String.valueOf(unaLiquidacion.getIdLiquidacion());
    //                                        String total= "$ "+String.valueOf(unaLiquidacion.getTotalLiquidado());
    //                                        String totalRendido = null;
    //                                        String porcentaje = null;
    //                                        if(unaLiquidacion.getUnCargo()!= null)
    //                                        {
    //                                            totalRendido = String.valueOf(unaLiquidacion.getDetalle());
    //                                            porcentaje= "No aplicado";
    //                                        }
    //
    //                                        String fechaLiquidado = utilidades.getFecha(unaLiquidacion.getFechaDePago());
    //                                        lista.add(new ImprimirLiquidacion(numeroLiq,total,totalRendido,porcentaje,fechaLiquidado));
    //
    //                                        if(unMaestro != null)
    //                                        {
    //                                            parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
    //                                            parametros.put("nombreEmpresa",remiseria.getNombre());
    //                                            parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
    //                                            parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
    //                                            parametros.put("fechaActual", utilidades.getFechaActual());
    //                                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
    //                                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
    //                                            parametros.put("logo", remiseria.getLogo());
    //                                            if(unCargo != null)
    //                                            {
    //                                                parametros.put("apellido", unaLiquidacion.getUnCargo().getApellido());
    //                                                parametros.put("nombre", unaLiquidacion.getUnCargo().getNombre());
    //                                                parametros.put("documento",String.valueOf(unaLiquidacion.getUnCargo().getDni()));
    //                                                parametros.put("funcion",unCargo.getTipoCargo());
    //                                            }
    //                                            
    //                                        }
    //                                        else if(unOperario != null)
    //                                        {
    //                                            parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
    //                                            parametros.put("nombreEmpresa",remiseria.getNombre());
    //                                            parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
    //                                            parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
    //                                            parametros.put("fechaActual", utilidades.getFechaActual());
    //                                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
    //                                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
    //                                            parametros.put("logo", remiseria.getLogo());
    //                                            if(unCargo != null)
    //                                            {
    //                                                parametros.put("apellido", unaLiquidacion.getUnCargo().getApellido());
    //                                                parametros.put("nombre", unaLiquidacion.getUnCargo().getNombre());
    //                                                parametros.put("documento",String.valueOf(unaLiquidacion.getUnCargo().getDni()));
    //                                                parametros.put("funcion",unCargo.getTipoCargo());
    //                                            }
    //                                            
    //                                        }
    //                                        
    //                                        JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Liquidacion.jrxml");
    //                                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(lista));
    //                                        JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
    //                                        JDialog frame;
    //                                        frame = new JDialog();
    //                                        frame.getContentPane().removeAll();
    //                                        frame.getContentPane().add(jviewer.getContentPane());
    //                                        frame.pack();
    //                                        frame.setSize(900, 700);
    //                                        frame.setModal(true);
    //                                        frame.setLocationRelativeTo(null);                          
    //                                        frame.show();
    //                                        
    //                                    }
    //                                    catch (JRException ex)
    //                                    {
    //                                        JOptionPane.showMessageDialog(this, ex.getMessage());
    //                                    }
    //                                }
    //                                catch(Exception e)
    //                                {
    //                                    JOptionPane.showMessageDialog(null,e," ",JOptionPane.ERROR_MESSAGE);
    //                                }
    //                            }
    //                            else
    //                            {
    //                                JOptionPane.showMessageDialog(this, "Debe realizar una Apertura de Lote para continuar con esta operacion", null, JOptionPane.INFORMATION_MESSAGE);
    //                            }                                 
    //                        }
    //                        else
    //                        {
    //                            JOptionPane.showMessageDialog(null, "Ya se ha realizado la Liquidación del Día", null, JOptionPane.WARNING_MESSAGE);
    //                        }
    //                }
    //                else if(unCargo.getTipoPago().equals("por el mes"))
    //                {
    //                        if(utilidades.getFecha(unCargo.getFechaPago()).equals(utilidades.getFecha(utilidades.getFechaHoraActual())))
    //                        {
    //                            if(remiseria.buscarUnaLiquidacionCargo(unCargo) == false)
    //                            {
    //                                Liquidacion unaLiquidacion = remiseria.agregarLiquidacionCargo(utilidades.getFechaHoraActual(), unCargo);
    //                                if (es_delDia(loteVigente()))
    //                                {
    //                                    try
    //                                    {
    //                                        Lote unLote = loteVigente();
    //                                        unLote.altaMovimiento(utilidades.getDate(utilidades.getFechaActual()), utilidades.getHora(utilidades.getFechaHoraActual()), unaLiquidacion.getTotalLiquidado(), "Egreso","Liquidación por Cargo "+ unaLiquidacion.getUnCargo(), "Efectivo");            
    //                                        cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1));
    //                                        cargarTablaCaja(tbl_caja, listarMovimientos());
    //                                        cargarInformacion(unLote);
    //                                        try
    //                                        {
    //
    //                                            LinkedList <ImprimirLiquidacion> lista = new LinkedList<ImprimirLiquidacion>(); 
    //                                            HashMap<String, Object> parametros = new HashMap();
    //                                            parametros.clear();
    //                                            String numeroLiq = String.valueOf(unaLiquidacion.getIdLiquidacion());
    //                                            String total= "$ "+String.valueOf(unaLiquidacion.getTotalLiquidado());
    //                                            String totalRendido = null;
    //                                            String porcentaje = null;
    //                                            if(unaLiquidacion.getUnCargo()!= null)
    //                                            {
    //                                                totalRendido = String.valueOf(unaLiquidacion.getDetalle());
    //                                                porcentaje= "No aplicado";
    //                                            }
    //
    //                                            String fechaLiquidado = utilidades.getFecha(unaLiquidacion.getFechaDePago());
    //                                            lista.add(new ImprimirLiquidacion(numeroLiq,total,totalRendido,porcentaje,fechaLiquidado));
    //
    //                                            if(unMaestro != null)
    //                                            {
    //                                                parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
    //                                                parametros.put("nombreEmpresa",remiseria.getNombre());
    //                                                parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
    //                                                parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
    //                                                parametros.put("fechaActual", utilidades.getFechaActual());
    //                                                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
    //                                                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
    //                                                parametros.put("logo", remiseria.getLogo());
    //                                                if(unCargo != null)
    //                                                {
    //                                                    parametros.put("apellido", unaLiquidacion.getUnCargo().getApellido());
    //                                                    parametros.put("nombre", unaLiquidacion.getUnCargo().getNombre());
    //                                                    parametros.put("documento",String.valueOf(unaLiquidacion.getUnCargo().getDni()));
    //                                                    parametros.put("funcion",unCargo.getTipoCargo());
    //                                                }
    //
    //                                            }
    //                                            else if(unOperario != null)
    //                                            {
    //                                                parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
    //                                                parametros.put("nombreEmpresa",remiseria.getNombre());
    //                                                parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
    //                                                parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
    //                                                parametros.put("fechaActual", utilidades.getFechaActual());
    //                                                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
    //                                                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
    //                                                parametros.put("logo", remiseria.getLogo());
    //                                                if(unCargo != null)
    //                                                {
    //                                                    parametros.put("apellido", unaLiquidacion.getUnCargo().getApellido());
    //                                                    parametros.put("nombre", unaLiquidacion.getUnCargo().getNombre());
    //                                                    parametros.put("documento",String.valueOf(unaLiquidacion.getUnCargo().getDni()));
    //                                                    parametros.put("funcion",unCargo.getTipoCargo());
    //                                                }
    //
    //                                            }
    //
    //                                            JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Liquidacion.jrxml");
    //                                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(lista));
    //                                            JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
    //                                            JDialog frame;
    //                                            frame = new JDialog();
    //                                            frame.getContentPane().removeAll();
    //                                            frame.getContentPane().add(jviewer.getContentPane());
    //                                            frame.pack();
    //                                            frame.setSize(900, 700);
    //                                            frame.setModal(true);
    //                                            frame.setLocationRelativeTo(null);                          
    //                                            frame.show();
    //
    //                                        }
    //                                        catch (JRException ex)
    //                                        {
    //                                            JOptionPane.showMessageDialog(this, ex.getMessage());
    //                                        }
    //                                    }
    //                                    catch(Exception e)
    //                                    {
    //                                        JOptionPane.showMessageDialog(null,e," ",JOptionPane.ERROR_MESSAGE);
    //                                    }
    //                                }
    //                                else
    //                                {
    //                                    JOptionPane.showMessageDialog(this, "Debe realizar una Apertura de Lote para continuar con esta operacion",null, JOptionPane.WARNING_MESSAGE);
    //                                }     
    //                            }
    //                            else
    //                            {
    //                                JOptionPane.showMessageDialog(null, "Ya se ha realizado la Liquidación del Día", null, JOptionPane.WARNING_MESSAGE);
    //                            }
    //                        }
    //                        else
    //                        {
    //                            JOptionPane.showMessageDialog(null, "La fecha de hoy no corresponde con la fecha de pago del mes asignado al Cargo para poder realizar la Liquidación",null,JOptionPane.INFORMATION_MESSAGE);
    //                        }
    //                }
    //            }
    //            else        
    //            {
    //                JOptionPane.showMessageDialog(null,"No ha seleccionado la Liquidacion a Pagar", null, JOptionPane.ERROR_MESSAGE);
    //            }
            }
            else
            {
                JOptionPane.showMessageDialog(null, " No ha seleccionado la Persona para realizar la Liquidación", null, JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al realizar la Liquidación", null,JOptionPane.ERROR_MESSAGE);
        }
    } 
       
    public  void cargarTablaLiquidacionPagadas(JTable tabla , List<Liquidacion> liquidaciones) {
        limpiar_tabla(tabla);
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
            modelo.addColumn("N° Liquidación");
            modelo.addColumn("Tipo");
            modelo.addColumn("N° Documento");
            modelo.addColumn("Nombre y Apellido");
            modelo.addColumn("Fecha de pago");
            modelo.addColumn("Detalle");
            modelo.addColumn("Total Liquidado");
            Liquidacion aux = null;
            Iterator iter = liquidaciones.iterator();
            while (iter.hasNext())
            {
                aux = (Liquidacion) iter.next();
                if(aux.getUnChofer() != null)
                {
                    Object [] fila = new Object[7];
                    fila[0] = aux.getIdLiquidacion();
                    fila[1] = "Chofer";
                    fila[2] = aux.getUnChofer().getDni();
                    fila[3] = aux.getUnChofer().getNombre()+ " "+ aux.getUnChofer().getApellido();
                    fila[4] = utilidades.getFecha(aux.getFechaDePago());
                    fila[5] = aux.getDetalle();
                    fila[6] = "$ "+aux.getTotalLiquidado();
                    modelo.addRow(fila);
                }
                if(aux.getUnOperario() != null)
                {
                    if(aux.getTotalLiquidado()>0.0)
                    {
                        Object [] fila = new Object[7];
                        fila[0] = aux.getIdLiquidacion();
                        fila[1] = "Operario";
                        fila[2] = aux.getUnOperario().getDni();
                        fila[3] = aux.getUnOperario().getNombre() + " "+ aux.getUnOperario().getApellido();
                        fila[4] = utilidades.getFecha(aux.getFechaDePago());
                        if(remiseria.getUnPagoOperario().getTipoPago().equals("por hora"))
                        {
                            fila[5] = aux.getDetalle();
                        }
                        else
                        {
                            fila[5] = aux.getDetalle();
                        }
                        fila[6] =  "$ "+ String.valueOf(aux.getTotalLiquidado());
                        modelo.addRow(fila);
                    }
                }
                if(aux.getUnCargo() != null)
                {
                    Object [] fila = new Object[7];
                    fila[0] = aux.getIdLiquidacion();
                    fila[1] = aux.getUnCargo().getTipoCargo();
                    fila[2] = aux.getUnCargo().getDni();
                    fila[3] = aux.getUnCargo().getNombre() + " "+ aux.getUnCargo().getApellido();
                    fila[4] = utilidades.getFecha(aux.getFechaDePago());
                    fila[5] = aux.getDetalle();
                    fila[6] =  "$ "+ String.valueOf(aux.getTotalLiquidado());
                    modelo.addRow(fila);
                }                
            }
            modelo.rowsRemoved(null);
            tabla.setModel(modelo);
            tabla.getColumn("N° Liquidación").setMaxWidth(100);
            tabla.getColumn("Tipo").setMaxWidth(100);
            tabla.getColumn("N° Documento").setMaxWidth(100);
            tabla.getColumn("Nombre y Apellido").setMaxWidth(350);
            tabla.getColumn("Fecha de pago").setMaxWidth(150);
            tabla.getColumn("Detalle").setMaxWidth(170);
            tabla.getColumn("Total Liquidado").setMaxWidth(150);
    }         
            
            
    public  void cargarTablaPersonas() {
        limpiar_tabla(TablaTipoPersona);
        Collection choferes = remiseria.buscarChoferes(); 
        Collection operarios = remiseria.buscarOperariosActivos();
        Collection cargos = remiseria.buscarCargos();
        if(operarios != null && choferes != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = TablaTipoPersona.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("N° Documento");
            modelo.addColumn("Tipo");
            modelo.addColumn("Nombre y Apellido");
            Chofer aux = null;
            Operario aux2 = null;
            Cargo aux3= null;
            Iterator iter = choferes.iterator();
            while (iter.hasNext())
            {
                aux = (Chofer) iter.next();
                Object [] fila = new Object[3];
                fila[0] = aux.getDni();
                fila[1] = "Chofer";
                fila[2] = aux.getNombre()+ " "+ aux.getApellido();
                modelo.addRow(fila);
            }

            Iterator iter2 = operarios.iterator();
            while (iter2.hasNext())
            {
                aux2 = (Operario) iter2.next();
                Object [] fila = new Object[3];
                fila[0] = aux2.getDni();
                fila[1] = "Operario";
                fila[2] = aux2.getNombre()+ " "+ aux2.getApellido();
                modelo.addRow(fila);
            }
            
            Iterator iter3 = cargos.iterator();
            while (iter3.hasNext())
            {
                aux3 = (Cargo) iter3.next();
                Object [] fila = new Object[3];
                fila[0] = aux3.getDni();
                fila[1] = aux3.getTipoCargo();
                fila[2] = aux3.getNombre()+ " "+ aux3.getApellido();
                modelo.addRow(fila);
            }            
            modelo.rowsRemoved(null);
            TablaTipoPersona.setModel(modelo);
            TablaTipoPersona.getColumn("N° Documento").setMaxWidth(100);
            TablaTipoPersona.getColumn("Tipo").setMaxWidth(70);
            TablaTipoPersona.getColumn("Nombre y Apellido").setMaxWidth(200);
        } 
    }  
      
      
      public  void cargarTablaLiquidacionSegunPersona(List<Liquidacion> liquidaciones) {
        limpiar_tabla(tablaLiquidacionImpaga);
           DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaLiquidacionImpaga.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("N° Liquidación");
            modelo.addColumn("Tipo");
            modelo.addColumn("Fecha");
            modelo.addColumn("Detalle");
            modelo.addColumn("Total a Liquidar");
            Liquidacion aux = null;
            Iterator iter = liquidaciones.iterator();
            while (iter.hasNext())
            {
                aux = (Liquidacion) iter.next();
                if(aux.getUnChofer() != null)
                {
                    Object [] fila = new Object[5];
                    fila[0] = aux.getIdLiquidacion();
                    fila[1] = "Chofer";
                    fila[2] = utilidades.getFecha(aux.getFechaCreacion());
                    fila[3] = "Rindio $ "+aux.getUnaRendicion().getTotalRendicion();
                    fila[4] = "$ "+aux.getTotalLiquidado();
                    modelo.addRow(fila);
                }
                if(aux.getUnOperario() != null)
                {
                    if(aux.getTotalLiquidado()>0.0)
                    {
                        Object [] fila = new Object[5];
                        fila[0] = aux.getIdLiquidacion();
                        fila[1] = "Operario";
                        fila[2] = utilidades.getFecha(aux.getFechaCreacion());
                        if(remiseria.getUnPagoOperario().getTipoPago().equals("por hora"))
                        {
                            fila[3] = "Rindio " + aux.getUnMarcarTarjeta().getHoras()+ " hs " +aux.getUnMarcarTarjeta().getMinutos()+" min ";
                        }
                        else
                        {
                            fila[3] = "Rindio por el día";
                        }
                        fila[4] = "$ "+aux.getTotalLiquidado();
                        modelo.addRow(fila);
                    }
                }
            }
            modelo.rowsRemoved(null);
            tablaLiquidacionImpaga.setModel(modelo);
            tablaLiquidacionImpaga.getColumn("N° Liquidación").setMaxWidth(130);
            tablaLiquidacionImpaga.getColumn("Tipo").setMaxWidth(155);
            tablaLiquidacionImpaga.getColumn("Fecha").setMaxWidth(150);
            tablaLiquidacionImpaga.getColumn("Detalle").setMaxWidth(160);
            tablaLiquidacionImpaga.getColumn("Total a Liquidar").setMaxWidth(170);
    }        
      
      public Object obtenerPersona ()
      {
        int fila = TablaTipoPersona.getSelectedRow();
        int numeroDoc = (int) TablaTipoPersona.getValueAt(fila,0);
        if(numeroDoc != -1)
        {
            Object unChofer = remiseria.buscarChofer(numeroDoc);  
            Object unOperador = remiseria.buscarOperario(numeroDoc); 
           // Object unCargo = remiseria.buscarCargoPersona(numeroDoc);
            if(unChofer != null)
            {
                 return unChofer;
            }
            else if(unOperador != null)
            {
                return unOperador;
            }
//            else if(unCargo != null)
//            {
//                return unCargo;
//            }
                 
        }
        return null;
      }
  //</editor-fold>    
    
  //<editor-fold defaultstate="collapsed" desc=" MÉTODOS PARA CAJA ">    
  
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
    
        private void cargarInformacion(Lote elLote) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
        double totalSalidas = 0.00;
        double totalEntradas = 0.00;
        
        if (elLote != null) {
             Collection movim = elLote.getMovimientos().values();
            //List movimientosDelLote = new LinkedList();
            Movimiento unMovimiento = null;
            Iterator iter = movim.iterator();
            while (iter.hasNext()){
              unMovimiento =(Movimiento) iter.next();
              //if (unMovimiento.getFuente().equals("tarjeta")) {
                //    tarjetas += unMovimiento.getImporte();
               /* } else*/ if (unMovimiento.getFuente().equals("Efectivo")) {
                    if ("Egreso".equals(unMovimiento.getTipo())) {
                        totalSalidas += Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                    }
                    else
                    {
                        totalEntradas += Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                    }
                }
            } 
             
            Double totalIN = (totalEntradas);
            Double saldo = (totalEntradas - totalSalidas);
            lbl_saldo.setText("$ "+String.valueOf(Double.parseDouble(formateador.format(saldo))));
            lbl_Efectivo.setText("$ "+String.valueOf(Double.parseDouble(formateador.format(totalEntradas))));
            lbl_totalOUT.setText("$ "+String.valueOf(Double.parseDouble(formateador.format(totalSalidas))));
            lbl_totalIN.setText("$ "+String.valueOf(Double.parseDouble(formateador.format(totalIN))));
   
        }
    }

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
     
     private void verificarCierrePendiente() 
     {
        int res = 0;
        Lote vigente = unaCaja.obtenerLote_Vigente();
        if (vigente != null) {
            if (!es_delDia(vigente)) {
                if (!vigente.isCerrado() && "".equals(vigente.getHoraCierre())) {
                    if (vigente.getMovimientos() != null) {
                        res = JOptionPane.showConfirmDialog(this, "Aún no se ha realizado el cierre de caja de las ultimas actividades, ¿desea cerrarlo?","Advertencia", JOptionPane.YES_NO_OPTION);
                    }
                    if (res == JOptionPane.YES_OPTION) {
                        btnCerrarCajaActionPerformed(null);
                    }
                }
            }
        }
    }
    
    private List<Movimiento> listarMovimientos()
    {
        try{
            List<Movimiento> movim = new LinkedList<>();
            List<Lote> lotess = new LinkedList<>();
            Collection lotes = unaCaja.getLotes().values();
            Iterator iter = lotes.iterator();
            Lote lo = null;
            while(iter.hasNext())
            {
                lo = (Lote) iter.next();
                lotess.add(lo);
            }

            for (Lote l : lotess) {
                if ((l.getFechaLote().getTime() == dp_desde3.getDate().getTime()) || (l.getFechaLote().getTime() > dp_desde3.getDate().getTime())) {
                    if ((l.getFechaLote().getTime() == dp_hasta3.getDate().getTime()) || (l.getFechaLote().getTime() < dp_hasta3.getDate().getTime())) {
                        Collection movimientos = l.getMovimientos().values();
                        Iterator iter2 = movimientos.iterator();
                        Movimiento m = null;
                        while(iter2.hasNext()){
                            m = (Movimiento)iter2.next();
                            movim.add(m);

                        }
        
                        Collections.sort(movim, new Comparator<Movimiento>() {
                        @Override
                        public int compare(Movimiento p1, Movimiento p2) {               
                        return new Long(utilidades.getHour(p1.getHora_mov()).getTime()).compareTo(new Long(utilidades.getHour(p2.getHora_mov()).getTime()));
                        }
                        });                         
                    }
                }
            }
            
            return movim;
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        return null;
    }

     public void cargarTablaBuscados(JTable tabla, List lista) {
        limpiar_tabla(tabla);
        
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
            modelo.addColumn("Deuda N°");
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Viajes");
            ChoferPorMovil aux = null;
            Iterator iter = lista.iterator();
            while (iter.hasNext())
            {                
                aux = (ChoferPorMovil) iter.next();
                if(aux.getEstadoRendicion().equals("sin rendir"))
                {
                    if(aux.getCantidViajes()>0)
                    {
                        //aux.setCantidViajes(this.contarViajesSinRendir(aux.getUnMovil(), aux.getUnChofer()));
                        aux.setCantidViajes(this.contarViajesSinRendir(aux));
                        Object [] fila = new Object[4];
                        fila[0] = aux.getIdChoferPorMovil();
                        fila[1] = aux.getUnMovil().getNumeroMovil();
                        fila[2] = aux.getUnChofer().getNombre() +" "+aux.getUnChofer().getApellido();
                        fila[3] = aux.getCantidViajes();
                        modelo.addRow(fila);
                    }
                }
            }
            modelo.rowsRemoved(null);
            tabla.setModel(modelo);
            tabla.getColumn("Deuda N°").setMaxWidth(80);
            tabla.getColumn("Móvil N°").setMaxWidth(80);
            tabla.getColumn("Chofer").setMaxWidth(400);
            tabla.getColumn("Viajes").setMaxWidth(100);      
    }
     
    private void hasta()
    {
        Date ahora = new Date();
        dp_desde3.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hasta3.setDate(m.getTime());
    }
    
    private void mostrarLoteActual() {
            cargarTablaCaja(tbl_caja, listarMovimientos());
    } 
    

    
    public List cargarTablaLiquidacionSegunFecha()
    {
        Object persona = this.obtenerPersona();
        Chofer unChofer = null;
        Operario unOpera = null;
        if(persona instanceof Chofer)
        {
            unChofer = (Chofer) persona;

        }
        else if(persona instanceof Operario)
        {
            unOpera = (Operario) persona;
        }
        List<Liquidacion> liquidados = new LinkedList<>();
        Collection liq = remiseria.getLiquidaciones().values();
        Liquidacion aux = null;
        Iterator iter = liq.iterator();
        while(iter.hasNext()){
            aux = (Liquidacion)iter.next();
            if ((aux.getFechaCreacion().getTime() == dp_desdeLiq.getDate().getTime()) || (aux.getFechaCreacion().getTime() > dp_desdeLiq.getDate().getTime())) {
                if ((aux.getFechaCreacion().getTime() == dp_hastaLiq.getDate().getTime()) || (aux.getFechaCreacion().getTime() < dp_hastaLiq.getDate().getTime())) {
                    if(aux.getEstadoLiquidacion().equals("sin pagar"))
                    {   
                        if(aux.getUnChofer()== unChofer)
                        {
                            liquidados.add(aux);
                        }
                        else if(aux.getUnOperario() == unOpera)
                        {
                            liquidados.add(aux);
                        }
                    }
                }
            }
        }
        return liquidados;    
    }    
    
      //</editor-fold>    
    
  public void cargarEntrada()
  {
      cbxEntrada.removeAllItems();
      Collection conceptoEntrada = unaCaja.getConceptos().values();
      if(conceptoEntrada != null)
      {
        Concepto con = null;
        Iterator iter = conceptoEntrada.iterator();
        while(iter.hasNext())
        {
            con = (Concepto) iter.next();
            if(con.getTipo().equals("Entrada"))
            {
                cbxEntrada.addItem(con.toString());
            }
        }
      }
  
  }
    public void cargarSalida()
  {
      cbxSalida.removeAllItems();
      Collection conceptoSalida = unaCaja.getConceptos().values();
      if(conceptoSalida != null)
      {
        Concepto con = null;
        Iterator iter = conceptoSalida.iterator();
        while(iter.hasNext())
        {
            con = (Concepto) iter.next();
            if(con.getTipo().equals("Salida"))
            {
                cbxSalida.addItem(con.toString());
            }
        }
      }
  
  }  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        blackTabbedPaneUI1 = new org.matrix.BlackTabbedPaneUI();
        jPanel28 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        blackTabbedPane1 = new org.matrix.BlackTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jXTaskPaneContainer9 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        dp_desde3 = new org.jdesktop.swingx.JXDatePicker();
        jLabel18 = new javax.swing.JLabel();
        dp_hasta3 = new org.jdesktop.swingx.JXDatePicker();
        btn_hoy3 = new javax.swing.JButton();
        jXTaskPaneContainer8 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel25 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        rbnEntrada = new javax.swing.JRadioButton();
        rbnSalida = new javax.swing.JRadioButton();
        cbxSalida = new javax.swing.JComboBox();
        cbxEntrada = new javax.swing.JComboBox();
        btnNuevosConceptos = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txtImporte = new javax.swing.JTextField();
        btnNuevosConceptos1 = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txtDetalle = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        btnCancelarMov = new javax.swing.JButton();
        jPanel35 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        lbl_Efectivo = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jXTaskPaneContainer7 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_caja = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lbl_totalIN = new javax.swing.JLabel();
        lbl_totalOUT = new javax.swing.JLabel();
        lbl_saldo = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        btnReabrirCaja = new javax.swing.JButton();
        btnCerrarCaja = new javax.swing.JButton();
        btnAbrirCaja = new javax.swing.JButton();
        btnDetalles = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        panelRendiciones = new javax.swing.JPanel();
        JTabbedPanelRendiciones = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jXTaskPaneContainer2 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel6 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        dp_desde = new org.jdesktop.swingx.JXDatePicker();
        jLabel24 = new javax.swing.JLabel();
        dp_hasta = new org.jdesktop.swingx.JXDatePicker();
        btn_hoy4 = new javax.swing.JButton();
        btnTodoRen = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btnRendir = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        txtKmsTotal = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jXTaskPaneContainer5 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla2 = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTotalRendir = new javax.swing.JTextField();
        jXTaskPaneContainer6 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla1 = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtBuscar = new org.jdesktop.swingx.JXSearchField();
        cmbFiltro = new javax.swing.JComboBox();
        jPanel12 = new javax.swing.JPanel();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaRendPagos = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cmbOpcionBusquedaRendicion = new javax.swing.JComboBox();
        SearchRendicion = new org.jdesktop.swingx.JXSearchField();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jXTaskPaneContainer4 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel13 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        dp_desde1 = new org.jdesktop.swingx.JXDatePicker();
        jLabel26 = new javax.swing.JLabel();
        dp_hasta1 = new org.jdesktop.swingx.JXDatePicker();
        btn_hoy5 = new javax.swing.JButton();
        btnTodoRenPag = new javax.swing.JButton();
        checkTodo1 = new javax.swing.JCheckBox();
        checkFiltrar = new javax.swing.JCheckBox();
        cmbFiltroReporte = new javax.swing.JComboBox();
        cmbChoferMovil = new javax.swing.JComboBox();
        btnListar = new javax.swing.JButton();
        panelLiquidaciones = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jXTaskPaneContainer10 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel14 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        dp_desdeLiq = new org.jdesktop.swingx.JXDatePicker();
        jLabel32 = new javax.swing.JLabel();
        dp_hastaLiq = new org.jdesktop.swingx.JXDatePicker();
        btn_hoyLiq = new javax.swing.JButton();
        btnTodoLiq = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaLiquidacionImpaga = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TablaTipoPersona = new javax.swing.JTable();
        SearchFieldPersona = new org.jdesktop.swingx.JXSearchField();
        cmbOpcionBusqueda = new javax.swing.JComboBox();
        jPanel17 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        TablaLiquidacionesPagadas = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        searchLiquidacion = new org.jdesktop.swingx.JXSearchField();
        cmbFlitroLiq = new javax.swing.JComboBox();
        jPanel33 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        dp_desdeLiq1 = new org.jdesktop.swingx.JXDatePicker();
        jLabel36 = new javax.swing.JLabel();
        dp_hastaLiq1 = new org.jdesktop.swingx.JXDatePicker();
        btn_hoyLiq1 = new javax.swing.JButton();
        btnTodoLiq1 = new javax.swing.JButton();
        checkTodo = new javax.swing.JCheckBox();
        checFiltrarLiq = new javax.swing.JCheckBox();
        cmbFiltrarPorChoferOperario = new javax.swing.JComboBox();
        cmbOperarioChofer = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setType(java.awt.Window.Type.POPUP);

        jPanel28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel29.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel29.setText("Caja Principal");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1081, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        blackTabbedPane1.setForeground(new java.awt.Color(0, 0, 0));
        blackTabbedPane1.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        blackTabbedPane1.setTabSelectedColor(new java.awt.Color(117, 150, 227));

        jXTaskPaneContainer9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel16.setText("Caja Principal");
        jPanel10.add(jLabel16);

        jLabel17.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel17.setText("Desde:");
        jPanel10.add(jLabel17);

        dp_desde3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_desde3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_desde3ActionPerformed(evt);
            }
        });
        jPanel10.add(dp_desde3);

        jLabel18.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel18.setText("Hasta:");
        jPanel10.add(jLabel18);

        dp_hasta3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_hasta3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_hasta3ActionPerformed(evt);
            }
        });
        jPanel10.add(dp_hasta3);

        btn_hoy3.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btn_hoy3.setText("Hoy");
        btn_hoy3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hoy3ActionPerformed(evt);
            }
        });
        jPanel10.add(btn_hoy3);

        javax.swing.GroupLayout jXTaskPaneContainer9Layout = new javax.swing.GroupLayout(jXTaskPaneContainer9);
        jXTaskPaneContainer9.setLayout(jXTaskPaneContainer9Layout);
        jXTaskPaneContainer9Layout.setHorizontalGroup(
            jXTaskPaneContainer9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(621, Short.MAX_VALUE))
        );
        jXTaskPaneContainer9Layout.setVerticalGroup(
            jXTaskPaneContainer9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel27.setText("Referencias");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel27))
        );

        buttonGroup1.add(rbnEntrada);
        rbnEntrada.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        rbnEntrada.setText("Entrada:");
        rbnEntrada.setBackground(new java.awt.Color(117, 150, 227));
        rbnEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbnEntradaMouseClicked(evt);
            }
        });
        rbnEntrada.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbnEntradaStateChanged(evt);
            }
        });
        rbnEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnEntradaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbnSalida);
        rbnSalida.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        rbnSalida.setText("Salida:");
        rbnSalida.setBackground(new java.awt.Color(117, 150, 227));
        rbnSalida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbnSalidaMouseClicked(evt);
            }
        });
        rbnSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbnSalidaActionPerformed(evt);
            }
        });

        cbxSalida.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxSalida.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbxSalida.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSalidaItemStateChanged(evt);
            }
        });

        cbxEntrada.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxEntrada.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbxEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxEntradaMouseClicked(evt);
            }
        });
        cbxEntrada.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEntradaItemStateChanged(evt);
            }
        });

        btnNuevosConceptos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnNuevosConceptos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevosConceptosActionPerformed(evt);
            }
        });

        jPanel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel28.setText("Detalle");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel28))
        );

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("Ingrese importe: $");

        txtImporte.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtImporte.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtImporte.setBackground(new java.awt.Color(0, 204, 51));
        txtImporte.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImporteKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addComponent(jSeparator1)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnNuevosConceptos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnNuevosConceptos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevosConceptos1ActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnGuardar.setText("Guardar ");
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        txtDetalle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDetalle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDetalleKeyTyped(evt);
            }
        });

        jPanel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel38.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel38.setText("Importe");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel38))
        );

        btnCancelarMov.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCancelarMov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        btnCancelarMov.setText("Cancelar");
        btnCancelarMov.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelarMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarMovActionPerformed(evt);
            }
        });

        jPanel35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel39.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel39.setText("Gestor de movimientos");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel39))
        );

        lbl_Efectivo.setBackground(new java.awt.Color(0, 204, 51));
        lbl_Efectivo.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lbl_Efectivo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lbl_Efectivo.setOpaque(true);

        jLabel19.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jLabel19.setText("Ingresos en Efectivo:");

        javax.swing.GroupLayout jXTaskPaneContainer8Layout = new javax.swing.GroupLayout(jXTaskPaneContainer8);
        jXTaskPaneContainer8.setLayout(jXTaskPaneContainer8Layout);
        jXTaskPaneContainer8Layout.setHorizontalGroup(
            jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer8Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jXTaskPaneContainer8Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer8Layout.createSequentialGroup()
                                .addComponent(rbnSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxSalida, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevosConceptos1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTaskPaneContainer8Layout.createSequentialGroup()
                                .addComponent(rbnEntrada)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxEntrada, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevosConceptos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jXTaskPaneContainer8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(6, 6, 6))
            .addGroup(jXTaskPaneContainer8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer8Layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarMov))
                    .addComponent(jPanel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDetalle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer8Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jXTaskPaneContainer8Layout.setVerticalGroup(
            jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevosConceptos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbnEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17)
                .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevosConceptos1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbnSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarMov, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_Efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jXTaskPaneContainer7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jScrollPane3.setAutoscrolls(true);
        jScrollPane3.setMaximumSize(null);

        tbl_caja.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbl_caja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ingreso", "Egreso", "Detalle", "Fecha", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbl_caja);

        jPanel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel15.setText("Movimientos");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel15))
        );

        lbl_totalIN.setBackground(new java.awt.Color(0, 204, 51));
        lbl_totalIN.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lbl_totalIN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lbl_totalIN.setOpaque(true);

        lbl_totalOUT.setBackground(new java.awt.Color(255, 51, 51));
        lbl_totalOUT.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lbl_totalOUT.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lbl_totalOUT.setOpaque(true);

        lbl_saldo.setBackground(new java.awt.Color(0, 204, 255));
        lbl_saldo.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lbl_saldo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        lbl_saldo.setOpaque(true);

        jLabel23.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jLabel23.setText("Saldo:");

        jLabel21.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jLabel21.setText("Totales:");

        javax.swing.GroupLayout jXTaskPaneContainer7Layout = new javax.swing.GroupLayout(jXTaskPaneContainer7);
        jXTaskPaneContainer7.setLayout(jXTaskPaneContainer7Layout);
        jXTaskPaneContainer7Layout.setHorizontalGroup(
            jXTaskPaneContainer7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jXTaskPaneContainer7Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer7Layout.createSequentialGroup()
                                .addComponent(lbl_totalIN, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(lbl_totalOUT, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jXTaskPaneContainer7Layout.setVerticalGroup(
            jXTaskPaneContainer7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_totalOUT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_totalIN, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnReabrirCaja.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnReabrirCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/keyring_dll_01_15.png"))); // NOI18N
        btnReabrirCaja.setText("Reabrir Caja");
        btnReabrirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReabrirCajaActionPerformed(evt);
            }
        });

        btnCerrarCaja.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCerrarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/no.png"))); // NOI18N
        btnCerrarCaja.setText("Cerrar Caja");
        btnCerrarCaja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCerrarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarCajaActionPerformed(evt);
            }
        });

        btnAbrirCaja.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAbrirCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Moneda.png"))); // NOI18N
        btnAbrirCaja.setText("Abrir Caja");
        btnAbrirCaja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAbrirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirCajaActionPerformed(evt);
            }
        });

        btnDetalles.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnDetalles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/currency.png"))); // NOI18N
        btnDetalles.setText("Detalles");
        btnDetalles.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallesActionPerformed(evt);
            }
        });

        btnGuardar1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnGuardar1.setText("<html><p>Agregar</p><p>Movimiento</p></html>");
        btnGuardar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReabrirCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCerrarCaja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAbrirCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDetalles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar1))
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAbrirCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReabrirCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPaneContainer8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jXTaskPaneContainer9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jXTaskPaneContainer7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jXTaskPaneContainer8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        blackTabbedPane1.addTab("Caja Principal", jPanel1);

        JTabbedPanelRendiciones.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        JTabbedPanelRendiciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTabbedPanelRendicionesMouseClicked(evt);
            }
        });

        jXTaskPaneContainer2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel20.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel20.setText("Desde:");
        jPanel6.add(jLabel20);

        dp_desde.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_desde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_desdeActionPerformed(evt);
            }
        });
        jPanel6.add(dp_desde);

        jLabel24.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel24.setText("Hasta:");
        jPanel6.add(jLabel24);

        dp_hasta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_hasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_hastaActionPerformed(evt);
            }
        });
        jPanel6.add(dp_hasta);

        btn_hoy4.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btn_hoy4.setText("Hoy");
        btn_hoy4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hoy4ActionPerformed(evt);
            }
        });
        jPanel6.add(btn_hoy4);

        btnTodoRen.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnTodoRen.setText("Todo");
        btnTodoRen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodoRenActionPerformed(evt);
            }
        });
        jPanel6.add(btnTodoRen);

        javax.swing.GroupLayout jXTaskPaneContainer2Layout = new javax.swing.GroupLayout(jXTaskPaneContainer2);
        jXTaskPaneContainer2.setLayout(jXTaskPaneContainer2Layout);
        jXTaskPaneContainer2Layout.setHorizontalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jXTaskPaneContainer2Layout.setVerticalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnRendir.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnRendir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sign-Down.png"))); // NOI18N
        btnRendir.setText("Rendir");
        btnRendir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRendir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRendirActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        txtKmsTotal.setEditable(false);
        txtKmsTotal.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        txtKmsTotal.setBackground(new java.awt.Color(204, 204, 204));
        txtKmsTotal.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel30.setFont(new java.awt.Font("Goudy Old Style", 3, 14)); // NOI18N
        jLabel30.setText("Kms totales:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRendir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtKmsTotal, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRendir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addGap(6, 6, 6)
                .addComponent(txtKmsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tabla2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Viaje", "Fecha", "Hora", "Costo"
            }
        ));
        jScrollPane4.setViewportView(tabla2);

        jLabel13.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel13.setText("Detalle");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 3, 14)); // NOI18N
        jLabel2.setText("Total a rendir $");

        txtTotalRendir.setEditable(false);
        txtTotalRendir.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        txtTotalRendir.setBackground(new java.awt.Color(255, 255, 0));
        txtTotalRendir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jXTaskPaneContainer5Layout = new javax.swing.GroupLayout(jXTaskPaneContainer5);
        jXTaskPaneContainer5.setLayout(jXTaskPaneContainer5Layout);
        jXTaskPaneContainer5Layout.setHorizontalGroup(
            jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer5Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer5Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalRendir, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jXTaskPaneContainer5Layout.setVerticalGroup(
            jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalRendir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        jXTaskPaneContainer6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tabla1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Deuda N°", "Móvil N°", "Chofer ", "Viajes "
            }
        ));
        tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla1);

        jLabel14.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel14.setText("Rendiciones por pagar");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGap(0, 296, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        txtBuscar.setPrompt("Buscar rendiciones de una persona");
        txtBuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtBuscarCaretUpdate(evt);
            }
        });

        cmbFiltro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Móvil N°", "Chofer" }));

        javax.swing.GroupLayout jXTaskPaneContainer6Layout = new javax.swing.GroupLayout(jXTaskPaneContainer6);
        jXTaskPaneContainer6.setLayout(jXTaskPaneContainer6Layout);
        jXTaskPaneContainer6Layout.setHorizontalGroup(
            jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer6Layout.createSequentialGroup()
                        .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jXTaskPaneContainer6Layout.setVerticalGroup(
            jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPaneContainer5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jXTaskPaneContainer6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPaneContainer5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        JTabbedPanelRendiciones.addTab("Dinero circulante", jPanel2);

        jXTaskPaneContainer3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaRendPagos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaRendPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rendición N°", "Móvil N°", "Chofer ", "Total ", "Fecha ", "Hora", "Viajes"
            }
        ));
        tablaRendPagos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaRendPagosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaRendPagos);

        jLabel12.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel12.setText("Rendiciones");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        cmbOpcionBusquedaRendicion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Móvil N°", "Apellido", "Nombre" }));

        SearchRendicion.setPrompt("Buscar una Rendicion");
        SearchRendicion.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                SearchRendicionCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer3Layout = new javax.swing.GroupLayout(jXTaskPaneContainer3);
        jXTaskPaneContainer3.setLayout(jXTaskPaneContainer3Layout);
        jXTaskPaneContainer3Layout.setHorizontalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)))
                    .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cmbOpcionBusquedaRendicion, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchRendicion, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jXTaskPaneContainer3Layout.setVerticalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbOpcionBusquedaRendicion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchRendicion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaDetalles.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Detalle", "Costo", "Fecha", "Hora"
            }
        ));
        jScrollPane6.setViewportView(tablaDetalles);

        jLabel11.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel11.setText("Detalles de rendición");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jXTaskPaneContainer4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel25.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel25.setText("Desde:");

        dp_desde1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_desde1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_desde1ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel26.setText("Hasta:");

        dp_hasta1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_hasta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_hasta1ActionPerformed(evt);
            }
        });

        btn_hoy5.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btn_hoy5.setText("Hoy");
        btn_hoy5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hoy5ActionPerformed(evt);
            }
        });

        btnTodoRenPag.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnTodoRenPag.setText("Todo");
        btnTodoRenPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodoRenPagActionPerformed(evt);
            }
        });

        checkTodo1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkTodo1.setText("Todo");
        checkTodo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTodo1ActionPerformed(evt);
            }
        });

        checkFiltrar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkFiltrar.setText("Filtrar");
        checkFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFiltrarActionPerformed(evt);
            }
        });

        cmbFiltroReporte.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbFiltroReporte.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chofer", "Móvil" }));
        cmbFiltroReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroReporteActionPerformed(evt);
            }
        });
        cmbFiltroReporte.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbFiltroReportePropertyChange(evt);
            }
        });

        cmbChoferMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(5, 5, 5)
                .addComponent(dp_desde1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel26)
                .addGap(5, 5, 5)
                .addComponent(dp_hasta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btn_hoy5)
                .addGap(5, 5, 5)
                .addComponent(btnTodoRenPag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkTodo1)
                .addGap(5, 5, 5)
                .addComponent(checkFiltrar)
                .addGap(5, 5, 5)
                .addComponent(cmbFiltroReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(cmbChoferMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel25))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(dp_desde1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel26))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(dp_hasta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(btn_hoy5))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkTodo1)
                            .addComponent(checkFiltrar)
                            .addComponent(btnTodoRenPag)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbFiltroReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbChoferMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnListar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER 1.png"))); // NOI18N
        btnListar.setText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer4Layout = new javax.swing.GroupLayout(jXTaskPaneContainer4);
        jXTaskPaneContainer4.setLayout(jXTaskPaneContainer4Layout);
        jXTaskPaneContainer4Layout.setHorizontalGroup(
            jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jXTaskPaneContainer4Layout.setVerticalGroup(
            jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer4Layout.createSequentialGroup()
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPaneContainer4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jXTaskPaneContainer4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        JTabbedPanelRendiciones.addTab("Rendiciones pagadas", jPanel12);

        javax.swing.GroupLayout panelRendicionesLayout = new javax.swing.GroupLayout(panelRendiciones);
        panelRendiciones.setLayout(panelRendicionesLayout);
        panelRendicionesLayout.setHorizontalGroup(
            panelRendicionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JTabbedPanelRendiciones)
        );
        panelRendicionesLayout.setVerticalGroup(
            panelRendicionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JTabbedPanelRendiciones, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        blackTabbedPane1.addTab("Rendiciones", panelRendiciones);

        jTabbedPane1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N

        jXTaskPaneContainer10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel31.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel31.setText("Desde:");
        jPanel14.add(jLabel31);

        dp_desdeLiq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_desdeLiq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_desdeLiqActionPerformed(evt);
            }
        });
        jPanel14.add(dp_desdeLiq);

        jLabel32.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel32.setText("Hasta:");
        jPanel14.add(jLabel32);

        dp_hastaLiq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_hastaLiq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_hastaLiqActionPerformed(evt);
            }
        });
        jPanel14.add(dp_hastaLiq);

        btn_hoyLiq.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btn_hoyLiq.setText("Hoy");
        btn_hoyLiq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hoyLiqActionPerformed(evt);
            }
        });
        jPanel14.add(btn_hoyLiq);

        btnTodoLiq.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnTodoLiq.setText("Todo");
        btnTodoLiq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodoLiqActionPerformed(evt);
            }
        });
        jPanel14.add(btnTodoLiq);

        javax.swing.GroupLayout jXTaskPaneContainer10Layout = new javax.swing.GroupLayout(jXTaskPaneContainer10);
        jXTaskPaneContainer10.setLayout(jXTaskPaneContainer10Layout);
        jXTaskPaneContainer10Layout.setHorizontalGroup(
            jXTaskPaneContainer10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer10Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jXTaskPaneContainer10Layout.setVerticalGroup(
            jXTaskPaneContainer10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer10Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(117, 150, 227));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaLiquidacionImpaga.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaLiquidacionImpaga.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Liquidación", "Tipo", "Fecha", "Detalle", "Total a Liquidar"
            }
        ));
        jScrollPane7.setViewportView(tablaLiquidacionImpaga);

        jLabel34.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel34.setText("Liquidaciones a pagar");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel34)
                .addGap(0, 508, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel34)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(117, 150, 227));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel33.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel33.setText("Tipo de persona");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jLabel33)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addComponent(jLabel33)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TablaTipoPersona.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TablaTipoPersona.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Documento", "Tipo", "Nombre y Apellido"
            }
        ));
        TablaTipoPersona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaTipoPersonaMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TablaTipoPersona);

        SearchFieldPersona.setPrompt("Buscar una persona");
        SearchFieldPersona.setRecentSearchesSaveKey("");
        SearchFieldPersona.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                SearchFieldPersonaCaretUpdate(evt);
            }
        });

        cmbOpcionBusqueda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbOpcionBusqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N° Documento", "Nombre", "Apellido" }));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(cmbOpcionBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SearchFieldPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 362, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchFieldPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbOpcionBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                    .addContainerGap(34, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(37, 37, 37)))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jButton1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sign-up.png"))); // NOI18N
        jButton1.setText("Liquidar");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jXTaskPaneContainer10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Liquidaciones por pagar", jPanel7);

        jPanel19.setBackground(new java.awt.Color(117, 150, 227));
        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        TablaLiquidacionesPagadas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TablaLiquidacionesPagadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Liquidación", "Tipo", "N° Documento", "Nombre y Apellido", "Fecha de pago", "Detalle", "Total Liquidado"
            }
        ));
        jScrollPane9.setViewportView(TablaLiquidacionesPagadas);

        jLabel37.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel37.setText("Liquidaciones pagadas");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        searchLiquidacion.setPrompt("Buscar una liquidación");
        searchLiquidacion.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                searchLiquidacionCaretUpdate(evt);
            }
        });

        cmbFlitroLiq.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N° Documento", "Apellido", "Nombre" }));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1082, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbFlitroLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchLiquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFlitroLiq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jButton4.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER 1.png"))); // NOI18N
        jButton4.setText("Listar");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        jButton5.setText("Salir");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(117, 150, 227));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel35.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel35.setText("Desde:");

        dp_desdeLiq1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_desdeLiq1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_desdeLiq1ActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel36.setText("Hasta:");

        dp_hastaLiq1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_hastaLiq1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_hastaLiq1ActionPerformed(evt);
            }
        });

        btn_hoyLiq1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btn_hoyLiq1.setText("Hoy");
        btn_hoyLiq1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hoyLiq1ActionPerformed(evt);
            }
        });

        btnTodoLiq1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnTodoLiq1.setText("Todo");
        btnTodoLiq1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodoLiq1ActionPerformed(evt);
            }
        });

        checkTodo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkTodo.setText("Todo");
        checkTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTodoActionPerformed(evt);
            }
        });

        checFiltrarLiq.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checFiltrarLiq.setText("Filtrar");
        checFiltrarLiq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checFiltrarLiqActionPerformed(evt);
            }
        });

        cmbFiltrarPorChoferOperario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbFiltrarPorChoferOperario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chofer", "Operario" }));
        cmbFiltrarPorChoferOperario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltrarPorChoferOperarioActionPerformed(evt);
            }
        });

        cmbOperarioChofer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel35)
                .addGap(5, 5, 5)
                .addComponent(dp_desdeLiq1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel36)
                .addGap(5, 5, 5)
                .addComponent(dp_hastaLiq1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btn_hoyLiq1)
                .addGap(5, 5, 5)
                .addComponent(btnTodoLiq1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkTodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checFiltrarLiq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbFiltrarPorChoferOperario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbOperarioChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel35))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(dp_desdeLiq1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel36))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(dp_hastaLiq1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(btn_hoyLiq1))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbFiltrarPorChoferOperario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbOperarioChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(checFiltrarLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(checkTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnTodoLiq1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Liquidaciones pagadas", jPanel8);

        javax.swing.GroupLayout panelLiquidacionesLayout = new javax.swing.GroupLayout(panelLiquidaciones);
        panelLiquidaciones.setLayout(panelLiquidacionesLayout);
        panelLiquidacionesLayout.setHorizontalGroup(
            panelLiquidacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        panelLiquidacionesLayout.setVerticalGroup(
            panelLiquidacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        blackTabbedPane1.addTab("Liquidaciones", panelLiquidaciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(blackTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blackTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
  
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//        // TODO add your handling code here:
        try
        {
        if(rbnEntrada.isSelected()==true || rbnSalida.isSelected()==true)
        {
            if (es_delDia(loteVigente())) 
            {
                try{
                    Lote unLote = loteVigente();
                    Double importe = Double.valueOf(txtImporte.getText());
                    if(rbnEntrada.isSelected() == true){

                    unLote.altaMovimiento(utilidades.getDate(utilidades.getFechaActual()), utilidades.getHora(utilidades.getFechaHoraActual()), importe, "Ingreso", cbxEntrada.getSelectedItem().toString()+" " + txtDetalle.getText(), "Efectivo");
                    }
                    else
                    {
                        if(rbnSalida.isSelected()== true)
                        {
                             unLote.altaMovimiento(utilidades.getDate(utilidades.getFechaActual()), utilidades.getHora(utilidades.getFechaHoraActual()), importe, "Egreso", cbxSalida.getSelectedItem().toString()+ " " +txtDetalle.getText(), "Efectivo");
                        }
                    }
                    cargarTablaCaja(tbl_caja, listarMovimientos());
                    cargarInformacion(unLote);
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado una operación para poder guadar el nuevo movimento", null, JOptionPane.ERROR_MESSAGE);
                    if(txtImporte.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null,"No ha ingresado el Importe correspondiente a la operación", null, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else 
            {
                JOptionPane.showMessageDialog(this, "Debe realizar una Apertura de Lote para continuar con esta operacion");
            }
            btnCancelarMov.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnGuardar1.setEnabled(true);
            rbnEntrada.setEnabled(false);
            rbnSalida.setEnabled(false);
            cbxEntrada.setEnabled(false);
            cbxSalida.setEnabled(false);
            txtDetalle.setEnabled(false);
            txtImporte.setText(null);
            txtImporte.setEnabled(false);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No ha seleccionado el Tipo de Movimiento - Entrada o Salida");
        }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al Guardar el Movimiento", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void rbnEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbnEntradaMouseClicked
        // TODO add your handling code here:
           //NO SE USA 
    }//GEN-LAST:event_rbnEntradaMouseClicked

    private void rbnSalidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbnSalidaMouseClicked
        // TODO add your handling code here:
          //NO SE USA 
    }//GEN-LAST:event_rbnSalidaMouseClicked

    private void rbnEntradaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbnEntradaStateChanged
          //NO SE USA 
    }//GEN-LAST:event_rbnEntradaStateChanged

    private void rbnEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnEntradaActionPerformed
        this.cbxEntrada.setEnabled(true);    
        this.cbxSalida.setEnabled(false); 
    }//GEN-LAST:event_rbnEntradaActionPerformed

    private void rbnSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnSalidaActionPerformed
        this.cbxSalida.setEnabled(true);
        this.cbxEntrada.setEnabled(false);
    }//GEN-LAST:event_rbnSalidaActionPerformed

    private void btnCerrarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarCajaActionPerformed
       // TODO add your handling code here:
        try
        {
            if(unMaestro == null)
            {
                 Lote oneLote = loteVigente();
                 if (!oneLote.isCerrado()) {
                     JDialogCierreDeCaja cierre = new JDialogCierreDeCaja(remiseria,parent,modal,unaCaja,oneLote,utilidades,unOperario,unMaestro,rbnEntrada,rbnSalida,cbxEntrada,cbxSalida,txtImporte, lbl_saldo,lbl_Efectivo,lbl_totalOUT,lbl_totalIN, tbl_caja,dp_desde3, dp_hasta3);
                     cierre.show();
                     cargarInformacion(loteVigente());
                     cargarTablaCaja(tbl_caja, listarMovimientos());
                     blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),false);
                     blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),false);
                   //  btnCerrarCaja.setEnabled(false);
                     btnGuardar.setEnabled(false);
                     //btnGuardar1.setEnabled(false);
                     btnCancelarMov.setEnabled(false);
                 } else {
                     JOptionPane.showMessageDialog(this, "El Lote que quiere cerrar ya ha sido cerrado previamente");
                 }
            }
            else
            {
                 Lote oneLote = loteVigente();
                 if (!oneLote.isCerrado()) {
                    JDialogCierreDeCaja cierre = new JDialogCierreDeCaja(remiseria,parent,modal,unaCaja,oneLote,utilidades,unOperario,unMaestro,rbnEntrada,rbnSalida,cbxEntrada,cbxSalida,txtImporte, lbl_saldo,lbl_Efectivo,lbl_totalOUT,lbl_totalIN, tbl_caja,dp_desde3, dp_hasta3);
                     cierre.show();
                     cargarInformacion(loteVigente());
                     cargarTablaCaja(tbl_caja, listarMovimientos());
                     blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),false);
                     blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),false);   
                     btnGuardar.setEnabled(false);
                     btnCancelarMov.setEnabled(false);     
                 } else {
                     JOptionPane.showMessageDialog(this, "El Lote que quiere cerrar ya ha sido cerrado previamente");
                 }
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al Cerrar Caja", null, JOptionPane.ERROR_MESSAGE);
        }
              
    }//GEN-LAST:event_btnCerrarCajaActionPerformed

    private void cbxEntradaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEntradaItemStateChanged

    }//GEN-LAST:event_cbxEntradaItemStateChanged

    public static void cargarTablaCaja(JTable tabla, List<Movimiento> lista) 
    {
        final Utilidad utilidades = new Utilidad();
        if(lista != null)
        {
            limpiar_tabla(tabla);


            Collections.sort(lista, new Comparator<Movimiento>() {
            @Override
            public int compare(Movimiento p1, Movimiento p2) {                                   
                //return new Long(p1.getFecha_dia().getTime()).compareTo(new Long(p1.getFecha_dia().getTime()));
                return new Integer(p1.getIdMovimiento()).compareTo(new Integer(p2.getIdMovimiento()));
            }          
            });              
            for (Movimiento unMovimiento : lista) {

                ((DefaultTableModel) tabla.getModel()).addRow((unMovimiento).toVector());
            }
        }
    }
        
    private void cbxEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxEntradaMouseClicked
        // TODO add your handling code here:
        //NO SE USA 
    }//GEN-LAST:event_cbxEntradaMouseClicked

    private void cbxSalidaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSalidaItemStateChanged
        // TODO add your handling code here:
        //NO SE USA 
    }//GEN-LAST:event_cbxSalidaItemStateChanged

    private void btnReabrirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReabrirCajaActionPerformed
        // TODO add your handling code here:
        try
        {
            if (es_delDia(loteVigente()))
            {
                Lote ultimoLote = unaCaja.obtenerLote_Vigente();
                if(ultimoLote != null )
                {
                    unaCaja.reabrirLote(ultimoLote);
                    btnCerrarCaja.setEnabled(true);
                    btnGuardar1.setEnabled(true);
                    blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),true);
                    blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),true);   
                    JOptionPane.showMessageDialog(this, "Se ha vuelto a abrir el presente Lote");
                    cargarTablaCaja(tbl_caja, listarMovimientos());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Aun no ha hecho la Apertura de Caja del día", null, JOptionPane.ERROR_MESSAGE);
                }
            }   
            else
            {
                JOptionPane.showMessageDialog(null,"No ha realizado la Apertura de Caja del día");
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al Reabrir Caja", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnReabrirCajaActionPerformed

    private void dp_desde3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_desde3ActionPerformed
        cargarTablaCaja(tbl_caja, listarMovimientos());
    }//GEN-LAST:event_dp_desde3ActionPerformed

    private void dp_hasta3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_hasta3ActionPerformed
        cargarTablaCaja(tbl_caja, listarMovimientos());
    }//GEN-LAST:event_dp_hasta3ActionPerformed

    private void btn_hoy3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hoy3ActionPerformed
        hasta();
        cargarTablaCaja(tbl_caja, listarMovimientos());

    }//GEN-LAST:event_btn_hoy3ActionPerformed

    private void btnAbrirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirCajaActionPerformed
        // TODO add your handling code here:
        try
        {
            verificarCierrePendiente();
            Calendar hoy = Calendar.getInstance();
            Calendar fechaLote = Calendar.getInstance();

            if (unaCaja.obtenerLote_Vigente() != null) {
                fechaLote.setTime(unaCaja.obtenerLote_Vigente().getFechaLote());

                if (fechaLote.get(Calendar.DAY_OF_YEAR) == hoy.get(Calendar.DAY_OF_YEAR)) 
                {
                    int res = JOptionPane.showConfirmDialog(this, "La apertura de caja ya ha sido realizada el dia de hoy","Advertencia", JOptionPane.PLAIN_MESSAGE);
                } 
                else 
                {             
                   JDialogAperturaDeCaja apertura = new JDialogAperturaDeCaja(parent,modal,remiseria,utilidades,unaCaja,lbl_saldo,lbl_Efectivo,lbl_totalOUT,lbl_totalIN,null,null,tbl_caja ,dp_desde3,dp_hasta3);
                   apertura.show();
                   cargarTablaCaja(tbl_caja, listarMovimientos());
                   blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),true);
                   blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),true); 
                   btnCerrarCaja.setEnabled(true);
    //               rbnEntrada.setEnabled(true);
    //               rbnSalida.setEnabled(true);
    //               txtImporte.setEnabled(true);
                   btnGuardar1.setEnabled(false);               
                }    
            } 
            else
            {
               JDialogAperturaDeCaja apertura = new JDialogAperturaDeCaja(parent,modal,remiseria,utilidades,unaCaja,lbl_saldo,lbl_Efectivo,lbl_totalOUT,lbl_totalIN,null,null,tbl_caja ,dp_desde3,dp_hasta3);
               apertura.show();
               cargarTablaCaja(tbl_caja, listarMovimientos());
               blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelRendiciones),true);
               blackTabbedPane1.setEnabledAt(blackTabbedPane1.indexOfComponent(panelLiquidaciones),true); 
               btnCerrarCaja.setEnabled(true);
               btnGuardar.setEnabled(true);
               btnGuardar1.setEnabled(false);

            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al Abrir Caja",null,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAbrirCajaActionPerformed

    private void tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla1MouseClicked
        int fila = tabla1.getSelectedRow();
        int numero = (int) tabla1.getValueAt(fila, 0);
        ChoferPorMovil unChoferPorMovil = remiseria.getChoferesPorMoviles().get(numero);
        //cargarTabla2(tabla2, listarViajes(unChoferPorMovil.getUnMovil(),unChoferPorMovil.getUnChofer()));
        cargarTabla2(tabla2, listarViajes(unChoferPorMovil),unChoferPorMovil);
        KilometrosEnServicio kmsEnSer= unChoferPorMovil.getUnMovil().buscarUltimoKilometrajeEnServicio(unChoferPorMovil.getUnMovil());
//        txtKmsInicial1.setText(""+kmsEnSer.getKilometroInicialServicio());
//        txtKmsActual1.setText(""+ kmsEnSer.getKilometroFinalServicio());
//        txtKmsRealizados2.setText(""+kmsEnSer.getTotalKms());
        txtKmsTotal.setText(""+kmsEnSer.getTotalKms());
    }//GEN-LAST:event_tabla1MouseClicked

    private void dp_desdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_desdeActionPerformed

         this.cargarTabla1Bis(tabla1, this.listarChoferPorMovilSegunFecha());
    }//GEN-LAST:event_dp_desdeActionPerformed

    private void dp_hastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_hastaActionPerformed
//        int fila = tabla1.getSelectedRow();
//        int numero = (int) tabla1.getValueAt(fila, 0);
//        if(numero != -1)
//        {
//           // Movil unMovil = remiseria.buscarMovil(numeroMovil);
//            ChoferPorMovil unChoferPorMovil = remiseria.getChoferesPorMoviles().get(numero);
//            if(unChoferPorMovil != null)
//            {
//               // cargarTabla2(tabla2, listarViajes(unMovil, unMovil.getUnChofer()));
//               cargarTabla2(tabla2, listarViajes(unChoferPorMovil));
//            }
//        }
         this.cargarTabla1Bis(tabla1, this.listarChoferPorMovilSegunFecha());
    }//GEN-LAST:event_dp_hastaActionPerformed

    private void btn_hoy4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hoy4ActionPerformed
        hasta0();
        limpiar_tabla(tabla2);
        this.cargarTabla1Bis(tabla1, this.listarChoferPorMovilSegunFecha());      
    }//GEN-LAST:event_btn_hoy4ActionPerformed

    private void btnRendirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRendirActionPerformed
    try {

            this.agregarRendicion();
        
    } catch (JRException ex) {
        Logger.getLogger(JDialogCaja.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnRendirActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void dp_desde1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_desde1ActionPerformed
        // TODO add your handling code here:
        this.cargarTablaRendicionesPagadas2(tablaRendPagos, this.listarViajesRendidos2());
    }//GEN-LAST:event_dp_desde1ActionPerformed

    private void dp_hasta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_hasta1ActionPerformed
        // TODO add your handling code here:
        this.cargarTablaRendicionesPagadas2(tablaRendPagos, this.listarViajesRendidos2());
    }//GEN-LAST:event_dp_hasta1ActionPerformed

    private void btn_hoy5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hoy5ActionPerformed
        // TODO add your handling code here:
        hasta1();
        cargarTablaRendicionesPagadas2(tablaRendPagos,listarViajesRendidos2());
    }//GEN-LAST:event_btn_hoy5ActionPerformed

    private void JTabbedPanelRendicionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTabbedPanelRendicionesMouseClicked
        // TODO add your handling code here:
        hasta();
        hasta1();
        this.cargarTablaRendicionesPagadas2(tablaRendPagos, this.listarViajesRendidos3());
    }//GEN-LAST:event_JTabbedPanelRendicionesMouseClicked

    private void tablaRendPagosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRendPagosMouseClicked
        // TODO add your handling code here:
        try{
            Rendicion unaRendicion = obtenerRendicion();
            this.cargarTablaDetalle(tablaDetalles,this.listarViajesRendidos(unaRendicion));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e,"", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tablaRendPagosMouseClicked

    private void btnNuevosConceptosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevosConceptosActionPerformed
        // TODO add your handling code here:
        JDialogConceptos ventana = new JDialogConceptos(parent,modal,remiseria,unMaestro, unOperario, utilidades , "Entrada", cbxEntrada,null);
        ventana.show();
    }//GEN-LAST:event_btnNuevosConceptosActionPerformed

    private void btnTodoRenPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodoRenPagActionPerformed
        // TODO add your handling code here:    
        this.cargarTablaRendicionesPagadas2(tablaRendPagos, this.listarViajesRendidosTodo());       
    }//GEN-LAST:event_btnTodoRenPagActionPerformed

    private void dp_desdeLiqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_desdeLiqActionPerformed
        if(TablaTipoPersona.getSelectedRow() != -1)
        {               
          cargarTablaLiquidacionSegunPersona(cargarTablaLiquidacionSegunFecha());
        }
    }//GEN-LAST:event_dp_desdeLiqActionPerformed

    private void dp_hastaLiqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_hastaLiqActionPerformed
        if(TablaTipoPersona.getSelectedRow() != -1)
        {               
          cargarTablaLiquidacionSegunPersona(cargarTablaLiquidacionSegunFecha());
        }
    }//GEN-LAST:event_dp_hastaLiqActionPerformed

    private void btn_hoyLiqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hoyLiqActionPerformed
        // TODO add your handling code here:
        if(TablaTipoPersona.getSelectedRow() != -1){        
            hastaLiquidacion();
            cargarTablaLiquidacionSegunPersona(cargarTablaLiquidacionSegunFecha());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No ha selecciona un Tipo de Persona", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_hoyLiqActionPerformed

    private void btnTodoLiqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodoLiqActionPerformed
        // TODO add your handling code here:
        if(TablaTipoPersona.getSelectedRow() != -1)
        {
            Object persona = this.obtenerPersona();
            if(persona instanceof Chofer)
            {
                Chofer unChofer = (Chofer) persona;
                List liq = remiseria.buscarLiquidacionChofer(unChofer);
                cargarTablaLiquidacionSegunPersona(liq);
            }
            else if(persona instanceof Operario)
            {
                Operario unOpera = (Operario) persona;
                List liq =  remiseria.buscarLiquidacionOperario(unOpera);
                cargarTablaLiquidacionSegunPersona(liq);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No ha selecciona un Tipo de Persona", null, WIDTH);
        }        
    }//GEN-LAST:event_btnTodoLiqActionPerformed

    private void TablaTipoPersonaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaTipoPersonaMouseClicked
        // TODO add your handling code here:
        if(TablaTipoPersona.getSelectedRow() != -1)
        {
            Object persona = this.obtenerPersona();
            if(persona instanceof Chofer)
            {
                Chofer unChofer = (Chofer) persona;
                List liq = remiseria.buscarLiquidacionChofer(unChofer);
                cargarTablaLiquidacionSegunPersona(liq);
            }
            else if(persona instanceof Operario)
            {
                Operario unOpera = (Operario) persona;
                List liq =  remiseria.buscarLiquidacionOperario(unOpera);
                cargarTablaLiquidacionSegunPersona(liq);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No ha selecciona un Tipo de Persona", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_TablaTipoPersonaMouseClicked

    private void btnTodoRenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodoRenActionPerformed
        // TODO add your handling code here:
        limpiar_tabla(tabla2);
        this.cargarTabla1Bis(tabla1,listarChoferPorMovilSinFecha());
    }//GEN-LAST:event_btnTodoRenActionPerformed

    private void dp_desdeLiq1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_desdeLiq1ActionPerformed
        cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1));
    }//GEN-LAST:event_dp_desdeLiq1ActionPerformed

    private void dp_hastaLiq1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_hastaLiq1ActionPerformed
        // TODO add your handling code here:
        cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1));
    }//GEN-LAST:event_dp_hastaLiq1ActionPerformed

    private void btn_hoyLiq1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hoyLiq1ActionPerformed
        // TODO add your handling code here:
        this.hastaLiquidacionPagado();
        cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1));
    }//GEN-LAST:event_btn_hoyLiq1ActionPerformed

    private void btnTodoLiq1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodoLiq1ActionPerformed
        // TODO add your handling code here:
        cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasTodo());
    }//GEN-LAST:event_btnTodoLiq1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.agregarLiquidacionPagada();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnNuevosConceptos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevosConceptos1ActionPerformed
        // TODO add your handling code here:
        JDialogConceptos ventana = new JDialogConceptos(parent,modal,remiseria,unMaestro, unOperario, utilidades , "Salida",null,cbxSalida);
        ventana.show();        
    }//GEN-LAST:event_btnNuevosConceptos1ActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        // TODO add your handling code here:
        try
        {
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
            if(checkTodo1.isSelected() ==true)
            {
                double tot = 0.0;
                List rendiciones =listarViajesRendidos2();
                LinkedList <ImprimirRendicionSegunFiltro> lista = new LinkedList<ImprimirRendicionSegunFiltro>();
                Iterator iter = rendiciones.iterator();
                Rendicion aux = null;
                    while (iter.hasNext())
                    {
                        aux = (Rendicion) iter.next();    
                        tot = tot + aux.getTotalRendicion();
                        lista.add(new ImprimirRendicionSegunFiltro(String.valueOf(aux.getIdRendicion()),aux.getUnMovil().toString(),aux.getUnChofer().toString(),utilidades.getFecha(aux.getFechaRendicion()),utilidades.getHora(aux.getFechaRendicion()),String.valueOf(aux.getViajesPagados().size()),"$ "+String.valueOf(aux.getTotalRendicion())));                 
                    } 
              try
                {
                    HashMap<String, Object> parametros = new HashMap();
                    parametros.clear();
                    if(unMaestro != null)
                    {
                            parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                            parametros.put("nombreEmpresa",remiseria.getNombre());
                            parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                            parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                            parametros.put("fechaActual", utilidades.getFechaActual());
                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                            parametros.put("logo", remiseria.getLogo());
                            //parametros.put("total","$ "+ remiseria.totalRendicionSegunFiltro(rendiciones));
                            parametros.put("total","$ "+ String.valueOf(tot));
                            tot = 0.0;
                   }
                    else if(unOperario != null)
                   {
                            parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                            parametros.put("nombreEmpresa",remiseria.getNombre());
                            parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                            parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                            parametros.put("fechaActual", utilidades.getFechaActual());
                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                            parametros.put("logo", remiseria.getLogo());
                            //parametros.put("total","$ "+ remiseria.totalRendicionSegunFiltro(rendiciones));
                            parametros.put("total","$ "+ String.valueOf(tot));
                            tot = 0.0;
                    }  
                    //C:/Users/garba/Desktop/Reportes/
                      //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/RendicionSegunFiltro.jrxml");
                      JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/RendicionSegunFiltro.jrxml");
                      JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JRBeanCollectionDataSource(lista));
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
            else if(checkFiltrar.isSelected()==true)
            {
                double totalRendidoChofer = 0.0;
                Movil unMovil = null;
                Chofer unChofer = null;
                List rendiciones =listarViajesRendidos2();
                LinkedList <ImprimirRendicionSegunFiltro> lista = new LinkedList<ImprimirRendicionSegunFiltro>();
                if(cmbFiltroReporte.getSelectedItem().equals("Chofer"))
                {
                    unChofer =(Chofer) cmbChoferMovil.getSelectedItem();
                    Rendicion aux = null;
                    Iterator iter = rendiciones.iterator();
                    while (iter.hasNext())
                    {
                        aux = (Rendicion) iter.next();    
                        if(aux.getUnChofer().equals(unChofer))
                        {
                            lista.add(new ImprimirRendicionSegunFiltro(String.valueOf(aux.getIdRendicion()),aux.getUnMovil().toString(),aux.getUnChofer().toString(),utilidades.getFecha(aux.getFechaRendicion()),utilidades.getHora(aux.getFechaRendicion()),String.valueOf(aux.getViajesPagados().size()),"$ "+String.valueOf(aux.getTotalRendicion())));
                            totalRendidoChofer =totalRendidoChofer + aux.getTotalRendicion() ;
                        }
                    }
                }
                else
                {
                    unMovil = (Movil) cmbChoferMovil.getSelectedItem();
                    Rendicion aux = null;
                    Iterator iter = rendiciones.iterator();
                    while (iter.hasNext())
                    {
                        aux = (Rendicion) iter.next();    
                        if(aux.getUnMovil().equals(unMovil))
                        {
                            lista.add(new ImprimirRendicionSegunFiltro(String.valueOf(aux.getIdRendicion()),aux.getUnMovil().toString(),aux.getUnChofer().toString(),utilidades.getFecha(aux.getFechaRendicion()),utilidades.getHora(aux.getFechaRendicion()),String.valueOf(aux.getViajesPagados().size()),"$ "+String.valueOf(aux.getTotalRendicion())));
                            totalRendidoChofer =totalRendidoChofer + aux.getTotalRendicion() ;
                        }               
                    }                
                }
                try
                {
                    HashMap<String, Object> parametros = new HashMap();
                    parametros.clear();
                    if(unMaestro != null)
                    {
                            parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                            parametros.put("nombreEmpresa",remiseria.getNombre());
                            parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                            parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                            parametros.put("fechaActual", utilidades.getFechaActual());
                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                            parametros.put("logo", remiseria.getLogo());
                            totalRendidoChofer = Double.parseDouble(formateador.format(totalRendidoChofer));
                            parametros.put("total","$ "+String.valueOf(totalRendidoChofer));
                   }
                    else if(unOperario != null)
                   {
                            parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                            parametros.put("nombreEmpresa",remiseria.getNombre());
                            parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                            parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                            parametros.put("fechaActual", utilidades.getFechaActual());
                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                            parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                            parametros.put("logo", remiseria.getLogo());
                             totalRendidoChofer = Double.parseDouble(formateador.format(totalRendidoChofer));
                           parametros.put("total","$ "+String.valueOf(totalRendidoChofer));
                    }  
                    //C:/Users/garba/Desktop/Reportes/
                     // JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/RendicionSegunFiltro.jrxml");
                      JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/RendicionSegunFiltro.jrxml");
                      JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JRBeanCollectionDataSource(lista));
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
                if(tablaRendPagos.getSelectedRow()!= -1)
                {
                    Rendicion unaRendicion = obtenerRendicion();
                    try{  
                         List historial = this.listarViajesRendidos(unaRendicion);
                         LinkedList <ImprimirViajesRendiciones> lista = new LinkedList<ImprimirViajesRendiciones>(); 
                         Viaje aux = null;
                         Iterator iter = historial.iterator();
                         while (iter.hasNext())
                         {
                               aux = (Viaje) iter.next();                                  
                               lista.add(new ImprimirViajesRendiciones(aux.getUnDomiclio().getUnPais().getNombrePais()+"-"+aux.getUnDomiclio().getUnaProvincia().getNombreProvincia()+"-"+aux.getUnDomiclio().getUnBarrio()+"-"+aux.getUnDomiclio().getUnaDireccionViaje().toString(),"$ "+String.valueOf(aux.getTarifa()),utilidades.getFecha(aux.getFechaHora()),utilidades.getHora(aux.getFechaHora())));
                         }
                         HashMap<String, Object> parametros = new HashMap();
                         parametros.clear();
                         if(unMaestro != null)
                         {
                               parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                               parametros.put("nombreEmpresa",remiseria.getNombre());
                               parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                               parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                               parametros.put("fechaActual", utilidades.getFechaActual());
                               parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                               parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                               parametros.put("logo", remiseria.getLogo());
                               parametros.put("chofer", unaRendicion.getUnChofer());
                               parametros.put("movil", unaRendicion.getUnMovil().getNumeroMovil());
                               parametros.put("total","$ "+String.valueOf(Double.parseDouble(formateador.format((unaRendicion.getTotalRendicion())))));
                               parametros.put("numeroRendicion",String.valueOf(unaRendicion.getIdRendicion()));
                         }
                         else if(unOperario != null)
                         {
                               parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                               parametros.put("nombreEmpresa",remiseria.getNombre());
                               parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                               parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                               parametros.put("fechaActual", utilidades.getFechaActual());
                               parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                               parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                               parametros.put("logo", remiseria.getLogo());
                               parametros.put("chofer", unaRendicion.getUnChofer());
                               parametros.put("movil", unaRendicion.getUnMovil().getNumeroMovil());
                               parametros.put("total","$ "+String.valueOf(Double.parseDouble(formateador.format(unaRendicion.getTotalRendicion()))));
                               parametros.put("numeroRendicion",String.valueOf(unaRendicion.getIdRendicion()));
                         }  
                         //C:/Users/garba/Desktop/Reportes/
                         //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Rendicion.jrxml");
                         JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Rendicion.jrxml");
                         JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JRBeanCollectionDataSource(lista));
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
                    JOptionPane.showMessageDialog(null, "No ha seleccionado una Rendición pagada", null, JOptionPane.ERROR_MESSAGE);
                }
             }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error al realizar la Rendición", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnListarActionPerformed

    private void checkFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFiltrarActionPerformed
        // TODO add your handling code here:
        if(checkFiltrar.isSelected()==true)
        {
             cmbFiltroReporte.setEnabled(true);
             cmbChoferMovil.setEnabled(true);
             this.cargarChoferesCombo();
        }
        else
        {
             cmbFiltroReporte.setEnabled(false);
             cmbChoferMovil.setEnabled(false);
             cmbChoferMovil.removeAllItems();
        }
    }//GEN-LAST:event_checkFiltrarActionPerformed
    public void cargarChoferesCombo()
    {
        cmbChoferMovil.removeAllItems();
        List choferes = remiseria.buscarChoferes(); 
        if(choferes != null)
        {
            Chofer unChofer = null;
            Iterator iter = choferes.iterator();
            while(iter.hasNext())
            {
                if(cmbChoferMovil.isEnabled() == true && checkFiltrar.isSelected() == true)
                {
                    unChofer = (Chofer) iter.next();
                    cmbChoferMovil.addItem(unChofer);
                }
            }
        }
    }
    
    public void cargarChoferesCombo2()
    {
        cmbOperarioChofer.removeAllItems();
        List choferes = remiseria.buscarChoferes(); 
        if(choferes != null)
        {
            Chofer unChofer = null;
            Iterator iter = choferes.iterator();
            while(iter.hasNext())
            {
                if(cmbOperarioChofer.isEnabled() == true && checFiltrarLiq.isSelected() == true)
                {
                    unChofer = (Chofer) iter.next();
                    cmbOperarioChofer.addItem(unChofer);
                }
            }
        }
    }
    
    public void cargarMovilCombo()
    {
        cmbChoferMovil.removeAllItems();
        List choferes = remiseria.buscarMoviles(); 
        if(choferes != null)
        {
            Movil unMovil = null;
            Iterator iter = choferes.iterator();
            while(iter.hasNext())
            {
                unMovil = (Movil) iter.next();
                cmbChoferMovil.addItem(unMovil);

            }
        }
    }    
    private void cmbFiltroReportePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbFiltroReportePropertyChange
        // TODO add your handling code here:
//        if(cmbFiltroReporte.getSelectedItem().equals("Chofer"))
//        {
//            this.cargarChoferesCombo();
//        }
//        else
//        {
//            this.cargarMovilCombo();
//        }           
    }//GEN-LAST:event_cmbFiltroReportePropertyChange

    private void cmbFiltroReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroReporteActionPerformed
        // TODO add your handling code here:
        if(checkFiltrar.isSelected() == true)
        {
            if(cmbFiltroReporte.getSelectedItem().equals("Chofer"))
            {
                this.cargarChoferesCombo();
            }
            else
            {
                this.cargarMovilCombo();
            }
        }
    }//GEN-LAST:event_cmbFiltroReporteActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
       
        Chofer unChofer = null;
        Operario unOpera = null;
        if(checkTodo.isSelected() == true)
        {
            double tot = 0.0;
           HashMap<String, Object> parametros = new HashMap();
           parametros.clear();
           List liq = remiseria.totalLiquidacionTodosSegunFecha(dp_desdeLiq1,dp_hastaLiq1);
           Liquidacion unaLiquidacion = null;
           LinkedList <ImprimirLiquidacionSegunFiltro> lista = new LinkedList<ImprimirLiquidacionSegunFiltro>(); 
           Iterator iter = liq.iterator();
           while (iter.hasNext())
           {
               unaLiquidacion = (Liquidacion) iter.next();
               String numeroLiq = String.valueOf(unaLiquidacion.getIdLiquidacion());
               String tipo = null;
               String numDocumento = null;
               String nombreYapellido = null;
               String totalRendido = null;
               if(unaLiquidacion.getTotalLiquidado()>0.0)
               {
                    if(unaLiquidacion.getUnChofer()!= null)
                    {
                        tipo = "Chofer" ;
                        nombreYapellido = unaLiquidacion.getUnChofer().toString();
                        numDocumento = String.valueOf(unaLiquidacion.getUnChofer().getDni());
                        totalRendido = "$ "+String.valueOf(unaLiquidacion.getUnaRendicion().getTotalRendicion());
                    }
                    else if(unaLiquidacion.getUnOperario()!= null)
                    {
                        tipo = "Operario";
                        nombreYapellido = unaLiquidacion.getUnOperario().toString();
                        numDocumento = String.valueOf(unaLiquidacion.getUnOperario().getDni());
                        totalRendido = "$ "+String.valueOf(unaLiquidacion.getDetalle());
                    }
                    String fechaDePago = utilidades.getFecha(unaLiquidacion.getFechaDePago());

                    String totalLiquidado = "$ "+String.valueOf(unaLiquidacion.getTotalLiquidado());
                    tot = tot  + unaLiquidacion.getTotalLiquidado();
                    lista.add(new ImprimirLiquidacionSegunFiltro(numeroLiq,tipo,numDocumento,nombreYapellido,fechaDePago,totalRendido,totalLiquidado));
               }
            }            
            String total = String.valueOf(tot);//remiseria.totalLiquidacionSegunFiltro(liq);
            try{
                   if(unMaestro != null)
                   {
                         parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                         parametros.put("nombreEmpresa",remiseria.getNombre());
                         parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                         parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                         parametros.put("fechaActual", utilidades.getFechaActual());
                         parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                         parametros.put("logo", remiseria.getLogo());
                         parametros.put("total","$ " + total);
                   }
                   else if(unOperario != null)
                   {
                         parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                         parametros.put("nombreEmpresa",remiseria.getNombre());
                         parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                         parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                         parametros.put("fechaActual", utilidades.getFechaActual());
                         parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                         parametros.put("logo", remiseria.getLogo());
                        parametros.put("total","$ " + total);

                   }  
                   //C:/Users/garba/Desktop/Reportes/
                   //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/LiquidacionFiltro.jrxml");
                   JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/LiquidacionFiltro.jrxml");
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
                   tot = 0.0;
                } 
                catch (JRException ex) 
                {
                     JOptionPane.showMessageDialog(this, ex.getMessage());
                }             
        }
        else if(checFiltrarLiq.isSelected()==true)
        {
            Object persona = (Object) cmbOperarioChofer.getSelectedItem();
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
                 HashMap<String, Object> parametros = new HashMap();
                 parametros.clear();
                 List liq = remiseria.listaLiquidacionePagadasSegunFechaYPersona(dp_desdeLiq1,dp_hastaLiq1,persona);
                 List listaLiquidacion = remiseria.agregarLiqEstatico(liq);
                 String total = remiseria.totalLiquidacionSegunFiltro(liq);
                 try{
                        if(unMaestro != null)
                        {
                              parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                              parametros.put("logo", remiseria.getLogo());
                              parametros.put("total","$ " + total);
                        }
                        else if(unOperario != null)
                        {
                              parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                              parametros.put("logo", remiseria.getLogo());
                             parametros.put("total","$ " + total);

                        }  
                        //C:/Users/garba/Desktop/Reportes/
                        //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/LiquidacionFiltro.jrxml");
                         JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/LiquidacionFiltro.jrxml");
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaLiquidacion));
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
                JOptionPane.showMessageDialog(null, "No existen personas", null, JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            if(TablaLiquidacionesPagadas.getSelectedRow()!= -1)
            {
                try
                {
                int fila = TablaLiquidacionesPagadas.getSelectedRow();
                unChofer = remiseria.buscarChofer((int) TablaLiquidacionesPagadas.getValueAt(fila,2));             
                unOpera = remiseria.buscarOperario((int) TablaLiquidacionesPagadas.getValueAt(fila,2));
                Liquidacion unaLiquidacion = this.obtenerLiquidacion2();
                LinkedList <ImprimirLiquidacion> lista = new LinkedList<ImprimirLiquidacion>(); 
                HashMap<String, Object> parametros = new HashMap();
                parametros.clear();
                String numeroLiq = String.valueOf(unaLiquidacion.getIdLiquidacion());
                String total= "$ "+String.valueOf(unaLiquidacion.getTotalLiquidado());
                String totalRendido = null;
                String porcentaje = null;
                if(unaLiquidacion.getUnOperario()!= null)
                {
                    totalRendido = String.valueOf(unaLiquidacion.getDetalle());
                    porcentaje = "No aplicado";
                }
                
                else if(unaLiquidacion.getUnChofer()!= null)
                {
                    totalRendido = "$ "+String.valueOf(unaLiquidacion.getUnaRendicion().getTotalRendicion());
                    porcentaje= String.valueOf((remiseria.getUnPorcentajeChofer().getPorcentajeChofer()*100) + " %");
                }                
                String fechaLiquidado = utilidades.getFecha(unaLiquidacion.getFechaCreacion());
                lista.add(new ImprimirLiquidacion(numeroLiq,total,totalRendido,porcentaje,fechaLiquidado));

                if(unMaestro != null)
                {
                      parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                      parametros.put("nombreEmpresa",remiseria.getNombre());
                      parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                      parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                      parametros.put("fechaActual", utilidades.getFechaActual());
                      parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                      parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                      parametros.put("logo", remiseria.getLogo());
                      if(unChofer != null)
                      {
                            parametros.put("apellido", unaLiquidacion.getUnChofer().getApellido());
                            parametros.put("nombre", unaLiquidacion.getUnChofer().getNombre());
                            parametros.put("documento",String.valueOf(unaLiquidacion.getUnChofer().getDni()));
                            parametros.put("funcion","Chofer");
                      }
                      else if(unOpera != null)
                      {
                            parametros.put("apellido", unaLiquidacion.getUnOperario().getApellido());
                            parametros.put("nombre", unaLiquidacion.getUnOperario().getNombre());
                            parametros.put("documento",String.valueOf(unaLiquidacion.getUnOperario().getDni()));
                            parametros.put("funcion","Operario");

                      }
                }
                else if(unOperario != null)
                {
                      parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                      parametros.put("nombreEmpresa",remiseria.getNombre());
                      parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                      parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                      parametros.put("fechaActual", utilidades.getFechaActual());
                      parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                      parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                      parametros.put("logo", remiseria.getLogo());
                      if(unChofer != null)
                      {
                            parametros.put("apellido", unaLiquidacion.getUnChofer().getApellido());
                            parametros.put("nombre", unaLiquidacion.getUnChofer().getNombre());
                            parametros.put("documento",String.valueOf(unaLiquidacion.getUnChofer().getDni()));
                            parametros.put("funcion","Chofer");
                      }
                      else if(unOpera != null)
                      {
                            parametros.put("apellido", unaLiquidacion.getUnOperario().getApellido());
                            parametros.put("nombre", unaLiquidacion.getUnOperario().getNombre());
                            parametros.put("documento",String.valueOf(unaLiquidacion.getUnOperario().getDni()));
                            parametros.put("funcion","Operario");

                      }

                }  
                //C:/Users/garba/Desktop/Reportes/
                //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Liquidacion.jrxml");
                JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Liquidacion.jrxml");
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
                JOptionPane.showMessageDialog(null, "No ha seleccionado una Liquidación", null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void checFiltrarLiqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checFiltrarLiqActionPerformed
        // TODO add your handling code here:
        if(checFiltrarLiq.isSelected() == true)
        {
            cmbFiltrarPorChoferOperario.setEnabled(true);
            cmbOperarioChofer.setEnabled(true);
            this.cargarChoferesCombo2();
            checkTodo.setEnabled(false);
        }
        else
        {
            cmbFiltrarPorChoferOperario.setEnabled(false);
            cmbOperarioChofer.setEnabled(false);
            cmbOperarioChofer.removeAllItems();
            checkTodo.setEnabled(true);
        }
    }//GEN-LAST:event_checFiltrarLiqActionPerformed

    public void cargarOperarioCombo()
    {
        cmbOperarioChofer.removeAllItems();
        List operarios = remiseria.buscarOperariosActivos();
        Operario aux = null;
        Iterator iter = operarios.iterator();
        while(iter.hasNext())
        {
            aux = (Operario) iter.next();
            cmbOperarioChofer.addItem(aux);
        }
    }
    private void cmbFiltrarPorChoferOperarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltrarPorChoferOperarioActionPerformed
        // TODO add your handling code here:
        if(checFiltrarLiq.isSelected() == true)
        {
            if(cmbFiltrarPorChoferOperario.getSelectedItem().equals("Chofer"))
            {
                this.cargarChoferesCombo2();
            }
            else
            {
                this.cargarOperarioCombo();
            }
        }
    }//GEN-LAST:event_cmbFiltrarPorChoferOperarioActionPerformed

    private void checkTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTodoActionPerformed
        // TODO add your handling code here:
        if(checkTodo.isSelected() == true)
        {
            checFiltrarLiq.setEnabled(false);
            cmbFiltrarPorChoferOperario.setEnabled(false);
            cmbOperarioChofer.setEnabled(false);
            TablaLiquidacionesPagadas.setRowSelectionAllowed(false);
        }
        else
        {
            checFiltrarLiq.setEnabled(true);
            TablaLiquidacionesPagadas.setRowSelectionAllowed(true);
        }
    }//GEN-LAST:event_checkTodoActionPerformed

    private void checkTodo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTodo1ActionPerformed
        // TODO add your handling code here:
        if(checkTodo1.isSelected() == true)
        {
            checkFiltrar.setSelected(false);
            checkFiltrar.setEnabled(false);
            cmbFiltroReporte.setEnabled(false);
            cmbChoferMovil.setEnabled(false);
        }
        else
        {
            checkFiltrar.setSelected(false);
            checkFiltrar.setEnabled(true);

        }
    }//GEN-LAST:event_checkTodo1ActionPerformed

    private void btnDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetallesActionPerformed
        // TODO add your handling code here:
        try
        {
        JDialogResumenes ventana = new JDialogResumenes (parent,modal,unaCaja,remiseria,unMaestro,unOperario);
        ventana.show();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al abrir la pantalla Detalles");
        }
                
    }//GEN-LAST:event_btnDetallesActionPerformed
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
    private void txtImporteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyTyped
        // TODO add your handling code here:
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }  
        int limite  = 10;
        if(txtImporte.getText().length()== limite)
        {
            evt.consume();
        }          
    }//GEN-LAST:event_txtImporteKeyTyped
public  void cargarTablaPersonasBuscadas(List filtro1, List filtro2, List filtro3) {
        limpiar_tabla(TablaTipoPersona);
        if(filtro1!= null || filtro2 != null|| filtro3 != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = TablaTipoPersona.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente); 
            modelo.addColumn("N° Documento");
            modelo.addColumn("Tipo");
            modelo.addColumn("Nombre y Apellido");
            Chofer aux = null;
            Operario aux2 = null;
            Cargo aux3= null;
            Iterator iter = filtro1.iterator();
            while (iter.hasNext())
            {
                aux = (Chofer) iter.next();
                Object [] fila = new Object[3];
                fila[0] = aux.getDni();
                fila[1] = "Chofer";
                fila[2] = aux.getNombre()+ " "+ aux.getApellido();
                modelo.addRow(fila);
            }

            Iterator iter2 = filtro2.iterator();
            while (iter2.hasNext())
            {
                aux2 = (Operario) iter2.next();
                Object [] fila = new Object[3];
                fila[0] = aux2.getDni();
                fila[1] = "Operario";
                fila[2] = aux2.getNombre()+ " "+ aux2.getApellido();
                modelo.addRow(fila);
            }
            Iterator iter3 = filtro3.iterator();
            while (iter3.hasNext())
            {
                aux3 = (Cargo) iter3.next();
                Object [] fila = new Object[3];
                fila[0] = aux3.getDni();
                fila[1] = aux3.getTipoCargo();
                fila[2] = aux3.getNombre()+ " "+ aux3.getApellido();
                modelo.addRow(fila);
            }
            modelo.rowsRemoved(null);
            TablaTipoPersona.setModel(modelo);
            TablaTipoPersona.getColumn("N° Documento").setMaxWidth(100);
            TablaTipoPersona.getColumn("Tipo").setMaxWidth(70);
            TablaTipoPersona.getColumn("Nombre y Apellido").setMaxWidth(200);
        } 
    }
    
    private void SearchFieldPersonaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_SearchFieldPersonaCaretUpdate
        // TODO add your handling code here:
        if (!SearchFieldPersona.getText().isEmpty()) 
        {
            Chofer aux = null;
            Operario aux2 = null;
            Cargo aux3 = null;
            Collection choferes = remiseria.buscarChoferes(); 
            Collection operarios = remiseria.buscarOperariosActivos();
            Collection cargos = remiseria.buscarCargos();
            List filtro = new LinkedList();
            List filtro2 = new LinkedList();   
            List filtro3 = new LinkedList();   
            Iterator iter1 = choferes.iterator();
            Iterator iter2 = operarios.iterator();
            Iterator iter3 = cargos.iterator();
            while (iter1.hasNext())
            {
                aux = (Chofer) iter1.next();
                if(cmbOpcionBusqueda.getSelectedItem().equals("N° Documento"))
                {
                        if (String.valueOf(aux.getDni()).startsWith(SearchFieldPersona.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaPersonasBuscadas(filtro,filtro2,filtro3);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("Nombre"))
                {
                        if (aux.getNombre().toUpperCase().startsWith(SearchFieldPersona.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                       this.cargarTablaPersonasBuscadas(filtro,filtro2,filtro3);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("Apellido"))
                {
                        if (aux.getApellido().toUpperCase().startsWith(SearchFieldPersona.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                       this.cargarTablaPersonasBuscadas(filtro,filtro2,filtro3);
                }
            } 
            while (iter2.hasNext())
            {
                aux2 = (Operario) iter2.next();
                if(cmbOpcionBusqueda.getSelectedItem().equals("N° Documento"))
                {
                        if (String.valueOf(aux2.getDni()).startsWith(SearchFieldPersona.getText().toUpperCase())) 
                        {
                            filtro2.add(aux2);
                        }
                        this.cargarTablaPersonasBuscadas(filtro, filtro2,filtro3);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("Nombre"))
                {
                        if (aux2.getNombre().toUpperCase().startsWith(SearchFieldPersona.getText().toUpperCase())) 
                        {
                            filtro2.add(aux2);
                        }
                       this.cargarTablaPersonasBuscadas( filtro,filtro2,filtro3);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("Apellido"))
                {
                        if (aux2.getApellido().toUpperCase().startsWith(SearchFieldPersona.getText().toUpperCase())) 
                        {
                            filtro2.add(aux2);
                        }
                       this.cargarTablaPersonasBuscadas( filtro,filtro2,filtro3);
                }

            }
            while (iter3.hasNext())
            {
                aux3 = (Cargo) iter3.next();
                if(cmbOpcionBusqueda.getSelectedItem().equals("N° Documento"))
                {
                        if (String.valueOf(aux3.getDni()).startsWith(SearchFieldPersona.getText().toUpperCase())) 
                        {
                            filtro3.add(aux3);
                        }
                        this.cargarTablaPersonasBuscadas(filtro, filtro2,filtro3);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("Nombre"))
                {
                        if (aux3.getNombre().toUpperCase().startsWith(SearchFieldPersona.getText().toUpperCase())) 
                        {
                            filtro3.add(aux3);
                        }
                       this.cargarTablaPersonasBuscadas( filtro,filtro2,filtro3);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("Apellido"))
                {
                        if (aux3.getApellido().toUpperCase().startsWith(SearchFieldPersona.getText().toUpperCase())) 
                        {
                            filtro3.add(aux3);
                        }
                       this.cargarTablaPersonasBuscadas( filtro,filtro2,filtro3);
                }
            }            
        } 
        else 
        {
            this. cargarTablaPersonas();
        }        
    }//GEN-LAST:event_SearchFieldPersonaCaretUpdate

    private void txtBuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBuscarCaretUpdate
        // TODO add your handling code here:
        if (!txtBuscar.getText().isEmpty()) 
        {
            ChoferPorMovil aux = null;
            Collection rend =  remiseria.getChoferesPorMoviles().values();
            List filtro = new LinkedList();
            Iterator iter = rend.iterator();
            while (iter.hasNext())
            {
                aux = (ChoferPorMovil) iter.next();
                if(cmbFiltro.getSelectedItem().equals("Móvil N°"))
                {
                        if (String.valueOf(aux.getUnMovil().getNumeroMovil()).startsWith(txtBuscar.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaBuscados(tabla1, filtro);
                }
                
                if(cmbFiltro.getSelectedItem().equals("Chofer"))
                {
                        if (String.valueOf(aux.getUnChofer()).startsWith(txtBuscar.getText())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaBuscados(tabla1, filtro);
                }                             
            }
        } 
        else 
        {
            cargarTabla1Bis(tabla1, remiseria.getChoferesPorMoviles().values());
        }                
    }//GEN-LAST:event_txtBuscarCaretUpdate

    private void SearchRendicionCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_SearchRendicionCaretUpdate
        // TODO add your handling code here:
        if (!SearchRendicion.getText().isEmpty()) 
        {
            List rendiciones =listarViajesRendidos2();
            Iterator iter = rendiciones.iterator();
            List filtro = new LinkedList();
            Rendicion aux = null;    
            while (iter.hasNext())
            {
                aux = (Rendicion) iter.next();
                if(cmbOpcionBusquedaRendicion.getSelectedItem().equals("Móvil N°"))
                {
                        if (String.valueOf(aux.getUnMovil().getNumeroMovil()).startsWith(SearchRendicion.getText())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaRendicionesPagadasBuscados(tablaRendPagos,filtro);
                }
                
                if(cmbOpcionBusquedaRendicion.getSelectedItem().equals("Nombre"))
                {
                        if (aux.getUnChofer().getNombre().toUpperCase().startsWith(SearchRendicion.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                       this.cargarTablaRendicionesPagadasBuscados(tablaRendPagos,filtro);
                }
                
                if(cmbOpcionBusquedaRendicion.getSelectedItem().equals("Apellido"))
                {
                        if (aux.getUnChofer().getApellido().toUpperCase().startsWith(SearchRendicion.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                       this.cargarTablaRendicionesPagadasBuscados(tablaRendPagos,filtro);
                }
            }
        }
        else 
        {
            this.cargarTablaRendicionesPagadas2(tablaRendPagos, this.listarViajesRendidos2());    
        }                
    }//GEN-LAST:event_SearchRendicionCaretUpdate

    private void searchLiquidacionCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_searchLiquidacionCaretUpdate
        // TODO add your handling code here:
         if (!searchLiquidacion.getText().isEmpty()) 
        {
            Liquidacion aux = null;
            List liq = remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1);
            List filtro = new LinkedList();
            Iterator iter = liq.iterator();
            while (iter.hasNext())
            {
                
                aux = (Liquidacion) iter.next();
                if(aux.getUnChofer()!= null)
                {
                    if(cmbFlitroLiq.getSelectedItem().equals("N° Documento"))
                    {
                            if (String.valueOf(aux.getUnChofer().getDni()).startsWith(searchLiquidacion.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                           this.cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas,filtro);
                    }

                    if(cmbFlitroLiq.getSelectedItem().equals("Nombre"))
                    {
                            if (aux.getUnChofer().getNombre().toUpperCase().startsWith(searchLiquidacion.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                           this.cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas,filtro);
                    }

                    if(cmbFlitroLiq.getSelectedItem().equals("Apellido"))
                    {
                            if (aux.getUnChofer().getApellido().toUpperCase().startsWith(searchLiquidacion.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                           this.cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas,filtro);
                    }
                }
                else if(aux.getUnOperario()!= null)
                {
                    if(cmbFlitroLiq.getSelectedItem().equals("N° Documento"))
                    {
                            if (String.valueOf(aux.getUnOperario().getDni()).startsWith(searchLiquidacion.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                            this.cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas,filtro);
                    }

                    if(cmbFlitroLiq.getSelectedItem().equals("Nombre"))
                    {
                            if (aux.getUnOperario().getNombre().toUpperCase().startsWith(searchLiquidacion.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                           this.cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas,filtro);
                    }

                    if(cmbFlitroLiq.getSelectedItem().equals("Apellido"))
                    {
                            if (aux.getUnOperario().getApellido().toUpperCase().startsWith(searchLiquidacion.getText().toUpperCase())) 
                            {
                                filtro.add(aux);
                            }
                           this.cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas,filtro);
                    }
                
                }
            } 
        } 
        else 
        {
            cargarTablaLiquidacionPagadas(TablaLiquidacionesPagadas, remiseria.listaLiquidacionePagadasSegunFecha(dp_desdeLiq1,dp_hastaLiq1));
        }               
    }//GEN-LAST:event_searchLiquidacionCaretUpdate

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        // TODO add your handling code here:
        rbnEntrada.setEnabled(true);
        rbnSalida.setEnabled(true);
        cbxEntrada.setEnabled(true);
        cbxSalida.setEnabled(true);
        txtDetalle.setEnabled(true);
        txtImporte.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnCancelarMov.setEnabled(true);  
        btnGuardar1.setEnabled(false);
        rbnEntrada.setSelected(true);
        cbxEntrada.setEnabled(true);
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void btnCancelarMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarMovActionPerformed
        // TODO add your handling code here:
        rbnEntrada.setEnabled(false);
        rbnSalida.setEnabled(false);
        cbxEntrada.setEnabled(false);
        cbxSalida.setEnabled(false);
        txtDetalle.setEnabled(false);
        txtDetalle.setText(null);
        txtImporte.setEnabled(false);
        txtImporte.setText(null);
        btnGuardar.setEnabled(false);
        btnGuardar1.setEnabled(true);
        btnCancelarMov.setEnabled(false);          
    }//GEN-LAST:event_btnCancelarMovActionPerformed

    private void txtDetalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDetalleKeyTyped
        // TODO add your handling code here:
        int limite  = 35;
        if(txtImporte.getText().length()== limite)
        {
            evt.consume();
        }               
    }//GEN-LAST:event_txtDetalleKeyTyped
    
    public  void cargarTablaRendicionesPagadasBuscados(JTable tabla, List<Rendicion> rendiciones) {
        limpiar_tabla(tabla);
        limpiar_tabla(tablaDetalles);
        Collection rend = rendiciones; 
        if(rend != null)
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
            modelo.addColumn("Rendición N°");
            modelo.addColumn("Móvil N°");
            modelo.addColumn("Chofer");
            modelo.addColumn("Total");
            modelo.addColumn("Fecha");
            modelo.addColumn("Hora"); 
            modelo.addColumn("Viajes");
            Rendicion aux = null;
            Iterator iter = rend.iterator();
            while (iter.hasNext())
            {
                    aux = (Rendicion) iter.next();
                    Object [] fila = new Object[7];
                    fila[0] = aux.getIdRendicion();
                    fila[1] = aux.getUnMovil().getNumeroMovil();
                    fila[2] = aux.getUnChofer();
                    fila[3] = "$ "+aux.getTotalRendicion();
                    fila[4] = utilidades.getFecha(aux.getFechaRendicion());
                    fila[5] = utilidades.getHora(aux.getFechaRendicion());
                    fila[6] = aux.getViajesPagados().size();
                    modelo.addRow(fila);
            }
            modelo.rowsRemoved(null);
            tabla.setModel(modelo);
            tabla.getColumn("Rendición N°").setMaxWidth(100);
            tabla.getColumn("Móvil N°").setMaxWidth(60);
            tabla.getColumn("Chofer").setMaxWidth(160);
            tabla.getColumn("Total").setMaxWidth(75);
            tabla.getColumn("Fecha").setMaxWidth(80);
            tabla.getColumn("Hora").setMaxWidth(45);
            tabla.getColumn("Viajes").setMaxWidth(90);
        } 
    }
   
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
            java.util.logging.Logger.getLogger(JDialogCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogCaja dialog = new JDialogCaja(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        }); */
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane JTabbedPanelRendiciones;
    private org.jdesktop.swingx.JXSearchField SearchFieldPersona;
    private org.jdesktop.swingx.JXSearchField SearchRendicion;
    private javax.swing.JTable TablaLiquidacionesPagadas;
    private javax.swing.JTable TablaTipoPersona;
    private org.matrix.BlackTabbedPane blackTabbedPane1;
    private org.matrix.BlackTabbedPaneUI blackTabbedPaneUI1;
    private javax.swing.JButton btnAbrirCaja;
    private javax.swing.JButton btnCancelarMov;
    private javax.swing.JButton btnCerrarCaja;
    private javax.swing.JButton btnDetalles;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnNuevosConceptos;
    private javax.swing.JButton btnNuevosConceptos1;
    private javax.swing.JButton btnReabrirCaja;
    private javax.swing.JButton btnRendir;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTodoLiq;
    private javax.swing.JButton btnTodoLiq1;
    private javax.swing.JButton btnTodoRen;
    private javax.swing.JButton btnTodoRenPag;
    private javax.swing.JButton btn_hoy3;
    private javax.swing.JButton btn_hoy4;
    private javax.swing.JButton btn_hoy5;
    private javax.swing.JButton btn_hoyLiq;
    private javax.swing.JButton btn_hoyLiq1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbxEntrada;
    private javax.swing.JComboBox cbxSalida;
    private javax.swing.JCheckBox checFiltrarLiq;
    private javax.swing.JCheckBox checkFiltrar;
    private javax.swing.JCheckBox checkTodo;
    private javax.swing.JCheckBox checkTodo1;
    private javax.swing.JComboBox cmbChoferMovil;
    private javax.swing.JComboBox cmbFiltrarPorChoferOperario;
    private javax.swing.JComboBox cmbFiltro;
    private javax.swing.JComboBox cmbFiltroReporte;
    private javax.swing.JComboBox cmbFlitroLiq;
    private javax.swing.JComboBox cmbOpcionBusqueda;
    private javax.swing.JComboBox cmbOpcionBusquedaRendicion;
    private javax.swing.JComboBox cmbOperarioChofer;
    private org.jdesktop.swingx.JXDatePicker dp_desde;
    private org.jdesktop.swingx.JXDatePicker dp_desde1;
    private org.jdesktop.swingx.JXDatePicker dp_desde3;
    private org.jdesktop.swingx.JXDatePicker dp_desdeLiq;
    private org.jdesktop.swingx.JXDatePicker dp_desdeLiq1;
    private org.jdesktop.swingx.JXDatePicker dp_hasta;
    private org.jdesktop.swingx.JXDatePicker dp_hasta1;
    private org.jdesktop.swingx.JXDatePicker dp_hasta3;
    private org.jdesktop.swingx.JXDatePicker dp_hastaLiq;
    private org.jdesktop.swingx.JXDatePicker dp_hastaLiq1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer10;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer4;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer5;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer6;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer7;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer8;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer9;
    private javax.swing.JLabel lbl_Efectivo;
    private javax.swing.JLabel lbl_saldo;
    private javax.swing.JLabel lbl_totalIN;
    private javax.swing.JLabel lbl_totalOUT;
    private javax.swing.JPanel panelLiquidaciones;
    private javax.swing.JPanel panelRendiciones;
    private javax.swing.JRadioButton rbnEntrada;
    private javax.swing.JRadioButton rbnSalida;
    private org.jdesktop.swingx.JXSearchField searchLiquidacion;
    private javax.swing.JTable tabla1;
    private javax.swing.JTable tabla2;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaLiquidacionImpaga;
    private javax.swing.JTable tablaRendPagos;
    private javax.swing.JTable tbl_caja;
    private org.jdesktop.swingx.JXSearchField txtBuscar;
    private javax.swing.JTextField txtDetalle;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtKmsTotal;
    private javax.swing.JTextField txtTotalRendir;
    // End of variables declaration//GEN-END:variables
}
