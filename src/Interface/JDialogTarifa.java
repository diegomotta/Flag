/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;


import Clases.ImprimirTarifas;
import Clases.Maestro;
import Clases.Operario;
import Clases.Remiseria;
import Clases.Utilidad;
import Clases.Tarifa;
import Clases.Zona;
import static Interface.JDialogCliente.esEntero;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
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
public class JDialogTarifa extends javax.swing.JDialog {
private Remiseria remiseria;
private Utilidad utilidades;
private java.awt.Frame parent;
private boolean modal;
private Maestro unMaestro;
private Operario unOperario;
    /**
     * Creates new form JDialogTarifa
     */
    public JDialogTarifa(java.awt.Frame parent, boolean modal, Remiseria remiseria,Utilidad utilidades, Maestro unMaestro, Operario unOperario) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unMaestro = unMaestro;
        this.unOperario = unOperario;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarTarifas();
        txtCodigo.setEnabled(false);
        txtReferencia.setEnabled(false);
        txtImporte.setEnabled(false);
        txtHasta.setEnabled(false);  
        jXTaskPane1.setCollapsed(true);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);  
        try{
            tablaTarifas.setRowSelectionInterval(0, 0);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No hay tarifas cargadas aún",null, JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void cargarTarifas()
    {
        Collection tarifas = remiseria.buscarTarifas(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaTarifas.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Código");
        modelo.addColumn("Referencia");
        modelo.addColumn("Importe");   
        Tarifa aux = null;
        Iterator iter = tarifas.iterator();
        while (iter.hasNext())
        {
            aux = remiseria.getTarifas().get(iter.next());
            Object [] fila = new Object[3];
            fila[0] = aux.getCodigoTarifa();
            fila[1] = aux.getReferencia();
            fila[2] = aux.getPrecio();
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tablaTarifas.setModel(modelo);
    }
    
    public void obtenerTarifa()
    {
        int fila = tablaTarifas.getSelectedRow();
        double codigo = (double) tablaTarifas.getValueAt(fila, 0);
        Tarifa unaTarifa = remiseria.buscarTarifa(codigo);
        txtCodigo.setText(String.valueOf(unaTarifa.getCodigoTarifa()));
        txtReferencia.setText(unaTarifa.getReferencia());
        txtImporte.setText(String.valueOf(unaTarifa.getPrecio()));
    }

     public static void limpiar_tabla(JTable tabla) {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }
    
     public void agregarTarifaMejorado()
     {
         if(utilidades.isDouble(txtHasta.getText())==true)
         {
             limpiar_tabla(tablaTarifas);
             DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
             simbolos.setDecimalSeparator('.');
             DecimalFormat formateador = new DecimalFormat("###########.#",simbolos);
             DecimalFormat formateador2 = new DecimalFormat("###########.##",simbolos);
            // double desde = Double.parseDouble(formateador.format(Double.parseDouble(txtDesde.getText())));
             double hasta = Double.parseDouble(formateador.format(Double.parseDouble(txtHasta.getText())));
             double i = Double.parseDouble(formateador.format(1.0));
             while (i <= hasta)
             {
                 double imp = (i * remiseria.getUnCierreKilometraje().getValor())+ remiseria.getUnaBajadaBandera().getValorBajadaBandera();
                 double importe = Double.parseDouble(formateador2.format(imp));
                 remiseria.agregarTarifa(i, "", importe);
                 
                 i= i + 0.1;
                 i = Double.parseDouble(formateador.format(i));
             }
             this.cargarTarifas();
             tablaTarifas.setRowSelectionInterval(0, 0);
             jXTaskPane1.setCollapsed(true);
             btnGuardarModificaciones.setEnabled(false);
             btnGuardarChofer.setEnabled(false);
             btnModificar.setEnabled(true);
             btnEliminar.setEnabled(true);
             btnAgregar.setEnabled(true);
             btnEliminar1.setEnabled(true); 
             tablaTarifas.setEnabled(true);
         }
     
     }
    
    public void agregarTarifa()
    {   
        double codigo = Double.parseDouble(txtCodigo.getText());
        if (remiseria.buscarTarifa(codigo) == null)
        {
            String referencia = txtReferencia.getText();
            double importe = Double.parseDouble(txtImporte.getText());
            if(referencia != null)
            {
                remiseria.agregarTarifa(codigo, referencia, importe);
            }
            else
            {
                remiseria.agregarTarifa(codigo, "", importe);
            }
            this.cargarTarifas();
            txtCodigo.setText(null);
            txtReferencia.setText(null);
            txtImporte.setText(null);
             tablaTarifas.setRowSelectionInterval(0, 0);
             jXTaskPane1.setCollapsed(true);
             btnGuardarModificaciones.setEnabled(false);
             btnGuardarChofer.setEnabled(false);
             btnModificar.setEnabled(true);
             btnEliminar.setEnabled(true);
             btnAgregar.setEnabled(true);
             btnEliminar1.setEnabled(true); 
             tablaTarifas.setEnabled(true);            
            JOptionPane.showMessageDialog(null,"Se registro una nueva tarifa","",JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Ya se encuentra registrado una tarifa con el mismo codigo","",JOptionPane.ERROR_MESSAGE);
            txtCodigo.setText(null);
        }
    }
    
    public void modificarTarifa()
    {
        int fila = tablaTarifas.getSelectedRow();
        double codigo = (double) tablaTarifas.getValueAt(fila, 0);
        Tarifa unaTarifa = remiseria.buscarTarifa(codigo);
        double cod = Double.parseDouble(txtCodigo.getText());
        if (remiseria.buscarTarifa(cod) == null)
        {
            String referencia = txtReferencia.getText();
            double importe = Double.parseDouble(txtImporte.getText());
            if(remiseria.buscarImporte(importe) == null)
            {
                remiseria.modificarTarifa(unaTarifa, cod, referencia, importe);
                this.cargarTarifas();
                txtCodigo.setText(null);
                txtReferencia.setText(null);
                txtImporte.setText(null);
                JOptionPane.showMessageDialog(null,"Se ha modifica la tarifa seleccionada","",JOptionPane.INFORMATION_MESSAGE);
                tablaTarifas.setRowSelectionInterval(0, 0);
                jXTaskPane1.setCollapsed(true);
                btnGuardarModificaciones.setEnabled(false);
                btnGuardarChofer.setEnabled(false);
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnAgregar.setEnabled(true);
                btnEliminar1.setEnabled(true); 
                tablaTarifas.setEnabled(true);
                
            }
            else if(importe == unaTarifa.getPrecio())
            {
                remiseria.modificarTarifa(unaTarifa, cod, referencia, importe);
                this.cargarTarifas();
                txtCodigo.setText(null);
                txtReferencia.setText(null);
                txtImporte.setText(null);
                JOptionPane.showMessageDialog(null,"Se ha modifica la tarifa seleccionada","",JOptionPane.INFORMATION_MESSAGE);
                tablaTarifas.setRowSelectionInterval(0, 0);
                jXTaskPane1.setCollapsed(true);
                btnGuardarModificaciones.setEnabled(false);
                btnGuardarChofer.setEnabled(false);
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnAgregar.setEnabled(true);
                btnEliminar1.setEnabled(true); 
                tablaTarifas.setEnabled(true);
            }
            else
            {
                txtImporte.setBackground(Color.red); 
                JOptionPane.showMessageDialog(null,"El importe de la Tarifa ya existe","",JOptionPane.INFORMATION_MESSAGE);       
            }
        }
        else if(cod == unaTarifa.getCodigoTarifa() )
        {
            String referencia = txtReferencia.getText();
            double importe = Double.parseDouble(txtImporte.getText());
            if(remiseria.buscarImporte(importe) == null)
            {
                remiseria.modificarTarifa(unaTarifa, cod, referencia, importe);
                this.cargarTarifas();
                txtCodigo.setText(null);
                txtReferencia.setText(null);
                txtImporte.setText(null);
                JOptionPane.showMessageDialog(null,"Se ha modifica la tarifa seleccionada","",JOptionPane.INFORMATION_MESSAGE);
                tablaTarifas.setRowSelectionInterval(0, 0);
                jXTaskPane1.setCollapsed(true);
                btnGuardarModificaciones.setEnabled(false);
                btnGuardarChofer.setEnabled(false);
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnAgregar.setEnabled(true);
                btnEliminar1.setEnabled(true); 
                tablaTarifas.setEnabled(true);
                
            }  
            else if(importe == unaTarifa.getPrecio())
            {
                remiseria.modificarTarifa(unaTarifa, cod, referencia, importe);
                this.cargarTarifas();
                txtCodigo.setText(null);
                txtReferencia.setText(null);
                txtImporte.setText(null);
                JOptionPane.showMessageDialog(null,"Se ha modifica la tarifa seleccionada","",JOptionPane.INFORMATION_MESSAGE);
                tablaTarifas.setRowSelectionInterval(0, 0);
                jXTaskPane1.setCollapsed(true);
                btnGuardarModificaciones.setEnabled(false);
                btnGuardarChofer.setEnabled(false);
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnAgregar.setEnabled(true);
                btnEliminar1.setEnabled(true); 
                tablaTarifas.setEnabled(true);
            }
            else
            {
                txtImporte.setBackground(Color.red); 
                JOptionPane.showMessageDialog(null,"El importe de la Tarifa ya existe","",JOptionPane.INFORMATION_MESSAGE);       
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Ya existe una tarifa con el mismo codigo"," ",JOptionPane.ERROR_MESSAGE);
            txtCodigo.setBackground(Color.red);
        }
    }


    
    public void eliminarTarifa()
    {
            int eleccion = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar la Tarifa seleccionado?");
            if ( eleccion == 0)
            {
                double codigo = (double) tablaTarifas.getValueAt(tablaTarifas.getSelectedRow(), 0);
                Tarifa unaTarifa = remiseria.buscarTarifa(codigo);
                remiseria.eliminarTarifa(unaTarifa);
                this.cargarTarifas();
                JOptionPane.showMessageDialog(null,"La Tarifa a sido eliminado", null, JOptionPane.INFORMATION_MESSAGE);
            }
    }
    
    public void entradaAgregarTarifa()
    {
        try{
            
            if(jRadioButton2.isSelected()==true)
            {
                jRadioButton2.setNextFocusableComponent(txtHasta);
                txtHasta.requestFocus();
                if(utilidades.isDouble(txtHasta.getText()))
                {
                    if(remiseria.getTarifas().size()>0)
                    {
                        
                        JOptionPane.showMessageDialog(null, "Para realizar esta operación es necesario eliminar\n todas las tárifas. Ir al botón ''Eliminar Todo'' para ejecutar esta acción", null, WIDTH);
                    }
                    else
                    {
                        this.agregarTarifaMejorado();
                    }
                }
                else
                {
                    if(txtHasta.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null,"No ha ingresado el Kilomentraje aproximando en la ciudad");
                        txtHasta.setBackground(Color.red);
                    }
                }
            }
            else if(jRadioButton1.isSelected()==true)
            {
                txtCodigo.requestFocus();
                this.agregarTarifa();
            }
        }
        catch (Exception ex)
        {
            if(jRadioButton2.isSelected()==true)
            {
                if(txtHasta.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"No ha ingresado el Kilomentraje aproximando en la ciudad");
                    txtHasta.setBackground(Color.red);
                }
            }
            if(jRadioButton1.isSelected()==true)
            {
                if(txtCodigo.getText().isEmpty())
                {
                      JOptionPane.showMessageDialog(null,"No ha ingresado el código de la tarifa"," ",JOptionPane.ERROR_MESSAGE);
                      txtCodigo.setBackground(Color.red);
                }

                if(txtImporte.getText().isEmpty())
                {
                      JOptionPane.showMessageDialog(null,"No ha ingresado el importe de la tarifa"," ",JOptionPane.ERROR_MESSAGE);
                      txtImporte.setBackground(Color.red);                
                } 
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTarifas = new javax.swing.JTable();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtReferencia = new javax.swing.JTextField();
        txtImporte = new javax.swing.JTextField();
        txtHasta = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        btnGuardarChofer = new javax.swing.JButton();
        btnGuardarModificaciones = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnModificar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnEliminar1 = new javax.swing.JButton();
        btnBuscarMóvil1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaTarifas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaTarifas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaTarifas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Referencia", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaTarifas.setGridColor(new java.awt.Color(0, 0, 0));
        tablaTarifas.setRowHeight(20);
        tablaTarifas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTarifasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaTarifas);

        jXTaskPane1.setCollapsed(true);
        jXTaskPane1.setTitle("Datos de la tarifa");

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Código de tarifa:");

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("Referencia:");

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel4.setText("Importe en $:");

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCodigo.setNextFocusableComponent(txtReferencia);
        txtCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodigoMouseClicked(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        txtReferencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtReferencia.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtReferencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtReferencia.setNextFocusableComponent(txtImporte);
        txtReferencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtReferenciaMouseClicked(evt);
            }
        });
        txtReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtReferenciaKeyTyped(evt);
            }
        });

        txtImporte.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtImporte.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtImporte.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtImporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtImporteMouseClicked(evt);
            }
        });
        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImporteKeyTyped(evt);
            }
        });

        txtHasta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtHasta.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtHasta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHastaMouseClicked(evt);
            }
        });
        txtHasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHastaKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel6.setText("Kilometros:");

        jRadioButton1.setBackground(new java.awt.Color(214, 223, 247));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Tarifa fija");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setBackground(new java.awt.Color(214, 223, 247));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Tarifas ajustadas");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
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

        btnGuardarModificaciones.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnGuardarModificaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnGuardarModificaciones.setText("<html><p>Guardar</p><p>Modificaciones</p></html>");
        btnGuardarModificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarModificacionesActionPerformed(evt);
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
                .addComponent(jRadioButton1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPane1Layout.createSequentialGroup()
                        .addComponent(btnGuardarChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarModificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPane1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtHasta)
                            .addComponent(txtReferencia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                            .addComponent(txtImporte, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jXTaskPane1Layout.createSequentialGroup()
                        .addComponent(jRadioButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jXTaskPane1Layout.setVerticalGroup(
            jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPane1Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPane1Layout.createSequentialGroup()
                        .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3)
                    .addGroup(jXTaskPane1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4)))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                    .addComponent(jXTaskPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 463, Short.MAX_VALUE))
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
        jLabel5.setText("Gestor de Tarifas");

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

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnModificar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accessories-text-editor.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnAgregar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install (2).png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-trash.png"))); // NOI18N
        btnEliminar.setText("Eliminar ");
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCerrar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCerrar.setText("Salir");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnEliminar1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gtk-no.png"))); // NOI18N
        btnEliminar1.setText("Eliminar Todo");
        btnEliminar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEliminar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarMóvil1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        jXTaskPane1.setCollapsed(false);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnAgregar.setEnabled(false);
        btnEliminar1.setEnabled(false);  
        jRadioButton1.setSelected(true);
        txtCodigo.requestFocus();
        txtCodigo.setEnabled(true);
        txtReferencia.setEnabled(true);
        txtImporte.setEnabled(true);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        this.obtenerTarifa();
        tablaTarifas.setEnabled(false);
        jXTaskPane1.setCollapsed(false);
        btnGuardarModificaciones.setEnabled(true);
        btnGuardarChofer.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnAgregar.setEnabled(false);
        btnEliminar1.setEnabled(false); 
        jRadioButton1.setEnabled(false);
        jRadioButton2.setEnabled(false);
        txtCodigo.requestFocus();
        txtCodigo.setEnabled(true);
        txtReferencia.setEnabled(true);
        txtImporte.setEnabled(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void tablaTarifasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTarifasMouseClicked
        // TODO add your handling code here:
        //this.obtenerTarifa();
        txtReferencia.setBackground(Color.white);
        txtCodigo.setBackground(Color.white);
    }//GEN-LAST:event_tablaTarifasMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
         if(tablaTarifas.getSelectedRow()!=-1){
            this.eliminarTarifa();
            this.cargarTarifas();
            txtCodigo.setText(null);
            txtReferencia.setText(null);
            txtImporte.setText(null);
         }
         else
         {
             JOptionPane.showMessageDialog(null,"No ha seleccionado una tarifa"," ",JOptionPane.ERROR_MESSAGE); 
         }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodigoMouseClicked
        // TODO add your handling code here:
        txtCodigo.setBackground(Color.white);
    }//GEN-LAST:event_txtCodigoMouseClicked

    private void txtReferenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtReferenciaMouseClicked
        // TODO add your handling code here:
        txtReferencia.setBackground(Color.white);

    }//GEN-LAST:event_txtReferenciaMouseClicked

    private void txtImporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtImporteMouseClicked
        // TODO add your handling code here:
        
         txtImporte.setBackground(Color.white);
    }//GEN-LAST:event_txtImporteMouseClicked

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90)
        {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209)
        {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 10;
        {if (txtCodigo.getText().length()== limite)
            evt.consume();
        }          
         if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txtCodigoKeyTyped
        public static boolean esEntero(char caracter) {
        boolean res = true;
        //12 = borrar; 127 = suprimir; 46 = punto; 44 = coma
        if (!Character.isDigit(caracter)) {
            if (caracter != (char) '\b') {
                if (caracter != (char) 127) {
                    if (caracter != (char) 46) {
                        res = false;
                        java.awt.Toolkit.getDefaultToolkit().beep();
                    }

                }
            }
        }
        return res;
    }
    private void txtImporteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90)
        {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209)
        {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        char caracter = evt.getKeyChar();
        if((caracter == ',')||(caracter == ';'))
        {
             evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 10;
        {if (txtImporte.getText().length()== limite)
            evt.consume();
        }  
         if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
    }//GEN-LAST:event_txtImporteKeyTyped

    private void txtHastaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHastaKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90)
        {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209)
        {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 4;
        {if (txtHasta.getText().length()== limite)
            evt.consume();
        }     
         if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }          
    }//GEN-LAST:event_txtHastaKeyTyped

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        // TODO add your handling code here:
        int eleccion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar todas las Tarifas?");
        if ( eleccion == 0)
        {
            remiseria.eliminarTodasLasTarifas();
            this.cargarTarifas();
        }
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void txtHastaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHastaMouseClicked
        // TODO add your handling code here:
        txtHasta.setBackground(Color.white);
    }//GEN-LAST:event_txtHastaMouseClicked

    private void txtReferenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReferenciaKeyTyped
        // TODO add your handling code here:
        int limite  = 40;
        {if (txtReferencia.getText().length()== limite)
            evt.consume();
        }          
    }//GEN-LAST:event_txtReferenciaKeyTyped

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton1.isSelected()==true)
        {
            txtCodigo.setEnabled(true);
            txtReferencia.setEnabled(true);
            txtImporte.setEnabled(true);
            txtHasta.setEnabled(false);
        }
        else
        {
            txtHasta.setEnabled(true);
            txtCodigo.setEnabled(false);
            txtReferencia.setEnabled(false);
            txtImporte.setEnabled(false);            
        }
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton2.isSelected()==true)
        {
            txtCodigo.setEnabled(false);
            txtReferencia.setEnabled(false);
            txtImporte.setEnabled(false);
            txtHasta.setEnabled(true);
        }
        else
        {
            txtHasta.setEnabled(false);
            txtCodigo.setEnabled(true);
            txtReferencia.setEnabled(true);
            txtImporte.setEnabled(true);            
        }        
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void btnGuardarChoferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarChoferActionPerformed
        // TODO add your handling code here:
        if(jRadioButton1.isSelected()==true ||jRadioButton2.isSelected()==true )
        {
            this.entradaAgregarTarifa();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado una opción para agregar las Tarifas");
        }
    }//GEN-LAST:event_btnGuardarChoferActionPerformed

    private void btnGuardarModificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarModificacionesActionPerformed
        // TODO add your handling code here:
       if(!txtCodigo.getText().isEmpty()&&(!txtImporte.getText().isEmpty()))
        {
            this.modificarTarifa();
        }
        else
        {
            if(txtCodigo.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el código de la tarifa"," ",JOptionPane.ERROR_MESSAGE);
                  txtCodigo.setBackground(Color.red);
            }
            
            
             if(txtImporte.getText().isEmpty())
            {
                  JOptionPane.showMessageDialog(null,"No ha ingresado el importe de la tarifa"," ",JOptionPane.ERROR_MESSAGE);
                  txtImporte.setBackground(Color.red);                
            }
        }
    }//GEN-LAST:event_btnGuardarModificacionesActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        jXTaskPane1.setCollapsed(true);
        btnGuardarModificaciones.setEnabled(false);
        btnGuardarChofer.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnAgregar.setEnabled(true);
        btnEliminar1.setEnabled(true); 
        tablaTarifas.setEnabled(true);
        txtCodigo.setText(null);
        txtReferencia.setText(null);
        txtImporte.setText(null);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarMóvil1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMóvil1ActionPerformed
        // TODO add your handling code here:
        try{
            List historial = remiseria.buscarTarifasActivas();
            LinkedList <ImprimirTarifas> lista = new LinkedList<ImprimirTarifas>();
            Tarifa aux = null;
            Iterator iter = historial.iterator();
            while (iter.hasNext())
            {
                aux = (Tarifa) iter.next();
                String codigo = String.valueOf(aux.getCodigoTarifa());
                String referencia = "";
                if(aux.getReferencia()!= null)
                {
                    referencia=aux.getReferencia();
                }
                String importe ="$ "+String.valueOf(aux.getPrecio());
                

                lista.add(new ImprimirTarifas(codigo,referencia,importe));
            }
            HashMap<String, Object> parametros = new HashMap();
            Collections.sort(lista, new Comparator<ImprimirTarifas>() {
                @Override
                public int compare(ImprimirTarifas p1, ImprimirTarifas p2) {                
                        return new Double(Double.parseDouble(p1.getCodigo())).compareTo(new Double(Double.parseDouble(p2.getCodigo())));
                }
            });                
            parametros.clear();
            if(unMaestro != null)
            {
                parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
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
                parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                parametros.put("nombreEmpresa",remiseria.getNombre());
                parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                parametros.put("fechaActual", utilidades.getFechaActual());
                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                parametros.put("logo", remiseria.getLogo());

            }
            //C:/Users/garba/Desktop/Reportes/
            //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Tarifas.jrxml");
            JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Tarifas.jrxml");
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
            java.util.logging.Logger.getLogger(JDialogTarifa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogTarifa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogTarifa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogTarifa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogTarifa dialog = new JDialogTarifa(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnGuardarChofer;
    private javax.swing.JButton btnGuardarModificaciones;
    private javax.swing.JButton btnModificar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTable tablaTarifas;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtHasta;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtReferencia;
    // End of variables declaration//GEN-END:variables
}
