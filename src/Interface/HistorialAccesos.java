/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Clases.ImprimirAccesos;
import Clases.Maestro;
import Clases.MarcarTarjeta;
import Clases.Operario;
import Clases.Remiseria;
import Clases.Utilidad;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
public class HistorialAccesos extends javax.swing.JDialog {
private Maestro unMaestro;
private Operario unOperario;
private Remiseria remiseria;
private Utilidad utilidades = new Utilidad();
private LinkedList<ImprimirAccesos> imprimir = new LinkedList<>();
    /**
     * Creates new form HistorialAccesos
     */
    public HistorialAccesos(java.awt.Frame parent, boolean modal,Remiseria remiseria, Maestro unMaestro , Operario unOperario) {
        super(parent, modal);
        this.remiseria = remiseria;
        this.unMaestro = unMaestro;
        this.unOperario = unOperario;
        initComponents();
                this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarOperariosCombo();
        fechaDeterminado.setDate(utilidades.getFechaHoraActual());
        chekPeriodo.setSelected(true);
        dp_desde.setEnabled(true);
        dp_hasta.setEnabled(true);
        fechaDeterminado.setEnabled(false);  
        cmbOperarios.setEnabled(false);
        chekPeriodo1.setSelected(false);
        hasta();
    }
    
 public  void limpiar_tabla(JTable tabla) {
        int filas = tabla.getRowCount();
        for (int i = 0; i < filas; i++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }
    }    
    
