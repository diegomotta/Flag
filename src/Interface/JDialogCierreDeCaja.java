/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.*;
import static Interface.JDialogCaja.cargarTablaCaja;
import static Interface.JDialogCaja.limpiar_tabla;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author garba
 */
public class JDialogCierreDeCaja extends javax.swing.JDialog {
private  Caja unaCaja;
private Lote unLote;
private Utilidad utilidades;
private Operario unOperario;
private JRadioButton rbnEntrada;
private JRadioButton rbnSalida;
private JComboBox cbxEntrada;
private JComboBox cbxSalida;
private JTextField txtImporte;
private Maestro unMaestro;
private Double totalEntradas = 0.00;
private Double totalSalidas = 0.00;
private Remiseria remiseria;
private JLabel lbl_saldo;
private JLabel lbl_Efectivo;
private JLabel lbl_totalOUT; 
private JLabel lbl_totalIN;
private JTable tbl_caja;
private JXDatePicker dp_desde3;
private JXDatePicker dp_hasta3;

    /**
     * Creates new form JDialogCierreDeCaja
     */
    public JDialogCierreDeCaja(Remiseria remiseria,java.awt.Frame parent, boolean modal,Caja unaCaja,Lote unLote,Utilidad utilidades,Operario unOperario,Maestro unMaestro,JRadioButton rbnEntrada,JRadioButton rbnSalida,JComboBox cbxEntrada,JComboBox cbxSalida,JTextField txtImporte,JLabel lbl_saldo,JLabel lbl_Efectivo,JLabel lbl_totalOUT,JLabel lbl_totalIN, JTable tbl_caja,JXDatePicker dp_desde3,JXDatePicker dp_hasta3) {
        super(parent, modal);
        this.lbl_saldo =lbl_saldo;
        this.lbl_Efectivo =lbl_Efectivo ;
        this.lbl_totalOUT =lbl_totalOUT ; 
        this.lbl_totalIN = lbl_totalIN;
        this.tbl_caja =tbl_caja ;
        this.dp_desde3 = dp_desde3;
        this.dp_hasta3=dp_hasta3 ;        
        this.unaCaja = unaCaja;
        this.unLote = unLote;
        this.utilidades = utilidades;
        this.unOperario = unOperario;
        this.unMaestro = unMaestro;
        this.rbnEntrada = rbnEntrada;
        this.rbnSalida = rbnSalida;
        this.cbxEntrada = cbxEntrada;
        this.cbxSalida = cbxSalida;
        this.txtImporte = txtImporte;
        this.remiseria = remiseria;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarCampos(unLote);
    }
     
//     public JDialogCierreDeCaja(Remiseria remiseria,java.awt.Frame parent, boolean modal,Caja unaCaja,Lote unLote,Utilidad utilidades,Maestro unMaestro,JRadioButton rbnEntrada,JRadioButton rbnSalida,JComboBox cbxEntrada,JComboBox cbxSalida,JTextField txtImporte) {
//        super(parent, modal);
//        this.unaCaja = unaCaja;
//        this.unLote = unLote;
//        this.remiseria = remiseria;
//        this.utilidades = utilidades;
//        this.unMaestro = unMaestro;
//        this.rbnEntrada = rbnEntrada;
//        this.rbnSalida = rbnSalida;
//        this.cbxEntrada = cbxEntrada;
//        this.cbxSalida = cbxSalida;
//        this.txtImporte = txtImporte;
//
//        initComponents();
//        this.setModal(true);
//        this.setLocationRelativeTo(null);
//        this.cargarCampos(unLote);
//    }   
     
     
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

    public Lote loteVigente() {

        Lote vigente = null;
        Collection lotesAnteriores = unaCaja.getLotes().values();
        List lotesAnteriores2 = new LinkedList();
        Iterator iter = lotesAnteriores.iterator();
        Lote lote = null;
        while(iter.hasNext()){
            lote = (Lote) iter.next();
            lotesAnteriores2.add(lote);
        }
        if (!lotesAnteriores2.isEmpty()) {
            vigente = (Lote) lotesAnteriores2.get(lotesAnteriores2.size() - 1);
        }
        return vigente;
    }
    
