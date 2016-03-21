/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.CodPais;
import Clases.Remiseria;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
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
public class JDialogCodPaisTelefono extends javax.swing.JDialog {
private java.awt.Frame parent;
private  boolean modal;
private JComboBox cmbCodPais;
private  Remiseria remiseria;
    /**
     * Creates new form NewCodPaisTelefono
     */
    public JDialogCodPaisTelefono(java.awt.Frame parent, boolean modal,JComboBox cmbCodPais, Remiseria remiseria) {
        super(parent, modal);
        this.cmbCodPais = cmbCodPais;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        btnAgregarCodPais.setEnabled(true);
        btnEliminarCodPais.setEnabled(true);
        btnModificarCodPais.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtCodPais.setEnabled(false);
        txtCodPais.setText(null);
        tablaCodPais.setEnabled(true);
    
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
    
     public void cargarTabla(){
        Collection codigos = remiseria.buscarCodigosPaises(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaCodPais.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);   
        modelo.addColumn("Código de país");
       
        CodPais aux = null;
       
        Iterator iter = codigos.iterator();
            while (iter.hasNext()){
            aux = (CodPais) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getCodPais();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaCodPais.setModel(modelo);
    }
     

    
    public void eliminarCodPais(){
        String codigoPais = (String) tablaCodPais.getValueAt(tablaCodPais.getSelectedRow(), 0);
        remiseria.eliminarCodigoPais(codigoPais);
        this.cargarTabla();
        cargarCodPaisCombo();
        JOptionPane.showMessageDialog(null,"El Código de País sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);
        
    }

    
    
    public void obtenerCodPais(){
        int fila = tablaCodPais.getSelectedRow();
        String codigoPais = (String) tablaCodPais.getValueAt(fila, 0);
       // CodPais unCodPais = remiseria.buscarCodigoPais(codigoPais);
        txtCodPais.setText(codigoPais);
    }

    public void agregarNuevoCodPais()
    {
     
        String elCodPais = txtCodPais.getText();
        if(remiseria.buscarCodigoPais(elCodPais)==null)
        {
            remiseria.agregarCodPais(elCodPais);
            this.cargarTabla();
            this.cargarCodPaisCombo();
            txtCodPais.setText("");
            JOptionPane.showMessageDialog(null,"Se registro un nuevo Código de País"," ",JOptionPane.INFORMATION_MESSAGE);
            btnAgregarCodPais.setEnabled(true);
            btnEliminarCodPais.setEnabled(true);
            btnModificarCodPais.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(true);
            txtCodPais.setEnabled(false);
            txtCodPais.setText(null);
            tablaCodPais.setEnabled(true);        
        }
        else
        {
                JOptionPane.showMessageDialog(null,"Ya existe el Código de País","",JOptionPane.ERROR_MESSAGE);
                txtCodPais.setText("");
        }
   }
    
     public void modificarCodPais()
    {
        int fila = tablaCodPais.getSelectedRow();
        String codPaisSeleccionado = (String) tablaCodPais.getValueAt(fila, 0);
        CodPais unCodPais = remiseria.buscarCodigoPais(codPaisSeleccionado);
        String codPaisModificar = (txtCodPais.getText());
         if (remiseria.buscarCodigoPais(codPaisModificar) == null){
            remiseria.modificarCodigoPais(unCodPais, codPaisModificar);
            this.cargarTabla();
            this.cargarCodPaisCombo();
            txtCodPais.setText(null);
            JOptionPane.showMessageDialog(null,"Se modificó el Código de País seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);
            btnAgregarCodPais.setEnabled(true);
            btnEliminarCodPais.setEnabled(true);
            btnModificarCodPais.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(true);
            txtCodPais.setEnabled(false);
            txtCodPais.setText(null);
            tablaCodPais.setEnabled(true);  
        }
            else
            {
                 JOptionPane.showMessageDialog(null,"Ya existe el Código de País"," ",JOptionPane.ERROR_MESSAGE);
                txtCodPais.setText(null);
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

        jPanel3 = new javax.swing.JPanel();
        btnModificarCodPais = new javax.swing.JButton();
        btnAgregarCodPais = new javax.swing.JButton();
        btnEliminarCodPais = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCodPais = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtCodPais = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnGuardaModificacion = new javax.swing.JButton();
        btnGuarda = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnModificarCodPais.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarCodPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarCodPais.setText("Modificar ");
        btnModificarCodPais.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarCodPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCodPaisActionPerformed(evt);
            }
        });

        btnAgregarCodPais.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregarCodPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregarCodPais.setText("Agregar");
        btnAgregarCodPais.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarCodPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCodPaisActionPerformed(evt);
            }
        });