    public List<MarcarTarjeta> filtrarTodo()
    {
        try
        {
            imprimir.clear();          
            List<MarcarTarjeta> l = new LinkedList<>();
            if(remiseria.getMarcados()!= null)
            {        
                l.clear();
                Collection accesos = remiseria.getMarcados().values();
                Iterator iter = accesos.iterator();
                MarcarTarjeta tarjeta = null;
                if(chekPeriodo1.isSelected()==false)
                {
                    while (iter.hasNext())
                    {
                        tarjeta = (MarcarTarjeta) iter.next();
                        Date fec = utilidades.getDate(utilidades.getFecha(tarjeta.getFechaInicio()));               
                        if(dp_desde.isEnabled()==true && dp_hasta.isEnabled()==true)
                        {
                            if ((fec.getTime() == dp_desde.getDate().getTime()) || (fec.getTime() > dp_desde.getDate().getTime())) 
                            {
                                if ((fec.getTime() == dp_hasta.getDate().getTime()) || (fec.getTime() < dp_hasta.getDate().getTime())) 
                                {      
                                    if(tarjeta.getFechaFin()!=null)
                                    {
                                        if(tarjeta.getUnOperario()!= null)
                                        {
                                            l.add(tarjeta);
                                            imprimir.add(new ImprimirAccesos (tarjeta.getUnOperario().toString(),utilidades.getFecha(tarjeta.getFechaInicio())+"-"+utilidades.getHora(tarjeta.getFechaInicio()),utilidades.getFecha(tarjeta.getFechaFin())+"-"+utilidades.getHora(tarjeta.getFechaFin()),utilidades.getHora(tarjeta.getTotal())));
                                        }   
                                        else if(tarjeta.getUnMaestro()!= null)
                                        {
                                             l.add(tarjeta);
                                             imprimir.add(new ImprimirAccesos (tarjeta.getUnMaestro().toString(),utilidades.getFecha(tarjeta.getFechaInicio())+"-"+utilidades.getHora(tarjeta.getFechaInicio()),utilidades.getFecha(tarjeta.getFechaFin())+"-"+utilidades.getHora(tarjeta.getFechaFin()),utilidades.getHora(tarjeta.getTotal())));
                                        }    
                                    }
                                } 
                            }
                        }
                        else if(fechaDeterminado.isEnabled()==true)
                        {
                            if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                            {
                                    if(tarjeta.getFechaFin()!=null)
                                    {
                                        if(tarjeta.getUnOperario()!= null)
                                        {
                                            l.add(tarjeta);
                                            imprimir.add(new ImprimirAccesos (tarjeta.getUnOperario().toString(),utilidades.getFecha(tarjeta.getFechaInicio())+"-"+utilidades.getHora(tarjeta.getFechaInicio()),utilidades.getFecha(tarjeta.getFechaFin())+"-"+utilidades.getHora(tarjeta.getFechaFin()),utilidades.getHora(tarjeta.getTotal())));
                                        }   
                                        else if(tarjeta.getUnMaestro()!= null)
                                        {
                                             l.add(tarjeta);
                                             imprimir.add(new ImprimirAccesos (tarjeta.getUnMaestro().toString(),utilidades.getFecha(tarjeta.getFechaInicio())+"-"+utilidades.getHora(tarjeta.getFechaInicio()),utilidades.getFecha(tarjeta.getFechaFin())+"-"+utilidades.getHora(tarjeta.getFechaFin()),utilidades.getHora(tarjeta.getTotal())));
                                        }
                                    }
                            }
                        }    
                    }
                }
                else if(chekPeriodo1.isSelected()==true)
                {
                    if(cmbOperarios.getSelectedItem()!= null)
                    {
                        Operario unOper = (Operario) cmbOperarios.getSelectedItem();
                        Operario u = remiseria.buscarOperario(unOper.getDni());
                        
                        while (iter.hasNext())
                        {
                            tarjeta = (MarcarTarjeta) iter.next();
                            Date fec = utilidades.getDate(utilidades.getFecha(tarjeta.getFechaInicio()));               
                            if(dp_desde.isEnabled()==true && dp_hasta.isEnabled()==true)
                            {
                                if(tarjeta.getUnOperario()!= null)
                                {
                                    if(tarjeta.getUnOperario().equals(u))
                                    {
                                        if ((fec.getTime() == dp_desde.getDate().getTime()) || (fec.getTime() > dp_desde.getDate().getTime())) 
                                        {
                                            if ((fec.getTime() == dp_hasta.getDate().getTime()) || (fec.getTime() < dp_hasta.getDate().getTime())) 
                                            {    
                                                if(tarjeta.getFechaFin()!=null)
                                                {
                                                    if(tarjeta.getUnOperario()!= null)
                                                    {
                                                        l.add(tarjeta);
                                                        imprimir.add(new ImprimirAccesos (tarjeta.getUnOperario().toString(),utilidades.getFecha(tarjeta.getFechaInicio())+"-"+utilidades.getHora(tarjeta.getFechaInicio()),utilidades.getFecha(tarjeta.getFechaFin())+"-"+utilidades.getHora(tarjeta.getFechaFin()),utilidades.getHora(tarjeta.getTotal())));
                                                    }
                                                }
                                            }    
                                        }
                                    }
                                }
                            }
                            else if(fechaDeterminado.isEnabled()==true)
                            {
                                
                                if ((fec.getTime() == fechaDeterminado.getDate().getTime())) 
                                {
                                    if(tarjeta.getUnOperario()!= null)
                                    {                                    
                                        if(tarjeta.getUnOperario().equals(u))
                                        {
                                            if(tarjeta.getFechaFin()!=null)
                                            {
                                                    l.add(tarjeta);
                                                    imprimir.add(new ImprimirAccesos (tarjeta.getUnOperario().toString(),utilidades.getFecha(tarjeta.getFechaInicio())+"-"+utilidades.getHora(tarjeta.getFechaInicio()),utilidades.getFecha(tarjeta.getFechaFin())+"-"+utilidades.getHora(tarjeta.getFechaFin()),utilidades.getHora(tarjeta.getTotal())));
                                            }
                                        }
                                    }
                                }
                            }    
                        }                    
                    }
                }
            }
            return l;
        
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;
    }

     

    
    
    public  void cargarTablaAuditoria(JTable tabla, List<MarcarTarjeta> lista) {
        try
        {
        limpiar_tabla(tabla);
        Object[] filas = new Object[4];
        Collections.sort(lista, new Comparator<MarcarTarjeta>() {
                @Override
                public int compare(MarcarTarjeta p1, MarcarTarjeta p2) {                
                        return new Long(p1.getFechaInicio().getTime()).compareTo(new Long(p2.getFechaInicio().getTime()));
                }
        });        
       // for (Entidad_Revisada lala : lista) {
        for (MarcarTarjeta lala : lista) {    
           // filas[0] = String.valueOf(lala.getId());//getCustomTimestamp());
            if(lala.getUnOperario()!= null)
            {
            filas[0] = lala.getUnOperario().toString();
            }
            else if (lala.getUnMaestro()!= null)
            {
                filas[0] = lala.getUnMaestro().toString();
            }
            filas[1] = utilidades.getFecha(lala.getFechaInicio())+"-"+ utilidades.getHora(lala.getFechaInicio());
            filas[2] = utilidades.getFecha(lala.getFechaFin())+"-"+ utilidades.getHora(lala.getFechaFin());
            filas[3] = utilidades.getHora(lala.getTotal());
            ((DefaultTableModel) tabla.getModel()).addRow(filas);
        }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar la Tabla de Logs");
        }    
    }
    
    private void hasta()
    {
        Date ahora = new Date();
        dp_desde.setDate(ahora);
        Calendar m = Calendar.getInstance();
        m.add(Calendar.DAY_OF_MONTH, 1);
        dp_hasta.setDate(m.getTime());
    }
    public void cargarOperariosCombo ()
    {
        cmbOperarios.removeAllItems();
        Collection operarios = remiseria.buscarOperariosActivos();
        if(operarios != null)
        {
            Operario unOper = null;
            Iterator iter = operarios.iterator();
            while (iter.hasNext())
            {
                unOper = (Operario) iter.next();
                cmbOperarios.addItem(unOper);
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
        dp_desde = new org.jdesktop.swingx.JXDatePicker();
        jLabel43 = new javax.swing.JLabel();
        dp_hasta = new org.jdesktop.swingx.JXDatePicker();
        jLabel39 = new javax.swing.JLabel();
        fechaDeterminado = new org.jdesktop.swingx.JXDatePicker();
        jLabel44 = new javax.swing.JLabel();
        cmbOperarios = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        chekPeriodo = new javax.swing.JCheckBox();
        chekPeriodo1 = new javax.swing.JCheckBox();
        jLabel45 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnListar1 = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAccesos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(117, 150, 227));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        dp_desde.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_desde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_desdeActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel43.setText("Hasta:");

        dp_hasta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dp_hasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dp_hastaActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel39.setText("Fecha particular:");

        fechaDeterminado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fechaDeterminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaDeterminadoActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel44.setText("Desde");

        cmbOperarios.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Operario:");

        chekPeriodo.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        chekPeriodo.setText("Por periodo de tiempo");
        chekPeriodo.setBackground(new java.awt.Color(117, 150, 227));
        chekPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chekPeriodoActionPerformed(evt);
            }
        });

