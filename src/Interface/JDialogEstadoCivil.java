/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.EstadoCivil;
import Clases.Remiseria;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author garba
 */
public class JDialogEstadoCivil extends javax.swing.JDialog {
private JComboBox cmbEstadoCivil;
private Remiseria remiseria;
private java.awt.Frame parent;
private boolean modal;
    /**
     * Creates new form JDialogEstadoCivil
     */
    public JDialogEstadoCivil(java.awt.Frame parent, boolean modal,JComboBox cmbEstadoCivil,Remiseria remiseria) {
        super(parent, modal);
        this.cmbEstadoCivil = cmbEstadoCivil;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);   
        btnModificarEstadoCivil.setEnabled(true);
        btnEliminarEstadoCivil.setEnabled(true);
        btnNuevoEstadoCivil.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtEstadoCivil.setEnabled(false);
        txtEstadoCivil.setText(null);
        tablaEstadoCivil.setEnabled(true);  
        this.cargarTabla();
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
    
   public void cargarTabla(){
        Collection estadosCiviles = remiseria.buscarEstadosCiviles(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Estado civil");
       
        EstadoCivil aux = null;
       
        Iterator iter = estadosCiviles.iterator();
        while (iter.hasNext()){
            aux = (EstadoCivil) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreEstadoCivil();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaEstadoCivil.setModel(modelo);
    }
     
      public void agregarNuevoEstadoCivil(){
            String nombreEstadoCivil = txtEstadoCivil.getText();
            if(remiseria.buscarEstadoCivil(nombreEstadoCivil)==null)
            {
                remiseria.agregarEstadoCivil(nombreEstadoCivil);
                this.cargarTabla();
                this.cargarEstadosCivilesCombo();
                txtEstadoCivil.setText(null);
                JOptionPane.showMessageDialog(null,"Se registro un nuevo estado civil"," ",JOptionPane.INFORMATION_MESSAGE);
                btnModificarEstadoCivil.setEnabled(true);
                btnEliminarEstadoCivil.setEnabled(true);
                btnNuevoEstadoCivil.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                txtEstadoCivil.setEnabled(false);
                txtEstadoCivil.setText(null);
                tablaEstadoCivil.setEnabled(true);  
            }
            else
            {
                    JOptionPane.showMessageDialog(null,"Ya existe el estado civil"," ",JOptionPane.ERROR_MESSAGE);
                    txtEstadoCivil.setText(null);
            }
            
            }

    
    public void eliminarEstadoCivil(){
        try
        {
            String nombreEstadoCivil = (String) tablaEstadoCivil.getValueAt(tablaEstadoCivil.getSelectedRow(), 0);
            remiseria.eliminarEstadoCivil(nombreEstadoCivil);
            this.cargarTabla();
            cargarEstadosCivilesCombo();
            JOptionPane.showMessageDialog(null,"El Estado Civíl sido eliminado\n Se sugiere modificar los datos de aquellos registros que contenian el Estado Civil eliminado", null, JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex," ",JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
    public void obtenerEstadoCivil(){
        int fila = tablaEstadoCivil.getSelectedRow();
        String nombreEstadoCivil = (String) tablaEstadoCivil.getValueAt(fila, 0);
        txtEstadoCivil.setText(nombreEstadoCivil);
   //     EstadoCivil unEstadoCivil = remiseria.buscarEstadoCivil(nombreEstadoCivil);
      
    }

    public void modificarEstadoCivil()
    {
        int fila = tablaEstadoCivil.getSelectedRow();
        String nombreEstadoCivil = (String) tablaEstadoCivil.getValueAt(fila,0);
        EstadoCivil unEstadoCivil = remiseria.buscarEstadoCivil(nombreEstadoCivil);
        String estadoCivilMod = (txtEstadoCivil.getText());
        if (remiseria.buscarTipoDocumento(estadoCivilMod) == null || unEstadoCivil.getNombreEstadoCivil().equals(estadoCivilMod) ){
           remiseria.modificarEstadoCivil(unEstadoCivil,nombreEstadoCivil);
           this.cargarTabla();
           this.cargarEstadosCivilesCombo();
           txtEstadoCivil.setText(null);
           JOptionPane.showMessageDialog(null,"Se ha modifica el estado civil seleccionado","",JOptionPane.INFORMATION_MESSAGE);
            btnModificarEstadoCivil.setEnabled(true);
            btnEliminarEstadoCivil.setEnabled(true);
            btnNuevoEstadoCivil.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtEstadoCivil.setEnabled(false);
            txtEstadoCivil.setText(null);
            tablaEstadoCivil.setEnabled(true);          
        }
        else
        {
                if(remiseria.buscarTipoDocumento(estadoCivilMod) != null)
                {
                    remiseria.modificarEstadoCivil(unEstadoCivil,nombreEstadoCivil);
                    this.cargarTabla();
                    this.cargarEstadosCivilesCombo();
                    txtEstadoCivil.setText(null);
                    JOptionPane.showMessageDialog(null,"Se ha modifica el estado civil seleccionado","",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Ya existe el estado civil"," ",JOptionPane.ERROR_MESSAGE);
                    txtEstadoCivil.setText(null);
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
        btnEliminarEstadoCivil = new javax.swing.JButton();
        btnModificarEstadoCivil = new javax.swing.JButton();
        btnNuevoEstadoCivil = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEstadoCivil = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtEstadoCivil = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnGuarda = new javax.swing.JButton();
        btnGuardaModificacion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnEliminarEstadoCivil.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarEstadoCivil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarEstadoCivil.setText("Eliminar");
        btnEliminarEstadoCivil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarEstadoCivil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEstadoCivilActionPerformed(evt);
            }
        });

        btnModificarEstadoCivil.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarEstadoCivil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarEstadoCivil.setText("Modificar");
        btnModificarEstadoCivil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarEstadoCivil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEstadoCivilActionPerformed(evt);
            }
        });

        btnNuevoEstadoCivil.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnNuevoEstadoCivil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnNuevoEstadoCivil.setText("Agregar");
        btnNuevoEstadoCivil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevoEstadoCivil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoEstadoCivilActionPerformed(evt);
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
                    .addComponent(btnModificarEstadoCivil, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnNuevoEstadoCivil, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnEliminarEstadoCivil, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevoEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaEstadoCivil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaEstadoCivil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Estado civil"
            }
        ));
        tablaEstadoCivil.setRequestFocusEnabled(false);
        tablaEstadoCivil.setRowHeight(20);
        tablaEstadoCivil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEstadoCivilMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaEstadoCivil);

