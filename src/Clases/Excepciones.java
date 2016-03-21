/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Owner
 */
public class Excepciones extends Exception{
    Excepciones(){}
    
    public Excepciones(String mensaje) {
       super(mensaje);
    }
}
