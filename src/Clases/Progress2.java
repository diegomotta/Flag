/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import Interface.VentanaLogueo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel;
import sismas.SisMas;

/**
 *
 * @author garba
 */
public class Progress2 extends Thread{
    private Progress hilo1;
    private JFrame inter;
    public Progress2(Progress hilo1,JFrame inter)
    {
        this.hilo1 = hilo1;
        this.inter = inter;
    }
    
    
     @Override
    public void run(  ) 
    {
   
              try
                {  
                     UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());  
                }
                catch (Exception e)
                {  
                  JOptionPane.showMessageDialog(null, "Error al Iniciar la liberia LookAndFeel"," ",JOptionPane.ERROR_MESSAGE);  
                }                    
                Remiseria remiseria = null;
                if ((Remiseria.persistencia.restaurar("Remiseria").isEmpty()))
                {
                    try
                    {
                        //remiseria = new Remiseria("MAS", "Argentina", "Misiones", "Posadas");
                        remiseria = new Remiseria(null,null,null,null);

                    }
                    catch (Exception ex)
                    {
                        Logger.getLogger(SisMas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {                    
                    remiseria = (Remiseria) Remiseria.persistencia.restaurar("Remiseria").get(0);
                }
                try
                {  
                    if(remiseria.getUnaConfiguracion().getSkin().equals("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"))
                    {
                         UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                         ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel();
                    }
                    else
                    {
                        if(remiseria.getUnaConfiguracion().getSkin().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"))
                        {
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                        }
                        else if(remiseria.getUnaConfiguracion().getSkin().equals(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel().toString()))
                        {
                            UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());
                        }
                          
                    }
                }
                catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e)
                {  
                    JOptionPane.showMessageDialog(null, "Error al Iniciar  Look And Feel por defecto"," ",JOptionPane.ERROR_MESSAGE);  
                }
//                AvisoReserva hilo = new AvisoReserva(remiseria);
//                hilo.start();

                
                Utilidad utilidades = new Utilidad();

                VentanaLogueo ventana = new VentanaLogueo(remiseria, utilidades);

                VentanaLogueo.setDefaultLookAndFeelDecorated(true);
                
                if(!remiseria.getUnaConfiguracion().getSkin().equals("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel") ||(remiseria.getUnaConfiguracion().getSkin().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")))
                {
                    SubstanceLookAndFeel.setSkin(remiseria.getUnaConfiguracion().getSkin());
                }
                inter.dispose();
                hilo1.terminar();
                ventana.show();  
    }
}
