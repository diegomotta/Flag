/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Cliente;
import Clases.Domicilio;
import Clases.EstadoCivil;
import Clases.ImprimirClientes;
import Clases.Maestro;
import Clases.Nacionalidad;
import Clases.Operario;
import Clases.Remiseria;
import Clases.Sexo;
import Clases.Telefono;
import Clases.TipoDni;
import Clases.Utilidad;
import static Interface.JDialogMovil.esEntero;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
public class JDialogCliente extends javax.swing.JDialog {
    private Remiseria remiseria;
    private Utilidad utilidades;

    private java.awt.Frame parent;
    private boolean modal;
    private Maestro unMaestro;
    private Operario unOperario;
    private JComboBox cmbCliente;
    /**
     * Creates new form JDialogCliente
     */
    public JDialogCliente(java.awt.Frame parent, boolean modal,Remiseria remiseria,Utilidad utilidades,Maestro unMaestro,Operario unOperario,JComboBox cmbCliente) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unMaestro = unMaestro;
        this.unOperario = unOperario;
        this.cmbCliente = cmbCliente; 
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarClientes();
        this.cargarEstadosCivilesCombo();
        this.cargarNacionalidadesCombo();
        this.cargarSexosCombo();
        this.cargarTiposDocumentos();

        AutoCompleteDecorator.decorate(this.cmbSexo);
        AutoCompleteDecorator.decorate(this.cmbTipoDeDocumento);
        AutoCompleteDecorator.decorate(this.cmbNacionalidad);
        AutoCompleteDecorator.decorate(this.cmbEstadoCivil);
        jdcFecha.setPreferredSize(new Dimension(95,20));
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);
        jXTaskPane1.setCollapsed(true);
        txtNumeroDocumento.setEnabled(false);
        cmbTipoDeDocumento.setEnabled(false);
        btnTipoDeDocumento.setEnabled(false);
        txtApellido.setEnabled(false);
        txtNombre.setEnabled(false);
        jdcFecha.setEnabled(false);
        cmbSexo.setEnabled(false);
        btnSexo.setEnabled(false);
        cmbEstadoCivil.setEnabled(false);
        btnEstadoCivil.setEnabled(false);
        cmbNacionalidad.setEnabled(false);
        btnNacionalidad.setEnabled(false);   
        try
        {
            tablaClientes.setRowSelectionInterval(0, 0);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No hay Clientes registrados aún", null, JOptionPane.WARNING_MESSAGE);
        }
    }

    public void cargarComboViajeCliente()
    {
        if(cmbCliente!= null)
        {
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
        }
   }
    
    public void obtenerCliente(){
        int fila = (int)tablaClientes.getSelectedRow();
        int dni = (int) tablaClientes.getValueAt(fila, 0);
        Cliente unCliente = remiseria.buscarCliente(dni);
        txtNumeroDocumento.setText(String.valueOf(unCliente.getDni()));
        cmbTipoDeDocumento.setSelectedItem(unCliente.getUnTipoDni().getSiglas());
        txtApellido.setText(unCliente.getApellido());
        txtNombre.setText(unCliente.getNombre());
        jdcFecha.setDate(unCliente.getFechaNacimiento());
        cmbSexo.setSelectedItem(unCliente.getUnSexo().getNombreSexo());
        cmbEstadoCivil.setSelectedItem(unCliente.getUnEstadoCivil().getNombreEstadoCivil());
        cmbNacionalidad.setSelectedItem(unCliente.getUnaNacionalidad().getNombreNacionalidad());
    }
    
    public void obtenerDomicilios()
    {
            int fila = (int)tablaClientes.getSelectedRow();
            int dni = (int) tablaClientes.getValueAt(fila, 0);
            Cliente unCliente = remiseria.buscarCliente(dni);
            this.cargarDomicilios(unCliente);           
    }
    
    public void obtenerTelefonos()
    {
            int fila = (int)tablaClientes.getSelectedRow();
            int dni = (int) tablaClientes.getValueAt(fila, 0);
            Cliente unCliente = remiseria.buscarCliente(dni);
            this.cargarTelefonos(unCliente);
    }
    
    
    public void eliminarCliente(){
        try
        {
            int eleccion = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el Cliente seleccionado?");
            if ( eleccion == 0)
            {
                int dniCliente = (int) tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0);
                Cliente unCliente = remiseria.buscarCliente(dniCliente);
                remiseria.eliminarCliente(unCliente);
                limpiar_tabla(tablaDirecciones);
                limpiar_tabla(tablaTelefonos);
                this.cargarClientes();
                JOptionPane.showMessageDialog(null,"El Cliente a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex, null, JOptionPane.ERROR_MESSAGE);
        }
    }
        
    public  void cargarClientes(){
        List clientes = remiseria.buscarClientes(); 
        Collections.sort(clientes, new Comparator<Cliente>() {
                @Override
                public int compare(Cliente p1, Cliente p2) {                
                        return new Integer(p1.getIdPersona()).compareTo(new Integer(p2.getIdPersona()));
                }
        });          
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaClientes.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);  
        modelo.addColumn("N° de Documento");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");

        Cliente aux = null;
        Iterator iter = clientes.iterator();
        while (iter.hasNext()){
            aux = (Cliente) iter.next();
            Object [] fila = new Object[3];
            fila[0] = aux.getDni();
            fila[1] = aux.getNombre();
            fila[2] = aux.getApellido();
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaClientes.setModel(modelo);
    }
    
     public  void cargarDomicilios(Cliente unCliente){
        List domicilios = unCliente.buscarDirecciones(unCliente);
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaDirecciones.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);          
        modelo.addColumn("Dirección");
        modelo.addColumn("Barrio");
        modelo.addColumn("Ciudad");
        modelo.addColumn("Provincia");
        modelo.addColumn("País");

        Domicilio aux = null;
        Iterator iter = domicilios.iterator();
        while (iter.hasNext()){
            aux = (Domicilio) iter.next();
            Object [] fila = new Object[5];
            if(aux.getPiso().isEmpty() && aux.getDpto().isEmpty())
            {
                fila[0] ="Calle "+ aux.getCalle()+" N° "+aux.getNroCasa();
            }
            else
            {
                fila[0] ="Calle "+ aux.getCalle()+" N° "+aux.getNroCasa()+" Piso: "+ aux.getPiso()+" Dpto: "+aux.getDpto();
            }
            fila[1] = aux.getUnBarrio().getNombreBarrio();
            fila[2] = aux.getUnaCiudad().getNombreCiudad();
            fila[3] = aux.getUnaProvincia().getNombreProvincia();
            fila[4] = aux.getUnPais().getNombrePais();
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaDirecciones.setModel(modelo);
    }
     
     public  void cargarTelefonos(Cliente unCliente){
        List telefonos = unCliente.buscarTelefonos(unCliente);
        if(telefonos != null)
        {
            DefaultTableModel modelo = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTableHeader th; 
            th = tablaTelefonos.getTableHeader(); 
            Font fuente = new Font("Tahoma", Font.BOLD, 12); 
            th.setForeground(java.awt.Color.BLACK);
            th.setBackground(java.awt.Color.white);
            th.setFont(fuente);  
            modelo.addColumn("N° Telefónico");
            modelo.addColumn("Tipo de Teléfono");
            Telefono aux = null;
            Iterator iter = telefonos.iterator();
            while (iter.hasNext()){
                aux = (Telefono) iter.next();
                Object [] fila = new Object[2];

                fila[0] = "("+aux.getUnCodPais().getCodPais()+") "+ aux.getUnCodArea().getCodArea()+ " "+ aux.getNroTelefonico();
                fila[1] = aux.getUnTipoTelefono().getNombreTipoTelefono().toString();

                modelo.addRow(fila);
            }
            modelo.rowsRemoved(null);
            tablaTelefonos.setModel(modelo);
        }
    }
    
     public void cargarNacionalidadesCombo(){
        cmbNacionalidad.removeAllItems();
        List nacionalidades = remiseria.buscarNacionalidades();
        if(nacionalidades != null)
        {
            Nacionalidad aux = null;
            Iterator iter = nacionalidades.iterator();
            while (iter.hasNext()){
                aux = (Nacionalidad) iter.next();
                cmbNacionalidad.addItem(aux.getNombreNacionalidad());
            }
        }
    }
    
     public void cargarEstadosCivilesCombo(){
        cmbEstadoCivil.removeAllItems();
        List estados = remiseria.buscarEstadosCiviles();
        if(estados != null)
        {
            EstadoCivil aux = null;
            Iterator iter = estados.iterator();
            while (iter.hasNext()){
                aux = (EstadoCivil) iter.next();
                cmbEstadoCivil.addItem(aux.getNombreEstadoCivil());
            }
        }
    }
    
      public void cargarSexosCombo(){
        cmbSexo.removeAllItems();
        List sexos =remiseria.buscarSexos();
        if(sexos != null)
        {
            Sexo aux = null;
            Iterator iter = sexos.iterator();
            while (iter.hasNext())
            {
                aux = (Sexo) iter.next();
                cmbSexo.addItem(aux.getNombreSexo());
            }
        }
    }

    public void cargarTiposDocumentos(){
        cmbTipoDeDocumento.removeAllItems();
        List tiposdnis =remiseria.buscarTiposDocumentos();
        if(tiposdnis != null)
        {
            TipoDni aux = null;
            Iterator iter = tiposdnis.iterator();
            while (iter.hasNext())
            {
                aux = (TipoDni) iter.next();
                cmbTipoDeDocumento.addItem(aux.getSiglas());
            }
        }
    }
    

    
    public void agregarCliente(){
                boolean ok = true;
                String numeroDocumento = txtNumeroDocumento.getText();
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                TipoDni tipoDocumento = null;
                Date fechaNacimiento = jdcFecha.getDate();
                Sexo unSexo = null;
                EstadoCivil unEstadoCivil = null;
                Nacionalidad unaNacionalidad = null;
            
                if(txtNumeroDocumento.getText().toString().isEmpty())
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el N° de Documento","",JOptionPane.ERROR_MESSAGE);
                     txtNumeroDocumento.setBackground(java.awt.Color.red);
                     ok= false;
                }
                
                if((cmbTipoDeDocumento != null))
                {
                    tipoDocumento = remiseria.buscarTipoDocumento(cmbTipoDeDocumento.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el Tipo de Documento","",JOptionPane.ERROR_MESSAGE);
                     ok= false;                
                }
                
                if(txtApellido.getText().toString().isEmpty())
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el Apellido","",JOptionPane.ERROR_MESSAGE);
                     txtApellido.setBackground(java.awt.Color.red);           
                     ok= false;
                }

                if(txtNombre.getText().toString().isEmpty())
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el Nombre","",JOptionPane.ERROR_MESSAGE);
                     txtNombre.setBackground(java.awt.Color.red); 
                     ok= false;
                }
                if(jdcFecha.getDate() == null)
                {
                      JOptionPane.showMessageDialog(null,"No ha ingresado la Fecha de Nacimiento","",JOptionPane.ERROR_MESSAGE); 
                      ok= false;
                }

                if( cmbSexo.getSelectedItem() != null)
                {
                    unSexo = remiseria.buscarSexo(cmbSexo.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado el Sexo","",JOptionPane.ERROR_MESSAGE);
                      ok= false;                
                }

                if(cmbEstadoCivil.getSelectedItem() != null )
                {
                    unEstadoCivil = remiseria.buscarEstadoCivil(cmbEstadoCivil.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado el Estado Civil","",JOptionPane.ERROR_MESSAGE);
                      ok= false;                
                }
                if(cmbNacionalidad.getSelectedItem() != null )
                {
                     unaNacionalidad = remiseria.buscarNacionalidad(cmbNacionalidad.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado la Nacionalidad","",JOptionPane.ERROR_MESSAGE);
                     ok= false;                
                }
    
                if(utilidades.isNumber(numeroDocumento)== true)
                {
                    int dni = Integer.parseInt(txtNumeroDocumento.getText());
                    if (remiseria.nroClienteLibre(dni) == false)
                    {
                        if(ok == true)
                        {
                            remiseria.agregarCliente(dni, tipoDocumento, unSexo, unEstadoCivil, unaNacionalidad, nombre, apellido,  fechaNacimiento);
                            JOptionPane.showMessageDialog(null,"Se registró un nuevo Cliente ","",JOptionPane.INFORMATION_MESSAGE);
                            this.cargarClientes();
                            if(cmbCliente!= null)
                            {
                                this.cargarComboViajeCliente();
                            }
                            btnTelefonos.setEnabled(true);
                            btnDomicilioa.setEnabled(true);
                            btnEliminarCliente.setEnabled(true);
                            btnModificarCliente.setEnabled(true);
                            btnNuevoCliente.setEnabled(true);
                            jXTaskPane1.setCollapsed(true);
                            btnGuardarModificaciones.setEnabled(false);
                            btnGuardarChofer.setEnabled(false);
                            tablaClientes.setEnabled(true);
                            tablaDirecciones.setEnabled(true);
                            tablaTelefonos.setEnabled(true);  
                            btnBuscarChofer.setEnabled(true);
                            tablaClientes.setRowSelectionInterval(0, 0);
                            this.limpiar();
                        }
                     }
                     else
                     {
                          JOptionPane.showMessageDialog(null,"El N° de Documento ya existe " + dni ,"",JOptionPane.ERROR_MESSAGE);
                          txtNumeroDocumento.setText(null);
                          txtNumeroDocumento.setBackground(java.awt.Color.red);
                     } 
                 }

        
    }

    public void modificarCliente()
    {
                boolean ok = true;
                int fila = tablaClientes.getSelectedRow();
                int numeroCliente = (int) tablaClientes.getValueAt(fila, 0);
                Cliente unCliente  = remiseria.buscarCliente(numeroCliente);
                String numeroDocumento = txtNumeroDocumento.getText();
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                TipoDni tipoDocumento = null;
                Date fechaNacimiento = jdcFecha.getDate();
                Sexo unSexo = null;
                EstadoCivil unEstadoCivil = null;
                Nacionalidad unaNacionalidad = null;
            
                if(txtNumeroDocumento.getText().toString().isEmpty())
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el N° de Documento","",JOptionPane.ERROR_MESSAGE);
                     txtNumeroDocumento.setBackground(java.awt.Color.red);
                     ok= false;
                }
                
                if((cmbTipoDeDocumento != null))
                {
                    tipoDocumento = remiseria.buscarTipoDocumento(cmbTipoDeDocumento.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el Tipo de Documento","",JOptionPane.ERROR_MESSAGE);
                     ok= false;                
                }
                
                if(txtApellido.getText().toString().isEmpty())
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el Apellido","",JOptionPane.ERROR_MESSAGE);
                     txtApellido.setBackground(java.awt.Color.red);           
                     ok= false;
                }

                if(txtNombre.getText().toString().isEmpty())
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el Nombre","",JOptionPane.ERROR_MESSAGE);
                     txtNombre.setBackground(java.awt.Color.red); 
                     ok= false;
                }
                if(jdcFecha.getDate() == null)
                {
                      JOptionPane.showMessageDialog(null,"No ha ingresado la Fecha de Nacimiento","",JOptionPane.ERROR_MESSAGE); 
                      ok= false;
                }

                if( cmbSexo.getSelectedItem() != null)
                {
                    unSexo = remiseria.buscarSexo(cmbSexo.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado el Sexo","",JOptionPane.ERROR_MESSAGE);
                      ok= false;                
                }

                if(cmbEstadoCivil.getSelectedItem() != null )
                {
                    unEstadoCivil = remiseria.buscarEstadoCivil(cmbEstadoCivil.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado el Estado Civil","",JOptionPane.ERROR_MESSAGE);
                      ok= false;                
                }
                if(cmbNacionalidad.getSelectedItem() != null )
                {
                     unaNacionalidad = remiseria.buscarNacionalidad(cmbNacionalidad.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado la Nacionalidad","",JOptionPane.ERROR_MESSAGE);
                     ok= false;                
                }
                
                if(unCliente == null)
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado un Cliente"," ",JOptionPane.ERROR_MESSAGE);
                     ok = false;   
                }
                
                if(utilidades.isNumber(numeroDocumento)== true)
                {
                    int dni = Integer.parseInt(numeroDocumento);
                    if (dni == unCliente.getDni())
                    {
                        if(ok == true)
                        {
                            remiseria.modificarCliente(unCliente, dni, tipoDocumento, unSexo, unEstadoCivil, unaNacionalidad, nombre, apellido, fechaNacimiento);
                            JOptionPane.showMessageDialog(null,"Se modifico el Cliente seleccionado","",JOptionPane.INFORMATION_MESSAGE);
                            this.cargarClientes();
                            if(cmbCliente!= null)
                            {
                                this.cargarComboViajeCliente();
                            }                            
                            btnTelefonos.setEnabled(true);
                            btnDomicilioa.setEnabled(true);
                            btnEliminarCliente.setEnabled(true);
                            btnModificarCliente.setEnabled(true);
                            btnNuevoCliente.setEnabled(true);
                            jXTaskPane1.setCollapsed(true);
                            btnGuardarModificaciones.setEnabled(false);
                            btnGuardarChofer.setEnabled(false);
                            tablaClientes.setEnabled(true);
                            tablaDirecciones.setEnabled(true);
                            tablaTelefonos.setEnabled(true);  
                            btnBuscarChofer.setEnabled(true);
                            tablaClientes.setRowSelectionInterval(0, 0);
                            this.limpiar();
                          
                        }
                     }
                     else
                     {
                            if(remiseria.buscarChofer(dni) == null)
                            {
                                if(ok == true)
                                {
                                    remiseria.modificarCliente(unCliente, dni, tipoDocumento, unSexo, unEstadoCivil, unaNacionalidad, nombre, apellido, fechaNacimiento);
                                    JOptionPane.showMessageDialog(null,"Se modifico el Cliente seleccionado","",JOptionPane.INFORMATION_MESSAGE);
                                    this.cargarClientes();
                                    if(cmbCliente!= null)
                                    {
                                        this.cargarComboViajeCliente();
                                    }                                    
                                    btnTelefonos.setEnabled(true);
                                    btnDomicilioa.setEnabled(true);
                                    btnEliminarCliente.setEnabled(true);
                                    btnModificarCliente.setEnabled(true);
                                    btnNuevoCliente.setEnabled(true);
                                    jXTaskPane1.setCollapsed(true);
                                    btnGuardarModificaciones.setEnabled(false);
                                    btnGuardarChofer.setEnabled(false);
                                    tablaClientes.setEnabled(true);
                                    tablaDirecciones.setEnabled(true);
                                    tablaTelefonos.setEnabled(true); 
                                    btnBuscarChofer.setEnabled(true);
                                    tablaClientes.setRowSelectionInterval(0, 0);
                                    this.limpiar();
                                }
                            }
                            else
                            {
                                 JOptionPane.showMessageDialog(null,"El N° de Documento ya existe " + dni," ",JOptionPane.ERROR_MESSAGE);
                                 txtNumeroDocumento.setText("");
                                 txtNumeroDocumento.setBackground(java.awt.Color.red);
                            }
                     }
                }               
    }

    public static void limpiar_tabla(JTable tabla) {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }
    
    public static void cargarTablaClientesBuscados(JTable tabla, List lista) {
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
        modelo.addColumn("N° de Documento");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");

        Cliente aux = null;
        Iterator iter = lista.iterator();
        while (iter.hasNext()){
            aux = (Cliente) iter.next();
            Object [] fila = new Object[3];
            fila[0] = aux.getDni();
            fila[1] = aux.getNombre();
            fila[2] = aux.getApellido();

            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tabla.setModel(modelo);
    }
    
    public Cliente obtenerElCliente()
    {
          int fila = tablaClientes.getSelectedRow();
          int numeroCliente = (int) tablaClientes.getValueAt(fila, 0);
          Cliente unCliente  = remiseria.buscarCliente(numeroCliente);
          return unCliente;
    }

     public void limpiar()
     {
        txtNumeroDocumento.setText("");
        txtNumeroDocumento.setBackground(Color.white);
        this.cargarTiposDocumentos();
        txtApellido.setText("");
        txtApellido.setBackground(Color.white);
        txtNombre.setText("");
        txtNombre.setBackground(Color.white);
        jdcFecha.cleanup();
        this.cargarSexosCombo();
        this.cargarEstadosCivilesCombo();
        this.cargarNacionalidadesCombo();    
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
        jPanel4 = new javax.swing.JPanel();
        btnNuevoCliente = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnModificarCliente = new javax.swing.JButton();
        btnDomicilioa = new javax.swing.JButton();
        btnTelefonos = new javax.swing.JButton();
        btnBuscarChofer = new javax.swing.JButton();
        btnBuscarMóvil1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cmbTipoDeDocumento = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        txtNumeroDocumento = new javax.swing.JTextField();
        btnTipoDeDocumento = new javax.swing.JButton();
        txtApellido = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        cmbSexo = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cmbEstadoCivil = new javax.swing.JComboBox();
        cmbNacionalidad = new javax.swing.JComboBox();
        jLabel36 = new javax.swing.JLabel();
        btnNacionalidad = new javax.swing.JButton();
        btnEstadoCivil = new javax.swing.JButton();
        btnSexo = new javax.swing.JButton();
        btnGuardarChofer = new javax.swing.JButton();
        btnGuardarModificaciones = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        SearchCliente = new javax.swing.JTextField();
        cmbOpcionBusqueda = new javax.swing.JComboBox();
        jXTaskPaneContainer2 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDirecciones = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaTelefonos = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnNuevoCliente.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevoCliente.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnNuevoCliente.setText("Agregar");
        btnNuevoCliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
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

        btnEliminarCliente.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarCliente.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarCliente.setText("Eliminar ");
        btnEliminarCliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnModificarCliente.setBackground(new java.awt.Color(255, 255, 255));
        btnModificarCliente.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarCliente.setText("Modificar ");
        btnModificarCliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarClienteActionPerformed(evt);
            }
        });

        btnDomicilioa.setBackground(new java.awt.Color(255, 255, 255));
        btnDomicilioa.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnDomicilioa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/leafpad.png"))); // NOI18N
        btnDomicilioa.setText("Editar Domicilios");
        btnDomicilioa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDomicilioa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDomicilioaActionPerformed(evt);
            }
        });

        btnTelefonos.setBackground(new java.awt.Color(255, 255, 255));
        btnTelefonos.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnTelefonos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/contact-new (2).png"))); // NOI18N
        btnTelefonos.setText("Editar Teléfonos");
        btnTelefonos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTelefonos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTelefonosActionPerformed(evt);
            }
        });

        btnBuscarChofer.setBackground(new java.awt.Color(255, 255, 255));
        btnBuscarChofer.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnBuscarChofer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search.png"))); // NOI18N
        btnBuscarChofer.setText("Buscar");
        btnBuscarChofer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBuscarChofer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarChoferActionPerformed(evt);
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
                    .addComponent(btnTelefonos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDomicilioa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscarChofer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDomicilioa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTelefonos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel1.setText("Gestor de Clientes");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jXTaskPane1.setCollapsed(true);
        jXTaskPane1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document-new.png"))); // NOI18N
        jXTaskPane1.setTitle("Datos del Cliente");

        jLabel13.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel13.setText("Nombre:");

        jLabel14.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel14.setText("Apellido:");

        jLabel21.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel21.setText("Tipo de documento:");

        cmbTipoDeDocumento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbTipoDeDocumento.setNextFocusableComponent(txtApellido);

        jLabel15.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel15.setText("N° de Documento:");

        txtNumeroDocumento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNumeroDocumento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroDocumento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumeroDocumento.setNextFocusableComponent(cmbTipoDeDocumento);
        txtNumeroDocumento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumeroDocumentoMouseClicked(evt);
            }
        });
        txtNumeroDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroDocumentoKeyTyped(evt);
            }
        });

        btnTipoDeDocumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnTipoDeDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipoDeDocumentoActionPerformed(evt);
            }
        });

        txtApellido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtApellido.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtApellido.setNextFocusableComponent(txtNombre);
        txtApellido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtApellidoMouseClicked(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombre.setNextFocusableComponent(jdcFecha);
        txtNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreMouseClicked(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel16.setText("Fecha de nacimiento:");

        jdcFecha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jdcFecha.setNextFocusableComponent(cmbSexo);

        cmbSexo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbSexo.setNextFocusableComponent(cmbEstadoCivil);

        jLabel22.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel22.setText("Sexo:");

        jLabel23.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel23.setText("Estado civil:");

        cmbEstadoCivil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbEstadoCivil.setNextFocusableComponent(btnNacionalidad);

        cmbNacionalidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel36.setText("Nacionalidad:");

        btnNacionalidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnNacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNacionalidadActionPerformed(evt);
            }
        });

        btnEstadoCivil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnEstadoCivil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoCivilActionPerformed(evt);
            }
        });

        btnSexo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnSexo.setToolTipText("");
        btnSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSexoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel21)
                                .addComponent(jLabel15)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel14)))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtApellido, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(cmbTipoDeDocumento, 0, 163, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTipoDeDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNombre)
                    .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(9, 70, Short.MAX_VALUE)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(cmbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(cmbNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel21))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbTipoDeDocumento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTipoDeDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(8, 8, 8)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(4, 4, 4)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15)
                                .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel22)
                                .addComponent(cmbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel36)
                                .addComponent(cmbNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
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
        btnGuardarModificaciones.setText("<html><p>Guardar</p><p>Modificaciones</p></html>");
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

        javax.swing.GroupLayout jXTaskPane1Layout = new javax.swing.GroupLayout(jXTaskPane1.getContentPane());
        jXTaskPane1.getContentPane().setLayout(jXTaskPane1Layout);
        jXTaskPane1Layout.setHorizontalGroup(
            jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addComponent(btnGuardarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jXTaskPane1Layout.setVerticalGroup(
            jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jXTaskPane2.setCollapsed(true);
        jXTaskPane2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search.png"))); // NOI18N
        jXTaskPane2.setTitle("Buscar un Cliente");

        SearchCliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SearchCliente.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                SearchClienteCaretUpdate(evt);
            }
        });

        cmbOpcionBusqueda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbOpcionBusqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N° Documento", "Apellido", "Nombre" }));

        javax.swing.GroupLayout jXTaskPaneContainer3Layout = new javax.swing.GroupLayout(jXTaskPaneContainer3);
        jXTaskPaneContainer3.setLayout(jXTaskPaneContainer3Layout);
        jXTaskPaneContainer3Layout.setHorizontalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                .addComponent(cmbOpcionBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SearchCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jXTaskPaneContainer3Layout.setVerticalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbOpcionBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jXTaskPane2Layout = new javax.swing.GroupLayout(jXTaskPane2.getContentPane());
        jXTaskPane2.getContentPane().setLayout(jXTaskPane2Layout);
        jXTaskPane2Layout.setHorizontalGroup(
            jXTaskPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jXTaskPane2Layout.setVerticalGroup(
            jXTaskPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane2Layout.createSequentialGroup()
                .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jXTaskPaneContainer2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaDirecciones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaDirecciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dirección", "Barrio", "Ciudad", "Provincia", "Pais"
            }
        ));
        tablaDirecciones.setRowHeight(20);
        jScrollPane3.setViewportView(tablaDirecciones);

        tablaTelefonos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaTelefonos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Telefónico", "Tipo de Teléfono"
            }
        ));
        tablaTelefonos.setRowHeight(20);
        jScrollPane2.setViewportView(tablaTelefonos);

        tablaClientes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° de Documento", "Nombre", "Apellido"
            }
        ));
        tablaClientes.setRowHeight(20);
        tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaClientes);

        javax.swing.GroupLayout jXTaskPaneContainer2Layout = new javax.swing.GroupLayout(jXTaskPaneContainer2);
        jXTaskPaneContainer2.setLayout(jXTaskPaneContainer2Layout);
        jXTaskPaneContainer2Layout.setHorizontalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jXTaskPaneContainer2Layout.setVerticalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jXTaskPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:
        btnBuscarChofer.setEnabled(false);
        btnTelefonos.setEnabled(false);
        btnDomicilioa.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        btnModificarCliente.setEnabled(false);
        btnNuevoCliente.setEnabled(false);
        jXTaskPane1.setCollapsed(false);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(true);
        tablaClientes.setEnabled(false);
        tablaDirecciones.setEnabled(false);
        tablaTelefonos.setEnabled(false);
        txtNumeroDocumento.setEnabled(true);
        cmbTipoDeDocumento.setEnabled(true);
        btnTipoDeDocumento.setEnabled(true);
        txtApellido.setEnabled(true);
        txtNombre.setEnabled(true);
        jdcFecha.setEnabled(true);
        cmbSexo.setEnabled(true);
        btnSexo.setEnabled(true);
        cmbEstadoCivil.setEnabled(true);
        btnEstadoCivil.setEnabled(true);
        cmbNacionalidad.setEnabled(true);
        btnNacionalidad.setEnabled(true);           
        txtNumeroDocumento.requestFocus();
        this.limpiar();
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        // TODO add your handling code here:
        if(tablaClientes.getSelectedRow()!=-1)
        {
            this.eliminarCliente();
            
            this.limpiar();

        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Cliente"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnModificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarClienteActionPerformed
        // TODO add your handling code here:
        if(tablaClientes.getSelectedRow()!=-1)
        {
            this.obtenerCliente();
            btnBuscarChofer.setEnabled(false);
            btnTelefonos.setEnabled(false);
            btnDomicilioa.setEnabled(false);
            btnEliminarCliente.setEnabled(false);
            btnModificarCliente.setEnabled(false);
            btnNuevoCliente.setEnabled(false);
            jXTaskPane1.setCollapsed(false);
            btnGuardarModificaciones.setEnabled(true);
            btnGuardarChofer.setEnabled(false);
            tablaClientes.setEnabled(false);
            tablaDirecciones.setEnabled(false);
            tablaTelefonos.setEnabled(false);  
            txtNumeroDocumento.setEnabled(true);
            cmbTipoDeDocumento.setEnabled(true);
            btnTipoDeDocumento.setEnabled(true);
            txtApellido.setEnabled(true);
            txtNombre.setEnabled(true);
            jdcFecha.setEnabled(true);
            cmbSexo.setEnabled(true);
            btnSexo.setEnabled(true);
            cmbEstadoCivil.setEnabled(true);
            btnEstadoCivil.setEnabled(true);
            cmbNacionalidad.setEnabled(true);
            btnNacionalidad.setEnabled(true); 
            txtNumeroDocumento.requestFocus();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Cliente para realizar la modificación", null, JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnModificarClienteActionPerformed

    private void tablaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClientesMouseClicked
        // TODO add your handling code here:
        this.obtenerCliente();
        this.obtenerDomicilios();
        this.obtenerTelefonos();
        
    }//GEN-LAST:event_tablaClientesMouseClicked
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
    private void txtNumeroDocumentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroDocumentoKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 9;
        if(txtNumeroDocumento.getText().length()== limite)
        {
            evt.consume();
        }  
         if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        } 
    }//GEN-LAST:event_txtNumeroDocumentoKeyTyped

    private void btnTipoDeDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipoDeDocumentoActionPerformed
        // TODO add your handling code here:
        JDialogTipoDni ventana = new JDialogTipoDni(parent, modal, cmbTipoDeDocumento, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnTipoDeDocumentoActionPerformed

    private void btnNacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNacionalidadActionPerformed
        // TODO add your handling code here:
        JDialogNacionalidad ventana = new JDialogNacionalidad(parent,modal,cmbNacionalidad,remiseria);
        ventana.show();
    }//GEN-LAST:event_btnNacionalidadActionPerformed

    private void btnEstadoCivilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoCivilActionPerformed
        // TODO add your handling code here:
        JDialogEstadoCivil ventana = new JDialogEstadoCivil(parent,modal,cmbEstadoCivil,remiseria);
        ventana.show();
    }//GEN-LAST:event_btnEstadoCivilActionPerformed

    private void btnSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSexoActionPerformed
        // TODO add your handling code here:
        JDialogSexo ventana = new JDialogSexo(parent, modal, cmbSexo, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnSexoActionPerformed

    private void btnDomicilioaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDomicilioaActionPerformed
       if(tablaClientes.getSelectedRow() != -1)
       {
        JDialogEditarDomicilios ventana = new JDialogEditarDomicilios(parent, modal, remiseria, this.obtenerElCliente(),tablaDirecciones);
        ventana.show();
       }
       else
       {
           JOptionPane.showMessageDialog(null,"No ha seleccionado un Cliente"," ",JOptionPane.ERROR_MESSAGE);
       }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDomicilioaActionPerformed

    private void btnTelefonosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTelefonosActionPerformed
        // TODO add your handling code here:
       if(tablaClientes.getSelectedRow() != -1)
       {
            JDialogTelefonos ventana = new JDialogTelefonos(parent, modal, remiseria, this.obtenerElCliente(),tablaTelefonos);
            ventana.show();
       }
       else
       {
           JOptionPane.showMessageDialog(null,"No ha seleccionado un Cliente"," ",JOptionPane.ERROR_MESSAGE);
       }
    }//GEN-LAST:event_btnTelefonosActionPerformed

    private void txtNombreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreMouseClicked
        // TODO add your handling code here:
        txtNombre.setBackground(Color.white);
    }//GEN-LAST:event_txtNombreMouseClicked

    private void txtApellidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtApellidoMouseClicked
        // TODO add your handling code here:
        txtApellido.setBackground(Color.white);
    }//GEN-LAST:event_txtApellidoMouseClicked

    private void txtNumeroDocumentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroDocumentoMouseClicked
        // TODO add your handling code here:
        txtNumeroDocumento.setBackground(Color.white);
    }//GEN-LAST:event_txtNumeroDocumentoMouseClicked

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
        char c;
        c = evt.getKeyChar();
        if (!(c < '0' || c > '9'))
        {
            evt.consume();
        }
        int limite  = 20;
        if(txtNombre.getText().length()== limite)
        {
            evt.consume();
        }         
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        // TODO add your handling code here:
        char c;
        c = evt.getKeyChar();
        if (!(c < '0' || c > '9'))
        {
            evt.consume();
        }
        int limite  = 20;
        if(txtApellido.getText().length()== limite)
        {
            evt.consume();
        }  
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void SearchClienteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_SearchClienteCaretUpdate
        // TODO add your handling code here:
        Cliente aux = null;
        List clientes =  remiseria.buscarClientes();
        List filtro = new LinkedList();
        if (!SearchCliente.getText().isEmpty())
        {
            Iterator iter = clientes.iterator();
            while (iter.hasNext())
            {
                aux = (Cliente) iter.next();
                if(cmbOpcionBusqueda.getSelectedItem().equals("N° Documento"))
                {
                    if (String.valueOf(aux.getDni()).toUpperCase().startsWith(SearchCliente.getText().toUpperCase()))
                    {
                        filtro.add(aux);
                    }
                    this.cargarTablaClientesBuscados(tablaClientes, filtro);
                }

                if(cmbOpcionBusqueda.getSelectedItem().equals("Apellido"))
                {
                    if (String.valueOf(aux.getApellido()).toUpperCase().startsWith(SearchCliente.getText().toUpperCase()))
                    {
                        filtro.add(aux);
                    }
                    this.cargarTablaClientesBuscados(tablaClientes, filtro);
                }

                if(cmbOpcionBusqueda.getSelectedItem().equals("Nombre"))
                {
                    if (aux.getNombre().toUpperCase().startsWith(SearchCliente.getText().toUpperCase()))
                    {
                        filtro.add(aux);
                    }
                    this.cargarTablaClientesBuscados(tablaClientes, filtro);
                }

            }
        }
        else
        {
            this.cargarClientes();
            // this.cargarTablaChoferesBuscados(tablaChoferes, filtro);
        }
    }//GEN-LAST:event_SearchClienteCaretUpdate

    private void btnGuardarChoferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarChoferActionPerformed
        // TODO add your handling code here:
        this.agregarCliente();
    }//GEN-LAST:event_btnGuardarChoferActionPerformed

    private void btnGuardarModificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionesActionPerformed
        // TODO add your handling code here:
        this.modificarCliente();
    }//GEN-LAST:event_btnGuardarModificacionesActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
            btnTelefonos.setEnabled(true);
            btnDomicilioa.setEnabled(true);
            btnEliminarCliente.setEnabled(true);
            btnModificarCliente.setEnabled(true);
            btnNuevoCliente.setEnabled(true);
            jXTaskPane1.setCollapsed(true);
            btnGuardarModificaciones.setEnabled(false);
            btnGuardarChofer.setEnabled(false);
            tablaClientes.setEnabled(true);
            tablaDirecciones.setEnabled(true);
            tablaTelefonos.setEnabled(true); 
            txtNumeroDocumento.setEnabled(false);
            cmbTipoDeDocumento.setEnabled(false);
            btnTipoDeDocumento.setEnabled(false);
            txtApellido.setEnabled(false);
            txtNombre.setEnabled(false);
            jdcFecha.setEnabled(false);
            cmbSexo.setEnabled(false);
            btnSexo.setEnabled(false);
            cmbEstadoCivil.setEnabled(false);
            btnEstadoCivil.setEnabled(false);
            cmbNacionalidad.setEnabled(false);
            btnNacionalidad.setEnabled(false);  
            btnBuscarChofer.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarChoferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarChoferActionPerformed
        // TODO add your handling code here:
        jXTaskPane2.setCollapsed(false);
    }//GEN-LAST:event_btnBuscarChoferActionPerformed

    private void btnBuscarMóvil1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMóvil1ActionPerformed
        // TODO add your handling code here:
        try{
        try{
            
            List historial = remiseria.buscarClientes();
            if(historial!= null)
            {
                LinkedList <ImprimirClientes> lista = new LinkedList<ImprimirClientes>();
                Cliente aux = null;
                Iterator iter = historial.iterator();
                while (iter.hasNext())
                {
                    aux = (Cliente) iter.next();
                    String dni = String.valueOf(aux.getDni());
                    String nombre = String.valueOf(aux.getNombre());
                    String apellido =aux.getApellido();
                    String fechaNacimiento = utilidades.getFecha(aux.getFechaNacimiento());
                    Collection domicilios = aux.getDomicilios().values();
                    Collection telefonos = aux.getTelefonos().values();
                    Domicilio unDomicilio = null;
                    Telefono unTelefono = null;
                    String telefono = null;
                     Iterator iter3 = telefonos.iterator();
                    while(iter3.hasNext())
                    {
                        unTelefono = (Telefono) iter3.next();
                        telefono  = unTelefono.toString();
                        break;
                    }
                    Iterator iter2 = domicilios.iterator();

                    String domicilio = null;

                    while (iter2.hasNext())
                    {
                        unDomicilio =(Domicilio) iter2.next();
                        domicilio = unDomicilio.getUnPais()+"-"+unDomicilio.getUnaProvincia()+"-"+unDomicilio.getUnaCiudad()+"-"+unDomicilio.getUnBarrio()+"-Calle "+unDomicilio.getCalle()+" N° "+unDomicilio.getNroCasa();
                        lista.add(new ImprimirClientes(dni,nombre,apellido,fechaNacimiento,domicilio,telefono));
                    }
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
                //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Clientes.jrxml");
                JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Clientes.jrxml");
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
                JOptionPane.showMessageDialog(null,"No hay Información de Clientes para realizar el reporte");
            }
        }
        catch (JRException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al realizar el reporte de clientes, no hay Información de clientes, direcciones o teléfonos");
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
            java.util.logging.Logger.getLogger(JDialogCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogCliente dialog = new JDialogCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField SearchCliente;
    private javax.swing.JButton btnBuscarChofer;
    private javax.swing.JButton btnBuscarMóvil1;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDomicilioa;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEstadoCivil;
    private javax.swing.JButton btnGuardarChofer;
    private javax.swing.JButton btnGuardarModificaciones;
    private javax.swing.JButton btnModificarCliente;
    private javax.swing.JButton btnNacionalidad;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSexo;
    private javax.swing.JButton btnTelefonos;
    private javax.swing.JButton btnTipoDeDocumento;
    private javax.swing.JComboBox cmbEstadoCivil;
    private javax.swing.JComboBox cmbNacionalidad;
    private javax.swing.JComboBox cmbOpcionBusqueda;
    private javax.swing.JComboBox cmbSexo;
    private javax.swing.JComboBox cmbTipoDeDocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaDirecciones;
    private javax.swing.JTable tablaTelefonos;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumeroDocumento;
    // End of variables declaration//GEN-END:variables
}
