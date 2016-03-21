


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Clases.Color;
import Clases.ImprimirMóviles;
import Clases.ImprimirViajesRendiciones;
import Clases.Maestro;
import Clases.Marca;
import Clases.Modelo;
import Clases.Motor;
import java.util.LinkedList;
import javax.swing.JTable;
import Clases.Remiseria;
import Clases.Movil;
import Clases.Operario;
import Clases.Rodado;
import Clases.TipoUtilidad;
import Clases.Utilidad;
import java.awt.AWTException;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
public class JDialogMovil extends javax.swing.JDialog {
    private Remiseria remiseria;
    private Utilidad utilidades;
    private java.awt.Frame parent;
    private boolean modal;
    private LayoutManager layout;
    private Maestro unMaestro;
    private Operario unOperario;
    /**
     * Creates new form JDialogMovil
     */
    public JDialogMovil(java.awt.Frame parent, boolean modal,Remiseria remiseria,Utilidad utilidades, Maestro unMaestro, Operario unOperario){
        super(parent, modal);
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unMaestro = unMaestro;
        this.unOperario = unOperario;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        this.cargarMarcasCombo();
        this.cargarColoresCombo();
        this.cargarMotoresCombo();
        this.cargarTiposUtilidadesCombo();
        this.cargarRodadosCombo();
        chekAlquilar.setSelected(false);
        jXTaskPane1.setEnabled(false);
        txtNumeroMovil.setEnabled(false);
        txtLetras.setEnabled(false);
        txtNumeros.setEnabled(false);
        chooserAño.setEnabled(false);
        txtKilometros.setEnabled(false);
        txtKilometros.setEnabled(false);
        txtCapacidadCarga.setEnabled(false);
        cmbMarcas.setEnabled(false);
        cmbModelo.setEnabled(false);
        cmbColor.setEnabled(false);
        cmbMotor.setEnabled(false);
        cmbUtilidad.setEnabled(false);
        cmbTipoDeRodado.setEnabled(false);
        cmbAireAcondicionado.setEnabled(false); 
        btnMarca.setEnabled(false); 
        btnModelo.setEnabled(false); 
        btnColor.setEnabled(false); 
        btnMotor.setEnabled(false); 
        btnUtilidad.setEnabled(false); 
        btnTipoRodado.setEnabled(false); 
        btnGuardarModificaciones.setEnabled(false);
        jButton1.setEnabled(false);
        try{
            tablaMoviles.setRowSelectionInterval(0, 0);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No hay Móviles registrados aún",null, JOptionPane.WARNING_MESSAGE);
        }
               
        
        jXTaskPane2.setCollapsed(true);
    }   
    
    public void cargarModelosCombo(Marca unaMarca){
        cmbModelo.removeAllItems();
        List modelos = unaMarca.buscarModelos();
        if(modelos != null)
        {
            Modelo aux = null;
            Iterator iter = modelos.iterator();
            while (iter.hasNext())
            {
                aux = (Modelo) iter.next();
                cmbModelo.addItem(aux.getNombreModelo());
            }
        }
     }
    
    
     public void cargarMarcasCombo(){
        cmbMarcas.removeAllItems();
        List marcas = remiseria.buscarMarcas();
        if(marcas != null)
        {
            Marca aux = null;
            Iterator iter = marcas.iterator();
            while (iter.hasNext()){
                aux = (Marca) iter.next();
                cmbMarcas.addItem(aux.getNombreMarca());
            }
        }
    }
     
        public void cargarColoresCombo(){
        cmbColor.removeAllItems();
        List colores = remiseria.buscarColores();
        if(colores != null)
        {
            Color aux = null;
            Iterator iter = colores.iterator();
            while (iter.hasNext()){
                aux = (Color) iter.next();
                cmbColor.addItem(aux.getNombreColor());
            }
        }
        
    }
          
     public void cargarMotoresCombo(){
        cmbMotor.removeAllItems();
        List motores = remiseria.buscarMotores();
        if(motores != null)
        {
            Motor aux = null;
            Iterator iter = motores.iterator();
            while (iter.hasNext())
            {
                aux = (Motor) iter.next();
                cmbMotor.addItem(aux.getNombreMotor());
            }
        }

    }
          
    public void cargarTiposUtilidadesCombo(){
        cmbUtilidad.removeAllItems();
        List TiposUtilidades = remiseria.buscarTiposUtilidades();
        if(TiposUtilidades != null)
        {
            TipoUtilidad aux = null;
            Iterator iter = TiposUtilidades.iterator();
            while (iter.hasNext())
            {
                aux = (TipoUtilidad) iter.next();
                cmbUtilidad.addItem(aux.getNombreTipoUtilidad());
            }
        }

    }
     
   public void cargarRodadosCombo(){
        cmbTipoDeRodado.removeAllItems();
        List rodados = remiseria.buscarRodados();
        if(rodados != null)
        {
            Rodado aux = null;
            Iterator iter = rodados.iterator();
            while (iter.hasNext()){
                aux = (Rodado) iter.next();
                cmbTipoDeRodado.addItem(aux.getNombreRodado());
            }
        }
    }
   
    public void cargarTabla(){
        List moviles = remiseria.buscarMoviles(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        
        JTableHeader th; 
        th = tablaMoviles.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);     
        modelo.addColumn("N° Móvil");
        modelo.addColumn("Patente");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Tipo de Rodado"); 
        modelo.addColumn("Color");   
        modelo.addColumn("Utilidad");
        modelo.addColumn("Alquilado");
        Movil aux = null;

        Iterator iter = moviles.iterator();
        while (iter.hasNext()){
            Object [] fila = new Object[8];
            aux = (Movil) iter.next();
            fila[0] = aux.getNumeroMovil();
            //PATENTE
            fila[1] = (aux.getPatente());
            fila[2] =(aux.getUnaMarca().getNombreMarca());
            //aux.getUnaMarca().toString();
            fila[3] = aux.getUnModelo().getNombreModelo();
            fila[4] = aux.getUnRodado().getNombreRodado();
            fila[5] = aux.getUnColor().getNombreColor();
            fila[6] = aux.getUnTipoUtilidad().getNombreTipoUtilidad();
            if(aux.isAlquilado()==false)
            {
                 fila[7] = "No";
            }
            else
            {
                 fila[7] = "Si";
            }            
            modelo.addRow(fila);
            }
        modelo.rowsRemoved(null);
        tablaMoviles.setModel(modelo);
    }
    
