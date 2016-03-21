/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Remiseria;
import Clases.TipoTelefono;
import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author garba
 */
public class JDialogTipoTelefono extends javax.swing.JDialog {
private java.awt.Frame parent;
private   boolean modal;
private JComboBox cmbTipoTelefono;
private Remiseria remiseria;
    /**
     * Creates new form JDialogTipoTelefono
     */
    public JDialogTipoTelefono(java.awt.Frame parent, boolean modal, JComboBox cmbTipoTelefono, Remiseria remiseria) {
        super(parent, modal);
        this.cmbTipoTelefono = cmbTipoTelefono;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        btnAgregarTipoTelefono.setEnabled(true);
        btnModificarTipoTelefono.setEnabled(true);
        btnEliminarTipoTelefono.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtTipoTelefono.setEnabled(false);
        txtTipoTelefono.setText(null);
        tablaTipoTelefono.setEnabled(true);        
    }


    
     public void cargarTipoTelefonoCombo(){
        cmbTipoTelefono.removeAllItems();
        List telefonos =remiseria.buscarTiposTelefonos();
        if(telefonos != null)
        {
            TipoTelefono aux = null;
            Iterator iter = telefonos.iterator();
            while (iter.hasNext())
            {
                aux = (TipoTelefono) iter.next();
                cmbTipoTelefono.addItem(aux.getNombreTipoTelefono());
            }
        }
    }
    