    public static void limpiar_tabla(JTable tabla)
    {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
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
    
    private void mostrarLoteActual() {
            cargarTablaCaja(tbl_caja, listarMovimientos());
    }       
    private void cargarInformacion(Lote elLote) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
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
                        totalSalidas += Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                    }
                    else
                    {
                        totalEntradas += Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                    }
                }
            } 
             
            Double totalIN = (totalEntradas);
            Double saldo = (totalEntradas - totalSalidas);
            lbl_saldo.setText("$ "+String.valueOf(Double.parseDouble(formateador.format(saldo))));
            lbl_Efectivo.setText("$ "+String.valueOf(Double.parseDouble(formateador.format(totalEntradas))));
            lbl_totalOUT.setText("$ "+String.valueOf(Double.parseDouble(formateador.format(totalSalidas))));
            lbl_totalIN.setText("$ "+String.valueOf(Double.parseDouble(formateador.format(totalIN))));
   
        }        
    }    
    
    private List<Movimiento> listarMovimientos()
    {
        try{
            List<Movimiento> movim = new LinkedList<>();
            List<Lote> lotess = new LinkedList<>();
            Collection lotes = unaCaja.getLotes().values();
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
    
        
    private void cargarCampos(Lote elLote) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
        if (elLote != null) {
            System.out.println(elLote);
            Double entro = 0.00;
            Double salio = 0.00;
            //Double tarjetas = 0.00;
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
                        salio += Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                    }
                    else
                    {
                        entro += Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                    }
                }
            }       

           
            txt_apertura.setText(utilidades.getFecha(elLote.getFechaLote()));
            System.out.println(elLote.getFechaLote());
            txt_cierre.setText(utilidades.getFechaActual() + " - " + utilidades.getHoraActual());
            Double teorico = (entro - salio);
            txt_totalIngresos.setText(String.valueOf(Double.parseDouble(formateador.format(entro))));
            txt_totalEgresos.setText(String.valueOf(Double.parseDouble(formateador.format(salio))));
            txt_finalTeorico.setText(String.valueOf(Double.parseDouble(formateador.format(teorico))));
            txt_saldoInicial.setText(String.valueOf(elLote.getSaldoInicial()));
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

        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel1 = new javax.swing.JLabel();
        txt_apertura = new javax.swing.JTextField();
        txt_totalIngresos = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_saldoInicial = new javax.swing.JTextField();
        txt_saldoFinal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_totalRetirado = new javax.swing.JTextField();
        txt_diferencia = new javax.swing.JTextField();
        txt_finalTeorico = new javax.swing.JTextField();
        txt_totalEgresos = new javax.swing.JTextField();
        txt_cierre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

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

        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel27.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel27.setText("Cierre de caja");

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

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Apertura:");

        txt_apertura.setEditable(false);
        txt_apertura.setBackground(new java.awt.Color(204, 204, 204));
        txt_apertura.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_apertura.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txt_apertura.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_apertura.setEnabled(false);

        txt_totalIngresos.setEditable(false);
        txt_totalIngresos.setBackground(new java.awt.Color(204, 204, 204));
        txt_totalIngresos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_totalIngresos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txt_totalIngresos.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_totalIngresos.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel8.setText("Total Ingresos:");

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("Saldo Inicial:");

        txt_saldoInicial.setEditable(false);
        txt_saldoInicial.setBackground(new java.awt.Color(204, 204, 204));
        txt_saldoInicial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_saldoInicial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txt_saldoInicial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_saldoInicial.setEnabled(false);

        txt_saldoFinal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_saldoFinal.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_saldoFinalCaretUpdate(evt);
            }
        });
        txt_saldoFinal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_saldoFinalMouseClicked(evt);
            }
        });
        txt_saldoFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_saldoFinalKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel4.setText("Saldo Final:");

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel6.setText("Total Retirado:");

        txt_totalRetirado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_totalRetirado.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txt_totalRetiradoCaretUpdate(evt);
            }
        });
        txt_totalRetirado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_totalRetiradoMouseClicked(evt);
            }
        });
        txt_totalRetirado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_totalRetiradoFocusLost(evt);
            }
        });
        txt_totalRetirado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_totalRetiradoKeyTyped(evt);
            }
        });

        txt_diferencia.setEditable(false);
        txt_diferencia.setBackground(new java.awt.Color(204, 204, 204));
        txt_diferencia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_diferencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txt_diferencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_diferencia.setEnabled(false);

        txt_finalTeorico.setEditable(false);
        txt_finalTeorico.setBackground(new java.awt.Color(204, 204, 204));
        txt_finalTeorico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_finalTeorico.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txt_finalTeorico.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_finalTeorico.setEnabled(false);

        txt_totalEgresos.setEditable(false);
        txt_totalEgresos.setBackground(new java.awt.Color(204, 204, 204));
        txt_totalEgresos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_totalEgresos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txt_totalEgresos.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_totalEgresos.setEnabled(false);

        txt_cierre.setEditable(false);
        txt_cierre.setBackground(new java.awt.Color(204, 204, 204));
        txt_cierre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt_cierre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txt_cierre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_cierre.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Cierre:");

        jLabel10.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel10.setText("Total Egresos:");

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel9.setText("Final Teórico:");

        jLabel5.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel5.setText("Diferencia:");

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jLabel4))
                                .addComponent(jLabel3))
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_totalRetirado, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_saldoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_saldoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_apertura, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(txt_totalIngresos))))
                .addGap(18, 18, 18)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_finalTeorico)
                            .addComponent(txt_diferencia, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_cierre, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(txt_totalEgresos))))
                .addGap(21, 21, 21))
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_apertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_totalIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txt_totalEgresos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_saldoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_finalTeorico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_saldoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txt_diferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_totalRetirado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_totalRetiradoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_totalRetiradoCaretUpdate
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
        if (txt_saldoFinal.getText().isEmpty()) {
            txt_diferencia.setText("");
        } else {
            if (txt_totalRetirado.getText().isEmpty()) {
                Double diferencia = (Double.parseDouble(txt_saldoFinal.getText()) - Double.parseDouble(txt_finalTeorico.getText()));
                txt_diferencia.setText(String.valueOf(Double.parseDouble(formateador.format(diferencia))));
            } else {
                Double diferencia = (Double.parseDouble(txt_saldoFinal.getText()) - Double.parseDouble(txt_totalRetirado.getText()) );//- Double.parseDouble(txt_finalTeorico.getText()));
                txt_diferencia.setText(String.valueOf(Double.parseDouble(formateador.format(diferencia))));
            }
        }

    }//GEN-LAST:event_txt_totalRetiradoCaretUpdate

    private void txt_totalRetiradoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_totalRetiradoFocusLost
