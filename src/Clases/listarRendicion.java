/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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
public class listarRendicion extends Thread {
    private int valor = 1;
    private Maestro unMaestro;
    private Operario unOperario;
    private Utilidad utilidades;
    private Remiseria remiseria;
    private int i;
    private ChoferPorMovil unChoferPorMovil;
    private Rendicion rendicion;

    public listarRendicion()
    {}
    public listarRendicion(Maestro unMaestro, Operario unOperario, Utilidad utilidades,Remiseria remiseria,ChoferPorMovil unChoferPorMovil,Rendicion rendicion)
    {
        this.unMaestro = unMaestro;
        this.unOperario = unOperario;
        this.utilidades = utilidades;
        this.remiseria = remiseria;
        this.unChoferPorMovil = unChoferPorMovil;
        this.rendicion = rendicion;
    }
    @Override
    public void run() 
    {
        if(valor ==1)
        {
            try{
                  List historial = this.listarViajesRendidos(rendicion);
                  LinkedList <ImprimirViajesRendiciones> lista = new LinkedList<ImprimirViajesRendiciones>(); 
                  if(unChoferPorMovil.getUnMovil().isAlquilado()==false)
                  {
                        Viaje aux = null;
                        Iterator iter = historial.iterator();
                        while (iter.hasNext())
                        {
                              aux = (Viaje) iter.next();                                  
                              lista.add(new ImprimirViajesRendiciones(aux.getUnDomiclio().getUnPais().getNombrePais()+"-"+aux.getUnDomiclio().getUnaProvincia().getNombreProvincia()+"-"+aux.getUnDomiclio().getUnBarrio()+"-"+aux.getUnDomiclio().getUnaDireccionViaje().toString(),"$ "+String.valueOf(aux.getTarifa()),utilidades.getFecha(aux.getFechaHora()),utilidades.getHora(aux.getFechaHora())));
                        }
                  }
                  else
                  {
                      lista.add(new ImprimirViajesRendiciones("Pago de Alquiler del Móvil","$ "+rendicion.getTotalRendicion(),utilidades.getFecha(rendicion.getFechaRendicion()),utilidades.getHora(rendicion.getFechaRendicion())));
                  }                
                  HashMap<String, Object> parametros = new HashMap();
                  parametros.clear();
                  if(unMaestro != null)
                  {
                        parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                        parametros.put("nombreEmpresa",remiseria.getNombre());
                        parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                        parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                        parametros.put("fechaActual", utilidades.getFechaActual());
                        parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                        parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                        parametros.put("logo", remiseria.getLogo());
                        parametros.put("chofer", unChoferPorMovil.getUnChofer());
                        parametros.put("movil", unChoferPorMovil.getUnMovil().getNumeroMovil());
                        parametros.put("total","$ "+String.valueOf(rendicion.getTotalRendicion()));
                        parametros.put("numeroRendicion",String.valueOf(rendicion.getIdRendicion()));
                  }
                  else if(unOperario != null)
                  {
                        parametros.put("operario",unOperario.getNombre()+ " " + unOperario.getApellido());
                        parametros.put("nombreEmpresa",remiseria.getNombre());
                        parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                        parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                        parametros.put("fechaActual", utilidades.getFechaActual());
                        parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                        parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());
                        parametros.put("logo", remiseria.getLogo());
                        parametros.put("chofer", unChoferPorMovil.getUnChofer());
                        parametros.put("movil", unChoferPorMovil.getUnMovil().getNumeroMovil());
                        parametros.put("total","$ "+String.valueOf(rendicion.getTotalRendicion()));
                        parametros.put("numeroRendicion",String.valueOf(rendicion.getIdRendicion()));
                  }  
                  //C:/Users/garba/Desktop/Reportes/
                  //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Rendicion.jrxml");
                  JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Rendicion.jrxml");
                  JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, new JRBeanCollectionDataSource(lista));
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
                  this.terminar();
              } catch (JRException ex) {
                  JOptionPane.showMessageDialog(null, ex.getMessage());
              }
        }
        
        System.out.println("Aca "+ i++);
    }
    
    public void terminar()
    {
        valor = 0;
        System.out.println("termino el ciclo");
    }
     
    private List<Viaje> listarViajesRendidos(Rendicion unaRendicion) {

           List<Viaje> via = new LinkedList<>();

           Collection viajesMovil = unaRendicion.getViajesPagados().values();
           Viaje aux = null;
           Iterator iter = viajesMovil.iterator();
           while(iter.hasNext()){
               aux = (Viaje)iter.next();

                       if(aux.getEstadoRendicion().equals("rendido"))
                       {   
                           via.add(aux);
                       }
           }
           return via;
       }     
    
}
