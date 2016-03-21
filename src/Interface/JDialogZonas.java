/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.ImprimirZona;
import Clases.Maestro;
import Clases.Operario;
import Clases.Remiseria;
import Clases.Utilidad;
import Clases.Zona;
import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author garba
 */
public class JDialogZonas extends javax.swing.JDialog {
    private Remiseria remiseria;
    private JComboBox cmbZona;
    private Operario unOperario;
    private Maestro unMaestro;
    private Utilidad utilidades = null;
    /**
     * Creates new form JDialogZonas
     */
    public JDialogZonas(java.awt.Frame parent, boolean modal, Remiseria remiseria, JComboBox cmbZona, Maestro unMaestro, Operario unOperario) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.cmbZona = cmbZona;
        this.unOperario = unOperario;
        this.unMaestro = unMaestro;
        this.utilidades = new Utilidad();
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarZonas();
        jXTaskPane1.setCollapsed(true);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);
        txtNombreZona.setEnabled(false);
        txtDescripcion.setEnabled(false);
        btnModificar.setEnabled(true);
        btnAgregar.setEnabled(true);
        btnEliminar.setEnabled(true);    
        try
        {
            tablaZonas.setRowSelectionInterval(0, 0);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "No hay Zonas registradas aúx", null, JOptionPane.WARNING_MESSAGE);
        }
    }

    public void cargarZonaCombo ()
    {
        if(cmbZona!=null)
        {
            cmbZona.removeAllItems();
            if(remiseria.getZonas()!= null)
            {
                Zona zo = null;
                Collection zonas = remiseria.getZonas().values();
                Iterator iter = zonas.iterator();
                while(iter.hasNext())
                {
                      zo = (Zona) iter.next();
                      cmbZona.addItem(zo);
                }
            }
        }
    }
    
   public void cargarZonas()
   {
        Collection zonas = remiseria.getZonas().values(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaZonas.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripción");
        Zona aux = null;
        Iterator iter = zonas.iterator();
        while (iter.hasNext())
        {
            aux = (Zona) iter.next();
            Object [] fila = new Object[2];
            fila[0] = aux.getNombreZona();
            fila[1] = aux.getDescripcion();
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaZonas.setModel(modelo);
    }
     
    public void obtenerZona()
    {
        int fila = tablaZonas.getSelectedRow();
        String codigo = (String) tablaZonas.getValueAt(fila, 0);     
        if(codigo != null)
        {    
        Zona unaZona = remiseria.buscarZona(codigo);
        txtDescripcion.setText(unaZona.getDescripcion());
        txtNombreZona.setText(unaZona.getNombreZona());
        }
     }
    
     public void agregarZona()
     {  
        String nombreZona = txtNombreZona.getText();
        if (remiseria.buscarZona(nombreZona) == null)
        {
            String descripcion = txtDescripcion.getText();
            remiseria.agregarZona( nombreZona, descripcion);
            this.cargarZonas();
            txtDescripcion.setText(null);
            txtNombreZona.setText(null);
            JOptionPane.showMessageDialog(null,"Se registró una nueva Zona"," ",JOptionPane.INFORMATION_MESSAGE);  
            if(remiseria.getUnaVentana()!= null)
            {
                remiseria.getUnaVentana().cargarZonas();
            }
            if(cmbZona!= null)
            {
                this.cargarZonaCombo();
            }               
            jXTaskPane1.setCollapsed(true);
            btnGuardarModificaciones.setEnabled(false);
            btnGuardarChofer.setEnabled(false);
            txtNombreZona.setEnabled(false);
            txtDescripcion.setEnabled(false);
            btnModificar.setEnabled(true);
            btnAgregar.setEnabled(true);
            btnEliminar.setEnabled(true);
            tablaZonas.setEnabled(true);
            tablaZonas.setRowSelectionInterval(0, 0);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Ya existe la Zona"," ",JOptionPane.ERROR_MESSAGE);
        }               
     }
     
     public void modificarZona()
     {
        int fila = tablaZonas.getSelectedRow();
        String codigo = (String) tablaZonas.getValueAt(fila, 0);
        Zona unaZona = remiseria.buscarZona(codigo);
        String nombreZona = txtNombreZona.getText();
        if (remiseria.buscarZona(nombreZona) == null)
        {
            String descripcion = txtDescripcion.getText();
            remiseria.modificarZona(unaZona, nombreZona, descripcion);
            this.cargarZonas();
            txtDescripcion.setText(null);
            txtNombreZona.setText(null);
            JOptionPane.showMessageDialog(null,"Se ha modifica la Zona seleccionada","",JOptionPane.INFORMATION_MESSAGE);
            if(remiseria.getUnaVentana()!= null)
            {
                remiseria.getUnaVentana().cargarZonas();
            }
            jXTaskPane1.setCollapsed(true);
            btnGuardarModificaciones.setEnabled(false);
            btnGuardarChofer.setEnabled(false);
            txtNombreZona.setEnabled(false);
            txtDescripcion.setEnabled(false);
            btnModificar.setEnabled(true);
            btnAgregar.setEnabled(true);
            btnEliminar.setEnabled(true);
            tablaZonas.setEnabled(true);
            tablaZonas.setRowSelectionInterval(0, 0);
        }
        else if(nombreZona.equals(unaZona.getNombreZona()))
        {
                    String descripcion = txtDescripcion.getText();
            remiseria.modificarZona(unaZona, nombreZona, descripcion);
            this.cargarZonas();
            txtDescripcion.setText(null);
            txtNombreZona.setText(null);
            JOptionPane.showMessageDialog(null,"Se ha modifica la Zona seleccionada","",JOptionPane.INFORMATION_MESSAGE);
            if(cmbZona!= null)
            {
                this.cargarZonaCombo();
            }
            if(remiseria.getUnaVentana()!= null)
            {
                remiseria.getUnaVentana().cargarZonas();
            }
            jXTaskPane1.setCollapsed(true);
            btnGuardarModificaciones.setEnabled(false);
            btnGuardarChofer.setEnabled(false);
            txtNombreZona.setEnabled(false);
            txtDescripcion.setEnabled(false);
            btnModificar.setEnabled(true);
            btnAgregar.setEnabled(true);
            btnEliminar.setEnabled(true);
            tablaZonas.setEnabled(true);
            tablaZonas.setRowSelectionInterval(0, 0);            
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Ya existe el nombre de Zona","",JOptionPane.ERROR_MESSAGE);
        }
            
     }
        
    public void eliminarZona()
    {
        int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar la Zona seleccionada?");
        if ( eleccion == 0)
        {   
            String codigo = (String) tablaZonas.getValueAt(tablaZonas.getSelectedRow(), 0);
            Zona unaZona = remiseria.buscarZona(codigo);
            remiseria.eliminarZona(unaZona);
            this.cargarZonas();
            if(cmbZona!= null)
            {
                this.cargarZonaCombo();
            }            
            if(remiseria.getUnaVentana()!= null)
            {
                remiseria.getUnaVentana().cargarZonas();
            }            
            tablaZonas.setRowSelectionInterval(0, 0);
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

        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscarMóvil1 = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaZonas = new javax.swing.JTable();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        txtNombreZona = new javax.swing.JTextField();
        btnGuardarModificaciones = new javax.swing.JButton();
        btnGuardarChofer = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnAgregar.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregar.setText("Nueva");
        btnAgregar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(255, 255, 255));
        btnModificar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificar.setText("Modificar ");
        btnModificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminar.setText("Eliminar ");
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnBuscarMóvil1.setBackground(new java.awt.Color(255, 255, 255));
        btnBuscarMóvil1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnBuscarMóvil1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER 1.png"))); // NOI18N
        btnBuscarMóvil1.setText("Listar");
        btnBuscarMóvil1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBuscarMóvil1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMóvil1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaZonas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaZonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripción"
            }
        ));
        tablaZonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaZonasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaZonas);

        jXTaskPane1.setCollapsed(true);
        jXTaskPane1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/document-new.png"))); // NOI18N
        jXTaskPane1.setTitle("Datos de la zona");

        jLabel15.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel15.setText("Nombre de zona:");

        jLabel14.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel14.setText("Inmediaciones:");

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescripcion.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDescripcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDescripcionMouseClicked(evt);
            }
        });
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });

        txtNombreZona.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombreZona.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreZona.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombreZona.setNextFocusableComponent(txtDescripcion);
        txtNombreZona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreZonaMouseClicked(evt);
            }
        });
        txtNombreZona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreZonaKeyTyped(evt);
            }
        });

        btnGuardarModificaciones.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardarModificaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnGuardarModificaciones.setText("<html><p>Guardar</p><p>Modificaciones</p></html>");
        btnGuardarModificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificacionesActionPerformed(evt);
            }
        });

        btnGuardarChofer.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardarChofer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/media-floppy.png"))); // NOI18N
        btnGuardarChofer.setText("Guardar");
        btnGuardarChofer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardarChofer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarChoferActionPerformed(evt);
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

        javax.swing.GroupLayout jXTaskPane1Layout = new javax.swing.GroupLayout(jXTaskPane1.getContentPane());
        jXTaskPane1.getContentPane().setLayout(jXTaskPane1Layout);
        jXTaskPane1Layout.setHorizontalGroup(
            jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPane1Layout.createSequentialGroup()
                        .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcion)
                            .addComponent(txtNombreZona)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPane1Layout.createSequentialGroup()
                        .addComponent(btnGuardarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jXTaskPane1Layout.setVerticalGroup(
            jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTaskPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel5.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel5.setText("Gestor de Zonas");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
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
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        jXTaskPane1.setCollapsed(false);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(true);
        txtNombreZona.setEnabled(true);
        txtDescripcion.setEnabled(true);
        btnModificar.setEnabled(false);
        btnAgregar.setEnabled(false);
        btnEliminar.setEnabled(false);
        txtNombreZona.requestFocus();
        tablaZonas.setEnabled(false);
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        if(tablaZonas.getSelectedRow()!= -1)
        {
            this.obtenerZona();        
            jXTaskPane1.setCollapsed(false);
            btnGuardarModificaciones.setEnabled(true);
            btnGuardarChofer.setEnabled(false);
            txtNombreZona.setEnabled(true);
            txtDescripcion.setEnabled(true);
            btnModificar.setEnabled(false);
            btnAgregar.setEnabled(false);
            btnEliminar.setEnabled(false);
            txtNombreZona.requestFocus();
            tablaZonas.setEnabled(false);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una Zona a modificar");
        }
        

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
         if(tablaZonas.getSelectedRow()!=-1)
         {
            this.eliminarZona();
            this.cargarZonas();
            txtNombreZona.setText("");
            txtDescripcion.setText("");
            if(remiseria.getUnaVentana()!= null)
            {
                remiseria.getUnaVentana().cargarZonas();            
            }
         }
         else
         {
             JOptionPane.showMessageDialog(null,"No ha seleccionado una Zona"," ",JOptionPane.ERROR_MESSAGE); 
         }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tablaZonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaZonasMouseClicked
        // TODO add your handling code here:
        //this.obtenerZona();
        txtNombreZona.setBackground(Color.white);
        txtDescripcion.setBackground(Color.white);
    }//GEN-LAST:event_tablaZonasMouseClicked

    private void txtNombreZonaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreZonaMouseClicked
        // TODO add your handling code here:
        txtNombreZona.setBackground(Color.white);
    }//GEN-LAST:event_txtNombreZonaMouseClicked

    private void txtDescripcionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDescripcionMouseClicked
        // TODO add your handling code here:
        txtDescripcion.setBackground(Color.white);
    }//GEN-LAST:event_txtDescripcionMouseClicked

    private void btnGuardarModificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionesActionPerformed
        // TODO add your handling code here:
        if((tablaZonas.getSelectedRow()!=-1)&& (!txtNombreZona.getText().isEmpty()) && (!txtNombreZona.getText().isEmpty())&&(!txtDescripcion.getText().isEmpty()) ){
            this.modificarZona();
        }
        else
        {
            if(tablaZonas.getSelectedRow()==-1){
                JOptionPane.showMessageDialog(null,"No ha selecciona una Zona para realizar la modificación"," ",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                    if(txtNombreZona.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null,"No ha ingresado el nombre de la Zona"," ",JOptionPane.ERROR_MESSAGE);
                        txtNombreZona.setBackground(Color.red);
                    }
                    if(txtDescripcion.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null,"No ha ingresado las inmediaciones de la Zona"," ",JOptionPane.ERROR_MESSAGE);
                        txtDescripcion.setBackground(Color.red);                
                    }      
            }
        }
    }//GEN-LAST:event_btnGuardarModificacionesActionPerformed

    private void btnGuardarChoferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarChoferActionPerformed
        // TODO add your handling code here:
        if(!txtNombreZona.getText().isEmpty()&&(!txtDescripcion.getText().isEmpty()))
        {
            this.agregarZona();
        }
        else
        {
            if(txtNombreZona.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el nombre de la Zona"," ",JOptionPane.ERROR_MESSAGE);
                  txtNombreZona.setBackground(Color.red);
            }
            
            if(txtDescripcion.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado las inmediaciones de la Zona"," ",JOptionPane.ERROR_MESSAGE);
                  txtDescripcion.setBackground(Color.red);                
            }
        }
    }//GEN-LAST:event_btnGuardarChoferActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        jXTaskPane1.setCollapsed(true);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);
        txtNombreZona.setEnabled(false);
        txtDescripcion.setEnabled(false);
        btnModificar.setEnabled(true);
        btnAgregar.setEnabled(true);
        btnEliminar.setEnabled(true);
        txtNombreZona.requestFocus();
        tablaZonas.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNombreZonaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreZonaKeyTyped
        // TODO add your handling code here:
        int limite  = 25;
        {if (txtNombreZona.getText().length()== limite)
            evt.consume();
        } 
    }//GEN-LAST:event_txtNombreZonaKeyTyped

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        // TODO add your handling code here:
        int limite  = 40;
        {if (txtDescripcion.getText().length()== limite)
            evt.consume();
        }         
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void btnBuscarMóvil1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMóvil1ActionPerformed
        // TODO add your handling code here:
        try{
            List historial = remiseria.buscarZonas();
            LinkedList <ImprimirZona> lista = new LinkedList<>();
            Zona aux = null;
            Iterator iter = historial.iterator();
            while (iter.hasNext())
            {
                aux = (Zona) iter.next();
                String codigo = String.valueOf(aux.getNombreZona());
                String referencia = "";
                if(aux.getDescripcion()!= null)
                {
                    referencia=String.valueOf((aux.getDescripcion()));
                }
                lista.add(new ImprimirZona(codigo,referencia));
            }
            HashMap<String, Object> parametros = new HashMap();
            parametros.clear();
            if(unMaestro != null)
            {
                parametros.put("operario1",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                parametros.put("nombreEmpresa",remiseria.getNombre());
                parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                parametros.put("fechaActual", utilidades.getFechaActual());
                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                parametros.put("logo", remiseria.getLogo());

            }
            else if(unOperario != null)
            {
                parametros.put("operario1",unOperario.getNombre()+ " " + unOperario.getApellido());
                parametros.put("nombreEmpresa",remiseria.getNombre());
                parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                parametros.put("fechaActual", utilidades.getFechaActual());
                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                parametros.put("logo", remiseria.getLogo());

            }
            //C:/Users/garba/Desktop/Reportes/
            //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/ReporteZonas.jrxml");
            JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/ReporteZonas.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JRBeanCollectionDataSource(lista));
            JasperViewer jviewer = new JasperViewer(jasperPrint, false);
            JDialog frame;
            frame = new JDialog();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(jviewer.getContentPane());
            frame.pack();
            frame.setSize(1100, 600);
            frame.setModal(true);
            frame.setLocationRelativeTo(null);
            frame.show();
        }
        catch (JRException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarMóvil1ActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogZonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogZonas dialog = new JDialogZonas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscarMóvil1;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarChofer;
    private javax.swing.JButton btnGuardarModificaciones;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaZonas;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombreZona;
    // End of variables declaration//GEN-END:variables
}
