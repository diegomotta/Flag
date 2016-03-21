/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
/**
 *
 * @author Owner
 */
public class Reloj extends Thread{
    private int i,s,m,h;
    private int num = 1;
    private JTextField reloj;
    private boolean estado = true;
    
    public Reloj() {
    }
    public Reloj(JTextField reloj) {
        this.reloj = reloj;
    }
    
    public void run(){
        while (estado = true){
            //if (entrar = true){
                
                try{
                    sleep(1000);
                }
                catch(InterruptedException e){
                }
                s = s + 1;
                if (s == 60){
                    m = m +1;
                    s = 0;
                    if (m == 60){
                        m = 0;
                        h = h +1;
                    }    
                }
                reloj.setText(this.horario());
        }
    }
    
    public String horario(){
        String retorno = "";
        retorno = h + ":" + m + ":" + s;
        return retorno;
    }
    
     public void terminar(boolean estado){
        this.estado = estado;
    }
}
