/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Barrio;
import Clases.Ciudad;
import Clases.CodArea;
import Clases.CodPais;
import Clases.Domicilio;
import Clases.EstadoCivil;
import Clases.ImprimirOperarios;
import Clases.Maestro;
import Clases.Nacionalidad;
import Clases.Operario;
import Clases.Remiseria;
import Clases.Operario;
import Clases.Pais;
import Clases.Provincia;
import Clases.Rol;
import Clases.Sexo;
import Clases.Telefono;
import Clases.TipoDni;
import Clases.TipoTelefono;
import Clases.Utilidad;
import static Interface.JInternalFrameViaje.esEntero;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
public class JDialogOperario extends javax.swing.JDialog {
private Remiseria remiseria;
private Utilidad utilidades;
private java.awt.Frame parent;
private boolean modal;
private Maestro unMaestro;
private Operario unOperario;
    /**
     * Creates new form JDialogOperario
     */
    public JDialogOperario(java.awt.Frame parent, boolean modal, Remiseria remiseria,Utilidad utilidades, Maestro unMaestro, Operario unOperario) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unOperario = unOperario;
        this.unMaestro = unMaestro;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarOperarios();
        this.cargarTiposDocumentos();
        this.cargarSexosCombo();
        this.cargarEstadosCivilesCombo();
        this.cargarNacionalidadesCombo();
        this.cargarPaisCombo();
        this.cargarCodPaisCombo();
        this.cargarTipoTelefonoCombo();
        this.cargarComboRoles();
        AutoCompleteDecorator.decorate(this.cmbPais);
        AutoCompleteDecorator.decorate(this.cmbProvincia);
        AutoCompleteDecorator.decorate(this.cmbCiudad);
        AutoCompleteDecorator.decorate(this.cmbBarrio);        
        AutoCompleteDecorator.decorate(this.cmbCodPais);
        AutoCompleteDecorator.decorate(this.cmbCodArea);
        AutoCompleteDecorator.decorate(this.cmbTipodeTelefono);
        AutoCompleteDecorator.decorate(this.cmbSexo);
        AutoCompleteDecorator.decorate(this.cmbTipoDeDocumento);
        AutoCompleteDecorator.decorate(this.cmbNacionalidad);
        AutoCompleteDecorator.decorate(this.cmbEstadoCivil);
        jdcFecha.setPreferredSize(new Dimension(95,20));
        jXTaskPane1.setCollapsed(true);
        btnAgregar.setEnabled(true);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnRoles.setEnabled(true);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);
        btnCancelar1.setEnabled(true);
        tablaOperarios.setEnabled(true);
        btnBuscarChofer.setEnabled(true); 
        txtNumeroDocumento.setEnabled(false);
        cmbTipoDeDocumento.setEnabled(false);
        txtApellido.setEnabled(false);
        txtNombre.setEnabled(false);
        jdcFecha.setEnabled(false);
        cmbSexo.setEnabled(false);
        btnSexo.setEnabled(false);
        cmbEstadoCivil.setEnabled(false);
        btnEstadoCivil.setEnabled(false);
        btnNacionalidad.setEnabled(false);
        cmbNacionalidad.setEnabled(false);
        cmbPais.setEnabled(false);
        btnPais.setEnabled(false);
        cmbProvincia.setEnabled(false);
        btnProvincia.setEnabled(false);
        cmbCiudad.setEnabled(false);
        bnCiudad.setEnabled(false);
        cmbBarrio.setEnabled(false);
        btnBarrio.setEnabled(false);
        txtCalle.setEnabled(false);
        txtNumeroCasa.setEnabled(false);
        txtDpto.setEnabled(false);
        txtPiso.setEnabled(false);
        cmbCodPais.setEnabled(false);
        btnCodPais.setEnabled(false);
        cmbCodArea.setEnabled(false);
        btnCodArea.setEnabled(false);
        txtNumeroTel.setEnabled(false);
        cmbTipodeTelefono.setEnabled(false);
        btnTipoTelefono.setEnabled(false);
        txtNick.setEnabled(false);
        txtClave.setEnabled(false);
        cmbRol.setEnabled(false);      
        
        try
        {
            tablaOperarios.setRowSelectionInterval(0, 0);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No se hay Operarios registrados aún", null, JOptionPane.WARNING_MESSAGE);
        }        
    }
    
    public  void cargarOperarios(){
        Collection usuarios = remiseria.getOperarios().values(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaOperarios.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 11); 
        th.setBackground(java.awt.Color.white);
        th.setForeground(java.awt.Color.black);
        th.setFont(fuente);         
        modelo.addColumn("N° Documento");
        modelo.addColumn("Usuario");
        modelo.addColumn("Rol");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Teléfono");
        Operario aux = null;
        Iterator iter = usuarios.iterator();
        while (iter.hasNext()){
            aux = (Operario) iter.next();
            Object [] fila = new Object[6];
            fila[0] = aux.getDni();
            fila[1] = aux.getId();
            if(aux.getUnRol()!= null)
            {
                fila[2] = aux.getUnRol().getNombreRol();
            }
            fila[3] = aux.getNombre();
            fila[4] = aux.getApellido();
            if(aux.getUnTelefono()!= null){
                fila[5] ="("+ aux.getUnTelefono().getUnCodPais().getCodPais()+") "+ aux.getUnTelefono().getUnCodArea().getCodArea()+" "+aux.getUnTelefono().getNroTelefonico() ;            
            }
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaOperarios.setModel(modelo);
    }
 
public static void limpiar_tabla(JTable tabla) {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }
    
    public static void cargarTablaOperariosBuscados(JTable tabla, List lista) {
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
        th.setForeground(java.awt.Color.black);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);           
        modelo.addColumn("N° Documento");
        modelo.addColumn("Usuario");
        modelo.addColumn("Rol");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Teléfono");
        Operario aux = null;
        Iterator iter = lista.iterator();
        while (iter.hasNext()){
            aux = (Operario) iter.next();
            Object [] fila = new Object[6];
            fila[0] = aux.getDni();
            fila[1] = aux.getId();
            if(aux.getUnRol()!= null)
            {
                fila[2] = aux.getUnRol().getNombreRol();
            }
            fila[3] = aux.getNombre();
            fila[4] = aux.getApellido();
            if(aux.getUnTelefono()!= null){
                fila[5] ="("+ aux.getUnTelefono().getUnCodPais().getCodPais()+") "+ aux.getUnTelefono().getUnCodArea().getCodArea()+" "+aux.getUnTelefono().getNroTelefonico() ;            
            }
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tabla.setModel(modelo);
    }    
    
    
    
    public void obtenerOperario(){
        int fila = (int) tablaOperarios.getSelectedRow();
        int dni = (int) tablaOperarios.getValueAt(fila, 0);
        Operario unUsuario = remiseria.buscarOperario(dni);
            if(unUsuario != null)
            {
                txtNumeroDocumento.setText(String.valueOf(unUsuario.getDni()));
                cmbTipoDeDocumento.setSelectedItem(""+unUsuario.getUnTipoDni().getSiglas());
                txtApellido.setText(unUsuario.getApellido());
                txtNombre.setText(unUsuario.getNombre());
                jdcFecha.setDate(unUsuario.getFechaNacimiento());
                cmbSexo.setSelectedItem(""+unUsuario.getUnSexo().getNombreSexo());
                cmbEstadoCivil.setSelectedItem(unUsuario.getUnEstadoCivil().getNombreEstadoCivil());
                cmbNacionalidad.setSelectedItem(unUsuario.getUnaNacionalidad().getNombreNacionalidad());
                cmbPais.setSelectedItem(unUsuario.getUnDomicilio().getUnPais().getNombrePais());
                cmbProvincia.setSelectedItem(unUsuario.getUnDomicilio().getUnaProvincia().getNombreProvincia());
                cmbCiudad.setSelectedItem(unUsuario.getUnDomicilio().getUnaCiudad().getNombreCiudad());
                cmbBarrio.setSelectedItem(unUsuario.getUnDomicilio().getUnBarrio().getNombreBarrio());
                txtCalle.setText(unUsuario.getUnDomicilio().getCalle());
                txtNumeroCasa.setText(""+ unUsuario.getUnDomicilio().getNroCasa());
                txtDpto.setText(""+unUsuario.getUnDomicilio().getDpto());
                txtPiso.setText(""+unUsuario.getUnDomicilio().getPiso());
                cmbCodPais.setSelectedItem(unUsuario.getUnTelefono().getUnCodPais().getCodPais());
                cmbCodArea.setSelectedItem(unUsuario.getUnTelefono().getUnCodArea().getCodArea());
                txtNumeroTel.setText(""+ unUsuario.getUnTelefono().getNroTelefonico());
                cmbTipodeTelefono.setSelectedItem(unUsuario.getUnTelefono().getUnTipoTelefono().getNombreTipoTelefono());
                txtNick.setText(unUsuario.getId());
                txtClave.setText(unUsuario.getContrasena());
                cmbRol.setSelectedItem(unUsuario.getUnRol().getNombreRol());
            }
        }

    public void cargarTipoTelefonoCombo(){
        cmbTipodeTelefono.removeAllItems();
        List telefonos =remiseria.buscarTiposTelefonos();
        if(telefonos != null)
        {
            TipoTelefono aux = null;
            Iterator iter = telefonos.iterator();
            while (iter.hasNext())
            {
                aux = (TipoTelefono) iter.next();
                cmbTipodeTelefono.addItem(aux.getNombreTipoTelefono());
            }
        }
    }
    
    public void cargarCodPaisCombo(){
        cmbCodPais.removeAllItems();
        List codigos = remiseria.buscarCodigosPaises();
        if(codigos != null)
        {
            CodPais aux = null;
            Iterator iter = codigos.iterator();
            while (iter.hasNext())
            {
                aux = (CodPais) iter.next();
                cmbCodPais.addItem(aux.getCodPais());
            }
        }
        
    }
    
     public void cargarPaisCombo(){
        cmbPais.removeAllItems();
        List paises = remiseria.buscarPaises();
        if(paises != null)
        {
            Pais aux = null;
            Iterator iter = paises.iterator();
            while (iter.hasNext())
            {
                aux = (Pais) iter.next();
                cmbPais.addItem(aux.getNombrePais());
            }
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
            while (iter.hasNext())
            {
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
    
    public void cargarProvinciasCombo(Pais unPais){
        cmbProvincia.removeAllItems();
        List provincias = unPais.buscarProvincias();
        if(provincias != null)
        {
            Provincia aux = null;
            Iterator iter = provincias.iterator();
            while (iter.hasNext())
            {
                aux = (Provincia) iter.next();
                cmbProvincia.addItem(aux.getNombreProvincia());
            }
        }
    }
    
    public void cargarCiudadesCombo(Provincia unaProvincia)
    {
        cmbCiudad.removeAllItems();
        List ciudades = unaProvincia.buscarCiudades();
        if(ciudades != null)
        {
            Ciudad aux = null;
            Iterator iter = ciudades.iterator();
            while (iter.hasNext())
            {
                aux = (Ciudad) iter.next();
                cmbCiudad.addItem(aux.getNombreCiudad());
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
            while (iter.hasNext())
            {
                aux = (Barrio) iter.next();
                cmbBarrio.addItem(aux.getNombreBarrio());
            }
        }
    }
    
    public void cargarCodigoProvinciaCombo( CodPais unCodPais)
    {
        cmbCodArea.removeAllItems();
        List codigoProvincia = unCodPais.buscarCodigosAreas();
        if(codigoProvincia!= null)
        {
            CodArea aux = null;
            Iterator iter = codigoProvincia.iterator();
            while (iter.hasNext())
            {
                aux = (CodArea) iter.next();
                cmbCodArea.addItem(aux.getCodArea());
            }
        }       
    }    
    
    
    public void cargarComboRoles()
    {
        cmbRol.removeAllItems();
        if(remiseria.getRoles()!= null){
            Collection roles = remiseria.getRoles().values();
            Iterator iter = roles.iterator();
            while (iter.hasNext()){
                Rol unRol = (Rol) iter.next();
                cmbRol.addItem(unRol.getNombreRol());
            }
        }
    }
    
    public void limpiar()
    {
        Operario unOperario = null;
        txtNumeroDocumento.setText("");
        txtNumeroDocumento.setBackground(Color.white);
        txtNick.setText(null);
        txtNick.setBackground(Color.white);
        txtClave.setText(null);
        txtClave.setBackground(Color.white);
        this.cargarComboRoles();
        this.cargarTiposDocumentos();
        txtApellido.setText("");
        txtApellido.setBackground(Color.white);
        txtNombre.setText("");
        txtNombre.setBackground(Color.white);
        jdcFecha.setDate(null);
        this.cargarSexosCombo();
        this.cargarEstadosCivilesCombo();
        this.cargarNacionalidadesCombo();
        this.cargarPaisCombo();
        cmbProvincia.removeAllItems();
        cmbCiudad.removeAllItems();
        cmbBarrio.removeAllItems();
        txtCalle.setText("");
        txtCalle.setBackground(Color.white);
        txtNumeroCasa.setText("");
        txtNumeroCasa.setBackground(Color.white);
        txtPiso.setText("");
        txtPiso.setBackground(Color.white);
        txtDpto.setText("");
        txtDpto.setBackground(Color.white);
        this.cargarCodPaisCombo();
        cmbCodArea.removeAllItems();
        txtNumeroTel.setText("");
        txtNumeroTel.setBackground(Color.white);
        this.cargarTipoTelefonoCombo();   
    }
   
     public void agregarOperario()
     {     
                boolean ok = true;
                String numeroDocumento = txtNumeroDocumento.getText();
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                TipoDni tipoDocumento = null;
                Date fechaNacimiento = jdcFecha.getDate();
                Sexo unSexo = null;
                EstadoCivil unEstadoCivil = null;
                Nacionalidad unaNacionalidad = null;
                Pais unPais = null;
                Provincia unaProvincia = null;
                Ciudad unaCiudad = null;
                Barrio unBarrio = null;
                String calle = txtCalle.getText();
                String numeroCasa = txtNumeroCasa.getText();
                String dpto = txtDpto.getText();
                String piso = txtPiso.getText();
                CodPais unCodPais = null;
                CodArea unCodArea = null;
                String numeroTelefono = txtNumeroTel.getText();                
                TipoTelefono unTipoTelefono = null;  
                String nick = txtNick.getText();
                String contrasena = txtClave.getText();
                Rol unRol = null;
                
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
                

                if(cmbPais.getSelectedItem() != null)
                {
                    unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado el Pais","",JOptionPane.ERROR_MESSAGE);
                    ok= false;                                     
                }
           
                if(cmbProvincia.getSelectedItem() != null || unaProvincia != null)
                {
                    unaProvincia = unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado el Provincia","",JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }
                if(cmbCiudad.getSelectedItem()!=null|| unaCiudad != null)
                {                
                    unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado la Ciudad","",JOptionPane.ERROR_MESSAGE);
                    ok= false;                
                }
                if(cmbBarrio.getSelectedItem()!= null|| unBarrio != null)
                {                
                    unBarrio = unaCiudad.buscarBarrio(cmbBarrio.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado el Barrio","",JOptionPane.ERROR_MESSAGE);
                    ok= false;                
                }

              
               if(txtCalle.getText().toString().isEmpty())
               {
                   JOptionPane.showMessageDialog(null,"No ha ingresado la Calle","",JOptionPane.ERROR_MESSAGE);
                   ok= false;
                   txtCalle.setBackground(Color.red);
               } 

                if(txtNumeroCasa.getText().toString().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"No ha ingresado el Número de Domicilio","",JOptionPane.ERROR_MESSAGE);
                    ok= false;
                    txtNumeroCasa.setBackground(Color.red);
                } 
                               
                if((cmbCodPais.getSelectedItem()!= null))
                {
                   unCodPais = remiseria.buscarCodigoPais(cmbCodPais.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado el Código de País del Teléfono","",JOptionPane.ERROR_MESSAGE);
                     ok = false;
                }
                             
                if(cmbCodArea.getSelectedItem()!= null|| unCodArea != null)
                {
                    unCodArea = unCodPais.buscarCodArea(cmbCodArea.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado el Código de Área","",JOptionPane.ERROR_MESSAGE);
                    ok= false;                 
                }
                
                if(txtNumeroTel.getText().toString().isEmpty())
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el N° Personal del Teléfono","",JOptionPane.ERROR_MESSAGE);
                     ok = false;
                     txtNumeroTel.setBackground(java.awt.Color.red);
                }
                
                if(cmbTipodeTelefono.getSelectedItem()!= null)
                {
                    unTipoTelefono = remiseria.buscarTipoTelefono(cmbTipodeTelefono.getSelectedItem().toString());

                }
                else
                {
                      JOptionPane.showMessageDialog(null,"No ha seleccionado el Tipo de Teléfono","",JOptionPane.ERROR_MESSAGE);
                      ok = false;
                }
                   
           
            Domicilio unDomicilio = new Domicilio (unPais,unaProvincia,unaCiudad,unBarrio,calle,numeroCasa,piso,dpto);
            Telefono unTelefono = new Telefono(numeroTelefono,unCodArea,unCodPais,unTipoTelefono);
            
            if(unDomicilio == null)
            {
                JOptionPane.showMessageDialog(null,"Error al ingresar datos del Domicilio","",JOptionPane.ERROR_MESSAGE);
                ok=false;
            }

            if(unTelefono == null)
            {
                JOptionPane.showMessageDialog(null,"Error al ingresar datos del Teléfono","",JOptionPane.ERROR_MESSAGE);
                ok=false;
            }
            
                   
                if(fechaNacimiento !=null)
                {
                    if ((utilidades.calcularEdad(String.valueOf(utilidades.getFecha(fechaNacimiento)))) <= remiseria.getUnaEdad().getEdadMinimaOperario())
                    {
                              JOptionPane.showMessageDialog(null,"Es menor de "+ remiseria.getUnaEdad().getEdadMinimaOperario() + " años"," ",JOptionPane.ERROR_MESSAGE);
                              ok = false;
                    }
                }

                if(txtNick.getText().isEmpty())
                {
                         JOptionPane.showMessageDialog(null,"No ha ingresado un Nick","",JOptionPane.ERROR_MESSAGE);
                         txtNick.setBackground(Color.red);
                          ok = false;
                }

                if(txtClave.getText().isEmpty())
                {
                         JOptionPane.showMessageDialog(null,"No ha ingresado una Clave","",JOptionPane.ERROR_MESSAGE);
                         txtClave.setBackground(Color.red);
                         ok = false;
                }    

                if (cmbRol.getSelectedItem() != null)
                {
                    unRol = remiseria.buscarUnRol(cmbRol.getSelectedItem().toString());
                }
                else
                {     
                    JOptionPane.showMessageDialog(null,"No ha ingresado una Rol","",JOptionPane.ERROR_MESSAGE);
                    ok= false;
                }

                if(utilidades.isNumber(numeroDocumento)== true)
                {
                    int dni = Integer.parseInt(txtNumeroDocumento.getText());
                    if(remiseria.nroOperarioLibre(dni) == false)
                    {
                        if(remiseria.buscarIdOperario(nick) == null)
                        {
                            if(ok == true)
                            {
                                remiseria.agregarOperario(nick, contrasena, dni, tipoDocumento, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento, unTelefono, unRol);
                                JOptionPane.showMessageDialog(null,"Se registró un nuevo Operario ","",JOptionPane.INFORMATION_MESSAGE);
                                this.cargarOperarios();
                                jXTaskPane1.setCollapsed(true);
                                btnAgregar.setEnabled(true);
                                btnModificar.setEnabled(true);
                                btnEliminar.setEnabled(true);
                                btnRoles.setEnabled(true);
                                btnGuardarModificaciones.setEnabled(false);
                                btnGuardarChofer.setEnabled(false);
                                btnCancelar1.setEnabled(true);
                                tablaOperarios.setEnabled(true);
                                  btnBuscarChofer.setEnabled(true);
                                txtNumeroDocumento.setEnabled(false);
                                cmbTipoDeDocumento.setEnabled(false);
                                txtApellido.setEnabled(false);
                                txtNombre.setEnabled(false);
                                jdcFecha.setEnabled(false);
                                cmbSexo.setEnabled(false);
                                btnSexo.setEnabled(false);
                                cmbEstadoCivil.setEnabled(false);
                                btnEstadoCivil.setEnabled(false);
                                btnNacionalidad.setEnabled(false);
                                cmbNacionalidad.setEnabled(false);
                                cmbPais.setEnabled(false);
                                btnPais.setEnabled(false);
                                cmbProvincia.setEnabled(false);
                                btnProvincia.setEnabled(false);
                                cmbCiudad.setEnabled(false);
                                bnCiudad.setEnabled(false);
                                cmbBarrio.setEnabled(false);
                                btnBarrio.setEnabled(false);
                                txtCalle.setEnabled(false);
                                txtNumeroCasa.setEnabled(false);
                                txtDpto.setEnabled(false);
                                txtPiso.setEnabled(false);
                                cmbCodPais.setEnabled(false);
                                btnCodPais.setEnabled(false);
                                cmbCodArea.setEnabled(false);
                                btnCodArea.setEnabled(false);
                                txtNumeroTel.setEnabled(false);
                                cmbTipodeTelefono.setEnabled(false);
                                btnTipoTelefono.setEnabled(false);
                                txtNick.setEnabled(false);
                                txtClave.setEnabled(false);
                                cmbRol.setEnabled(false);    
                                tablaOperarios.setRowSelectionInterval(0, 0);
                                this.limpiar();
                            }
                        }
                        else
                        {
                             JOptionPane.showMessageDialog(null,"El Nick "+ nick + " ya existe","",JOptionPane.ERROR_MESSAGE);
                             txtNick.setText(null);
                             txtNick.setBackground(java.awt.Color.red);
                        } 
                    }
                    else
                    {
                             JOptionPane.showMessageDialog(null,"El N° de Documento "+ dni +"  ya existe" ,"",JOptionPane.ERROR_MESSAGE);
                             txtNumeroDocumento.setText(null);
                             txtNumeroDocumento.setBackground(java.awt.Color.red);
                    }
                }

     }
     
      public void modificarOperario()
      {
                boolean ok = true;
                int fila = (int) tablaOperarios.getSelectedRow();
                int numeroOperario = (int) tablaOperarios.getValueAt(fila, 0);
                Operario unOperario = remiseria.buscarOperario(numeroOperario);
                String numeroDocumento = txtNumeroDocumento.getText();
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                TipoDni tipoDocumento = null;
                Date fechaNacimiento = jdcFecha.getDate();
                Sexo unSexo = null;
                EstadoCivil unEstadoCivil = null;
                Nacionalidad unaNacionalidad = null;
                Pais unPais = null;
                Provincia unaProvincia = null;
                Ciudad unaCiudad = null;
                Barrio unBarrio = null;
                String calle = txtCalle.getText();
                String numeroCasa = txtNumeroCasa.getText();
                String dpto = txtDpto.getText();
                String piso = txtPiso.getText();
                CodPais unCodPais = null;
                CodArea unCodArea = null;
                String numeroTelefono = txtNumeroTel.getText();                
                TipoTelefono unTipoTelefono = null;  
                String nick = txtNick.getText();
                String contrasena = txtClave.getText();
                Rol unRol = null;
                
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
                

                if(cmbPais.getSelectedItem() != null)
                {
                    unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado el Pais","",JOptionPane.ERROR_MESSAGE);
                    ok= false;                                     
                }
           
                if(cmbProvincia.getSelectedItem() != null || unaProvincia != null)
                {
                    unaProvincia = unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado el Provincia","",JOptionPane.ERROR_MESSAGE);
                    ok= false; 
                }
                if(cmbCiudad.getSelectedItem()!=null|| unaCiudad != null)
                {                
                    unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado la Ciudad","",JOptionPane.ERROR_MESSAGE);
                    ok= false;                
                }
                if(cmbBarrio.getSelectedItem()!= null|| unBarrio != null)
                {                
                    unBarrio = unaCiudad.buscarBarrio(cmbBarrio.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado el Barrio","",JOptionPane.ERROR_MESSAGE);
                    ok= false;                
                }

              
               if(txtCalle.getText().toString().isEmpty())
               {
                   JOptionPane.showMessageDialog(null,"No ha ingresado la Calle","",JOptionPane.ERROR_MESSAGE);
                   ok= false;
                   txtCalle.setBackground(Color.red);
               } 

                if(txtNumeroCasa.getText().toString().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"No ha ingresado el Número de Domicilio","",JOptionPane.ERROR_MESSAGE);
                    ok= false;
                    txtNumeroCasa.setBackground(Color.red);
                } 
                               
                if((cmbCodPais.getSelectedItem()!= null))
                {
                   unCodPais = remiseria.buscarCodigoPais(cmbCodPais.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado el Código de País del Teléfono","",JOptionPane.ERROR_MESSAGE);
                     ok = false;
                }
                             
                if(cmbCodArea.getSelectedItem()!= null|| unCodArea != null)
                {
                    unCodArea = unCodPais.buscarCodArea(cmbCodArea.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado el Código de Área","",JOptionPane.ERROR_MESSAGE);
                    ok= false;                 
                }
                
                if(txtNumeroTel.getText().toString().isEmpty())
                {
                     JOptionPane.showMessageDialog(null,"No ha ingresado el N° Personal del Teléfono","",JOptionPane.ERROR_MESSAGE);
                     ok = false;
                     txtNumeroTel.setBackground(java.awt.Color.red);
                }
                
                if(cmbTipodeTelefono.getSelectedItem()!= null)
                {
                    unTipoTelefono = remiseria.buscarTipoTelefono(cmbTipodeTelefono.getSelectedItem().toString());

                }
                else
                {
                      JOptionPane.showMessageDialog(null,"No ha seleccionado el Tipo de Teléfono","",JOptionPane.ERROR_MESSAGE);
                      ok = false;
                }
                   
           
            Domicilio unDomicilio = new Domicilio (unPais,unaProvincia,unaCiudad,unBarrio,calle,numeroCasa,piso,dpto);
            Telefono unTelefono = new Telefono(numeroTelefono,unCodArea,unCodPais,unTipoTelefono);
            
            if(unDomicilio == null)
            {
                JOptionPane.showMessageDialog(null,"Error al ingresar datos del Domicilio","",JOptionPane.ERROR_MESSAGE);
                ok=false;
            }

            if(unTelefono == null)
            {
                JOptionPane.showMessageDialog(null,"Error al ingresar datos del Teléfono","",JOptionPane.ERROR_MESSAGE);
                ok=false;
            }
            
                   
                if(fechaNacimiento !=null)
                {
                    if ((utilidades.calcularEdad(String.valueOf(utilidades.getFecha(fechaNacimiento)))) <= remiseria.getUnaEdad().getEdadMinimaOperario())
                    {
                              JOptionPane.showMessageDialog(null,"Es menor de "+ remiseria.getUnaEdad().getEdadMinimaOperario() + " años"," ",JOptionPane.ERROR_MESSAGE);
                              ok = false;
                    }
                }

                if(txtNick.getText().isEmpty())
                {
                         JOptionPane.showMessageDialog(null,"No ha ingresado un Nick","",JOptionPane.ERROR_MESSAGE);
                         txtNick.setBackground(Color.red);
                          ok = false;
                }

                if(txtClave.getText().isEmpty())
                {
                         JOptionPane.showMessageDialog(null,"No ha ingresado una Clave","",JOptionPane.ERROR_MESSAGE);
                         txtClave.setBackground(Color.red);
                         ok = false;
                }    

                if (cmbRol.getSelectedItem() != null)
                {
                    unRol = remiseria.buscarUnRol(cmbRol.getSelectedItem().toString());
                }
                else
                {     
                    JOptionPane.showMessageDialog(null,"No ha ingresado una Rol","",JOptionPane.ERROR_MESSAGE);
                    ok= false;
                }
            
            
            if(unOperario == null)
            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado un Operario","",JOptionPane.ERROR_MESSAGE);
                ok= false;                           
            }
            if(utilidades.isNumber(numeroDocumento)== true)
            {
                int dni = Integer.parseInt(txtNumeroDocumento.getText());
                if(unOperario.getDni()== dni)
                {
                    if(unOperario.getId().equals(nick))
                    {
                        if(ok == true)
                        {
                            remiseria.modificarOperario(unOperario, nick, contrasena, dni, tipoDocumento, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento, unTelefono, unRol);
                            JOptionPane.showMessageDialog(null,"Se modificó un nuevo Operario ","",JOptionPane.INFORMATION_MESSAGE);
                            this.cargarOperarios(); 
                            jXTaskPane1.setCollapsed(true);
                            btnAgregar.setEnabled(true);
                            btnModificar.setEnabled(true);
                            btnEliminar.setEnabled(true);
                            btnRoles.setEnabled(true);
                            btnGuardarModificaciones.setEnabled(false);
                            btnGuardarChofer.setEnabled(false);
                            btnCancelar1.setEnabled(true);
                            tablaOperarios.setEnabled(true);

                            txtNumeroDocumento.setEnabled(false);
                            cmbTipoDeDocumento.setEnabled(false);
                            txtApellido.setEnabled(false);
                            txtNombre.setEnabled(false);
                            jdcFecha.setEnabled(false);
                            cmbSexo.setEnabled(false);
                            btnSexo.setEnabled(false);
                            cmbEstadoCivil.setEnabled(false);
                            btnEstadoCivil.setEnabled(false);
                            btnNacionalidad.setEnabled(false);
                            cmbNacionalidad.setEnabled(false);
                            cmbPais.setEnabled(false);
                            btnPais.setEnabled(false);
                            cmbProvincia.setEnabled(false);
                            btnProvincia.setEnabled(false);
                            cmbCiudad.setEnabled(false);
                            bnCiudad.setEnabled(false);
                            cmbBarrio.setEnabled(false);
                            btnBarrio.setEnabled(false);
                            txtCalle.setEnabled(false);
                            txtNumeroCasa.setEnabled(false);
                            txtDpto.setEnabled(false);
                            txtPiso.setEnabled(false);
                            cmbCodPais.setEnabled(false);
                            btnCodPais.setEnabled(false);
                            cmbCodArea.setEnabled(false);
                            btnCodArea.setEnabled(false);
                            txtNumeroTel.setEnabled(false);
                            cmbTipodeTelefono.setEnabled(false);
                            btnTipoTelefono.setEnabled(false);
                            txtNick.setEnabled(false);
                            txtClave.setEnabled(false);
                            cmbRol.setEnabled(false);   
                            btnBuscarChofer.setEnabled(true);
                            tablaOperarios.setRowSelectionInterval(0, 0);
                            this.limpiar();
                        }
                    }
                    else
                    {
                        if(remiseria.buscarIdOperario(nick) == null)
                        {
                            if(ok == true)
                            {
                                remiseria.modificarOperario(unOperario, nick, contrasena, dni, tipoDocumento, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento, unTelefono, unRol);
                                JOptionPane.showMessageDialog(null,"Se modificó un nuevo Operario ","",JOptionPane.INFORMATION_MESSAGE);
                                this.cargarOperarios();  
                                jXTaskPane1.setCollapsed(true);
                                btnAgregar.setEnabled(true);
                                btnModificar.setEnabled(true);
                                btnEliminar.setEnabled(true);
                                btnRoles.setEnabled(true);
                                btnBuscarChofer.setEnabled(true);                       
                                btnGuardarModificaciones.setEnabled(false);
                                btnGuardarChofer.setEnabled(false);
                                btnCancelar1.setEnabled(true);
                                tablaOperarios.setEnabled(true);
                                tablaOperarios.setRowSelectionInterval(0, 0);
                                txtNumeroDocumento.setEnabled(false);
                                cmbTipoDeDocumento.setEnabled(false);
                                txtApellido.setEnabled(false);
                                txtNombre.setEnabled(false);
                                jdcFecha.setEnabled(false);
                                cmbSexo.setEnabled(false);
                                btnSexo.setEnabled(false);
                                cmbEstadoCivil.setEnabled(false);
                                btnEstadoCivil.setEnabled(false);
                                btnNacionalidad.setEnabled(false);
                                cmbNacionalidad.setEnabled(false);
                                cmbPais.setEnabled(false);
                                btnPais.setEnabled(false);
                                cmbProvincia.setEnabled(false);
                                btnProvincia.setEnabled(false);
                                cmbCiudad.setEnabled(false);
                                bnCiudad.setEnabled(false);
                                cmbBarrio.setEnabled(false);
                                btnBarrio.setEnabled(false);
                                txtCalle.setEnabled(false);
                                txtNumeroCasa.setEnabled(false);
                                txtDpto.setEnabled(false);
                                txtPiso.setEnabled(false);
                                cmbCodPais.setEnabled(false);
                                btnCodPais.setEnabled(false);
                                cmbCodArea.setEnabled(false);
                                btnCodArea.setEnabled(false);
                                txtNumeroTel.setEnabled(false);
                                cmbTipodeTelefono.setEnabled(false);
                                btnTipoTelefono.setEnabled(false);
                                txtNick.setEnabled(false);
                                txtClave.setEnabled(false);
                                cmbRol.setEnabled(false);                                  
                                this.limpiar();
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"El Nick "+ nick + " ya existe","",JOptionPane.ERROR_MESSAGE);
                            txtNick.setText(null);
                            txtNick.setBackground(java.awt.Color.red);
                        } 
                    } 
                }
                else
                {
                    if(remiseria.buscarOperario(dni) == null)
                    {                
                       if(unOperario.getId().equals(nick))
                       {    
                            if(ok == true)
                            {
                                remiseria.modificarOperario(unOperario, nick, contrasena, dni, tipoDocumento, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento, unTelefono, unRol);
                                JOptionPane.showMessageDialog(null,"Se modificó un nuevo Operario ","",JOptionPane.INFORMATION_MESSAGE);
                                this.cargarOperarios(); 
                                btnBuscarChofer.setEnabled(true);                                 
                                jXTaskPane1.setCollapsed(true);
                                btnAgregar.setEnabled(true);
                                btnModificar.setEnabled(true);
                                btnEliminar.setEnabled(true);
                                btnRoles.setEnabled(true);
                                btnGuardarModificaciones.setEnabled(false);
                                btnGuardarChofer.setEnabled(false);
                                btnCancelar1.setEnabled(true);
                                tablaOperarios.setEnabled(true);
                                tablaOperarios.setRowSelectionInterval(0, 0);     
                                txtNumeroDocumento.setEnabled(false);
                                cmbTipoDeDocumento.setEnabled(false);
                                txtApellido.setEnabled(false);
                                txtNombre.setEnabled(false);
                                jdcFecha.setEnabled(false);
                                cmbSexo.setEnabled(false);
                                btnSexo.setEnabled(false);
                                cmbEstadoCivil.setEnabled(false);
                                btnEstadoCivil.setEnabled(false);
                                btnNacionalidad.setEnabled(false);
                                cmbNacionalidad.setEnabled(false);
                                cmbPais.setEnabled(false);
                                btnPais.setEnabled(false);
                                cmbProvincia.setEnabled(false);
                                btnProvincia.setEnabled(false);
                                cmbCiudad.setEnabled(false);
                                bnCiudad.setEnabled(false);
                                cmbBarrio.setEnabled(false);
                                btnBarrio.setEnabled(false);
                                txtCalle.setEnabled(false);
                                txtNumeroCasa.setEnabled(false);
                                txtDpto.setEnabled(false);
                                txtPiso.setEnabled(false);
                                cmbCodPais.setEnabled(false);
                                btnCodPais.setEnabled(false);
                                cmbCodArea.setEnabled(false);
                                btnCodArea.setEnabled(false);
                                txtNumeroTel.setEnabled(false);
                                cmbTipodeTelefono.setEnabled(false);
                                btnTipoTelefono.setEnabled(false);
                                txtNick.setEnabled(false);
                                txtClave.setEnabled(false);
                                cmbRol.setEnabled(false);                                  
                                this.limpiar();
                            }                           
                       }
                       else
                       {
                            if (remiseria.buscarIdOperario(nick) == null)
                            {
                                if(ok == true)
                                {
                                    remiseria.modificarOperario(unOperario, nick, contrasena, dni, tipoDocumento, unSexo, unEstadoCivil, unaNacionalidad, unDomicilio, nombre, apellido, fechaNacimiento, unTelefono, unRol);
                                    JOptionPane.showMessageDialog(null,"Se modificó un nuevo Operario ","",JOptionPane.INFORMATION_MESSAGE);
                                    this.cargarOperarios(); 
                                    btnBuscarChofer.setEnabled(true);                                     
                                    jXTaskPane1.setCollapsed(true);
                                    btnAgregar.setEnabled(true);
                                    btnModificar.setEnabled(true);
                                    btnEliminar.setEnabled(true);
                                    btnRoles.setEnabled(true);
                                    btnGuardarModificaciones.setEnabled(false);
                                    btnGuardarChofer.setEnabled(false);
                                    btnCancelar1.setEnabled(true);
                                    tablaOperarios.setEnabled(true);
                                    tablaOperarios.setRowSelectionInterval(0, 0);      
                                    txtNumeroDocumento.setEnabled(false);
                                    cmbTipoDeDocumento.setEnabled(false);
                                    txtApellido.setEnabled(false);
                                    txtNombre.setEnabled(false);
                                    jdcFecha.setEnabled(false);
                                    cmbSexo.setEnabled(false);
                                    btnSexo.setEnabled(false);
                                    cmbEstadoCivil.setEnabled(false);
                                    btnEstadoCivil.setEnabled(false);
                                    btnNacionalidad.setEnabled(false);
                                    cmbNacionalidad.setEnabled(false);
                                    cmbPais.setEnabled(false);
                                    btnPais.setEnabled(false);
                                    cmbProvincia.setEnabled(false);
                                    btnProvincia.setEnabled(false);
                                    cmbCiudad.setEnabled(false);
                                    bnCiudad.setEnabled(false);
                                    cmbBarrio.setEnabled(false);
                                    btnBarrio.setEnabled(false);
                                    txtCalle.setEnabled(false);
                                    txtNumeroCasa.setEnabled(false);
                                    txtDpto.setEnabled(false);
                                    txtPiso.setEnabled(false);
                                    cmbCodPais.setEnabled(false);
                                    btnCodPais.setEnabled(false);
                                    cmbCodArea.setEnabled(false);
                                    btnCodArea.setEnabled(false);
                                    txtNumeroTel.setEnabled(false);
                                    cmbTipodeTelefono.setEnabled(false);
                                    btnTipoTelefono.setEnabled(false);
                                    txtNick.setEnabled(false);
                                    txtClave.setEnabled(false);
                                    cmbRol.setEnabled(false);                                      
                                    this.limpiar();
                                }                                   
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"El Nick "+ nick + " ya existe","",JOptionPane.ERROR_MESSAGE);
                                txtNick.setText(null);
                                txtNick.setBackground(java.awt.Color.red);
                            }
                       }   
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"El N° de Documento ya existe"," ",JOptionPane.ERROR_MESSAGE);
                        txtNumeroDocumento.setText("");
                        txtNumeroDocumento.setBackground(java.awt.Color.red);
                    }
                }       
            }                     
      }
      
      public void eliminarOperario(){
          int eleccion = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el Operario seleccionado?");
          if ( eleccion == 0)
          {    
           int dniOperario = (int) tablaOperarios.getValueAt(tablaOperarios.getSelectedRow(), 0);
           Operario unOperario = remiseria.buscarOperario(dniOperario);
           remiseria.eliminarOperario(unOperario);
           this.cargarOperarios();
           this.limpiar();
           JOptionPane.showMessageDialog(null,"El Operario a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);
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

        jPanel2 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnRoles = new javax.swing.JButton();
        btnBuscarChofer = new javax.swing.JButton();
        btnBuscarMóvil1 = new javax.swing.JButton();
        btnBuscarMóvil2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jXTaskPane3 = new org.jdesktop.swingx.JXTaskPane();
        cmbOpcionBusqueda = new javax.swing.JComboBox();
        SearchCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaOperarios = new javax.swing.JTable();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtNumeroDocumento = new javax.swing.JTextField();
        cmbTipoDeDocumento = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        cmbSexo = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cmbEstadoCivil = new javax.swing.JComboBox();
        cmbNacionalidad = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        btnSexo = new javax.swing.JButton();
        btnEstadoCivil = new javax.swing.JButton();
        btnNacionalidad = new javax.swing.JButton();
        btnTipoDeDocumento = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        cmbPais = new javax.swing.JComboBox();
        cmbProvincia = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cmbCiudad = new javax.swing.JComboBox();
        cmbBarrio = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        txtNumeroCasa = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDpto = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtPiso = new javax.swing.JTextField();
        btnPais = new javax.swing.JButton();
        btnProvincia = new javax.swing.JButton();
        bnCiudad = new javax.swing.JButton();
        btnBarrio = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtNick = new javax.swing.JTextField();
        cmbRol = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        txtClave = new javax.swing.JPasswordField();
        btnRolC = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        cmbCodPais = new javax.swing.JComboBox();
        btnCodPais = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        cmbCodArea = new javax.swing.JComboBox();
        btnCodArea = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        txtNumeroTel = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        cmbTipodeTelefono = new javax.swing.JComboBox();
        btnTipoTelefono = new javax.swing.JButton();
        btnGuardarModificaciones = new javax.swing.JButton();
        btnGuardarChofer = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1118, 642));
        setMinimumSize(new java.awt.Dimension(1118, 642));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel2.setPreferredSize(new java.awt.Dimension(162, 280));

        btnAgregar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificar.setText("Modificar ");
        btnModificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminar.setText("Eliminar ");
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnCancelar.setText("Salir");
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnRoles.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnRoles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/format-text-direction-ltr.png"))); // NOI18N
        btnRoles.setText("Editar roles");
        btnRoles.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRolesActionPerformed(evt);
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

        btnBuscarMóvil2.setBackground(new java.awt.Color(255, 255, 255));
        btnBuscarMóvil2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnBuscarMóvil2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/config-users.png"))); // NOI18N
        btnBuscarMóvil2.setText("<html><p>Historial de</p><p>accesos al sistema</p></html>");
        btnBuscarMóvil2.setActionCommand("<html><p>Historial de accesos</p><p> al sistema</p></html>");
        btnBuscarMóvil2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBuscarMóvil2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMóvil2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnBuscarMóvil2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnBuscarChofer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRoles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarMóvil2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel1.setText("Gestor de Operarios");

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

        jXTaskPane3.setCollapsed(true);
        jXTaskPane3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Search.png"))); // NOI18N
        jXTaskPane3.setTitle("Buscar un Operario");

        cmbOpcionBusqueda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbOpcionBusqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N° Documento", "Apellido", "Nombre" }));

        SearchCliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SearchCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        SearchCliente.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                SearchClienteCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPane3Layout = new javax.swing.GroupLayout(jXTaskPane3.getContentPane());
        jXTaskPane3.getContentPane().setLayout(jXTaskPane3Layout);
        jXTaskPane3Layout.setHorizontalGroup(
            jXTaskPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane3Layout.createSequentialGroup()
                .addComponent(cmbOpcionBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SearchCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jXTaskPane3Layout.setVerticalGroup(
            jXTaskPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(SearchCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbOpcionBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBackground(new java.awt.Color(117, 150, 227));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaOperarios.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaOperarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Documento", "Usuario", "Rol", "Nombre", "Apellido", "Teléfono"
            }
        ));
        tablaOperarios.setRowHeight(20);
        tablaOperarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaOperariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaOperarios);
        if (tablaOperarios.getColumnModel().getColumnCount() > 0) {
            tablaOperarios.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jXTaskPane1.setCollapsed(true);
        jXTaskPane1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document-new.png"))); // NOI18N
        jXTaskPane1.setTitle("Datos del Operario");

        jLabel15.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel15.setText("N° de Documento:");

        jLabel20.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel20.setText("Tipo de documento:");

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

        cmbTipoDeDocumento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbTipoDeDocumento.setNextFocusableComponent(txtApellido);

        jLabel14.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel14.setText("Apellido:");

        txtApellido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtApellido.setHorizontalAlignment(javax.swing.JTextField.LEFT);
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
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
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

        jLabel13.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel13.setText("Nombre:");

        jLabel16.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel16.setText("Fecha de nacimiento:");

        jdcFecha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jdcFecha.setNextFocusableComponent(cmbSexo);

        cmbSexo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbSexo.setNextFocusableComponent(cmbEstadoCivil);

        jLabel21.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel21.setText("Sexo:");

        jLabel22.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel22.setText("Estado civil:");

        cmbEstadoCivil.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbEstadoCivil.setNextFocusableComponent(cmbNacionalidad);

        cmbNacionalidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbNacionalidad.setNextFocusableComponent(cmbPais);

        jLabel23.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel23.setText("Nacionalidad:");

        btnSexo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnSexo.setBorder(null);
        btnSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSexoActionPerformed(evt);
            }
        });

        btnEstadoCivil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnEstadoCivil.setBorder(null);
        btnEstadoCivil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoCivilActionPerformed(evt);
            }
        });

        btnNacionalidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnNacionalidad.setBorder(null);
        btnNacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNacionalidadActionPerformed(evt);
            }
        });

        btnTipoDeDocumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnTipoDeDocumento.setBorder(null);
        btnTipoDeDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipoDeDocumentoActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Domicilio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Goudy Old Style", 1, 14), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel24.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel24.setText("País:");

        cmbPais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbPais.setNextFocusableComponent(cmbProvincia);
        cmbPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaisActionPerformed(evt);
            }
        });

        cmbProvincia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbProvincia.setNextFocusableComponent(cmbCiudad);
        cmbProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvinciaActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel25.setText("Provincia:");

        jLabel26.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel26.setText("Ciudad:");

        cmbCiudad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbCiudad.setNextFocusableComponent(cmbBarrio);
        cmbCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCiudadActionPerformed(evt);
            }
        });

        cmbBarrio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbBarrio.setNextFocusableComponent(txtCalle);

        jLabel27.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel27.setText("Barrio:");

        jLabel17.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel17.setText("Calle:");

        txtCalle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCalle.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCalle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCalle.setNextFocusableComponent(txtNumeroCasa);
        txtCalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCalleMouseClicked(evt);
            }
        });
        txtCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCalleKeyTyped(evt);
            }
        });

        txtNumeroCasa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNumeroCasa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroCasa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumeroCasa.setNextFocusableComponent(txtDpto);
        txtNumeroCasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumeroCasaMouseClicked(evt);
            }
        });
        txtNumeroCasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroCasaKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel18.setText("N°:");

        txtDpto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDpto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDpto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDpto.setNextFocusableComponent(txtPiso);
        txtDpto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDptoMouseClicked(evt);
            }
        });
        txtDpto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDptoKeyTyped(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel28.setText("Dpto:");

        jLabel29.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel29.setText("Piso:");

        txtPiso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPiso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPiso.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPiso.setNextFocusableComponent(cmbCodPais);
        txtPiso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPisoMouseClicked(evt);
            }
        });
        txtPiso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPisoKeyTyped(evt);
            }
        });

        btnPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaisActionPerformed(evt);
            }
        });

        btnProvincia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProvinciaActionPerformed(evt);
            }
        });

        bnCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        bnCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnCiudadActionPerformed(evt);
            }
        });

        btnBarrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarrioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addGap(11, 11, 11))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbBarrio, 0, 135, Short.MAX_VALUE)
                                    .addComponent(cmbCiudad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bnCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24)
                                .addGap(14, 14, 14)
                                .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel18))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel17)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtPiso, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(txtNumeroCasa)
                    .addComponent(txtDpto)
                    .addComponent(txtCalle))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel24)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(cmbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(btnProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bnCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDpto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28)
                                .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel26)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPiso, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(jLabel27))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Cuenta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Goudy Old Style", 1, 14))); // NOI18N

        jLabel38.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel38.setText("Nick:");

        jLabel39.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel39.setText("Clave:");

        txtNick.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNick.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNick.setNextFocusableComponent(txtClave);
        txtNick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNickMouseClicked(evt);
            }
        });
        txtNick.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNickKeyTyped(evt);
            }
        });

        cmbRol.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel40.setText("Rol:");

        txtClave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtClave.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtClave.setNextFocusableComponent(cmbRol);
        txtClave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtClaveMouseClicked(evt);
            }
        });
        txtClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClaveKeyTyped(evt);
            }
        });

        btnRolC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnRolC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRolCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRolC, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRolC, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38)
                        .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel39)
                        .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel40)
                        .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Telefono", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Goudy Old Style", 1, 14))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel30.setText("Cod. país:");

        cmbCodPais.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbCodPais.setNextFocusableComponent(cmbCodArea);
        cmbCodPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodPaisActionPerformed(evt);
            }
        });

        btnCodPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnCodPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodPaisActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel31.setText("Cod. área:");

        cmbCodArea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbCodArea.setNextFocusableComponent(txtNumeroTel);

        btnCodArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnCodArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodAreaActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel32.setText("N°:");

        txtNumeroTel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNumeroTel.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroTel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumeroTel.setNextFocusableComponent(cmbTipodeTelefono);
        txtNumeroTel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumeroTelMouseClicked(evt);
            }
        });
        txtNumeroTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroTelKeyTyped(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel33.setText("Tipo de teléfono:");

        cmbTipodeTelefono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbTipodeTelefono.setNextFocusableComponent(txtNick);

        btnTipoTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnTipoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipoTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(33, 33, 33))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumeroTel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbTipodeTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTipodeTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumeroTel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbTipoDeDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTipoDeDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbSexo, 0, 170, Short.MAX_VALUE)
                            .addComponent(cmbEstadoCivil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbNacionalidad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel16)
                                .addComponent(jLabel13))
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApellido, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))))
                .addGap(18, 54, Short.MAX_VALUE)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbTipoDeDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20))
                            .addComponent(btnTipoDeDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel21)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23))))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        btnGuardarModificaciones.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardarModificaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnGuardarModificaciones.setText("<html><p>Guardar</p><p>Modificaciones</p></html>");
        btnGuardarModificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificacionesActionPerformed(evt);
            }
        });

        btnGuardarChofer.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardarChofer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/media-floppy.png"))); // NOI18N
        btnGuardarChofer.setText("Guardar");
        btnGuardarChofer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardarChofer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarChoferActionPerformed(evt);
            }
        });

        btnCancelar1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        btnCancelar1.setText("Cancelar");
        btnCancelar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
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
                .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jXTaskPane1Layout.setVerticalGroup(
            jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGuardarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(7, 7, 7)
                        .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        jXTaskPane1.setCollapsed(false);
        btnAgregar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnRoles.setEnabled(false);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(true);
        btnCancelar1.setEnabled(true);
        tablaOperarios.setEnabled(false);
        btnBuscarChofer.setEnabled(false);
        txtNumeroDocumento.setEnabled(true);
        cmbTipoDeDocumento.setEnabled(true);
        txtApellido.setEnabled(true);
        txtNombre.setEnabled(true);
        jdcFecha.setEnabled(true);
        cmbSexo.setEnabled(true);
        btnSexo.setEnabled(true);
        cmbEstadoCivil.setEnabled(true);
        btnEstadoCivil.setEnabled(true);
        btnNacionalidad.setEnabled(true);
        cmbNacionalidad.setEnabled(true);
        cmbPais.setEnabled(true);
        btnPais.setEnabled(true);
        cmbProvincia.setEnabled(true);
        btnProvincia.setEnabled(true);
        cmbCiudad.setEnabled(true);
        bnCiudad.setEnabled(true);
        cmbBarrio.setEnabled(true);
        btnBarrio.setEnabled(true);
        txtCalle.setEnabled(true);
        txtNumeroCasa.setEnabled(true);
        txtDpto.setEnabled(true);
        txtPiso.setEnabled(true);
        cmbCodPais.setEnabled(true);
        btnCodPais.setEnabled(true);
        cmbCodArea.setEnabled(true);
        btnCodArea.setEnabled(true);
        txtNumeroTel.setEnabled(true);
        cmbTipodeTelefono.setEnabled(true);
        btnTipoTelefono.setEnabled(true);
        txtNick.setEnabled(true);
        txtClave.setEnabled(true);
        cmbRol.setEnabled(true);
        txtNumeroDocumento.requestFocus();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if(tablaOperarios.getSelectedRow()!=-1)
        { 
        this.obtenerOperario();
        jXTaskPane1.setCollapsed(false);
        btnAgregar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnRoles.setEnabled(false);
        btnGuardarModificaciones.setEnabled(true);
        btnGuardarChofer.setEnabled(false);
        btnCancelar1.setEnabled(true);
        tablaOperarios.setEnabled(false);
         btnBuscarChofer.setEnabled(false);
        txtNumeroDocumento.setEnabled(true);
        cmbTipoDeDocumento.setEnabled(true);
        txtApellido.setEnabled(true);
        txtNombre.setEnabled(true);
        jdcFecha.setEnabled(true);
        cmbSexo.setEnabled(true);
        btnSexo.setEnabled(true);
        cmbEstadoCivil.setEnabled(true);
        btnEstadoCivil.setEnabled(true);
        btnNacionalidad.setEnabled(true);
        cmbNacionalidad.setEnabled(true);
        cmbPais.setEnabled(true);
        btnPais.setEnabled(true);
        cmbProvincia.setEnabled(true);
        btnProvincia.setEnabled(true);
        cmbCiudad.setEnabled(true);
        bnCiudad.setEnabled(true);
        cmbBarrio.setEnabled(true);
        btnBarrio.setEnabled(true);
        txtCalle.setEnabled(true);
        txtNumeroCasa.setEnabled(true);
        txtDpto.setEnabled(true);
        txtPiso.setEnabled(true);
        cmbCodPais.setEnabled(true);
        btnCodPais.setEnabled(true);
        cmbCodArea.setEnabled(true);
        btnCodArea.setEnabled(true);
        txtNumeroTel.setEnabled(true);
        cmbTipodeTelefono.setEnabled(true);
        btnTipoTelefono.setEnabled(true);
        txtNick.setEnabled(true);
        txtClave.setEnabled(true);
        cmbRol.setEnabled(true);
        txtNumeroDocumento.requestFocus();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Operario para realizar la modificación");
        }      
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if(tablaOperarios.getSelectedRow()!=-1)
        {
            this.eliminarOperario();
            this.limpiar();
            this.cargarOperarios();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Operario"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRolesActionPerformed
        // TODO add your handling code here:
        JDialogRoles ventana = new JDialogRoles (parent,modal,remiseria, cmbRol);
        ventana.show();
    }//GEN-LAST:event_btnRolesActionPerformed

    private void tablaOperariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaOperariosMouseClicked
        // TODO add your handling code here:
        //this.obtenerOperario();
    }//GEN-LAST:event_tablaOperariosMouseClicked

    private void txtNumeroDocumentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroDocumentoMouseClicked
        // TODO add your handling code here:
        txtNumeroDocumento.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtNumeroDocumentoMouseClicked
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
        //        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 9;
        {if ( txtNumeroDocumento.getText().length()== limite)
            evt.consume();
        } 
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }           
    }//GEN-LAST:event_txtNumeroDocumentoKeyTyped

    private void txtApellidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtApellidoMouseClicked
        // TODO add your handling code here:
        txtApellido.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtApellidoMouseClicked

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        // TODO add your handling code here:
        char c;
        c = evt.getKeyChar();
        if (!(c < '0' || c > '9'))
        {
            evt.consume();
        }
         int limite  = 20;
        {if ( txtApellido.getText().length()== limite)
            evt.consume();
        }         
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtNombreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreMouseClicked
        // TODO add your handling code here:
        txtNombre.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtNombreMouseClicked

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
        char c;
        c = evt.getKeyChar();
        if (!(c < '0' || c > '9'))
        {
            evt.consume();
        }
        int limite  = 20;
        {if ( txtNombre.getText().length()== limite)
            evt.consume();
        }         
    }//GEN-LAST:event_txtNombreKeyTyped

    private void btnSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSexoActionPerformed
        // TODO add your handling code here:
        JDialogSexo ventana = new JDialogSexo(parent, modal, cmbSexo, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnSexoActionPerformed

    private void btnEstadoCivilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoCivilActionPerformed
        // TODO add your handling code here:
        JDialogEstadoCivil ventana = new JDialogEstadoCivil(parent,modal,cmbEstadoCivil,remiseria);
        ventana.show();
    }//GEN-LAST:event_btnEstadoCivilActionPerformed

    private void btnNacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNacionalidadActionPerformed
        // TODO add your handling code here:}
        JDialogNacionalidad ventana = new JDialogNacionalidad(parent,modal,cmbNacionalidad,remiseria);
        ventana.show();
    }//GEN-LAST:event_btnNacionalidadActionPerformed

    private void btnTipoDeDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipoDeDocumentoActionPerformed
        // TODO add your handling code here:
        JDialogTipoDni ventana = new JDialogTipoDni(parent, modal, cmbTipoDeDocumento, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnTipoDeDocumentoActionPerformed

    private void cmbPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaisActionPerformed
        // TODO add your handling code here:
        if (cmbPais.getItemCount()!= 0){
            cmbProvincia.removeAllItems();
            cmbCiudad.removeAllItems();
            cmbBarrio.removeAllItems();
            String nombrePais = cmbPais.getSelectedItem().toString();
            Pais unPais = remiseria.buscarPais(nombrePais);
            if(unPais != null)
            {
                this.cargarProvinciasCombo(unPais);
            }
        }
    }//GEN-LAST:event_cmbPaisActionPerformed

    private void cmbProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinciaActionPerformed
        // TODO add your handling code here:
        if (cmbProvincia.getItemCount()!= 0){
            cmbCiudad.removeAllItems();
            cmbBarrio.removeAllItems();
            String nombrePais = cmbPais.getSelectedItem().toString();
            if(!nombrePais.equals(""))
            {
            Pais unPais = remiseria.buscarPais(nombrePais);
                 if (unPais != null)
                 {
                        String nombreProvincia = cmbProvincia.getSelectedItem().toString();
                        if(nombreProvincia != "")
                        {
                            Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
                            if(unaProvincia != null)
                            {
                                this.cargarCiudadesCombo(unaProvincia);
                            }
                        }
                 }
            }
        }
    }//GEN-LAST:event_cmbProvinciaActionPerformed

    private void cmbCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCiudadActionPerformed
        // TODO add your handling code here:
        if (cmbCiudad.getItemCount() != 0) 
        {
            cmbBarrio.removeAllItems();
            String nombrePais = cmbPais.getSelectedItem().toString();
            if(!nombrePais.equals(""))
            {
                Pais unPais = remiseria.buscarPais(nombrePais);
                 if (unPais != null)
                 {
                      String nombreProvincia = cmbProvincia.getSelectedItem().toString();
                      if(!nombreProvincia.equals(""))
                      {
                            Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
                            if(unaProvincia != null)
                            {
                                  String nombreCiudad = cmbCiudad.getSelectedItem().toString();
                                  if(!nombreCiudad.equals(""))
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
            }
        }
    }//GEN-LAST:event_cmbCiudadActionPerformed

    private void txtCalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCalleMouseClicked
        // TODO add your handling code here:
        txtCalle.setBackground(Color.white);
    }//GEN-LAST:event_txtCalleMouseClicked

    private void txtNumeroCasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroCasaMouseClicked
        // TODO add your handling code here:
        txtNumeroCasa.setBackground(Color.white);
    }//GEN-LAST:event_txtNumeroCasaMouseClicked

    private void txtNumeroCasaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroCasaKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
         int limite  = 5;
        {if ( txtNumeroCasa.getText().length()== limite)
            evt.consume();
        }           
    }//GEN-LAST:event_txtNumeroCasaKeyTyped

    private void txtDptoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDptoMouseClicked
        // TODO add your handling code here:
        txtDpto.setBackground(Color.white);
    }//GEN-LAST:event_txtDptoMouseClicked

    private void txtPisoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPisoMouseClicked
        // TODO add your handling code here:
        txtPiso.setBackground(Color.white);
    }//GEN-LAST:event_txtPisoMouseClicked

    private void txtPisoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPisoKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
         int limite  =5;
        {if ( txtPiso.getText().length()== limite)
            evt.consume();
        }         
    }//GEN-LAST:event_txtPisoKeyTyped

    private void btnPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaisActionPerformed
        // TODO add your handling code here:
        JDialogPais ventana = new JDialogPais (parent,modal,cmbPais, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnPaisActionPerformed

    private void btnProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProvinciaActionPerformed
        // TODO add your handling code here:
        if(cmbPais.getSelectedItem().toString()!= "")
        {
            JDialogProvincia ventana = new JDialogProvincia (parent,modal,cmbProvincia, remiseria.buscarPais(cmbPais.getSelectedItem().toString()), remiseria);
            ventana.show();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Pais"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProvinciaActionPerformed

    private void bnCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnCiudadActionPerformed
        // TODO add your handling code here:
        try{
            //if(cmbProvincia.getSelectedItem().toString()!= ""){
                Pais unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
                JDialogCiudad ventana = new JDialogCiudad (parent,modal,cmbCiudad,unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString()),remiseria );
                ventana.show();
            }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado una Provincia"," ",JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_bnCiudadActionPerformed

    private void btnBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarrioActionPerformed
        // TODO add your handling code here:
        try{
            //if(cmbCiudad.getSelectedItem().toString()!= ""){
                Pais unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
                Provincia unaProvincia = unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString());
                Ciudad unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
                JDialogBarrio ventana = new JDialogBarrio (parent,modal,cmbBarrio,unaCiudad ,remiseria);
                ventana.show();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado una Ciudad"," ",JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btnBarrioActionPerformed

    private void cmbCodPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodPaisActionPerformed
        // TODO add your handling code here:
        if (cmbCodPais.getItemCount()!= 0){
            cmbCodArea.removeAllItems();
            String codigoPais = (cmbCodPais.getSelectedItem().toString());
            if(!codigoPais.equals(""))
            {
                CodPais unCodPais = remiseria.buscarCodigoPais(codigoPais);
                if(unCodPais != null)
                {
                    this.cargarCodigoProvinciaCombo(unCodPais);
                }
            }
        }
    }//GEN-LAST:event_cmbCodPaisActionPerformed

    private void btnCodPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodPaisActionPerformed
        // TODO add your handling code here:
        JDialogCodPaisTelefono ventana = new  JDialogCodPaisTelefono (parent,  modal,cmbCodPais, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnCodPaisActionPerformed

    private void btnCodAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodAreaActionPerformed
        // TODO add your handling code here:
        if (!cmbCodPais.getSelectedItem().toString().equals("")){
            JDialogCodAreaTelefono ventana = new JDialogCodAreaTelefono(parent, modal, cmbCodArea, remiseria.buscarCodigoPais((cmbCodPais.getSelectedItem().toString())),remiseria) ;
            ventana.show();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado el Código de País"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCodAreaActionPerformed

    private void txtNumeroTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroTelKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  =10;
        {if ( txtNumeroTel.getText().length()== limite)
            evt.consume();
        }                   
    }//GEN-LAST:event_txtNumeroTelKeyTyped

    private void btnTipoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipoTelefonoActionPerformed
        // TODO add your handling code here:
        JDialogTipoTelefono ventana = new JDialogTipoTelefono (parent,modal,cmbTipodeTelefono,remiseria);
        ventana.show();
    }//GEN-LAST:event_btnTipoTelefonoActionPerformed

    private void SearchClienteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_SearchClienteCaretUpdate
        // TODO add your handling code here:
        // TODO add your handling code here:
        Operario aux = null;
        List operarios =  remiseria.buscarOperariosActivos();
        List filtro = new LinkedList();
        if (!SearchCliente.getText().isEmpty())
        {
            Iterator iter = operarios.iterator();
            while (iter.hasNext())
            {
                aux = (Operario) iter.next();
                if(cmbOpcionBusqueda.getSelectedItem().equals("N° Documento"))
                {
                    if (String.valueOf(aux.getDni()).toUpperCase().startsWith(SearchCliente.getText().toUpperCase()))
                    {
                        filtro.add(aux);
                    }
                    this.cargarTablaOperariosBuscados(tablaOperarios, filtro);
                }

                if(cmbOpcionBusqueda.getSelectedItem().equals("Apellido"))
                {
                    if (String.valueOf(aux.getApellido()).toUpperCase().startsWith(SearchCliente.getText().toUpperCase()))
                    {
                        filtro.add(aux);
                    }
                    this.cargarTablaOperariosBuscados(tablaOperarios, filtro);
                }

                if(cmbOpcionBusqueda.getSelectedItem().equals("Nombre"))
                {
                    if (aux.getNombre().toUpperCase().startsWith(SearchCliente.getText().toUpperCase()))
                    {
                        filtro.add(aux);
                    }
                    this.cargarTablaOperariosBuscados(tablaOperarios, filtro);
                }

            }
        }
        else
        {
            this.cargarOperarios();
            // this.cargarTablaChoferesBuscados(tablaChoferes, filtro);
        }
    }//GEN-LAST:event_SearchClienteCaretUpdate

    private void txtNickMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNickMouseClicked
        // TODO add your handling code here:
        txtNick.setBackground(Color.white);
    }//GEN-LAST:event_txtNickMouseClicked

    private void txtClaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtClaveMouseClicked
        // TODO add your handling code here:
        txtClave.setBackground(Color.white);
    }//GEN-LAST:event_txtClaveMouseClicked

    private void txtNumeroTelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroTelMouseClicked
        // TODO add your handling code here:
        txtNumeroTel.setBackground(Color.white);
    }//GEN-LAST:event_txtNumeroTelMouseClicked

    private void btnGuardarChoferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarChoferActionPerformed
        // TODO add your handling code here:
        this.agregarOperario();
    }//GEN-LAST:event_btnGuardarChoferActionPerformed

    private void btnGuardarModificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionesActionPerformed
        // TODO add your handling code here:
        if(tablaOperarios.getSelectedRow()!=-1)
        {
            this.modificarOperario();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Operario"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarModificacionesActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        jXTaskPane1.setCollapsed(true);
        btnAgregar.setEnabled(true);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnRoles.setEnabled(true);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);
        btnCancelar1.setEnabled(true);
        tablaOperarios.setEnabled(true);
        btnBuscarChofer.setEnabled(true); 
        txtNumeroDocumento.setEnabled(false);
        cmbTipoDeDocumento.setEnabled(false);
        txtApellido.setEnabled(false);
        txtNombre.setEnabled(false);
        jdcFecha.setEnabled(false);
        cmbSexo.setEnabled(false);
        btnSexo.setEnabled(false);
        cmbEstadoCivil.setEnabled(false);
        btnEstadoCivil.setEnabled(false);
        btnNacionalidad.setEnabled(false);
        cmbNacionalidad.setEnabled(false);
        cmbPais.setEnabled(false);
        btnPais.setEnabled(false);
        cmbProvincia.setEnabled(false);
        btnProvincia.setEnabled(false);
        cmbCiudad.setEnabled(false);
        bnCiudad.setEnabled(false);
        cmbBarrio.setEnabled(false);
        btnBarrio.setEnabled(false);
        txtCalle.setEnabled(false);
        txtNumeroCasa.setEnabled(false);
        txtDpto.setEnabled(false);
        txtPiso.setEnabled(false);
        cmbCodPais.setEnabled(false);
        btnCodPais.setEnabled(false);
        cmbCodArea.setEnabled(false);
        btnCodArea.setEnabled(false);
        txtNumeroTel.setEnabled(false);
        cmbTipodeTelefono.setEnabled(false);
        btnTipoTelefono.setEnabled(false);
        txtNick.setEnabled(false);
        txtClave.setEnabled(false);
        cmbRol.setEnabled(false); 
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnBuscarChoferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarChoferActionPerformed
        // TODO add your handling code here:
        jXTaskPane3.setCollapsed(false);
    }//GEN-LAST:event_btnBuscarChoferActionPerformed

    private void txtCalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleKeyTyped
        // TODO add your handling code here:
         int limite  = 20;
        {if ( txtCalle.getText().length()== limite)
            evt.consume();
        }           
    }//GEN-LAST:event_txtCalleKeyTyped

    private void txtDptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDptoKeyTyped
         int limite  =5;
        {if ( txtDpto.getText().length()== limite)
            evt.consume();
        }             
    }//GEN-LAST:event_txtDptoKeyTyped

    private void txtNickKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNickKeyTyped
        // TODO add your handling code here:
         int limite  =15;
        {if ( txtNick.getText().length()== limite)
            evt.consume();
        }          
    }//GEN-LAST:event_txtNickKeyTyped

    private void txtClaveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveKeyTyped
        // TODO add your handling code here:
        int limite  =150;
        {if ( txtClave.getText().length()== limite)
            evt.consume();
        }           
    }//GEN-LAST:event_txtClaveKeyTyped

    private void btnRolCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRolCActionPerformed
        // TODO add your handling code here:
        JDialogRoles ventana = new JDialogRoles (parent,modal,remiseria, cmbRol);
        ventana.show();        
    }//GEN-LAST:event_btnRolCActionPerformed

    private void btnBuscarMóvil1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMóvil1ActionPerformed
        // TODO add your handling code here:
        try{
            List historial = remiseria.buscarOperariosActivos();
            if(historial != null)
            {
                LinkedList <ImprimirOperarios> lista = new LinkedList<ImprimirOperarios>();
                Operario aux = null;
                Iterator iter = historial.iterator();
                while (iter.hasNext())
                {
                    aux = (Operario) iter.next();
                    String dni = String.valueOf(aux.getDni());
                    String nombre = String.valueOf(aux.getNombre());
                    String apellido =aux.getApellido();
                    String fechaNacimiento = utilidades.getFecha(aux.getFechaNacimiento());
                    String domicilio = aux.getUnDomicilio().getUnPais()+"-"+aux.getUnDomicilio().getUnaProvincia()+"-"+aux.getUnDomicilio().getUnBarrio()+"- Calle "+aux.getUnDomicilio().getCalle()+" N° "+aux.getUnDomicilio().getNroCasa();

                    lista.add(new ImprimirOperarios(dni,nombre,apellido,fechaNacimiento,domicilio));
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

                }
                //C:/Users/garba/Desktop/Reportes/
                //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Operarios.jrxml");
                JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Operarios.jrxml");
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
                JOptionPane.showMessageDialog(null,"No hay Información de Operarios para realizar el reporte");
            }
        }
        catch (JRException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

    }//GEN-LAST:event_btnBuscarMóvil1ActionPerformed

    private void btnBuscarMóvil2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMóvil2ActionPerformed
        // TODO add your handling code here:
      HistorialAccesos  ventana= new HistorialAccesos( parent,  modal,remiseria, unMaestro ,  unOperario);
      ventana.show();
    }//GEN-LAST:event_btnBuscarMóvil2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialogOperario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogOperario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogOperario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogOperario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogOperario dialog = new JDialogOperario(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bnCiudad;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBarrio;
    private javax.swing.JButton btnBuscarChofer;
    private javax.swing.JButton btnBuscarMóvil1;
    private javax.swing.JButton btnBuscarMóvil2;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnCodArea;
    private javax.swing.JButton btnCodPais;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEstadoCivil;
    private javax.swing.JButton btnGuardarChofer;
    private javax.swing.JButton btnGuardarModificaciones;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNacionalidad;
    private javax.swing.JButton btnPais;
    private javax.swing.JButton btnProvincia;
    private javax.swing.JButton btnRolC;
    private javax.swing.JButton btnRoles;
    private javax.swing.JButton btnSexo;
    private javax.swing.JButton btnTipoDeDocumento;
    private javax.swing.JButton btnTipoTelefono;
    private javax.swing.JComboBox cmbBarrio;
    private javax.swing.JComboBox cmbCiudad;
    private javax.swing.JComboBox cmbCodArea;
    private javax.swing.JComboBox cmbCodPais;
    private javax.swing.JComboBox cmbEstadoCivil;
    private javax.swing.JComboBox cmbNacionalidad;
    private javax.swing.JComboBox cmbOpcionBusqueda;
    private javax.swing.JComboBox cmbPais;
    private javax.swing.JComboBox cmbProvincia;
    private javax.swing.JComboBox cmbRol;
    private javax.swing.JComboBox cmbSexo;
    private javax.swing.JComboBox cmbTipoDeDocumento;
    private javax.swing.JComboBox cmbTipodeTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JTable tablaOperarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtDpto;
    private javax.swing.JTextField txtNick;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumeroCasa;
    private javax.swing.JTextField txtNumeroDocumento;
    private javax.swing.JTextField txtNumeroTel;
    private javax.swing.JTextField txtPiso;
    // End of variables declaration//GEN-END:variables
}