     public void cargarTabla(){
        Collection telefonos = remiseria.buscarTiposTelefonos(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaTipoTelefono.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);
        modelo.addColumn("Tipos de Teléfonos");
       
        TipoTelefono aux = null;
       
        Iterator iter = telefonos.iterator();
            while (iter.hasNext()){
            aux = (TipoTelefono) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreTipoTelefono();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaTipoTelefono.setModel(modelo);
    }
     

    
    public void eliminarTipoTelefono(){
        String nombreTipoTelefono = (String) tablaTipoTelefono.getValueAt(tablaTipoTelefono.getSelectedRow(), 0);
        remiseria.eliminarTipoTelefono(nombreTipoTelefono);
        this.cargarTabla();
        cargarTipoTelefonoCombo();
        JOptionPane.showMessageDialog(null,"El Tipo de Teléfono a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);
        
    }

    
    
    public void obtenerTipoTelefono(){
        int fila = tablaTipoTelefono.getSelectedRow();
        String nombreTipoTelefono = (String) tablaTipoTelefono.getValueAt(fila, 0);
        txtTipoTelefono.setText(nombreTipoTelefono);
    }

    public void agregarNuevoTipoTelefono(){
        String nombreTipoTelefono = txtTipoTelefono.getText();
        if(remiseria.buscarTipoTelefono(nombreTipoTelefono)==null){
            remiseria.agregarTipoTelefono(nombreTipoTelefono);
            this.cargarTabla();
            this.cargarTipoTelefonoCombo();
            txtTipoTelefono.setText(null);
            JOptionPane.showMessageDialog(null,"Se registro un nuevo Tipo de Teléfono"," ",JOptionPane.INFORMATION_MESSAGE);
            btnAgregarTipoTelefono.setEnabled(true);
            btnModificarTipoTelefono.setEnabled(true);
            btnEliminarTipoTelefono.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtTipoTelefono.setEnabled(false);
            txtTipoTelefono.setText(null);
            tablaTipoTelefono.setEnabled(true);   
        }
        else
        {
                JOptionPane.showMessageDialog(null,"Ya existe el Tipo de Teléfono","",JOptionPane.ERROR_MESSAGE);
                txtTipoTelefono.setText(null);
        }
    }
    
    
    public void modificarTipoTelefono()
    {
        int fila = tablaTipoTelefono.getSelectedRow();
        String tipoTelefonoSeleccionado = (String) tablaTipoTelefono.getValueAt(fila, 0);
        TipoTelefono unTipoTelefono = remiseria.buscarTipoTelefono(tipoTelefonoSeleccionado);
        String tipoTelefonoModificar = (txtTipoTelefono.getText());
         if (remiseria.buscarTipoTelefono(tipoTelefonoModificar) == null){
            remiseria.modificarTipoTelefono(unTipoTelefono,tipoTelefonoModificar);
            this.cargarTabla();
            this.cargarTipoTelefonoCombo();
            txtTipoTelefono.setText(null);
            JOptionPane.showMessageDialog(null,"Se modificó el Tipo de Teléfono seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);
            btnAgregarTipoTelefono.setEnabled(true);
            btnModificarTipoTelefono.setEnabled(true);
            btnEliminarTipoTelefono.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtTipoTelefono.setEnabled(false);
            txtTipoTelefono.setText(null);
            tablaTipoTelefono.setEnabled(true);            
        }
            else
            {
                 JOptionPane.showMessageDialog(null,"Ya existe el Tipo de Telefono"," ",JOptionPane.ERROR_MESSAGE);
                txtTipoTelefono.setText(null);
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

        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTipoTelefono = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtTipoTelefono = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnGuarda = new javax.swing.JButton();
        btnGuardaModificacion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnModificarTipoTelefono = new javax.swing.JButton();
        btnAgregarTipoTelefono = new javax.swing.JButton();
        btnEliminarTipoTelefono = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jScrollPane3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tablaTipoTelefono.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaTipoTelefono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaTipoTelefono.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de Teléfono"
            }
        ));
        tablaTipoTelefono.setGridColor(new java.awt.Color(0, 0, 0));
        tablaTipoTelefono.setRowHeight(20);
        tablaTipoTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTipoTelefonoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaTipoTelefono);

        txtTipoTelefono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTipoTelefono.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTipoTelefono.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTipoTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTipoTelefonoMouseClicked(evt);
            }
        });
        txtTipoTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTipoTelefonoKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Tipo de teléfono:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTipoTelefono)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuarda.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuarda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/media-floppy.png"))); // NOI18N
        btnGuarda.setText("Guardar");
        btnGuarda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaActionPerformed(evt);
            }
        });

        btnGuardaModificacion.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardaModificacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnGuardaModificacion.setText("<html><p>Guardar</p><p>Modificación</p></html>");
        btnGuardaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardaModificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaModificacionActionPerformed(evt);
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

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnModificarTipoTelefono.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarTipoTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarTipoTelefono.setText("Modificar");
        btnModificarTipoTelefono.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarTipoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarTipoTelefonoActionPerformed(evt);
            }
        });

        btnAgregarTipoTelefono.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregarTipoTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregarTipoTelefono.setText("Agregar");
        btnAgregarTipoTelefono.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarTipoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTipoTelefonoActionPerformed(evt);
            }
        });

        btnEliminarTipoTelefono.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarTipoTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarTipoTelefono.setText("Eliminar");
        btnEliminarTipoTelefono.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarTipoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTipoTelefonoActionPerformed(evt);
            }
        });

        btnAceptar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnAceptar.setText("Salir");
        btnAceptar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnAgregarTipoTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarTipoTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarTipoTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregarTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel3.setText("Gestor del tipo de teléfono");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
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
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarTipoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTipoTelefonoActionPerformed
        // TODO add your handling code here:
        btnAgregarTipoTelefono.setEnabled(false);
        btnModificarTipoTelefono.setEnabled(false);
        btnEliminarTipoTelefono.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtTipoTelefono.setEnabled(true);
        txtTipoTelefono.setText(null);
        tablaTipoTelefono.setEnabled(false);        
        txtTipoTelefono.requestFocus();
    }//GEN-LAST:event_btnAgregarTipoTelefonoActionPerformed

    private void btnEliminarTipoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTipoTelefonoActionPerformed
        // TODO add your handling code here:
        if(tablaTipoTelefono.getSelectedRow()!=-1)
        {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el Tipo de Teléfono seleccionado?");
            if ( eleccion == 0)
            {  
                txtTipoTelefono.setText("");
                this.eliminarTipoTelefono();
                this.cargarTabla();
                this.cargarTipoTelefonoCombo();
            }
         }
         else
         {
             JOptionPane.showMessageDialog(null,"No ha seleccionado un Tipo de Teléfono"," ",JOptionPane.ERROR_MESSAGE); 
         }

    }//GEN-LAST:event_btnEliminarTipoTelefonoActionPerformed

    private void tablaTipoTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTipoTelefonoMouseClicked
        // TODO add your handling code here:
         //this.obtenerTipoTelefono();
         txtTipoTelefono.setBackground(Color.white);
    }//GEN-LAST:event_tablaTipoTelefonoMouseClicked

    private void btnModificarTipoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarTipoTelefonoActionPerformed
        // TODO add your handling code here:
        if(tablaTipoTelefono.getSelectedRow()!=-1)
        {   
            this.obtenerTipoTelefono();
            btnAgregarTipoTelefono.setEnabled(false);
            btnModificarTipoTelefono.setEnabled(false);
            btnEliminarTipoTelefono.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtTipoTelefono.setEnabled(true);
            tablaTipoTelefono.setEnabled(false);  
            txtTipoTelefono.requestFocus();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Tipo de Teléfono para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnModificarTipoTelefonoActionPerformed

    private void txtTipoTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTipoTelefonoMouseClicked
        // TODO add your handling code here:
        txtTipoTelefono.setBackground(Color.white);
    }//GEN-LAST:event_txtTipoTelefonoMouseClicked

    private void txtTipoTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoTelefonoKeyTyped
        // TODO add your handling code here:
       char c;        
       c = evt.getKeyChar();
       if (!(c < '0' || c > '9'))
       {
          evt.consume();
       }
        int limite  = 15;
        {if (txtTipoTelefono.getText().length()== limite)
            evt.consume();
        }           
    }//GEN-LAST:event_txtTipoTelefonoKeyTyped

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
       if(!txtTipoTelefono.getText().isEmpty())
        {
            this.agregarNuevoTipoTelefono();
        }
        else
        {
            if(txtTipoTelefono.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el Tipo de Teléfono"," ",JOptionPane.ERROR_MESSAGE);
                  txtTipoTelefono.setBackground(Color.red);
            }      
        }
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
       if(!txtTipoTelefono.getText().isEmpty())
        {
            this.modificarTipoTelefono();
        }
        else
        {
            if(txtTipoTelefono.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el Tipo de Teléfono"," ",JOptionPane.ERROR_MESSAGE);
                  txtTipoTelefono.setBackground(Color.red);
            }      
        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnAgregarTipoTelefono.setEnabled(true);
        btnModificarTipoTelefono.setEnabled(true);
        btnEliminarTipoTelefono.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtTipoTelefono.setEnabled(false);
        txtTipoTelefono.setText(null);
        tablaTipoTelefono.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogTipoTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogTipoTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogTipoTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogTipoTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogTipoTelefono dialog = new JDialogTipoTelefono(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarTipoTelefono;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarTipoTelefono;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarTipoTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaTipoTelefono;
    private javax.swing.JTextField txtTipoTelefono;
    // End of variables declaration//GEN-END:variables
}
