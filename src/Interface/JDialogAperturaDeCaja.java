/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.*;
import static Interface.JDialogCaja.limpiar_tabla;
import static Interface.JDialogCierreDeCaja.cargarTablaCaja;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author garba
 */
public class JDialogAperturaDeCaja extends javax.swing.JDialog {
private Caja unaCajaPrincipal;
private Utilidad utilidades;
private JLabel lbl_saldo;
private JLabel lbl_Efectivo;
private JLabel lbl_totalOUT;
private JLabel lbl_totalIN;
private JButton btnIniciarTurno;
private JButton jButton11;
private JTable tbl_caja;
private  JXDatePicker dp_desde3;
private  JXDatePicker dp_hasta3;
    /**
     * Creates new form JDialogApertunaDeCaja
     */
    public JDialogAperturaDeCaja(java.awt.Frame parent, boolean modal, Remiseria remiseria, Utilidad utilidades, Caja unaCajaPrincipal,JLabel lbl_saldo,JLabel lbl_Efectivo, JLabel lbl_totalOUT, JLabel lbl_totalIN, JButton btnIniciarTurno,JButton jButton11,JTable tbl_caja ,JXDatePicker dp_desde3,JXDatePicker dp_hasta3) {
        super(parent, modal);
        this.unaCajaPrincipal = unaCajaPrincipal;
        this.utilidades = utilidades;
        this.btnIniciarTurno = btnIniciarTurno;
        this.lbl_saldo = lbl_saldo;
        this.lbl_Efectivo = lbl_Efectivo;
        this.lbl_totalIN = lbl_totalIN;
        this.lbl_totalOUT = lbl_totalOUT;
        this.jButton11 = jButton11;
        this.tbl_caja = tbl_caja;
        this.dp_desde3 = dp_desde3;
        this.dp_hasta3 =dp_hasta3;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        cargarCampos();
    }

    private void cargarCampos() {
        txtHorayFechaDeCierre.setText(utilidades.getFechaActual() + " - " + utilidades.getHoraActual());

        Date hoy = new Date();

        Calendar mañana = Calendar.getInstance();
        mañana.add(Calendar.DAY_OF_MONTH, 1);

        Calendar ayer = Calendar.getInstance();
        ayer.add(Calendar.DAY_OF_MONTH, -2);

        boolean anterior = false;
        Double saldoAnterior = 0.00;

        Collection lotesCaja = unaCajaPrincipal.getLotes().values();

        Lote loteAnterior = unaCajaPrincipal.obtenerLote_Vigente();

        if (loteAnterior != null) {
            saldoAnterior = loteAnterior.getSaldoFinal();
            System.out.println(saldoAnterior);
            anterior = true;
        }


        if (!anterior) {
            txtSaldoAnterior.setText("0.00");
        } else {
            if (anterior) {
                txtSaldoAnterior.setText(String.valueOf(saldoAnterior));
            }

        }

    }
    
    
     private void cargarInformacion(Lote elLote) {

        double totalSalidas = 0.00;
        double totalEntradas = 0.00;
        
        if (elLote != null) {
             Collection movim = elLote.getMovimientos().values();
            //List movimientosDelLote = new LinkedList();
            Movimiento unMovimiento = null;
            Iterator iter = movim.iterator();
            while (iter.hasNext()){
              unMovimiento =(Movimiento) iter.next();
              //if (unMovimiento.getFuente().equals("tarjeta")) {
                //    tarjetas += unMovimiento.getImporte();
               /* } else*/ if (unMovimiento.getFuente().equals("Efectivo")) {
                    if ("Egreso".equals(unMovimiento.getTipo())) {
                        totalSalidas += unMovimiento.getImporte();
                    }
                    else
                    {
                        totalEntradas += unMovimiento.getImporte();
                    }
                }
            } 
            
            
            Double totalIN = (totalEntradas);
            Double saldo = (totalEntradas - totalSalidas);
            if(lbl_saldo!= null && lbl_Efectivo!= null && lbl_totalOUT != null && lbl_totalIN!= null && tbl_caja!= null && dp_desde3!= null &&dp_hasta3 != null)
            {
            
                lbl_saldo.setText(String.valueOf(saldo));
                lbl_Efectivo.setText(String.valueOf(totalEntradas));
                lbl_totalOUT.setText(String.valueOf(totalSalidas));
                lbl_totalIN.setText(String.valueOf(totalIN));
                cargarTablaCaja(tbl_caja, listarMovimientos());
            }
            
            
        }
    }

    public static void cargarTablaCaja(JTable tabla, List<Movimiento> lista) 
    {
        if(lista != null)
        {
            limpiar_tabla(tabla);
            for (Movimiento unMovimiento : lista) {

                ((DefaultTableModel) tabla.getModel()).addRow((unMovimiento).toVector());
            }
        }
    }     
     
