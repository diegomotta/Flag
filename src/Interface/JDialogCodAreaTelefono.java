/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.CodArea;
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
public class JDialogCodAreaTelefono extends javax.swing.JDialog {
private java.awt.Frame parent;
private boolean modal;
private final CodPais unCodPais;
private final JComboBox cmbCodArea;
private Remiseria remiseria;
    /**
     * Creates new form JDialogCodAreaTelefono
     */
    public JDialogCodAreaTelefono(java.awt.Frame parent, boolean modal,JComboBox cmbCodArea, CodPais unCodPais,Remiseria remiseria) {
        super(parent, modal);
        this.cmbCodArea = cmbCodArea;
        this.unCodPais = unCodPais;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        txtCodPais.setText(String.valueOf(unCodPais.getCodPais()));
        btnAgregarCodArea.setEnabled(true);
        btnModificarCodArea.setEnabled(true);
        btnEliminarCodArea.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtCodArea.setEnabled(false);
        txtCodArea.setText(null);
        tablaCodArea.setEnabled(true);    
    }
    
     public void cargarCodAreaCombo(){
        cmbCodArea.removeAllItems();
        List codigos = unCodPais.buscarCodigosAreas();
        if(codigos != null)
        {
            CodArea aux = null;
            Iterator iter = codigos.iterator();
            while (iter.hasNext())
            {
                aux = (CodArea) iter.next();
                cmbCodArea.addItem(aux.getCodArea());
            }
        }

    }
    
     public void cargarTabla(){
        Collection codigosAreas = unCodPais.buscarCodigosAreas(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaCodArea.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Código de área");
       
        CodArea aux = null;
       
        Iterator iter = codigosAreas.iterator();
            while (iter.hasNext()){
            aux = (CodArea) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getCodArea();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaCodArea.setModel(modelo);
    }
     

    
    public void eliminarCodArea(){
        String codigoArea = (String) tablaCodArea.getValueAt(tablaCodArea.getSelectedRow(), 0);
        unCodPais.eliminarCodArea(codigoArea,remiseria);
        this.cargarTabla();
        cargarCodAreaCombo();
        JOptionPane.showMessageDialog(null,"El Código de Área sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);

    }

    
    
    public void obtenerCodArea(){
        int fila = tablaCodArea.getSelectedRow();
        String codigoArea = (String) tablaCodArea.getValueAt(fila, 0);
        txtCodArea.setText(codigoArea);
    }

    public void agregarNuevoCodArea()
    {
     
        String elCodArea = txtCodArea.getText();
        if(unCodPais.buscarCodArea(elCodArea)==null){

            unCodPais.agregarCodArea(elCodArea);
            this.cargarTabla();
            this.cargarCodAreaCombo();
            txtCodArea.setText("");
            JOptionPane.showMessageDialog(null,"Se registro un nuevo Código de Área"," ",JOptionPane.INFORMATION_MESSAGE);
            btnAgregarCodArea.setEnabled(true);
            btnModificarCodArea.setEnabled(true);
            btnEliminarCodArea.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtCodArea.setEnabled(false);
            txtCodArea.setText(null);
            tablaCodArea.setEnabled(true);           
        }
        else
        {
                JOptionPane.showMessageDialog(null,"Ya existe el Código de Área","",JOptionPane.ERROR_MESSAGE);
                txtCodPais.setText("");
        }
   }
    
     public void modificarCodArea()
    {
        int fila = tablaCodArea.getSelectedRow();
        String codAreaaSeleccionado = (String) tablaCodArea.getValueAt(fila, 0);
        CodArea unCodArea = unCodPais.buscarCodArea(codAreaaSeleccionado);
        String codAreaModificar = (txtCodArea.getText());
         if (unCodPais.buscarCodArea(codAreaModificar) == null){
            unCodPais.modificarCodArea(unCodArea, codAreaModificar);
            this.cargarTabla();
            this.cargarCodAreaCombo();
            txtCodArea.setText(null);
            JOptionPane.showMessageDialog(null,"Se modificó el Código de Área seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);
            btnAgregarCodArea.setEnabled(true);
            btnModificarCodArea.setEnabled(true);
            btnEliminarCodArea.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtCodArea.setEnabled(false);
            txtCodArea.setText(null);
            tablaCodArea.setEnabled(true);          
        }
            else
            {
                 JOptionPane.showMessageDialog(null,"Ya existe el Código de Área"," ",JOptionPane.ERROR_MESSAGE);
                txtCodArea.setText(null);
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
        tablaCodArea = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtCodArea = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtCodPais = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnGuarda = new javax.swing.JButton();
        btnGuardaModificacion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnModificarCodArea = new javax.swing.JButton();
        btnAgregarCodArea = new javax.swing.JButton();
        btnEliminarCodArea = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaCodArea.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaCodArea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaCodArea.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de área"
            }
        ));
        tablaCodArea.setGridColor(new java.awt.Color(0, 0, 0));
        tablaCodArea.setRowHeight(20);
        tablaCodArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCodAreaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCodArea);

        txtCodArea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCodArea.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodArea.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodAreaMouseClicked(evt);
            }
        });
        txtCodArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodAreaKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Código de área:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodArea)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtCodPais.setEditable(false);
        txtCodPais.setBackground(new java.awt.Color(204, 204, 204));
        txtCodPais.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCodPais.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Código de país:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodPais)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
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
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnModificarCodArea.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarCodArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarCodArea.setText("Modificar");
        btnModificarCodArea.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarCodArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCodAreaActionPerformed(evt);
            }
        });

        btnAgregarCodArea.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregarCodArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregarCodArea.setText("Agregar ");
        btnAgregarCodArea.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarCodArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCodAreaActionPerformed(evt);
            }
        });

        btnEliminarCodArea.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarCodArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarCodArea.setText("Eliminar ");
        btnEliminarCodArea.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarCodArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCodAreaActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminarCodArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarCodArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnAgregarCodArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnAgregarCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addGap(10, 10, 10))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel3.setText("Gestor de código de área del teléfono");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(303, Short.MAX_VALUE))
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
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarCodAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCodAreaActionPerformed
        // TODO add your handling code here:
        if(tablaCodArea.getSelectedRow()!=-1)
        {
            this.obtenerCodArea();
            btnAgregarCodArea.setEnabled(false);
            btnModificarCodArea.setEnabled(false);
            btnEliminarCodArea.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtCodArea.setEnabled(true);
            tablaCodArea.setEnabled(false);        
            txtCodArea.requestFocus();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleecionado el Código de Área a seleccionar");
        }
    }//GEN-LAST:event_btnModificarCodAreaActionPerformed

    private void btnAgregarCodAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCodAreaActionPerformed
        // TODO add your handling code here:
        btnAgregarCodArea.setEnabled(false);
        btnModificarCodArea.setEnabled(false);
        btnEliminarCodArea.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtCodArea.setEnabled(true);
        txtCodArea.setText(null);
        tablaCodArea.setEnabled(false);
        txtCodArea.requestFocus();
    }//GEN-LAST:event_btnAgregarCodAreaActionPerformed

    private void btnEliminarCodAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCodAreaActionPerformed
        // TODO add your handling code here:
        if(tablaCodArea.getSelectedRow()!=-1)
        {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el Código de Área seleccionado?");
            if ( eleccion == 0)
            {  
                this.eliminarCodArea();
                this.cargarTabla();
                this.cargarCodAreaCombo();
                txtCodPais.setText("");
            }
        }
        else
        {
             JOptionPane.showMessageDialog(null,"No ha seleccionado un Código de Área"," ",JOptionPane.ERROR_MESSAGE); 
        }
    }//GEN-LAST:event_btnEliminarCodAreaActionPerformed

    private void tablaCodAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCodAreaMouseClicked
        // TODO add your handling code here:
