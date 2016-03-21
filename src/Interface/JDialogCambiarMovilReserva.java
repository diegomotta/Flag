/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Clases.Chofer;
import Clases.Movil;
import Clases.Remiseria;
import Clases.Reserva;
import Clases.Rodado;
import Clases.Utilidad;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author garba
 */
public class JDialogCambiarMovilReserva extends javax.swing.JDialog {
private Reserva unaReserva;
private Remiseria remiseria;
private Utilidad utilidades;
private  Collection movilesReservas;
private java.awt.Frame parent;
private boolean modal;
    /**
     * Creates new form JDialogCambiarMovilReserva
     */
    public JDialogCambiarMovilReserva(java.awt.Frame parent, boolean modal, Reserva unaReserva,Remiseria remiseria, Utilidad utilidades) {
        super(parent, modal);
        this.unaReserva = unaReserva;
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
         cmbChofer.removeAllItems();
         cbxMovil.removeAllItems();
       // this.cargarChoferesCombo();
        this.cargarRodados();
     //   this.buscarMovilesDispReservas();
        AutoCompleteDecorator.decorate(this.cbxMovil);
        AutoCompleteDecorator.decorate(this.cmbChofer);
//        AutoCompleteDecorator.decorate(this.cmbTipoMovil);
        if(unaReserva.getUnCliente()!= null)
        {
            txtCliente.setText(unaReserva.getUnCliente().toString());
            txtDirección.setText(unaReserva.getUnPais()+"-"+unaReserva.getUnaProvincia()+"-"+unaReserva.getUnaCiudad()+"-"+unaReserva.getUnBarrio()+"-"+unaReserva.getUnaDireccionViaje());
        }
        else if(unaReserva.getNombreClienteEsporadicto()!= null)
        {
            txtCliente.setText(unaReserva.getNombreClienteEsporadicto().toString());
            txtDirección.setText(unaReserva.getUnPais()+"-"+unaReserva.getUnaProvincia()+"-"+unaReserva.getUnaCiudad()+"-"+unaReserva.getUnBarrio()+"-"+unaReserva.getUnaDireccionViaje());
        }
        if(unaReserva.getFechaInicio()!= null)
        {
          txtFechaReserva.setText(utilidades.getFecha(unaReserva.getFechaInicio()));  
        }
        if(unaReserva.getHoraInicio()!= null && unaReserva.getHoraFin()!= null)
        {
          txtHoraInicio.setText(utilidades.getHora(unaReserva.getHoraInicio()));
          txtHoraFinalización.setText(utilidades.getHora(unaReserva.getHoraFin()));
        }
     } 
     
