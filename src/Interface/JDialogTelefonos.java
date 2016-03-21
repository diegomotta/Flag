/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Clases.Cliente;
import Clases.CodArea;
import Clases.CodPais;
import Clases.Remiseria;
import Clases.Telefono;
import Clases.TipoTelefono;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author garba
 */
public class JDialogTelefonos extends javax.swing.JDialog {
private Cliente unCliente;
private Remiseria remiseria;
private java.awt.Frame parent;
private boolean modal;
private JTable tablaTelefono;
    /**
     * Creates new form JDialogTelefonos
     */
    public JDialogTelefonos(java.awt.Frame parent, boolean modal, Remiseria remiseria, Cliente unCliente, JTable tablaTelefono) {
        super(parent, modal);
        this.unCliente = unCliente;
        this.remiseria = remiseria;
        this.tablaTelefono = tablaTelefono;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        txtCliente.setText(unCliente.getNombre()+" "+unCliente.getApellido());
        this.cargarCodPaisCombo();
        this.cargarTipoTelefonoCombo();
        this.cargarTelefonos(unCliente);
        AutoCompleteDecorator.decorate(this.cmbCodPais);
        AutoCompleteDecorator.decorate(this.cmbCodArea);
        AutoCompleteDecorator.decorate(this.cmbTipodeTelefono);  
        cmbCodPais.setEnabled(false);
        btnCodigoTelefono.setEnabled(false);
        cmbCodArea.setEnabled(false);
        btnCodigoTelefono1.setEnabled(false);
        txtNumeroTel.setEnabled(false);
        cmbTipodeTelefono.setEnabled(false);
        btnTipoTelefono.setEnabled(false);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);
        jXTaskPane1.setEnabled(true);
    }
    
        public  void cargarTelefonosCliente(Cliente unCliente){
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
            th = tablaTelefono.getTableHeader(); 
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
            tablaTelefono.setModel(modelo);
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
    
    public void cargarCodigoProvinciaCombo( CodPais unCodPais)
    {
        cmbCodArea.removeAllItems();
        List codigoProvincia = unCodPais.buscarCodigosAreas();
        if(codigoProvincia != null)
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
    
    
    public  void cargarTelefonos(Cliente unCliente){
        List telefonos = unCliente.buscarTelefonos(unCliente);
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaTelefono.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);          
        modelo.addColumn("Código de País");
        modelo.addColumn("Código de Área");
        modelo.addColumn("N° Personal");
        modelo.addColumn("Tipo de Teléfono");
        Telefono aux = null;
        Iterator iter = telefonos.iterator();
        while (iter.hasNext()){
            aux = (Telefono) iter.next();
            Object [] fila = new Object[4];
            
            fila[0] = aux.getUnCodPais().getCodPais();
            fila[1] = aux.getUnCodArea().getCodArea();
            fila[2] = aux.getNroTelefonico();
            fila[3] = aux.getUnTipoTelefono().getNombreTipoTelefono().toString();

            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaTelefonos.setModel(modelo);
    }

    public void agregarTelefono ()
    {
                boolean ok = true;
                CodPais unCodPais = null;
                CodArea unCodArea = null;
                String numeroTelefono = txtNumeroTel.getText();
                TipoTelefono unTipoTelefono = null;
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
  
                if(ok == true)
                {
                    if(unCliente.existeTelefono(unCodPais, unCodArea, numeroTelefono) == false)
                    {
                        unCliente.agregarTelefono(numeroTelefono, unCodArea, unCodPais, unTipoTelefono);
                        JOptionPane.showMessageDialog(null,"Se registró un nuevo Teléfono ","",JOptionPane.INFORMATION_MESSAGE);
                        this.cargarTelefonos(unCliente);
                        cargarTelefonosCliente(unCliente);
                        jXTaskPane1.setCollapsed(true);
                        btnNuevo.setEnabled(true);
                        btnModificiar.setEnabled(true);
                        btnEliminar.setEnabled(true);
                        tablaTelefono.setEnabled(true);
                        btnGuardarChofer.setEnabled(false);
                        btnGuardarModificaciones.setEnabled(false);
                        tablaTelefono.setRowSelectionInterval(0, 0);
                        this.limpiar();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Ya existe el Número Telefónico ","",JOptionPane.ERROR_MESSAGE);
                        txtNumeroTel.setBackground(Color.red);
                    }
                }
        
    }
    
    
    public void modificarTelefono()
    {
                boolean ok = true;
                String numeroTelefonico = (String) tablaTelefonos.getValueAt(tablaTelefonos.getSelectedRow(), 2);
                Telefono unTelefono = unCliente.buscarTelefono(numeroTelefonico);
                CodPais unCodPais = null;
                CodArea unCodArea = null;
                String numeroTelefono = txtNumeroTel.getText();
                TipoTelefono unTipoTelefono = null;
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
  
                                             
                if(unTelefono != null)
                {
                    if(ok == true)
                    {
                        unCliente.modificarTelefono(unTelefono, numeroTelefono, unCodArea, unCodPais, unTipoTelefono);
                        JOptionPane.showMessageDialog(null,"Se modificó el Teléfono seleccionado","",JOptionPane.INFORMATION_MESSAGE);
                        this.cargarTelefonos(unCliente);
                        cargarTelefonosCliente(unCliente);
                        jXTaskPane1.setCollapsed(true);
                        btnNuevo.setEnabled(true);
                        btnModificiar.setEnabled(true);
                        btnEliminar.setEnabled(true);
                        tablaTelefono.setEnabled(true);
                        btnGuardarChofer.setEnabled(false);
                        btnGuardarModificaciones.setEnabled(false);    
                        tablaTelefono.setRowSelectionInterval(0, 0);
                        this.limpiar();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado un Teléfono","",JOptionPane.ERROR_MESSAGE);
                }   
     }
    
    public void eliminarTelefono ()
    {
        String numeroTelefono = (String) tablaTelefonos.getValueAt(tablaTelefonos.getSelectedRow(), 2);
        Telefono unTelefono = unCliente.buscarTelefono(numeroTelefono);
        unCliente.eliminarTelefono(unTelefono,remiseria);
        this.cargarTelefonos(unCliente);
        cargarTelefonosCliente(unCliente);
        JOptionPane.showMessageDialog(null,"El Teléfono del Cliente a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);

    }
    
     public void obtenerTelefono()
     {
          String numeroTelefonico = (String) tablaTelefonos.getValueAt(tablaTelefonos.getSelectedRow(), 2);
          Telefono unTelefono = unCliente.buscarTelefono(numeroTelefonico);
          cmbCodPais.setSelectedItem(unTelefono.getUnCodPais().getCodPais());
          cmbCodArea.setSelectedItem(unTelefono.getUnCodArea().getCodArea());
          txtNumeroTel.setText(unTelefono.getNroTelefonico());
          cmbTipodeTelefono.setSelectedItem(unTelefono.getUnTipoTelefono().getNombreTipoTelefono());
     
     }
     
     public void limpiar()
     {
         this.cargarCodPaisCombo();
         cmbCodArea.removeAllItems();
         txtNumeroTel.setText("");
         txtNumeroTel.setBackground(Color.white);
         this.cargarTipoTelefonoCombo();
     
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTelefonos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        cmbCodPais = new javax.swing.JComboBox();
        btnCodigoTelefono = new javax.swing.JButton();
        cmbCodArea = new javax.swing.JComboBox();
        btnCodigoTelefono1 = new javax.swing.JButton();
        txtNumeroTel = new javax.swing.JTextField();
        cmbTipodeTelefono = new javax.swing.JComboBox();
        btnTipoTelefono = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnGuardarModificaciones = new javax.swing.JButton();
        btnGuardarChofer = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaTelefonos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaTelefonos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de País", "Código de Área", "N° Personal", "Tipo de Teléfono"
            }
        ));
        tablaTelefonos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTelefonosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaTelefonos);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnNuevo.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnNuevo.setText("Agregar");
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
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnModificiar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel1.setText("Gestor de Teléfonos");

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

        txtCliente.setEditable(false);
        txtCliente.setBackground(new java.awt.Color(204, 204, 204));
        txtCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Cliente:");

        jXTaskPane1.setCollapsed(true);
        jXTaskPane1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document-new.png"))); // NOI18N
        jXTaskPane1.setTitle("Datos del teléfono");

        cmbCodPais.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbCodPais.setNextFocusableComponent(cmbCodArea);
        cmbCodPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodPaisActionPerformed(evt);
            }
        });

        btnCodigoTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnCodigoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodigoTelefonoActionPerformed(evt);
            }
        });

        cmbCodArea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbCodArea.setNextFocusableComponent(txtNumeroTel);

        btnCodigoTelefono1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnCodigoTelefono1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodigoTelefono1ActionPerformed(evt);
            }
        });

        txtNumeroTel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNumeroTel.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        cmbTipodeTelefono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnTipoTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnTipoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipoTelefonoActionPerformed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel37.setText("Tipo de teléfono:");

        jLabel38.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel38.setText("Cod. área:");

        jLabel39.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel39.setText("Cod. país:");

        jLabel40.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel40.setText("N°:");

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("Datos del Telefono");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 540, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3))
        );

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCodigoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCodigoTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroTel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(cmbTipodeTelefono, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel37))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbTipodeTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCodigoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumeroTel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCodigoTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(12, Short.MAX_VALUE))
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
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPane1Layout.createSequentialGroup()
                        .addComponent(btnGuardarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jXTaskPane1Layout.setVerticalGroup(
            jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarChofer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jXTaskPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
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
                        .addGap(7, 7, 7)
                        .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCodPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodPaisActionPerformed
        // TODO add your handling code here:
        if (cmbCodPais.getItemCount()!= 0){
            String codigoPais = (cmbCodPais.getSelectedItem().toString());
            if(!codigoPais.isEmpty())
            {
                CodPais unCodPais = remiseria.buscarCodigoPais(codigoPais);
                if(unCodPais != null)
                {
                    this.cargarCodigoProvinciaCombo(unCodPais);
                }
            }
        }
    }//GEN-LAST:event_cmbCodPaisActionPerformed

    private void btnTipoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipoTelefonoActionPerformed
        // TODO add your handling code here:
        JDialogTipoTelefono ventana = new JDialogTipoTelefono (parent,modal,cmbTipodeTelefono,remiseria);
        ventana.show();
    }//GEN-LAST:event_btnTipoTelefonoActionPerformed

    private void txtNumeroTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroTelKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 10;
        if(txtNumeroTel.getText().length()== limite)
        {
            evt.consume();
        }      
    }//GEN-LAST:event_txtNumeroTelKeyTyped

    private void btnCodigoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodigoTelefonoActionPerformed
        // TODO add your handling code here:
        JDialogCodPaisTelefono ventana = new  JDialogCodPaisTelefono (parent,  modal,cmbCodPais, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnCodigoTelefonoActionPerformed

    private void btnCodigoTelefono1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodigoTelefono1ActionPerformed
        // TODO add your handling code here:
        if (cmbCodPais.getItemCount()!= 0){
            JDialogCodAreaTelefono ventana = new JDialogCodAreaTelefono(parent, modal, cmbCodArea, remiseria.buscarCodigoPais((cmbCodPais.getSelectedItem().toString())),remiseria) ;
            ventana.show();}
    }//GEN-LAST:event_btnCodigoTelefono1ActionPerformed

    private void txtNumeroTelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroTelMouseClicked
        // TODO add your handling code here:
        txtNumeroTel.setBackground(Color.white);
    }//GEN-LAST:event_txtNumeroTelMouseClicked

    private void tablaTelefonosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTelefonosMouseClicked
        // TODO add your handling code here:
       // this.obtenerTelefono();
    }//GEN-LAST:event_tablaTelefonosMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        jXTaskPane1.setCollapsed(false);
        btnNuevo.setEnabled(false);
        btnModificiar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(true);
        tablaTelefonos.setEnabled(false);
        cmbCodPais.setEnabled(true);
        btnCodigoTelefono.setEnabled(true);
        cmbCodArea.setEnabled(true);
        btnCodigoTelefono1.setEnabled(true);
        txtNumeroTel.setEnabled(true);
        cmbTipodeTelefono.setEnabled(true);
        btnTipoTelefono.setEnabled(true);
        cmbCodPais.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificiarActionPerformed
        // TODO add your handling code here:
        jXTaskPane1.setCollapsed(false);
        btnNuevo.setEnabled(false);
        btnModificiar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardarModificaciones.setEnabled(true);
        btnGuardarChofer.setEnabled(false);
        cmbCodPais.setEnabled(true);
        btnCodigoTelefono.setEnabled(true);
        cmbCodArea.setEnabled(true);
        btnCodigoTelefono1.setEnabled(true);
        txtNumeroTel.setEnabled(true);
        cmbTipodeTelefono.setEnabled(true);
        btnTipoTelefono.setEnabled(true);      
        tablaTelefonos.setEnabled(false);  
        cmbCodPais.requestFocus();
        this.obtenerTelefono();
    }//GEN-LAST:event_btnModificiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if(tablaTelefonos.getSelectedRow()!=-1)
        {
            this.eliminarTelefono();
            this.limpiar();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Telefono"," ",JOptionPane.ERROR_MESSAGE);
        }  
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarModificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionesActionPerformed
        // TODO add your handling code here:
        if(tablaTelefonos.getSelectedRow()!=-1)
        {
            this.modificarTelefono();

        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Telefono"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarModificacionesActionPerformed

    private void btnGuardarChoferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarChoferActionPerformed
        // TODO add your handling code here:
        this.agregarTelefono();   
    }//GEN-LAST:event_btnGuardarChoferActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:

        jXTaskPane1.setCollapsed(true);
        btnNuevo.setEnabled(true);
        btnModificiar.setEnabled(true);
        btnEliminar.setEnabled(true);
        tablaTelefono.setEnabled(true);
        btnGuardarChofer.setEnabled(false);
        btnGuardarModificaciones.setEnabled(false);
        cmbCodPais.setEnabled(false);
        btnCodigoTelefono.setEnabled(false);
        cmbCodArea.setEnabled(false);
        btnCodigoTelefono1.setEnabled(false);
        txtNumeroTel.setEnabled(false);
        cmbTipodeTelefono.setEnabled(false);
        btnTipoTelefono.setEnabled(false);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogTelefonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogTelefonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogTelefonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogTelefonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogTelefonos dialog = new JDialogTelefonos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCodigoTelefono;
    private javax.swing.JButton btnCodigoTelefono1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarChofer;
    private javax.swing.JButton btnGuardarModificaciones;
    private javax.swing.JButton btnModificiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTipoTelefono;
    private javax.swing.JComboBox cmbCodArea;
    private javax.swing.JComboBox cmbCodPais;
    private javax.swing.JComboBox cmbTipodeTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaTelefonos;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtNumeroTel;
    // End of variables declaration//GEN-END:variables
}
