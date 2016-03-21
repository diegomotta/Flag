/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.util.HashMap;
import java.util.LinkedList;
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
public class listarViaje extends Thread{
    private Maestro unMaestro;
    private Operario unOperario;
    private Utilidad utilidades;
    private Remiseria remiseria;
    private Viaje viaje;
    private  LinkedList<ImprimirTicket> listaViaje;
    private int valor = 1;
    private int i;
    public listarViaje(Maestro unMaestro,Operario unOperario,Utilidad utilidades, Remiseria remiseria, Viaje viaje,LinkedList<ImprimirTicket> listaViaje )
    {
        this.unMaestro = unMaestro;
        this.unOperario = unOperario;
        this.utilidades = utilidades;
        this.remiseria = remiseria;
        this.viaje = viaje;
        this.listaViaje = listaViaje;
        
    }
    
    public listarViaje()
    {}
    
    @Override
    public void run() 
    {
        if(valor ==1)
        {
                    try{
                         HashMap<String, Object> parametros = new HashMap();
                         parametros.clear();
                         if(unMaestro != null)
                         {
                              parametros.put("operario",unMaestro.getNombre()+ " " + unMaestro.getApellido());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("fechaActual", utilidades.getFechaActual());
                              parametros.put("nombreEmpresa",remiseria.getNombre());
                              parametros.put("direccionEmpresa","Calle "+remiseria.getUnDomicilio().getCalle()+" N° "+remiseria.getUnDomicilio().getNroCasa() );
                              parametros.put("telefonoEmpresa",remiseria.getUnTelefono());
                              parametros.put("paisprovincia", remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()+"-"+remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()+" "+remiseria.getUnDomicilio().getUnPais().getNombrePais());        
                              parametros.put("logo", remiseria.getLogo());  
                              parametros.put("numeroTicket", viaje.getNumeroViaje());  
                              if(viaje.getUnCliente()!= null)
                              {
                                    parametros.put("cliente", viaje.getUnCliente().toString());  
                              }
                              else
                              {
                                    parametros.put("cliente",null);
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
                              parametros.put("numeroTicket", viaje.getNumeroViaje());  

                              if(viaje.getUnCliente()!= null)
                              {
                                    parametros.put("cliente", viaje.getUnCliente().toString());  
                              }
                              else
                              {
                                    parametros.put("cliente",null);
                              }                             
                        }  
                        // C:/Users/garba/Desktop/Reportes/
                        //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/ticketViaje.jrxml");
                        JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/ticketViaje.jrxml");
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(listaViaje));

                        JasperViewer jviewer = new JasperViewer(jasperPrint, false);                   
                        JDialog frame;
                        frame = new JDialog();
                        frame.getContentPane().removeAll();
                        frame.getContentPane().add(jviewer.getContentPane());
                        frame.pack();
                        frame.setSize(1100, 700);
                        frame.setModal(true);
                        frame.setLocationRelativeTo(null);                          
                        frame.show();
                        this.terminar();
                       
                     } 
                     catch (JRException ex) 
                     {
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
   
}