    public void buscarMovilesDispReservas()
    {     
        try{
            Date fechaInicio = unaReserva.getFechaInicio();
            if(fechaInicio == null)
            {
                Calendar cal = Calendar.getInstance();
       //       Calendar to Date Conversion
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DATE);
                fechaInicio = new Date(year -1900, month, day);
            }
            String horaInicio =utilidades.getHora(unaReserva.getHoraInicio());
            String horaFin = utilidades.getHora(unaReserva.getHoraFin());
            movilesReservas = remiseria.movilesDisponiblesReservas(fechaInicio,horaInicio,horaFin);
            cargarMoviles(movilesReservas);
        }
        catch (Exception ex)
        {
            JOptionPane.showConfirmDialog(null, ex);
        }
    }
    
    public void cargarMoviles(Collection moviles)
    {
        cbxMovil.removeAllItems();
        cbxMovil.addItem("Sin móvil");
        Movil aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Movil) iter.next();
            System.out.println(aux);
            if(aux.getEstado().equals("disponible"))
            {
//                if(aux.isAlquilado()==false)
//                {
                    cbxMovil.addItem(aux.getNumeroMovil());
//                }
            }
        }        
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
                if(unChofer.getUnMovil()!= null)
                {
                    if (remiseria.choferOcupado2(unChofer) == false)
                    {
                        cmbChofer.addItem(unChofer);
                    }
                }
            }
        }
    }
    
    
    public void cargarRodados()
    {
        cmbTipoMovil.removeAllItems();
        List rod = remiseria.buscarRodados(); 
        if(rod != null)
        {
            Rodado unRodado = null;
            Iterator iter = rod.iterator();
            while(iter.hasNext())
            {
                unRodado = (Rodado) iter.next();
                cmbTipoMovil.addItem(unRodado);
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
        jLabel15 = new javax.swing.JLabel();
        cmbTipoMovil = new javax.swing.JComboBox();
        cbxMovil = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cmbChofer = new javax.swing.JComboBox();
        txtCliente = new javax.swing.JTextField();
        txtDirección = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtFechaReserva = new javax.swing.JTextField();
        txtHoraFinalización = new javax.swing.JTextField();
        txtHoraInicio = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        buscarDisp = new javax.swing.JButton();
        buscarDisp1 = new javax.swing.JButton();
        buscarDisp2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(117, 150, 227));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel15.setText("Tipo de Móvil:");

        cmbTipoMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbTipoMovil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMovilActionPerformed(evt);
            }
        });
        cmbTipoMovil.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbTipoMovilPropertyChange(evt);
            }
        });

        cbxMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxMovil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMovilActionPerformed(evt);
            }
        });
        cbxMovil.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbxMovilPropertyChange(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel10.setText("Móvil disponible:");

        jLabel16.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel16.setText("Chofer:");

        cmbChofer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbChofer.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbChoferPropertyChange(evt);
            }
        });

        txtCliente.setEditable(false);
        txtCliente.setBackground(new java.awt.Color(204, 204, 204));
        txtCliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtDirección.setEditable(false);
        txtDirección.setBackground(new java.awt.Color(204, 204, 204));
        txtDirección.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDirección.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel17.setText("Cliente:");

        jLabel18.setBackground(new java.awt.Color(0, 0, 0));
        jLabel18.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel18.setText("Dirección:");

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel19.setText("Hora de inicio:");

        jLabel20.setBackground(new java.awt.Color(0, 0, 0));
        jLabel20.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel20.setText("Fecha de reserva:");

        jLabel21.setBackground(new java.awt.Color(0, 0, 0));
        jLabel21.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel21.setText("Hora de finalización:");

        txtFechaReserva.setEditable(false);
        txtFechaReserva.setBackground(new java.awt.Color(204, 204, 204));
        txtFechaReserva.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFechaReserva.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtHoraFinalización.setEditable(false);
        txtHoraFinalización.setBackground(new java.awt.Color(204, 204, 204));
        txtHoraFinalización.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtHoraFinalización.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtHoraInicio.setEditable(false);
        txtHoraInicio.setBackground(new java.awt.Color(204, 204, 204));
        txtHoraInicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtHoraInicio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel5.setText("Seleccione un móvil");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel4.setText("Datos de la reserva");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFechaReserva)
                            .addComponent(txtCliente)
                            .addComponent(txtDirección)
                            .addComponent(cmbTipoMovil, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxMovil, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbChofer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtHoraInicio)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(18, 18, 18)
                                .addComponent(txtHoraFinalización)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDirección, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtFechaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtHoraFinalización, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cmbTipoMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cmbChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jLabel1.setOpaque(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnAceptar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        buscarDisp.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        buscarDisp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/object-rotate-right.png"))); // NOI18N
        buscarDisp.setText("<html><p>Cargar móviles</p><p>disponibles</p></html>");
        buscarDisp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buscarDisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarDispActionPerformed(evt);
            }
        });

        buscarDisp1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        buscarDisp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel.png"))); // NOI18N
        buscarDisp1.setText("Cancelar");
        buscarDisp1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buscarDisp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarDisp1ActionPerformed(evt);
            }
        });

        buscarDisp2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        buscarDisp2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Taxi_Icon_32.png"))); // NOI18N
        buscarDisp2.setText("<html><p>Ver móviles</p><p>fuera de servicio</p></html>");
        buscarDisp2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buscarDisp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarDisp2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarDisp2)
                    .addComponent(buscarDisp)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buscarDisp1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buscarDisp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarDisp2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarDisp1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void cargarComboMovilSegunTipoMovil(Collection movilesSegTipoMovil)
    {
        if(movilesSegTipoMovil!= null)
        {
            cbxMovil.removeAllItems();
            Movil aux = null;
            Iterator iter = movilesSegTipoMovil.iterator();
            while (iter.hasNext())
            {
                aux = (Movil) iter.next();
                cbxMovil.addItem(aux.getNumeroMovil());      
            }  
        }
    }
    
    private void cmbTipoMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoMovilActionPerformed
        // TODO add your handling code here:
        if(cmbTipoMovil.getItemCount()!= 0)
        {
            Rodado unRodadito = (Rodado)cmbTipoMovil.getSelectedItem();
            Collection movilesDisponiblesRes = movilesReservas;
            if(movilesDisponiblesRes != null)
            {
                //cmbTipoMovil.setSelectedItem(unaReserva.getUnRodado());
                List filtro = new LinkedList();
                Movil aux = null;
                Iterator iter = movilesDisponiblesRes.iterator();
                
                while (iter.hasNext())
                {
                        aux = (Movil) iter.next();
                        if (aux.getUnRodado().equals(unRodadito)) 
                        {
                            if(aux.getEstado().equals("disponible"))
                            {
                                filtro.add(aux);
                            }
                        }
                } 
                cargarComboMovilSegunTipoMovil(filtro);
            }
        }          
    }//GEN-LAST:event_cmbTipoMovilActionPerformed

    private void cbxMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMovilActionPerformed
        // TODO add your handling code here:

        if(cbxMovil.isEnabled()==true)
        {
            if (cbxMovil.getItemCount() != 0)
            {
                //this.buscarMovilesDispReservas();
                if(utilidades.isNumber(cbxMovil.getSelectedItem().toString()) ==true)
                {
                  Movil unMovil = remiseria.buscarMovil(Integer.parseInt(cbxMovil.getSelectedItem().toString()));
                    if(unMovil.getUnChofer()!= null)
                    {
                        cmbChofer.setSelectedItem(unMovil.getUnChofer());
                    }
                    else
                    {
                        cmbChofer.setSelectedItem("Sin chofer");
                    }
                }
            }            
        }
    }//GEN-LAST:event_cbxMovilActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        try
        {
            Movil unMovil = remiseria.buscarMovil((Integer.parseInt(cbxMovil.getSelectedItem().toString())));
            Chofer unChofer = (Chofer)cmbChofer.getSelectedItem();
            Rodado unRodado = (Rodado)cmbTipoMovil.getSelectedItem();
            remiseria.cargarReservaDatosReserva(unaReserva, unMovil, unChofer, unRodado);
            if(remiseria.getUnaVentana()!= null)
            {
                remiseria.getUnaVentana().cargarReservasTransito();
                remiseria.getUnaVentana().cargarReservas();
                remiseria.getUnaVentana().cargarDisponibles();
                remiseria.getUnaVentana().cargarTablaViajes();
            }
            this.dispose();
        }
        catch (Exception ex)
        {
              if(cmbChofer.getSelectedItem() == null ||cmbChofer.getSelectedItem().toString().equals("Sin chofer"))
              {
                  jLabel1.setText("Informe de Error: No ha selecciona un chofer");
              }
              else if(cbxMovil.getSelectedItem() == null || cbxMovil.getSelectedItem().toString().equals("Sin móvil"))
              {
                  jLabel1.setText("Informe de Error: No ha selecciona un chofer");
              }
              else if(cmbChofer.getSelectedItem() == null && cbxMovil.getSelectedItem() == null)
              {
                  jLabel1.setText("Informe de Error: No ha selecciona un chofer");
              }
              else
              {
                  JOptionPane.showMessageDialog(null, ex);
              }
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void cmbTipoMovilPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbTipoMovilPropertyChange
        // TODO add your handling code here:
        //this.buscarMovilesDispReservas();        
    }//GEN-LAST:event_cmbTipoMovilPropertyChange

    private void cbxMovilPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbxMovilPropertyChange
        // TODO add your handling code here:
        //this.buscarMovilesDispReservas();
    }//GEN-LAST:event_cbxMovilPropertyChange

    private void cmbChoferPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbChoferPropertyChange
        // TODO add your handling code here:
        //this.buscarMovilesDispReservas();
    }//GEN-LAST:event_cmbChoferPropertyChange

    private void buscarDispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarDispActionPerformed
        // TODO add your handling code here:
        try{
            this.cargarChoferesCombo();
            this.buscarMovilesDispReservas();
        }
        catch (Exception ex)
        {
            JOptionPane.showConfirmDialog(null, ex);
        }
                
    }//GEN-LAST:event_buscarDispActionPerformed

    private void buscarDisp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarDisp1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_buscarDisp1ActionPerformed

    private void buscarDisp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarDisp2ActionPerformed
        // TODO add your handling code here:
       JDialogActivarMoviles ventana =  new JDialogActivarMoviles( parent, modal, remiseria);
       ventana.show();
    }//GEN-LAST:event_buscarDisp2ActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogCambiarMovilReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCambiarMovilReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCambiarMovilReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCambiarMovilReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogCambiarMovilReserva dialog = new JDialogCambiarMovilReserva(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton buscarDisp;
    private javax.swing.JButton buscarDisp1;
    private javax.swing.JButton buscarDisp2;
    private javax.swing.JComboBox cbxMovil;
    private javax.swing.JComboBox cmbChofer;
    private javax.swing.JComboBox cmbTipoMovil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDirección;
    private javax.swing.JTextField txtFechaReserva;
    private javax.swing.JTextField txtHoraFinalización;
    private javax.swing.JTextField txtHoraInicio;
    // End of variables declaration//GEN-END:variables
}
