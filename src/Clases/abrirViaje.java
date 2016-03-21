/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import Interface.JInternalFrameViaje;
import Interface.VentanaPrincipal;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

/**
 *
 * @author garba
 */
public class abrirViaje extends Thread {
    private int valor = 1;
    public abrirViaje(){}
    private Remiseria remiseria;
    private Utilidad utilidades;
    private JButton btnViajes;
    private Operario unOperario;
    private Maestro usuarioMaestro;
    private JDesktopPane jDesktopPane1;
    private int i;
    public abrirViaje(Remiseria remiseria,Utilidad utilidades,JButton btnViajes,Operario unOperario,Maestro usuarioMaestro,JDesktopPane jDesktopPane1)
    {
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        this.btnViajes = btnViajes;
        this.unOperario = unOperario;
        this.usuarioMaestro = usuarioMaestro;
        this.jDesktopPane1  = jDesktopPane1;
    }
    
    @Override
    public void run()
    {
        if(valor ==1)
        {
            try
                {
////                  JInternalFrameViaje ventana3 = new JInternalFrameViaje (remiseria, utilidades,btnViajes,unOperario,usuarioMaestro,this);
//                  remiseria.setUnaVentana(ventana3);
//                  ventana3.pack();
//                  this.jDesktopPane1.add(ventana3);
//                  ventana3.setVisible(true);
//                    try 
//                    {
//                        ventana3.setMaximum(true);
//
//                    }
//                    catch (PropertyVetoException ex)
//                    {
//                        Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//                        this.terminar();
//                    }

                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,"Error al querer abrir la pantalla de Viajes");
                }
            
        }
        System.out.println("Soy ventana viaje "+ i++);
        
    }
    
    public void terminar()
    {
       valor = 0;
       
    }
}
