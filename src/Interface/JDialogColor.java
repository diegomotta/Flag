/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Color;
import Clases.Remiseria;
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
public class JDialogColor extends javax.swing.JDialog {
private JComboBox cmbColor;
private Remiseria remiseria;
    private java.awt.Frame parent;
    private boolean modal;
    /**
     * Creates new form JDialogColor
     */
    public JDialogColor(java.awt.Frame parent, boolean modal, JComboBox cmbColor, Remiseria remiseria) {
        super(parent, modal);
        this.cmbColor = cmbColor;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        btnModificarColor.setEnabled(true);
        btnEliminarColor.setEnabled(true);
        btnNuevoColor.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtColor.setEnabled(false);
        tablaColores.setEnabled(true);
    
    }
    
     public void cargarColoresCombo(){
        cmbColor.removeAllItems();
        List colores = remiseria.buscarColores();
        if(colores != null){
            Color aux = null;
            Iterator iter = colores.iterator();
            while (iter.hasNext())
            {
                aux = (Color) iter.next();
                cmbColor.addItem(aux.getNombreColor());
            }
        }
    }
    
     public void cargarTabla(){
        Collection colores = remiseria.buscarColores(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th;
        th = tablaColores.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Colores");
       
        Color aux = null;
       
        Iterator iter = colores.iterator();
            while (iter.hasNext()){
            aux = (Color) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreColor();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaColores.setModel(modelo);
    }
     

    
    public void eliminarColor(){
        String nombreColor = (String) tablaColores.getValueAt(tablaColores.getSelectedRow(), 0);
        remiseria.eliminarColor(nombreColor);
        this.cargarTabla();
        cargarColoresCombo();
        JOptionPane.showMessageDialog(null,"El Color sido eliminado.\nSe sugiere modificar los datos de aquellos registros que contenian el Color eliminado.", null, JOptionPane.INFORMATION_MESSAGE);
        
    }

    
    
    public void obtenerColor(){
        int fila = tablaColores.getSelectedRow();
        String nombreColor = (String) tablaColores.getValueAt(fila, 0);
        txtColor.setText(nombreColor);
    }

      public void agregarNuevoColor(){
            String nombreColor = txtColor.getText();
            if(remiseria.buscarColor(nombreColor)== null){
                remiseria.agregarColor(nombreColor);
                this.cargarTabla();
                this.cargarColoresCombo();
                txtColor.setText("");
                JOptionPane.showMessageDialog(null,"Se registro un nuevo Color"," ",JOptionPane.INFORMATION_MESSAGE);
                btnModificarColor.setEnabled(true);
                btnEliminarColor.setEnabled(true);
                btnNuevoColor.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(true);
                txtColor.setEnabled(false);
                txtColor.setText(null);
                tablaColores.setEnabled(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Ya existe el Color"," ",JOptionPane.ERROR_MESSAGE);
            }
    }
      
    public void modificarColor()
    {
           int fila = tablaColores.getSelectedRow();
           String nombreColor = (String) tablaColores.getValueAt(fila, 0);
           Color unColor = remiseria.buscarColor(nombreColor);
           String nombreColorMod = txtColor.getText();
           if(remiseria.buscarMarca(nombreColorMod)== null){
                remiseria.modificarColor(unColor,nombreColor);
                this.cargarTabla();
                this.cargarColoresCombo();
                txtColor.setText("");
                JOptionPane.showMessageDialog(null,"Se ha modificado el Color seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);
                btnModificarColor.setEnabled(true);
                btnEliminarColor.setEnabled(true);
                btnNuevoColor.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(true);
                txtColor.setEnabled(false);
                txtColor.setText(null);
                tablaColores.setEnabled(true);
           }
           else
           {
                JOptionPane.showMessageDialog(null,"Ya existe el Color"," ",JOptionPane.ERROR_MESSAGE);               
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
    
    public static void cargarTablaColoresBuscados(JTable tabla, List lista) {
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
        modelo.addColumn("Colores");
  
       
        Color aux = null;
        
        Iterator iter = lista.iterator();
        while (iter.hasNext()){
            aux = (Color) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreColor();

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
        tablaColores = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtColor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnGuardaModificacion = new javax.swing.JButton();
        btnGuarda = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnNuevoColor = new javax.swing.JButton();
        btnModificarColor = new javax.swing.JButton();
        btnEliminarColor = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        txt_searchPro = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaColores.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaColores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaColores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Colores"
            }
        ));
        tablaColores.setColumnSelectionAllowed(true);
        tablaColores.setGridColor(new java.awt.Color(0, 0, 0));
        tablaColores.setRowHeight(20);
        tablaColores.getTableHeader().setReorderingAllowed(false);
        tablaColores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaColoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaColores);
        tablaColores.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        txtColor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtColor.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtColor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtColorMouseClicked(evt);
            }
        });
        txtColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtColorKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Nombre de Color:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtColor)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
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
                .addGap(5, 5, 5))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnNuevoColor.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnNuevoColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnNuevoColor.setText("Agregar");
        btnNuevoColor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevoColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoColorActionPerformed(evt);
            }
        });

        btnModificarColor.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarColor.setText("Modificar ");
        btnModificarColor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarColorActionPerformed(evt);
            }
        });

        btnEliminarColor.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarColor.setText("Eliminar ");
        btnEliminarColor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarColorActionPerformed(evt);
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModificarColor, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnNuevoColor, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnEliminarColor, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevoColor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarColor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarColor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel2.setText("Gestor de colores");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
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
        jLabel4.setText("Buscar color");

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoColorActionPerformed
        btnModificarColor.setEnabled(false);
        btnEliminarColor.setEnabled(false);
        btnNuevoColor.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtColor.setEnabled(true);
        tablaColores.setEnabled(false);
        txtColor.requestFocus();
    }//GEN-LAST:event_btnNuevoColorActionPerformed

    private void btnModificarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarColorActionPerformed
        // TODO add your handling code here:
        if(tablaColores.getSelectedRow()!=-1)
        {
            this.obtenerColor();
            btnModificarColor.setEnabled(false);
            btnEliminarColor.setEnabled(false);
            btnNuevoColor.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtColor.setEnabled(true);
            tablaColores.setEnabled(false);  
            txtColor.requestFocus();            
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Color para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnModificarColorActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnEliminarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarColorActionPerformed
        // TODO add your handling code here:
        
        if(tablaColores.getSelectedRow()!=-1)
        {
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el Color seleccionado?");
            if ( eleccion == 0)
            {    
                this.eliminarColor();
                this.cargarColoresCombo();
                this.cargarTabla();
                txtColor.setText("");
            }
        }
        else
        {
             JOptionPane.showMessageDialog(null,"No ha seleccionado un Color"," ",JOptionPane.ERROR_MESSAGE);       
        }
    }//GEN-LAST:event_btnEliminarColorActionPerformed

    private void tablaColoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaColoresMouseClicked
        // TODO add your handling code here:
       // this.obtenerColor();
        txtColor.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_tablaColoresMouseClicked

    private void txtColorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtColorMouseClicked
        // TODO add your handling code here:
        txtColor.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtColorMouseClicked

    private void txtColorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColorKeyTyped
        // TODO add your handling code here:
       char c; 
       c = evt.getKeyChar();
       if (!(c < '0' || c > '9'))
       {
          evt.consume();
       }
        int limite  = 15;
        {if (txtColor.getText().length()== limite)
            evt.consume();
        }           
    }//GEN-LAST:event_txtColorKeyTyped

    private void txt_searchProCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_searchProCaretUpdate
        // TODO add your handling code here:
        Color aux = null;
        List colores =  remiseria.buscarColores();
        List filtro = new LinkedList();
        if (!txt_searchPro.getText().isEmpty())
        {
            Iterator iter = colores.iterator();
            while (iter.hasNext())
            {
                aux = (Color) iter.next();
                if (aux.getNombreColor().toUpperCase().startsWith(txt_searchPro.getText().toUpperCase()))
                {
                    filtro.add(aux);
                }
                this.cargarTablaColoresBuscados(tablaColores, filtro);
            }
        }
        else
        {
            this.cargarTabla();
        }
    }//GEN-LAST:event_txt_searchProCaretUpdate

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnModificarColor.setEnabled(true);
        btnEliminarColor.setEnabled(true);
        btnNuevoColor.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtColor.setEnabled(false);
        txtColor.setText(null);
        tablaColores.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        // TODO add your handling code here:
     if((tablaColores.getSelectedRow()!=-1)&& (!txtColor.getText().isEmpty())){
            this.modificarColor();
        }
        else
        {
            if(tablaColores.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Color para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                if(txtColor.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"No ha ingresado un Color"," ",JOptionPane.ERROR_MESSAGE);
                    txtColor.setBackground(java.awt.Color.red);
                }
            }
        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        if(!txtColor.getText().isEmpty())
        {
            this.agregarNuevoColor();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado una nueva Color"," ",JOptionPane.ERROR_MESSAGE);
            txtColor.setBackground(java.awt.Color.red);
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
            java.util.logging.Logger.getLogger(JDialogColor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogColor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogColor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogColor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogColor dialog = new JDialogColor(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEliminarColor;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarColor;
    private javax.swing.JButton btnNuevoColor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private javax.swing.JTable tablaColores;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txt_searchPro;
    // End of variables declaration//GEN-END:variables
}
