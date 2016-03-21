/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Marca;
import Clases.Movil;
import Clases.Remiseria;
import java.awt.Color;
import java.awt.Container;
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
public class JDialogMarca extends javax.swing.JDialog {
    private Remiseria remiseria;
    private java.awt.Frame parent;
    private boolean modal;
    private JComboBox cmbMarcas;
    
    /**
     * Creates new form JDialologMarca
     */
    public JDialogMarca(java.awt.Frame parent, boolean modal,JComboBox cmbMarcas ,Remiseria remiseria) {
        super(parent, modal);
        this.cmbMarcas = cmbMarcas;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        btnAgregarMarca.setEnabled(true);
        btnModificarMarca.setEnabled(true);
        btnEliminarMarca.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtMarca.setEnabled(false);
        txtMarca.setText(null);
        tablaMarca.setEnabled(true);
    
    }
    
     public void cargarMarcasCombo(){
        cmbMarcas.removeAllItems();
        List marcas = remiseria.buscarMarcas();
        if(marcas != null){
            Marca aux = null;
            Iterator iter = marcas.iterator();
            while (iter.hasNext())
            {
                aux = (Marca) iter.next();
                cmbMarcas.addItem(aux.getNombreMarca());
            }
        }
    }
    
     public void cargarTabla(){
        Collection marcas = remiseria.buscarMarcas(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th;
        th = tablaMarca.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);
        modelo.addColumn("Marcas");
       
        Marca aux = null;
       
        Iterator iter = marcas.iterator();
            while (iter.hasNext()){
            aux = (Marca) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreMarca();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaMarca.setModel(modelo);
    }
     

    
    public void eliminarMarca(){
        txtMarca.setText("");
        String nombreMarca = (String) tablaMarca.getValueAt(tablaMarca.getSelectedRow(), 0);
        remiseria.eliminarMarca(nombreMarca);
        JOptionPane.showMessageDialog(null,"La Marca del Móvil sido eliminado.\nSe sugiere modificar los datos de aquellos registros que contenian la Marca eliminada.", null, JOptionPane.INFORMATION_MESSAGE);
        
        this.cargarTabla();
    }

    
    
    public void obtenerMarca(){
        int fila = tablaMarca.getSelectedRow();
        String nombreMarca = (String) tablaMarca.getValueAt(fila, 0);
        txtMarca.setText(nombreMarca);
    }
    
     public void agregarNuevaMarca()
     {
            String nombreMarca = txtMarca.getText();
            if(remiseria.buscarMarca(nombreMarca)== null){
                remiseria.agregarMarca(nombreMarca);
                this.cargarTabla();
                this.cargarMarcasCombo();
                txtMarca.setText("");  
                JOptionPane.showMessageDialog(null,"Se registro una nueva marca"," ",JOptionPane.INFORMATION_MESSAGE);
                btnAgregarMarca.setEnabled(true);
                btnModificarMarca.setEnabled(true);
                btnEliminarMarca.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                txtMarca.setEnabled(false);
                txtMarca.setText(null);
                tablaMarca.setEnabled(true);                
          }
            else
            {
                  JOptionPane.showMessageDialog(null,"Ya existe la marca"," ",JOptionPane.ERROR_MESSAGE);
            }
    }
     
     
    public void modificarMarca()
    {
           int fila = tablaMarca.getSelectedRow();
           String nombreMarca = (String) tablaMarca.getValueAt(fila, 0);
           Marca unaMarca = remiseria.buscarMarca(nombreMarca);
           String nombreMarcaMod = txtMarca.getText();
           if(remiseria.buscarMarca(nombreMarcaMod)== null){
                remiseria.modificarMarca(unaMarca,nombreMarcaMod);
                this.cargarTabla();
                this.cargarMarcasCombo();
                txtMarca.setText("");
                JOptionPane.showMessageDialog(null,"Se ha modificado la marca seleccionado"," ",JOptionPane.INFORMATION_MESSAGE);
                btnAgregarMarca.setEnabled(true);
                btnModificarMarca.setEnabled(true);
                btnEliminarMarca.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                txtMarca.setEnabled(false);
                txtMarca.setText(null);
                tablaMarca.setEnabled(true);                
           }
           else
            {
                  JOptionPane.showMessageDialog(null,"Ya existe la marca"," ",JOptionPane.ERROR_MESSAGE);
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
    
    public static void cargarTablaMarcasBuscados(JTable tabla, List lista) {
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
        modelo.addColumn("Marcas");
  
       
        Marca aux = null;
        
        Iterator iter = lista.iterator();
        while (iter.hasNext()){
            aux = (Marca) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreMarca();

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
        tablaMarca = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtMarca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnGuarda = new javax.swing.JButton();
        btnGuardaModificacion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnModificarMarca = new javax.swing.JButton();
        btnAgregarMarca = new javax.swing.JButton();
        btnEliminarMarca = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        txt_searchPro = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaMarca.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaMarca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaMarca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Marcas"
            }
        ));
        tablaMarca.setGridColor(new java.awt.Color(0, 0, 0));
        tablaMarca.setRowHeight(20);
        tablaMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMarcaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaMarca);

        txtMarca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMarca.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMarca.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMarcaMouseClicked(evt);
            }
        });
        txtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarcaKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Nombre de marca:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMarca)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
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
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnModificarMarca.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarMarca.setText("Modificar ");
        btnModificarMarca.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarMarcaActionPerformed(evt);
            }
        });

        btnAgregarMarca.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregarMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregarMarca.setText("Agregar ");
        btnAgregarMarca.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMarcaActionPerformed(evt);
            }
        });

        btnEliminarMarca.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarMarca.setText("Eliminar ");
        btnEliminarMarca.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMarcaActionPerformed(evt);
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
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnModificarMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarMarca, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnAgregarMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel3.setText("Gestor de marcas");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
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
        jLabel4.setText("Buscar marca");

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
                .addComponent(txt_searchPro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnAgregarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMarcaActionPerformed
        btnAgregarMarca.setEnabled(false);
        btnModificarMarca.setEnabled(false);
        btnEliminarMarca.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtMarca.setEnabled(true);
        txtMarca.setText(null);
        tablaMarca.setEnabled(false);            
    }//GEN-LAST:event_btnAgregarMarcaActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        
         this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnEliminarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMarcaActionPerformed
        // TODO add your handling code here:
        if(tablaMarca.getSelectedRow()!=-1){
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar la Marca seleccionada?");
            if ( eleccion == 0)
            { 
                this.eliminarMarca();
                this.cargarMarcasCombo();
                this.cargarTabla();
                txtMarca.setText("");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una marca"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarMarcaActionPerformed

    private void tablaMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMarcaMouseClicked
        // TODO add your handling code here:

        this.obtenerMarca();
        txtMarca.setBackground(Color.WHITE);
    }//GEN-LAST:event_tablaMarcaMouseClicked

    private void btnModificarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarMarcaActionPerformed
        // TODO add your handling code here:
        if(tablaMarca.getSelectedRow()!=-1)
        {
                this.obtenerMarca();        
                btnAgregarMarca.setEnabled(false);
                btnModificarMarca.setEnabled(false);
                btnEliminarMarca.setEnabled(false);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(true);
                btnCancelar.setEnabled(true);
                txtMarca.setEnabled(true);
                tablaMarca.setEnabled(false);     
        }
        else
        {
             JOptionPane.showMessageDialog(null,"No ha selecciona una Marca para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarMarcaActionPerformed

    private void txtMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMarcaMouseClicked
        // TODO add your handling code here:
         txtMarca.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtMarcaMouseClicked

    private void txt_searchProCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_searchProCaretUpdate
        // TODO add your handling code here:
        Marca aux = null;
        List marcas =  remiseria.buscarMarcas();
        List filtro = new LinkedList();
        if (!txt_searchPro.getText().isEmpty())
        {
            Iterator iter = marcas.iterator();
            while (iter.hasNext())
            {
                aux = (Marca) iter.next();
                if (aux.getNombreMarca().toUpperCase().startsWith(txt_searchPro.getText().toUpperCase()))
                {
                    filtro.add(aux);
                }
                this.cargarTablaMarcasBuscados(tablaMarca, filtro);
            }
        }
        else
        {
            this.cargarTabla();
        }
    }//GEN-LAST:event_txt_searchProCaretUpdate

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        if(!txtMarca.getText().isEmpty())
        {
            this.agregarNuevaMarca();
        }
        else
        {
                 JOptionPane.showMessageDialog(null,"No ha ingresado una nueva marca"," ",JOptionPane.ERROR_MESSAGE);
                  txtMarca.setBackground(Color.red);
        }
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
     if((tablaMarca.getSelectedRow()!=-1)&& (!txtMarca.getText().isEmpty())){
            this.modificarMarca();
        }
        else
        {
        if(tablaMarca.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null,"No ha selecciona una marca para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                if(txtMarca.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"No ha ingresado una marca"," ",JOptionPane.ERROR_MESSAGE);
                    txtMarca.setBackground(Color.red);
                }
            }
        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnAgregarMarca.setEnabled(true);
        btnModificarMarca.setEnabled(true);
        btnEliminarMarca.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtMarca.setEnabled(false);
        txtMarca.setText(null);
        tablaMarca.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarcaKeyTyped
        // TODO add your handling code here:
        int limite  = 20;
        {if (txtMarca.getText().length()== limite)
            evt.consume();
        }          
    }//GEN-LAST:event_txtMarcaKeyTyped

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
            java.util.logging.Logger.getLogger(JDialogMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogMarca dialog = new JDialogMarca(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregarMarca;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarMarca;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarMarca;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private javax.swing.JTable tablaMarca;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txt_searchPro;
    // End of variables declaration//GEN-END:variables
}
