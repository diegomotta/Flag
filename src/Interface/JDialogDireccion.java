/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Clases.Barrio;
import Clases.DireccionViaje;
import Clases.Remiseria;
import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author garba
 */
public class JDialogDireccion extends javax.swing.JDialog {
private Barrio unBarrio;
private JComboBox cmbDireccion;
private Remiseria remiseria;
    /**
     * Creates new form JDialogDireccion
     */
    public JDialogDireccion(java.awt.Frame parent, boolean modal,JComboBox cmbDireccion,Barrio unBarrio,Remiseria remiseria) {
        super(parent, modal);
        this.unBarrio = unBarrio;
        this.cmbDireccion = cmbDireccion;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        txtBarrio.setText(unBarrio.getNombreBarrio());
        btnAgregarMarca2.setEnabled(true);
        btnModificarBarrio.setEnabled(true);
        btnEliminarBarrio.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtDireccion.setText(null);
        tablaDirecciones.setEnabled(true);        
    }
    
    public void cargarDireccionesAutoCompleter(Barrio unBarrio){
        cmbDireccion.removeAllItems();
        List direcciones = unBarrio.buscarDirecciones();
        if(direcciones != null)
        {
            DireccionViaje aux = null;
            Iterator iter = direcciones.iterator();
            while (iter.hasNext()){
                aux = (DireccionViaje) iter.next();
                cmbDireccion.addItem(aux.getDireccion());
            }
        }
    }
    
     public void cargarTabla(){
        Collection direcciones = unBarrio.buscarDirecciones(); 
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
        modelo.addColumn("Direcciones");
       
        DireccionViaje aux = null;
       
        Iterator iter = direcciones.iterator();
            while (iter.hasNext()){
            aux = (DireccionViaje) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getDireccion();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaDirecciones.setModel(modelo);
    }
     

    
    public void eliminarDireccion(){
        String direccion = (String) tablaDirecciones.getValueAt(tablaDirecciones.getSelectedRow(), 0);
        unBarrio.eliminarDireccion(direccion,remiseria);
        this.cargarTabla();
        this.cargarDireccionesAutoCompleter(unBarrio);
        JOptionPane.showMessageDialog(null,"La Dirección a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);
        
    }

    
    
    public void obtenerDireccion(){
        int fila = tablaDirecciones.getSelectedRow();
        String direccion = (String) tablaDirecciones.getValueAt(fila, 0);
      //  Barrio unBarrio = unaCiudad.buscarBarrio(nombreBarrio);
        txtDireccion.setText(direccion);
    }

    
   public void agregarNuevaDireccion(){
        
        String direccion = txtDireccion.getText();
        if(unBarrio.buscarDireccionViaje(direccion)==null){
            unBarrio.agregarDireccion(direccion);
            this.cargarTabla();

            this.cargarDireccionesAutoCompleter(unBarrio);
            txtDireccion.setText("");
            JOptionPane.showMessageDialog(null,"Se registro una nueva Dirección"," ",JOptionPane.INFORMATION_MESSAGE);
            btnAgregarMarca2.setEnabled(true);
            btnModificarBarrio.setEnabled(true);
            btnEliminarBarrio.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtDireccion.setEnabled(false);
            txtDireccion.setText(null);
            tablaDirecciones.setEnabled(true);            
        }
        else
        {
                JOptionPane.showMessageDialog(null,"Ya existe la Dirección","",JOptionPane.ERROR_MESSAGE);
                txtDireccion.setText("");
        }
            
   }
       
     public void modificarDireccion()
    {
        int fila = tablaDirecciones.getSelectedRow();
        String direccionSeleccionado = (String) tablaDirecciones.getValueAt(fila, 0);
        DireccionViaje unaDireccion = unBarrio.buscarDireccionViaje(direccionSeleccionado);
        String direccionModificar = (txtDireccion.getText());
         if (unBarrio.buscarDireccionViaje(direccionModificar) == null){
            unBarrio.modificarDireccion(unaDireccion,direccionModificar);
            this.cargarTabla();
            this.cargarDireccionesAutoCompleter(unBarrio);
            txtDireccion.setText(null);
            JOptionPane.showMessageDialog(null,"Se modificó la Dirección seleccionada"," ",JOptionPane.INFORMATION_MESSAGE);
            
        }
            else
            {
                 JOptionPane.showMessageDialog(null,"Ya existe la Dirección"," ",JOptionPane.ERROR_MESSAGE);
                txtDireccion.setText(null);
            }       
        
    } 
     
     public static void limpiar_tabla(JTable tabla) {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }
    
    public static void cargarTablaBarriosBuscados(JTable tabla, List lista) {
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
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);         
        modelo.addColumn("Direcciones");       
        Barrio aux = null;
       
        Iterator iter = lista.iterator();
        while (iter.hasNext()){
            aux = (Barrio) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreBarrio();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tabla.setModel(modelo);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel10 = new javax.swing.JPanel();
        btnModificarBarrio = new javax.swing.JButton();
        btnAgregarMarca2 = new javax.swing.JButton();
        btnEliminarBarrio = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtBarrio = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaDirecciones = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardaModificacion = new javax.swing.JButton();
        btnGuarda = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnModificarBarrio.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarBarrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarBarrio.setText("Modificar");
        btnModificarBarrio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarBarrioActionPerformed(evt);
            }
        });

