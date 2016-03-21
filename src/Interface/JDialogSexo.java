/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Remiseria;
import Clases.Sexo;
import java.awt.Color;
import java.awt.Frame;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author garba
 */
public class JDialogSexo extends javax.swing.JDialog {
private Remiseria remiseria;
private JComboBox cmbSexo;
private java.awt.Frame parent;
private boolean modal;
    /**
     * Creates new form JDialogSexo
     */
    public JDialogSexo(java.awt.Frame parent, boolean modal,JComboBox cmbSexo, Remiseria remiseria) {
      super(parent, modal);
      this.cmbSexo = cmbSexo;
      this.remiseria = remiseria;
      initComponents();
      this.setModal(true);
      this.setLocationRelativeTo(null);
      this.cargarTabla();
        btnNuevoTipoDeSexo.setEnabled(true);
        btnModificarTipoDeSexo.setEnabled(true);
        btnEliminarTipoDeSexo.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtSexo.setEnabled(false);
        txtSexo.setText(null);
        tablaTipoDeSexo.setEnabled(true);
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
    
     public void cargarTabla(){
        Collection sexos = remiseria.buscarSexos(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Tipo de sexo");
       
        Sexo aux = null;
       
        Iterator iter = sexos.iterator();
        while (iter.hasNext())
        {
            aux = (Sexo) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreSexo();
            modelo.addRow(fila);
        }       
        modelo.rowsRemoved(null);
        tablaTipoDeSexo.setModel(modelo);
    }
    public void eliminarSexo(){
        String nombreSexo = (String) tablaTipoDeSexo.getValueAt(tablaTipoDeSexo.getSelectedRow(), 0);
        remiseria.eliminarSexo(nombreSexo);
        this.cargarTabla();
        this.cargarSexosCombo();
        JOptionPane.showMessageDialog(null,"El Tipo de Sexo a sido eliminado.", null, JOptionPane.INFORMATION_MESSAGE);
        
    }
   
    
    public void obtenerSexo()
    {
        int fila = tablaTipoDeSexo.getSelectedRow();
        String nombreSexo = (String) tablaTipoDeSexo.getValueAt(fila, 0);
        txtSexo.setText(nombreSexo);
    }
    
    
   public void agregarNuevoSexo()
   {
        String nombreSexo = txtSexo.getText();
        if(remiseria.buscarSexo(nombreSexo)==null)
        {
            remiseria.agregarSexo(nombreSexo);
            this.cargarTabla();
            this.cargarSexosCombo();
            txtSexo.setText(null);
            JOptionPane.showMessageDialog(null,"Se registro un nuevo tipo de sexo"," ",JOptionPane.INFORMATION_MESSAGE);
            btnNuevoTipoDeSexo.setEnabled(true);
            btnModificarTipoDeSexo.setEnabled(true);
            btnEliminarTipoDeSexo.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtSexo.setEnabled(false);
            txtSexo.setText(null);
            tablaTipoDeSexo.setEnabled(true);
        }
        else
        {
                JOptionPane.showMessageDialog(null,"Ya existe el tipo de sexo"," ",JOptionPane.ERROR_MESSAGE);
                txtSexo.setText(null);

        }
   }
       
   public void modificarSexo()
    {
        int fila = tablaTipoDeSexo.getSelectedRow();
        String codigo = (String) tablaTipoDeSexo.getValueAt(fila,0);
        Sexo unSexo = remiseria.buscarSexo(codigo);
        String cod = (txtSexo.getText());
        if (remiseria.buscarTipoDocumento(cod) == null || unSexo.getNombreSexo().equals(cod) )
        {
            remiseria.modificarSexo(unSexo,cod);
            this.cargarTabla();
            this.cargarSexosCombo();
            txtSexo.setText(null);
            JOptionPane.showMessageDialog(null,"Se ha modifica el tipo de sexo seleccionado","",JOptionPane.INFORMATION_MESSAGE);
            btnNuevoTipoDeSexo.setEnabled(true);
            btnModificarTipoDeSexo.setEnabled(true);
            btnEliminarTipoDeSexo.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtSexo.setEnabled(false);
            txtSexo.setText(null);
            tablaTipoDeSexo.setEnabled(true);
        }
        else
        {
            if(remiseria.buscarTipoDocumento(cod) != null)
            {
                remiseria.modificarSexo(unSexo,cod);
                this.cargarTabla();
                this.cargarSexosCombo();
                txtSexo.setText(null);
                JOptionPane.showMessageDialog(null,"Se ha modifica el tipo de sexo seleccionado","",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Ya existe el tipo de sexo"," ",JOptionPane.ERROR_MESSAGE);
                txtSexo.setText(null);
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

        jPanel2 = new javax.swing.JPanel();
        btnEliminarTipoDeSexo = new javax.swing.JButton();
        btnModificarTipoDeSexo = new javax.swing.JButton();
        btnNuevoTipoDeSexo = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel3 = new javax.swing.JPanel();
        txtSexo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTipoDeSexo = new javax.swing.JTable();
        btnGuardaModificacion = new javax.swing.JButton();
        btnGuarda = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnEliminarTipoDeSexo.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarTipoDeSexo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarTipoDeSexo.setText("Eliminar");
        btnEliminarTipoDeSexo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarTipoDeSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTipoDeSexoActionPerformed(evt);
            }
        });

        btnModificarTipoDeSexo.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarTipoDeSexo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarTipoDeSexo.setText("Modificar ");
        btnModificarTipoDeSexo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarTipoDeSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarTipoDeSexoActionPerformed(evt);
            }
        });

