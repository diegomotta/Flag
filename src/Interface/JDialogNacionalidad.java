/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Nacionalidad;
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
public class JDialogNacionalidad extends javax.swing.JDialog {
private java.awt.Frame parent;
private boolean modal;
private JComboBox cmbNacionalidades;
private Remiseria remiseria;
    /**
     * Creates new form JDialogNacionalidad
     */
    public JDialogNacionalidad(java.awt.Frame parent, boolean modal,JComboBox cmbNacionalidades,Remiseria remiseria) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.cmbNacionalidades = cmbNacionalidades;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTabla();
        btnNuevaNacionalidad.setEnabled(true);
        btnModificarNacionalidad.setEnabled(true);
        btnEliminarNacionalidad.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtNacionalidad.setEnabled(false);
        txtNacionalidad.setText(null);
        tablaNacionalidades.setEnabled(true);
    
    }
    
     public void cargarNacionalidadesCombo(){
        cmbNacionalidades.removeAllItems();
        List nacionalidades = remiseria.buscarNacionalidades();
        if(nacionalidades != null)
        {
            Nacionalidad aux = null;
            Iterator iter = nacionalidades.iterator();
            while (iter.hasNext())
            {
                aux = (Nacionalidad) iter.next();
                cmbNacionalidades.addItem(aux.getNombreNacionalidad());
            }
        }
    }
    
     public void cargarTabla(){
        Collection nacionalidades = remiseria.buscarNacionalidades(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Nacionalidad");
       
        Nacionalidad aux = null;
       
        Iterator iter = nacionalidades.iterator();
            while (iter.hasNext()){
            aux = (Nacionalidad) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreNacionalidad();
            modelo.addRow(fila);
            }
        
        modelo.rowsRemoved(null);
        tablaNacionalidades.setModel(modelo);
    }
     

    
    public void eliminarNacionalidad(){
        String nombreNacionalidad = (String) tablaNacionalidades.getValueAt(tablaNacionalidades.getSelectedRow(), 0);
        remiseria.eliminarNacionalidad(nombreNacionalidad);
        this.cargarTabla();
        cargarNacionalidadesCombo();
        JOptionPane.showMessageDialog(null,"La Nacionalidad a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);
        
    }

    
    
    public void obtenerNacionaliad(){
        int fila = tablaNacionalidades.getSelectedRow();
        String nombreNacionalidad = (String) tablaNacionalidades.getValueAt(fila, 0);
        txtNacionalidad.setText(nombreNacionalidad);
        
    }

      public void agregarNuevoNacionalidad(){
            String nombreNacionalidad= txtNacionalidad.getText();
            if(remiseria.buscarNacionalidad(nombreNacionalidad)==null){
                remiseria.agregarNacionalidad(nombreNacionalidad);
                this.cargarTabla();
                this.cargarNacionalidadesCombo();
                txtNacionalidad.setText(null);
                JOptionPane.showMessageDialog(null,"Se registro una nueva Nacionalidad"," ",JOptionPane.INFORMATION_MESSAGE);
                btnNuevaNacionalidad.setEnabled(true);
                btnModificarNacionalidad.setEnabled(true);
                btnEliminarNacionalidad.setEnabled(true);
                btnGuarda.setEnabled(false);
                btnGuardaModificacion.setEnabled(false);
                btnCancelar.setEnabled(false);
                txtNacionalidad.setEnabled(false);
                txtNacionalidad.setText(null);
                tablaNacionalidades.setEnabled(true);                
            }
            else
            {
                    JOptionPane.showMessageDialog(null,"Ya existe la Nacionalidad"," ",JOptionPane.ERROR_MESSAGE);
                    txtNacionalidad.setText(null);
            }
      
      }
      
    
    public void modificarNacionalidad()
    {       
        int fila = tablaNacionalidades.getSelectedRow();
        String nombreNacionalidad = (String) tablaNacionalidades.getValueAt(fila,0);
        Nacionalidad unNacionalidad = remiseria.buscarNacionalidad(nombreNacionalidad);
        String nombreNacionalidadMod = (txtNacionalidad.getText());
        if (remiseria.buscarNacionalidad(nombreNacionalidadMod) == null || unNacionalidad.getNombreNacionalidad().equals(nombreNacionalidadMod) ){
           remiseria.modificarNacionalidad(unNacionalidad,nombreNacionalidadMod);
           this.cargarTabla();
           this.cargarNacionalidadesCombo();
           txtNacionalidad.setText(null);
           JOptionPane.showMessageDialog(null,"Se ha modifica la Nacionalidad seleccionado","",JOptionPane.INFORMATION_MESSAGE);
            btnNuevaNacionalidad.setEnabled(true);
            btnModificarNacionalidad.setEnabled(true);
            btnEliminarNacionalidad.setEnabled(true);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtNacionalidad.setEnabled(false);
            txtNacionalidad.setText(null);
            tablaNacionalidades.setEnabled(true);           
        }
        else
        {
                if(remiseria.buscarNacionalidad(nombreNacionalidadMod) != null)
                {
                   remiseria.modificarNacionalidad(unNacionalidad,nombreNacionalidadMod);
                    this.cargarTabla();
                    this.cargarNacionalidadesCombo();
                    txtNacionalidad.setText(null);
                    JOptionPane.showMessageDialog(null,"Se ha modifica la Nacionalidad seleccionado","",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Ya existe la Nacionalidad"," ",JOptionPane.ERROR_MESSAGE);
                    txtNacionalidad.setText(null);
                }
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
    
    public static void cargarTablaNacionalidadesBuscados(JTable tabla, List lista) {
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
        modelo.addColumn("Nacionalidad");
  
       
        Nacionalidad aux = null;
        
        Iterator iter = lista.iterator();
        while (iter.hasNext()){
            aux = (Nacionalidad) iter.next();
            Object [] fila = new Object[1];
            fila[0] = aux.getNombreNacionalidad();

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
        btnEliminarNacionalidad = new javax.swing.JButton();
        btnModificarNacionalidad = new javax.swing.JButton();
        btnNuevaNacionalidad = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaNacionalidades = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtNacionalidad = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnGuardaModificacion = new javax.swing.JButton();
        btnGuarda = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        txt_searchPro = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnEliminarNacionalidad.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminarNacionalidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminarNacionalidad.setText("Eliminar ");
        btnEliminarNacionalidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminarNacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarNacionalidadActionPerformed(evt);
            }
        });

        btnModificarNacionalidad.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificarNacionalidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificarNacionalidad.setText("Modificar ");
        btnModificarNacionalidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificarNacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarNacionalidadActionPerformed(evt);
            }
        });

        btnNuevaNacionalidad.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnNuevaNacionalidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnNuevaNacionalidad.setText("Agregar");
        btnNuevaNacionalidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevaNacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaNacionalidadActionPerformed(evt);
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
                    .addComponent(btnNuevaNacionalidad, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarNacionalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarNacionalidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevaNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaNacionalidades.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaNacionalidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nacionalidades"
            }
        ));
        tablaNacionalidades.setRequestFocusEnabled(false);
        tablaNacionalidades.setRowHeight(20);
        tablaNacionalidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaNacionalidadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaNacionalidades);

        txtNacionalidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNacionalidad.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNacionalidad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNacionalidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNacionalidadMouseClicked(evt);
            }
        });
        txtNacionalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNacionalidadKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Nacionalidad:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNacionalidad)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardaModificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel3.setText("Gestor de nacionalidades");

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
        jLabel4.setText("Buscar nacionalidad");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
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

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void tablaNacionalidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaNacionalidadesMouseClicked
        // TODO add your handling code here:
        this.obtenerNacionaliad();
        txtNacionalidad.setBackground(Color.white);
    }//GEN-LAST:event_tablaNacionalidadesMouseClicked

    private void btnNuevaNacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaNacionalidadActionPerformed
        // TODO add your handling code here:
        btnNuevaNacionalidad.setEnabled(false);
        btnModificarNacionalidad.setEnabled(false);
        btnEliminarNacionalidad.setEnabled(false);
        btnGuarda.setEnabled(true);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(true);
        txtNacionalidad.setEnabled(false);
        txtNacionalidad.setText(null);
        tablaNacionalidades.setEnabled(false);

    }//GEN-LAST:event_btnNuevaNacionalidadActionPerformed

    private void btnModificarNacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarNacionalidadActionPerformed
        // TODO add your handling code here:
        if(tablaNacionalidades.getSelectedRow()!= -1)
        {
            this.obtenerNacionaliad();
            btnNuevaNacionalidad.setEnabled(false);
            btnModificarNacionalidad.setEnabled(false);
            btnEliminarNacionalidad.setEnabled(false);
            btnGuarda.setEnabled(false);
            btnGuardaModificacion.setEnabled(true);
            btnCancelar.setEnabled(true);
            txtNacionalidad.setEnabled(true);
            tablaNacionalidades.setEnabled(false);        
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha selecciona una Nacionalidad para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarNacionalidadActionPerformed

    private void btnEliminarNacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarNacionalidadActionPerformed
        // TODO add your handling code here:
         if(tablaNacionalidades.getSelectedRow()!=-1){
            int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar la Nacionalidad seleccionada?");
            if ( eleccion == 0)
            {  
                this.eliminarNacionalidad();
                this.cargarTabla();
                txtNacionalidad.setText(null);
         
            }
         }
         else
         {
             JOptionPane.showMessageDialog(null,"No ha seleccionado una Nacionalidad"," ",JOptionPane.ERROR_MESSAGE); 
         }
    }//GEN-LAST:event_btnEliminarNacionalidadActionPerformed

    private void txtNacionalidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNacionalidadKeyTyped
        // TODO add your handling code here:
       char c; 
       c = evt.getKeyChar();
       if (!(c < '0' || c > '9'))
       {
          evt.consume();
       }
        int limite  = 20;
        {if (txtNacionalidad.getText().length()== limite)
            evt.consume();
        }   
    }//GEN-LAST:event_txtNacionalidadKeyTyped

    private void txtNacionalidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNacionalidadMouseClicked
        // TODO add your handling code here:
        txtNacionalidad.setBackground(Color.white);
    }//GEN-LAST:event_txtNacionalidadMouseClicked

    private void txt_searchProCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_searchProCaretUpdate
        // TODO add your handling code here:
        Nacionalidad aux = null;
        List tipos =  remiseria.buscarNacionalidades();
        List filtro = new LinkedList();
        if (!txt_searchPro.getText().isEmpty())
        {
            Iterator iter = tipos.iterator();
            while (iter.hasNext())
            {
                aux = (Nacionalidad) iter.next();
                if (aux.getNombreNacionalidad().toUpperCase().startsWith(txt_searchPro.getText().toUpperCase()))
                {
                    filtro.add(aux);
                }
                this.cargarTablaNacionalidadesBuscados(tablaNacionalidades, filtro);
            }
        }
        else
        {
            this.cargarTabla();
        }
    }//GEN-LAST:event_txt_searchProCaretUpdate

    private void btnGuardaModificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaModificacionActionPerformed
        if(!txtNacionalidad.getText().isEmpty())
        {
            this.modificarNacionalidad();
        }
        else
        {
            if(txtNacionalidad.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado la Nacionalidad"," ",JOptionPane.ERROR_MESSAGE);
                  txtNacionalidad.setBackground(Color.red);
            }
                     

        }
    }//GEN-LAST:event_btnGuardaModificacionActionPerformed

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        if(!txtNacionalidad.getText().isEmpty())
        {
            this.agregarNuevoNacionalidad();
        }
        else
        {
            if(txtNacionalidad.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado la Nacionalidad"," ",JOptionPane.ERROR_MESSAGE);
                  txtNacionalidad.setBackground(Color.red);
            }                    
        }
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        btnNuevaNacionalidad.setEnabled(true);
        btnModificarNacionalidad.setEnabled(true);
        btnEliminarNacionalidad.setEnabled(true);
        btnGuarda.setEnabled(false);
        btnGuardaModificacion.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtNacionalidad.setEnabled(false);
        txtNacionalidad.setText(null);
        tablaNacionalidades.setEnabled(true);
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
            java.util.logging.Logger.getLogger(JDialogNacionalidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogNacionalidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogNacionalidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogNacionalidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogNacionalidad dialog = new JDialogNacionalidad(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEliminarNacionalidad;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnGuardaModificacion;
    private javax.swing.JButton btnModificarNacionalidad;
    private javax.swing.JButton btnNuevaNacionalidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private javax.swing.JTable tablaNacionalidades;
    private javax.swing.JTextField txtNacionalidad;
    private javax.swing.JTextField txt_searchPro;
    // End of variables declaration//GEN-END:variables
}