//        if (!txt_totalRetirado.getText().isEmpty()) {
//            if (Double.parseDouble(txt_diferencia.getText()) != 0.0) {
//                JOptionPane.showMessageDialog(this, "La suma entre el total retirado y el saldo final no puede ser distinto al final Teorico");
//            }
//
//        }
    }//GEN-LAST:event_txt_totalRetiradoFocusLost

    private void txt_totalRetiradoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_totalRetiradoKeyTyped
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
        int limite  = 12;
        {if ( txt_totalRetirado.getText().length()== limite)
            evt.consume();
        }              
    }//GEN-LAST:event_txt_totalRetiradoKeyTyped

    private void txt_saldoFinalCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_saldoFinalCaretUpdate

   }//GEN-LAST:event_txt_saldoFinalCaretUpdate

    private void txt_saldoFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_saldoFinalKeyTyped
        int limite  = 12;
        {if (txt_saldoFinal.getText().length()== limite)
            evt.consume();
        }  
   }//GEN-LAST:event_txt_saldoFinalKeyTyped

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
     try {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
            if(unOperario != null)
            {
            String elUsuario = unOperario.getNombre()+" " + unOperario.getApellido();
            Date diaLote = unLote.getFechaLote();
            Integer numeroLote = unLote.getIdLote();
            String horaCierre;
            Calendar fechaActual = Calendar.getInstance();
            Calendar diaDelLote = Calendar.getInstance();
            diaDelLote.setTime(unLote.getFechaLote());
            if (fechaActual.get(Calendar.DAY_OF_YEAR) == diaDelLote.get(Calendar.DAY_OF_YEAR)) {
                horaCierre = txt_cierre.getText();
            } else {
                horaCierre = "23:59:59";
            }

            Double totalRetirado = Double.parseDouble(txt_totalRetirado.getText());
            Double saldoFinal = Double.parseDouble(txt_saldoFinal.getText());    
            Double teorico =    Double.parseDouble(txt_finalTeorico.getText());
//            if(Double.parseDouble(txt_diferencia.getText())+ totalRetirado ==teorico)
//            {
                if (teorico > saldoFinal) 
                { 
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), teorico-saldoFinal, "Egreso",  elUsuario + "- Faltante de caja", "Efectivo");
                    JOptionPane.showMessageDialog(null,"El saldo teórico es mayor al saldo final");
                    if(totalRetirado != 0.0)
                    {
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), totalRetirado, "Egreso",  elUsuario + "- Retiro de caja", "Efectivo");
                    }
               } 
               else if (teorico < saldoFinal) 
               {
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), saldoFinal-teorico, "Ingreso",  elUsuario + "- Ingreso a caja", "Efectivo");
                    if(totalRetirado != 0.0)
                    {
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), totalRetirado, "Egreso",  elUsuario + "- Retiro de caja", "Efectivo");
                    }  
               }
                else if(String.valueOf(teorico).equals(String.valueOf(saldoFinal)))
                {      
                    if(totalRetirado != 0.0)
                    {
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), totalRetirado, "Egreso",  elUsuario + "- Retiro de caja", "Efectivo");
                    }
                }
                rbnEntrada.setEnabled(false);
                rbnSalida.setEnabled(false);
                cbxEntrada.setEnabled(false);
                cbxSalida.setEnabled(false);
                txtImporte.setEnabled(false);
                cargarInformacion(unaCaja.obtenerLote_Vigente());
                mostrarLoteActual(); 
                unaCaja.cerrarLote(saldoFinal, horaCierre, totalRetirado, unLote);       

                try
                    {   List <Movimiento> listaMovimientos = new LinkedList();
                        Collection movi = unLote.getMovimientos().values();
                        Iterator iter = movi.iterator();
                        while(iter.hasNext())
                        {
                            listaMovimientos.add((Movimiento) iter.next());
                        }
                        for (Movimiento unMovimiento : listaMovimientos) {
                          if (unMovimiento.getFuente().equals("Efectivo")) {
                                if ("Egreso".equals(unMovimiento.getTipo())) {
                                    totalSalidas +=  Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                                } else {
                                    totalEntradas +=  Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                                }
                            }

                        }

                        Double totalRetiradoo = Double.parseDouble(txt_totalRetirado.getText());
                        Double queda = (Double.parseDouble(txt_saldoFinal.getText())-Double.parseDouble(txt_totalRetirado.getText()));   
                        //Double queda = Double.parseDouble(txt_saldoFinal.getText());                     
                        HashMap<String, Object> parametros = new HashMap();
                        parametros.clear();
                        parametros.put("nombreEmpresa",remiseria.getNombre());
                        parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                        parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                        parametros.put("fechaActual", utilidades.getFechaActual());
                        parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                        parametros.put("totalEntradas", Double.parseDouble(formateador.format(totalEntradas)));
                        parametros.put("totalSalidas", Double.parseDouble(formateador.format(totalSalidas)));
                        parametros.put("final", queda);
                        parametros.put("retirado", totalRetiradoo);
                        parametros.put("dia", diaLote);
                        parametros.put("numero", numeroLote);
                        parametros.put("us", elUsuario);
                        parametros.put("logo", remiseria.getLogo());

                       JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/CierreCaja.jrxml");
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JRBeanCollectionDataSource(listaMovimientos));
                        JasperViewer jviewer = new JasperViewer(jasperPrint, false);
                        JDialog frame;
                        frame = new JDialog();
                        frame.getContentPane().removeAll();
                        frame.getContentPane().add(jviewer.getContentPane());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setSize(1100, 600);
                        frame.setModal(true);
                        frame.setLocationRelativeTo(null);      
                        frame.show();
                        this.dispose();
                }
                catch (JRException ex) 
                {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }  
        }
        else if( unMaestro != null)
        {
            String elUsuario = unMaestro.getNombre()+" " + unMaestro.getApellido();
            Date diaLote = unLote.getFechaLote();
            Integer numeroLote = unLote.getIdLote();
            String horaCierre;
            Calendar fechaActual = Calendar.getInstance();
            Calendar diaDelLote = Calendar.getInstance();
            diaDelLote.setTime(unLote.getFechaLote());
            if (fechaActual.get(Calendar.DAY_OF_YEAR) == diaDelLote.get(Calendar.DAY_OF_YEAR)) {
                horaCierre = txt_cierre.getText();
            } else {
                horaCierre = "23:59:59";
            }

            Double totalRetirado = Double.parseDouble(txt_totalRetirado.getText());
            Double saldoFinal = Double.parseDouble(txt_saldoFinal.getText());    
            Double teorico =    Double.parseDouble(txt_finalTeorico.getText());
//            if(Double.parseDouble(txt_diferencia.getText())+ totalRetirado ==teorico)
//            {
                if (teorico > saldoFinal) 
                { 
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), teorico-saldoFinal, "Egreso",  elUsuario + "- Faltante de caja", "Efectivo");
                    JOptionPane.showMessageDialog(null,"El saldo teórico es mayor al saldo final");
                    if(totalRetirado != 0.0)
                    {
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), totalRetirado, "Egreso",  elUsuario + "- Retiro de caja", "Efectivo");
                    }
               } 
               else if (teorico < saldoFinal) 
               {
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), saldoFinal-teorico, "Ingreso",  elUsuario + "- Ingreso a caja", "Efectivo");
                    if(totalRetirado != 0.0)
                    {
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), totalRetirado, "Egreso",  elUsuario + "- Retiro de caja", "Efectivo");
                    }  
               }
                else if(String.valueOf(teorico).equals(String.valueOf(saldoFinal)))
                {      
                    if(totalRetirado != 0.0)
                    {
                    unLote.altaMovimiento(diaLote, utilidades.getHoraActual(), totalRetirado, "Egreso",  elUsuario + "- Retiro de caja", "Efectivo");
                    }
                }
                rbnEntrada.setEnabled(false);
                rbnSalida.setEnabled(false);
                cbxEntrada.setEnabled(false);
                cbxSalida.setEnabled(false);
                txtImporte.setEnabled(false);
                cargarInformacion(unaCaja.obtenerLote_Vigente());
                mostrarLoteActual(); 
                unaCaja.cerrarLote(saldoFinal, horaCierre, totalRetirado, unLote);       

                try
                    {   List <Movimiento> listaMovimientos = new LinkedList();
                        Collection movi = unLote.getMovimientos().values();
                        Iterator iter = movi.iterator();
                        while(iter.hasNext())
                        {
                            listaMovimientos.add((Movimiento) iter.next());
                        }
                        for (Movimiento unMovimiento : listaMovimientos) {
                          if (unMovimiento.getFuente().equals("Efectivo")) {
                                if ("Egreso".equals(unMovimiento.getTipo())) {
                                    totalSalidas +=  Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                                } else {
                                    totalEntradas +=  Double.parseDouble(formateador.format(unMovimiento.getImporte()));
                                }
                            }

                        }

                        Double totalRetiradoo = Double.parseDouble(txt_totalRetirado.getText());
                        Double queda = Double.parseDouble(txt_saldoFinal.getText())-Double.parseDouble(txt_totalRetirado.getText());   
                        //Double queda = Double.parseDouble(txt_saldoFinal.getText());    
                        HashMap<String, Object> parametros = new HashMap();
                        parametros.clear();
                        parametros.put("nombreEmpresa",remiseria.getNombre());
                        parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa());
                        parametros.put("telefonoEmpresa",remiseria.getUnTelefono().toString());
                        parametros.put("fechaActual", utilidades.getFechaActual());
                        parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                        parametros.put("totalEntradas", Double.parseDouble(formateador.format(totalEntradas)));
                        parametros.put("totalSalidas", Double.parseDouble(formateador.format(totalSalidas)));
                        parametros.put("final", queda);
                        parametros.put("retirado", totalRetiradoo);
                        parametros.put("dia", diaLote);
                        parametros.put("numero", numeroLote);
                        parametros.put("us", elUsuario);
                        parametros.put("logo", remiseria.getLogo());

                        JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/CierreCaja.jrxml");
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JRBeanCollectionDataSource(listaMovimientos));
                        JasperViewer jviewer = new JasperViewer(jasperPrint, false);
                        JDialog frame;
                        frame = new JDialog();
                        frame.getContentPane().removeAll();
                        frame.getContentPane().add(jviewer.getContentPane());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setSize(1100, 600);
                        frame.setModal(true);
                        frame.setLocationRelativeTo(null);      
                        frame.show();
                        this.dispose();
                }
                catch (JRException ex) 
                {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }  
