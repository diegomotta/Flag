
package Clases;

import Interface.JDialogCambiarMovilReserva;
import static java.lang.Thread.sleep;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author garba
 */
public class AvisoReserva2 extends Thread{
    private Utilidad utilidades ;
    private Remiseria remiseria;
    private java.awt.Frame parent;
    private boolean modal;
    private int i;
    private JDialogCambiarMovilReserva ventana;
    private boolean valor = true;
    public AvisoReserva2(){}
    
    public AvisoReserva2(Remiseria remiseria,Utilidad utilidades) {
        this.remiseria = remiseria;
        this.utilidades = utilidades;
        i = 1;
    }
    
    
    @Override
    public void run() 
    {
        this.actualizar();
    }
    
    
    public String obtenerHora() {
        Calendar c = new GregorianCalendar();
        String retorno;
        retorno = Integer.toString(c.get(Calendar.HOUR_OF_DAY)) + ":" + Integer.toString(c.get(Calendar.MINUTE)) + ":" + Integer.toString(c.get(Calendar.SECOND));
        return retorno;
    }
    
           
    public void actualizar() 
    {
        while (true) 
        {          
            if(valor == true)
            {
                Collection reservas = remiseria.buscarReservasActivasDelDiaCliente();
                Reserva aux = null;
                Iterator iter = reservas.iterator();
                while (iter.hasNext()){
                    aux = (Reserva) iter.next();
                    long hora1 = aux.getHoraInicio().getTime();
                    //System.out.println(hora1);
                    long hora2 = utilidades.getHour(obtenerHora()).getTime();
                    //System.out.println(hora2);
                    long resta = hora1 - hora2;
                    //System.out.println("Reservar Fijada N° "+aux.getNumeroReserva()+ " le quedan  " + resta/60000 + " Minutos"); 
                    if(resta == remiseria.getUnTiempoEspera().getValorTiempo() )
                    {
                       // System.out.println("15 minutos");
                        if(aux.getUnCliente() != null)
                        {
                            int ax = JOptionPane.showConfirmDialog(null," Reserva N°: " + aux.getNumeroReserva()+ "\n Cliente: " + aux.getUnCliente() +"\n Dirección: "+ aux.getUnPais()+"-"+aux.getUnaProvincia()+"-"+aux.getUnaCiudad()+"-"+aux.getUnBarrio()+"-"+aux.getUnaDireccionViaje() + "\n Vence en 15 minutos, desea iniciar el viaje?",null,JOptionPane.INFORMATION_MESSAGE);
                            if(ax == JOptionPane.YES_OPTION)
                            {
                                     //JOptionPane.showMessageDialog(null,"El móvil "+aux.getUnMovil().getNumeroMovil() + " está siendo ocupado en otro viaje, seleccione otro móvil para procedecer con la activación de la reserva",null,JOptionPane.ERROR_MESSAGE);
                                     ventana = new JDialogCambiarMovilReserva ( parent, modal, aux, remiseria,  utilidades);
                                     ventana.show();                        
                            }
                            else if(ax == JOptionPane.NO_OPTION)
                            {
                                break;
                            }
                        }

                    }
                    else if(resta == remiseria.getUnTiempoEspera2().getValorTiempo() )
                    {    
                    //    System.out.println("5 minutos");
                        if(aux.getUnCliente() != null)
                        {
                            int ax = JOptionPane.showConfirmDialog(null,"Reserva N°: " + aux.getNumeroReserva()+ "\n Cliente: " + aux.getUnCliente() +"\n Dirección: "+ aux.getUnPais()+"-"+aux.getUnaProvincia()+"-"+aux.getUnaCiudad()+"-"+aux.getUnBarrio()+"-"+aux.getUnaDireccionViaje() + "\n Vence en 5 minutos, desea iniciar el viaje?",null,JOptionPane.INFORMATION_MESSAGE);
                            if(ax == JOptionPane.YES_OPTION)
                            {
                                        //JOptionPane.showMessageDialog(null,"El móvil "+aux.getUnMovil().getNumeroMovil() + " está siendo ocupado en otro viaje, seleccione otro móvil para procedecer con la activación de la reserva",null,JOptionPane.ERROR_MESSAGE);
                                        ventana = new JDialogCambiarMovilReserva ( parent, modal, aux, remiseria,  utilidades);
                                        ventana.show();                         
                            }
                            else if(ax == JOptionPane.NO_OPTION)
                            {
                                break;
                            }
                        }

                    }
    //                else if( resta == 0)
    //                {
    //                    remiseria.aplicarVencimientoReserva(aux);
    //                    remiseria.getUnaVentana().cargarReservas();
    //                }
                }

               // System.out.println(i++); 
                try 
                    {
                       //sleep(60000);
                         sleep(1000);
                    } 
                catch (InterruptedException ex) 
                    {
                        Logger.getLogger(AvisoReserva.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
    }
 

    public Utilidad getUtilidades() {
        return utilidades;
    }

    public void setUtilidades(Utilidad utilidades) {
        this.utilidades = utilidades;
    }

    public Remiseria getRemiseria() {
        return remiseria;
    }

    public void setRemiseria(Remiseria remiseria) {
        this.remiseria = remiseria;
    }

        public void terminar ()
    {
        valor = false;
    }
}