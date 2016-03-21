/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Clases.Barrio;
import Clases.Ciudad;
import Clases.Cliente;
import Clases.Domicilio;
import Clases.Pais;
import Clases.Provincia;
import Clases.Remiseria;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author garba
 */
public class JDialogEditarDomicilios extends javax.swing.JDialog {
private Remiseria remiseria;
private Cliente unCliente;
private java.awt.Frame parent;
private boolean modal;
private JTable tablaDirecciones;
private JComboBox cmbDireccionCliente ; private JComboBox cmbCiudadCliente; private JComboBox cmbBarrioCliente;
    /**
     * Creates new form JDialogEditarDomicilios
     */
    public JDialogEditarDomicilios(java.awt.Frame parent, boolean modal,Remiseria remiseria, Cliente unCliente, JTable tablaDirecciones) {
        super(parent, modal);
        this.unCliente = unCliente;
        this.remiseria = remiseria;
        this.tablaDirecciones = tablaDirecciones;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarPaisCombo();
        this.cargarDomicilios(unCliente);
        txtCliente.setText(unCliente.getNombre()+" "+unCliente.getApellido());
        JTableHeader th; 
        th = tablaDirecciones.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);
        JTableHeader t; 
        t = tablaDomicilios.getTableHeader(); 
        t.setForeground(java.awt.Color.BLACK);
        t.setBackground(java.awt.Color.white);
        t.setFont(fuente); 
        AutoCompleteDecorator.decorate(this.cmbPais);
        AutoCompleteDecorator.decorate(this.cmbProvincia);
        AutoCompleteDecorator.decorate(this.cmbCiudad);
        AutoCompleteDecorator.decorate(this.cmbBarrio);        
        jXTaskPane1.setCollapsed(true);
        btnNuevo.setEnabled(true);
        btnModificiar.setEnabled(true);
        btnEliminar.setEnabled(true);
        tablaDomicilios.setEnabled(true);
        btnGuardarChofer.setEnabled(false);
        btnGuardarModificaciones.setEnabled(false);

