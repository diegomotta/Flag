/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Clases.*;
import static Interface.JDialogCaja.cargarTablaCaja;
import static Interface.JDialogCambiarEstado.esEntero;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
/**
 *
 * @author garba
 */
public class JDialogCambiarDisponible extends javax.swing.JDialog {
    private Remiseria remiseria;
    private Utilidad utilidades;
    private Movil unMovil;
    private JTable tabla;
    private JTable tabla2;
    private java.awt.Frame parent;
    private boolean modal;
    private Caja unaCaja;
    /**
     * Creates new form JDialogCambiarDisponible
     */
    public JDialogCambiarDisponible(java.awt.Frame parent, boolean modal,Remiseria remiseria,Utilidad utilidades,Movil unMovil, JTable tabla, JTable tabla2, Caja unaCaja) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unaCaja = unaCaja;
        this.unMovil = unMovil;
        this.tabla = tabla;
        this.tabla2 = tabla2;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        txtMovil.setText(String.valueOf(this.unMovil.getNumeroMovil()));
        txtPatente.setText(this.unMovil.getPatente());
        txtFecha.setText(utilidades.getFechaActual());
        txtHora.setText(utilidades.getHoraActual());
//        if(this.unMovil.getKilometraje() >= 999999)
//        {
//            unMovil.ponerEnCeroKilometraje(unMovil);
//        }
        txtKilometros.setText(String.valueOf(this.unMovil.getKilometraje()));
        this.cargarZonas();
        this.cargarChoferesCombo();
        Movil mov = remiseria.buscarMovil(this.unMovil.getNumeroMovil());
        if(mov.getUnChofer()!= null)
        {
            cmbChofer.setSelectedItem(mov.getUnChofer());
        }
        else
        {
            cmbChofer.setSelectedItem("Sin chofer");
        }
        AutoCompleteDecorator.decorate(this.cmbChofer);
        AutoCompleteDecorator.decorate(this.cbxZona);
        txtRadio.setText(String.valueOf(remiseria.getUnPagoRadio().getValor()));
    }

    public void cargarZonas()
    {
        Collection zonas = remiseria.getZonas().values();
        if(zonas != null)
        {
            Zona aux = null;
            Iterator iter = zonas.iterator();
            while (iter.hasNext())
            {
                aux = (Zona) iter.next();
                cbxZona.addItem(aux);
            }
            //cbxZona.setSelectedIndex(0);
        }
    }
    
       public void cambiarEstado2(){
//        try{
            Zona zona = remiseria.buscarZona(cbxZona.getSelectedItem().toString());    
            if(!cmbChofer.getSelectedItem().equals("Sin chofer"))
            {
                if(utilidades.isNumber(txtKilometros.getText()))
                {
                    if (es_delDia(loteVigente())) 
                    {
                        Chofer chofer = (Chofer) cmbChofer.getSelectedItem();
                        String estado = txtEstado.getText().toString();
                        unMovil.agregarKilometrosEnServicio(Integer.valueOf(txtKilometros.getText()), utilidades.getFechaHoraActual(), chofer);
                        remiseria.cargarKms(unMovil, Integer.valueOf(txtKilometros.getText()));
                        remiseria.asignarPrioridad(unMovil);
                        remiseria.configurarEstado (unMovil,chofer,zona,estado);          
                        this.agregarNuevoChoferPorMovil();
                        this.agragarNuevoMovilPorZona();
                        this.cargarDisponibles();
                        this.cargarSinServicio();                        
                        this.dispose(); 
                        if(remiseria.getUnaVentana()!= null)
                        {
                            remiseria.getUnaVentana().cargarReservas();
                            remiseria.getUnaVentana().cargarKilometrosRecorridosEnServicioFechaFijaYHoras();
                            remiseria.getUnaVentana().cargarDisponibles();
                            remiseria.getUnaVentana().cargarSinServicio();
                        }
                        this.cargarChoferesCombo();             
                        try
                        {
                            Lote unLote = loteVigente();
                            unLote.altaMovimiento(utilidades.getDate(utilidades.getFechaActual()), utilidades.getHora(utilidades.getFechaHoraActual()), remiseria.getUnPagoRadio().getValor(), "Ingreso", "Pago de radio "+"-"+chofer.toString() , "Efectivo");
                        }
                        catch (Exception e)
                        {
                            JOptionPane.showMessageDialog(null,"No es el día vigente del lote", null, JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else 
                    {
                        JOptionPane.showMessageDialog(this, "Debe realizar una Apertura de Lote para continuar con esta operacion");
                    }                    
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado un Chofer para el Móvil"," ",JOptionPane.ERROR_MESSAGE);
            }
//        }
//        catch (Exception e)
//        {
//            JOptionPane.showMessageDialog(null,e," ",JOptionPane.ERROR_MESSAGE);
//        }
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
                fila[4] = aux.getUnaZona();          
            }
            else
            {
                 fila[4] = "";   
            }
            modelo.addRow(fila);
        }
        modelo.rowsRemoved(null);
        tabla.setModel(modelo);
    }
    
    public void cargarSinServicio()
    {
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
    
    public void agregarNuevoChoferPorMovil()
    {
        int numeroMovil = Integer.parseInt(txtMovil.getText());
        Chofer unChofer = (Chofer)cmbChofer.getSelectedItem();
        Movil movil = remiseria.buscarMovil(numeroMovil);
        String horaInicio = txtHora.getText();
        Date fechaInicio = utilidades.getDate(txtFecha.getText());
        remiseria.agregarChoferPorMovil(movil, unChofer, horaInicio, fechaInicio);
    }
    
    public void agragarNuevoMovilPorZona ()
    {
        int numeroMovil = Integer.parseInt(txtMovil.getText());
        Zona unaZona = remiseria.buscarZona(cbxZona.getSelectedItem().toString());
        Movil movil = remiseria.buscarMovil(numeroMovil);
        String horaInicio = txtHora.getText();
        String fechaInicio = txtFecha.getText();
        remiseria.agregarMovilPorZona(movil, unaZona, horaInicio, fechaInicio);
    }
      

    
    public void cargarChoferesCombo()
    {
        cmbChofer.removeAllItems();
        cmbChofer.addItem("Sin chofer");
        List choferes = remiseria.buscarChoferes(); 
        if(choferes != null)
        {
            Chofer unChofer = null;
            Iterator iter = choferes.iterator();
            while(iter.hasNext())
            {
                unChofer = (Chofer) iter.next();
                if (remiseria.choferOcupado(unChofer) == false)
                {
                    cmbChofer.addItem(unChofer);
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

        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMovil = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        cbxZona = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtPatente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtKilometros = new javax.swing.JTextField();
        cmbChofer = new javax.swing.JComboBox();
        txtRadio = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Movil N°:");

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Cambiar a estado:");

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel4.setText("Zona:");

        txtMovil.setEditable(false);
        txtMovil.setBackground(new java.awt.Color(204, 204, 204));
        txtMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMovil.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtMovil.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtMovil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMovilActionPerformed(evt);
            }
        });
        txtMovil.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtMovilPropertyChange(evt);
            }
        });

        txtEstado.setEditable(false);
        txtEstado.setBackground(new java.awt.Color(204, 204, 204));
        txtEstado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEstado.setText("Disponible");
        txtEstado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        cbxZona.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxZona.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel5.setText("Chofer:");

        txtPatente.setEditable(false);
        txtPatente.setBackground(new java.awt.Color(204, 204, 204));
        txtPatente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPatente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("Patente:");

        txtHora.setEditable(false);
        txtHora.setBackground(new java.awt.Color(204, 204, 204));
        txtHora.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtHora.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(204, 204, 204));
        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFecha.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel6.setText("Fecha de inicio:");

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel7.setText("Hora de inicio:");

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel8.setText("Kilometros inicial:");

        txtKilometros.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtKilometros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKilometrosKeyTyped(evt);
            }
        });

        cmbChofer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbChofer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtRadio.setEditable(false);
        txtRadio.setBackground(new java.awt.Color(204, 204, 204));
        txtRadio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel10.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel10.setText("Pago de la base: $");

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel10))
                        .addGap(10, 10, 10)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFecha)
                            .addComponent(txtHora)
                            .addComponent(txtEstado)
                            .addComponent(txtRadio)
                            .addComponent(cmbChofer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxZona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtKilometros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(15, 15, 15)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPatente, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                            .addComponent(txtMovil))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPatente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtKilometros, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbChofer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAceptar.setBackground(new java.awt.Color(255, 255, 255));
        btnAceptar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel9.setText("Iniciar turno");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9))
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:       
        this.cambiarEstado2();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txtMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMovilActionPerformed
        // TODO add your handling code here:

        
    }//GEN-LAST:event_txtMovilActionPerformed

    private void txtMovilPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtMovilPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_txtMovilPropertyChange
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
    private void txtKilometrosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKilometrosKeyTyped
        // TODO add your handling code here:
        int limite  = 7;
        {if (txtKilometros.getText().length()== limite)
            evt.consume();
        } 
        
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }           
    }//GEN-LAST:event_txtKilometrosKeyTyped

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
            java.util.logging.Logger.getLogger(JDialogCambiarDisponible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCambiarDisponible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCambiarDisponible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCambiarDisponible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogCambiarDisponible dialog = new JDialogCambiarDisponible(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox cbxZona;
    private javax.swing.JComboBox cmbChofer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtKilometros;
    private javax.swing.JTextField txtMovil;
    private javax.swing.JTextField txtPatente;
    private javax.swing.JTextField txtRadio;
    // End of variables declaration//GEN-END:variables
}