    public void eliminarMovil(){
            int eleccion = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el Móvil seleccionado?");
            if ( eleccion == 0)
            {
                int numeroMovil = (int) tablaMoviles.getValueAt(tablaMoviles.getSelectedRow(), 0);
                remiseria.eliminarMovil(numeroMovil);
                JOptionPane.showMessageDialog(null,"El Móvil a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);
                if(remiseria.getUnaVentana()!= null)
                {
                    remiseria.getUnaVentana().cargarDisponibles();
                    remiseria.getUnaVentana().cargarTransito();  
                    remiseria.getUnaVentana().cargarSinServicio();  
                    remiseria.getUnaVentana().cargarDescanso(); 
                }
                this.cargarTabla();
               
            }
    }
    public void obtenerMovil(){
        int fila = tablaMoviles.getSelectedRow();
        int numeroMovil = (int) tablaMoviles.getValueAt(fila, 0);
        Movil unMovil = remiseria.buscarMovil(numeroMovil);
        //return unMovil;}
        txtNumeroMovil.setText("" + unMovil.getNumeroMovil());
        cmbMarcas.setSelectedItem(unMovil.getUnaMarca().getNombreMarca());
        cmbModelo.setSelectedItem(unMovil.getUnModelo().getNombreModelo());
        cmbMotor.setSelectedItem(unMovil.getUnMotor().getNombreMotor());
        cmbColor.setSelectedItem(unMovil.getUnColor().getNombreColor());
        cmbUtilidad.setSelectedItem(unMovil.getUnTipoUtilidad().getNombreTipoUtilidad());
        cmbTipoDeRodado.setSelectedItem(unMovil.getUnRodado().getNombreRodado());
        txtLetras.setText(unMovil.getPatente().toString().substring(0, 3)) ;
        txtNumeros.setText((unMovil.getPatente().toString().substring(4, 7)));
        chooserAño.setYear(unMovil.getAnio());
        txtKilometros.setText("" +unMovil.getKilometraje());
        txtCapacidadCarga.setText(""+unMovil.getKilometraje());
        if(unMovil.isAlquilado()==true)
        {
            chekAlquilar.setSelected(true);
        }
        else
        {
            chekAlquilar.setSelected(false);
        }
    }
    
     public static void limpiar_tabla(JTable tabla) {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }
    
    public static void cargarTablaMovilesBuscados(JTable tabla, List lista) {
        limpiar_tabla(tabla);
        
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 11); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);           
        modelo.addColumn("N° Móvil");
        modelo.addColumn("Patente");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Color");   
        modelo.addColumn("Utilidad");
        Movil aux = null;
        Iterator iter = lista.iterator();
        while (iter.hasNext()){
            Object [] fila = new Object[7];
            aux = (Movil) iter.next();
            fila[0] = aux.getNumeroMovil();
            //PATENTE
            fila[1] = (aux.getPatente());
            fila[2] =(aux.getUnaMarca().getNombreMarca());
            //aux.getUnaMarca().toString();
            fila[3] = aux.getUnModelo().getNombreModelo();
            fila[4] = aux.getUnRodado().getNombreRodado();
            fila[5] = aux.getUnColor().getNombreColor();
            fila[6] = aux.getUnTipoUtilidad().getNombreTipoUtilidad();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tabla.setModel(modelo);
    }

    public void agregarMovil()
    {
            boolean ok = true;
            int nroMovil = -1;
            int kms = -1;
            double capacidad = -1;
            String numeroMovil = (txtNumeroMovil.getText());
            String patente = txtLetras.getText().toUpperCase()+ "-" + txtNumeros.getText();
            Marca unaMarca = null;
            Modelo unModelo = null;
            Color unColor = null;
            Motor unMotor = null;
            TipoUtilidad unTipoUtilidad = null;
            Rodado unRodado = null;

            int anio = (int) chooserAño.getValue();
            int anioActual = Integer.parseInt(String.valueOf(utilidades.getFechaHoraActual().getYear()));

            String kilometraje = (txtKilometros.getText().toString());
            String capacidadCarga = (txtCapacidadCarga.getText().toString());
            String aireAcondicionado = cmbAireAcondicionado.getSelectedItem().toString();

            if(utilidades.isNumber(numeroMovil)==true)
            {
                nroMovil = Integer.parseInt(numeroMovil);
            }else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado correctamente el Número del móvil, ingrese nuevamente"," ",JOptionPane.ERROR_MESSAGE);
                txtNumeroMovil.grabFocus();
                txtNumeroMovil.setBackground(java.awt.Color.red);
                ok = false;
            }   

            if(patente.length()<7)
            {
                JOptionPane.showMessageDialog(null,"El número de la Patente no es correcto"," ",JOptionPane.ERROR_MESSAGE);
                txtLetras.grabFocus();
                txtNumeros.setBackground(java.awt.Color.red);
                txtLetras.setBackground(java.awt.Color.red);
                ok = false;
            }                                                     

            if(cmbMarcas.getSelectedItem() != null)
            {
               unaMarca = remiseria.buscarMarca(cmbMarcas.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado una Marca"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;                     
            }

            if(cmbModelo.getSelectedItem() != null)
            {
                unModelo = unaMarca.buscarModelo(cmbModelo.getSelectedItem().toString());
            } 
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Modelo"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;    
            }

