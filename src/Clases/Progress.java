/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author garba
 */
public class Progress extends Thread{
    private JFrame interfaz;
    private int numero = 0;
    public Progress(JFrame interfaz)
    {
        this.interfaz = interfaz;
    }
     @Override
    public void run(  ) 
    {
     if(numero == 0)
     {
        try
        {
            interfaz.show();
        }
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, ex, null, JOptionPane.ERROR_MESSAGE);
        }
     }
     
    }
    
    public void terminar ()
    {
        numero =1;
    }
}
