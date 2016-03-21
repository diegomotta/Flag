/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;
import Clases.AvisoReserva;
import Clases.AvisoReserva2;
import Clases.Barrio;
import Clases.Ciudad;
import Clases.CodArea;
import Clases.CodPais;
import Clases.Foto;
import Clases.Maestro;
import Clases.MarcarTarjeta;
import Clases.Pais;
import Clases.Provincia;
import Clases.Remiseria;
import Clases.TipoTelefono;
import Clases.Utilidad;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
//import org.pushingpixels.substance.api.SubstanceLookAndFeel;
//import org.pushingpixels.substance.api.skin.SkinInfo;
import org.jvnet.substance.*;
import org.jvnet.substance.skin.SkinInfo;
import org.jvnet.substance.watermark.*;

/**
 *
 * @author garba
 */
public class JDialogCustomizar extends javax.swing.JDialog {
private	ArrayList<String> skins;
private java.awt.Frame parent;
private boolean modal;	
private	JComboBox<String> comboSkins;
private Remiseria remiseria;
private Maestro unUsuarioMaestro;
private Utilidad utilidades;
private VentanaPrincipal vent;
private Foto unLogo;
private AvisoReserva hilo1;
private AvisoReserva2 hilo2;
    /**
     * Creates new form JDialogCustomizar
     */
    public JDialogCustomizar(java.awt.Frame parent, boolean modal, Remiseria remiseria, Maestro unUsuarioMaestro,Utilidad utilidades,VentanaPrincipal vent) {
        super(parent, modal);
        this.skins = null;
        this.comboSkins = null;
        this.unLogo = null;
        this.remiseria = remiseria;
        this.unUsuarioMaestro = unUsuarioMaestro;
        this.utilidades = utilidades;
        this.vent = vent;
        initComponents();
        this.cargarCodPaisCombo();
        this.cargarPaisCombo();
        this.cargarTipoTelefonoCombo();
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.cargarSkinsCombo();
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.txtEdadChofer.setText((String.valueOf(remiseria.getUnaEdad().getEdadMinimaChofer())));
        this.txtEdadOperario.setText((String.valueOf(remiseria.getUnaEdad().getEdadMinimaOperario())));
        this.txtCierreDeKilometraje.setText(String.valueOf(remiseria.getUnCierreKilometraje().getValor()/10));
        this.txtPorcentajeChofer.setText(String.valueOf(remiseria.getUnPorcentajeChofer().getPorcentajeChofer()*100));
        this.txtPrecioAlquilerMovil.setText(String.valueOf(remiseria.getUnPrecioAlquiler().getPrecio()));
        txtPagoOperarios.setText(String.valueOf(remiseria.getUnPagoOperario().getPrecio()));
        txtBajadaBandera.setText(String.valueOf(remiseria.getUnaBajadaBandera().getValorBajadaBandera()));
        txtPrecioRadio.setText(String.valueOf(remiseria.getUnPagoRadio().getValor()));
        this.txtTiempoEspera.setText(String.valueOf(remiseria.getUnTiempoEspera().getValorTiempo()/60000));
        this.txtTiempoEspera1.setText(String.valueOf(remiseria.getUnTiempoEspera2().getValorTiempo()/60000));
        String opcion = remiseria.getUnPagoOperario().getTipoPago();
        
        switch (opcion) 
        {
            case "por día":
                rbnPorDia.setSelected(true);
                break;
            case "por hora":
                rbnPorHora.setSelected(true);
                break;
        }
        btnCancelar.setVisible(false);
        if(vent == null)
        {
            btnCancelar.setVisible(true);
        }
        if(remiseria.getLogo() == null)
        {
            unLogo = new Foto("src/Imagenes/unnamed.png");          
            ImageIcon fot = new ImageIcon(unLogo.getImage());
            Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbl_logo.getWidth(), lbl_logo.getHeight(), Image.SCALE_DEFAULT));
            lbl_logo.setIcon(icono);
            this.repaint();
        }
        if(remiseria.getNombre()!= null && remiseria.getUnDomicilio() != null && remiseria.getUnTelefono()!= null && remiseria.getLogo() != null)
        {
            txtNombreEmpresa.setText(remiseria.getNombre());
            cmbPais.setSelectedItem(remiseria.getUnDomicilio().getUnPais().getNombrePais());
            cmbProvincia.setSelectedItem(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia());
            cmbCiudad.setSelectedItem(remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad());
            txtCalle.setText(remiseria.getUnDomicilio().getCalle());
            txtNumeroCasa.setText(remiseria.getUnDomicilio().getNroCasa());
            cmbCodPais.setSelectedItem(remiseria.getUnTelefono().getUnCodPais().getCodPais());
            cmbCodArea.setSelectedItem(remiseria.getUnTelefono().getUnCodArea().getCodArea());
            txtNumeroTel.setText(remiseria.getUnTelefono().getNroTelefonico());
            cmbTipodeTelefono.setSelectedItem(remiseria.getUnTelefono().getUnTipoTelefono().getNombreTipoTelefono());
            ImageIcon fot = new ImageIcon(remiseria.getLogo().getImage());
            Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbl_logo.getWidth(), lbl_logo.getHeight(), Image.SCALE_DEFAULT));
            lbl_logo.setIcon(icono);   
            lbl_logo.repaint();
        }
        AutoCompleteDecorator.decorate(this.cmbPais);
        AutoCompleteDecorator.decorate(this.cmbProvincia);
        AutoCompleteDecorator.decorate(this.cmbCiudad);
        AutoCompleteDecorator.decorate(this.cmbCodPais);
        AutoCompleteDecorator.decorate(this.cmbCodArea);
        AutoCompleteDecorator.decorate(this.cmbTipodeTelefono);
    }
    
    private void agregarDatosRemiseria()
    {
        boolean ok = true;
        Pais unPais = null;
        Provincia unaProvincia = null;
        Ciudad unaCiudad = null;
        CodPais unCodPais = null;
        CodArea unCodArea = null;
        TipoTelefono unTipoTelefono = null;
        String nombreEmpresa = null;
        String calle = null;
        String nroCasa = null;
        String nroTel = null;
        
        Foto unFoto;
        
        if (unLogo != null)
        {
                unFoto = unLogo;
        }
        else 
        {
            if (remiseria != null) 
            {
               unFoto = remiseria.getLogo();
            }
            else
            {
               unFoto = null;
            }
         }
        
        if(txtNombreEmpresa.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado el Nombre de la Empresa","",JOptionPane.ERROR_MESSAGE);
            ok= false;
            txtNombreEmpresa.setBackground(Color.red);
        }
        else
        {  
            nombreEmpresa = txtNombreEmpresa.getText();
        }
            
        if(cmbPais.getSelectedItem() != null)
        {
            unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado el Pais","",JOptionPane.ERROR_MESSAGE);
            ok= false;                                     
        }

        if(cmbProvincia.getSelectedItem() != null || unaProvincia != null)
        {
            unaProvincia = unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString());
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado el Provincia","",JOptionPane.ERROR_MESSAGE);
            ok= false; 
        }
        if(cmbCiudad.getSelectedItem()!=null|| unaCiudad != null)
        {                
            unaCiudad = unaProvincia.buscarCiudad(cmbCiudad.getSelectedItem().toString());
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado la Ciudad","",JOptionPane.ERROR_MESSAGE);
            ok= false;                
        }


       if(txtCalle.getText().toString().isEmpty())
       {
           JOptionPane.showMessageDialog(null,"No ha ingresado la Calle","",JOptionPane.ERROR_MESSAGE);
           ok= false;
           txtCalle.setBackground(Color.red);
       }
       else
       {
           calle = txtCalle.getText();
       }

        if(txtNumeroCasa.getText().toString().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado el Número de Domicilio","",JOptionPane.ERROR_MESSAGE);
            ok= false;
            txtNumeroCasa.setBackground(Color.red);
        } 
        else
        {
            nroCasa = txtNumeroCasa.getText();
        }

        if((cmbCodPais.getSelectedItem()!= null))
        {
           unCodPais = remiseria.buscarCodigoPais(cmbCodPais.getSelectedItem().toString());
        }
        else
        {
             JOptionPane.showMessageDialog(null,"No ha seleccionado el Código de País del Teléfono","",JOptionPane.ERROR_MESSAGE);
             ok = false;
        }

        if(cmbCodArea.getSelectedItem()!= null|| unCodArea != null)
        {
            unCodArea = unCodPais.buscarCodArea(cmbCodArea.getSelectedItem().toString());
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado el Código de Área","",JOptionPane.ERROR_MESSAGE);
            ok= false;                 
        }

        if(txtNumeroTel.getText().toString().isEmpty())
        {
             JOptionPane.showMessageDialog(null,"No ha ingresado el N° Personal del Teléfono","",JOptionPane.ERROR_MESSAGE);
             ok = false;
             txtNumeroTel.setBackground(java.awt.Color.red);
        }
        else
        {
            nroTel = txtNumeroTel.getText();
        }

        if(cmbTipodeTelefono.getSelectedItem()!= null)
        {
            unTipoTelefono = remiseria.buscarTipoTelefono(cmbTipodeTelefono.getSelectedItem().toString());

        }
        else
        {
              JOptionPane.showMessageDialog(null,"No ha seleccionado el Tipo de Teléfono","",JOptionPane.ERROR_MESSAGE);
              ok = false;
        }
        
        if(ok == true)
        {
            if((remiseria.cargarDatosRemiseria(nombreEmpresa, unPais, unaProvincia, unaCiudad,calle,nroCasa,unCodPais,unCodArea,nroTel,unTipoTelefono,unFoto))==true)
            {
                        JOptionPane.showMessageDialog(null,"Datos guardados exitosamente!"," ",JOptionPane.INFORMATION_MESSAGE);
            }
         }
    
    }

     public void cargarSkinsCombo(){
        //cmbMascaras.addItem("");
        String listado[];
	skins=new ArrayList<>();
	TreeMap<String, SkinInfo> pieles= (TreeMap<String, SkinInfo>) SubstanceLookAndFeel.getAllSkins();
	Iterator<Map.Entry<String,SkinInfo>> it=pieles.entrySet().iterator();
	while(it.hasNext()){
		Map.Entry<String,SkinInfo> e = (Map.Entry<String,SkinInfo>)it.next();
		skins.add(e.getValue().getClassName());
	}
	listado=new String[skins.size()];
	for(int i=0;i<skins.size();i++)
        {
		listado[i]=skins.get(i);
                cmbMascaras.addItem(listado[i]);
	}
        cmbMascaras.addItem("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        cmbMascaras.addItem("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        cmbMascaras.addItem(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel().toString());
        cmbMascaras.setSelectedIndex(0);
    }
     

public void cargarProvinciasCombo(Pais unPais)
    {
        cmbProvincia.removeAllItems();
        List provincias = unPais.buscarProvincias();
        if(provincias != null)
        {
            Provincia aux = null;
            Iterator iter = provincias.iterator();
            while (iter.hasNext())
            {
                aux = (Provincia) iter.next();
                cmbProvincia.addItem(aux.getNombreProvincia());
            }
        }
    }
    
    public void cargarCiudadesCombo(Provincia unaProvincia)
    {
        cmbCiudad.removeAllItems();
        List ciudades = unaProvincia.buscarCiudades();
        if(ciudades != null)
        {
            Ciudad aux = null;
            Iterator iter = ciudades.iterator();
            while (iter.hasNext())
            {
                aux = (Ciudad) iter.next();
                cmbCiudad.addItem(aux.getNombreCiudad());
            }
        }
    }
      
    public void cargarCodigoProvinciaCombo( CodPais unCodPais)
    {
        cmbCodArea.removeAllItems();
        List codigoProvincia = unCodPais.buscarCodigosAreas();
        if(codigoProvincia!= null)
        {
            CodArea aux = null;
            Iterator iter = codigoProvincia.iterator();
            while (iter.hasNext())
            {
                aux = (CodArea) iter.next();
                cmbCodArea.addItem(aux.getCodArea());
            }
        }       
    }
    
     public void cargarPaisCombo()
     {
        cmbPais.removeAllItems();
        List paises = remiseria.buscarPaises();
        if(paises != null)
        {
            Pais aux = null;
            Iterator iter = paises.iterator();
            while (iter.hasNext())
            {
                aux = (Pais) iter.next();
                cmbPais.addItem(aux.getNombrePais());
            }
        }
    }
     
    public void cargarCodPaisCombo(){
        cmbCodPais.removeAllItems();
        List codigos = remiseria.buscarCodigosPaises();
        if(codigos != null)
        {
            CodPais aux = null;
            Iterator iter = codigos.iterator();
            while (iter.hasNext())
            {
                aux = (CodPais) iter.next();
                cmbCodPais.addItem(aux.getCodPais());
            }
        }
        
    }
    
    public void cargarTipoTelefonoCombo()
    {
        cmbTipodeTelefono.removeAllItems();
        List telefonos =remiseria.buscarTiposTelefonos();
        if(telefonos != null)
        {
            TipoTelefono aux = null;
            Iterator iter = telefonos.iterator();
            while (iter.hasNext())
            {
                aux = (TipoTelefono) iter.next();
                cmbTipodeTelefono.addItem(aux.getNombreTipoTelefono());
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnAceptar3 = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jXTaskPaneContainer3 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        cmbPais = new javax.swing.JComboBox();
        cmbProvincia = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cmbCiudad = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        txtNumeroCasa = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btnPais = new javax.swing.JButton();
        btnProvincia = new javax.swing.JButton();
        bnCiudad = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        cmbCodPais = new javax.swing.JComboBox();
        btnCodPais = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        cmbCodArea = new javax.swing.JComboBox();
        btnCodArea = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        txtNumeroTel = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        cmbTipodeTelefono = new javax.swing.JComboBox();
        btnTipoTelefono = new javax.swing.JButton();
        txtNombreEmpresa = new javax.swing.JTextField();
        btnAceptar2 = new javax.swing.JButton();
        lbl_logo = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jXTaskPaneContainer4 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtNickAdministrador = new javax.swing.JTextField();
        txtClaveAdministrador = new javax.swing.JPasswordField();
        btnRecuperar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jXTaskPaneContainer2 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEdadChofer = new javax.swing.JTextField();
        txtEdadOperario = new javax.swing.JTextField();
        btnAceptar1 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jXTaskPaneContainer5 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCierreDeKilometraje = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jXTaskPaneContainer9 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel16 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        txtBajadaBandera = new javax.swing.JTextField();
        btnBajadaBandera = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jXTaskPaneContainer7 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel14 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtPorcentajeChofer = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jXTaskPaneContainer6 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtPrecioAlquilerMovil = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jXTaskPaneContainer8 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel15 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtPagoOperarios = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        rbnPorDia = new javax.swing.JRadioButton();
        rbnPorHora = new javax.swing.JRadioButton();
        panel1 = new java.awt.Panel();
        jXTaskPaneContainer10 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel23 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        txtPrecioRadio = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        panel2 = new java.awt.Panel();
        jXTaskPaneContainer11 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel24 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        txtTiempoEspera = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        txtTiempoEspera1 = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbMascaras = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel2.setFont(new java.awt.Font("Goudy Old Style", 1, 24)); // NOI18N
        jLabel2.setText("Configuración del Sistema:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnAceptar3.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAceptar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Check.png"))); // NOI18N
        btnAceptar3.setText("Aceptar");
        btnAceptar3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptar3ActionPerformed(evt);
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

        jTabbedPane1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N

        jXTaskPaneContainer3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel8.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel8.setText("Nombre de Empresa:");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Domicilio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Goudy Old Style", 1, 14), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel24.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel24.setText("País:");

        cmbPais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaisActionPerformed(evt);
            }
        });

        cmbProvincia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvinciaActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel25.setText("Provincia:");

        jLabel26.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel26.setText("Ciudad:");

        cmbCiudad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel17.setText("Calle:");

        txtCalle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCalle.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCalle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCalleMouseClicked(evt);
            }
        });

        txtNumeroCasa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNumeroCasa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroCasa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumeroCasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumeroCasaMouseClicked(evt);
            }
        });
        txtNumeroCasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroCasaKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel18.setText("N°:");

        btnPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnPais.setBorderPainted(false);
        btnPais.setOpaque(false);
        btnPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaisActionPerformed(evt);
            }
        });

        btnProvincia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnProvincia.setBorderPainted(false);
        btnProvincia.setOpaque(false);
        btnProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProvinciaActionPerformed(evt);
            }
        });

        bnCiudad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        bnCiudad.setBorderPainted(false);
        bnCiudad.setOpaque(false);
        bnCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnCiudadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bnCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(14, 14, 14)
                        .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel18))
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCalle)
                    .addComponent(txtNumeroCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel24)
                        .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProvincia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNumeroCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)
                        .addComponent(cmbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bnCiudad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Telefono", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Goudy Old Style", 1, 14))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel30.setText("Cod. país:");

        cmbCodPais.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbCodPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodPaisActionPerformed(evt);
            }
        });

        btnCodPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnCodPais.setBorderPainted(false);
        btnCodPais.setOpaque(false);
        btnCodPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodPaisActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel31.setText("Cod. área:");

        cmbCodArea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnCodArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnCodArea.setBorderPainted(false);
        btnCodArea.setOpaque(false);
        btnCodArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodAreaActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel32.setText("N°:");

        txtNumeroTel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNumeroTel.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumeroTel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNumeroTel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumeroTelMouseClicked(evt);
            }
        });
        txtNumeroTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroTelActionPerformed(evt);
            }
        });
        txtNumeroTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroTelKeyTyped(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel33.setText("Tipo de teléfono:");

        cmbTipodeTelefono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnTipoTelefono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/gnome-control-center.png"))); // NOI18N
        btnTipoTelefono.setBorderPainted(false);
        btnTipoTelefono.setOpaque(false);
        btnTipoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTipoTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cmbCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cmbCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumeroTel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cmbTipodeTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(196, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCodPais, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnCodArea, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbTipodeTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTipoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumeroTel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(19, 19, 19))
        );

        txtNombreEmpresa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombreEmpresa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombreEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreEmpresaMouseClicked(evt);
            }
        });

        btnAceptar2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAceptar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        btnAceptar2.setText("Guardar");
        btnAceptar2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptar2ActionPerformed(evt);
            }
        });

        lbl_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_logo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lbl_logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_logoMouseReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel12.setText("Logotipo del Ente:");

        javax.swing.GroupLayout jXTaskPaneContainer3Layout = new javax.swing.GroupLayout(jXTaskPaneContainer3);
        jXTaskPaneContainer3.setLayout(jXTaskPaneContainer3Layout);
        jXTaskPaneContainer3Layout.setHorizontalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreEmpresa))
                            .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                                .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAceptar2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jXTaskPaneContainer3Layout.setVerticalGroup(
            jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jXTaskPaneContainer3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(6, 6, 6)
                .addGroup(jXTaskPaneContainer3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Datos de la Empresa", jPanel8);

        jXTaskPaneContainer4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel9.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel9.setText("Datos del Administrador del Sistema:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jLabel10.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel10.setText("Nick:");

        jLabel11.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel11.setText("Clave:");

        txtNickAdministrador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNickAdministrador.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNickAdministrador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNickAdministradorMouseClicked(evt);
            }
        });
        txtNickAdministrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNickAdministradorKeyTyped(evt);
            }
        });

        txtClaveAdministrador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtClaveAdministrador.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtClaveAdministrador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtClaveAdministradorMouseClicked(evt);
            }
        });

        btnRecuperar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnRecuperar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/object-rotate-right.png"))); // NOI18N
        btnRecuperar.setText("Recuperar datos");
        btnRecuperar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRecuperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecuperarActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        btnModificar.setText("Guardar");
        btnModificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtApellido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtApellido.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel27.setText("Nombre:");

        jLabel28.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel28.setText("Apellido:");

        javax.swing.GroupLayout jXTaskPaneContainer4Layout = new javax.swing.GroupLayout(jXTaskPaneContainer4);
        jXTaskPaneContainer4.setLayout(jXTaskPaneContainer4Layout);
        jXTaskPaneContainer4Layout.setHorizontalGroup(
            jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer4Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer4Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtClaveAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTaskPaneContainer4Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel28))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNickAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 275, Short.MAX_VALUE)
                        .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRecuperar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer4Layout.setVerticalGroup(
            jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer4Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addGap(5, 5, 5)
                        .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(5, 5, 5)
                        .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNickAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTaskPaneContainer4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtClaveAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jXTaskPaneContainer4Layout.createSequentialGroup()
                        .addComponent(btnRecuperar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(310, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Datos del Administrador", jPanel9);

        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N

        jXTaskPaneContainer2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel4.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel4.setText("Edad mínima del Chofer:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 257, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jLabel5.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel5.setText("Edad mínima del Operario:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jLabel6.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel6.setText("Edad:");

        jLabel7.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel7.setText("Edad:");

        txtEdadChofer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEdadChofer.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEdadChofer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtEdadChofer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEdadChoferMouseClicked(evt);
            }
        });
        txtEdadChofer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEdadChoferKeyTyped(evt);
            }
        });

        txtEdadOperario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEdadOperario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEdadOperario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtEdadOperario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEdadOperarioMouseClicked(evt);
            }
        });
        txtEdadOperario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEdadOperarioKeyTyped(evt);
            }
        });

        btnAceptar1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAceptar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        btnAceptar1.setText("Guardar");
        btnAceptar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer2Layout = new javax.swing.GroupLayout(jXTaskPaneContainer2);
        jXTaskPaneContainer2.setLayout(jXTaskPaneContainer2Layout);
        jXTaskPaneContainer2Layout.setHorizontalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                        .addGroup(jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEdadChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEdadOperario, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAceptar1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jXTaskPaneContainer2Layout.setVerticalGroup(
            jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtEdadChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtEdadOperario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAceptar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(279, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Edad mínima", jPanel17);

        jXTaskPaneContainer5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel13.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel13.setText("Precio por Ficha:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 257, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        txtCierreDeKilometraje.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCierreDeKilometraje.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCierreDeKilometraje.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCierreDeKilometraje.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCierreDeKilometrajeMouseClicked(evt);
            }
        });
        txtCierreDeKilometraje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCierreDeKilometrajeKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel15.setText("Precio: $");

        jButton1.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer5Layout = new javax.swing.GroupLayout(jXTaskPaneContainer5);
        jXTaskPaneContainer5.setLayout(jXTaskPaneContainer5Layout);
        jXTaskPaneContainer5Layout.setHorizontalGroup(
            jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCierreDeKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jXTaskPaneContainer5Layout.setVerticalGroup(
            jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jXTaskPaneContainer5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCierreDeKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(332, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Precio por ficha", jPanel18);

        jXTaskPaneContainer9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel29.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel29.setText("Precio de Bajada de Bandera:");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 233, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        txtBajadaBandera.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtBajadaBandera.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtBajadaBandera.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtBajadaBandera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBajadaBanderaMouseClicked(evt);
            }
        });
        txtBajadaBandera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBajadaBanderaKeyTyped(evt);
            }
        });

        btnBajadaBandera.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnBajadaBandera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        btnBajadaBandera.setText("Guardar");
        btnBajadaBandera.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBajadaBandera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajadaBanderaActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel35.setText("Precio: $");

        javax.swing.GroupLayout jXTaskPaneContainer9Layout = new javax.swing.GroupLayout(jXTaskPaneContainer9);
        jXTaskPaneContainer9.setLayout(jXTaskPaneContainer9Layout);
        jXTaskPaneContainer9Layout.setHorizontalGroup(
            jXTaskPaneContainer9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer9Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBajadaBandera, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBajadaBandera, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jXTaskPaneContainer9Layout.setVerticalGroup(
            jXTaskPaneContainer9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jXTaskPaneContainer9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBajadaBandera, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBajadaBandera, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(332, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Precio de bajada de bandera", jPanel19);

        jXTaskPaneContainer7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel20.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel20.setText("Porcentaje de pago a Choferes:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 192, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        txtPorcentajeChofer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPorcentajeChofer.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPorcentajeChofer.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPorcentajeChofer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPorcentajeChoferMouseClicked(evt);
            }
        });
        txtPorcentajeChofer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPorcentajeChoferKeyTyped(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel21.setText("Porcentaje:");

        jButton3.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        jButton3.setText("Guardar");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel36.setText("%");

        javax.swing.GroupLayout jXTaskPaneContainer7Layout = new javax.swing.GroupLayout(jXTaskPaneContainer7);
        jXTaskPaneContainer7.setLayout(jXTaskPaneContainer7Layout);
        jXTaskPaneContainer7Layout.setHorizontalGroup(
            jXTaskPaneContainer7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTaskPaneContainer7Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jXTaskPaneContainer7Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPorcentajeChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jXTaskPaneContainer7Layout.setVerticalGroup(
            jXTaskPaneContainer7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jXTaskPaneContainer7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPorcentajeChofer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(333, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Porcentaje de Pago a choferes", jPanel20);

        jXTaskPaneContainer6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel16.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel16.setText("Precio de alquiler de Móvil:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 192, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        txtPrecioAlquilerMovil.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrecioAlquilerMovil.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPrecioAlquilerMovil.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPrecioAlquilerMovil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPrecioAlquilerMovilMouseClicked(evt);
            }
        });
        txtPrecioAlquilerMovil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioAlquilerMovilKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel19.setText("Precio: $");

        jButton2.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        jButton2.setText("Guardar");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer6Layout = new javax.swing.GroupLayout(jXTaskPaneContainer6);
        jXTaskPaneContainer6.setLayout(jXTaskPaneContainer6Layout);
        jXTaskPaneContainer6Layout.setHorizontalGroup(
            jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer6Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioAlquilerMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jXTaskPaneContainer6Layout.setVerticalGroup(
            jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jXTaskPaneContainer6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioAlquilerMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(332, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Precio de alquiler de móvil", jPanel21);

        jXTaskPaneContainer8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel22.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel22.setText("Precio de pago a Operarios:");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        txtPagoOperarios.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPagoOperarios.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPagoOperarios.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPagoOperarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPagoOperariosMouseClicked(evt);
            }
        });
        txtPagoOperarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagoOperariosKeyTyped(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel23.setText("Precio: $");

        jButton4.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        jButton4.setText("Guardar");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        rbnPorDia.setBackground(new java.awt.Color(117, 150, 227));
        buttonGroup1.add(rbnPorDia);
        rbnPorDia.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        rbnPorDia.setText("Por día");

        rbnPorHora.setBackground(new java.awt.Color(117, 150, 227));
        buttonGroup1.add(rbnPorHora);
        rbnPorHora.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        rbnPorHora.setText("Por hora");

        javax.swing.GroupLayout jXTaskPaneContainer8Layout = new javax.swing.GroupLayout(jXTaskPaneContainer8);
        jXTaskPaneContainer8.setLayout(jXTaskPaneContainer8Layout);
        jXTaskPaneContainer8Layout.setHorizontalGroup(
            jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer8Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPagoOperarios, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbnPorDia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbnPorHora, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 61, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jXTaskPaneContainer8Layout.setVerticalGroup(
            jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPagoOperarios, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(rbnPorDia)
                    .addComponent(rbnPorHora))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(336, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Precio de pago a operarios", jPanel22);

        jXTaskPaneContainer10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel34.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel34.setText("Precio de pago de la base");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 192, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        txtPrecioRadio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrecioRadio.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPrecioRadio.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPrecioRadio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPrecioRadioMouseClicked(evt);
            }
        });
        txtPrecioRadio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioRadioKeyTyped(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel37.setText("Precio: $");

        jButton5.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        jButton5.setText("Guardar");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer10Layout = new javax.swing.GroupLayout(jXTaskPaneContainer10);
        jXTaskPaneContainer10.setLayout(jXTaskPaneContainer10Layout);
        jXTaskPaneContainer10Layout.setHorizontalGroup(
            jXTaskPaneContainer10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer10Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jXTaskPaneContainer10Layout.setVerticalGroup(
            jXTaskPaneContainer10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jXTaskPaneContainer10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(332, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Precio de pago de la base", panel1);

        jXTaskPaneContainer11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel38.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel38.setText("Aviso del vencimiento de la reserva");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 192, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        txtTiempoEspera.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTiempoEspera.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTiempoEspera.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTiempoEspera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTiempoEsperaMouseClicked(evt);
            }
        });
        txtTiempoEspera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTiempoEsperaKeyTyped(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel39.setText("Minutos:");

        jButton6.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        jButton6.setText("Guardar");
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        jButton7.setText("Guardar");
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel40.setText("Minutos:");

        txtTiempoEspera1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTiempoEspera1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTiempoEspera1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTiempoEspera1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTiempoEspera1MouseClicked(evt);
            }
        });
        txtTiempoEspera1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTiempoEspera1KeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel14.setText("Primer aviso:");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 257, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jLabel43.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel43.setText("Segundo aviso");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 257, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jXTaskPaneContainer11Layout = new javax.swing.GroupLayout(jXTaskPaneContainer11);
        jXTaskPaneContainer11.setLayout(jXTaskPaneContainer11Layout);
        jXTaskPaneContainer11Layout.setHorizontalGroup(
            jXTaskPaneContainer11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTaskPaneContainer11Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTiempoEspera1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jXTaskPaneContainer11Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTiempoEspera, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXTaskPaneContainer11Layout.setVerticalGroup(
            jXTaskPaneContainer11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jXTaskPaneContainer11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTiempoEspera, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTiempoEspera1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(268, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Aviso de vencimiento", panel2);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2))
        );

        jTabbedPane1.addTab("Parámetros específicos", jPanel10);

        jXTaskPaneContainer1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Goudy Old Style", 1, 18)); // NOI18N
        jLabel3.setText("Configurar apariencia:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 459, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3)
        );

        cmbMascaras.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Goudy Old Style", 0, 18)); // NOI18N
        jLabel1.setText("Máscara:");

        btnAceptar.setFont(new java.awt.Font("Goudy Old Style", 1, 14)); // NOI18N
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/package-install.png"))); // NOI18N
        btnAceptar.setText("Guardar");
        btnAceptar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTaskPaneContainer1Layout = new javax.swing.GroupLayout(jXTaskPaneContainer1);
        jXTaskPaneContainer1.setLayout(jXTaskPaneContainer1Layout);
        jXTaskPaneContainer1Layout.setHorizontalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbMascaras, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jXTaskPaneContainer1Layout.setVerticalGroup(
            jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTaskPaneContainer1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTaskPaneContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cmbMascaras, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(353, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Apariencia", jPanel11);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAceptar3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        try
        {
            if((cmbMascaras.getSelectedItem().toString().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"))||(cmbMascaras.getSelectedItem().toString().equals("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"))||(cmbMascaras.getSelectedItem().equals(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel().toString())))
            {
                 remiseria.cargarSkin(cmbMascaras.getSelectedItem().toString());
                 int eleccion = JOptionPane.showConfirmDialog(null, "Para aplicar esta Máscara deberá salir del sistema y volver a ejercutar el Sistema, desea salir?.");
		 if ( eleccion == 0) {
                    MarcarTarjeta unMarcarTarjeta = remiseria.buscarTarjetas();
                    if(unMarcarTarjeta.getUnMaestro()!= null)
                    {
                        remiseria.cargarSetMarcadoTarjeta(unMarcarTarjeta,remiseria.getVentPrincipal().obtenerHora());
                        remiseria.agregarLiquidacionPreviaOperario(null, unUsuarioMaestro);
                    }
			System.exit(0);
		}
            }
            else
            {
                
                SubstanceLookAndFeel.setSkin(skins.get(cmbMascaras.getSelectedIndex()));
                remiseria.cargarSkin(cmbMascaras.getSelectedItem().toString());
                SwingUtilities.updateComponentTreeUI(this);
            }
            
	}
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
	}
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnAceptar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptar1ActionPerformed
        // TODO add your handling code here:
        boolean ok = true;
        if(txtEdadChofer.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado la Edad Mínima del Chofer","",JOptionPane.ERROR_MESSAGE);
            ok = false;
            txtEdadChofer.setBackground(Color.red);
        }
        if(txtEdadOperario.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado la Edad Mínima del Operario","",JOptionPane.ERROR_MESSAGE);
            ok = false;
            txtEdadOperario.setBackground(Color.red);
        }
        
        if(ok == true)
        {
            remiseria.cargarEdadChofer(Integer.valueOf(txtEdadOperario.getText()));
            remiseria.cargarEdadChofer(Integer.valueOf(txtEdadOperario.getText()));
            JOptionPane.showMessageDialog(null,"Edad de Chofer y Operarios guardados con éxito","",JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_btnAceptar1ActionPerformed

    private void txtEdadChoferKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEdadChoferKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 2;
        {if (txtEdadChofer.getText().length()== limite)

            evt.consume();
        }   
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
    }//GEN-LAST:event_txtEdadChoferKeyTyped

    private void txtEdadOperarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEdadOperarioKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 2;
        {if (txtEdadOperario.getText().length()== limite)

            evt.consume();
        }  
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
    }//GEN-LAST:event_txtEdadOperarioKeyTyped

    private void cmbPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaisActionPerformed
        // TODO add your handling code here:
        if (cmbPais.getItemCount()!= 0){
            cmbProvincia.removeAllItems();
            cmbCiudad.removeAllItems();
            String nombrePais = cmbPais.getSelectedItem().toString();
            Pais unPais = remiseria.buscarPais(nombrePais);
            if(unPais != null)
            {
                this.cargarProvinciasCombo(unPais);
            }
        }
    }//GEN-LAST:event_cmbPaisActionPerformed

    private void cmbProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinciaActionPerformed
        // TODO add your handling code here:
        if (cmbProvincia.getItemCount()!= 0){
            cmbCiudad.removeAllItems();
            String nombrePais = cmbPais.getSelectedItem().toString();
            if(!nombrePais.equals(""))
            {
                Pais unPais = remiseria.buscarPais(nombrePais);
                if (unPais != null)
                {
                    String nombreProvincia = cmbProvincia.getSelectedItem().toString();
                    if(nombreProvincia != "")
                    {
                        Provincia unaProvincia = unPais.buscarProvincia(nombreProvincia);
                        if(unaProvincia != null)
                        {
                            this.cargarCiudadesCombo(unaProvincia);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_cmbProvinciaActionPerformed

    private void txtCalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCalleMouseClicked
        // TODO add your handling code here:
        txtCalle.setBackground(Color.white);
    }//GEN-LAST:event_txtCalleMouseClicked

    private void txtNumeroCasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroCasaMouseClicked
        // TODO add your handling code here:
        txtNumeroCasa.setBackground(Color.white);
    }//GEN-LAST:event_txtNumeroCasaMouseClicked

    private void txtNumeroCasaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroCasaKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
    }//GEN-LAST:event_txtNumeroCasaKeyTyped

    private void btnPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaisActionPerformed
        // TODO add your handling code here:
        JDialogPais ventana = new JDialogPais (parent,modal,cmbPais, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnPaisActionPerformed

    private void btnProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProvinciaActionPerformed
        // TODO add your handling code here:
        if(cmbPais.getSelectedItem().toString()!= "")
        {
            JDialogProvincia ventana = new JDialogProvincia (parent,modal,cmbProvincia, remiseria.buscarPais(cmbPais.getSelectedItem().toString()), remiseria);
            ventana.show();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado un Pais"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProvinciaActionPerformed

    private void bnCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnCiudadActionPerformed
        // TODO add your handling code here:
        try{
            //if(cmbProvincia.getSelectedItem().toString()!= ""){
                Pais unPais = remiseria.buscarPais(cmbPais.getSelectedItem().toString());
                JDialogCiudad ventana = new JDialogCiudad (parent,modal,cmbCiudad,unPais.buscarProvincia(cmbProvincia.getSelectedItem().toString()),remiseria );
                ventana.show();
            }
            catch (Exception e)

            {
                JOptionPane.showMessageDialog(null,"No ha seleccionado una Provincia"," ",JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_bnCiudadActionPerformed

    private void cmbCodPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodPaisActionPerformed
        // TODO add your handling code here:
        if (cmbCodPais.getItemCount()!= 0){
            cmbCodArea.removeAllItems();
            String codigoPais = (cmbCodPais.getSelectedItem().toString());
            if(!codigoPais.equals(""))
            {
                CodPais unCodPais = remiseria.buscarCodigoPais(codigoPais);
                if(unCodPais != null)
                {
                    this.cargarCodigoProvinciaCombo(unCodPais);
                }
            }
        }
    }//GEN-LAST:event_cmbCodPaisActionPerformed

    private void btnCodPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodPaisActionPerformed
        // TODO add your handling code here:
        JDialogCodPaisTelefono ventana = new  JDialogCodPaisTelefono (parent,  modal,cmbCodPais, remiseria);
        ventana.show();
    }//GEN-LAST:event_btnCodPaisActionPerformed

    private void btnCodAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodAreaActionPerformed
        // TODO add your handling code here:
        if (!cmbCodPais.getSelectedItem().toString().equals("")){
            JDialogCodAreaTelefono ventana = new JDialogCodAreaTelefono(parent, modal, cmbCodArea, remiseria.buscarCodigoPais((cmbCodPais.getSelectedItem().toString())),remiseria) ;
            ventana.show();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"No ha seleccionado el Código de País"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCodAreaActionPerformed

    private void txtNumeroTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroTelKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
    }//GEN-LAST:event_txtNumeroTelKeyTyped

    private void btnTipoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipoTelefonoActionPerformed
        // TODO add your handling code here:
        JDialogTipoTelefono ventana = new JDialogTipoTelefono (parent,modal,cmbTipodeTelefono,remiseria);
        ventana.show();
    }//GEN-LAST:event_btnTipoTelefonoActionPerformed

    private void btnRecuperarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecuperarActionPerformed
        // TODO add your handling code here:
        txtNickAdministrador.setText(remiseria.getUnUsuarioMaestro().getUssername());
        txtClaveAdministrador.setText(remiseria.getUnUsuarioMaestro().getPassword());
        txtNombre.setText(remiseria.getUnUsuarioMaestro().getNombre());
        txtApellido.setText(remiseria.getUnUsuarioMaestro().getApellido());
    }//GEN-LAST:event_btnRecuperarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        boolean ok = true;
        if(txtNickAdministrador.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado un Nick de Administrador"," ",JOptionPane.ERROR_MESSAGE);
            ok = false;
            txtNickAdministrador.setBackground(Color.red);
        }
        if(txtClaveAdministrador.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado una Clave de Administrador"," ",JOptionPane.ERROR_MESSAGE);
            ok = false;
            txtClaveAdministrador.setBackground(Color.red);
        }        
        if(ok == true)
        {
            remiseria.getUnUsuarioMaestro().setUssername(txtNickAdministrador.getText());
            remiseria.getUnUsuarioMaestro().setPassword(remiseria.MD5(txtClaveAdministrador.getText()));
            JOptionPane.showMessageDialog(null,"Se han modificado con exito los Datos del Administrador"," ",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtNickAdministradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNickAdministradorMouseClicked
        // TODO add your handling code here:
        txtNickAdministrador.setBackground(Color.white);
    }//GEN-LAST:event_txtNickAdministradorMouseClicked

    private void txtClaveAdministradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtClaveAdministradorMouseClicked
        // TODO add your handling code here:
        txtClaveAdministrador.setBackground(Color.white);
    }//GEN-LAST:event_txtClaveAdministradorMouseClicked

    private void btnAceptar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptar2ActionPerformed
        // TODO add your handling code here:
        this.agregarDatosRemiseria();
    }//GEN-LAST:event_btnAceptar2ActionPerformed

    private void txtNombreEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreEmpresaMouseClicked
        // TODO add your handling code here:
        txtNombreEmpresa.setBackground(Color.white);
    }//GEN-LAST:event_txtNombreEmpresaMouseClicked

    private void btnAceptar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptar3ActionPerformed
        // TODO add your handling code here:
        if(remiseria.getNombre()!= null && remiseria.getUnDomicilio() != null && remiseria.getUnTelefono()!= null)
        {
            if(vent == null)
            {
                VentanaPrincipal ventana = new VentanaPrincipal(remiseria,utilidades,unUsuarioMaestro);
                try 
                {
                    remiseria.cargarDatos();
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(JDialogCustomizar.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventana.show();
                this.dispose();
                if(ventana.isShowing()==true)
                {
                    this.hilo1 = new AvisoReserva (remiseria,utilidades);
                    hilo1.start(); 
                    this.hilo2 = new AvisoReserva2 (remiseria,utilidades);
                    hilo2.start(); 
                }
            }
            else
            {
                this.dispose();
            }
        }
        else
        {
             JOptionPane.showMessageDialog(null, "No ha aplicado todos los Cambios de la Configuración", null, JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAceptar3ActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void lbl_logoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMouseReleased
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo de Imagen", "jpg"));
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String URL = fileChooser.getSelectedFile().getAbsolutePath();
            unLogo = new Foto(URL);          
            ImageIcon fot = new ImageIcon(unLogo.getImage());
            Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbl_logo.getWidth(), lbl_logo.getHeight(), Image.SCALE_DEFAULT));
            lbl_logo.setIcon(icono);
            this.repaint();
        }
    }//GEN-LAST:event_lbl_logoMouseReleased

    private void txtCierreDeKilometrajeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCierreDeKilometrajeKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 9;
        {if (txtCierreDeKilometraje.getText().length()== limite)
            evt.consume();
        } 
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
    }//GEN-LAST:event_txtCierreDeKilometrajeKeyTyped

    private void txtPrecioAlquilerMovilKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioAlquilerMovilKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 9;
        {if (txtPrecioAlquilerMovil.getText().length()== limite)

            evt.consume();
        }  
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
    }//GEN-LAST:event_txtPrecioAlquilerMovilKeyTyped

    private void txtPagoOperariosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoOperariosKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 9;
        {if (txtPagoOperarios.getText().length()== limite)

            evt.consume();
        }  
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
    }//GEN-LAST:event_txtPagoOperariosKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try{
            if(utilidades.isDouble(txtPorcentajeChofer.getText())== true)
            {
               double porcentaje = Double.parseDouble(txtPorcentajeChofer.getText());
               if(porcentaje > 0  && porcentaje <= 100)
               {
                   remiseria.cargarPorcentajeChofer(porcentaje/100);
                   JOptionPane.showMessageDialog(null,"Porcentaje de Pago al Chofer guardado con éxito","",JOptionPane.INFORMATION_MESSAGE);
               }
               else
               {
                   if(porcentaje < 0)
                   {
                        JOptionPane.showMessageDialog(null,"Porcentaje ingresado es incorrecto debe ser mayor a 0% ","",JOptionPane.INFORMATION_MESSAGE);
                   }
                   else if (porcentaje > 100)
                   {
                       JOptionPane.showMessageDialog(null,"Porcentaje ingresado es incorrecto debe ser menor a 100%","",JOptionPane.INFORMATION_MESSAGE);
                   }
            }
            }
        }
        catch (Exception e )
        {
            if(txtPorcentajeChofer.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "No ha ingresado un Porcentaje de pago a choferes", null, JOptionPane.ERROR_MESSAGE);
                txtPorcentajeChofer.setBackground(Color.red);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtPorcentajeChoferMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPorcentajeChoferMouseClicked
        // TODO add your handling code here:
        txtPorcentajeChofer.setBackground(Color.white);
    }//GEN-LAST:event_txtPorcentajeChoferMouseClicked

    private void txtPorcentajeChoferKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPorcentajeChoferKeyTyped
        // TODO add your handling code here:
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        if (k == 241 || k == 209) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
        int limite  = 3;
        {if (txtEdadOperario.getText().length()== limite)

            evt.consume();
        }  
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
    }//GEN-LAST:event_txtPorcentajeChoferKeyTyped

    private void txtEdadChoferMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEdadChoferMouseClicked
        // TODO add your handling code here:
        txtEdadChofer.setBackground(Color.white);
    }//GEN-LAST:event_txtEdadChoferMouseClicked

    private void txtEdadOperarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEdadOperarioMouseClicked
        // TODO add your handling code here:
        txtEdadOperario.setBackground(Color.white);
    }//GEN-LAST:event_txtEdadOperarioMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try
        {
            if(utilidades.isDouble(txtCierreDeKilometraje.getText())== true)
            {
                double valor = 10*Double.parseDouble(txtCierreDeKilometraje.getText());
                remiseria.cargarCierreKilometraje(valor);
                JOptionPane.showMessageDialog(null,"Valor de la Ficha guardado con éxito","",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No es un número válido", null, JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception ex)
        {
            if(txtCierreDeKilometraje.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "No ha ingresado el Cierre de Kilomentraje", null, JOptionPane.ERROR_MESSAGE);
                txtCierreDeKilometraje.setBackground(Color.red);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCierreDeKilometrajeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCierreDeKilometrajeMouseClicked
        // TODO add your handling code here:
         txtCierreDeKilometraje.setBackground(Color.white);
    }//GEN-LAST:event_txtCierreDeKilometrajeMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try
        {
            if(utilidades.isDouble(txtPrecioAlquilerMovil.getText())== true)
            {
                Double valor = Double.parseDouble(txtPrecioAlquilerMovil.getText());
                remiseria.cargarPrecioAlquiler(valor);
                JOptionPane.showMessageDialog(null,"Precio del Alquiler del Movil guardado con éxito","",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"EL número ingresado no es válido", null, JOptionPane.ERROR_MESSAGE);

            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado el Precio de alquiler del Móvil");
            txtPrecioAlquilerMovil.setBackground(Color.red);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPrecioAlquilerMovilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrecioAlquilerMovilMouseClicked
        // TODO add your handling code here:
        txtPrecioAlquilerMovil.setBackground(Color.white);
    }//GEN-LAST:event_txtPrecioAlquilerMovilMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try{
            if(utilidades.isDouble(txtPagoOperarios.getText())== true && (rbnPorDia.isSelected() || rbnPorHora.isSelected()))
            {
                double valor = Double.parseDouble(txtPagoOperarios.getText());
                if(rbnPorDia.isSelected() == true)
                {
                    remiseria.cargarPagoOperario(valor, "por día");
                    JOptionPane.showMessageDialog(null,"Precio de Pago de Operario guardado con éxito","",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    if(rbnPorHora.isSelected() == true)
                    {
                        remiseria.cargarPagoOperario(valor, "por hora");
                        JOptionPane.showMessageDialog(null,"Precio de Pago de Operario guardado con éxito","",JOptionPane.INFORMATION_MESSAGE);
                    }
                }    
            }
        }
        catch(Exception e)
        {
            if(txtPagoOperarios.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null,"No ha ingresado el valor de Pago de Operarios ", null, JOptionPane.ERROR_MESSAGE);
                txtPagoOperarios.setBackground(Color.red);
            }
             if(rbnPorDia.isSelected() == false &&  rbnPorHora.isSelected() == false)
             {
                 JOptionPane.showMessageDialog(null,"No ha seleccionado una de las opciones de Pago a Operarios ", null, JOptionPane.ERROR_MESSAGE);
                 
             }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtPagoOperariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPagoOperariosMouseClicked
        // TODO add your handling code here:
        txtPagoOperarios.setBackground(Color.white);
    }//GEN-LAST:event_txtPagoOperariosMouseClicked

    private void txtBajadaBanderaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBajadaBanderaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBajadaBanderaMouseClicked

    private void txtBajadaBanderaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBajadaBanderaKeyTyped
        // TODO add your handling code here:
        int limite  = 10;
        {if (txtBajadaBandera.getText().length()== limite)
            evt.consume();
        }        
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
    }//GEN-LAST:event_txtBajadaBanderaKeyTyped

    private void btnBajadaBanderaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajadaBanderaActionPerformed
        // TODO add your handling code here:
        try
        {
            if(utilidades.isDouble(txtBajadaBandera.getText())== true)
            {
                double valor = Double.parseDouble(txtBajadaBandera.getText());
                remiseria.cargarBajadaBandera(valor);
                JOptionPane.showMessageDialog(null,"Valor de la Bajada de Bandera guardado con éxito","",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No es un número válido", null, JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception ex)
        {
            if(txtBajadaBandera.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "No ha ingresado el valor de Bajada de Bandera", null, JOptionPane.ERROR_MESSAGE);
                txtBajadaBandera.setBackground(Color.red);
            }
        }        
    }//GEN-LAST:event_btnBajadaBanderaActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
        int limite  = 25;
        {if (txtNombre.getText().length()== limite)

            evt.consume();
        }          
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        // TODO add your handling code here:
        int limite  = 25;
        {if (txtApellido.getText().length()== limite)

            evt.consume();
        }           
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtNickAdministradorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNickAdministradorKeyTyped
        // TODO add your handling code here:
        int limite  = 25;
        {if (txtNickAdministrador.getText().length()== limite)

            evt.consume();
        }            
    }//GEN-LAST:event_txtNickAdministradorKeyTyped

    private void txtPrecioRadioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrecioRadioMouseClicked
        // TODO add your handling code here:
        txtPrecioRadio.setBackground(Color.white);        
    }//GEN-LAST:event_txtPrecioRadioMouseClicked
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
        

    private void txtPrecioRadioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioRadioKeyTyped
        // TODO add your handling code here:
        int limite  = 10;
        {if (txtBajadaBandera.getText().length()== limite)
            evt.consume();
        }   
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }         
    }//GEN-LAST:event_txtPrecioRadioKeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try
        {
            if(utilidades.isDouble(txtPrecioRadio.getText())== true)
            {
                Double valor = Double.parseDouble(txtPrecioRadio.getText());
               remiseria.cargarPagoRadio(valor);
                JOptionPane.showMessageDialog(null,"Precio de Pago de la radio guardado con éxito","",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"EL número ingresado no es válido", null, JOptionPane.ERROR_MESSAGE);

            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado Precio de Pago de la radio");
            txtPrecioRadio.setBackground(Color.red);
        }        
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtNumeroTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroTelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroTelActionPerformed

    private void txtNumeroTelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumeroTelMouseClicked
        // TODO add your handling code here:
        txtNumeroTel.setBackground(Color.white);
    }//GEN-LAST:event_txtNumeroTelMouseClicked

    private void txtTiempoEsperaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTiempoEsperaMouseClicked
        // TODO add your handling code here:
        txtTiempoEspera.setBackground(Color.white);
    }//GEN-LAST:event_txtTiempoEsperaMouseClicked

    private void txtTiempoEsperaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTiempoEsperaKeyTyped
        // TODO add your handling code here:
        int limite  = 2;
        {if (txtTiempoEspera.getText().length()== limite)
            evt.consume();
        }        
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }            
    }//GEN-LAST:event_txtTiempoEsperaKeyTyped

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try
        {
            if(utilidades.isNumber(txtTiempoEspera.getText())== true)
            {
                int valor = Integer.parseInt(txtTiempoEspera.getText());
               remiseria.cargarTiempoEspera(valor);
                JOptionPane.showMessageDialog(null,"El primer aviso de vencimiento de la reserva ha sido guardado con éxito","",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"EL número ingresado no es válido", null, JOptionPane.ERROR_MESSAGE);

            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado el primer aviso de vencimiento de la reserva");
            txtTiempoEspera.setBackground(Color.red);
        }            
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        try
        {
            if(utilidades.isNumber(txtTiempoEspera1.getText())== true)
            {
                int valor = Integer.parseInt(txtTiempoEspera1.getText());
                remiseria.cargarTiempoEspera2(valor);
                JOptionPane.showMessageDialog(null,"El segundo aviso de vencimiento de la reserva ha sido guardado con éxito","",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"EL número ingresado no es válido", null, JOptionPane.ERROR_MESSAGE);

            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"No ha ingresado el segundo aviso de vencimiento de la reserva");
            txtTiempoEspera1.setBackground(Color.red);
        }            
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtTiempoEspera1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTiempoEspera1MouseClicked
        // TODO add your handling code here:
        txtTiempoEspera1.setBackground(Color.white);        
    }//GEN-LAST:event_txtTiempoEspera1MouseClicked

    private void txtTiempoEspera1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTiempoEspera1KeyTyped
        // TODO add your handling code here:
        int limite  = 2;
        {if (txtTiempoEspera1.getText().length()== limite)
            evt.consume();
        }        
        if (!esEntero(evt.getKeyChar())) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }           
    }//GEN-LAST:event_txtTiempoEspera1KeyTyped

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
            java.util.logging.Logger.getLogger(JDialogCustomizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogCustomizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogCustomizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogCustomizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
////	    new Principal();
                JDialogCustomizar dialog = new JDialogCustomizar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bnCiudad;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAceptar1;
    private javax.swing.JButton btnAceptar2;
    private javax.swing.JButton btnAceptar3;
    private javax.swing.JButton btnBajadaBandera;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCodArea;
    private javax.swing.JButton btnCodPais;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnPais;
    private javax.swing.JButton btnProvincia;
    private javax.swing.JButton btnRecuperar;
    private javax.swing.JButton btnTipoTelefono;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbCiudad;
    private javax.swing.JComboBox cmbCodArea;
    private javax.swing.JComboBox cmbCodPais;
    private javax.swing.JComboBox cmbMascaras;
    private javax.swing.JComboBox cmbPais;
    private javax.swing.JComboBox cmbProvincia;
    private javax.swing.JComboBox cmbTipodeTelefono;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer10;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer11;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer2;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer4;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer5;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer6;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer7;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer8;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer9;
    private javax.swing.JLabel lbl_logo;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private javax.swing.JRadioButton rbnPorDia;
    private javax.swing.JRadioButton rbnPorHora;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBajadaBandera;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCierreDeKilometraje;
    private javax.swing.JPasswordField txtClaveAdministrador;
    private javax.swing.JTextField txtEdadChofer;
    private javax.swing.JTextField txtEdadOperario;
    private javax.swing.JTextField txtNickAdministrador;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreEmpresa;
    private javax.swing.JTextField txtNumeroCasa;
    private javax.swing.JTextField txtNumeroTel;
    private javax.swing.JTextField txtPagoOperarios;
    private javax.swing.JTextField txtPorcentajeChofer;
    private javax.swing.JTextField txtPrecioAlquilerMovil;
    private javax.swing.JTextField txtPrecioRadio;
    private javax.swing.JTextField txtTiempoEspera;
    private javax.swing.JTextField txtTiempoEspera1;
    // End of variables declaration//GEN-END:variables
}