        btnEliminarCodPais.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarCodPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarCodPais.setText("Eliminar ");
        btnEliminarCodPais.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarCodPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCodPaisActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnAgregarCodPais, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarCodPais, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarCodPais, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregarCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnModificarCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaCodPais.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaCodPais.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaCodPais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código del país"
            }
        ));
        tablaCodPais.setGridColor(new java.awt.Color(0, 0, 0));
        tablaCodPais.setRowHeight(20);
        tablaCodPais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCodPaisMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCodPais);

        txtCodPais.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCodPais.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodPais.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodPais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodPaisMouseClicked(evt);
            }
        });
        txtCodPais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodPaisKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Código del país:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodPais)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btnCancelar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
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

        btnGuarda.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuarda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/media-floppy.png"))); // NOI18N
        btnGuarda.setText("Guardar");
        btnGuarda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaActionPerformed(evt);
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel3.setText("Gestor de código de país del teléfono");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(304, Short.MAX_VALUE))
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
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarCodPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCodPaisActionPerformed
        // TODO add your handling code here:
        btnAgregarCodPais.setEnabled(false);
        btnEliminarCodPais.setEnabled(false);
        btnModificarCodPais.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtCodPais.setEnabled(true);
        txtCodPais.setText(null);
        tablaCodPais.setEnabled(false);
        txtCodPais.requestFocus();
    }//GEN-LAST:event_btnAgregarCodPaisActionPerformed

    private void btnModificarCodPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCodPaisActionPerformed
        // TODO add your handling code here:
        if(tablaCodPais.getSelectedRow()!=-1)
        {        
            this.obtenerCodPais();
            btnAgregarCodPais.setEnabled(false);
            btnEliminarCodPais.setEnabled(false);
            btnModificarCodPais.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtCodPais.setEnabled(true);
            tablaCodPais.setEnabled(false);
            txtCodPais.requestFocus();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Código de País para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarCodPaisActionPerformed

    private void btnEliminarCodPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCodPaisActionPerformed
        // TODO add your handling code here:
        if(tablaCodPais.getSelectedRow()!=-1)
        {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el Código de País seleccionado?");
            if ( eleccion == 0)
            {   
                this.eliminarCodPais();
                this.cargarTabla();
                this.cargarCodPaisCombo();
                txtCodPais.setText("");
            }
        }
        else
        {
             JOptionPane.showMessageDialog(null,"No ha seleccionado un Código de País"," ",JOptionPane.ERROR_MESSAGE); 
        }
    }//GEN-LAST:event_btnEliminarCodPaisActionPerformed

    private void tablaCodPaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCodPaisMouseClicked
        // TODO add your handling code here:
        txtCodPais.setBackground(Color.white);
    }//GEN-LAST:event_tablaCodPaisMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtCodPaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodPaisMouseClicked
        // TODO add your handling code here:
        txtCodPais.setBackground(Color.white);
    }//GEN-LAST:event_txtCodPaisMouseClicked

    private void txtCodPaisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodPaisKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 10;
        {if (txtCodPais.getText().length()== limite)
            evt.consume();
        }            
    }//GEN-LAST:event_txtCodPaisKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnAgregarCodPais.setEnabled(true);
        btnEliminarCodPais.setEnabled(true);
        btnModificarCodPais.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtCodPais.setEnabled(false);
        txtCodPais.setText(null);
        tablaCodPais.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        // TODO add your handling code here:
       if(!txtCodPais.getText().isEmpty())
        {
            this.modificarCodPais();
        }
        else
        {
            if(txtCodPais.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el Código de País"," ",JOptionPane.ERROR_MESSAGE);
                  txtCodPais.setBackground(Color.red);
            }          
        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
       if(!txtCodPais.getText().isEmpty())
        {
            this.agregarNuevoCodPais();
        }
        else
        {
            if(txtCodPais.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el Código de País"," ",JOptionPane.ERROR_MESSAGE);
                  txtCodPais.setBackground(Color.red);
            }          
        }
    }//GEN-LAST:event_btnGuardaActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogCodPaisTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCodPaisTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCodPaisTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCodPaisTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogCodPaisTelefono dialog = new JDialogCodPaisTelefono(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregarCodPais;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarCodPais;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarCodPais;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaCodPais;
    private javax.swing.JTextField txtCodPais;
    // End of variables declaration//GEN-END:variables
}
