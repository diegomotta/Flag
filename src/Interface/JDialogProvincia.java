/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Pais;
import Clases.Provincia;
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
public class JDialogProvincia extends javax.swing.JDialog {
private JComboBox cmbProvincia;
private Pais unPais;
private java.awt.Frame parent;
private boolean modal;
private Remiseria remiseria;
    /**
     * Creates new form JDialogProvincia
     */
    public JDialogProvincia(java.awt.Frame parent, boolean modal,JComboBox cmbProvincia, Pais unPais, Remiseria remiseria) {
        super(parent, modal);
        this.cmbProvincia = cmbProvincia;
        this.unPais = unPais;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        txtPais.setText(unPais.getNombrePais());
        txtProvincia.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
    
    }
    

    
     public void cargarProvinciaCombo(){
        cmbProvincia.removeAllItems();
        List provincias = unPais.buscarProvincias();
        if(provincias != null)
        {
            Provincia aux = null;
            Iterator iter = provincias.iterator();
            while (iter.hasNext())
            {
                aux = (Provincia) iter.next();
                cmbProvincia.addItem(aux.getNombreProvincia());
            }
        }

    }
    
     public void cargarTabla(){
        Collection provincias = unPais.buscarProvincias(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th;
        th = tablaProvincia.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);
        modelo.addColumn("Provincias");
       
        Provincia aux = null;
       
        Iterator iter = provincias.iterator();
            while (iter.hasNext()){
            aux = (Provincia) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreProvincia();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaProvincia.setModel(modelo);
    }
     

    
    public void eliminarProvincia(){
        String nombreProvincia = (String) tablaProvincia.getValueAt(tablaProvincia.getSelectedRow(), 0);
        unPais.eliminarProvincia(nombreProvincia,remiseria);
        this.cargarTabla();
        cargarProvinciaCombo();
    }

    
    
    public void obtenerProvincia(){
        int fila = tablaProvincia.getSelectedRow();
        String nombreProvincia = (String) tablaProvincia.getValueAt(fila, 0);
        txtProvincia.setText(nombreProvincia);
    }
    public void agregarNuevaProvincia(){
        
        String nombreProvincia = txtProvincia.getText();
        if(unPais.buscarProvincia(nombreProvincia)==null){
            unPais.agregarProvincia(nombreProvincia);
            this.cargarTabla();
            this.cargarProvinciaCombo();
            txtProvincia.setText(null);
            txtProvincia.setEnabled(false);
            btnModificarProvincia.setEnabled(true);
            btnEliminarProvincia.setEnabled(true);
            btnAgregarProvincia.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            tablaProvincia.setEnabled(true);           
            JOptionPane.showMessageDialog(null,"Se registro una nueva Provincia"," ",JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
                JOptionPane.showMessageDialog(null,"Ya existe la Provincia","",JOptionPane.ERROR_MESSAGE);
                txtProvincia.setText(null);
        }
            
   }
       
     public void modificarProvincia()
    {
        int fila = tablaProvincia.getSelectedRow();
        String provinciaSeleccionada = (String) tablaProvincia.getValueAt(fila, 0);
        Provincia unaProvinca = unPais.buscarProvincia(provinciaSeleccionada);
        String provinciaModificar = (txtProvincia.getText());
        if(unPais.buscarProvincia(provinciaModificar)==null){
                unPais.modificarProvincia(unaProvinca,provinciaModificar);
                this.cargarTabla();
                this.cargarProvinciaCombo();
                txtProvincia.setText(null);
                txtProvincia.setEnabled(false);
                btnModificarProvincia.setEnabled(true);
                btnEliminarProvincia.setEnabled(true);
                btnAgregarProvincia.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                tablaProvincia.setEnabled(true);                   
                JOptionPane.showMessageDialog(null,"Se modificó la Provincia seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);
         }
         else
            {
                 JOptionPane.showMessageDialog(null,"Ya existe la Provinca"," ",JOptionPane.ERROR_MESSAGE);
                 txtProvincia.setText(null);
            }
    } 
     
     public static void limpiar_tabla(JTable tabla) {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }
    
    public static void cargarTablaProvinciasBuscados(JTable tabla, List lista) {
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
        modelo.addColumn("Provincias");
  
       
        Provincia aux = null;
        
        Iterator iter = lista.iterator();
        while (iter.hasNext()){
            aux = (Provincia) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreProvincia();

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

        jPanel2 = new javax.swing.JPanel();
        btnModificarProvincia = new javax.swing.JButton();
        btnAgregarProvincia = new javax.swing.JButton();
        btnEliminarProvincia = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel3 = new javax.swing.JPanel();
        txtPais = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProvincia = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtProvincia = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnGuarda = new javax.swing.JButton();
        btnGuardaModificacion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        txt_searchPro = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnModificarProvincia.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarProvincia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarProvincia.setText("Modificar ");
        btnModificarProvincia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProvinciaActionPerformed(evt);
            }
        });

        btnAgregarProvincia.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregarProvincia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregarProvincia.setText("Agregar ");
        btnAgregarProvincia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProvinciaActionPerformed(evt);
            }
        });

        btnEliminarProvincia.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarProvincia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarProvincia.setText("Eliminar");
        btnEliminarProvincia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProvinciaActionPerformed(evt);
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
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnAgregarProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregarProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnEliminarProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        txtPais.setEditable(false);
        txtPais.setBackground(new java.awt.Color(204, 204, 204));
        txtPais.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPais.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPaisMouseClicked(evt);
            }
        });
        txtPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaisActionPerformed(evt);
            }
        });
        txtPais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaisKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("País:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPais)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(179, 179, 179))
        );

        tablaProvincia.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaProvincia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaProvincia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Provincias"
            }
        ));
        tablaProvincia.setGridColor(new java.awt.Color(0, 0, 0));
        tablaProvincia.setRowHeight(20);
        tablaProvincia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProvinciaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaProvincia);

        txtProvincia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProvincia.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtProvincia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtProvincia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtProvinciaMouseClicked(evt);
            }
        });
        txtProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProvinciaActionPerformed(evt);
            }
        });
        txtProvincia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProvinciaKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Nombre de Provincia:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtProvincia)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9))
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
        jLabel4.setText("Buscar provincia");

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

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel1.setText("Gestor de provincias");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProvinciaActionPerformed
        // TODO add your handling code here:
        txtProvincia.setEnabled(true);
        btnModificarProvincia.setEnabled(false);
        btnEliminarProvincia.setEnabled(false);
        btnAgregarProvincia.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        tablaProvincia.setEnabled(false);    
        txtProvincia.requestFocus();
    }//GEN-LAST:event_btnAgregarProvinciaActionPerformed

    private void btnEliminarProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProvinciaActionPerformed
        // TODO add your handling code here:
         if(tablaProvincia.getSelectedRow()!=-1){
                int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar la Provincia seleccionada?");
                if ( eleccion == 0)
                {
                this.eliminarProvincia();
                txtProvincia.setText("");
                this.cargarTabla();
                this.cargarProvinciaCombo();
                }
         }
         else
         {
             JOptionPane.showMessageDialog(null,"No ha seleccionado una Provincia"," ",JOptionPane.ERROR_MESSAGE); 
         }
    }//GEN-LAST:event_btnEliminarProvinciaActionPerformed

    private void txtProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProvinciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProvinciaActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void tablaProvinciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProvinciaMouseClicked
        // TODO add your handling code here:
        txtProvincia.setBackground(Color.white);
    }//GEN-LAST:event_tablaProvinciaMouseClicked

    private void btnModificarProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProvinciaActionPerformed
        if(tablaProvincia.getSelectedRow()!=-1)
        {   
            this.obtenerProvincia();
            txtProvincia.setEnabled(true);
            btnModificarProvincia.setEnabled(false);
            btnEliminarProvincia.setEnabled(false);
            btnAgregarProvincia.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            tablaProvincia.setEnabled(false); 
            txtProvincia.requestFocus();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una Provincia para realizar la modificación");
        }
    }//GEN-LAST:event_btnModificarProvinciaActionPerformed

    private void txtProvinciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtProvinciaMouseClicked
        // TODO add your handling code here:
        txtProvincia.setBackground(Color.white);
    }//GEN-LAST:event_txtProvinciaMouseClicked

    private void txtProvinciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProvinciaKeyTyped
        // TODO add your handling code here:
       char c; 
       c = evt.getKeyChar();
       if (!(c < '0' || c > '9'))
       {
          evt.consume();
       }
        int limite  = 20;
        {if (txtProvincia.getText().length()== limite)
            evt.consume();
        }          
    }//GEN-LAST:event_txtProvinciaKeyTyped

    private void txtPaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPaisMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaisMouseClicked

    private void txtPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaisActionPerformed

    private void txtPaisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaisKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaisKeyTyped

    private void txt_searchProCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_searchProCaretUpdate
        // TODO add your handling code here:
        Provincia aux = null;
        List provincias = unPais.buscarProvincias();
        List filtro = new LinkedList();
        if (!txt_searchPro.getText().isEmpty()) {
            Iterator iter = provincias.iterator();
            while (iter.hasNext()) 
            {
                aux = (Provincia) iter.next();
                if (aux.getNombreProvincia().toUpperCase().startsWith(txt_searchPro.getText().toUpperCase())) {
                    filtro.add(aux);
                }
                this.cargarTablaProvinciasBuscados(tablaProvincia, filtro);
            }
        }
        else 
        {
                this.cargarTabla();
        }
    }//GEN-LAST:event_txt_searchProCaretUpdate

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
       if(!txtProvincia.getText().isEmpty())
        {
            this.agregarNuevaProvincia();
        }
        else
        {
            if(txtProvincia.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado la Provincia"," ",JOptionPane.ERROR_MESSAGE);
                  txtProvincia.setBackground(Color.red);
            }       
        }
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        // TODO add your handling code here:
       if(!txtProvincia.getText().isEmpty())
        {
            this.modificarProvincia();
        }
        else
        {
            if(txtProvincia.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado la Provincia"," ",JOptionPane.ERROR_MESSAGE);
                  txtProvincia.setBackground(Color.red);
            }       
        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        txtProvincia.setEnabled(false);
        btnModificarProvincia.setEnabled(true);
        btnEliminarProvincia.setEnabled(true);
        btnAgregarProvincia.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        tablaProvincia.setEnabled(true); 
        txtProvincia.setText(null);
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
            java.util.logging.Logger.getLogger(JDialogProvincia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogProvincia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogProvincia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogProvincia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogProvincia dialog = new JDialogProvincia(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregarProvincia;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarProvincia;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarProvincia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private javax.swing.JTable tablaProvincia;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JTextField txt_searchPro;
    // End of variables declaration//GEN-END:variables
}