        cmbPais.setEnabled(false);
        cmbProvincia.setEnabled(false);
        cmbCiudad.setEnabled(false);
        cmbBarrio.setEnabled(false);
        btnPais.setEnabled(false);
        btnProvincia.setEnabled(false);
        bnCiudad.setEnabled(false);
        btnBarrio.setEnabled(false);
        txtCalle.setEnabled(false);
        txtNumeroCasa.setEnabled(false);
        txtDpto.setEnabled(false);
        txtPiso.setEnabled(false);   
    }
    
    public JDialogEditarDomicilios(java.awt.Frame parent, boolean modal,Remiseria remiseria, Cliente unCliente,JComboBox cmbDireccionCliente,JComboBox cmbCiudadCliente,JComboBox cmbBarrioCliente) {
        super(parent, modal);
        this.unCliente = unCliente;
        this.remiseria = remiseria;
        this.cmbDireccionCliente = cmbDireccionCliente;
        this.cmbCiudadCliente = cmbCiudadCliente;
        this.cmbBarrioCliente =cmbBarrioCliente;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarPaisCombo();
        this.cargarDomicilios(unCliente);
        txtCliente.setText(unCliente.getNombre()+" "+unCliente.getApellido());
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        JTableHeader t; 
        t = tablaDomicilios.getTableHeader(); 
        t.setForeground(java.awt.Color.BLACK);
        t.setBackground(java.awt.Color.white);
        t.setFont(fuente); 
        AutoCompleteDecorator.decorate(this.cmbPais);
        AutoCompleteDecorator.decorate(this.cmbProvincia);
        AutoCompleteDecorator.decorate(this.cmbCiudad);
        AutoCompleteDecorator.decorate(this.cmbBarrio);        
        jXTaskPane1.setCollapsed(true);
        btnNuevo.setEnabled(true);
        btnModificiar.setEnabled(true);
        btnEliminar.setEnabled(true);
        tablaDomicilios.setEnabled(true);
        btnGuardarChofer.setEnabled(false);
        btnGuardarModificaciones.setEnabled(false);

        cmbPais.setEnabled(false);
        cmbProvincia.setEnabled(false);
        cmbCiudad.setEnabled(false);
        cmbBarrio.setEnabled(false);
        btnPais.setEnabled(false);
        btnProvincia.setEnabled(false);
        bnCiudad.setEnabled(false);
        btnBarrio.setEnabled(false);
        txtCalle.setEnabled(false);
        txtNumeroCasa.setEnabled(false);
        txtDpto.setEnabled(false);
        txtPiso.setEnabled(false);   
    }    
    
    
  public void cargarDireccionCliente ()
  {
      try{
        cmbDireccionCliente.removeAllItems();
        Cliente cliente = remiseria.buscarCliente(unCliente.getDni());
        Collection direcciones = cliente.getDomicilios().values();
        Domicilio aux = null;
        Ciudad unaCiudad = null;
        Barrio unBarrio = null;
        Iterator iter= direcciones.iterator();
            while(iter.hasNext())
            {
                aux = (Domicilio) iter.next();
                unaCiudad = aux.getUnaCiudad();
                unBarrio = aux.getUnBarrio();
                cmbDireccionCliente.addItem("Calle "+aux.getCalle()+ " N° "+ aux.getNroCasa());
            }
            if (unaCiudad!= null && unBarrio!= null)
            {
                cmbCiudadCliente.setSelectedItem(unaCiudad.getNombreCiudad());
                cmbBarrioCliente.setSelectedItem(unBarrio.getNombreBarrio());  
            }
      }
      catch (Exception ex)
      {
          JOptionPane.showMessageDialog(null,"Error al cargar las direcciones del cliente");
      }
  }  
    
   public  void cargarDomiciliosDeCliente(Cliente unCliente){
       if(tablaDirecciones!= null)
       {
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
                fila[0] = "Calle "+aux.getCalle()+" N° "+aux.getNroCasa()+" Piso: "+ aux.getPiso()+" Dpto: "+aux.getDpto();
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
    }    
    
    
 public  void cargarDomicilios(Cliente cliente){
        Collection<Domicilio> domicilios = cliente.getDomicilios().values(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaDomicilios.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("País");
        modelo.addColumn("Provincia");
        modelo.addColumn("Ciudad");
        modelo.addColumn("Barrio");
        modelo.addColumn("Calle");
        modelo.addColumn("N°");
        modelo.addColumn("Dpto");
        modelo.addColumn("Piso");
        Domicilio aux = null;
        Iterator iter = domicilios.iterator();
        while (iter.hasNext()){
            aux = (Domicilio) iter.next();
            Object [] fila = new Object[8];
            fila[0] = aux.getUnPais().getNombrePais();
            fila[1] = aux.getUnaProvincia().getNombreProvincia();
            fila[2] = aux.getUnaCiudad().getNombreCiudad();
            fila[3] = aux.getUnBarrio().getNombreBarrio();
            fila[4] = aux.getCalle();
            fila[5] = aux.getNroCasa();
            fila[6] = aux.getDpto();
            fila[7] = aux.getPiso();
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaDomicilios.setModel(modelo);
    }
   
 public void cargarPaisCombo(){
        cmbPais.removeAllItems();
        List paises = remiseria.buscarPaises();
        if(paises != null)
        {
            Pais aux = null;
            Iterator iter = paises.iterator();
            while (iter.hasNext()){
                aux = (Pais) iter.next();
                cmbPais.addItem(aux.getNombrePais());
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
  
    public void obtenerDomicilio()
    {
        String numeroDomicilio = (String) tablaDomicilios.getValueAt(tablaDomicilios.getSelectedRow(), 5);
        Domicilio unDomicilio = unCliente.buscarDomicilio(numeroDomicilio,unCliente);
        cmbPais.setSelectedItem(unDomicilio.getUnPais().getNombrePais().toString());
        cmbProvincia.setSelectedItem(unDomicilio.getUnaProvincia().getNombreProvincia().toString());
        cmbCiudad.setSelectedItem(unDomicilio.getUnaCiudad().getNombreCiudad().toString());
        cmbBarrio.setSelectedItem(unDomicilio.getUnBarrio().getNombreBarrio().toString());
        txtCalle.setText(unDomicilio.getCalle());
        txtNumeroCasa.setText(unDomicilio.getNroCasa());
        txtDpto.setText(unDomicilio.getDpto());
        txtPiso.setText(unDomicilio.getPiso());

    }    
    
    public void limpiar()
    {
        txtCalle.setText("");
        txtCalle.setBackground(Color.white);
        txtNumeroCasa.setText("");
        txtNumeroCasa.setBackground(Color.white);
        txtPiso.setText("");
        txtPiso.setBackground(Color.white);
        txtDpto.setText("");
        txtDpto.setBackground(Color.white);
        this.cargarPaisCombo();
        cmbProvincia.removeAllItems();
        cmbCiudad.removeAllItems();
        cmbBarrio.removeAllItems();
    }
    
    public void agregarDomicilio()
    {
        boolean ok = true;
        Pais unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
        Provincia unaProvincia = null;
        Ciudad unaCiudad = null;
        Barrio unBarrio = null;
        String calle = txtCalle.getText();
        String numeroCasa = txtNumeroCasa.getText();
        String dpto = txtDpto.getText();
        String piso = txtPiso.getText();
        if(unPais !=null)
        {
            unaProvincia = unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString());
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado el País","",JOptionPane.ERROR_MESSAGE);
            ok= false;
        }

        if(unaProvincia != null)
        {
            unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
        }
        else
        {
             JOptionPane.showMessageDialog(null,"No ha seleccionado la Provincia","",JOptionPane.ERROR_MESSAGE);
             ok= false;
        }   

        if(unaCiudad !=null)
        {
              unBarrio = unaCiudad.buscarBarrio(cmbBarrio.getSelectedItem().toString());
        }
        else
        {
             JOptionPane.showMessageDialog(null,"No ha seleccionado la Ciudad","",JOptionPane.ERROR_MESSAGE);
             ok= false;
        }
        if(unBarrio ==null)
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

        if(ok == true)
        {  
            unCliente.agregarDomicilio(unPais, unaProvincia, unaCiudad, unBarrio, calle, numeroCasa, piso, dpto);
            JOptionPane.showMessageDialog(null,"Se registró un nuevo Domicilio ","",JOptionPane.INFORMATION_MESSAGE);
            this.cargarDomicilios(unCliente);
            cargarDomiciliosDeCliente(unCliente);
            jXTaskPane1.setCollapsed(true);
            btnNuevo.setEnabled(true);
            btnModificiar.setEnabled(true);
            btnEliminar.setEnabled(true);
            tablaDomicilios.setEnabled(true);
            btnGuardarChofer.setEnabled(false);
            btnGuardarModificaciones.setEnabled(false);
            if(tablaDirecciones!= null)
            {
                tablaDirecciones.setEnabled(true);
                tablaDirecciones.setRowSelectionInterval(0, 0);
            }
            if(cmbDireccionCliente!= null)
            {
                cargarDireccionCliente();
            }
            this.limpiar();
        }
     }
    
    public void modificarDomicilio()
    {
                boolean ok = true;
                String numeroDomicilio = (String) tablaDomicilios.getValueAt(tablaDomicilios.getSelectedRow(), 5);
                Domicilio unDomicilio = unCliente.buscarDomicilio(numeroDomicilio,unCliente);
                Pais unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
                Provincia unaProvincia = null;
                Ciudad unaCiudad = null;
                Barrio unBarrio = null;
                String calle = txtCalle.getText();
                String numeroCasa = txtNumeroCasa.getText();
                String dpto = txtDpto.getText();
                String piso = txtPiso.getText();
                if(unPais !=null)
                {
                    unaProvincia = unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado el País","",JOptionPane.ERROR_MESSAGE);
                    ok= false;
                }
                
                if(unaProvincia != null)
                {
                    unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado la Provincia","",JOptionPane.ERROR_MESSAGE);
                     ok= false;
                }   
                
                if(unaCiudad !=null)
                {
                      unBarrio = unaCiudad.buscarBarrio(cmbBarrio.getSelectedItem().toString());
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No ha seleccionado la Ciudad","",JOptionPane.ERROR_MESSAGE);
                     ok= false;
                }
                if(unBarrio ==null)
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
                
                if(unDomicilio == null)
                {
                    JOptionPane.showMessageDialog(null,"No ha selecciona un Domicilio","",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                    
                }
                if(ok == true)
                {
                    unCliente.modificarDireccion(unDomicilio, unPais, unaProvincia, unaCiudad, unBarrio, calle, numeroCasa, piso, dpto);
                    JOptionPane.showMessageDialog(null,"Se modificó el Domicilio seleccionado","",JOptionPane.INFORMATION_MESSAGE);
                    this.cargarDomicilios(unCliente);
                    cargarDomiciliosDeCliente(unCliente);
                    jXTaskPane1.setCollapsed(true);
                    btnNuevo.setEnabled(true);
                    btnModificiar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    tablaDomicilios.setEnabled(true);
                    btnGuardarChofer.setEnabled(false);
                    btnGuardarModificaciones.setEnabled(false);
                    tablaDirecciones.setEnabled(true);   
                            tablaDirecciones.setRowSelectionInterval(0, 0);
                    if(cmbDireccionCliente!= null)
                    {
                        cargarDireccionCliente();
                    }                    
                    this.limpiar();
                    
               }   
    }
    
    public void eliminarDomicilio(){
        String numeroDomicilio = (String) tablaDomicilios.getValueAt(tablaDomicilios.getSelectedRow(), 5);
        Domicilio unDomicilio = unCliente.buscarDomicilio(numeroDomicilio,unCliente);
        unCliente.eliminarDomicilio(unDomicilio);
        this.cargarDomicilios(unCliente);
        cargarDomiciliosDeCliente(unCliente);
        if(cmbDireccionCliente!= null)
        {
            cargarDireccionCliente();
        }        
        JOptionPane.showMessageDialog(null,"El Domicilio del Cliente a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);        
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
        btnNuevo = new javax.swing.JButton();
        btnModificiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDomicilios = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cmbProvincia = new javax.swing.JComboBox();
        cmbPais = new javax.swing.JComboBox();
        btnPais = new javax.swing.JButton();
        btnProvincia = new javax.swing.JButton();
        bnCiudad = new javax.swing.JButton();
        cmbCiudad = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cmbBarrio = new javax.swing.JComboBox();
        btnBarrio = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNumeroCasa = new javax.swing.JTextField();
        txtDpto = new javax.swing.JTextField();
        txtPiso = new javax.swing.JTextField();
        btnGuardarChofer = new javax.swing.JButton();
        btnGuardarModificaciones = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnNuevo.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnNuevo.setText("Agregar");
        btnNuevo.setHideActionText(true);
        btnNuevo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevo.setPreferredSize(new java.awt.Dimension(49, 23));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificiar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificiar.setText("Modificar");
        btnModificiar.setHideActionText(true);
        btnModificiar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificiar.setPreferredSize(new java.awt.Dimension(49, 23));
        btnModificiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificiarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setHideActionText(true);
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.setPreferredSize(new java.awt.Dimension(49, 23));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHideActionText(true);
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir.setPreferredSize(new java.awt.Dimension(49, 23));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnModificiar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tablaDomicilios.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaDomicilios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "País", "Provincia", "Ciudad", "Barrio", "Calle", "N°", "Dpto", "Piso"
            }
        ));
        tablaDomicilios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDomiciliosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDomicilios);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel1.setText("Gestor de Domicilios");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Cliente:");

        txtCliente.setEditable(false);
        txtCliente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCliente.setBackground(new java.awt.Color(204, 204, 204));
        txtCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jXTaskPane1.setCollapsed(true);
        jXTaskPane1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document-new.png"))); // NOI18N
        jXTaskPane1.setTitle("Datos del domicilio del cliente");

        jLabel24.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel24.setText("País:");

        jLabel25.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel25.setText("Provincia:");

        cmbProvincia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbProvincia.setNextFocusableComponent(cmbCiudad);
        cmbProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvinciaActionPerformed(evt);
            }
        });

        cmbPais.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbPais.setNextFocusableComponent(cmbProvincia);
        cmbPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaisActionPerformed(evt);
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

        cmbCiudad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbCiudad.setNextFocusableComponent(cmbBarrio);
        cmbCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCiudadActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel26.setText("Ciudad:");

        jLabel27.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel27.setText("Barrio:");

        cmbBarrio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbBarrio.setNextFocusableComponent(txtCalle);

        btnBarrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarrioActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel17.setText("Calle:");

        jLabel18.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel18.setText("N°:");

        jLabel28.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel28.setText("Dpto:");

        jLabel29.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel29.setText("Piso:");

        txtCalle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel3.setText("Datos del Domicilio");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3))
        );

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

        txtPiso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPiso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPiso.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(cmbCiudad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bnCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(cmbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addComponent(txtNumeroCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDpto, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPiso, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(btnPais, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(txtNumeroCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel26)
                                .addComponent(bnCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDpto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28)))
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel29)
                                    .addComponent(txtPiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel27))
                                    .addComponent(btnBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbPais)
                                    .addComponent(jLabel24))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25))))
                        .addGap(90, 90, 90))))
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
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jXTaskPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaisActionPerformed
        // TODO add your handling code here:
        if (cmbPais.getItemCount()!= 0)
        {
            String pais = (cmbPais.getSelectedItem().toString());
            if(!pais.isEmpty())
            {
                Pais unPais = remiseria.buscarPais(pais);
                if(unPais != null)
                {
                    this.cargarProvinciasCombo(unPais);
                }
            }
        }
    }//GEN-LAST:event_cmbPaisActionPerformed

    private void cmbProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinciaActionPerformed
        // TODO add your handling code here:
         if (cmbProvincia.getItemCount()!= 0){
            String nombrePais = cmbPais.getSelectedItem().toString();
            if(!nombrePais.isEmpty())
            {
            Pais unPais = remiseria.buscarPais(nombrePais);
                 if (unPais != null)
                 {
                        String nombreProvincia = cmbProvincia.getSelectedItem().toString();
                        if(!nombreProvincia.isEmpty())
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
            String nombrePais = cmbPais.getSelectedItem().toString();
            if(!nombrePais.isEmpty())
            {
                Pais unPais = remiseria.buscarPais(nombrePais);
                 if (unPais != null)
                 {
                      String nombreProvincia = cmbProvincia.getSelectedItem().toString();
                      if(!nombreProvincia.isEmpty())
                      {
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
            }
        } 
    }//GEN-LAST:event_cmbCiudadActionPerformed

    private void btnPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaisActionPerformed
        // TODO add your handling code here:
        JDialogPais ventana = new JDialogPais(parent, modal, cmbPais, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnPaisActionPerformed

    private void btnProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProvinciaActionPerformed
        // TODO add your handling code here:
        if (cmbPais.getSelectedItem().toString() != " ") {
            JDialogProvincia ventana = new JDialogProvincia(parent, modal, cmbProvincia, remiseria.buscarPais(cmbPais.getSelectedItem().toString()),remiseria);
            ventana.show();
        }
    }//GEN-LAST:event_btnProvinciaActionPerformed

    private void bnCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnCiudadActionPerformed
        // TODO add your handling code here:
        if (cmbProvincia.getSelectedItem().toString() != " ") {
            Pais unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
            JDialogCiudad ventana = new JDialogCiudad(parent, modal, cmbCiudad, unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString()),remiseria);
            ventana.show();
        }
    }//GEN-LAST:event_bnCiudadActionPerformed

    private void btnBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarrioActionPerformed
        // TODO add your handling code here:
        if (cmbCiudad.getSelectedItem().toString() != " ") {
            Pais unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
            Provincia unaProvincia = unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString());
            Ciudad unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
            JDialogBarrio ventana = new JDialogBarrio(parent, modal, cmbBarrio, unaCiudad,remiseria);
            ventana.show();
        }
    }//GEN-LAST:event_btnBarrioActionPerformed

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
        if(txtNumeroCasa.getText().length()== limite)
        {
            evt.consume();
        }   
    }//GEN-LAST:event_txtNumeroCasaKeyTyped

    private void txtPisoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPisoKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 5;
        if(txtPiso.getText().length()== limite)
        {
            evt.consume();
        }          
    }//GEN-LAST:event_txtPisoKeyTyped

    private void txtCalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCalleMouseClicked
        // TODO add your handling code here:
        txtCalle.setBackground(Color.white);
    }//GEN-LAST:event_txtCalleMouseClicked

    private void txtNumeroCasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroCasaMouseClicked
        // TODO add your handling code here:
        txtNumeroCasa.setBackground(Color.white);
    }//GEN-LAST:event_txtNumeroCasaMouseClicked

    private void txtDptoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDptoMouseClicked
        // TODO add your handling code here:
        txtDpto.setBackground(Color.white);
    }//GEN-LAST:event_txtDptoMouseClicked

    private void txtPisoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPisoMouseClicked
        // TODO add your handling code here:
        txtPiso.setBackground(Color.white);
    }//GEN-LAST:event_txtPisoMouseClicked

    private void tablaDomiciliosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDomiciliosMouseClicked
        // TODO add your handling code here:
