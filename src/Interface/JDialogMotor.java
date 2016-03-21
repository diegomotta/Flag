/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Motor;
import Clases.Movil;
import Clases.Remiseria;
import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author garba
 */
public class JDialogMotor extends javax.swing.JDialog {
private Remiseria remiseria;
private JComboBox cmbMotor;
private java.awt.Frame parent;
private boolean modal;

    /**
     * Creates new form JDialogMotor
     */
    public JDialogMotor(java.awt.Frame parent, boolean modal, JComboBox cmbMotor, Remiseria remiseria) {
        super(parent, modal);
        this.cmbMotor = cmbMotor;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        btnNuevoMotor.setEnabled(true);
        btnModificarMotor.setEnabled(true);
        btnEliminarMotor.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtMotor.setEnabled(false);
        txtMotor.setText(null);
        tablaMotor.setEnabled(true);
    }
    
     public void cargarMotoresCombo(){
        cmbMotor.removeAllItems();
        List motores =remiseria.buscarMotores();
        if(motores != null)
        {
            Motor aux = null;
            Iterator iter = motores.iterator();
            while (iter.hasNext())
            {
                aux = (Motor) iter.next();
                cmbMotor.addItem(aux.getNombreMotor());
            }
        }
    }
    
     public void cargarTabla(){
        Collection motores = remiseria.buscarMotores(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th;
        th = tablaMotor.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);
        modelo.addColumn("Motores");
       
        Motor aux = null;
       
        Iterator iter = motores.iterator();
            while (iter.hasNext()){
            aux = (Motor) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreMotor();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaMotor.setModel(modelo);
    }
     

    
    public void eliminarMotor(){
        String nombreMotor = (String) tablaMotor.getValueAt(tablaMotor.getSelectedRow(), 0);
        remiseria.eliminarMotor(nombreMotor);
        this.cargarTabla();
        cargarMotoresCombo();
        JOptionPane.showMessageDialog(null,"El Motor sido eliminado.\n Se sugiere modificar los datos de aquellos registros que contenian el Motor eliminado.", null, JOptionPane.INFORMATION_MESSAGE);
        
    }

    
    
    public void obtenerMotor(){
        int fila = tablaMotor.getSelectedRow();
        String nombreMotor = (String) tablaMotor.getValueAt(fila, 0);
        txtMotor.setText(nombreMotor);
    }
    
    public void agregarNuevoMotor(){
            String nombreMotor = txtMotor.getText();
            if(remiseria.buscarMotor(nombreMotor)== null)
            {
                remiseria.agregarMotor(nombreMotor);
                this.cargarTabla();
                this.cargarMotoresCombo();
                txtMotor.setText("");
                JOptionPane.showMessageDialog(null,"Se registro un nuevo motor"," ",JOptionPane.INFORMATION_MESSAGE);
                btnNuevoMotor.setEnabled(true);
                btnModificarMotor.setEnabled(true);
                btnEliminarMotor.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                txtMotor.setEnabled(false);
                txtMotor.setText(null);
                tablaMotor.setEnabled(true);                
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Ya existe el motor"," ",JOptionPane.ERROR_MESSAGE);
            }
            
            
    }
    
         public void modificarMotor()
    {
           int fila = tablaMotor.getSelectedRow();
           String nombreModelo = (String) tablaMotor.getValueAt(fila, 0);
           Motor unMotor = remiseria.buscarMotor(nombreModelo);
           String nombreMotorMod = txtMotor.getText();
            if(remiseria.buscarMotor(nombreMotorMod)== null)
            {   
                remiseria.modificarMotor(unMotor,nombreMotorMod);
                this.cargarTabla();
                this.cargarMotoresCombo();
                txtMotor.setText("");
                JOptionPane.showMessageDialog(null,"Se ha modificado el motor seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);
                btnNuevoMotor.setEnabled(true);
                btnModificarMotor.setEnabled(true);
                btnEliminarMotor.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                txtMotor.setEnabled(false);
                txtMotor.setText(null);
                tablaMotor.setEnabled(true);               
            }
            else
            {
                    JOptionPane.showMessageDialog(null,"Ya existe el motor"," ",JOptionPane.ERROR_MESSAGE);        
            }
    } 
         
    public static void limpiar_tabla(JTable tabla)
    {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) 
        {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }
    
    public static void cargarTablaMotoresBuscados(JTable tabla, List lista) {
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
        modelo.addColumn("Motores");
  
       
        Motor aux = null;
        
        Iterator iter = lista.iterator();
        while (iter.hasNext()){
            aux = (Motor) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreMotor();

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

        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMotor = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtMotor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnGuardaModificacion = new javax.swing.JButton();
        btnGuarda = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnNuevoMotor = new javax.swing.JButton();
        btnModificarMotor = new javax.swing.JButton();
        btnEliminarMotor = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        txt_searchPro = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaMotor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaMotor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaMotor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Motores"
            }
        ));
        tablaMotor.setColumnSelectionAllowed(true);
        tablaMotor.setGridColor(new java.awt.Color(0, 0, 0));
        tablaMotor.setRowHeight(20);
        tablaMotor.getTableHeader().setReorderingAllowed(false);
        tablaMotor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMotorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaMotor);
        tablaMotor.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tablaMotor.getAccessibleContext().setAccessibleName("");

