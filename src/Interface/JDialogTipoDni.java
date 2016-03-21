/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Remiseria;
import Clases.TipoDni;
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
public class JDialogTipoDni extends javax.swing.JDialog {
private Remiseria remiseria;
private  JComboBox cmbTipoDni;
private java.awt.Frame parent;
private boolean modal;
    /**
     * Creates new form JDialogTipoDni
     */
    public JDialogTipoDni(java.awt.Frame parent, boolean modal,JComboBox cmbTipoDni, Remiseria remiseria) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.cmbTipoDni = cmbTipoDni;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        btnAgregarTipoDni.setEnabled(true);
        btnModificarTipoDni.setEnabled(true);
        btnEliminarTipoDni.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtTipoDni.setEnabled(false);
        txtTipoDni.setText(null);
        txtSiglas.setEnabled(false);
        txtSiglas.setText(null);
        tablaTipoDni.setEnabled(true);
    
    }
    
     public void cargarTiposDocumentos(){
        cmbTipoDni.removeAllItems();
        List tiposdnis =remiseria.buscarTiposDocumentos();
        if(tiposdnis != null)
        {
            TipoDni aux = null;
            Iterator iter = tiposdnis.iterator();
            while (iter.hasNext())
            {
                aux = (TipoDni) iter.next();
                cmbTipoDni.addItem(aux.getSiglas());
            }
        }
    }
    
     public void cargarTabla(){
        Collection tiposDnis = remiseria.buscarTiposDocumentos(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th;
        th = tablaTipoDni.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);
        modelo.addColumn("Descripción");
        modelo.addColumn("Siglas");
        TipoDni aux = null;
       
        Iterator iter = tiposDnis.iterator();
            while (iter.hasNext()){
            aux = (TipoDni) iter.next();
            Object [] fila = new Object[2];
            fila[0] = aux.getDescripcionTipoDNI();
            fila[1]= aux.getSiglas();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaTipoDni.setModel(modelo);
    }
     

    
    public void eliminarTipoDocumento(){
        String nombreSiglas = (String) tablaTipoDni.getValueAt(tablaTipoDni.getSelectedRow(), 1);
        remiseria.eliminarTipoDocumento(nombreSiglas);
        this.cargarTabla();
        cargarTiposDocumentos();
        JOptionPane.showMessageDialog(null,"El Tipo de Documento sido eliminado. \n Se sugiere modificar los datos de aquellos registros que contenian el Tipo de Documento eliminado", null, JOptionPane.INFORMATION_MESSAGE);
        
    }

    
    
    public void obtenerTipoDocumento(){
        int fila = tablaTipoDni.getSelectedRow();
        String nombreSiglas = (String) tablaTipoDni.getValueAt(fila, 1);
        TipoDni unTipoDni = remiseria.buscarTipoDocumento(nombreSiglas);
        txtSiglas.setText(unTipoDni.getSiglas());
        txtTipoDni.setText(unTipoDni.getDescripcionTipoDNI());

    }
    
      public void agregarNuevoTipoDni()
      {
            String nombreTipoDni = txtTipoDni.getText();
            String siglas = txtSiglas.getText();
            if(remiseria.buscarTipoDocumento(siglas)==null)
            {
                remiseria.agregartTipoDocumento(nombreTipoDni,siglas);
                this.cargarTabla();
                this.cargarTiposDocumentos();
                txtTipoDni.setText(null);
                txtSiglas.setText(null);
                JOptionPane.showMessageDialog(null,"Se registro un nuevo tipo de documento"," ",JOptionPane.INFORMATION_MESSAGE);
                btnAgregarTipoDni.setEnabled(true);
                btnModificarTipoDni.setEnabled(true);
                btnEliminarTipoDni.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                txtTipoDni.setEnabled(false);
                txtTipoDni.setText(null);
                txtSiglas.setEnabled(false);
                txtSiglas.setText(null);
                tablaTipoDni.setEnabled(true);            
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Ya existe el tipo de documento"," ",JOptionPane.ERROR_MESSAGE);
            }
            
     }
      
    public void modificarTipoDni(){
        int fila = tablaTipoDni.getSelectedRow();
        String codigo = (String) tablaTipoDni.getValueAt(fila, 1);
        TipoDni unTipoDni = remiseria.buscarTipoDocumento(codigo);
        String cod = (txtSiglas.getText());
        if (remiseria.buscarTipoDocumento(cod) == null || unTipoDni.getSiglas().equals(cod) )
        {
            String referencia = txtTipoDni.getText();
            remiseria.modificarTipoDocumento(unTipoDni, referencia, cod);
            this.cargarTiposDocumentos();
            this.cargarTabla();
            txtSiglas.setText(null);
            txtTipoDni.setText(null);
            JOptionPane.showMessageDialog(null,"Se ha modifica el tipo de documento seleccionado","",JOptionPane.INFORMATION_MESSAGE);
            btnAgregarTipoDni.setEnabled(true);
            btnModificarTipoDni.setEnabled(true);
            btnEliminarTipoDni.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtTipoDni.setEnabled(false);
            txtTipoDni.setText(null);
            txtSiglas.setEnabled(false);
            txtSiglas.setText(null);
            tablaTipoDni.setEnabled(true);      
        }
        else
        {
            if(remiseria.buscarTipoDocumento(cod) != null)
            {
                String referencia = txtTipoDni.getText();
                remiseria.modificarTipoDocumento(unTipoDni, referencia, cod);
                this.cargarTabla();
                txtSiglas.setText(null);
                txtTipoDni.setText(null);
                JOptionPane.showMessageDialog(null,"Se ha modificado el tipo de documento seleccionado","",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Ya existe el tipo de documento"," ",JOptionPane.ERROR_MESSAGE);
                txtSiglas.setText(null);
                txtTipoDni.setText(null);
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

        jPanel6 = new javax.swing.JPanel();
        btnModificarTipoDni = new javax.swing.JButton();
        btnAgregarTipoDni = new javax.swing.JButton();
        btnEliminarTipoDni = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTipoDni = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTipoDni = new javax.swing.JTextField();
        txtSiglas = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnGuardaModificacion = new javax.swing.JButton();
        btnGuarda = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnModificarTipoDni.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarTipoDni.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarTipoDni.setText("Modificar ");
        btnModificarTipoDni.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarTipoDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarTipoDniActionPerformed(evt);
            }
        });

        btnAgregarTipoDni.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregarTipoDni.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregarTipoDni.setText("Agregar ");
        btnAgregarTipoDni.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarTipoDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTipoDniActionPerformed(evt);
            }
        });

        btnEliminarTipoDni.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarTipoDni.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarTipoDni.setText("Eliminar ");
        btnEliminarTipoDni.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarTipoDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTipoDniActionPerformed(evt);
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
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnModificarTipoDni, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                        .addComponent(btnAgregarTipoDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnEliminarTipoDni, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregarTipoDni, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarTipoDni, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarTipoDni, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaTipoDni.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaTipoDni.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaTipoDni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descripción", "Siglas"
            }
        ));
        tablaTipoDni.setGridColor(new java.awt.Color(0, 0, 0));
        tablaTipoDni.setRowHeight(20);
        tablaTipoDni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTipoDniMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaTipoDni);

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Tipo de documento:");

        txtTipoDni.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTipoDni.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTipoDni.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTipoDni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTipoDniMouseClicked(evt);
            }
        });
        txtTipoDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTipoDniKeyTyped(evt);
            }
        });

        txtSiglas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSiglas.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtSiglas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSiglas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSiglasMouseClicked(evt);
            }
        });
        txtSiglas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSiglasKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Siglas:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTipoDni)
                    .addComponent(txtSiglas))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipoDni, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSiglas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardaModificacion.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardaModificacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnGuardaModificacion.setText("<html><p>Guardar</p><p>Modificación</p></html>");
        btnGuardaModificacion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardaModificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaModificacionActionPerformed(evt);
            }
        });

        btnGuarda.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuarda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/media-floppy.png"))); // NOI18N
        btnGuarda.setText("Guardar");
        btnGuarda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaActionPerformed(evt);
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

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel3.setText("Gestor del tipo de documento");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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

    private void btnAgregarTipoDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTipoDniActionPerformed
        // TODO add your handling code here:

            btnAgregarTipoDni.setEnabled(false);
            btnModificarTipoDni.setEnabled(false);
            btnEliminarTipoDni.setEnabled(false);
            btnGuarda.setEnabled(true);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(true);
            txtTipoDni.setEnabled(true);
            txtSiglas.setEnabled(true);
            tablaTipoDni.setEnabled(false);
            txtTipoDni.requestFocus(); 
    }//GEN-LAST:event_btnAgregarTipoDniActionPerformed

    private void btnEliminarTipoDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTipoDniActionPerformed
        // TODO add your handling code here:
         if(tablaTipoDni.getSelectedRow()!=-1){
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el Tipo de Documento seleccionado?");
            if ( eleccion == 0)
            {  
                this.eliminarTipoDocumento();
                this.cargarTabla();
                txtTipoDni.setText(null);
                txtSiglas.setText(null);
            }
         }
         else
         {
             JOptionPane.showMessageDialog(null,"No ha seleccionado un Tipo de Documento"," ",JOptionPane.ERROR_MESSAGE); 
         }
    }//GEN-LAST:event_btnEliminarTipoDniActionPerformed

    private void btnModificarTipoDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarTipoDniActionPerformed
        // TODO add your handling code here:
        if(tablaTipoDni.getSelectedRow()!=-1)
        { 
            this.obtenerTipoDocumento();
            btnAgregarTipoDni.setEnabled(false);
            btnModificarTipoDni.setEnabled(false);
            btnEliminarTipoDni.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtTipoDni.setEnabled(true);
            txtSiglas.setEnabled(true);
            tablaTipoDni.setEnabled(false);
            txtTipoDni.requestFocus();            
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Tipo de Documento para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }
  
    }//GEN-LAST:event_btnModificarTipoDniActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void tablaTipoDniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTipoDniMouseClicked
        // TODO add your handling code here:
      //  this.obtenerTipoDocumento();
        txtSiglas.setBackground(Color.white); 
        txtTipoDni.setBackground(Color.white);  
    }//GEN-LAST:event_tablaTipoDniMouseClicked

    private void txtTipoDniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTipoDniMouseClicked
        // TODO add your handling code here:
        txtTipoDni.setBackground(Color.white);  
    }//GEN-LAST:event_txtTipoDniMouseClicked

    private void txtSiglasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSiglasMouseClicked
        // TODO add your handling code here:
        txtSiglas.setBackground(Color.white);       
    }//GEN-LAST:event_txtSiglasMouseClicked

    private void txtTipoDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoDniKeyTyped
        // TODO add your handling code here:
       char c; 
       c = evt.getKeyChar();
       if (!(c < '0' || c > '9'))
       {
          evt.consume();
       }
        int limite  = 30;
        {if (txtTipoDni.getText().length()== limite)
            evt.consume();
        }   
    }//GEN-LAST:event_txtTipoDniKeyTyped

    private void txtSiglasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSiglasKeyTyped
        // TODO add your handling code here:
       char c; 
       c = evt.getKeyChar();
       if (!(c < '0' || c > '9'))
       {
          evt.consume();
       }
        int limite  = 10;
        {if (txtSiglas.getText().length()== limite)
            evt.consume();
        }         
    }//GEN-LAST:event_txtSiglasKeyTyped

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        if(!txtTipoDni.getText().isEmpty()&&(!txtSiglas.getText().isEmpty()))
        {
            this.modificarTipoDni();
        }
        else
        {
            if(txtTipoDni.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el tipo de documento"," ",JOptionPane.ERROR_MESSAGE);
                  txtTipoDni.setBackground(Color.red);
            }
            
            if(txtSiglas.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado las siglas del tipo de documento"," ",JOptionPane.ERROR_MESSAGE);
                  txtSiglas.setBackground(Color.red);                
            }
        }   
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        if(!txtTipoDni.getText().isEmpty()&&(!txtSiglas.getText().isEmpty()))
        {
            this.agregarNuevoTipoDni();
        }
        else
        {
            if(txtTipoDni.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el tipo de documento"," ",JOptionPane.ERROR_MESSAGE);
                  txtTipoDni.setBackground(Color.red);
            }
            
            if(txtSiglas.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado las siglas del tipo de documento"," ",JOptionPane.ERROR_MESSAGE);
                  txtSiglas.setBackground(Color.red);                
            }
        }
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnAgregarTipoDni.setEnabled(true);
        btnModificarTipoDni.setEnabled(true);
        btnEliminarTipoDni.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtTipoDni.setEnabled(false);
        txtTipoDni.setText(null);
        txtSiglas.setEnabled(false);
        txtSiglas.setText(null);
        tablaTipoDni.setEnabled(true);
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
            java.util.logging.Logger.getLogger(JDialogTipoDni.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogTipoDni.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogTipoDni.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogTipoDni.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
    
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogTipoDni dialog = new JDialogTipoDni(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });     */
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarTipoDni;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarTipoDni;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarTipoDni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaTipoDni;
    private javax.swing.JTextField txtSiglas;
    private javax.swing.JTextField txtTipoDni;
    // End of variables declaration//GEN-END:variables
}
