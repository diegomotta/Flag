/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Clases.Caja;
import Clases.Lote;
import Clases.Movil;
import Clases.Remiseria;
import Clases.Utilidad;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author garba
 */
public class JDialogActivarMoviles extends javax.swing.JDialog {
private Remiseria remiseria;
private Caja unaCaja = null;
private java.awt.Frame parent;
private boolean modal;
private Utilidad utilidades;
    /**
     * Creates new form JDialogActivarMoviles
     */
    public JDialogActivarMoviles(java.awt.Frame parent, boolean modal, Remiseria remiseria) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.unaCaja =  remiseria.getCajaPrincipal();
        this.utilidades = new Utilidad();
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        if (es_delDia(loteVigente())) 
        {                        
             jButton11.setEnabled(false);
             btnIniciarTurno.setEnabled(true);
        }              
        else 
        {
             jButton11.setEnabled(true);
             btnIniciarTurno.setEnabled(false);
        }         
        this.cargarDisponibles();
        this.cargarSinServicio();
         
    }
     public void cargarDisponibles()
     {
        Collection moviles = remiseria.buscarMovilesDisponibles(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaDisponibles.getTableHeader(); 
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
        tablaDisponibles.setModel(modelo);
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
        th = tablaSinServicio.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Chofer");
        modelo.addColumn("Alquilado");
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            Object [] fila = new Object[5];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnaMarca().getNombreMarca();
            fila[2] = aux.getUnModelo().getNombreModelo();
            if (aux.getUnChofer() == null)
            {
                fila[3] = "Sin chofer";
            }
            else
            {
                fila[3] = aux.getUnChofer().getNombre()+ " " +aux.getUnChofer().getApellido() ;
            }
            if(aux.isAlquilado()==false)
            {
            fila[4] = "No";
            }
            else
            {
                 fila[4] = "Si";
            }
            modelo.addRow(fila);
            }
        modelo.rowsRemoved(null);
        tablaSinServicio.setModel(modelo);
    }    
 
     private boolean es_delDia(Lote unLote)
     {
        boolean ok = false;
        
        if (unLote != null) {
            Calendar hoy = Calendar.getInstance();
            Calendar fechaLote = Calendar.getInstance();
            fechaLote.setTime(unLote.getFechaLote());
            if (hoy.get(Calendar.DAY_OF_YEAR) == fechaLote.get(Calendar.DAY_OF_YEAR)) {
                ok = true;
            }
        }
        return ok;
    }    

    public Lote loteVigente() {

        Lote vigente = null;
        Collection lotesAnteriores = unaCaja.getLotes().values();
        //antes solamente List
        LinkedList<Lote> lotesAnteriores2 = new LinkedList();
        Iterator iter = lotesAnteriores.iterator();
        Lote lote = null;
        while(iter.hasNext()){
            lote = (Lote) iter.next();
            lotesAnteriores2.add(lote);
        }
        Collections.sort(lotesAnteriores2, new Comparator<Lote>() {
                @Override
                public int compare(Lote p1, Lote p2) {                
                        return new Long(p1.getFechaLote().getTime()).compareTo(new Long(p2.getFechaLote().getTime()));
                }
        });        
//        if (!lotesAnteriores2.isEmpty()) {
//            vigente = (Lote) lotesAnteriores2.get(lotesAnteriores2.size() - 1);
//        }
        if (!lotesAnteriores2.isEmpty()) {
            vigente = (Lote) lotesAnteriores2.getLast();
        }
        return vigente;
    }     
     
     private int verificarCierrePendiente() 
     {
        int res= 0;
        Lote vigente = unaCaja.obtenerLote_Vigente();
        if (vigente != null) 
        {
            if (!es_delDia(vigente)) {
                if (!vigente.isCerrado() && "".equals(vigente.getHoraCierre())) {
                    if (vigente.getMovimientos() != null) 
                    {
                   //     res = JOptionPane.showConfirmDialog(this, "Aún no se ha realizado el cierre de caja de las ultimas actividades, ¿desea cerrarlo?","Advertencia", JOptionPane.YES_NO_OPTION);
                        JOptionPane.showConfirmDialog(this, "Aún no se ha realizado el cierre de caja de las ultimas actividades, deber ir a Caja y realizar el Cierre de caja","Advertencia", JOptionPane.WARNING_MESSAGE);
                        res = 1;
                    }
//                    if (res == JOptionPane.YES_OPTION) {
//                        btnCerrarCajaActionPerformed(null);
//                    }
                }
            }
        }
        return res;
    }    
     
    public Movil obtenerMovil(){
        int fila = (int) tablaSinServicio.getSelectedRow();
        int numeroMovil = (int) tablaSinServicio.getValueAt(fila, 0);
        Movil unMovil = remiseria.buscarMovil(numeroMovil);
        return unMovil;
    }     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXTaskPaneContainer5 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaSinServicio = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jXSearchMovilesSinServicio = new org.jdesktop.swingx.JXSearchField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDisponibles = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        btnSalir4 = new javax.swing.JButton();
        btnIniciarTurno = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaSinServicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaSinServicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaSinServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Móvil N°", "Marca", "Modelo", "Nombre ", "Apellido "
            }
        ));
        tablaSinServicio.setGridColor(new java.awt.Color(0, 0, 0));
        tablaSinServicio.setRowHeight(20);
        tablaSinServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSinServicioMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaSinServicio);

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel7.setText("Móviles fuera de servicio");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 552, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7))
        );

        jXSearchMovilesSinServicio.setPrompt("Buscar un Móvil");
        jXSearchMovilesSinServicio.setToolTipText("");
        jXSearchMovilesSinServicio.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jXSearchMovilesSinServicioCaretUpdate(evt);
            }
        });
        jXSearchMovilesSinServicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jXSearchMovilesSinServicioKeyTyped(evt);
            }
        });

        tablaDisponibles.setBackground(new java.awt.Color(51, 255, 51));
        tablaDisponibles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaDisponibles.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablaDisponibles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Móvil N°", "Marca", "Modelo", "Chofer", "Zona"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDisponibles.setGridColor(new java.awt.Color(0, 0, 0));
        tablaDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDisponiblesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDisponibles);

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel8.setText("Móviles disponibles");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8))
        );

        javax.swing.GroupLayout jXTaskPaneContainer5Layout = new javax.swing.GroupLayout(jXTaskPaneContainer5);
        jXTaskPaneContainer5.setLayout(jXTaskPaneContainer5Layout);
        jXTaskPaneContainer5Layout.setHorizontalGroup(
            jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jXSearchMovilesSinServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jXTaskPaneContainer5Layout.setVerticalGroup(
            jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer5Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXSearchMovilesSinServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel16.setMaximumSize(new java.awt.Dimension(162, 453));
        jPanel16.setMinimumSize(new java.awt.Dimension(162, 453));

        btnSalir4.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir4.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        btnSalir4.setText("Salir");
        btnSalir4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir4ActionPerformed(evt);
            }
        });

        btnIniciarTurno.setBackground(new java.awt.Color(255, 255, 255));
        btnIniciarTurno.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnIniciarTurno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/dialog-apply.png"))); // NOI18N
        btnIniciarTurno.setText("Iniciar turno");
        btnIniciarTurno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnIniciarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarTurnoActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Moneda.png"))); // NOI18N
        jButton11.setText("Abrir caja");
        jButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalir4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btnIniciarTurno)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIniciarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jXTaskPaneContainer5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaSinServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSinServicioMouseClicked
        // TODO add your handling code here:

        if(evt.getClickCount()== 2)
        {

            Lote ultimoLote = unaCaja.obtenerLote_Vigente();
            if(ultimoLote == null )
            {
                JOptionPane.showMessageDialog(null, "Debe realizar la Apertura de la Caja, para Iniciar los viajes", null, JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                boolean ok = true;
                Collection choferes = remiseria.buscarChoferes();
                Collection zonas = remiseria.buscarZonas();
                Collection tarifas = remiseria.buscarTarifas();
                if (choferes.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Registre Choferes para iniciar el turno","No hay Choferes registrados",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                }
                if(zonas.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Registre Zonas para iniciar el turno","No hay Zonas registradas",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                }

                if(tarifas.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Registre Tarifas para iniciar el turno","No hay Tarifas registradas",JOptionPane.ERROR_MESSAGE);
                    ok = false;
                }
                if(ok == true)
                {
                    if(tablaSinServicio.getSelectedRow()!= -1)
                    {
                        if (es_delDia(loteVigente()))
                        {
                            btnIniciarTurno.setEnabled(true);
                            JDialogCambiarDisponible ventana = new JDialogCambiarDisponible(parent, modal, remiseria,utilidades, this.obtenerMovil(), tablaDisponibles, tablaSinServicio, unaCaja);
                            ventana.show();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, "Debe realizar una Apertura de Lote para continuar con esta operacion");
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil Fuera de Servicio para iniciar el turno"," ",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        }
    }//GEN-LAST:event_tablaSinServicioMouseClicked

    private void jXSearchMovilesSinServicioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jXSearchMovilesSinServicioCaretUpdate
        // TODO add your handling code here:
        Movil aux = null;
        Collection mo = remiseria.buscarMovilesSinServicio();
        List filtro = new LinkedList();
        if (!jXSearchMovilesSinServicio.getText().isEmpty())
        {
            Iterator iter = mo.iterator();
            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                if (String.valueOf(aux.getNumeroMovil()).equals(jXSearchMovilesSinServicio.getText()))
                {
                    filtro.add(aux);
                }
                this.cargarSinServicioBuscado(filtro);
            }
        }
        else
        {
            this.cargarSinServicio();
        }
    }//GEN-LAST:event_jXSearchMovilesSinServicioCaretUpdate
public void cargarSinServicioBuscado(List filtro){
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaSinServicio.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Chofer");
        modelo.addColumn("Alquilado");
        Movil aux = null;
        Iterator iter = filtro.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            Object [] fila = new Object[5];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnaMarca().getNombreMarca();
            fila[2] = aux.getUnModelo().getNombreModelo();
            if (aux.getUnChofer() == null)
            {
                fila[3] = "Sin chofer";
            }
            else
            {
                fila[3] = aux.getUnChofer().getNombre()+ " " +aux.getUnChofer().getApellido() ;
            }
            if(aux.isAlquilado()==false)
            {
            fila[4] = "No";
            }
            else
            {
                 fila[4] = "Si";
            }
            modelo.addRow(fila);
            }
        modelo.rowsRemoved(null);
        tablaSinServicio.setModel(modelo);
    }    
    private void jXSearchMovilesSinServicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jXSearchMovilesSinServicioKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
    }//GEN-LAST:event_jXSearchMovilesSinServicioKeyTyped

    private void btnSalir4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalir4ActionPerformed

    private void btnIniciarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarTurnoActionPerformed
        // TODO add your handling code here:
        try
        {

            boolean ok = true;
            Collection choferes = remiseria.buscarChoferes();
            Collection zonas = remiseria.buscarZonas();
            Collection tarifas = remiseria.buscarTarifas();
            if (choferes.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Registre Choferes para iniciar el turno","No hay Choferes registrados",JOptionPane.ERROR_MESSAGE);
                ok = false;
            }
            if(zonas.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Registre Zonas para iniciar el turno","No hay Zonas registradas",JOptionPane.ERROR_MESSAGE);
                ok = false;
            }

            if(tarifas.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Registre Tarifas para iniciar el turno","No hay Tarifas registradas",JOptionPane.ERROR_MESSAGE);
                ok = false;
            }
            if(ok == true)
            {
                if(tablaSinServicio.getSelectedRow()!= -1)
                {
                    if (es_delDia(loteVigente()))
                    {
                        JDialogCambiarDisponible ventana = new JDialogCambiarDisponible(parent, modal, remiseria,utilidades, this.obtenerMovil(), tablaDisponibles, tablaSinServicio, unaCaja);
                        ventana.show();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Debe realizar la Apertura de la Caja, para Iniciar los viajes", null, JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"No ha seleccionado un Móvil Fuera de Servicio para iniciar el turno"," ",JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnIniciarTurnoActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        try
        {
            int res = verificarCierrePendiente();
            Calendar hoy = Calendar.getInstance();
            Calendar fechaLote = Calendar.getInstance();
            if (unaCaja.obtenerLote_Vigente() != null)
            {
                fechaLote.setTime(unaCaja.obtenerLote_Vigente().getFechaLote());

                if (fechaLote.get(Calendar.DAY_OF_YEAR) == hoy.get(Calendar.DAY_OF_YEAR))
                {
                    JOptionPane.showMessageDialog(null, "La apertura de caja ya ha sido realizada el dia de hoy","Advertencia",JOptionPane.WARNING_MESSAGE);
                }
                else if(res ==0)
                {
                    JDialogAperturaDeCaja apertura = new JDialogAperturaDeCaja(parent,modal,remiseria,utilidades,unaCaja,null,null,null,null,btnIniciarTurno,jButton11,null,null,null);
                    apertura.show();

                }
            }
            else
            {
                JDialogAperturaDeCaja apertura = new JDialogAperturaDeCaja(parent,modal,remiseria,utilidades,unaCaja,null,null,null,null,btnIniciarTurno,jButton11,null,null,null);
                apertura.show();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al Abrir Caja",null,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void tablaDisponiblesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDisponiblesMouseClicked
        // TODO add your handling code here
    }//GEN-LAST:event_tablaDisponiblesMouseClicked

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
            java.util.logging.Logger.getLogger(JDialogActivarMoviles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogActivarMoviles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogActivarMoviles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogActivarMoviles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogActivarMoviles dialog = new JDialogActivarMoviles(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnIniciarTurno;
    private javax.swing.JButton btnSalir4;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private org.jdesktop.swingx.JXSearchField jXSearchMovilesSinServicio;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer5;
    private javax.swing.JTable tablaDisponibles;
    private javax.swing.JTable tablaSinServicio;
    // End of variables declaration//GEN-END:variables
}