        txtEstadoCivil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEstadoCivil.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEstadoCivil.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtEstadoCivil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEstadoCivilMouseClicked(evt);
            }
        });
        txtEstadoCivil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoCivilKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Estado civil:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEstadoCivil)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel3.setText("Gestor del estado civil");

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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaEstadoCivilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEstadoCivilMouseClicked
        // TODO add your handling code here:
        
        //this.obtenerEstadoCivil();
        txtEstadoCivil.setBackground(Color.white);

    }//GEN-LAST:event_tablaEstadoCivilMouseClicked

    private void btnNuevoEstadoCivilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoEstadoCivilActionPerformed
        // TODO add your handling code here:
        btnModificarEstadoCivil.setEnabled(false);
        btnEliminarEstadoCivil.setEnabled(false);
        btnNuevoEstadoCivil.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtEstadoCivil.setEnabled(true);
        txtEstadoCivil.setText(null);
        tablaEstadoCivil.setEnabled(false);  
        txtEstadoCivil.requestFocus();
    }//GEN-LAST:event_btnNuevoEstadoCivilActionPerformed

    private void btnModificarEstadoCivilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEstadoCivilActionPerformed
        // TODO add your handling code here:
        if(tablaEstadoCivil.getSelectedRow()!=-1)
        {   
            this.obtenerEstadoCivil();
            btnModificarEstadoCivil.setEnabled(false);
            btnEliminarEstadoCivil.setEnabled(false);
            btnNuevoEstadoCivil.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtEstadoCivil.setEnabled(true);
            tablaEstadoCivil.setEnabled(false);    
            txtEstadoCivil.requestFocus();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Estado Civil para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }
    
    }//GEN-LAST:event_btnModificarEstadoCivilActionPerformed

    private void btnEliminarEstadoCivilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEstadoCivilActionPerformed
        // TODO add your handling code here:
         if(tablaEstadoCivil.getSelectedRow()!=-1){
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el Estado Civil seleccionado?");
            if ( eleccion == 0)
            {   
                this.eliminarEstadoCivil();
                this.cargarTabla();
                txtEstadoCivil.setText(null);
         
            }
         }
         else
         {
             JOptionPane.showMessageDialog(null,"No ha seleccionado un estado civil"," ",JOptionPane.ERROR_MESSAGE); 
         }
    }//GEN-LAST:event_btnEliminarEstadoCivilActionPerformed

    private void txtEstadoCivilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEstadoCivilMouseClicked
        // TODO add your handling code here:
                  txtEstadoCivil.setBackground(Color.white);
    }//GEN-LAST:event_txtEstadoCivilMouseClicked

    private void txtEstadoCivilKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoCivilKeyTyped
        // TODO add your handling code here:
       char c; 
       c = evt.getKeyChar();
       if (!(c < '0' || c > '9'))
       {
          evt.consume();
       }
        int limite = 20;
        {if ( txtEstadoCivil.getText().length()== limite)
            evt.consume();
        }         
    }//GEN-LAST:event_txtEstadoCivilKeyTyped

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        if(!txtEstadoCivil.getText().isEmpty())
        {
            this.agregarNuevoEstadoCivil();
        }
        else
        {
            if(txtEstadoCivil.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el estado civil"," ",JOptionPane.ERROR_MESSAGE);
                  txtEstadoCivil.setBackground(Color.red);
            }
            
             

        }
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        // TODO add your handling code here:
        if(tablaEstadoCivil.getSelectedRow()!=-1)
        {     
            if(!txtEstadoCivil.getText().isEmpty())
            {
                this.modificarEstadoCivil();
            }
            else
            {
                if(txtEstadoCivil.getText().isEmpty())
                {
                      JOptionPane.showMessageDialog(null,"No ha ingresado el estado civil"," ",JOptionPane.ERROR_MESSAGE);
                      txtEstadoCivil.setBackground(Color.red);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha selecciona un Estado Civil a modificar");
        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnModificarEstadoCivil.setEnabled(true);
        btnEliminarEstadoCivil.setEnabled(true);
        btnNuevoEstadoCivil.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtEstadoCivil.setEnabled(false);
        txtEstadoCivil.setText(null);
        tablaEstadoCivil.setEnabled(true);  
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogEstadoCivil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogEstadoCivil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogEstadoCivil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogEstadoCivil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogEstadoCivil dialog = new JDialogEstadoCivil(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarEstadoCivil;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarEstadoCivil;
    private javax.swing.JButton btnNuevoEstadoCivil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaEstadoCivil;
    private javax.swing.JTextField txtEstadoCivil;
    // End of variables declaration//GEN-END:variables
}