    public static void limpiar_tabla(JTable tabla)
    {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }    
     
    private List<Movimiento> listarMovimientos()
    {
        try{
            List<Movimiento> movim = new LinkedList<>();
            List<Lote> lotess = new LinkedList<>();
            Collection lotes = unaCajaPrincipal.getLotes().values();
            Iterator iter = lotes.iterator();
            Lote lo = null;
            while(iter.hasNext())
            {
                lo = (Lote) iter.next();
                lotess.add(lo);
            }

            for (Lote l : lotess) {
                if ((l.getFechaLote().getTime() == dp_desde3.getDate().getTime()) || (l.getFechaLote().getTime() > dp_desde3.getDate().getTime())) {
                    if ((l.getFechaLote().getTime() == dp_hasta3.getDate().getTime()) || (l.getFechaLote().getTime() < dp_hasta3.getDate().getTime())) {
                        Collection movimientos = l.getMovimientos().values();
                        Iterator iter2 = movimientos.iterator();
                        Movimiento m = null;
                        while(iter2.hasNext()){
                            m = (Movimiento)iter2.next();
                            movim.add(m);

                        }
                    }
                }
            }
            return movim;
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        return null;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtHorayFechaDeCierre = new javax.swing.JTextField();
        txtSaldoAnterior = new javax.swing.JTextField();
        txtSaldoInicial = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("Total inicial:");

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Total cierre anterior:");

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Fecha y Hora de cierre:");

        txtHorayFechaDeCierre.setEditable(false);
        txtHorayFechaDeCierre.setBackground(new java.awt.Color(204, 204, 204));
        txtHorayFechaDeCierre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtHorayFechaDeCierre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtSaldoAnterior.setEditable(false);
        txtSaldoAnterior.setBackground(new java.awt.Color(204, 204, 204));
        txtSaldoAnterior.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSaldoAnterior.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtSaldoInicial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSaldoInicial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSaldoInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSaldoInicialMouseClicked(evt);
            }
        });
        txtSaldoInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSaldoInicialKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHorayFechaDeCierre)
                    .addComponent(txtSaldoAnterior)
                    .addComponent(txtSaldoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtHorayFechaDeCierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSaldoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaldoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 19, Short.MAX_VALUE))
        );

        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel27.setText("Apertura de caja");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSaldoInicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoInicialKeyTyped
        // TODO add your handling code here:
        int limite  = 12;
        {if (txtSaldoInicial.getText().length()== limite)
            evt.consume();
        }   
    }//GEN-LAST:event_txtSaldoInicialKeyTyped

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        try {
            if(utilidades.isDouble(txtSaldoInicial.getText()))
            {
                Double saldoInicial = Double.parseDouble(txtSaldoInicial.getText());
                Double saldoFinal = 0.00;
                String horaApertura = utilidades.getHoraActual();
                String horaCierre = "";
                Double totalRetirado = 0.00;
                String elConcepto = null;
                //        Date fechaLote = new Date();
                Concepto unConcepto = null;
                Collection conceptoss = unaCajaPrincipal.getConceptos().values();
                Iterator iter = conceptoss.iterator();
                while(iter.hasNext())
                {
                    unConcepto = (Concepto) iter.next();
                    if (unConcepto.getDetalle().equals("Inicio de Caja")) {
                        elConcepto = unConcepto.getDetalle();
                    }
                }

                //Integer comprobante = unaCaja.obtenerNC();

                if (unaCajaPrincipal.obtenerLote_Vigente() != null)
                {
                    Double finalAnterior = Double.parseDouble(txtSaldoAnterior.getText());
                    if (saldoInicial.equals(finalAnterior)) 
                    {
                        Calendar hoy = Calendar.getInstance();
                        Calendar fechaLote = Calendar.getInstance();
                        fechaLote.setTime(unaCajaPrincipal.obtenerLote_Vigente().getFechaLote());
                        if (fechaLote.get(Calendar.DAY_OF_YEAR) != hoy.get(Calendar.DAY_OF_YEAR)) 
                        {
                            Lote nuevoLote = unaCajaPrincipal.abrirLote(saldoInicial, saldoFinal, horaApertura, horaCierre, totalRetirado/*, fechaLote*/);
                            nuevoLote.altaMovimiento(nuevoLote.getFechaLote(), nuevoLote.getHoraApertura(), nuevoLote.getSaldoInicial(), "Ingreso", elConcepto, "Efectivo");
                            if(btnIniciarTurno!= null)
                            {
                                btnIniciarTurno.setEnabled(true);
                                jButton11.setEnabled(false);
                            }
                            this.cargarInformacion(nuevoLote);
                            this.dispose();
                            // unaCaja.ADD_Compeobante(comprobante);
                         //   Acceso.getUnaHosteria().agregarComprobante(comprobante);              
                        } 
                        else
                        {
                            JOptionPane.showMessageDialog(this, "No se puede abrir mas de un Lote en el mismo dia");
                        }
                    }
                    else
                    {
                        if (saldoInicial > finalAnterior)
                        {
                            Calendar hoy = Calendar.getInstance();
                            Calendar fechaLote = Calendar.getInstance();
                            fechaLote.setTime(unaCajaPrincipal.obtenerLote_Vigente().getFechaLote());
                            if (fechaLote.get(Calendar.DAY_OF_YEAR) != hoy.get(Calendar.DAY_OF_YEAR))
                            {
                                Lote nuevoLote = unaCajaPrincipal.abrirLote(finalAnterior, saldoFinal, horaApertura, horaCierre, totalRetirado/*, fechaLote*/);
                                nuevoLote.altaMovimiento(nuevoLote.getFechaLote(), nuevoLote.getHoraApertura(), nuevoLote.getSaldoInicial(), "Ingreso", elConcepto, "Efectivo");
                               // unaCajaPrincipal.ADD_Compeobante(comprobante);
                                nuevoLote.altaMovimiento(nuevoLote.getFechaLote(), utilidades.getHoraActual(), (saldoInicial - finalAnterior), "Ingreso", "Ajuste de Inicio de caja respecto al cierre anterior", "Efectivo");
                               // unaCaja.ADD_Compeobante((comprobante + 1));
                                if(btnIniciarTurno!= null)
                                {
                                    btnIniciarTurno.setEnabled(true);
                                    jButton11.setEnabled(false);
                                }
                                this.cargarInformacion(nuevoLote);
                                this.dispose();
                            } 
                            else
                            {
                                JOptionPane.showMessageDialog(this, "No se puede abrir mas de un Lote en el mismo dia");
                            }
                        }
                        else 
                        {
                            Calendar hoy = Calendar.getInstance();
                            Calendar fechaLote = Calendar.getInstance();
                            fechaLote.setTime(unaCajaPrincipal.obtenerLote_Vigente().getFechaLote());
                            if (fechaLote.get(Calendar.DAY_OF_YEAR) != hoy.get(Calendar.DAY_OF_YEAR)) {
                                Lote nuevoLote = unaCajaPrincipal.abrirLote(finalAnterior, saldoFinal, horaApertura, horaCierre, totalRetirado/*, fechaLote*/);
                                nuevoLote.altaMovimiento(nuevoLote.getFechaLote(), nuevoLote.getHoraApertura(), nuevoLote.getSaldoInicial(), "Ingreso", elConcepto, "Efectivo");
                                //unaCaja.ADD_Compeobante(comprobante);
                                nuevoLote.altaMovimiento(nuevoLote.getFechaLote(), utilidades.getHoraActual(), (finalAnterior - saldoInicial), "Egreso", "Ajuste de Inicio de caja respecto al cierre anterior", "Efectivo");
                            //    unaCaja.ADD_Compeobante((comprobante + 1));
                               if(btnIniciarTurno!= null)
                                {
                                    btnIniciarTurno.setEnabled(true);
                                    jButton11.setEnabled(false);
                                }       
                                this.cargarInformacion(nuevoLote);
                                this.dispose();
                            } 
                            else
                            {
                                JOptionPane.showMessageDialog(this, "No se puede abrir mas de un Lote en el mismo dia");
                            }
                        }
                    }

                }
                else 
                {
                    Lote nuevoLote = unaCajaPrincipal.abrirLote(saldoInicial, saldoFinal, horaApertura, horaCierre, totalRetirado/*, fechaLote*/);
                    nuevoLote.altaMovimiento(nuevoLote.getFechaLote(), nuevoLote.getHoraApertura(), nuevoLote.getSaldoInicial(), "Ingreso", elConcepto, "Efectivo");
                   // unaCaja.ADD_Compeobante(comprobante);
                    this.cargarInformacion(nuevoLote);
                   if(btnIniciarTurno!= null)
                    {
                        btnIniciarTurno.setEnabled(true);
                        jButton11.setEnabled(false);
                    }
                    this.dispose();
                }
            }
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, "No ha ingresado el Saldo Inicial para realizar la Apertura de Caja", null, JOptionPane.ERROR_MESSAGE);
            txtSaldoInicial.setBackground(java.awt.Color.red);
        }

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtSaldoInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSaldoInicialMouseClicked
        // TODO add your handling code here:
        txtSaldoInicial.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txtSaldoInicialMouseClicked

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
            java.util.logging.Logger.getLogger(JDialogAperturaDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogAperturaDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogAperturaDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogAperturaDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogAperturaDeCaja dialog = new JDialogAperturaDeCaja(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel25;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTextField txtHorayFechaDeCierre;
    private javax.swing.JTextField txtSaldoAnterior;
    private javax.swing.JTextField txtSaldoInicial;
    // End of variables declaration//GEN-END:variables
}