//        this.obtenerCodArea();
        txtCodArea.setBackground(Color.white);
    }//GEN-LAST:event_tablaCodAreaMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtCodAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodAreaMouseClicked
        // TODO add your handling code here:
        txtCodArea.setBackground(Color.white);
    }//GEN-LAST:event_txtCodAreaMouseClicked

    private void txtCodAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodAreaKeyTyped
        // TODO add your handling code here:
         int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 10;
        {if (txtCodArea.getText().length()== limite)
            evt.consume();
        }             
    }//GEN-LAST:event_txtCodAreaKeyTyped

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
       if(!txtCodArea.getText().isEmpty())
        {
            this.agregarNuevoCodArea();
        }
        else
        {
            if(txtCodArea.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el Código de Área"," ",JOptionPane.ERROR_MESSAGE);
                  txtCodArea.setBackground(Color.red);
            }          
        }
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        // TODO add your handling code here:
      if(!txtCodArea.getText().isEmpty())
        {
            this.modificarCodArea();
        }
        else
        {
            if(txtCodArea.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el Código de Área"," ",JOptionPane.ERROR_MESSAGE);
                  txtCodArea.setBackground(Color.red);
            }          
        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnAgregarCodArea.setEnabled(true);
        btnModificarCodArea.setEnabled(true);
        btnEliminarCodArea.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtCodPais.setEnabled(false);
        txtCodPais.setText(null);
        tablaCodArea.setEnabled(true);
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
            java.util.logging.Logger.getLogger(JDialogCodAreaTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCodAreaTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCodAreaTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCodAreaTelefono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
   
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogCodAreaTelefono dialog = new JDialogCodAreaTelefono(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });      */
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCodArea;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarCodArea;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarCodArea;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaCodArea;
    private javax.swing.JTextField txtCodArea;
    private javax.swing.JTextField txtCodPais;
    // End of variables declaration//GEN-END:variables
}
