/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Clases.Remiseria;
import Clases.TipoUtilidad;
import Clases.UtilidadViaje;
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
public class JDialogUtilidadViajes extends javax.swing.JDialog {
private JComboBox cmbTipoUtilidadViaje;
private Remiseria remiseria;
    /**
     * Creates new form JDialogUtilidadViajes
     */
    public JDialogUtilidadViajes(java.awt.Frame parent, boolean modal,JComboBox cmbTipoUtilidadViaje ,Remiseria remiseria) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.cmbTipoUtilidadViaje = cmbTipoUtilidadViaje;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        btnNuevaUtilidad.setEnabled(true);
        btnModificarUtilidad.setEnabled(true);
        btnEliminarUtilidad.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtUtilidad.setEnabled(false);
        txtUtilidad.setText(null);
        tablaUtilidades.setEnabled(true);
    }

    public void cargarTiposUtilidadesCombo(){
        cmbTipoUtilidadViaje.removeAllItems();
        List TiposUtilidades = remiseria.buscarTiposUtilidadViaje();
        if(TiposUtilidades != null)
        {
            UtilidadViaje aux = null;
            Iterator iter = TiposUtilidades.iterator();
            while (iter.hasNext()){
                aux = (UtilidadViaje) iter.next();
                cmbTipoUtilidadViaje.addItem(aux.getNombreUtilidadViaje());
            }
            cmbTipoUtilidadViaje.setSelectedIndex(0);
        }
    }
    
     public void cargarTabla(){
        Collection TiposUtilidades = remiseria.buscarTiposUtilidadViaje(); 
        if(TiposUtilidades != null){
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaUtilidades.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Utilidades");
       
        UtilidadViaje aux = null;
       
        Iterator iter = TiposUtilidades.iterator();
            while (iter.hasNext()){
            aux = (UtilidadViaje) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreUtilidadViaje();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaUtilidades.setModel(modelo);}
    }
   
    public void eliminarTipoUtilidad(){
        String nombreTipoUtilidad = (String) tablaUtilidades.getValueAt(tablaUtilidades.getSelectedRow(), 0);
        remiseria.eliminarTipoUtilidadViaje(nombreTipoUtilidad);
        this.cargarTabla();
        cargarTiposUtilidadesCombo();
        JOptionPane.showMessageDialog(null,"La Utilidad del Viaje a sido eliminado.", null, JOptionPane.INFORMATION_MESSAGE);
        
    }
   
    public void obtenerTipoUtilidad(){
        int fila = tablaUtilidades.getSelectedRow();
        String nombreTipoUtilidad = (String) tablaUtilidades.getValueAt(fila, 0);
        txtUtilidad.setText(nombreTipoUtilidad);
    }
    
    public void agregarNuevoTipoUtilidad(){
            String nombreTipoUtilidad = txtUtilidad.getText();
            if(remiseria.buscarTiposUtilidadViaje(nombreTipoUtilidad)== null){
                remiseria.agregarTipoUtilidadViaje(nombreTipoUtilidad);
                this.cargarTabla();
                this.cargarTiposUtilidadesCombo();
                txtUtilidad.setText("");
                JOptionPane.showMessageDialog(null,"Se registro una nueva Utilidad de viaje "," ",JOptionPane.INFORMATION_MESSAGE);
                btnNuevaUtilidad.setEnabled(true);
                btnModificarUtilidad.setEnabled(true);
                btnEliminarUtilidad.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                txtUtilidad.setEnabled(false);
                txtUtilidad.setText(null);
                tablaUtilidades.setEnabled(true);           
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Ya existe la Utilidad de viaje"," ",JOptionPane.ERROR_MESSAGE);
            }
    }
    
        public void modificarTipoUtilidad()
    {
           int fila = tablaUtilidades.getSelectedRow();
           String nombreTipoUtilidad = (String) tablaUtilidades.getValueAt(fila, 0);
           UtilidadViaje unTipoUtilidad = remiseria.buscarTiposUtilidadViaje(nombreTipoUtilidad);
           String nombreTipoUtilidadMod = txtUtilidad.getText();
           if(remiseria.buscarTiposUtilidadViaje(nombreTipoUtilidadMod)== null){
                remiseria.modificarTipoUtilidadViaje(unTipoUtilidad,nombreTipoUtilidadMod);
                this.cargarTabla();
                this.cargarTiposUtilidadesCombo();
                txtUtilidad.setText("");
                JOptionPane.showMessageDialog(null,"Se ha modificado el tipo de utilidad seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);
                btnNuevaUtilidad.setEnabled(true);
                btnModificarUtilidad.setEnabled(true);
                btnEliminarUtilidad.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                txtUtilidad.setEnabled(false);
                txtUtilidad.setText(null);
                tablaUtilidades.setEnabled(true);           
           }
           else
           {
                JOptionPane.showMessageDialog(null,"Ya existe el tipo de utilidad"," ",JOptionPane.ERROR_MESSAGE);               
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUtilidades = new javax.swing.JTable();
        btnGuardaModificacion = new javax.swing.JButton();
        btnGuarda = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtUtilidad = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnNuevaUtilidad = new javax.swing.JButton();
        btnModificarUtilidad = new javax.swing.JButton();
        btnEliminarUtilidad = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaUtilidades.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaUtilidades.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaUtilidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Utilidades"
            }
        ));
        tablaUtilidades.setColumnSelectionAllowed(true);
        tablaUtilidades.setGridColor(new java.awt.Color(0, 0, 0));
        tablaUtilidades.setRowHeight(20);
        tablaUtilidades.getTableHeader().setReorderingAllowed(false);
        tablaUtilidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUtilidadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaUtilidades);
        tablaUtilidades.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

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

        txtUtilidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUtilidad.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtUtilidad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtUtilidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUtilidadMouseClicked(evt);
            }
        });
        txtUtilidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUtilidadKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Nombre de Utilidad:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUtilidad)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnNuevaUtilidad.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnNuevaUtilidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnNuevaUtilidad.setText("Agregar");
        btnNuevaUtilidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevaUtilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaUtilidadActionPerformed(evt);
            }
        });

        btnModificarUtilidad.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarUtilidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarUtilidad.setText("Modificar ");
        btnModificarUtilidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarUtilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarUtilidadActionPerformed(evt);
            }
        });

        btnEliminarUtilidad.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarUtilidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarUtilidad.setText("Eliminar ");
        btnEliminarUtilidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarUtilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUtilidadActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModificarUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnNuevaUtilidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevaUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel9.setText("Utilidades del viaje");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(408, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9))
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
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaUtilidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUtilidadesMouseClicked
        // TODO add your handling code here:
        //this.obtenerTipoUtilidad();
        txtUtilidad.setBackground(Color.white);
    }//GEN-LAST:event_tablaUtilidadesMouseClicked

    private void btnNuevaUtilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaUtilidadActionPerformed
        // TODO add your handling code here:
        btnNuevaUtilidad.setEnabled(false);
        btnModificarUtilidad.setEnabled(false);
        btnEliminarUtilidad.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtUtilidad.setEnabled(true);
        txtUtilidad.setText(null);
        tablaUtilidades.setEnabled(false);        
        txtUtilidad.requestFocus();
    }//GEN-LAST:event_btnNuevaUtilidadActionPerformed

    private void btnModificarUtilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarUtilidadActionPerformed
        // TODO add your handling code here:
        if(tablaUtilidades.getSelectedRow()!=-1)
        {
            this.obtenerTipoUtilidad();
            btnNuevaUtilidad.setEnabled(false);
            btnModificarUtilidad.setEnabled(false);
            btnEliminarUtilidad.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtUtilidad.setEnabled(true);
            tablaUtilidades.setEnabled(false);        
            txtUtilidad.requestFocus();
        }
        else
        {
             JOptionPane.showMessageDialog(null,"No ha seleccionado un Tipo de Utilidad del Viaje para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }
       
    }//GEN-LAST:event_btnModificarUtilidadActionPerformed

    private void btnEliminarUtilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUtilidadActionPerformed
        // TODO add your handling code here:
        if(tablaUtilidades.getSelectedRow()!=-1){
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el Tipo de Utilidad de Viaje seleccionado?");
            if ( eleccion == 0)
            {  
                this.eliminarTipoUtilidad();
                this.cargarTiposUtilidadesCombo();
                this.cargarTabla();
                txtUtilidad.setText("");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Tipo de Utilidad de Viaje"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarUtilidadActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txtUtilidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUtilidadMouseClicked
        // TODO add your handling code here:
        txtUtilidad.setBackground(Color.white);
    }//GEN-LAST:event_txtUtilidadMouseClicked

    private void txtUtilidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUtilidadKeyTyped
        // TODO add your handling code here:
        char c;
        c = evt.getKeyChar();
        if (!(c < '0' || c > '9'))
        {
            evt.consume();
        }
        int limite  = 20;
        {if ( txtUtilidad.getText().length()== limite)
            evt.consume();
        }           
    }//GEN-LAST:event_txtUtilidadKeyTyped

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        if((tablaUtilidades.getSelectedRow()!=-1)&& (!txtUtilidad.getText().isEmpty())){
            this.modificarTipoUtilidad();
        }
        else
        {
            if(tablaUtilidades.getSelectedRow()==-1){
               
            }
            else
            {
                if(txtUtilidad.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"No ha ingresado un color"," ",JOptionPane.ERROR_MESSAGE);
                    txtUtilidad.setBackground(java.awt.Color.red);
                }
            }
        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        if(!txtUtilidad.getText().isEmpty())
        {
            this.agregarNuevoTipoUtilidad();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado una nueva utilidad"," ",JOptionPane.ERROR_MESSAGE);
            txtUtilidad.setBackground(java.awt.Color.red);
        }
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnNuevaUtilidad.setEnabled(true);
        btnModificarUtilidad.setEnabled(true);
        btnEliminarUtilidad.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtUtilidad.setEnabled(false);
        txtUtilidad.setText(null);
        tablaUtilidades.setEnabled(true);
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
            java.util.logging.Logger.getLogger(JDialogUtilidadViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogUtilidadViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogUtilidadViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogUtilidadViajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogUtilidadViajes dialog = new JDialogUtilidadViajes(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarUtilidad;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarUtilidad;
    private javax.swing.JButton btnNuevaUtilidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaUtilidades;
    private javax.swing.JTextField txtUtilidad;
    // End of variables declaration//GEN-END:variables
}
