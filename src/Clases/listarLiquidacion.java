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
public class listarLiquidacion extends Thread{
    private Maestro unMaestro;
    private Operario unOperario;
    private Remiseria remiseria;
    private Utilidad utilidades;
    private Chofer unChofer;
    private Liquidacion unaLiquidacion;
    private Operario unOpera;
    private int valor = 1;
    public listarLiquidacion(){}
    
    public listarLiquidacion(Maestro unMaestro, Operario unOperario,Remiseria remiseria, Utilidad utilidades,Chofer unChofer,Liquidacion unaLiquidacion, Operario unOpera)
    {
        this.unMaestro = unMaestro;
        this.unOperario = unOperario;
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.unChofer = unChofer;
        this.unaLiquidacion = unaLiquidacion;
        this.unOpera = unOpera;
    }
    
    @Override    
    public void run()
    {
        if(valor ==1)
        {
            try
            {
                LinkedList <ImprimirLiquidacion> lista = new LinkedList<ImprimirLiquidacion>(); 
                String numeroLiq = String.valueOf(unaLiquidacion.getIdLiquidacion());
                String total= "$ "+String.valueOf(unaLiquidacion.getTotalLiquidado());
                String totalRendido = null;
                String porcentaje = null;
                if(unaLiquidacion.getUnOperario()!= null)
                {
                    totalRendido = String.valueOf(unaLiquidacion.getDetalle());
                    porcentaje = "No aplicado";
                }
                else if(unaLiquidacion.getUnChofer()!= null)
                {
                    totalRendido = "$ "+String.valueOf(unaLiquidacion.getUnaRendicion().getTotalRendicion());
                    porcentaje= String.valueOf(((remiseria.getUnPorcentajeChofer().getPorcentajeChofer())*100) + " %");
                }
                String fechaLiquidado = utilidades.getFecha(unaLiquidacion.getFechaCreacion());
                lista.add(new ImprimirLiquidacion(numeroLiq,total,totalRendido,porcentaje,fechaLiquidado));                
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

                      parametros.put("logo", remiseria.getLogo());
                      if(unChofer != null)
                      {
                            parametros.put("apellido", unaLiquidacion.getUnChofer().getApellido());
                            parametros.put("nombre", unaLiquidacion.getUnChofer().getNombre());
                            parametros.put("documento",String.valueOf(unaLiquidacion.getUnChofer().getDni()));
                            parametros.put("funcion","Chofer");
                      }
                      else if(unOpera != null)
                      {
                            parametros.put("apellido", unaLiquidacion.getUnOperario().getApellido());
                            parametros.put("nombre", unaLiquidacion.getUnOperario().getNombre());
                            parametros.put("documento",String.valueOf(unaLiquidacion.getUnOperario().getDni()));
                            parametros.put("funcion","Operario");

                      }
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
                      if(unChofer != null)
                      {
                            parametros.put("apellido", unaLiquidacion.getUnChofer().getApellido());
                            parametros.put("nombre", unaLiquidacion.getUnChofer().getNombre());
                            parametros.put("documento",String.valueOf(unaLiquidacion.getUnChofer().getDni()));
                            parametros.put("funcion","Chofer");
                      }
                      else if(unOpera != null)
                      {
                            parametros.put("apellido", unaLiquidacion.getUnOperario().getApellido());
                            parametros.put("nombre", unaLiquidacion.getUnOperario().getNombre());
                            parametros.put("documento",String.valueOf(unaLiquidacion.getUnOperario().getDni()));
                            parametros.put("funcion","Operario");

                      }

                }  
                //C:/Users/garba/Desktop/Reportes/
                //JasperReport jasper = JasperCompileManager.compileReport("src/Reportes/Liquidacion.jrxml");
                JasperReport jasper = JasperCompileManager.compileReport("C:/Users/garba/Desktop/Reportes/Liquidacion.jrxml");
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros,new JRBeanCollectionDataSource(lista));
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
                } 
            catch (JRException ex) 
            {
              JOptionPane.showMessageDialog(null, ex.getMessage());
            }                          
        }
    }
    
    public void terminar()
    {
        valor = 0;
        System.out.println("termino el ciclo");
    }
}