//        this.obtenerDomicilio();
    }//GEN-LAST:event_tablaDomiciliosMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        btnNuevo.setEnabled(false);
        btnModificiar.setEnabled(false);
        btnEliminar.setEnabled(false);
        tablaDomicilios.setEnabled(false);
        btnGuardarChofer.setEnabled(true);
        btnGuardarModificaciones.setEnabled(false);
        cmbPais.requestFocus();
        jXTaskPane1.setCollapsed(false);
        cmbPais.setEnabled(true);
        cmbProvincia.setEnabled(true);
        cmbCiudad.setEnabled(true);
        cmbBarrio.setEnabled(true);
        btnPais.setEnabled(true);
        btnProvincia.setEnabled(true);
        bnCiudad.setEnabled(true);
        btnBarrio.setEnabled(true);
        txtCalle.setEnabled(true);
        txtNumeroCasa.setEnabled(true);
        txtDpto.setEnabled(true);
        txtPiso.setEnabled(true);       

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificiarActionPerformed
        // TODO add your handling code here:
        if(tablaDomicilios.getSelectedRow()!=-1)
        {
            this.obtenerDomicilio();
            btnNuevo.setEnabled(false);
            btnModificiar.setEnabled(false);
            btnEliminar.setEnabled(false);
            tablaDomicilios.setEnabled(false);
            cmbPais.requestFocus();
            btnGuardarChofer.setEnabled(false);
            btnGuardarModificaciones.setEnabled(true);   
            jXTaskPane1.setCollapsed(false);
            cmbPais.setEnabled(true);
            cmbProvincia.setEnabled(true);
            cmbCiudad.setEnabled(true);
            cmbBarrio.setEnabled(true);
            btnPais.setEnabled(true);
            btnProvincia.setEnabled(true);
            bnCiudad.setEnabled(true);
            btnBarrio.setEnabled(true);
            txtCalle.setEnabled(true);
            txtNumeroCasa.setEnabled(true);
            txtDpto.setEnabled(true);
            txtPiso.setEnabled(true);               
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una Domicilio"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
       if(tablaDomicilios.getSelectedRow()!=-1)
        {
            this.eliminarDomicilio();
            this.cargarPaisCombo();
            this.limpiar();

        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Domicilio"," ",JOptionPane.ERROR_MESSAGE);
        }  
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarChoferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarChoferActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        this.agregarDomicilio();
    }//GEN-LAST:event_btnGuardarChoferActionPerformed

    private void btnGuardarModificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionesActionPerformed
        // TODO add your handling code here:
        if(tablaDomicilios.getSelectedRow()!=-1)
        {
            this.modificarDomicilio();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una Domicilio"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarModificacionesActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:

        jXTaskPane1.setCollapsed(true);
        btnNuevo.setEnabled(true);
        btnModificiar.setEnabled(true);
        btnEliminar.setEnabled(true);
        tablaDomicilios.setEnabled(true);
        btnGuardarChofer.setEnabled(false);
        btnGuardarModificaciones.setEnabled(false);
        cmbPais.setEnabled(false);
        cmbProvincia.setEnabled(false);
        cmbCiudad.setEnabled(false);
        cmbBarrio.setEnabled(false);
        btnPais.setEnabled(false);
        btnProvincia.setEnabled(false);
        bnCiudad.setEnabled(false);
        btnBarrio.setEnabled(false);
        txtCalle.setEnabled(false);
        txtNumeroCasa.setEnabled(false);
        txtDpto.setEnabled(false);
        txtPiso.setEnabled(false);   
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleKeyTyped
        // TODO add your handling code here:
        int limite  = 40;
        if(txtCalle.getText().length()== limite)
        {
            evt.consume();
        }          
    }//GEN-LAST:event_txtCalleKeyTyped

    private void txtDptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDptoKeyTyped
        // TODO add your handling code here:
        int limite = 5;
        if(txtDpto.getText().length()== limite)
        {
            evt.consume();
        }        
    }//GEN-LAST:event_txtDptoKeyTyped

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
            java.util.logging.Logger.getLogger(JDialogEditarDomicilios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogEditarDomicilios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogEditarDomicilios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogEditarDomicilios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogEditarDomicilios dialog = new JDialogEditarDomicilios(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bnCiudad;
    private javax.swing.JButton btnBarrio;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarChofer;
    private javax.swing.JButton btnGuardarModificaciones;
    private javax.swing.JButton btnModificiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPais;
    private javax.swing.JButton btnProvincia;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cmbBarrio;
    private javax.swing.JComboBox cmbCiudad;
    private javax.swing.JComboBox cmbPais;
    private javax.swing.JComboBox cmbProvincia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaDomicilios;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDpto;
    private javax.swing.JTextField txtNumeroCasa;
    private javax.swing.JTextField txtPiso;
    // End of variables declaration//GEN-END:variables
}
