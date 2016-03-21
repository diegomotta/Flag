package Interface;
import Clases.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author garba
 */
public class VentanaPrincipal extends javax.swing.JFrame  {
    private Remiseria remiseria;
    private Utilidad utilidades;
    private Reloj reloj;
    private java.awt.Frame parent;
    private boolean modal;
    private JDesktopPane elEscritorio;
    private Operario unOperario;
    private Maestro usuarioMaestro;
    //private Usuario unUsuario;
    /**
     * Creates new form ventanaPrincipal
     */
    public VentanaPrincipal(final Remiseria remiseria,Utilidad utilidades, final Operario unOperario) {
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unOperario = unOperario;
        initComponents();
        VentanaPrincipal.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
        @Override
	public void windowClosing(WindowEvent we){
		int eleccion = JOptionPane.showConfirmDialog(null, "Desea salir?");

		if ( eleccion == 0)
                {
                    MarcarTarjeta unMarcarTarjeta = remiseria.buscarTarjetas();
                    if(unMarcarTarjeta.getUnOperario()!= null)
                    {
                        remiseria.cargarSetMarcadoTarjeta(unMarcarTarjeta, reloj);
                        remiseria.agregarLiquidacionPreviaOperario(unOperario, null);
                    }
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    try{
                    VentanaLogueo.getAviso().terminar();
                    VentanaLogueo.getAviso2().terminar();
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error al cerrar el ciclo de los procesos que controlan el aviso de vencimiento de la reserva");
                    }                    
                    System.exit(0); 
                }
               
	}
        });
        this.btnPersonalizar.setEnabled(false);
        this.btnPersonalizar.setVisible(false);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.reloj = new Reloj(txtReloj);
        this.reloj.start();
        txtFecha.setText(utilidades.getFechaActual());
        elEscritorio = new JDesktopPane();
        elEscritorio.setVisible(true);     
        add(elEscritorio);
        remiseria.ponerEnCeroPrioridad();
        txtUsuario.setText(this.unOperario.toString());
        if(this.unOperario.getUnRol()!= null)
        {
            if ( this.unOperario != null) {
               Object unUsuario = this.unOperario;
               if (unUsuario instanceof Operario) {
                   Map<String, Permisos> permisos = ((Operario) unUsuario).getUnRol().getPermisos();
                   if (!permisos.containsKey("permitirGestionDeUsuarios")) {
                       btnOperarios.setEnabled(false);
                       btnOperarios.setVisible(false);
                   } else {
                       btnOperarios.setEnabled(true);
                       btnOperarios.setVisible(true);
                   }

                   if (!permisos.containsKey("permitirGestionDeChoferes")) {
                       btnChoferes.setEnabled(false);
                       btnChoferes.setVisible(false);
                   } else {
                       btnChoferes.setEnabled(true);
                       btnChoferes.setVisible(true);
                   }

                   if (!permisos.containsKey("permitirGestionDeMoviles")) {
                       btnMoviles.setEnabled(false);
                       btnMoviles.setVisible(false);
                   } else {
                       btnMoviles.setEnabled(true);
                       btnMoviles.setVisible(true);
                   }

                   if (!permisos.containsKey("permitirGestionDeTarifas")) {
                       btnTarifas.setEnabled(false);
                       btnTarifas.setVisible(false);
                   } else {
                       btnTarifas.setEnabled(true);
                       btnTarifas.setVisible(true);
                   }

                   if (!permisos.containsKey("permitirGestionDeZonas")) {
                       btnZonas.setEnabled(false);
                       btnZonas.setVisible(false);
                   } else {
                       btnZonas.setEnabled(true);
                       btnZonas.setVisible(true);
                   }

                   if (!permisos.containsKey("permitirGestionDeViajes")) {
                       btnViajes.setEnabled(false);
                       btnViajes.setVisible(false);
                   } else {
                       btnViajes.setEnabled(true);
                       btnViajes.setVisible(true);
                   }

                   if (!permisos.containsKey("permitirGestionDeClientes")) {
                       btnCliente.setEnabled(false);
                       btnCliente.setVisible(false);
                   } else {
                       btnCliente.setEnabled(true);
                       btnCliente.setVisible(true);
                   }
//                   if (!permisos.containsKey("permitirGestionDeCargos")) {
//                       btnCargos.setEnabled(false);
//                       btnCargos.setVisible(false);
//                   } else {
//                       btnCargos.setEnabled(true);
//                       btnCargos.setVisible(true);
//                   }
                   if (!permisos.containsKey("permitirGestionDeCaja")) {
                       btnCaja.setEnabled(false);
                       btnCaja.setVisible(false);
                   } else {
                       btnCaja.setEnabled(true);
                       btnCaja.setVisible(true);
                   }          
                   if (!permisos.containsKey("permitirGestionDeLogs")) {
                       btnLogs.setEnabled(false);
                       btnLogs.setVisible(false);
                   } else {
                       btnLogs.setEnabled(true);
                       btnLogs.setVisible(true);
                   } 
                   if (!permisos.containsKey("permitirGestionDeRespaldo")) {
                       btnRespaldo.setEnabled(false);
                       btnRespaldo.setVisible(false);
                   } else {
                       btnRespaldo.setEnabled(true);
                       btnRespaldo.setVisible(true);
                   } 
               }
            }
        }
    }

    VentanaPrincipal(final Remiseria remiseria, Utilidad utilidades, final Maestro usuarioMaestro) {
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.usuarioMaestro = usuarioMaestro;
        
        initComponents();

        setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
        @Override
	public void windowClosing(WindowEvent we){
		int eleccion = JOptionPane.showConfirmDialog(null, "Desea salir?");
		if ( eleccion == 0) 
                {
                    MarcarTarjeta unMarcarTarjeta = remiseria.buscarTarjetas();
                    if(unMarcarTarjeta.getUnMaestro()!= null)
                    {
                        remiseria.cargarSetMarcadoTarjeta(unMarcarTarjeta,reloj);
                        remiseria.agregarLiquidacionPreviaOperario(null, usuarioMaestro);
                    }
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    System.exit(0); 
                }	
	}
        });
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.reloj = new Reloj(txtReloj);
        this.reloj.start();
        txtUsuario.setText(this.usuarioMaestro.toString());
        txtFecha.setText(utilidades.getFechaActual());
        elEscritorio = new JDesktopPane();
        elEscritorio.setVisible(true);
        add(elEscritorio);
        remiseria.ponerEnCeroPrioridad();
