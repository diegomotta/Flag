/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.KilometrosEnServicio;
import Clases.KilometrosRecorridos;
import Clases.Movil;
import Clases.Remiseria;
import Clases.Utilidad;
import Clases.Zona;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author garba
 */
public class JDialogCambiarEstado extends javax.swing.JDialog {
    private Remiseria remiseria;
    private Movil unMovil;
    private JTable tabla;
    private JTable tabla2;
    private JTable tabla3;
    private int lugar;
    private Utilidad utilidades;    
    /**
     * Creates new form JDialogCambiarEstado
     */
    public JDialogCambiarEstado(java.awt.Frame parent, boolean modal,Remiseria remiseria,Movil unMovil, JTable tabla, JTable tabla2, JTable tabla3, int lugar,Utilidad utilidades) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.unMovil = unMovil;
        this.tabla = tabla;
        this.tabla2 = tabla2;
        this.tabla3 = tabla3;
        this.lugar = lugar;
        this.utilidades = utilidades;
        initComponents();
        txtDNI.setVisible(false);
        this.cargarZonas();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        if (lugar == 0){
            cbxEstado.addItem("sin servicio");
            cbxEstado.addItem("descanso");
        }else if (lugar == 1){
            cbxEstado.addItem("disponible");
            cbxEstado.addItem("sin servicio");
        }
        if(unMovil.getEstado().equals("disponible"))
        {
            cmbZona.setEnabled(false);
//            KilometrosRecorridos kmsRec = unMovil.buscarUltimoKilometrajeRecorrido(unMovil);
//            if(kmsRec!= null)
//            {
//                  if(kmsRec.getKilometroFinal()>0)
//                  {
//                      txtKilometroFinalTeorico.setText(String.valueOf(kmsRec.getKilometroFinal()));
//                  }
//                  else
//                  {
//                      txtKilometroInicial.setText(String.valueOf(unMovil.getKilometraje()));
//                  }
//            }
        }
        else
        {
            if(unMovil.getEstado().equals("descanso"))
            {
                cmbZona.setEnabled(true);
//                 KilometrosRecorridos kmsRec = unMovil.buscarUltimoKilometrajeRecorrido(unMovil);
//                if(kmsRec!= null)
//                {
//                  if(kmsRec.getKilometroFinal()>0)
//                  {
//                  txtKilometroFinalTeorico.setText(String.valueOf(kmsRec.getKilometroFinal()));
//                  }
//                  else
//                  {
//                      txtKilometroInicial.setText(String.valueOf(unMovil.getKilometraje()));
//                  }
//                }
                
                if(cbxEstado.getSelectedItem().toString().equals("disponible"))
                {
                    unMovil.setEstado("disponible");
                }
                else
                {
                    if(cbxEstado.getSelectedItem().toString().equals("sin servicio"))
                    {
                        unMovil.setEstado("sin servicio");
                    }
                }
            }
        }
        txtMovil.setText(String.valueOf(this.unMovil.getNumeroMovil()));
        txtPantente.setText(this.unMovil.getPatente());
        txtNombre.setText(unMovil.getUnChofer().getNombre());
        txtApellido.setText(unMovil.getUnChofer().getApellido());
        txtDNI.setText(String.valueOf(unMovil.getUnChofer().getDni()));
        //txtKilometros.setText(""+unMovil.getKilometraje());
//        KilometrosRecorridos kmsRec = unMovil.buscarUltimoKilometrajeRecorrido(unMovil);
//        if(kmsRec != null)
//        {
//            txtKilometroFinalTeorico.setText(String.valueOf(kmsRec.getKilometroFinal()));
//        }
//        else
//        {
//            txtKilometroFinalTeorico.setText(String.valueOf(unMovil.getKilometraje()));
//        }
        try
        {
            //txtKilometroInicial.setText(String.valueOf((unMovil.buscarUltimoKilometrajeEnServicio(unMovil)).getKilometroInicialServicio()));
            txtKilometroInicial.setText(String.valueOf(unMovil.getKilometraje()));
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "NO se cargo el Kilometraje inicial debido que no se iniciaron viajes");
        }
   }

        public void cargarZonas(){
        Collection zonas = remiseria.getZonas().values();
        if(zonas!= null)
        {
            Zona aux = null;
            Iterator iter = zonas.iterator();
            while (iter.hasNext())
            {
                aux = (Zona) iter.next();
                cmbZona.addItem(aux.getNombreZona());
            }
        }
    }
    
        public void cargarDisponibles(){
        Collection moviles = remiseria.buscarMovilesDisponibles(); 
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
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Chofer");
        modelo.addColumn("Zona");
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            Object [] fila = new Object[5];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnaMarca().getNombreMarca();
            fila[2] = aux.getUnModelo().getNombreModelo();
            fila[3] = aux.getUnChofer().getNombre()+" "+aux.getUnChofer().getApellido();
            if(aux.getUnaZona()!= null)
            {
                fila[4] = aux.getUnaZona().getNombreZona();
            }        
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tabla.setModel(modelo);
    }
    
    public void cargarSinServicio(){
        Collection moviles = remiseria.buscarMovilesSinServicio(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tabla2.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Tipo de rodado");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Chofer");
        modelo.addColumn("Alquilado");
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            Object [] fila = new Object[6];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnRodado().getNombreRodado();
            fila[2] = aux.getUnaMarca().getNombreMarca();
            fila[3] = aux.getUnModelo().getNombreModelo();
            if (aux.getUnChofer() == null)
            {
                fila[4] = "Sin chofer";
            }
            else
            {
                fila[4] = aux.getUnChofer().getNombre()+ " " +aux.getUnChofer().getApellido() ;
            }
            if(aux.isAlquilado()==false)
            {
                 fila[5] = "No";
            }
            else
            {
                 fila[5] = "Si";
            }
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tabla2.setModel(modelo);
    }
    
    public void cargarDescanso(){
        Collection moviles = remiseria.buscarMovilesDescanso();
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tabla3.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente);
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Chofer");
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext()){
            aux = (Movil) iter.next();
            Object [] fila = new Object[4];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnaMarca().getNombreMarca();
            fila[2] = aux.getUnModelo().getNombreModelo();
            fila[3] = aux.getUnChofer().getNombre()+" "+aux.getUnChofer().getApellido();
            modelo.addRow(fila);
            }
        modelo.rowsRemoved(null);
        tabla3.setModel(modelo);
    }
    //ver aqui
    public void cambiarEstado(){
        String estate  = cbxEstado.getSelectedItem().toString();
        Zona unaZona = remiseria.buscarZona(cmbZona.getSelectedItem().toString());

            unMovil.setEstado(estate);
            if( estate.equals("sin servicio"))
            {
                if(utilidades.isNumber(txtKilometrajeActual.getText())==true)
                {
                    if(Integer.valueOf(txtKilometrajeActual.getText())>= unMovil.getKilometraje())
                    {    
                        remiseria.modificarMovilPorZona(unMovil);
                        unMovil.cargarDatosMovilSinServicio(unMovil,estate,txtKilometrajeActual.getText());
                        remiseria.modificarChoferPorMovil(unMovil);
                        KilometrosEnServicio kmsEnServ = unMovil.buscarUltimoKilometrajeEnServicio(unMovil);
                        unMovil.modificarKilometrosEnServicio(kmsEnServ, Integer.valueOf(txtKilometrajeActual.getText()), utilidades.getFechaHoraActual());
                        remiseria.getUnaVentana().cargarKilometrosRecorridosEnServicioFechaFijaYHoras();
                        this.cargarDisponibles();
                        this.cargarSinServicio();
                        this.cargarDescanso();
                        this.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "El Kilomentraje Actual debe ser mayor o igual al Kilometraje registrado por el sistema", null, JOptionPane.ERROR_MESSAGE);
                        txtKilometrajeActual.setBackground(Color.red);
                    }
                }
                else
                {
                    if(txtKilometrajeActual.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "No ha ingresado el Kilomentraje Actual", null, JOptionPane.ERROR_MESSAGE);
                        txtKilometrajeActual.setBackground(Color.red);
                    }
        
                }
            }
            else
            {
                if(estate.equals("disponible"))
                {
                    if(utilidades.isNumber(txtKilometrajeActual.getText())==true)
                    {
                        if(Integer.valueOf(txtKilometrajeActual.getText())>= unMovil.getKilometraje())
                        {       
                            remiseria.asignarPrioridad(unMovil);
                            unMovil.cargarDatosMovilDisponible(unMovil,estate,txtKilometrajeActual.getText(),unaZona);
                            this.cargarDisponibles();
                            this.cargarSinServicio();
                            this.cargarDescanso();
                            this.dispose();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "El Kilomentraje Actual debe ser mayor o igual al Kilometraje Inicial", null, JOptionPane.ERROR_MESSAGE);
                            txtKilometrajeActual.setBackground(Color.red);
                        }
                    }
                    else
                    {    if(txtKilometrajeActual.getText().isEmpty())
                         {
                            JOptionPane.showMessageDialog(null, "No ha ingresado el Kilomentraje Actual", null, JOptionPane.ERROR_MESSAGE);
                            txtKilometrajeActual.setBackground(Color.red);
                        }
                    }
                }
                else
                {
                    if(estate.equals("descanso"))
                    {
                        if(utilidades.isNumber(txtKilometrajeActual.getText())==true)
                        {
                            if(Integer.valueOf(txtKilometrajeActual.getText())>= unMovil.getKilometraje())
                            {    
                                unMovil.cargarDatosMovilDescanso(unMovil,estate,txtKilometrajeActual.getText());
                                this.cargarDisponibles();
                                this.cargarSinServicio();
                                this.cargarDescanso();
                                this.dispose();   
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "El Kilomentraje Actual debe ser mayor o igual al Kilometraje Inicial", null, JOptionPane.ERROR_MESSAGE);
                                txtKilometrajeActual.setBackground(Color.red);
                            }
                        } 
                        else
                        {
                            if(txtKilometrajeActual.getText().isEmpty())
                           {
                                JOptionPane.showMessageDialog(null, "No ha ingresado el Kilomentraje Actual", null, JOptionPane.ERROR_MESSAGE);
                                txtKilometrajeActual.setBackground(Color.red);
                           }
                    
                        } 
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

        jPanel1 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtDNI = new javax.swing.JTextField();
        cmbZona = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPantente = new javax.swing.JTextField();
        txtMovil = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtKilometrajeActual = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtKilometroInicial = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(400, 268));
        setMinimumSize(new java.awt.Dimension(400, 268));
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnAceptar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel9.setText("Cambiar estado del móvil");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9))
        );

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel4.setText("Estado:");

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Chofer:");

        cbxEstado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxEstado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbxEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEstadoItemStateChanged(evt);
            }
        });

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(204, 204, 204));
        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtApellido.setEditable(false);
        txtApellido.setBackground(new java.awt.Color(204, 204, 204));
        txtApellido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtApellido.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        txtDNI.setEditable(false);
        txtDNI.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDNI.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDNIActionPerformed(evt);
            }
        });

        cmbZona.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbZona.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cmbZona.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbZonaItemStateChanged(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel6.setText("Zona:");

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("Patente:");

        txtPantente.setEditable(false);
        txtPantente.setBackground(new java.awt.Color(204, 204, 204));
        txtPantente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPantente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtMovil.setEditable(false);
        txtMovil.setBackground(new java.awt.Color(204, 204, 204));
        txtMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMovil.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Movil N°:");

        txtKilometrajeActual.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtKilometrajeActual.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtKilometrajeActual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtKilometrajeActualMouseClicked(evt);
            }
        });
        txtKilometrajeActual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKilometrajeActualKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel7.setText("Kilometraje actual:");

        txtKilometroInicial.setEditable(false);
        txtKilometroInicial.setBackground(new java.awt.Color(204, 204, 204));
        txtKilometroInicial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtKilometroInicial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtKilometroInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtKilometroInicialMouseClicked(evt);
            }
        });
        txtKilometroInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKilometroInicialKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel10.setText("Kilometraje:");

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKilometroInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKilometrajeActual, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmbZona, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtPantente, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPantente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbZona, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKilometroInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKilometrajeActual, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(52, Short.MAX_VALUE))
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void cbxEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEstadoItemStateChanged
        // TODO add your handling code here:
        if (cbxEstado.getSelectedItem().toString().equals("sin servicio")) {
           // txtKilometrojeActualTeorico.setEnabled(true);
        } else {
          //  txtKilometrojeActualTeorico.setEnabled(false);
        }
    }//GEN-LAST:event_cbxEstadoItemStateChanged

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.cambiarEstado();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDNIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDNIActionPerformed

    private void cmbZonaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbZonaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbZonaItemStateChanged
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
    private void txtKilometrajeActualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKilometrajeActualKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }      
        int limite  = 7;
        {if (txtKilometrajeActual.getText().length()== limite)
            evt.consume();
        }  
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }          
    }//GEN-LAST:event_txtKilometrajeActualKeyTyped

    private void txtKilometrajeActualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKilometrajeActualMouseClicked
        // TODO add your handling code here:
         txtKilometrajeActual.setBackground(Color.white);
    }//GEN-LAST:event_txtKilometrajeActualMouseClicked

    private void txtKilometroInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKilometroInicialMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKilometroInicialMouseClicked

    private void txtKilometroInicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKilometroInicialKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKilometroInicialKeyTyped

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
            java.util.logging.Logger.getLogger(JDialogCambiarEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCambiarEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCambiarEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCambiarEstado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogCambiarEstado dialog = new JDialogCambiarEstado(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox cbxEstado;
    private javax.swing.JComboBox cmbZona;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtKilometrajeActual;
    private javax.swing.JTextField txtKilometroInicial;
    private javax.swing.JTextField txtMovil;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPantente;
    // End of variables declaration//GEN-END:variables
}