        txtMotor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMotor.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMotor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtMotor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMotorMouseClicked(evt);
            }
        });
        txtMotor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMotorKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Nombre de motor:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMotor)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnNuevoMotor.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnNuevoMotor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnNuevoMotor.setText("Agregar");
        btnNuevoMotor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevoMotor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoMotorMouseClicked(evt);
            }
        });
        btnNuevoMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoMotorActionPerformed(evt);
            }
        });

        btnModificarMotor.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarMotor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarMotor.setText("Modificar");
        btnModificarMotor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarMotorActionPerformed(evt);
            }
        });

        btnEliminarMotor.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarMotor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarMotor.setText("Eliminar");
        btnEliminarMotor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMotorActionPerformed(evt);
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnModificarMotor, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(btnNuevoMotor, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(btnEliminarMotor, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevoMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel1.setText("Gestor de motores");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jXTaskPaneContainer3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        txt_searchPro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_searchPro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txt_searchPro.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_searchProCaretUpdate(evt);
            }
        });

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel4.setText("Buscar motor");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        javax.swing.GroupLayout jXTaskPaneContainer3Layout = new javax.swing.GroupLayout(jXTaskPaneContainer3);
        jXTaskPaneContainer3.setLayout(jXTaskPaneContainer3Layout);
        jXTaskPaneContainer3Layout.setHorizontalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                        .addComponent(txt_searchPro, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jXTaskPaneContainer3Layout.setVerticalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_searchPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoMotorActionPerformed
        // TODO add your handling code here:
        btnNuevoMotor.setEnabled(false);
        btnModificarMotor.setEnabled(false);
        btnEliminarMotor.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtMotor.setEnabled(true);
        txtMotor.setText(null);
        tablaMotor.setEnabled(false);        
 
    }//GEN-LAST:event_btnNuevoMotorActionPerformed

    private void btnModificarMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarMotorActionPerformed
        // TODO add your handling code here:
        if(tablaMotor.getSelectedRow()!=-1)
        {
            this.obtenerMotor();
            btnNuevoMotor.setEnabled(false);
            btnModificarMotor.setEnabled(false);
            btnEliminarMotor.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtMotor.setEnabled(true);
            tablaMotor.setEnabled(false);          
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha selecciona un Motor para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnModificarMotorActionPerformed

    private void tablaMotorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMotorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaMotorMouseClicked

    private void btnEliminarMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMotorActionPerformed
        // TODO add your handling code here:
        if(tablaMotor.getSelectedRow()!=-1){
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el Motor seleccionado?");
            if ( eleccion == 0)
            {              
                this.eliminarMotor();
                this.cargarMotoresCombo();
                this.cargarTabla();
                txtMotor.setText("");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un motor"," ",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarMotorActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txtMotorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMotorMouseClicked
        // TODO add your handling code here:
        txtMotor.setBackground(Color.white);
    }//GEN-LAST:event_txtMotorMouseClicked

    private void btnNuevoMotorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMotorMouseClicked
        // TODO add your handling code here:
        btnNuevoMotor.setEnabled(true);
        btnEliminarMotor.setEnabled(true);
        this.obtenerMotor();
    }//GEN-LAST:event_btnNuevoMotorMouseClicked

    private void txtMotorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMotorKeyTyped
        // TODO add your handling code here:
       char c; 
       c = evt.getKeyChar();
       if (!(c < '0' || c > '9'))
       {
          evt.consume();
       }
        int limite  = 20;
        {if (txtMotor.getText().length()== limite)
            evt.consume();
        }        
    }//GEN-LAST:event_txtMotorKeyTyped

    private void txt_searchProCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_searchProCaretUpdate
        // TODO add your handling code here:
        Motor aux = null;
        List modelos =  remiseria.buscarMotores();
        List filtro = new LinkedList();
        if (!txt_searchPro.getText().isEmpty())
        {
            Iterator iter = modelos.iterator();
            while (iter.hasNext())
            {
                aux = (Motor) iter.next();
                if (aux.getNombreMotor().toUpperCase().startsWith(txt_searchPro.getText().toUpperCase()))
                {
                    filtro.add(aux);
                }
                this.cargarTablaMotoresBuscados(tablaMotor, filtro);
            }
        }
        else
        {
            this.cargarTabla();
        }
    }//GEN-LAST:event_txt_searchProCaretUpdate

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnNuevoMotor.setEnabled(true);
        btnModificarMotor.setEnabled(true);
        btnEliminarMotor.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtMotor.setEnabled(false);
        txtMotor.setText(null);
        tablaMotor.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        if((tablaMotor.getSelectedRow()!=-1)&& (!txtMotor.getText().isEmpty())){
            this.modificarMotor();
        }
        else
        {
            if(tablaMotor.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null,"No ha selecciona un motor para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                if(txtMotor.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"No ha ingresado un motor"," ",JOptionPane.ERROR_MESSAGE);
                    txtMotor.setBackground(Color.red);
                }
            }
        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        if(!txtMotor.getText().isEmpty())
        {
            this.agregarNuevoMotor();
        }
        else
        {
                 JOptionPane.showMessageDialog(null,"No ha ingresado un nuevo motor"," ",JOptionPane.ERROR_MESSAGE);
                 txtMotor.setBackground(Color.red);
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
            java.util.logging.Logger.getLogger(JDialogMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogMotor dialog = new JDialogMotor(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEliminarMotor;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarMotor;
    private javax.swing.JButton btnNuevoMotor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private javax.swing.JTable tablaMotor;
    private javax.swing.JTextField txtMotor;
    private javax.swing.JTextField txt_searchPro;
    // End of variables declaration//GEN-END:variables
}