        btnNuevoTipoDeSexo.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnNuevoTipoDeSexo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnNuevoTipoDeSexo.setText("Agregar");
        btnNuevoTipoDeSexo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevoTipoDeSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoTipoDeSexoActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminarTipoDeSexo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarTipoDeSexo, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnNuevoTipoDeSexo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevoTipoDeSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarTipoDeSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarTipoDeSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        txtSexo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSexo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtSexo.setToolTipText("");
        txtSexo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSexo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSexoMouseClicked(evt);
            }
        });
        txtSexo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSexoKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Sexo:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSexo)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tablaTipoDeSexo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaTipoDeSexo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo de sexo"
            }
        ));
        tablaTipoDeSexo.setRequestFocusEnabled(false);
        tablaTipoDeSexo.setRowHeight(20);
        tablaTipoDeSexo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTipoDeSexoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaTipoDeSexo);

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
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel3.setText("Gestor del tipo de sexo");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(439, Short.MAX_VALUE))
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoTipoDeSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoTipoDeSexoActionPerformed
        // TODO add your handling code here:
        btnNuevoTipoDeSexo.setEnabled(false);
        btnModificarTipoDeSexo.setEnabled(false);
        btnEliminarTipoDeSexo.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtSexo.setEnabled(true);
        txtSexo.setText(null);
        tablaTipoDeSexo.setEnabled(false);  
        txtSexo.requestFocus();
    }//GEN-LAST:event_btnNuevoTipoDeSexoActionPerformed

    private void btnModificarTipoDeSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarTipoDeSexoActionPerformed
        // TODO add your handling code here:
        if(tablaTipoDeSexo.getSelectedRow()!= -1)
        {
            this.obtenerSexo();
            btnNuevoTipoDeSexo.setEnabled(false);
            btnModificarTipoDeSexo.setEnabled(false);
            btnEliminarTipoDeSexo.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtSexo.setEnabled(true);
            tablaTipoDeSexo.setEnabled(false);
            txtSexo.requestFocus();
        }
        else
        {
            JOptionPane.showConfirmDialog(null, "No ha seleccionado un Sexo para realizar la modificación", null, JOptionPane.ERROR_MESSAGE);
        
        }
            
    }//GEN-LAST:event_btnModificarTipoDeSexoActionPerformed

    private void btnEliminarTipoDeSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTipoDeSexoActionPerformed
        // TODO add your handling code here:
         if(tablaTipoDeSexo.getSelectedRow()!=-1)
         {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el Sexo seleccionado?");
            if ( eleccion == 0)
            {  
                this.eliminarSexo();
                this.cargarTabla();
                txtSexo.setText(null);
            }
         }
         else
         {
             JOptionPane.showMessageDialog(null,"No ha seleccionado un tipo de sexo"," ",JOptionPane.ERROR_MESSAGE); 
         }
    }//GEN-LAST:event_btnEliminarTipoDeSexoActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void tablaTipoDeSexoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTipoDeSexoMouseClicked
        // TODO add your handling code here:
        this.obtenerSexo();
        this.txtSexo.setBackground(Color.white);
    }//GEN-LAST:event_tablaTipoDeSexoMouseClicked

    private void txtSexoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSexoMouseClicked
        // TODO add your handling code here:
        this.txtSexo.setBackground(Color.white);
    }//GEN-LAST:event_txtSexoMouseClicked

    private void txtSexoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSexoKeyTyped
        // TODO add your handling code here:
       char c; 
       c = evt.getKeyChar();
       if (!(c < '0' || c > '9'))
       {
          evt.consume();
       }
       
        int limite  = 15;
        {if (txtSexo.getText().length()== limite)
            evt.consume();
        }         
    }//GEN-LAST:event_txtSexoKeyTyped

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        if(!txtSexo.getText().isEmpty())
        {
            this.agregarNuevoSexo();
        }
        else
        {
            if(txtSexo.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el tipo de sexo"," ",JOptionPane.ERROR_MESSAGE);
                  txtSexo.setBackground(Color.red);
            }
        }   
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        if(!txtSexo.getText().isEmpty())
        {
            this.modificarSexo();
        }
        else
        {
            if(txtSexo.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el tipo de sexo"," ",JOptionPane.ERROR_MESSAGE);
                  txtSexo.setBackground(Color.red);
            }
        }   
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnNuevoTipoDeSexo.setEnabled(true);
        btnModificarTipoDeSexo.setEnabled(true);
        btnEliminarTipoDeSexo.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtSexo.setEnabled(false);
        txtSexo.setText(null);
        tablaTipoDeSexo.setEnabled(true);
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
            java.util.logging.Logger.getLogger(JDialogSexo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogSexo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogSexo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogSexo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
       
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogSexo dialog = new JDialogSexo(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });  */
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarTipoDeSexo;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarTipoDeSexo;
    private javax.swing.JButton btnNuevoTipoDeSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaTipoDeSexo;
    private javax.swing.JTextField txtSexo;
    // End of variables declaration//GEN-END:variables
}
