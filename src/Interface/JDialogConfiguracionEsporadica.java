/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Clases.Barrio;
import Clases.Ciudad;
import Clases.Cliente;
import Clases.DireccionViaje;
import Clases.Domicilio;
import Clases.Maestro;
import Clases.Movil;
import Clases.Operario;
import Clases.Pais;
import Clases.Permisos;
import Clases.Provincia;
import Clases.Remiseria;
import Clases.Utilidad;
import Clases.UtilidadViaje;
import Clases.Viaje;
import Clases.Zona;
import java.awt.Font;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Color;
import java.util.Map;
import  org.jdesktop.swingx.autocomplete.*;
/**
 *
 * @author garba
 */
public class JDialogConfiguracionEsporadica extends javax.swing.JDialog {
private Remiseria remiseria;
private JTable tablaDisponibles;
private JTable tablaTransito;
private Utilidad utilidades;
private java.awt.Frame parent;
private boolean modal;
private Maestro unMaestro;
private Operario unOperario;


    /**
     * Creates new form JDialogConfiguracionEsporadica
     */
    public JDialogConfiguracionEsporadica(java.awt.Frame parent, boolean modal, Remiseria remiseria, JTable tablaDisponibles, JTable tablaTransito, Utilidad utilidades, Maestro unMaestro , Operario unOperario) {
        super(parent, modal);
        this.unMaestro = unMaestro;
        this.unOperario = unOperario;
        this.remiseria = remiseria;
        this.tablaDisponibles = tablaDisponibles;
        this.tablaTransito = tablaTransito;
        this.utilidades = utilidades;
        initComponents();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        Pais pais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
        this.cargarCiudadesCombo(pais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()));
        this.cargarUtilidadesViajeCombo();
        int fila = (int) tablaDisponibles.getSelectedRow();
        int numeroMovil = (int) tablaDisponibles.getValueAt(fila, 0);
        Movil unMovil = remiseria.buscarMovil(numeroMovil);
        this.txtKilometroInicial.setText(String.valueOf(unMovil.getKilometraje()));
        AutoCompleteDecorator.decorate(this.cmbCiudad);
        AutoCompleteDecorator.decorate(this.cmbBarrio);
        AutoCompleteDecorator.decorate(this.cmbDireccion);
        AutoCompleteDecorator.decorate(this.cmbDireccionCliente);
        AutoCompleteDecorator.decorate(this.cmbCliente);
        this.cargarZonaCombo();
        if ( this.unOperario != null) 
        {
               Object unUsuario = this.unOperario;
               if (unUsuario instanceof Operario) 
               {
                   Map<String, Permisos> permisos = ((Operario) unUsuario).getUnRol().getPermisos();
                   if (!permisos.containsKey("permitirGestionDeClientes")) {
                       btnCliente.setEnabled(false);
                       btnCliente.setVisible(false);
                       btnDirecciónCliente.setEnabled(false);
                       btnDirecciónCliente.setVisible(false);
                   } else {
                       btnCliente.setEnabled(true);
                       btnCliente.setVisible(true);
                       btnDirecciónCliente.setEnabled(true);
                       btnDirecciónCliente.setVisible(true);                       
                   } 
               }
        }        
        if(unMovil.getUnaZona()!= null)
        {
            cmbZona.setSelectedItem(unMovil.getUnaZona());
        }
        AutoCompleteDecorator.decorate(this.cmbZona);
    }
    
    public void cargarZonaCombo ()
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

    public void cargarDisponibles(){
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
        while (iter.hasNext()){
            aux = (Movil) iter.next();
            Object [] fila = new Object[5];
            fila[0] = aux.getNumeroMovil();
            fila[1] = aux.getUnaMarca().getNombreMarca();
            fila[2] = aux.getUnModelo().getNombreModelo();
            fila[3] = aux.getUnChofer().getNombre()+" "+aux.getUnChofer().getApellido();
            fila[4] = aux.getUnaZona().getNombreZona();          
            modelo.addRow(fila);
            }
        modelo.rowsRemoved(null);
        tablaDisponibles.setModel(modelo);
    }
    
     public void cargarTransito(){
        Collection moviles = remiseria.buscarViajesActivos(); 
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTableHeader th; 
        th = tablaTransito.getTableHeader(); 
        Font fuente = new Font("Tahoma", Font.BOLD, 12); 
        th.setForeground(java.awt.Color.BLACK);
        th.setBackground(java.awt.Color.white);
        th.setFont(fuente); 
        modelo.addColumn("Móvil N°");
        modelo.addColumn("Viaje N°");        
        modelo.addColumn("Chofer");
        modelo.addColumn("Hora");
        modelo.addColumn("Origen");
        modelo.addColumn("Destino");
        modelo.addColumn("Utilidad");
        modelo.addColumn("Asignado");
        Viaje aux = null;
        Iterator iter = moviles.iterator();
        while (iter.hasNext())
        {
            aux = (Viaje) iter.next();
            Object [] fila = new Object[8];
            fila[0] = aux.obtenerNumeroMovil();
            fila[1] = aux.getNumeroViaje();
            fila[2] = aux.getUnChofer().getNombre()+" "+aux.getUnChofer().getApellido() ;
            fila[3] = utilidades.getHora(aux.getFechaHora());
            fila[4] = aux.getUnaZona().getNombreZona();
            fila[5] = aux.getUnDomiclio().getUnaCiudad().getNombreCiudad()+" - "+aux.getUnDomiclio().getUnBarrio().getNombreBarrio()+" - "+ aux.getUnDomiclio().getUnaDireccionViaje().getDireccion();
            if(aux.getUnCliente()!= null)
            {
                fila[6] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje() +" "+ aux.getUnCliente();
            }
            else
            {
                fila[6] = aux.getUnaUtilidadViaje().getNombreUtilidadViaje();
            }
            if (aux.getAsignadoBase().equals("Base"))
            {
                fila[7] = "Base";
            }else{
                fila[7] = "Particular";
            }


            modelo.addRow(fila);
            }      
        modelo.rowsRemoved(null);
        tablaTransito.setModel(modelo);
        tablaTransito.getColumn("Móvil N°").setMaxWidth(80);
        tablaTransito.getColumn("Viaje N°").setMaxWidth(80);        
        tablaTransito.getColumn("Chofer").setMaxWidth(200);   
        tablaTransito.getColumn("Hora").setMaxWidth(60);  
        tablaTransito.getColumn("Origen").setMaxWidth(80);  
        tablaTransito.getColumn("Destino").setMaxWidth(350);      
        tablaTransito.getColumn("Utilidad").setMaxWidth(230); 
        tablaTransito.getColumn("Asignado").setMaxWidth(80);     
    }
     
    public void iniciarViaje(){
        try
        {
                boolean ok = true;
                int fila = (int) tablaDisponibles.getSelectedRow();
                int numeroMovil = (int) tablaDisponibles.getValueAt(fila, 0);
                Movil unMovil = remiseria.buscarMovil(numeroMovil);
                //unMovil.setPrioridad(0); 
                UtilidadViaje unaUtilidadViaje = remiseria.buscarTiposUtilidadViaje(cmbUtilidadViaje.getSelectedItem().toString());

                if(!cmbUtilidadViaje.getSelectedItem().equals("Cliente"))
                {
                    String unaCiudad = cmbCiudad.getSelectedItem().toString();
                    String unBarrio = cmbBarrio.getSelectedItem().toString();
                    String unaDireccionViaje = null;
                    if(unaCiudad.isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "No ha seleccionado una Ciudad", "",JOptionPane.ERROR_MESSAGE);
                        ok = false;
                    }
                    if(unBarrio.isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "No ha seleccionado una Barrio", "",JOptionPane.ERROR_MESSAGE);
                        ok = false;            
                    }
                    if(cmbDireccion.getSelectedItem()== null)
                    {
                        JOptionPane.showMessageDialog(null, "No ha seleccionado una Dirección o no ha registrado la dirección ingresada", "",JOptionPane.ERROR_MESSAGE);
                        ok = false;           
                    }
                    else
                    {
                        unaDireccionViaje = cmbDireccion.getSelectedItem().toString();
                    }
                    if(ok == true)
                    {
                        Pais unPais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
                        if(unPais != null)
                        {
                            Provincia unaProvincia = unPais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia());
                            if(unaProvincia != null)
                            {
                                Ciudad ciudad = unaProvincia.buscarCiudad(unaCiudad);
                                if(ciudad != null)
                                {
                                    Barrio barrio = ciudad.buscarBarrio(unBarrio);
                                    if(barrio != null){
                                        //unaDireccionViaje = cmbDireccionCliente.getSelectedItem();
                                        DireccionViaje direccionViaje = barrio.buscarDireccionViaje(cmbDireccion.getSelectedItem().toString());
                                        if(direccionViaje != null)
                                        {
                                            if(ok == true)
                                            {
                                                    if(cmbInicio.getSelectedItem().equals("Base"))
                                                    {
                                                        Cliente unCliente =(Cliente) cmbCliente.getSelectedItem();
                                                        try{
                                                            Zona zo=(Zona)cmbZona.getSelectedItem();
                                                            remiseria.iniciarViaje("Base", unMovil, unaUtilidadViaje,unPais,unaProvincia, ciudad, barrio, direccionViaje, zo,null);
                                                            this.dispose();
                                                        }
                                                        catch (Exception ex)
                                                        {

                                                            JOptionPane.showMessageDialog(null, ex, null, JOptionPane.ERROR_MESSAGE);

                                                        }
                                                    }
                                                    else
                                                    {
                                                        Zona zo=(Zona)cmbZona.getSelectedItem();
                                                        remiseria.iniciarViaje("Particular", unMovil, unaUtilidadViaje,unPais,unaProvincia, ciudad, barrio, direccionViaje, zo,null);
                                                        remiseria.getUnaVentana().cargarKilometrosRecorridos();
                                                        this.dispose();
                                                    }
                                                this.cargarDisponibles();
                                                this.cargarTransito();
                                            }
                                        }
                                        else
                                        {
                                            JOptionPane.showMessageDialog(null,"No seleccionado una Dirección del Particular"," ",JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null,"No seleccionado un Barrio"," ",JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null,"No seleccionado una Ciudad"," ",JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"No seleccionado una Provincia"," ",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"No seleccionado un País"," ",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else
                {
                        if(cmbDireccionCliente.getSelectedItem()!= null)
                        {     
                            Domicilio unDomicilio = (Domicilio)cmbDireccionCliente.getSelectedItem();
                            if(cmbInicio.getSelectedItem().equals("Base"))
                            {
                                Zona zo=(Zona)cmbZona.getSelectedItem();
                                 remiseria.iniciarViaje1("Base", unMovil, unaUtilidadViaje,unDomicilio.getUnPais(),unDomicilio.getUnaProvincia(), unDomicilio.getUnaCiudad(), unDomicilio.getUnBarrio(), "Calle "+unDomicilio.getCalle()+" N° "+unDomicilio.getNroCasa(), zo,(Cliente)(cmbCliente.getSelectedItem()));
                                 remiseria.getUnaVentana().cargarKilometrosRecorridos();
                                 this.dispose();   
                            }
                            else
                            {
                                Cliente unCliente =(Cliente) (cmbCliente.getSelectedItem());
                                Cliente cliente = remiseria.buscarCliente(unCliente.getDni());
                                Zona zo=(Zona)cmbZona.getSelectedItem();
                                 remiseria.iniciarViaje1("Particular", unMovil, unaUtilidadViaje,unDomicilio.getUnPais(),unDomicilio.getUnaProvincia(), unDomicilio.getUnaCiudad(), unDomicilio.getUnBarrio(), "Calle "+unDomicilio.getCalle()+" N° "+unDomicilio.getNroCasa(), zo,cliente);
                                 remiseria.getUnaVentana().cargarKilometrosRecorridos();
                                 this.dispose();
                            }   
                            this.cargarDisponibles();
                            this.cargarTransito();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"No hay direcciones del Cliente cargados"," ",JOptionPane.ERROR_MESSAGE);
                        }
            }    
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al Iniciar Viaje");
        }
}

    public void cargarUtilidadesViajeCombo()
    {
       cmbUtilidadViaje.removeAllItems();
       List utilidadesViajes = remiseria.buscarTiposUtilidadViaje();
       if(utilidadesViajes != null)
       {
            UtilidadViaje aux = null;
            Iterator iter = utilidadesViajes.iterator();
            while (iter.hasNext()){
                aux = (UtilidadViaje) iter.next();
                cmbUtilidadViaje.addItem(aux.getNombreUtilidadViaje());
            }
       }
    }    
    public void cargarCiudadesCombo(Provincia unaProvincia)
    {
        cmbCiudad.removeAllItems();
        List ciudades = unaProvincia.buscarCiudades();
       if(ciudades != null){
        Ciudad aux = null;
        Iterator iter = ciudades.iterator();
        while (iter.hasNext()){
            aux = (Ciudad) iter.next();
            cmbCiudad.addItem(aux.getNombreCiudad());
        }
        //cmbCiudad.setSelectedIndex(0);
       }
    }
    public void cargarBarriosCombo(Ciudad unaCiudad){
        cmbBarrio.removeAllItems();
        List barrios = unaCiudad.buscarBarrios();
        if(barrios != null)
        {
            Barrio aux = null;
            Iterator iter = barrios.iterator();
            while (iter.hasNext()){
                aux = (Barrio) iter.next();
                cmbBarrio.addItem(aux.getNombreBarrio());
            }
           // cmbBarrio.setSelectedIndex(0);
        }
    } 
    
public void cargarDireccionesAutoCompleter(Barrio unBarrio){
        cmbDireccion.removeAllItems();
        List direcciones = unBarrio.buscarDirecciones();
        if(direcciones != null)
        {
            DireccionViaje aux = null;
            Iterator iter = direcciones.iterator();
            while (iter.hasNext()){
                aux = (DireccionViaje) iter.next();
                cmbDireccion.addItem(aux.getDireccion());
            }
        }
    }

   public void agregarNuevaDireccion(){
      String nombrePais = remiseria.getUnDomicilio().getUnPais().getNombrePais();
      Pais unPais = remiseria.buscarPais(nombrePais);
      if (unPais != null)
      {
          String nombreProvincia = remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia();
          Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
          if(unaProvincia != null)
          {
              String nombreCiudad = cmbCiudad.getSelectedItem().toString();
              if(!nombreCiudad.isEmpty())
              {
                 Ciudad unaCiudad = unaProvincia.buscarCiudad(nombreCiudad);
                 if(unaCiudad != null)
                 {
                    String nombreBarrio = cmbBarrio.getSelectedItem().toString();
                    if(!nombreBarrio.isEmpty())
                    {
                        Barrio unBarrio = unaCiudad.buscarBarrio(nombreBarrio);
                        String direccion = cmbDireccion.getSelectedItem().toString();
                        if(unBarrio.buscarDireccionViaje(direccion)==null){
                            unBarrio.agregarDireccion(direccion);
                            this.cargarDireccionesAutoCompleter(unBarrio);
                            JOptionPane.showMessageDialog(null,"Se registro una nueva Dirección"," ",JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                        {
                                JOptionPane.showMessageDialog(null,"Ya existe la Dirección","",JOptionPane.ERROR_MESSAGE);

                        }
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
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbZona = new javax.swing.JComboBox();
        btnZona = new javax.swing.JButton();
        cmbInicio = new javax.swing.JComboBox();
        cmbUtilidadViaje = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        btnUtilidad = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        cmbCliente = new javax.swing.JComboBox();
        btnCliente = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cmbCiudad = new javax.swing.JComboBox();
        btnCiudad = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cmbBarrio = new javax.swing.JComboBox();
        btnBarrio = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cmbDireccion = new javax.swing.JComboBox();
        btnAgregarDireccion = new javax.swing.JButton();
        btnDirreccion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnDirecciónCliente = new javax.swing.JButton();
        cmbDireccionCliente = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtKilometroInicial = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

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
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel9.setText("Inicio de viaje");

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

        jPanel8.setBackground(new java.awt.Color(117, 150, 227));
        jPanel8.setForeground(new java.awt.Color(117, 150, 227));

        jLabel5.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel5.setText("Asignación del viaje:");

        jLabel10.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel10.setText("Zona:");

        cmbZona.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbZonaActionPerformed(evt);
            }
        });

        btnZona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZonaActionPerformed(evt);
            }
        });

        cmbInicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbInicio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Base", "Particular" }));

        cmbUtilidadViaje.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbUtilidadViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUtilidadViajeActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel7.setText("Utilidad:");

        btnUtilidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnUtilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUtilidadActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(117, 150, 227));

        cmbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbClienteActionPerformed(evt);
            }
        });

        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel4.setText("Cliente:");

        jPanel5.setBackground(new java.awt.Color(117, 150, 227));

        cmbCiudad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCiudadActionPerformed(evt);
            }
        });

        btnCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCiudadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel3.setText("Ciudad:");

        jPanel4.setBackground(new java.awt.Color(117, 150, 227));

        cmbBarrio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbBarrio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBarrioItemStateChanged(evt);
            }
        });
        cmbBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBarrioActionPerformed(evt);
            }
        });

        btnBarrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarrioActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel2.setText("Barrio:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2))
            .addComponent(btnBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBackground(new java.awt.Color(117, 150, 227));

        cmbDireccion.setEditable(true);
        cmbDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDireccionActionPerformed(evt);
            }
        });
        cmbDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cmbDireccionKeyTyped(evt);
            }
        });

        btnAgregarDireccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnAgregarDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDireccionActionPerformed(evt);
            }
        });

        btnDirreccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnDirreccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirreccionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(cmbDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnAgregarDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnDirreccion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnDirreccion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btnAgregarDireccion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Dirección:");

        jPanel2.setBackground(new java.awt.Color(117, 150, 227));

        btnDirecciónCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnDirecciónCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirecciónClienteActionPerformed(evt);
            }
        });

        cmbDireccionCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDireccionClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(cmbDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnDirecciónCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDirecciónCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(cmbDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel6.setText("Dirección:");

        jPanel9.setBackground(new java.awt.Color(117, 150, 227));

        txtKilometroInicial.setEditable(false);
        txtKilometroInicial.setBackground(new java.awt.Color(204, 204, 204));
        txtKilometroInicial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(txtKilometroInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtKilometroInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel8.setText("Kilometraje Inicial:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbZona, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, 0)
                                        .addComponent(btnZona, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                        .addComponent(cmbUtilidadViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(btnUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cmbZona, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnZona, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbUtilidadViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(btnUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addGap(5, 5, 5)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel6)))
                .addGap(3, 3, 3)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(30, Short.MAX_VALUE))
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
                        .addGap(4, 4, 4)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnUtilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUtilidadActionPerformed
        // TODO add your handling code here:
        JDialogUtilidadViajes ventana = new JDialogUtilidadViajes(parent, modal, cmbUtilidadViaje , remiseria);
        ventana.show();
    }//GEN-LAST:event_btnUtilidadActionPerformed

    private void btnCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCiudadActionPerformed
        // TODO add your handling code here:
        Pais unPais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
        Provincia unaProvincia = unPais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia());
        JDialogCiudad ventana = new JDialogCiudad(parent, modal,cmbCiudad, unaProvincia,remiseria);
        ventana.show();
    }//GEN-LAST:event_btnCiudadActionPerformed

    private void cmbCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCiudadActionPerformed
        // TODO add your handling code here:
        try
        {
            if (cmbCiudad.getItemCount() != 0) 
            {
                cmbBarrio.removeAllItems();
                cmbDireccion.removeAllItems();
                String nombrePais = remiseria.getUnDomicilio().getUnPais().getNombrePais();
                Pais unPais = remiseria.buscarPais(nombrePais);
                if (unPais != null)
                {
                          String nombreProvincia = remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia();
                          Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
                          if(unaProvincia != null)
                          {
                                      String nombreCiudad = cmbCiudad.getSelectedItem().toString();
                                      if(!nombreCiudad.isEmpty())
                                      {
                                          Ciudad unaCiudad = unaProvincia.buscarCiudad(nombreCiudad);
                                          if(unaCiudad != null)
                                          {
                                              this.cargarBarriosCombo(unaCiudad);
                                          }
                                      }
                          }
                }
            }     
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Error al cargar las ciudades", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmbCiudadActionPerformed

    private void btnBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarrioActionPerformed
        // TODO add your handling code here:
        try{
            //if(cmbCiudad.getSelectedItem().toString()!= ""){
                Pais unPais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
                Provincia unaProvincia = unPais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia());
                Ciudad unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
                JDialogBarrio ventana = new JDialogBarrio (parent,modal,cmbBarrio,unaCiudad,remiseria );
                ventana.show();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado una Ciudad"," ",JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_btnBarrioActionPerformed

    private void btnDirreccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirreccionActionPerformed
        // TODO add your handling code here:
        try{
            //if(cmbCiudad.getSelectedItem().toString()!= ""){
                Pais unPais = remiseria.buscarPais(remiseria.getUnDomicilio().getUnPais().getNombrePais());
                Provincia unaProvincia = unPais.buscarProvincia(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia());
                Ciudad unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
                Barrio unBarrio = unaCiudad.buscarBarrio(cmbBarrio.getSelectedItem().toString());
                JDialogDireccion ventana = new JDialogDireccion (parent,modal,cmbDireccion,unBarrio,remiseria);
                ventana.show();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado un Barrio"," ",JOptionPane.ERROR_MESSAGE);
            }        
    }//GEN-LAST:event_btnDirreccionActionPerformed

    private void cmbBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBarrioActionPerformed
//        // TODO add your handling code here:
        try
        {
            if (cmbBarrio.getItemCount()!= 0) 
            {
                String nombrePais = remiseria.getUnDomicilio().getUnPais().getNombrePais();
                Pais unPais = remiseria.buscarPais(nombrePais);
                if (unPais != null)
                {
                          String nombreProvincia = remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia();
                          Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
                          if(unaProvincia != null)
                          {
                                      String nombreCiudad = cmbCiudad.getSelectedItem().toString();
                                      if(!nombreCiudad.isEmpty())
                                      {
                                          Ciudad unaCiudad = unaProvincia.buscarCiudad(nombreCiudad);
                                          if(unaCiudad != null)
                                          {
                                              String nombreBarrio = cmbBarrio.getSelectedItem().toString();
                                              if(!nombreBarrio.isEmpty())
                                              {
                                                  Barrio unBarrio = unaCiudad.buscarBarrio(nombreBarrio);
                                                  if(unBarrio != null)
                                                  {
                                                      this.cargarDireccionesAutoCompleter(unBarrio);
                                                  }
                                              }
                                          }
                                      }
                          }
                }
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar los Barrios");
        }
    }//GEN-LAST:event_cmbBarrioActionPerformed

    private void btnAgregarDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDireccionActionPerformed
        // TODO add your handling code here:
        if(!cmbDireccion.getSelectedItem().toString().isEmpty() || cmbDireccion.getSelectedItem().toString().equals("")|| cmbDireccion.getSelectedItem().toString() != null ||  cmbDireccion.getSelectedItem() != "" )
        {
            this.agregarNuevaDireccion();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado la Dirección"," ",JOptionPane.ERROR_MESSAGE);
            
        }
    }//GEN-LAST:event_btnAgregarDireccionActionPerformed

    private void cmbUtilidadViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUtilidadViajeActionPerformed
        // TODO add your handling code here:
        try{
            if(cmbUtilidadViaje.getItemCount()!= 0)
            {
                if(((remiseria.buscarTiposUtilidadViaje(cmbUtilidadViaje.getSelectedItem().toString()).getNombreUtilidadViaje()).toUpperCase().equals("CLIENTE")))
                {
                    cmbCiudad.setEnabled(false);
                    cmbBarrio.setEnabled(false);
                    btnCiudad.setEnabled(false);
                    btnBarrio.setEnabled(false);  
                    jLabel1.setVisible(false);
                    jLabel4.setVisible(true);
                    jLabel6.setVisible(true);
                    btnCliente.setVisible(true);
                    cmbDireccion.setVisible(false);
                    btnAgregarDireccion.setVisible(false);
                    btnDirreccion.setVisible(false);
                    cmbDireccionCliente.setVisible(true);  
                    btnDirecciónCliente.setEnabled(true);
                    btnDirecciónCliente.setVisible(true);
                    cmbCliente.setVisible(true);
                    cmbCliente.removeAllItems();
                    Collection clientes = remiseria.buscarClientes();
                    if(clientes != null)
                    {
                        Cliente aux = null;
                        Iterator iter = clientes.iterator();
                        while (iter.hasNext())
                        {
                            aux = (Cliente) iter.next();
                            cmbCliente.addItem(aux);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"No hay Cliente registrados, por favor registre al menos un Cliente para iniciar el viaje"," ",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {

                    btnCiudad.setEnabled(true);
                    btnBarrio.setEnabled(true);                        
                    btnCliente.setVisible(false);
                    cmbCiudad.setEnabled(true);
                    cmbBarrio.setEnabled(true);
                    jLabel1.setVisible(true);
                    cmbDireccion.setVisible(true);
                    btnAgregarDireccion.setVisible(true);
                    btnDirreccion.setVisible(true);
                    cmbDireccionCliente.setVisible(false);
                    cmbCliente.setVisible(false);
                    jLabel6.setVisible(false);
                    jLabel4.setVisible(false);
                    btnDirecciónCliente.setEnabled(false);
                    btnDirecciónCliente.setVisible(false);
                }
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al elegir la Utilidad de Viaje", null,JOptionPane.ERROR_MESSAGE);
        }
                
    }//GEN-LAST:event_cmbUtilidadViajeActionPerformed

    private void cmbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbClienteActionPerformed
        // TODO add your handling code here:
        try
        {
            cmbCiudad.setEnabled(false);
            cmbBarrio.setEnabled(false);
            btnCiudad.setEnabled(false);
            btnBarrio.setEnabled(false);
            if(cmbCliente.getItemCount() != 0)
            {
                cmbDireccionCliente.removeAllItems();
                String nombre = cmbUtilidadViaje.getSelectedItem().toString();
                if(nombre.equals("Cliente"))
                {
                    Cliente unCliente =(Cliente) (cmbCliente.getSelectedItem());
                    Cliente cliente = remiseria.buscarCliente(unCliente.getDni());
                    if(cliente != null)
                    {
                        Collection direcciones = cliente.getDomicilios().values();
                        Domicilio aux = null;
                        Iterator iter= direcciones.iterator();
                        while(iter.hasNext())
                        {
                            aux = (Domicilio) iter.next();
                            cmbDireccionCliente.addItem(aux);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar la direccion Cliente seleccionado");
        }
    }//GEN-LAST:event_cmbClienteActionPerformed

    private void cmbBarrioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBarrioItemStateChanged
        // TODO add your handling code here:
        // TODO add your handling code here:
     
    }//GEN-LAST:event_cmbBarrioItemStateChanged

    private void cmbDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDireccionKeyTyped
        // TODO add your handling code here:
         
    }//GEN-LAST:event_cmbDireccionKeyTyped

    private void btnDirecciónClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirecciónClienteActionPerformed
        try
        {
            if(cmbCliente.getSelectedItem()!= null)
            {
                Cliente unCliente = (Cliente) cmbCliente.getSelectedItem();
                JDialogEditarDomicilios ventana = new JDialogEditarDomicilios(parent, modal, remiseria, unCliente,cmbDireccionCliente,cmbCiudad,cmbBarrio);
                ventana.show();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar ingresar a la pantalla de Direcciones del Cliente", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDirecciónClienteActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        // TODO add your handling code here:
        JDialogCliente ventana = new JDialogCliente(parent,modal,remiseria,utilidades,unMaestro,unOperario,cmbCliente);
        ventana.show();     
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        this.iniciarViaje();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZonaActionPerformed
        // TODO add your handling code here:
        JDialogZonas ventana = new JDialogZonas(parent,modal,remiseria,cmbZona,unMaestro, unOperario);
        ventana.show();        
    }//GEN-LAST:event_btnZonaActionPerformed

    private void cmbZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbZonaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbZonaActionPerformed

    private void cmbDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDireccionActionPerformed

    private void cmbDireccionClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDireccionClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDireccionClienteActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogConfiguracionEsporadica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogConfiguracionEsporadica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogConfiguracionEsporadica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogConfiguracionEsporadica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
       
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogConfiguracionEsporadica dialog = new JDialogConfiguracionEsporadica(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });  */
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregarDireccion;
    private javax.swing.JButton btnBarrio;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCiudad;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnDirecciónCliente;
    private javax.swing.JButton btnDirreccion;
    private javax.swing.JButton btnUtilidad;
    private javax.swing.JButton btnZona;
    private javax.swing.JComboBox cmbBarrio;
    private javax.swing.JComboBox cmbCiudad;
    private javax.swing.JComboBox cmbCliente;
    private javax.swing.JComboBox cmbDireccion;
    private javax.swing.JComboBox cmbDireccionCliente;
    private javax.swing.JComboBox cmbInicio;
    private javax.swing.JComboBox cmbUtilidadViaje;
    private javax.swing.JComboBox cmbZona;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField txtKilometroInicial;
    // End of variables declaration//GEN-END:variables
}