        btnAgregarMarca2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregarMarca2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregarMarca2.setText("Agregar");
        btnAgregarMarca2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarMarca2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMarca2ActionPerformed(evt);
            }
        });

        btnEliminarBarrio.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarBarrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarBarrio.setText("Eliminar");
        btnEliminarBarrio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarBarrioActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarMarca2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarBarrio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarBarrio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregarMarca2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnModificarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel5.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel5.setText("Barrio:");

        txtBarrio.setEditable(false);
        txtBarrio.setBackground(new java.awt.Color(204, 204, 204));
        txtBarrio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtBarrio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBarrio)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaDirecciones.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaDirecciones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaDirecciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Direcciones"
            }
        ));
        tablaDirecciones.setGridColor(new java.awt.Color(0, 0, 0));
        tablaDirecciones.setRowHeight(20);
        tablaDirecciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDireccionesMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaDirecciones);

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel4.setText("Nueva Dirección:");

        txtDireccion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDireccion.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDireccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDireccionMouseClicked(evt);
            }
        });
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDireccion)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
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

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel1.setText("Gestor de Direcciones");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
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
                        .addGap(5, 5, 5)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarBarrioActionPerformed
        // TODO add your handling code here:
        if(tablaDirecciones.getSelectedRow()!= -1)
        {
            this.obtenerDireccion();
            btnAgregarMarca2.setEnabled(false);
            btnModificarBarrio.setEnabled(false);
            btnEliminarBarrio.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtDireccion.setEnabled(true);
            tablaDirecciones.setEnabled(false);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una Nacionalidad para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnModificarBarrioActionPerformed

    private void btnAgregarMarca2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMarca2ActionPerformed
        // TODO add your handling code here:
        btnAgregarMarca2.setEnabled(false);
        btnModificarBarrio.setEnabled(false);
        btnEliminarBarrio.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtDireccion.setEnabled(true);
        tablaDirecciones.setEnabled(false);       

    }//GEN-LAST:event_btnAgregarMarca2ActionPerformed

    private void btnEliminarBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarBarrioActionPerformed
        // TODO add your handling code here:
        if(tablaDirecciones.getSelectedRow()!=-1)
        {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar la Nacionalidad seleccionada?");
            if ( eleccion == 0)
            {  
                this.eliminarDireccion();
                txtDireccion.setText("");
                this.cargarTabla();
                this.cargarDireccionesAutoCompleter(unBarrio);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una Dirección"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarBarrioActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void tablaDireccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDireccionesMouseClicked
        // TODO add your handling code here:
//        this.obtenerDireccion();
//        txtDireccion.setBackground(Color.white);
    }//GEN-LAST:event_tablaDireccionesMouseClicked

    private void txtDireccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDireccionMouseClicked
        // TODO add your handling code here:
        txtDireccion.setBackground(Color.white);
    }//GEN-LAST:event_txtDireccionMouseClicked

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        if(!txtDireccion.getText().isEmpty())
        {
            this.modificarDireccion();
        }
        else
        {
            if(txtDireccion.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado la Dirección"," ",JOptionPane.ERROR_MESSAGE);
                txtDireccion.setBackground(Color.red);
            }

        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        if(!txtDireccion.getText().isEmpty())
        {
            this.agregarNuevaDireccion();
        }
        else
        {
            if(txtDireccion.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado la Dirección"," ",JOptionPane.ERROR_MESSAGE);
                txtDireccion.setBackground(Color.red);
            }

        }
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnAgregarMarca2.setEnabled(true);
        btnModificarBarrio.setEnabled(true);
        btnEliminarBarrio.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtDireccion.setText(null);
        tablaDirecciones.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        // TODO add your handling code here:
        int limite  = 40;
        {if (txtDireccion.getText().length()== limite)
            evt.consume();
        }           
    }//GEN-LAST:event_txtDireccionKeyTyped

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
            java.util.logging.Logger.getLogger(JDialogDireccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogDireccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogDireccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogDireccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogDireccion dialog = new JDialogDireccion(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregarMarca2;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarBarrio;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarBarrio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane5;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaDirecciones;
    private javax.swing.JTextField txtBarrio;
    private javax.swing.JTextField txtDireccion;
    // End of variables declaration//GEN-END:variables
}