        chekPeriodo1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        chekPeriodo1.setText("Por operario");
        chekPeriodo1.setBackground(new java.awt.Color(117, 150, 227));
        chekPeriodo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chekPeriodo1ActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel45.setText("Filtrar por:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chekPeriodo)
                    .addComponent(chekPeriodo1)
                    .addComponent(jLabel45))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dp_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbOperarios, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dp_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechaDeterminado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(119, 119, 119))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(dp_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel44)
                                .addComponent(jLabel39)
                                .addComponent(fechaDeterminado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chekPeriodo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chekPeriodo1)
                            .addComponent(jLabel43)
                            .addComponent(dp_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(cmbOperarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnListar1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnListar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/computer.png"))); // NOI18N
        btnListar1.setText("Visualizar");
        btnListar1.setBackground(new java.awt.Color(255, 255, 255));
        btnListar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListar1ActionPerformed(evt);
            }
        });

        btnListar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PRINTER 1.png"))); // NOI18N
        btnListar.setText("Listar todo");
        btnListar.setBackground(new java.awt.Color(255, 255, 255));
        btnListar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application-exit.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnListar1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnListar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel30.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel30.setText("Historial de Accesos al Sistema");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(117, 150, 227));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tablaAccesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Operario", "Fecha - Hora de incio", "Fecha - Hora de finalización", "Total de horas"
            }
        ));
        jScrollPane1.setViewportView(tablaAccesos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fechaDeterminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaDeterminadoActionPerformed
        // TODO add your handling code here:
        try
        {
             cargarTablaAuditoria(tablaAccesos,this.filtrarTodo());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error al cargar la Tabla de Accesos al Sistema", null, JOptionPane.ERROR_MESSAGE);
       
        }
    }//GEN-LAST:event_fechaDeterminadoActionPerformed

    private void dp_hastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_hastaActionPerformed
        // TODO add your handling code here:
        try
        {
             cargarTablaAuditoria(tablaAccesos,this.filtrarTodo());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error al cargar la Tabla de Accesos al Sistema", null, JOptionPane.ERROR_MESSAGE);
       
        }
    }//GEN-LAST:event_dp_hastaActionPerformed

    private void dp_desdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dp_desdeActionPerformed
        // TODO add your handling code here:
        try
        {
             cargarTablaAuditoria(tablaAccesos,this.filtrarTodo());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error al cargar la Tabla de Accesos al Sistema", null, JOptionPane.ERROR_MESSAGE);
       
        }
    }//GEN-LAST:event_dp_desdeActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        // TODO add your handling code here:
       try
       {
        try{
            if(imprimir != null)
            {
                HashMap<String, Object> parametros = new HashMap();
                parametros.clear();
                if(unMaestro != null)
                {
                    parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                    parametros.put("fechaActual", utilidades.getFechaActual());
                    parametros.put("nombreEmpresa",remiseria.getNombre());
                    parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                    parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                    parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                    parametros.put("logo", remiseria.getLogo());

                    if(dp_desde.isEnabled()==true&& dp_hasta.isEnabled())
                    {
                        if(dp_desde.getDate()!= null || dp_hasta.getDate()!= null)
                        {
                            parametros.put("fechaDesde",utilidades.getFecha(dp_desde.getDate()) );
                            parametros.put("fechaHasta",utilidades.getFecha(dp_hasta.getDate()) );

                        }
                    }
                    else if (fechaDeterminado.isEnabled()==true)
                    {
                        parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                        parametros.put("fechaHasta", null );
                    }
                }
                else if(unOperario != null)
                {
                    parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                    parametros.put("fechaActual", utilidades.getFechaActual());
                    parametros.put("fechaActual", utilidades.getFechaActual());
                    parametros.put("nombreEmpresa",remiseria.getNombre());
                    parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                    parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                    parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                    parametros.put("logo", remiseria.getLogo());

                    if(dp_desde.isEnabled()==true&& dp_hasta.isEnabled())
                    {
                        if(dp_desde.getDate()!= null || dp_hasta.getDate()!= null)
                        {
                            parametros.put("fechaDesde",utilidades.getFecha(dp_desde.getDate()) );
                            parametros.put("fechaHasta",utilidades.getFecha(dp_hasta.getDate()) );

                        }
                    }
                    else if (fechaDeterminado.isEnabled()==true)
                    {
                        parametros.put("fechaDesde",utilidades.getFecha(fechaDeterminado.getDate()) );
                        parametros.put("fechaHasta",null );
                    }
                }
                //C:\Users\garba\Desktop\Reportes
                //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/AccesosSistema.jrxml");

                JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/AccesosSistema.jrxml");
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(imprimir));
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
            else
            {
                JOptionPane.showMessageDialog(null,"No hay Información de Viajes para realizar el reporte", null, JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(JRException ex)
        {
            JOptionPane.showMessageDialog(this,ex.getMessage());
        }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"No hay Información de Viajes para realizar el reporte", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnListar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListar1ActionPerformed
        // TODO add your handling code here:
////        try
//        {
             cargarTablaAuditoria(tablaAccesos,this.filtrarTodo());
//        }
//        catch (Exception ex)
//        {
//            JOptionPane.showMessageDialog(null, "Error al cargar la Tabla de Accesos al Sistema "+ ex, null, JOptionPane.ERROR_MESSAGE);
//       
//        }
    }//GEN-LAST:event_btnListar1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void chekPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chekPeriodoActionPerformed
        // TODO add your handling code here:
        if(chekPeriodo.isSelected()==true)
        {
            dp_desde.setEnabled(true);
            dp_hasta.setEnabled(true);
            fechaDeterminado.setEnabled(false);
        }
        else
        {
            dp_desde.setEnabled(false);
            dp_hasta.setEnabled(false);
            fechaDeterminado.setEnabled(true);        
        }
    }//GEN-LAST:event_chekPeriodoActionPerformed

    private void chekPeriodo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chekPeriodo1ActionPerformed
        // TODO add your handling code here:
        if(chekPeriodo1.isSelected()==true)
        {
              cmbOperarios.setEnabled(true);
        }
        else
        {
            cmbOperarios.setEnabled(false);
        }
    }//GEN-LAST:event_chekPeriodo1ActionPerformed

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
            java.util.logging.Logger.getLogger(HistorialAccesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistorialAccesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistorialAccesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistorialAccesos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HistorialAccesos dialog = new HistorialAccesos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnListar1;
    private javax.swing.JCheckBox chekPeriodo;
    private javax.swing.JCheckBox chekPeriodo1;
    private javax.swing.JComboBox cmbOperarios;
    private org.jdesktop.swingx.JXDatePicker dp_desde;
    private org.jdesktop.swingx.JXDatePicker dp_hasta;
    private org.jdesktop.swingx.JXDatePicker fechaDeterminado;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaAccesos;
    // End of variables declaration//GEN-END:variables
}