            if(cmbColor.getSelectedItem() != null)
            {
                unColor = remiseria.buscarColor(cmbColor.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Color"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;  
            }

            if(cmbMotor.getSelectedItem() != null)
            {
                unMotor = remiseria.buscarMotor(cmbMotor.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Motor"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;                     
            }

            if(cmbUtilidad.getSelectedItem() != null)
            {
                unTipoUtilidad = remiseria.buscarTipoUtilidad( cmbUtilidad.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Tipo de Utilidad"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;                    
            }


            if(cmbTipoDeRodado.getSelectedItem() != null)
            {
                unRodado = remiseria.buscarRodado(cmbTipoDeRodado.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Rodado"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;   
            }
//            if(String.valueOf(anio).length() < 5 )
//            {
//                JOptionPane.showMessageDialog(null,"Ha ingresado incorrectamente el año"," ",JOptionPane.ERROR_MESSAGE);
//                ok = false;    
//            }

            if(anio <= anioActual )
            {
                JOptionPane.showMessageDialog(null,"Ha ingresado un Año mayor al actual"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;    
            }                     

            if(utilidades.isNumber(kilometraje)==true)
            {
                kms = Integer.parseInt(kilometraje);
            }else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado correctamente el Kilometraje"," ",JOptionPane.ERROR_MESSAGE);
                txtKilometros.grabFocus();
                txtKilometros.setBackground(java.awt.Color.red);
                ok = false;
            }                    

            if(utilidades.isNumber(capacidadCarga)==true)
            {
                capacidad = Double.parseDouble(capacidadCarga);
            }else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado correctamente la Capacidad de Carga"," ",JOptionPane.ERROR_MESSAGE);
                txtCapacidadCarga.grabFocus();
                txtCapacidadCarga.setBackground(java.awt.Color.red);
                ok = false;
            }  
            boolean alquilar = false;
            if(chekAlquilar.isSelected()==true)
            {
                alquilar = true;
            }
                    
      if(ok == true)
      {     
          if((nroMovil != -1) && (kms != 1))
          {          
            if (remiseria.nroMovilLibre(nroMovil) == false){
                if(remiseria.buscarMovilPatente(patente) == null){
                    remiseria.agregarMovil(nroMovil, patente, unaMarca,unModelo,  anio, unColor, null, unTipoUtilidad, unRodado, kms, aireAcondicionado, capacidad, unMotor,alquilar);
                    this.cargarTabla();
                    limpiar();
                    jXTaskPane1.setCollapsed(true);
                    btnModificar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    jXTaskPane1.setEnabled(false);
                    tablaMoviles.setEnabled(true);
                    tablaMoviles.setRowSelectionInterval(0, 0);
                    txtNumeroMovil.setEnabled(false);
                    txtLetras.setEnabled(false);
                    txtNumeros.setEnabled(false);
                    chooserAño.setEnabled(false);
                    txtKilometros.setEnabled(false);
                    txtKilometros.setEnabled(false);
                    txtCapacidadCarga.setEnabled(false);
                    cmbMarcas.setEnabled(false);
                    cmbModelo.setEnabled(false);
                    cmbColor.setEnabled(false);
                    cmbMotor.setEnabled(false);
                    cmbUtilidad.setEnabled(false);
                    cmbTipoDeRodado.setEnabled(false);
                    cmbAireAcondicionado.setEnabled(false);    
                    btnMarca.setEnabled(false); 
                    btnModelo.setEnabled(false); 
                    btnColor.setEnabled(false); 
                    btnMotor.setEnabled(false); 
                    btnUtilidad.setEnabled(false); 
                    btnTipoRodado.setEnabled(false);   
                    jButton1.setEnabled(false);
                    btnGuardarModificaciones.setEnabled(false);    
                    btnBuscarMóvil.setEnabled(true);
                    JOptionPane.showMessageDialog(null,"Se registró un nuevo Móvil"," ",JOptionPane.INFORMATION_MESSAGE);
                }else
                {
                     JOptionPane.showMessageDialog(null,"El Movil con la Patente: " + patente + " ya existe"," ",JOptionPane.ERROR_MESSAGE);
                     txtNumeros.setText(null);
                     txtLetras.setText(null);
                     txtNumeros.setBackground(java.awt.Color.red);
                     txtLetras.setBackground(java.awt.Color.red);                             
                }
            }
            else
                {
                    JOptionPane.showMessageDialog(null,"El Móvil con el N° "+ numeroMovil + " ya existe "," ",JOptionPane.ERROR_MESSAGE);
                    txtNumeroMovil.setText(null);
                    txtNumeroMovil.setBackground(java.awt.Color.red);
                }
            
          }
          
      }
       
      
    }
 
    public void modificarMovil()
    {
        try
        {
            int fila = tablaMoviles.getSelectedRow();
            int numeroMov = (int) tablaMoviles.getValueAt(fila, 0);
            Movil unMov = remiseria.buscarMovil(numeroMov);
            boolean ok = true;
            int nroMovil = -1;
            int kms = -1;
            double capacidad = -1;
            String numeroMovil = (txtNumeroMovil.getText());
            String patente = txtLetras.getText().toUpperCase()+ "-" + txtNumeros.getText();
            Marca unaMarca = null;
            Modelo unModelo = null;
            Color unColor = null;
            Motor unMotor = null;
            TipoUtilidad unTipoUtilidad = null;
            Rodado unRodado = null;

            int anio = (int) chooserAño.getValue();
            int anioActual = Integer.parseInt(String.valueOf(utilidades.getFechaHoraActual().getYear()));

            String kilometraje = (txtKilometros.getText().toString());
            String capacidadCarga = (txtCapacidadCarga.getText().toString());
            String aireAcondicionado = cmbAireAcondicionado.getSelectedItem().toString();

            if(utilidades.isNumber(numeroMovil)==true)
            {
                nroMovil = Integer.parseInt(numeroMovil);
            }else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado correctamente el Número del móvil, ingrese nuevamente"," ",JOptionPane.ERROR_MESSAGE);
                txtNumeroMovil.grabFocus();
                txtNumeroMovil.setBackground(java.awt.Color.red);
                ok = false;
            }   

            if(patente.length()<7)
            {
                JOptionPane.showMessageDialog(null,"El número de la Patente no es correcto"," ",JOptionPane.ERROR_MESSAGE);
                txtLetras.grabFocus();
                txtNumeros.setBackground(java.awt.Color.red);
                txtLetras.setBackground(java.awt.Color.red);
                ok = false;
            }                                                     

            if(cmbMarcas.getSelectedItem() != null)
            {
               unaMarca = remiseria.buscarMarca(cmbMarcas.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado una Marca"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;                     
            }

            if(cmbModelo.getSelectedItem() != null)
            {
                unModelo = unaMarca.buscarModelo(cmbModelo.getSelectedItem().toString());
            } 
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Modelo"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;    
            }

            if(cmbColor.getSelectedItem() != null)
            {
                unColor = remiseria.buscarColor(cmbColor.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Color"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;  
            }

            if(cmbMotor.getSelectedItem() != null)
            {
                unMotor = remiseria.buscarMotor(cmbMotor.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Motor"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;                     
            }

            if(cmbUtilidad.getSelectedItem() != null)
            {
                unTipoUtilidad = remiseria.buscarTipoUtilidad( cmbUtilidad.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Tipo de Utilidad"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;                    
            }


            if(cmbTipoDeRodado.getSelectedItem() != null)
            {
                unRodado = remiseria.buscarRodado(cmbTipoDeRodado.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado un Rodado"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;   
            }      
//            if(String.valueOf(anio).length() < 5 )
//            {
//                JOptionPane.showMessageDialog(null,"Ha ingresado incorrectamente el año"," ",JOptionPane.ERROR_MESSAGE);
//                ok = false;    
//            }
            if(anio <= anioActual )
            {
                JOptionPane.showMessageDialog(null,"Ha ingresado un Año mayor al actual"," ",JOptionPane.ERROR_MESSAGE);
                ok = false;    
            }                     

            if(utilidades.isNumber(kilometraje)==true)
            {
                kms = Integer.parseInt(kilometraje);
            }else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado correctamente el Kilometraje"," ",JOptionPane.ERROR_MESSAGE);
                txtKilometros.grabFocus();
                txtKilometros.setBackground(java.awt.Color.red);
                ok = false;
            }                    

            if(utilidades.isNumber(capacidadCarga)==true)
            {
                capacidad = Double.parseDouble(capacidadCarga);
            }else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado correctamente la Capacidad de Carga"," ",JOptionPane.ERROR_MESSAGE);
                txtCapacidadCarga.grabFocus();
                txtCapacidadCarga.setBackground(java.awt.Color.red);
                ok = false;
            }    

            if(utilidades.isNumber(capacidadCarga)==true)
            {
                capacidad = Double.parseDouble(capacidadCarga);
            }else
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado correctamente la Capacidad de Carga"," ",JOptionPane.ERROR_MESSAGE);
                txtCapacidadCarga.grabFocus();
                txtCapacidadCarga.setBackground(java.awt.Color.red);
                ok = false;
            }

                boolean alquilar = false;
                if(chekAlquilar.isSelected()==true)
                {
                    alquilar = true;
                }        
            if(ok == true)
            {
                if((nroMovil != -1) && (kms != 1))
                {   
                    boolean valor = remiseria.modificarMovil(unMov, nroMovil, patente, unaMarca, unModelo, anio, unColor, null, unTipoUtilidad, unRodado, kms, aireAcondicionado, capacidad, unMotor,alquilar);
                    this.cargarTabla();
                    if(remiseria.getUnaVentana()!= null)
                    {
                        remiseria.getUnaVentana().cargarSinServicio();
                    }
                    jXTaskPane1.setCollapsed(true);
                    btnModificar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    jXTaskPane1.setEnabled(false);
                    tablaMoviles.setEnabled(true);
                    tablaMoviles.setRowSelectionInterval(0, 0);
                    txtNumeroMovil.setEnabled(false);
                    txtLetras.setEnabled(false);
                    txtNumeros.setEnabled(false);
                    chooserAño.setEnabled(false);
                    txtKilometros.setEnabled(false);
                    txtKilometros.setEnabled(false);
                    txtCapacidadCarga.setEnabled(false);
                    cmbMarcas.setEnabled(false);
                    cmbModelo.setEnabled(false);
                    cmbColor.setEnabled(false);
                    cmbMotor.setEnabled(false);
                    cmbUtilidad.setEnabled(false);
                    cmbTipoDeRodado.setEnabled(false);
                    cmbAireAcondicionado.setEnabled(false);    
                    btnMarca.setEnabled(false); 
                    btnModelo.setEnabled(false); 
                    btnColor.setEnabled(false); 
                    btnMotor.setEnabled(false); 
                    btnUtilidad.setEnabled(false); 
                    btnTipoRodado.setEnabled(false);                    
                    jXTaskPane1.setCollapsed(true);
                    btnModificar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    jXTaskPane1.setEnabled(false);
                    tablaMoviles.setEnabled(true);
                    tablaMoviles.setRowSelectionInterval(0, 0);
                    txtNumeroMovil.setEnabled(false);
                    txtLetras.setEnabled(false);
                    txtNumeros.setEnabled(false);
                    chooserAño.setEnabled(false);
                    txtKilometros.setEnabled(false);
                    txtKilometros.setEnabled(false);
                    txtCapacidadCarga.setEnabled(false);
                    cmbMarcas.setEnabled(false);
                    cmbModelo.setEnabled(false);
                    cmbColor.setEnabled(false);
                    cmbMotor.setEnabled(false);
                    cmbUtilidad.setEnabled(false);
                    cmbTipoDeRodado.setEnabled(false);
                    cmbAireAcondicionado.setEnabled(false);    
                    btnMarca.setEnabled(false); 
                    btnModelo.setEnabled(false); 
                    btnColor.setEnabled(false); 
                    btnMotor.setEnabled(false); 
                    btnUtilidad.setEnabled(false); 
                    btnTipoRodado.setEnabled(false); 
                    jButton1.setEnabled(false);
                    btnGuardarModificaciones.setEnabled(false);
                    btnAgregar.setEnabled(true);
                    btnBuscarMóvil.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Se ha modificado con éxito el Móvil seleccionado", null, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Debe tener abierto la ventana viajes para aplicar los cambios");
        }

    }
    
    public void limpiar ()
    {
            txtNumeroMovil.setText("");
            txtNumeroMovil.setBackground(java.awt.Color.white);
            this.cargarMarcasCombo();
            this.cargarColoresCombo();
            this.cargarMotoresCombo();
            this.cargarTiposUtilidadesCombo();
            this.cargarRodadosCombo();
            this.cmbModelo.removeAllItems();
            txtLetras.setBackground(java.awt.Color.white);
            txtNumeros.setBackground(java.awt.Color.white);
            txtLetras.setText("") ;
            txtNumeros.setText("");
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            chooserAño.setYear(año);
            txtKilometros.setText("");
            txtKilometros.setBackground(java.awt.Color.white);
            txtCapacidadCarga.setText(""); 
            txtCapacidadCarga.setBackground(java.awt.Color.white);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscarMóvil = new javax.swing.JButton();
        btnBuscarMóvil1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnMarca = new javax.swing.JButton();
        cmbMarcas = new javax.swing.JComboBox();
        cmbModelo = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        cmbColor = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        cmbMotor = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        cmbUtilidad = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        cmbTipoDeRodado = new javax.swing.JComboBox();
        txtKilometros = new javax.swing.JTextField();
        txtCapacidadCarga = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        chooserAño = new com.toedter.calendar.JYearChooser();
        cmbAireAcondicionado = new javax.swing.JComboBox();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        btnColor = new javax.swing.JButton();
        btnMotor = new javax.swing.JButton();
        btnUtilidad = new javax.swing.JButton();
        btnTipoRodado = new javax.swing.JButton();
        btnModelo = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        txtNumeroMovil = new javax.swing.JTextField();
        txtLetras = new javax.swing.JTextField();
        txtNumeros = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        chekAlquilar = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnGuardarModificaciones = new javax.swing.JButton();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPaneContainer2 = new org.jdesktop.swingx.JXTaskPaneContainer();
        SearchBuscarMovil = new javax.swing.JTextField();
        cmbOpcionBusqueda = new javax.swing.JComboBox();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMoviles = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnAgregar.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(255, 255, 255));
        btnModificar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificar.setText("Modificar ");
        btnModificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnBuscarMóvil.setBackground(new java.awt.Color(255, 255, 255));
        btnBuscarMóvil.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnBuscarMóvil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search.png"))); // NOI18N
        btnBuscarMóvil.setText("Buscar");
        btnBuscarMóvil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBuscarMóvil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMóvilActionPerformed(evt);
            }
        });

