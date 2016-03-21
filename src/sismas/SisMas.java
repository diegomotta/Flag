/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor. DIEGO
 */
package sismas;
import Clases.AvisoReserva;
import Clases.Progress;
import Clases.Progress2;
import Clases.Remiseria;
import Clases.Utilidad;
import Interface.VentanaLogueo;
import ProgressBar.interfaz;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//import org.pushingpixels.substance.api.SubstanceLookAndFeel;
//import org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel;
import org.jvnet.substance.*;
import org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel;
import ch.randelshofer.quaqua.*;
//import Interface.VentanaLogueo;

/**
 *
 * @author Owner
 */
public class SisMas {
    private static Progress hilo;
    private static Progress2 hilo2;
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
//                final interfaz inter = new interfaz();
//                hilo = new Progress(inter);
//                hilo.start();
//                hilo2 = new Progress2 (hilo,inter);
//                hilo2.start();
          SwingUtilities.invokeLater(new Runnable()
          {  
              public void run()
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


                
                Utilidad utilidades = new Utilidad();

                VentanaLogueo ventana = new VentanaLogueo(remiseria, utilidades);

                VentanaLogueo.setDefaultLookAndFeelDecorated(true);
                
                if(!remiseria.getUnaConfiguracion().getSkin().equals("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel") ||(remiseria.getUnaConfiguracion().getSkin().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")))
                {
                    SubstanceLookAndFeel.setSkin(remiseria.getUnaConfiguracion().getSkin());
                }
                ventana.show();  

              }   
          });       
   }
}