//            }
//            else
//            {
//               JOptionPane.showMessageDialog(this, "La suma entre el total retirado y la diferecnia del saldo final no puede ser distinto al final Teorico");
//            }

        }    
     }
     catch (Exception ex) 
     {   
                if(txt_saldoFinal.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "No ha ingresado el Saldo Final", null, JOptionPane.ERROR_MESSAGE);
                    txt_saldoFinal.setBackground(java.awt.Color.red);
                }
                if(txt_totalRetirado.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "No ha ingresado el Total Retirado", null, JOptionPane.ERROR_MESSAGE);
                    txt_totalRetirado.setBackground(java.awt.Color.red);
                }

            }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void txt_saldoFinalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_saldoFinalMouseClicked
        // TODO add your handling code here:
        txt_saldoFinal.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txt_saldoFinalMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txt_totalRetiradoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_totalRetiradoMouseClicked
        // TODO add your handling code here:
        txt_totalRetirado.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_txt_totalRetiradoMouseClicked

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
            java.util.logging.Logger.getLogger(JDialogCierreDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCierreDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCierreDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCierreDeCaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogCierreDeCaja dialog = new JDialogCierreDeCaja(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel25;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JTextField txt_apertura;
    private javax.swing.JTextField txt_cierre;
    private javax.swing.JTextField txt_diferencia;
    private javax.swing.JTextField txt_finalTeorico;
    private javax.swing.JTextField txt_saldoFinal;
    private javax.swing.JTextField txt_saldoInicial;
    private javax.swing.JTextField txt_totalEgresos;
    private javax.swing.JTextField txt_totalIngresos;
    private javax.swing.JTextField txt_totalRetirado;
    // End of variables declaration//GEN-END:variables
}