        btnBuscarMóvil1.setBackground(new java.awt.Color(255, 255, 255));
        btnBuscarMóvil1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnBuscarMóvil1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER 1.png"))); // NOI18N
        btnBuscarMóvil1.setText("Listar");
        btnBuscarMóvil1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBuscarMóvil1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMóvil1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscarMóvil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarMóvil, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel1.setText("Gestor de Móviles");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jXTaskPane1.setCollapsed(true);
        jXTaskPane1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document-new.png"))); // NOI18N
        jXTaskPane1.setTitle("Datos del Móvil");
        jXTaskPane1.setEnabled(false);

        jXTaskPaneContainer1.setRequestFocusEnabled(false);

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel7.setText("Patente:");

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel8.setText("Marca:");

        btnMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcaActionPerformed(evt);
            }
        });

        cmbMarcas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbMarcas.setNextFocusableComponent(cmbModelo);
        cmbMarcas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMarcasActionPerformed(evt);
            }
        });

        cmbModelo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbModelo.setToolTipText("");
        cmbModelo.setNextFocusableComponent(cmbColor);
        cmbModelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbModeloKeyPressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel22.setText("Modelo:");

        cmbColor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbColor.setToolTipText("");
        cmbColor.setNextFocusableComponent(cmbMotor);
        cmbColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbColorKeyPressed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel28.setText("Color:");

        cmbMotor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbMotor.setToolTipText("");
        cmbMotor.setName(""); // NOI18N
        cmbMotor.setNextFocusableComponent(cmbUtilidad);
        cmbMotor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMotorKeyPressed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel29.setText("Motor:");

        jLabel30.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel30.setText("Utilidad:");

        cmbUtilidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbUtilidad.setToolTipText("");
        cmbUtilidad.setNextFocusableComponent(cmbTipoDeRodado);
        cmbUtilidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbUtilidadKeyPressed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel31.setText("Tipo de rodado:");

        cmbTipoDeRodado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbTipoDeRodado.setToolTipText("");
        cmbTipoDeRodado.setNextFocusableComponent(chooserAño);

        txtKilometros.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtKilometros.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtKilometros.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtKilometros.setNextFocusableComponent(txtCapacidadCarga);
        txtKilometros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtKilometrosMouseClicked(evt);
            }
        });
        txtKilometros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKilometrosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKilometrosKeyTyped(evt);
            }
        });

        txtCapacidadCarga.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCapacidadCarga.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCapacidadCarga.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCapacidadCarga.setNextFocusableComponent(cmbAireAcondicionado);
        txtCapacidadCarga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCapacidadCargaMouseClicked(evt);
            }
        });
        txtCapacidadCarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCapacidadCargaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCapacidadCargaKeyTyped(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel32.setText("Kilometros:");

        jLabel33.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel33.setText("Año:");

        chooserAño.setEndYear(9999);
        chooserAño.setNextFocusableComponent(txtKilometros);
        chooserAño.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chooserAñoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                chooserAñoKeyTyped(evt);
            }
        });

        cmbAireAcondicionado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbAireAcondicionado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));
        cmbAireAcondicionado.setLightWeightPopupEnabled(false);

        jLabel34.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel34.setText("kg");

        jLabel35.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel35.setText("Capacidad de carga:");

        jLabel36.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel36.setText("Aire acondicionado:");

        btnColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColorbtnMarcaActionPerformed(evt);
            }
        });

        btnMotor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotorbtnMarcaActionPerformed(evt);
            }
        });

        btnUtilidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnUtilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUtilidadbtnMarcaActionPerformed(evt);
            }
        });

        btnTipoRodado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnTipoRodado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipoRodadobtnMarcaActionPerformed(evt);
            }
        });

        btnModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModeloActionPerformed1(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel37.setText("N° Móvil:");

        txtNumeroMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNumeroMovil.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroMovil.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumeroMovil.setNextFocusableComponent(txtLetras);
        txtNumeroMovil.setScrollOffset(1);
        txtNumeroMovil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumeroMovilMouseClicked(evt);
            }
        });
        txtNumeroMovil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroMovilKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroMoviltxtColorKeyTyped(evt);
            }
        });

        txtLetras.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLetras.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtLetras.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtLetras.setNextFocusableComponent(txtNumeros);
        txtLetras.setScrollOffset(2);
        txtLetras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLetrasMouseClicked(evt);
            }
        });
        txtLetras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLetrasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLetrasKeyTyped(evt);
            }
        });

        txtNumeros.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNumeros.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeros.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumeros.setNextFocusableComponent(cmbMarcas);
        txtNumeros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumerosMouseClicked(evt);
            }
        });
        txtNumeros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumerosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumerosKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel9.setText("XXX - 999");

        jLabel10.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("-");

        chekAlquilar.setBackground(new java.awt.Color(117, 150, 227));
        chekAlquilar.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        chekAlquilar.setText("Alquilar");

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(10, 10, 10)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumeroMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(txtLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumeros, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9))))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addGap(10, 10, 10))
                                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addGap(12, 12, 12)))
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbColor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel22))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                        .addComponent(cmbMarcas, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                        .addComponent(cmbModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(165, 165, 165)
                                .addComponent(jLabel30))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel33)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbAireAcondicionado, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(239, 239, 239)
                                .addComponent(chooserAño, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbTipoDeRodado, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(239, 239, 239)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCapacidadCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKilometros, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTipoRodado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(chekAlquilar)))
                .addContainerGap(196, Short.MAX_VALUE))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(txtNumeroMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUtilidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel30))
                            .addComponent(chekAlquilar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)))
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumeros, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(txtLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbTipoDeRodado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel31))
                    .addComponent(btnTipoRodado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(cmbMarcas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel29)
                                .addComponent(cmbMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(chooserAño, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(txtKilometros, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCapacidadCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                                    .addComponent(cmbAireAcondicionado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel32))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/media-floppy.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnGuardarModificaciones.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardarModificaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnGuardarModificaciones.setText("<html><p>Guardar</p><p>Modificaciones</p></html>");
        btnGuardarModificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificacionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPane1Layout = new javax.swing.GroupLayout(jXTaskPane1.getContentPane());
        jXTaskPane1.getContentPane().setLayout(jXTaskPane1Layout);
        jXTaskPane1Layout.setHorizontalGroup(
            jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jXTaskPane1Layout.setVerticalGroup(
            jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jXTaskPane2.setCollapsed(true);
        jXTaskPane2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search.png"))); // NOI18N
        jXTaskPane2.setTitle("Buscar un Móvil");

        SearchBuscarMovil.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        SearchBuscarMovil.setToolTipText("");
        SearchBuscarMovil.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        SearchBuscarMovil.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                SearchBuscarMovilCaretUpdate(evt);
            }
        });
        SearchBuscarMovil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SearchBuscarMovilKeyTyped(evt);
            }
        });

        cmbOpcionBusqueda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbOpcionBusqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N° Móvil", "Patente", "Marca", "Modelo", "Color" }));

        javax.swing.GroupLayout jXTaskPaneContainer2Layout = new javax.swing.GroupLayout(jXTaskPaneContainer2);
        jXTaskPaneContainer2.setLayout(jXTaskPaneContainer2Layout);
        jXTaskPaneContainer2Layout.setHorizontalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                .addComponent(cmbOpcionBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchBuscarMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jXTaskPaneContainer2Layout.setVerticalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchBuscarMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbOpcionBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jXTaskPane2Layout = new javax.swing.GroupLayout(jXTaskPane2.getContentPane());
        jXTaskPane2.getContentPane().setLayout(jXTaskPane2Layout);
        jXTaskPane2Layout.setHorizontalGroup(
            jXTaskPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jXTaskPane2Layout.setVerticalGroup(
            jXTaskPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jXTaskPaneContainer3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        tablaMoviles.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.black, null, null));
        tablaMoviles.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaMoviles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Móvil", "Patente", "Marca", "Modelo", "Tipo de Rodado", "Color", "Utilidad", "Alquilado"
            }
        ));
        tablaMoviles.setGridColor(new java.awt.Color(0, 0, 0));
        tablaMoviles.setRowHeight(20);
        tablaMoviles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMovilesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaMoviles);
        if (tablaMoviles.getColumnModel().getColumnCount() > 0) {
            tablaMoviles.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jXTaskPaneContainer3Layout = new javax.swing.GroupLayout(jXTaskPaneContainer3);
        jXTaskPaneContainer3.setLayout(jXTaskPaneContainer3Layout);
        jXTaskPaneContainer3Layout.setHorizontalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jXTaskPaneContainer3Layout.setVerticalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jXTaskPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaMovilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMovilesMouseClicked
        // TODO add your handling code here:
//        this.obtenerMovil();
    }//GEN-LAST:event_tablaMovilesMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
            // TODO add your handling code here:
        jXTaskPane1.setEnabled(true);
        jXTaskPane1.setCollapsed(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnAgregar.setEnabled(false);
        //txtNumeroMovil.set;
        tablaMoviles.setEnabled(false);
        txtNumeroMovil.setEnabled(true);
        txtLetras.setEnabled(true);
        txtNumeros.setEnabled(true);
        chooserAño.setEnabled(true);
        txtKilometros.setEnabled(true);
        txtKilometros.setEnabled(true);
        txtCapacidadCarga.setEnabled(true);
        cmbMarcas.setEnabled(true);
        cmbModelo.setEnabled(true);
        cmbColor.setEnabled(true);
        cmbMotor.setEnabled(true);
        cmbUtilidad.setEnabled(true);
        cmbTipoDeRodado.setEnabled(true);
        cmbAireAcondicionado.setEnabled(true);
        btnMarca.setEnabled(true); 
        btnModelo.setEnabled(true); 
        btnColor.setEnabled(true); 
        btnMotor.setEnabled(true); 
        btnUtilidad.setEnabled(true); 
        btnTipoRodado.setEnabled(true);   
        btnGuardarModificaciones.setEnabled(false);
        jButton1.setEnabled(true);
        btnBuscarMóvil.setEnabled(false);
        txtNumeroMovil.requestFocus();
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        if(tablaMoviles.getSelectedRow()!=-1)
        {
            this.obtenerMovil();
            jXTaskPane1.setEnabled(true);
            jXTaskPane1.setCollapsed(false);
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
            //txtNumeroMovil.set;
            tablaMoviles.setEnabled(true);
            txtNumeroMovil.setEnabled(true);
            txtLetras.setEnabled(true);
            txtNumeros.setEnabled(true);
            chooserAño.setEnabled(true);
            txtKilometros.setEnabled(true);
            txtCapacidadCarga.setEnabled(true);
            cmbMarcas.setEnabled(true);
            cmbModelo.setEnabled(true);
            cmbColor.setEnabled(true);
            cmbMotor.setEnabled(true);
            cmbUtilidad.setEnabled(true);
            cmbTipoDeRodado.setEnabled(true);
            cmbAireAcondicionado.setEnabled(true);
            btnMarca.setEnabled(true); 
            btnModelo.setEnabled(true); 
            btnColor.setEnabled(true); 
            btnMotor.setEnabled(true); 
            btnUtilidad.setEnabled(true); 
            btnTipoRodado.setEnabled(true); 
            btnGuardarModificaciones.setEnabled(true);
            jButton1.setEnabled(false);
            tablaMoviles.setEnabled(false);
            btnAgregar.setEnabled(false);
            txtNumeroMovil.requestFocus();
            btnBuscarMóvil.setEnabled(false);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Móvil para realizar la modificación", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
            // TODO add your handling code here:
        if(tablaMoviles.getSelectedRow()!=-1)
        {
            this.eliminarMovil();
            txtNumeroMovil.setText("");
            this.cargarMarcasCombo();
            this.cargarColoresCombo();
            this.cargarMotoresCombo();
            this.cargarTiposUtilidadesCombo();
            this.cargarRodadosCombo();
            this.cmbModelo.removeAllItems();
            txtLetras.setText("") ;
            txtNumeros.setText("");
            Calendar fecha = new GregorianCalendar();
            int año = fecha.get(Calendar.YEAR);
            chooserAño.setYear(año);
            txtKilometros.setText("");
            txtCapacidadCarga.setText("");   
         }
         else
         {
             JOptionPane.showMessageDialog(null,"No ha seleccionado un Movil"," ",JOptionPane.ERROR_MESSAGE); 
         }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void SearchBuscarMovilKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchBuscarMovilKeyTyped
        // TODO add your handling code here:
        Movil aux = null;
        List moviles =  remiseria.buscarMoviles();
        List filtro = new LinkedList();
        if (!SearchBuscarMovil.getText().isEmpty()) 
        {
            Iterator iter = moviles.iterator();
            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                if(cmbOpcionBusqueda.getSelectedItem().equals("Marca"))
                {
                        if (aux.getUnaMarca().getNombreMarca().toUpperCase().startsWith(SearchBuscarMovil.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("N° Móvil"))
                {
                        if (String.valueOf(aux.getNumeroMovil()).startsWith(SearchBuscarMovil.getText())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("Modelo"))
                {
                        if (aux.getUnModelo().getNombreModelo().toUpperCase().startsWith(SearchBuscarMovil.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
                
                 if(cmbOpcionBusqueda.getSelectedItem().equals("Color"))
                {
                        if (aux.getUnColor().getNombreColor().toUpperCase().startsWith(SearchBuscarMovil.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
                 
                 if(cmbOpcionBusqueda.getSelectedItem().equals("Patente"))
                {
                        if (aux.getPatente().toUpperCase().startsWith(SearchBuscarMovil.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
            }
        } 
        else 
        {
            this.cargarTabla();
        }
    }//GEN-LAST:event_SearchBuscarMovilKeyTyped

    private void btnMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcaActionPerformed
        // TODO add your handling code here:
        JDialogMarca ventana =  new JDialogMarca( parent,  modal, cmbMarcas , remiseria);
        ventana.show();
    }//GEN-LAST:event_btnMarcaActionPerformed

    private void txtKilometrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKilometrosMouseClicked
        // TODO add your handling code here:
        txtKilometros.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtKilometrosMouseClicked

    private void txtKilometrosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKilometrosKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 7;
        {if (txtKilometros.getText().length()== limite)
            evt.consume();
        }       
    }//GEN-LAST:event_txtKilometrosKeyTyped

    private void txtCapacidadCargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCapacidadCargaMouseClicked
        // TODO add your handling code here:
        txtCapacidadCarga.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtCapacidadCargaMouseClicked

    private void txtCapacidadCargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCapacidadCargaKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 7;
        {if (txtCapacidadCarga.getText().length()== limite)
            evt.consume();
        }    
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }          
    }//GEN-LAST:event_txtCapacidadCargaKeyTyped

    private void btnColorbtnMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColorbtnMarcaActionPerformed
        // TODO add your handling code here:
        JDialogColor ventana =  new JDialogColor( parent,  modal, cmbColor , remiseria);
        ventana.show();
    }//GEN-LAST:event_btnColorbtnMarcaActionPerformed

    private void btnMotorbtnMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotorbtnMarcaActionPerformed
        // TODO add your handling code here:
        JDialogMotor ventana =  new JDialogMotor( parent,  modal, cmbMotor , remiseria);
        ventana.show();
    }//GEN-LAST:event_btnMotorbtnMarcaActionPerformed

    private void btnUtilidadbtnMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUtilidadbtnMarcaActionPerformed
        // TODO add your handling code here:

        JDialogUtilidad ventana =  new JDialogUtilidad( parent,  modal, cmbUtilidad , remiseria);
        ventana.show();
    }//GEN-LAST:event_btnUtilidadbtnMarcaActionPerformed

    private void btnTipoRodadobtnMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipoRodadobtnMarcaActionPerformed
        // TODO add your handling code here:
        JDialogRodado ventana =  new JDialogRodado( parent,  modal, cmbTipoDeRodado , remiseria);
        ventana.show();
    }//GEN-LAST:event_btnTipoRodadobtnMarcaActionPerformed

    private void btnModeloActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModeloActionPerformed1
        // TODO add your handling code here:
        if(!cmbMarcas.getSelectedItem().toString().equals(""))
        {
            Marca unaMarca = remiseria.buscarMarca(cmbMarcas.getSelectedItem().toString());
            JDialogModelo ventana =  new JDialogModelo( parent,  modal, cmbModelo , unaMarca,remiseria);
            ventana.show();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una Marca"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModeloActionPerformed1

    private void txtNumeroMovilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroMovilMouseClicked
        // TODO add your handling code here:
        txtNumeroMovil.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtNumeroMovilMouseClicked

    private void txtNumeroMoviltxtColorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroMoviltxtColorKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 7;
        {if (txtNumeroMovil.getText().length()== limite)
            evt.consume();
        }        
    }//GEN-LAST:event_txtNumeroMoviltxtColorKeyTyped

    private void txtLetrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLetrasMouseClicked
        // TODO add your handling code here:
        txtLetras.setBackground(java.awt.Color.white);
        txtNumeros.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtLetrasMouseClicked

    private void txtLetrasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLetrasKeyTyped
        // TODO add your handling code here:
        char c;
        c = evt.getKeyChar();
        if (!(c < '0' || c > '9'))
        {
            evt.consume();
        }
        int limite  = 3;
        {if (txtLetras.getText().length()== limite)

            evt.consume();
        }
    }//GEN-LAST:event_txtLetrasKeyTyped

    private void txtNumerosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumerosMouseClicked
        // TODO add your handling code here:
        txtLetras.setBackground(java.awt.Color.white);
        txtNumeros.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtNumerosMouseClicked

    private void txtNumerosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumerosKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 3;
        {if (txtNumeros.getText().length()== limite)

            evt.consume();
        }
    }//GEN-LAST:event_txtNumerosKeyTyped

    private void SearchBuscarMovilCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_SearchBuscarMovilCaretUpdate
        // TODO add your handling code here:
        Movil aux = null;
        List moviles =  remiseria.buscarMoviles();
        List filtro = new LinkedList();
        if (!SearchBuscarMovil.getText().isEmpty()) 
        {
            Iterator iter = moviles.iterator();
            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                if(cmbOpcionBusqueda.getSelectedItem().equals("Marca"))
                {
                        if (aux.getUnaMarca().getNombreMarca().toUpperCase().startsWith(SearchBuscarMovil.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("N° Móvil"))
                {
                        if (String.valueOf(aux.getNumeroMovil()).startsWith(SearchBuscarMovil.getText())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
                
                if(cmbOpcionBusqueda.getSelectedItem().equals("Modelo"))
                {
                        if (aux.getUnModelo().getNombreModelo().toUpperCase().startsWith(SearchBuscarMovil.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
                
                 if(cmbOpcionBusqueda.getSelectedItem().equals("Color"))
                {
                        if (aux.getUnColor().getNombreColor().toUpperCase().startsWith(SearchBuscarMovil.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
                 
                 if(cmbOpcionBusqueda.getSelectedItem().equals("Patente"))
                {
                        if (aux.getPatente().toUpperCase().startsWith(SearchBuscarMovil.getText().toUpperCase())) 
                        {
                            filtro.add(aux);
                        }
                        this.cargarTablaMovilesBuscados(tablaMoviles, filtro);
                }
            }
        } 
        else 
        {
            this.cargarTabla();
        }
    }//GEN-LAST:event_SearchBuscarMovilCaretUpdate

    private void cmbMarcasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMarcasActionPerformed
        // TODO add your handling code here:
        if (cmbMarcas.getItemCount()!= 0){
            String nombreMarcas = cmbMarcas.getSelectedItem().toString();
            if(!nombreMarcas.isEmpty())
            {
                Marca unaMarca = remiseria.buscarMarca(nombreMarcas);
                if(unaMarca != null)
                {
                    this.cargarModelosCombo(unaMarca);
                }
            }
        }        
    }//GEN-LAST:event_cmbMarcasActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
            this.agregarMovil();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jXTaskPane1.setCollapsed(true);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        jXTaskPane1.setEnabled(false);
        tablaMoviles.setEnabled(true);
        tablaMoviles.setRowSelectionInterval(0, 0);
        txtNumeroMovil.setEnabled(false);
        txtLetras.setEnabled(false);
        txtNumeros.setEnabled(false);
        chooserAño.setEnabled(false);
        txtKilometros.setEnabled(false);
        txtKilometros.setEnabled(false);
        txtCapacidadCarga.setEnabled(false);
        cmbMarcas.setEnabled(false);
        cmbModelo.setEnabled(false);
        cmbColor.setEnabled(false);
        cmbMotor.setEnabled(false);
        cmbUtilidad.setEnabled(false);
        cmbTipoDeRodado.setEnabled(false);
        cmbAireAcondicionado.setEnabled(false);    
        btnMarca.setEnabled(false); 
        btnModelo.setEnabled(false); 
        btnColor.setEnabled(false); 
        btnMotor.setEnabled(false); 
        btnUtilidad.setEnabled(false); 
        btnTipoRodado.setEnabled(false);   
        btnAgregar.setEnabled(true);   
        tablaMoviles.setRowSelectionInterval(0, 0);
        btnBuscarMóvil.setEnabled(true);
        limpiar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnGuardarModificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionesActionPerformed
        // TODO add your handling code here:
        this.modificarMovil();
    }//GEN-LAST:event_btnGuardarModificacionesActionPerformed

    private void btnBuscarMóvilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMóvilActionPerformed
        // TODO add your handling code here:
        jXTaskPane2.setCollapsed(false);
    }//GEN-LAST:event_btnBuscarMóvilActionPerformed

    private void txtNumeroMovilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroMovilKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtLetras.setNextFocusableComponent(txtNumeros);
        }  
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }          
    }//GEN-LAST:event_txtNumeroMovilKeyPressed

    private void txtLetrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLetrasKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtNumeros.setNextFocusableComponent(cmbMarcas);
        }         
    }//GEN-LAST:event_txtLetrasKeyPressed

    private void txtNumerosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumerosKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            cmbMarcas.setNextFocusableComponent(cmbModelo);
        }   
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }          
    }//GEN-LAST:event_txtNumerosKeyPressed

    private void cmbModeloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbModeloKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            cmbColor.setNextFocusableComponent(cmbMotor);
        }           
    }//GEN-LAST:event_cmbModeloKeyPressed

    private void cmbColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbColorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            cmbMotor.setNextFocusableComponent(cmbUtilidad);
        }          
    }//GEN-LAST:event_cmbColorKeyPressed

    private void cmbMotorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMotorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            cmbUtilidad.setNextFocusableComponent(cmbTipoDeRodado);
        }          
    }//GEN-LAST:event_cmbMotorKeyPressed

    private void cmbUtilidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbUtilidadKeyPressed
        // TODO add your handling code here:
 
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            cmbTipoDeRodado.setNextFocusableComponent(chooserAño);
        }          
    }//GEN-LAST:event_cmbUtilidadKeyPressed

    private void chooserAñoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chooserAñoKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtKilometros.setNextFocusableComponent(txtCapacidadCarga);
        }          
    }//GEN-LAST:event_chooserAñoKeyPressed

    private void txtKilometrosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKilometrosKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            txtCapacidadCarga.setNextFocusableComponent(cmbAireAcondicionado);
        }  
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }          
    }//GEN-LAST:event_txtKilometrosKeyPressed

    private void txtCapacidadCargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCapacidadCargaKeyPressed
        // TODO add your handling code here:
                
    }//GEN-LAST:event_txtCapacidadCargaKeyPressed

    private void chooserAñoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chooserAñoKeyTyped
        // TODO add your handling code here:
        int limite  = 5;
         if(String.valueOf(chooserAño.getValue()).length() < limite)
        {
            evt.consume();
        }         
    }//GEN-LAST:event_chooserAñoKeyTyped
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
    private void btnBuscarMóvil1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMóvil1ActionPerformed
        // TODO add your handling code here:
                 try{  
                     List historial = remiseria.buscarMoviles();
                     if(historial != null)
                     {
                        LinkedList <ImprimirMóviles> lista = new LinkedList<ImprimirMóviles>(); 
                        Movil aux = null;
                        Iterator iter = historial.iterator();
                        while (iter.hasNext())
                        {
                              aux = (Movil) iter.next();  
                              String numero = String.valueOf(aux.getNumeroMovil());
                              String patente = aux.getPatente();
                              String año = String.valueOf(aux.getAnio());
                              String unaMarca = aux.getUnaMarca().getNombreMarca();
                              String unModelo = aux.getUnModelo().getNombreModelo();
                              String unMotor = aux.getUnMotor().getNombreMotor();
                              String unColor = aux.getUnColor().getNombreColor();
                              String unTipoUtilidad = aux.getUnTipoUtilidad().getNombreTipoUtilidad();
                              String unRodado = aux.getUnRodado().getNombreRodado();
                              String kilometro = String.valueOf(aux.getKilometraje());
                              String aireAcondicionado = aux.getAireAcondicionado();
                              String capacidadCarga = String.valueOf(aux.getCapacidadCarga());
                              String alquilado = null;
                              if(aux.isAlquilado()== true)
                              {
                                  alquilado = "Si";
                              }
                              else
                              {
                                  alquilado = "No";
                              }

                              lista.add(new ImprimirMóviles(numero,patente,año,unaMarca,unModelo,unMotor,unColor,unTipoUtilidad,unRodado,kilometro+" Km",aireAcondicionado,capacidadCarga+" Kg",alquilado));
                        }
                        HashMap<String, Object> parametros = new HashMap();
                        parametros.clear();
                        if(unMaestro != null)
                        {
                              parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                              parametros.put("logo", remiseria.getLogo());

                        }
                        else if(unOperario != null)
                        {
                              parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getCalle() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                              parametros.put("logo", remiseria.getLogo());

                        }  
                        //C:/Users/garba/Desktop/Reportes/
                        //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Moviles.jrxml");
                        JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Moviles.jrxml");
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
                     else
                     {
                         JOptionPane.showMessageDialog(null,"No hay Información de Móviles para realizar el reporte");
                     }
                 } 
                 catch (JRException ex) 
                 {
                     JOptionPane.showMessageDialog(this, ex.getMessage());
                 }       
        
    }//GEN-LAST:event_btnBuscarMóvil1ActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogMovil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogMovil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogMovil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogMovil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogMovil dialog = new JDialogMovil(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField SearchBuscarMovil;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscarMóvil;
    private javax.swing.JButton btnBuscarMóvil1;
    private javax.swing.JButton btnColor;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarModificaciones;
    private javax.swing.JButton btnMarca;
    private javax.swing.JButton btnModelo;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnMotor;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTipoRodado;
    private javax.swing.JButton btnUtilidad;
    private javax.swing.JCheckBox chekAlquilar;
    private com.toedter.calendar.JYearChooser chooserAño;
    private javax.swing.JComboBox cmbAireAcondicionado;
    private javax.swing.JComboBox cmbColor;
    private javax.swing.JComboBox cmbMarcas;
    private javax.swing.JComboBox cmbModelo;
    private javax.swing.JComboBox cmbMotor;
    private javax.swing.JComboBox cmbOpcionBusqueda;
    private javax.swing.JComboBox cmbTipoDeRodado;
    private javax.swing.JComboBox cmbUtilidad;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private javax.swing.JTable tablaMoviles;
    private javax.swing.JTextField txtCapacidadCarga;
    private javax.swing.JTextField txtKilometros;
    private javax.swing.JTextField txtLetras;
    private javax.swing.JTextField txtNumeroMovil;
    private javax.swing.JTextField txtNumeros;
    // End of variables declaration//GEN-END:variables
}