//        this.hilo1= new AvisoReserva( remiseria, utilidades);
//        hilo1.run();
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
        jPanel1 = new javax.swing.JPanel();
        btnMoviles = new javax.swing.JButton();
        btnViajes = new javax.swing.JButton();
        btnTarifas = new javax.swing.JButton();
        btnCaja = new javax.swing.JButton();
        btnOperarios = new javax.swing.JButton();
        btnZonas = new javax.swing.JButton();
        btnChoferes = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnPersonalizar = new javax.swing.JButton();
        btnLogs = new javax.swing.JButton();
        btnRespaldo = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        btnPersonalizar1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtReloj = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        clockFace1 = new org.edisoncor.gui.varios.ClockFace();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Flag: Sistema de Gestión para Agencias de Remises");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(117, 150, 227));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        btnMoviles.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnMoviles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Taxi_Icon_32.png"))); // NOI18N
        btnMoviles.setText("Móviles");
        btnMoviles.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMoviles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovilesActionPerformed(evt);
            }
        });

        btnViajes.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnViajes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/car.png"))); // NOI18N
        btnViajes.setText("Viajes");
        btnViajes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnViajes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViajesActionPerformed(evt);
            }
        });

        btnTarifas.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnTarifas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/view-list-compact-symbolic.png"))); // NOI18N
        btnTarifas.setText("Tarifas");
        btnTarifas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTarifas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarifasActionPerformed(evt);
            }
        });

        btnCaja.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/caja_registradora.png"))); // NOI18N
        btnCaja.setText("Caja");
        btnCaja.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaActionPerformed(evt);
            }
        });

        btnOperarios.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnOperarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/emblem-people.png"))); // NOI18N
        btnOperarios.setText("Operarios");
        btnOperarios.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOperarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOperariosActionPerformed(evt);
            }
        });

        btnZonas.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnZonas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ciudad.png"))); // NOI18N
        btnZonas.setText("Zonas");
        btnZonas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnZonas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZonasActionPerformed(evt);
            }
        });

        btnChoferes.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnChoferes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/avatar-default.png"))); // NOI18N
        btnChoferes.setText("Choferes");
        btnChoferes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnChoferes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoferesActionPerformed(evt);
            }
        });

        btnCliente.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-contacts.png"))); // NOI18N
        btnCliente.setText("Clientes");
        btnCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });

        btnPersonalizar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnPersonalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/drive-harddisk-system.png"))); // NOI18N
        btnPersonalizar.setText("Configuración");
        btnPersonalizar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPersonalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPersonalizarActionPerformed(evt);
            }
        });

        btnLogs.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnLogs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aptdaemon-resolve.png"))); // NOI18N
        btnLogs.setText("Logs");
        btnLogs.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogsActionPerformed(evt);
            }
        });

        btnRespaldo.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnRespaldo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/mail-inbox.png"))); // NOI18N
        btnRespaldo.setText("Respaldo");
        btnRespaldo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRespaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRespaldoActionPerformed(evt);
            }
        });

        btnCerrarSesion.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/keys.png"))); // NOI18N
        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        btnPersonalizar1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnPersonalizar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Help.png"))); // NOI18N
        btnPersonalizar1.setText("Ayuda");
        btnPersonalizar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPersonalizar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPersonalizar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnViajes, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnMoviles, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnTarifas, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnChoferes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnOperarios)
                .addGap(0, 0, 0)
                .addComponent(btnCliente)
                .addGap(0, 0, 0)
                .addComponent(btnCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnLogs, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnRespaldo)
                .addGap(0, 0, 0)
                .addComponent(btnPersonalizar)
                .addGap(0, 0, 0)
                .addComponent(btnPersonalizar1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViajes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoferes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoviles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTarifas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOperarios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPersonalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogs, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRespaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPersonalizar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel9.setBackground(new java.awt.Color(117, 150, 227));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        txtReloj.setEditable(false);
        txtReloj.setBackground(new java.awt.Color(204, 204, 204));
        txtReloj.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtReloj.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        txtUsuario.setEditable(false);
        txtUsuario.setBackground(new java.awt.Color(204, 204, 204));
        txtUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel2.setText("Usuario:");

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel4.setText("Fecha de hoy:");

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(204, 204, 204));
        txtFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFecha.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel3.setText("Tiempo:");

        clockFace1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout clockFace1Layout = new javax.swing.GroupLayout(clockFace1);
        clockFace1.setLayout(clockFace1Layout);
        clockFace1Layout.setHorizontalGroup(
            clockFace1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        clockFace1Layout.setVerticalGroup(
            clockFace1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnSalir.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/shutdown.png"))); // NOI18N
        btnSalir.setText("Cerrar el Sistema");
        btnSalir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Havell.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(9, 9, 9)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clockFace1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 498, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(txtReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
            .addComponent(clockFace1, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jDesktopPane1.setBackground(new java.awt.Color(0, 78, 164));
        jDesktopPane1.setMaximumSize(new java.awt.Dimension(1368, 545));
        jDesktopPane1.setMinimumSize(new java.awt.Dimension(1368, 545));
        jScrollPane1.setViewportView(jDesktopPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMovilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovilesActionPerformed
        // TODO add your handling code here:
        JDialogMovil ventana = new JDialogMovil(parent,modal,remiseria,utilidades,usuarioMaestro,unOperario);

         ventana.show();
    }//GEN-LAST:event_btnMovilesActionPerformed

    private void btnChoferesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoferesActionPerformed
        // TODO add your handling code here:
        JDialogChofer1 ventana = new JDialogChofer1(parent,modal,remiseria,utilidades,usuarioMaestro,unOperario);
        ventana.show();
    }//GEN-LAST:event_btnChoferesActionPerformed
    
    
    private void btnViajesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViajesActionPerformed
    try
    {
            boolean ok = true;
            Collection moviles = remiseria.buscarMoviles();
            Collection choferes = remiseria.buscarChoferes();
            Collection zonas = remiseria.buscarZonas();
            Collection tarifas = remiseria.buscarTarifas();
            if(moviles.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Registre Móviles para poder acceder al Gestor de Viajes","No hay Móviles registrados",JOptionPane.ERROR_MESSAGE);
                ok = false;
            }
            if(choferes.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Registre Choferes para poder acceder al Gestor de Viajes","No hay Choferes registrados",JOptionPane.ERROR_MESSAGE);
                ok = false;
            }
            if(zonas.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Registre Zonas para poder acceder al Gestor de Viajes","No hay Zonas registradas",JOptionPane.ERROR_MESSAGE);
                ok = false;
            }
            if(tarifas.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"Registre Tarifas para poder acceder al Gestor de Viajes","No hay Tarifas registradas",JOptionPane.ERROR_MESSAGE);
                ok = false;
            }

            if(ok == true)
            {
//                abrirViaje hilo;
//                hilo = new abrirViaje( remiseria, utilidades, btnViajes,unOperario, usuarioMaestro,jDesktopPane1);
//                hilo.start();
                try
                {
                  JInternalFrameViaje ventana3 = new JInternalFrameViaje (remiseria, utilidades,btnViajes,unOperario,usuarioMaestro);
                  remiseria.setUnaVentana(ventana3);
                  ventana3.pack();
                  this.jDesktopPane1.add(ventana3);
                  ventana3.setVisible(true);
                    try 
                    {
                        ventana3.setMaximum(true);
                    }
                    catch (PropertyVetoException ex)
                    {
                        Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,"Error al querer abrir la pantalla de Viajes");
                }
            }
    }
    catch (Exception ex)
    {
        JOptionPane.showMessageDialog(null, "Error al abrir la ventana de Gestor de Viajes", null, JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnViajesActionPerformed

    private void btnTarifasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifasActionPerformed
        // TODO add your handling code here:
        JDialogTarifa ventana = new JDialogTarifa(parent,modal,remiseria,utilidades,usuarioMaestro,unOperario);
        ventana.show();
    }//GEN-LAST:event_btnTarifasActionPerformed

    private void btnCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaActionPerformed
        // TODO add your handling code here:
        if(usuarioMaestro == null)
        {
            JDialogCaja ventana = new JDialogCaja (parent,modal,remiseria, utilidades,unOperario);
            ventana.show();
        }
        else
        {
            JDialogCaja ventana = new JDialogCaja (parent,modal,remiseria, utilidades,usuarioMaestro);
            ventana.show();
        }
    }//GEN-LAST:event_btnCajaActionPerformed

    private void btnOperariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOperariosActionPerformed
        // TODO add your handling code here:
        JDialogOperario ventana = new JDialogOperario(parent,modal,remiseria,utilidades,usuarioMaestro,unOperario);
        ventana.show();
    }//GEN-LAST:event_btnOperariosActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        // TODO add your handling code here:
        try
        {
            MarcarTarjeta unMarcarTarjeta = remiseria.buscarTarjetas();
            if(unMarcarTarjeta.getUnMaestro()!= null)
                {
                    remiseria.cargarSetMarcadoTarjeta(unMarcarTarjeta,reloj);
                    remiseria.agregarLiquidacionPreviaOperario(null, usuarioMaestro);
                    VentanaLogueo ventana = new VentanaLogueo(remiseria, utilidades);
                    ventana.show();
                    try{
                    VentanaLogueo.getAviso().terminar();
                    VentanaLogueo.getAviso2().terminar();
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error al cerrar el ciclo de los procesos que controlan el aviso de vencimiento de la reserva");
                    }
                    this.dispose();
                }
            
            else if(unMarcarTarjeta.getUnOperario()!= null)
            {
                remiseria.cargarSetMarcadoTarjeta(unMarcarTarjeta,reloj);
                remiseria.agregarLiquidacionPreviaOperario(unOperario, null);
                VentanaLogueo ventana = new VentanaLogueo(remiseria, utilidades);
                ventana.show();
                try{
                VentanaLogueo.getAviso().terminar();
                VentanaLogueo.getAviso2().terminar();
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error al cerrar el ciclo de los procesos que controlan el aviso de vencimiento de la reserva");
                }
                    
                this.dispose();
            }
        }
        catch (Exception ex)
        {
                JOptionPane.showMessageDialog(null, "Error al cerrar el sistema");
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        int eleccion = JOptionPane.showConfirmDialog(null, "Desea salir?");
        if ( eleccion == 0) 
        {
                MarcarTarjeta unMarcarTarjeta = remiseria.buscarTarjetas();
                if(unMarcarTarjeta.getUnMaestro()!= null)
                {
                    remiseria.cargarSetMarcadoTarjeta(unMarcarTarjeta,reloj);
                    remiseria.agregarLiquidacionPreviaOperario(unOperario, usuarioMaestro);
                }
                else if(unMarcarTarjeta.getUnOperario()!= null)
                {
                    remiseria.cargarSetMarcadoTarjeta(unMarcarTarjeta, reloj);
                    remiseria.agregarLiquidacionPreviaOperario(unOperario, usuarioMaestro);
                }
                JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                try{
                VentanaLogueo.getAviso().terminar();
                VentanaLogueo.getAviso2().terminar();
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "Error al cerrar el ciclo de los procesos que controlan el aviso de vencimiento de la reserva");
                }
                System.exit(0);
        }	
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnZonasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZonasActionPerformed
        // TODO add your handling code here:
        JDialogZonas ventana = new JDialogZonas(parent,modal,remiseria,null,usuarioMaestro,unOperario);
        ventana.show();
    }//GEN-LAST:event_btnZonasActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        // TODO add your handling code here:
     JDialogCliente ventana = new JDialogCliente(parent,modal,remiseria,utilidades,usuarioMaestro,unOperario,null);
        ventana.show();
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnPersonalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPersonalizarActionPerformed
        // TODO add your handling code here:
        JDialogCustomizar ventana = new JDialogCustomizar(parent,modal,remiseria,usuarioMaestro,utilidades,this);
        ventana.show();
    }//GEN-LAST:event_btnPersonalizarActionPerformed

    private void btnLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogsActionPerformed
        // TODO add your handling code here:
        JDialogAuditoria ventana = new JDialogAuditoria(parent,modal, remiseria, usuarioMaestro,unOperario);
        ventana.show();
    }//GEN-LAST:event_btnLogsActionPerformed

    private void btnRespaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRespaldoActionPerformed
        // TODO add your handling code here:
        JDialogBackup ventana = new JDialogBackup(parent,modal);
        ventana.show();
    }//GEN-LAST:event_btnRespaldoActionPerformed

    private void btnPersonalizar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPersonalizar1ActionPerformed
        // TODO add your handling code here:
     try {
        Runtime run = Runtime.getRuntime();
        //C:\Users\garba\Desktop\Flag
        //String path = new String("hh.exe C:\\Users\\garba\\Desktop\\SisMas\\src\\ManualDeUsuarioV1.chm");
        String path = new String("hh.exe C:\\Users\\garba\\Desktop\\Flag\\ManualDeUsuarioV1.chm");
        Process pro = run.exec(path); 
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex, null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPersonalizar1ActionPerformed

    
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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
        * 
        */
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCaja;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnChoferes;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnLogs;
    private javax.swing.JButton btnMoviles;
    private javax.swing.JButton btnOperarios;
    private javax.swing.JButton btnPersonalizar;
    private javax.swing.JButton btnPersonalizar1;
    private javax.swing.JButton btnRespaldo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTarifas;
    private javax.swing.JButton btnViajes;
    private javax.swing.JButton btnZonas;
    private org.edisoncor.gui.varios.ClockFace clockFace1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtReloj;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

  public Reloj obtenerHora()
  {
      return this.reloj;
  }
    
}
